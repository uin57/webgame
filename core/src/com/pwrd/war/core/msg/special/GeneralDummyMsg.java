package com.pwrd.war.core.msg.special;

import java.util.Date;

import com.pwrd.war.core.msg.BaseMessage;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;

/**
 * 这个消息基本上不会用到,只用于方便测试使用
 * 
 * 
 */
public abstract class GeneralDummyMsg extends BaseMessage {
	public GeneralDummyMsg() {
	}


	@Override
	public byte readByte() {
		return super.readByte();
	}


	@Override
	public void readBytes(byte[] bytes) {
		super.readBytes(bytes);
	}

	
	@Override	
	public Date readDate() {
		return super.readDate();
	}

	@Override
	public double readDouble() {
		return super.readDouble();
	}

	@Override
	public float readFloat() {
		return super.readFloat();
	}


	@Override
	public boolean readImpl() {
		return false;
	}

	@Override
	public int readInt() {
		return super.readInt();
	}

	@Override
	public long readLong() {
		return super.readLong();
	}

	@Override
	public void readMessage(IMessage msg) throws MessageParseException {
		super.readMessage(msg);
	}

	@Override
	public short readShort() {
		return super.readShort();
	}

	@Override
	public String readString() {
		return super.readString();
	}

	@Override
	public String readString(String charset) {
		return super.readString(charset);
	}

	@Override
	public void writeByte(int data) {
		super.writeByte(data);
	}

	@Override
	public void writeBytes(byte[] data) {
		super.writeBytes(data);
	}

	@Override
	public void writeDate(Date data) {
		super.writeDate(data);
	}

	@Override
	public void writeDouble(double data) {
		super.writeDouble(data);
	}

	@Override
	public void writeFloat(float data) {
		super.writeFloat(data);
	}

	@Override
	public boolean writeImpl() {
		return false;
	}

	@Override
	public void writeInt(float data) {
		super.writeInt(data);
	}

	@Override
	public void writeInt(int data) {
		super.writeInt(data);
	}

	@Override
	public void writeLong(long data) {
		super.writeLong(data);
	}

	@Override
	public void writeMessage(IMessage msg) throws MessageParseException {
		super.writeMessage(msg);
	}

	@Override
	public void writeMessageWithoutHead(IMessage msg) throws MessageParseException {
		super.writeMessageWithoutHead(msg);
	}

	@Override
	public void writeShort(int data) {
		super.writeShort(data);
	}

	@Override
	public void writeString(String str, String charset) {
		super.writeString(str, charset);
	}

	@Override
	public void writeString(String str) {
		super.writeString(str);
	}
}