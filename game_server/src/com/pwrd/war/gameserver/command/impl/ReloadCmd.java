package com.pwrd.war.gameserver.command.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.pwrd.war.common.Reloadable;
import com.pwrd.war.common.Reloadable.IParameter;
import com.pwrd.war.common.Reloadable.IResult;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;

public class ReloadCmd implements IAdminCommand<ISession>{
	
	/** 重新加载管理器,key:加载器名称,value:加载器 */
	private final Map<String, Reloadable> reloadMap = Maps.newHashMap();
	
	public ReloadCmd() {
		reloadMap.put("items.xls", new ItemReloadable());
	}
	
	@Override
	public void execute(ISession session, String[] commands) {
//		if (commands.length == 2) {
//			int index = Integer.parseInt(commands[1]);
//			Globals.getTemplateService().reload(commands[0], index);
//			if (reloadMap.containsKey(commands[0])) {
//				Reloadable reload = reloadMap.get(commands[0]);
//				TemplateParameter para = new TemplateParameter(commands[0], index);
//				IResult result = reload.reload(para);
//				reload.afterReload(result);
//			} else {
//				Loggers.msgLogger.error("参数不正确");
//			}
//		} else {
//			Loggers.msgLogger.error("参数个数不正确");
//		}
//		
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_RELOAD;
	}
	
	
	/**
	 * 模板重载
	 * 
	 * 
	 * @author
	 */
	public static class TemplateParameter implements IParameter {
		public final String fileName;
		public final int index;

		public TemplateParameter(String fileName, int index) {
			super();
			this.fileName = fileName;
			this.index = index;
		}
	}
	
	
	/**
	 * 
	 * Item模板重载
	 * 
	 * @author
	 */
	private class ItemReloadable implements Reloadable {

		@Override
		public IResult reload(IParameter iParameter) {
			Globals.getItemService().reload();
			return null;
		}

		@Override
		public boolean afterReload(IResult iResult) {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
