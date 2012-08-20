package com.pwrd.war.robot;

import com.pwrd.war.robot.strategy.impl.ArenaStrategy;
import com.pwrd.war.robot.strategy.impl.BattleStrategy;
import com.pwrd.war.robot.strategy.impl.BossStrategy;
import com.pwrd.war.robot.strategy.impl.CharMoveStrategy;
import com.pwrd.war.robot.strategy.impl.CreateFamilyStrategy;
import com.pwrd.war.robot.strategy.impl.CreateRoleTestStrategy;
import com.pwrd.war.robot.strategy.impl.GMCmdTestStrategy;
import com.pwrd.war.robot.strategy.impl.RobberyStrategy;



public class RobotMain {
	
	public static void main(String[] args) {
		
		/**
		 * 配置基本设置
		 */
		int robotIdStart = 1;
		int robotCount = 51;
		String targetServerIp = "localhost";
//		String targetServerIp = "10.2.4.124";
		int port =  8084;
		// 完整测试
		//createActiveRole(robotIdStart, robotCount, targetServerIp, port);
		completeTest(robotIdStart, robotCount, targetServerIp, port);
		try {
			System.out.println("press any key to continue ...");
			System.in.read();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

	/**
	 * 创建活跃用户：加武将，加钱，加经验，加道具，每10个用户创建1个家族，互加好友,开启所有功能
	 * @param robotIdStart
	 * @param robotCount
	 * @param targetServerIp
	 * @param port
	 */
	private static void createActiveRole(int robotIdStart, int robotCount, String targetServerIp, int port){
		int every = 1;
		for (int i = robotIdStart; i < robotIdStart + robotCount; i++) {
			int delay = i * 10;
			Robot robot = new Robot("key" + i, i, targetServerIp, port);
			robot.addRobotStrategy(new CreateRoleTestStrategy(robot, 1000 + delay));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!addpet 10001"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!addcharge 1000000"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!addmoney 1 1000000"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10012001 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10022002 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10032003 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10042004 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10052005 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!additem 10062006 1"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!addlevel 40"));
			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!openfunc -1"));
			if(every == 1){
				robot.addRobotStrategy(new CreateFamilyStrategy(robot, 1000 + delay));
			}
			if(every == 10){
				every = 0;
			}
			every ++;
			robot.start();
			robot.doEnterScene();
		}

	}
	/**
	 * 完整测试, 移动，押镖，接任务，战斗,修炼,南蛮入侵活动
	 * 
	 * @param robotIdStart
	 * @param robotCount
	 * @param targetServerIp
	 * @param port
	 * 
	 */
	private static void completeTest(
		int robotIdStart, int robotCount, String targetServerIp, int port) {
		
		for (int i = robotIdStart; i < robotIdStart + robotCount; i++) {
			int size = 0;
			int delay = i * 10;
			Robot robot = new Robot("key" + i, i, targetServerIp, port);
			robot.addRobotStrategy(new CreateRoleTestStrategy(robot, 1000 + delay));
			robot.addRobotStrategy(new CharMoveStrategy(robot, 1000 + delay,10000));
//			robot.addRobotStrategy(new GMCmdTestStrategy(robot, 1000 + delay,"!givedailyquest"));
			robot.addRobotStrategy(new RobberyStrategy(robot, 10000 * size + delay));
//			robot.addRobotStrategy(new QuestAcceptStrategy(robot, 4000, 111000002));
//			robot.addRobotStrategy(new QuestFinishStrategy(robot, 5000, 111000002));
//			robot.addRobotStrategy(new StartXiuLianStrategy(robot, 1000 + delay)); 
			robot.addRobotStrategy(new ArenaStrategy(robot, 1000 + delay));
			robot.addRobotStrategy(new BattleStrategy(robot, 1000 + delay));
			robot.addRobotStrategy(new BossStrategy(robot, 1000 + delay));
			robot.start();
			robot.doEnterScene();
			size ++;
		}
	}

	/**
	 * 创建机器人
	 * 
	 * @param robotId
	 * @param targetServerIp
	 * @param port
	 * @return
	 */
	private static Robot createRobot(
		int robotId, String targetServerIp, int port) {
		return new Robot("robot" + robotId, robotId, targetServerIp, port);
	}
}
