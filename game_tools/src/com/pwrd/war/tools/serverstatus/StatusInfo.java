package com.pwrd.war.tools.serverstatus;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.pwrd.war.common.constants.ServerTypes;

/**
 * 状态信息
 * 
 * 
 */
public class StatusInfo {
	/** 服务器类型 */
	private String serverType;
	/** 服务器ID */
	private String serverID;
	/** 空闲内存 */
	private String freeMemory;
	/** 使用的内存 */
	private String usedMemory;
	/** 总内存 */
	private String totalMemory;
	/** cpu利用率 */
	private String cpuUsgRate;
	/** 最近更新时间 */
	private long lastUpdateTime;
	/** 记录创建数据时的excel列索引 */
	private int cellIndex;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

	/**
	 * 创建Excel行
	 * 
	 * @param row
	 */
	public void createXlsRow(HSSFRow row) {
		// 创建第i个单元格
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue(getTimeStr(lastUpdateTime));
		cell = row.createCell(cellIndex++);
		cell.setCellValue(Integer.parseInt(freeMemory));
		cell = row.createCell(cellIndex++);
		cell.setCellValue(Integer.parseInt(usedMemory));
		cell = row.createCell(cellIndex++);
		cell.setCellValue(Integer.parseInt(totalMemory));
		cell = row.createCell(cellIndex++);
		cell.setCellValue(Double.parseDouble(cpuUsgRate));
	}

	/**
	 * 获得服务器名
	 * 
	 * @return
	 */
	public String getServerName() {
		int serverIntType = Integer.parseInt(serverType);
		switch (serverIntType) {
		case ServerTypes.DBS:
			return "DBSServer";
		case ServerTypes.GAME:
			return "GameServer";
		case ServerTypes.LOGIN:
			return "LoginServer";
		case ServerTypes.WORLD:
			return "WorldServer";
		case ServerTypes.AGENT:
			return "AgentServer";
		default:
			return "Unknown";

		}
	}

	/**
	 * 格式化时间
	 * 
	 * @param updateTime
	 * @return
	 */
	private String getTimeStr(long updateTime) {
		Date date = new Date(updateTime);
		return sdf.format(date);
	}

	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getServerID() {
		return serverID;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public String getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}

	public String getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getCpuUsgRate() {
		return cpuUsgRate;
	}

	public void setCpuUsgRate(String cpuUsgRate) {
		this.cpuUsgRate = cpuUsgRate;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String toString() {
		return serverID + " : " + serverType + " : " + freeMemory + " : "
				+ usedMemory + " : " + totalMemory;
	}
}
