package com.gnet.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public class RestfulUtil {

	public static String sendHttpRequest(String psUrl, String psJson)
			throws UnsupportedEncodingException, MalformedURLException {
		return HttpUtil.sendPostRequest(psUrl, "" + psJson, false);
	}
}
