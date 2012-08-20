package com.pwrd.war.core.msg.filter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象过滤器
 * 
 * 
 */
public class AbstractFilter extends IoFilterAdapter {

	private static final String REMOTE_HOST_PORT = "REMOTE_HOST_PORT";

	private static final String REMOTE_HOST_IP = "REMOTE_HOST_IP";

	/** 日志 */
	protected Logger log = LoggerFactory.getLogger(getClass());
	/** MIS 系统 IP 地址 */
	private Pattern[] misIpPatterns = null;
	/** 因为洪水攻击被拒绝的IP */
	protected Map<String, AtomicInteger> blockedIps = new ConcurrentHashMap<String, AtomicInteger>();

	/**
	 * 类参数构造器
	 * 
	 * @param misIps MIS (后台)系统 IP 地址
	 */
	public AbstractFilter(String misIps) {
		// 构建 IP 地址验证规则表达式
		this.misIpPatterns = this.buildMisIpPatterns(misIps);
	}

	/**
	 * 定时器
	 */
	protected ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4, new ThreadFactory() {
		private AtomicInteger threadNumber = new AtomicInteger();
		public Thread newThread(Runnable r) {
			SecurityManager s = System.getSecurityManager();
	        ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
	        Thread t = new Thread(group, r);
	        t.setName("Filter Scheduler Task Thread - " + threadNumber.incrementAndGet());
	        return t;
		}
	});

	/**
	 * 已BLOCK次数
	 * 
	 * @param ip
	 * @return
	 */
	protected int getBlock(String ip) {
		if (blockedIps.containsKey(ip)) {
			AtomicInteger blocks = blockedIps.get(ip);
			return blocks.get();
		}
		return 0;
	}

	/**
	 * 添加禁用IP
	 * 
	 * @param ip
	 */
	protected void addBlock(final String ip) {
		AtomicInteger blocks = blockedIps.get(ip);
		if (blocks == null) {
			blocks = new AtomicInteger();
			blockedIps.put(ip, blocks);
		}
		blocks.incrementAndGet();

		if (blocks.get() > FloodRecode.BLOCK_DETECT_COUNT) {
			// 定期移除黑名单定时器
			scheduler.schedule(new Runnable() {
				public void run() {
					if (blockedIps.containsKey(ip)) {
						blockedIps.remove(ip);
						log.error("移除洪水攻击黑名单IP[{}]", ip);
					}
				}
			}, FloodRecode.BLOCK_IP_MINUS, TimeUnit.MINUTES);
		}
	}

	/**
	 * 获取远程IP
	 * 
	 * @param connection
	 * @return
	 */
	protected String getRemoteIp(IoSession connection) {
		if (connection != null) {
			String att = (String) connection.getAttribute(REMOTE_HOST_IP);
			if (att != null) {
				return att;
			}

			SocketAddress address = (SocketAddress) connection
					.getRemoteAddress();
			if (address != null) {
				InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
				if (inetSocketAddress != null) {
					InetAddress addr = inetSocketAddress.getAddress();
					if (addr != null) {
						String ip = addr.getHostAddress();
						return ip;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取远程端口
	 * 
	 * @param connection
	 * @return
	 */
	protected int getRemotePort(IoSession connection) {
		if (connection != null) {
			Integer att = (Integer) connection.getAttribute(REMOTE_HOST_PORT);
			if (att != null) {
				return att;
			}

			SocketAddress address = (SocketAddress) connection
					.getRemoteAddress();
			if (address != null) {
				InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
				if (inetSocketAddress != null) {
					return inetSocketAddress.getPort();
				}
			}
		}
		return -1;
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession connection)
			throws Exception {
		String ip = getRemoteIp(connection);
		if (ip != null) {
			int blockedCount = getBlock(ip);
			if (blockedCount > FloodRecode.BLOCK_DETECT_COUNT) {
				log.error("访问 - 加入到洪水攻击黑名单的IP[{}]", ip);
				if (connection.isConnected()) {
					connection.close();
				}
				return;
			}
		}
		super.sessionOpened(nextFilter, connection);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession connection)
			throws Exception {
		if (connection.containsAttribute(REMOTE_HOST_IP)) {
			connection.removeAttribute(REMOTE_HOST_IP);
		}
		if (connection.containsAttribute(REMOTE_HOST_PORT)) {
			connection.removeAttribute(REMOTE_HOST_PORT);
		}
		super.sessionClosed(nextFilter, connection);
	}

	/**
	 * 构建 MIS (后台)系统 IP 地址正则表达式
	 * 
	 * @param misIps MIS 系统 IP 地址
	 * @return
	 */
	private Pattern[] buildMisIpPatterns(String misIps) {
		if (misIps == null) {
			return null;
		}

		// 以逗号分割字符串
		String[] ips = misIps.split(",");
		// 创建正则表达式对象
		Pattern[] misIpPatterns = new Pattern[ips.length];
		
		for (int i = 0; i < ips.length; i++) {
			// . 替换为 [.], * 替换为 [0-9]*
			String s = ips[i].replace(".", "[.]").replace("*", "[0-9]*");
			// 创建正则表达式对象
			misIpPatterns[i] = Pattern.compile(s);
		}

		return misIpPatterns;
	}

	/**
	 * 验证是否为允许访问的 MIS (后台)系统 IP 地址
	 * 
	 * @param ip
	 * @return
	 */
	public boolean isAllowedMisIp(String ip) {
		if (ip == null) {
			return false;
		}

		if (this.misIpPatterns == null) {
			// 如果没有定义后台系统 IP 地址验证规则, 
			// 则直接返回 false
			return false;
		}
		
		for (Pattern p : this.misIpPatterns) {
			if (p != null && p.matcher(ip).matches()) {
				return true;
			}
		}

		return false;
	}
}