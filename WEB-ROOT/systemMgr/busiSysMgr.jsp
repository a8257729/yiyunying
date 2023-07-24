<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>业务系统管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script type="text/javascript" src="js/busiSysMgr.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
			<div id='menuMngDiv'></div>				
	</body>
</html>

<script language="JScript">

Ext.apply(Ext.form.VTypes, {
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});
var oper = new Oper();
var busiSysOper = new BusiSysOper();
var session1 = GetSession();
Ext.onReady(function(){

	var pushMsgClassGrid = oper.initMenuGrid();
	var pushMsgClassPanel = oper.initInfoPanel();
	oper.initGridMenu();
	oper.initGridEvent();
	
	var otherPanel = new Ext.Panel({
		border: false,
		title: '业务系统管理',
		region: 'center',
		layout: 'border',
		items:[pushMsgClassGrid, pushMsgClassPanel]
	}); 
	
	var tabs = new Ext.TabPanel({
		region: 'center',
		id : 'tabs',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.85,   
	    height:Ext.getBody().getSize().height*0.8, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[otherPanel]
	});
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[tabs]
	});

});

function Oper(){
	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
            baseParams:{"optype":"ALL","pagin":"N"},
            remoteSort: true,
	        fields: [ 
	        	'busiSysId', 'sysFieldType', 
	        	'sysName', 'sysProvider','sysAddr','buildTime', 'updateTime'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelBusiSysQueryAction'
	        }),
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'标识ID',dataIndex:'busiSysId',hidden:true},
		   	{header:'系统域类型',dataIndex:'sysFieldType',width:200},
		   	{header:'系统名称',dataIndex:'sysName',width:200},
		    {header:'系统提供商',dataIndex:'sysProvider',width:200},
		    {header:'系统地址',dataIndex:'sysAddr',width:200},
		    {header:'创建时间',dataIndex:'buildTime',width:200},
		    {header:'修改时间',dataIndex:'updateTime',width:200}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.7,
		    title:'业务系统列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	Ext.getCmp("p_sysName").setValue(rec.data.sysName);
		            	Ext.getCmp("p_sysFieldType").setValue(rec.data.sysFieldType);
		            	Ext.getCmp("p_sysProvider").setValue(rec.data.sysProvider);
		            	Ext.getCmp("p_sysAddr").setValue(rec.data.sysAddr);
		            	Ext.getCmp("p_buildTime").setValue(rec.data.buildTime);
		            	Ext.getCmp("p_updateTime").setValue(rec.data.updateTime);
		            }
		        }
		    })
	    });
		return menuGrid;
	}
	
	this.initInfoPanel = function(){
		var infoPanel1 = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: '业务系统信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.3,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.4,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '系统域类型',
	                    labelStyle: "text-align: right;",
	                    name: 'p_sysFieldType',
	                    id: 'p_sysFieldType',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '创建时间',
	                    labelStyle: "text-align: right;",
	                    name: 'p_buildTime',
	                    id: 'p_buildTime',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '更新时间',
	                    labelStyle: "text-align: right;",
	                    name: 'p_updateTime',
	                    id: 'p_updateTime',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '系统名称',
	                    labelStyle: "text-align: right;",
	                    name: 'p_sysName',
	                    id: 'p_sysName',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '系统提供商',
	                    labelStyle: "text-align: right;",
	                    name: 'p_sysProvider',
	                    id: 'p_sysProvider',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '系统地址',
	                    labelStyle: "text-align: right;",
	                    name: 'p_sysAddr',
	                    id: 'p_sysAddr',
	                    anchor:'100%'
	                }]
	            }]
	        }]
	    });
		return infoPanel1;	    
	}
	
    this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	this.initGridEvent = function(){
		Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load();
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加业务系统' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改业务系统' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除业务系统' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', busiSysOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加业务系统' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    this.menuAdd = function(){
    	busiSysOper.showMenuInfoPage('I');
    }
    
    this.menuMod = function(){
    	busiSysOper.showMenuInfoPage('U');
    }

}
</script>
