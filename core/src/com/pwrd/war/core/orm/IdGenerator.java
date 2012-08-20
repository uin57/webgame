package com.pwrd.war.core.orm;

import java.io.Serializable;


/**
 * 
 * 
 */
public interface IdGenerator {
	@SuppressWarnings("unchecked")
	public Serializable generateId(BaseEntity entity);
}