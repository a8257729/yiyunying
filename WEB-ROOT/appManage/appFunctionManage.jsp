<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用功能注册</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
	    <script language="javascript" src="js/funcReg.js"></script>
 <script language="javascript" src="js/unfuncReg.js"></script>
		
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
var funcOper = new FuncOper();
var unfuncOper = new unFuncOper();
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
		    {header:'菜单ID',dataIndex:'menuId',hidden:true },	
		    {header:'路径ID',dataIndex:'pathCode',hidden:true },	
		    {header:'父ID',dataIndex:'parentId',hidden:true },	
		    {header:'menuIconUri',dataIndex:'menuIconUri',hidden:true },	
		    {header:'privCode',dataIndex:'privCode',hidden:true },		    		    
		    {header:'menuIndex',dataIndex:'menuIndex',hidden:true },	
		    {header:'应用分类',dataIndex:'menuName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
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
				   Ext.getCmp('classGrid').store.baseParams = {flag:3,osType:1};
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
		    {header:'菜单ID',dataIndex:'menuId',hidden:true },	
		    {header:'路径ID',dataIndex:'pathCode',hidden:true },	
		    {header:'父ID',dataIndex:'parentId',hidden:true },	
		    {header:'menuIconUri',dataIndex:'menuIconUri',hidden:true },	
		    {header:'privCode',dataIndex:'privCode',hidden:true },		    		    
		    {header:'menuIndex',dataIndex:'menuIndex',hidden:true },	
		    {header:'应用分类',dataIndex:'menuName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
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
				   Ext.getCmp('iosGrid').store.baseParams = {flag:3,osType:2};
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
		    {header:'菜单ID',dataIndex:'menuId',hidden:true },	
		    {header:'路径ID',dataIndex:'pathCode',hidden:true },	
		    {header:'父ID',dataIndex:'parentId',hidden:true },	
		    {header:'menuIconUri',dataIndex:'menuIconUri',hidden:true },	
		    {header:'privCode',dataIndex:'privCode',hidden:true },		    		    
		    {header:'menuIndex',dataIndex:'menuIndex',hidden:true },	
		    {header:'应用分类',dataIndex:'menuName',width:240,renderer:function (val){ return "&nbsp;&nbsp;<img src='image/1.gif'/>"+val;} }   
		    
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
				   Ext.getCmp('windowGrid').store.baseParams = {flag:3,osType:3};
				}
			}
	    )
	    Ext.getCmp('windowGrid').store.removeAll() ;
		Ext.getCmp('windowGrid').store.load({params:{start:0, limit:16}});
	} 

	this.initMenuGrid = function(){

		var menuColumn = new Ext.grid.ColumnModel({
		 defaults:{
             css:'height:50px;line-height:50px;'
         },        
		 columns:[	    
		    {header:'目录ID',dataIndex:'menuId',hidden:true },	
		    {header:'父节点Id',dataIndex:'parentId',hidden:true },
		     {header:'menuConfigId',dataIndex:'menuConfigId',hidden:true },
		    {header:'menuType',dataIndex:'menuType',hidden:true },		    			
		    {header:'功能图标',dataIndex:'menuIconURL',width:swidth*0.10 ,renderer:function (val){ if (val != null){return "<img src='"+val+"'/>"} else {return "<img src='/MOBILE/appManage/image/001.png'/>";}}},
		    {header:'功能名称',dataIndex:'menuName',width:swidth*0.11 },
		    {header:'功能路径',dataIndex:'pathName',width:swidth*0.13},
		    {header:'功能编码',dataIndex:'privCode',width:swidth*0.13,hidden:true},
		    {header:'功能地址',dataIndex:'menuUri',width:swidth*0.25 },
		    {header:'创建时间',dataIndex:'buildTime',width:swidth*0.13},
		    {header:'功能类型',dataIndex:'menuTypeName',width:swidth*0.13},
	        {header:'功能描述',dataIndex:'menuInfo',width:swidth*0.13 },
	        {header:'顺序',dataIndex:'menuIndex',width:100 ,hidden:true}
  	    
		]});   
				
		var menuGrid =  new ZTESOFT.Grid({
			id: 'menuGrid',
			region: 'center',					
		    title:'功能列表',
		    cm:menuColumn,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelAppFunctionAction',
			
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
		         var moduleId =  selGridItem[0].data.menuId;
		        if (selGridItem != null && selGridItem.length >0){
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:2,osType:1,menuId:moduleId};
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
			        var moduleId =  selGridItem[0].data.menuId;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:2,osType:2};
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
			        var moduleId =  selGridItem[0].data.menuId;
				    Ext.getCmp('menuGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('menuGrid').store.lastOptions != null){
							   Ext.getCmp('menuGrid').store.baseParams = {flag:2,osType:3};
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
        paramObj.muneId = selGridItem[0].data.menuId;
        
        var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);      
        if (retMap){
           oper.QryClassGrid();
        }
      }
    }
    
    this.moduelMod = function(){
		var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.muneName ;
    	var pmId = selGridItem[0].data.menuId ;
    	
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
	    var sysFlag =1;//默认为安卓
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
			    sysFlag = 1 ;
				break ;
			}
			case "iosGrid":
			{
                sysFlag = 2 ;            
				break ;
			}
            case "windowGrid":
			{
                sysFlag = 3 ;              
				break ;
			}
		}	
	   		
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
		if (sysFlag ==0 ){
			rightClick.insert(i++,new Ext.menu.Item({ text: '功能注册' ,handler: function() {
					rightClick.hide();
					oper.apkReg();
			}}));
		}


			rightClick.insert(i++,new Ext.menu.Item({ text: '修改功能' ,handler: function() {
				rightClick.hide();
				oper.apkMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除功能' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.apkDel);
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '功能详情' ,handler: function() {
				rightClick.hide();
				oper.apkDetail();
			}}));

				
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
	    var sysFlag =1;//默认为安卓
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
			    sysFlag = 1 ;
				break ;
			}
			case "iosGrid":
			{
                sysFlag = 2 ;            
				break ;
			}
            case "windowGrid":
			{
                sysFlag = 3 ;              
				break ;
			}
		}	
	
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '功能注册' ,handler: function() {
			nullRightClick.hide();
			oper.apkReg('');
		}}));
         
           nullRightClick.insert(i++,new Ext.menu.Item({ text: '非功能注册' ,handler: function() {
		nullRightClick.hide();
			oper.apkReg('unfunction');		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    	
    }
    
    this.apkReg = function(func){
        var selItem;
        var selGridItem;
        var activeTab = Ext.getCmp('tabs').activeTab.id ;
       
        var pmName  ;
	    switch (activeTab){
			case "classGrid":
			{
				selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();  
				selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
			    pmName = 1 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                selGridItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();    
                pmName = 2 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                selGridItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();     
                pmName = 3 ;              
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
         	
    	var pmId = selGridItem[0].data.menuId ;
    	var menuName=selGridItem[0].data.menuName
    	if(func=='' ||func == 'undedind'){
    //	alert(selGridItem[0].data +"  aaaa"+selItem +"  " +pmName+" , "+menuName);
          funcOper.funcInfoPage('add',pmName,pmId,menuName);}
       else{
            unfuncOper.funcInfoPage('add',pmName,pmId,menuName);
       }
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
    	var pmName = selItem[0].data.menuName ;
    	var menuType =selGridItem[0].data.menuType;
    	var pmId = selGridItem[0].data.menuId ;
    	if(menuType==2){
    	    funcOper.funcInfoPage('mod',pmName,pmId,pmName);
    	}else{
    	  unfuncOper.funcInfoPage('mod',pmName,pmId,pmName);
    	}
    }
    this.apkDetail = function(){
     
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.menuName ;
    	var pmId = selGridItem[0].data.menuId ;
    	 var menuType =selGridItem[0].data.menuType;
    	if(menuType==1){
    	    funcOper.funcInfoPage('detail',pmName,pmId,pmName);
    	}else{
    	unfuncOper.funcInfoPage('detail',pmName,pmId,pmName);
    	}
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
			    pmName = 1 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                pmName = 2 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                pmName = 3 ;              
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
			    pmName = 1 ;
				break ;
			}
			case "iosGrid":
			{
                selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();     
                pmName = 2 ;            
				break ;
			}
            case "windowGrid":
			{
                selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();   
                pmName = 3 ;              
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
      
   
       
    this.apkDel = function(btn){
        if (btn == 'yes'){
			var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
	    	var paramObj = new Object();
	    	paramObj.type ="del";
	    	paramObj.menuId = selGridItem[0].data.menuId ;
	    	paramObj.apkCode = selGridItem[0].data.apkCode ;
	    	paramObj.apkId = selGridItem[0].data.apkId ;
	    	paramObj.menuConfigId=selGridItem[0].data.menuConfigId ;
	    	var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
	    	oper.QryMenuGrid();
    	}
    } 
}

</script>