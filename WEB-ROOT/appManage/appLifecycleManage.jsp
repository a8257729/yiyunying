<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用生命周期管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
	    <script language="javascript" src="js/appReg.js"></script>
	    <script language="javascript" src="js/appDetail.js"></script>
        <script language="javascript" src="js/commonUpload.js"></script>
		
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
var regOper = new RegOper();
var uploadOper = new UploadOper();
var detailOper = new DetailOper();
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
		    {header:'paramId',dataIndex:'paramId',hidden:true },	
		    {header:'系统域编码',dataIndex:'mcode',hidden:true },	
		    {header:'系统域名',dataIndex:'mname',width:240 }   
		    
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
			url:'/MOBILE/ExtServlet?actionName=system/AppManagementAction',
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
				   Ext.getCmp('classGrid').store.baseParams = {flag:4,gcode:'SERVER_SYS'};
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
		    {header:'paramId',dataIndex:'paramId',hidden:true },	
		    {header:'系统域编码',dataIndex:'mcode',hidden:true },	
		    {header:'系统域名',dataIndex:'mname',width:240 }   
		    
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
			url:'/MOBILE/ExtServlet?actionName=system/AppManagementAction',
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
				   Ext.getCmp('iosGrid').store.baseParams = {flag:4,gcode:'SERVER_SYS'};
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
		    {header:'paramId',dataIndex:'paramId',hidden:true },	
		    {header:'系统域编码',dataIndex:'mcode',hidden:true },	
		    {header:'系统域名',dataIndex:'mname',width:240 }   
		    
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
			url:'/MOBILE/ExtServlet?actionName=system/AppManagementAction',
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
				   Ext.getCmp('windowGrid').store.baseParams = {flag:4,gcode:'SERVER_SYS'};
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
		    {header:'appId',dataIndex:'appId',hidden:true },		    			
		    {header:'appClass',dataIndex:'appClass',hidden:true },
		    {header:'appSnapshot',dataIndex:'appSnapshot',hidden:true },	
		    {header:'osType',dataIndex:'osType',hidden:true },	
		    {header:'内部版本号',dataIndex:'versionCode',hidden:true },
		    {header:'应用发布人id',dataIndex:'appPublisher',hidden:true},	
		    {header:'应用状态',dataIndex:'appStatus',hidden:true },		    
		    {header:'应用下载链接',dataIndex:'downloadUrl',hidden:true },
		    {header:'二维码下载链接',dataIndex:'qrcodeUrl',hidden:true },
		    {header:'应用图标',dataIndex:'iconUri',width:80 ,renderer:function (val){ if (val != null && val != ""){return "<img src='"+val+"'/>"} else {return "<img src='image/001.png'/>";}}},
		    {header:'应用名称',dataIndex:'appName',width:100 },			        	    		    
		    {header:'应用版本',dataIndex:'versionName',width:80 },
		    {header:'应用大小',dataIndex:'appSize',width:80}, 
		    {header:'应用关键字',dataIndex:'appKeyWord',width:80},
		    {header:'应用状态',dataIndex:'appStatusName',width:80 },		    
		    {header:'应用下载次数',dataIndex:'downloadCount',width:80},	
		    {header:'应用介绍',dataIndex:'appIntro',width:250}, 		    	    
		    {header:'应用发布人',dataIndex:'staffName',width:80},		
		    {header:'创建时间',dataIndex:'buildTime',width:120}	   		    
  	    
		]});   
				
		var menuGrid =  new ZTESOFT.Grid({
			id: 'menuGrid',
			region: 'center',					
		    title:'应用列表',
		    cm:menuColumn,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/AppManagementAction',
			
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
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{			    
		        var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
		        if (selGridItem != null && selGridItem.length >0){
			        var appClass =  selGridItem[0].data.mcode;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:1,appClass:appClass,osType:0};
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
			        var appClass =  selGridItem[0].data.mcode;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:1,appClass:appClass,osType:1};
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
			        var appClass =  selGridItem[0].data.mcode;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:1,appClass:appClass,osType:2};
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


			rightClick.insert(i++,new Ext.menu.Item({ text: '修改应用' ,handler: function() {
				rightClick.hide();
				oper.apkMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除应用' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.apkDel);
			}}));
			
			//插入一个分割符
			rightClick.insert(i++,rightClick.addSeparator());
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '应用详情' ,handler: function() {
				rightClick.hide();
				oper.apkDetail();
			}}));

            rightClick.insert(i++,new Ext.menu.Item({ text: '应用升级' ,handler: function() {
				rightClick.hide();
				oper.apkUpgrade();
			}}));
				
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

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '应用注册' ,handler: function() {
			nullRightClick.hide();
			oper.apkReg();
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
    	var pmName = selGridItem[0].data.appName ;
    	var pmId = selGridItem[0].data.appId ;
    	
    	regOper.showModuleInfoPage('mod',pmName,pmId);
    }
    
    this.apkDel = function(btn){
        if (btn == 'yes'){
			var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
	    	var paramObj = new Object();
	    	paramObj.type ="del";
	    	paramObj.appId = selGridItem[0].data.appId ;	    	
	    	var retMap = invokeAction("/appmanage/OptAppAction", paramObj);
	    	oper.QryMenuGrid();
    	}
    } 
    
    this.apkDetail = function(){
   
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.appName ;
    	var pmId = selGridItem[0].data.appId ;
    	
    	detailOper.showModuleInfoPage('detail',pmName,pmId);
    }
            
    this.apkUpgrade = function(){
   
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
    	var pmName = selGridItem[0].data.appName ;
    	var pmId = selGridItem[0].data.appId ;
    	
    	regOper.showModuleInfoPage('upgrade',pmName,pmId);
    }                   

}

</script>