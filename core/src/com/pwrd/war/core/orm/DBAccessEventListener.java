package com.pwrd.war.core.orm;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.config.ServerConfig;
import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.core.util.ReportServerStatus;
import com.pwrd.war.core.util.ReportServerStatus.ServerStatusType;

/**
 * 数据库访问事件监听器,监听的事件处理如下:
 * <ul>
 * <li>{@link DBAccessEvent.Type#ERROR} 向外部的监控系统汇报错误状态</li>
 * </ul>
 * 
  *
 * 
 */
public class DBAccessEventListener implements IEventListener<DBAccessEvent> {
	private final static Logger logger = LoggerFactory.getLogger("war.db.listener");
	private final ServerConfig serverConfig;

	public DBAccessEventListener(ServerConfig serverConfig) {
		super();
		this.serverConfig = serverConfig;
	}

	@Override
	public void fireEvent(DBAccessEvent event) {
		if (event == null) {
			return;
		}
		if (event.getType() == DBAccessEvent.Type.ERROR) {
			// 出错了,向外部的监控系统汇报状态
			try {
				ReportServerStatus.report(serverConfig.getReportDomain(),
						serverConfig.getServerDomain(), serverConfig
								.getServerId(), ServerStatusType.STATUS_ERROR
								.getStatusCode(), event.getInfo());
			} catch (IOException ioe) {
				if (logger.isErrorEnabled()) {
					logger.error("Report status error", ioe);
				}
			}
		}
	}
}
