
var requestParam = {};
var commonInfo ={};
var callBackReasonType={};
var callBackReason= null;
var datas1 = null;
	var datas2 = null;
	var datas3 = null;
function init()
{
	debugger;
	//解析url参数
	 requestParam = GetURLRequestParam();
	//获取window.name参数
	 commonInfo = parseWindowName();
	var staffName=commonInfo.ktdb.STAFF_NAME;
	var orderNumber = commonInfo.ktdb.WorkOrderID; //orderCode
getCallBackReason();

		//经度节点生成
	$("#order_number").replaceWith('<div id="order_number">'+orderNumber+'</div>');
	$("#ope_order_number").replaceWith('<div id="ope_order_number">'+staffName+'</div>');


	
}


function makeTreeViewData(dataList)
{
	 datas1 = new Array();
	 datas2 = new Array();
	 datas3 = new Array();
	callBackReason = new Array();
	//进行数据筛选
	 for(var i=0;i<dataList.length;i++)
	  {
		 var data = dataList[i];
		 
		  if(data.level_id=="2")
		 {
			datas1.push(data);
		 }
		  if(data.level_id=="3")
		 {
			datas2.push(data);
		 }
		  if(data.level_id=="4")
		 {
			datas3.push(data);
		 }

	  }
	  
	  for(var i=0;i<datas1.length;i++)
	  {
		  var nodeList1 = new Array();
		  for(var z=0;z<datas2.length;z++)
		  {
			  var nodeList2 = new Array();
			  for(var j=0;j<datas3.length;j++)
			  {
				  if(datas3[j].parent_id==datas2[z].id)
				  {
					 // var node3= datas3[j];
					  var node3={
						  id:datas3[j].id,
						  level_id:datas3[j].level_id,
						  parent_id:datas3[j].parent_id,
						  text:datas3[j].return_name,
						  mhasChild:datas3[j].mhasChild

					  };
				      nodeList2.push(node3);
				  }
				  
			  }
			  if(datas2[z].parent_id==datas1[i].id)
				  {
					   var node2 =null;
					  if(nodeList2.length!=0)
					  {
							  node2 =
						  {
						   id:datas2[z].id,
						   level_id:datas2[z].level_id,
						   parent_id:datas2[z].parent_id,
						   text:datas2[z].return_name,
						   mhasChild:datas2[z].mhasChild,
						   nodes:nodeList2
						  }; 
					  }
					  else
					  {
						    node2 =
						  {
						   id:datas2[z].id,
						    level_id:datas2[z].level_id,
							parent_id:datas2[z].parent_id,
						   text:datas2[z].return_name,
						   mhasChild:datas2[z].mhasChild
						  
						  }; 
					  }
					  
					  nodeList1.push(node2);
				  }
		  }
		  var node1 =
		  {
			   id:datas1[i].id,
			   level_id:datas1[i].level_id,
			   parent_id:datas1[i].parent_id,
			   text:datas1[i].return_name,
			   mhasChild:datas1[i].mhasChild,
			   nodes:nodeList1
			  
		  };
		  callBackReason.push(node1);
		  
	  }
	  
	  
	  
}


//下拉框联动
/*function setReasonSelect(result)
{
	debugger;
	var resultList= result.resultData.ReturnData;
	var reasonType = new Array();
	var reasons = new Array();
	for(var i=0;i<resultList.length;i++)
	{
		var type = resultList[i].key[0];
		reasonType.push(type);
		var reasonList = resultList[i].value;
		for(var z=0;z<reasonList.length;z++)
		{
			var reason = reasonList[z];
			reasons.push(reason);
		}
	}
	$("#reasonId").empty();
	for(var i=0;i<reasonType.length;i++)
	{
		var object = reasonType[i];
		
		var opt= $("<option id='"+object.id+"'>"+object.return_name+"</option>");
		$("#reasonId").append(opt);
	}
	$("#reasonType").empty();
	for(var i=0;i<reasons.length;i++)
	{
		var object = reasons[i];
		
		var opt= $("<option id='"+object.id+"'>"+object.return_name+"</option>");
		$("#reasonType").append(opt);
	}
	
}*/
/*
function changeReasonSelect()
{
	debugger;
	var resultList= callBackReason.resultData.ReturnData;
	var reasonType = new Array();
	var reasons = new Array();
	var id = $("#reasonId option:selected").attr('id');
	for(var i=0;i<resultList.length;i++)
	{
		var type = resultList[i].key[0];
		reasonType.push(type);
		if(id==type.id)
		{
			var reasonList = resultList[i].value;
			for(var z=0;z<reasonList.length;z++)
			{
				var reason = reasonList[z];
				
				reasons.push(reason);
			}
		}
		
	}
	$("#reasonType").empty();
	for(var i=0;i<reasons.length;i++)
	{
		var object = reasons[i];
		var opt= $("<option id='"+object.id+"'>"+object.return_name+"</option>");
		$("#reasonType").append(opt);
	}
	
}*/

function getCallBackReason()
{
	var param ={
		content:"11"
	};
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/kt/callBack/reason',
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
	
	//setReasonSelect(callBackReason);
	var dataLists= result.resultData.ReturnData;
	makeTreeViewData(dataLists);
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  if(result.resultMsg==""||result.resultMsg==null)
		  {
			    
		  if(requestParam.forward=='1')
				{
					showMessage('1','退单原因数据获取失败');
				}
				else
				{
					showMessage('1','退单原因数据获取失败');
				}
		  }
		  else
		  {
			  if(requestParam.forward=='1')
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
			  if(requestParam.forward=='1')
				{
					showMessage('2','退单原因数据获取失败');
				}
				else
				{
					showMessage('3','退单原因数据获取失败');
				}
        }
		
	});
}

function callBack()
{
	window.history.back(-1); 
}

function showDescModel()
{
	debugger;
	$('#treeview1').treeview({ 
    data: callBackReason
  
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
  //如果没有第四级只需要把内容写入描述
  if((node.level_id=="3" && !node.nodes))
  {
	  id = node.id;
	  describer=node.text;
	 var content = id+'-'+describer;
	 $("#describe1").replaceWith('<textarea  rows="4px" cols="22px" id="describe1" onclick="showDescModel()" readonly="readonly" value="'+content+'">'+content+'</textarea>');
	  $("#remark1").replaceWith('<textarea  rows="4px" cols="22px" id="remark1"></textarea>');
	  $("#describeSelect").modal('hide');
  }
  //判断是第四级的话需要把第三级的数据放入描述
  if(node.level_id=="4")
  {
	  for(var i=0;i<datas2.length;i++)
	  {
		  if(datas2[i].id==node.parent_id)
		  {
			   id = node.id;
	          describer=node.text;
	          var content = id+'-'+describer;
			   $("#describe1").replaceWith('<textarea  rows="4px" cols="22px" id="describe1" readonly="readonly" onclick="showDescModel()" value="'+content+'">'+content+'</textarea>');
			   $("#remark1").replaceWith('<textarea  rows="4px" cols="22px" id="remark1">'+datas2[i].return_name+'</textarea>');
	         break;
		  }
	  }
	   $("#describeSelect").modal('hide');
  }
 
  
});


	
	
	$("#describeSelect").modal('show');
}




function submitCallBackInfo()
{
	debugger;
	
	//显示遮罩
$("#loadingModal").modal('show');
	var content  = $("#describe1").val();
	var remark =  $("#remark1").val();
	var orderCode = requestParam.orderCode;//工单编码
	var executorId = commonInfo.ktdb.staffId;//staffid
	//var orderType = requestParam.orderType;//开通工单是10M故障工单10S
	var staffId = commonInfo.ktdb.staffId;
	var userName = commonInfo.ktdb.USERNAME;
	var workOrderID = commonInfo.ktdb.WorkOrderID;
	
	if(content=="" || content== null)
	{
		//描述不能为空
		
		 showMessage('1','描述不能为空');
		 	  	     //关闭遮罩
$("#loadingModal").modal('hide');
		return;
	}
	var param = {
		content:content,
		executorId:executorId,
	    orderType:"1",
		remark:remark,
		staffId:staffId,
		userName:userName,
		workOrderCode:orderCode,
		//username:"",
		workOrderID:workOrderID
	}
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/kt/backorderinfo/submit',
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
					showReplyMessage('1','数据提交成功',commonInfo.ktdb.staffId);
				}
				else
				{
					showReplyMessage('0','数据提交成功',commonInfo.ktdb.staffId);
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
			  if(requestParam.forward=='1')
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


