
var commonInfo={};
var requestParam={};
var timePeroidList=[];
var timePeriod={};

function init()
{
	//时间控件初始化
	debugger;
	 $('#datetimepicker').datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
		startDate:new Date(),
        initialDate: new Date(),
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
	requestParam = GetURLRequestParam();
commonInfo = parseWindowName();
	var orderCode =requestParam.orderCode;
	var phoneNumber = requestParam.Contact_P;//联系号码contact_P
	var businessNumber=requestParam.Logic_Num;//业务号码 LOGIN_Num
	$("#orderNumber").replaceWith('<li class="value" id="orderNumber">'+orderCode+'</li>');
	$("#contact_p").replaceWith('<li class="value" id="contact_p">'+phoneNumber+'</li>');
	$("#businessNumber").replaceWith('	<li class="value" id="businessNumber">'+businessNumber+'</li>');
}

function makeTimePeroidOption(timePeroidList)
{
	$("#selectTime").empty();
	for(var i=0;i<timePeroidList.length;i++)
	{
		var object = timePeroidList[i];
		var value = object.BeginTime+','+object.EndTime;
		var opt= $("<option id='"+object.ReasonCode+"' value='"+value+"'>"+object.ReasonName+"</option>");
		$("#selectTime").append(opt);
	}
	changeSelectOption();
}

function changeSelectOption()
{
	debugger;
	timePeriod={};
	var times =   $("#selectTime option:selected").val();
	var timeList = times.split(',');
	var beginTime =  $("#datetimepicker").val()+" "+timeList[0];
	var endTime = $("#datetimepicker").val()+" "+timeList[1];
	var reasonCode=   $("#selectTime option:selected").attr('id');
	timePeriod={
		beginTime:beginTime,
		endTime : endTime,
		reasonCode:reasonCode
	}
}


function queryTimePeriod()
{
	debugger;
	
	//显示遮罩
	timePeroidList=[];
$("#loadingModal").modal('show');
time = $("#datetimepicker").val();
	var param = {
		BokDate:time,
		StaffId :commonInfo.ktdb.staffId
	    
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/workorder/web/timeSeq/query',
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
	 timePeroidList = result.resultData.CANCEL_REASON_LIST;
	 makeTimePeroidOption(timePeroidList);
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			  //复用界面区分是故障工单还是工单详情
			    if(requestParam.forward=='1')
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
			   if(requestParam.forward=='1')
				{
					showMessage('2','数据提交失败');
				}
				else
				{
					showMessage('3','数据提交失败');
				}
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			     //关闭遮罩
$("#loadingModal").modal('hide');
 //提示成功返回到详情界面
	   if(requestParam.forward=='1')
				{
					showMessage('2','数据提交失败');
				}
				else
				{
					showMessage('3','数据提交失败');
				}
        }
		
	});
	
}

function callBack()
{
	window.history.back(-1); 
}

function submitReservationInfo()
{
	debugger;
	//显示遮罩
	$("#loadingModal").modal('show');
	if($("#selectTime").val()=="")
	{
		 //关闭遮罩
$("#loadingModal").modal('hide');
		
		showMessage('1','请选择预约时间');
		  //关闭遮罩
$("#loadingModal").modal('hide');
		return ;
	}
	if($("#inputText").val()=="")
	{
		 //关闭遮罩
$("#loadingModal").modal('hide');
		
		showMessage('1','请填写描述');
		  //关闭遮罩
$("#loadingModal").modal('hide');
		return ;
	}

	var workOrderID  = commonInfo.ktdb.WorkOrderID;
	//var appointBeginDate =  $("#callBack_detail").val();
	var assignReason = "1";//写死1
	var comment = "";
	var desc = $("#inputText").val();//描述
	var handleStaffId = commonInfo.ktdb.staffId;//staffid
	var isSendNotice = "1";//写死1
	var trackStaffId = commonInfo.ktdb.staffId;//staffid
	var orderClass = requestParam.orderClass;//开通工单是10S 故障单是1SA
	var appointBeginDate = timePeriod.beginTime;
	var appointEndDate = timePeriod.endTime;
	var timeSeqId = timePeriod.reasonCode;//查询时间段的reasonCode
	
	
	var param = {
		WorkOrderID:workOrderID,
		AssignReason:assignReason,
	    Comment:comment,
		Desc:desc,
		HandleStaffId:handleStaffId,
		IsSendNotice:isSendNotice,
		TrackStaffId:trackStaffId,
		OrderClass:orderClass,
		AppointBeginDate:appointBeginDate,
		AppointEndDate:appointEndDate,
		TimeSeqId:timeSeqId
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/kt/appointorder/web/submit',
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
	        if(requestParam.forward=='1')
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
			    
		   if(requestParam.forward=='1')
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
			    if(requestParam.forward=='1')
				{
					showMessage('2','数据提交失败');
				}
				else
				{
					showMessage('3','数据提交失败');
				}
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			     //关闭遮罩
$("#loadingModal").modal('hide');
  //提示成功返回到详情界面
	  if(requestParam.forward=='1')
				{
					showMessage('2','数据提交失败');
				}
				else
				{
					showMessage('3','数据提交失败');
				}
        }
		
	});
	

}


