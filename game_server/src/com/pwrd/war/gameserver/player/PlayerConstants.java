package com.pwrd.war.gameserver.player;


/**
 * 角色常量定义
 * 
  *
 * @author Fancy
 * 
 */
public class PlayerConstants {

	/** 角色姓名最小允许中文字符数 */
	public static final int MIN_NAME_LENGTH_ZHCN = 2;
	/** 角色姓名最大允许中文字符数 */
	public static final int MAX_NAME_LENGTH_ZHCN = 7;
	/** 角色姓名最小允许英文字符数 */
	public static final int MIN_NAME_LENGTH_ENG = 4;
	/** 角色姓名最大允许英文字符数 */
	public static final int MAX_NAME_LENGTH_ENG = 14;
	/** 角色签名最小允许中文字符数 */
	public static final int MIN_SIGNATURE_LENGTH_ZHCN = 0;
	/** 角色签名最大允许中文字符数 */
	public static final int MAX_SIGNATURE_LENGTH_ZHCN = 50;
	/** 角色签名最小允许英文字符数 */
	public static final int MIN_SIGNATURE_LENGTH_ENG = 0;
	/** 角色签名最大允许英文字符数 */
	public static final int MAX_SIGNATURE_LENGTH_ENG = 50;

	/* 角色信息保存的Mask定义 ： 通过Mask保存相应的数据 */
	
	/** 什么也不保存 */
	public static final int CHARACTER_MASK_NO= 0x0;
	/** 角色基本信息 */
	public static final int CHARACTER_INFO_MASK_BASE = 0x00000001;
	/** [大字段结构需要]已经完成的任务信息(Blob中已完成任务的分开处理) */
	public static final int CHARACTER_INFO_MASK_FINISH_QUEST = 0x00000002;
	/** [大字段结构需要]正在进行的任务信息 */
	public static final int CHARACTER_INFO_MASK_DOING_QUEST = 0x00000004;
	/** [大字段结构需要]物品信息 */
	public static final int CHARACTER_INFO_MASK_ITEM = 0x00000008;
	/** [大字段结构需要]宠物信息 */
	public static final int CHARACTER_INFO_MASK_PET = 0x00000010;
	/** 战斗快照信息 */
	public static final int CHARACTER_INFO_MASK_BATTLE_SNAP = 0x00000020;
	/** 全部信息 */
	public static final int CHARACTER_INFO_MASK_ALL = 0x0000FFFF;
	
	/* 角色信息删除的Mask定义 ： 通过Mask删除相应的数据 */
	/** 什么也不删除 */
	public static final int CHARACTER_DEL_MASK_NO= 0x0;
	/** 角色无效的任务信息 */
	public static final int CHARACTER_DEL_INVALID_TASK_MASK = 0x00000001;
	
}
