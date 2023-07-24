<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>消息推送管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>

		<script type="text/javascript" src="js/pushmsgConfig.js"></script>
		<script type="text/javascript" src="js/pushmsgMgr.js"></script>
		<script type="text/javascript" src="js/pnUserMgr.js"></script>
		<script type="text/javascript" src="js/pnUserConfig.js"></script>

	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	 	<div id='push_msg_vp'></div>
	</body>
</html>

<script type="text/javascript">
	var swidth = GetScreenWidth();
	var sheight = GetScreenHeight();
	var session1 = GetSession();

	Ext.onReady(function(){

		//创建全局Panel
		PushMessageGridsPanel.init();

	});
</script>