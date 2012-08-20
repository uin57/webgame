package com.pwrd.war.tools.serverstatus.datagen;

import com.pwrd.war.common.constants.ServerTypes;
import com.pwrd.war.tools.serverstatus.AgentServerStatus;
import com.pwrd.war.tools.serverstatus.DbsServerStatus;
import com.pwrd.war.tools.serverstatus.GameServerStatus;
import com.pwrd.war.tools.serverstatus.StatusInfo;

/**
 * 服务器状态设置
 * 
 * 
 */
public class StatusInfoSetor {
	/** 类型标识字符串 */
	private static final String TYPE_STR = "type:";

	/**
	 * 设置状态数据
	 * 
	 * @param statusStr
	 * @return
	 */
	public static StatusInfo setData(String statusStr) {
		int index = statusStr.indexOf(TYPE_STR);
		if (index <= 0) {
			return null;
		}
		int typeIndex = index + TYPE_STR.length();
		String serverType = statusStr.substring(typeIndex, typeIndex + 1);
		StatusInfo status = StatusInfoCreator.createStatusInfo(serverType);
		if (status == null) {
			return null;
		}
		String[] statusInfos = statusStr.split(",");
		for (String statusInfo : statusInfos) {
			String[] statusInfoStrs = statusInfo.split(":");
			if (statusInfoStrs.length < 2) {
				continue;
			}
			setValue(statusInfoStrs, status);
		}
		return status;
	}

	/**
	 * 根据状态名设置相关信息
	 * 
	 * @param fieldNameAndValue
	 * @param statusInfo
	 */
	public static void setValue(String[] fieldNameAndValue,
			StatusInfo statusInfo) {
		String _fieldName = fieldNameAndValue[0].trim();
		String _fieldVal = fieldNameAndValue[1].trim();

		if (_fieldName.equals("id")) {
			statusInfo.setServerID(_fieldVal);
		} else if (_fieldName.equals("type")) {
			statusInfo.setServerType(_fieldVal);
		} else if (_fieldName.equals("ip")) {
			// statusInfo.setIp(_fieldVal);
		} else if (_fieldName.equals("freeMemory")) {
			statusInfo.setFreeMemory(_fieldVal);
		} else if (_fieldName.equals("usedMemory")) {
			statusInfo.setUsedMemory(_fieldVal);
		} else if (_fieldName.equals("totalMemory")) {
			statusInfo.setTotalMemory(_fieldVal);
		} else if (_fieldName.equals("cpuUsageRate")) {
			statusInfo.setCpuUsgRate(_fieldVal);
		} else if (_fieldName.equals("lastUpdateTime")) {
			statusInfo.setLastUpdateTime(Long.parseLong(_fieldVal));
		} else if (_fieldName.equals("agentStatus")) {
			int fieldsLen = fieldNameAndValue.length;
			if (fieldsLen != 4) {
				return;
			}
			String cacheStrTp = fieldNameAndValue[fieldsLen - 1].trim();
			String[] cacheData = cacheStrTp.split("/");

			((DbsServerStatus) statusInfo).setCacheUsed(cacheData[0]);
			// ((DbsServerStatus)statusInfo).setCacheTotal(cacheData[1]);
		} else if (_fieldName.equals("evicted")) {
			// ((DbsServerStatus)statusInfo).setEvicted(_fieldVal.replace("]",
			// ""));
		} else if (_fieldName.equals("state")) {
			// ((GameServerStatus)statusInfo).setState(_fieldVal);
		} else if (_fieldName.equals("onlinePlayerCount")) {
			if (Integer.parseInt(statusInfo.getServerType()) == ServerTypes.GAME) {
				((GameServerStatus) statusInfo).setOnlines(_fieldVal);
			} else {
				((AgentServerStatus) statusInfo).setOnlines(_fieldVal);
			}
		}
	}

}
