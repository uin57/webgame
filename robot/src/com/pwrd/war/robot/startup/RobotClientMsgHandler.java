package com.pwrd.war.robot.startup;

import com.pwrd.war.common.model.item.CommonItem;
import com.pwrd.war.common.model.quest.QuestInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.server.IMessageHandler;
import com.pwrd.war.core.session.MinaSession;
import com.pwrd.war.gameserver.buff.msg.GCBuffChangeMessage;
import com.pwrd.war.gameserver.common.msg.GCHandshake;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedNumber;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedString;
import com.pwrd.war.gameserver.human.msg.GCStartXiulian;
import com.pwrd.war.gameserver.item.msg.GCBagUpdate;
import com.pwrd.war.gameserver.item.msg.GCItemUpdate;
import com.pwrd.war.gameserver.pet.msg.GCPetList;
import com.pwrd.war.gameserver.player.msg.GCEnterScene;
import com.pwrd.war.gameserver.player.msg.GCFailedMsg;
import com.pwrd.war.gameserver.player.msg.GCRoleInfo;
import com.pwrd.war.gameserver.player.msg.GCSceneInfo;
import com.pwrd.war.gameserver.quest.DoingQuest.QuestStatus;
import com.pwrd.war.gameserver.quest.msg.GCQuestList;
import com.pwrd.war.gameserver.quest.msg.GCQuestUpdate;
import com.pwrd.war.gameserver.scene.msg.GCSwitchScene;
import com.pwrd.war.gameserver.story.msg.GCStoryList;
import com.pwrd.war.gameserver.story.msg.GCStoryShow;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.RobotManager;
import com.pwrd.war.robot.msg.RobotMsgRegister;
import com.pwrd.war.robot.strategy.IRobotStrategy;

public class RobotClientMsgHandler implements IMessageHandler<IMessage> {
	
	private Robot getRobot(IMessage message)
	{
		MinaSession minaSession = (MinaSession)((GCMessage)message).getSession();
		return RobotManager.getInstance().getRobot(minaSession);		
	}

	@Override
	public void execute(IMessage message) {
		Robot robot = getRobot(message);
		System.out.println(robot.getPid()+":"+message);
		
		if(message instanceof GCHandshake)
		{
			robot.doLogin();
//			robot.createUser();
		}
		if(message instanceof GCEnterScene)
		{
			robot.doEnterScene();
		}
		else if(message instanceof GCRoleInfo)
		{
			GCRoleInfo gcRoleList = (GCRoleInfo)message;
			robot.handleGCRoleList(robot, gcRoleList);
		}
		else if(message instanceof GCSceneInfo)
		{
			robot.handleGCSceneInfo(robot);
		}
		else if(message instanceof GCBagUpdate)
		{
			robot.getBagManager().init((GCBagUpdate)message);			
		}
		else if (message instanceof GCItemUpdate)
		{
			for(CommonItem c: ((GCItemUpdate) message).getItem()){
				robot.getBagManager().updateItem(c);
			}
		}
		else if (message instanceof GCPropertyChangedNumber)
		{
			robot.handleGCPropertyChangedNumber((GCPropertyChangedNumber) message);
		}
		else if (message instanceof GCPropertyChangedString)
		{
			robot.handleGCPropertyChangedString((GCPropertyChangedString) message);
		}
		else if (message instanceof GCPetList)
		{
			robot.getPetManager().init((GCPetList)message);
		}
		else if (message instanceof GCQuestList){
			GCQuestList list = (GCQuestList) message;
			System.out.println(">>> GCQuestList size=" + list.getQuestInfos().length);
			for (QuestInfo info : list.getQuestInfos()) {
				int questId = info.getQuestId();
				int questStatus = info.getStatus();
				System.out.println(">>> quest id=" + questId + ", status=" + questStatus);
				if (questStatus == QuestStatus.CANACCEPT.getIndex()) {
					System.out.println(">>> can accept id=" + questId);
				} else if (questStatus == QuestStatus.ACCEPTED.getIndex()) {
					System.out.println(">>> can giveup id=" + questId);
				} else if (questStatus == QuestStatus.CANFINISH.getIndex()) {
					System.out.println(">>> can finish id=" + questId);
				} else {
					System.out.println(">>> other status=" + questStatus);
				}
			}
		} else if (message instanceof GCQuestUpdate) {
			GCQuestUpdate update = (GCQuestUpdate) message;
			QuestInfo info = update.getQuestInfo();
			System.out.println(">>> GCQuestUpdate id=" + info.getQuestId() + ", status=" + info.getStatus());
		} else if (message instanceof GCStartXiulian) {
			GCStartXiulian xl = (GCStartXiulian)message;
			System.out.println("xiulian=" + xl.getLeftTime());
		} else if (message instanceof GCStoryList) {
			GCStoryList list = (GCStoryList)message;
			System.out.println("--- story show list size=:" + list.getCanShowList().length);
//			for (int l : list.getCanShowList()) {
//				System.out.println("--- story show list=" + l);
//			}
		} else if (message instanceof GCStoryShow) {
			GCStoryShow show = (GCStoryShow)message;
			System.out.println("--- story show:" + show.getStoryId());
		} else if (message instanceof GCBuffChangeMessage) {
			
		}
		else if(message instanceof GCFailedMsg){
			System.out.println("错误:"+message);
		}
		//处理切换场景
		else if(message instanceof GCSwitchScene){
			GCSwitchScene msg = (GCSwitchScene)message;
			robot.handleGCSwitchScend(msg);
		}
		else
		{
			for(IRobotStrategy strategy : robot.getStrategyList())
			{
				strategy.onResponse(message);	
			}			
		}
	}

	@Override
	public short[] getTypes() {
		return RobotMsgRegister.getTypes();
	}


}
