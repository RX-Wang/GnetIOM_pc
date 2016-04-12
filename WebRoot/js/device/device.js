$(document).ready(function(){
  $(".device_add").click(function(){
	  clearText();
	  $("#deviceType").empty();
	  var tx ="";
	  $.ajax({
			type : "POST",
			url : "../../web/devicesController/synchroDevicesType",
			data : {},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
//					layer.alert("类型:"+data);
//					var type = JSON.parse(data);
					for(var i = 0; i < data.length; i++){
//						layer.alert(data[i]["type_id"]);
						tx += "<option value=\""+data[i]["type_id"]+"\">"+data[i]["type_id"]+"</option>";
					}
					$("#deviceType").append(tx);
				}
			},
			error : function(){
				layer.alert("加载数据出错...");
			}
		});
  });
  
  //同步设备类型
  $("#devicesType").click(function(){
	  var tx="";
	  $.ajax({
			type : "POST",
			url : "../../web/devicesController/synchroDevicesType",
			data : {},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
					for(var i = 0; i < data.length; i++){
						tx += "<option value=\""+data[i]["type_id"]+"\">"+data[i]["type_id"]+"</option>";
					}
					 $("#deviceType").empty();
					 $("#deviceType").append(tx);
					 layer.alert("同步设备类型成功");
				}
			},
			error : function(){
				layer.alert("加载数据出错...");
			}
		});
	 
  });
});

//查询设备昵称是否重复以及设备编号和安全码是否正确
function checkInfo(){
	var flag = false;
	var nickName =$("#nickName").val();
	var uniqueId = $("#uniqueId").val();
	var securityCode = $("#securityCode").val();
	if((nickName!="" && null!=nickName)||(uniqueId!=""&& null!=uniqueId)){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/devicesController/checkSameNickName",
			data : {
				"nickName" : nickName,
				"uniqueId" : uniqueId,
				"securityCode" : securityCode
			},
			dataType : "json",
			success : function(retJs) {
					var result = retJs.DataResult;
					var data = retJs.message;
					if (result == 'success') {
						if(securityCode!="" && null!=securityCode){
							var secsCode = data["security_code"];
							if(secsCode != securityCode){
//								layer.alert("设备安全码不正确");
								layer.tips("安全码不正确","#securityCode");
							}else{
								flag = true;
							}
						}
					}else if(result == 'devIdError'){
						layer.tips("设备编号不正确","#uniqueId");
					}else if(result == 'hasBind'){
						layer.tips("该设备已被绑定","#uniqueId");
					}else if(result == 'nickNameRep'){
						layer.tips("使用者名称已存在","#nickName");
					}else if(result == 'suc'){
						
					}else{
						layer.alert(data);
					}
			},
			error : function(){
				layer.alert("加载数据出错...");
			}
		});
	}
//	layer.alert("97flag:"+flag);
	return flag;
}

//checkNickName
function checkNickName(){
	
	var nickName =cutTrim($("#nickName").val(), "g");
	var oldNickName = $("#oldNickName").val();
	var flag = false;
	if(nickName!="" && null!=nickName && (nickName != oldNickName)){
		$.ajax({
			async : false,
			type : "POST",
			url : "../../web/devicesController/checkNickName",
			data : {
				"nickName" : nickName
			},
			dataType : "json",
			success : function(retJs) {
					var result = retJs.DataResult;
					var data = retJs.message;
					if (result == 'pass') {
					}else{
						layer.tips(data,"#nickName");
					}
					if(result == 'pass'){
						flag = true;
					}else{
						flag = false;
					}
			},
			error : function(){
				layer.alert("加载数据出错...");
			}
		});
	}else if(nickName == oldNickName){
		flag = true;
	}
	return flag;
}


//更新
$("#update").click(function(){
	
	if(checkHanzi($("#nickName").val(),"#nickName","使用者名称")&&
	   filSpeSym($("#nickName").val(),"#nickName","使用者名称") &&
	   checkHanzi($("#userPhone").val(),"#userPhone","电话")&&
	   checkNum($("#userPhone").val(),"#userPhone","电话") && 
	   checkHanzi($("#userType").val(),"#userType","使用者类型")&&
	   checkHanzi($("#groupId").val(),"#groupId","所属分组")&&
	   checkHanzi($("#uniqueId").val(),"#uniqueId","设备编号")&&
	   checkHanzi($("#securityCode").val(),"#securityCode","安全码")&&
	   checkHanzi($("#simNumber").val(),"#simNumber","SIM卡号")&&
	   checkNum($("#simNumber").val(),"#simNumber","SIM卡号") && 
	   checkHanzi($("#deviceType").val(),"#deviceType","设备类型")&&
	   checkNickName()
			){
		if($("#linkManTable").find("tr").length==1){
			layer.alert("尚未添加联系人");	
		}else{
			var linkMen = [];
			$("#linkManTable").find("tr").each(function(index,elem){
				if(index>0){
					var linkMan = {}; 
					var _th = ["name","tel","phone","description"];
					for(var i = 0 ; i<elem.children.length;i++){
						linkMan[_th[i]] = elem.children[i].innerHTML;
					}
					linkMen.push(linkMan);
					$("#linkmen").val(JSON.stringify(linkMen));
				}
			});
			
			var formData = getFormJson(document.getElementById("deviceForm"));
			
			$.ajax({
				url : "../../web/devicesController/updateDeviceUser",
				data : formData,
				type : "post",
				dataType : "json",
				success : function(resJson){
					var result = resJson.DataResult;
					var data = resJson.data;
					if(result == 'pass'){
						layer.msg('修改成功',{
							time : 0
							,btn : ['确定']
							,yes : function(index){
								layer.close(index);
								icon : 6;
								parent.window.location.href="../../web/pageController/toDevice";
								parent.layer.close(parent.layer.getFrameIndex(window.name));
							}
						});
					}else{
						layer.alert(data);
					}
				},
				error : function(){
					layer.alert("更新信息失败");
				}
			});
		}
	}else{
//		layer.alert("数据验证有误");
	}
});




//保存
document.getElementById("save").onclick=function(){
	if(checkHanzi($("#nickName").val(),"#nickName","使用者名称")&&
	   filSpeSym($("#nickName").val(),"#nickName","使用者名称") &&
	   checkHanzi($("#userPhone").val(),"#userPhone","电话")&&
	   checkNum($("#userPhone").val(),"#userPhone","电话") && 
	   checkHanzi($("#userType").val(),"#userType","使用者类型")&&
	   checkHanzi($("#groupId").val(),"#groupId","所属分组")&&
	   checkHanzi($("#uniqueId").val(),"#uniqueId","设备编号")&&
	   checkHanzi($("#securityCode").val(),"#securityCode","安全码")&&
	   checkHanzi($("#simNumber").val(),"#simNumber","SIM卡号")&&
	   checkNum($("#simNumber").val(),"#simNumber","SIM卡号") &&
	   checkHanzi($("#deviceType").val(),"#deviceType","设备类型")&&
	   checkInfo()
	){
		if($("#linkManTable").find("tr").length==1){
			layer.alert("请添加联系人");	
		}else{
			var linkMen = [];
			$("#linkManTable").find("tr").each(function(index,elem){
				if(index>0){
					var linkMan = {}; 
					var _th = ["name","tel","phone","description"];
					for(var i = 0 ; i<elem.children.length;i++){
						linkMan[_th[i]] = elem.children[i].innerHTML;
					}
					linkMen.push(linkMan);
					$("#linkmen").val(JSON.stringify(linkMen));
				}
			});
			
			var formData = getFormJson(document.getElementById("deviceForm"));
			
			$.ajax({
				url : "../../web/devicesController/addBindDevice",
				data : formData,
				type : "post",
				dataType : "json",
				success : function(resJson){
					var result = resJson.DataResult;
					var data = resJson.data;
					if(result == 'pass'){
						layer.msg('添加成功',{
							time : 0
							,btn : ['确定']
							,yes : function(index){
								layer.close(index);
								icon : 6;
								parent.window.location.href="../../web/pageController/toDevice";
								parent.layer.close(parent.layer.getFrameIndex(window.name));
							}
						});
					}else{
						layer.alert(data);
					}
				},
				error : function(){
					layer.alert("保存设备信息失败");
				}
			});
		}
	}else{
	}
};

//reset
$("#reset").click(function(){
	cleanText(
			"#nickName","#userPhone","#groupId",
			"#userAddress","#uniqueId","#securityCode",
			"#simNumber"
			);
	$("#userType").children().removeAttr("selected");
	
	$("#userDescription").val("");
	var _th = $("#linkManTable").find("tr:first");
	$("#linkManTable").empty().append(_th[0]);
});


function updateReset(){
//	alert("修改重置");
	cleanText(
			"#nickName","#userPhone","#groupId",
			"#userAddress","#uniqueId","#securityCode",
			"#simNumber"
			);
	$("#userType").children().removeAttr("selected");
	$("#description").val("");
	var _th = $("#linkManTable").find("tr:first");
	$("#linkManTable").empty().append(_th[0]);
}

//页面加载
function addDevOnload(){
	  synchroDevType();
}




//查询一级分组名称
function synchroGroup(){
	var tx="";
	  $.ajax({
			type : "POST",
			url : "../../web/groupController/selOneGroup",
			data : {},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
					for(var i = 0; i< data.length;i++){
						tx += "<li class=\"active\">"
	                        	+"<a onclick=\"leftzhedie()\">"
								+"<span onblur=\"editOblur()\">一级</span>"
								+"<span class=\"badge badge-roundless\">222</span>"
								+"<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"
	                        	+"<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"
	                        	+"<i onclick=\"chahao()\" class=\"glyphicon glyphicon-remove\"></i>"
	                        	+"</a>"
	                        	+"</li>";
					}
					$(".menu").append(tx);
				}
			},
			error : function(){
				layer.alert("加载分组名称异常...");
			}
		});
}

function synchroDevType(){
	var tx="";
	  $.ajax({
			type : "POST",
			url : "../../web/devicesController/synchroDevicesType",
			data : {},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
//					var type = JSON.parse(data);
					for(var i = 0; i < data.length; i++){
//						layer.alert(data[i]["type_id"]);
						tx += "<option value=\""+data[i]["type_id"]+"\">"+data[i]["type_id"]+"</option>";
					}
					 $("#deviceType").empty();
					 $("#deviceType").append(tx);
//					 layer.alert("同步设备类型成功");
				}
			},
			error : function(){
				layer.alert("加载设备类型出错...");
			}
		});
	
}

//清空页面文本框
function clearText(){
	var txts=document.getElementsByTagName("input");  
	var txtrs=document.getElementsByTagName("textarea");  
	
    for(var i=0;i<txts.length;i++)  
    {  
      if(txts[i].type=="text")  
      {  
        txts[i].value ="";  
      }  
    }
    for(var i=0;i<txtrs.length;i++)  
    {  
    		txtrs[i].value ="";  
    }
}
//公共获取form表单方法
function getFormJson(frm) {
	var o = {};
	var a = $(frm).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

/**
 * 去空格
 * @param str
 * @param is_global
 * @returns
 */
function cutTrim(str,is_global)
{
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g,"");
    if(is_global.toLowerCase()=="g")
    {
        result = result.replace(/\s/g,"");
     }
    return result;
}


