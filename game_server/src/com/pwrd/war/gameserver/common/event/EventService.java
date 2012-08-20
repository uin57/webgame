package com.pwrd.war.gameserver.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.core.util.CollectionUtil;

/**
 * 事件管理器,负责事件监听器的注册,以及各系统之间事件的转发
 * 
  *
 * 
 */
@SuppressWarnings("unchecked")
public class EventService implements IManager, IEventListener {
	private final Map<Class, List<IEventListener>> eventListener = CollectionUtil
			.buildHashMap();

	/**
	 * 注册事件监听器
	 * 
	 * @param <T>
	 * @param eventClass
	 * @param listener
	 * @throws IllegalArgumentException
	 * 
	 */
	public void addListener(Class eventClass, IEventListener listener) {
		List<IEventListener> _list = eventListener.get(eventClass);
		if (_list == null) {
			_list = new ArrayList<IEventListener>();
			this.eventListener.put(eventClass, _list);
		}
		for (IEventListener _listener : _list) {
			if (_listener.getClass() == listener.getClass()) {
				throw new IllegalArgumentException("The dup class listener ["
						+ listener.getClass() + "]");
			}
		}
		_list.add(listener);
	}

	/**
	 * 转发事件
	 */
	@Override
	public void fireEvent(final IEvent event) {
		List<IEventListener> _eventListeners = this.eventListener.get(event
				.getClass());
		if (_eventListeners == null || _eventListeners.isEmpty()) {
			return;
		}
		for (IEventListener _listener : _eventListeners) {
			_listener.fireEvent(event);
		}
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
