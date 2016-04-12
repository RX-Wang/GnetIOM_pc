
/**
 * 查询设备的实时位置
 * @returns {Boolean}
 */
function selPosition(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
	
		var uniqueId = $("#forSearchUniqueId").val();
		if(uniqueId != null && "" != uniqueId){
	//		layer.alert("uniqueId:"+uniqueId);
			$.ajax({
				async : false,
				type : "POST",
				url : "../../web/devicesController/selPositionByUniqueId",
				data : {
					"uniqueId" : uniqueId
				},
				dataType : "",
				success : function(retJs){
					retJs = JSON.parse(retJs);
					var result = retJs.DataResult;
					var data = retJs.message;
					
					if(result == 'pass'){
						for(var i=0;i<data.length;i++){
							var longitude = data[i]["longitude"];
							var latitude = data[i]["latitude"];
							var nickName = data[i]["nick_name"];
							var address = data[i]["address"];
							var time = data[i]["time"];
							var uniqueId = data[i]["unique_id"];
	//						layer.alert("time:"+time);
		//					dotting(longitude, latitude);
							dotting1(longitude, latitude,nickName,address,time,uniqueId);
						}
					}else if(result == 'nocompanyInfo'){
						layer.alert(data);
						showMap();
					}else if(result == 'nodevice'){
						layer.alert(data);
						showMap();
					}else if(result == 'nohasposition'){
						layer.alert(data);
						showMap();
					}else{
						layer.alert(data);
						showMap();
					}
				},
				error : function(){
					layer.alert("查询异常...");
				}
			});
		}else{
			layer.alert("请输入要查询的设备串号");
			return false;
		}
	}else{
		layer.alert("您没有操作权限");
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
 * 根据经纬度在地图上打点(没有弹出框)
 * @param longitude
 * @param latitude
 */
function dotting(longitude,latitude){
	 // 百度地图API功能
    //GPS坐标
    var x = longitude;
    var y = latitude;
    var ggPoint = new BMap.Point(x,y);

    //地图初始化
    var bm = new BMap.Map("allmap");
    bm.centerAndZoom(ggPoint, 15);
    bm.addControl(new BMap.NavigationControl());

    //添加gps marker和label
//    var markergg = new BMap.Marker(ggPoint);
//    bm.addOverlay(markergg); //添加GPS marker
//    var labelgg = new BMap.Label("未转换的GPS坐标（错误）",{offset:new BMap.Size(20,-10)});
//    markergg.setLabel(labelgg); //添加GPS label

    //坐标转换完之后的回调函数
    translateCallback = function (data){
      if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);
        bm.addOverlay(marker);
        var label = new BMap.Label(null,{offset:new BMap.Size(20,-10)});
        marker.setLabel(label); //添加百度label
        bm.setCenter(data.points[0]);
      }
    }

    setTimeout(function(){
        var convertor = new BMap.Convertor();
        var pointArr = [];
        pointArr.push(ggPoint);
        convertor.translate(pointArr, 1, 5, translateCallback)
    }, 1000);
}

//带弹出框
function dotting1(longitude, latitude,nickName,address,time,uniqueId){
	
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(longitude,latitude);
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	map.centerAndZoom(point, 15);
	var opts = {
	  width : 200,     // 信息窗口宽度
	  height: 180,     // 信息窗口高度
	  title : nickName , // 信息窗口标题
	  enableMessage:false,//设置允许信息窗发送短息
	  message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
	}
	//window.location.href='../../web/devicesController/selDevByIdOrName'
	var tx = "<hr/>"+address+"<hr/>"+time+"<br/><a onclick=\"checkDeviInfo("+uniqueId+");\">查看</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"toTrackPage("+uniqueId+")\">追踪</a>";
	var infoWindow = new BMap.InfoWindow(tx, opts);  // 创建信息窗口对象
	marker.addEventListener("click", function(){          
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	});
}

function checkDeviInfo(uniqueId){
	window.location.href="../../web/pageController/toDevice?uniqueId="+uniqueId
//	$.ajax({
//		type : "POST",
//		url : "../../web/pageController/toDevice",
//		data : {
//			"uniqueId" : uniqueId
//		},
//		dataType : "",
//		success : function(){
//		
//		},
//		error : function(){
//			layer.alert("查看异常");
//		}
//	});
}


/**
 * 开启追踪模式
 */
function toTrackPage(uniqueId){
	layer.alert("正在开启追踪模式...");
//	alert("开启追踪。。。");
//	window.location.href="../../web/devicesController/toTrackPage?uniqueId="+uniqueId;
	$.ajax({
//		async : false,
		type : "POST",
		url : "../../web/devicesController/toTrackPage",
		data : {
			"uniqueId" : uniqueId
		},
		dataType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs)
			var result = retJs.DataResult;
			var data = retJs.message;
			var openTrackStatusResult = retJs.openTrackStatusResult;
			if(result == 'pass'){
				layer.msg(openTrackStatusResult,{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						window.location.href="../../web/devicesController/toPage?uniqueId="+data["uniqueId"]+"&curTime="+data["curTime"];
					}
				});
			}else if(result == 'hasbegin'){
				layer.msg('该设备已经开启追踪模式',{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						window.location.href="../../web/devicesController/toPage?uniqueId="+data["uniqueId"]+"&curTime="+data["curTime"];
					}
				});
			}else{
				layer.alert(openTrackStatusResult);
			}
		},
		error : function(){
			layer.alert("追踪异常");
		}
	});
	
	
}


