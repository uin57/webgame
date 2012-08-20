package com.pwrd.war.gameserver.form.template;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.util.StringUtils;
@ExcelRowBinding
public class FormTemplate extends FormTemplateVO {
	private List<Integer> openPositions = new ArrayList<Integer>();

	@Override
	public void check() throws TemplateConfigException {
		String[] prop = StringUtils.getStringList(positions, ",");
		for (int i = 0; i < prop.length; i++) {
			try {
				int r = Integer.parseInt(prop[i]);
				openPositions.add(r);
			} catch (Exception e) {
			}
		}
	}
	
	public List<Integer> getOpenPositions() {
		return openPositions;
	}
	
}

