<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>终端框架应用管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/mobileFrameAppMng.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
			<div id='menuMngDiv'></div>				
	</body>
</html>

<script language="JScript">

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();

var oper = new Oper();
Ext.onReady(function(){

	var mainPanel = oper.initMainPanel();
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			items:[mainPanel]
	});
	
});

function Oper(){

	this.initMainPanel = function(){
		
		var downloadChartPanel = new Ext.Panel({
		    id:'flowChartPanel',
			border: false,
			title: '应用下载统计图',
			/*region: 'center',
			layout: 'border',*/
			 html : '<iframe id="flowChart" src="/MOBILE/api/busi/stat/shanghai/download_stat.html" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
		});
                		 				
		 var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[downloadChartPanel],
		    		    listeners: {   }
		});
		
		var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[tabs]
	 	});
	 	return mainPanel;
	}
}

</script>
