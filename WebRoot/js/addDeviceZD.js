$(function(){
	synchroGroup();
	synDevicesType();
	//在这里给设备修改页面 进行数据回填
    if($("#IMModifyPage").length>0){
    	//......
    	$.ajax({
    		type : "POST",
    		url : "../../web/devicesController/checkDeviceInfo",
    		data : { 
    			"id" : $("#forDeviceId").val()
    		},
    		dataType : "",
    		success : function(retJs){
    			retJs = JSON.parse(retJs);
    			var result = retJs.DataResult;
    			var data = retJs.message[0];
    			if(result == 'pass'){
    				
    				$("#dev_userId").val(data["id"]);
    				$("#nickName").val(data["nickName"]);
    				//保存原来的名称
    				$("#oldNickName").val(data["nickName"]);
    				$("#userPhone").val(data["userPhone"]);
    				$("#userType").val(data["userType"]).attr("selected",true);
    				
    				//根据分组id查询分组名称
    				var groupName = selGroupNameById(data["groupId"]);
    				$("#groupId").val(groupName);
    				
    				$("#gId").val(data["groupId"]);
    				$("#userAddress").val(data["userAddress"]);
    				$("#description").val(data["description"]);
    				$("#uniqueId").val(data["uniqueId"]);
    				$("#securityCode").val(data["devicesSecCode"]);
    				$("#simNumber").val(data["simNumber"]);
    				$("#deviceType").val(data["devicesTypeId"]).attr("selected",true);
    				$("#linkmen").val(data["linkman"]);
    				debugger
    				jsonLinkmen(data["linkman"]);
    			}
    		}
    	});
//    	layer.alert("这里用来给页面回填数据");
    }
});

//拼联系人
function jsonLinkmen(linkmenJson){
	var tx = "";
	var data = JSON.parse(linkmenJson);
	for(var i=0;i<data.length;i++){
		var name = data[i]["name"];
		var tel = data[i]["tel"];
		var phone = data[i]["phone"];
		var description = data[i]["description"];
		tx += "<tr>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\">"+name+"</td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\">"+tel+"</td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\">"+phone+"</td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\">"+description+"</td>"+
         "</tr>";
	}
	$("#linkManTable").append(tx);
}

//根据id查询分组名称
function selGroupNameById(id){
	var res = "";
	$.ajax({
		async: false,
		type : "POST",
		url : "../../web/groupController/selGroupNameById",
		data : { 
			"id" : id
		},
		dataType : "",
		success : function(retJs){
			retJs = JSON.parse(retJs);
			var result = retJs.DataResult;
			var data = retJs.message;
			
			if(result == 'pass'){
				res = data;
			}
		},
		error : function(){
			layer.alert("查询分组名称失败");
		}
	});
//	alert("67result:"+res);
	return res;
}



/**
 * 加载设备类型
 */
function synDevicesType(){
	$("#deviceType").empty();
	  var tx ="";
	  $.ajax({
			type : "POST",
			async : false,
			url : "../../web/devicesController/synchroDevicesType",
			data : {},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var data = retJs.message;
				if (result == 'pass') {
//					alert("类型:"+data);
//					var type = JSON.parse(data);
					for(var i = 0; i < data.length; i++){
//						alert(data[i]["type_id"]);
						tx += "<option value=\""+data[i]["type_id"]+"\">"+data[i]["type_id"]+"</option>";
					}
					$("#deviceType").append(tx);
				}
			},
			error : function(){
				layer.alert("加载数据出错...");
			}
		});
	
}

//查询第一级分组名称
var synchroGroup = function(){
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
						tx += "<li class=\"active\" style=\"background-color: white;border-bottom:0px\">"
	                        	+"	<a id="+data[i]["id"]+" onclick=\"leftzhedie()\" style=\"background-color: white; border-left: 1px solid #000; border-right: 1px solid #000;border-bottom: 1px solid #000;\">"
								+"		<span onblur=\"editOblur()\" title=\""+ data[i]["groupName"] +"\" style=\"color: black\">"+(data[i]["groupName"].length>4?data[i]["groupName"].substring(0,4)+"...":data[i]["groupName"])+"</span>"
/*								+"		<span class=\"badge badge-roundless\">222</span>"
								+"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"
	                        	+"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"
	                        	+"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"
*/	                        	+"</a>"
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
		success : function(data){debugger
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
					_newUl.append("<li style=\"background-color: white;border-bottom: 0px;\">"+
									"   <a id=\""+ data.message[i].id +"\" onclick=\"leftzhedie()\" style=\"background-color: white; border-left: 1px solid #000; border-right: 1px solid #000;border-bottom: 1px solid #000;\">"+
									"		<span onblur=\"editOblur()\" title=\""+ data.message[i].groupName +"\" style=\"color: black\">"+(data.message[i].groupName.length>4?data.message[i].groupName.substring(0,4)+"...":data.message[i].groupName)+"</span>"+
									/*"		<span class=\"badge badge-roundless\">222</span>"+
									"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"+
									"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"+
									"		<i onclick=\"chahao(event)\" class=\"glyphicon glyphicon-remove\"></i>"+*/
									"	</a>"+
								"</li>");
				}
				_newUl.slideDown("fast");
			}else{
//				layer.alert(data.message);
			}
		},
		error : function(){
			layer.alert("查询子节点异常");
		}
	});
}

$("li a").click(leftzhedie);
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
        	clickedA.parent().parent().find("ul").slideUp("fast");//同级的其他li标签里面的所有ul收起来。
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
    }else{
    	clickedA.addClass("forChild");
    	selChildGroup(clickedA.attr("id"));
    }
    //给被选中的组加上选中状态
    $("ul li a").css({
    	 	"border-left": "1px solid #000",
		    "border-right": "1px solid #000",
		    "border-bottom": "1px solid #000"
    });
    clickedA.css({
    	"border-left": "1px solid red",
	    "border-right": "1px solid red",
	    "border-bottom": "1px solid red"
    });
    
    //把选中的组的名字填充到 【所属分组中】，组的ID也加进去。
    if($("#IMAddPage")){
    	$("#groupId").val(clickedA.find("span").attr("title"));
//    	$("#groupId").attr("groupId",event.target.id);
    	$("#gId").attr("value",event.target.id);
    }
};

