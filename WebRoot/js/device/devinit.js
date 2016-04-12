$(function(){
	synchroGroup();
});


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
//						layer.alert(data[i]["groupName"]);
						//查询每一级节点下的设备数量
						var devNum = selDevNum(data[i]["id"]);
						
						
						tx += "<li class=\"active\">"
	                        	+"	<a id="+data[i]["id"]+" onclick=\"leftzhedie()\">"
								+"		<span onblur=\"editOblur()\">"+data[i]["groupName"]+"</span>"
								+"		<span class=\"badge badge-roundless\">222</span>"
								+"		<i onclick=\"jiahao()\" class=\"glyphicon glyphicon-plus\" data-toggle=\"modal\" href=\"#\"></i>"
	                        	+"		<i onclick=\"pencilClick()\" class=\"glyphicon glyphicon-pencil\"></i>"
	                        	+"		<i onclick=\"chahao()\" class=\"glyphicon glyphicon-remove\"></i>"
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

//查询当前目录下的设备数量
function selDevNum(id){
//	layer.alert("46id:"+id);
	$.ajax({
		type : "POST",
		url : "../../web/groupController/selDevNum",
		data : {"groupId" : id},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var data = retJs.message;
			if (result == 'pass') {
				for(var i = 0; i< data.length;i++){
//					layer.alert(data[i]["groupName"]);
					//查询每一级节点下的设备数量
					var devNum = selDevNum(data[i]["id"]);
					
					
					
				}
				$(".menu").append(tx);
			}
		},
		error : function(){
			layer.alert("加载分组名称异常...");
		}
	});
}

