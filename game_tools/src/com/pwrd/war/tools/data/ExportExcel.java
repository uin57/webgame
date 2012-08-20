package com.pwrd.war.tools.data;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 将指定策划资源目录下的数据导出为技术使用的格式,使用方式:
 * 
 * <pre>
 * java com.pwrd.war.tools.data.ExportExcel &lt;resourceSrcDir&gt; &lt;outRoot&gt; &lt;showHeader&gt; &lt;configRowIndex&gt; &lt;dataRowIndex&gt; [i18n]
 * </pre>
 * 
 * 参数说明:
 * <ul>
 * <li>resourceSrcDir: 策划资源的目录</li>
 * <li>outRoot: 导出数据的输出目录</li>
 * <li>i18n: 多语言选项,如zh_CN,如果不填默认使用在Excel工作薄中的langs表</li>
 * </ul>
 * 
 * 输出: 在 参数<b>outRoot</b>目录中会生成两个目录,分别是server和client,各自对应给服务器和 客户端使用的脚本数据.
 * 
 * 
 * 
 */
public class ExportExcel {
	private static final String I18N_DIR = "i18n";

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java com.pwrd.war.tools.data.ExportExcel <resourceSrcDir> <outRoot> <showHeader> <configRowIndex> <dataRowIndex> [i18n]");
			System.exit(1);
		}
		String resourceSrcDir = args[0];
		String outRoot = args[1];
		boolean isShowHeader = Boolean.valueOf(args[2]);
		int configRowIndex = Integer.valueOf(args[3]);
		int dataRowIndex = Integer.valueOf(args[4]);		
		String i18n = null;
		if (args.length >= 6) {
			i18n = args[5];
		}

		File resSrcDir = new File(resourceSrcDir);
		File outRootFile = new File(outRoot);
		File outServerRootDir = outRootFile;

		if (!resSrcDir.isDirectory()) {
			System.err.println("The resource source dir is not exitst or not a dir.");
			System.exit(1);
		}

		if (outServerRootDir.exists()) {
			Util.deleteFile(outServerRootDir);
		}

		if (!outServerRootDir.mkdirs()) {
			System.err.println("Make dir " + outServerRootDir + " fail.");
			System.exit(1);
		}


		final FileFilter excelFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				// 忽略i18n的文件
				if (file.getParent().indexOf(I18N_DIR) >= 0) {
					return false;
				}
				return file.getName().endsWith(".xls");
			}
		};

		List<File> xlsFiles = Util.listFiles(resSrcDir, excelFilter);
		String resSrcDirRoot = resSrcDir.getAbsolutePath();

		File i18nDirRoot = i18n != null ? new File(resSrcDir, I18N_DIR
				+ File.separator + i18n) : null;
		for (File file : xlsFiles) {
			String relativePath = file.getAbsolutePath().substring(
					resSrcDirRoot.length());
			File serverOutPath = new File(outServerRootDir, relativePath);
			if (!serverOutPath.getParentFile().exists()) {
				if (!serverOutPath.getParentFile().mkdirs()) {
					throw new RuntimeException("Can't create the dir "
							+ serverOutPath.getParentFile());
				}
			}
			IExcelWorkbookHandler wbHandler = new ExportWbHandler(serverOutPath, isShowHeader, configRowIndex, dataRowIndex);
			if (i18n == null) {
				exportExcelData(file, wbHandler, null);
			} else {
				String dataFileName = file.getName();
				int index = dataFileName.lastIndexOf(".");
				File i18nFile = new File(i18nDirRoot, dataFileName.substring(0,
						index)
						+ "_lang" + dataFileName.substring(index));
				exportExcelData(file, wbHandler, i18nFile);
			}
		}
	}

	/**
	 * @param xlsPath
	 * @param sheetIndexes
	 * @param
	 */
	private static void exportExcelData(File fileName,
			IExcelWorkbookHandler wbHandler, File i18n) {
		System.out.println("*** Start export excel data  src[" + fileName
				+ "] i18n [" + (i18n != null ? i18n : "default") + "]");
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
			preHandleI18n(fileName, i18n, wb);
			wbHandler.onStart(wb);
			final int sheetCount = wb.getNumberOfSheets();
			System.out.println("Export " + sheetCount + " sheets");
			for (int i = 0; i < sheetCount; i++) {
				final IExcelSheetHandler _sheetHandler = wbHandler
						.getSheetHandler(i);
				final HSSFSheet sheet = wb.getSheetAt(Integer.valueOf(i));
				System.out.println("##Start export sheet "
						+ sheet.getSheetName());
				// Util.printSheet(sheet);
				try {
					if (!_sheetHandler.onStart(sheet)) {
						continue;
					}
					int rows = sheet.getLastRowNum();
					for (int j = 0; j <= rows; j++) {
						HSSFRow row = sheet.getRow(j);
						if (row != null) {
							_sheetHandler.handle(row);
						}
					}
					_sheetHandler.onFinish();
				} catch (Exception e) {
					throw new RuntimeException("Error sheet name:"
							+ sheet.getSheetName() + " sheet index:"
							+ wb.getSheetIndex(sheet), e);
				}
			}
			wbHandler.onFinish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		System.out.println("*** Finish export excle data  src[" + fileName
				+ "] i18n[" + (i18n != null ? i18n : "default") + "]");
	}

	/**
	 * 预处理i18n的数据
	 * 
	 * @param excelDataFile
	 * @param i18n
	 * @param wb
	 */
	private static void preHandleI18n(File excelDataFile, File i18n,
			HSSFWorkbook wb) {
		if (i18n == null) {
			return;
		}
		// 设置了i18n参数,替换原数据里的langs sheet
		do {
			if (!i18n.isFile()) {
				System.out.println("The i18n file [" + i18n.getAbsolutePath()
						+ "] dose not exist,use default language package");
				break;
			}
			HSSFSheet oldLangsSheet = wb.getSheet("langs");
			if (oldLangsSheet == null) {
				System.out.println("The excel " + excelDataFile
						+ " doesn't contain the langs sheet,skip it.");
				break;
			}

			InputStream i18nIn = null;
			try {
				i18nIn = new FileInputStream(i18n);
				HSSFWorkbook i18nWb = new HSSFWorkbook(new POIFSFileSystem(
						i18nIn));
				HSSFSheet i18nSheet = i18nWb.getSheetAt(0);
				if (i18nSheet == null) {
					System.out.println("The i18n file " + i18n
							+ " doesn't contain the sheet,skip it.");
					break;
				}
				wb.removeSheetAt(wb.getSheetIndex(oldLangsSheet));
				HSSFSheet newLangsSheet = wb.createSheet("langs");
				Util.copySheet(i18nSheet, newLangsSheet);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (i18nIn != null) {
					try {
						i18nIn.close();
					} catch (IOException e) {
					}
				}
			}

		} while (false);
	}
}
