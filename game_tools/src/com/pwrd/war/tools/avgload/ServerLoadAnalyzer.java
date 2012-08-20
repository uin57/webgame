package com.pwrd.war.tools.avgload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ServerLoadAnalyzer {
	
	
	/** 公共表头 */
	private static final String[] tableHeader = { "Time","avgload", "%CPU", "%MEM", "%Survivor0", "%Survivor1", "%Eden", "%Old", "%Perm",
													"Yong Gc Count","Yong Gc Time","Full Gc Count","Full Gc Time","Gc Time"};
	/** avgload.log存放目录 */
	private static String LOG_FILE = "D:\\avgload.log";
	/** 生成的excel文件的路径 */
	private static String EXP_XLS_FILE = "D:\\serverload.xls";
	
	
	/**  */
	private Map<String, List<ServerLoad>> statusInfos = new HashMap<String, List<ServerLoad>>();
	
	
	
	/**
	 * 正则表达式分析格式如下的字符串
	 * 
	 *  2010-12-08 10:39:01-----1.91-----
	 *  -Dserver.name=world_server_10 30908 0.0 3.6 0.00 97.23 37.38 3.81 99.67 9 0.132 0 0.000 0.132
	 *  -Dserver.name=log_server_13 31004 0.0 1.5 0.00 99.77 25.44 0.87 73.23 1 0.014 0 0.000 0.014
	 *  -Dserver.name=dbs_server_11 31055 0.0 2.1 0.00 73.36 58.20 0.00 99.64 1 0.024 0 0.000 0.024
	 *  -Dserver.name=game_server_1 31196 0.1 15.7 0.00 40.00 22.45 66.46 99.54 189 6.166 0 0.000 6.166
	 *  -Dserver.name=game_server_2 31289 0.6 12.9 60.00 0.00 71.65 92.48 79.71 626 8.956 1 1.924 10.880
	 *  -Dserver.name=game_server_3 31342 0.2 13.0 25.00 0.00 50.27 91.89 79.58 340 6.512 1 1.775 8.287
	 *  -Dserver.name=game_server_4 31395 1.3 12.7 0.00 66.67 25.60 91.12 79.12 1105 13.028 1 1.832 14.860
	 *  -Dserver.name=agent_server_14 31503 174 3.9 100.00 100.00 100.00 37.29 30.23 21288 3673.385 23998 1019.899 4693.284
	 * 
	 * 上述字符串各个字段的意义如下：
	 * 2010-01-06 11:08:01（记录日志的时间）-----0.02（5分钟的平均负载）-----
	 * 
	 * servername   				pid		%CPU	%MEM	S0		S1		E		O		P		YGC		YGCT	FGC		FGCT	GCT
	 * -Dserver.name=log_server_13  25948	0.3		1.8		0.00	87.73	77.32	5.33	84.79	5		0.117 	0		0.000 	0.117
	 * 
	 */
	public void buildData()
	{
		File logFile = getLogFile();
		if (logFile == null) {
			return;
		}
		try {
			String logStr = FileUtils.readFileToString(logFile);
			
			/*
			 * 分隔日志的字符串如下：
			 * -*-*-*-*-*
			 */
			String[] logs = logStr.trim().split("-\\*-\\*-\\*-\\*-\\*");
			
			for(String perLog : logs)
			{
				
				String[] lines = perLog.trim().split("\n");
				if(lines.length < 2) {
					System.out.println(perLog);
					throw new RuntimeException("Invalid log format:\n" + perLog);
				}
				
				//解析第一行
				Pattern perLogTitlePtn = Pattern.compile("(.{19})-----(.*)-----", Pattern.DOTALL);
				Matcher perLogTitleMatcher = perLogTitlePtn.matcher(lines[0].trim());
				
				String time = "";
				String avgLoad = "";
				if(perLogTitleMatcher.matches()) {
					time = perLogTitleMatcher.group(1);	//时间
					avgLoad = perLogTitleMatcher.group(2);//5分钟平均负载
				}
				else {
					throw new RuntimeException("Invalid log format:\n" + perLog);
				}
				
				//解析第二行到最后一行
				for (int i = 1; i < lines.length; i++) {
					Pattern pattern = Pattern.compile("(.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*)", Pattern.DOTALL);					
					Matcher matcher = pattern.matcher(lines[i].trim());
					if(matcher.matches())
					{
						String serverName = matcher.group(1);
						 List<ServerLoad> serverloads = statusInfos.get(serverName);
						 if(serverloads == null )
						 {
							 serverloads = new ArrayList<ServerLoad>();
							 statusInfos.put(serverName, serverloads);
						 }
						 ServerLoad data = new ServerLoad();
						 data.setTime(time);								//时间
						 data.setAvgLoad(avgLoad);							//5分钟平均负载
						 data.setServerName(serverName);            		//服务器名称
						 data.setPid(matcher.group(2));             		//Java进程ID			
						 data.setCpuPercent(matcher.group(3));				//%CPU
						 data.setMemoryPercent(matcher.group(4));			//%MEM
						 data.setSurvivor0Size(matcher.group(5));			//Survivor 0 大小
						 data.setSurvivor1Size(matcher.group(6));			//Survivor 1 大小
						 data.setEdenSize(matcher.group(7));				//Eden 大小
						 data.setOldSize(matcher.group(8));					//年老代内存大小
						 data.setPermSize(matcher.group(9));				//持久代大小
						 data.setWholeYongGcCount(matcher.group(10));		//Yong GC次数
						 data.setWholeYongGcTime(matcher.group(11));		//Yong GC时间
						 data.setWholeFullGcCount(matcher.group(12));		//Full GC次数
						 data.setWholeFullGcTime(matcher.group(13));		//Full GC时间
						 data.setWholeGcTime(matcher.group(14));			//该进程从启动到现在GC的总时间
						
						 fillDiff(data);									//diff
						 
						 serverloads.add(data);
					}			
				}		
				
			}	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void saveToExcel()
	{
		HSSFWorkbook _workBook = new HSSFWorkbook();// 创建工作本
		Iterator<Map.Entry<String, List<ServerLoad>>> _serverloadSet = statusInfos.entrySet().iterator();
		while (_serverloadSet.hasNext()) {
			Map.Entry<String, List<ServerLoad>> _tempEntry = _serverloadSet.next();
			String _serverName = _tempEntry.getKey();
			List<ServerLoad> _serverLoadList = _tempEntry.getValue();
			if (_serverLoadList == null || _serverLoadList.isEmpty()) {
				return;
			}
			ServerLoad _tempInfo = _serverLoadList.get(0);

			HSSFSheet _sheet = _workBook.createSheet(_serverName); // 创建表
			createTableHeader(_sheet, _tempInfo);// 创建表头
			
			int rowIndex = 1;
			for (ServerLoad _statusInfo : _serverLoadList) {
				createTableRow(_sheet, (short) rowIndex, _statusInfo);// 创建行
				rowIndex++;
			}
			_sheet.setGridsPrinted(true);
		}
		
		File xlsFile = getExportExcelFile();
		
		if(xlsFile.exists()) {
			xlsFile.delete();
		}
		
		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(xlsFile);
			_workBook.write(fos);
			fos.close();
			fos = null;

			System.out.println("ServerLoad Data export to : " + EXP_XLS_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
	}
	
	/**
	 * 创建表头
	 * 
	 * @param sheet
	 * @param statusInfo
	 * @return
	 */
	private void createTableHeader(HSSFSheet sheet, ServerLoad statusInfo) {
		HSSFRow headerRow = sheet.createRow((short) 0);
		int i = 0;

		for (int j = 0; j < tableHeader.length; j++) {
			HSSFCell headerCell = headerRow.createCell(i++);
			headerCell.setCellValue(tableHeader[j]);
		}
	}
	
	/**
	 * 创建行
	 * 
	 * @param cells
	 * @param rowIndex
	 * @param statusInfos
	 */
	private void createTableRow(HSSFSheet sheet, short rowIndex,
			ServerLoad data) {
		HSSFRow row = sheet.createRow((short) rowIndex);// 创建第rowIndex行
		
		int cellIndex = 0;		
		// 创建第i个单元格
		row.createCell(cellIndex++).setCellValue(new HSSFRichTextString(data.getTime()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getAvgLoad()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getCpuPercent()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getMemoryPercent()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getSurvivor0Size()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getSurvivor1Size()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getEdenSize()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getOldSize()));
		row.createCell(cellIndex++).setCellValue(Float.parseFloat(data.getPermSize()));
		row.createCell(cellIndex++).setCellValue(data.getYongGcCount());
		row.createCell(cellIndex++).setCellValue(data.getYongGcTime());
		row.createCell(cellIndex++).setCellValue(data.getFullGcCount());
		row.createCell(cellIndex++).setCellValue(data.getFullGcTime());
		row.createCell(cellIndex++).setCellValue(data.getGcTime());
	}
	
	/**
	 * 填充差值
	 * 
	 * @param data
	 */
	private void fillDiff(ServerLoad data) {

		String servername = data.getServerName();
		String wholeYongGcCount = data.getWholeYongGcCount();
		String wholeYongGcTime = data.getWholeYongGcTime();
		String wholeFullGcCount = data.getWholeFullGcCount();
		String wholeFullGcTime = data.getWholeFullGcTime();
		String wholeGcTime = data.getWholeGcTime();
		
		String lastWholeYongGcCount = "0";
		String lastWholeYongGcTime = "0";
		String lastWholeFullGcCount = "0";
		String lastWholeFullGcTime = "0";
		String lastWholeGcTime = "0";
		ServerLoad lastData = getLastServerLoadData(servername);
		if(lastData != null) {
			lastWholeYongGcCount = lastData.getWholeYongGcCount();
			lastWholeYongGcTime = lastData.getWholeYongGcTime();
			lastWholeFullGcCount = lastData.getWholeFullGcCount();
			lastWholeFullGcTime = lastData.getWholeFullGcTime();
			lastWholeGcTime = lastData.getWholeGcTime();
		}
		
		int yongGcCount = Integer.parseInt(wholeYongGcCount) - Integer.parseInt(lastWholeYongGcCount);
		data.setYongGcCount(yongGcCount);
		
		float yongGcTime = Float.parseFloat(wholeYongGcTime) - Float.parseFloat(lastWholeYongGcTime);
		data.setYongGcTime(yongGcTime);
		
		int fullGcCount = Integer.parseInt(wholeFullGcCount) - Integer.parseInt(lastWholeFullGcCount);
		data.setFullGcCount(fullGcCount);
		
		float fullGcTime = Float.parseFloat(wholeFullGcTime) - Float.parseFloat(lastWholeFullGcTime);
		data.setFullGcTime(fullGcTime);
		
		float gcTime = Float.parseFloat(wholeGcTime) - Float.parseFloat(lastWholeGcTime);
		data.setGcTime(gcTime);
		
	}
	
	/**
	 * 获取指定进程ID的最后一个负载数据
	 * 
	 * @param pid
	 * @return
	 */
	public ServerLoad getLastServerLoadData(String serverName) {
		List<ServerLoad> list = statusInfos.get(serverName);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(list.size() - 1);
	}	
	
	/**
	 * 获得所有日志文件
	 * 
	 * @return
	 */
	private File getLogFile() {
		return new File(LOG_FILE);
	}
	
	private File getExportExcelFile()
	{
		return new File(EXP_XLS_FILE);
	}
	
	public static void main(String[] args) {
		ServerLoadAnalyzer analyzer = new ServerLoadAnalyzer();
		analyzer.buildData();	
		analyzer.saveToExcel();
	}
	
}
