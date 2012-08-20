package com.pwrd.war.common.model;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.ServerTypes;

/**
 * LOGServer状态消息
 * 
 * 
 */
public class LogServerStatus extends ServerStatus {

	public LogServerStatus() {
		this.serverType = ServerTypes.LOG;
	}

	@Override
	public String toString() {
		JSONObject statusJSON = super.getStatusJSON();
		return statusJSON.toString();
	}

}
