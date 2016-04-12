$(function(){
	historyMapOnload();
});

/**
 * 加载事件
 */
function historyMapOnload(){

//	layer.alert("这是追踪页面");
	var time = $("#time").val();
	var uniqueId = $("#uniqueId").val();
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/devicesController/checkHistoryTrace",
		data : {
			"time" : time,
			"uniqueId" : uniqueId
		},
		dateType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs);
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
				var points=new Array();
				var start=[];
				for(var i= 0;i<data.length;i++){
					if(i==0){
						var a=[];
						start.push(data[i]["longitude"]);
						start.push(data[i]["latitude"]);
						a.push(data[i]["longitude"]);
						a.push(data[i]["latitude"]);
						points.push(a);
					}else{
						var b=[];
						b.push(data[i]["longitude"]);
						b.push(data[i]["latitude"]);
						points.push(b);
					}
				}
				deviceLocus(points,start);
			}else{
				layer.alert(data);
				showMap();
			}
		},
		error : function(){
			layer.alert("地图加载错误");
		}
	});
}

/**
 * 展示地图
 */
function showMap(){
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放 */
}



/**
 * 历史轨迹打点
 * @param data
 */
function deviceLocus(data,start){
//	layer.alert("data:"+data);
	
	// 百度地图API功能
//	var map = new BMap.Map("allmap");    // 创建Map实例
//	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
//	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
//	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放 */
	
	if(data!=null && data!=""){
//		data = JSON.stringify(data);
		$("#allmap").empty();
		var map = new BMap.Map("allmap");
		var mPoint = new BMap.Point(start[0],start[1]);//116.404, 39.915
//		var mPoint = new BMap.Point(116.404, 39.915);//116.404, 39.915
//		var marker = new BMap.Marker(mPoint); // 创建标注
		map.centerAndZoom(mPoint, 15);
		
		map.enableScrollWheelZoom();
		map.enableContinuousZoom(); 
		
//		map.addOverlay(marker); // 将标注添加到地图中
		map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
		map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
		map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
		map.addControl(new BMap.NavigationControl({
			anchor : BMAP_ANCHOR_TOP_RIGHT,
			type : BMAP_NAVIGATION_CONTROL_SMALL
		}));

		var isIn;

		//定点值
//		var point =[[116.400,39.900],[116.383752,39.91334],
//				[116.410,39.9125],[116.384502,39.932241],
//				[116.402,39.910]];
		
		
		var point = data;
		var pointArray = new Array();
		var geoc = new BMap.Geocoder();
		var pos_point = [];
		var myIcon1 = new BMap.Icon(
				"http://developer.baidu.com/map/jsdemo/img/dest_markers.png",
				new BMap.Size(42, 34), {
					imageOffset : new BMap.Size(0, 0)
				});
		var myIcon2 = new BMap.Icon(
				"http://developer.baidu.com/map/jsdemo/img/dest_markers.png",
				new BMap.Size(42, 34), {
					imageOffset : new BMap.Size(0, -34)
				});
		var markers = [];
		for (var i = 0; i < point.length; i++) {
			pos_point[i] = new BMap.Point(point[i][0], point[i][1]);
			if (i == 0) {
				var pos_marker = new BMap.Marker(pos_point[i], {
					icon : myIcon1
				});

			} else if (i == point.length - 1) {
				pos_marker = new BMap.Marker(pos_point[i], {
					icon : myIcon2
				});
			} else {
				pos_marker = new BMap.Marker(pos_point[i]); // 创建点
			}
			markers.push(pos_marker);
			//map.addOverlay(pos_marker); //增加点
			pos_marker.addEventListener("click", function(e) {
				geoc.getLocation(e.point, function(e){
					addComp = e.addressComponents;
					layer.alert("您所在位置经纬度为：" + e.point.lng + ", " + e.point.lat + "\n您所在位置为："
						+ addComp.province + ", " + addComp.city + ", "
						+ addComp.district + ", " + addComp.street + ", "
						+ addComp.streetNumber);
				});
			});
		}
		var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		//让所有点在视野范围内
		map.setViewport(pointArray);
		//画轨迹，将定点用折线连接起来
		polyline = new BMap.Polyline(pos_point, {
			strokeColor : "blue",
			strokeWeight : 3,
			strokeOpacity : 0.5
		});
		map.addOverlay(polyline);
		
	}else{
		var map = new BMap.Map("allmap");    // 创建Map实例
		map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放 */
	}
	
}

































