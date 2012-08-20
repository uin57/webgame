package com.pwrd.war.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;

/**
 * Http请求工具
 * 
 * 
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger("HttpUtil");

	private static final String CHARSET = "charset=";
	/** 连接超时,默认5秒 */
	private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
	/** 读取超时,默认5秒 */
	private static final int DEFAULT_READ_TIMEOUT = 5000;
	/** 连接local的参数编码 */
	private static final String DEFAULT_ENCODE_TYPE = "utf-8";

	/**
	 * 按照utf-8的编码格式进行编码
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(String param) {
		try {
			return URLEncoder.encode(param, DEFAULT_ENCODE_TYPE);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.ARG_INVALID,
						"#Core.HttpUtil.encode", String.format(
								"String:%s endcode to type:%s exception",
								param, DEFAULT_ENCODE_TYPE)), e);
			}

			// 出异常了返回自身
			return param;
		}
	}

	/**
	 * 获取指定地址的内容,如果能够从URLConnection中可以解析出编码则使用解析出的编码;否则就使用GBK编码
	 * 
	 * @param requestUrl
	 * @return
	 * @throws IOException
	 */
	public static String getUrl(String requestUrl) throws IOException {
		final long _begin = System.nanoTime();
		BufferedReader reader = null;
		HttpURLConnection urlConnection = null;
		try {
			InputStream urlStream;
			URL url = new URL(requestUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
			urlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT);
			urlConnection.connect();
			urlStream = urlConnection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(urlStream,
					parseEncoding(urlConnection)));
			char[] _buff = new char[128];
			StringBuilder temp = new StringBuilder();
			int _len = -1;
			while ((_len = reader.read(_buff)) != -1) {
				temp.append(_buff, 0, _len);
			}
			return temp.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				urlConnection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对url的参数进行编码，并返回编码后的url
	 * 
	 * @param requestParmUrl
	 *            带参数的url请求
	 * @param params
	 *            ulr中的参数
	 * @return
	 */
	public static String encodeUrl(String requestParmUrl, Object... params) {
		// 对所有的字符类型参数进行编码
		for (int i = 0; i < params.length; i++) {
			Object _o = params[i];
			if (_o != null && _o instanceof String) {
				params[i] = encode((String) params[i]);
			}
		}
		return String.format(requestParmUrl, params);
	}

	/**
	 * 带参数的url请求, 会先对URL中的参数进行编码
	 * 
	 * @param requestParmUrl
	 *            请求的url
	 * @param params
	 *            请求的参数
	 * @return 返回的结果
	 * @throws IOException
	 */
	public static String getUrl(String requestParmUrl, Object... params)
			throws IOException {
		String _url = encodeUrl(requestParmUrl, params);
		return getUrl(_url);
	}

	/**
	 * 尝试解析Http请求的编码格式,如果没有解析到则使用GBK编码(主要考虑到Local平台的返回编码是gb2312的)
	 * 
	 * @param urlConnection
	 * @return
	 */
	static String parseEncoding(HttpURLConnection urlConnection) {
		String _encoding = urlConnection.getContentEncoding();
		if (_encoding != null) {
			return _encoding;
		}
		String _contentType = urlConnection.getContentType();
		if (_contentType != null) {
			int _index = _contentType.toLowerCase().indexOf(CHARSET);
			if (_index > 0) {
				_encoding = _contentType.substring(_index + CHARSET.length());
			}
		}
		if (_encoding != null) {
			return _encoding;
		} else {
			return DEFAULT_ENCODE_TYPE;
		}
	}
}
