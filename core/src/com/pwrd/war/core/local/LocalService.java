//package com.pwrd.war.core.local;
//
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//
//import com.imop.platform.local.HandlerFactory;
//import com.imop.platform.local.callback.ICallback;
//import com.imop.platform.local.config.LocalConfig;
//import com.imop.platform.local.exception.LocalConfigException;
//import com.imop.platform.local.handler.IHandler;
//import com.imop.platform.local.response.AddWallowInfoResponse;
//import com.imop.platform.local.response.GetGoodsResponse;
//import com.imop.platform.local.response.IResponse;
//import com.imop.platform.local.response.LoginResponse;
//import com.imop.platform.local.response.LogoutResponse;
//import com.imop.platform.local.response.QueryBalanceResponse;
//import com.imop.platform.local.response.QueryGoodsResponse;
//import com.imop.platform.local.response.QueryOnlineTimeResponse;
//import com.imop.platform.local.response.TransferResponse;
//import com.imop.platform.local.type.LoginType;
//import com.opi.gibp.probe.category.ProcessResult;
//import com.pwrd.war.common.constants.Loggers;
//import com.pwrd.war.common.constants.SharedConstants;
//import com.pwrd.war.core.config.ServerConfig;
//import com.pwrd.war.core.util.PM;
//import com.pwrd.war.probe.PIProbeCollector;
//import com.pwrd.war.probe.PIProbeConstants.ProbeName;
//
///**
// * 平台local服务
// * 
// * 
// * 
// */
//public class LocalService {
//	
//	private static final Logger logger = Loggers.localLogger;
//	/** 平台接口配置 */
//	private LocalConfig localConfig;
//
//	/** 平台接口的处理器 */
//	private IHandler localHandler;
//
//	/** 连接超时,默认11秒,人人接口响应时间为5-10秒 */
//	private static final int DEFAULT_TIMEOUT = 11000;
//    
//    /** 同步local服务 */
//	private final SynLocalService synLocalService;
//
//    /** 异步local服务 */
//	private final AsyncLocalService asyncLocalService;
//
//	public LocalService(ServerConfig serverConfig) {
//		this.initLocalConfig(serverConfig);
//		this.initHandler(localConfig);
//		this.synLocalService = new SynLocalService(localHandler);
//		this.asyncLocalService = new AsyncLocalService(localHandler);
//	}
//
//	/**
//	 * 获得同步请求器
//	 * 
//	 * @return
//	 */
//	public SynLocalService getSynLocalService() {
//		return this.synLocalService;
//	}
//
//	/**
//	 * 获得异步汇报器
//	 * 
//	 * @return
//	 */
//	public AsyncLocalService getAsyncLocalService() {
//		return this.asyncLocalService;
//	}
//
//	/**
//	 * 初始化平台配置
//	 * 
//	 * @param serverConfig
//	 */
//	private void initLocalConfig(ServerConfig serverConfig) {
//		if (!serverConfig.isTurnOnLocalInterface()) {
//			return ;
//		}
//		try {
//			localConfig = new LocalConfig();
//			//必须配置
//			localConfig.setGameKey(SharedConstants.LOCAL_MD5_KEY);
//			localConfig.setAreaId(Integer.parseInt(serverConfig.getRegionId()));
//			localConfig.setServerId(Integer.parseInt(serverConfig.getLocalHostId()));
//			localConfig.setDomain(serverConfig.getServerDomain());
//			localConfig.setRequestDomain(serverConfig.getRequestDomain());
//			localConfig.setReportDomain(serverConfig.getReportDomain());
//
//			//可选配置
//			localConfig.setTimeout(DEFAULT_TIMEOUT);
//			localConfig.setThreadNum(2);
//			
//			this.checkConfig();
//
//			logger.info("Local config init success");
//
//		} catch (Exception e) {
//			throw new RuntimeException("The local config init error.", e);
//		}
//	}
//
//	/**
//	 * 初始化接口处理器
//	 * 
//	 * @param localConfig
//	 */
//	private void initHandler(LocalConfig localConfig) {
//		if (localConfig == null) {
//			return ;
//		}
//		try {
//			localHandler = HandlerFactory.createHandler(localConfig);
//		} catch (Exception e) {
//			throw new RuntimeException("The local handler init error.", e);
//		}
//	}
//
//	/**
//	 * 同步平台服务
//	 * 
//	 * 
//	 */
//	public static final class SynLocalService {
//		/** 平台接口的处理器 */
//		private IHandler synLocalHandler;
//
//		public SynLocalService(IHandler handler) {
//			this.synLocalHandler = handler;
//		}
//
//		/**
//		 * 用户登录验证接口（同步调用）
//		 * 
//		 * @param userName
//		 *            当使用用户名密码认证时 username=用户名；当使用COOKIE认证 时
//		 *            username=cookie值；当使用TOKEN认证时 username=token值。
//		 * @param password
//		 *            表示用户密码 。
//		 * @param ip
//		 *            表示用户登录游戏时的客户端IP地址。
//		 * @param loginType
//		 *            参见枚举类LoginType
//		 * @return 登录结果<br>
//		 * <br>
//		 *         登录成功时：<br>
//		 *         &nbsp;&nbsp;1：userId表示用户ID<br>
//		 *         &nbsp;&nbsp;2：userName表示用户名<br>
//		 *         &nbsp;&nbsp;3：antiIndulge表示该用户是否为防沉迷对象（-1不是，1是）；<br>
//		 *         &nbsp;&nbsp;4：infoIntegrity表示资料是否完整<br>
//		 * <br>
//		 *         登录失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式不正确<br>
//		 *         &nbsp;&nbsp;4：用户名密码验证未通过<br>
//		 *         &nbsp;&nbsp;5：用户已经被锁定<br>
//		 *         &nbsp;&nbsp;6：密保未通过<br>
//		 *         &nbsp;&nbsp;7：cookie验证未通过<br>
//		 *         &nbsp;&nbsp;8：token验证未通过<br>
//		 *         &nbsp;&nbsp;9：大区验证未通过<br>
//		 *         &nbsp;&nbsp;999：系统异常，登录操作不成功<br>
//		 */
//		public LoginResponse validateUser(String name, String password, String ip, LoginType loginType) {
//			final long _begin = System.nanoTime();
//			//发起登录行为
//			LoginResponse _response = this.synLocalHandler.login(name, password, ip, loginType);
//			LocalService.collectRequestUseTime(_response, _begin);
//
//			return _response;
//		}
//		
//		
//		/**
//		 * 添加防沉迷认证信息
//		 * 
//		 * @param passportId
//		 * @param trueName
//		 * @param idCard
//		 * @param ip
//		 * @return
//		 */
//		public AddWallowInfoResponse addWallowInfo(String passportId,String trueName,String idCard,String ip)
//		{
//			final long _begin = System.nanoTime();
//			//填写防沉迷的认证信息
//			AddWallowInfoResponse _response = this.synLocalHandler.addWallowInfo(passportId, trueName, idCard, ip);
//			LocalService.collectRequestUseTime(_response, _begin);
//			return _response;
//		}
//		
//		/**
//		 * 用户登出接口
//		 * 
//		 * 
//		 * @param userId
//		 * @param ip
//		 * @return
//		 */
//		public LogoutResponse logout(String userId,String ip)
//		{
//			LogoutResponse _response = this.synLocalHandler.logout(userId, ip);
//			return _response;
//		}
//
//		/**
//		 * 奖品领取扩展接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param roleId
//		 *            用户在游戏中的角色编号(role id)。
//		 * @param ip
//		 *            用户领取奖品时的客户端IP地址。
//		 * @param id
//		 *            奖励唯一ID。
//		 * @param largessId
//		 *            礼品编号。
//		 * @return 领奖结果<br>
//		 * <br>
//		 *         领奖成功时：<br>
//		 *         &nbsp;&nbsp;1：prizeId表示礼品编号<br>
//		 * <br>
//		 *         领奖失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式错误<br>
//		 *         &nbsp;&nbsp;4：该用户没有礼品<br>
//		 *         &nbsp;&nbsp;999：系统异常，查询操作不成功<br>
//		 */
//		public GetGoodsResponse getGoods(String userId, String roleId, String ip, int id, int largessId) {
//			final long _begin = System.nanoTime();
//			GetGoodsResponse _response = this.synLocalHandler.getGoods(userId, roleId, ip, id, largessId);
//			LocalService.collectRequestUseTime(_response, _begin);
//			
//			return _response;
//		}
//
//		/**
//		 * 奖品查询扩展接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param roleId
//		 *            用户在游戏中的角色编号(role id)。
//		 * @param ip
//		 *            用户查询礼品时的客户端IP地址。
//		 * @return 奖品查询结果<br>
//		 * <br>
//		 *         查询成功时：<br>
//		 *         &nbsp;&nbsp;1：goodsInfoList表示奖品集合<br>
//		 * <br>
//		 *         查询失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式错误<br>
//		 *         &nbsp;&nbsp;4：该用户没有礼品。<br>
//		 *         &nbsp;&nbsp;999：系统异常，查询操作不成功<br>
//		 */
//		public QueryGoodsResponse queryGoods(String userId, String roleId, String ip) {
//			final long _begin = System.nanoTime();
//			QueryGoodsResponse _response = this.synLocalHandler.queryGoods(userId, roleId, ip);
//			LocalService.collectRequestUseTime(_response, _begin);
//			
//			return _response;
//		}
//
//		/**
//		 * 游戏世界兑换游戏币接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param roleId
//		 *            用户在游戏中的角色编号(role id)。
//		 * @param money
//		 *            充值金额。
//		 * @param ip
//		 *            用户兑换游戏币时的客户端IP地址。
//		 * @return 兑换结果<br>
//		 * <br>
//		 *         兑换成功时：<br>
//		 *         &nbsp;&nbsp;1：userId表示用户ID<br>
//		 *         &nbsp;&nbsp;2：balance表示扣除后的用户账户余额<br>
//		 * <br>
//		 *         兑换失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式不正确<br>
//		 *         &nbsp;&nbsp;4：余额不足<br>
//		 *         &nbsp;&nbsp;999：系统异常，兑换操作不成功<br>
//		 */
//		public TransferResponse transfer(String userId, String roleId, int money, String ip) {
//			final long _begin = System.nanoTime();
//			TransferResponse _response = this.synLocalHandler.transfer(userId, roleId, money, ip);
//			LocalService.collectRequestUseTime(_response, _begin);
//			
//			return _response;
//		}
//
//		/**
//		 * 游戏世界余额查询接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param ip
//		 *            表示用户查询余额时的客户端IP地址。
//		 * @return 查询结果<br>
//		 * <br>
//		 *         查询成功时：<br>
//		 *         &nbsp;&nbsp;1：balance表示用户账户余额<br>
//		 * <br>
//		 *         查询失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式不正确<br>
//		 *         &nbsp;&nbsp;999：系统异常，查询操作不成功<br>
//		 */
//		public QueryBalanceResponse queryBalance(String userId, String ip) {
//			final long _begin = System.nanoTime();
//			QueryBalanceResponse _response = this.synLocalHandler.queryBalance(userId, ip);
//			LocalService.collectRequestUseTime(_response, _begin);
//			
//			return _response;
//		}
//
//		/**
//		 * 获取用户登录千橡游戏累计时间接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户id。
//		 * @return 获取累计时间结果<br>
//		 * <br>
//		 *         获取成功时：<br>
//		 *         &nbsp;&nbsp;1：用户的在线累计秒数<br>
//		 * <br>
//		 *         获取失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式错误<br>
//		 *         &nbsp;&nbsp;4：非法用户<br>
//		 *         &nbsp;&nbsp;999：系统异常，操作不成功<br>
//		 */
//		public long queryOnlineTime(String userId) {
//			final long _begin = System.nanoTime();
//			QueryOnlineTimeResponse _queryResponse = this.synLocalHandler.queryOnlineTime(userId);
//			LocalService.collectRequestUseTime(_queryResponse, _begin);
//			
//			if (_queryResponse == null) {
//				return -1;
//			}
//
//			if (_queryResponse.isSuccess()) {
//				//返回的时间单位:s
//				return _queryResponse.getOnlineTime();
//			} else {
//				return -1;
//			}
//		}
//		
//		/**
//		 * 用户退出游戏接口（同步调用）
//		 * 
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param ip
//		 *            表示用户退出游戏时的客户端IP地址。
//		 * @return 退出结果<br>
//		 * <br>
//		 *         退出成功时：<br>
//		 *         &nbsp;&nbsp;1：userId表示用户ID<br>
//		 * <br>
//		 *         退出失败时：errorCode表示错误码<br>
//		 *         &nbsp;&nbsp;1：签名验证失败<br>
//		 *         &nbsp;&nbsp;2：时间戳过期<br>
//		 *         &nbsp;&nbsp;3：有参数为空或格式不正确<br>
//		 *         &nbsp;&nbsp;999：系统异常，退出操作不成功<br>
//		 */
//		public LogoutResponse reqPlayerLogout(String userId, String ip) {
//			final long _begin = System.nanoTime();
//			LogoutResponse _response = this.synLocalHandler.logout(userId, ip);
//			LocalService.collectRequestUseTime(_response, _begin);
//			
//			return _response;
//		}
//
//		
//		/**
//		 * 游戏server运行状态汇报接口（异步调用）,自行实现同步机制
//		 * 
//		 * @param servers
//		 *            服务器id
//		 * @param status
//		 *            当前运行状态，大写，例：RUN
//		 * @param extra
//		 *            其他备选信息。
//		 */
//		public void reportServerStatus(String servers, String status, String extra,ICallback callback) {
//			final long _beginTime = System.nanoTime();
//			try {
//				this.synLocalHandler.statusReport(servers, status, extra, callback);
//			} catch (Exception e) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("[#GS.AsyncLocalService.reportServerStatus] ["
//							+ String.format("servers:%s, status:%s, extra:%s", servers, status, extra) + "]", e);
//				}
//				LocalService.collectRequestUseTime(null, _beginTime);
//			}
//		}
//		
//	}
//
//	/**
//	 * 平台汇报回调接口
//	 *
//	 *
//	 */
//	public static class LocalReportCallBacker implements ICallback {
//		/** 每次汇报的开始时间 */
//		private long beginTime;
//		
//		public LocalReportCallBacker(long beginTime) {
//			this.beginTime = beginTime;
//		}
//		
//		@Override
//		public void onFail(IResponse response) {
//			LocalService.collectRequestUseTime(response, beginTime);
//		}
//
//		@Override
//		public void onSuccess(IResponse response) {
//			LocalService.collectRequestUseTime(response, beginTime);
//		}
//	}
//	
//	/**
//	 * 异步平台汇报器
//	 * 
//	 * 
//	 */
//	public static final class AsyncLocalService {
//		/** 平台接口的处理器 */
//		private final IHandler asyncLocalHandler;
//		
//		public AsyncLocalService(IHandler handler) {
//			this.asyncLocalHandler = handler;
//		}
//
//		/**
//		 * 游戏server运行状态汇报接口（异步调用）
//		 * 
//		 * @param servers
//		 *            服务器id
//		 * @param status
//		 *            当前运行状态，大写，例：RUN
//		 * @param extra
//		 *            其他备选信息。
//		 */
//		public void reportServerStatus(String servers, String status, String extra) {
//			final long _beginTime = System.nanoTime();
//			try {
//				this.asyncLocalHandler.statusReport(servers, status, extra, new LocalReportCallBacker(_beginTime));
//			} catch (Exception e) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("[#GS.AsyncLocalService.reportServerStatus] ["
//							+ String.format("servers:%s, status:%s, extra:%s", servers, status, extra) + "]", e);
//				}
//				LocalService.collectRequestUseTime(null, _beginTime);
//			}
//		}
//
//		/**
//		 * 记录游戏贵重物品消耗接口（异步调用）
//		 * 
//		 * @param orderId
//		 *            表示消耗订单号。
//		 * @param userId
//		 *            用户ID（userid）。
//		 * @param roleId
//		 *            用户在游戏中的角色编号(role id)。
//		 * @param money
//		 *            该笔消耗所花费的金额。
//		 * @param goodSid
//		 *            购买的游戏物品编号。
//		 * @param ip
//		 *            用户消耗贵重物品时的客户端IP地址。
//		 */
//		public void reportTransRecord(String orderId, String userId, String roleId, int money, String goodSid, String ip) {
//			final long _beginTime = System.nanoTime();
//			try {
//				this.asyncLocalHandler.transRecordReport(orderId, userId, roleId, money, goodSid, ip, new LocalReportCallBacker(_beginTime));
//			} catch (Exception e) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("[#GS.AsyncLocalService.reportTransRecord] ["
//							+ String.format("orderId:%s, userId:%s, roleId:%s, money:%s, goodSid:%s", orderId, userId,
//									roleId, money, goodSid) + "]", e);
//				}
//				LocalService.collectRequestUseTime(null, _beginTime);
//			}
//		}
//
//		/**
//		 * 游戏同时在线人数接口（异步调用）
//		 * 
//		 * @param onlineNum
//		 *            当前的游戏服务器人数。
//		 */
//		public void reportOnlinePlayers(int onlineNum) {
//			final long _beginTime = System.nanoTime();
//			try {
//				this.asyncLocalHandler.onlineReport(onlineNum, new LocalReportCallBacker(_beginTime));
//			} catch (Exception e) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("[#GS.AsyncLocalService.reportOnlinePlayers] ["
//							+ String.format("onlineNum:%s", onlineNum) + "]", e);
//				}
//				LocalService.collectRequestUseTime(null, _beginTime);
//			}
//		}
//		
//		/**
//		 * 举报玩家汇报接口（异步调用）
//		 * 
//		 */
//		public void reportPlayer(String ip, String reporterPassName, String reporterPassId, String reporterCharId,
//				String reporterCharName, int reporterLevel, long reportTime, String playerPassName, long playerPassId,
//				long playerCharId, String playerCharName, int playerLevel, String channel, String sayTime,
//				String chatId, String content,ICallback callBack) {
//			final long _beginTime = System.nanoTime();
//			try {
//				this.asyncLocalHandler.reportPlayerReport(ip, reporterPassName , reporterPassId ,
//						reporterCharId , reporterCharName , reporterLevel , reportTime ,
//						playerPassName, playerPassId , playerCharId , playerCharName, playerLevel, channel, sayTime, chatId, content,
//						callBack);
//			} catch (Exception e) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("[#GS.AsyncLocalService.reportPlayer] ["
//							+ PM.kv("ip", ip).kv("aPName", reporterPassName).kv("aPid", reporterPassId).kv("aCid",
//									reporterCharId).kv("aCName", reporterCharId).kv("aLvl", reporterLevel).kv("bPName",
//									playerPassName).kv("bPid", playerPassId).kv("bCid", playerCharId).kv("bCName",
//									playerCharId).kv("bLvl", playerLevel).kv("channel", channel).kv("sayTime", sayTime)
//									.kv("chatId", chatId).kv("content", content).tos() + "]", e);
//				}
//				LocalService.collectRequestUseTime(null, _beginTime);
//			}
//		}
//
//	}
//
//	/**
//	 * 检查必须配置是否完整
//	 * 
//	 * @throws LocalConfigException
//	 */
//	private void checkConfig() throws LocalConfigException {
//		if (0 == localConfig.getAreaId()) {
//			throw new LocalConfigException("areaId is zero");
//		}
//
//		if (0 == localConfig.getServerId()) {
//			throw new LocalConfigException("serverId is zero");
//		}
//
//		if (null == localConfig.getGameKey()) {
//			throw new LocalConfigException("gameKey is null");
//		}
//
//		if (null == localConfig.getDomain()) {
//			throw new LocalConfigException("domain is null");
//		}
//
//		if (null == localConfig.getRequestDomain()) {
//			throw new LocalConfigException("requestDomain is null");
//		}
//
//		if (null == localConfig.getReportDomain()) {
//			throw new LocalConfigException("reportDomain is null");
//		}
//
//		if (10 > localConfig.getTimeout()) {
//			throw new LocalConfigException("timeout is too short");
//		}
//
//		if (null == localConfig.getReportService()) {
//			throw new LocalConfigException("reportService is null");
//		}
//
//	}
//	
//	/**
//	 * 性能采集汇报平台请求花费时间
//	 * 
//	 * @param response
//	 * 			请求的响应结果
//	 * @param beginTime
//	 * 			开始请求的时间（ns）
//	 */
//	private static void collectRequestUseTime(IResponse response, long beginTime) {
//		// 没有响应或者使用时间为0，应该是请求失败
//		if(response == null || response.getUseTime() == 0) {
//			PIProbeCollector.collect(ProbeName.RPC, ProcessResult.FAIL, TimeUnit.NANOSECONDS.toMillis(System.nanoTime()
//					- beginTime));
//			return;
//		}
//		
//		// 调用接口处理成功采集
//		PIProbeCollector.collect(ProbeName.RPC, ProcessResult.SUCCESS, response.getUseTime());
//	}
//}
