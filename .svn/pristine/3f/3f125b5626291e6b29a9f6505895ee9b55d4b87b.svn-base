




function callBackHistory(url)
{
	debugger;
	if(url!="")
	{
		//回退到具体页面
		window.location.href =url;
	}
	else
	{
		 //页面回退一级
		window.history.back(-1); 
	}
	
	  
	
}

function showMessage(retCode,message)
{
	debugger;
	//等于1表示不需要跳转的报错提示
	if(retCode!='0')
	{
			$('#validate_img').replaceWith(" <img src='image/yjzd_icon3.png' id='validate_img'/>");
		$('#validate_message').empty();
		var node = $('<label id="model-message">'+message+'</label>');
		$('#validate_message').append(node);
		if(retCode=='1')
		{
			$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal">确定</button>');
		}
		else if(retCode=='2')//跳转工单详情界面
		{
			$('#validate_img').replaceWith(" <img src='image/msgbox_true.png' id='validate_img'/>");
			$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("/MOBILE/report/workfloworder/detailInfo.html")>确定</button>');
		}
		else if(retCode=='4')//成功跳转故障工单详情
		{
					$('#validate_img').replaceWith(" <img src='image/msgbox_true.png' id='validate_img'/>");
		$('#validate_message').empty();
		var node = $('<label id="model-message">'+message+'</label>');
		$('#validate_message').append(node);
		$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("/MOBILE/report/workfloworder/faultdetail.html")>确定</button>');
		$('#validate').modal('show');
		}
		else
		{
			$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("/MOBILE/report/workfloworder/faultdetail.html")>确定</button>');
		}
		$('#validate').modal('show');
	}
	else
	{
				$('#validate_img').replaceWith(" <img src='image/msgbox_true.png' id='validate_img'/>");
		$('#validate_message').empty();
		var node = $('<label id="model-message">'+message+'</label>');
		$('#validate_message').append(node);
		$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("/MOBILE/report/workfloworder/detailInfo.html")>确定</button>');
		$('#validate').modal('show');
	}
	
}


function showReplyMessage(retCode,message,staffId)
{
	debugger;
	//等于1表示不需要跳转的报错提示
	
		$('#validate_img').replaceWith(" <img src='image/msgbox_true.png' id='validate_img'/>");
		$('#validate_message').empty();
		var node = $('<label id="model-message">'+message+'</label>');
		$('#validate_message').append(node);
		if(retCode==1)
		{
			var url = "/MOBILE/report/deviceManager/ktdb_orderList.jsp?staffId="+staffId;
				$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("'+url+'")>确定</button>');
		}
		else
		{
			var url = "/MOBILE/report/bz/faultWorkOrder.jsp?staffId="+staffId;
				$('#validate_button').replaceWith('<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("'+url+'")>确定</button>');
		}
	
		$('#validate').modal('show');
	
	
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

function GetURLRequestParam() {  
   var url = decodeURI(window.location.href); //获取url中"?"符后的字串  
   var theRequest = new Object();  
   if (url.indexOf("?") != -1) {  
      var str = url.split("?")[1];  
      strs = str.split("&");  
      for(var i = 0; i < strs.length; i ++) {  
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
      }  
   }  
   return theRequest;  
}
