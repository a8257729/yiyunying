var requestParam = {};
var commonInfo = {};
var callBackReason = null;
var datas1 = null;
var datas2 = null;
var datas3 = null;
function init() {
	debugger;
	$("#loadingModal").modal('show');
	$("#loadingModal").modal('hide');
	// 解析url参数
	requestParam = GetURLRequestParam();
	// 获取window.name参数
	commonInfo = parseWindowName();
	var staffName = commonInfo.ktdb.STAFF_NAME;
	var orderNumber = commonInfo.ktdb.WorkOrderID; //工单编号
	var contactPhone = commonInfo.ktdb.contactPhone;//客户联系电话
	var businessCode = commonInfo.ktdb.businessCode;//业务号码
	var serviceName = commonInfo.ktdb.serviceName;//服务名称
	/*getCallBackReason()*/

	$("#orderNumber").val(orderNumber);
	$("#oprateName").val(staffName);
	$("#serviceNum").val(businessCode);
	$("#contactPhone").val(contactPhone);
	$("#serviceType").val(serviceName);

}

function callBack() {
	window.history.back(-1);
}

//改单提交
function submitModifyOrderInfo() {
	debugger;
	
	var oprateName = $("#oprateName").val();
	var serviceNum = $("#serviceNum").val();
	var contactPhone = $("#contactPhone").val();
	var addressInfo = $("#addressInfo").val();
	var remark = $("#remark").val();
	var windowNameParam = parseWindowName();
	var orderId = windowNameParam.ktdb.orderId;
	var staffId = windowNameParam.ktdb.staffId;
	
	if(addressInfo == '' || remark == ''){
		showModifyOrderMessage(0, "标准地址和改单描述不能为空！");
		return;
	}
	
	$("#loadingModal").modal('show');
	
	var param = {
		orderId : orderId,
		staffId : staffId,
		oprateName : oprateName,
		serviceNum : serviceNum,
		contactPhone : contactPhone,
		addressInfo : addressInfo,
		remark : remark
	};
	$.ajax({
		type : 'POST',
		url : '/MOBILE/api/client/xj/kt/web/modifyorder/submit',
		data : JSON.stringify(param),
		dataType : "json",
		contentType : "application/json",
		success : function(result) {
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');
			var resultCode = result.resultCode;
//			resultCode=1;
			if (resultCode == 1 && result) {
				showModifyOrderMessage(resultCode, '数据提交成功');
			} else {
				showModifyOrderMessage(resultCode, result.resultMsg);

			}

		},
		error : function(result) {
			/* 错误信息处理 */
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');
			showModifyOrderMessage(0, '数据提交失败');
		}

	});

}

function closeVaidateMessage() {
	$('#validate').modal('hide');
}

function showModifyOrderMessage(retCode, message) {
	debugger;
	// 提交失败
	if (retCode != 1) {
		if(message == null || !message || message == ''){
			message = '改单失败，调用接口失败！';
		}
		$('#validate_img').replaceWith(
				" <img src='image/yjzd_icon3.png' id='validate_img'/>");
		$('#validate_button')
		.replaceWith(
				'<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick="closeVaidateMessage();">确定</button>');
	} else {
		$('#validate_img').replaceWith(
				" <img src='image/msgbox_true.png' id='validate_img'/>");
		$('#validate_button')
		.replaceWith(
				'<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick=callBackHistory("/MOBILE/report/workfloworder/detailInfo.html")>确定</button>');
	}
	$('#validate_message').empty();
	var node = $('<label id="model-message">' + message + '</label>');
	$('#validate_message').append(node);
	$('#validate').modal('show');

}

function makeTreeViewData(dataList) {
	datas1 = new Array();
	datas2 = new Array();
	datas3 = new Array();
	callBackReason = new Array();
	// 进行数据筛选
	for (var i = 0; i < dataList.length; i++) {
		var data = dataList[i];

		if (data.level_id == "2") {
			datas1.push(data);
		}
		if (data.level_id == "3") {
			datas2.push(data);
		}
		if (data.level_id == "4") {
			datas3.push(data);
		}

	}

	for (var i = 0; i < datas1.length; i++) {
		var nodeList1 = new Array();
		for (var z = 0; z < datas2.length; z++) {
			var nodeList2 = new Array();
			for (var j = 0; j < datas3.length; j++) {
				if (datas3[j].parent_id == datas2[z].id) {
					// var node3= datas3[j];
					var node3 = {
						id : datas3[j].id,
						level_id : datas3[j].level_id,
						parent_id : datas3[j].parent_id,
						text : datas3[j].return_name,
						mhasChild : datas3[j].mhasChild

					};
					nodeList2.push(node3);
				}

			}
			if (datas2[z].parent_id == datas1[i].id) {
				var node2 = null;
				if (nodeList2.length != 0) {
					node2 = {
						id : datas2[z].id,
						level_id : datas2[z].level_id,
						parent_id : datas2[z].parent_id,
						text : datas2[z].return_name,
						mhasChild : datas2[z].mhasChild,
						nodes : nodeList2
					};
				} else {
					node2 = {
						id : datas2[z].id,
						level_id : datas2[z].level_id,
						parent_id : datas2[z].parent_id,
						text : datas2[z].return_name,
						mhasChild : datas2[z].mhasChild

					};
				}

				nodeList1.push(node2);
			}
		}
		var node1 = {
			id : datas1[i].id,
			level_id : datas1[i].level_id,
			parent_id : datas1[i].parent_id,
			text : datas1[i].return_name,
			mhasChild : datas1[i].mhasChild,
			nodes : nodeList1

		};
		callBackReason.push(node1);

	}

}

function getCallBackReason() {
	var value = "";
	// 2代表开通工单4代表故障工单
	if (requestParam.forward == '2') {
		value = 'kt';
	} else {
		value = 'sa';
	}
	var param = {
		key : value
	};
	$.ajax({
		type : 'POST',
		url : '/MOBILE/api/client/xj/web/fault/callbackreason',
		data : JSON.stringify(param),
		dataType : "json",
		contentType : "application/json",
		success : function(result) {
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');
			var resultCode = result.resultCode;
			if (resultCode == '1000' && result) {

				// setReasonSelect(callBackReason);
				var dataLists = result.resultData.ReturnData;
				makeTreeViewData(dataLists);
			} else {
				// 提示失败原因返回到详情
				if (result.resultMsg == "" || result.resultMsg == null) {

					showMessage(requestParam.forward, '反馈原因数据获取失败');

				} else {

					showMessage(requestParam.forward, result.resultMsg);

				}

			}

		},
		error : function(result) {
			/* 错误信息处理 */
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');

			showMessage(requestParam.forward, '反馈原因数据获取失败');

		}

	});
}

function inputText() {
	debugger;
	$("#describeSelect").modal('hide');
	$("#describe1").focus();
}

function showDescModel() {
	debugger;
	$("describe1").focus();
	$('#treeview1').treeview({
		data : callBackReason

	}).treeview('collapseAll', {// 节点展开
		silent : true
	});
	$('#treeview1')
			.on(
					'nodeSelected',
					function(event, data) {
						debugger;
						if (data.nodes != null) {
							var select_node = $('#treeview1').treeview(
									'getSelected');
							if (select_node[0].state.expanded) {
								$('#treeview1').treeview('collapseNode',
										select_node);
								select_node[0].state.selected = false;
							} else {
								$('#treeview1').treeview('expandNode',
										select_node);
								select_node[0].state.selected = false;
							}
						}
						var node = data;
						var id = "";
						var name = "请选择修复原因";
						// 第四级只需要把内容写入描述
						if ((node.level_id == "4" && !node.nodes)) {
							id = node.id;
							describer = node.text;
							var content = describer;
							$("#describe1")
									.replaceWith(
											'<textarea  rows="4px" cols="22px" id="describe1" onclick="showDescModel()"  value="'
													+ content
													+ '">'
													+ content
													+ '</textarea>');
							$("#describeSelect").modal('hide');
						}

					});

	$("#describeSelect").modal('show');
}
