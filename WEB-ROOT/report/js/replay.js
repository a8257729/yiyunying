
var requestParam={};
var formData = new FormData();
var commonInfo={};
var snParam = {};
var flag =true;
//方法初始化
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
		
	}
	if (browser.versions.android) {
		flag=false;
		$("#img").css("display","none");
	}

	

requestParam = GetURLRequestParam();
commonInfo = parseWindowName();
getSNAndCode();

	//buildExecutorNode();

	//展示工单编号
	var orderCode = requestParam.orderCode;
	$("#workOrderId").replaceWith('<li class="value" id="workOrderId">'+orderCode+'</li>');
	
	//初始化执行时间span
	var time = new Date().format("yyyy-MM-dd hh:mm:ss");
	$("#startTime").replaceWith('<li class="value" id="startTime">'+time+'</li>');
	
	//初始化执行人
	$("#selectPeople").replaceWith('<li class="value" id="selectPeople">'+commonInfo.ktdb.STAFF_NAME+'</li>');
}

function callBack()
{
	//页面回退
	url="/MOBILE/report/deviceManager/ktdb_orderList.jsp?staffId="+commonInfo.ktdb.staffId;
	callBackHistory(url);
}


//日期格式化
Date.prototype.format = function (format) {
           var args = {
               "M+": this.getMonth() + 1,
               "d+": this.getDate(),
               "h+": this.getHours(),
               "m+": this.getMinutes(),
               "s+": this.getSeconds(),
               "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
               "S": this.getMilliseconds()
           };
           if (/(y+)/.test(format))
               format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
           for (var i in args) {
               var n = args[i];
               if (new RegExp("(" + i + ")").test(format))
                   format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
           }
           return format;
       };
	   
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
		$("#sn_number").replaceWith('<li class="value" id="sn_number">'+snParam.sn+'</li>');
	$("#code_number").replaceWith('<li class="value" id="code_number">'+snParam.code+'</li>');
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

	   //提交回单数据
function submitReplayInfo()
{
	debugger;
	var time = $("#startTime").html();
	handleResult= $("#textdetail").val();
	var sn = "";
	var code="";
	if(!$.isEmptyObject(snParam))
{
	sn = snParam.sn;
	code = snParam.code;
}
	var param = {
		workOrderID :commonInfo.ktdb.WorkOrderID,
		builderId :commonInfo.ktdb.staffId,//staffid
	    executeTime :time,//实时时间
		handleResult :handleResult,//处理结果
		hgu_sn:sn,//可以不传
		overTimeReasonDesc :"",//可以不传
		overTimeReasonId :"",//可以不传
		stb_mac:code,//可以不传
		trackHelpId :commonInfo.ktdb.staffId//staffid
	
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/kt/web/replyorder/submit',
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
	  alert('提交成功');
	  callBack();
	  
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
        }
		
	});
	
}


//获取提交的图片信息，组装接口参数
function setImageInfo()
{
	debugger;
	if($("#file1")[0].files[0] && $("#file2")[0].files[0] && $("#file3")[0].files[0])
	{
		 var file1 = $("#file1")[0].files[0];
	  var file2 = $("#file2")[0].files[0];
	   var file3 = $("#file3")[0].files[0];
	     formData = new FormData();
	    formData.append("photoFile", file1); 
			formData.append("photoFile", file2); 
			formData.append("photoFile", file3); 
			formData.append("staffId", commonInfo.ktdb.staffId); 
			formData.append("workOrderId", commonInfo.ktdb.WorkOrderID); 
			formData.append("EqpID", ""); //好像可以不用传----郭浩
			formData.append("PortID", ""); //好像可以不用传----郭浩
			formData.append("codeBar", "");//取空 
			var fileNameStr = file1.name+";"+file2.name+";"+file3.name;
		//	$("#selectTime").empty();
			var optionStr =$("<option selected=selected>"+fileNameStr+"</option>");
			$("#selectImage").append(optionStr);
			$("#uploadFile1").modal('hide');
	}
	else
	{
		//输出提示信息，图片需要上传三张
		
	}
		
}


        
function openModel()
{
	$('#uploadFile1').modal('show');
}

function submit()
{
	debugger;
	
	//显示遮罩
$("#loadingModal").modal('show');
	
	
	uploadFile();
	
}



function uploadFile()
{
	debugger;
	 
     
            
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/hn/web/wkorder/upload/photo',
   data: formData,           
   processData: false,       //必不可缺
   contentType: false,       //必不可缺
   success: function (e) {
	   
	   debugger;
	

	 if(e.resultCode=='1000')
	  {
	 //提示成功返回到详情界面
	 submitReplayInfo();
	 
	  }
	  else
	  {
		     //关闭遮罩
$("#loadingModal").modal('hide');
		  showMessage('2','文件上传失败');
		 
	  }
   },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			//关闭遮罩
$("#loadingModal").modal('hide');

			  showMessage('2','文件上传失败');
        }
		
	});
	
	
	
}

function submitData()
{
	if(flag)
	{
		uploadFile();
	}
	else
	{
		submitReplayInfo();
	}
	
}






