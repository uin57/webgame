package com.pwrd.war.gameserver.common.unit;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.pwrd.war.common.constants.Loggers;

/**
 * GameUnit对象列表
 * 
 * <pre>
 * 列表具有一定的初始化大小，列表内用数组存储对象，列表内对象的Id即为
 * 此对象在列表内数组中的索引值加1
 * </pre>
 * 
 * @see GameUnit
 */
public final class GameUnitList<E extends GameUnit> extends AbstractList<E> {
	/** */
	private GameUnit[] objs;
	/** */
	private int maxSize;
	/** 当前objs数组中对象的个数 */
	private int curSize;
	/** 当前objs数组中所有对象id的列表，遍历objs时使用 */
	private int[] unitIds;
	/** */
	public static final int NULL_ID = -1;
	/** 最新加入列表中的对象的id */
	private int newestUnitId;

	public GameUnitList(int maxSize) {
		maxSize += 100;// 留出余量，防止根据id取到的对象不是原来想找的对象
		this.maxSize = maxSize;
		objs = new GameUnit[maxSize];
		unitIds = new int[maxSize];
		Arrays.fill(unitIds, NULL_ID);
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean add(E obj) {
		if (obj == null) {
			return false;
		}
		if (curSize == maxSize) {
			return false;
		}
		//此处从最后加入的位置开始找空位，是为了防止刚刚释放掉的id又被重新使用了
		for (int i = newestUnitId; i < objs.length; i++) {
			if (objs[i] == null) {
				objs[i] = obj;
				obj.setId(i + 1);
				newestUnitId =obj.getId();
				break;
			}
		}
		if (obj.getId() < 1) {
			for (int i = 0; i < newestUnitId; i++) {
				if (objs[i] == null) {
					objs[i] = obj;
					obj.setId(i + 1);
					newestUnitId = obj.getId();
					break;
				}
			}
		}
		if (obj.getId() < 1) {//正常情况下应该不会出现这种情况
			throw new RuntimeException("[#GameUnitList]无法为新加入的对象分配Id");
		}
		unitIds[curSize] = obj.getId();
		curSize++;
		modCount++;
		return true;
	}

	/**
	 * @param unitId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int unitId) {
		if (curSize < 1) {
			return null;
		}
		if (unitId > maxSize) {
			return null;
		}
		if (unitId <= 0) {
			return null;
		}
		E objToRemove = (E) objs[unitId - 1];
		if (objToRemove == null) {
			Loggers.objectLogger.error("unitId:" + unitId + " not exists");
			return null;
		}
		objs[unitId - 1] = null;
		for (int i = 0; i < curSize; i++) {
			if (unitIds[i] == unitId) {
				if (i == curSize - 1) {
					unitIds[i] = NULL_ID;
				} else {
					unitIds[i] = unitIds[curSize - 1];
					unitIds[curSize - 1] = NULL_ID;
				}
			}
		}
		curSize--;
		objToRemove.setId(NULL_ID);
		modCount++;
		return objToRemove;
	}

	/**
	 * 迭代时使用的外部移除,下次迭代时自动删除
	 * 
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E softRemove(int unitId) {
		if (curSize < 1) {
			return null;
		}
		if (unitId >= maxSize) {
			return null;
		}
		if (unitId <= 0) {
			return null;
		}
		E objToRemove = (E) objs[unitId - 1];
		if (objToRemove == null) {
			Loggers.objectLogger.error("unitId:" + unitId + " not exists");
			return null;
		}
		for (int i = 0; i < curSize; i++) {
			if (unitIds[i] == unitId) {
				objs[unitId - 1].setId(NULL_ID);
				break;
			}
		}
		return objToRemove;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index <= 0 || index >= maxSize) {
			return null;
		}
		E result = (E) objs[index - 1];
		if (result == null || result.getId() == NULL_ID) {
			// 说明已经被软删除掉了
			return null;
		}
		return result;
	}

	@Override
	public int size() {
		return curSize;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/**
	 * 迭代器
	 * 
	 */
	private class Itr implements Iterator<E> {
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		int cursor = 0;

		/**
		 * Index of element returned by most recent call to next or previous.
		 * Reset to -1 if this element is deleted by a call to remove.
		 */
		int lastRet = -1;

		/**
		 * The modCount value that the iterator believes that the backing List
		 * should have. If this expectation is violated, the iterator has
		 * detected concurrent modification.
		 */
		int expectedModCount = modCount;

		@Override
		public boolean hasNext() {
			return cursor != size();
		}

		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			checkForComodification();
			try {
				E next = (E) objs[unitIds[cursor] - 1];
				lastRet = cursor++;
				if (next.getId() == 0 || next.getId() == NULL_ID) {
					remove();
					return null;
				}
				return next;
			} catch (IndexOutOfBoundsException e) {
				checkForComodification();
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			if (lastRet == -1)
				throw new IllegalStateException();
			checkForComodification();

			try {
				// 注意，此处传的是unitId
				GameUnitList.this.remove(unitIds[lastRet]);
				if (lastRet < cursor)
					cursor--;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException e) {
				throw new ConcurrentModificationException();
			}
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}
}
