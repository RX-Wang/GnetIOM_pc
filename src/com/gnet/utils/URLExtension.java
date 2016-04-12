/**
 * 
 */
package com.gnet.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络URL相关拓展
 * <p>
 * <Strong>Date: </Strong> 2015年4月29日 下午8:44:28
 * 
 * @author Spartacus
 */
public class URLExtension {

	/**
	 * http url 是否可用
	 * <p>
	 * <h1>created by Spartacus at 2015年4月29日 下午8:42:46</h1>
	 * <p>
	 * 
	 * @param url
	 * @param timeout
	 * @return
	 */
	public static boolean isUrlAvailable(String url, Integer timeout) {

		boolean _result = false;
		try {
			URL _url = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) _url.openConnection();

			con.setConnectTimeout(timeout);

			int state = con.getResponseCode();

			// 200表示可以连接
			if (state == 200) {
				_result = true;
			}
		}
		catch (Exception e) {

		}
		return _result;
	}
}
