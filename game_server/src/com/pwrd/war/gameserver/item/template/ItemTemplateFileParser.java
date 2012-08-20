package com.pwrd.war.gameserver.item.template;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.template.I18NField;
import com.pwrd.war.core.template.TemplateFileParser;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.PoiUtils;
import com.pwrd.war.gameserver.item.ItemDef;

/**
 * 
 * 在同一个sheet页可以生成多种道具类型的模板
 * 
 */
public class ItemTemplateFileParser extends TemplateFileParser {

	/** 身份类型所在列 */
	private static final int IDENDITY_TYPE_COLUMN = 3;
	private Class<?>[] clazzes = new Class<?>[8];

	public ItemTemplateFileParser() {
		super();
		clazzes[0] = ItemTemplate.class;
		clazzes[1] = EquipItemTemplate.class;
		clazzes[2] = ConsumeItemTemplate.class;
		clazzes[3] = ReelTemplate.class;
		clazzes[4] = MaterialTemplate.class;
		clazzes[5] = XinghunItemTemplate.class;
		clazzes[6] = ItemBagTemplate.class;
		clazzes[7] = ItemPetCardTemplate.class;
	}

	/**
	 * 
	 * 解析一个Excel文件，加载该文件表示的所有TemplateObject对象到templateObjects；
	 * 
	 * @param classes 不使用该参数 由本类私有clazzes决定
	 * @param templateObjects
	 * @throws Exception
	 */
	@Override
	public void parseXlsFile(Class<?>[] classes,Map<Class<?>, 
			Map<Integer, TemplateObject>> templateObjects,InputStream is, String excelFileName) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		int nums = wb.getNumberOfSheets();
		for(int i = 0; i < nums; i++){
			HSSFSheet sheet = wb.getSheetAt(i);
			Map<Integer, TemplateObject> curSheetObjects = parseXlsSheet(sheet, i, excelFileName);
			Map<Integer, TemplateObject> existCurClazzMap = templateObjects
					.get(clazzes[0]);
			if (existCurClazzMap != null) {
				existCurClazzMap.putAll(curSheetObjects);
			} else {
				templateObjects.put(clazzes[0], curSheetObjects);
			}
		}
		
	}

	/**
	 * 重载上面方法的目的：为了对应item模板与其他模板规则不一致的情况 解析Excel文件中的一个Sheet，返回以<id,数据对象>为键值对的Map
	 * classes类型要等到读到row时才能确定
	 * 
	 * @param sheet
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private Map<Integer, TemplateObject> parseXlsSheet(HSSFSheet sheet
			, int sheetIndex, String excelFileName)	throws InstantiationException, IllegalAccessException {
		String name = excelFileName.toLowerCase().replace(".xls", "");
		HSSFRow row0 = sheet.getRow(0);
		Map<Integer, I18NField> i18nField = new HashMap<Integer, I18NField>();
		Iterator<Cell> cellIt = row0.cellIterator();
		while(cellIt.hasNext()){
			Cell cell = cellIt.next();
			if(cell == null){
				break;
			}
			String str = cell.getStringCellValue();
			Pattern p = Pattern.compile(".+\\[(.+)\\]");
			Matcher m = p.matcher(str);
			if(m.find()){ 
				I18NField f = new I18NField();
				f.setOffset(cell.getColumnIndex());
				f.setShortTip(m.group(1));
				f.setSheetIndex(sheetIndex);
				f.setFileName(name);
				i18nField.put(cell.getColumnIndex(), f);
			}
			
			 
		}
		
		Map<Integer, TemplateObject> map = new HashMap<Integer, TemplateObject>();
		for (int i = 1; i < Short.MAX_VALUE; i++) {
			HSSFRow row = sheet.getRow(i);
			if (isEmpty(row)) {
				// 遇到空行就结束
				break;
			}
			// 读每个row之前，要先判断item的"物品身份类型"，来决定模板对象obj的真实类型
			ItemTemplate obj = null;
			HSSFCell cell = row.getCell(IDENDITY_TYPE_COLUMN);
			int equipType = PoiUtils.getIntValue(cell);
			ItemDef.IdentityType idType = ItemDef.IdentityType.valueOf(equipType);
			
			// 身份类型不可以不填
			if (idType == null || idType == ItemDef.IdentityType.NULL) {
				HSSFCell idCell = row.getCell(0);
				int templId = PoiUtils.getIntValue(idCell);
				throw new TemplateConfigException(ItemTemplate.SHEET_NAME,
						templId, "错误的道具身份类型 typeId=" + equipType);
			}
			switch (idType) {
			case EQUIP:
				// 装备
				obj = (ItemTemplate) clazzes[1].newInstance();
				break;
			case CONSUMABLE:
				// 消耗品
				obj = (ItemTemplate) clazzes[2].newInstance();
				break;
			case REEL:
				// 卷轴
				obj = (ItemTemplate) clazzes[3].newInstance();
				break;
			case MATERIAL:
				// 材料
				obj = (ItemTemplate) clazzes[4].newInstance();
				break;
			case XINGHUN:
				// 消耗品
				obj = (ItemTemplate) clazzes[5].newInstance();
				break;
			case ITEMBAG:
				// 物品包
				obj = (ItemTemplate) clazzes[6].newInstance();
				break;
			case PETCARD:
				// 武将卡
				obj = (ItemTemplate) clazzes[7].newInstance();
				break;
			default:
				break;
			}
			if (obj == null)
				break;
			this.parseXlsRow(obj, row, i18nField);
			map.put(obj.getId(), obj);
		}
		return map;
	}

	@Override
	public Class<?>[] getLimitClazzes() {
		return this.clazzes;
	}
}
