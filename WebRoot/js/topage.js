function toPage(url){
	
	var flag = checkCompanyInfo();
	
	if(flag){
		window.location.href=url;
	}else{
		layer.alert("当前没有集团账号登录,请先登录集团账号...");
	}
}