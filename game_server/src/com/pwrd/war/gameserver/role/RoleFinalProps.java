package com.pwrd.war.gameserver.role;

import com.pwrd.war.core.annotation.Type;
import com.pwrd.war.gameserver.role.properties.PropertyObject;
import com.pwrd.war.gameserver.role.properties.PropertyType;

/**
 * 角色在游戏过程中对客户端不可见的属性
 * 
 */
public class RoleFinalProps extends PropertyObject {

	
	public static final int _BEGIN = 0;
	
	public static int _END = _BEGIN;

	/** 账号ID */
	@Type(Long.class)
	public static final int PASSPORT_ID = ++_END;
	/** 最近一次登入，单位毫秒 */
	@Type(Long.class)
	public static final int LAST_LOGIN = ++_END;
	/** 最近一次登出，单位毫秒 */
	@Type(Long.class)
	public static final int LAST_LOGOUT = ++_END;
	/** 最近一次登录ip */
	@Type(String.class)
	public static final int LAST_IP = ++_END;
	/** 角色创建时间 */
	@Type(Long.class)
	public static final int CREATE_TIME = ++_END;
	/** 角色总在线时长 */
	@Type(Integer.class)
	public static final int TOTAL_MINUTE = ++_END;
	/** 当日充值数额 */
	@Type(Integer.class)
	public static final int TODAY_CHARGE = ++_END;
	/** 总充值数额 */
	@Type(Integer.class)
	public static final int TOTAL_CHARGE = ++_END;
	/** 最后充值时间 */
	@Type(Long.class)
	public static final int LAST_CHARGE_TIME = ++_END;	
	/** 最后成为某级别VIP级别时间 */
	@Type(Long.class)
	public static final int LAST_VIP_TIME = ++_END;	

	/** 上上一次登入，单位毫秒 */
	@Type(Long.class)
	public static final int LAST_LAST_LOGIN = ++_END;
	/** 上上一次登录ip */
	@Type(String.class)
	public static final int LAST_LAST_IP = ++_END;
	
	/** 个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
	
	public static final PropertyType TYPE = PropertyType.ROLE_PROPS_FINAL;

	public RoleFinalProps() {
		super(_SIZE, TYPE);
	}

}
