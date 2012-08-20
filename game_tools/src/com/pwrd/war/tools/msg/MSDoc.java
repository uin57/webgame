package com.pwrd.war.tools.msg;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;


public class MSDoc {
	
	private static final Logger logger = Logger.getLogger(MessageGenerator.class);
	
	public static MSWordManager msMgr = new MSWordManager(true);

	public static final String MODEL_DIC = "msg/model/";
	public static final Namespace NAME_SPACE = Namespace.getNamespace("http://com.pwrd.war.message");
	private String[] template = {"类型","字段","说明"};
	private int length = template.length;
	
	
	private String fieldType ;
	private String fieldName ;
	private String fieldComment ;
	private String fieldList ;
	private String fieldMacro ;
	
	private List<Element> macros;
	
	
	static String subject = "消息定义";

	public static void main(String[] args) {
		msMgr.createNewDocument();
		MSDoc msdoc = new MSDoc();
		msdoc.createMacros("macros.xml");
		msdoc.createMessageFiles("battle_message.xml");
		msdoc.createMessageFiles("chat_message.xml");
		msdoc.createMessageFiles("common_message.xml");
		msdoc.createMessageFiles("human_message.xml");
		msdoc.createMessageFiles("item_message.xml");
		msdoc.createMessageFiles("pet_message.xml");
		msdoc.createMessageFiles("player_message.xml");
		msdoc.createMessageFiles("scene_message.xml");
		
		msMgr.save("C:\\"+subject+".doc");
	}
	
	@SuppressWarnings("unchecked")
	public void createMacros(String modelFileName) {
		try {
			String configFilePath = GeneratorHelper.getBuildPath(MODEL_DIC
					+ modelFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			String module = root.getAttributeValue("module");// 所属模块
			msMgr.setFont(true, false, false, "000000", "16", "宋体");
			msMgr.putTxt("Macros 消息定义\r\n");
			msMgr.moveEnd();
			
			
			macros = (List<Element>)root.getChildren("macro", NAME_SPACE);// 消息体定义

			createFiles(macros, module);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void createMessageFiles(String modelFileName) {
		try {
			String configFilePath = GeneratorHelper.getBuildPath(MODEL_DIC
					+ modelFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			String module = root.getAttributeValue("module");// 所属模块
			msMgr.setFont(true, false, false, "000000", "16", "宋体");
			msMgr.putTxt(module+"消息定义\r\n");
			msMgr.moveEnd();
			List<Element> messages = (List<Element>)root.getChildren("message", NAME_SPACE);// 消息体定义

			createFiles(messages, module);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void createMacroFiles(String modelFileName) {
		try {
			String configFilePath = GeneratorHelper.getBuildPath(MODEL_DIC
					+ modelFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			macros = (List<Element>)root.getChildren("macro", NAME_SPACE);// 消息体定义
			for (Iterator<Element> i = macros.iterator(); i.hasNext();){
				Element macroElement = (Element) i.next();
				if(macroElement.getAttributeValue("id").equals(fieldMacro)){
					List<Element> fElements = (List<Element>)macroElement.getChildren("field", NAME_SPACE);
					
					for (Iterator<Element> j = fElements.iterator(); j.hasNext();){
						int cols;
						cols=2;
						Element fieldElement = (Element) j.next();
						fieldType = fieldElement.getAttributeValue("type");
						fieldName = fieldElement.getAttributeValue("name");
						fieldComment = fieldElement.getAttributeValue("comment");
						
						if(!(fieldType==null&&fieldName==null&&fieldComment==null)){
							msMgr.moveEnd();
							msMgr.addLastTableRow(msMgr.getTableCount());
							msMgr.setFont(false, true, false, "000000", "9", "Times New Roman");
							msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldName);
							msMgr.setFont(false, true, false, "000000", "9", "Times New Roman");
							msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldType);
							msMgr.setFont(false, true, false, "000000", "9", "Times New Roman");
							msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldComment);
							msMgr.moveEnd();
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void createFiles(List<Element> messages, String module) throws Exception {
		MsgNameAndNum msgNAN = new MsgNameAndNum();
		Map<String,Short> map = (Map<String,Short>)msgNAN.getNameAndNum(com.pwrd.war.core.msg.MessageType.class);
		for (Iterator<Element> i = messages.iterator(); i.hasNext();) {
			Element msgElement = (Element) i.next();
			String msgType = msgElement.getAttributeValue("type");
			msMgr.setFont(false, false, false, "000000", "10.5", "Calibri");
			if(msgType==null){
				String msgId = msgElement.getAttributeValue("id");
				msMgr.putTxt(msgId+"\r\n");
				msMgr.moveEnd();
			}else{
				msMgr.putTxt(msgType+": ");
				msMgr.moveEnd();
				try{
					msMgr.putTxt(map.get(msgType).toString()+"\r\n");
				}catch(Exception e){
					msMgr.putTxt("No such number\r\n");
				}
				msMgr.moveEnd();
			}
			
			msMgr.createTable(length+1, 1);
			for (int k = 2; k <= length+1; k++) {  //打印表头
				msMgr.putSelectCell(msMgr.getTableCount(), 1, k);//1行k列选中
				msMgr.putTxt(template[k - 2]);
				msMgr.moveEnd();
			}
			msMgr.addRow(msMgr.getTableCount());
			msMgr.mergeCell(msMgr.getTableCount(), 1, 1, 1, 2);
			msMgr.moveEnd();
			List<Element> fElements = (List<Element>)msgElement.getChildren("field", NAME_SPACE);
			for (Iterator<Element> j = fElements.iterator(); j.hasNext();){
				int cols;
				cols=2;
				Element fieldElement = (Element) j.next();
				fieldType = fieldElement.getAttributeValue("type");
				fieldName = fieldElement.getAttributeValue("name");
				fieldComment = fieldElement.getAttributeValue("comment");
				fieldList = fieldElement.getAttributeValue("list");
				fieldMacro = fieldElement.getAttributeValue("macro");
				
				if(!(fieldType==null&&fieldName==null&&fieldComment==null)){//type,name,comment不全为空
					msMgr.addLastTableRow(msMgr.getTableCount());
					msMgr.setFont(false, false, false, "000000", "10.5", "Calibri");
					if(null==fieldMacro){
						msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldName+"");
					}else{
						msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldName+" (macro="+fieldMacro+")");
					}
					msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldType);
					msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldComment);
					msMgr.mergeCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, 1, msMgr.getTableRows(msMgr.getTableCount())-1, 2);
					msMgr.moveEnd();
				}
				
				if(!(null==fieldMacro)){
					createMacroFiles("macros.xml");
				}
				
				if("true".equals(fieldList)){
					List<Element> ffElements = (List<Element>)fieldElement.getChildren("field", NAME_SPACE);
					for (Iterator<Element> l = ffElements.iterator(); l.hasNext();){
						cols=2;
						Element fieldElement2 = (Element) l.next();
						fieldType = fieldElement2.getAttributeValue("type");
						fieldName = fieldElement2.getAttributeValue("name");
						fieldComment = fieldElement2.getAttributeValue("comment");
						msMgr.addLastTableRow(msMgr.getTableCount());
						msMgr.setFont(false, false, false, "000000", "9", "Times New Roman");
						msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldName);
						msMgr.setFont(false, false, false, "000000", "9", "Times New Roman");
						msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldType);
						msMgr.setFont(false, false, false, "000000", "9", "Times New Roman");
						msMgr.putTxtToCell(msMgr.getTableCount(), msMgr.getTableRows(msMgr.getTableCount())-1, cols++, fieldComment);
						msMgr.moveEnd();
					}
				}
			}
			msMgr.deleteLastRow(msMgr.getTableCount());
		}
	}
	

}




