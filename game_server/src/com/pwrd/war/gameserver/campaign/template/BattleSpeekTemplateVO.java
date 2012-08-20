package com.pwrd.war.gameserver.campaign.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 战斗武将说话
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class BattleSpeekTemplateVO extends TemplateObject {

	/** 武将id */
	@ExcelCellBinding(offset = 1)
	protected String petSn;

	/** 武将名称 */
	@ExcelCellBinding(offset = 2)
	protected String petName;

	/** 战斗情况一对话 */
	@ExcelCellBinding(offset = 3)
	protected String battleSpeek1;

	/** 战斗情况二对话 */
	@ExcelCellBinding(offset = 4)
	protected String battleSpeek2;

	/** 战斗情况三对话 */
	@ExcelCellBinding(offset = 5)
	protected String battleSpeek3;

	/** 战斗情况四对话 */
	@ExcelCellBinding(offset = 6)
	protected String battleSpeek4;

	/** 区域对话一 */
	@ExcelCellBinding(offset = 7)
	protected String areaSpeek1;

	/** 区域对话二 */
	@ExcelCellBinding(offset = 8)
	protected String areaSpeek2;

	/** 区域对话三 */
	@ExcelCellBinding(offset = 9)
	protected String areaSpeek3;

	/** 区域对话四 */
	@ExcelCellBinding(offset = 10)
	protected String areaSpeek4;

	/** 情绪对话一 */
	@ExcelCellBinding(offset = 11)
	protected String moodSpeek1;

	/** 情绪对话二 */
	@ExcelCellBinding(offset = 12)
	protected String moodSpeek2;

	/** 情绪对话三 */
	@ExcelCellBinding(offset = 13)
	protected String moodSpeek3;

	/** 情绪对话四 */
	@ExcelCellBinding(offset = 14)
	protected String moodSpeek4;


	public String getPetSn() {
		return this.petSn;
	}

	public void setPetSn(String petSn) {
		if (StringUtils.isEmpty(petSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[武将id]petSn不可以为空");
		}
		this.petSn = petSn;
	}
	
	public String getPetName() {
		return this.petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	public String getBattleSpeek1() {
		return this.battleSpeek1;
	}

	public void setBattleSpeek1(String battleSpeek1) {
		this.battleSpeek1 = battleSpeek1;
	}
	
	public String getBattleSpeek2() {
		return this.battleSpeek2;
	}

	public void setBattleSpeek2(String battleSpeek2) {
		this.battleSpeek2 = battleSpeek2;
	}
	
	public String getBattleSpeek3() {
		return this.battleSpeek3;
	}

	public void setBattleSpeek3(String battleSpeek3) {
		this.battleSpeek3 = battleSpeek3;
	}
	
	public String getBattleSpeek4() {
		return this.battleSpeek4;
	}

	public void setBattleSpeek4(String battleSpeek4) {
		this.battleSpeek4 = battleSpeek4;
	}
	
	public String getAreaSpeek1() {
		return this.areaSpeek1;
	}

	public void setAreaSpeek1(String areaSpeek1) {
		this.areaSpeek1 = areaSpeek1;
	}
	
	public String getAreaSpeek2() {
		return this.areaSpeek2;
	}

	public void setAreaSpeek2(String areaSpeek2) {
		this.areaSpeek2 = areaSpeek2;
	}
	
	public String getAreaSpeek3() {
		return this.areaSpeek3;
	}

	public void setAreaSpeek3(String areaSpeek3) {
		this.areaSpeek3 = areaSpeek3;
	}
	
	public String getAreaSpeek4() {
		return this.areaSpeek4;
	}

	public void setAreaSpeek4(String areaSpeek4) {
		this.areaSpeek4 = areaSpeek4;
	}
	
	public String getMoodSpeek1() {
		return this.moodSpeek1;
	}

	public void setMoodSpeek1(String moodSpeek1) {
		this.moodSpeek1 = moodSpeek1;
	}
	
	public String getMoodSpeek2() {
		return this.moodSpeek2;
	}

	public void setMoodSpeek2(String moodSpeek2) {
		this.moodSpeek2 = moodSpeek2;
	}
	
	public String getMoodSpeek3() {
		return this.moodSpeek3;
	}

	public void setMoodSpeek3(String moodSpeek3) {
		this.moodSpeek3 = moodSpeek3;
	}
	
	public String getMoodSpeek4() {
		return this.moodSpeek4;
	}

	public void setMoodSpeek4(String moodSpeek4) {
		this.moodSpeek4 = moodSpeek4;
	}
	

	@Override
	public String toString() {
		return "BattleSpeekTemplateVO[petSn=" + petSn + ",petName=" + petName + ",battleSpeek1=" + battleSpeek1 + ",battleSpeek2=" + battleSpeek2 + ",battleSpeek3=" + battleSpeek3 + ",battleSpeek4=" + battleSpeek4 + ",areaSpeek1=" + areaSpeek1 + ",areaSpeek2=" + areaSpeek2 + ",areaSpeek3=" + areaSpeek3 + ",areaSpeek4=" + areaSpeek4 + ",moodSpeek1=" + moodSpeek1 + ",moodSpeek2=" + moodSpeek2 + ",moodSpeek3=" + moodSpeek3 + ",moodSpeek4=" + moodSpeek4 + ",]";

	}
}