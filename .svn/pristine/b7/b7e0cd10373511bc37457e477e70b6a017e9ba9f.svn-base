<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>APK管理</title>
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
		<div id="menuMngDiv"></div>
	</body>
</html> 

<script language="JScript">

var oper = new Oper();
function Oper(){
	//APK管理列表初始化
	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelOtherApkManagerAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
				}
	        }
	    });
		
		//创建列'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'apk标识',dataIndex:'apkId',hidden:true },
		    {header:'备注',dataIndex:'comments',hidden:true },
		    {header:'apk编码',dataIndex:'apkCode',width:150 },
		    {header:'apk包名',dataIndex:'apkPackage',width:150},
		    {header:'版本号',dataIndex:'apkVersionNo',width:100 },
		    {header:'是否强制升级',dataIndex:'forceUpdate',width:100},
		    {header:'apk地址',dataIndex:'apkUrl',width:200},
		    {header:'创建时间',dataIndex:'createDate',width:150}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height,
		    title:'菜单列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			listeners: {
                "rowdblclick": function() {
                     var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections(); 
				    	window.returnValue = selItem;
	                    window.close();
                }
            }
	    });
		//menuStore.load({params:{moduleId:0}});//根据moduleId来载入
		return menuGrid;
	}
	
	this.initMenuGridLoad = function(){
		Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load();
	}
	
	this.initBtnPanel = function(){
	  var btnPanel = new Ext.FormPanel({
			id:'btnPanel',
	     	region: 'south',
		    border:false,
	        buttonAlign: 'center',
	        collapsible:false, 
	        height:Ext.getBody().getSize().height*0.08,
	        buttons: [{
		            text: '确定',
		            id:'submitBtn',
		            listeners:{"click":function(){
				    	var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections(); 
				    	window.returnValue = selItem;
	                    window.close();
				    }}
		        },{
		            text: '取消',
		             id:'cancelBtn',
			         listeners:{"click":function(){
				    	window.close();
				    }}
		        }]
	    	});
	    	return btnPanel;
    }
}


Ext.onReady(function(){

    var menuGrid = oper.initMenuGrid();
    oper.initMenuGridLoad();
    var btnPanel = oper.initBtnPanel();
    
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[menuGrid,btnPanel]
	});
});

</script>