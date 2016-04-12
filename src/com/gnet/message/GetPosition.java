package com.gnet.message;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// zdoz 的地址转换
public class GetPosition {

	static double pi = 3.14159265358979324;
	static double a = 6378245.0;
	static double ee = 0.00669342162296594323;

	static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(x > 0 ? x : -x);
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(x > 0 ? x : -x);
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
				* pi)) * 2.0 / 3.0;
		return ret;
	}

	static int outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return 1;
		if (lat < 0.8293 || lat > 55.8271)
			return 1;
		return 0;
	}

	static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	public static String transgpshx(double lng, double lat) {

		String postion = "";
		if (1 == outOfChina(lat, lng)) {
			postion = lng + "," + lat;
			return postion;
		}
		double dLat = transformLat(lng - 105.0, lat - 35.0);
		double dLon = transformLon(lng - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

		//double x = lng + dLon, y = lat + dLat;
		//double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		//double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		/*Double thisLng = z * Math.cos(theta) + 0.0065;
		Double thisLat = z * Math.sin(theta) + 0.006;*/
		Double thisLng = lng + dLon;
		Double thisLat = lat + dLat;
		postion = thisLng + "," + thisLat;
		return postion;

	}
	public static String transgpsbd(double x, double y) {

		String postion = "";
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		Double thisLng = z * Math.cos(theta) + 0.0065;
		Double thisLat = z * Math.sin(theta) + 0.006;
		postion = thisLng + "," + thisLat;
		return postion;

	}
	
	public static void main(String[] args) {
		
		String res = transgpsbd(116.32290349532427,39.95704397015557);
		
		System.out.println(res);
	}
	
}
