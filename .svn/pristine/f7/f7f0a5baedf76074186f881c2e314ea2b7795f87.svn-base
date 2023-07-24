
var orderList={};
var requestParam ={};
function init()
{
	debugger;
	//window.name="ktdb:WorkOrderID=61339626,WorkOrderType=10A,OrderCode=1538529001,WorkOrderCode=T16082710590257078390538FWBZ_CL1,JOB_ID=112820,ORG_ID=748,ORG_NAME=湖南联通省公司,STAFF_NAME=测试账号1,USERNAME=iom,staffId=242000";
	//显示遮罩
$("#loadingModal").modal('show');
	 
	

 orderList={};
 
 requestParam = parseWindowName();
var workOrderId = requestParam.ktdb.WorkOrderID;
	
	var data = {
		WorkOrderID: workOrderId
	

	};
	
	$.ajax({
  type: 'POST',
  url: '/MOBILE/api/client/xj/bz/web/faultorder/private/detail',
  data:JSON.stringify(data),
  dataType: "json",
  contentType:"application/json",
  success: function (result){
	  debugger;
	  	//关闭遮罩
$("#loadingModal").modal('hide');
	  var resultCode =  result.resultCode;
	  if(resultCode=='1000' && result)
	  {
	
	 orderList = result.resultData.workorderDetail;
	
	  getBasicInfo();
	  }
	  else
	  {
		  //alert();
		  getBasicInfo();
	  }
	
	  
  },
  error: function (result) {
            /*错误信息处理*/
			debugger;
		
		  getBasicInfo();
				//关闭遮罩
$("#loadingModal").modal('hide');
        }

  
});
	
}


function getRequestParamId(stuffId)
{
	
}

function GetURLRequestParam() {  
   var url = location.search; //获取url中"?"符后的字串  
   var theRequest = new Object();  
   if (url.indexOf("?") != -1) {  
      var str = url.substr(1);  
      strs = str.split("&");  
      for(var i = 0; i < strs.length; i ++) {  
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
      }  
   }  
   return theRequest;  
}

function callBack()
{
	//页面回退
	url="/MOBILE/report/bz/faultWorkOrder.jsp?staffId="+requestParam.ktdb.staffId;
	callBackHistory(url);
	
}


function getBasicInfo() {
	debugger;
$("#basic").attr('bgcolor','darkorange');
$("#workFlow").attr('bgcolor','#FFFFFF');
$("#custInfo").attr('bgcolor','#FFFFFF');
$("#callBack").attr('bgcolor','#FFFFFF');
$("#resource").attr('bgcolor','#FFFFFF');	
$("#detail-ul").empty();
var number="";
var WorkOrderCode = "";
var workOrderServiceNbr = "";
var workOrderAcceptTime = "";
var workOrderCustLinkPerson = "";
var workOrderCustLinkPhone = "";
var workOrderCustName = "";
var workOrderTitle = "";
var workOrderAddress = "";
var workOrderDesc = "";
var workOrderLimitTime = "";
var workOrderState = "";
var workOrderDealMan = "";
var workOrderUrgeNum = "";
if(!$.isEmptyObject(orderList))
{
	workOrderServiceNbr = orderList.workOrderServiceNbr== null ? "" : orderList.workOrderServiceNbr;
 workOrderAcceptTime = orderList.workOrderAcceptTime == null ? "" : orderList.workOrderAcceptTime;
 workOrderCustLinkPerson = orderList.workOrderCustLinkPerson == null ? "" :orderList.workOrderCustLinkPerson;
 workOrderCustLinkPhone = orderList.workOrderCustLinkPhone == null ? "" : orderList.workOrderCustLinkPhone;
 workOrderCustName = orderList.workOrderCustName == null ? "" : orderList.workOrderCustName;
 workOrderTitle = orderList.workOrderTitle == null ? "" :orderList.workOrderTitle;
 workOrderAddress = orderList.workOrderAddress == null ? "" : orderList.workOrderAddress;
workOrderDesc = orderList.workOrderDesc == null ? "" : orderList.workOrderDesc;
workOrderLimitTime = orderList.workOrderLimitTime == null ? "" : orderList.workOrderLimitTime;
workOrderState = orderList.workOrderState == null ? "" : orderList.workOrderState;
workOrderDealMan = orderList.workOrderDealMan == null ? "" : orderList.workOrderDealMan;
workOrderUrgeNum = orderList.workOrderUrgeNum == null ? "" : orderList.workOrderUrgeNum;
}

 

var WorkOrderCode1 = $("<li class=detail_li>工单编号:<span class=info_detail>"+requestParam.ktdb.WorkOrderCode+"</span></li>");
//workOrderServiceNbr
var workOrderServiceNbr1 = $("<li class=detail_li>业务号码:<span class=info_detail>"+workOrderServiceNbr+"</span></li>");
//workOrderAcceptTime
var workOrderAcceptTime1 = $("<li class=detail_li>订单受理时间:<span class=info_detail>"+workOrderAcceptTime+"</span></li>");
//workOrderCustLinkPerson
var workOrderCustLinkPerson1 = $("<li class=detail_li>联系人:<span class=info_detail>"+workOrderCustLinkPerson+"</span></li>");
//workOrderCustLinkPhone
var workOrderCustLinkPhone1 = $("<li class=detail_li>客户联系电话:<span class=info_detail>"+workOrderCustLinkPhone+"</span></li>");

//workOrderCustName
var workOrderCustName1 = $("<li class=detail_li>客户名称:<span class=info_detail>"+workOrderCustName+"</span></li>");
//workOrderCustLinkPhone
var workOrderCustLinkPhone2 = $("<li class=detail_li>联系电话:<span class=info_detail>"+workOrderCustLinkPhone+"</span></li>");
//workOrderTitle
var workOrderTitle1= $("<li class=detail_li>工单主题:<span class=info_detail>"+workOrderTitle+"</span></li>");
//workOrderAddress
var workOrderAddress1 = $("<li class=detail_li>用户地址:<span class=info_detail>"+workOrderAddress+"</span></li>");
var ip = $("<li class=detail_li>受理人:<span class=info_detail>"+number+"</span></li>");
var remark1 = $("<li class=detail_li>故障现象:<span class=info_detail>"+number+"</span></li>");
//workOrderDesc
var workOrderDesc1 = $("<li class=detail_li>故障描述:<span class=info_detail>"+workOrderDesc+"</span></li>");
//workOrderLimitTime
var workOrderLimitTime1 = $("<li class=detail_li>截止时间:<span class=info_detail>"+workOrderLimitTime+"</span></li>");
var remark2 = $("<li class=detail_li>预约时间:<span class=info_detail>"+number+"</span></li>");
//workOrderState
var workOrderState1 = $("<li class=detail_li>工单状态:<span class=info_detail>"+workOrderState+"</span></li>");
//workOrderDealMan
var workOrderDealMan1 = $("<li class=detail_li>处理人:<span class=info_detail>"+workOrderDealMan+"</span></li>");
//workOrderUrgeNum
var workOrderUrgeNum1= $("<li class=detail_li>催缴次数:<span class=info_detail>"+workOrderUrgeNum+"</span></li>");
$("#detail-ul").append(WorkOrderCode1);
$("#detail-ul").append(workOrderServiceNbr1);
$("#detail-ul").append(workOrderAcceptTime1);
$("#detail-ul").append(workOrderCustLinkPerson1);
$("#detail-ul").append(workOrderCustLinkPhone1);


$("#detail-ul").append(workOrderCustName1);
$("#detail-ul").append(workOrderCustLinkPhone2);
$("#detail-ul").append(workOrderTitle1);
$("#detail-ul").append(workOrderAddress1);
$("#detail-ul").append(ip);
$("#detail-ul").append(remark1);
$("#detail-ul").append(workOrderDesc1);
$("#detail-ul").append(remark2);
$("#detail-ul").append(workOrderState1);
$("#detail-ul").append(workOrderDealMan1);
$("#detail-ul").append(workOrderUrgeNum1);
}


function getWorkFlowInfo() {
	debugger;
$("#workFlow").attr('bgcolor','darkorange');
$("#basic").attr('bgcolor','#FFFFFF');
$("#custInfo").attr('bgcolor','#FFFFFF');
$("#callBack").attr('bgcolor','#FFFFFF');
$("#resource").attr('bgcolor','#FFFFFF');	
$("#detail-ul").empty();
var number = "";
var iom_Order_Code="";
var applicTime ="2015\/12\/25 17:41:08"; 
var accep_D =""; 
var accep_U =""; 
if(!$.isEmptyObject(orderList))
{
	iom_Order_Code = orderList.Iom_Order_Code== null ? "" : orderList.Iom_Order_Code;
	applicTime = orderList.Applic_Date== null ? "" : orderList.Applic_Date;
	if(applicTime!="")
	{
		applicTime = applicTime.replace('"','').replace(/[\\]/g,'');
	}
	accep_U = orderList.Accep_U== null ? "" : orderList.Accep_U;
	accep_D = orderList.Accep_D== null ? "" : orderList.Accep_D;
}

var orderNumber = $("<li class=detail_li>订单编码:<span class=info_detail>"+iom_Order_Code+"</span></li>");
var orderLevel = $("<li class=detail_li>工单级别:<span class=info_detail>"+number+"</span></li>");
var applyTime = $("<li class=detail_li>申请时间:<span class=info_detail>"+applicTime+"</span></li>");
var lastOrderTime = $("<li class=detail_li>最后一次预约时间:<span class=info_detail>"+number+"</span></li>");
var receiver = $("<li class=detail_li>受理人:<span class=info_detail>"+accep_U+"</span></li>");
var dept = $("<li class=detail_li>受理部门:<span class=info_detail>"+accep_D+"</span></li>");
var remark1 = $("<li class=detail_li>受理备注:<span class=info_detail>"+number+"</span></li>");
var orderRecTime = $("<li class=detail_li>订单受理时间:<span class=info_detail>"+applicTime+"</span></li>");
var orderStartTime = $("<li class=detail_li>履约时间:<span class=info_detail>"+number+"</span></li>");
var remark2 = $("<li class=detail_li>备注:<span class=info_detail>"+number+"</span></li>");
$("#detail-ul").append(orderNumber);
$("#detail-ul").append(orderLevel);
$("#detail-ul").append(applyTime);
$("#detail-ul").append(lastOrderTime);
$("#detail-ul").append(receiver);
$("#detail-ul").append(dept);
$("#detail-ul").append(remark1);
$("#detail-ul").append(orderRecTime);
$("#detail-ul").append(orderStartTime);
$("#detail-ul").append(remark2);
}








function forwardToPage(name)
{
	
	var url  = "/MOBILE/report/workfloworder/";
	//签到
	if(name=="signed.html")
	{
		
		window.location.href = url+name+"?forward=2";
	}
	//退单
	if(name=="callback.html")
	{
		
		
		window.location.href = url+name+'?'+"orderCode="+requestParam.ktdb.OrderCode+"&forward=2";
	}

	//回单
	if(name=="faultReplay.html")//
	{
		
		window.location.href = url+name+'?'+"phoneNumber="+orderList.workOrderCustLinkPhone;
	}
	//反馈
	if(name=="faultcallback.html")
	{
		
		window.location.href = url+name+'?'+"forward=4";
	}
	//预约
	if(name=="reservation.html")
	{
		//业务号码
		var Logic_Num = orderList.workOrderServiceNbr;
		var contact_p = orderList.Contact_P;
		
		
		window.location.href = url+name+'?'+"orderCode="+requestParam.ktdb.WorkOrderCode+'&'+'Logic_Num='+Logic_Num+'&'+'Contact_P='+orderList.workOrderCustLinkPhone+"&orderClass=1SA"+"&forward=2";
	}
	
	
}



