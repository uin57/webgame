/**
 * 
 */
package com.pwrd.war.robot.msg;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.common.MessageMappingProvider;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGHandshake;
import com.pwrd.war.gameserver.common.msg.GCHandshake;


/**
 * 机器人消息注册器
 * @author dengdan
 *
 */
public class RobotMsgRegister {
	
	public static Map<Short, Class<?>> msgs = new HashMap<Short, Class<?>>();
	public static short[] types;
	
	static{
		init();
		types = new short[msgs.keySet().size()];
		int i = 0;
		for(short type : msgs.keySet()){
			types[i] = type;
			i++;
		}
	}
	
	/**
	 * 注册消息号和消息类的映射
	 * 
	 * @param mappingProvider
	 */
	private static void registerMsgMapping(MessageMappingProvider mappingProvider) {
		msgs.putAll(mappingProvider.getMessageMapping());
	}
	
	public static void init() {
		registerMsgMapping(new MessageMappingProvider(){
			@Override
			public Map<Short, Class<?>> getMessageMapping() {
				Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
				map.put(MessageType.CG_HANDSHAKE, CGHandshake.class);
				map.put(MessageType.GC_HANDSHAKE, GCHandshake.class);
				return map;
			}			
		});
		registerMsgMapping(new CommonMsgMappingProvider());
		registerMsgMapping(new PlayerMsgMappingProvider());
		registerMsgMapping(new SceneMsgMappingProvider());
		registerMsgMapping(new ChatMsgMappingProvider());
		registerMsgMapping(new QuestMsgMappingProvider());
		registerMsgMapping(new ItemMsgMappingProvider());
		registerMsgMapping(new HumanMsgMappingProvider());
		registerMsgMapping(new PetMsgMappingProvider());		
		registerMsgMapping(new MailMsgMappingProvider());	
		registerMsgMapping(new WallowMsgMappingProvider());
		registerMsgMapping(new PrizeMsgMappingProvider());
		registerMsgMapping(new FightMsgMappingProvider());
		registerMsgMapping(new SkillMsgMappingProvider());
		registerMsgMapping(new FormMsgMappingProvider());
		registerMsgMapping(new VocationMsgMappingProvider());
		registerMsgMapping(new MallMsgMappingProvider());
		registerMsgMapping(new RepMsgMappingProvider());
		registerMsgMapping(new BuffMsgMappingProvider());
		registerMsgMapping(new VitalityMsgMappingProvider());
		registerMsgMapping(new TransfervocationMsgMappingProvider());
		registerMsgMapping(new InteractiveMsgMappingProvider());
		registerMsgMapping(new ResearchMsgMappingProvider());
		registerMsgMapping(new QiecuoMsgMappingProvider());
		registerMsgMapping(new MonsterMsgMappingProvider());
		registerMsgMapping(new StoryMsgMappingProvider());		
		registerMsgMapping(new FriendMsgMappingProvider());
		registerMsgMapping(new SecretshopMsgMappingProvider());
		registerMsgMapping(new RobberyMsgMappingProvider());
		registerMsgMapping(new ArenaMsgMappingProvider());
		registerMsgMapping(new ActivityMsgMappingProvider());
		registerMsgMapping(new DaytaskMsgMappingProvider());
		registerMsgMapping(new TowerMsgMappingProvider());
		registerMsgMapping(new FamilyMsgMappingProvider());
		registerMsgMapping(new GiftbagMsgMappingProvider());
		registerMsgMapping(new WarcraftMsgMappingProvider());
		registerMsgMapping(new TreeMsgMappingProvider());
		registerMsgMapping(new PromptbuttonMsgMappingProvider());
	}
	
	public static Map<Short, Class<?>> getMsgs(){
		return msgs;
	}
	public static short[] getTypes(){
		return types;
	}
	
}
