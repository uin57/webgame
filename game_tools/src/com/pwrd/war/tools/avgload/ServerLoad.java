package com.pwrd.war.tools.avgload;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ServerLoad {
	private String time; // 时间
	private String serverName; // 服务器名称
	private String pid; // 进程ID
	private String avgLoad; // 5分钟平均负载
	private String cpuPercent; // CPU占用率
	private String memoryPercent; // 内存占用率

	private String survivor0Size; // Survivor 0 大小
	private String survivor1Size; // Survivor 1 大小
	private String edenSize; // Eden 大小
	private String oldSize; // 年老代内存大小
	private String permSize; // 持久代大小

	private String wholeYongGcCount;// Yong GC总次数
	private String wholeYongGcTime; // Yong GC总时间
	private String wholeFullGcCount;// Full GC总次数
	private String wholeFullGcTime; // Full GC总时间
	private String wholeGcTime; // 垃圾回收总时间

	private int yongGcCount;
	private float yongGcTime;
	private int fullGcCount;
	private float fullGcTime;
	private float gcTime;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAvgLoad() {
		return avgLoad;
	}

	public void setAvgLoad(String avgLoad) {
		this.avgLoad = avgLoad;
	}

	public String getMemoryPercent() {
		return memoryPercent;
	}

	public void setMemoryPercent(String memoryPercent) {
		this.memoryPercent = memoryPercent;
	}

	public String getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(String cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	public String getWholeGcTime() {
		return wholeGcTime;
	}

	public void setWholeGcTime(String wholeGcTime) {
		this.wholeGcTime = wholeGcTime;
	}

	public float getGcTime() {
		return gcTime;
	}

	public void setGcTime(float gcTime) {
		this.gcTime = gcTime;
	}

	public String getSurvivor0Size() {
		return survivor0Size;
	}

	public void setSurvivor0Size(String survivor0Size) {
		this.survivor0Size = survivor0Size;
	}

	public String getSurvivor1Size() {
		return survivor1Size;
	}

	public void setSurvivor1Size(String survivor1Size) {
		this.survivor1Size = survivor1Size;
	}

	public String getEdenSize() {
		return edenSize;
	}

	public void setEdenSize(String edenSize) {
		this.edenSize = edenSize;
	}

	public String getOldSize() {
		return oldSize;
	}

	public void setOldSize(String oldSize) {
		this.oldSize = oldSize;
	}

	public String getPermSize() {
		return permSize;
	}

	public void setPermSize(String permSize) {
		this.permSize = permSize;
	}

	public String getWholeYongGcCount() {
		return wholeYongGcCount;
	}

	public void setWholeYongGcCount(String wholeYongGcCount) {
		this.wholeYongGcCount = wholeYongGcCount;
	}

	public String getWholeYongGcTime() {
		return wholeYongGcTime;
	}

	public void setWholeYongGcTime(String wholeYongGcTime) {
		this.wholeYongGcTime = wholeYongGcTime;
	}

	public String getWholeFullGcCount() {
		return wholeFullGcCount;
	}

	public void setWholeFullGcCount(String wholeFullGcCount) {
		this.wholeFullGcCount = wholeFullGcCount;
	}

	public String getWholeFullGcTime() {
		return wholeFullGcTime;
	}

	public void setWholeFullGcTime(String wholeFullGcTime) {
		this.wholeFullGcTime = wholeFullGcTime;
	}

	public float getYongGcTime() {
		return yongGcTime;
	}

	public void setYongGcTime(float yongGcTime) {
		this.yongGcTime = yongGcTime;
	}

	public float getFullGcTime() {
		return fullGcTime;
	}

	public void setFullGcTime(float fullGcTime) {
		this.fullGcTime = fullGcTime;
	}

	public int getYongGcCount() {
		return yongGcCount;
	}

	public void setYongGcCount(int yongGcCount) {
		this.yongGcCount = yongGcCount;
	}

	public int getFullGcCount() {
		return fullGcCount;
	}

	public void setFullGcCount(int fullGcCount) {
		this.fullGcCount = fullGcCount;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
