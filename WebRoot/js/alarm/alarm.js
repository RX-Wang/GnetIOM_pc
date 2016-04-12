function toMsg(temp){
	//layer.alert(12345);
	var url="";
	var toUrl="";
	if(""==temp || null==temp){
		$(".o_header").find("ul").children().each(function(index){
			if($(this).hasClass('active')){
				switch(index){
					case 0:
						temp='all';
						break;
					case 1:
						temp='alarm';
						break;
					case 2:
						temp='thing';
						break;
					case 3:
						temp='fault';
						break;
				}
			}
		});
	}
	
	if(temp=='all'){
		//查询全部信息
		url="../../web/alarmController/findAllMsg";
		toUrl="../../web/alarmController/toAllMsg";
	}else if(temp=='alarm'){
		//查看全部报警信息
		url="../../web/alarmController/findAlarmMsg";
		toUrl="../../web/alarmController/toAlarm";
	}else if(temp=='thing'){
		//查看全部事件消息
		url="../../web/alarmController/findThingMsg";
		toUrl="../../web/alarmController/toThing";
	}else if(temp=='fault'){
		//查看全部故障事件
		url="../../web/alarmController/findFaultMsg";
		toUrl="../../web/alarmController/toFault";
	}
	$.ajax({
		type : "POST",
		url : url,
		data : {},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$("#AllMsg").empty();
//				window.location.href=toUrl;
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var devicesName = data[i].devicesName;
					var uniqueId = data[i].uniqueId;
					var alarmType = data[i].alarmType;
					var address = data[i].address;
					var alarmTypeMsg = data[i].alarmTypeMsg;
					
					if(alarmTypeMsg == "" || alarmTypeMsg == null){
						alarmTypeMsg = "未知";
					}
					
					if(address=="undefined" || null ==address){
						address="";
					}
//					var e=new Date(data[i].sendTime);
//					var sendTime =formatDate(e);
					var sendTime = data[i].sendTime;
					
					var handle = data[i].handle;
					var hd = '0';
					if(handle=='0'){
						hd = "<a onclick=\"dealWith('"+id+"')\">处理</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"ignore('"+id+"')\">忽略</a>";
					}else if(handle=='1'){
						hd = "已处理";
					}else if(handle=='2'){
						hd = "已忽略";
					}
					var cls="danger";
					if(alarmType=="报警"){
						cls="danger";
					}else if(alarmType=="事件"){
						cls="warning";
					}else if(alarmType=="故障"){
						cls="active";
					}
					var tx = "<tr>"
						+"<tr class=\""+cls+"\">"
						+"<td style=\"width: 18%;\">"+devicesName+"</td>"
						+"<td style=\"width: 18%;\">"+uniqueId+"</td>"
						+"<td style=\"width: 18%;\">"+alarmTypeMsg+"</td>"
						+"<td style=\"width: 19%;\">"+address+"</td>"
						+"<td style=\"width: 10%;\">"+sendTime+"</td>"
						+"<td style=\"width: 9%;\">"+hd+"</td>"
                    +"</tr>"
					$("#AllMsg").append(tx);
				}
			}else if(result=='nodata'){
				$("#AllMsg").empty();
				$("#AllMsg").append("");
//				layer.alert(data);
			}else{
				$("#AllMsg").empty();
				$("#AllMsg").append("");
				layer.alert("数据加载异常...");
			}
		},
		error : function(){
			$("#AllMsg").empty();
			$("#AllMsg").append("");
			layer.alert("加载数据出错...");
		}
	});
}




/**
 * 处理
 * @param id
 */
function dealWith(id){
//	layer.alert("id:"+id);
	
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/alarmController/dealWithMsg",
			data : {
				id : id
			},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
					alarmOnload("");
				}else{
					layer.alert(data);
				}
			}});
	}else{
		layer.alert("您没有操作权限");
	}
}

/**
 * 忽略
 * @param id
 */
function ignore(id){
//	layer.alert("id:"+id);
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/alarmController/ignoreMsg",
			data : {
				id : id
			},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
					alarmOnload("");
				}else{
					layer.alert(data);
				}
			}});
	}else{
		layer.alert("您没有操作权限");
	}
}


//查询所有信息
function findAllMsg(){
	$.ajax({
		type : "POST",
		url : "../../web/alarmController/findAllMsg",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$("#tbAllMsg").empty();
				for(var i=0;i<data.length;i++){
					var devicesName = data[i].devicesName;
					var uniqueId = data[i].uniqueId;
					var alarmType = data[i].alarmType;
					var address = data[i].address;
					var alarmTypeMsg = data[i].alarmTypeMsg;
					
					if(alarmTypeMsg == "" || alarmTypeMsg == null){
						alarmTypeMsg = "未知";
					}
					
					if(address=="undefined" || null ==address){
						address="";
					}
//					var e=new Date(data[i].sendTime);
//					var sendTime =formatDate(e);
					var sendTime =  data[i].sendTime;
					var cls="tb_bg1";
					if(alarmType=="报警"){
						cls="tb_bg4";
					}else if(alarmType=="事件"){
						cls="tb_bg1";
					}else if(alarmType=="故障"){
						cls="tb_bg2";
					}
					var tx = "<tr>"
						+"<td width=\"3%\"  class=\""+cls+"\"></td>"
						+"<td style=\"width:20.1%\">"+devicesName+"</td>"
						+"<td style=\"width:20.2%\">"+uniqueId+"</td>"
						+"<td style=\"width:20.1%\">"+alarmTypeMsg+"</td>"
						+"<td style=\"width:20.1%\">"+address+"</td>"
						+"<td style=\"width:19.3%\">"+sendTime+"</td>"
                    +"</tr>"
					$("#tbAllMsg").append(tx);
				}
				
			}else if(result=='nodata'){
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
//				layer.alert(data);
			}else{
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
				layer.alert("数据加载异常...");
			}
		}});
//	setTimeout("alarmOnload('')", 10000);
}

//查询所有未处理的消息总条数
function findAllUntreatedNum(){
	$.ajax({
		type : "POST",
		url : "../../web/alarmController/findAllUntreatedNum",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$(".badge-warning").html(data);
			}else{
				$(".badge-warning").html("0");
				layer.alert("数据加载异常");
			}
		}});
	
}


//查询所有未处理的报警信息
function findAlarmMsg(){
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findAlarmMsg",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$("#tbAllMsg").empty();
				for(var i=0;i<data.length;i++){
					var devicesName = data[i].devicesName;
					var uniqueId = data[i].uniqueId;
					var alarmType = data[i].alarmType;
					var address = data[i].address;
					var alarmTypeMsg = data[i].alarmTypeMsg;
					
					if(alarmTypeMsg == "" || alarmTypeMsg == null){
						alarmTypeMsg = "未知";
					}
					
					
					if(address=="undefined" || null ==address){
						address="";
					}
					var sendTime = data[i].sendTime;
					
					var tx="<tr>"
						+"<td width=\"36\"><em class=\"tb_bg1\"></em></td>"
						+"<td width=\"20%\">"+devicesName+"</td>"
						+"<td width=\"20%\">"+uniqueId+"</td>"
						+"<td width=\"20%\">"+alarmTypeMsg+"</td>"
						+"<td width=\"20%\">"+address+"</td>"
						+"<td width=\"20%\">"+sendTime+"</td>"
						+"</tr>";
					$("#tbAllMsg").append(tx);
				}
				
			}else if(result=='nodata'){
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
//				layer.alert(data);
			}else{
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
				layer.alert("数据加载异常...");
			}
		}});
//	setTimeout("alarmOnload('')", 10000);
	
}


//查询所有未处理的报警信息总条数
function findAlarmMsgNum(){
//	var count = 0;
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findAlarmMsgNum",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$(".badge-danger").html(data);
			}else{
				$(".badge-danger").html("0");
				layer.alert("数据加载异常");
			}
		}});
	
}


//查询所有未处理的事件信息
function findThingMsg(){
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findThingMsg",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$("#tbAllMsg").empty();
				for(var i=0;i<data.length;i++){
					var devicesName = data[i].devicesName;
					var uniqueId = data[i].uniqueId;
					var alarmType = data[i].alarmType;
					var address = data[i].address;
					var alarmTypeMsg = data[i].alarmTypeMsg;
					
					if(alarmTypeMsg == "" || alarmTypeMsg == null){
						alarmTypeMsg = "未知";
					}
					
					if(address=="undefined" || null ==address){
						address="";
					}
					var sendTime = data[i].sendTime;
//					}
					
					var tx="<tr>"
						+"<td width=\"36\"><em class=\"tb_bg1\"></em></td>"
						+"<td width=\"20%\">"+devicesName+"</td>"
						+"<td width=\"20%\">"+uniqueId+"</td>"
						+"<td width=\"20%\">"+alarmTypeMsg+"</td>"
						+"<td width=\"20%\">"+address+"</td>"
						+"<td width=\"20%\">"+sendTime+"</td>"
						+"</tr>";
					$("#tbAllMsg").append(tx);
				}
				
			}else if(result=='nodata'){
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
//				layer.alert(data);
			}else{
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
				layer.alert("数据加载异常...");
			}
		}});
//	setTimeout("alarmOnload('')", 10000);
	
}

//查询所有未处理的事件信息的总条数
function findThingMsgNum(){
//	var count = 0;
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findThingMsgNum",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$(".badge-success").html(data);
			}else{
				$(".badge-success").html("0");
				layer.alert("数据加载异常");
			}
		}});
}



//查询所有未处理的故障信息
function findFaultMsg(){
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findFaultMsg",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$("#tbAllMsg").empty();
				for(var i=0;i<data.length;i++){
					var devicesName = data[i].devicesName;
					var uniqueId = data[i].uniqueId;
					var alarmType = data[i].alarmType;
					var address = data[i].address;
					var alarmTypeMsg = data[i].alarmTypeMsg;
					
					if(alarmTypeMsg == "" || alarmTypeMsg == null){
						alarmTypeMsg = "未知";
					}
					
					
					if(address=="undefined" || null ==address){
						address="";
					}
					var sendTime = data[i].sendTime;
					
					var tx="<tr>"
						+"<td width=\"36\"><em class=\"tb_bg1\"></em></td>"
						+"<td width=\"20%\">"+devicesName+"</td>"
						+"<td width=\"20%\">"+uniqueId+"</td>"
						+"<td width=\"20%\">"+alarmTypeMsg+"</td>"
						+"<td width=\"20%\">"+address+"</td>"
						+"<td width=\"20%\">"+sendTime+"</td>"
						+"</tr>";
					$("#tbAllMsg").append(tx);
				}
				
			}else if(result == 'nodata'){
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
//				layer.alert(data);
			}else{
				$("#tbAllMsg").empty();
				$("#tbAllMsg").append("");
				layer.alert("数据加载异常...");
			}
		}});
	
//	setTimeout("alarmOnload('')", 10000);
}

//查询所有未处理的故障信息的总条数
function findFaultMsgNum(){
//	var count = 0;
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findFaultMsgNum",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$(".badge-primary").html(data);
			}else{
				$(".badge-primary").html("0");
				layer.alert("数据加载异常");
			}
		}});
}

function alarmOnload(temp){
	
	findAllUntreatedNum();
	findAlarmMsgNum();
	findThingMsgNum();
	findFaultMsgNum();
	findAllMsg();  
	toMsg(temp); 
	getUserInfo();
	setTimeout("alarmOnload('')", 5000);
}


//获取session中保存的用户信息
function getUserInfo(){
	$
	.ajax({
		type : "POST",
		url : "../../web/userController/getUserInfo",
		data : {
			"flag" : "1"
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if (result == 'pass') {
				for(var i=0;i<data.length;i++){
					var name = data[i].opName;
					$(".top_user").html(name);
				}
			}else if(result == "fail"){	
				$(".top_user").html(data);
			}else{
				$(".top_user").html("0");
				layer.alert("数据加载异常1");
			}
		}});
}



//跳到报警页面
function toAlarm(){
	$
	.ajax({
		type : "POST",
		url : "../../web/alarmController/findFaultMsgNum",
		data : {
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				$(".badge-primary").html(data);
			}else{
				$(".badge-primary").html("0");
				layer.alert("数据加载异常");
			}
		}});
}



//格式化时间格式
function formatDate(now) {
	var year = now.getFullYear();

	var month = now.getMonth() + 1;

	var date = now.getDate();

	var hour = now.getHours();
	if(hour<10){
		hour = "0"+hour;
	}
	var minute = now.getMinutes();
	if(minute<10){
		minute = "0"+minute;
	}

	var second = now.getSeconds();
	if(second<10){
		second = "0"+second;
	}

	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}


//
///**
// * 查询当前登录的操作员的权限
// */
//function checkPermission(){
//	var permiss = "";
//	$.ajax({
//		async : false,
//		type : "POST",
//		url : "../../web/userController/checkPermission",
//		data : {},
//		dataType : "JSON",
//		success : function(retJs) {
//			var result = retJs.DataResult;
//			var data = retJs.message;
//			if (result == 'pass') {
//				permiss = data;
//			}else{
//				layer.alert(data);
//			}
//		},
//		error : function(){
//			layer.alert("查询当前操作员权限失败");
//		}
//	});
//	return permiss;
//}
















