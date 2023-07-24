<%@ page contentType="text/html; charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath2 = request.getScheme()+"://"+request.getRemoteAddr()+":"+request.getServerPort()+"/";
%>
<!DOCTYPE html >
<html>
	<head>
		<title>移动服务平台</title>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<link href="css/main.css" rel="stylesheet" type="text/css" />
		<link href="css/themesetting.css" rel="stylesheet"  type="text/css" >
		<link href="css/skins/black.css" rel="stylesheet" />
		<link href="css/powerFloat.css" rel="stylesheet" type="text/css"/>	
		<link href="css/smartMenu.css" rel="stylesheet"  type="text/css" />
		<link href="css/gridDiv.css" rel="stylesheet"  type="text/css" />
		<script  type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="js/jquery-powerFloat-min.js"></script>
		<script  type="text/javascript" src="js/jquery-smartMenu.js"></script>
		<script  type="text/javascript" src="js/jquery-class.js"></script>
		<script  type="text/javascript" src="js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="js/iframeTools.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.core-min.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.widget-min.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.mouse-min.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.draggable-min.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.droppable-min.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.sortable.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.effect.js"></script>
		<script  type="text/javascript" src="js/jquery.ui.effect-fade.js"></script>
		<script  type="text/javascript" src="js/data.js"></script>
		<script  type="text/javascript" src="js/chat.js"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		

	</head>
	<body onload="Fkey();">
		<div  id="themeSetting_wrap" style="display:none;">			
				<div id="themeSetting_head" class="themeSetting_head">		
					<div id="themeSetting_tabTheme" class="themeSetting_tab current" style="display: block;">系统主题</div>		
				</div>					
				<div id="themeSetting_body" class="themeSetting_body">	
					<div id="themeSetting_area"  class="themeSetting_area" style="display: block;">						 
					<a href="###" themeid="theme_e_biz" class="themeSetting_settingButton" id="themeSetting_theme_e_biz">
						<div style="background: url(images/bg/icon/theme_e_biz.jpg) no-repeat;" class="themeSetting_settingButton_icon"></div>
						<div class="themeSetting_settingButton_text">主题1</div>
					</a>
					<a href="###" themeid="theme_e_biz1" class="themeSetting_settingButton" id="themeSetting_theme_e_biz1">
						<div style="background: url(images/bg/icon/theme_e_biz1.jpg) no-repeat;" class="themeSetting_settingButton_icon"></div>
						<div class="themeSetting_settingButton_text">主题2</div>
					</a>            
					<a href="###" themeid="theme_e_biz2" class="themeSetting_settingButton" id="themeSetting_theme_e_biz2">
						<div style="background: url(images/bg/icon/theme_e_biz2.jpg) no-repeat;" class="themeSetting_settingButton_icon"></div>
						<div class="themeSetting_settingButton_text">主题3</div>
					</a>  
					<a href="###" themeid="theme_e_biz3" class="themeSetting_settingButton" id="themeSetting_theme_e_biz3">
						<div style="background: url(images/bg/icon/theme_e_biz3.jpg) no-repeat;" class="themeSetting_settingButton_icon"></div>
						<div class="themeSetting_settingButton_text">主题4</div>
					</a>              
					</div>						
					<div id="themeSetting_wallpaper" class="themeSetting_wallpaper" style="display: none;"></div>			
				</div>
				<div id="themeSetting_head" class="themeSetting_head" style="margin:5px 0px 0px 0px">		
					<div id="themeSetting_tabTheme" class="themeSetting_tab current" style="display: block;">系统协助</div>		
				</div>
				<div id="themeSetting_body" class="themeSetting_body1">
					<a href="###" class="themeSetting_settingIcon" id="site_search">
						<div style="background: url(icon/desktopshare.png) no-repeat;" class="themeSetting_settingIcon_icon"></div>
						<div class="themeSetting_settingButton_text">站内搜索</div>
					</a> 
					<a href="###" class="themeSetting_settingIcon" id="person_info_change">
						<div style="background: url(icon/desktopshare.png) no-repeat;" class="themeSetting_settingIcon_icon"></div>
						<div class="themeSetting_settingButton_text">个人信息修改</div>
					</a>
					<a href="###" class="themeSetting_settingIcon" id="password_change">
						<div style="background: url(icon/desktopshare.png) no-repeat;" class="themeSetting_settingIcon_icon"></div>
						<div class="themeSetting_settingButton_text">密码修改</div>
					</a> 
				</div>
				<div id="themeSetting_head" class="themeSetting_head" style="margin:5px 0px 0px 0px">		
					<div id="themeSetting_tabTheme" class="themeSetting_tab current" style="display: block;">系统帮助</div>		
				</div>
				<div id="themeSetting_body" class="themeSetting_body2">	
					<a href="###" class="themeSetting_settingLink" id="qq_service">
						<div class="themeSetting_settingButton_text">QQ客服</div>
					</a> 
					<a href="###" class="themeSetting_settingLink" id="link_number">
						<div class="themeSetting_settingButton_text">联系电话</div>
					</a>
				</div>
		</div>
		
		<div id="zoomWallpaperGrid" class="zoomWallpaperGrid" style="position: absolute; z-index: -10; left: 0pt; top: 0pt; overflow: hidden; height: 100%; width: 100%;">
			<img id="zoomWallpaper" class="zoomWallpaper" style="position: absolute; top: 0pt; left: 0pt; height: 100%; width: 100%;" src="images/bg/e_biz2.jpg">
		</div>
		<iframe id="f11" src="" style="display: none;"></iframe>
		<div id="secondMenuMorePanel" class="secondMenuMorePanel" style="display: none;">
			<div class='boxUnderWrapper_header' style="float:left"></div>
			<div  id='navbar2more' class='navbar2more' style="float:left"></div>
			<div class='boxUnderWrapper_tail' style="float:left"></div>
		</div>
	</body>
</html>
<script language="javascript"><%@ include file="js/webos-core.js"%></script>
<script language="javascript"><%@ include file="js/mainOper.js"%></script>
<script language="javascript">
window.ctxPath = "<%=basePath %>";
function Fkey(){
	//document.getElementById('f11').src='fullscreen.jsp';
	//Navbar.overlapFix();//修复桌面图标重叠bug
}
</script>