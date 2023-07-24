var requestParam = {};
var commonInfo = {};
var mark = "";
var snParam = {};
var sn_btFlag = false;
var code_btFlag = false;
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
		$("#code_bt").replaceWith(
				'<button onclick="openModel(2)" id="code_bt">扫码</button>');
	}
	if (browser.versions.android) {
		$("#sn_bt").replaceWith(
				'<button onclick="openModelAndroid(1)" id="sn_bt">扫码</button>');
		$("#code_bt")
				.replaceWith(
						'<button onclick="openModelAndroid(2)" id="code_bt">扫码</button>');
	}

	// 从URL中获取参数
	requestParam = GetURLRequestParam();
}


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
						'<input id="input_SN" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'
								+ result + '"/>');
		sn_btFlag = false;
	} else {
		$("#input_code")
				.replaceWith(
						'<input id="input_code" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'
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
						'<input id="input_code" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'
								+ code + '"/>');
	} else {
		$("#input_SN")
				.replaceWith(
						'<input id="input_SN" style="border-radius: 10px;border: solid #B2DFEE; background-color:#F0F8FF ;" value="'
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

function submitTerminalInfo() {
	debugger;
	// 显示遮罩
	var qrCode = $("#input_SN").val();
	//第三位，a或者b代表obd标签，c代表皮线标签.不区分大小写
    if(qrCode == ''){
    	showFailMessage('设备标签为空!');
    	return;
    }else if(qrCode.length < 3){
    	showFailMessage('设备标签错误!');
    	return;
    }
    else{
    	var markStr = qrCode.substring(2,3).toUpperCase();
    	if(markStr == 'A' || markStr == 'B'){
    		//标签为obd标签
    		location.href = "resourceInfo.html?qrCode=" + qrCode;
    	}else if(markStr == 'C'){
    		//标签为端口标签
    		location.href = "userDetail.html?qrCode=" + qrCode;
    	}else{
    		showFailMessage('设备标签错误!');
    	}
    }
	
}

function callBack() {
	window.history.back(-1);
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
