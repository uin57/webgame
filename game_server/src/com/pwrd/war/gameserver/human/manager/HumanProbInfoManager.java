package com.pwrd.war.gameserver.human.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.db.model.ProbEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.domain.ProbInfo;

public class HumanProbInfoManager {
	private Human human;
	private Map<String, ProbInfo> probs;
	public HumanProbInfoManager(Human human) {
		this.human = human;
		probs = new HashMap<String, ProbInfo>();
	}
	public void load(){
		List<ProbEntity> list = Globals.getDaoService().getProbInfoDAO().getByPlayerUUID(human.getUUID());
		if(list.size() > 0){
			for(ProbEntity ent : list){
				ProbInfo info = new ProbInfo(human);
				info.fromEntity(ent);
				info.setInDb(true);
				info.getLifeCycle().activate();
				this.probs.put(info.getEventId(), info);
			}			
		}		
	}
	private int[] getZeros(int length)
	{
		int[] times = new int[length];
		for(int i = 0; i< times.length; i++){
			times[i] = 0;
		}
		return times;
	}
	//根据事件id和概率初始化
	public void init(String eventId,  int[] probs){
		if(this.probs.containsKey(eventId)){
			return;
		}
		ProbInfo info = new ProbInfo(human); 
		info.setEventId(eventId);
		info.setProbs(probs);
		info.setPlayerUUID(human.getUUID());
		info.setTimes(getZeros(probs.length));
		info.setInDb(false);
		info.setDbId(KeyUtil.UUIDKey());
		info.getLifeCycle().activate();
		info.setModified();
		this.probs.put(info.getEventId(), info);
	}
	//
	/**
	 * 根据事件id取得随机到的结果，该方法保证probs在所有权重用完后会各出现至少一次
	 */
	public int prob(String eventId){
		if(!this.probs.containsKey(eventId)){
			return -1;
		}
		ProbInfo p = this.probs.get(eventId);
		
		int sel = this.calcprob(p.getProbs(), p.getTimes());
		p.getTimes()[sel] += 1;
 
		int allWeight = 0; //总权重
		int alltimes = 0;//已随机次数 
		for(int i = 0; i< p.getProbs().length; i++){
			alltimes += p.getTimes()[i];
			allWeight += p.getProbs()[i]; 
		}
		if(alltimes  == allWeight){
			//重置
			p.setTimes(getZeros(p.getProbs().length));
		}
		p.setModified();
		return sel;
	}
	
	public static void main(String[] args) {
		HumanProbInfoManager a = new HumanProbInfoManager(null);
		int c = 0;
		for(int i = 0; i < 100; i++){
			int s = a.calcprob(new int[]{99,1}, new int[]{99,0});
			if(s == 1){c++;}
		}
		System.out.println(c);
	}
	
	private int calcprob(int[] prob, int[] times){
		int allWeight = 0; //总权重
		int alltimes = 0;//已随机次数
		int zeroProb = 0;//次数0的权重和
		for(int i = 0; i< prob.length; i++){
			alltimes += times[i];
			allWeight += prob[i];
			if(times[i] == 0){
				zeroProb += prob[i];
			}
		}
		int sel = -1;
		if(allWeight - alltimes <= zeroProb){
			//权重为0的必须随机出一个
			while(true){
				int ri = RandomUtils.nextInt(prob.length);
				int w = prob[ri];
				if(times[ri] == 0 &&
						RandomUtils.nextInt(allWeight) <= w){
					sel = ri; 
					break;
				}
			}
		}else{
			while(true){
				int ri = RandomUtils.nextInt(prob.length);
				int w = prob[ri];
				if(RandomUtils.nextInt(allWeight) <= w){
					sel = ri; 
					break;
				}
			}
		}
		return sel;
	}
	
}
