package com.gnet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

//import com.gnet.iot.utils.RestfulUtil;

//import utils.HttpClientUtil;

public class HttpUtils {
	/**
	 * http请求示例
	 * @param httpUrl
	 * @param httpArg
	 * @return
	 */
	public static String request(String httpUrl, String httpArg) {		
	    BufferedReader reader = null;		
	    String result = null;		
	    StringBuffer sbf = new StringBuffer();		
	    httpUrl = httpUrl + "?" + httpArg;	
	    System.out.println(httpUrl);
	    try {		
	        URL url = new URL(httpUrl);		
	        HttpURLConnection connection = (HttpURLConnection) url		
	                .openConnection();		
	        connection.setRequestMethod("POST");		
	        connection.connect();
	        connection.setReadTimeout(2000);
	        InputStream is = connection.getInputStream();		
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;		
	        while ((strRead = reader.readLine()) != null) {		
	            sbf.append(strRead);		
	            sbf.append("\r\n");		
	        }		
	        reader.close();		
	        result = sbf.toString();		
	    } catch (Exception e) {		
	        e.printStackTrace();		
	    }		
	    return result;		
	}	
	public static void main(String[] args) throws ParseException, EncoderException, IOException, IOException {
/*		JSONObject obh2 = JSONObject.parseObject("{}");
		System.out.println(obh2.size());
*/		String URL = "http://123.126.34.168:9092/supernove/mbclient";
		//String URL = "http://localhost:8086/supernove/mbclient";
	//	String params = "{\"at\":\"createHealthReport\",\"vs\":\"14\",\"username\":\"lizhaoxia\"}";
		//String messageString=java.net.URLEncoder.encode("{\"MessageHead\":\"+RESP:GTSOS==_)(*&^%$#@!;/*/\",\"ProtocolVersion\":\"1A0102\",\"UniqueID\":\"860599000057489\",\"DeviceName\":\"GL300\",\"ReportID\":\"0\",\"ReportType\":\"0\",\"Number\":\"1\",\"GPSAccuracy\":\"4\",\"Speed\":\"22.3\",\"Azimuth\":\"194\",\"Altitude\":\"96.9\",\"Longitude\":\"116.327874\",\"Latitude\":\"39.961480\",\"GPSUTCtime\":\"20141228111153\",\"MCC\":\"0460\",\"MNC\":\"0000\",\"LAC\":\"102C\",\"CellID\":\"8CCE\",\"OdoMileage\":\"388.5\",\"batteryPercentage\":\"98\",\"SendTime\":\"20141228111154\",\"CountNumber\":\"32B5$\"}");
	//	String httpUrl = "http://114.112.90.40:9928/Gnet_Info/web/actionController/requestorRep";
//		String httpArg = "topic=123&message="+messageString+"&timeout=2000";
		//String httpArg = "params={\"at\":\"getSMSVerification\",\"vs\":\"14\",\"phone\":\"18911132523\"}";	
		String httpArg = "params={\"at\":\"userRegister\",\"vs\":\"14\",\"phone\":\"18911132523\",\"sms\":\"681031\",\"residentID\":\"430921199002013872\",\"password\":\"dfdf\"}";
		//String httpArg = "params={\"at\":\"perfectRegister\",\"vs\":\"14\",\"phone\":\"18614065381\",\"email\":\"ncw@163.com\",\"realName\":\"倪成伟\",\"password\":\"dfdf\",\"username\":\"nichengwei123\"}";
		//String httpArg = "params={\"at\":\"homePageInfo\",\"vs\":\"14\",\"username\":\"ceshi1\"}";
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date date=new Date();
		//String start = "2014-12-07 11:58:05";
		//String end = "2014-12-25 11:58:05";
		/*Date start = sdf.parse(startTime);
		Date end = sdf.parse(endTime);*/
		//String httpArg = "params={\"at\":\"sportRecord\",\"vs\":\"14\",\"username\":\"lizhaoxia\",\"uri\":\"sportRecord\",\"type\":\"sportRecord_table\",\"starttime\":\""+start+"\",\"endtime\":\""+end+"\"}";
		String httpArg1 = "params=";
		URLCodec c = new URLCodec("UTF-8");
		//String httpArg2 = c.encode("{\"at\":\"sportRecord\",\"vs\":\"14\",\"username\":\"lizhaoxia\",\"uri\":\"sportRecord\",\"type\":\"sportRecord_table\",\"starttime\":\""+start+"\",\"endtime\":\""+end+"\"}");
		//String httpArg2 = c.encode("{\"at\":\"locationMessage\",\"vs\":\"14\",\"type\":\"locationMessage_table\",\"username\":\"lizhaoxia\",\"starttime\":\""+start+"\",\"endtime\":\""+end+"\"}");
		//String httpArg2 = c.encode("{\"at\":\"muscle\",\"vs\":\"14\",\"username\":\"lizhaoxia\", \"type\":\"muscle\" }");
		//String httpArg2 = c.encode("{\"at\":\"modifyFlags\",\"uri\":\"bodyhighweight\",\"username\":\"lizhaoxia\",\"dataId\":\"0052992a-3fd5-11e5-93eb-00163e0001c4\",\"vs\":\"14\"}");
		//String httpArg2 = c.encode("{\"at\":\"handWritten\", \"vs\":\"14\", \"uri\":\"BMR\", \"bmr\":\"12\",\"type\":\"customuserinbody\", \"checktime\":\"2015-11-04 18:20:20\", \"username\":\"lizhaoxia\"}");
		//String httpArg2 = c.encode("{\"at\":\"perfectRegister\",\"vs\":\"14\",\"username\":\"18601103158\",\"email\":\"ncw@163.com\",\"realName\":\"倪成伟\",\"password\":\"dfdf\"}");
		//String httpArg2 = c.encode("{\"at\":\"modifyPassword\",\"vs\":\"14\",\"uri\":\"phone\",\"value\":\"18614065381\"}");
		//String httpArg2 = c.encode("{\"at\":\"modifyPassword\",\"vs\":\"14\",\"email\":\"34660836@qq.com\"}");
		//String httpArg2 = c.encode("{\"at\":\"modifyDetection\", \"vs\":\"14\", \"type\":\"inBody\", \"dataId\":\"00eab30e-470c-11e5-8524-00163e0001c4\",\"checktime\":\"2015-11-04 17:55:55\", \"uri\":\"update\"}");

	//	String httpArg2 = c.encode("{'username' : 'lizhaoxia','start' : '2015-02-1 16:49:15','at' : 'historyRecord','vs' : '14','type' : 'chart','uri' : 'bone','password' : 'e10adc3949ba59abbe56e057f20f883e','end' : '2015-09-25 16:49:15'}");
		//String httpArg2 = c.encode("{'at': 'checkClientVersion','vs': '14','cl':'anzhi','cv':'4.2.1.11'}");
		/*String httpArg2 = c.encode("{'at': 'historyHealthAssessment','vs': '14','startTime':'2015-09-26','endTime':'2015-09-26','username':'lizhaoxia'}");
		String httpArg = httpArg1+httpArg2;*/
		////String lizhaoxiaName = "lizhaoxia";
		////String lizhaoxiaUserId = "8a484ade-fda0-42ae-8afb-9ce0d5382764";
		//String httpArg = "params={\"at\":\"sportRecord\",\"vs\":\"14\",\"os\":\"android\",\"username\":\"lizhaoxia\",\"password\":\"21232f297a57a5a743894a0e4a801fc3\",\"type\":\"table\",\"start\":\"2014-1-05\",\"end\":\"2015-8-25\",\"uri\":\"sportRecord\"}";
		//String httpArg = "params={\"at\":\"getHistoryHealthAssessment\",\"vs\":\"14\",\"os\":\"android\",\"username\":\"lizhaoxia\",\"dataId\":\"68233a37f96d4d1abb1d344c3ac38621\"}";
        //String httpArg = "params={\"at\":\"saveHealthReport\",\"vs\":\"14\",\"os\":\"android\",\"username\":\"lizhaoxia\",\"dataId\":\"b11ed6e1dcf2470fa6a4a57dddb3d207\"}";
		//String httpArg = "params={\"at\":\"thrend\",\"vs\":\"14\",\"startTime\":\"2015-09-20\",\"endTime\":\"2015-09-23\",\"username\":\"lizhaoxia\",\"type\":\"inBody\"}";
		//System.out.println("httpArg:"+httpArg);
//		String jsonResult = request(URL, httpArg);
	//	HttpClientUtil.get("http://localhost:8086/sypro/DeviceAvailablePush?user_id=031fa654-7772-40b5-84da-760f9e0f9b06&device_id=007");
		//String jsonResult = request(URL, httpArg1+httpArg2);
		/*JSONObject obh = JSONObject.parseObject(jsonResult);
		String obj = obh.getString("data");
		JSONObject obh1 = JSONObject.parseObject(obj);
		String jarr = obh1.getString("collectItems");
		JSONArray jarr1 = JSONArray.parseArray(jarr);
		System.out.println(jarr1.get(0));
		System.out.println(obh.size());*/
	/*	JSONObject obh1 = JSONObject.parseObject(jsonResult);
		JSONObject obh2 = obh1.getJSONObject("info");
		JSONObject obh3 = obh2.getJSONObject("details");
		JSONObject obh4 = obh3.getJSONObject("BMI");
		JSONArray obh5 = obh4.getJSONArray("resultSet");*/
//		System.out.println(jsonResult);
		/*System.out.println(obh5.toString());*/
		String endpoint = "http://localhost:8080/MyGnetIOT/services/WebServiceRest/registCompany/";
//		String endpoint = "http://192.168.1.131:8080/MyGnetIOT/services/WebServiceRest/companyLogin/";
//		String endpoint = "http://192.168.1.131:8080/MyGnetIOT/services/WebServiceRest/synchroDevicesType/";
//		String endpoint = "http://192.168.1.131:8080/MyGnetIOT/services/WebServiceRest/checkSameNickName/";
//		String endpoint = "http://localhost:8080/MyGnetIOT/services/WebServiceRest/checkCompanyIdIsExist/";
		String companyId = "15210579620";//15210579620   010lza88022843
		String companyPwd = "123456";
		String companyId1 = "010lza39749450001";
//		 String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"cid\":\""+companyId+"\"}}";
//		 String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{}}";
		 String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""+companyId+"\",\"companyPwd\":\""+companyPwd+"\",\"companyName\":\"凯英信业\",\"companyInterAddr\":\"http://www.keytec.com.cn\",\"companyAddr\":\"北京市海淀区学院路30号科大天工大厦B座18层\",\"companyPhone\":\"12345678909\"}}";
//		 String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""+companyId+"\",\"nickName\":\"派大星\"}}";
//		String psJson = "{\"GMSG_HEAD\":{\"Version\":\"1.0\",\"AccessType\":\"PC\",\"Sign\":\"N_N_00001\",\"Token\":\"keytec\"},\"GMSG_IOT\":{\"companyId\":\""+companyId+"\"}}";
//		 String URL = "http://123.126.34.168:9092/supernove/mbclient";
//		 String jsonResult = request(endpoint, psJson);	
//		 System.out.println(jsonResult);
		 
		 RestfulUtil restfulUtil=new RestfulUtil();
		 String result = restfulUtil.sendHttpRequest(endpoint, psJson);
		 System.out.println("执行结果："+result);
	}
}
