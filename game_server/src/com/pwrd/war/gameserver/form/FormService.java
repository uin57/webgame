package com.pwrd.war.gameserver.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.db.model.FormEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.i18n.constants.SkillLangConstants_80000;
import com.pwrd.war.gameserver.form.msg.CGFormPosition;
import com.pwrd.war.gameserver.form.msg.GCForm;
import com.pwrd.war.gameserver.form.template.FormSnTemplate;
import com.pwrd.war.gameserver.form.template.FormTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.Player;

public class FormService {
	//全部阵型模板，按照职业各自分开保存
	private Map<VocationType, List<FormTemplate>> formMap = new HashMap<VocationType, List<FormTemplate>>();
	
	//全部阵型模板，按照模板id保存
	private Map<Integer, FormTemplate> formMapById = new HashMap<Integer, FormTemplate>();
	
	//全部阵型位置效果模板，按照位置保存
	private Map<Integer, FormSnTemplate> formSnMap = new HashMap<Integer, FormSnTemplate>();

	public FormService() {
		//加载阵型模板，按照职业和等级排序
		List<FormTemplate> temps = new ArrayList<FormTemplate>();
		for (FormTemplate temp : Globals.getTemplateService().getAll(FormTemplate.class).values()) {
			temps.add(temp);
		}
		Collections.sort(temps, new Comparator<FormTemplate>() {
			@Override
			public int compare(FormTemplate t1, FormTemplate t2) {
				//职业从小到大排列，如果职业相同按照级别从小到大排列
				if (t1.getVocation() == t2.getVocation()) {
					return t1.getLevel() - t2.getLevel();
				} else {
					return t1.getVocation() - t2.getVocation();
				}
			}
			
		});
		
		//将排序后的模板放入map中
		for (FormTemplate temp : temps) {
			VocationType vocation = VocationType.getByCode(temp.getVocation());
			if (formMap.containsKey(vocation)) {
				List<FormTemplate> list = formMap.get(vocation);
				list.add(temp);
			} else {
				List<FormTemplate> list = new ArrayList<FormTemplate>();
				list.add(temp);
				formMap.put(vocation, list);
			}
			
			formMapById.put(temp.getId(), temp);
		}
		
		//加载阵型位置效果模板
		for (FormSnTemplate temp : Globals.getTemplateService().getAll(FormSnTemplate.class).values()) {
			formSnMap.put(temp.getPosition(), temp);
		}
	}

	/**
	 * 根据阵型位置获取对应的效果模板
	 * @param position
	 * @return
	 */
	public FormSnTemplate getFormSnTemplate(int position) {
		return formSnMap.get(position);
	}
	
	/**
	 * 根据阵型id获取阵型模板
	 * @param tid
	 * @return
	 */
	public FormTemplate getFormTemplateById(int tid) {
		return formMapById.get(tid);
	}
	
	/**
	 * 根据用户职业和当前等级获取不高于用户等级的最高级的阵型模板
	 * @param vocation
	 * @param level
	 * @return
	 */
	private FormTemplate getFormTemplate(VocationType vocation, int level) {
		List<FormTemplate> list = formMap.get(vocation);
		if (list != null) {
			FormTemplate lastTemp = null;
			for (FormTemplate temp : list) {
				if (temp.getLevel() <= level) {
					lastTemp = temp;
				} else {
					return lastTemp;
				}
			}
			//如果用户级别大于所有模板则返回最后一个模板
			return lastTemp;
		}
		return null;
	}
	
	/**
	 * 角色初始化阵型，将自身放入阵法中
	 * @param human
	 * @param temp
	 * @return
	 */
	private BattleForm initBattleForm(Human human, FormTemplate temp) {
		//初始化时将玩家放入阵型配置的第一个开启位置中
		String[] positions = new String[BattleForm.TotalPositions];
		int initPos = temp.getOpenPositions().get(0);
		positions[initPos - 1] = human.getUUID();
		
		//构造阵型对象并保存到数据库中
		BattleForm form = new BattleForm(human);
		form.setFormSn(temp.getId());
		form.setOwner(human);
		form.setPositions(positions);
		FormEntity entity = form.toEntity();
		Globals.getDaoService().getFormDao().save(entity);
		form.setDbId(entity.getId());
		form.setInDb(true);
		
		//设置角色阵型
		human.setBattleForm(form);
		return form;
	}

	/**
	 * 获取用户当前阵型
	 * @param player
	 */
	public void getForm(Player player) {
		//获取用户当前对应的阵型配置，找不到配置直接返回错误
		Human human = player.getHuman();
		FormTemplate template = getFormTemplate(human.getVocationType(), human.getLevel());
		if (template == null) {
			player.sendErrorPromptMessage(SkillLangConstants_80000.TEMPLATE_NOT_FOUND);
			return;
		}
		
		//获取用户当前阵法，找不到则初始化阵法
		BattleForm form = human.getBattleForm();
		if (form == null) {
			form = initBattleForm(human, template);
		}
		String[] positions = form.getPositions();
		
		//根据当前阵法和阵法配置构造返回消息
		List<FormPosition> list = new ArrayList<FormPosition>();
		for (int open : template.getOpenPositions()) {
			if (open > 0 && open <= positions.length && StringUtils.isNotEmpty(positions[open - 1])) {
				list.add(new FormPosition(open, positions[open - 1]));
			} else {
				list.add(new FormPosition(open, "-1"));
			}
		}
		
		//返回初始化消息
		GCForm msg = new GCForm();
		msg.setFormPositions(list.toArray(new FormPosition[0]));
		msg.setMaxPos(template.getMaxPos());
		player.sendMessage(msg);
	}
	
	/**
	 * 根据客户端提供的阵型位置修改用户阵型
	 * @param player
	 * @param message
	 */
	public void modifyFormOrder(Player player, CGFormPosition message) {
		//获取用户当前对应的阵型配置，找不到配置直接返回错误
		Human human = player.getHuman();
		FormTemplate template = getFormTemplate(human.getVocationType(), human.getLevel());
		if (template == null) {
			player.sendErrorPromptMessage(SkillLangConstants_80000.TEMPLATE_NOT_FOUND);
			return;
		}
		
		//获取用户当前阵法，找不到返回错误
		BattleForm form = human.getBattleForm();
		if (form == null) {
			player.sendErrorPromptMessage(SkillLangConstants_80000.TEMPLATE_NOT_FOUND);
			return;
		}
		
		//获取用户提供的阵型站位信息，判断是否都站在有效位置上，是否有玩家，是否存在重复id
		String[] positions = new String[BattleForm.TotalPositions];
		int totalPos = 0;
		boolean hasHuman = false;
		Map<String, String> allPets = new HashMap<String, String>();
		for (FormPosition position : message.getPetPositions()) {
			//初步判断信息有效，即位置上的id不为空，并且位置在阵法规定的范围内
			String petSn = position.getPetSn();
			int formPos = position.getPosition();
			if (StringUtils.isNotEmpty(petSn) && formPos > 0 && formPos <= positions.length) {
				//判断是否存在重复id或重复位置
				if (allPets.containsKey(petSn) || StringUtils.isNotEmpty(positions[formPos - 1])) {
					player.sendErrorPromptMessage(SkillLangConstants_80000.DUPLICATE_PET);
					return;
				}
				
				//判断是否为玩家本身
				if (!hasHuman) {
					hasHuman = petSn.equals(human.getUUID());
				}
				
				//判断是否为玩家所属角色，如果是则放入阵法信息中
				if (human.getRole(petSn) != null) {
					positions[formPos - 1] = petSn;
					allPets.put(petSn, petSn);
					totalPos ++;
				}
			}
		}
		
		//判断阵型中总人数是否超过阵法最大人数限制
		if (totalPos > template.getMaxPos()) {
			player.sendErrorPromptMessage(SkillLangConstants_80000.OUT_OF_MAX_POSITIONS);
			return;
		}
		
		//如果玩家不在阵型中返回错误
		if (!hasHuman) {
			player.sendErrorPromptMessage(SkillLangConstants_80000.NO_PLAYER_IN_FORM);
			return;
		}
		
		//保存阵法信息
		form.setPositions(positions);
		form.setModified();
		
		//更新玩家武将是否在阵型的状态
		for (Pet pet : human.getPetManager().getPets()) {
			if (form.isBattle(pet.getUUID())) {
				pet.getPropertyManager().setIsInBattle(true);
				pet.snapChangedProperty(true);
			} else {
				pet.getPropertyManager().setIsInBattle(false);
				pet.snapChangedProperty(true);
			}
		}
	}

}
