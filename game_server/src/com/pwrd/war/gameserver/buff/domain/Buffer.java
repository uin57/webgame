package com.pwrd.war.gameserver.buff.domain;

import java.util.Map;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.BufferEntity;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.msg.GCBuffChangeMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;

public class Buffer implements PersistanceObject<String, BufferEntity> {

	/** 是否已经变更了 */
	private boolean modified;

	/** 物品的生命期的状态 */
	private final LifeCycle lifeCycle;

	protected int bigType;

	/** 此实例是否在db中 */
	private boolean isInDb;

	private String id;
	/** 所有者 */
	private Human owner;
	/** bufferId */
	private String bufferSn;
	/** buffer名称 */
	private String bufferName;
	/** buffer类型 */
	private int bufferType;
	/** 使用的次数 */
	private int usedNumber;
	/** buffer持续时间 */
	private long bufferTime;
	/** 开始时间 */
	private long startTime;
	/** 结束时间 */
	private long endTime;
	/** buffer图标 */
	private String bufferIcon;
	/** buffer图样 */
//	private String bufferImage;
	/** buffer描述 */
	private String bufferDescription;
	/** 数值类型 */
	protected int valueType;
	/** 数值 */
	protected double value;
	/** 附加值 **/
	private String ext;
	
	public Buffer() {
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
	}

	public Buffer(Human owner) {
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
		this.owner = owner;

	}

	public boolean addBuffer(Human human) {
		Map<BuffBigType, Buffer> buffers = human.getBuffers();
//		BufferType bufferType = BufferType.getBufferTypeById(getBufferType());
		if (buffers.containsKey(getBuffBigType())) {			
			human.sendErrorMessage("已经有相同buff");
			return false;
		}/* else if (getBigType() == BigType.system.getId()) {
			if (buffers.get(bufferType) != null) {
				human.sendMessage(generatorMessage(human, ModifyType.delete));
			}
		}*/
		BufferEntity entity = this.toEntity();
		Globals.getDaoService().getBufferDao().save(entity);
		this.setId(entity.getId());
		this.setInDb(true);
		buffers.put(getBuffBigType(), this);
		human.sendMessage(generatorMessage(human, ModifyType.add));
		return true;
	}

	protected void remove(Human human) {
	}
	
	public void update(Human human) {
	}
	public void updateThisRole(Role role) {
	}
	public GCBuffChangeMessage generatorMessage(Human human, ModifyType type) {
		if (type == ModifyType.delete) {
			remove(human);
			human.getBuffers().remove(this.getBuffBigType());
			onDelete();
		} else if (type == ModifyType.change) {
			try {
				setModified();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		GCBuffChangeMessage msg = new GCBuffChangeMessage();
		msg.setModifyType(type.getId());
		msg.setBuff(this);
		return msg;
	}

	/**
	 * @param human
	 * @param number
	 * @param time
	 * @return
	 */
	public double modify(Role role, int number) {
		return 0;
	}

	public double getValue(double value) {
		return 0;
	}

	/**
	 * 判断当前buffer是否在buffers中
	 * 
	 * @param buffers
	 * @return
	 */
	protected boolean isContain(Map<BuffBigType, Buffer> bufferMap) {
		return bufferMap.containsKey(this.getBuffBigType());
	}

	@Override
	public void setDbId(String id) {
		this.id = id;
	}

	@Override
	public String getDbId() {
		return id;
	}

	@Override
	public String getGUID() {
		return id;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
	}

	@Override
	public String getCharId() {
		return owner == null ? "" : owner.getCharId();
	}

	@Override
	public BufferEntity toEntity() {
		BufferEntity entity = new BufferEntity();
		entity.setBufferSn(getBufferSn());
		entity.setHumanSn(owner.getUUID());
		entity.setId(getId());
		entity.setBufferNumber(getUsedNumber());
		entity.setBufferTime(getBufferTime());
		entity.setBufferValue(getValue());
		entity.setStartTime(getStartTime());
		entity.setEndTime(getEndTime());
		entity.setExt(getExt());
		entity.setBufferName(getBufferName());
		entity.setBufferDescription(getBufferDescription());
		return entity;
	}

	@Override
	public void fromEntity(BufferEntity entity) {
		throw new RuntimeException();
		// BufferTemplate bufferTemplate =
		// Globals.getTemplateService().get(Integer.parseInt(entity.getBufferSn()),
		// BufferTemplate.class);
		// Globals.getBufferService().getInstanceBuffer(entity.getBufferSn());
		// setId(entity.getId());
		// setBufferSn(entity.getBufferSn());
		// setBufferDescription(bufferTemplate.getBufferDescription());
		// setBufferIcon(bufferTemplate.getBufferIcon());
		// setBufferImage(bufferTemplate.getBufferImage());
		// setBufferName(bufferTemplate.getBufferName());
		// setBufferTime(entity.getBufferTime());
		// setBufferType(bufferTemplate.getBuffType());
		// setUsedNumber(entity.getBufferNumber());
	}

	public static Buffer generatorBuffer(BufferEntity entity) {
		Buffer buffer = Globals.getBufferService().getInstanceBuffer(
				entity.getBufferSn(), entity.getStartTime());
		buffer.setUsedNumber(entity.getBufferNumber());
		buffer.setValue(entity.getBufferValue());
		buffer.setId(entity.getId());
		buffer.setExt(entity.getExt());
		buffer.setBufferName(entity.getBufferName());
		buffer.setBufferDescription(entity.getBufferDescription());
		return buffer;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		modified = true;
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
		}
	}

	public void onDelete() {
		modified = true;
		lifeCycle.destroy();
		// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
		owner.getPlayer().getDataUpdater().addDelete(lifeCycle);
	}

	public String getBufferSn() {
		return bufferSn;
	}

	public void setBufferSn(String bufferSn) {
		this.bufferSn = bufferSn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBufferName() {
		return bufferName;
	}

	public void setBufferName(String bufferName) {
		this.bufferName = bufferName;
	}

	public int getUsedNumber() {
		return usedNumber;
	}

	public void setUsedNumber(int usedNumber) {
		this.usedNumber = usedNumber;
	}

	public long getBufferTime() {
		this.bufferTime = (endTime - Globals.getTimeService().now()) / 1000;
		return bufferTime;
	}

	@Deprecated
	public void setBufferTime(long bufferTime) {
		this.bufferTime = bufferTime;
	}

	public String getBufferIcon() {
		return bufferIcon;
	}

	public void setBufferIcon(String bufferIcon) {
		this.bufferIcon = bufferIcon;
	}

//	public String getBufferImage() {
//		return bufferImage;
//	}
//
//	public void setBufferImage(String bufferImage) {
//		this.bufferImage = bufferImage;
//	}

	public String getBufferDescription() {
		return bufferDescription;
	}

	public void setBufferDescription(String bufferDescription) {
		this.bufferDescription = bufferDescription;
	}

	public int getBufferType() {
		return bufferType;
	}

	public void setBufferType(int bufferType) {
		this.bufferType = bufferType;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getBigType() {
		return bigType;
	}
	public BuffBigType getBuffBigType() {
		return BuffBigType.getBufferTypeById(bigType);
	}
	public void setBigType(int bigType) {
		this.bigType = bigType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "Buffer [modified=" + modified + ", lifeCycle=" + lifeCycle
				+ ", bigType=" + bigType + ", isInDb=" + isInDb + ", id=" + id
				+ ", owner=" + owner + ", bufferSn=" + bufferSn
				+ ", bufferName=" + bufferName + ", bufferType=" + bufferType
				+ ", usedNumber=" + usedNumber + ", bufferTime=" + bufferTime
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", bufferIcon=" + bufferIcon  
				+ ", bufferDescription=" + bufferDescription + ", valueType="
				+ valueType + ", value=" + value + ", ext=" + ext + "]";
	}

}
