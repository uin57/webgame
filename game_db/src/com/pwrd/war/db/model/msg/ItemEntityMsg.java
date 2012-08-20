package com.pwrd.war.db.model.msg;
import com.pwrd.war.core.msg.BaseEntityMsg;
/**
 * This is an auto generated source,please don't modify it.
 * 
 * @author com.pwrd.war.tools.msg.EntityMsgGenerator
 */
public final class ItemEntityMsg extends BaseEntityMsg<com.pwrd.war.db.model.ItemEntity>{
	private com.pwrd.war.db.model.ItemEntity  _entity;
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getEntityClass(){
		return com.pwrd.war.db.model.ItemEntity.class;	
	}
	
	
	@Override
	public com.pwrd.war.db.model.ItemEntity getEntity() {
		return _entity;
	}
	
	@Override
	public void setEntity(com.pwrd.war.db.model.ItemEntity entity) {
		this._entity = entity;
	}
	
	@Override
	public  void initEntity(){
		if(this._entity ==null){
			this._entity = new com.pwrd.war.db.model.ItemEntity();
		}else{
			throw new IllegalStateException("The entity is set.");
		}
	}
	
	@Override
	protected final boolean _read(final byte sequence) {
		switch (sequence) {


		    	case 1:{
	     _entity.setProperties(_readString());
		 return true;
	}
			    	case 2:{
	     _entity.setId(_readString());
		 return true;
	}
					case 3:{
		long _time = _readLong();
					if(_time >0 ){
				_entity.setCreateTime( new java.sql.Timestamp(_time));
			}
				return true;
	}
			    	case 4:{
	     _entity.setCharId(_readString());
		 return true;
	}
			    	case 6:{
	     _entity.setBagId(_readInt());
		 return true;
	}
			    	case 7:{
	     _entity.setBagIndex(_readInt());
		 return true;
	}
			    	case 8:{
	     _entity.setTemplateId(_readInt());
		 return true;
	}
			    	case 9:{
	     _entity.setOverlap(_readInt());
		 return true;
	}
			    	case 10:{
	     _entity.setBind(_readInt());
		 return true;
	}
					case 11:{
		long _time = _readLong();
					if(_time >0 ){
				_entity.setDeadline( new java.sql.Timestamp(_time));
			}
				return true;
	}
			    	case 12:{
	     _entity.setDeleted(_readInt());
		 return true;
	}
					case 13:{
		long _time = _readLong();
					if(_time >0 ){
				_entity.setDeleteDate( new java.sql.Timestamp(_time));
			}
				return true;
	}
			    	case 14:{
	     _entity.setCurEndure(_readInt());
		 return true;
	}
			    	case 15:{
	     _entity.setCoolDownTimePoint(_readLong());
		 return true;
	}
			    	case 16:{
	     _entity.setStar(_readInt());
		 return true;
	}
	
		}
		return false;
	}	
	
	@Override
	protected final boolean _write(){



						_writeString(1,_entity.getProperties());
									_writeString(2,_entity.getId());
						if(_entity.getCreateTime()!=null){
	_writeLong(3,_entity.getCreateTime().getTime());
}else{
	_writeLong(3,0);
}	
							_writeString(4,_entity.getCharId());
									_writeInt(6,_entity.getBagId());
									_writeInt(7,_entity.getBagIndex());
									_writeInt(8,_entity.getTemplateId());
									_writeInt(9,_entity.getOverlap());
									_writeInt(10,_entity.getBind());
						if(_entity.getDeadline()!=null){
	_writeLong(11,_entity.getDeadline().getTime());
}else{
	_writeLong(11,0);
}	
							_writeInt(12,_entity.getDeleted());
						if(_entity.getDeleteDate()!=null){
	_writeLong(13,_entity.getDeleteDate().getTime());
}else{
	_writeLong(13,0);
}	
							_writeInt(14,_entity.getCurEndure());
									_writeLong(15,_entity.getCoolDownTimePoint());
									_writeInt(16,_entity.getStar());
			
		return true;
	}
	
	@Override
	public final short getType() {
		return EntityMessageType.ItemEntityMsg;
	}


	@Override
	public final String getTypeName() {
		return "ItemEntityMsg";
	}
}