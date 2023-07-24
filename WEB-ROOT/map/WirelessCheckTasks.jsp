<%@ page contentType='text/html; charset=utf-8'%>
<html>
	<head>
		<title>APK管理</title>
		<%@ include file='../public/common.jsp'%>
		<%@ include file='../public/style.jsp'%>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
		<script language='javascript' src='js/otherApkMng.js'></script>
	</head>

	<body onContextMenu='return false;'
		background='../images/main/background.gif'
		style='width: 100%; height: 100%; overflow: hidden'>
			<div id='menuMngDiv'></div>				
	</body>
</html>

<script language='JScript'>
	
	//debugger;
	
	
Ext.apply(Ext.form.VTypes, {
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});

//add by guo.jinjun at 2012-08-02 17:21:07
/**
 *把浏览器高宽分成100份 
 */
var perWidth = Ext.getBody().getSize().width/100;
var perHeight = Ext.getBody().getSize().height/100;

var oper = new Oper();
var markers = [];
var paths = [];
var map = new Object();
var retMapDetail = new Object();
var retMapInst = new Object();

var selectGridItem = new Object();
var operator = "mod";

Ext.onReady(function(){
	
	//接入控制 或 业务系统管理 的 系统列表 add by guo.jinjun at 2012-05-10 15:15:36
	var tasksGrid = oper.initTasksGrid();	
	var detailForm = oper.initDetailForm();
	oper.initGridMenu();
	oper.initGridEvent();
	
	var tabs = new Ext.TabPanel({
		region: 'center',
		id : 'tabs',
	    activeTab: 1,
	    width: perWidth*80,   
	    height: perHeight*90, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[detailForm,
	    	{
	    		title: '地图',
	    		html: '<div style="width:100%;height:100%;border:1px solid gray" id="mapPanel"></div>'
	    	}]
	});
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[tasksGrid, tabs]
	});

	oper.initMap();

	map.addEventListener("tilesloaded", oper.showMakers);
	map.addEventListener("zoomend", oper.showMakers);
	map.addEventListener("moveend", oper.showMakers);
	
	tabs.setActiveTab(0);

});

function Oper(){
	
	//接入控制 或 业务系统管理 的 系统列表,取其系统编码	add by guo.jinjun at 2012-05-10 14:59:44
	this.initTasksGrid = function(){
		var store = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'workorderId', 'workorderTitle', 'workContent', 'workorderState', 'planBeginDate', 'planEndDate', 'wirelessCheckTemplateId',
	        	'templateName', 'checkContent'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/UnitjobWorkorderAction'
	        }),
	        listeners:{
	        	load: function(store){
					grid.getSelectionModel().selectRow(0);
				}
	        }
	    });
		
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'任务ID',dataIndex:'workorderId',hidden:true},
		    {header:'任务标题',dataIndex:'workorderTitle',width:120},
		    {header:'任务内容',dataIndex:'workContent',hidden:true},
		    {header:'任务状态',dataIndex:'workorderState',width:70},
		    {header:'开始日期',dataIndex:'planBeginDate',hidden:true},
		    {header:'结束日期',dataIndex:'planEndDate',hidden:true},
		    {header:'模板Id',dataIndex:'wirelessCheckTemplateId',hidden:true},
		    {header:'模板名称',dataIndex:'templateName',hidden:true},
		    {header:'模板内容',dataIndex:'checkContent',hidden:true}
		]);   
		column.menuDisabled=true;
		
		var gridView = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});		
		
		var grid = new Ext.grid.GridPanel({
			id:'tasksGrid',
	        region: 'west',
	        collapsible:true, 
	        width: perWidth*20,   
		    height: perHeight*90,
		    title:'任务列表',
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
	                    Ext.getCmp('detailForm').getForm().loadRecord(rec);
	                    operator = 'mod';
			    		Ext.getCmp('btn_ok').setText('修改');
			    		selectGridItem = Ext.getCmp('tasksGrid').getSelectionModel().getSelections();
	                    //Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
	                    //new Date(Date.parse(s.replace(/-/g,   "/")));  
	                    var begin = rec.data.planBeginDate.substring(0, rec.data.planBeginDate.length-2);
	                    Ext.getCmp('planBeginDate').setValue(new Date(Date.parse(begin.replace(/-/g,   "/"))));
	                    var end = rec.data.planBeginDate.substring(0, rec.data.planEndDate.length-2);
	                    Ext.getCmp('planEndDate').setValue(new Date(Date.parse(end.replace(/-/g,   "/"))));
	                    
	                    oper.searchWirelessCheck();
	                }
				}
			})
	    });
	    store.load({params:{start:0, limit:50}});
		
	    return grid;
	}

	//APK管理列表初始化

	this.initDetailForm = function(){
		var infoForm = new Ext.FormPanel({
	    	id:'detailForm',
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        title:'巡检详情',
	        collapsible:true, 
	        //store:formStore,
	        height:Ext.getBody().getSize().height*0.90,
	        width: perWidth*80,
	        layout:'table',
	        layoutConfig:{columns:2},//将父容器分成3列	
			autoScroll: true,
	        buttonAlign: 'center',
		    items:[{
				xtype:'panel',
				layout:'form',
	       	    colspan:2,
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'巡检任务标题',
		       		name:'workorderTitle',
		       		width: perWidth*60
		       		//disabled:true
				}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'巡检状态',
	       			name:'workorderState',
		       		width: perWidth*23,
		       		readOnly: true
	       			//disabled:true      		
	       		}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       		defaultType: 'textfield',
	       		items:[{
	       			xtype: 'hidden',
	       			fieldLabel:'巡检模板ID',
	       			name:'wirelessCheckTemplateId',
		       		width: perWidth*23
	       		}]
	       	},{
	       	    xtype:'panel',
	       		layout:'form',
	       		defaultType: 'textfield',
	       		items:[{
	       			xtype: 'datefield',
	        		fieldLabel:'开始日期',
	       			name:'planBeginDate',
	       			id:'planBeginDate',
	       			format :'Y-m-d',
		       		width: perWidth*23
	       			//disabled:true          		
	       		}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       		defaultType: 'textfield',
	       		items:[{
	       			xtype: 'datefield',
	       			fieldLabel:'结束日期',
	       			name:'planEndDate',
	       			id:'planEndDate',
		       		format :'Y-m-d',
		       		width: perWidth*23
	       		}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       	    colspan:2,
	       		defaultType: 'textarea',
	       		items:[{
	        		fieldLabel:'巡检任务内容',
	       			name:'workContent',
		       		width: perWidth*60,
		       		height: perHeight*30
	       			//disabled:true      		
	       		}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       	    colspan:2,
	       		defaultType: 'textfield',
	       		items:[{
	       			fieldLabel:'巡检模板名称',
	       			name:'templateName',
		       		width: perWidth*60,
		       		readOnly: true
	       		}]
	       	},{
	       		xtype:'panel',
	       		layout:'form',
	       	    colspan:2,
	       		defaultType: 'textarea',
	       		items:[{
	        		fieldLabel:'巡检模板内容',
	       			name:'checkContent',
		       		width: perWidth*60,
		       		height: perHeight*30,
		       		readOnly: true
	       			//disabled:true      		
	       		}]
	       	}],
	       	buttons: [{
	            text: '确定',
	            id: 'btn_ok',
	            listeners:{"click":function(){
	       			
	       		
	       			//debugger;
			    	if(!infoForm.getForm().isValid()){
			    		return ;
			    	}
			    	var resultStr ;
			    	
//			    	var objq = new Object();
//	    			objq.type = "selIsUnique";
//	    			objq.fristPage = '1';  
//	    			objq.muneId = selNode.id; 
//	    			objq.pageType = '0';
//	 				var tmpObj = invokeAction("/mobsystem/InsertMobileApkFunctionAction", objq);
//	 				if(tmpObj.totalRecords > 1 && Ext.getCmp("m_frist_page").getValue().inputValue == '1'){
//					  	Ext.MessageBox.show({
//				          	title: '提示',
//					        msg: '只能允许一个(首页)存在，请重新指定！',
//					        buttons: Ext.MessageBox.OK,
//					        width:200,
//					        icon: Ext.MessageBox.ERROR
//					    });
//						return;
//					}			    	
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';

			    			var inputParams = infoForm.getForm().getValues();
							inputParams.planBeginDate = Ext.getCmp('planBeginDate').getValue();
							inputParams.planEndDate = Ext.getCmp('planEndDate').getValue();
							//inputParams.workorderCode = "000";
							//inputParams.dispatchOrgId = "0";
							//inputParams.dispatchOrgName = "000";
							//inputParams.dispatchStaffId = "0";
							//inputParams.dispatchStaffName = "000";
							//inputParams.dispatchType = "0";
							//inputParams.woSourceType = "0";
							//inputParams.curType = "0";
							//inputParams.woSourceId = "0";
							//inputParams.stateDate = new Date();
							//inputParams.createDate = new Date();
							inputParams.state = "1";
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/UnitjobWorkorderAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = infoForm.getForm().getValues();
			    			inputParams.workorderId = selectGridItem[0].data.workorderId;
							inputParams.planBeginDate = Ext.getCmp('planBeginDate').getValue();
							inputParams.planEndDate = Ext.getCmp('planEndDate').getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/UnitjobWorkorderAction", inputParams);
							
			    			break ;
			    		}
			    	}
			    	operator = 'mod';
			    	Ext.getCmp('btn_ok').setText('修改');
			    	
			        Ext.MessageBox.show({
			           	msg: '系统正在提交数据……',
			           	progressText: 'Saving...',
			           	width:300,
			           	wait:true,
			           	waitConfig: {interval:100},
			           	icon:'ext-mb-download'
			       	});
			        setTimeout(function(){
			            Ext.MessageBox.hide();
			            Ext.example.msg('',resultStr);
			        }, 1000);
			        
			        Ext.getCmp('tasksGrid').store.removeAll();
			        Ext.getCmp('tasksGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	Ext.getCmp('tasksGrid').store.removeAll();
			        Ext.getCmp('tasksGrid').store.reload()
			    }}
	        }]
		}); 

		return infoForm;
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
		Ext.getCmp('tasksGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('tasksGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//APK管理组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加任务' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		//rightClick.insert(i++,new Ext.menu.Item({ text: '修改APK' ,handler: function() {
		//	rightClick.hide();
		//	oper.menuMod();
		//}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除任务' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', otherApkOper.moduleDel);
		}}));
	
			
		Ext.getCmp('tasksGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加任务' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.menuAdd = function(){
    	operator = 'add';
		Ext.getCmp('btn_ok').setText('添加');
		Ext.getCmp('detailForm').getForm().reset();
    }
    
    this.menuDel = function(){
    	
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////
    /**
     * 地图
     */
	this.initMap = function() {
		
		map = new BMap.Map('mapPanel'); // 创建Map实例
		var point = new BMap.Point(113.266897, 23.135782); // 创建点坐标
		map.centerAndZoom(point, 15); // 初始化地图,设置中心点坐标和地图级别。
		var myCity = new BMap.LocalCity();//根据IP地位地图
		myCity.get(oper.myFun);

		map.enableScrollWheelZoom(); // 启用滚轮放大缩小。
		map.enableKeyboard();
	}
    
    this.searchWirelessCheck = function() {
		var objq = new Object();
		markers = [];
		paths = [];
		
		objq.flag = "searchWirelessCheck";
		objq.wirelessCheckTemplateId = selectGridItem[0].data.wirelessCheckTemplateId;
		objq.isFinish = selectGridItem[0].data.workorderState;
		map.clearOverlays();//移除所有标注
		retMapDetail = invokeAction("/mobsystem/WirelessCheckDetailManaAction", objq);
		oper.addPaths(retMapDetail,"blue");
		oper.addMarkers(retMapDetail);
		
		objq = new Object;
		objq.flag = "searchWirelessCheck";
		objq.unitjobWorkorderIdId = selectGridItem[0].data.workorderId;
		objq.isFinish = selectGridItem[0].data.workorderState;
		objq.planTaskId = "0";
		retMapInst = invokeAction("/mobsystem/WirelessCheckInstManaAction", objq);
		oper.addPaths(retMapInst,"green");
		oper.addMarkers(retMapInst);
		oper.showMakers();
	}
	//添加路线
	this.addPaths = function(retMap, color){
		for ( var j = 1; j < retMap.length; j++) {
			var point1 = new BMap.Point(retMap[j-1].coordinateY, retMap[j-1].coordinateX);
			var point2 = new BMap.Point(retMap[j].coordinateY, retMap[j].coordinateX);
			var polyline = new BMap.Polyline([
			  	point1,
			  	point2
			], 
			{
				strokeColor:color, 
				strokeWeight:6, 
				strokeOpacity:0.5
			});
			paths[paths.length] = polyline;
			map.addOverlay(polyline);
		}
	}
	//把查询到的标注保存到makers数组中
	this.addMarkers = function(retMap) {
		for ( var i = 0; i < retMap.length; i++) {
			var point = new BMap.Point(retMap[i].coordinateY, retMap[i].coordinateX);
			if (i == 0) {
				map.centerAndZoom(point, 15);
			}
			var labelText = retMap[i].comments;
			var marker = new BMap.Marker(point);
			marker.enableMassClear();//允许覆盖物在map.clearOverlays方法中被清除。
			var infoWindow = new BMap.InfoWindow(labelText, opts); // 创建信息窗口对象
			marker.addEventListener("click", function() {
				this.openInfoWindow(infoWindow);
			});
			markers[markers.length] = marker;
		}
		
	}
	this.showbySelect = function(){
		map.clearOverlays();//移除所有标注		
		markers = [];
		paths = [];

		var deviceTyeList = document.getElementsByName("deviceTypes");
		for ( var i = 0; i < deviceTyeList.length; i++) {
			if (deviceTyeList[i].checked) {
				switch(deviceTyeList[i].value){
				case "1":
					oper.addPaths(retMapDetail,"blue");
					oper.addMarkers(retMapDetail);
					break;
				case "2":
					oper.addPaths(retMapInst,"green");
					oper.addMarkers(retMapInst);
					break;
				}
			}			
		}
		oper.showMakers();
	}
	
	this.showMakers = function() {
		for ( var i = 0; i < markers.length; i++) {
			var result = BMapLib.GeoUtils.isPointInRect(markers[i].point, map.getBounds());
			if (result == true) { //在可视范围内则增加相应标注
				map.addOverlay(markers[i]);
			} else { //不在可视范围内则删除相应标注
				map.removeOverlay(markers[i]);
			}
		}
	}
	var opts = {
		width : 250, // 信息窗口宽度
		height : 100, // 信息窗口高度
		title : "设备信息" // 信息窗口标题
	}
	this.myFun = function(result) {
		var cityName = result.name;
		map.setCenter(cityName);
	}
    

}



</script>
