package com.pwrd.war.gameserver.common.function;

import java.util.Comparator;

import com.pwrd.war.gameserver.human.Human;

/**
 * 游戏功能接口
 * 
 * @author haijiang.jin
 *
 * @param <FUNC_TYPE> 功能类型
 * @param <FUNC_TARGET> 目标
 */
public interface IGameFunc<FUNC_TYPE extends IGameFuncType, FUNC_TARGET extends Object> {
	/**
	 * 获取功能所在其容器中的索引
	 * 
	 * @return 
	 */
	int getIndex();

	/**
	 * 设置此功能在其容器中的索引
	 * 
	 * @param value
	 */
	void setIndex(int value);

	/**
	 * 获取功能标题
	 * 
	 * @return 
	 */
	String getTitle();

	/**
	 * 设置功能标题
	 * <p><strong>配置在区域配置表上的功能需要实现</strong>
	 * 
	 * @param value 标题
	 */
	void setTitle(String value);

	/**
	 * 获取功能描述
	 * 
	 * @return 
	 */
	String getDesc();

	/**
	 * 设置功能描述
	 * 
	 * @param value 描述
	 */
	void setDesc(String value);

	/**
	 * 设置功能参数
	 * <p><strong>配置在区域配置表上的功能需要实现</strong>
	 * 
	 * @param values 参数，全部用字符串格式，由具体的功能进行类型解析。
	 */
	void setArgs(String[] values);

	/**
	 * 获取功能类型
	 * 
	 * @return 
	 */
	FUNC_TYPE getType();

	/**
	 * 获取功能类型 Id, <b>与功能类型的 index 一致</b>
	 * 
	 * @return
	 */
	int getTypeId();

	/**
	 * 此是否可见
	 * 
	 * @param human
	 * @param target 
	 * @return
	 */
	boolean canSee(Human human, FUNC_TARGET target);

	/**
	 * 点击了此功能
	 * 
	 * @param human
	 * @param target 
	 * 
	 */
	void onClick(Human human, FUNC_TARGET target);

	/**
	 * 获取功能显示优先数，优先数越显示得越靠上
	 * 
	 * @return
	 */
	int getPriority();

	/**
	 * 判断此功能是否可以点击, 如果可以点击返回空字符串, 如果不能点击则返回原因
	 * 
	 * @param humna
	 * @param target 
	 * @return
	 */
	String isAvailable(Human human, FUNC_TARGET target);

	/**
	 * 参数合法性检验
	 * 
	 */
	void checkArgs();

	/**
	 * 获取与玩家相关的信息
	 * 
	 * @param human
	 * @param target
	 * @return 
	 * 
	 */
	String getAboutInfo(Human human, FUNC_TARGET target);

	/** 比较器 */
	public static final Comparator<IGameFunc<?, ?>> comparator = new Comparator<IGameFunc<?, ?>>() {
		@Override
		public int compare(IGameFunc<?, ?> funA, IGameFunc<?, ?> funB) {
			if (funA.getPriority() < funB.getPriority()) {
				return -1;
			} else if (funA.getPriority() > funB.getPriority()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
}
