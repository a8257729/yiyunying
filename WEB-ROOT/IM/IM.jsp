<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		
		<link rel="stylesheet" type="text/css" href="style/im.css" />
		<script type="text/javascript"  src="../IM/js/ajax-pushlet-client.js"></script>
		<script type="text/javascript"	src="../IM/js/imUI.js"></script>	
		<script type="text/javascript"	src="../IM/js/imOper.js"></script>
		<script type="text/javascript"	src="../IM/js/imGroupOper.js"></script>
		<script type="text/javascript"  src="../IM/js/IMSearchField.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<style>
		
			</style>
		<title>ZTESOFT IM</title>
	
	</head> 
	<body >
		<div id='im'></div>
	</body>
</html>
<script>
/** by tang.lin 2010-10-29 IM即时聊天 **/
//全局变量定义	
//var opener = window.dialogArguments;//父窗体引用


var session1 = GetSession();//当前用户session
var swidth =GetScreenWidth();//分辨率宽度


var sheight = GetScreenHeight();//分辨率高度


var userInfoImg = '../IM/image/IM_logo.gif';//用户信息面板背景图


var imOper = new ImOper()//IM操作类


var imUI = new ImUI();//IM界面
var imGroupOper = new ImGroupOper();
var isClosed = false;
Ext.onReady(function(){
		Ext.QuickTips.init();
		//初始化im窗口
		var imWin = imUI.initImWin();
		
		imOper.initOper();
		//展开好友树形节点
        Ext.getCmp('groupTree').getRootNode().expand(true);
        Ext.getCmp('onlineList').getRootNode().expand(true);
});
function onEvent(event){
		if(event.arr.p_event=='leave-ack'){
			isClosed = true;
			//window.close();
			art.dialog.close();
		}
		
}
function onData(event){
		imOper.onData(event);
}
window.onunload = function (){
		//opener.isClosed = false;

		//通知服务器IM下线
		imOper.leaveLine();
		//关闭所有子窗口
		//for(var i=0;i<imOper.chatWins.length;i++){
		//	imOper.chatWins[i].close();
		//}
		Ext.Ajax.request({
		   url: '/MOBILE/ExtPagingServlet?actionName=LogoutIMAction',
		   success: function(){
		   		window.close();
		   },
		   failure: function(){
		   		alert("注销IM失败");
		   },
		   sync:false,
		   autoAbort:false
		});
			
}
window.onbeforeunload = function(){
		//判断是否是父窗口关闭
		//if(!opener.isClosed){
		//	EventUtil.preventDefault(window.event,'离开此页面将退出IM,不需要退出可以选择最小化');
		//}
}

</script>