package com.pwrd.war.gameserver.common.i18n;

import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;

public class LangServiceImpl implements LangService{

	@Override
	public String read(Integer key) { 
		return "[\"#sys_lang_"+key+"\"]";
	}

	/* 
	 * ["#key",[intvalue,'string value', this]]
	 */
	@Override
	public String read(Integer key, Object... params) { 
		StringBuffer sb = new StringBuffer();
		sb.append("[\"#");
		sb.append("sys_lang_"+key);
		sb.append("\"");
		if(params != null && params.length > 0){
			sb.append(",[");
			for(int i = 0; i< params.length; i++){
				if(params[i] instanceof String && !((String)params[i]).startsWith("[\"#")){
					sb.append("\""+params[i]+"\"");
				}else{
					sb.append(params[i]);
				}
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}
		sb.append("]");
		
		return sb.toString();
	}
	public String readFromTemplate(String templateValue, Object... params){
		StringBuffer sb = new StringBuffer();
		sb.append("[\"#");
		sb.append(templateValue.substring(3, templateValue.length() - 2));
		sb.append("\"");
		if(params != null && params.length > 0){
			sb.append(",[");
			for(int i = 0; i< params.length; i++){
				if(params[i] instanceof String && !((String)params[i]).startsWith("[\"#")){
					sb.append("\""+params[i]+"\"");
				}else{
					sb.append(params[i]);
				}
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}
		sb.append("]");
		
		return sb.toString();
	}
	public void test() throws Exception{
		String t  = "";
		t = read(CommonLangConstants_10000.CURRENCY_NAME_GOLD);
//		JSONArray ar = JSONArray.fromObject(t);
		System.out.println(t);
		t = read(CommonLangConstants_10000.CURRENCY_NAME_GOLD, 1,2,3);
		System.out.println(t);
		t = read(CommonLangConstants_10000.CURRENCY_NAME_GOLD, "1",2,3);
//		JSONArray ar1 = JSONArray.fromObject(t);
		System.out.println(t);
		t = read(CommonLangConstants_10000.CURRENCY_NAME_GOLD, 
				read(CommonLangConstants_10000.CURRENCY_NAME_GOLD),
				read(CommonLangConstants_10000.CURRENCY_NAME_GOLD,3,4),
				2,3);
//		JSONArray ar2 = JSONArray.fromObject(t);
		System.out.println(t);
		t = readFromTemplate("[\"#secretshop_0_notice_30\"]", "sdaf",123);
		System.out.println(t);
	}
	
	public static void main(String[] args)throws Exception{
		new LangServiceImpl().test();
	}

}
