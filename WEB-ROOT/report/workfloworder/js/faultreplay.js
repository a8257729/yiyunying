
var commonInfo={};
var requestParam={};
var checkBoxParam={};
var defaultData = null;

function init()
{
	//显示遮罩
	

$("#loadingModal").modal('show');
	//时间控件初始化
	debugger;
	 $(' .datetimepicker').datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: 0,//设置只显示到月份
        initialDate: new Date(),
		startDate: new Date(),
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
		 minuteStep:5,
        locale: moment.locale('zh-cn')
    });
	$("#startTime").datetimepicker("setDate", new Date());
	$("#endTime").datetimepicker("setDate", new Date());
	//是否上门和自动回访初始化
	checkBoxParam ={
		isVisit:"1",
		autoReturnVisit:"1"
	};
		requestParam = GetURLRequestParam();
    commonInfo = parseWindowName();
	//初始化故障失败原因
	refreshFaultReason()

	var staffName =commonInfo.ktdb.STAFF_NAME;
	var workOrderCode =commonInfo.ktdb.WorkOrderCode;
	var phoneNumber =requestParam.phoneNumber;
	$('#workCode').replaceWith('<li class="value" style="margin-top:15px;border:1px solid transparent;" id="workCode">'+workOrderCode+'</li>');
	$('#staff_name').replaceWith('<span id="staff_name">'+staffName+'</span>');
	$('#repairPerson').replaceWith('<span id="repairPerson">'+staffName+'</span>');
	$('#phoneNumber').replaceWith('<span id="phoneNumber">'+phoneNumber+'</span>');

}

function openReasonModal()
{
	debugger;
	$('#treeview1').treeview({ 
    data: defaultData
  
}).treeview('collapseAll', {// 节点展开
    silent : true
});
$('#treeview1').on('nodeSelected', function(event, data) {
	debugger;
	if(data.nodes!=null){                               
        var select_node = $('#treeview1').treeview('getSelected');
        if(select_node[0].state.expanded){
            $('#treeview1').treeview('collapseNode',select_node);
            select_node[0].state.selected=false;
        }
        else{
            $('#treeview1').treeview('expandNode',select_node);
            select_node[0].state.selected=false;
        }
    }
  var node = data;
  var id="";
  var name = "请选择修复原因";
  if(node.mhasChild=="false")
  {
	  id = node.id;
	  name=node.text;
	  $("#repairReason").replaceWith('<span id="repairReason" class="'+id+'">'+name+'</span>');
  $("#reasonTreeModal").modal('hide');
  }
  
});


	$("#reasonTreeModal").modal('show');
}






//选择上门和自动回访控制JS
function chooseButton(checked,check)
{
	debugger;
	if(checked==1 || check == 1)
	{
		if(checked==1)
		{
			$("#select1").replaceWith('<img src="image/gzdb_icon_selected1.png" id="select1" onclick="chooseButton('+checked+','+check+')"/>');
			$("#select2").replaceWith('<img src="image/gzdb_icon_select1.png" id="select2" onclick="chooseButton('+check+','+checked+')"/>');
			checkBoxParam.isVisit="1";
		}
		else
		{
			$("#select2").replaceWith('<img src="image/gzdb_icon_selected1.png" id="select2" onclick="chooseButton('+checked+','+check+')"/>');
			$("#select1").replaceWith('<img src="image/gzdb_icon_select1.png" id="select1" onclick="chooseButton('+check+','+checked+')"/>');
			checkBoxParam.isVisit="0";
		}
	}
	if(checked==3 || check == 3)
	{
		if(checked==3)
		{
			$("#select3").replaceWith('<img src="image/gzdb_icon_selected1.png" id="select3" onclick="chooseButton('+checked+','+check+')"/>');
			$("#select4").replaceWith('<img src="image/gzdb_icon_select1.png" id="select4" onclick="chooseButton('+check+','+checked+')"/>');
			checkBoxParam.autoReturnVisit="1";
		}
		else
		{
			$("#select4").replaceWith('<img src="image/gzdb_icon_selected1.png" id="select4" onclick="chooseButton('+checked+','+check+')"/>');
			$("#select3").replaceWith('<img src="image/gzdb_icon_select1.png" id="select3" onclick="chooseButton('+check+','+checked+')"/>');
			checkBoxParam.autoReturnVisit="0";
		}
	}
	
}


function refreshFaultReason()
{
	
	var param = {
		JobId:commonInfo.ktdb.JOB_ID
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/tree/fault/reason/query',
  data:JSON.stringify(param),
  dataType: "json",
  contentType:"application/json",
  success: function (result){
	  debugger;
	       //关闭遮罩
$("#loadingModal").modal('hide');
$("#repairReason").empty();
var titleNode =   $('<option id="titleNode"  selected="selected">请选择修复原因</option>');
  $("#repairReason").append(titleNode);
	  var resultCode =  result.resultCode;
	  if(resultCode=='1000' && result)
	  {
	
	  var dataList =   result.resultData.Listdata;
	  makeTreeViewData(dataList);
	  
	
	
	  }
	  else
	  {
		//提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		  showMessage('3','修复原因查询失败');
		  }
		  else
		  {
			   showMessage('3',result.resultMsg);
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			     //关闭遮罩
$("#loadingModal").modal('hide');
  //提示成功返回到详情界面
	  showMessage('2','修复原因查询失败');
        }
		
	});
	
	
}

function makeTreeViewData(dataList)
{
	var datas0 = new Array();
	var datas1 = new Array();
	var datas2 = new Array();
	var datas3 = new Array();
	defaultData = new Array();
	//进行数据筛选
	 for(var i=0;i<dataList.length;i++)
	  {
		 var data = dataList[i];
		 if(data.level=="1")
		 {
			datas0.push(data);
		 }
		 
		  if(data.level=="2")
		 {
			datas1.push(data);
		 }
		  if(data.level=="3")
		 {
			datas2.push(data);
		 }
		  if(data.level=="4")
		 {
			datas3.push(data);
		 }

	  }
	  for(var a=0;a<datas0.length;a++)
	  {
		var nodeList0 = new Array();
	  for(var i=0;i<datas1.length;i++)
	  {
		  var nodeList1 = new Array();
		  for(var z=0;z<datas2.length;z++)
		  {
			  var nodeList2 = new Array();
			  for(var j=0;j<datas3.length;j++)
			  {
				  if(datas3[j].parent==datas2[z].id)
				  {
					 // var node3= datas3[j];
					  var node3={
						  id:datas3[j].id,
						  level:datas3[j].level,
						   parents:datas3[j].parent,
						  text:datas3[j].name,
						  mhasChild:datas3[j].mhasChild

					  };
				      nodeList2.push(node3);
				  }
				  
			  }
			  if(datas2[z].parent==datas1[i].id)
				  {
					   var node2 =null;
					  if(nodeList2.length!=0)
					  {
							  node2 =
						  {
						   id:datas2[z].id,
						   level:datas2[z].level,
						    parents:datas2[z].parent,
						   text:datas2[z].name,
						   mhasChild:datas2[z].mhasChild,
						   nodes:nodeList2
						  }; 
					  }
					  else
					  {
						    node2 =
						  {
						   id:datas2[z].id,
						   level:datas2[z].level,
						    parents:datas2[z].parent,
						   text:datas2[z].name,
						   mhasChild:datas2[z].mhasChild
						  
						  }; 
					  }
					  
					  nodeList1.push(node2);
				  }
		  }
		  if(datas1[i].parent==datas0[a].id)
		  {
			  var node1=null;
			  if(nodeList1.length!=0)
		  {
				   node1 =
			  {
				   id:datas1[i].id,
				   text:datas1[i].name,
				   level:datas1[i].level,
				   parents:datas1[i].parent,
				   mhasChild:datas1[i].mhasChild,
				   nodes:nodeList1
				  
			  };
		  }else
		  {
			  node1 =
			  {
				   id:datas1[i].id,
				   text:datas1[i].name,
				   level:datas1[i].level,
				   parents:datas1[i].parent,
				   mhasChild:datas1[i].mhasChild
				   
				  
			  };
		  }
		  nodeList0.push(node1);
		  }

		  
	  }
	  var node0={
				   id:datas0[a].id,
				   text:datas0[a].name,
				   level:datas0[a].level,
				   parents:datas0[a].parent,
				   mhasChild:datas0[a].mhasChild,
				   nodes:nodeList0
	  }
	   defaultData.push(node0);
	  }
	  
}


function callBack()
{
	//页面回退
	url="/MOBILE/report/bz/faultWorkOrder.jsp?staffId="+commonInfo.ktdb.staffId;
	callBackHistory(url);
	
}



function submitFaultReplayInfo()
{
	debugger;
	//显示遮罩
$("#loadingModal").modal('show');
	
	
	
	//var orderType  = commonInfo.ktdb.WorkOrderID;
	
	var workOrderId = commonInfo.ktdb.WorkOrderID;
	var workOrderCode = commonInfo.ktdb.WorkOrderCode;
	var autoReturnVisit = checkBoxParam.autoReturnVisit;
	var isVisit =checkBoxParam.isVisit;
	//恢复时间
	var recoverTime = $("#endTime").val();
	var recoverReasonId = $("#repairReason").attr('class');
	var maintainStaffId = commonInfo.ktdb.staffId;
	//到达时间
	var arriveTime = $("#startTime").val();
	//staff_name
	var recoverConfirmStaff = commonInfo.ktdb.STAFF_NAME;
	//联系人号码
	var confirmTel =requestParam.phoneNumber;
	//描述
	var desc =$("#describe").val();
	//备注
	var remark = "";
	//免责备注
	var reliefRemark ="";
	//资源变更说明
	var resChangeDes = "";//
	var timeOutReasonId = "";//传空
	//staffId
	var trackHelpId = commonInfo.ktdb.staffId;//
	if(arriveTime=="" || arriveTime==null)
	{
		       //关闭遮罩
$("#loadingModal").modal('hide');
		
		showMessage('1','到达时间不能为空');
		return;
	}
	if(recoverTime=="" || recoverTime==null)
	{
		       //关闭遮罩
$("#loadingModal").modal('hide');
	
		showMessage('1','恢复时间不能为空');
				return;
	}
	if(desc=="" || desc==null)
	{
		       //关闭遮罩
$("#loadingModal").modal('hide');
	
			showMessage('1','描述不能为空');
				return;
	}
	if(recoverReasonId=="" || recoverReasonId==null)
	{
		       //关闭遮罩
$("#loadingModal").modal('hide');
		
		showMessage('1','修复原因不能为空');
				return;
	}
	
	var param = {
		orderType:"",
		workOrderId:workOrderId,
	    workOrderCode:workOrderCode,
		autoReturnVisit:autoReturnVisit,
		isVisit:isVisit,
		recoverTime:recoverTime,
		recoverReasonId:recoverReasonId,
		maintainStaffId:maintainStaffId,
		arriveTime:arriveTime,
		recoverConfirmStaff:recoverConfirmStaff,
		confirmTel:confirmTel,
		desc:desc,
		remark:remark,
		reliefRemark:reliefRemark,
		resChangeDes:resChangeDes,
		timeOutReasonId:timeOutReasonId,
		trackHelpId:trackHelpId
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/bz/web/faultorder/reply',
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
	 
	  	 showReplyMessage('0','提交成功',commonInfo.ktdb.staffId);
	 // callBack();
	  }
	  else
	  {
		//提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		  showMessage('3','数据提交失败');
		  }
		  else
		  {
			   showMessage('3',result.resultMsg);
		  }
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
			     //关闭遮罩
$("#loadingModal").modal('hide');
  //提示成功返回到详情界面
	  showMessage('3','系统内部错误');
        }
		
	});
	

}


