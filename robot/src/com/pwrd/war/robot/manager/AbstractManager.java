package com.pwrd.war.robot.manager;

import com.pwrd.war.robot.Robot;

public class AbstractManager implements IManager{
	
	private Robot owner = null;
	
	public AbstractManager(Robot owner)
	{
		this.owner = owner;
	}

	@Override
	public Robot getOwner() {
		return owner;
	}
	

}
