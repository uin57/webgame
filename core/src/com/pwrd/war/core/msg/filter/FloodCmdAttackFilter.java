package com.pwrd.war.core.msg.filter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.common.IoSession;

/**
 * 洪水攻击过滤器
 * 
 * 
 */
public class FloodCmdAttackFilter extends AbstractFilter {
	/** 洪水攻击检查 */
	private final String KEY = "FLOOD_CHECK";

	/**
	 * 类参数构造器
	 * 
	 * @param misIps MIS (后台)系统 IP 地址
	 */
	public FloodCmdAttackFilter(String misIps) {
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
			long lastSec = floodCheck.lastPackTime / 1000;
			long lastMin = lastSec / 60;
			int lastMinPack = floodCheck.lastMinutePacks;
			int lastSecPack = floodCheck.lastSecendPacks;
			// 同一分钟时间, 判断是否到达临界
			if(lastMin == curMin) { 
				if(lastMinPack < FloodRecode.MAX_MINUTE_PACKS) { // 未到达分钟临界
					if(lastSec != curSec) {
						flag = 1; // 重置秒
					} else {
						if(lastSecPack < FloodRecode.MAX_SECEND_PACKS){
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
			floodCheck.lastMinutePacks = 0;
		case 1:
			floodCheck.lastSecendPacks = 0;
		case 2:
			floodCheck.lastSecendPacks ++;
			floodCheck.lastMinutePacks ++;
			floodCheck.lastPackTime = System.currentTimeMillis();
			break;
		default:
			SocketAddress address = (SocketAddress) connection.getRemoteAddress();
			InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
			String ip = inetSocketAddress.getAddress().getHostAddress();
			int port = inetSocketAddress.getPort();
			if(!this.isAllowedMisIp(ip)){
				log.error("非法访问 - 客户端数据包发送频率过高, 关闭链接!!, IP[{}:{}]", ip, port);
				if(connection.isConnected()) {
					connection.close();
				}
				// 加入黑名单
				addBlock(ip);
				return; // 中断执行链
			} else {
				log.error("警告 - 管理后台数据包发送频率过高!!, IP[{}:{}]", ip, port);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("接受到数据包 - 秒: {}, 分: {}" , new Object[]{ 
					floodCheck.lastSecendPacks, 
					floodCheck.lastMinutePacks, 
			});
		}
		super.messageReceived(nextFilter,connection, message); 
	}
}
