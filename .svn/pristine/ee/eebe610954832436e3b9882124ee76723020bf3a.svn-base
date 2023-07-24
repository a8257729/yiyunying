var requestParam={};
var commonInfo={};
var mark = "";
var snParam = {};
var sn_btFlag=false;
var code_btFlag=false;
function init()
{
	debugger;
	var browser = {
		versions: function () {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息 
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
			iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器 
			iPad: u.indexOf('iPad') > -1, //是否iPad 
			};
		}(),
	}
	if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
		$("#sn_bt").replaceWith('<button onclick="openModel(1)" id="sn_bt">扫码</button>');
	$("#code_bt").replaceWith('<button onclick="openModel(2)" id="code_bt">扫码</button>');
	}
	if (browser.versions.android) {
		$("#sn_bt").replaceWith('<button onclick="openModelAndroid(1)" id="sn_bt">扫码</button>');
	$("#code_bt").replaceWith('<button onclick="openModelAndroid(2)" id="code_bt">扫码</button>');
	}

	
	//从URL中获取参数
	 requestParam = GetURLRequestParam();
	 commonInfo = parseWindowName();
	 getSNAndCode();
	var orderCode =requestParam.orderCode;
	var Logic_Num = requestParam.Logic_Num;
	$("#number1").replaceWith('<li class="value" id="number1">'+orderCode+'</li>');
	$("#number2").replaceWith('<li class="value" id="number2">'+Logic_Num+'</li>');
	
	
	
}

	   function getSNAndCode()
	   {
		  var param = {
		workOrderId :commonInfo.ktdb.WorkOrderID
		
	
	}
		   $.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/kt/terminal/query',
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
	 // showMessage('0','提交成功');
	 if(!$.isEmptyObject(result.resultData.data_info))
	 {
		snParam = {
			sn:result.resultData.data_info.hgn_sn,
			code:result.resultData.data_info.stb_mac
		}

	$("#input_code").replaceWith('<input id="input_code" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+snParam.code+'"/>');
	$("#input_SN").replaceWith('<input id="input_SN" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+snParam.sn+'"/>');
	  }
	
	  }
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			   //关闭遮罩
$("#loadingModal").modal('hide');
        }
		
	});
		   
		
	   }
	   
	   function openModelAndroid(str)
	   {
		   
			if(str==1)
			{
				sn_btFlag=true;
			}
			
            android.doScan();
	   }
	   
	   function setScanResult(result){
          
			if(sn_btFlag==true)
			{
				  $("#input_SN").replaceWith('<input id="input_SN" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+result+'"/>');
				  sn_btFlag=false;
			}
			else
			{
				 $("#input_code").replaceWith('<input id="input_code" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+result+'"/>');
				
			}
        };


//获取IOS条形码
function startGetCode()
{
	
	var parameter = {'title':'JS调OC','describe':'这里就是JS传给OC的参数'};
	 var code = window.iosDelegate.startGetCode(JSON.stringify(parameter));
	 if(mark!='1')
	 {
		 $("#input_code").replaceWith('<input id="input_code" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+code+'"/>');
	 }
	 else
	 {
		  $("#input_SN").replaceWith('<input id="input_SN" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'+code+'"/>');
	 }
 
	
}


function openModel(str)
{
	debugger;
	mark=str;
	
	var parameter = {'title':'JS调OC','describe':'这里就是JS传给OC的参数'};
	//调取IOS扫码功能
	window.iosDelegate.getImage(JSON.stringify(parameter));
	//startGetCode();
	
}



function submitTerminalInfo()
{
	debugger;
	debugger;
	//显示遮罩
$("#loadingModal").modal('show');

	var orderCode =requestParam.orderCode;
	var Logic_Num = requestParam.Logic_Num;
	var commonInfo = parseWindowName();
	
	var hgu_sn  = $("#input_SN").val();
	var staff_id = commonInfo.ktdb.staffId;
	var staff_name = commonInfo.ktdb.STAFF_NAME;
	var stb_mac = $("#input_code").val();
	var wkOrder_id = commonInfo.ktdb.WorkOrderID;
	
	

	
	
	var param = {
		hgu_sn:hgu_sn,
		staff_id:staff_id,
	    staff_name:staff_name,
		stb_mac:stb_mac,
		wk_order_id:wkOrder_id
		
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/kt/terminal/submit',
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
		  if(result.resultData.makeDataFlag=='1')
		  {
			   //提示成功返回到详情界面
	  showMessage('0',result.resultData.flag_desc);
		  }
		  else
		  {
			  
			   showMessage('1',"终端扫码有误,请确认设备规格!("+result.resultData.flag_desc+")");
		  }
		
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		  showMessage('2','数据提交失败');
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
		  showMessage('2','数据提交失败');
        }
		
	});
	

}


