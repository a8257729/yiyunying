var requestParam = {};
var commonInfo = {};
function init() {
	debugger;
	$("#loadingModal").modal('show');
	// 解析url参数
	requestParam = GetURLRequestParam();
	// 获取window.name参数
	var portId = requestParam.portId;
	var qrCode = requestParam.qrCode;
    if(!portId){
    	portId = '';
    }
    if(!qrCode){
    	qrCode = '';
    }
	console.log("查询的ponId:" + portId);
	console.log("查询的qrCode:" + qrCode);
	queryUserInfoInfo(portId,qrCode);

}

function queryUserInfoInfo(portId,qrCode) {
	var param = {
			portId : portId,
			qrCode: qrCode
	};
	
	$.ajax({
				type : 'POST',
				url : '/MOBILE/api/client/resource/resourceInfo/queryUserInfo',
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
							var qrCode = result.resultData.data_info.qrCode;
							var custName = result.resultData.data_info.custName;
							var serviceNum = result.resultData.data_info.serviceNum;
							var custAddr = result.resultData.data_info.custAddr;

							var oltIP = result.resultData.data_info.oltIP;
							var oltName= result.resultData.data_info.oltName;
						    var pon= result.resultData.data_info.pon;
							$("#custName").val(custName);
							$("#oltIP").val(oltIP);
							$("#oltName").val(oltName);
							$("#pon").val(pon);

							$("#serviceNum").val(serviceNum);
							$("#custAddr").val(custAddr);
							$("#qrCode").val(qrCode);
							
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

