package com.gnet.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author sam
 * 
 * @version v0.1
 * 
 *          Created on 2013-06-24
 * 
 *          Revision History: Date Reviser Description ---- -------
 *          ----------------------------------------------------
 * 
 *          Description:java对象与json字符的相互转换
 */
public class JsonUtil {

	/**
	 * @param pobj
	 *            需要转换的java对象
	 * @return
	 * 				转换后的对象
	 */
	public static String toJSONString(Object pobj, 
			SerializerFeature ...features) {
		SerializeWriter out = new SerializeWriter();
		String s;
		JSONSerializer serializer = new JSONSerializer(out);
		SerializerFeature arr$[] = features;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			SerializerFeature feature = arr$[i$];
			serializer.config(feature, true);
		}

		serializer.getValueFilters().add(new ValueFilter() {
			public Object process(Object obj, String s, Object value) {
				if(null!=value) {
					if(value instanceof java.util.Date) {
						return String.format("%1$tF %1tT", value);
					}
					return value;
				}else {
					return "";
				}
			}
		});
		serializer.write(pobj);
		s = out.toString();
		out.close();
		return s;
	}
	
	/**
	 * @param psJson
	 *            需要转换的json字符串
	 * @return
	 * 				转换后的对象
	 */
	public static final Object parse(String psJson) {
		return JSON.parseObject(psJson);
	}
	
	public static final String toJsonString(Object obj){
		return JSON.toJSONString(obj);
	}

	/**
	 * @param psJson
	 *            需要转换的json字符串
	 * @return
	 * 				转换后的对象
	 */
	public static final List parseArray(String psJson) {
		return JSON.parseArray(psJson);
	}

	/**
	 * Description: 返回信息的JSON字符串
	 * 
	 * @param map
	 *            解析的JSON字符串
	 * @param sign
	 *            当sign==userId时不为null
	 * @param rescode
	 *            返回的code值
	 * @param memo
	 *            对结果的描述信息
	 * @return 返回字符串类型
	 */
	@SuppressWarnings("unchecked")
	public static Map resInfo(Map<String, Object> map, String sign,
			String rescode, String memo, Map<String, Object> listMap) {
		Map mp = new HashMap();
		List lst = new ArrayList();
		Map mapTmp = new HashMap();
//		mapTmp.put(Constants.HEAD_RESPONSE[0],
//				map.get(Constants.HEAD_RESPONSE[0].toLowerCase()));
//		mapTmp.put(Constants.HEAD_RESPONSE[1], rescode);
//		mapTmp.put(Constants.HEAD_RESPONSE[2], memo);
//		mapTmp.put(Constants.HEAD_RESPONSE[3], sign);
//		if (sign == null || sign.equals("")) {
//			mapTmp.put(Constants.HEAD_RESPONSE[3],
//					map.get(Constants.HEAD_RESPONSE[1].toLowerCase()));
//		}
//		lst.add(mapTmp);
//		mp.put(Constants.JSON_HEAD, lst);
//		if (listMap != null) {
//			Iterator it = listMap.entrySet().iterator();
//			while (it.hasNext()) {
//				Map.Entry entry = (Map.Entry) it.next();
//				mp.put(entry.getKey(), listMap.get(entry.getKey().toString()));
//			}
//		}
		return mp;
	}

	/**
	 * Description: 将json字符内的所有键值转换为小写
	 * 
	 * @param psJson
	 * 				需要转换JSON字符串
	 * @return
	 * 				转换后的对象
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static Map<String, Object> convertJsonKeysToLC(String psJson) {
		Map<String, Object> _mapTmp = new HashMap<String, Object>();
		try {
			String _sJsonTmp = "";
			List<Map<String, Object>> _lstJson = parseArray(psJson);

			String _sValue = "";
			for (Map<String, Object> map : _lstJson) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					_sValue = map.get(key).toString();
					_mapTmp.put(key.toLowerCase(), (null == _sValue || _sValue
							.equals("")) ? "" : (_sValue));
				}
			}
		} catch (Exception e) {
			//new GnetException(e.getMessage());
		}
		return _mapTmp;
	}
	
	/**
	 * 将对象转为Json字符串,默认时间格式
	 * 
	 * @authoor 
	 *  2014年4月17日 下午2:40:29
	 * 
	 * @param object
	 * @return
	 */
	public static String serializeObject(Object object) {
		return JSON.toJSONString(object,
				SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 将对象序列化为Json字符串
	 * <p>
	 * 如果对象包含时间类型，则默认一律转为"yyyy-MM-dd HH:mm:ss"格式.
	 * <p>
	 * 并且如果对象的某个属性为null,则不会将该属性添加至Json中
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:14:10</h1>
	 * <p>
	 * 
	 * @param object
	 *            需要序列化的对象
	 * @return 序列化后的Json
	 */
	public static String serializeObjectIgnoreNullValue(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * 将Java对象序列化为为Json字符串
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:21:33</h1>
	 * <p>
	 * 
	 * @param obj
	 *            需要序列化的对象
	 * @param features
	 *            序列化的特征,可多个项
	 * @return 序列化后的Json
	 */
	public static String serializeObject(Object obj,
			SerializerFeature... features) {
		return JSON.toJSONString(obj, features);
	}
}
