package com.pwrd.war.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 登陆礼包领取状况
 */
@Entity
@Table(name = "t_login_gifts")
public class LoginGiftsEntity implements BaseEntity<String>{
 
	private static final long serialVersionUID = 5185370236771933062L;
	private String id;
	
	@Id
	@Override
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	 
}
