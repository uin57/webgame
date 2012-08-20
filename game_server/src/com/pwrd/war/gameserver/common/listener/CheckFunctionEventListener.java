package com.pwrd.war.gameserver.common.listener;

import java.util.List;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.CheckFunctionEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.human.template.OpenFunctionTemplate;

public class CheckFunctionEventListener implements IEventListener<CheckFunctionEvent> {

	@Override
	public void fireEvent(CheckFunctionEvent event) {
		Human human = event.getHuman();
		int level = human.getLevel();
		String repId = event.getRepSN();
		List<OpenFunctionTemplate> tmps = Globals.getHumanService().getOpenFuncTemplate(level, repId);
		if(tmps == null || tmps.size() == 0)return;
		for(OpenFunctionTemplate tmp : tmps){
			int funcid = tmp.getFuncid();
			if(funcid == -1)return;//未达成功能开启条件
			String func = human.getPropertyManager().getOpenFunc();
			if(OpenFunction.isopen(func, funcid)) continue;//已经开启
			func = OpenFunction.open(func, funcid);//开启功能
			human.getPropertyManager().setOpenFunc(func);//保存
			human.snapChangedProperty(true);
			//针对特殊funcid操作特殊化
			this.dodeal(funcid);
		}
	}
	
	public void dodeal(int funcid){
		switch (funcid) {
			case 13:
				//官职开放
				break;
			

			default:
				break;
		}
	}

}
