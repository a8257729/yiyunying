<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.* " %>  
<%@ page import=",java.io.*" %>  
<%@ page import="java.util.*" %> 
<html>
	<head>
		<title>终端框架应用管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/mobileFrameAppMng.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
			<div id='menuMngDiv'></div>				
	</body>
</html>

<script language="JScript">

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
var oper = new Oper();
var mobileFrameAppOper = new MobileFrameAppOper();
var startRow = 0;
var rowIndex = 0;
<%  
	Properties pro = new Properties();  
	String realpath = request.getRealPath("/");  
	try{  
	FileInputStream in = new FileInputStream(realpath+"//WEB-INF/application.properties");  
	pro.load(in);  
	}  
	catch(FileNotFoundException e){  
	    out.println(e);  
	}  
	catch(IOException e){out.println(e);}  
	String url = pro.getProperty("server.url");  
	System.out.println(url);  
%> 
Ext.onReady(function(){
	
	var otherApkGrid = oper.initMenuGrid();
	var otherApkPanel = oper.initInfoPanel();
	oper.initGridMenu();
	oper.initGridEvent();
	
	var otherPanel = new Ext.Panel({
		border: false,
		title: '终端框架应用管理',
		region: 'center',
		layout: 'border',
		items:[otherApkGrid, otherApkPanel]
	}); 
	
	var tabs = new Ext.TabPanel({
		region: 'center',
		id : 'tabs',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.85,   
	    height:Ext.getBody().getSize().height*0.6, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[otherPanel]
	});
	
	var qryPanel = oper.initQryPanel();
	
	var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,tabs]
	 	});
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[mainPanel]
	});
	
	//var viewport = new Ext.Viewport({
	//	el:'menuMngDiv',
	//	layout: 'border',
	//	items:[tabs]
	//});

});

function Oper(){

	this.initQryPanel = function(){
	    var yesOrNo  = new Ext.data.JsonStore({ 
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/YES_OR_NO'
	        })
		});
	        
		var appOsType  = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/OS_TYPE'
	        })
		});
		
	    var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
		region: 'north',
		frame:true,
		title: '查询条件',
	    bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.08,
        collapsible:true,
        height:Ext.getBody().getSize().height*0.2,
        width:Ext.getBody().getSize().width*0.8,
        buttons:[{
            text: '查询',onClick:doQry},{text: '重置',onClick:reset
        },{text: '短信推送',onClick:pushMsg}
        ],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'combo',
                    fieldLabel: '系统类型',
                    name: 'osType',
                    id: 'q_os_type',
                    valueField: 'mcode',
                    displayField: 'mname',
                    blankText:'请选择系统类型',
                    emptyText:'选择系统类型',
                    mode: 'remote',
                    triggerAction: 'all',
                    store: appOsType,
                    allowBlank: false,
                    anchor:'100%'
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'combo',
                    fieldLabel: '是否发布版本',
                    name: 'isRelease',
                    id: 'q_is_release',
                    valueField: 'mcode',
                    displayField: 'mname',
                    blankText:'请选择',
                    emptyText:'请选择',
                    mode: 'remote',
                    triggerAction: 'all',
                    value:'',
                    store: yesOrNo,
                    allowBlank: false,
                    anchor:'100%'
                }]
            }]
        }]
	});
	   
       return qryPanel;
	}
	
	function doQry(){
		var varOsType = Ext.getCmp('q_os_type').getValue();
		
		var varIsRelease = Ext.getCmp('q_is_release').getValue();
		Ext.getCmp('menuGrid2').store.removeAll();
		Ext.getCmp('menuGrid2').store.load({params:{start:0, limit:5,osType:varOsType, isRelease:varIsRelease}});
	}
	
	function reset() {
		Ext.getCmp('q_os_type').setValue('');
		Ext.getCmp('q_is_release').setValue('');
	}
	
	function pushMsg() {
		var _obj = new Object();
		_obj.staffId=0;
		_obj.jobId=0;
		_obj.defaultJobId=0;
		// 服务器地址
		var serverUrl = "<%=url%>";
		_obj.appUrl = serverUrl + "MOBILE/api/web/downloads/frame/app/latest/android";
		window.showModalDialog("../system/SelStaffForPushMsg.jsp", _obj, "dialogWidth:680px;dialogHeight:660px;status:no");
	}
	
	//APK管理列表初始化

	this.initMenuGrid = function(){
	/*	var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'frameAppId', 'name', 'iconUrl', 'versionCode',
	        	'versionName','filePath','osType','downloadUrl',
	        	'qrcodeUrl','downloadCount','updateTime','updateLog',
	        	'buildTime','memo','isRelease'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QryMobileFrameAppAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入

	        listeners:{
	        	load: function(store){
					//Ext.getCmp('menuGrid2').getSelectionModel().selectFirstRow();
				}
	        }
	    });
	    */
		//创建列'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'应用ID',dataIndex:'frameAppId',hidden:true },
		    {header:'图标地址',dataIndex:'iconUrl',hidden:true },
		    {header:'文件地址',dataIndex:'filePath',hidden:true },
		    {header:'应用名称',dataIndex:'name',width:100 },
		    {header:'内部版本号',dataIndex:'versionCode',width:90},
		    {header:'外部版本',dataIndex:'versionName',width:100 },
		    {header:'系统类型',dataIndex:'osType',width:100,renderer:function(v){return v=='1'?'Android':'iOS';}},
		    {header:'下载地址',dataIndex:'downloadUrl',width:320,renderer:DomUrl},
		    {header:'是否是发布版',dataIndex:'isRelease',width:80,renderer:function(v){return v=='0'?'否':'是';}},
		    {header:'二维码地址',dataIndex:'qrcodeUrl',width:80,hidden:true},
		    {header:'下载次数',dataIndex:'downloadCount',width:90},
		    {header:'更新时间',dataIndex:'updateTime',width:110},
		    {header:'创建时间',dataIndex:'buildTime',hidden:true },
		    {header:'更新日志',dataIndex:'updateLog',width:200},
		    {header:'备注',dataIndex:'memo',width:100}
		]);   
		//menuColumn.defaultSortable = true;
		
		var menuGrid = new ZTESOFT.Grid({
			id: 'menuGrid2',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.6,
		    title:'终端框架信息列表',
	        //store: menuStore,
	        //trackMouseOver:false,
	        //autoScroll: true,
	        //loadMask: true,
			cm:menuColumn,
			pageSize:5,
			paging:true,
			url: '/MOBILE/ExtServlet?actionName=system/QryMobileFrameAppAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            	Ext.getCmp("v_frame_name").setValue(rec.data.name);
		            	Ext.getCmp("v_version_code").setValue(rec.data.versionCode);
		            	Ext.getCmp("v_version_name").setValue(rec.data.versionName);
		            	Ext.getCmp("v_icon_url").setValue(rec.data.iconUrl);
		            //	Ext.getCmp("v_file_path").setValue(rec.data.filePath);
		            	Ext.getCmp("v_os_type").setValue(rec.data.osType=='1'?'Android':'iOS');
		            	Ext.getCmp("v_download_url").setValue(rec.data.downloadUrl);
		            	Ext.getCmp("v_qrcode_url").setValue(rec.data.qrcodeUrl);
		            	Ext.getCmp("v_download_count").setValue(rec.data.downloadCount);
		            	Ext.getCmp("v_update_time").setValue(rec.data.updateTime);
		            	Ext.getCmp("v_update_log").setValue(rec.data.updateLog);
		            	Ext.getCmp("v_build_time").setValue(rec.data.buildTime);
		            	Ext.getCmp("v_memo").setValue(rec.data.memo);
		            	Ext.getCmp("v_is_release").setValue(rec.data.isRelease=='1'?'是':'否');
		            }
		        }
		    })
	    });

		return menuGrid;
	}
	
	this.initInfoPanel = function(){
		var infoPanel = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: '手机版本详细信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.35,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.3,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '应用名称',
	                    name: 'v_frame_name',
	                    id: 'v_frame_name',
	                    readOnly: true,
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '版本号',
	                    name: 'v_version_code',
	                    id: 'v_version_code',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '版本名称',
	                    name: 'v_version_name',
	                    id: 'v_version_name',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '是否发布版本',
	                    name: 'v_is_release',
	                    id: 'v_is_release',
	                    readOnly: true,
	                    anchor:'100%'
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.3,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '图标地址',
	                    name: 'v_icon_url',
	                    id: 'v_icon_url',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '下载地址',
	                    name: 'v_download_url',
	                    id: 'v_download_url',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '二维码下载地址',
	                    name: 'v_qrcode_url',
	                    id: 'v_qrcode_url',
	                    readOnly: true,
	                    anchor:'100%'
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.3,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '下载次数',
	                    name: 'v_download_count',
	                    id: 'v_download_count',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '系统类型',
	                    name: 'v_os_type',
	                    id: 'v_os_type',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '更新时间',
	                    name: 'v_update_time',
	                    id: 'v_update_time',
	                    readOnly: true,
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '创建时间',
	                    name: 'v_build_time',
	                    id: 'v_build_time',
	                    readOnly: true,
	                    anchor:'100%'
	                }]
	                }]
	        }
	        ,{
			    xtype:'textarea',
			    fieldLabel: '更新日志',
			    name: 'v_update_log',
			    id: 'v_update_log',
			    height : 30,
	            readOnly: true,
			    anchor:'100%'
			}
			,{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'v_memo',
			    id: 'v_memo',
			    height : 30,
	            readOnly: true,
			    anchor:'100%'
			}
			]
	    });
		return infoPanel;	    
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
    
	//定义手机版本信息列表事件
	this.initGridEvent = function(){
		Ext.getCmp('menuGrid2').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('menuGrid2').store.lastOptions != null){
					   Ext.getCmp('menuGrid2').store.baseParams = {osType:1};
					}
				}
		    )
		Ext.getCmp('menuGrid2').store.removeAll() ;
		Ext.getCmp('menuGrid2').store.load({params:{start:0, limit:5}});
		Ext.getCmp('menuGrid2').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid2').addListener('contextmenu', oper.nullRightClickFn);
		//Ext.getCmp('menuGrid2').addListener('cellclick', oper.leftClickFn);
		//Ext.getCmp('menuGrid2').addListener('cellcontextmenu', oper.cellclick);
	}
	
	this.leftClickFn = function(menuGrid2,rowIndex,e){
		var record = menuGrid2.getStore().getAt(rowIndex);
		info = record.get("downloadUrl");
		alert('onClick.' + info);
	}
	
	//获取选中行选中列的值  
	this.cellclick = function(menuGrid2, rowIndex, columnIndex, e) {   
		var record = menuGrid2.getStore().getAt(rowIndex);
		var fieldName = menuGrid2.getColumnModel().getDataIndex(columnIndex);
	       //info为一个全局变量 
		info = record.get(fieldName);
		alert('onClick.' + info);
	}
	
	//手机版本信息组装
	this.rightClickFn = function(menuGrid2,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加终端版本信息' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改终端版本信息' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除终端版本信息' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', mobileFrameAppOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid2').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加终端版本信息' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.menuAdd = function(){
    	mobileFrameAppOper.showMenuInfoPage('add');
    }
    
    this.menuMod = function(){
    	mobileFrameAppOper.showMenuInfoPage('mod');
    }
    
    this.downloadApk = function() {
    	alert('this.downloadApk');
    }

}

function DomUrl(value) {
		startRow++;
		var rowIndexs = Ext.getCmp('menuGrid2').getStore().getCount();
		if (startRow > rowIndexs) {
			startRow = 1;
		}
		var row = Ext.getCmp('menuGrid2').getSelectionModel().selectRow(startRow-1);
		var rownum = Ext.getCmp('menuGrid2').getSelectionModel().getSelected();
		
		//var strUrl = rownum.get("downloadUrl");
		var frameAppId = rownum.get("frameAppId");
		var downloadCount = rownum.get("downloadCount");
		var strUrl = "/MOBILE/api/server/downloads/appFrame/" + frameAppId;
		return "<a href='" + strUrl + "' onClick='urlClick(" + frameAppId + "," + downloadCount + ")'>" + value + "</a>";
//		return "<a href='" + strUrl + ">" + value + "</a>";
	}

function urlClick(frameAppId,downloadCount) {
	//alert('urlClick' + frameAppId + downloadCount);
//	var inputParams = new Object();
//	inputParams.frameAppId = frameAppId;
//	inputParams.downloadCount = downloadCount ;
//	inputParams.type = "incDownCount";
//	
//	var retMap = invokeAction("/appmanage/MobileFrameAppAction", inputParams);
//	
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
	Ext.getCmp('menuGrid2').store.removeAll();
	Ext.getCmp('menuGrid2').store.reload();
}

</script>
