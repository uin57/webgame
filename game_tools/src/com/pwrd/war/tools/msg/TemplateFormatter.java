package com.pwrd.war.tools.msg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 消息模板格式化，主要用于去除模板中多余的空格
 * 
 * 
 */
public class TemplateFormatter {
	/** */
	public static final String TEMPLATE_DIC_FROM = "D:\\__SVN_SOURCE\\tr\\server\\trunk\\game_tools\\config\\log\\template\\original\\";
	/** */
	public static final String TEMPLATE_DIC_TO = "D:\\__SVN_SOURCE\\tr\\server\\trunk\\game_tools\\config\\log\\template\\";

	public static String replaceSpaces(String str) {
		// Pattern pattern=Pattern.compile("[\\s*\\#]");
		// Matcher matcher = pattern.matcher(str);
		// matcher.rep
		return str.replaceAll("\\s+\\#if", "#if")
				.replaceAll("\\s+\\#else", "#else").replaceAll("\\s+\\#end", "#end")
				.replaceAll("\\s+\\#for", "#for");
	}

	public static void main(String[] args) {
		File origPath = new File(TEMPLATE_DIC_FROM);
		File[] listFiles = origPath.listFiles();
		if (listFiles == null) {
			return;
		}
		for (File file : listFiles) {
			if (!file.getName().endsWith(".vm")) {
				continue;
			}
			BufferedReader reader = null;
			BufferedWriter writer = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				File targetFile = new File(TEMPLATE_DIC_TO + file.getName());
				if (!targetFile.exists()) {
					targetFile.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(targetFile));
				while (reader.ready()) {
					String line = reader.readLine();
					writer.write(replaceSpaces(line));
					if (reader.ready()) {
						writer.newLine();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.flush();
					writer.close();
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
