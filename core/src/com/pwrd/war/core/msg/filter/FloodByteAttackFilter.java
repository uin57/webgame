package com.pwrd.war.core.msg.filter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;

/**
 * 洪水攻击过滤器
 * 
 * 
 */
public class FloodByteAttackFilter extends AbstractFilter {
	/** 洪水攻击检查 */
	private final String KEY = "FLOOD_CHECK";
	/** 已连接客户端总数 */
	private AtomicInteger clients = new AtomicInteger();

	/**
	 * 类参数构造器
	 * 
	 * @param misIps MIS (后台)系统 IP 地址
	 */
	public FloodByteAttackFilter(String misIps) {
		super(misIps);
	}
	
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession connection,
			Object message) throws Exception {		
		long ms = System.currentTimeMillis(); 	// 当前毫秒
		long curSec = ms / 1000; 				// 当前秒数
		long curMin = curSec / 60; 				// 当前分钟
		
		FloodRecode floodCheck = (FloodRecode) connection.getAttribute(KEY);
		
		int flag = -1;  // 数据包记录状态
		if(floodCheck == null) { // 新连接
			floodCheck = new FloodRecode();
			connection.setAttribute(KEY, floodCheck);
			flag = 0;	// 重置全部
		} else {
			long lastSec = floodCheck.lastSizeTime / 1000;
			long lastMin = lastSec / 60;
			int lastMinSize = floodCheck.lastMinuteSizes;
			int lastSecSize = floodCheck.lastSecendSizes;
			// 同一分钟时间, 判断是否到达临界
			if(lastMin == curMin) { 
				if(lastMinSize < FloodRecode.MAX_MINUTE_SIZES) { // 分钟数据量未到临界
					if(lastSec != curSec) {
						flag = 1; // 重置秒
					} else {
						if(lastSecSize < FloodRecode.MAX_SECEND_SIZES) { // 秒数据量未到临界
							flag = 2; // +++
						}
					}
				}
			} else {
				flag = 0;	// 重置
			}
		}
		
		switch(flag) {
		case 0:
			floodCheck.lastMinuteSizes = 0;
		case 1:
			floodCheck.lastSecendSizes = 0;
		case 2:
			floodCheck.lastMinutePacks ++;
									
			if(message instanceof ByteBuffer) {
				ByteBuffer buf = (ByteBuffer) message;;
				int size = buf.remaining();
				floodCheck.lastSecendSizes += size;
				floodCheck.lastMinuteSizes += size;
			}
			floodCheck.lastSizeTime = System.currentTimeMillis();
			break;
		default:
			String ip = getRemoteIp(connection);
			int port = getRemotePort(connection);
			if(!this.isAllowedMisIp(ip)){
				log.error("非法访问 - 客户端数据包发送数据过多, 关闭链接!!, IP[{}:{}]", ip, port);
				if(connection.isConnected()) {
					connection.close();
				}
				// 加入黑名单
				addBlock(ip);
				return;
			} else {
				log.error("警告 - 管理后台数据包发送数据过多!!, IP[{}:{}]", ip, port);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("接受到数据包 - 秒:{} BYTE, 分:{} BYTE" , new Object[]{ 
					floodCheck.lastSecendSizes, 
					floodCheck.lastMinuteSizes, 
			});
		}
		super.messageReceived(nextFilter,connection, message); 
	}
	
	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession connection)
			throws Exception {
		// 是否到达连接数限制
		int count = clients.getAndIncrement();
		if(count > FloodRecode.MAX_CLIENTS_LIMIT) {
			if(connection.isConnected()) {
				connection.close();
			}
			String ip = getRemoteIp(connection);
			log.error("连接数[{}]到达上限, 关闭[{}]链接!!", count, ip);
			return;
		} 
		super.sessionCreated(nextFilter,connection);
	}
	
	

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession connection)
			throws Exception {
		int count = clients.get();
		if(count > FloodRecode.MAX_CLIENTS_ACTIVE) {
			if(connection.isConnected()) {
				String ip = getRemoteIp(connection);
				log.info("通知客户端客户端连接数[{}]到达上限", count, ip);
				
				// 延迟关闭链接
				final IoSession cnn = connection;
				scheduler.schedule(new Runnable() {
					public void run() {
						cnn.close();
					}
				}, 3, TimeUnit.SECONDS);
			}
			return; // 中断执行
		} else {
			if(log.isDebugEnabled()) {
				String ip = getRemoteIp(connection);
				log.debug("[{}]已连接, 当前连接数[{}]", ip, count);
			}
		}
		
		super.sessionOpened(nextFilter, connection);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession connection)
			throws Exception {
		int count = clients.decrementAndGet();
		if(log.isDebugEnabled()) {
			log.debug("已关闭, 当前连接数[{}]", count);
		}
		super.sessionClosed(nextFilter,connection);
	}
	
	
}
