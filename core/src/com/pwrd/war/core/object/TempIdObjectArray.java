package com.pwrd.war.core.object;


/**
 * <code>TempIdObject</code>对象的数组容器
 * 
 * @param <E>
 * 
 * 
 */
public class TempIdObjectArray<E extends TempIdObject> {
	private TempIdObject[] objs;
	private int maxSize;
	private int curSize;

	public TempIdObjectArray(int maxSize) {
		this.maxSize = maxSize;
		objs = new TempIdObject[maxSize];
	}

	/**
	 * @param obj
	 * @return
	 */
	public boolean add(E obj) {
		if (obj == null) {
			return false;
		}
		if (curSize == maxSize) {
			return false;
		}
		objs[curSize] = obj;
		obj.setTempId(curSize);
		curSize++;
		return true;
	}

	/**
	 * 从数组中移除一个元素
	 * 
	 * <pre>
	 * 如果待移除的元素是数组中的最后一个元素，则将数组中此索引的值置为null，
	 * 否则将数组中最后一个元素移到当前索引处，将数组中最后一个有效的索引的
	 * 值置为null
	 * </pre>
	 * 
	 * @param tempId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E remove(int tempId) {
		if (tempId > curSize - 1) {
			return null;
		}
		E objToRemove = (E) objs[tempId];
		if (tempId == curSize - 1) {
			objs[tempId] = null;
		} else {
			objs[tempId] = objs[curSize - 1];
			objs[tempId].setTempId(tempId);
			objs[curSize-1] = null;
		}
		curSize--;
		return objToRemove;
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E) objs[index];
	}

	public int size() {
		return curSize;
	}

}
