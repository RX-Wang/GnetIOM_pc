package com.gnet.common;

/**
 * 
 * @author admin
 * 
 * @version
 * 
 *          2014年11月1日 下午1:46:06
 * 
 *          Revision History: Date Reviser Description 将参数拼接 [null,\"xxx\"]的形式
 */
public class ArgsAppend {
    // 字符串为空 按null处理
	// 字符串为空 按null处理
	public static String getAppendArgs(String[] args) {
		if (args != null && args.length > 0) {
			StringBuffer result = new StringBuffer("[");
			for (String arg : args) {
				if (arg == null||arg.trim().equals("")) {
					result.append("null,");
				} else {
					result.append("\"" + arg + "\",");
				}
			}
			// 去掉末尾的逗号“，”
			String _backStr = result.substring(0, result.lastIndexOf(","));
			_backStr = _backStr + "]";
			return _backStr;
		} else {
			return null;
		}

	}
	// 不处理空字符串 
		public static String getAppendArgsOfNull(String[] args) {
			if (args != null && args.length > 0) {
				StringBuffer result = new StringBuffer("[");
				for (String arg : args) {
					if (arg == null) {
						result.append("null,");
					} else {
						result.append("\"" + arg + "\",");
					}
				}
				// 去掉末尾的逗号“，”
				String _backStr = result.substring(0, result.lastIndexOf(","));
				_backStr = _backStr + "]";
				return _backStr;
			} else {
				return null;
			}

		}
		
		public static String ArrayConvertToString(String[] ids){
			if (ids != null && ids.length > 0) {
				StringBuffer result = new StringBuffer("(");
				for (String id : ids) {
					if (id == null||id.trim().equals("")) {
						continue;
					} else {
						result.append("\"" + id + "\",");
					}
				}
				// 去掉末尾的逗号“，”
				String _backStr = result.substring(0, result.lastIndexOf(","));
				_backStr = _backStr + ")";
				return _backStr;
			} else {
				return null;
			}
		}
	public static void main(String[] args) {
		System.out.println(getAppendArgs(new String[] { "1", null, "2" }));
	}
}
