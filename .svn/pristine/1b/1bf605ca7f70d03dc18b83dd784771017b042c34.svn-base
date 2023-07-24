<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
	<script type="text/javascript" src="js/appRightPage.js"></script>
	<script type="text/javascript" src="js/appfuncRightPage.js"></script>
	<script type="text/javascript" src="js/commonUpload.js"></script>
	
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id='sysGird' />
		
		 <div id="window-win" style="position: absolute;">
		            <div id="fromPanel" style="height: 40%"></div>
		            <div id="grid" style="height: 60%"></div>
		   </div>
	</body>
</html>
<script type="text/javascript">
   

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var session1 = GetSession();
var callRowCount = 5; 
var appTypeJson;
var oper = new Oper(); 
var appOper= new AppOper();
var funOper =new FunOper();
var uploadOper=new UploadOper()
 var v_staffId = session1.staff.staffId ;
var v_staffName = session1.staff.staffName ;
var v_username = session1.staff.userName;

/**
Ext.apply(Ext.form.VTypes, {
    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },passwordText: '确认密码与密码不一致！',
    
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！',
    
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } 
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    }
});
*/
var startRow = 0;
var rowIndex = 0;
   Ext.onReady(function(){
		var mainPanel = oper.initMainPanel();
		  var classGrid = oper.initModuelTree();
		      var iosGrid = oper.initIosTree();
		         
           var windowGrid = oper.initWindowTree();
            oper.initTreeEvent();
		 oper.initGridEvent();
		   var tables = new Ext.TabPanel({
			region: 'west',
			id : 'tables',
		    activeTab: 0,
		    resizeTabs:true,
		    tabWidth:80, 
		    width:245,   
		    height:Ext.getBody().getSize().height*0.8, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[classGrid,iosGrid,windowGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab =tables.activeTab.id ;
	            	switch (activeTab){
						case "classGrid":
						{
							oper.QryClassGrid();
							break ;
						}
					}	
	    		}
	    }
		});
		 
		 
		 
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			items:[tables,mainPanel]
	});
});
     
    function getJosnParam(gcode,param){
    	 var t;
    	  Ext.Ajax.request({
			   url: '/MOBILE/api/param/list/'+gcode,
			   method:'POST',
			   params: {	// 发送的参数
			   				},
    	       async : false,
			  	scope: this,
			  	 callback: function(options, success, response) {  
                    var msg = ['请求是否成功:', success, '\n', '服务器返回值：', response.responseText];  
                     appTypeJson=response.responseText;
                     alert(appTypeJson);
                } 
			});

        return t;
      }
  
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
        Ext.getCmp('monitorGrid').store.removeAll() ;

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
	this.initMainPanel = function(){
         
         var qryPanel = this.initQryPanel();        
		 var monitorGrid = this.initMonitorGrid();		 
		
		 		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '应用管理',
			region: 'center',
			layout: 'border',
			items:[monitorGrid]
		}); 
		
         
				
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[monitorGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "classGrid":
						{
							oper.QryMonitorGrid();
							break ;
						}						
					}	
	    		}
	    }
		});
	
		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,tabs]
	 	});
	 	return mainPanel;
	}
	    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'应用ID',dataIndex:'appId',hidden:true },		   
		    {header:'操作系统',dataIndex:'osType',hidden:true},
		    {header:'应用图标路径',dataIndex:'iconUri',hidden:true,width:swidth*0.13,renderer:function(val) {
		    	                                                             if (val != null){return "<img src='"+val+"'/>"} 
		    	                                                             else {return "<img src='/MOBILE/appManage/image/001.png'/>";}}},
		    {header:'应用类型id',dataIndex:'appType',hidden:true },
		 
		    {header:'应用名称',dataIndex:'appName'},	
		    {header:'应用code',dataIndex:'appStatus',width:swidth*0.13 ,hidden:true },
		    {header:'应用状态',dataIndex:'appStatusName',width:swidth*0.13  },
		    //,renderer:DomUrl
		    {header:'应用下载路径',dataIndex:'downloadUrl',width:swidth*0.15 ,renderer:DomUrl},
		    {header:'内部版本号',dataIndex:'versionCode',width:swidth*0.13,hidden:true}, 
		    {header:'外部版本号',dataIndex:'versionName',width:swidth*0.13}, 
		    {header:'操作系统',dataIndex:'osName',width:swidth*0.13},
		    {header:'应用大小(MB)',dataIndex:'appSize',width:swidth*0.13,renderer:function (val){ if (val !='') {
		    	                                                                                          return parseInt((parseInt(val)/1024/1024).toFixed(2))+0.01
		    	                                                                                          }
		                                                                                                      }},
		    {header:'应用介绍',dataIndex:'appIntro',width:swidth*0.13},  
		    {header:'下载次数',dataIndex:'downloadCount',width:swidth*0.13}, 
		    {header:'应用发布人',dataIndex:'appPublisher',width:swidth*0.13},
		     {header:'应用类型Id',dataIndex:'busiSysId'  ,hidden:true},
		      {header:'应用类型',dataIndex:'appTypeName' },
		    {header:'应用分类Id',dataIndex:'appClass',width:swidth*0.13  ,hidden:true},
		    {header:'应用分类',dataIndex:'appClassName',width:swidth*0.13 },
		    {header:'更新时间',dataIndex:'updateTime',width:swidth*0.13}, 
		    {header:'更新日志',dataIndex:'updateLog',width:swidth*0.13},
		     {header:'备注',dataIndex:'memo',width:swidth*0.13}
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'应用列表',
		    cm:column,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelAppFunHisAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: { 
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return monitorGrid;	
	}
		this.QryMonitorGrid = function(){
	    var appname = Ext.getCmp('appname').getValue();
		var appType = Ext.getCmp('appType').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
	   var appClass =  selGridItem[0].data.mcode;
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {appType:appType,appname:appname,beginDate:beginDate,endDate:endDate,flag:1,appClass:appClass};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	
	this.initServeStore = function(){
	     var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/APP_OS_TYPE'
	        }),
	          baseParams:{flag:1}
	    
	        
	    });

	    return sysStore;
	}
	
	this.initQryPanel = function(){
	 
	     var sysStore = this.initServeStore();
	    var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
		region: 'north',
		frame:true,
		title: '查询条件',
	    bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true,
        height:Ext.getBody().getSize().height*0.23,
        width:Ext.getBody().getSize().width*0.8,
        buttons:[{
            text: '查询',onClick:doQry},{text: '重置',onClick:reset
        }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '应用名称',
                    name: 'appname',
                    id: 'appname',
                    anchor:'95%'
                },{
                    xtype:'datefield',
                    fieldLabel: '开始日期',
                    name: 'beginDate',
                    id: 'beginDate',
                    format :'Y-m-d',
                    anchor:'95%'              
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    fieldLabel: '应用系统',
                    xtype: 'combo',
                    name: 'appType',
	                id: 'appType',
	                valueField: 'mcode',
	                displayField: 'mname',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:sysStore
                },{
                    xtype:'datefield',
                    fieldLabel: '结束日期',
                    name: 'endDate',
                    id: 'endDate',
                    format :'Y-m-d',
                    anchor:'95%'
                }]
            }]
        }]
	});
	          return qryPanel;
	}
	
	//定义菜单目录树事件


	this.initTreeEvent = function(){
	    Ext.getCmp('classGrid').addListener('rowclick',oper.QryMenuGrid);
	    Ext.getCmp('iosGrid').addListener('rowclick',oper.QryMenuGrid);
	    Ext.getCmp('windowGrid').addListener('rowclick',oper.QryMenuGrid);
 	   
		 
	}
		this.QryMenuGrid = function(){
	    var activeTab = Ext.getCmp('tables').activeTab.id ;
	  
	    switch (activeTab){
			case "classGrid":
			{
			  var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
		         var appClass =  selGridItem[0].data.mcode;
		        if (selGridItem != null && selGridItem.length >0){
				    Ext.getCmp('monitorGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('monitorGrid').store.lastOptions != null){
							   Ext.getCmp('monitorGrid').store.baseParams = {flag:2,osType:1,appClass:appClass};
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
				    Ext.getCmp('monitorGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('monitorGrid').store.lastOptions != null){
							   Ext.getCmp('monitorGrid').store.baseParams = {flag:2,osType:2};
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
				    Ext.getCmp('monitorGrid').store.on('beforeload',
						function(store){ 
							if(Ext.getCmp('monitorGrid').store.lastOptions != null){
							   Ext.getCmp('monitorGrid').store.baseParams = {flag:2,osType:3};
							}
						}
				    )
			    }                           
			   break ;
			}

		}	
	    
	
        
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:10}});
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
		Ext.getCmp('monitorGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('monitorGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	    
	//菜单组装
	this.rightClickFn = function(monitorGrid,rowIndex,e){
	
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		Ext.getCmp('monitorGrid').getSelectionModel().selectRow(rowIndex);
		
	//		rightClick.insert(i++,new Ext.menu.Item({ text: '添加应用' ,handler: function() {
	//				rightClick.hide();
	//				oper.apkReg();
	//		}}));
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

				
		Ext.getCmp('monitorGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	   this.nullRightClickFn = function(e){

	
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '应用添加' ,handler: function() {
			nullRightClick.hide();
			oper.apkReg();
		}}));
         
         
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    	
    }
 
	//添加调用的弹出框
   this.apkReg = function(){
		   var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
		//   var appid=   selGridItem[0].data.appId;
          appOper.funcInfoPage('add',null,null,null,null);
    }
    //应用修改
    this.apkMod = function (){
    		   var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
    		  if(selGridItem==null||  selGridItem=='' ||  selGridItem=='undefind'){
    			   Ext.MessageBox.alert('提示', '请选择一条记录！',null);
    			   return false;
    		   }
		     var appid=   selGridItem[0].data.appId;
    	var appid=   selGridItem[0].data.appId;
    //	alert(appid);
    	appOper.funcInfoPage('mod',null,null,null,appid);
    	
    }
	 //应用删除
    this.apkDel = function (btn){
		 
    	if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "del";
        paramObj.optfunmenu='app';
        paramObj.appId = selGridItem[0].data.appId;
        var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
        if (retMap){
           oper.QryMonitorGrid();
        }
      }
    	
    }
	 //应用详情
	 this.apkDetail=function(){
		    var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
    		  if(selGridItem==null||  selGridItem=='' ||  selGridItem=='undefind'){
    			   Ext.MessageBox.alert('提示', '请选择一条记录！',null);
    			   return false;
    		   }
		     var appid=   selGridItem[0].data.appId;
    	var appid=   selGridItem[0].data.appId;
    	appOper.funcInfoPage('detail',null,null,null,appid);
		 
	 }
		function doQry(){
	
	    var activeTab = Ext.getCmp('tables').activeTab.id ;
	    switch (activeTab){
			case "classGrid":
			{
				oper.QryMonitorGrid();
				break ;
			}
			
		}	
	}
		
		function reset(){
		Ext.getCmp('appname').setValue('');
		Ext.getCmp('appType').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}

	}
	function DomUrl(value) {
		startRow++;
		var rowIndexs = Ext.getCmp('monitorGrid').getStore().getCount();
		if (startRow > rowIndexs) {
			startRow = 1;
		}
		var row = Ext.getCmp('monitorGrid').getSelectionModel().selectRow(startRow-1);
		var rownum = Ext.getCmp('monitorGrid').getSelectionModel().getSelected();
		
		//var strUrl = rownum.get("downloadUrl");
		var frameAppId = rownum.get("appId");
		var downloadCount = rownum.get("downloadCount");
		var strUrl = "/MOBILE/api/server/downloads/app/" + frameAppId;
		return "<a href='" + strUrl + "' onClick='urlClick(" + frameAppId + "," + downloadCount + ")'>" + value + "</a>";
	}

function urlClick(frameAppId,downloadCount) {
	//alert('urlClick' + frameAppId + downloadCount);
//	var inputParams = new Object();
//	inputParams.appId = frameAppId;
//	inputParams.downloadCount = downloadCount ;
//	inputParams.type = "download";
//	             inputParams.optfunmenu="app"
//	            
//	var retMap = invokeAction("/appmanage/OptAppFunHisAction", inputParams);
	
	Ext.MessageBox.show({
           	msg: '系统正在请求数据……',
           	progressText: 'Saving...',
           	width:300,
           	wait:true,
           	waitConfig: {interval:100},
           	icon:'ext-mb-download'
       	});
	setTimeout(function(){
        Ext.MessageBox.hide();
    
    }, 1000);
    startRow = 0;
	Ext.getCmp('monitorGrid').store.removeAll();
	Ext.getCmp('monitorGrid').store.reload();
}
</script>