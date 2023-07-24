
var commonInfo= {};
var requestParam={};
function init()
{
	requestParam={};
	 commonInfo = parseWindowName();
	 requestParam = GetURLRequestParam();
	 if(!$.isEmptyObject(requestParam))
	 {
		 var port_name =  decodeURI(requestParam.port_name);
		 var eqp_name = decodeURI(requestParam.eqp_name);
		 var pon_name = decodeURI(requestParam.pon_name);
		 
		 $("#select1").empty();
		 var selectNode1 = $("<option id='select_option1'>"+eqp_name+"</option>")
		  $("#select1").append(selectNode1);
		   $("#select2").empty();
		 var selectNode2 = $("<option id='select_option2'>"+port_name+"</option>")
		  $("#select2").append(selectNode2);
		   $("#select3").empty();
		 var selectNode3 = $("<option id='select_option3'>"+pon_name+"</option>")
		  $("#select3").append(selectNode3);
	 }
	  else
	 {
		 initDataQuery();
		
	 }
	 makeProcessDataQuery();
}

function chooseEqument()
{
	window.location.href ="/MOBILE/report/zy/deviceManager.jsp?page=active&staffId="+commonInfo.ktdb.staffId;
}

function initDataQuery()
{
	//显示遮罩
	$("#loadingModal").modal('show');
	var param = {
	
		//wk_order_id:commonInfo.ktdb.WorkOrderID
		workOrderId:commonInfo.ktdb.WorkOrderID
		
		
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/kt/makedata/query',
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
		  if(!$.isEmptyObject(result.resultData.data_info))
		  {
			
				  var port_name =  result.resultData.data_info.portName;
			 var eqp_name = result.resultData.data_info.eqpName;
			 var pon_name = result.resultData.data_info.ponName;
			 requestParam ={
				 eqp_id:result.resultData.data_info.eqpId,
				 port_id:result.resultData.data_info.portId,
				 pon_id:result.resultData.data_info.ponId
				 
			 }
			  if(port_name=="" || $.isEmptyObject(port_name))
			 {
				 return;
			 }
			  $("#select1").empty();
			 var selectNode1 = $("<option id='select_option1'>"+eqp_name+"</option>")
			  $("#select1").append(selectNode1);
			   $("#select2").empty();
			 var selectNode2 = $("<option id='select_option2'>"+port_name+"</option>")
			  $("#select2").append(selectNode2);
			   $("#select3").empty();
			 var selectNode3 = $("<option id='select_option3'>"+pon_name+"</option>")
			  $("#select3").append(selectNode3);
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



//制作数据查询
function makeProcessDataQuery()
{
	//显示遮罩
	$("#loadingModal").modal('show');
	var param = {
	
		//wk_order_id:commonInfo.ktdb.WorkOrderID
		workOrderId:commonInfo.ktdb.WorkOrderID
		
		
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/kt/makedata/query',
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
		  $("#detailContent").empty();
		 //制作数据查询显示
		 var time =result.resultData.progress_info.startDate;
		 var progress1 = result.resultData.progress_info.progress1;
		 var progress2 =result.resultData.progress_info.progress2;
		 var progress3 = result.resultData.progress_info.progress3;
		 var progress4 = result.resultData.progress_info.progress4;
		 var progress5 = result.resultData.progress_info.progress5;
	     var progress6 = result.resultData.progress_info.progress6;
		 var progress7 = result.resultData.progress_info.progress7;
		 var progress8 = result.resultData.progress_info.progress8;
		 var progress9 = result.resultData.progress_info.progress9;
	     var progress10 = result.resultData.progress_info.progress10;
		  var endTime = result.resultData.progress_info.endDate;
		 
		  if($.trim(time)!="")
		 {
			 var node = $('<div id="detailArea2"><span>数据制作开始时间:'+time+'</span></div>')
			 $("#detailContent").append(node)
		 }
		 if($.trim(progress1)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress1+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress2)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress2+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress3)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress3+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress4)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress4+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress5)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress5+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress6)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress6+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress7)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress7+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress8)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress8+'</span></div>')
			 $("#detailContent").append(node)
		 }
		  if($.trim(progress9)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress9+'</span></div>')
			 $("#detailContent").append(node)
		 }
		   if($.trim(progress10)!="")
		 {
			 var node = $('<div id="detailArea2"><span>'+progress10+'</span></div>')
			 $("#detailContent").append(node)
		 }
		 if($.trim(endTime)!="")
		 {
			 var node = $('<div id="detailArea2"><span>数据制作结束时间:'+endTime+'</span></div>')
			 $("#detailContent").append(node)
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
        }
		
	});
	
}

//制作数据
function makeProcessData()
{
	//显示遮罩
	$("#loadingModal").modal('show');
	debugger;
	//没有选择设备部执行制作数据
	if($.isEmptyObject(requestParam))
	 {
		 	    //关闭遮罩
$("#loadingModal").modal('hide');
		return;
		
	 }
	
	var hgu_sn  = "";//不传
	
	var stb_mac = "";//不传
	var eqp_id = requestParam.eqp_id;
	var port_id = requestParam.port_id;
	var wk_order_id = commonInfo.ktdb.WorkOrderID;
	var pon_id = requestParam.pon_id;
	var staff_id = commonInfo.ktdb.staffId;
	var staff_name = commonInfo.ktdb.STAFF_NAME;

	
	
	var param = {
		hgu_sn:hgu_sn,
		stb_mac:stb_mac,
	    eqp_id:eqp_id,
		port_id:port_id,
		wk_order_id:wk_order_id,
		 pon_id:pon_id,
		staff_id:staff_id,
		staff_name:staff_name,
		
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/kt/addmakedata/submit',
  data:JSON.stringify(param),
  dataType: "json",
  contentType:"application/json",
  success: function (result){
	  debugger;
	  var resultCode =  result.resultCode;
	  if(resultCode=='1000' && result)
	  {
		 //制作数据查询 
	 makeProcessDataQuery();
	  }
	  else
	  {
		      //关闭遮罩
$("#loadingModal").modal('hide');
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


