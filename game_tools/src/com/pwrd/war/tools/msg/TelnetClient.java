package com.pwrd.war.tools.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.util.MD5Util;

public class TelnetClient {
	
	/**
	 * 发送命令
	 * 
	 * @param cmd
	 * @param svr
	 * @return 命令结果
	 */
	public List<String> sendCmd(String ip,int port,String cmd) {
		List<String> commandList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();

		Socket clientSocket = new Socket();
		try {
			InetSocketAddress address = new InetSocketAddress(ip,port);
			commandList.add("LOGIN name=test password=test");
			commandList.add(cmd);
			commandList.add("Quit");
			clientSocket.connect(address, 5000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream(),Charset.forName("UTF-8")));			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),Charset.forName("UTF-8")));
			while (true) {
				if (commandList.size() > 0) {
					out.println(commandList.get(0));
					out.flush();
					commandList.remove(0);
				} else {
					break;
				}
				String result = null;
				while ((result = in.readLine()) != null) {
					if (result.endsWith("begin")) {
						continue;
					} else if (result.startsWith("Unknown command")) {
						resultList.add("unknown");
						break;
					} else if (result.endsWith("end")) {
						break;
					} else {
						resultList.add(result);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultList.add("connect error");
			return resultList;

		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {			
				e.printStackTrace();
			} finally {
				clientSocket = null;
			}
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		TelnetClient telnetClient = new TelnetClient();
		
		String md5str = MD5Util.createMD5String("201103201443448376" + "7" + "100" + "1305644361" + SharedConstants.HITHERE_MD5_KEY);
		
		JSONObject _toSendJsonParam = new JSONObject();
		_toSendJsonParam.put("orderId", "201103201443448376");
		_toSendJsonParam.put("userId", "7");
		_toSendJsonParam.put("money", "100");
		_toSendJsonParam.put("time", "1305644361");
		_toSendJsonParam.put("sign", md5str);
		
		
		List<String> resultList = telnetClient.sendCmd("192.168.128.89", 7000, "DIRECT_CHARGE " + _toSendJsonParam.toString());
		System.out.println(resultList);
		
	}

}
