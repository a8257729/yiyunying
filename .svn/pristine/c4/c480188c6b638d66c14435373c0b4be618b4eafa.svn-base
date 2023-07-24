<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script type="text/javascript" src="../ext/ux/SearchField.js"></script>
		<script type="text/javascript" src="js/chatLog.js"></script>
		<link rel="stylesheet" type="text/css" href="style/im.css" />	
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>聊天记录查看</title>
	</head> 
	<body >
		<div id='chatLog'></div>
	</body>
</html>
<script>
/** by tang.lin 2010-11-9 聊天记录**/
//全局变量
var staffId = art.dialog.data('staffId');//window.dialogArguments;//父窗体引用

var session1 = GetSession();
var swidth =GetScreenWidth();//分辨率宽度

var sheight = GetScreenHeight();//分辨率高度

var chatLog = new ChatLog();

Ext.onReady(function(){
		var chatLogWin = Ext.getCmp('chatLogWin');
		if(Ext.isEmpty(chatLogWin)){
			//chatLogWin = chatLog.mainChatLogWin();
			//chatLogWin.show();
			chatLog.mainChatLogWin();
			chatLog.qryChatLog();
		}
		
});

</script>
