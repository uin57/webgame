package com.pwrd.war.gameserver.human;

import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;

/**
 * 开通功能
 * 
 * @author zhutao
 * 
 */
public class OpenFunction {

 

	/** 任务 **/
	public static final int renwu = 0;
	/** 战斗 **/
	public static final int zhandou = 1;
	/** 副本 **/
	public static final int fuben = 2;
	/** 强化 **/
	public static final int qianghua = 3;
	/** 布阵 **/
	public static final int buzhen = 4;
	/** 研究 **/
	public static final int yanjiu = 5;
	/** 打坐 **/
	public static final int dazuo = 6;
	/** 竞技场 **/
	public static final int jingjichang = 7;
	/** 摇钱树 **/
	public static final int yaoqianshu = 8;
	/** 官职 **/
	public static final int guanzhi = 9;
	/** 精英副本 **/
	public static final int jingyingfuben = 10;
	/** 家族 **/
	public static final int jiazu = 11;
	/** 扫荡 **/
	public static final int saodang = 12;
	/** 浇水 **/
	public static final int jiaoshui = 13;
	/** 购买体力 **/
	public static final int goumaitili = 14;
	/** vip **/
	public static final int vip = 15;
	/** 神秘商店 **/
	public static final int shenmishangdian = 16;
	/** 日常任务 **/
	public static final int richangrenwu = 17;
	/** 成长 **/
	public static final int chenzhang = 18;
	/** 兵法 **/
	public static final int bingfa = 19;
	/** 草船借剑**/
	public static final int caochuan = 20;
	/** 将星 **/
	public static final int jiangxing = 21;
	/** 封魂 **/
	public static final int fenghun = 22;	
	/** 切磋 **/
	public static final int qiecuo = 23;
	/** 加为好友**/
	public static final int addfriend = 24;
	/** 发起聊天 **/
	public static final int chat = 25;
	/** 查看信息**/
	public static final int viewinfo = 26;
	/** 屏蔽发言 **/
	public static final int stopchat = 27;
	/** 活动 **/
	public static final int act = 28;
	/** 训练场 **/
	public static final int xunlianchang = 29;
	/**
	 * 开启功能
	 * @author xf
	 */
	public static String open(String function, int function_id){
		if(isopen(function, function_id))return function;
		StringBuffer sb = new StringBuffer(function);
		if(function_id >= sb.length()){
			int len = function_id - sb.length() + 1;
			while(len--> 0){
				sb.append('0');
			}
		}
		sb.setCharAt(function_id, '1');//开启
		return sb.toString();
	}
	/**
	 * 是否开启某功能
	 * @author xf
	 */
	public static boolean isopen(String function, int function_id){
		if(function_id >= function.length())return false;
		return function.charAt(function_id) == '1';
	}
	/**
	 * 是否开启某功能
	 * @author xf
	 */
	public static boolean isopen(Human human, int funcid, boolean notify){
		boolean rs = isopen(human.getPropertyManager().getOpenFunc(), funcid);
		if(!rs && notify){
			human.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.FUNC_INVALID);
		}
		return rs;
	}
	
//	/**
//	 * 开通聊天功能
//	 */
//	public static int openChat(int openFuntion){
//		return openFuntion|chat;
//	}
//	
//	/**
//	 * 是否能聊天
//	 * @return
//	 */
//	public static boolean canChat(int openFuntion){
//		return (openFuntion&chat)>0;
//	}
//	
//	/**
//	 * 开通查看资料的功能
//	 */
//	public static int openInformation(int openFuntion){
//		return openFuntion|information;
//	}
//	
//	/**
//	 * 是否能查看资料
//	 * @return
//	 */
//	public static boolean canInformation(int openFuntion){
//		return (openFuntion&information)>0;
//	}
//	
//	/**
//	 * 开通切磋功能
//	 */
//	public static int openQieCuo(int openFuntion){
//		return openFuntion|qieCuo;
//	}
//	
//	/**
//	 * 是否可以切磋
//	 * @return
//	 */
//	public static boolean canQieCuo(int openFuntion){
//		return (openFuntion&qieCuo)>0;
//	}
//	
//	/**
//	 * 开通加好友功能
//	 */
//	public static int openFriend(int openFuntion){
//		return openFuntion|friend;
//	}
//	
//	/**
//	 * 检查是否可以加好友
//	 * @return
//	 */
//	public static boolean canFriend(int openFuntion){
//		return (openFuntion&friend)>0;
//	}

	

}
