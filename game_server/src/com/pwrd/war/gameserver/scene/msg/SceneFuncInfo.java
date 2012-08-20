package com.pwrd.war.gameserver.scene.msg;


/**
 * 场景功能信息
 * 
 * @author haijiang.jin
 * 
 */
public class SceneFuncInfo {
	/** 标题 */
	private String _title;
	/** 功能类型 */
	private short _typeId;
	/** 索引 */
	private short _index;
	/** 是否可用 */
	private String _available;
	/** 说明 */
	private String _desc;
	/** 关于信息 */
	private String _aboutInfo;

	/**
	 * 类默认构造器
	 * 
	 */
	public SceneFuncInfo() {
	}

	/**
	 * 获取标题
	 * 
	 * @return
	 */
	public String getTitle() {
		return this._title;
	}

	/**
	 * 设置标题
	 * 
	 * @param value
	 */
	public void setTitle(String value) {
		this._title = value;
	}

	/**
	 * 获取类型 Id
	 * 
	 * @return
	 */
	public short getTypeId() {
		return this._typeId;
	}

	/**
	 * 设置类型 Id
	 * 
	 * @param value
	 */
	public void setTypeId(short value) {
		this._typeId = value;
	}

	/**
	 * 获取功能索引
	 * 
	 * @return
	 */
	public short getIndex() {
		return this._index;
	}

	/**
	 * 设置功能索引
	 * 
	 * @param value
	 */
	public void setIndex(short value) {
		this._index = value;
	}

	/**
	 * 获取是否可用
	 * 
	 * @return
	 */
	public String getAvailable() {
		return this._available;
	}

	/**
	 * 设置是否可用
	 * 
	 * @param value
	 */
	public void setAvailable(String value) {
		this._available = value;
	}

	/**
	 * 获取说明
	 * 
	 * @return
	 */
	public String getDesc() {
		return this._desc;
	}

	/**
	 * 设置说明
	 * 
	 * @param value
	 */
	public void setDesc(String value) {
		this._desc = value;
	}

	/**
	 * 获取关于信息
	 * 
	 * @return
	 */
	public String getAboutInfo() {
		return this._aboutInfo;
	}

	/**
	 * 设置关于信息
	 * 
	 * @param value
	 */
	public void setAboutInfo(String value) {
		this._aboutInfo = value;
	}

	@Override
	public String toString() {
		return "SceneFuncInfo [ available = " + getAvailable() 
			+ ", index = " + this.getIndex() 
			+ ", title = " + this.getTitle() 
			+ ", typeId = " + this.getTypeId() 
			+ ", desc = " + this.getDesc()
			+ ", aboutInfo = " + this.getAboutInfo() + "]";
	}
	
}
