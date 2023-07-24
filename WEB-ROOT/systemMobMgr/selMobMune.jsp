<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>移动菜单管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
	</head>
	<style type="text/css">
		.x-window-dlg .ext-mb-download {
		    background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
		    height:46px;
		}
	</style>
	<body onContextMenu="return false;"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="indicatorDiv"></div>
	</body>
</html>

<script language="JScript">

Ext.onReady(function(){
var moduleId = -1;
	var dataTree = new Ext.tree.TreePanel({
			id: 'dataTree',
	    	title:'移动菜单目录',
	    	region: 'center',
	    	
	        autoScroll:true,
	         loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/MobMuneTreeAction&type=model'
	        }),
	        containerScroll: true,
	        border: false,
	        width:Ext.getBody().getSize().width*0.2,
		    height:Ext.getBody().getSize().height,
	        rootVisible: true,
	        root: new Ext.tree.AsyncTreeNode({
	            id:'0',
	            text: '移动菜单目录'
	        })
	        
	    });
	    new Ext.tree.TreeSorter(dataTree, {folderSort:true});
	    
	 var btnPanel = new Ext.FormPanel({
		id:'btnPanel',
     	region: 'south',
	    border:false,
        buttonAlign: 'center',
        collapsible:false, 
        height:Ext.getBody().getSize().height*0.30,
        buttons: [{
	            text: '确定',
	            id:'submitBtn',
	            listeners:{"click":function(){
			    	
			    }}
	        },{
	            text: '取消',
	             id:'cancelBtn',
		         listeners:{"click":function(){
			    	
			    }}
	        }]
    	});
    
	    var viewport = new Ext.Viewport({
			el:'indicatorDiv',
			layout: 'border',
			items:[dataTree,btnPanel]
		});
	
	
});

</script>