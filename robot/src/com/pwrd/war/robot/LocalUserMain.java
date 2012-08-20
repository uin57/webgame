package com.pwrd.war.robot;

import com.pwrd.war.robot.strategy.impl.PlayerChargeStrategy;

public class LocalUserMain {
	
	public static void main(String[] args) {
		
		Robot localUser = new Robot("jiliang.lu@opi-corp.com", 1, "192.168.1.17", 8084);		
		localUser.setPassword("password");
		
		localUser.addRobotStrategy(new PlayerChargeStrategy(localUser,5000));
		
		
		localUser.start();
	}

}
