package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 星魂批量洗练
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCXinghunPiliangXilian extends GCMessage{
	
	/** 星魂批量洗练信息 */
	private com.pwrd.war.common.model.xinghun.XinghunXilianInfo[] xinghunXilianInfo;

	public GCXinghunPiliangXilian (){
	}
	
	public GCXinghunPiliangXilian (
			com.pwrd.war.common.model.xinghun.XinghunXilianInfo[] xinghunXilianInfo ){
			this.xinghunXilianInfo = xinghunXilianInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		xinghunXilianInfo = new com.pwrd.war.common.model.xinghun.XinghunXilianInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.xinghun.XinghunXilianInfo obj = new com.pwrd.war.common.model.xinghun.XinghunXilianInfo();
			obj.setResultId(readInteger());
			obj.setProp1(readString());
			obj.setProp2(readString());
			obj.setProp3(readString());
			obj.setPropValue1(readDouble());
			obj.setPropValue2(readDouble());
			obj.setPropValue3(readDouble());
			obj.setPropQualtity1(readInteger());
			obj.setPropQualtity2(readInteger());
			obj.setPropQualtity3(readInteger());
			xinghunXilianInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(xinghunXilianInfo.length);
		for(int i=0; i<xinghunXilianInfo.length; i++){
			writeInteger(xinghunXilianInfo[i].getResultId());
			writeString(xinghunXilianInfo[i].getProp1());
			writeString(xinghunXilianInfo[i].getProp2());
			writeString(xinghunXilianInfo[i].getProp3());
			writeDouble(xinghunXilianInfo[i].getPropValue1());
			writeDouble(xinghunXilianInfo[i].getPropValue2());
			writeDouble(xinghunXilianInfo[i].getPropValue3());
			writeInteger(xinghunXilianInfo[i].getPropQualtity1());
			writeInteger(xinghunXilianInfo[i].getPropQualtity2());
			writeInteger(xinghunXilianInfo[i].getPropQualtity3());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_XINGHUN_PILIANG_XILIAN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_XINGHUN_PILIANG_XILIAN";
	}

	public com.pwrd.war.common.model.xinghun.XinghunXilianInfo[] getXinghunXilianInfo(){
		return xinghunXilianInfo;
	}

	public void setXinghunXilianInfo(com.pwrd.war.common.model.xinghun.XinghunXilianInfo[] xinghunXilianInfo){
		this.xinghunXilianInfo = xinghunXilianInfo;
	}	
}