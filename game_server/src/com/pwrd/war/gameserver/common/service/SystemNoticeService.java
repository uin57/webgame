package com.pwrd.war.gameserver.common.service;

import java.util.List;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.GameColor;
import com.pwrd.war.common.constants.NoticeTypes;
import com.pwrd.war.common.constants.SysMsgShowTypes;
import com.pwrd.war.common.constants.SysPromptType;
import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.common.msg.GCSystemMessage;
import com.pwrd.war.gameserver.common.msg.GCSystemNotice;
import com.pwrd.war.gameserver.common.msg.GCSystemPrompt;
import com.pwrd.war.gameserver.scene.Scene;

/**
 * 
 * 系统消息提示服务类
 * @author dengdan
 *
 */
public class SystemNoticeService implements InitializeRequired{
	
	/** 调度任务服务 */
	@SuppressWarnings("unused")
	private ScheduleService schService;
	
	public SystemNoticeService(ScheduleService schService)
	{	
		this.schService = schService;
	}

	@Override
	public void init() {
		
	}
	public void sendNotice(Short noticeType, String content){
		sendNotice(noticeType, content, null);
	}
	
	/**
	 * 全世界发送公告
	 * 
	 * @param noticeType
	 * @param content
	 */
	public void sendNotice(Short noticeType, String content,String sceneId) {
		switch (noticeType) {
		//发送公告消息
		case NoticeTypes.system:
			sendSystemNotice(content, (short) Globals.getGameConstants()
					.getDefaultNoticeSpeed(),sceneId);
			break;
		//发送公告消息(聊天框)
		case NoticeTypes.sysChat:
			sendSysWithChatNotice(content,sceneId);
			break;
		//发送重要提示消息
		case NoticeTypes.important:
			sendImportPromptMessage(content,sceneId);
			break;
		//发送普通提示消息(聊天框)
		case NoticeTypes.common:
			sendCommonNotice(content,sceneId);
			break;
		//发送错误提示消息
		case NoticeTypes.error:
			sendErrorNotice(content,sceneId);
			break;
		//发送提示按钮消息
		case NoticeTypes.button:
			sendButtonNotice(content,sceneId);
			break;
		//发送头顶提示消息
		case NoticeTypes.head:
			sendHeadNotice(content,GameColor.SYSMSG_COMMON.getRgb(),sceneId);
			break;
		//发送特殊提示消息
		case NoticeTypes.special:
			sendSpecialNotice(content,sceneId);
			break;
		//发送正确提示消息
		case NoticeTypes.right:
			sendRightNotice(content,sceneId);
			break;
		}
	}
	
	public void sendMultiNotice(String content, String sceneId,Short... noticeTypes){
		if(noticeTypes == null || noticeTypes.length == 0){
			return;
		}
		for(short noticeType : noticeTypes){
			sendNotice(noticeType,content,sceneId);
		}
	}
	
	/**
	 * 根据指定位置频道发送多个系统提示消息
	 * 
	 * @param content
	 * @param noticeTypes
	 */
	public void sendMultiNotice(String content,Short... noticeTypes){
		if(noticeTypes == null || noticeTypes.length == 0){
			return;
		}
		for(short noticeType : noticeTypes){
			sendNotice(noticeType,content,null);
		}
	}
	/**
	 * 
	 * 向本服务器上的玩家发送公告
	 * @param content
	 * @param speed
	 */
	private static void sendSystemNotice(String content, short speed,String sceneId) {
		GCSystemNotice msg = new GCSystemNotice(content,
				GameColor.SYSMSG_NOTICE.getRgb(), speed,(short)6);
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 向全服发送重要系统公告[聊天框]
	 * 
	 * @param content
	 */
	private static void sendSysWithChatNotice(String content,String sceneId) {
		GCSystemMessage msg = new GCSystemMessage(content,
				GameColor.SYSMSG_IMPORT.getRgb(), SysMsgShowTypes.important);
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	

	/**
	 * 向全服发送系统提示
	 * 
	 * @param content
	 */
	private static void sendImportPromptMessage(String content,String sceneId) {
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.prompt,content,GameColor.SYSMSG_COMMON.getRgb());
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 向全服发送系统提示[聊天框]
	 * 
	 * @param content
	 */
	private static void sendCommonNotice(String content,String sceneId) {
		GCSystemMessage msg = new GCSystemMessage(content,
				GameColor.SYSMSG_COMMON.getRgb(), SysMsgShowTypes.generic);
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}

	/**
	 * 向全服发送错误提示消息
	 * 
	 * @param content
	 */
	private static void sendErrorNotice(String content,String sceneId){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.error,content,GameColor.SYSMSG_FAIL.getRgb());
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 向全服发送提示按钮
	 * 
	 * @param content
	 */
	private static void sendButtonNotice(String content,String sceneId){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.button,content,GameColor.SYSMSG_COMMON.getRgb());
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 向全服发送头顶提示消息
	 * @param content
	 */
	private static void sendHeadNotice(String content,int color,String sceneId){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.head,content,color);
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}

	/**
	 * 向全服发送特殊提示消息
	 * @param content
	 */
	private static void sendSpecialNotice(String content,String sceneId){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.special,content,GameColor.SYSMSG_COMMON.getRgb());
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 向全服发送正确提示消息
	 * @param content
	 */
	private static void sendRightNotice(String content,String sceneId){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.right,content,GameColor.SYSMSG_COMMON.getRgb());
		if(sceneId == null){
			Globals.getOnlinePlayerService().broadcastMessage(msg);
		}else{
			sendNoticeToScene(msg,sceneId);
		}
	}
	
	/**
	 * 像指定场景内发送消息
	 * @param msg
	 * @param sceneId
	 */
	private static void sendNoticeToScene(GCMessage msg,String sceneId){
		List<Scene> list = Globals.getSceneService().getScene(sceneId);
		if(list == null || list.size() == 0){
			return;
		}
		for(Scene scene : list){
			scene.getPlayerManager().sendGCMessage(msg, null);
		}
	}
	
}
