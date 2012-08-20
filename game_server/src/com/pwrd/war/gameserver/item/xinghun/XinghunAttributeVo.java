package com.pwrd.war.gameserver.item.xinghun;



/**
 * 星魂属性信息
 * 
 */
public class XinghunAttributeVo {
	
	private double value;	//属性值
	
	private int qualtityNum;	//属性品质
	
	public XinghunAttributeVo(){
		
	}
	
	public XinghunAttributeVo(double value, int qualtityNum){
		this.value = value;
		this.qualtityNum = qualtityNum;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getQualtityNum() {
		return qualtityNum;
	}

	public void setQualtityNum(int qualtityNum) {
		this.qualtityNum = qualtityNum;
	}
}
