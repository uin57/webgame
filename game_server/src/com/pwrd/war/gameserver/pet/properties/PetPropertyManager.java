package com.pwrd.war.gameserver.pet.properties;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.properties.RolePropertyManager;


public class PetPropertyManager extends RolePropertyManager{
	
	/** 整型属性 */
	protected PetIntProperty petIntProperty;
	
	/** 字符串型属性 */
	protected PetStrProperty petStrProperty;
	
	protected PetNormalProperty petNormalProperty; 
	
	private Pet owner;
	public PetPropertyManager(Pet role)
	{	
		this.owner = role; 
		this.petIntProperty = new PetIntProperty();
		this.petStrProperty = new PetStrProperty();
		this.petNormalProperty = new PetNormalProperty();
	}
	
	public int getStar() {
		return petIntProperty.getInt(PetIntProperty.STAR);
	}

	public void setStar(int star) {
		petIntProperty.set(PetIntProperty.STAR, star);
		this.owner.setModified();
	}
	
	public int getGrowType() {
		return petIntProperty.getInt(PetIntProperty.GROW_TYPE);
	}

	public void setGrowType(int growType) {
		petIntProperty.set(PetIntProperty.GROW_TYPE, growType);
		this.owner.setModified();
	}
	
	public int getMood() {
		return petIntProperty.getInt(PetIntProperty.MOOD);
	}
	
	public void setMood(int mood) {
		petIntProperty.set(PetIntProperty.MOOD, mood);
		this.owner.setModified();
	}
	
	public int getBaseGrow() {
		return petIntProperty.getInt(PetIntProperty.BASE_GROW);
	}

	public void setBaseGrow(int baseGrow) {
		petIntProperty.set(PetIntProperty.BASE_GROW, baseGrow);
		this.owner.setModified();
	}

	public int getSpecialGrow() {
		return petIntProperty.getInt(PetIntProperty.SPECIAL_GROW);
	}

	public void setSpecialGrow(int specialGrow) {
		petIntProperty.set(PetIntProperty.SPECIAL_GROW, specialGrow);
		this.owner.setModified();
	}
	 
	
	public boolean getIsInBattle(){
		return petIntProperty.getInt(PetIntProperty.ISIN_BATTLE) == 1;
	}
	
	public void setIsInBattle(boolean rs){
		petIntProperty.set(PetIntProperty.ISIN_BATTLE, rs?1:0); 
		this.owner.setModified();
	}
	
	 
 
	@Override
	public boolean isNumchanged() { 
		return petIntProperty.isChanged();
	}


	@Override
	public boolean isStrchanged() { 
		return petStrProperty.isChanged();
	}

 

	@Override
	public void resetChanged() {
		petStrProperty.resetChanged();
		petIntProperty.resetChanged(); 
	}

 
 

	@Override
	public List<KeyValuePair<Integer, Integer>>  getChangedNum() {
		KeyValuePair<Integer, Object>[]  v = petIntProperty.getChanged();
		List<KeyValuePair<Integer, Integer>>  _changed = new ArrayList<KeyValuePair<Integer, Integer>>();
		for (int i = 0; i < v.length; i++) { 
			_changed.add( new KeyValuePair<Integer, Integer>(
				v[i].getKey(),
				(Integer)v[i].getValue()));
			  
		}
		return _changed;	
	}


	@Override
	public List<KeyValuePair<Integer, String>>  getChangedString() {
		KeyValuePair<Integer, Object>[]  v = petStrProperty.getChanged();
		List<KeyValuePair<Integer, String>> _strChanged = new ArrayList<KeyValuePair<Integer, String>>();
		for (int i = 0; i < v.length; i++) {
			_strChanged.add(new KeyValuePair<Integer, String>(
					v[i].getKey(),
					v[i].getValue().toString()));
		}
		return _strChanged;		
	}
 

	public double getBuffAtk() {
		return petNormalProperty.getBuffAtk();
	}

	public void setBuffAtk(double buffAtk) {
		double oatk = petNormalProperty.getBuffAtk();
		petNormalProperty.setBuffAtk(buffAtk);
		this.owner.addAtk(buffAtk - oatk);
		this.owner.setModified();
	}

	public double getBuffDef() {
		return petNormalProperty.getBuffDef();
	}

	public void setBuffDef(double buffDef) {
		double odef = petNormalProperty.getBuffDef();
		petNormalProperty.setBuffDef(buffDef);
		this.owner.addDef(buffDef - odef);
		this.owner.setModified();
	}

	public double getBuffMaxHp() {
		return petNormalProperty.getBuffMaxHp();
	}

	public void setBuffMaxHp(double buffMaxHp) {
		double oMaxHp = petNormalProperty.getBuffMaxHp();
		petNormalProperty.setBuffMaxHp(buffMaxHp);
		this.owner.addMaxHp(buffMaxHp - oMaxHp);
		this.owner.setModified();
	}

	public PetIntProperty getPetIntProperty() {
		return petIntProperty;
	}

	public void setPetIntProperty(PetIntProperty petIntProperty) {
		this.petIntProperty = petIntProperty;
	}

	public PetNormalProperty getPetNormalProperty() {
		return petNormalProperty;
	}

	public void setPetNormalProperty(PetNormalProperty petNormalProperty) {
		this.petNormalProperty = petNormalProperty;
	}
	 
}
