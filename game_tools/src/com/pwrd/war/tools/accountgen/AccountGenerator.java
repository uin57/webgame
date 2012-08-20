package com.pwrd.war.tools.accountgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 账号自动生成
 * 
 * 
 */
public class AccountGenerator {

	public static void main(String[] args) throws IOException {
		
		File file = new File("d:\\account.sql");
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		

		for (int i = 1; i <= 1000; i++) {
			String _name = String.valueOf(i);
			StringBuffer _strBuff = new StringBuffer();
			_strBuff
					.append("INSERT INTO `t_user_info` (id,name,password,role,failedLogins,lockStatus,muteTime) VALUES (");
			_strBuff.append(_name).append(",'");
			_strBuff.append(_name).append("','");
			_strBuff.append(1).append("',");
			_strBuff.append("2").append(",");
			_strBuff.append("0").append(",");
			_strBuff.append("0").append(",");
			_strBuff.append("0").append(");\r\n");
			System.out.println(_strBuff.toString());
			writer.write(_strBuff.toString());			
		}
		
		writer.close();

	}

}
