var orderList = {};
var workOrderList = {};
var resourceList = {};
var requestParam = {};
function init() {
	debugger;
	// window.name="ktdb:WorkOrderID=21946000,WorkOrderType=10A,OrderCode=1538529001,WorkOrderCode=,JOB_ID=50632,ORG_ID=748,ORG_NAME=湖南联通省公司,STAFF_NAME=公众工号,USERNAME=iom,staffId=242000";
	// 显示遮罩
	$("#loadingModal").modal('show');

	resourceList = {};
	requestParam = parseWindowName();
	var workOrderId = requestParam.ktdb.WorkOrderID;
	getResourceInfoById(workOrderId);

}

function getResourceInfoById(workOrderId) {
	debugger;
	var data = {
		workOrderID : workOrderId
	}
	debugger;
	$.ajax({
		type : 'POST',
		url : '/MOBILE/api/client/xj/kt/detail/resource',
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json",
		success : function(result) {
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');
			var resultCode = result.resultCode;
			if (resultCode == '1000' && result) {
				var dataStr = result.resultData.ReturnData[0];
//				resourceList = JSON.parse(dataStr);
				resourceList = eval('('+ dataStr + ')');
				getBasicInfo();

			} else {
				getBasicInfo();
			}

		},
		error : function(result) {
			/* 错误信息处理 */
			debugger;

			getBasicInfo();
			// 关闭遮罩
			$("#loadingModal").modal('hide');
		}

	});

}

function GetURLRequestParam() {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}

function callBack() {
	// 页面回退
	url = "/MOBILE/report/deviceManager/ktdb_orderList.jsp?staffId="
			+ requestParam.ktdb.staffId;
	callBackHistory(url);
}

function getBasicInfo() {
	debugger;
	$("#basic").attr('bgcolor', 'darkorange');
	$("#workFlow").attr('bgcolor', '#FFFFFF');
	$("#custInfo").attr('bgcolor', '#FFFFFF');
	$("#callBack").attr('bgcolor', '#FFFFFF');
	$("#resource").attr('bgcolor', '#FFFFFF');
	$("#detail-ul").empty();

	var cust_Name = "";
	var contact = "";
	var contact_P = "";
	var zJAddr = "";
	var new_speed_name = "";
	var project_name = "";
	var logic_Num = "";
	if (!$.isEmptyObject(resourceList.app_basic_info)) {
		cust_Name = resourceList.app_basic_info.cust_name == null ? ""
				: resourceList.app_basic_info.cust_name;
		contact = resourceList.app_basic_info.contact_name == null ? ""
				: resourceList.app_basic_info.contact_name;
		contact_P = resourceList.app_basic_info.contact_nbr == null ? ""
				: resourceList.app_basic_info.contact_nbr;
		zJAddr = resourceList.app_basic_info.addr_name == null ? ""
				: resourceList.app_basic_info.addr_name;
		logic_Num = resourceList.app_basic_info.acc_nbr == null ? ""
				: resourceList.app_basic_info.acc_nbr;
		new_speed_name = resourceList.app_basic_info.acc_nbr == null ? ""
				: resourceList.app_basic_info.new_speed_name;
		project_name = resourceList.app_order_info.project_name == null ? ""
				: resourceList.app_order_info.project_name;
	}

	var userName = $("<li class=detail_li>用户名:<span class=info_detail>"
			+ cust_Name + "</span></li>");
	var contactName = $("<li class=detail_li>联系人:<span class=info_detail>"
			+ contact + "</span></li>");
	var serviceNumber = $("<li class=detail_li>联系电话:<span class=info_detail>"
			+ contact_P + "</span></li>");
	var address = $("<li class=detail_li>装机地址:<span class=info_detail>"
			+ zJAddr + "</span></li>");
	var businessCode = $("<li class=detail_li>业务号码:<span class=info_detail>"
			+ logic_Num + "</span></li>");
	var speedname = $("<li class=detail_li>宽带速率:<span class=info_detail>"
			+ new_speed_name + "</span></li>");
	var projectName = $("<li class=detail_li>所属项目:<span class=info_detail>"
			+ project_name + "</span></li>");
	$("#detail-ul").append(userName);
	$("#detail-ul").append(contactName);
	$("#detail-ul").append(serviceNumber);
	$("#detail-ul").append(address);
	$("#detail-ul").append(businessCode);
	$("#detail-ul").append(speedname);
	$("#detail-ul").append(projectName);
	// 加上服务号码和客户联系电话参数
	window.name = window.name + ',contactPhone=' + contact_P + ',businessCode='
			+ logic_Num;
	// 加上服务名称
	if (!$.isEmptyObject(resourceList.app_order_info)) {
		window.name = window.name
				+ ',serviceName='
				+ (resourceList.app_order_info.service_name == null ? ""
						: resourceList.app_order_info.service_name);
	}
}

function getWorkFlowInfo() {
	debugger;
	$("#workFlow").attr('bgcolor', 'darkorange');
	$("#basic").attr('bgcolor', '#FFFFFF');
	$("#custInfo").attr('bgcolor', '#FFFFFF');
	$("#callBack").attr('bgcolor', '#FFFFFF');
	$("#resource").attr('bgcolor', '#FFFFFF');
	$("#detail-ul").empty();

	var cust_order_code = "";
	var service_name = "";
	var accept_staff_name = "";
	var business_office = "";
	var front_create_date = "";
	if (!$.isEmptyObject(resourceList.app_order_info)) {
		cust_order_code = resourceList.app_order_info.work_order_code == null ? ""
				: resourceList.app_order_info.work_order_code;
		service_name = resourceList.app_order_info.service_name == null ? ""
				: resourceList.app_order_info.service_name;
		accept_staff_name = resourceList.app_order_info.accept_staff_name == null ? ""
				: resourceList.app_order_info.accept_staff_name;
		business_office = resourceList.app_order_info.business_office == null ? ""
				: resourceList.app_order_info.business_office;

		front_create_date = resourceList.app_order_info.front_create_date == null ? ""
				: resourceList.app_order_info.front_create_date;

	}

	var orderNumber = $("<li class=detail_li>订单编码:<span class=info_detail>"
			+ cust_order_code + "</span></li>");

	var applyTime = $("<li class=detail_li>服务名称:<span class=info_detail>"
			+ service_name + "</span></li>");

	var receiver = $("<li class=detail_li>受理人:<span class=info_detail>"
			+ accept_staff_name + "</span></li>");
	var dept = $("<li class=detail_li>受理部门:<span class=info_detail>"
			+ business_office + "</span></li>");

	var orderRecTime = $("<li class=detail_li>订单受理时间:<span class=info_detail>"
			+ front_create_date + "</span></li>");

	$("#detail-ul").append(orderNumber);

	$("#detail-ul").append(applyTime);
	$("#detail-ul").append(receiver);
	$("#detail-ul").append(dept);

	$("#detail-ul").append(orderRecTime);
}

function getResourceInfo() {
	debugger;
	$("#basic").attr('bgcolor', '#FFFFFF');
	$("#workFlow").attr('bgcolor', '#FFFFFF');
	$("#custInfo").attr('bgcolor', '#FFFFFF');
	$("#callBack").attr('bgcolor', '#FFFFFF');
	$("#resource").attr('bgcolor', 'darkorange');
	$("#detail-ul").empty();
	var businessType = "";
	var oltid = "";
	var ponid = "";
	var equip_number = "";
	var cvlan = "";
	var svlan = "";
	var mod_number = "";
	var new_r_p_tele = "";
	var wireless_number = "";
	var yy_pwd = "";
	if (!$.isEmptyObject(resourceList.app_rim_info)) {
		businessType = $("<li class=detail_li>局向:<span class=info_detail>"
				+ resourceList.app_rim_info.exch_name + "</span></li>");
		oltid = $("<li class=detail_li>ONU所属OLT的IP地址:<span class=info_detail>"
				+ resourceList.app_rim_info.oltid + "</span></li>");
		ponid = $("<li class=detail_li>PON端口（架-框-板-端口）:<span class=info_detail>"
				+ resourceList.app_rim_info.ponid + "</span></li>");
		equip_number = $("<li class=detail_li>设备号（端口号）:<span class=info_detail>"
				+ resourceList.app_rim_info.equip_number + "</span></li>");
		cvlan = $("<li class=detail_li>内层VLAN:<span class=info_detail>"
				+ resourceList.app_rim_info.cvlan + "</span></li>");
		svlan = $("<li class=detail_li>外层VLAN:<span class=info_detail>"
				+ resourceList.app_rim_info.svlan + "</span></li>");
		mod_number = $("<li class=detail_li>模块号:<span class=info_detail>"
				+ resourceList.app_rim_info.mod_number + "</span></li>");
		new_r_p_tele = $("<li class=detail_li>新物理号码:<span class=info_detail>"
				+ resourceList.app_rim_info.new_r_p_tele + "</span></li>");
		wireless_number = $("<li class=detail_li>NGN协议:<span class=info_detail>"
				+ resourceList.app_rim_info.wireless_number + "</span></li>");
		yy_pwd = $("<li class=detail_li>IMS语音密码:<span class=info_detail>"
				+ resourceList.app_rim_info.yy_pwd + "</span></li>");
	}

	$("#detail-ul").append(businessType);
	$("#detail-ul").append(oltid);
	$("#detail-ul").append(ponid);
	$("#detail-ul").append(equip_number);
	$("#detail-ul").append(cvlan);
	$("#detail-ul").append(svlan);
	$("#detail-ul").append(mod_number);
	$("#detail-ul").append(new_r_p_tele);
	$("#detail-ul").append(wireless_number);
	$("#detail-ul").append(yy_pwd);

}

function getCallBackInfo() {
	debugger;
	$("#basic").attr('bgcolor', '#FFFFFF');
	$("#workFlow").attr('bgcolor', '#FFFFFF');
	$("#custInfo").attr('bgcolor', '#FFFFFF');
	$("#callBack").attr('bgcolor', 'darkorange');
	$("#resource").attr('bgcolor', '#FFFFFF');
	$("#detail-ul").empty();
	var number = "";
	var name = $("<li class=detail_li>反馈人:<span class=info_detail>" + number
			+ "</span></li>");
	var callBackTime = $("<li class=detail_li>反馈日期:<span class=info_detail>"
			+ number + "</span></li>");
	var callBackDetail = $("<li class=detail_li>反馈内容:<span class=info_detail>"
			+ number + "</span></li>");

	$("#detail-ul").append(name);
	$("#detail-ul").append(callBackTime);
	$("#detail-ul").append(callBackDetail);

}

function forwardToPage(name) {
	var url = "/MOBILE/report/workfloworder/";
	if (name == "signed.html") {

		window.location.href = url + name + "?forward=1";
	}
	if (name == "callback.html") {

		window.location.href = url + name + '?' + "orderCode="
				+ requestParam.ktdb.OrderCode + "&forward=1";
	}
	if (name == "terminal.html") {
		var Logic_Num = resourceList.app_basic_info.acc_nbr;

		window.location.href = url + name + '?' + "orderCode="
				+ requestParam.ktdb.OrderCode + '&' + 'Logic_Num=' + Logic_Num;
	}

	if (name == "replay.html")// 还差光猫
	{

		window.location.href = url + name + '?' + "orderCode="
				+ requestParam.ktdb.OrderCode;
	}
	if (name == "active.html") {

		window.location.href = url + name;
	}
	if (name == "reservation.html") {
		var Logic_Num = resourceList.app_basic_info.acc_nbr;
		var contact_p = resourceList.app_basic_info.contact_nbr;

		window.location.href = url + name + '?' + "orderCode="
				+ requestParam.ktdb.OrderCode + '&' + 'Logic_Num=' + Logic_Num
				+ '&' + 'Contact_P=' + contact_p + "&orderClass=10S"
				+ "&forward=1";
	}
	if (name == "faultcallback.html") {

		window.location.href = url + name + "?forward=2";
	}
	if (name == "modifyOrder.html") {
		//forward为2表示提交后返回工单详情页面
		window.location.href = url + name + "?forward=2";
	}
	if(name == "qrcode_install.html"){
		window.location.href = url + name;
	}

}
