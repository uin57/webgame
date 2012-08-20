package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_guide")
public class PlayerGuideEntity implements BaseEntity<String> {

	private static final long serialVersionUID = 4130843481786303246L;

	private String id;
 

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
	private String playerUUID;
	//已经完成的id
	private String ids;

	@Column(length = 20480)
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}
	 
	
}
