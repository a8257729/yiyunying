<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>在线人员列表</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>

		<script type="text/javascript" src="js/pnUserConfig.js"></script>
		<script type="text/javascript" src="js/pnUserMgr.js"></script>

	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	 	<div id='pn_user_vp'></div>
	</body>
</html>

<script type="text/javascript">
	var swidth = GetScreenWidth();
	var sheight = GetScreenHeight();
	var session1 = GetSession();

	Ext.onReady(function(){

		//创建全局Panel
		PnUserGridsPanel.init();

	});
</script>