$(function(){
	var _map = new Map();
	_map.put("systemLog", "../../web/logController/selectLog"); //系统日志的查询方法
	_map.put("things", "../../web/logController/selectAllThings"); //事件的查询方法
	_map.put("userMenu", "../../web/logController/selectAllUser"); //用户清单的查询方法
	//左侧导航被点击时，右侧的切换效果
	$(".dropdown").click(function(){
		$(".dropdown").removeClass("active");
		$(this).addClass("active");
		$(".rig_box").attr("hidden",true);
		//对应的 左侧导航 的数据查询AJAX
		queryDatas(_map.get($(this).attr("forDiv")), $(this).find("a").text(), "#"+$(this).attr("forDiv")+"List",$(this).attr("forDiv"));
//		queryDatas("../../web/logController/selectLog","系统日志","#sysLogList");
		document.getElementById($(this).attr("forDiv")).removeAttribute("hidden");
	});
	//页面加载时默认加载【系统日志】里面的所有记录
	queryDatas("../../web/logController/selectLog","系统日志","#sysLogList","systemLog");
	
	stopPropage("#sysLogAllDate",
			"#calendar1",
			"#calendar2",
			"#sysLogAllUsers",
			"#sysLogUser",
			"#thingsAllDate",
			"#calendar3",
			"#calendar4",
			"#thingsDeviceName",
			"#thingsDeviceType",
			"#calendar5",
			"#calendar6",
			"#userMenuName",
			"#userMenuType",
			"#userMenuTel",
			"#userMenuAdd");
	/*dateTextOnfocus(
			"#calendar1",
			"#calendar2",
			"#calendar3",
			"#calendar4",
			"#calendar5",
			"#calendar6");*/
	
});
function dateTextOnfocus(){
	for(var i = 0 ; i < arguments.length;i++){
		$(arguments[i]).on("focus",function(){
			stopPropage(
					".day_id-calendar1",
					".day_id-calendar2",
					".day_id-calendar3",
					".day_id-calendar4",
					".day_id-calendar5",
					".day_id-calendar6"
					/*"#row_id-calendar1"*/);
		});
	}
}
//下载按钮的点击事件
$(".downLoad").click(function(){
	
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		var _tbId = $(this).attr("forTable"); //拿到按钮对应的表格的ID
		var _head = [];  //表头
		$("#"+_tbId).find("thead").find("tr").find("th").each(function(_index,_elem){
			_head.push($(_elem).text());
		});
		var _list = []; //所有记录的list
		$("#"+_tbId).find("tbody").find("tr").each(function(_ind,_ele){
			var _tEvery = [];//每条记录
			$(_ele).find("td").each(function(ind,ele){
				_tEvery.push($(ele).text());
			});
			_list.push(_tEvery);
		});
		$.ajax({
			url : "../../web/devicesController/creatExicel",
			data : {
				head : JSON.stringify(_head),
				list : JSON.stringify(_list)
			},
			type : "POST",
			dataType : "JSON",
			success : function(data){	
				var filePath = data.filePath;
//				alert("86filePath:"+filePath);
				window.location.href = "../../web/devicesController/downloadExicel?filePath=" + filePath;
			},
			error : function(e){
				var filePath = e.responseText;
				var filePathArray =filePath.substr(1,filePath.length-2).split(":");
				var filePathStr = filePathArray[1]+":" + filePathArray[2];
				window.location.href = "../../web/devicesController/downloadExicel?filePath=" + filePathStr;
			}
		});
	}else{
		layer.alert("您没有操作权限");
	}
});
/**
 * @param _url  : 请求的路径
 * @param _name  : 被点击的 导航的名称
 * @param _appendToId  : 记录将要添加到那个tbody中   "#id"
 * @param _leftType  : 被点击的导航类型  systemLog  things userMenu userMenuList
 */
function queryDatas(_url,_name,_appendToId,_leftType){
	var tx="";
	$.ajax({
		url : _url,
		data : {},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(_leftType=="systemLog"){
				var beginTime = "";
				var endTime = "";
				if(result=='pass'){
					if(data!=null && data!=""){
						for(var i=0;i<data.length;i++){
							if(i==0){
								endTime = data[i]["createdate"]+" "+data[i]["createTime"]
							}
							if(i==data.length-1){
								beginTime = data[i]["createdate"]+" "+data[i]["createTime"]
							}
							tx+=" <tr>"
				               +"<td>"+data[i]["id"]+"</td>"
				               +"<td>"+data[i]["createdate"]+"</td>"
				               +"<td>"+data[i]["createTime"]+"</td>"
				               +"<td>"+data[i]["eventType"]+"</td>"
				               +"<td>"+data[i]["description"]+"</td>"
				               +"<td>"+data[i]["opName"]+"</td>"
				               +"<td>"+data[i]["opCode"]+"</td>"
				               +"</tr>";
						}
						$(_appendToId).empty();
						$(_appendToId).append(tx);
						$("#sysLogUName").html("全部");
						$("#sysLogStartTime").html(beginTime);
						$("#sysLogEndTime").html(endTime);
					}else{
						$(_appendToId).empty();
						$(_appendToId).append("");
						$("#sysLogUName").html("全部");
						$("#sysLogStartTime").html("--/--/--");
						$("#sysLogEndTime").html("--/--/--");
					}
				}else{
					$(_appendToId).empty();
					$(_appendToId).append("");
					$("#sysLogUName").html("全部");
					$("#sysLogStartTime").html("--/--/--");
					$("#sysLogEndTime").html("--/--/--");
				}
			}else if(_leftType=="things"){
				var beginTime = "";
				var endTime = "";
				if(result=='pass'){
					if(data!=null && data!=""){
						for(var i=0;i<data.length;i++){
							if(i==0){
								endTime = data[i]["sendTime"];
							}
							if(i==data.length-1){
								beginTime = data[i]["sendTime"];
							}
							tx+=" <tr>"
				               +"<td>"+data[i]["uniqueId"]+"</td>"
				               +"<td>"+data[i]["alarmTypeMsg"]+"</td>"
				               +"<td>"+data[i]["address"]+"</td>"
				               +"<td>"+data[i]["sendTime"]+"</td>"
				               +"<td>"+(data[i]["longitude"]==""?"未知":data[i]["longitude"])+"</td>"
				               +"<td>"+(data[i]["latitude"]==""?"未知":data[i]["latitude"])+"</td>"
				               +"<td>"+data[i]["devicesName"]+"</td>"
				               +"</tr>";
						}
						$(_appendToId).empty();
						$(_appendToId).append(tx);
						$("#thingsDeviceName").html("全部");
						$("#thingsAlarmType").html("全部");
						$("#thingsStartTime").html(beginTime);
						$("#thingsEndTime").html(endTime);
					}else{
						$(_appendToId).empty();
						$(_appendToId).append("");
						$("#thingsDeviceName").html("全部");
						$("#thingsAlarmType").html("全部");
						$("#thingsStartTime").html("--/--/--");
						$("#thingsEndTime").html("--/--/--");
					}
				}else{
					$(_appendToId).empty();
					$(_appendToId).append("");
					$("#thingsDeviceName").html("全部");
					$("#thingsAlarmType").html("全部");
					$("#thingsStartTime").html("--/--/--");
					$("#thingsEndTime").html("--/--/--");
				}
			}else if(_leftType=="userMenu"){
				var beginTime = "";
				var endTime = "";
				if(result=='pass'){
					if(data!=null && data!=""){
						for(var i=0;i<data.length;i++){
							if(i==0){
								endTime = data[i]["addDate"];
							}
							if(i==data.length-1){
								beginTime = data[i]["addDate"];
							}
							tx+=" <tr>"
				               +"<td>"+data[i]["uniqueId"]+"</td>"
				               +"<td>"+data[i]["devicesTypeId"]+"</td>"
				               +"<td>"+data[i]["simNumber"]+"</td>"
				               +"<td>"+data[i]["nickName"]+"</td>"
				               +"<td>"+data[i]["userType"]+"</td>"
				               +"<td>"+data[i]["addDate"]+"</td>"
				               +"<td>"+data[i]["userPhone"]+"</td>"
				               +"<td>"+data[i]["userAddress"]+"</td>"
				               +"<td>"+data[i]["nickName"]+"</td>"
				               +"<td>"+data[i]["devicesState"]+"</td>"
				               +"<td>"+data[i]["groupName"]+"</td>"
				               +"</tr>";
						}
						$(_appendToId).empty();
						$(_appendToId).append(tx);
						$("#userMenuUName").html("全部");
						$("#userMenuStartTime").html(beginTime);
						$("#userMenuEndTime").html(endTime);
					}else{
						$(_appendToId).empty();
						$(_appendToId).append("");
						$("#userMenuUName").html("全部");
						$("#userMenuStartTime").html("--/--/--");
						$("#userMenuEndTime").html("--/--/--");
					}
				}else{
					$(_appendToId).empty();
					$(_appendToId).append("");
					$("#userMenuUName").html("全部");
					$("#userMenuStartTime").html("--/--/--");
					$("#userMenuEndTime").html("--/--/--");
				}
			}
		},
		error : function(){
			layer.alert("查询" + _name + "失败");
		}
	});
}

//系统日志   里的查询按钮被点击
$("#sysLogBtn").click(function(){
//	layer.alert("11");
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		var tx = "";
		event.stopPropagation();
		var _isAllDate = 0;//是否选中【全部日期】  未选中：0；选中：1
		var _isAllDateChecked = document.getElementById("sysLogAllDate").checked;
		if(_isAllDateChecked){
			_isAllDate = 1;
		}
		var _startDate = $("#calendar1").val(); //起始时间
		var _endDate = $("#calendar2").val();  //终止事件
		var _isAllUsers = 0;//是否选中【全部操作员】  未选中：0；选中：1
		var _isAllUsersChecked = document.getElementById("sysLogAllUsers").checked;
		if(_isAllUsersChecked){
			_isAllUsers = 1;
		}
		var _sysLogUser = $("#sysLogUser").val();  //操作员姓名
	//	if(
	//			(_isAllDateChecked || 
	//					(checkHanzi(_startDate, "#calendar1", "起始日期") && 
	//							checkHanzi(_endDate, "#calendar2", "结束日期")
	//					)
	//			)  && 
	//			(_isAllUsersChecked || 
	//					checkHanzi(_sysLogUser, "#sysLogUser", "操作员名称")
	//			)
	//	   ){
		if(chkTime(_startDate, _endDate, "#calendar2")){
			$.ajax({
				async : false,
				url : "../../web/logController/selectLogByCondition",
				data : {
					_isAllDate : _isAllDate,
					_startDate : _startDate,
					_endDate : _endDate,
					_isAllUsers : _isAllUsers,
					_sysLogUser : _sysLogUser
				},
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					var beginTime = "";
					var endTime = "";
					if(result == 'pass'){
						if(data!=null && data!=""){
							for(var i=0;i<data.length;i++){
								if(i==0){
									endTime = data[i]["createdate"]+" "+data[i]["createTime"]
								}
								if(i==data.length-1){
									beginTime = data[i]["createdate"]+" "+data[i]["createTime"]
								}
								tx+=" <tr>"
					               +"<td>"+data[i]["id"]+"</td>"
					               +"<td>"+data[i]["createdate"]+"</td>"
					               +"<td>"+data[i]["createTime"]+"</td>"
					               +"<td>"+data[i]["eventType"]+"</td>"
					               +"<td>"+data[i]["description"]+"</td>"
					               +"<td>"+data[i]["opName"]+"</td>"
					               +"<td>"+data[i]["opCode"]+"</td>"
					               +"</tr>";
							}
							$("#sysLogList").empty();
							$("#sysLogList").append(tx);
							$("#sysLogUName").html(_sysLogUser==""?"全部":_sysLogUser);
							$("#sysLogStartTime").html(beginTime);
							$("#sysLogEndTime").html(endTime);
							$(".active").removeClass("open");
							cleanText("#calendar1","#calendar2","#sysLogUser");
							document.getElementById("sysLogAllDate").checked=false;
						}else{
							$("#sysLogList").empty();
							$("#sysLogList").append("");
							$("#sysLogUName").html(_sysLogUser==""?"全部":_sysLogUser);
							$("#sysLogStartTime").html("--/--/--");
							$("#sysLogEndTime").html("--/--/--");
							$(".active").removeClass("open");
							cleanText("#calendar1","#calendar2","#sysLogUser");
							document.getElementById("sysLogAllDate").checked=false;
						}
					}else{
						layer.alert(data);
						$("#sysLogList").empty();
						$("#sysLogList").append(tx);
						$("#sysLogUName").html(_sysLogUser==""?"全部":_sysLogUser);
						$("#sysLogStartTime").html(_startDate==""?"--/--/--":_startDate);//_startDate==""?"--/--/--":_startDate
						$("#sysLogEndTime").html(_endDate==""?"--/--/--":_endDate);
						$(".active").removeClass("open");
						cleanText("#calendar1","#calendar2","#sysLogUser");
						document.getElementById("sysLogAllDate").checked=false;
						
						//在没有符合条件的记录的情况下，是显示所有记录还是一条都不显示？如果显示所有则打开下面的这行代码
	//					queryDatas("../../web/logController/selectLog","系统日志","#sysLogList");
					}
				},
				error : function(){
					layer.alert("查询系统日志失败,请稍后重试");
				}
			});
			//对接接口后，可以删除下面这段
			$(".active").removeClass("open");
		}
		}else{
			layer.alert("您没有操作权限");
	}
//	}
});
//事件   里的查询按钮被点击
$("#thingsBtn").click(function(){
	
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		event.stopPropagation();
		var tx = "";
		var _isAllDate = 0;//是否选中【全部日期】  未选中：0；选中：1
		var _isAllDateChecked = document.getElementById("thingsAllDate").checked;
		if(_isAllDateChecked)
			_isAllDate = 1;
		var _startDate = $("#calendar3").val(); //起始时间
		var _endDate = $("#calendar4").val();  //终止时间
		var _DeviceName = $("#thingsDeviceName").val();  //设备名称【设备名称可以不填写，如果不填写则值为"",默认查询所有名称】
		var _DeviceType = $("#thingsDeviceType").val();  //报警类型【现在是不可为空如果需要为空，参照 设备名称即可】
	//	if(
	//			(_isAllDateChecked ||
	//					(checkHanzi(_startDate, "#calendar3", "起始日期") && 
	//							checkHanzi(_endDate, "#calendar4", "结束日期")
	//					)
	//			) //&& checkHanzi(_DeviceType, "#thingsDeviceType", "报警类型") //报警类型校验【此处不可为空若可为空，删掉此校验即可】
	//	){
		if(chkTime(_startDate, _endDate, "#calendar4")){
			$.ajax({
				url : "../../web/logController/selectThingsByCondition",
				data : {
					_isAllDate : _isAllDate,
					_startDate : _startDate,
					_endDate : _endDate,
					_DeviceName : _DeviceName,//设备名称可能为空，后台需要进行判断
					_DeviceType : _DeviceType
				},
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					var beginTime = "";
					var endTime = "";
					
					if(result == 'pass'){
						if(data!=null && data!=""){
							for(var i=0;i<data.length;i++){
								if(i==0){
									endTime = data[i]["sendTime"];
								}
								if(i==data.length-1){
									beginTime = data[i]["sendTime"];
								}
								tx+=" <tr>"
					               +"<td>"+data[i]["uniqueId"]+"</td>"
					               +"<td>"+data[i]["alarmTypeMsg"]+"</td>"
					               +"<td>"+data[i]["address"]+"</td>"
					               +"<td>"+data[i]["sendTime"]+"</td>"
					               +"<td>"+(data[i]["longitude"]==""?"未知":data[i]["longitude"])+"</td>"
					               +"<td>"+(data[i]["latitude"]==""?"未知":data[i]["latitude"])+"</td>"
					               +"<td>"+data[i]["devicesName"]+"</td>"
					               +"</tr>";
							}
							$("#thingsList").empty();
							$("#thingsList").append(tx);
	//						$("#thingsDeviceName").html("全部");
	//						$("#thingsAlarmType").html("全部");
							$("#thingsStartTime").html(beginTime==""?"--/--/--":beginTime);
							$("#thingsEndTime").html(endTime==""?"--/--/--":endTime);
							$("#thingsDeviceName1").html(_DeviceName==""?"全部":_DeviceName);
							$("#thingsAlarmType").html(_DeviceType==""?"全部":_DeviceType);
							$(".active").removeClass("open");
							cleanText("#calendar3","#calendar4","#thingsDeviceName","#thingsDeviceType");
							document.getElementById("thingsAllDate").checked=false;
						}else{
							$("#thingsList").empty();
							$("#thingsList").append("");
							$("#thingsStartTime").html(beginTime==""?"--/--/--":beginTime);
							$("#thingsEndTime").html(endTime==""?"--/--/--":endTime);
							$("#thingsDeviceName1").html(_DeviceName==""?"全部":_DeviceName);
							$("#thingsAlarmType").html(_DeviceType==""?"全部":_DeviceType);
							$(".active").removeClass("open");
							cleanText("#calendar3","#calendar4","#thingsDeviceName","#thingsDeviceType");
							document.getElementById("thingsAllDate").checked=false;
						}
					}else{
						layer.alert("没有找到符合条件的记录");
						$("#thingsList").empty();
						$("#thingsList").append("");
						$(".active").removeClass("open");
						$("#thingsStartTime").html(_startDate==""?"--/--/--":_startDate);
						$("#thingsEndTime").html(_endDate==""?"--/--/--":_endDate);
						$("#thingsDeviceName1").html(_DeviceName==""?"全部":_DeviceName);
						$("#thingsAlarmType").html(_DeviceType==""?"全部":_DeviceType);
						cleanText("#calendar3","#calendar4","#thingsDeviceName","#thingsDeviceType");
						document.getElementById("thingsAllDate").checked=false;
					}
				},
				error : function(){
					layer.alert("查询事件失败");
				}
			});
		}
	}else{
		layer.alert("您没有操作权限");
	}
//	}
});
//用户清单   里的查询按钮被点击
$("#userMenuBtn").click(function(){
	
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		event.stopPropagation();
		var tx = "";
		var _isAllDate = 0;//是否选中【全部日期】  未选中：0；选中：1
		var _isAllDateChecked = document.getElementById("userMenuAllDate").checked;
		if(_isAllDateChecked)
			_isAllDate = 1;
		var _startDate = $("#calendar5").val(); //起始时间
		var _endDate = $("#calendar6").val();  //终止时间
		var userMenuName = $("#userMenuName").val();  //用户姓名【现在是不可为空如果需要为空，参照 设备名称即可】
		var userMenuType = $("#userMenuType").val();  //用户类型【现在是不可为空如果需要为空，参照 设备名称即可】
		var userMenuTel = $("#userMenuTel").val();  //用户电话【现在是不可为空如果需要为空，参照 设备名称即可】
		var userMenuAdd = $("#userMenuAdd").val();  //用户地址【现在是不可为空如果需要为空，参照 设备名称即可】
	//	if(
	//		(
	//				(_isAllDateChecked ||
	//					 checkHanzi(_startDate, "#calendar5", "起始日期") && 
	//					 checkHanzi(_endDate, "#calendar6", "结束日期")&&
	//					 checkHanzi(userMenuName, "#userMenuName", "用户姓名") && //用户姓名校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuType, "#userMenuType", "用户类型") && //用户类型校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuTel, "#userMenuTel", "用户电话") && //用户电话校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuAdd, "#userMenuAdd", "用户地址") //用户地址校验【此处不可为空若可为空，删掉此校验即可】
	//				) &&
	//				(!_isAllDateChecked ||
	//					 checkHanzi(userMenuName, "#userMenuName", "用户姓名") && //用户姓名校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuType, "#userMenuType", "用户类型") && //用户类型校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuTel, "#userMenuTel", "用户电话") && //用户电话校验【此处不可为空若可为空，删掉此校验即可】
	//					 checkHanzi(userMenuAdd, "#userMenuAdd", "用户地址") //用户地址校验【此处不可为空若可为空，删掉此校验即可】
	//				)
	//		)
	//	){
		if(chkTime(_startDate, _endDate, "#calendar6")){
			$.ajax({
				url : "../../web/logController/selectUsersByCondition",
				data : {
					_isAllDate : _isAllDate,
					_startDate : _startDate,				
					_endDate : _endDate,
					userMenuName : userMenuName, //若用户姓名可为空，后台需要进行判断
					userMenuType : userMenuType,//若用户类型可为空，后台需要进行判断
					userMenuTel : userMenuTel,//若用户电话可为空，后台需要进行判断
					userMenuAdd : userMenuAdd//若用户地址可为空，后台需要进行判断
				},
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					var beginTime = "";
					var endTime = "";
					if(result == 'pass'){
						if(data!=null && data!=""){
							for(var i=0;i<data.length;i++){
								if(i==0){
									endTime = data[i]["addDate"];
								}
								if(i==data.length-1){
									beginTime = data[i]["addDate"];
								}
								tx+=" <tr>"
					               +"<td>"+data[i]["uniqueId"]+"</td>"
					               +"<td>"+data[i]["devicesTypeId"]+"</td>"
					               +"<td>"+data[i]["simNumber"]+"</td>"
					               +"<td>"+data[i]["nickName"]+"</td>"
					               +"<td>"+data[i]["userType"]+"</td>"
					               +"<td>"+data[i]["addDate"]+"</td>"
					               +"<td>"+data[i]["userPhone"]+"</td>"
					               +"<td>"+data[i]["userAddress"]+"</td>"
					               +"<td>"+data[i]["nickName"]+"</td>"
					               +"<td>"+data[i]["devicesState"]+"</td>"
					               +"<td>"+data[i]["groupName"]+"</td>"
					               +"</tr>";
							}
							$("#userMenuList").empty();
							$("#userMenuList").append(tx);
							$("#userMenuUName").html("全部");
							$("#userMenuStartTime").html(beginTime);
							$("#userMenuEndTime").html(endTime);
							$("#userMenuUName").text(userMenuName==""?"全部":userMenuName);
							$(".active").removeClass("open");
							cleanText("#calendar5","#calendar6","#userMenuName","#userMenuType","#userMenuTel","#userMenuAdd");
						}else{
							$("#userMenuList").empty();
							$("#userMenuList").append("");
							$("#userMenuUName").html("全部");
							$("#userMenuStartTime").html("--/--/--");
							$("#userMenuEndTime").html("--/--/--");
							$("#userMenuUName").text(userMenuName==""?"全部":userMenuName);
							$(".active").removeClass("open");
							cleanText("#calendar5","#calendar6","#userMenuName","#userMenuType","#userMenuTel","#userMenuAdd");
						}
					}else{
						layer.alert("没有找到符合条件的记录");
						$("#userMenuList").empty();
						$("#userMenuList").append("");
						$("#userMenuStartTime").text(_startDate==""?"--/--/--":_startDate);
						$("#userMenuEndTime").text(_endDate==""?"--/--/--":_endDate);
						$("#userMenuUName").text(userMenuName==""?"全部":userMenuName);
						$(".active").removeClass("open");
						cleanText("#calendar5","#calendar6","#userMenuName","#userMenuType","#userMenuTel","#userMenuAdd");
					}
						
						/*$("#userMenuStartTime").text(_startDate==""?"--/--/--":_startDate);
						$("#userMenuEndTime").text(_endDate==""?"--/--/--":_endDate);
						$("#userMenuUName").text(userMenuName==""?"全部":userMenuName);
						$("#sysLogList").append(
								"<tr>"+
								"       <td>1</td>"+
								"       <td>2015-8.8</td>"+
								"       <td>10.10.10</td>"+
								"       <td>oprater</td>"+
								"       <td>系统登录</td>"+
								"       <td>系统默认管理员</td>"+
								"       <td>system</td>"+
								"       <td>system</td>"+
								"       <td>system</td>"+
								"       <td>system</td>"+
								"       <td>system</td>"+
						"</tr>");
						$(".active").removeClass("open");
						//$("#calendar5").val("");
						cleanText("#calendar5","#calendar6","#userMenuName","#userMenuType","#userMenuTel","#userMenuAdd");*/
	//				}
				},
				error : function(){
					layer.alert("查询用户清单失败");
				}
			});
		}
	}else{
		layer.alert("您没有操作权限");
	}
//	}
});
