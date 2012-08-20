package com.pwrd.war.core.event;

/**
 * 要触发事件的触发器,只用于标识某个服务或者接口要触发某个事件,实现该接口的类应该提供注入事件监听器{@link IEventListener} 的途径,
 * 如在构造函数中提供参数
 * 
  *
 * @param T
 *            触发的事件类型
 * 
 */
@SuppressWarnings("unchecked")
public interface IEventTrigger<T extends IEvent> {

}
