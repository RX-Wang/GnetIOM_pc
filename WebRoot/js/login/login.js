$(function(){
	var compInfo=getCookie("compInfo");
	if(compInfo!=null && compInfo!=""){
		$("#companyId").val(compInfo);
	}
	
	var userInfo = getCookie("userInfo");
	if(userInfo!=null && userInfo!=""){
		$("#userName").val(userInfo);
	}
});


//用户登录
function checUserkLogin(){
	var userName = $("#userName").val();
	var userPwd = $("#userPwd").val();
	if(userName==null || "" == userName){
//		layer.alert("用户名不能为空");
		layer.tips("用户名不能为空","#userName");
		return;
	}else if(userPwd==null || "" == userPwd){
//		layer.alert("用户密码不能为空");
		layer.tips("用户密码不能为空","#userPwd");
		return;
	}
	
	$
	.ajax({
		async : false,
		type : "POST",
		url : "../../web/userController/validateLoginUser",
		data : {
			"userName" : userName,
			"userPwd" : userPwd,
			"type" : 1
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var message = retJs.message;
			if (result == 'pass') {
				addCookie("userInfo", userName, 72);
				window.location.href = "../../web/pageController/toIndex"
			}else{
				layer.alert(message);
			}
		}});
}



//集团登录
function checkCompanyLogin(){
	var companyId = $("#companyId").val();
	var companyPwd = $("#companyPwd").val();
	if(companyId==null || "" == companyId){
		layer.tips("集团账号不能为空","#companyId");
		return;
	}else if(companyPwd==null || "" == companyPwd){
		layer.tips("集团密码不能为空","#companyPwd");
		return;
	}
	$
	.ajax({
		type : "POST",
		url : "../../web/userController/validateLoginUser",
		data : {
			"companyId" : companyId,
			"companyPwd" : companyPwd,
			"type" : 0
		},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var message = retJs.message;
			if (result == 'pass') {
				addCookie("compInfo", companyId, 72);
				layer.alert("登录成功!");
			}else{
				layer.alert(message);
//				layer.alert("集团账号或密码错误!");
			}			
		}});
}

/**
 * 到注册页面
 */
function toRegFun(){
	document.getElementById("regDiv").removeAttribute("hidden");
	$("#loginDiv").attr("hidden","hidden");
}
/**
 * 注册方法
 */
function regFun(){
	var regCompId = $("#regCompId").val();
	var regCompName = $("#regCompName").val();
	var regCompPwd1 = $("#regCompPwd1").val();
	var regCompPwd2 = $("#regCompPwd2").val();
	var regCompEmail = $("#regCompEmail").val();
	var regCompTel = $("#regCompTel").val();
	var regCompAdd = $("#regCompAdd").val();
	
	if(
			checkHanzi(regCompId, "#regCompId", "集团ID") &&
			checkHanzi(regCompName, "#regCompName", "名称") &&
			filSpeSym(regCompName, "#regCompName", "名称") &&
			checkHanzi(regCompPwd1, "#regCompPwd1", "新密码") &&
			checkPwd(regCompPwd1, "#regCompPwd1") && 
			checkHanzi(regCompPwd2, "#regCompPwd2", "确认密码") &&
			newOpCodeIsSame("regCompPwd1", "regCompPwd2") &&
			checkHanzi(regCompEmail, "#regCompEmail", "网址") &&
			checkEmail(regCompEmail, "#regCompEmail", "网址") &&
			checkHanzi(regCompTel, "#regCompTel", "电话") &&
			checkNum(regCompTel, "#regCompTel", "电话") &&
			checkHanzi(regCompAdd, "#regCompAdd", "住址") &&
			checkCompIDOnly(regCompId,"#regCompId") &&    //校验集团ID是否正确
//			checkCompIDIsExist(regCompId,"#regCompId") &&    //校验集团ID是否可注册，不需要可删除
			checkCompNameOnly(regCompName,"#regCompName")   //校验名称唯一
		){
		$.ajax({
			url : "../../web/userController/register",
			data : {
				"companyId" : regCompId,
				"companyName" : regCompName,
				"companyPwd" : regCompPwd1,
				"companyInterAddr" : regCompEmail,
				"companyPhone" : regCompTel,
				"companyAddr" : regCompAdd
			},
			type : "POST",
			dataType : "JSON",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					addCookie("compInfo", regCompId, 72);
//					layer.alert("注册成功");
					layer.msg('注册成功',{
						time : 0,
						btn : ['确定'],
						yes : function(index){
							layer.close(index);
							icon : 6;
							window.location.href='../../web/pageController/loginPage';
						}
					});
					
					
//					addCookie("compInfo", regCompId, 72);
//					document.getElementById("loginDiv").removeAttribute("hidden");
//					$("#regDiv").attr("hidden","hidden");
//					cleanText("#regCompId","#regCompName","#regCompPwd1","#regCompPwd2","#regCompEmail","#regCompTel","#regCompAdd");
				}
			},
			error : function(){
				layer.alert("注册失败");
			}
		});
	}
}

/**校验集团ID唯一
 * @param _compID  集团ID
 * @param _id  提示所在的位置
 */
function checkCompIDOnly(_compID,_id){
	var flag = false;
	$.ajax({
		async : false,
		url : "../../web/userController/checkCompanyId",
		data : {
			"companyId" : _compID
		},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				flag = true;
			}
			else if(result=='hasexist')
			{
				layer.tips(data,_id);
			}else if(result == 'errorno'){
				layer.tips(data,_id);
			}else{
				layer.tips(data,_id);
			}
		},
		error : function(){
			layer.alert("校验集团ID合法性失败");
		}
	});
	return flag;
}


/**校验名称唯一
 * @param _compID  集团ID
 * @param _id  提示所在的位置
 */
function checkCompNameOnly(_compName,_id){
	var flag = false;
	$.ajax({
		async : false,
		url : "../../web/userController/checkCompanyName",
		data : {
			"compName" : _compName
		},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
				flag = true;
			}else if(result == 'hasexist'){
				layer.tips("名称已存在",_id);
				flag = false;
			}else{
				layer.tips("查询集团名称唯一性失败",_id);
				flag = false;
			}
		},
		error : function(){
			layer.alert("校验集团名称唯一性失败");
			flag = false;
		}
	});
	return flag;
}


/**
 * 添加一个cookie
 * @param name
 * @param value
 * @param expiresHours
 */
function addCookie(name,value,expiresHours){ 
		var cookieString=name+"="+escape(value); 
		//判断是否设置过期时间,0代表关闭浏览器时失效
		if(expiresHours>0){ 
			var date=new Date(); 
	//		date.setTime(date.getTime()+expiresHours*3600*1000); 
	//		cookieString=cookieString+"; expires="+date.toGMTString(); 
			cookieString=cookieString+"; expires="+new Date(new Date().getTime()+expiresHours*3600*1000).toGMTString();
		
		
	//		(new Date().getTime+expiresHours*3600*1000).toGMTString()
		} 
		document.cookie=cookieString; 
	} 

/**
 * 获取指定名称的cookie值
 * @param name
 * @returns
 */
function getCookie(name){
	var value = "";
	  var strCookie=document.cookie; 
	  var arrCookie=strCookie.split("; "); 
	  for(var i=0;i<arrCookie.length;i++){ 
	    var arr=arrCookie[i].split("="); 
	    for(var j=0;j<arr.length;j++){
	    	if(arr[j]==name){
	    		 value = unescape(arr[1]);
	    		 break;
	    	}
	    }
	  }
	  return value;
} 






