package com.pwrd.war.gameserver.common.world;


public class WorldService  {
	
	private volatile WorldRunner worldRunner;
	
	public WorldService(World world)
	{
		worldRunner = new WorldRunner(world);
	}

	public WorldRunner getWorldRunner() {
		return worldRunner;
	}
}
