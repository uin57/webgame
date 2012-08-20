package com.pwrd.war.gameserver.common.unit;

import com.google.common.base.Objects;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.Maps;

public abstract class DynamicUnit extends SceneUnit{
	
	private final ClassToInstanceMap<Optional> values = Maps.newClassToInstanceMap();
	
	public <T extends Optional> T getOptional(Class<T> type) {
		return Objects.nonNull(values.getInstance(type));
	}

	public <T extends Optional> T getOptional(Class<T> type, T defaultValue) {
		T result = values.getInstance(type);
		return result != null ? result : defaultValue;
	}

	public <T extends Optional> void putOptional(Class<T> type, T value) {
		values.putInstance(type, value);
	}

	/**
	 * 可选的内部对象,需要实现该接口
	 * @author jiliang.lu
	 *
	 */
	public interface Optional {

	}

}
