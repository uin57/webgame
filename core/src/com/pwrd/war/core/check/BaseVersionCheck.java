package com.pwrd.war.core.check;

import org.slf4j.Logger;

import com.pwrd.war.core.config.Config;
import com.pwrd.war.core.util.ServerVersion;

/**
 * 服务器版本检测,检查服务器程序的的版本号是否和系统配置的版本号一致
 * 
 * 
 */
public class BaseVersionCheck<E extends Config> implements ICheck {
	protected final E config;
	protected final Logger logger;

	public BaseVersionCheck(E config, Logger logger) {
		this.config = config;
		this.logger = logger;
	}

	@Override
	public boolean check() {
		final String _serverVersion = ServerVersion.getServerVersion();
		boolean _match = true;
		do {
			//检查程序的系统配置的版本号是否一致
			_match = _serverVersion.equals(config.getVersion());
			logVersionCheck("serverVersion", _serverVersion, "configVersion", config.getVersion(), _match);
			if (!_match) {
				break;
			}
		} while (false);
		return _match;
	}

	protected void logVersionCheck(String version1Name, String version1Value, String version2Name,
			String version2Value, boolean match) {
		if (logger != null && logger.isInfoEnabled()) {
			logger.info("[#WS.VersionCheck.logVersionCheck] [" + version1Name + ":" + version1Value + ","
					+ version2Name + ":" + version2Value + ",match:" + (match ? "Yes" : "No") + "]");
		}

	}
}
