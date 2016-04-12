/**联系人---添加按钮---功能实现*/
document.getElementById("addLinkman").onclick=function(evt){
	$(evt.target.parentElement).siblings("table").find("tbody").append(
		 "<tr>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
            " <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
         "</tr>");
};
/**联系人---添加按钮---功能实现*/
document.getElementById("addLinkmanI").onclick=function(evt){
	$(evt.target.parentElement.parentElement).siblings("table").find("tbody").append(
			"<tr>"+
			" <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
			" <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
			" <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
			" <td contenteditable=\"true\" style=\"width:142px; height: 30px;\" onfocus=\"addFocus(event)\"></td>"+
	"</tr>");
};
/**联系人---删除按钮---功能实现*/
document.getElementById("removeLinkman").onclick=function(){
	var p = document.getElementById("removeLinkman").parentElement;
	var t = $(p).siblings("table");
	
	if(t.find(".ffocus").length>0){
		t.find(".ffocus").parent().remove();
	}else{
		layer.alert("请选择需要删除的记录行");
	}
};


function addFocus(evt){
	evt.target.setAttribute("class","ffocus");
	//当前td的同辈元素去掉  class： ffcous
	$(evt.target).siblings("td").removeClass("ffocus");
	//其他tr中的td元素 去掉 class：ffcous
	$(evt.target).parent().siblings("tr").each(function(index,_elem){
		if(index!=0){
			$(_elem.children).removeClass("ffocus");
		}
	});
}
//保存按钮(测试 联系人 表格)
document.getElementById("save").onclick=function(){
	if($("#linkManTable").find("tr").length==1)
		layer.alert("尚未添加联系人");
	else{
		var linkMen = [];
		$("#linkManTable").find("tr").each(function(index,elem){
			if(index>0){
				var linkMan = {}; 
				var _th = ["name","tel","phone","description"];
				for(var i = 0 ; i<elem.children.length;i++){
					linkMan[_th[i]] = elem.children[i].innerHTML;
				}
				linkMen.push(linkMan);
			}
		});
		
		$.ajax({
			url : "../../web/devicesController/saveLinkMan",
			data : {linkMen : JSON.stringify(linkMen)},
			type : "post",
			dataType : "json",
			success : function(data){
				for(var i = 0 ; i<data.tableValue.length;i++)
					console.log(data.tableValue[i]);
			},
			error : function(){
				layer.alert("保存 联系人失败");
			}
		});
		
	}
};
