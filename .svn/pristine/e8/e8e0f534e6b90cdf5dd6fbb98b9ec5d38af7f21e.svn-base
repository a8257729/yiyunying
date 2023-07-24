


function GetURLRequestParam() {  
   var url = location.search; //获取url中"?"符后的字串  
   var theRequest = new Object();  
   if (url.indexOf("?") != -1) {  
      var str = url.substr(1);  
      strs = str.split("&");  
      for(var i = 0; i < strs.length; i ++) {  
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
      }  
   }  
   return theRequest;  
}

function callBackHistory()
{
	//页面回退
	window.history.back(-1); 
}

function showErrorMessage(message)
{
	
}

function showSuccessMessage(message)
{
	
}

function parseWindowName(){
	debugger;
	var map_key={};
	
	var paramString = window.name;
	var strs  = paramString. split("&");
	for(var i=0;i<strs.length;i++){
		var result=[];
		var obj = {};
		var paramStrs = strs[i].split(":");
		
		var params = paramStrs[1].split(",");
	 params.forEach(function(item){
    
    var key = item.split("=")[0];
    var value = item.split("=")[1];
      obj[key] = value;
  });

	
  
	map_key[paramStrs[0]]=obj;
}
	
	return map_key;
	
}

function parseUrl(url){
  var result = [];
  var query = url.split("?")[1];
  var queryArr = query.split("&");
  queryArr.forEach(function(item){
    var obj = {};
    var value = item.split("=")[0];
    var key = item.split("=")[1];
    obj[key] = value;
    result.push(obj);
  });
  return result;
}
