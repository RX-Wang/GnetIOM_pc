/**
 * 定义 map
 */
var Map = function(){
	var result = {};
	this.put = function(key,value){
		if(key===null || key === "" || key === undefined){
			console.log("key is necessary!");
			return;
		}else if(result[key] !== undefined){
			console.log("key is must only");
			return;
		}else{
			result[key] = value;
			return;
		}
	};
	this.get = function(key){
		if(key===null || key === "" || key === undefined){
			console.log("key is necessary!");
			return;
		}else if(result[key] === undefined){
			alert("Map中没有以“" + key + "”为Key的值！");
			return;
		}else{
			return result[key];
		}
	};
	this.size = function(){
		var _num = 0;
		for(var _elem in result){
			_num+=1 ;
		}
		return _num;
	}
};