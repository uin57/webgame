package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 查询账户余额的结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerQueryAccount extends GCMessage{
	
	/** MM余额 */
	private int mmBalance;

	public GCPlayerQueryAccount (){
	}
	
	public GCPlayerQueryAccount (
			int mmBalance ){
			this.mmBalance = mmBalance;
	}

	@Override
	protected boolean readImpl() {
		mmBalance = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mmBalance);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_QUERY_ACCOUNT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_QUERY_ACCOUNT";
	}

	public int getMmBalance(){
		return mmBalance;
	}
		
	public void setMmBalance(int mmBalance){
		this.mmBalance = mmBalance;
	}
}