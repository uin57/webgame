package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 服务器给客户端下发人物全信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanDetailInfo extends GCMessage{
	
	/** 玩家 */
	private com.pwrd.war.common.model.human.HumanInfo human;

	public GCHumanDetailInfo (){
	}
	
	public GCHumanDetailInfo (
			com.pwrd.war.common.model.human.HumanInfo human ){
			this.human = human;
	}

	@Override
	protected boolean readImpl() {
		human = new com.pwrd.war.common.model.human.HumanInfo();
					human.setRoleUUID(readString());
							human.setName(readString());
							human.setCamp(readInteger());
							human.setSex(readInteger());
							human.setVocation(readInteger());
							human.setLevel(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(human.getRoleUUID());
		writeString(human.getName());
		writeInteger(human.getCamp());
		writeInteger(human.getSex());
		writeInteger(human.getVocation());
		writeInteger(human.getLevel());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_DETAIL_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_DETAIL_INFO";
	}

	public com.pwrd.war.common.model.human.HumanInfo getHuman(){
		return human;
	}
		
	public void setHuman(com.pwrd.war.common.model.human.HumanInfo human){
		this.human = human;
	}
}