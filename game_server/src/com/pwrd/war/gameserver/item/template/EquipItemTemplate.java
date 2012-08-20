package com.pwrd.war.gameserver.item.template;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemFeature;

/**
 * 装备数据模板
 * 
 */
@ExcelRowBinding
public class EquipItemTemplate extends ItemTemplate{
	
	/** 装备对应的位置 */
	@ExcelCellBinding(offset = 4)
	private int positionId;
	
	private ItemDef.Position position;
	 
	/** 速度 */
	@ExcelCellBinding(offset = (START_INDEX+1))
	private double spd;
	  
	/** 攻击 */
	@ExcelCellBinding(offset = (START_INDEX+2))
	private double atk;
	
	/** 防御 */
	@ExcelCellBinding(offset = (START_INDEX+3))
	private double def;
	
	/** 气血值 */
	@ExcelCellBinding(offset = (START_INDEX+4))
	private double maxHp;
	
	/** 反击值 */
	@ExcelCellBinding(offset = (START_INDEX+5))
	private double fanji;
	
	/** 命中值 */
	@ExcelCellBinding(offset = (START_INDEX+6))
	private double mingzhong;
	
	/** 闪避值 */
	@ExcelCellBinding(offset = (START_INDEX+7))
	private double shanbi;
	
	/** 暴击值 */
	@ExcelCellBinding(offset = (START_INDEX+8))
	private double cri;
	
	/** 连击值 */
	@ExcelCellBinding(offset = (START_INDEX+9))
	private double  lianji ;
	
	/** 伤害率 */
	@ExcelCellBinding(offset = (START_INDEX+10))
	private double shanghai;
	
	/** 免伤率 */
	@ExcelCellBinding(offset = (START_INDEX+11))
	private double mianshang;
	/** 战斗力 */
	@ExcelCellBinding(offset = (START_INDEX+12))
	private double zhandouli;
	/** 强化等级上限 */
	@ExcelCellBinding(offset = (START_INDEX+13))
	private int enhanceMaxLevel;
	
	/** 套装属性ID*/
	@ExcelCellBinding(offset = (START_INDEX+14))
	private String suitSn;
//	/** 可镶嵌宝石的种类 */
//	@ExcelCellBinding(offset = (START_INDEX+15))
//	private String rubyAvailable;
//	/** 宝石孔数上限 */
//	@ExcelCellBinding(offset = (START_INDEX+16))
//	private int maxRubyCount;
	/** 职业限制ID(激活特殊技能)  */
	@ExcelCellBinding(offset = (START_INDEX+15))
	private int activeSkillVocation;
	/** 武将限制ID(激活特殊技能)  */
	@ExcelCellBinding(offset = (START_INDEX+16))
	private int activeSkillFigure;
	/** 激活技能ID  */
	@ExcelCellBinding(offset = (START_INDEX+17))
	private String activeSkillSn;
	
	/** 用来升级的卷轴SN **/
	@ExcelCellBinding(offset = (START_INDEX+18))
	private String reelSn;
	
	/** 材料1不足时需要元宝 */
	@ExcelCellBinding(offset = (START_INDEX+21))
	private int item1Gold;
	
	public EquipItemTemplate() {
	}
	
	public void setPositionId(int positionId) {
		this.positionId = positionId;
		this.position = ItemDef.Position.valueOf(this.positionId);
		if (position == null) {
			throw new ConfigException(String.format(
					"装备positionId配置错误 positionId=%d", positionId));
		}else{
//			if(this.position == ItemDef.Position.WEAPON){
//				super.setTypeId(ItemDef.Type.WEAPON);
//			}else{
//				//后面都是顺序一样的
//				super.setTypeId(ItemDef.Type.valueOf((ItemDef.Type.CAP.index - ItemDef.Position.CAP.index) + this.positionId));
//			}
			super.setTypeId(positionId);
		}
	}
	
	@Override
	public ItemDef.Position getPosition() {
		return position;
	}
 
	 
	
	@Override
	public boolean isEquipment() {
		return true;
	}

 

	@Override
	public boolean getCanBeUsed() {
		return true;
	}
	
	@Override
	public ItemFeature initItemFeature(Item item) {
		EquipmentFeature feature = new EquipmentFeature(item); 
		return feature;
	}
	
	@Override
	public String toString() {
		return  JSONObject.fromObject(this).toString();
	}

	public double getSpd() {
		return spd;
	}

	public void setSpd(double spd) {
		this.spd = spd;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public double getDef() {
		return def;
	}

	public void setDef(double def) {
		this.def = def;
	}
 

	public double getFanji() {
		return fanji;
	}

	public void setFanji(double fanji) {
		this.fanji = fanji;
	}

	public double getMingzhong() {
		return mingzhong;
	}

	public void setMingzhong(double mingzhong) {
		this.mingzhong = mingzhong;
	}

	public double getShanbi() {
		return shanbi;
	}

	public void setShanbi(double shanbi) {
		this.shanbi = shanbi;
	}

	public double getCri() {
		return cri;
	}

	public void setCri(double cri) {
		this.cri = cri;
	}

	public double getLianji() {
		return lianji;
	}

	public void setLianji(double lianji) {
		this.lianji = lianji;
	}

	public double getShanghai() {
		return shanghai;
	}

	public void setShanghai(double shanghai) {
		this.shanghai = shanghai;
	}

	public double getMianshang() {
		return mianshang;
	}

	public void setMianshang(double mianshang) {
		this.mianshang = mianshang;
	}

	public double getZhandouli() {
		return zhandouli;
	}

	public void setZhandouli(double zhandouli) {
		this.zhandouli = zhandouli;
	}

	public int getEnhanceMaxLevel() {
		return enhanceMaxLevel;
	}

	public void setEnhanceMaxLevel(int enhanceMaxLevel) {
		this.enhanceMaxLevel = enhanceMaxLevel;
	}

	 
 
	public int getPositionId() {
		return positionId;
	}

	public void setPosition(ItemDef.Position position) {
		this.position = position;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public String getSuitSn() {
		return suitSn;
	}

	public void setSuitSn(String suitSn) {
		this.suitSn = suitSn;
	}
	
//	public List<String> getRubyAvailable(){
//		if(StringUtils.isEmpty(rubyAvailable)){
//			return Arrays.asList();
//		}
//		return Arrays.asList(rubyAvailable.split(","));
//	}
////	public String getRubyAvailable() {
////		return rubyAvailable;
////	}
//
//	public void setRubyAvailable(String rubyAvailable) {
//		this.rubyAvailable = rubyAvailable;
//	}
//
//	public int getMaxRubyCount() {
//		return maxRubyCount;
//	}
//
//	public void setMaxRubyCount(int maxRubyCount) {
//		this.maxRubyCount = maxRubyCount;
//	}

	public int getActiveSkillVocation() {
		return activeSkillVocation;
	}

	public void setActiveSkillVocation(int activeSkillVocation) {
		this.activeSkillVocation = activeSkillVocation;
	}

	public int getActiveSkillFigure() {
		return activeSkillFigure;
	}

	public void setActiveSkillFigure(int activeSkillFigure) {
		this.activeSkillFigure = activeSkillFigure;
	}

	public String getActiveSkillSn() {
		return activeSkillSn;
	}

	public void setActiveSkillSn(String activeSkillSn) {
		this.activeSkillSn = activeSkillSn;
	}

	public String getReelSn() {
		return reelSn;
	}

	public void setReelSn(String reelSn) {
		this.reelSn = reelSn;
	}

	public int getItem1Gold() {
		return item1Gold;
	}

	public void setItem1Gold(int item1Gold) {
		this.item1Gold = item1Gold;
	}
 

}
