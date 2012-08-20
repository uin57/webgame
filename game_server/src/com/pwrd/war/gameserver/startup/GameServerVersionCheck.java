/**
 * 
 */
package com.pwrd.war.gameserver.startup;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.check.BaseVersionCheck;
import com.pwrd.war.core.util.ServerVersion;
import com.pwrd.war.db.model.DbVersion;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;

/**
 * Game Server服务器版本检测
 * 
 * 
 */
public class GameServerVersionCheck extends BaseVersionCheck<GameServerConfig> {

	public GameServerVersionCheck(GameServerConfig config) {
		super(config, Loggers.gameLogger);
	}

	
	@Override
	public boolean check() {
		if(config.getIsDebug()){
			return true;
		}
		if (!super.check()) {
			return false;
		}
		final String _serverVersion = ServerVersion.getServerVersion();
		boolean _match = true;
		do {
			
			DbVersion _dbVersionObj = Globals.getDaoService().getDbVersionDao().getDbVersion();
			String _dbVersion = _dbVersionObj != null ? _dbVersionObj.getVersion() : null;
			//检查数据库中的版本号和系统配置的版本号是否一致
			_match = config.getDbVersion().equals(_dbVersion);
			logVersionCheck("dbVersion", _dbVersion, "configDbVersion", config.getDbVersion(), _match);
			if (!_match) {
				break;
			}
			
			String _resourceVersion = null;
			try {
				_resourceVersion = FileUtils.readFileToString(new File(config
						.getBaseResourceDir()
						+ File.separator + "version"));
			} catch (IOException e) {
				logger.error("#GS.WorldServerVersionCheck.check", e);
			}
			if (_resourceVersion == null) {
				_match = false;
				break;
			}
			
			_resourceVersion = _resourceVersion.trim();
			// 检查资源的版本号与配置中的资源版本号是否一致
			_match = _resourceVersion.equals(config.getResourceVersion());
			logVersionCheck("resourceVersoin", _resourceVersion, "resourceConfigVersion", config.getResourceVersion(),
					_match);
			if (!_match) {
				break;
			}
			
			// 检查资源的版本号是否与主版本号一致
			_match = ServerVersion.isMainVersionMatch(_serverVersion,_resourceVersion);
			logVersionCheck("resourceVersoin", _resourceVersion, "serverVersion", _serverVersion, _match);
			if (!_match) {
				break;
			}
			
			
		} while (false);
		return _match;
	}
}
