package com.pwrd.war.gameserver.player;

/**
 * GameServer上的玩家状态定义，玩家状态统一由主线程维护
 * 
 */
/**
 * @author xf
 */
/**
 * @author xf
 */
public enum PlayerState {
	/** 已创建，默认状态 */
	init,
	/** 已连接 */
	connected,
	/** 已认证 */
	auth,
	/** 正在异步加载角色列表 */
	loadingrolelist,
	/** 正在等待玩家选择角色 */
	waitingselectrole,
	/** 正在创建角色 */
	creatingrole,
	/** 收到CGPlayerEnter后进入此状态,异步加载玩家角色信息中， */
	loading,
	/** 角色信息加载完毕，等待玩家发送CGEnterScene消息 */
	incoming,
	/** 收到CGEnterScene,进入场景中 */
	entering,
	/** 玩家已进入场景，正式进入游戏状态 */
	gaming,	
//	/** 场景切换中 **/
//	switching,
	/** 离开场景中，等待场景线程处理 */
	leaving,
	/** 正在退出保存角色数据 */
	logouting,
	/** 已退出*/
	logouted;

	static {// 设置每个状态合法的前置状态，如果不设置，则无法进入此状态
		connected.setPrevStates(init);
		auth.setPrevStates(connected);
		loadingrolelist.setPrevStates(auth,creatingrole);
		waitingselectrole.setPrevStates(loadingrolelist);
		creatingrole.setPrevStates(loadingrolelist,waitingselectrole);		
		loading.setPrevStates(waitingselectrole,creatingrole);
		incoming.setPrevStates(loading,leaving);
		entering.setPrevStates(incoming);
		gaming.setPrevStates(entering/*,switching*/);// 切换场景失败会进入此状态
//		switching.setPrevStates(gaming);//切换场景和进入游戏可以互相切换
		leaving.setPrevStates(gaming);
		logouting.setPrevStates(init,connected,auth,loadingrolelist,waitingselectrole,creatingrole,loading,incoming,entering,gaming,leaving/*,switching*/);
		logouted.setPrevStates(logouting);
	}

	private PlayerState[] prevStates;

	private void setPrevStates(PlayerState... prevStates) {
		this.prevStates = prevStates;
	}

	/**
	 * @param targetState
	 * @return
	 */
	public boolean canEnter(PlayerState targetState) {
		if (targetState.prevStates == null) {
			return false;
		}
		for (int i = 0; i < targetState.prevStates.length; i++) {
			if (targetState.prevStates[i] == this) {
				return true;
			}
		}
		return false;
	}
}
