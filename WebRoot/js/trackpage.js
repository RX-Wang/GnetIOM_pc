$(function(){
	trackOnload();
});
/**
 * 查看历史轨迹按钮被点击
 */
$("#historyCheckBtn").click(selDevHasHistoryTrack);
$("#histroyCheckUniqueId").change(
		function(){
			if($("#histroyCheckUniqueId").val()==""){
				$("#historyTrace").empty();
			}else{
				selHistroy();
			}
		}
	);
/**
 * 查询当前集团下有历史轨迹的设备
 */
function selDevHasHistoryTrack(){
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/devicesController/selDevHasHistoryTrack",
		dateType : "",
		success : function(data){
			var _data = JSON.parse(data);
			if(_data.DataResult == "pass"){
				for(var i = 0 ; i < _data.message.length; i++){
					$("#histroyCheckUniqueId").append("<option value=\""+ _data.message[i].uniqueId +"\">"+ _data.message[i].uniqueId +"</option>");
				}
			}else{
				layer.alert("当前集团下没有已经保存历史轨迹的设备");
			}
		},
		error : function(){
				layer.alert(data.message);
			}
	});
}

/**
 * 加载事件
 */
function trackOnload(){
	var id = $("#uniqueId").val();
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var tx="";
	if(id!=null && id!=""){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/devicesController/beginTrack",
			data : {
				"uniqueId" : id,
				"curTime" : beginTime
			},
			dateType : "",
			success : function(retJs){
				retJs = JSON.parse(retJs);
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					var nickName = "";
					var power = ""
					var beginTime1="";
					var endTime1 = "";
					var longitude=[];
					var latitude=[];
					var points=new Array();
					var map = new Map();
					var abc="";
					var start=[];
					for(var i= 0;i<data.length;i++){
						if(i==(data.length-1)){
							nickName = data[i]["nickName"];
							power = data[i]["power"];
							endTime1 = data[i]["sendTime"];
						}
						if(i==0){
							beginTime1 = data[i]["sendTime"];
//							start.push(data[i]["longitude"]);
//							start.push(data[i]["latitude"]);
						}
						if(i==data.length-1){
							var a=[];
							a.push(data[i]["longitude"]);
							a.push(data[i]["latitude"]);
							points.push(a);
							start.push(data[i]["longitude"]);
							start.push(data[i]["latitude"]);
						}else{
							var b=[];
							b.push(data[i]["longitude"]);
							b.push(data[i]["latitude"]);
							
							points.push(b);
						}
					}
					deviceLocus(points,start);
//				var point =[[116.400,39.900],[116.383752,39.91334],
//				[116.410,39.9125],[116.384502,39.932241],
//				[116.402,39.910]];
					$("#power").html("电量："+power+"%");
					$("#beginTime").val(beginTime1.toString());
					$("#endTime").val(endTime1.toString());	
					$(".map_dev").empty();
					var deviceList = retJs.deviceList;
					if(deviceList !=null && deviceList != ""){
						for(var j=0;j<deviceList.length;j++){
	//						for(var k in deviceList[j]){
	//							if(k==id){
	//								tx += " <li><a onclick=\"trackPageATag("+k+")\" style=\"background:#f26d6d;\">"+deviceList[j][k]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+k+"')\"></i> </li>";
	//							}else{
	//								tx += " <li><a onclick=\"trackPageATag("+k+")\">"+deviceList[j][k]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+k+"')\"></i> </li>";
	//							}
	//						}
							if(deviceList[j]["uniqueId"]==id){
								tx += "<li><a onclick=\"trackPageATag("+deviceList[j]["uniqueId"]+")\" style=\"background:#f26d6d;\">"+deviceList[j]["nickName"]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+deviceList[j]["uniqueId"]+"')\"></i> </li>";
							}else{
								tx += " <li><a onclick=\"trackPageATag("+deviceList[j]["uniqueId"]+")\">"+deviceList[j]["nickName"]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+deviceList[j]["uniqueId"]+"')\"></i> </li>";
							}
						}
					}
//					tx+="<li><a onclick=\"showTime();\">查看最新时间</a><i class=\"glyphicon glyphicon-remove\"></i> </li>";
					$(".map_dev").append(tx);
				}else if(result == 'noInfo'){
					$(".map_dev").empty();
					var deviceList = retJs.deviceList;
					if(deviceList !=null && deviceList != ""){
						for(var j=0;j<deviceList.length;j++){
//							for(var k in deviceList[j]){
//								if(k==id){
//									tx += " <li><a onclick=\"trackPageATag("+k+")\" style=\"background:#f26d6d;\">"+deviceList[j][k]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+k+"')\"></i> </li>";
//								}else{
//									tx += " <li><a onclick=\"trackPageATag("+k+")\">"+deviceList[j][k]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+k+"')\"></i> </li>";
//								}
//							}
							
							if(deviceList[j]["uniqueId"]==id){
								tx += "<li><a onclick=\"trackPageATag("+deviceList[j]["uniqueId"]+")\" style=\"background:#f26d6d;\">"+deviceList[j]["nickName"]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+deviceList[j]["uniqueId"]+"')\"></i> </li>";
							}else{
								tx += " <li><a onclick=\"trackPageATag("+deviceList[j]["uniqueId"]+")\">"+deviceList[j]["nickName"]+"</a><i class=\"glyphicon glyphicon-remove\" onclick=\"closeTrack('"+deviceList[j]["uniqueId"]+"')\"></i> </li>";
							}
						}
					}
//					tx+="<li><a onclick=\"showTime();\">查看最新时间</a><i class=\"glyphicon glyphicon-remove\"></i> </li>";
					$(".map_dev").append(tx);
					$("#power").html("电量：未知");
					$("#endTime").val("");
					showMap();
				}else{
					showMap();
				}
			},
			error : function(){
				layer.alert("地图加载错误");
				showMap();
			}
		});
	}else{
		$(".map_dev").empty();
		$(".map_dev").append(tx);
		showMap();
	}
	setTimeout("trackOnload()", 5000);
}


//function showTime(){
//	layer.alert("最新开始时间："+$("#beginTime").val());
//	layer.alert("最新结束时间："+$("#endTime").val());
//}


/**
 * 保存历史轨迹
 */
function saveHistoryTrace(){
	var uniqueId = $("#uniqueId").val();
	if(uniqueId!="" && uniqueId!=null){
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		$.ajax({
			asycn : false,
			type : "POST",
			url : "../../web/devicesController/saveTrice",
			data : {
				"beginTime" : beginTime,
				"endTime" : endTime,
				"uniqueId" : uniqueId
			},
			dataType : "",
			success : function(retJs){
				retJs = JSON.parse(retJs);
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					layer.alert("保存历史轨迹成功");
				}else{
					layer.alert("保存历史轨迹失败，请稍后重试");
				}
			},
			error : function(){
				layer.alert("保存历史轨迹失败");
			}
		});
	}else{
		layer.alert("当前没有设备进入追踪模式");
	}
}


//设备切换
function trackPageATag(id){
	$("#uniqueId").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
	
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/devicesController/changeDev",
		data : {
			"uniqueId" : id
		},
		dateType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs);
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
				window.location.href="../../web/devicesController/toPage?uniqueId="+data["uniqueId"]+"&curTime="+data["curTime"];
			}else{
				layer.alert("切换设备失败,请稍后再试...");
				window.location.href="../../web/devicesController/toDevTrack";
			}
		}
	});
	
}
/**
 * 设备轨迹
 * @param data
 */
function deviceLocus(data,start){
	
	// 百度地图API功能
//	var map = new BMap.Map("allmap");    // 创建Map实例
//	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
//	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
//	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
//	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放 */
	
	if(data!=null && data!=""){
//		data = JSON.stringify(data);
		var map = new BMap.Map("allmap");
		var mPoint = new BMap.Point(start[0],start[1]);//116.404, 39.915
//		var mPoint = new BMap.Point(116.404, 39.915);//116.404, 39.915
		//	var marker = new BMap.Marker(mPoint); // 创建标注
		map.centerAndZoom(mPoint, 15);
		
		map.enableScrollWheelZoom();
		map.enableContinuousZoom(); 
		
		//	map.addOverlay(marker); // 将标注添加到地图中
		map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
		map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
//		map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
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
 * 查询历史轨迹
 */
function selHistroy(){
	var uniqueId = $("#histroyCheckUniqueId").val();
	if(uniqueId !=null && uniqueId!=""){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/devicesController/selHistoryTrace",
			data : {
				"uniqueId" : uniqueId
			},
			dateType : "",
			success : function(retJs){
				retJs=JSON.parse(retJs);
				var result = retJs.DataResult;
				var data = retJs.message;
				
				if(result == 'pass'){
					var tx="";
					for(var i =0;i<data.length;i++){
						data[i]["beginTime"];
						data[i]["endTime"];
						var time = data[i]["beginTime"]+"_"+data[i]["endTime"];//checkHistoryTraceById('"+data[i]["id"]+"')
						tx += "<tr>"
							+"<th style=\"text-align: center;\">"+time+"</th>"
							+"<th style=\"text-align: center;\"><a onclick=\"window.location.href='../../web/devicesController/toCheckHistoryTrace?time="+time+"&uniqueId="+uniqueId+"'\">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"delHistoryTrace('"+data[i]["id"]+"')\">删除</a></th>"
							+"</tr>";
					}
					$("#historyTrace").empty();
					$("#historyTrace").append(tx);
				}else{
					$("#historyTrace").empty();
					$("#historyTrace").append("");
					layer.alert("未找到该设备已保存的历史轨迹");
				}
			},
			error : function(){
				layer.alert("查询历史轨迹失败,请稍后再试");
			}
		});
	}
}



/**
 * 删除历史轨迹
 */
function delHistoryTrace(id){
//	var sure = confirm("确定要删除本条历史轨迹信息吗?");
	
	layer.msg('确定要删除本条历史轨迹信息吗?', {
	    time: 0 //不自动关闭
	    ,btn: ['确定', '取消']
	    ,yes: function(index){
	    	delTrack(id);
	        layer.close(index);
	        icon: 6;
	    }
	});
	
	
	

}

/**
 * 删除历史轨迹
 * @param id
 */
function delTrack(id){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/devicesController/delHistoryTrace",
			data : {
				"id" : id
			},
			dateType : "",
			success : function(retJs){
				retJs=JSON.parse(retJs);
				var result = retJs.DataResult;
				var data = retJs.message;
				
				if(result == 'pass'){
					layer.msg('删除成功',{
						time : 0
						,btn : ['确定']
						,yes : function(index){
							selHistroy();
							layer.close(index);
//							icon : 6;
						}
					});
					
//					layer.alert("删除成功");
					
				}else{
					layer.alert("删除失败");
				}
			},
			error : function(){
				layer.alert("删除设备历史轨迹失败,请稍后再试");
			}
		});
}



/**
 * 清空历史轨迹页面
 */
function clearTb(){
	$("#historyTrace").empty();
	var _option = $("#histroyCheckUniqueId").find(":first-child");
	$("#histroyCheckUniqueId").empty().append(_option);
}


/**
 * 关闭追踪模式
 * @param uniqueId
 */
function closeTrack(uniqueId){
//	layer.alert("关闭追踪..."+uniqueId);
	
	layer.msg('确定关闭追踪模式吗?',{
		time : 0,
		btn : ['确定','取消'],
		yes : function(index){
			layer.close(index);
			closeTrackStatus(uniqueId);
		}
	});
}

/**
 * 关闭追踪模式
 * @param uniqueId
 */
function closeTrackStatus(uniqueId){
	$.ajax({
		asycn : false,
		type : "POST",
		url : "../../web/devicesController/closeTrackStatus",
		data : {
			"uniqueId" : uniqueId
		},
		dataType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs);
			var result = retJs.DataResult;
			var data = retJs.message;
			var closeTrackStatusResult = retJs.closeTrackStatusResult;
			if(result == 'pass'){
				$("#uniqueId").val("");
				$("#beginTime").val("");
				$("#endTime").val("");
				layer.msg(closeTrackStatusResult,{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						window.location.href="../../web/devicesController/toPage?uniqueId="+data["uniqueId"]+"&curTime="+data["curTime"];
					}
				});
			}else{
				layer.alert(closeTrackStatusResult);
			}
		},
		error : function(){
			layer.alert("关闭追踪模式失败,请稍后再试...");
		}
	});
	
}














