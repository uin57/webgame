package com.pwrd.war.core.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class IpUtils {

	public static String getIp(org.apache.mina.common.IoSession session) {
		if (session == null) {
			// session为空的特殊IP
			return "255.255.255.255";
		}
		String ip = "";
		InetSocketAddress addr = (InetSocketAddress) session.getRemoteAddress();
		InetAddress ia = addr.getAddress();
		if (ia instanceof Inet4Address) {
			return ((Inet4Address) ia).getHostAddress() + ":" + addr.getPort();
		} else if (ia instanceof Inet6Address) {
			return ((Inet6Address) ia).getHostAddress();
		}
		return ip;
	}
}
