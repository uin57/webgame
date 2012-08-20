package com.pwrd.war.gameserver.player.async;

import java.util.List;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.prize.PlatformPrizeHolder;

/**
 * 查询平台奖励列表
 * 
 * 
 */
public class PlayerQueryPlatformPrizes implements IIoOperation {

	private final static Logger logger = Loggers.prizeLogger;

	private Player player;
	/** 调用平台查询返回的结果 */
//	private volatile QueryGoodsResponse response;
	/** 奖励列表 */
	private List<PlatformPrizeHolder> prizeHolders;

	public PlayerQueryPlatformPrizes(Player player) {
		this.player = player;
	}

	@Override
	public int doIo() {
		// 调用接口
//		try {
//			response = Globals.getSynLocalService().queryGoods(
//					player.getPassportId(), player.getRoleUUID() + "",
//					player.getClientIp());
//		} catch (Exception ex) {
//			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//					"玩家在查询平台奖励时出错"), ex);
//			player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//		}

//		if (!response.isSuccess()) {
//			// 根据错误码进行信息提示
//			switch (response.getErrorCode()) {
//			case 1:
//			case 2:
//			case 3:
//			case 999:
//				player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				break;
//			case 4:
//				player.sendErrorMessage(LangConstants.PRIZE_USER_NOT_EXIST);
//				break;
//			default:
//				// 此处可能是平台添加的奖励出了问题
//				player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				break;
//			}
//		}
//
//		if (response.isSuccess()) {
//			prizeHolders = new ArrayList<PlatformPrizeHolder>();
//
//			// 检查奖励是否存在
//			List<GoodsInfo> goods = response.getGoodsInfoList();
//			for (GoodsInfo gi : goods) {
//				try {
//					PlatformPrizeHolder holder = Globals.getPrizeService()
//							.fetchPlatformPrizeByPrizeId(gi.getId(),
//									gi.getGoodsId());
//					if (holder == null) {
//						logger.error("平台奖励不存在，prizeId : " + gi.getGoodsId());
//						continue;
//					}
//					prizeHolders.add(holder);
//				} catch (IllegalArgumentException ex) {
//					logger.error("奖励配置错误，prizeId : " + gi.getGoodsId(), ex);
//					player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				} catch (Exception ex) {
//					logger.error("查询平台奖励时，从本地数据库中查询出错，prizeId : "
//							+ gi.getGoodsId(), ex);
//					player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				}
//			}
//		}
		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		// 查询成功
//		if (response.isSuccess()) {
//			if (!prizeHolders.isEmpty()) {
//				GCPrizePlatformList msg = new GCPrizePlatformList(prizeHolders
//						.toArray(new PlatformPrizeHolder[0]));
//				player.sendMessage(msg);
//			} else {
//				//player.sendErrorMessage(LangConstants.PRIZE_USER_NOT_EXIST);
//			}
//		}

		return IIoOperation.STAGE_STOP_DONE;
	}

}