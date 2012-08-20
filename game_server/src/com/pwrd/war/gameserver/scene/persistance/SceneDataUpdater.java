package com.pwrd.war.gameserver.scene.persistance;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pwrd.war.core.annotation.NotThreadSafe;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.persistance.AbstractDataUpdater;
import com.pwrd.war.gameserver.common.db.POUpdater;

/**
 * 场景更新接口
 * 
 * @author haijiang.jin
 *
 */
@NotThreadSafe
public class SceneDataUpdater extends AbstractDataUpdater{

	private static Map<Class<? extends PersistanceObject<?, ?>>, POUpdater> operationDbMap = new LinkedHashMap<Class<? extends PersistanceObject<?, ?>>, POUpdater>();

	static {

	}

	public SceneDataUpdater() {
	}

	@Override
	protected void doUpdate(LifeCycle lc) {
		if (!lc.isActive()) {
			throw new IllegalStateException(
				"Only the live object can be updated.");
		}

		PersistanceObject<?, ?> po = lc.getPO();
		POUpdater poUpdater = operationDbMap.get(po.getClass());
		poUpdater.save(po);
	}

	@Override
	protected void doDel(LifeCycle lc) {
		if (!lc.isDestroyed()) {
			throw new IllegalStateException(
					"Only the destroyed object can be deleted.");
		}
		PersistanceObject<?, ?> po = lc.getPO();
		operationDbMap.get(po.getClass()).delete(po);
	}

}