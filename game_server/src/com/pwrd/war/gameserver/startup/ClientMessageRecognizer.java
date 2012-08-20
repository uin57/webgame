package com.pwrd.war.gameserver.startup;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.MessageMappingProvider;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.recognizer.BaseMessageRecognizer;
import com.pwrd.war.gameserver.activity.msg.ActivityMsgMappingProvider;
import com.pwrd.war.gameserver.arena.msg.ArenaMsgMappingProvider;
import com.pwrd.war.gameserver.buff.msg.BuffMsgMappingProvider;
import com.pwrd.war.gameserver.chat.msg.ChatMsgMappingProvider;
import com.pwrd.war.gameserver.common.msg.CGHandshake;
import com.pwrd.war.gameserver.common.msg.CommonMsgMappingProvider;
import com.pwrd.war.gameserver.dayTask.msg.DaytaskMsgMappingProvider;
import com.pwrd.war.gameserver.family.msg.FamilyMsgMappingProvider;
import com.pwrd.war.gameserver.fight.msg.FightMsgMappingProvider;
import com.pwrd.war.gameserver.form.msg.FormMsgMappingProvider;
import com.pwrd.war.gameserver.friend.msg.FriendMsgMappingProvider;
import com.pwrd.war.gameserver.giftBag.msg.GiftbagMsgMappingProvider;
import com.pwrd.war.gameserver.human.msg.HumanMsgMappingProvider;
import com.pwrd.war.gameserver.interactive.msg.InteractiveMsgMappingProvider;
import com.pwrd.war.gameserver.item.msg.ItemMsgMappingProvider;
import com.pwrd.war.gameserver.mail.msg.MailMsgMappingProvider;
import com.pwrd.war.gameserver.mall.msg.MallMsgMappingProvider;
import com.pwrd.war.gameserver.monster.msg.MonsterMsgMappingProvider;
import com.pwrd.war.gameserver.pet.msg.PetMsgMappingProvider;
import com.pwrd.war.gameserver.player.msg.PlayerMsgMappingProvider;
import com.pwrd.war.gameserver.prize.msg.PrizeMsgMappingProvider;
import com.pwrd.war.gameserver.promptButton.msg.PromptbuttonMsgMappingProvider;
import com.pwrd.war.gameserver.qiecuo.msg.QiecuoMsgMappingProvider;
import com.pwrd.war.gameserver.quest.msg.QuestMsgMappingProvider;
import com.pwrd.war.gameserver.rep.msg.RepMsgMappingProvider;
import com.pwrd.war.gameserver.research.msg.ResearchMsgMappingProvider;
import com.pwrd.war.gameserver.robbery.msg.RobberyMsgMappingProvider;
import com.pwrd.war.gameserver.scene.msg.SceneMsgMappingProvider;
import com.pwrd.war.gameserver.secretShop.msg.SecretshopMsgMappingProvider;
import com.pwrd.war.gameserver.skill.msg.SkillMsgMappingProvider;
import com.pwrd.war.gameserver.story.msg.StoryMsgMappingProvider;
import com.pwrd.war.gameserver.tower.msg.TowerMsgMappingProvider;
import com.pwrd.war.gameserver.transferVocation.msg.TransfervocationMsgMappingProvider;
import com.pwrd.war.gameserver.tree.msg.TreeMsgMappingProvider;
import com.pwrd.war.gameserver.vitality.msg.VitalityMsgMappingProvider;
import com.pwrd.war.gameserver.vocation.msg.VocationMsgMappingProvider;
import com.pwrd.war.gameserver.wallow.msg.WallowMsgMappingProvider;
import com.pwrd.war.gameserver.warcraft.msg.WarcraftMsgMappingProvider;

/**
 * 来自客户端的消息识别器
 * 
 */
public class ClientMessageRecognizer extends BaseMessageRecognizer implements
		InitializeRequired {
	private Map<Short, Class<?>> msgs = new HashMap<Short, Class<?>>();

	public ClientMessageRecognizer() {
		this.init();
	}

	@Override
	public void init() {
		registerMsgMapping(new MessageMappingProvider(){
			@Override
			public Map<Short, Class<?>> getMessageMapping() {
				Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
				map.put(MessageType.CG_HANDSHAKE, CGHandshake.class);
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

	/**
	 * 注册消息号和消息类的映射
	 * 
	 * @param mappingProvider
	 */
	private void registerMsgMapping(MessageMappingProvider mappingProvider) {
		msgs.putAll(mappingProvider.getMessageMapping());
	}

	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException {
		Class<?> clazz = msgs.get(type);
		if (clazz == null) {
			throw new MessageParseException("Unknow msgType:" + type);
		} else {
			try {
				IMessage msg = (IMessage) clazz.newInstance();
				return msg;
			} catch (Exception e) {
				throw new MessageParseException(
						"Message Newinstance Failed，msgType:" + type, e);
			}
		}
	}
	
}
