<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>消息分类管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script type="text/javascript" src="js/pushMsgClassMgr.js"></script>
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
var pushMsgClassOper = new PushMsgClassOper();
var session1 = GetSession();
Ext.onReady(function(){

	var pushMsgClassGrid = oper.initMenuGrid();
	var pushMsgClassPanel = oper.initInfoPanel();
	oper.initGridMenu();
	oper.initGridEvent();
	
	
	
	var otherPanel = new Ext.Panel({
		border: false,
		title: '消息分类管理',
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
	        	'pushMessageClassId', 'className', 
	        	'createTime', 'staffId','staffName','classType', 'source',
	        	'memo', 'state', 'stateDate'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelPushMessageClassAction'
	        }),
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'标识ID',dataIndex:'pushMessageClassId',hidden:true},
		    {header:'分类名称',dataIndex:'className',width:200},
		    {header:'创建人ID',dataIndex:'staffId',hidden:true},
		    {header:'创建人',dataIndex:'staffName',width:200},
		    {header:'创建时间',dataIndex:'createTime',width:200},
		    {header:'消息来源',dataIndex:'source',width:200},
		    {header:'备注',dataIndex:'memo',width:400}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.7,
		    title:'消息分类列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	//Ext.getCmp("p_pushMessageClassId").setValue(rec.data.pushMessageClassId);
		            	Ext.getCmp("p_className").setValue(rec.data.className);
		            	Ext.getCmp("p_createTime").setValue(rec.data.createTime);
		            	Ext.getCmp("p_staffName").setValue(rec.data.staffName);
		            	Ext.getCmp("p_source").setValue(rec.data.source);
		            	Ext.getCmp("p_memo").setValue(rec.data.memo);
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
	        title: '分类详细信息',
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
	                    fieldLabel: '分类名称',
	                    labelStyle: "text-align: right;",
	                    name: 'p_className',
	                    id: 'p_className',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '创建人',
	                    labelStyle: "text-align: right;",
	                    name: 'p_staffName',
	                    id: 'p_staffName',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '创建时间',
	                    labelStyle: "text-align: right;",
	                    name: 'p_createTime',
	                    id: 'p_createTime',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '消息来源',
	                    labelStyle: "text-align: right;",
	                    name: 'p_source',
	                    id: 'p_source',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '备注',
	                    labelStyle: "text-align: right;",
	                    name: 'p_memo',
	                    id: 'p_memo',
	                    anchor:'100%'
	                }]
	            }]
	        }]
	    });
		return infoPanel1;	    
	}
	
	//定义APK管理列表菜单
    this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义APK管理列表事件
	this.initGridEvent = function(){
		Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load();
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//APK管理组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加分类' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改分类' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除分类' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', pushMsgClassOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加分类' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.menuAdd = function(){
    	pushMsgClassOper.showMenuInfoPage('I');
    }
    
    this.menuMod = function(){
    	pushMsgClassOper.showMenuInfoPage('U');
    }

}
</script>
