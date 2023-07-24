var requestParam = {};
var commonInfo = {};
function init() {
	debugger;
	$("#loadingModal").modal('show');
	// 解析url参数
	requestParam = GetURLRequestParam();
	// 获取window.name参数
//	commonInfo = parseWindowName();
	var qrCode = requestParam.qrCode;
	console.log("qrCode:" + qrCode);
	var oltIP = "";
	var oltName = "";
	var pon = "";

	queryResourceInfo(qrCode);
	

}

function queryResourceInfo(qrCode) {
	var param = {
		qrCode : qrCode
	};
	
	$
			.ajax({
				type : 'POST',
				url : '/MOBILE/api/client/resource/resourceInfo/queryResourceInfo',
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json",
				success : function(result) {
					debugger;
					var resultCode = result.resultCode;
					if (resultCode == '1000' && result) {
						if (!$.isEmptyObject(result.resultData.data_info)) {
							console.log(result.resultData.data_info);

							// 页面展示数据
							var splitterName = result.resultData.data_info.splitterName;
							oltIP = result.resultData.data_info.oltIP;
							pon = result.resultData.data_info.pon;
							oltName = result.resultData.data_info.oltName;
							var workMode = result.resultData.data_info.workMode;
							var protectStyle = result.resultData.data_info.protectStyle;
							var qrCode = result.resultData.data_info.qrCode;
							// resultMap.put("posX", posX);
							// resultMap.put("posY", posY);

							$("#splitterName").val(splitterName);
							$("#oltIP").val(oltIP);
							$("#oltName").val(oltName);
							$("#pon").val(pon);

							$("#workMode").val(workMode);
							$("#protectStyle").val(protectStyle);
							$("#qrCode").val(pon);
							var ponPortList = result.resultData.data_info.ponPortList;
							var ponPortHtml = $("#ponPortList");
							ponPortHtml.html("");
							var j =1;
							if (ponPortList && ponPortList.length > 0) {
								for (var i = 0; i < ponPortList.length; i++) {
									var portName = ponPortList[i].portName;
									var state = ponPortList[i].portState;
									var portId = ponPortList[i].portId;
									var serviceNum = ponPortList[i].serviceNum==null?'':ponPortList[i].serviceNum;
									var custName = ponPortList[i].custName==null?'':ponPortList[i].custName;
									var custAddr = ponPortList[i].custAddr==null?'':ponPortList[i].custAddr;
									var position = ponPortList[i].position==null?'':ponPortList[i].position;
									var portButton = '';
									if (state == '占用' || state == '预占') {
										portButton = '<li class="mui-table-view-cell" portid="'
												+ portId
												+ '" onclick="toUserDetail(this);">'
												+ '	<div class="mui-table">                                                             '
												+ '		<span style="float: left; color: #0480BE;">'
												+ custName
												+ '</span>                    '
												+ '		<button                                                                           '
												+ '			style="float: right; background-color: #F0CA45; color: white;">端口'
												+ position
												+ '</button>   '
												+ '	</div>                                                                              '
												+ '	<div class="mui-table">                                                             '
												+ '		<span>'
												+ serviceNum
												+ '</span>                                                         '
												+ '	</div>                                                                              '
												+ '	<div class="mui-table">                                                             '
												+ '		<span>'
												+ custAddr+ '</span>'
												+ '	</div>                                                                              '
												+ '</li>                                                                                ';
									}
									ponPortHtml.append($(portButton));
								}

							}
							// 关闭遮罩
							$("#loadingModal").modal("hide");
						} else {
							// 关闭遮罩
							$("#loadingModal").modal("hide");
							showFailMessage('资源查询失败');
						}
					} else {
						// 关闭遮罩
						$("#loadingModal").modal("hide");
						showFailMessage('资源查询失败');
					}

				},
				error : function(result) {
					/* 错误信息处理 */
					debugger;
					// 关闭遮罩
					$("#loadingModal").modal("hide");
					showFailMessage('数据初始化失败');
				}

			});

}

function callBack() {
	window.history.back(-1);
}

function closeVaidateMessage() {
	$('#validate').modal('hide');
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

function toUserDetail(ele) {
	debugger;
	console.log("查询端口id为" + $(ele).attr("portid") + "的用户详情");
	var portId = $(ele).attr("portid");
	location.href =  "userDetail.html?portId=" + portId ;

}
