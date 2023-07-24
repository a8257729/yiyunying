
var urlParam ={}
function signedInit()
{
	//显示遮罩
$("#loadingModal").modal('show');

	debugger;
	 urlParam =   GetURLRequestParam();
var latitude ="";
var longitude = "";
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(postition_data){
		debugger;
if(this.getStatus() == BMAP_STATUS_SUCCESS){
//调取方位函数的回调方法获取经度纬度
			latitude=postition_data.point.lat;
			longitude=postition_data.point.lng;
		
	var ul_longitude_name = $("<li>经度</li>");
	var ul_longitude_value = $("<li id=longitude>"+longitude+"</li>");
	var ul_latitude_name = $("<li>纬度</li>");
	var ul_latitude_value = $("<li id=latitude>"+latitude+"</li>");
			//经度节点清空
	$("#ul_longitude").empty();
	//经度节点生成
	$("#ul_longitude").append(ul_longitude_name);
	$("#ul_longitude").append(ul_longitude_value);
	//纬度清空
	$("#ul_latitude").empty();
	//纬度节点生成
	$("#ul_latitude").append(ul_latitude_name);
	$("#ul_latitude").append(ul_latitude_value);
	//关闭遮罩
$("#loadingModal").modal('hide');

}
else {
	//关闭遮罩
$("#loadingModal").modal('hide');
	
	  if(urlParam.forward=='1')
				{
					showMessage('2','网络原因获取经纬度失败');
				}
				else
				{
					showMessage('3','网络原因获取经纬度失败');
				}
      
} 
});
	
	
	
	
	
	var ul_longitude_name = $("<li>经度</li>");
	var ul_longitude_value = $("<li id=longitude>"+longitude+"</li>");
	var ul_latitude_name = $("<li>纬度</li>");
	var ul_latitude_value = $("<li id=latitude>"+latitude+"</li>");
	//经度节点清空
	$("#ul_longitude").empty();
	//经度节点生成
	$("#ul_longitude").append(ul_longitude_name);
	$("#ul_longitude").append(ul_longitude_value);
	//纬度清空
	$("#ul_latitude").empty();
	//纬度节点生成
	$("#ul_latitude").append(ul_latitude_name);
	$("#ul_latitude").append(ul_latitude_value);
	
}


function submitLocationPosition()
{
	debugger;
	//显示遮罩
$("#loadingModal").modal('show');
		var requestParam =   parseWindowName();
		
	
	var checkCode = $("#input_code").val();
	if(checkCode=="" || checkCode== null)
	{
		//输出验证码不能为空
		
		 showMessage('1','验证码不能为空');
		 	//关闭遮罩
$("#loadingModal").modal('hide');
		return;
	}
	var latitude = $("#latitude").text();
	var longitude =  $("#longitude").text();
	var param = {
		checkCode:checkCode,
		latitude:latitude,
		longitude:longitude,
		orgId :requestParam.ktdb.ORG_ID,
		orgName:requestParam.ktdb.ORG_NAME,
		staffId:requestParam.ktdb.staffId,
		staffName:requestParam.ktdb.STAFF_NAME,
		//username:"",
		workOrderId:requestParam.ktdb.WorkOrderID
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/kt/signorder/submit/position',
  data:JSON.stringify(param),
  dataType: "json",
  contentType:"application/json;charset=utf-8",
  success: function (result){
	  debugger;
	  	//关闭遮罩
$("#loadingModal").modal('hide');
	  var resultCode =  result.resultCode;
	  if(resultCode=='1000' && result)
	  {
	 //提示成功返回到详情界面
	       if(urlParam.forward=='1')
				{
					showMessage('0','数据提交成功');
				}
				else
				{
					showMessage('4','数据提交成功');
				}
		  
	
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		      if(urlParam.forward=='1')
				{
					showMessage('2','数据提交失败');
				}
				else
				{
					showMessage('3','数据提交失败');
				}
		  }
		  else
		  {
			  
			   if(urlParam.forward=='1')
				{
					showMessage('2',result.resultMsg);
				}
				else
				{
					showMessage('3',result.resultMsg);
				}
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
				//关闭遮罩
$("#loadingModal").modal('hide');
			 if(urlParam.forward=='1')
				{
					showMessage('2','系统内部错误');
				}
				else
				{
					showMessage('3','系统内部错误');
				}
        }
		
	});
	

}

function backHistoryPage()
{
	window.history.back(-1); 
}


function showPosition(postition_data)
	{
		debugger;
		
		//调取方位函数的回调方法获取经度纬度
			latitude=postition_data.point.lat;
			longitude=postition_data.point.lng;
			
			//经度节点清空
	$("#ul_longitude").empty();
	//经度节点生成
	$("#ul_longitude").append(ul_longitude_name);
	$("#ul_longitude").append(ul_longitude_value);
	//纬度清空
	$("#ul_latitude").empty();
	//纬度节点生成
	$("#ul_latitude").append(ul_latitude_name);
	$("#ul_latitude").append(ul_latitude_value);
	
		
		
	}
	
	
	function showError(error){
		debugger;
switch(error.code) {
case error.PERMISSION_DENIED:
alert("定位失败,用户拒绝请求地理定位");
break;
case error.POSITION_UNAVAILABLE:
alert("定位失败,位置信息是不可用");
break;
case error.TIMEOUT:
alert("定位失败,请求获取用户位置超时");
break;
case error.UNKNOWN_ERROR:
alert("定位失败,定位系统失效");
break;
}
}
