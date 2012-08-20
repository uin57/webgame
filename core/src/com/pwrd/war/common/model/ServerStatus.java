package com.pwrd.war.common.model;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.ServerTypes;
import com.pwrd.war.core.util.JVMMonitor;
import com.pwrd.war.core.util.MemUtils;
/**
 * 服务器状态消息,用于向监控者通过服务器的运行状态
 * 
  *
 * 
 */
public class ServerStatus {
	/** 服务器在本组服务器内的索引值 */
	protected int serverIndex;
	/** 服务器ID */
	protected String serverId;
	/** 服务器名称 */
	protected String serverName;
	/** 服务器的类型 */
	protected int serverType;
	/** 服务器IP */
	protected String ip;
	/** 服务器端口 */
	protected String port;
	/** 最后更新时间 */
	protected long timestamp;
	/** 空闲的内存空间 */
	protected long freeMemory;
	/** 占用的内存空间 */
	protected long usedMemory;
	/** 总共的内存空间 */
	protected long totalMemory;
	/** 当前Cpu利用率 */
	protected double cpuRate;
	/** 当前程序的版本号 */
	protected String version;


	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the freeMemory
	 */
	public long getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @param freeMemory
	 *            the freeMemory to set
	 */
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	/**
	 * @return the usedMemory
	 */
	public long getUsedMemory() {
		return usedMemory;
	}

	/**
	 * @param usedMemory
	 *            the usedMemory to set
	 */
	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	/**
	 * @return the totalMemory
	 */
	public long getTotalMemory() {
		return totalMemory;
	}

	/**
	 * @return the cpuRate
	 */
	public double getCpuRate() {
		return cpuRate;
	}

	/**
	 * @param cpuRate
	 *            the cpuRate to set
	 */
	public void setCpuRate(double cpuRate) {
		this.cpuRate = cpuRate;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public void setServerIndex(int serverIndex) {
		this.serverIndex = serverIndex;
	}

	public int getServerIndex() {
		return serverIndex;
	}

	public boolean isGameServer() {
		return this.serverType == ServerTypes.GAME;
	}

	/**
	 * 刷新当前服务器状态:CPU + 内存,请注意,该方法需要调用调用利用Linux的/proc/stat去取得数据,FIXME
	 * 这可能是一个较重的操作,需要进行一些修改?
	 */
	public void refresh() {
		try {
			this.cpuRate = JVMMonitor.instance.getCpuUsage();
			this.freeMemory = MemUtils.getFreeMemoryMB();
			this.usedMemory = MemUtils.getUsedMemoryMB();
			this.totalMemory = MemUtils.getTotalMemoryMB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getStatusJSON() {
		JSONObject _status = new JSONObject();
		_status.put("id", getServerId());
		_status.put("index", getServerIndex());
		_status.put("name", getServerName());
		_status.put("type", getServerType());
		_status.put("ip", getIp());
		_status.put("port", getPort());
		_status.put("freeMemory", getFreeMemory());
		_status.put("usedMemory", getUsedMemory());
		_status.put("totalMemory", getTotalMemory());
		_status.put("cpuUsageRate", getCpuRate());
		_status.put("lastUpdateTime", this.getTimestamp());
		_status.put("version", this.version != null ? this.version : "");
		return _status;
	}
}