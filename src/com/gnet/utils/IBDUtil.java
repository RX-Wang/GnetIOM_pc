package com.gnet.utils;

import java.util.Map;

import com.gnet.common.BaseException;
import com.gnet.common.BaseException.ExceptionCode;

public class IBDUtil {
	private static ConstantUtil util = new ConstantUtil();

	/**
	 * Description:调用IBD接口 验证用户登录
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws BaseException
	 */
	public static Map<String, Object> validateLoginUser(String userName,
			String passWord) throws BaseException {
		String IbdUrl = util.getPropertiesByName("IBD_URL");

		String passWordMd5 = QMRUtil.Md5(passWord);

		String jsonParam = "{\"GII_HEAD\": {\"VERSION\": \"PC2.0\",\"SNID\": \"20140113140645068\",\"SIGN\": [{\"POSITION/k\": \"\"},{\"RANDOMCODE/v\": \"\"}],\"GZIP\": \"false\"},\"GII_IBD\": {\"USER_NAME\": \""
				+ userName + "\",\"USER_PASSWORD\": \"" + passWordMd5 + "\"}}";

		String loginJson = "";
		try {
			loginJson = AxisUtil.callService(IbdUrl,
					"http://webService.gnet.cn.com/", "GII_USER_LOGIN",
					"JsonString", jsonParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(ExceptionCode.WS, "调用IBD接口giiUSERLOGIN出现异常");
		}

		return (Map<String, Object>) JsonUtil.parse(loginJson);
	}

	/**
	 * @Description: 根据参数获取值
	 * @param
	 * @return
	 * @throws
	 */
	public static Map<String, Object> getDeviceSiteById(String user_id,
			String device_id) throws BaseException {
		String IbdUrl = util.getPropertiesByName("IBD_URL");
		String jsonParam = "{\"GII_HEAD\": {\"VERSION\": \"PC2.0\",\"SNID\": \"20140113140645068\",\"SIGN\": [{\"POSITION/k\": \"\"},{\"RANDOMCODE/v\": \"\"}],\"GZIP\": \"false\"},\"GII_IBD\": {\"USERID\": \""
				+ user_id + "\",\"IMEI\": \"" + device_id + "\"}}";
		String loginJson = "";
		try {
			String _returnStr = AxisUtil.callService(IbdUrl,
					"http://webService.gnet.cn.com/", "GII_USER_GetPosition",
					"JsonString", jsonParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(ExceptionCode.WS, "调用IBD接口giiUSERLOGIN出现异常");
		}
		return (Map<String, Object>) JsonUtil.parse(loginJson);
	}

	/**
	 * 
	 * @Title: registerIBD 
	 * @Description: 注册成为IBD集团用户 
	 * @param @param companyId
	 * @param @param companyPwd
	 * @param @param companyName
	 * @param @param companyAddr
	 * @param @param companyInterAddr
	 * @param @param companyPhone
	 * @param @return
	 * @param @throws BaseException    设定文件 
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static Boolean registerIBD(String companyId,String companyPwd,String companyName,String companyAddr,String companyInterAddr,String companyPhone)
			throws BaseException {
		String IbdUrl = util.getPropertiesByName("IBD_URL");
		String passWordMd5 = QMRUtil.Md5(companyPwd);// md5加密

		String _jsonParam = "{\"GII_HEAD\": {\"VERSION\": \"PC2.0\",\"SNID\": \"20140113140645068\", \"SIGN\": [{ \"position/k\": \"\" },{\"RandomCode/v\": \"\"}], \"GZIP\": \"false\" },\"GII_IBD\": {\"REQUEST\":[{\"USER_NAME\":\""
				+ companyId
				+ "\",\"USER_PASSWORD\":\""
				+ passWordMd5
				+ "\",\"ISETPUSER\":\"0\""
				+ "\",\"USER_TRUENAME\":\""
				+ companyName
				+ "\",\"ADDRESS\":\""
				+ companyAddr
				+ "\",\"EMAIL\":\""
				+ companyInterAddr
				+ "\",\"TEL\":\""
				+ companyPhone
				+ "}]}}";
		try {
			String _jsonReturn = AxisUtil.callService(IbdUrl,
					"http://webService.gnet.cn.com/", "GII_USER_AddUser",
					"JsonString", _jsonParam);

			Map<String, Object> retuMap = (Map<String, Object>) JsonUtil
					.parse(_jsonReturn);

			Map<String, Object> headMap = (Map<String, Object>) retuMap
					.get("GII_HEAD");

			if (headMap.get("RESCODE").toString().equals("0000")
					|| headMap.get("RESCODE").toString().equals("1000")) {
				return true;// 注册成功
			} else {
				return false;// 注册失败
			}
		} catch (Exception e) {
			throw new BaseException(ExceptionCode.WS, "调用ws异常");
		}
		/**
		 * 本来这里应该返回userId或者用户的信息的，但是因为注册的时候 调 IBD接口
		 * 填的数据较少，董淑芳说可能接口出现空指针，所以返回数据永远不可能是成功数据json
		 * ，但是用户名却已经被保存进数据库了，所以这里我先不做处理，返回true 或者 false
		 **/
	}

	/**
	 * Description:验证用户名
	 * 
	 * @param _psUserName
	 *            用户名
	 * @return true or false true 可以注册 /false 不可以注册
	 * @throws Exception
	 */
	public static Boolean validateUserName(String _psUserName) throws Exception {
		String IbdUrl = util.getPropertiesByName("IBD_URL");
		String _jsonParam = "{\"GII_HEAD\": {\"VERSION\": \"PC2.0\",\"SNID\": \"20140113140645068\",\"SIGN\": [{\"position/k\": \"\"},{\"RandomCode/v\": \"\"} ],\"GZIP\": \"true\"},\"GII_IBD\": { \"ACTION\": \"CheckUserAccount\", \"PARAM\": \""
				+ _psUserName + "\" }}";

		String _returnJson = AxisUtil.callService(IbdUrl,
				"http://webService.gnet.cn.com/", "GII_SYS_Check",
				"JsonString", _jsonParam);
		Map<String, Object> _rMap = (Map<String, Object>) JsonUtil
				.parse(_returnJson);

		Map<String, Object> headMap = (Map<String, Object>) _rMap
				.get("GII_HEAD");

		if ("0000".equals(headMap.get("RESCODE").toString())) {
			return true;// 可以注册
		} else {
			return false;// 系统异常 或者 用户名已注册
		}
	}

	public static void main(String[] args) {
		String IbdUrl = util.getPropertiesByName("IBD_URL");
		String passWordMd5 = QMRUtil.Md5("123456");
		String _jsonParam = "{\"GII_HEAD\": {\"VERSION\": \"PC2.0\",\"SNID\":"
				+ " \"20140113140645068\",\"SIGN\": [{\"position/k\": \"\"},"
				+ "{\"RandomCode/v\": \"\"} ],\"GZIP\": \"true\"},"
				+ "\"GII_IBD\": {"
				+ "\"REQUEST\":[{"
				+ " \"USER_NAME\": \"010bjba44222443\", "
				+ "\"USER_PASSWORD\": \""
				+ passWordMd5
				+ "\" ,"
				+ "\"ISETPUSER\":\"0\" ,"
				+ "\"USER_TRUENAME\":\"北京凯英信业科技股份有限公司\" ,"
				+ "\"ADDRESS\":\"北京市海淀区学院路30号科大天工大厦B座18层\" ,"
				+ "\"EMAIL\":\"www.keytec.com.cn\" ,"
				+ "\"TEL\":\"8610-82601199\""
				+"}]}}";

		String _returnJson = "";
		try {
			_returnJson = AxisUtil.callService(IbdUrl,
					"http://webService.gnet.cn.com/", "GII_USER_AddUser",
					"JsonString", _jsonParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(_returnJson);
	}
}
