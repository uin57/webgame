package com.pwrd.war.gameserver.player.msg;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.player.GamingStateIndex;

/**
 *消息类型和用户gaming状态配置表
 *
 * @author mxy
 *
 */
public enum MsgTypeToGameState {

	// /////////////////////////////////////////////////
	// 各模块通用的消息配置0x7fffffff为所有消息都适合
	//CG_PING("CG_PING", GamingStateIndex.IN_BATTLE.getValue() | GamingStateIndex.IN_COUNTRYWAR.getValue()),
	// /////////////////////////////////////////////////
	
	CG_ADMIN_COMMAND("CG_ADMIN_COMMAND",0x7fffffff),
	CG_PING("CG_PING", 0x7fffffff),
	CG_HANDSHAKE("CG_HANDSHAKE", 0x7fffffff),
	CG_SELECT_OPTION("CG_SELECT_OPTION", 0x7fffffff);
	
	
	
	/*
	 * 将MessageType的消息名称和值放到一个map里
	 */
	private static Map<Short, String> messageTypeMap;
	static {
		messageTypeMap = new HashMap<Short, String>();
		Class<MessageType> messageType = MessageType.class;
		Field[] fields = messageType.getFields();
		for (Field field : fields) {
			try {
				messageTypeMap
						.put(field.getShort(messageType), field.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 枚举构造
	 */
	private int value;
	private String label;
	public int getValue() {
        return value;
    }
	MsgTypeToGameState(String label, int value) {
		this.value = value;
		this.label=label;
	}
	
	
	/**
	 * 根据消息类型返回配置的游戏状态
	 * @param msgType
	 * @return
	 */
	public static int getGameStateByMsgType(short msgType){
		String typeName = messageTypeMap.get(msgType);
		for (MsgTypeToGameState msgTypeToGameState : MsgTypeToGameState.values()) {
			if(msgTypeToGameState.label.equals(typeName)){
				return msgTypeToGameState.getValue();
			}
		}
		return 0x7fffffff;
	}

}
