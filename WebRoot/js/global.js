$(".btn_lock").click(function(){
	
	$.ajax({
		async : false,
		type : "POST",
		url : "../../web/sysController/lockSys",
		data : {},
		dataType : "json",
		success : function(retJs) {
			var result = retJs.DataResult;
			var message = retJs.message;
			if (result == 'pass') {
				LockScreen(true,"",600,322,"../../web/pageController/lockPage");
//				layer.alert(message);
////			window.location.href = "../../web/mapController/toMap"
//				window.location.href = "../../web/pageController/toIndex"
			}else{
				layer.alert(message);
			}
		}});
	
});
//锁屏中的  【确定】按钮
var openLock = function(_this){
	if(checkHanzi($("#lockCode").val(), "#lockCode", "账号")&&
	   checkHanzi($("#lockPwd").val(), "#lockPwd", "密码")){
		$
		.ajax({
			async : false,
			type : "POST",
			url : "../../web/userController/validateLoginUser",
			data : {
				"userName" : $("#lockCode").val(),
				"userPwd" : $("#lockPwd").val(),
				"type" : 1
			},
			dataType : "json",
			success : function(retJs) {
				var result = retJs.DataResult;
				var message = retJs.message;
				if (result == 'pass') {
					LockScreen(false);
//					layer.alert(message);
////				window.location.href = "../../web/mapController/toMap"
//					window.location.href = "../../web/pageController/toIndex"
				}else{
					layer.alert(message);
				}
			}});
	}
};
//锁屏中的  【取消】按钮
var cleanLock = function(){
	$("#lockCode").val("");
	$("#lockPwd").val("");
};
function LockScreen(tag,title,width,height,url) {  
	//锁屏 
	if (tag){  
		var lockdiv = document.getElementById("lockscreen");    
		if (lockdiv!=null){      
			lockdiv.style.display = "block";       
			var subdiv = document.getElementById("subdialog");       
			if (subdiv!=null){         
				subdiv.style.display = "block";         
				document.getElementById("dialog1").src = url;       
			}
		}else{       
				//创建新的锁屏DIV,并执行锁屏      
				var tabframe= document.createElement("div");      
				tabframe.id = "lockscreen";       
				tabframe.name = "lockscreen";       
				tabframe.style.top = '0px';       
				tabframe.style.left = '0px';       
				tabframe.style.height = '100%';       
				tabframe.style.width = '100%';       
				tabframe.style.position = "absolute";       
				tabframe.style.opacity="0.5";
				tabframe.style.backgroundColor="#000000";
				tabframe.style.zIndex = "99998";       
				document.body.appendChild(tabframe);       
				tabframe.style.display = "block";       
				//子DIV       
				var subdiv = document.createElement("div");       
				subdiv.id = "subdialog";       
				subdiv.name = "subdialog";       
				subdiv.style.top = (window.screen.availHeight-height)/2 + "px";       
				subdiv.style.left = (window.screen.availWidth-width)/2 + "px";       
				subdiv.style.height = height+'px';       
				subdiv.style.width = width+'px';       
				subdiv.style.position = "absolute";      
				subdiv.style.backgroundColor="#000000";        
				subdiv.style.zIndex = "99999";    
				subdiv.style.filter = "Alpha(opacity=100)";  
				subdiv.style.border = "1px";     
				document.body.appendChild(subdiv);   
				subdiv.style.display = "block";     
				var iframe_height = height;    
				var titlewidth = width;     
				var html = "";
				html += "<tr>" +
							"<td></td>" +
								"<td style='height:100px;'>" +
									"<iframe id='dialog1' frameborder=0 style='width:"+titlewidth+"px;height:" + iframe_height + "px' src='"+url+"'></iframe>" +
								"</td>" +
							"<td></td>" +
						"</tr>";   
				html += "</table>";    
				subdiv.innerHTML = html;   
			}
		//阻止F5或者鼠标右键刷新,使锁屏失效。 
		document.onkeydown = function(){  
			if(event.keyCode==116) {  
				event.keyCode=0; 
				event.returnValue = false;  
			} 
		} 
		document.oncontextmenu = function() {
			event.returnValue = false;
		}
	}else{   
		//解屏    
		var lockdiv = $(parent.window.frames["dialog1"]).parent().siblings("#lockscreen");  
		if (lockdiv!=null){    
			lockdiv.css("display","none");
		}   
		var subdiv = $(parent.window.frames["dialog1"]).parent();
		if (subdiv!=null){     
			subdiv.css("display","none");
		}
		//去除阻止F5或者鼠标右键刷新,使锁屏失效。 
		$(parent.window.frames["dialog1"]).parent().parent().parent().context.documentElement.onkeydown = function(){  
			if(event.keyCode==116) {  
				event.keyCode=116; 
				event.returnValue = true;  
			} 
		} 
		$(parent.window.frames["dialog1"]).parent().parent().parent().context.documentElement.oncontextmenu = function() {
			event.returnValue = true;
		}
	}
} 
//锁屏页面加载时 去掉 F5 和鼠标右键功能
function killF5(){
	//阻止F5或者鼠标右键刷新,使锁屏失效。 
	document.onkeydown = function(){  
		if(event.keyCode==116) {  
			event.keyCode=0; 
			event.returnValue = false;  
		} 
	} 
	document.oncontextmenu = function() {
		event.returnValue = false;
	}
}


/**
 * 【设备】页面中的  【设置】按钮，对应的所有选项的中文字
 */
var _chineseArray = {
	"时区" : "houOffset"	,
	"定时唤醒开始时间" : "startTime"	,
	"定时唤醒时间间隔" : "interTime"	,
	"震动唤醒阈值" : "moveThreshold"	,
	"追踪发送位置时间间隔" : "floIterTim"	,
	"电池电量低报警阈值" : "batLowThres"	,
	"追踪上报频率" : "floIterTim"	,
	"定位上报频率" : "generalTime"	
};












