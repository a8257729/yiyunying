var requestParam={};
var commonInfo={};
var paramInfo={};
function init()
{
	debugger;
	//从URL中获取参数
	 requestParam = GetURLRequestParam();
//	var orderCode =requestParam.orderCode;
//	var Logic_Num = requestParam.Logic_Num;
	
	
	
	
}

function  paramQuery(){
			var staffId=requestParam.staffId;
			var paramId = {	
				staff_id:staffId.toString(),
			}
			
			$.ajax({
				url:"/MOBILE/api/client/xj/kt/private/params",
				type:"post",
				data:JSON.stringify(paramId),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					console.log("参数查询成功！");
					paramInfo = data.resultData;
				
				error:function(e){
					console.log("参数查询失败");
				}
			});
			
		}







function submitChangePassword()
{
	debugger;
	debugger;
	//显示遮罩
	var oldPWord =   $("#oldpassword").val();
	var pWord1 =   $("#newpassword1").val();
	var pWord2 =   $("#newpassword2").val();
	if(pWord1!=pWord2)
	{
		
		  showMessage('1','两次输入的密码不一致，请确认一致后再提交！');
		return;
	}
	
$("#loadingModal").modal('show');


	
	
	var param = {
		oldpassword:oldPWord,
		newpassword:pWord1,
	    username:paramInfo.USERNAME
	
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/user/changepassword',
  data:JSON.stringify(param),
  dataType: "json",
  contentType:"application/json",
  success: function (result){
	  debugger;
	  	     //关闭遮罩
$("#loadingModal").modal('hide');
	  var resultCode =  result.resultCode;
	  if(resultCode=='1000' && result)
	  {
		 //提示成功返回到详情界面
	  showMessage('0','密码修改成功');
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		  showMessage('2','密码修改失败');
		  }
		  else
		  {
			   showMessage('2',result.resultMsg);
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
				     //关闭遮罩
$("#loadingModal").modal('hide');
  //提示失败原因返回到详情
		  showMessage('2','密码修改失败');
        }
		
	});
	

}


