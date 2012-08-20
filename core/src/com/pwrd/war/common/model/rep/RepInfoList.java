package com.pwrd.war.common.model.rep;

import net.sf.json.JSONObject;

public class RepInfoList {

	/** 副本uuid */
	private String uuid;
	
	/** 副本id */
	private String repId;
	
	/** 副本类型*/
	private int repType;
	
	/** 副本标志 -1未开启，0已开启未通过，1已通过 */
	private int flag;
	
	/** 副本名称*/
	private String repName;
	
	/** 副本当天进入次数 */
	private int num;
	
	/** 副本分数*/
	private int score;
	
	/** 任务标记*/
	private boolean questFlag;

	public RepInfoList()
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


	public String getRepId() {
		return repId;
	}


	public void setRepId(String repId) {
		this.repId = repId;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getRepName() {
		return repName;
	}


	public void setRepName(String repName) {
		this.repName = repName;
	}


	public int getRepType() {
		return repType;
	}


	public void setRepType(int repType) {
		this.repType = repType;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getQuestFlag() {
		return questFlag;
	}

	public void setQuestFlag(boolean questFlag) {
		this.questFlag = questFlag;
	}
}
