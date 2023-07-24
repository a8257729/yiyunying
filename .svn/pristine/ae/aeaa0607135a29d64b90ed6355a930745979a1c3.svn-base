<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>APK管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/otherApkMng.js"></script>
		<script language="javascript" src="js/phoneVersionMng.js"></script>
		<script language="javascript" src="js/apkFunction.js"></script>
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
var otherApkOper = new OtherApkOper();
var funOper = new ApkFunctionOper();
var phoneVersionOper = new PhoneVersionOper();

Ext.onReady(function(){
	
	//接入控制 或 业务系统管理 的 系统列表 add by guo.jinjun at 2012-05-10 15:15:36
	var sysGrid = oper.initSysGrid();
	
	var otherApkGrid = oper.initMenuGrid();
	var funGrid = oper.initFunGrid();
	//var otherApkPanel = oper.initInfoPanel();//modify by guo.jinjun at 2012-05-10 17:15:32
	
	/////////////////////add by guo.jinjun at 2012-05-11 10:47:14
	var layer = window.dialogArguments;	
	//alert('==[debug]=: '+layer);
	if(layer == null){
		var phoneVersionGrid = oper.initMenuGrid2();
		var phoneVersionPanel = oper.initInfoPanel2();
		oper.initGridMenu();
		oper.initGridEvent();
		oper.initFunGridEvent();
		oper.initGridEvent2();
		
		var otherPanel = new Ext.Panel({
			border: false,
			title: '其他APK管理',
			region: 'center',
			layout: 'border',
			items:[otherApkGrid, funGrid]//otherApkPanel]//modify by guo.jinjun at 2012-05-10 17:15:49
		}); 
		
		var phonePanel = new Ext.Panel({
			border: false,
			title: '手机版本信息管理',
			region: 'center',
			layout: 'border',
			items:[phoneVersionGrid, phoneVersionPanel]
		}); 
		
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[otherPanel, phonePanel]
		});
	}else{			    
		var tabs = new Ext.Panel({
			border: false,
			title: '其他APK管理',
			region: 'center',
			layout: 'border',
			items:[otherApkGrid],
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
		oper.initEvent(layer);
		
		switch(layer){
	    	case "2":				
				tabs.add(funGrid);
	    	case "1":
	    }
	}	
	 
	var westForm = new Ext.FormPanel({
		id:'westForm',
		region: 'west',
		frame:true,
		//title:'查询条件',
		//labelWidth: 80,
        collapsible:true,
        width:200,
        defaultType: 'textfield',
        items:[sysGrid]
	});
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[westForm,tabs]
	});

});

function Oper(){
	
	this.initEvent = function(layer){
		switch(layer){
			case "2":
				oper.initFunEvent();
				break;
    		case "1":
    			oper.initMenuEvent();
				break;
		}
		oper.initBtnEvent(layer);
	}	
	this.initMenuEvent = function(){
		Ext.getCmp('menuGrid').on("rowdblclick", function() {
			var selItem = new Array();
			selItem[0] = Ext.getCmp('sysGrid').getSelectionModel().getSelections(); 
			selItem[1] = Ext.getCmp('menuGrid').getSelectionModel().getSelections(); 
		    window.returnValue = selItem;
			window.close();
		});
	}
	this.initFunEvent = function(){		
		Ext.getCmp('menuGrid').selModel = new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec){
	            	qryFunData(rec.data.apkCode);      
                    Ext.getCmp('funGrid').getSelectionModel().selectRow(0);
	            }
	        }
	    });
		Ext.getCmp('funGrid').on("rowdblclick", function() {
			var selItem = new Array();
			selItem[0] = Ext.getCmp('sysGrid').getSelectionModel().getSelections(); 
			selItem[1] = Ext.getCmp('menuGrid').getSelectionModel().getSelections(); 
            selItem[2] = Ext.getCmp('funGrid').getSelectionModel().getSelections(); 
		    window.returnValue = selItem;
           	window.close();
   		});
	}
	this.initBtnEvent = function(layer){
		Ext.getCmp('submitBtn').on("click", function() {
					var selItem = new Array();
					selItem[0] = Ext.getCmp('sysGrid').getSelectionModel().getSelections(); 
                    selItem[1] = Ext.getCmp('sysGrid').getSelectionModel().getSelections(); 
					if(layer == "2"){
						selItem[2] = Ext.getCmp('funGrid').getSelectionModel().getSelections(); 
					}
				    window.returnValue = selItem;
	                window.close();
                });
	}
	//接入控制 或 业务系统管理 的 系统列表,取其系统编码	add by guo.jinjun at 2012-05-10 14:59:44
	this.initSysGrid = function(){
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
		    {header:'系统编码',dataIndex:'sysCode',width:90},
		    {header:'系统名称',dataIndex:'sysName',width:100}
		]);   
		column.menuDisabled=true;
		
		var gridView = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});		
		
		var grid = new Ext.grid.GridPanel({
			id:'sysGrid',
	        region: 'center',
	        collapsible:true, 
	        width:Ext.getBody().getSize().width,   
		    height:Ext.getBody().getSize().height,
		    title:'系统列表',
	        store: store,
	        //trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : gridView,
			cm:column,
			//tbar:initToolBars(),
			//bbar:pagingBar,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
	                	//var sysId = rec.data.sysId;
	                	//Ext.Msg.alert('Message', sysId);
	                    //Ext.getCmp('detailForm').getForm().loadRecord(rec);
	                    qryAPKData(rec.data.sysCode);      
	                    Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
	                }
				}
			})
	    });
	    store.load({params:{start:0, limit:50}});
		
	    return grid;
	}
	function qryAPKData(sysCode){ 
		Ext.getCmp('menuGrid').store.on('beforeload', function(store){
			if(Ext.getCmp('menuGrid').store.lastOptions != null){
				Ext.getCmp('menuGrid').store.baseParams = {sysCode:sysCode};
			}
		});
		Ext.getCmp('menuGrid').store.on('load', function(store){      
                 	Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
		});
		Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load({params:{start:0, limit:50}});
	}
	
	//APK管理列表初始化


	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments', 'sysCode', 'openClass'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelOtherApkManagerAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入


	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		//创建列'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments' 
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'apk标识',dataIndex:'apkId',hidden:true },
		    {header:'备注',dataIndex:'comments',hidden:true },		    
		    {header:'系统编码',dataIndex:'sysCode',hidden:true },
		    {header:'apk编码',dataIndex:'apkCode',width:100 },
		    {header:'apk包名',dataIndex:'apkPackage',width:150},
		    {header:'版本号',dataIndex:'apkVersionNo',width:70 },
		    {header:'是否强制升级',dataIndex:'forceUpdate',width:90,renderer:function(v){return v=='1'?'是':'否';}},
		    {header:'apk名称',dataIndex:'apkUrl',width:200},
		    {header:'开放类名',dataIndex:'openClass',width:200 },
		    {header:'创建时间',dataIndex:'createDate',width:150}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'其他APK列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
	                    //qryFunData(rec.data.apkCode);      
	                    //Ext.getCmp('funGrid').getSelectionModel().selectRow(0);
		            }
		        }
		    })
	    });
		//menuStore.load({params:{moduleId:0}});//根据moduleId来载入


		return menuGrid;
	}
	function qryFunData(apkCode){ 
		Ext.getCmp('funGrid').store.on('beforeload', function(store){
			if(Ext.getCmp('funGrid').store.lastOptions != null){
				Ext.getCmp('funGrid').store.baseParams = {apkCode:apkCode};
			}
		});
		Ext.getCmp('funGrid').store.on('load', function(store){      
                 	Ext.getCmp('funGrid').getSelectionModel().selectRow(0);
		});
		Ext.getCmp('funGrid').store.removeAll() ;
		Ext.getCmp('funGrid').store.load({params:{start:0, limit:50}});
	}
	
	//////////////////////////////////////
	//其他apk功能列表
	this.initFunGrid = function(){
		var funStore = new Ext.data.JsonStore({
			id: 'funStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'funId', 'apkCode', 'funCode', 'funName', 'funClass'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelMobileApkFunctionAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入


	        listeners:{
	        	load: function(store){
					Ext.getCmp('funGrid').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		//创建列'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments' 
		var funColumn = new Ext.grid.ColumnModel([
		    {header:'功能ID',dataIndex:'funId',hidden:true },
		    {header:'APK编码',dataIndex:'apkCode',hidden:true },		 
		    {header:'功能编码',dataIndex:'funCode',width:100 },
		    {header:'功能名称',dataIndex:'funName',width:150},
		    {header:'功能类名',dataIndex:'funClass',width:300 }
		]);   
		funColumn.defaultSortable = true;
		
		var funGrid = new Ext.grid.GridPanel({
			id: 'funGrid',
	        region: 'south',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'功能列表',
	        store: funStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:funColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            }
		        }
		    })
	    });
		//funStore.load({params:{moduleId:0}});//根据moduleId来载入


		return funGrid;
	}
	//APK详情表单'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
	/* //废弃 modify by guo.jinjun at 2012-05-10 17:17:53
	this.initInfoPanel = function(){
		var infoPanel = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: 'APK详细信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.5,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.4,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: 'APK编码',
	                    name: 'p_apk_code',
	                    id: 'p_apk_code',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: 'apk包名',
	                    name: 'p_apk_package',
	                    id: 'p_apk_package',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '版本号',
	                    name: 'p_apk_version_no',
	                    id: 'p_apk_version_no',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '是否强制升级',
	                    name: 'p_force_update',
	                    id: 'p_force_update',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '创建时间',
	                    name: 'p_create_date',
	                    id: 'p_create_date',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: 'apk地址',
	                    name: 'p_apk_url',
	                    id: 'p_apk_url',
	                    anchor:'100%'
	                }]
	            }]
	        },{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'p_comments',
			    id: 'p_comments',
			    height : 130,
			    anchor:'100%'
			}]
	    });
		return infoPanel;	    
	}*/
	
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
		Ext.getCmp('menuGrid').selModel = new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec){
	            	qryFunData(rec.data.apkCode);      
                    Ext.getCmp('funGrid').getSelectionModel().selectRow(0);
	            }
	        }
	    });
		
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
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加APK' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改APK' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除APK' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', otherApkOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加APK' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.menuAdd = function(){
    	otherApkOper.showMenuInfoPage('add');
    }
    
    this.menuMod = function(){
    	otherApkOper.showMenuInfoPage('mod');
    }
    
    //apk功能 菜单	add by guo.jinjun at 2012-05-10 20:15:00
    ////////////////////////////////////////////////////////////////////////////////
    //定义APK管理列表事件
	this.initFunGridEvent = function(){
		Ext.getCmp('funGrid').store.removeAll() ;
		Ext.getCmp('funGrid').store.load();
		Ext.getCmp('funGrid').addListener('rowcontextmenu', oper.rightClickFn_fun);
		Ext.getCmp('funGrid').addListener('contextmenu', oper.nullRightClickFn_fun);
	}
	
	//APK管理组装
	this.rightClickFn_fun = function(funGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加功能' ,handler: function() {
			rightClick.hide();
			oper.funAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改功能' ,handler: function() {
			rightClick.hide();
			oper.funMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除功能' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', funOper.moduleDel);
		}}));
	
			
		Ext.getCmp('funGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn_fun = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加功能' ,handler: function() {
			nullRightClick.hide();
			oper.funAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.funAdd = function(){
    	funOper.showMenuInfoPage('add');
    }
    
    this.funMod = function(){
    	funOper.showMenuInfoPage('mod');
    }
    
    
    
/////////////////////////////////////////////////////////////////////////////////
    //手机版本信息列表初始化


	this.initMenuGrid2 = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore2',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'versionId', 'versionNo', 'versionUrl', 'forceUpdate','publicDate','picVersionNo','picVersionUrl','comments'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelMobilePhoneVersionAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入


	        listeners:{
	        	load: function(store){
					//Ext.getCmp('menuGrid2').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		//创建列'versionId', 'versionNo', 'versionUrl', 'forceUpdate','publicDate','picVersionNo','picVersionUrl','comments'
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'版本ID',dataIndex:'versionId',hidden:true },
		    {header:'更新内容',dataIndex:'comments',hidden:true },
		    {header:'最新版本号',dataIndex:'versionNo',width:100 },
		    {header:'下载地址',dataIndex:'versionUrl',width:200},
		    {header:'发布时间',dataIndex:'publicDate',width:150 },
		    {header:'是否强制升级',dataIndex:'forceUpdate',width:100,renderer:function(v){return v=='1'?'是':'否';}},
		    {header:'图片版本号',dataIndex:'picVersionNo',width:100},
		    {header:'图片地址',dataIndex:'picVersionUrl',width:200}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid2',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'手机版本信息列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	Ext.getCmp("v_version_no").setValue(rec.data.versionNo);
		            	Ext.getCmp("v_version_url").setValue(rec.data.versionUrl);
		            	Ext.getCmp("v_public_date").setValue(rec.data.publicDate);
		            	Ext.getCmp("v_pic_version_no").setValue(rec.data.picVersionNo);
		            	Ext.getCmp("v_pic_version_url").setValue(rec.data.picVersionUrl);
		            	Ext.getCmp("v_force_update").setValue(rec.data.forceUpdate);
		            	Ext.getCmp("v_comments").setValue(rec.data.comments);
		            }
		        }
		    })
	    });
		//menuStore.load({params:{moduleId:0}});//根据moduleId来载入


		return menuGrid;
	}
	
	this.initInfoPanel2 = function(){
		var infoPanel = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: '手机版本详细信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.5,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.4,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '最新版本号',
	                    name: 'v_version_no',
	                    id: 'v_version_no',
	                    readOnly: true,
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '下载地址',
	                    name: 'v_version_url',
	                    id: 'v_version_url',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '是否强制升级',
	                    name: 'v_force_update',
	                    id: 'v_force_update',
	                    readOnly: true,
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '图片版本号',
	                    name: 'v_pic_version_no',
	                    id: 'v_pic_version_no',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '图片下载地址',
	                    name: 'v_pic_version_url',
	                    id: 'v_pic_version_url',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '版本发布时间',
	                    name: 'v_public_date',
	                    id: 'v_public_date',
	                    readOnly: true,
	                    anchor:'100%'
	                }]
	            }]
	        },{
			    xtype:'textarea',
			    fieldLabel: '更新内容',
			    name: 'v_comments',
			    id: 'v_comments',
			    height : 130,
	            readOnly: true,
			    anchor:'100%'
			}]
	    });
		return infoPanel;	    
	}

    
	//定义手机版本信息列表事件
	this.initGridEvent2 = function(){
		Ext.getCmp('menuGrid2').store.removeAll() ;
		Ext.getCmp('menuGrid2').store.load();
		Ext.getCmp('menuGrid2').addListener('rowcontextmenu', oper.rightClickFn2);
		Ext.getCmp('menuGrid2').addListener('contextmenu', oper.nullRightClickFn2);
	}
	
	//手机版本信息组装
	this.rightClickFn2 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加手机版本信息' ,handler: function() {
			rightClick.hide();
			oper.menuAdd2();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改手机版本信息' ,handler: function() {
			rightClick.hide();
			oper.menuMod2();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除手机版本信息' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', phoneVersionOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid2').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn2 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加手机版本信息' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd2();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //手机版本信息方法
    this.menuAdd2 = function(){
    	phoneVersionOper.showMenuInfoPage('add');
    }
    
    this.menuMod2 = function(){
    	phoneVersionOper.showMenuInfoPage('mod');
    }
    

}




</script>
