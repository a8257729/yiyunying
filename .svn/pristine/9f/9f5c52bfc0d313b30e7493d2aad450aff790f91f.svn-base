var commonInfo = {};
var requestParam = {};
var qrCode = "";
var portId = "";
var pxCode = "";
var staffId = "";

function init() {
	debugger;
	var browser = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { // 移动终端浏览器版本信息
				ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
				android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或uc浏览器
				iPhone : u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
				iPad : u.indexOf('iPad') > -1, // 是否iPad
			};
		}(),
	}
	if (browser.versions.iPhone || browser.versions.iPad
			|| browser.versions.ios) {
		$("#sn_bt").replaceWith(
				'<button onclick="openModel(1)" id="sn_bt">扫码</button>');
		$("#code_bt").replaceWith('<button onclick="openModel(2)" id="code_bt">扫码</button>');
	}
	if (browser.versions.android) {
		$("#sn_bt").replaceWith(
				'<button onclick="openModelAndroid(1)" id="sn_bt">扫码</button>');
		$("#code_bt").replaceWith('<button onclick="openModelAndroid(2)" id="code_bt">扫码</button>');
	}

	requestParam = {};
	commonInfo = parseWindowName();
	requestParam = GetURLRequestParam();
	staffId = commonInfo.ktdb.staffId;
	console.log("staffId:" + staffId);
	initResourceData();
//	makeProcessDataQuery();
}

function submitQrCode() {
	qrCode = $("#input_SN").val();
	if (qrCode == '') {
		showFailMessage('二维码为空');
		return;
	}
	initResourceData('submit');
	
}

function initResourceData(type) {
	debugger;
	qrCode = $("#input_SN").val();
	// 显示遮罩
	$("#loadingModal").modal('show');
	var param = {
		qrCode : qrCode,
		workOrderId : commonInfo.ktdb.WorkOrderID
	}
	$
			.ajax({
				type : 'POST',
				url : '/MOBILE/api/client/xj/web/kt/qrcodeInstall/query',
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json",
				success : function(result) {
					debugger;
					var resultCode = result.resultCode;
					if (resultCode == '1000' && result) {
						if (!$.isEmptyObject(result.resultData.data_info)) {
							console.log(result.resultData.data_info);
							// 点确认提交时需要传给后台
//							var port_name = result.resultData.data_info.portName;
//							var eqp_name = result.resultData.data_info.eqpName;
//							var pon_name = result.resultData.data_info.ponName;
							requestParam = {
								eqp_id : result.resultData.data_info.eqpId,
								// port_id : result.resultData.data_info.portId,
								pon_id : result.resultData.data_info.ponId
							}

							// 页面展示数据
							var splitterName = result.resultData.data_info.splitterName;
							var oltIP = result.resultData.data_info.oltIP;
							var pon = result.resultData.data_info.pon;
							var oltName = result.resultData.data_info.oltName;
							var resutlQrCode = result.resultData.data_info.qrCode;
							var address = result.resultData.data_info.address;
							var standName = result.resultData.data_info.standName;
							if(type == 'submit'){
								//表示扫码之后提交校验，如果是进入页面初始化查询，就不需要校验
								if(splitterName == null || splitterName == ''){
									$("#loadingModal").modal('hide');
									showFailMessage("二维码不存在");
									return;
								}
							}
							if(resutlQrCode != null && resutlQrCode != ''){
								qrCode = resutlQrCode;
								$("#input_SN").val(qrCode);
								$("#sn_bt").attr("disabled","true");
								$("#sn_ok").attr("disabled","true");
								$("#save").attr("disabled","true");
								portId = result.resultData.data_info.portId;
								pxCode = result.resultData.data_info.digCode;
								if(pxCode != null && pxCode != '') {
									$("#input_code").val(pxCode);
								}
								if(portId != null && portId != ''){
								  $("#pixian").show();
								}
								makeProcessDataQuery();
							}
							$("#splitterName").val(splitterName);
							$("#oltIP").val(oltIP);
							$("#oltName").val(oltName);
							$("#pon").val(pon);
							$("#address").val(address);
							$("#standName").val(standName);
							var ponPortList = result.resultData.data_info.ponPortList;
							var ponPortHtml = $("#ponPortList");
							ponPortHtml.html("");
							if (ponPortList && ponPortList.length > 0) {
								for (var i = 0; i < ponPortList.length; i++) {
									var portName = ponPortList[i].portName;
									var state = ponPortList[i].state;
									var portButton = '';
									if (state == '空闲') {
										portButton = '<span port_id="'
												+ ponPortList[i].portId
												+ '" class="mui-badge mui-badge-success" onclick="selectPort(this);">'
												+ portName + '</span>';
									} else {
										portButton = '<span class="mui-badge">'
												+ portName + '</span>';
									}
									ponPortHtml.append($(portButton));
								}

							}
							// 关闭遮罩
							$("#loadingModal").modal('hide');
						} else {
							// 关闭遮罩
							$("#loadingModal").modal('hide');
							showFailMessage('资源查询失败');
						}
					} else {
						// 关闭遮罩
						$("#loadingModal").modal('hide');
						showFailMessage('资源查询失败');
					}

				},
				error : function(result) {
					/* 错误信息处理 */
					debugger;
					// 关闭遮罩
					$("#loadingModal").modal('hide');
					showFailMessage('数据初始化失败');
				}

			});

}

// 制作数据查询
function makeProcessDataQuery(showLoadingFlag) {
	// 显示遮罩
	if (showLoadingFlag) {
		$("#loadingModal").modal('show');
	}
	var param = {

		// wk_order_id:commonInfo.ktdb.WorkOrderID
		workOrderId : commonInfo.ktdb.WorkOrderID
	}
	$
			.ajax({
				type : 'POST',
				url : '/MOBILE/api/client/xj/web/kt/smMakedata/query',
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json",
				success : function(result) {
					debugger;
					// 关闭遮罩
					if (showLoadingFlag) {
						$("#loadingModal").modal('hide');
					}
					var resultCode = result.resultCode;
					if (resultCode == '1000' && result) {
						$("#detailContent").empty();
						// 制作数据查询显示
						var time = result.resultData.progress_info.startDate;
						var progress1 = result.resultData.progress_info.progress1;
						var progress2 = result.resultData.progress_info.progress2;
						var progress3 = result.resultData.progress_info.progress3;
						var progress4 = result.resultData.progress_info.progress4;
						var progress5 = result.resultData.progress_info.progress5;
						var progress6 = result.resultData.progress_info.progress6;
						var progress7 = result.resultData.progress_info.progress7;
						var progress8 = result.resultData.progress_info.progress8;
						var progress9 = result.resultData.progress_info.progress9;
						var progress10 = result.resultData.progress_info.progress10;
						var endTime = result.resultData.progress_info.endDate;

						if ($.trim(time) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row" style="height:40px;"><span style="line-height:40px;height:40px;">扫码装机开始时间:'
									+ time + '</span></div>')
							$("#detailContent").append(node)
						}
						if ($.trim(progress1) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">'
									+ progress1 + '</span></div>')
							$("#detailContent").append(node)
						}

						if ($.trim(progress2) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">'
									+ progress2 + '</span></div>')
							$("#detailContent").append(node)
						}
						if ($.trim(progress3) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">'
									+ progress3 + '</span></div>')
							$("#detailContent").append(node)
						}
						if ($.trim(progress4) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">'
									+ progress4 + '</span></div>')
							$("#detailContent").append(node)
						}
						if ($.trim(progress5) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">'
									+ progress5 + '</span></div>')
							$("#detailContent").append(node)
						}
						if ($.trim(endTime) != "") {
							var node = $('<div id="detailArea2" class="mui-input-row"><span style="line-height:40px;height:40px;">扫码装机结束时间:'
									+ endTime + '</span></div>')
							$("#detailContent").append(node)
						}

					} else {
						// 提示失败原因返回到详情

						if (result.resultMsg == "" || result.resultMsg == null) {

							showMessage('2', '数据提交失败');
						} else {
							showMessage('2', result.resultMsg);
						}

					}

				},
				error : function(result) {
					/* 错误信息处理 */
					debugger;
					// 关闭遮罩
					if (showLoadingFlag) {
						$("#loadingModal").modal('hide');
					}
				}

			});

}

// 制作数据
function makeProcessData() {
	// 显示遮罩
	$("#loadingModal").modal('show');
	debugger;
	// 没有选择设备部执行制作数据
	if ($.isEmptyObject(requestParam)) {
		// 关闭遮罩
		$("#loadingModal").modal('hide');
		return;

	}

	var hgu_sn = "";// 不传

	var stb_mac = "";// 不传
	var eqp_id = requestParam.eqp_id;
	qrCode = $("#input_SN").val();
	if(qrCode == null || qrCode == ''){
		showFailMessage("二维码为空！");
		$("#loadingModal").modal('hide');
		return;
	}
	if (portId == '') {
		showFailMessage("请先选择端口！");
		$("#loadingModal").modal('hide');
		return;
	}
	
	var wk_order_id = commonInfo.ktdb.WorkOrderID;
	var pon_id = requestParam.pon_id;
	var staff_id = commonInfo.ktdb.staffId;
	var staff_name = commonInfo.ktdb.STAFF_NAME;
	var orderId = commonInfo.ktdb.orderId;
	// alert(wk_order_id);

	var param = {
		hgu_sn : qrCode,
		stb_mac : stb_mac,
		eqp_id : eqp_id,
		port_id : portId,
		wk_order_id : wk_order_id,
		pon_id : pon_id,
		staff_id : staff_id,
		staff_name : staff_name,
		orderId: orderId

	}
	$.ajax({
		type : 'POST',
		url : '/MOBILE/api/client/xj/web/kt/qrcodeInstall/submit',
		data : JSON.stringify(param),
		dataType : "json",
		contentType : "application/json",
		success : function(result) {
			debugger;
			var resultCode = result.resultCode;
			if (resultCode == '1000' && result) {
				$("#loadingModal").modal('hide');
				if(result.resultData.makeDataFlag != '1'){
					if(result.resultData.flag_desc != null && result.resultData.flag_desc != ''){
					    showFailMessage(result.resultData.flag_desc);
					}else{
						showFailMessage("扫码装机失败");
					}
				}
				// 制作数据查询
				if(resutlQrCode != null && resutlQrCode != ''){
					makeProcessDataQuery();
				}
			} else {
				// 关闭遮罩
				$("#loadingModal").modal('hide');
				// 提示失败原因返回到详情
				if (result.resultMsg == "" || result.resultMsg == null) {

					showFailMessage('数据提交失败');
				} else {
					showFailMessage(result.resultMsg);
				}

			}

		},
		error : function(result) {
			/* 错误信息处理 */
			debugger;
			// 关闭遮罩
			$("#loadingModal").modal('hide');
		}

	});

}

function submitPxCode(){
	debugger;
	pxCode = $("#input_code").val();
	// 显示遮罩
	$("#loadingModal").modal('show');
	var param = {
		pxCode : pxCode,
		portId : portId,
		staffId: staffId
	}
	$
			.ajax({
				type : 'POST',
				url : '/MOBILE/api/client/xj/web/kt/qrcodeInstall/pxCodeBind',
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json",
				success : function(result) {
					debugger;
					var resultCode = result.resultCode;
					if (resultCode == '1000' && result) {
						if (result.resultData.data_info.resultCode == '1') {
							showSuccessMessage("绑定成功！");
							// 关闭遮罩
							$("#loadingModal").modal('hide');
						} else {
							// 关闭遮罩
							$("#loadingModal").modal('hide');
							showFailMessage(result.resultData.data_info.resultMsg);
						}
					} else {
						// 关闭遮罩
						$("#loadingModal").modal('hide');
						showFailMessage(result.resultMsg);
					}

				},
				error : function(result) {
					/* 错误信息处理 */
					debugger;
					// 关闭遮罩
					$("#loadingModal").modal('hide');
					showFailMessage('绑定失败！');
				}

			});

}


function selectPort(node) {
	var available = document
			.getElementsByClassName("mui-badge mui-badge-warning select");
	for (var i = 0; i < available.length; i++) {
		$(available[i]).attr("class", "mui-badge mui-badge-success select");
	}

	$(node).attr("class", "mui-badge mui-badge-warning select");
	portId = $(node).attr("port_id");
}

function showFailMessage(message) {
	$('#validate_img').replaceWith(
			" <img src='image/yjzd_icon3.png' id='validate_img'/>");
	$('#validate_message').empty();
	var node = $('<label id="model-message">' + message + '</label>');
	$('#validate_message').append(node);
	$('#validate_button')
			.replaceWith(
					'<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick="closeVaidateMessage();")>确定</button>');
	$('#validate').modal('show');
}
function showSuccessMessage(message){
	$('#validate_message').empty();
	$('#validate_img').replaceWith(
	" <img src='image/msgbox_true.png' id='validate_img'/>");
	var node = $('<label id="model-message">' + message + '</label>');
	$('#validate_message').append(node);
	$('#validate_button')
			.replaceWith(
					'<button type="button" id="validate_button" class="btn btn-default" data-dismiss="modal" onclick="closeVaidateMessage();")>确定</button>');
	$('#validate').modal('show');
}

function closeVaidateMessage() {
	$('#validate').modal('hide');
}

var sn_btFlag = false;
var code_btFlag = false;
var mark = "";
var snParam = {};

function openModelAndroid(str) {

	if (str == 1) {
		sn_btFlag = true;
	}

	android.doScan();
}

function setScanResult(result) {

	if (sn_btFlag == true) {
		$("#input_SN")
				.replaceWith(
						'<input id="input_SN" style="font-size:10px; border-radius: 10px; width: 20%; border: solid #B2DFEE;line-height:24px; background-color: #F0F8FF;" value="'
								+ result + '"/>');
		sn_btFlag = false;
	} else {
		$("#input_code")
				.replaceWith(
						'<input id="input_code" style="font-size:10px; border-radius: 10px; width: 20%; border: solid #B2DFEE;line-height:24px; background-color: #F0F8FF;" value="'
								+ result + '"/>');

	}
};

// 获取IOS条形码
function startGetCode() {

	var parameter = {
		'title' : 'JS调OC',
		'describe' : '这里就是JS传给OC的参数'
	};
	var code = window.iosDelegate.startGetCode(JSON.stringify(parameter));
	if (mark != '1') {
		$("#input_code")
				.replaceWith(
						'<input id="input_code" style="font-size:10px; border-radius: 10px; width: 20%; border: solid #B2DFEE;line-height:24px; background-color: #F0F8FF;" value="'
								+ code + '"/>');
	} else {
		$("#input_SN")
				.replaceWith(
						'<input id="input_SN" style="font-size:10px; border-radius: 10px; width: 20%; border: solid #B2DFEE;line-height:24px; background-color: #F0F8FF;" value="'
								+ code + '"/>');
	}

}

function openModel(str) {
	debugger;
	mark = str;

	var parameter = {
		'title' : 'JS调OC',
		'describe' : '这里就是JS传给OC的参数'
	};
	// 调取IOS扫码功能
	window.iosDelegate.getImage(JSON.stringify(parameter));
	// startGetCode();

}
