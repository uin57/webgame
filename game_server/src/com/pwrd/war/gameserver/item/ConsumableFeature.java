package com.pwrd.war.gameserver.item;

import net.sf.json.JSONObject;

/**
 * 消耗品道具实例属性
 * 
 * 
 */
public class ConsumableFeature implements ItemFeature {

	private Item item;
	
	/** 剩余使用量，可以是数量或次数等，即消耗品的耐久 */
	private int leftUseAmount;
	
	/** 总可使用量，即消耗品德耐久上限 */
	private int maxUseAmout;
	
	public ConsumableFeature(Item item) {
		this.item = item;
	}

	@Override
	public void bindItem(Item item) {
		this.item = item;
	}
	
	 
	public int getCurEndure() {
		return leftUseAmount;
	}
 
	public int getCurMaxEndure() {
		return maxUseAmout;
	}

 
	public boolean isFullEndure() {
		return leftUseAmount >= maxUseAmout;
	}
 
	public void recoverEndure() {
		this.leftUseAmount = maxUseAmout;
	}
 
	public void setCurEndure(int curEndure) {
		if (curEndure < 0) {
			this.leftUseAmount = 0;
		} else if (curEndure > maxUseAmout) {
			this.leftUseAmount = maxUseAmout;
		} else {
			this.leftUseAmount = curEndure;
		}
		item.setModified();
	}
	
	public void setCurMaxEndure(int endure) {
		this.maxUseAmout = endure >= 0 ? endure : 0;
		item.setModified();
	}
	
	public void initFromFeatureString(String feature){
		
	}
	public String toFeatureString(){
		JSONObject json = new JSONObject();
		return json.toString();
	}

	@Override
	public ItemFeature cloneTo(ItemFeature to) {
		ConsumableFeature c = (ConsumableFeature) to;
		c.leftUseAmount = this.leftUseAmount;
		c.maxUseAmout = this.maxUseAmout; 
		return c;
	}

	@Override
	public String toPropertyString() { 
		return null;
	}

	@Override
	public void fromPropertyString(String propStr) {
		 
		
	}
}
