package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.common.handler.CommonHandlerFactory;

/**
 * 选择确认对话框 ok 或 cancel 选项
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSelectOption extends CGMessage{
	
	/** 操作标识 */
	private String tag;
	/** 选项值 */
	private String value;
	
	public CGSelectOption (){
	}
	
	public CGSelectOption (
			String tag,
			String value ){
			this.tag = tag;
			this.value = value;
	}
	
	@Override
	protected boolean readImpl() {
		tag = readString();
		value = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(tag);
		writeString(value);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELECT_OPTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELECT_OPTION";
	}

	public String getTag(){
		return tag;
	}
		
	public void setTag(String tag){
		this.tag = tag;
	}

	public String getValue(){
		return value;
	}
		
	public void setValue(String value){
		this.value = value;
	}

	@Override
	public void execute() {
		CommonHandlerFactory.getHandler().handleSelectOption(this.getSession().getPlayer(), this);
	}
}