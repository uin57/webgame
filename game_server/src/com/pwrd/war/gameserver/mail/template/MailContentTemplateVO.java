package com.pwrd.war.gameserver.mail.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 系统邮件内容配置
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class MailContentTemplateVO extends TemplateObject {

	/** 触发原因 */
	@ExcelCellBinding(offset = 1)
	protected int reasonId;

	/** 触发行为 */
	@ExcelCellBinding(offset = 3)
	protected int behaviorId;

	/** 邮件标题多语言 */
	@ExcelCellBinding(offset = 5)
	protected int mailTitleLangId;

	/** 邮件标题多语言 */
	@ExcelCellBinding(offset = 6)
	protected String mailTitle;

	/** 邮件内容多语言 */
	@ExcelCellBinding(offset = 7)
	protected int mailContentLangId;

	/** 邮件内容多语言 */
	@ExcelCellBinding(offset = 8)
	protected String mailContent;


	public int getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(int reasonId) {
		if (reasonId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[触发原因]reasonId不可以为0");
		}
		if (reasonId > 106 || reasonId < 101) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[触发原因]reasonId的值不合法，应为101至106之间");
		}
		this.reasonId = reasonId;
	}
	
	public int getBehaviorId() {
		return this.behaviorId;
	}

	public void setBehaviorId(int behaviorId) {
		if (behaviorId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[触发行为]behaviorId不可以为0");
		}
		if (behaviorId > 3 || behaviorId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[触发行为]behaviorId的值不合法，应为1至3之间");
		}
		this.behaviorId = behaviorId;
	}
	
	public int getMailTitleLangId() {
		return this.mailTitleLangId;
	}

	public void setMailTitleLangId(int mailTitleLangId) {
		this.mailTitleLangId = mailTitleLangId;
	}
	
	public String getMailTitle() {
		return this.mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		if (StringUtils.isEmpty(mailTitle)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[邮件标题多语言]mailTitle不可以为空");
		}
		this.mailTitle = mailTitle;
	}
	
	public int getMailContentLangId() {
		return this.mailContentLangId;
	}

	public void setMailContentLangId(int mailContentLangId) {
		this.mailContentLangId = mailContentLangId;
	}
	
	public String getMailContent() {
		return this.mailContent;
	}

	public void setMailContent(String mailContent) {
		if (StringUtils.isEmpty(mailContent)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[邮件内容多语言]mailContent不可以为空");
		}
		this.mailContent = mailContent;
	}
	

	@Override
	public String toString() {
		return "MailContentTemplateVO[reasonId=" + reasonId + ",behaviorId=" + behaviorId + ",mailTitleLangId=" + mailTitleLangId + ",mailTitle=" + mailTitle + ",mailContentLangId=" + mailContentLangId + ",mailContent=" + mailContent + ",]";

	}
}