$(function(){
//	layer.alert(111);
	var id = $("#devId").val();
//	layer.alert(id);
	selBindUser(id);
	
});

function selBindUser(id){
	var tx="";
	$.ajax({
		  async: false,
		  type : "POST",
		  url : "../../web/devicesController/selBindUser",
		  data : {
			"id" : id  
		  },
		  dataType : "json",
		  success : function(retJs){
			  var result = retJs.DataResult;
			  var data = retJs.message;
			  if(result == 'pass'){
				  if(data!=null && ""!=data){
					  for(var i=0;i<data.length;i++){
//					  var time = data[i]["addDate"];
//					  var d = new Date(data[i]["addDate"]);
//					  var now = data[i]["addDate"].format("yyyy-MM-dd hh:mm:ss");
//					  layer.alert("now:"+now);
//					  var addDate = formatDate(d);
//					  layer.alert(time.format("yyyy-MM-dd hh:mm:ss"));
//					  layer.alert(addDate);//#jiechuDIV
						  tx += "<tr>"
							  +"<td>"+data[i]["userName"]+"</td>"
							  +"<td>"+data[i]["binderType"]+"</td>"
							  +"<td>"+data[i]["addDate"]+"</td>"
							  +"<td><a data-toggle=\"modal\" href=\"javascript:\" onclick=\"javascript:cancelSQ('"+data[i]["id"]+"');\">解除</a></td>"
							  +"</tr>";
					  }
				  }
				  $("#tbBindUser").empty();
				  $("#tbBindUser").append(tx);
			  }else{
			  }
		  },
		  error : function(){
			  layer.alert("查询绑定设备的绑定用户异常...");
		  }
	  });
	 
}

/**
 * 授权前进行验证
 */
function checkAccount(){
	var accountType = $("#accountType").val();
	var account = $("#accountNum").val();
	var id = $("#devId").val();
//	layer.alert("id:"+id);
//	layer.alert(accountType+":"+account);
//	layer.alert("验证账号");
	var flag = false;
	
	if(accountType==="个人账号"){
		if(account != null && account != "" &&
				checkHanzi($("#accountNum").val(), "#accountNum", "授权账号")&&
				checkNum($("#accountNum").val(), "#accountNum", "授权账号")){
			$.ajax({
				async: false,
				type : "POST",
				url : "../../web/devicesController/checkAccount",
				data : {
					"accountType" : accountType,
					"account" : account,
					"id" : id
				},
				dataType : "json",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					if(result == 'pass'){
//					layer.alert("账号可以授权");
						flag = true;
					}else{
						layer.tips(data,"#accountNum");
					}
				},
				error : function(){
					layer.alert("查询账号是否可以授权异常...");
				}
			});
		}
	}else{
		if(account != null && account != ""){
			$.ajax({
				async: false,
				type : "POST",
				url : "../../web/devicesController/checkAccount",
				data : {
					"accountType" : accountType,
					"account" : account,
					"id" : id
				},
				dataType : "json",
				success : function(retJs){
					var result = retJs.DataResult;
					var data = retJs.message;
					if(result == 'pass'){
//					layer.alert("账号可以授权");
						flag = true;
					}else{
						layer.tips(data,"#accountNum");
//						layer.alert(data);
					}
				},
				error : function(){
					layer.alert("查询账号是否可以授权异常");
				}
			});
		}
	}
	return flag;
}


/**
 * 添加授权
 */
document.getElementById("addSQ").onclick = function(){
	var accountType = $("#accountType").val();
	var account = $("#accountNum").val();
	var id = $("#devId").val();
	
	if(checkAccount()==true){
		$.ajax({
			async: false,
			type : "POST",
			url : "../../web/devicesController/accountAuthorize",
			data : {
				"accountType" : accountType,
				"account" : account,
				"id" : id
			},
			dataType : "json",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				if(result == 'pass'){
					layer.alert("授权成功");
					layer.msg('授权成功',{
						time : 0,
						btn : ['确定'],
						yes : function(index){
							layer.close(index);
							parent.window.location.href="../../web/pageController/toDevice";
							parent.layer.close(parent.layer.getFrameIndex(window.name));
						}
					});
				}else{
					layer.alert(data);
				}
			},
			error : function(){
				layer.alert("账号授权异常");
			}
		});
	}else{
		layer.alert("页面验证有误");
	}
}

/**
 * 解除授权
 */
function cancelSQ(id){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		
		layer.msg('确定要解除授权吗?', {
		    time: 0 //不自动关闭
		    ,btn: ['确定', '取消']
		    ,yes: function(index){
		    	delAuthorize(id);
		        layer.close(index);
		    }
		});
		
	}else{
		layer.layer.alert("您没有操作权限");
	}
}

/**
 * 解除授权
 * @param id
 */
function delAuthorize(id){
	$.ajax({
		async: false,
		type : "POST",
		url : "../../web/devicesController/delAuthorize",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
				layer.alert("解除授权成功");
				layer.msg('解除授权成功',{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						parent.window.location.href="../../web/pageController/toDevice";
						parent.layer.colose(parent.layer.getFrameIndex(window.name));
					}
				});
			}else{
				layer.alert(data);
			}
		},
		error : function(){
			layer.alert("解除授权异常...");
		}
	});
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



function formatDate1(now) {
	var year = now.getFullYear();

	var month = now.getMonth() + 1;

	var date = now.getDate();

	var hour = now.getHours();

	var minute = now.getMinutes();

	var second = now.getSeconds();

	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}