package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 显示确认对话框
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowOptionDlg extends GCMessage{
	
	/** 窗口标题 */
	private String title;
	/** 窗口内容 */
	private String content;
	/** 操作标识 */
	private String tag;
	/** ${field.comment} */
	private String okText;
	/** ${field.comment} */
	private String cancelText;

	public GCShowOptionDlg (){
	}
	
	public GCShowOptionDlg (
			String title,
			String content,
			String tag,
			String okText,
			String cancelText ){
			this.title = title;
			this.content = content;
			this.tag = tag;
			this.okText = okText;
			this.cancelText = cancelText;
	}

	@Override
	protected boolean readImpl() {
		title = readString();
		content = readString();
		tag = readString();
		okText = readString();
		cancelText = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(title);
		writeString(content);
		writeString(tag);
		writeString(okText);
		writeString(cancelText);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_OPTION_DLG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_OPTION_DLG";
	}

	public String getTitle(){
		return title;
	}
		
	public void setTitle(String title){
		this.title = title;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public String getTag(){
		return tag;
	}
		
	public void setTag(String tag){
		this.tag = tag;
	}

	public String getOkText(){
		return okText;
	}
		
	public void setOkText(String okText){
		this.okText = okText;
	}

	public String getCancelText(){
		return cancelText;
	}
		
	public void setCancelText(String cancelText){
		this.cancelText = cancelText;
	}
}