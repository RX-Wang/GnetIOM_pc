package com.gnet.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json相关操作类
 * <p>
 * 基于 {@link com.alibaba.fastjson}
 * 进行封装。如果已封装的方法不能满足您的需求。可以在此类基础上进行扩充。某些方法可能需要某些来自com.alibaba
 * .fastjson的类的支持,这将是更高级的用法。但是为了以后的方法更新，最好不要使用这些方法。尽量选择封装或简化。如果使用，将不能保证以后的兼容性。
 * <p>
 * <Strong>Date: </Strong> 2014年8月13日 下午4:31:59
 * 
 * @author Spartacus
 */
public class Json {

	/**
	 * 将对象序列化为Json字符串
	 * <p>
	 * 如果对象包含时间类型，则默认一律转为"yyyy-MM-dd HH:mm:ss"格式
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:14:10</h1>
	 * <p>
	 * 
	 * @param object
	 *            需要序列化的对象
	 * @return 序列化后的Json
	 */
	public static String serializeObject(Object obj) {
		// SerializerFeature.DisableCircularReferenceDetect
		// 默认fastjson是打开索引引用的。如果是null对象，或者对象多特定情况下，引用就会出$ref等字眼。
		return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
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
	public static String serializeObject(Object obj, SerializerFeature... features) {
		return JSON.toJSONString(obj, features);
	}

	/**
	 * 将对象序列化为Json 并且将其中的时间类型序列化为指定的格式
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:46:02</h1>
	 * <p>
	 * 
	 * @param obj
	 *            需要序列化的对象
	 * @param datePatten
	 *            时间格式 例如 "yyyy-MM-dd HH:mm:ss"
	 * @return 序列化后的Json
	 */
	public static String serializeObjectWithDateFormat(Object obj, String datePatten) {
		return JSON.toJSONStringWithDateFormat(obj, datePatten, SerializerFeature.WriteMapNullValue);
	}

	/**
	 * 将Json反序列化为Object对象
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午5:11:26</h1>
	 * <p>
	 * 
	 * @param psJson
	 *            要反序列化的Json
	 * @return 反序列化后的对象
	 */
	public static final Object parse(String psJson) {
		return JSON.parseObject(psJson);
	}

	/**
	 * 将Json反序列化为对象
	 * 
	 * <pre>
	 * Person p = JsonUtil.parese(Person.class, json);
	 * </pre>
	 * <p>
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:05:42</h1>
	 * <p>
	 * 
	 * @param clazz
	 *            对象的类
	 * @param json
	 *            要反序列化的Json
	 * @return 反序列化后的对象
	 */
	public static <T> T parse(Class<T> clazz, String json) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 将Json反序列化为对象(带泛型)
	 * 
	 * <pre>
	 * Map&lt;String, Object&gt; map = JsonUtil.parese(new TypeReference&lt;Map&lt;String, Object&gt;&gt;() {
	 * }, json);
	 * </pre>
	 * <p>
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:05:42</h1>
	 * <p>
	 * 
	 * @param typeReference
	 *            带泛型的类的声明
	 * @param json
	 *            要反序列化的Json
	 * @return 反序列化后的对象
	 */
	public static <T> T parse(TypeReference<T> typeReference, String json) {
		return JSON.parseObject(json, typeReference);
	}

	/**
	 * 将Json反序列化为列表
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午5:13:00</h1>
	 * <p>
	 * 
	 * @param psJson
	 *            要反序列化的Json
	 * @return 反序列化后的列表
	 */
	@SuppressWarnings("rawtypes")
	public static final List parseArray(String psJson) {
		return JSON.parseArray(psJson);
	}

	/**
	 * 将Json反序列化为对象列表
	 * 
	 * <pre>
	 * List&lt;Map&gt; map = JsonUtil.pareseArray(Map.class, json);
	 * </pre>
	 * <p>
	 * <h1>created by Spartacus at 2014年8月13日 下午4:09:34</h1>
	 * <p>
	 * 
	 * @param clazz
	 *            对象的类
	 * @param json
	 *            要反序列化的Json
	 * @return 反序列化后的对象列表
	 */
	public static <T> List<T> parseArray(Class<T> clazz, String json) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * 将非json格式的消息中的可能引起json关键字错误的符号进行替换
	 * <p>
	 * <h1>created by Spartacus at 2014年10月9日 下午4:53:49</h1>
	 * <p>
	 * 
	 * @param message
	 * @return
	 */
	public static String formatJsonMessage(String message) {
		String _s = message.replace(':', '：').replace(',', '，').replace('{', '<').replace('}', '>').replace('[', '(').replace(']', ')').replace('\n', '\t').replace('\\', '/').replace('"', '`');
		return _s;
	}
}
