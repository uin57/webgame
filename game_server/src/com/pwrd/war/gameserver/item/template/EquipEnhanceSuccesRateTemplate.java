package com.pwrd.war.gameserver.item.template;

import net.sf.json.JSONObject;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class EquipEnhanceSuccesRateTemplate  extends EquipEnhanceSuccesRateVO{

	@Override
	public void check() throws TemplateConfigException { 
		
	}
	
	/**
	 * 强化成功率
	 * @author xf
	 */
	public static class EquipEnhanceSuccesRate implements Cloneable{
		public double succesRate;
		/** 成功率出现权重 */ 
		public int weight; 
		
		public EquipEnhanceSuccesRate(){}
		public EquipEnhanceSuccesRate(JSONObject json){
			this.succesRate = json.getDouble("succesRate");
			this.weight = json.getInt("weight");
		}
		
		public EquipEnhanceSuccesRate clone(){
			EquipEnhanceSuccesRate r = new EquipEnhanceSuccesRate();
			r.succesRate = this.succesRate;
			r.weight = this.weight;
			return r;
		}

		@Override
		public int hashCode() { 
			return (int) (this.succesRate * 100000);
		}

		@Override
		public boolean equals(Object obj) { 
			if(obj instanceof EquipEnhanceSuccesRate){
				return ((EquipEnhanceSuccesRate) obj).succesRate == this.succesRate;
			}else{
				return false;
			} 
		}

		public double getSuccesRate() {
			return succesRate;
		}

		public void setSuccesRate(double succesRate) {
			this.succesRate = succesRate;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
		
		
	}
}
