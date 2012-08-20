package com.pwrd.war.tools.serverstatus.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.tools.serverstatus.StatusInfo;

/**
 * 服务器状态日志分析器
 * 
 * 
 */
public class ServerStatusAnalyzer {
	/** 状态日志文件存放目录 */
	private static String LOG_FILE = "D:\\server_status.log";
	/** 生成的excel文件的路径 */
	private static String EXP_XLS_DIR = "D:\\";
	/** 默认采样间隔30分（单位：ms） */
	private static long SAMPLE_RATE = 1000 * 60 * 30;

	/**
	 * 过滤状态日志信息
	 * 
	 * @param statusInfos
	 */
	public void filtData(Map<String, List<StatusInfo>> statusInfos) {
		File logFile = getLogFile();
		if (logFile == null) {
			return;
		}

		BufferedReader _reader = null;
		try {
			String _line = null;
			/* 读入每个日志文件 */
			System.out.println("Read log file : " + logFile.getName());
			_reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(logFile)));

			while ((_line = _reader.readLine()) != null) {
				_line = _line.trim();
				if (_line.length() == 0) {
					continue;
				}
				int _dataStart = _line.indexOf("{");
				int _dataEnd = _line.indexOf("}");
				String _dataStr = _line.substring(_dataStart + 1, _dataEnd);
				StatusInfo _status = StatusInfoSetor.setData(_dataStr.replace(
						"\"", ""));
				if (_status == null) {
					continue;
				}

				List<StatusInfo> _statusInfoList = statusInfos.get(_status
						.getServerID());
				if (null == _statusInfoList) {
					_statusInfoList = new ArrayList<StatusInfo>();
					statusInfos.put(_status.getServerID(), _statusInfoList);
				}
				/* 属于采样点才添加 */
				insertIntoList(_statusInfoList, _status);

			}
			_reader.close();
			_reader = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (_reader != null) {
					_reader.close();
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 如果满足采样条件，则把新状态信息插入到列表中
	 * 
	 * @param statusList
	 * @param statusInfo
	 */
	private void insertIntoList(List<StatusInfo> statusList,
			StatusInfo statusInfo) {
		if (statusList.isEmpty()) {
			statusList.add(statusInfo);
			return;
		}
		int lastStatusIndex = statusList.size() - 1;
		StatusInfo _lastStatus = statusList.get(lastStatusIndex);
		if (statusInfo.getLastUpdateTime() - _lastStatus.getLastUpdateTime() < SAMPLE_RATE) {
			return;
		}

		statusList.add(statusInfo);
	}

	/**
	 * 获得所有日志文件
	 * 
	 * @return
	 */
	private File getLogFile() {
		return new File(LOG_FILE);
	}

	public static void main(String[] args) {
		ServerStatusAnalyzer analyzer = new ServerStatusAnalyzer();
		Map<String, List<StatusInfo>> statusInfos = new HashMap<String, List<StatusInfo>>();

		/* 设置运行参数 */
		if (args.length > 1) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-t")) { // 采样间隔
					SAMPLE_RATE = Integer.parseInt(args[i + 1]) * 60 * 1000;
					i++;
				} else if (args[i].equals("-s")) {// status日志文件
					LOG_FILE = args[i + 1];
					i++;
				} else if (args[i].equals("-d")) {// 生成的csv文件存放目录
					EXP_XLS_DIR = args[i + 1];
					i++;
				}
			}
		}

		analyzer.filtData(statusInfos);

		ExportStatusToExcel exportExcel = new ExportStatusToExcel(EXP_XLS_DIR);
		exportExcel.createExcelSheeet(statusInfos);
	}

}
