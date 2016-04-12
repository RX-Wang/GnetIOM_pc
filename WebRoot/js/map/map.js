//查询所有信息
function findAllMsg(){
	$
	.ajax({
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
//						var e=new Date(data[i].sendTime);
//						var sendTime =formatDate(e);
						var sendTime =data[i].sendTime;
					
						var cls="tb_bg1";
						if(alarmType=="报警"){
							cls="tb_bg4";
						}else if(alarmType=="事件"){
							cls="tb_bg1";
						}else if(alarmType=="故障"){
							cls="tb_bg2";
						}
						var tx = "<tr>"
	                    +"<td width=\"3%\" class=\""+cls+"\"></td>"
	                    +"<td width=\"20%\">"+devicesName+"</td>"
	                    +"<td width=\"20%\" ondblclick=\"showId("+uniqueId+");\">"+uniqueId+"</td>"
	                    +"<td width=\"20%\">"+alarmTypeMsg+"</td>"
	                    +"<td width=\"20%\">"+address+"</td>"
	                    +"<td width=\"20%\">"+sendTime+"</td>"
	                    +"</tr>"
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
	
	setTimeout("mapOnload()", 5000);  
	
}


function showId(uniqueId){
//	alert(uniqueId);//
	var obj = document.getElementById("forSearchUniqueId");
	
	if(obj!=null && obj!=""){
		$("#forSearchUniqueId").val(uniqueId);
	}
//	var obj1= $("#forSearchUniqueId1");
//	alert(obj);
//	alert(obj1);
	
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
	$.ajax({
		type : "POST",
		url : "../../web/alarmController/findAlarmMsg",
		data : {},
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
					var cls="tb_bg1";
					if(alarmType=="报警"){
						cls="tb_bg4";
					}else if(alarmType=="事件"){
						cls="tb_bg1";
					}else if(alarmType=="故障"){
						cls="tb_bg2";
					}
					var tx="<tr>"
						+"<td width=\"3%\" class=\""+cls+"\"></td>"
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
	
//	setTimeout("mapOnload()", 10000); 
	
}


//查询所有未处理的报警信息总条数
function findAlarmMsgNum(){
	$.ajax({
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
		},
		error: function(){
			$(".badge-danger").html("0");
			layer.alert("加载未处理的报警消息总条数出错...");
		}
	});
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
					var cls="tb_bg1";
					if(alarmType=="报警"){
						cls="tb_bg4";
					}else if(alarmType=="事件"){
						cls="tb_bg1";
					}else if(alarmType=="故障"){
						cls="tb_bg2";
					}
					var tx="<tr>"
						+"<td width=\"3%\" class=\""+cls+"\"></td>"
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
		},
		error: function(){
			$("#tbAllMsg").empty();
			$("#tbAllMsg").append("");
			layer.alert("加载未处理的事件消息出错...");
		}
	});
//	setTimeout("mapOnload()", 10000); 
}

//查询所有未处理的事件信息的总条数
function findThingMsgNum(){
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
		},
		error: function(){
			$(".badge-success").html("0");
			layer.alert("加载未处理的事件消息总条数出错...");
		}
	});
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
					var cls="tb_bg1";
					if(alarmType=="报警"){
						cls="tb_bg4";
					}else if(alarmType=="事件"){
						cls="tb_bg1";
					}else if(alarmType=="故障"){
						cls="tb_bg2";
					}
					var tx="<tr>"
						+"<td width=\"3%\" class=\""+cls+"\"></td>"
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
		},
		error: function(){
			$("#tbAllMsg").empty();
			$("#tbAllMsg").append("");
			layer.alert("加载未处理的故障消息出错...");
		}
	});
	
//	setTimeout("mapOnload()", 10000); 
	
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
		},
		error: function(){
			$(".badge-primary").html("0");
			layer.alert("加载未处理的故障消息总条数出错...");
		}
	});
}

function mapOnload(){
	findAllUntreatedNum();
	findAlarmMsgNum();
	findThingMsgNum();
	findFaultMsgNum();
	findAllMsg();
	getUserInfo();
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
				$(".top_user").html("");
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
function selHistroy(){
	if($("#histroyCheck").val() != ""){
		//对接口时下面这个删掉
		$("#historyTb").append(
				"<tr id=\"aaaaaaaaaaa\">"+
            	"	<td>aaaa</td>"+
            	"	<td>"+
				"		<a onclick=\"checkHistory()\">查看</a>"+
				"		<a onclick=\"delHistory()\">删除</a>"+
				"	</td>"+
            	"</tr>");
	}else{
		layer.tips("请输入查询条件","#histroyCheck");
	}
}

function delHistory(){
	var _id = $(event.target).parent().parent().attr("id");
	console.log("被删除的设备的id是：" + $(event.target).parent().parent().attr("id"));
	/*$.ajax({
		url : "../../web/../..",
		data : {
			_id : _id
		},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.success){
				$(event.target).parent().parent().remove();
			}
		},
		error : function(){
			layer.tips("删除失败","#"+event.target.id);
		}
	});*/
	//对接口时下面这个删掉
	$(event.target).parent().parent().remove();
}

function checkHistory(){
	var _id = $(event.target).parent().parent().attr("id");
	console.log("被查看的设备的id是：" + $(event.target).parent().parent().attr("id"));
	window.location.href="../../web/pageController/historyMap?_id="+_id;
}




