package com.pwrd.war.tools.msg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class MsgNameAndNum {
	
	private static final Logger logger = Logger.getLogger(MessageGenerator.class);
	
	private String msgName;
	
	private short msgNum;
	
	private Map<String,Short> nameNum;//存放name和number的Map	

	public Map<String,Short> getNameAndNum(Class<?> messageType){
		Field[] fields = messageType.getDeclaredFields();
		Field.setAccessible(fields, true);
		
		try {
			nameNum = new HashMap<String,Short>();
			Set<Short> msgNumSet = new HashSet<Short>();
			for (int i = 0; i < fields.length; i++) {
				msgName = fields[i].getName();
				if (msgName.length() <= 3)
					continue;
				String prefix = msgName.substring(0, 3);
				if ((prefix.equals("CG_") || prefix.equals("GC_"))
						&& ((fields[i].getModifiers() & Modifier.STATIC) != 0)
						& ((fields[i].getModifiers() & Modifier.FINAL) != 0)) {
					msgNum = fields[i].getShort(null);
					if (msgNum <= 0) {
						throw new RuntimeException("消息号溢出！！");
					} else if (msgNumSet.contains(msgNum)) {
						throw new RuntimeException(String.format("%s消息号与其他消息号冲突", msgName));
					}
					msgNumSet.add(msgNum);
					Short msgNum2 = new Short(msgNum);
					nameNum.put(msgName, msgNum2);

				}

			}
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		} finally {
		}
		
		return nameNum;
	}
	
	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public short getMsgNum() {
		return msgNum;
	}
	public void setMsgNum(short msgNum) {
		this.msgNum = msgNum;
	}
	
	

}
