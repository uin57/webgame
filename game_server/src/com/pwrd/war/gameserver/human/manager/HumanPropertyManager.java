package com.pwrd.war.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.role.properties.HumanNumProperty;
import com.pwrd.war.gameserver.role.properties.HumanStrPropety;
import com.pwrd.war.gameserver.role.properties.RolePropertyManager;

/**
 * 玩家角色的属性管理器
 * 
 */
public class HumanPropertyManager extends RolePropertyManager {

	/** 人物数值属性,差异化推送给客户端 */
	protected HumanNumProperty propertyNum;

	/** 字符串数值属性 推送给客户端 **/
	protected HumanStrPropety propetyStr;

	/** 一般属性不需要推给客户端的 **/
	protected HumanNormalProperty propertyNormal;

	

	private Human human;

	public HumanPropertyManager(Human human) {
		this.human = human;
		propertyNum = new HumanNumProperty();
		propetyStr = new HumanStrPropety();
		propertyNormal = new HumanNormalProperty();
	}

	// /**
	// * 返回Human哪些属性集发生变化，返回的是一个属性集合，而不是属性里的单个属性
	// */
	// @Override
	// public KeyValuePair<Integer, Integer>[] getChanged() {
	// //TODO 这个getChanged方法获取到的实际上是全部值
	// if (propChangeSet.isEmpty()) {
	// return null;
	// }
	// boolean _aPropChange =
	// propChangeSet.get(RolePropertyManager.CHANGE_INDEX_APROP);
	// int _length = 0;
	// if (_aPropChange) {
	// _length += HumanNumProperty._SIZE;
	// }
	// //
	// // KeyValuePair<Integer, Integer>[] valuePairs =
	// KeyValuePair.newKeyValuePairArray(_length);
	// // int i = 0;
	// // if (_aPropChange) {
	// // for (KeyValuePair<Integer, Integer> valuePair :
	// this.property.getIndexValuePairs()) {
	// // valuePairs[i] = valuePair;
	// // //给客户端的属性id是根据属性类型计算得出的
	// ////
	// valuePairs[i].setKey(PropertyType.genPropertyKey(valuePairs[i].getKey(),
	// PropertyType.HUMAN_PROP_A));
	// // i++;
	// // }
	// // }
	// KeyValuePair<Integer, Integer>[] valuePairs =
	// this.propertyNum.getIndexValuePairs();
	// return valuePairs;
	// }
	//
	// public KeyValuePair<Integer, String>[] getChangedStr(){
	// KeyValuePair<Integer, Object>[] v = propetyStr.getChanged();
	// KeyValuePair<Integer, String>[] _strChanged =
	// KeyValuePair.newKeyValuePairArray(v.length);
	// for (int i = 0; i < v.length; i++) {
	// _strChanged[i] = new KeyValuePair<Integer, String>(
	// v[i].getKey(),
	// v[i].getValue().toString());
	// }
	// return _strChanged;
	// }
	// /*
	// * 如果A属性变化则返回true，同时重置，告诉role已修改，role可以保存到数据库
	// */
	// @Override
	// protected boolean updateAProperty(Human role, int effectMask) {
	// boolean _changed = false;
	// if (propertyNum.isChanged()) {
	// _changed = true;
	// propertyNum.resetChanged();
	// }
	// if (_changed) {
	// role.setModified();
	// return true;
	// }
	// return false;
	// }
	//
	// @Override
	// protected boolean updateBProperty(Human role, int effectMask) {
	// Loggers.humanLogger.error("Human has not Level B property.");
	// return false;
	// }
	//
	// /*
	// * 在更新Human属性后需要调用此，告知更新了哪些字段，方便差异化更新
	// */
	// public void updateProperty(int effectMask) {
	// //检查该标志是否有A属性，如果A变化了，则设置propChangeSet A变化了
	// if (this.updateAProperty(owner, effectMask)) {
	// effectMask |= RolePropertyManager.PROP_FROM_MARK_APROPERTY;
	// propChangeSet.set(RolePropertyManager.CHANGE_INDEX_APROP);
	// }
	// }

	public int getTitle() {
		return propertyNum.getInt(HumanNumProperty.TITLE);
	}

	public void setTitle(int title) {
		propertyNum.set(HumanNumProperty.TITLE, title);
		human.onModified();
	}

	public int getVitality() {
		return propertyNum.getInt(HumanNumProperty.VITALITY);
	}

	public void setVitality(int vitality) {
		propertyNum.set(HumanNumProperty.VITALITY, vitality);
		human.onModified();
	}

	public int getMaxVitality() {
		return propertyNum.getInt(HumanNumProperty.MAX_VITALITY);
	}

	public void setMaxVitality(int maxVitality) {
		propertyNum.set(HumanNumProperty.MAX_VITALITY, maxVitality);
		human.onModified();
	}

	public int getBuyVitTimes() {
		return propertyNum.getInt(HumanNumProperty.BUY_VIT_TIMES);
	}

	public void setBuyVitTimes(int buyVitTimes) {
		propertyNum.setInt(HumanNumProperty.BUY_VIT_TIMES, buyVitTimes);
		human.onModified();
	}

	public int getCharm() {
		return propertyNum.getInt(HumanNumProperty.CHARM);
	}

	public void setCharm(int charm) {
		propertyNum.set(HumanNumProperty.CHARM, charm);
		human.onModified();
	}

	public int getProtectFlower() {
		return propertyNum.getInt(HumanNumProperty.PROTECTFLOWER);
	}

	public void setProtectFlower(int protectFlower) {
		propertyNum.set(HumanNumProperty.PROTECTFLOWER, protectFlower);
		human.onModified();
	}

	public int getOffical() {
		return propertyNum.getInt(HumanNumProperty.OFFICAL);
	}

	public void setOffical(int offical) {
		propertyNum.set(HumanNumProperty.OFFICAL, offical);
		human.onModified();
	}

	public String getFamilyId() {
		return propetyStr.getString(HumanStrPropety.FAMILY_ID);
	}

	public void setFamilyId(String familyId) {
		propetyStr.set(HumanStrPropety.FAMILY_ID, familyId);
		human.onModified();
	}

	public int getHonour() {
		return propertyNum.getInt(HumanNumProperty.HONOUR);
	}

	public void setHonour(int honour) {
		propertyNum.set(HumanNumProperty.HONOUR, honour);
		human.onModified();
	}

	public int getMassacre() {
		return propertyNum.getInt(HumanNumProperty.MASSACRE);
	}

	public void setMassacre(int massacre) {
		propertyNum.set(HumanNumProperty.MASSACRE, massacre);
		human.onModified();
	}

	public int getBattleAchieve() {
		return propertyNum.getInt(HumanNumProperty.BATTLEACHIEVE);
	}

	public void setBattleAchieve(int battleAchieve) {
		propertyNum.set(HumanNumProperty.BATTLEACHIEVE, battleAchieve);
		human.onModified();
	}

	public int getSee() {
		return propertyNum.getInt(HumanNumProperty.SEE);
	}

	public void setSee(int see) {
		propertyNum.set(HumanNumProperty.SEE, see);
		human.onModified();
	}

	public int getVip() {
		return propertyNum.getInt(HumanNumProperty.VIP);
	}

	public void setVip(int vip) {
		propertyNum.setInt(HumanNumProperty.VIP, vip);
		human.onModified();
	}

//	public long getSliver() {
//		return propertyNum.getLong(HumanNumProperty.SLIVER);
//	}
//
//	public void setSliver(long sliver) {
//		propertyNum.set(HumanNumProperty.SLIVER, sliver);
//		human.onModified();
//	}

	public int getCoins() {
		return propertyNum.getInt(HumanNumProperty.COINS);
	}

	public void setCoins(int coins) {
		propertyNum.set(HumanNumProperty.COINS, coins);
		human.onModified();
	}

	public int getGold() {
		return propertyNum.getInt(HumanNumProperty.GOLD);
	}

	public void setGold(int gold) {
		propertyNum.set(HumanNumProperty.GOLD, gold);
		human.onModified();
	}

	public int getCoupon() {
		return propertyNum.getInt(HumanNumProperty.COUPON);
	}

	public void setCoupon(int coupon) {
		propertyNum.set(HumanNumProperty.COUPON, coupon);
		human.onModified();
	}

	public int getPopularity() {
		return propertyNum.getInt(HumanNumProperty.POPULARITY);
	}

	public void setPopularity(int popularity) {
		propertyNum.setInt(HumanNumProperty.POPULARITY, popularity);
		human.onModified();
	} 
	
	public void setMuanBattle(boolean bAuto) {
		propertyNum.set(HumanNumProperty.MUAN_BATTLE, bAuto ? 1 : 0);
		human.onModified();
	}

	public boolean getMuanBattle() {
		return propertyNum.getInt(HumanNumProperty.MUAN_BATTLE) == 1;
	}

	public void setBeforeSceneId(String beforeSceneId) {
		propetyStr.setString(HumanStrPropety.BEFORESCENEID, beforeSceneId);
	}

	public String getBeforeSceneId() {
		return propetyStr.getString(HumanStrPropety.BEFORESCENEID);
	}

	public int getBeforeX() {
		return propertyNum.getInt(HumanNumProperty.BEFORE_X);
	}

	public void setBeforeX(int x) {
		propertyNum.set(HumanNumProperty.BEFORE_X, x);
		human.onModified();
	}

	public int getBeforeY() {
		return propertyNum.getInt(HumanNumProperty.BEFORE_Y);
	}

	public void setBeforeY(int y) {
		propertyNum.set(HumanNumProperty.BEFORE_Y, y);
		human.onModified();
	}

	@Override
	public boolean isNumchanged() {
		return propertyNum.isChanged();
	}

	@Override
	public boolean isStrchanged() {
		return propetyStr.isChanged();
	}

	@Override
	public List<KeyValuePair<Integer, Integer>> getChangedNum() {
		KeyValuePair<Integer, Object>[] v = propertyNum.getChanged();
		List<KeyValuePair<Integer, Integer>> _changed = new ArrayList<KeyValuePair<Integer, Integer>>();
		for (int i = 0; i < v.length; i++) {
//			if (v[i].getValue() instanceof Double) {
//				_changed.add(new KeyValuePair<Integer, Integer>(v[i].getKey(),
//						(Integer) v[i].getValue()));
//			} else {
				_changed.add(new KeyValuePair<Integer, Integer>(v[i].getKey(),
						(Integer) v[i].getValue()));
//			}
		}
		return _changed;
	}

	@Override
	public List<KeyValuePair<Integer, String>> getChangedString() {
		KeyValuePair<Integer, Object>[] v = propetyStr.getChanged();
		List<KeyValuePair<Integer, String>> _strChanged = new ArrayList<KeyValuePair<Integer, String>>();
		for (int i = 0; i < v.length; i++) {
			if(v[i].getValue()== null){
				_strChanged.add(new KeyValuePair<Integer, String>(v[i].getKey(),
						null));
			}else{
				_strChanged.add(new KeyValuePair<Integer, String>(v[i].getKey(),
						v[i].getValue().toString()));
			}
		}
		return _strChanged;
	}

	@Override
	public void resetChanged() {
		propetyStr.resetChanged();
		propertyNum.resetChanged();
	}

	public int getCurrency(final Currency currency) {
		return propertyNum.getInt(currency.getRoleBaseIntPropIndex());
	}

	public void setCurrency(final Currency currency, int amount) {
		propertyNum.set(currency.getRoleBaseIntPropIndex(), amount);
		human.onModified();
	}

	public int getEquipEnhanceNum() {
		return propertyNum.getInt(HumanNumProperty.EQUIP_ENHANCE_NUM);
	}

	public void setEquipEnhanceNum(int equipEnhanceNum) {
		propertyNum.setInt(HumanNumProperty.EQUIP_ENHANCE_NUM, equipEnhanceNum);
		human.onModified();
	}

	public boolean isOpenForm() {
		return propertyNum.getInt(HumanNumProperty.IS_OPEN_FORM) == 1;
	}

	public void setIsOpenForm(boolean isOpen) {
		propertyNum.setInt(HumanNumProperty.IS_OPEN_FORM, isOpen ? 1 : 0);
		human.onModified();
	}

 

	public boolean getIsInBattle() {
		return propertyNum.getInt(HumanNumProperty.ISIN_BATTLE) == 1;
	}

	public void setIsInBattle(boolean rs) {
		propertyNum.setInt(HumanNumProperty.ISIN_BATTLE, rs ? 1 : 0);
	}

	public long getBuyVitalityDate() {
		return propertyNormal.getBuyVitalityDate();
	}

	public void setBuyVitalityDate(long buyVitalityDate) {
		propertyNormal.setBuyVitalityDate(buyVitalityDate);
		human.onModified();
	}
	
	public int getBagSize(){
		return propertyNormal.getBagSize();
	}
	public void setBagSize(int size){
		propertyNormal.setBagSize(size);
		human.onModified();
	}
	
	public int getMaxBagSize(){
		return propertyNormal.getMaxBagSize();
	}
	public void setMaxBagSize(int size){
		propertyNormal.setMaxBagSize(size);
		human.onModified();
	}
	
	public int getDepotSize(){
		return propertyNormal.getDepotSize();
	}
	public void setDepotSize(int size){
		propertyNormal.setDepotSize(size);
		human.onModified();
	}
	
	public int getMaxDepotSize(){
		return propertyNormal.getMaxDepotSize();
	}
	public void setMaxDepotSize(int size){
		propertyNormal.setMaxDepotSize(size);
		human.onModified();
	}
	
	public double getBuffAtk() {
		return propertyNormal.getBuffAtk();
	}

	public void setBuffAtk(double buffAtk) {
		double oatk = propertyNormal.getBuffAtk();
		propertyNormal.setBuffAtk(buffAtk);		
		this.human.addAtk(buffAtk - oatk);
		this.human.onModified();
	}

	public double getBuffDef() {
		return propertyNormal.getBuffDef();
	}

	public void setBuffDef(double buffDef) {
		double odef  = propertyNormal.getBuffDef();
		propertyNormal.setBuffDef(buffDef);
		this.human.addDef(buffDef - odef);
		human.onModified();
	}

	public double getBuffMaxHp() {
		return propertyNormal.getBuffMaxHp();
	}

	public void setBuffMaxHp(double buffMaxHp) {
		double oMaxHp = propertyNormal.getBuffMaxHp();
		propertyNormal.setBuffMaxHp(buffMaxHp);
		this.human.addMaxHp(buffMaxHp - oMaxHp);
		this.human.onModified();
	}
	public int getMerit(){
		return propertyNum.getInt(HumanNumProperty.MERIT);
	}
	public void setMerit(int merit){
		propertyNum.setInt(HumanNumProperty.MERIT, merit);
		this.human.onModified();
	}
	
	public int getChargeAmount(){
		return propertyNum.getInt(HumanNumProperty.CHARGE_AMOUNT);
	}
	
	public void setChargeAmount(int chargeAmount){
		propertyNum.setInt(HumanNumProperty.CHARGE_AMOUNT, chargeAmount);
		this.human.onModified();
	}

	public HumanNormalProperty getPropertyNormal() {
		return propertyNormal;
	}

	public HumanNumProperty getPropertyNum() {
		return propertyNum;
	}

	public void setPropertyNum(HumanNumProperty propertyNum) {
		this.propertyNum = propertyNum;
	}
	public void setPersonSign(String psersonSign){
		propetyStr.setString(HumanStrPropety.PERSONSIGN, psersonSign);
		this.human.onModified();
	}
	public String getPersonSign(){
		return this.propetyStr.getString(HumanStrPropety.PERSONSIGN);
	}
	
	public void setFamilyRole(int familyRole){
		propertyNum.setInt(HumanNumProperty.FAMILY_ROLE, familyRole);
	}
	
	public int getFamilyRole(){
		return propertyNum.getInt(HumanNumProperty.FAMILY_ROLE);
	}
	
	public void setFamilyName(String familyName){
		propetyStr.setString(HumanStrPropety.FAMILY_NAME, familyName);
	}
	
	public String getFamilyName(){
		return propetyStr.getString(HumanStrPropety.FAMILY_NAME);
	}
	
	public int getHunshiNum() {
		return propertyNum.getInt(HumanNumProperty.HUNSHI);
	}

	public void setHunshiNum(int hunshiNum) {
		propertyNum.setInt(HumanNumProperty.HUNSHI, hunshiNum);
		human.onModified();
	}
	
	public void setBuffVip(int vip){
		propertyNum.setInt(HumanNumProperty.BUFF_VIP, vip);
		human.onModified();
	}
	
	public int getBuffVip(){
		return propertyNum.getInt(HumanNumProperty.BUFF_VIP);
	}
	
	public String getOpenFunc(){
		return propetyStr.getString(HumanStrPropety.OPEN_FUNCTION);
	}
	public void setOpenFunc(String func){
		propetyStr.setString(HumanStrPropety.OPEN_FUNCTION, func);
		human.onModified();
	}
}
