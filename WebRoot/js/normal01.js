/**
 * Created by Gnet on 2015/11/17.
 */
$(function(){
	synchroGroup();
	
	//从地图页面跳过来是执行
//	var isCheck = $("#isCheck").val();
	var uniqueId = $("#uniqueId").val();
//	alert(isCheck);
//	alert("uniqueId:"+uniqueId);
	if(uniqueId !="" && uniqueId !="null"){
//		var uniqueId = $("#uniqueId").val();
		selDevByCondition(uniqueId);
	}
	
	selTotalDevNum();
	
	
    $("#tablist li").click( function(){
        $("#tablist li").removeClass("active");
        $(this).addClass("active");
        $("#tabcon .item").hide();
        var _index=$(this).index();
        $("#tabcon .item:eq("+_index+")").show();
    })

    $(".o_topnav li").click(function(){
        $(".o_topnav li").removeClass("active");
        $(this).closest("li").addClass("active");
        $(this).addClass("active");
    });
    
    $(".rig_table tbody tr").click(function(){
        $(".rig_table tbody tr").removeClass("active");
        $(this).closest("tr").addClass("active");
        $(this).addClass("active");
    });

    $(".rig_block .device_list .device_li").click(function(){ 
        $(".rig_block .device_list .device_li").removeClass("active");
        $(this).closest("div").addClass("active");
        $(this).addClass("active");
    });
    
    //点击添加 按钮以后的弹出层,弹出层里的 确定按钮的点击事件    添加分组
    $("#addGroupBtn").click(function(){
    	//先判断是否填写了组名称
    	if($("#groupNameText").val()==""){
    		$("#groupNameText").next().remove();
    		$("#groupNameText").after("<span style=\"color: red;\">请填写组名称<span>");
    	}else{
    		
    		//保存分组
//    		var parId = $("#addGroupBtn").attr("parentId");//拿到父节点的ID
//    		var groupName =  $("#groupNameText").val();
    		//filSpeSym(newGroupName, "#modilfygroupNameText", "组名称")
    		if(filSpeSym($("#groupNameText").val(), "#groupNameText", "组名称") && selGroupIsExist($("#addGroupBtn").attr("parentId"),$("#groupNameText").val())){
    			$.ajax({
        			async : false,
        			type : "POST",
        			url : "../../web/groupController/addGroup",
        			data : {
        				"groupId" : $("#addGroupBtn").attr("parentId"),
        				"groupName" : $("#groupNameText").val()
        			},
        			dataType : "JSON",
        			type : "post",
        			success : function(data){
        				if(data.DataResult === "pass"){
        					$(".wxqGroupLi > a").addClass("forChild");
        					$(".wxqGroupLi").addClass("active");
        					$("#addGroupBtn").removeAttr("parentId");//删除父节点的ID
        		    		//先移除  输入框后的错误提示
        		    		if($("#groupNameText").siblings("span").length>0)
        		    			$("#groupNameText").siblings("span").remove();
        		    		var _newULClass;
        		    		//如果该li下没有ul，那么新添加一个ul，如果有了，那就直接添加子级li
        		    		if($(".wxqGroupLi").has("ul").length==0){
        		    			//新添加的每一级最外边的ul
        		    			var _newUl = $("<ul>");
        		    			//被点击的添加按钮所在的ul 标签。
        		    			var _ul = $(".wxqGroupLi").parent();
        		    			//为新添加的UL标签添加class(分为二级、三级、四级)
        		    			if(_ul.hasClass("menu")){
        		    				_newUl.addClass("second-ul");
        		    				_newULClass = "second-ul";
        		    			}else if(_ul.hasClass("second-ul")){
        		    				_newUl.addClass("third_ul");
        		    				_newULClass = "third_ul";
        		    			}else if(_ul.hasClass("third_ul")){
        		    				_newUl.addClass("fourth_ul");
        		    				_newULClass = "fourth_ul";
        		    			}
        		    			//为ul添加第一个具有  正式功能   li标签
        		    			_newUl.append(
        		    					"<li class=\"active\" title=\""+ $("#groupNameText").val() +"\">"+
        		    					"   <a id=\""+ data.id +"\" onclick=\"leftzhedie()\" parId=\""+ data.parId +"\">"+
        		    					"		<span>"+($("#groupNameText").val().length>4?$("#groupNameText").val().substring(0,4)+"...":$("#groupNameText").val())+"</span>"+
        		    				//	"		<span class=\"badge badge-roundless\">0</span>"+
        		    					"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"+
        		    					"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"+
        		    					"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"+
        		    					"	</a>"+
        		    					"</li>");
        		    			$(".wxqGroupLi").append(_newUl).removeClass("wxqGroupLi");
        		    			_newUl.slideDown("fast");
        		    			$("#group").modal("hide");
        		    		}else{
        		    			$(".wxqGroupLi").find("ul li").removeClass("active");
        		    			$(".wxqGroupLi").children(":last").append( 
        		    					"<li class=\"active\" title=\""+ $("#groupNameText").val() +"\">"+
        		    					"   <a id=\""+ data.id +"\" onclick=\"leftzhedie()\" parId=\""+ data.parId +"\">"+
        		    					"		<span>"+($("#groupNameText").val().length>4?$("#groupNameText").val().substring(0,4)+"...":$("#groupNameText").val())+"</span>"+
//        		    					"		<span class=\"badge badge-roundless\">222</span>"+
        		    					"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"+
        		    					"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"+
        		    					"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"+
        		    					"	</a>"+
        		    					"</li>");
        		    			$(".wxqGroupLi").append(_newUl).removeClass("wxqGroupLi");
        		    			$("#group").modal("hide");
        		    		}
        		    		$("#groupNameText").val("");
        				}else{
        					layer.alert(data.message)
        				}
        			},
        			error : function(){
        				layer.alert("添加分组失败,请稍后重试");
        			}
        		});
    		}else{
    			layer.tips("分组已经存在","#groupNameText");
    		}
    	}
    });
    //左侧导航，添加按钮end
    //添加分组的弹出层里面的  取消按钮
    $("#consalGroupBtn").click(function(){
    	$("#groupNameText").val("");
    	if($("#groupNameText").next().is("span")){
    		$("#groupNameText").next().remove();
    	}
    	$("#group").modal("hide");
    });
    
    //搜索按钮
    $("#groupSearch").click(function(){
    	//根据搜索框中的名字搜索到的 目标组所在的span
    	var _targetGroup = $(".forSearch").siblings("ul").find("span[title='"+ $("#groupSearchText").val() +"']");
    	//目标组所在的ul的class
    	var _ulClass = _targetGroup.parent().parent().parent().attr("class");
    	//目标组所在的ul展开
    	if(_ulClass==="second-ul"){
    		$(".forSearch").siblings("ul").find("ul").slideUp("fast");
    		$(".forSearch").siblings("ul").slideDown("fast");
    	}else if(_ulClass==="third_ul"){
    		$(".forSearch").siblings("ul").find("ul").slideUp("fast");
    		$(".forSearch").siblings("ul").slideDown("fast");
    		_targetGroup.parent().parent().parent().slideDown("fast");
    	}else if(_ulClass==="fourth_ul"){
    		$(".forSearch").siblings("ul").slideDown("fast");
    		$(".forSearch").siblings("ul").find("ul").slideDown("fast");
    		_targetGroup.parent().parent().parent().slideDown("fast");
    	}
    	//forSearch下的所有li加上active
    	$(".forSearch").parent().find("li").addClass("active");
    	//目标组所在的li添加active  class
    	_targetGroup.parent().parent().addClass("active");
    	//目标组所在的li的同级去掉active  class
    	_targetGroup.parent().parent().siblings().removeClass("active");
    	
    	$(".forSearch").removeClass("forAjax");
    	//目标组所在的li的a标签添加forSearch  class
    	_targetGroup.parent().addClass("forAjax");
    	
    });
    
});


/**
 * 查询要添加的分组在当前分组下是否已经存在
 * @param parId
 * @param groupName
 */
function selGroupIsExist(parId,groupName){
	var flag = false;
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/groupController/selGroupIsExist",
		data : {
			"groupId" : parId,
			"groupName" : groupName
		},
		dataType : "JSON",
		type : "post",
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(result == 'pass'){
				flag = true;
			}else if(result == 'hasexist'){
				flag = false;
			}else{
				layer.alert("查询分组是否存在失败,请稍后重试");
				flag = false;
			}
		}
	});
	return flag;
}



/**
 * 查询当前集团下所有设备总数
 */
function selTotalDevNum(){
	var count = 0;
	$.ajax({
		async : false,
		url : "../../web/devicesController/selTotalDevNum",
		data:{},
		dataType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs);
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(result == 'pass'){
				$(".o_rignum").empty();
				$(".o_rignum").html("共有"+data+"个设备");
			}else{
				$(".o_rignum").empty();
				$(".o_rignum").html("共有"+data+"个设备");
			}
		},
		error : function(){
			layer.alert("查询当前集团下所有的设备数量失败,请稍后重试");
		}
	});
}




//左侧导航中的(加号图标)添加按钮
function jiahao(){
	//目前只准添加4级
	if($(event.target).parent().parent().parent().hasClass("fourth_ul")){
		event.stopPropagation();
		layer.alert("最多只能添加四级子目录");
	//	$(event.target).parent().attr("id")
	}else{
		//给添加按钮所在的li标签添加class=wxqGroupLi
		event.stopPropagation();
		$(event.target).parent().parent().addClass("wxqGroupLi");
		$("#group").modal("show");
		//给弹出框的 确认按钮添加 父节点的ID
		$("#addGroupBtn").attr("parentId",$(event.target).parent().attr("id"));
	}
};


//左侧导航折叠
function leftzhedie(){
	var clickedA;
	if(event.target.tagName === "SPAN"){
		clickedA = $(event.target).parent();
	}else{
		clickedA = $(event.target);
	}
    if(clickedA.next().is("ul")){
        if(clickedA.next("ul").is(":hidden")){
        	clickedA.parent().siblings("li").removeClass("active");//同级的其他li标签去掉 active 
        	clickedA.parent().parent().find("ul:visible").slideUp("fast");//同级的其他li标签里面的所有ul收起来。
        	clickedA.parent().addClass("active");//当前被点击的a标签所在的li加上active
        	clickedA.next("ul").slideDown("fast");//当前被点击的a标签所在的li下的第一个ul展开。
        	clickedA.next("ul").find("li").removeClass("active");//当前被点击的a标签所在的li下的第一个ul里面的所有li标签去掉active属性。
        }else{
        	clickedA.siblings().children().removeClass("active");
        	clickedA.next("ul").slideUp("fast");
        }
    }else{
    	clickedA.parent().addClass("active");
    	clickedA.parent().siblings().find("ul").slideUp("fast");
    	clickedA.parent().siblings().children("ul").find("li").removeClass("active");
    }
    clickedA.parent().siblings().removeClass("active");
    //给被选中的 组 添加特殊class，用来给搜索使用
    $("ul li a").removeClass("forSearch");
    clickedA.addClass("forSearch");
    //给被点击的a标签添加forChild  class，再次点击移除 forChild
    if(clickedA.hasClass("forChild")){
    	clickedA.removeClass("forChild");
    	//移除被点击的节点下面的子节点
    	clickedA.next().remove();
    	var wl = window.localStorage;
    	if(wl){
    		//如果被点击的节点下面有设备，就显示出来
    		if(wl.getItem(clickedA.attr("id")).length>0){
    			$("#typeListTb").children().remove();
    			$("#typePicDiv").children().remove();
    			$("#typeListTb").append(JSON.parse(wl.getItem(clickedA.attr("id")))._typeList);
    			$("#typePicDiv").append(JSON.parse(wl.getItem(clickedA.attr("id")))._typePic);
    			wl.removeItem(clickedA.attr("id"));
    			//下面三个： 让列表样式显示，图标样式和没有设备  隐藏
    			$("#typeList")[0].removeAttribute("hidden");
    			$("#typePic").attr("hidden","true");
    			$("#typeNone").attr("hidden","true");
    			$("#deviceShowType").addClass("defaultType").find("i").remove();
    			$("#deviceShowType").append("<i class=\"glyphicon glyphicon-th\"></i>");
    		}
    	}
    	/*if($("#typeList").children().length>0){
    		//下面三个： 让列表样式显示，图标样式和没有设备  隐藏
			$("#typeList")[0].removeAttribute("hidden");
			$("#typePic").attr("hidden","true");
			$("#typeNone").attr("hidden","true");
    	}else{
    		//如果被点击的节点下面没有设备，就显示无设备 页面
    		$("#typeList").attr("hidden","true");
			$("#typePic").attr("hidden","true");
			$("#typeNone")[0].removeAttribute("hidden");
    	}*/
    }else{
    	$("#deviceShowType").addClass("defaultType").find("i").remove();
		$("#deviceShowType").append("<i class=\"glyphicon glyphicon-th\"></i>");
		clickedA.addClass("forChild");
    	selChildGroup(clickedA.attr("id"));
    }
};



/**查询子节点
 * @param _parId 父节点ID
 */
function selChildGroup(_parId){
	$.ajax({
		url : "../../web/groupController/selChildGroup",
		data : {
			groupId : _parId
		},
		dataType : "JSON",
		success : function(data){
			//下面三个： 让列表样式显示，图标样式和没有设备  隐藏
			$("#typeList")[0].removeAttribute("hidden");
			$("#typePic").attr("hidden","true");
			$("#typeNone").attr("hidden","true");
			//下面两个：分别清除 列表样式和图标样式 里面的设备
			$("#typeListTb").children().remove();
			$("#typePicDiv").children().remove();
			//判断有没有子节点：返回的是 pass
			if(data.DataResult === "pass"){
				//被点击的a标签所在的UL  标签的
				var _ul = $("#" + _parId).parent().parent();
				//新添加的每一级最外边的ul
    			var _newUl = $("<ul>");
    			//为新添加的UL标签添加class(分为二级、三级、四级)
    			if(_ul.hasClass("menu")){
    				_newUl.addClass("second-ul");
    			}else if(_ul.hasClass("second-ul")){
    				_newUl.addClass("third_ul");
    			}else if(_ul.hasClass("third_ul")){
    				_newUl.addClass("fourth_ul");
    			}
    			//把新的ul添加到 被点击的a标签的父级 li标签
				$("#" + _parId).parent().append(_newUl);
				//子节点的个数
				var _childLength = data.message.length;
				//把查出来的子节点插入到  ul标签中
				for(var i = 0 ; i <_childLength ; i++){
					_newUl.append("<li title=\""+ data.message[i].groupName +"\">"+
									"   <a id=\""+ data.message[i].id +"\" onclick=\"leftzhedie()\" parId=\""+ data.message[i].parId +"\">"+
									"		<span>"+(data.message[i].groupName.length>4?data.message[i].groupName.substring(0,4)+"...":data.message[i].groupName)+"</span>"+
//									"		<span class=\"badge badge-roundless\">222</span>"+
									"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"+
									"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"+
									"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"+
									"	</a>"+
								"</li>");
				}
				_newUl.slideDown("fast");
			}else{
//				layer.alert(data.message);
			}
			//判断有没有设备
			if(data.Result === "hasdev"){
				var _devicesNum = data.msg.length;
				var _typeList = "";
				var _typePic = "";
				for(var j = 0 ; j < _devicesNum ; j++){
					$("#typeListTb").append(
							"<tr onclick=\"addDeviceFocus('"+data.msg[j].id+"_td')\" id=\""+data.msg[j].id+"_td\" deviceId=\""+data.msg[j].id+"\">"+
								"<td>"+
									"<a class=\"device_news\">"+
										"<span class=\"device_user\"><img src=\"../../images/td_img1.png\"/></span>"+
										"<span class=\"device_name\">"+data.msg[j].nickName+"</span>"+
										"<span class=\"device_name\">"+data.msg[j].devicesTypeId+"</span>"+
										"<span class=\"device_num\">"+data.msg[j].uniqueId+"</span>"+
									"</a>"+
								"</td>"+
							"</tr>");
					$("#typePicDiv").append(
						"<div class=\"device_li\" onclick=\"addDeviceFocus('"+data.msg[j].id+"_div')\" id=\""+data.msg[j].id+"_div\" deviceId=\""+data.msg[j].id+"\">"+
                           "<span class=\"device_user\">"+
                           		"<img src=\"../../images/user_img.png\"/>"+
                           "</span>"+
                           "<span class=\"device_name\">"+data.msg[j].nickName+"</span>"+
                           "<span class=\"device_name\">"+data.msg[j].devicesTypeId+"</span>"+
                           "<span class=\"device_num\">"+data.msg[j].uniqueId+"</span>"+
                       "</div>");
					var wl = window.localStorage;
					if(wl){
						_typeList += "<tr onclick=\"addDeviceFocus('"+data.msg[j].id+"_td')\" id=\""+data.msg[j].id+"_td\" deviceId=\""+data.msg[j].id+"\">"+
										"<td>"+
											"<a class=\"device_news\">"+
												"<span class=\"device_user\"><img src=\"../../images/td_img1.png\"/></span>"+
												"<span class=\"device_name\">"+data.msg[j].nickName+"</span>"+
												"<span class=\"device_name\">"+data.msg[j].devicesTypeId+"</span>"+
												"<span class=\"device_num\">"+data.msg[j].uniqueId+"</span>"+
											"</a>"+
										"</td>"+
									"</tr>";
						_typePic += "<div class=\"device_li\" onclick=\"addDeviceFocus('"+data.msg[j].id+"_div')\" id=\""+data.msg[j].id+"_div\" deviceId=\""+data.msg[j].id+"\">"+
						                   "<span class=\"device_user\">"+
						                   		"<img src=\"../../images/user_img.png\"/>"+
						                   "</span>"+
						                   "<span class=\"device_name\">"+data.msg[j].nickName+"</span>"+
						                   "<span class=\"device_name\">"+data.msg[j].devicesTypeId+"</span>"+
						                   "<span class=\"device_num\">"+data.msg[j].uniqueId+"</span>"+
						             "</div>";
					}
				}
				wl.setItem(_parId, JSON.stringify({_typeList:_typeList,_typePic:_typePic}));
			}else{
				$("#typeList").attr("hidden","true");
				$("#typePic").attr("hidden","true");
				$("#typeNone")[0].removeAttribute("hidden");
			}
		},
		error : function(){
			layer.alert("查询子节点异常");
		}
	});
}
//每条设备记录被点击的时候添加选中效果，并添加一个class 给删除和修改  使用
function addDeviceFocus(_id){
	if($(".addDeviceFocus").length>0)
		$(".addDeviceFocus")[0].removeAttribute("style");
	$(".addDeviceFocus").removeClass("addDeviceFocus");
	$("#"+_id).css({
					"background-color" : "#8e8da5",
					"font" : "black"
					}).addClass("addDeviceFocus");
}

// 铅笔图标(编辑按钮)被点击
function pencilClick(){
	event.stopPropagation();
	$("#modilfyGroup").modal("show");
	$(event.target).parent().parent().addClass("modifing");
};
//铅笔图标(编辑按钮)弹出层的【确认】按钮被点击
$("#modilfyaddGroupBtn").click(function(){
	
	var newGroupName = $("#modilfygroupNameText").val();
	var groupId = $(".modifing>a").attr("id");
	var parId = $(".modifing>a").attr("parId");
	
	if(selGroupIsExist(parId,newGroupName)){
		if(filSpeSym(newGroupName, "#modilfygroupNameText", "组名称")){
			$.ajax({
				url : "../../web/groupController/updateGroup",
				data : {
					newName : newGroupName,
					groupId : groupId
				},
				type : "POST",
				dataType : "JSON",
				success : function(data){
					if(data.DataResult == 'pass'){
						$(".modifing").attr("title",newGroupName);
						$(".modifing").find("span:first").text(newGroupName.length>4?newGroupName.substring(0,4):newGroupName);
						$("#modilfyGroup").modal("hide");
						$("#modilfygroupNameText").val("");
						$(".modifing").removeClass("modifing");
					}else{
						layer.alert("修改组名称失败！");
					}
				},
				error : function(){
					layer.alert("修改组名称异常！");
				}
			});
		}
	}else{
		layer.tips("分组已经存在","#modilfygroupNameText");
	}
});
//铅笔图标(编辑按钮)弹出层的【取消】按钮被点击
$("#modilfyconsalGroupBtn").click(function(){
	$("#modilfyGroup").modal("hide");
	$("#modilfygroupNameText").val("");
	$(".modifing").removeClass("modifing");
});

//编辑完的 导航，进行保存操作  修改分组
function editOblur(){
		if($(event.target).text().trim()==null ||$(event.target).text().trim()==""){
			layer.alert("组名不能为空");
		}else{
			$(event.target).removeAttr("contenteditable");
			
			var boo = confirm("要保存吗？");
			if(boo){
				//保存修改分组
				var id = $(event.target).parent().attr("id");
				var _newName = 	$(event.target).text();
				$.ajax({
					async  : false,
					type : "POST",
					url : "../../web/groupController/updateGroup",
					data : {
						"groupId" : id,
						"newName" : _newName
					},
					dataType : "JSON",
					success : function(retJs){
						var result = retJs.DataResult;
						var data = retJs.message;
						if(result == 'pass'){
							layer.alert("修改分组成功");
						}else{
							layer.alert("修改分组失败");
						}
					},
					error : function(){
						layer.alert("修改分组异常...");
					}
				});
				layer.alert("保存成功");
			}else{
				layer.alert("取消保存");
				$(event.target).text($("#forPencilModifyName").attr("value"));
				document.getElementById("forPencilModifyName").removeAttribute("value");
			}
		}
//	}else{
//		layer.layer.alert("您没有操作权限");
//	}
};

//左侧导航，差号图标(删除按钮)start  删除分组
function chahao(evt){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		
		//判断该组元素有没有子级元素(ul)
		if($(event.target).parent().siblings("ul").length<=0){
			evt.stopPropagation();
			var id = $(event.target).parent().attr("id");
			//查询当前分组下的设备数量
			var num = selGroupDevNum(id);
			if(num == 0){
				//删除分组
				layer.msg('确定要删除此分组吗?',{
					time : 0,
					btn : ['确定','取消'],
					yes : function(index){
						
						delGroup(id);
						layer.close(index);
					}
				});
			}else{
				layer.alert("当前分组下还有设备,不允许删除");
			}
		}else{
			layer.alert("该目录下仍有子集目录,不允许删除！");
		}
	}else{
		layer.alert("您没有操作权限");
	}
};




function delGroup(id){
	var $li = $("#" + id).parent();
	$.ajax({
		async : false,
		url : "../../web/groupController/delGroup",
		data : {"groupId" : id},
		dataType : "JSON",
		success : function(data){
			if(data.DataResult === "pass"){
				layer.alert(data.message,{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						$li.remove();
					}
				});
			}else
				layer.alert(data.message);
				
		},
		error : function(){
			layer.alert("删除分组失败！");
		}
	});
}

/**
 * 查询当前分组下是否有设备
 * @param id
 */
function selGroupDevNum(id){
	var num=0;
	$.ajax({
		async  : false,
		url : "../../web/groupController/selGroupDevNum",
		data : {"groupId" : $(event.target).parent().attr("id")},
		dataType : "JSON",
		success : function(data){
			var result = data.DataResult;
			var message = data.message;
			if(result == 'pass'){
				num = message;
			}
		},
		error : function(){
			layer.alert("查询当前分组下的设备失败！");
		}
	});
	
	return num;
}

//查询第一级分组名称
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
//						layer.alert(data[i]["groupName"]);
						//查询每一级节点下的设备数量
//						var devNum = selDevNum(data[i]["id"]);
						tx += "<li class=\"active\"  title=\""+ data[i].groupName +"\">"
	                        	+"	<a id="+data[i]["id"]+" onclick=\"leftzhedie()\" parId=\""+ data[i]["parId"] +"\">"
								+"		<span >"+(data[i]["groupName"].length>4?data[i]["groupName"].substring(0,4) + "...":data[i]["groupName"])+"</span>"
//								+"		<span class=\"badge badge-roundless\">222</span>"
								+"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"
	                        	+"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"
	                        	+"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"
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
//
////查询当前目录下的设备数量
//function selDevNum(id){
////	layer.alert("46id:"+id);
//	$.ajax({
//		type : "POST",
//		url : "../../web/groupController/selDevNum",
//		data : {"groupId" : id},
//		dataType : "json",
//		success : function(retJs) {
//			var result = retJs.DataResult;
//			var data = retJs.message;
//			if (result == 'pass') {
//				for(var i = 0; i< data.length;i++){
////					layer.alert(data[i]["groupName"]);
//					//查询每一级节点下的设备数量
//					var devNum = selDevNum(data[i]["id"]);
//					
//					
//					
//				}
//				$(".menu").append(tx);
//			}
//		},
//		error : function(){
//			layer.alert("加载分组名称异常...");
//		}
//	});
//}

//设备展示方式  切换按钮
$("#deviceShowType").click(function(){
	if($(this).hasClass("defaultType") && $("#typeNone").attr("hidden")==="hidden"){
		$(this).removeClass("defaultType").children().remove();
		$(this).append("<i class=\"glyphicon glyphicon-list\"></i>");
		$("#typeList").attr("hidden","true");
		$("#typePic")[0].removeAttribute("hidden");
		if($(".addDeviceFocus").length>0){
			$(".addDeviceFocus")[0].removeAttribute("style");
			$(".addDeviceFocus").removeClass("addDeviceFocus");
		}
	}else if($("#typeNone").attr("hidden")==="hidden"){
		$(this).addClass("defaultType").children().remove();
		$(this).append("<i class=\"glyphicon glyphicon-th\"></i>");
		$("#typePic").attr("hidden","true");
		$("#typeList")[0].removeAttribute("hidden");
		if($(".addDeviceFocus").length>0){
			$(".addDeviceFocus")[0].removeAttribute("style");
			$(".addDeviceFocus").removeClass("addDeviceFocus");
		}
	}
});


/**
 * 授权按钮(弹出层)
 */
document.getElementById("shouquan").onclick=function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if($(".addDeviceFocus").length>0){
		var id = $(".addDeviceFocus").attr("deviceId");
		var _boo = checkPermission_sign(id);
		if(_boo){
			layer.open({
				type: 2,
				area: ['1000px', '350px'],
				fix: false, //不固定
				maxmin: true,
				content: '../../web/devicesController/toShowquan?id='+id
			});
		}else{
			layer.alert("您没有权限对设备授权");
		}
		}else{
			layer.alert("请选择需要授权的设备");
		}
	}else{
		layer.alert("您没有操作权限");
	}
};
/**
 * 添加按钮(弹出层)
 */
document.getElementById("addDevice").onclick=function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		layer.open({
			title : "绑定设备",
			type: 2,
			area: ['900px', '600px'],
			fix: false, //不固定
			maxmin: true,
			content: '../../web/devicesController/toAddDevice'
		});
	}else{
		layer.alert("您没有操作权限");
	}
};
function checkPermission_sign(id){
	var flag = false;
	 $.ajax({
		url : "../../web/devicesController/checkPermission_sign",   //直接填上后台的方法就行了
		data : {
			"id" : id
		},
		dataType : "JSON",
		async  : false,
		success : function(retJs){
			var result = retJs.DataResult;
			var data = retJs.message;
			if(result == 'pass'){
				if(data == '1'){
					flag = true;
				}else{
					flag =  false;
				}
			}else{
				flag =  false;
			}
		},
		error : function(){
			layer.alert("查询权限失败");
			flag =  false;
			}
	});
//	 layer.alert("567flag:"+flag);
	 return flag;
}
/**
 * 修改按钮(弹出层)
 */
document.getElementById("modifyDevice").onclick=function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if($(".addDeviceFocus").length>0){
			var id = $(".addDeviceFocus").attr("deviceId");
			var _boo = checkPermission_sign(id);
			if( _boo){
				layer.open({
					title : "设备修改",
					type: 2,
					area: ['900px', '600px'],
					fix: false, //不固定
					maxmin: true,
					content: '../../web/devicesController/modifyDevice?id='+ id
				});
			}else{
				layer.alert("您没有权限修改此设备信息");
			}
		}else{
			layer.alert("请选择需要修改的记录");
		}
	}else{
		layer.alert("您没有操作权限");
	}
};



/**
 * 删除按钮
 */
document.getElementById("removeDevice").onclick=function(){
	var permission=checkPermission();
//	if(permission=="普通操作员" || permission=="系统操作员"){
	if(permission=="系统操作员"){
		if($(".addDeviceFocus").length>0){
//			var sure = confirm("确定删除吗?");
			var sure = false;
			var id = $(".addDeviceFocus").attr("deviceId");
			layer.msg('确定删除该设备吗?', {
			    time: 0 //不自动关闭
			    ,btn: ['确定', '取消']
			    ,yes: function(index){
			    	delDev(id);
			        layer.close(index);
			        icon: 6;
			    }
			});
		}else{
			layer.alert("请选择需要删除的记录");
		}
	}else{
		layer.alert("您没有操作权限");
	}
};

/**
 * 删除设备
 * @param id
 */
function delDev(id){
	//删除   
	$.ajax({
		url : "../../web/devicesController/deleteUser",   //直接填上后台的方法就行了
		data : {
			id : id
		},
		dataType : "JSON",
		success : function(data){
			if(data.DataResult === "pass"){
				layer.alert("删除成功");
				layer.msg('删除成功',{
					time : 0,
					btn : ['确定'],
					yes : function(index){
						layer.close(index);
						icon : 6;
//						$(".addDeviceFocus").remove();
						window.location.href="../../web/pageController/toDevice";
					}
				});
			}else{
				layer.alert("删除失败");
			}
		},
		error : function(){
			layer.alert("删除记录失败！");
			}
	});
}


// 【设置】按钮被点击
$("#settingBtn").click(function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if($(".addDeviceFocus").length>0){
			var _selectedId = $(".addDeviceFocus").find("span[class='device_num']").text();
			$("#settingIframClose").next("h4").text("设置面板：" + _selectedId);
			$.ajax({
				url : "../../web/devicesettingsController/findDeviceSettingsMsg",
				data : {
					uniqueId : _selectedId,
				},
				type : "POST",
				dataType : "JSON",
				success : function(data){
					$("#fortypeID").val(data.typeID);
					$("#settingForm").empty();
					$("#setting").modal("show");
					for(var _key in data.message){
						$("#settingForm").append(
							"<div class=\"form-group\">"+
		                       " <div class=\"col-md-3\">"+
		                       "     <label style=\"width: 14em;\">"+_key+"</label>"+
		                       " </div>"+
		                       " <div class=\"col-md-6\">"+
		                       "     <input type=\"text\" id=\""+ _chineseArray[_key] +"\" class=\"form-control\" value=\""+ (data.savedData.length>0?data.savedData[0][_chineseArray[_key]]:"") +"\" placeholder=\"\">"+
		                       " </div>"+
		                       " <span class=\"col-md-3\">"+data.message[_key]+"</span>"+
		                   " </div>"
						);
					}
					var houOffset = $("#houOffset").val();
					var startTime = $("#startTime").val();
					var interTime = $("#interTime").val();
					var moveThreshold = $("#moveThreshold").val();
					var floIterTim = $("#floIterTim").val();
					var batLowThres = $("#batLowThres").val();
					//判断一下是第一次保存还是修改
					if(
							houOffset!=""&&
							startTime!=""&&
							interTime!=""&&
							moveThreshold!=""&&
							floIterTim!=""&&
							batLowThres!=""
						){
						$("#forSettingSaveOrUpdate").val("update");
					}else{
						$("#forSettingSaveOrUpdate").val("save");
					}
				},
				error : function(){
					layer.alert("查询设置模板或以保存到数据失败");
				}
			});
		}else{
			layer.alert("请选择要操作的设备");
		}
	}else{
		layer.alert("您没有操作权限");
	}
})

//设置页面 中的  【保存】按钮被点击
$("#settingSaveBtn").click(function(){
	var _selectedId = $(".addDeviceFocus").find("span[class='device_num']").text();   //被选中的记录的 uniqueID
	var houOffset = $("#houOffset").val(); 			 //时区
	var startTime = $("#startTime").val();			//定时唤醒开始时间
	var interTime = $("#interTime").val();			//定时唤醒时间间隔
	var moveThreshold = $("#moveThreshold").val();	//震动唤醒阈值
	var floIterTim = $("#floIterTim").val();		//追踪发送位置时间间隔  / 追踪上报频率
	var batLowThres = $("#batLowThres").val();		//电池电量低报警阈值
	var generalTime = $("#generalTime").val();		//定位上报频率
	var saveOrUpdate = $("#forSettingSaveOrUpdate").val();  //用来区分是第一次保存还是修改
	var _data;
	var _checkForm;
	if(generalTime === undefined){   
		//如果是 六个的模板，那么就用下面的  data
		_data = {
				uniqueId : _selectedId,
				typeID : $("#fortypeID").val(),
				houOffset : houOffset,
				startTime : startTime,
				interTime : interTime,
				moveThreshold : moveThreshold,
				floIterTim : floIterTim,
				batLowThres : batLowThres,
				saveOrUpdate : saveOrUpdate	
		};
		//如果是 六个的模板，那么就用下面的  表单校验条件
		_checkForm =    checkHanzi(houOffset, "#houOffset", "时区")&&
						checkHanzi(startTime, "#startTime", "定时唤醒开始时间")&&
						checkHanzi(interTime, "#interTime", "定时唤醒时间间隔")&&
						checkHanzi(moveThreshold, "#moveThreshold", "震动唤醒阈值")&&
						checkHanzi(floIterTim, "#floIterTim", "追踪发送位置时间间隔")&&
						checkHanzi(batLowThres, "#batLowThres", "电池电量低报警阈值");
	}else{
		//如果是 二个的模板，那么就用下面的  data
		_data = {
				uniqueId : _selectedId,
				typeID : $("#fortypeID").val(),	
				floIterTim : floIterTim,
				generalTime : generalTime,
				saveOrUpdate : saveOrUpdate	
		};
		//如果是 二个的模板，那么就用下面的  表单校验条件
		_checkForm = checkHanzi(floIterTim, "#floIterTim", "追踪上报频率")&& 
					 checkHanzi(generalTime, "#generalTime", "定位上报频率")
	}
	if(_checkForm){
		$.ajax({
			url : "../../web/devicesettingsController/settingSaveOrUpdate",
			data : _data,
			type : "POST",
			dataType : "JSON",
			success : function(data){
				alert(data.result);
				if(data.saved == "ok")
					$("#settingIframClose").click();
				else if(data.updated == "ok")
					$("#settingIframClose").click();
				else
					layer.alert("保存失败1");
			},
			error : function(){
				layer.alert("保存失败2");
			}
		});
	}
});

/**
 *【抓擢列表】 按钮 
 */
$("#checkingBtn").click(function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
		if($(".addDeviceFocus").length>0){
			var _selectedId = $(".addDeviceFocus").find("span[class='device_num']").text();   //uniqueId
			$.ajax({
				url : "../../web/devicesettingsController/checkBatch",
				data : {
					uniqueId : _selectedId
				},
				type : "POST",
				dataType : "JSON",
				success : function(data){
					if(data.success){
						$("#checkBatch").modal("show");
						var batchType ;
						for(var i = 0 ; i < data.result.length ; i++){
							if(data.result[i].type == "DeviceSettings"){
								batchType = "设备设置";
							}else if(data.result[i].type == "closeTrackStatus"){
								batchType = "关闭追踪模式";
							}
							$("#checkBatchTbody").append(
									"<tr batchid=\""+ data.result[i].id +"\">" +
									"	<td style=\"text-align: center;\" type=\"uniqueId\">"+ data.result[i].uniqueId +"</td>" +
									"	<td style=\"text-align: center;\" type=\"type\">"+ batchType +"</td>" +
									"	<td style=\"text-align: center;\"><a href=\"javascript:void(0)\" onclick=\"delBatch()\">删除</a></td>" +
									"</tr>"
							);
						}
					}else{
						layer.alert("当前设备无抓擢数据");
					}
				},
				error : function(){
					layer.alert("查询抓擢数据失败！");
				}
			});
		}else{
			layer.alert("请选择要操作的设备");
		}
	}else{
		layer.alert("您没有操作权限");
	}
});
/**
 * 清空抓擢弹出层
 */
function clearTb(){
	$("#checkBatchTbody").empty();
}
/**
 * 删除抓擢列表中的数据
 */
function delBatch(){
	event.stopPropagation();
	var batchId = $(event.target).parent().parent().attr("batchid");
	var uniqueId = $(event.target).parent().siblings("td[type='uniqueId']").text();
	var type = $(event.target).parent().siblings("td[type='type']").text();
	$.ajax({
		url : "../../web/devicesettingsController/delBatch",
		data : {
			uniqueId : uniqueId,
			type : type
		},
		type : "POST",
		dataType : "JSON",
		success : function(data){
			if(data.success){
				$("tr[batchid='"+ batchId +"']").remove();
				//$(event.target).parent().parent().remove();
				layer.alert("删除成功");
			}else{
				layer.alert("删除记录失败");
			}
		},
		error : function(){
			layer.alert("删除记录异常！");
		}
	});
}
/**
 * 设备回控
 */
/*$("#settingBtn").click(function(){
	var permission=checkPermission();
	if(permission=="普通操作员" || permission=="系统操作员"){
//		$(this).attr("href","#setting").click();
		$("#setting").modal("show");
	}else{
		layer.alert("您没有操作权限");
	}
});*/
/**
 * 组内设备搜索功能
 */
$("#searchDevBtn").click(function(){
	var _deviceName = $("#searchDevText").val();
	if(_deviceName!=null && _deviceName!=""){
		selDevByCondition(_deviceName);
	}
});

/**
 * 根据设备号或名称查询设备信息
 * @param _deviceName
 */
function selDevByCondition(_deviceName){
	if(_deviceName!=null && _deviceName!=""){
		$.ajax({
			url : "../../web/devicesController/selDevByIdOrName",
			data : {
				deviceName : _deviceName
			},
			type : "POST",
			dataType : "JSON",
			success : function(retJs){
				var result = retJs.DataResult;
				var data = retJs.message;
				
				//下面两个：分别清除 列表样式和图标样式 里面的设备
				$("#typeListTb").children().remove();
				$("#typePicDiv").children().remove();
				var tx = "";
				var tx1 = "";
				if(result == "pass"){
					for(var i= 0;i<data.length;i++){
					tx +=
							"<tr onclick=\"addDeviceFocus('"+data[i].id+"_td')\" id=\""+data[i].id+"_td\" deviceId=\""+data[i].id+"\">"+
								"<td>"+
									"<a class=\"device_news\">"+
										"<span class=\"device_user\"><img src=\"../../images/td_img1.png\"/></span>"+
										"<span class=\"device_name\">"+data[i].nickName+"</span>"+
										"<span class=\"device_name\">"+data[i].devicesTypeId+"</span>"+
										"<span class=\"device_num\">"+data[i].uniqueId+"</span>"+
									"</a>"+
								"</td>"+
							"</tr>";
					tx1+=
							"<div class=\"device_li\" onclick=\"addDeviceFocus('"+data[i].id+"_div')\" id=\""+data[i].id+"_div\" deviceId=\""+data[i].id+"\">"+
	                           "<span class=\"device_user\">"+
	                           		"<img src=\"../../images/user_img.png\"/>"+
	                           "</span>"+
	                           "<span class=\"device_name\">"+data[i].nickName+"</span>"+
	                           "<span class=\"device_name\">"+data[i].devicesTypeId+"</span>"+
	                           "<span class=\"device_num\">"+data[i].uniqueId+"</span>"+
	                       "</div>";
					}
					$("#typeListTb").append(tx);
					$("#typePicDiv").append(tx1);
				}else{
					layer.alert("未找到符合条件的设备信息");
				}
			},
			error : function(){
				layer.alert("查询失败,请稍后重试");
			}
		});
	}
	
}

/*function addListenerForSpan(){
	$(".groupName").each(function(index,elem){
		elem.addEventListener("click",function(){},false);
	});
}*/













