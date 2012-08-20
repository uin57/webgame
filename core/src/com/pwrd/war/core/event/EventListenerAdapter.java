package com.pwrd.war.core.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.util.CollectionUtil;

/**
 * 事件监听适配器，可以包装多个适配器
 * 
 */
@SuppressWarnings("unchecked")
public class EventListenerAdapter implements IEventListener {
	private static final Logger logger = LoggerFactory.getLogger("war.event");
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
		if (logger.isInfoEnabled()) {
			logger
					.info("[#GS.EventManager.addListener] [Register event listener (event:"
							+ eventClass.getName()
							+ " listener:"
							+ listener.getClass().getName() + "]");
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
}
