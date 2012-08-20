package com.pwrd.war.core.template;

public class I18NField {
	private int offset;//便宜列
	private String shortTip;//简短提示
	private String fileName;//文件名,不包括后缀
	private int sheetIndex;//sheet序号
	
	/**
	 * 根据唯一序号取得该多语言id
	 * @author xf
	 */
	public String getId(int order){
		return "[\"#"+fileName+"_"+sheetIndex+"_"+shortTip+"_"+order+"\"]";
	}
	public String getIdNormal(int order){
		return "#"+fileName+"_"+sheetIndex+"_"+shortTip+"_"+order;
	}
	public String getIdNoFormat(int order){
		return fileName+"_"+sheetIndex+"_"+shortTip+"_"+order;
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getShortTip() {
		return shortTip;
	}
	public void setShortTip(String shortTip) {
		this.shortTip = shortTip;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	
	
}
