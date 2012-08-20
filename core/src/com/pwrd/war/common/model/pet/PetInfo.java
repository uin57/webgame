package com.pwrd.war.common.model.pet;

import net.sf.json.JSONObject;

public class PetInfo {
	
	/** 武将uuid */
	private String uuid;
	
	private String petSn;
//	
//	private String skeletonId;	//骨骼
//	/** 武将的名称 */
//	private String name;
//	 
//	/** 级别 */
//	private int level;	
//	
//	/** 经验值 */
//	private long exp;
 
	
	public PetInfo()
	{
		
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
 
	 
	public String toString(){
		return JSONObject.fromObject(this).toString();
	}


	public String getPetSn() {
		return petSn;
	}


	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

 

 
}
