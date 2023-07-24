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

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
this.initOuterSystemGrid = function(){
	var store = new Ext.data.JsonStore({
       	root: 'Body',
      	totalProperty: 'totalCount',
	    remoteSort: true,
        fields: [ 
       		'sysId', 'sysCode', 'sysName', 'sysRegionId','sysRegionName','sysIpAddress','sysAppAddress',
       		'sysIcon', 'sysDesc', 'companyId','companyName', 'remark','ssoTypeName','ssoTypeId'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
   	   	 }
   	 });
   	 //创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'系统ID',dataIndex:'sysId',hidden:true},
	    {header:'系统编码',dataIndex:'sysCode',hidden:true},
	    {header:'系统名称',dataIndex:'sysName',width:100},
	    {header:'系统所属域ID',dataIndex:'sysRegionId',hidden:true},
	    {header:'系统所属域',dataIndex:'sysRegionName',width:100},
	    {header:'系统IP地址',dataIndex:'sysIpAddress',width:100},
	    {header:'系统应用路径',dataIndex:'sysAppAddress',width:120},
	    {header:'单点登录类型',dataIndex:'ssoTypeName',width:100},
	    {header:'单点登录类型Id',dataIndex:'ssoTypeId',width:100,hidden:true},
	    {header:'系统图标路径',dataIndex:'sysIcon',width:80},
	    {header:'系统描述',dataIndex:'sysDesc',width:230},
	    {header:'厂家ID',dataIndex:'companyId',hidden:true},
	    {header:'厂家名称',dataIndex:'companyName',width:100},
	    {header:'备注',dataIndex:'remark',width:227}
	]);   
	column.menuDisabled=true;
	var pagingBar = new Ext.PagingToolbar({
		pageSize: 7,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	var gridView = new Ext.grid.GridView({
		sortAscText:'升序',
		sortDescText:'降序',
		columnsText:'列名'
	});
   	var grid = new Ext.grid.GridPanel({
		id:'outerSystemGrid',
        region: 'north',
        collapsible:true, 
        width:Ext.getBody().getSize().width,   
	    height:Ext.getBody().getSize().height*0.32,
	    title:'系统列表',
        store: store,
        //trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
        viewConfig : gridView,
		cm:column,
		bbar:pagingBar,
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true,
			listeners:{
				rowselect:function(sm,row,rec) {
                    oper.qryOtherSysData(rec.data.sysCode);      
                }
			}
		})
    });
    store.load({params:{start:0, limit:7}});
   	return grid;
   	
}//end
 
	  
    this.qryOtherSysData = function(sysCode){
			
			Ext.getCmp('otherSysGrid').store.on('beforeload',
					function(store){
						if(Ext.getCmp('otherSysGrid').store.lastOptions != null){
							
							Ext.getCmp('otherSysGrid').store.baseParams = {sysCode:sysCode};
						}
					}
			)
			Ext.getCmp('otherSysGrid').store.on('load',
					function(store){      
                    	Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(0);
					}
			)
			Ext.getCmp('otherSysGrid').store.removeAll() ;
			Ext.getCmp('otherSysGrid').store.load({params:{start:0, limit:10}});
	}
    
    
	
 /**
  * 系统接口地址表
  */
  
function renderInterfaceVersion(v){
	switch(v){
		case null:
			return '无';
			break;
		case '1':
			return 'axis 1.1';
			break;
		case '2': 
			return 'axis 1.2';
			break;
		case '3': 
			return 'xfire';
			break;
		default: 
			return 'error';
	}
}
function renderMethodType(v){
	switch(v){
		case '1':
			return '类名';
			break;
		case '2': 
			return '接口';
			break;
		case '3': 
			return 'servlet';
			break;
		default: 
			return 'error';
	}
}

//列表初始化

	this.initOtherSysGrid = function(){
		//创建列
		//'sysotherSysId', 'systemCode', 'methodType','methodotherSys','interfaceVersion','apkCode'
		var column = new Ext.grid.ColumnModel([
		    {header:'接口地址ID',dataIndex:'sysAddressId',hidden:true },	    
		    {header:'系统编码',dataIndex:'systemCode',hidden:true },
		    {header:'接口编码',dataIndex:'interfaceCode',width:100 },
		    {header:'方法类型',dataIndex:'methodType',width:100,renderer:function(v){return renderMethodType(v);}},
		    {header:'方法地址',dataIndex:'methodAddress',width:200 },
		    {header:'接口版本',dataIndex:'interfaceVersion',width:100,renderer:function(v){return renderInterfaceVersion(v);}},
		    {header:'APK编码',dataIndex:'apkCode',width:100 }	    
		    
		]);   
		
		var gridPanel = new ZTESOFT.Grid({
	    	id:'otherSysGrid',
	    	title : '系统接口地址表',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:10,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelOtherSysAction'
	        			
		});
		return gridPanel;
    }
    
    
    //接口映射配置
    ///////////////////////////////////////
    this.initInterfacePanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'接口映射ID',dataIndex:'mappingId',hidden:true },
		    {header:'系统接口ID',dataIndex:'sysAddressId',hidden:true },
		    {header:'服务编码',dataIndex:'mappingCode',width:150},    
		    {header:'映射名称',dataIndex:'mappingName',width:150 },
		    {header:'映射方法',dataIndex:'mappingMethod',width:150},
		    {header:'业务接口名称',dataIndex:'intefaceName',width:150},
		    {header:'业务接口方法',dataIndex:'intefaceMethod',width:150},
		    {header:'业务接口参数',dataIndex:'intefaceParams',width:150},
		    {header:'描述',dataIndex:'comments',width:150}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'interfaceGrid',
	    	title : '接口映射管理',
	    	region: 'south',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelMobInterfaceAction'
			
		});
		return gridPanel;
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
		            id:'submitBtn'
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
 
	this.initEvent = function(grid){
		switch(grid){
			case "interfaceGrid":
				oper.initInterfaceEvent();
				break;
    		case "otherSysGrid":
    			oper.initOtherSysEvent();
				break;
			case "outerSysGrid":
    			break;
		}
		oper.initBtnEvent(grid);
	}	
	this.initOtherSysEvent = function(){
		Ext.getCmp('otherSysGrid').on("rowdblclick", function() {
					var selItem = new Array();
                    selItem[0] = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections(); 
				    window.returnValue = selItem;
	                window.close();
                });
	}
	this.initInterfaceEvent = function(){
		Ext.getCmp('otherSysGrid').selModel = new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	oper.qryInterfaceGridData(rec.data.sysAddressId);
		            }
		        }
		    });
		Ext.getCmp('interfaceGrid').on("rowdblclick", function() {
					var selItem = new Array();
					selItem[0] = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections(); 
                    selItem[1] = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections(); 
				    window.returnValue = selItem;
	                window.close();
         		});
	}
	this.initBtnEvent = function(grid){
		Ext.getCmp('submitBtn').on("click", function() {
					var selItem = new Array();
                    selItem[0] = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections(); 
					if(grid == "interfaceGrid"){
						selItem[1] = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections(); 
					}
				    window.returnValue = selItem;
	                window.close();
                });
	}
	
    this.qryInterfaceGridData = function(sysAddressId){
			
			Ext.getCmp('interfaceGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('interfaceGrid').store.lastOptions != null){
							
							Ext.getCmp('interfaceGrid').store.baseParams = {sysAddressId:sysAddressId};
						}
					}
			)
			Ext.getCmp('interfaceGrid').store.on('load',
					function(store){      
                    	Ext.getCmp('interfaceGrid').getSelectionModel().selectRow(0);
					}
			)
			Ext.getCmp('interfaceGrid').store.removeAll() ;
			Ext.getCmp('interfaceGrid').store.load({params:{start:0, limit:10}});
		
	}
}


Ext.onReady(function(){

	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border'
	}); 
	
	var interfaceGrid = oper.initInterfacePanel();
	var otherSysGrid = oper.initOtherSysGrid();
	var outerSysGrid = oper.initOuterSystemGrid();
	
    var grid = window.dialogArguments;
    switch(grid){
    	case "interfaceGrid":
			earthviewport.add(interfaceGrid);
    	case "otherSysGrid":
			earthviewport.add(otherSysGrid);
		case "outerSysGrid":
    		earthviewport.add(outerSysGrid);
    }
    var btn = oper.initBtnPanel();
    oper.initEvent(grid);
    
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[earthviewport,btn]
	});
});

</script>