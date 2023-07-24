
var requestParam = {};
var commonInfo ={};
var callBackReason= null;
var datas1 = null;
	var datas2 = null;
	var datas3 = null;
function init()
{
	debugger;
	$("#loadingModal").modal('show');
	//解析url参数
	 requestParam = GetURLRequestParam();
	//获取window.name参数
	 commonInfo = parseWindowName();
	var staffName=commonInfo.ktdb.STAFF_NAME;
	var orderNumber = commonInfo.ktdb.WorkOrderID; //orderCode
getCallBackReason()

		//经度节点生成
	$("#order_number").replaceWith('<div id="order_number">'+orderNumber+'</div>');
	$("#ope_order_number").replaceWith('<div id="ope_order_number">'+staffName+'</div>');


	
}

function callBack()
{
	window.history.back(-1);
}


function submitCallBackInfo()
{
	debugger;
	$("#loadingModal").modal('show');
	var content = $("#describe1").val();
	var param ={
		work_order_id:commonInfo.ktdb.WorkOrderID,
		
		track_org_id:commonInfo.ktdb.ORG_ID,
		track_org_name:commonInfo.ktdb.ORG_NAME,
		track_staff_id:commonInfo.ktdb.staffId,
		track_staff_name:commonInfo.ktdb.STAFF_NAME,
		track_content:content
		
	};
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/fault/insert/callbackreason',
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
	
	 showMessage(requestParam.forward,'数据提交成功');
	  }
	  else
	  {
		  //提示失败原因返回到详情
		  
					showMessage(requestParam.forward,'反馈原因数据入库失败');
				
		
		
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
				     //关闭遮罩
$("#loadingModal").modal('hide');
			 
					showMessage(requestParam.forward,'反馈原因数据获取失败');

        }
		
	});
		 
	
	

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


function getCallBackReason()
{
	var value="";
	//2代表开通工单4代表故障工单
	if(requestParam.forward=='2')
	{
		value='kt';
	}
	else
	{
		value='sa';
	}
	var param ={
		key:value
	};
	$.ajax({
		type: 'POST',
  url: '/MOBILE/api/client/xj/web/fault/callbackreason',
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
			    
		  
					showMessage(requestParam.forward,'反馈原因数据获取失败');
				
		  }
		  else
		  {
			
					showMessage(requestParam.forward,result.resultMsg);

		  }
		
		 
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
				     //关闭遮罩
$("#loadingModal").modal('hide');
			 
					showMessage(requestParam.forward,'反馈原因数据获取失败');

        }
		
	});
}


function inputText()
{
	debugger;
	 $("#describeSelect").modal('hide');
	$("#describe1").focus();
}



function showDescModel()
{
	debugger;
	$("describe1").focus();
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
  //第四级只需要把内容写入描述
  if((node.level_id=="4" && !node.nodes))
  {
	  id = node.id;
	  describer=node.text;
	 var content =describer;
	 $("#describe1").replaceWith('<textarea  rows="4px" cols="22px" id="describe1" onclick="showDescModel()"  value="'+content+'">'+content+'</textarea>');
	 $("#describeSelect").modal('hide');
  }

  
});

	
	$("#describeSelect").modal('show');
}


