<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用注册</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
	    <script language="javascript" src="js/apkClass.js"></script>
	    <script language="javascript" src="js/apkReg.js"></script>
	    <script language="javascript" src="js/apkDetail.js"></script>
	    <script language="javascript" src="js/apkDev.js"></script>
		<script language="javascript" src="js/menuMng.js"></script>
		
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
var session1 = GetSession();
var rightClick2;
var nullRightClick2;
var staffId = session1.staff.staffId ;
var fext = new BSCommon();
var oper = new Oper();
var classOper = new ClassOper(); 
var regOper = new RegOper();
var apkDetailOper = new ApkDetailOper();
var apkDev = new ApkDev();
var menuOper = new MenuOper();
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

    var classGrid = oper.initModuelTree();
    var iosGrid = oper.initIosTree();
    var windowGrid = oper.initWindowTree();
    
    var menuGrid = oper.initMenuGrid();
    oper.QryClassGrid();
    oper.QryIosGrid();
    oper.QryWindowGrid();    
    oper.initTreeEvent();
    oper.initGridEvent();
    
    rightClick2 = new Ext.menu.Menu({
            id:'rightClick2'
    });
    nullRightClick2 = new Ext.menu.Menu({
            id:'nullRightClick2'
    });

    var treeMenu = oper.initClassMenu();
    
    var tabs = new Ext.TabPanel({
			region: 'west',
			id : 'tabs',
		    activeTab: 0,
		    resizeTabs:true,
		    tabWidth:80, 
		    width:245,   
		    height:Ext.getBody().getSize().height*0.8, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[classGrid, iosGrid,windowGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "classGrid":
						{
							oper.QryClassGrid();
							break ;
						}
						case "iosGrid":
						{
                            oper.QryIosGrid();                           
							break ;
						}
                        case "windowGrid":
						{
                            oper.QryWindowGrid();                           
							break ;
						}

					}	
	    		}
	    }
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
		    {header:'应用分类',dataIndex:'muneName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
		]});   
		   
		var classGrid =  new ZTESOFT.Grid({
			id: 'classGrid',
			region: 'west',		
			width:240,		
		    title:'Android',
		    cm:classColumn,
		    pageSize:200,
		    hideHeaders:true,
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
        Ext.getCmp('menuGrid').store.removeAll() ;

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
	
	
	this.initIosTree = function(){
   		
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
		    {header:'应用分类',dataIndex:'muneName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
		]});   
		   
		var iosGrid =  new ZTESOFT.Grid({
			id: 'iosGrid',
			region: 'west',		
			width:240,		
		    title:'iOS',
		    cm:classColumn,
		    pageSize:200,
		    hideHeaders:true,
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
				
		return iosGrid;
	}
	
	this.QryIosGrid = function(){
        Ext.getCmp('menuGrid').store.removeAll() ;

	    Ext.getCmp('iosGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('iosGrid').store.lastOptions != null){
				   Ext.getCmp('iosGrid').store.baseParams = {moduleId:0,flag:1,osType:1};
				}
			}
	    )
	    Ext.getCmp('iosGrid').store.removeAll() ;
		Ext.getCmp('iosGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initWindowTree = function(){
   		
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
		    {header:'应用分类',dataIndex:'muneName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
		]});   
		   
		var windowGrid =  new ZTESOFT.Grid({
			id: 'windowGrid',
			region: 'west',		
			width:240,		
		    title:'WindowsPhone',
		    cm:classColumn,
		    pageSize:200,
		    hideHeaders:true,
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
				
		return windowGrid;
	}
	
	this.QryWindowGrid = function(){
        Ext.getCmp('menuGrid').store.removeAll() ;

	    Ext.getCmp('windowGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('windowGrid').store.lastOptions != null){
				   Ext.getCmp('windowGrid').store.baseParams = {moduleId:0,flag:1,osType:2};
				}
			}
	    )
	    Ext.getCmp('windowGrid').store.removeAll() ;
		Ext.getCmp('windowGrid').store.load({params:{start:0, limit:16}});
	} 

	this.initMenuGrid = function(){

		var menuColumn = new Ext.grid.ColumnModel({
		 defaults:{
             css:'height:70px;line-height:70px;'
         },        
		 columns:[	    
		    {header:'目录ID',dataIndex:'muneId',hidden:true },	
		    {header:'apkId',dataIndex:'apkId',hidden:true },		    			
		    {header:'apkCode',dataIndex:'apkCode',hidden:true },			        	    
		    {header:'应用图标',dataIndex:'apkIcon',width:80 ,renderer:function (val){ if (val != null){return "<img src='"+val+"'/>"} else {return "<img src='image/001.png'/>";}}},
		    {header:'应用名称',dataIndex:'muneName',width:100 },
		    {header:'软件介绍内容',dataIndex:'apkContent',width:300}, 
		    {header:'应用大小',dataIndex:'apkSize',width:100,width:100},
		    {header:'版本号',dataIndex:'apkVersionNo',width:100},		    
		    {header:'应用状态',dataIndex:'funcStatus',width:100,renderer:function (val){ if (val == null || val ==""){return val } else if (val == 1) {return "已注册"} else if (val == 2) {return "审核不通过"} else if (val == 3) {return "审核通过"} else if (val == 4) {return "已更新"} }},
            {header:'应用类型',dataIndex:'apkType',width:100,renderer:function (val){ if (val == null || val ==""){return val } else if (val == 1) {return "应用注册"} else if (val == 2) {return "配置开发"}}}
  	    
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
	    var funcStatus ='';//1,2
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
			    
		        var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
		        if (selGridItem != null && selGridItem.length >0){
			        var moduleId =  selGridItem[0].data.muneId;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {moduleId:moduleId,flag:2,funcStatus:funcStatus,osType:0};
							}
						}
				    )
			    }
			   break ;
			}
			case "iosGrid":
			{
               var selGridItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();
		        if (selGridItem != null && selGridItem.length >0){
			        var moduleId =  selGridItem[0].data.muneId;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {moduleId:moduleId,flag:2,funcStatus:funcStatus,osType:1};
							}
						}
				    )
			    }                    
			   break ;
			}
            case "windowGrid":
			{
               var selGridItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();
		        if (selGridItem != null && selGridItem.length >0){
			        var moduleId =  selGridItem[0].data.muneId;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {moduleId:moduleId,flag:2,funcStatus:funcStatus,osType:2};
							}
						}
				    )
			    }                           
			   break ;
			}

		}	
	    
	
        
	    Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load({params:{start:0, limit:10}});
	}
	
	//定义菜单目录树事件


	this.initTreeEvent = function(){
	    Ext.getCmp('classGrid').addListener('rowclick',oper.QryMenuGrid);
	    Ext.getCmp('iosGrid').addListener('rowclick',oper.QryMenuGrid);
	    Ext.getCmp('windowGrid').addListener('rowclick',oper.QryMenuGrid);
		//Ext.getCmp('classGrid').addListener('rowcontextmenu', oper.rightClickFn1);
		//Ext.getCmp('classGrid').addListener('contextmenu', oper.nullRightClickFn1);    	   
		 
	}
	
	//定义菜单目录右键菜单
	this.initClassMenu = function(){
		var rightClick1 = new Ext.menu.Menu({
		    id:'rightClick1'
		});
		var nullRightClick1 = new Ext.menu.Menu({
		    id:'nullRightClick1'
		});
	}
	
	//菜单组装
	this.rightClickFn1 = function(classGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick1');
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加应用分类' ,handler: function() {
			rightClick.hide();
			oper.moduelAdd();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改应用分类' ,handler: function() {
			rightClick.hide();
			oper.moduelMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除应用分类' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.menuDel);
		}}));

		Ext.getCmp('classGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn1 = function(e){
	    var i = 0 ;
		var nullRightClick = Ext.getCmp('nullRightClick1');
		nullRightClick.removeAll();
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '增加应用分类' ,handler: function() {
			nullRightClick.hide();
			oper.moduelAdd();
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    }
	   	      
    this.moduelAdd = function(){
   
    	var pmName ="" ;
    	var pmId = "" ;  	
    	classOper.showModuleInfoPage('add',pmName,pmId);
    	
    }
    
    this.menuDel =  function(btn){
      if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "del";
        paramObj.muneId = selGridItem[0].data.muneId;
        
        var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);      
        if (retMap){
           oper.QryClassGrid();
        }
      }
    }
    
    this.moduelMod = function(){
		var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.muneId ;
    	
    	classOper.showModuleInfoPage('mod',pmName,pmId);
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
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//菜单组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
	    var sysFlag =0;//默认为安卓
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
			    sysFlag = 0 ;
				break ;
			}
			case "iosGrid":
			{
                sysFlag = 1 ;            
				break ;
			}
            case "windowGrid":
			{
                sysFlag = 2 ;              
				break ;
			}
		}	
	   
		
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
		if (sysFlag ==0 ){
			rightClick.insert(i++,new Ext.menu.Item({ text: '应用注册' ,handler: function() {
					rightClick.hide();
					oper.apkReg();
			}}));
		}
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '配置开发' ,handler: function() {
				rightClick.hide();
				oper.apkDevAdd();
		}}));
		//插入一个分割符
		rightClick.insert(i++,rightClick.addSeparator());
					
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		if (selGridItem != null &&　selGridItem.length >0 && selGridItem[0].data.apkType == "1"){
		  if (sysFlag ==0 ){	
			rightClick.insert(i++,new Ext.menu.Item({ text: '修改应用' ,handler: function() {
				rightClick.hide();
				oper.apkMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除应用' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.apkDel);
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '应用详情' ,handler: function() {
				rightClick.hide();
				oper.apkDetail();
			}}));
		  }
		}else {
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置修改' ,handler: function() {
				rightClick.hide();
				oper.apkDevMod();
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置删除' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.apkDevDel);
				
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置详情' ,handler: function() {
				rightClick.hide();
				oper.apkDevDetail();
			}}));
		}
		

		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
	    var sysFlag =0;//默认为安卓
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
			    sysFlag = 0 ;
				break ;
			}
			case "iosGrid":
			{
                sysFlag = 1 ;            
				break ;
			}
            case "windowGrid":
			{
                sysFlag = 2 ;              
				break ;
			}
		}	
	
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();
        if ( sysFlag == 0 ){
			nullRightClick.insert(i++,new Ext.menu.Item({ text: '应用注册' ,handler: function() {
				nullRightClick.hide();
				oper.apkReg();
			}}));
		}
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '配置开发' ,handler: function() {
			nullRightClick.hide();
			oper.apkDevAdd();
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    	
    }
    
    this.apkReg = function(){
        var selItem;
        var selGridItem;
        var activeTab = Ext.getCmp('tabs').activeTab.id ;
        var pmName  ;
	    switch (activeTab){
			case "classGrid":
			{
				selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
				selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
			    pmName = 0 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                selGridItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();    
                pmName = 1 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                selGridItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();     
                pmName = 2 ;              
				break ;
			}

		}	
                
        if (selItem == null || selItem.length ==0){
        	Ext.MessageBox.show({
               title: '提示',
               msg: '请选择应用分类！',
               buttons: Ext.MessageBox.OK,
               width:200,
               icon: Ext.MessageBox.ERROR
            });
            return;
        } 
         	
    	var pmId = selGridItem[0].data.muneId ;
    	
    	regOper.showModuleInfoPage('add',pmName,pmId);
    }
    
    this.apkMod = function(){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
        if (selItem == null || selItem.length ==0){
        	Ext.MessageBox.show({
               title: '提示',
               msg: '请选择应用分类！',
               buttons: Ext.MessageBox.OK,
               width:200,
               icon: Ext.MessageBox.ERROR
            });
            return;
        } 
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.muneId ;
    	
    	regOper.showModuleInfoPage('mod',pmName,pmId);
    }
    this.apkDetail = function(){
   
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.muneId ;
    	
    	apkDetailOper.showModuleInfoPage('detail',pmName,pmId);
    }
    
    this.apkDevAdd = function(){
        var selItem;
        var selGridItem;
        var activeTab = Ext.getCmp('tabs').activeTab.id ;
        var pmName  ;//用来存储终端类型参数ostype
	    switch (activeTab){
			case "classGrid":
			{
				selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
			    pmName = 0 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                pmName = 1 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                pmName = 2 ;              
				break ;
			}

		}	
    

        if (selItem == null || selItem.length ==0){
        	Ext.MessageBox.show({
               title: '提示',
               msg: '请选择应用分类！',
               buttons: Ext.MessageBox.OK,
               width:200,
               icon: Ext.MessageBox.ERROR
            });
            return;
        } 
   
    	var pmId = "" ;
    	
    	apkDev.showModuleInfoPage('devAdd',pmName,pmId);
    }
    
    this.apkDevMod = function(){
    
        var activeTab = Ext.getCmp('tabs').activeTab.id ;
        var pmName  ;//用来存储终端类型参数ostype
	    switch (activeTab){
			case "classGrid":
			{
				selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
			    pmName = 0 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                pmName = 1 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                pmName = 2 ;              
				break ;
			}

		}

        if (selItem == null || selItem.length ==0){
        	Ext.MessageBox.show({
               title: '提示',
               msg: '请选择应用分类！',
               buttons: Ext.MessageBox.OK,
               width:200,
               icon: Ext.MessageBox.ERROR
            });
            return;
        } 
   
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmId = selGridItem[0].data.muneId ;
    	
    	apkDev.showModuleInfoPage('devMod',pmName,pmId);
    }
    
    this.apkDevDetail = function(){
        var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
				selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();        
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();        
				break ;
			}
		}
		
        if (selItem == null || selItem.length ==0){
        	Ext.MessageBox.show({
               title: '提示',
               msg: '请选择应用分类！',
               buttons: Ext.MessageBox.OK,
               width:200,
               icon: Ext.MessageBox.ERROR
            });
            return;
        } 
   
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.muneId ;
    	
    	apkDev.showModuleInfoPage('devDetail',pmName,pmId);
    }
      
    this.apkDevDel = function(btn){
        if (btn == 'yes'){
			var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
	    	var paramObj = new Object();
	    	paramObj.type ="devDel";
	    	paramObj.muneId = selGridItem[0].data.muneId ;
	    	paramObj.apkId = selGridItem[0].data.apkId ;
	    	
	    	var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
	    	oper.QryMenuGrid();
    	}
    } 
       
    this.apkDel = function(btn){
        if (btn == 'yes'){
			var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
	    	var paramObj = new Object();
	    	paramObj.type ="del";
	    	paramObj.muneId = selGridItem[0].data.muneId ;
	    	paramObj.apkCode = selGridItem[0].data.apkCode ;
	    	paramObj.apkId = selGridItem[0].data.apkId ;
	    	
	    	var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
	    	oper.QryMenuGrid();
    	}
    } 
}

</script>