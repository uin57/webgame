package com.pwrd.war.gameserver.buff;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.gameserver.buff.domain.AttackBuffer;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.domain.ChangeBuffer;
import com.pwrd.war.gameserver.buff.domain.DefenseBuffer;
import com.pwrd.war.gameserver.buff.domain.ExperienceBuffer;
import com.pwrd.war.gameserver.buff.domain.LifeBuffer;
import com.pwrd.war.gameserver.buff.domain.MedicinalBuffer;
import com.pwrd.war.gameserver.buff.domain.MoneyBuffer;
import com.pwrd.war.gameserver.buff.domain.VipBuffer;
import com.pwrd.war.gameserver.buff.domain.VitalityBuffer;
import com.pwrd.war.gameserver.buff.enums.BufferType;
import com.pwrd.war.gameserver.buff.enums.SkillBufferType;
import com.pwrd.war.gameserver.buff.template.BufferTemplate;
import com.pwrd.war.gameserver.buff.template.StatusTemplate;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.skill.enums.EffectType;

public class BufferService {

	/**
	 * 技能对应的buff
	 */
	private static Map<String, Status> buffMap = new HashMap<String, Status>();

	public Buffer getInstanceBuffer(String bufferSn, long currentTime) {
		BufferTemplate bufferTemplate = Globals.getTemplateService().get(
				Integer.parseInt(bufferSn), BufferTemplate.class);
		Buffer buffer = getBuffer(BufferType.getBufferTypeById(bufferTemplate
				.getBuffType()));
		if (buffer != null) {
			buffer.setBufferDescription(bufferTemplate.getBufferDescription());
			buffer.setBufferIcon(bufferTemplate.getBufferIcon());
//			buffer.setBufferImage(bufferTemplate.getBufferImage());
			buffer.setBufferName(bufferTemplate.getBufferName());
			buffer.setBufferSn(bufferTemplate.getId() + "");

			buffer.setStartTime(currentTime);
			// 配置文件中是分
			buffer.setEndTime(currentTime + bufferTemplate.getTime() * 60
					* 1000);
			buffer.setUsedNumber(bufferTemplate.getNumber());
			buffer.setValue(bufferTemplate.getValue());
			buffer.setValueType(bufferTemplate.getValueType());
			buffer.setBigType(bufferTemplate.getBigType());
			buffer.setBufferType(bufferTemplate.getBuffType());
			return buffer;
		}
		return null;
	}

	private Buffer getBuffer(BufferType bufferType) {
		switch (bufferType) {
		case money:
			return new MoneyBuffer();
		case experience:
			return new ExperienceBuffer();
		case attack:
			return new AttackBuffer();
		case defense:
			return new DefenseBuffer();
		case life:
			return new LifeBuffer();
		case vitality:
			return new VitalityBuffer();
		case medicinal:
			return new MedicinalBuffer();
		case change:
			return new ChangeBuffer();
		case vip:
			return new VipBuffer();
		default:
			return null;
		}
	}

	public BufferService() {
		Map<Integer, StatusTemplate> statusMap = Globals.getTemplateService()
				.getAll(StatusTemplate.class);
		for (StatusTemplate statusTemplate : statusMap.values()) {
			buffMap.put(
					statusTemplate.getStatusSn() + "_"
							+ statusTemplate.getStatusLevel(),
					transfer(statusTemplate));
		}
	}

	private Status transfer(StatusTemplate statusTemplate) {
		Status buff = new Status();
		SkillBufferType bufferType = SkillBufferType
				.getBufferTypeByLabel(statusTemplate.getStatusType());
		buff.setBufferType(bufferType);
		buff.setStatusDescription(statusTemplate.getStatusDescription());
		buff.setStatusLevel(statusTemplate.getStatusLevel());
		buff.setStatusName(statusTemplate.getStatusName());
		buff.setStatusRound(statusTemplate.getStatusRound());
		buff.setStatusSn(statusTemplate.getStatusSn());
		buff.setStatusWeight(statusTemplate.getStatusWeight());
		buff.setEffectType(EffectType.getEffectTypeByLabel(statusTemplate
				.getStatusEffect()));
		buff.setStatusSpecialEffect(statusTemplate.getStatusSpecialEffect());
		buff.setArg1(statusTemplate.getArg1());
		buff.setArg2(statusTemplate.getArg2());
		return buff;
	}

	public static Status getAbstractBuff(String buffSn, int level) {
		return buffMap.get(buffSn + "_" + level);
	}

}
