/**判断中文正则
 * @param _param  表单元素的值
 * @param _id  表单元素的ID,例如："#idValue"
 * @param _tips  提示信息
 */
var checkHanzi = function(_param,_id,_tips){
	var reg = /\s/g;
	if(_param===""){
		layer.tips((_tips===undefined?"文字":_tips) + "不能为空",_id);
//		layer.alert(_tips+":false");
		return false;
	}else if(reg.test(_param)){
		layer.tips((_tips===undefined?"文字":_tips) + "不能有空格",_id);
//		layer.alert(_tips+":false");
		return false;
	}else{
//		layer.alert(_tips+":true");
		return true;
	}
};

/**
 * 验证密码长度
 * @param _param
 * @param _id
 * @param _tips
 */
function checkPwd(_param,_id,_tips){
	if(_param.length >=6 && _param.length <=18){
		return true;
	}else{
		layer.tips(_tips===undefined?"密码长度为6-18位":_tips,_id);
	}
}


/**
 * 开始事件不能大于结束时间
 */
var chkTime = function(_beginTime,_endTime,_id){
	 var arr = _beginTime.split("/");
	 var starttime = new Date(arr[2], arr[1], arr[0]);
	 var starttimes = starttime.getTime();

	 var arrs = _endTime.split("/");
	 var lktime = new Date(arrs[2], arrs[1], arrs[0]);
	 var lktimes = lktime.getTime();

	 if (starttimes > lktimes) {
		layer.tips("开始日期不能大于结束日期",_id);
//        alert('开始时间大于离开时间，请检查');
        return false;
	 }
	 else{
        return true;
	 }
}


/**
 * 过滤特殊字符
 */
var filSpeSym = function(_param,_id,_tips){
	var reg = /((?=[\x21-\x7e]+)[^A-Za-z0-9])/g;
	
	if(_param===""){
		layer.tips((_tips===undefined?"文字":_tips) + "不能为空",_id);
		return false;
	}else if(reg.test(_param)){
		layer.tips((_tips===undefined?"文字":_tips) + "不能包含特殊符号",_id);
		return false;
	}else{
		return true;
	}
};


/**判断电话正则
 * @param _param  表单元素的值
 * @param _id  表单元素的ID,例如："#idValue"
 * @param _tips  提示信息
 */
var checkNum = function(_param,_id,_tips){
//	var reg = /(\d{3,4}\d{7,8})|(1[3-8][0-9]{9})/g;
	var reg=/^(\d{11})$|^(\d{3,5}[-]?\d{6,8})$/g;
	//^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$
	if(_param===""){
		layer.tips(_tips===undefined?"电话":_tips + "不能为空",_id);
		return false;
	}else if(!reg.test(_param)){
		layer.tips(_tips===undefined?"电话":_tips + "格式不正确",_id);
		return false;
	}else{
		return true;
	}
};
/**判断Email正则
 * @param _param  表单元素的值
 * @param _id  表单元素的ID,例如："#idValue"
 * @param _tips  提示信息
 */
var checkEmail = function(_param,_id,_tips){
//	var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/g;//校验email
	var reg = /(http\:\/\/)?([\w.]+)(\/[\w- \.\/\?%&=]*)?/gi;//校验网址
	if(_param===""){
		layer.tips(_tips===undefined?"Email":_tips + "不能为空",_id);
		return false;
	}else if(!reg.test(_param)){
		layer.tips(_tips===undefined?"Email":_tips + "格式不正确",_id);
		return false;
	}else{
		return true;
	}
};
/**判断是否上传相关图片
 * @param _param  isUpload
 * @param _id  表单元素的ID,例如："#idValue"
 * @param _tips  提示信息
 */
var checkUpload = function(_param,_id,_tips){
	if($(_id).attr(_param)===undefined){
		layer.tips("请上传" + _tips===undefined?"图片！":_tips,_id);
		return false;
	}else{
		return true;
	}
};

 /**判断两次密码是否一样
 * @param _id1  第一次密码  ID,例如："idValue"
 * @param _id2  第二次密码  ID,例如："idValue"
 * @param _tips  错误提示
 * @returns {Boolean}
 */
var newOpCodeIsSame = function(_id1,_id2,_tips){
	if($("#"+_id1).val() === $("#" + _id2).val()){
		return true;
	}else{
		layer.tips(_tips===undefined?"两个新密码不一样！":_tips,"#"+_id2)
		return false;
	}
};
/**清空Text输入框
 * @param _ids  text的ID,可以传任意多个    "#id1","#id2"...
 */
var cleanText = function(){
	for(var i = 0 ; i < arguments.length;i++){
		$(arguments[i]).val("");
	}
};
/**阻止事件传播
 * @param _ids  text的ID,可以传任意多个    "#id1","#id2"...
 */
var stopPropage = function(){
	for(var i = 0 ; i < arguments.length;i++){
		$(arguments[i]).on("click",function(event){
			event.stopPropagation();
		});
	}
};


/**
 * 查询集团用户是否登录
 */
var checkCompanyInfo = function(){
	var flag = false;
	$.ajax({
		async : false,
		url : "../../web/pageController/getCompanyInfo",
		data : {},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(result == 'pass'){
				flag = true;
			}
		}
	});
	return flag;
}

/**
 * 查询当前登录的操作员的权限
 */
function checkPermission(){
	var permiss = "";
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/userController/checkPermission",
		data : {},
		dataType : "JSON",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				permiss = data;
			}else{
				layer.alert(data);
			}
		},
		error : function(){
			layer.alert("查询当前操作员权限失败");
		}
	});
	return permiss;
}

/**
 * 注销集团用户与操作员
 */
function opLogout(){
//	layer.alert("退出...");
	layer.msg('确定要注销集团用户和操作员吗?',{
		time : 0,
		btn : ['确定','取消'],
		yes : function(index){
			layer.close(index);
//			icon : 1;
			window.location.href="../../web/pageController/logout";
		}
	});
	
}




