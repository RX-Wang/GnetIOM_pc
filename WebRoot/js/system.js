$(function(){
	//左侧三个导航按钮切换效果
	var _btnArray = ["baseMsgWeihuBtn","czyManageBtn","pwdModifyBtn","compPwdModifyBtn","serviceConfBtn"];
	var _divArray = ["baseMsgWeihu","czyManage","pwdModify","compPwdModify","serviceConf"];
	$(_btnArray).each(function(index,elem){
		$("#" + elem).click(function(){
			$("#" + elem).parent().siblings("li").removeClass("active");
			$("#" + elem).parent().addClass("active");
			$(".rig_box").attr("hidden","true");
			$("#" + _divArray[index]).removeAttr("hidden");
		});
	});
	
	//公司图片 、软件logo上传
	var _uploadArray = ["companyPic","softLogo"];
	$(_uploadArray).each(function(index,elem){
		$("input[name='"+ elem +"']").change(function(){
			var fileSize = event.target.files[0].size;
			if(fileSize > 1024*1024){
				layer.alert("图片大小不能超过2M！");
			}else{
				var form = $("#"+ elem +"Form");
				form.ajaxSubmit({
					url : "../../web/SysConfController/uploadFile",
					type : "post",
					dataType : "JSON",
					success : function(retJson){
						if(retJson.success){
							$("#"+ elem +"Show").attr("src","../.." + retJson.filePath);
							$("#"+ elem +"Show").attr("value", retJson.filePath);
						}
					},
					error : function(){
						layer.alert("上传公司图片失败");
					}
				});
			}
		});
	});
	
	//获取当前登录操作员的 账号
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/userController/getUserInfo",
		data : {
				"flag" : "1"
			},
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			for(var i =0;i<data.length;i++){
				var opCode = data[i]["opCode"];
				$("#forOpCode").attr("value",opCode);
			}
		},
		error : function(){
			layer.alert("获取当前登录用户的账号失败");
		}
	});
	
	selAllOperatorInfo();
	
	
	//获取当前登录的集团的基本信息
	$.ajax({
		async : false,
		url : "../../web/userController/getUserInfo",
		data : {
			"flag" : "0"//0标识集团用户，1标识操作员
		},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
					$("#companyId").val(data["companyId"]);
					$("#companyName").val(data["companyName"]);
					$("#oldCompanyName").val(data["companyName"]);
					$("#companyInterAddr").val(data["companyInterAddr"]);
					$("#companyPhone").val(data["companyPhone"]);
					$("#softName").val(data["softName"]);
					$("#companyAddr").val(data["companyAddr"]);
					$("#companyDescription").val(data["companyDescription"]);
					$("#companyPic").val(data["companyPic"]);
					$("#softLogo").val(data["softLogo"]);
			}else{
				layer.alert("当前没有集团登录");
			}
		},
		error :function(){
			layer.alert("获取当前登录集团的基本信息失败");
		}
	});
});

/**
 * 查询当前集团下所有操作员信息
 */
function selAllOperatorInfo(){
	/**
	 * 查询当前集团下的所有操作员信息
	 */
	$.ajax({
		async : false,
		url : "../../web/operatorController/selAllOperatorInfo",
		data : {},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				var tx="";
				$("#tBody").empty();
				if(result == 'pass'){
					for(var i =0; i<data.length;i++){
						tx+=" <tr onclick=\"addSomeClass()\">"
                         +"<td>"+(i+1)+"</td>"
                         +"<td>"+data[i]["id"]+"</td>"
                         +"<td>"+data[i]["opCode"]+"</td>"
                         +"<td>"+data[i]["opName"]+"</td>"
                         +"<td>"+data[i]["opRule"]+"</td>"
                         +" <td>"+data[i]["opDate"]+"</td>"
                         +" <td>"+data[i]["opFlag"]+"</td>"
                         +"</tr>";
					}
				}else{
					layer.alert("当前集团用户下没有操作员");
				}
				$("#tBody").append(tx);
			},
			error :function(){
				layer.alert("获取当前集团下的操作员信息失败");
			}
	});
}

var checkNameRepeat = function(_param,_id,_tips){
	var flag = false;
	var oldCompanyName=$("#oldCompanyName").val();
	var companyName = $("#companyName").val();
	if(oldCompanyName != companyName){
		$.ajax({
			async : false,
			url : "../../web/userController/checkCompanyNameIsExist",
			data : {"companyName" : _param},
			type : "POST",
			dataType : "JSON",
			success : function(data){
				var result = data["DataResult"];
				var messag= data["message"];
				if(result == 'exist'){
					layer.tips(_tips+"已经被占用",_id);
				}else if(result == 'pass'){
					flag = true;
				}else{
				}
			},
			error : function(){
				layer.alert("查询公司名称是否唯一失败！");
			}
		});
	}else{
		flag = true;
	}
	return flag;
};

document.getElementById("czyManageBtn").onclick = function(){
	selAllOperatorInfo();
};




document.getElementById("companyName").onblur = function(){
	var flag = checkHanzi($("#companyName").val(),"#companyName","公司名称");
	
	var chkName = filSpeSym($("#companyName").val(),"#companyName","公司名称");
	
	if(flag==true && chkName==true){
		checkNameRepeat($("#companyName").val(),"#companyName","公司名称");
	}
};
document.getElementById("companyInterAddr").onblur = function(){checkHanzi($("#companyInterAddr").val(),"#companyInterAddr","公司网址")};
document.getElementById("softName").onblur = function(){checkHanzi($("#softName").val(),"#softName","软件标题")};
document.getElementById("companyAddr").onblur = function(){checkHanzi($("#companyAddr").val(),"#companyAddr","公司地址")};
document.getElementById("companyDescription").onblur = function(){checkHanzi($("#companyDescription").val(),"#companyDescription","公司描述")};
document.getElementById("companyPhone").onblur = function(){checkNum($("#companyPhone").val(),"#companyPhone","公司电话")};

//确定按钮被点击
document.getElementById("baseMsgBtn").onclick=function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if( checkHanzi($("#companyName").val(),"#companyName","公司名称") && 
			checkNameRepeat($("#companyName").val(),"#companyName","公司名称") && 
		    checkHanzi($("#companyInterAddr").val(),"#companyInterAddr","公司网址")&&
		    checkHanzi($("#softName").val(),"#softName","软件标题")&&
			checkHanzi($("#companyAddr").val(),"#companyAddr","公司地址")&&
			checkHanzi($("#companyDescription").val(),"#companyDescription","公司描述")&&
			checkNum($("#companyPhone").val(),"#companyPhone","公司电话")
	//		checkUpload("value", "#companyPicShow","公司图片")&&
	//		checkUpload("value", "#softLogoShow","软件LOGO")
		){
			var _data = {
					"companyId" : $("#companyId").val(),
					"companyName" : $("#companyName").val(),
					"companyInterAddr" : $("#companyInterAddr").val(),
					"softName" : $("#softName").val(),
					"companyAddr" : $("#companyAddr").val(),
					"description" : $("#companyDescription").val(),
					"companyPhone" : $("#companyPhone").val(),
					"companyPic" : $("#companyPicShow").attr("value"),
					"softLogo" : $("#softLogoShow").attr("value")
				};
			$.ajax({
				async : false,
				url : "../../web/userController/updateCompanyInfo",
				data : _data,
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					
					if(result == 'pass'){
//						layer.alert("基础信息维护保存成功！");
						layer.alert('基础信息维护保存成功！',{
							time : 0,
							btn : ['确定'],
							yes : function(index){
								window.location.href="../../web/pageController/toIndex";
								$("#companyName").val("");
								$("#companyInterAddr").val("");
								$("#softName").val("");
								$("#companyAddr").val("");
								$("#companyDescription").val("");
								$("#companyPhone").val("");
								$("#companyPicShow").removeAttr("value").attr("src","../../images/logo_img.png");
								$("#softLogoShow").removeAttr("value").attr("src","../../images/logo_litimg.png");
								layer.close(index);
							}
						});
					}else{
						layer.alert("维护基础信息失败");
					}
				},
				error : function(){
					layer.alert("基础信息维护保存失败！");
				}
			});
		}
	}else{
//		layer.alert("您没有操作权限");
		layer.alert("您没有操作权限");
	}
};

//为操作员管理中的每一条记录  (tr标签)添加点击事件
function addSomeClass(){
	$(".forModOrDel").removeClass("forModOrDel");
	$(event.target).parent().addClass("forModOrDel");
}
//用于添加的弹出层的取消按钮
document.getElementById("managerCansAdd").onclick = function(){
	$("#opCodeAdd").val("");
	$("#opPwdAdd").val("");
	$("#opNameAdd").val("");
	$("#systemAdd").modal("hide");
};
//用于修改的弹出层的取消按钮
document.getElementById("managerCansModify").onclick = function(){
	$("#opCodeModify").val("");
	$("#opNameModify").val("");
	$("#systemModify").modal("hide");
};

//用于添加的弹出层的 【操作员账号】输入框 ，验证账号的唯一性
function opCodeIsOnly(){
	var flag = false;
	
	//((?=[\x21-\x7e]+)[^A-Za-z0-9])
	var chk = filSpeSym($("#opCodeAdd").val(),"#opCodeAdd","操作员账号");
	
	if(chk && checkHanzi($("#opCodeAdd").val(),"#opCodeAdd","操作员账号")){
	$.ajax({
			async : false,
			url : "../../web/operatorController/checkOpCodeIsExist",
			data : {
				"opCode" : $("#opCodeAdd").val()
			},
			type : "POST",
			dataType : "JSON",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					layer.tips("账号可用","#opCodeAdd");
					flag = true;
				}else{
					layer.tips("账号已被使用","#opCodeAdd");
//					return false;
				}
			},
			error : function(){
				layer.alert("校验账号是否可用失败！");
			}
		});
	}
	return flag;
};

//用于添加操作员的弹出层的确定按钮
document.getElementById("managerSubAdd").onclick = function(){
//	layer.alert("277opName:"+$("#opNameAdd").val());
	
	var chkName = filSpeSym($("#opNameAdd").val(),"#opNameAdd","操作员姓名");
	if(
		checkHanzi($("#opCodeAdd").val(),"#opCodeAdd","操作员账号") &&
		checkHanzi($("#opPwdAdd").val(),"#opPwdAdd","操作员密码") &&
		checkPwd($("#opPwdAdd").val(),"#opPwdAdd") &&
		chkName && checkHanzi($("#opNameAdd").val(),"#opNameAdd","操作员姓名")&&opCodeIsOnly()
		){
		var _data = {
				"opCode" : $("#opCodeAdd").val(),
				"opPwd" : $("#opPwdAdd").val(),
				"opName" : $("#opNameAdd").val(),
				"opRule" : $("#opRuleAdd").val()
		}
		$.ajax({
			async : false,
			url : "../../web/operatorController/addOperator",
			data : _data,
			type : "POST",
			dataType : "JSON",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
//					layer.alert("添加操作员成功！");
//					window.location.href="../../web/pageController/toSystem";
					layer.msg('添加操作员成功！', {
					    time: 0 //不自动关闭
					    ,btn: ['确定']
					    ,yes: function(index){
					        layer.close(index);
					        icon: 6;
					        window.location.href="../../web/pageController/toSystem";
					    }
					});
					$("#opCodeAdd").val("");
					$("#opPwdAdd").val("");
					$("#opNameAdd").val("");
				}else{
					layer.alert("添加操作员失败");
				}
				/*$("#tBody").append("<tr onclick=\"addSomeClass()\">"+
		                               "<td>1</td>"+
		                               "<td>后台传来的UUID</td>"+
		                               "<td>"+ $("#opCodeAdd").val() +"</td>"+
		                               "<td>"+ $("#opNameAdd").val() +"</td>"+
		                               "<td>"+ $("#opRuleAdd").val() +"</td>"+
		                               "<td>后台传来的时间</td>"+
		                               "<td>这个你自己看着办吧</td>"+
									"</tr>");*/
			},
			error : function(){
				layer.alert("添加操作员失败！");
			}
		});
		$("#systemAdd").modal("hide");
	}
}
//用于修改的弹出层的确定按钮
document.getElementById("managerSubModify").onclick = function(){
//	layer.alert("326opName:"+$("#opNameModify").val());
	if(checkHanzi($("#opNameModify").val(),"#opNameModify","操作员姓名")){
		$.ajax({
			url : "../../web/operatorController/updateOperator",
			data : {
				"id" : $("#opId").val(),
				"opCode" : $("#opCodeModify").val(),
				"opName" : $("#opNameModify").val(),
				"opRule" : $("#opRuleModify").val()
			},
			type : "POST",
			dataType : "JSON",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					layer.alert("修改操作员成功！");
					$("#systemModify").modal("hide");
					$(".forModOrDel").children().each(function(index,elem){
						if(index==3)
							elem.innerHTML = $("#opNameModify").val();
						else if(index==4)
							elem.innerHTML = $("#opRuleModify").val();
					});
				}else{
					layer.alert("修改操作员失败");
				}
				
			},
			error : function(){
				layer.alert("修改操作员失败！");
			}
		});
	}
};

//修改按钮
document.getElementById("menagerModify").onclick = function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if($(".forModOrDel").length==0)
			layer.alert("请选择一条需要修改的记录");
		else{
			var _opCode = "";
			var _opName = "";
			var _opRule = "";
			var _id = "";
			$(".forModOrDel").children().each(function(index,elem){
				if(index==2){
					_opCode = elem.innerHTML;
				}else if(index==3){
					_opName = elem.innerHTML;
				}else if(index==4){
					_opRule = elem.innerHTML;
				}else if(index==1){
					_id = elem.innerHTML;
				}
			});
			
			if(_opCode=="system"){
				layer.alert("默认管理员不允许操作");
			}else{
				
				$("#opId").val(_id);
				$("#opCodeModify").val(_opCode);
				$("#opNameModify").val(_opName);
				$("#opRuleModify option").each(function(){
					if($(this).val() == _opRule){
						$(this).attr("selected","selected");
					}
				});
				$("#systemModify").modal("show");
			}
		}
	}else{
		layer.alert("您没有操作权限");
	}
};
//删除按钮
document.getElementById("menagerDel").onclick = function(){
	var permission=checkPermission();
//	if(permission=="普通操作员" || permission=="系统操作员"){
	if(permission=="系统操作员"){
		if($(".forModOrDel").length==0)
			layer.alert("请选择一条需要删除的记录");
		else{
				var _opCode = "";
				var _id = "";
				$(".forModOrDel").children().each(function(index,elem){
					if(index==2){
						_opCode = elem.innerHTML;
					}else if(index == 1){
						_id = elem.innerHTML;
					}
				});
				
				if(_opCode=="system"){
					layer.alert("默认管理员不允许操作");
				}else{
//					var boo = confirm("确定要删除这条记录吗？");
					
					layer.msg('确定删除这条记录吗?', {
					    time: 0 //不自动关闭
					    ,btn: ['确定', '取消']
					    ,yes: function(index){
					    	delOp(_id,_opCode);
					        layer.close(index);
					        icon: 6;
					    }
					});
				}
		}
	}else{
		layer.alert("您没有操作权限");
	}
};

/**
 * 删除操作员
 * @param _id  操作员记录id
 * @param _opCode 操作员账号
 */
function delOp(_id,_opCode){
	$.ajax({
		url : "../../web/operatorController/delOperator",
		data : {
			"id" : _id,
			"opCode" : _opCode
		},
		type : "POST",
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
//				layer.alert("删除操作员成功！");
//				window.location.href="../../web/pageController/toSystem";
				
				layer.msg('删除操作员成功！', {
				    time: 0 //不自动关闭
				    ,btn: ['确定']
				    ,yes: function(index){
				        layer.close(index);
				        icon: 6;
				        window.location.href="../../web/pageController/toSystem";
				    }
				});
				
				
				
			}else{
				layer.alert("删除操作员失败");
			}
		},
		error : function(){
			layer.alert("删除操作员失败！");
		}
	});
}



//根据操作员账号或名称查询操作员
function selectOperatorByCondition(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		var opNameOrCode = $("#opNameOrCode").val();
		if(opNameOrCode.trim()!="" && opNameOrCode.trim()!=null){
			$.ajax({
				async : false,
				url : "../../web/operatorController/selectOperatorByCondition",
				data : {
					"opNameOrCode" : opNameOrCode
				},
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
						var result = retJs.DataResult;
						var data = retJs.message;
						var tx="";
						$("#tBody").empty();
						if(result == 'pass'){
							for(var i =0; i<data.length;i++){
								tx+=" <tr onclick=\"addSomeClass()\">"
		                         +"<td>"+(i+1)+"</td>"
		                         +"<td>"+data[i]["id"]+"</td>"
		                         +"<td>"+data[i]["opCode"]+"</td>"
		                         +"<td>"+data[i]["opName"]+"</td>"
		                         +"<td>"+data[i]["opRule"]+"</td>"
		                         +" <td>"+data[i]["opDate"]+"</td>"
		                         +" <td>"+data[i]["opFlag"]+"</td>"
		                         +"</tr>";
							}
						}else{
							layer.alert("没有找到符合条件的操作员信息");
						}
						$("#tBody").append(tx);
					},
					error :function(){
						layer.alert("根据条件查询操作员信息失败");
					}
			});
		}
	}else{
		layer.alert("您没有操作权限");
	}
}

//判断当前登录操作员输入的旧密码是否正确
function oldOpCodeIsRight(){
	var flag = false;
	var oldPwd = $("#oldPwd").val();
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/userController/getUserInfo",
		data : {
//				opCode : $("#forOpCode").attr("value"),
//				opPwd : $("#oldPwd").val()
				"flag" : "1"
			},
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			for(var i =0;i<data.length;i++){
				var oldPwd1 = data[i]["opPwd"];
				if(oldPwd == oldPwd1){
					flag = true;
				}else{
					layer.tips("原始密码错误！","#oldPwd");
					flag = false;
				}
			}
		},
		error : function(){
			layer.alert("校验旧密码是否正确失败！");
		}
	});
	return flag;
}
//判断集团的旧密码是否正确
function oldCompCodeIsRight(){
	var flag = false;
	var cId = $("#compId").val();
	var oldPwd = $("#compOldPwd").val();
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/userController/getUserInfo",
		data : {
//			opCode : $("#compId").val(),
//			opPwd : $("#compOldPwd").val()
			"flag" : "0"
		},
		dataType : "JSON",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(result == 'pass'){
				var companyId = data["companyId"];
				var cPwd = data["companyPwd"];
				if(companyId!=cId || oldPwd!=cPwd){
					layer.tips("集团ID或原始密码错误！","#compId");
					flag = false;
				}else if(companyId==cId && oldPwd==cPwd){
					flag = true;
				}
			}
		},
		error : function(){
			layer.alert("校验旧密码是否正确失败！");
		}
	});
	return flag;
}

//旧密码丢失焦点
document.getElementById("oldPwd").onblur = function(){
	return checkHanzi($("#oldPwd").val(),"#oldPwd","原始密码") && oldOpCodeIsRight();
};
//修改密码页面的 【确定】按钮被点击
document.getElementById("modifyPwdBtn").onclick= function(event){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		event.stopPropagation();
		if(checkHanzi($("#oldPwd").val(),"#oldPwd","原始密码") && 
		   filSpeSym($("#newPwd1").val(),"#newPwd1","新密码") && 
		   checkHanzi($("#newPwd1").val(),"#newPwd1","新密码") && 
		   checkHanzi($("#newPwd2").val(),"#newPwd2","新密码") &&
		   oldOpCodeIsRight() && newOpCodeIsSame("newPwd1","newPwd2","两次输入的新密码不一样！")
		   ){
			$.ajax({
				url : "../../web/operatorController/updateOpPwd",
				type : "POST",
				data : {
						"opCode" : $("#forOpCode").attr("value"),
						"newPwd" : $("#newPwd1").val()
					},
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					if(result == 'pass'){
						layer.alert("修改密码成功！");
						$("#oldPwd").val("");
						$("#newPwd1").val("");
						$("#newPwd2").val("");
					}else{
						layer.alert("修改当前操作员密码失败");
					}
				},
				error : function(){
					layer.alert("修改密码失败！");
				}
			});
		}
	}else{
		layer.alert("您没有操作权限");
	}
}

//修改集团密码中的[取消]按钮
$("#compCan").click(function(){
	$("#compId").val("");
	$("#compOldPwd").val("");
	$("#compPwx1").val("");
	$("#compPwx2").val("");
});
//修改集团密码中的[确定]按钮
$("#compSub").click(function(){
	
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		var compId = $("#compId").val();
		var compOldPwd = $("#compOldPwd").val();
		var compPwx1 = $("#compPwx1").val();
		var compPwx2 = $("#compPwx2").val();
		if(checkHanzi(compId,"#compId","集团ID") &&
		   checkHanzi(compOldPwd,"#compOldPwd","原始密码") &&
		   oldCompCodeIsRight()&&
		   checkHanzi(compPwx1,"#compPwx1","新密码") && 
		   filSpeSym(compPwx1,"#compPwx1","新密码") &&
		   checkHanzi(compPwx2,"#compPwx2","确认密码") &&
		   newOpCodeIsSame("compPwx1", "compPwx2", "两次输入的新密码不一样！")){
			$.ajax({
				url : "../../web/userController/updateCompanyPwd",
				data : {
					"companyId" : compId,
					"companyPwd" : compPwx1
				},
				type : "POST",
				dataType : "JSON",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					if(result == 'pass'){
						layer.alert("集团密码修改成功");
						$("#compId").val("");
						$("#compOldPwd").val("");
						$("#compPwx1").val("");
						$("#compPwx2").val("");
					}else{
						layer.alert("集团密码修改失败");
					}
				},
				error : function(){
					layer.alert("修改集团密码失败！");
				}
			});
		}
	}else{
//		layer.alert("您没有操作权限");
		layer.alert("您没有操作权限");
	}
});

document.getElementById("opAdd").onclick= function(){
//$("#opAdd").click(function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
//		$(this).attr("href","#systemAdd").click();
		$("#systemAdd").modal("show");
	}else{
		layer.alert("您没有操作权限");
	}
//});
}

/**
 * 查询当前登录的操作员的权限
 */
//function checkPer(){
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




