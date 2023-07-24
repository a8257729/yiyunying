<%@ page contentType="text/html; charset=utf-8" %>
<html><head>		

		<script type="text/javascript" src="js/chat.js"></script>
		<link rel="stylesheet" type="text/css" href="style/im.css" />	
		<link rel="stylesheet" type="text/css" href="/MOBILE/ext/resources/css/ext-all.css" />
		<script type="text/javascript" src="/MOBILE/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/MOBILE/ext/ext-all.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>聊天窗口</title>
		
	</head> 
	<body ><div id='chat'></div>
	</body>
</html>
<script>
/** by tang.lin 2010-11-5 聊天**/
//全局变量定义
var opener = art.dialog.data('opener');//window.dialogArguments;//父窗体引用


var item = new Object();
var staffId = opener.imOper.item.id;
item.id = opener.imOper.item.id;
item.text = opener.imOper.item.text;

var name = opener.imOper.item.id;

var session1 = GetSession();

var swidth =400;//GetScreenWidth();//分辨率宽度


var sheight = 500;//GetScreenHeight();//分辨率高度


var chat = new Chat();
Ext.onReady(function(){
		//看是否已经打开窗口，对每个好友只能打开一个聊天窗口

		var chatWin = Ext.getCmp('chatWin_'+staffId);

		if(Ext.isEmpty(chatWin)){
			chat.mainChatWin();
		}
		//查看所有未读消息


		chat.getUnReadMsgForStaff();
		//设置消息状态为已读
		chat.updateReadState();
		//设置未读消息提醒
		// window.opener.imOper.openImMsgWin();
		opener.imOper.openImMsgWin();
});

		     
//聊天窗口退出是删除在主窗口保存的引用


window.onunload = function (){
		//判断是否是IM关闭
		if(!window.opener.closed){
			window.opener.imOper.removeWin(window.name);
		}
}		
	
</script>

