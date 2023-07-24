<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用更新</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
	    <script language="javascript" src="js/apkClass.js"></script>
	    <script language="javascript" src="js/apkReg.js"></script>

		
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
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var rightClick2;
var nullRightClick2;
var session1 = GetSession();
var staffId = session1.staff.staffId ;
var oper = new Oper();
var regOper = new RegOper();
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
Ext.apply(Ext.form.VTypes, {
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

    var moduelTree = oper.initModuelTree();
    var menuGrid = oper.initMenuGrid();
    oper.QryClassGrid();
    oper.initTreeEvent();
    oper.initGridEvent();
    
    var tabs = new Ext.TabPanel({
			region: 'west',
			id : 'tabs',
		    activeTab: 0,
		    resizeTabs:true,
		    tabWidth:80, 
		    width:215,   
		    height:Ext.getBody().getSize().height*0.8, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[moduelTree],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "classGrid":
						{

							break ;
						}
						
					}	
	    		}
	    }
		});
    
    rightClick2 = new Ext.menu.Menu({
            id:'rightClick2'
    });
    nullRightClick2 = new Ext.menu.Menu({
            id:'nullRightClick2'
    });
	  
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[tabs,menuGrid]
	});
});

function Oper(){
    var moduleId = -1;
	//目录树初始化
	this.initModuelTree = function(){
   		
		//创建列
		var classColumn = new Ext.grid.ColumnModel({		 
         defaults:{
             css:'height:30px;'
         },        
		 columns:[  
		    {header:'菜单ID',dataIndex:'muneId',hidden:true },	
		    {header:'路径ID',dataIndex:'pathCode',hidden:true },	
		    {header:'父ID',dataIndex:'parentId',hidden:true },	
		    {header:'iconAdr',dataIndex:'iconAdr',hidden:true },	
		    {header:'privCode',dataIndex:'privCode',hidden:true },		    		    
		    {header:'displayIndex',dataIndex:'displayIndex',hidden:true },	
		    {header:'应用分类',dataIndex:'muneName',width:200,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
		]});   
		   
		var classGrid =  new ZTESOFT.Grid({
			id: 'classGrid',
			region: 'west',		
			width:210,		
		    title:'Android',
		    cm:classColumn,
		    pageSize:200,
			paging:false,
			url:'/MOBILE/ExtServlet?actionName=system/AppClassManageAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){	            
             
		                
		            }
		        }
		    })
	    });
				
		return classGrid;
	}
	
	this.QryClassGrid = function(){


	    Ext.getCmp('classGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('classGrid').store.lastOptions != null){
				   Ext.getCmp('classGrid').store.baseParams = {moduleId:0,flag:1,osType:0};
				}
			}
	    )
	    Ext.getCmp('classGrid').store.removeAll() ;
		Ext.getCmp('classGrid').store.load({params:{start:0, limit:16}});
	}
	 
	this.initTreeEvent = function(){
	    Ext.getCmp('classGrid').addListener('rowclick',oper.QryMenuGrid);
    
	}

	this.initMenuGrid = function(){

		var menuColumn = new Ext.grid.ColumnModel({
		 defaults:{
             css:'height:50px;'
         },        
		 columns:[	    
		    {header:'目录ID',dataIndex:'muneId',hidden:true },	
		    {header:'apkId',dataIndex:'apkId',hidden:true },
		    {header:'apkType',dataIndex:'apkType',hidden:true },			
		    {header:'apkCode',dataIndex:'apkCode',hidden:true },			        	    
		    {header:'应用图标',dataIndex:'apkIcon',width:60 ,renderer:function (val){ if (val != null){return "<img src='"+val+"'/>"} else {return "<img src='image/001.png'/>";}}},
		    {header:'应用名称',dataIndex:'muneName',width:100 },
		    {header:'软件介绍内容',dataIndex:'apkContent',width:300}, 
		    {header:'应用大小',dataIndex:'apkSize',width:100,width:100},
		    {header:'版本号',dataIndex:'apkVersionNo',width:100},
		    {header:'应用状态',dataIndex:'funcStatus',width:100,renderer:function (val){ if (val == null || val ==""){return val } else if (val == 1) {return "已注册"} else if (val == 2) {return "审核不通过"} else if (val == 3) {return "审核通过"} else if (val == 4) {return "已更新"} }}

  	    
		]});   
				
		var menuGrid =  new ZTESOFT.Grid({
			id: 'menuGrid',
			region: 'center',					
		    title:'应用列表',
		    cm:menuColumn,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/AppClassManageAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });		
		
		return menuGrid;
	}
	
	this.QryMenuGrid = function(){
        var funcStatus = 3;
        var apkType = 1;  //1代表应用注册，2代表配置开发
        var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
	        var moduleId =  selGridItem[0].data.muneId;
		    Ext.getCmp('menuGrid').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('menuGrid').store.lastOptions != null){
					   Ext.getCmp('menuGrid').store.baseParams = {moduleId:moduleId,flag:2,funcStatus:funcStatus,apkType:apkType,osType:0};
					}
				}
		    )
	    }
	    Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load({params:{start:0, limit:10}});
	}
	
    
    //定义菜单列表菜单
    this.initGridMenu = new function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	this.initGridEvent = function(){		
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
	}
	
	//菜单组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '应用更新' ,handler: function() {
			rightClick.hide();
			oper.apkUpdate();
		}}));
	
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	    
    this.apkUpdate = function(){
      
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.muneId ;
    	
    	regOper.showModuleInfoPage('update',pmName,pmId);
    }
        
}

</script>