<%@ page contentType='text/html; charset=utf-8'%>
<html>
	<head>
		<title>XML转JSON管理</title>
		<%@ include file='../public/common.jsp'%>
		<%@ include file='../public/style.jsp'%>	
		<script language="javascript" src="js/otherSysMng.js"></script>
		<script language="javascript" src="js/interfaceMng.js"></script>
		<script language='javascript' src='js/selectMng.js'></script>
        <script language="javascript" src="js/wsXmlUpload.js"></script>
	</head>

	<body onContextMenu='return false;'
		background='../images/main/background.gif'
		style='width: 100%; height: 100%; overflow: hidden'>
			<div id='sysGird'></div>				
	</body>
</html>

<script language='JScript'>
var otherSysOper = new OtherSysOper();
var interfaceOper = new InterfaceManagerOper();
var wsXmlUpload = new WsXmlUpload();
var selOper = new SelOper();
var operator = 'add';
var session1 = GetSession();

Ext.onReady(function(){
	
	var sysStore = new Ext.data.JsonStore({
		root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: ['sysCode', 'sysName'],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
        }),
        baseParams:{start:0, limit:20}
    });

    sysStore.load({params:{start:0, limit:20}});
 
	var qryForm1 = new Ext.FormPanel({
		id:'qryForm1',
		region: 'west',
		frame:true,
		title:'查询条件',
		labelWidth: 60,
        collapsible:true,
        width:200,
        items:[{
				xtype:'combo',
				fieldLabel: '系统名称',
				name: 'v_sysCode',
				id: 'v_sysCode',
				valueField: 'sysCode',
				displayField: 'sysName',
				width:120,
				mode:'remote',
				triggerAction: 'all',
				forceSelection: true,
				editable :false,
				store:sysStore
			},{
           		xtype : 'button',
				text : '查询',
				handler : function(){
					var v = Ext.getCmp('v_sysCode').getValue();
	        		if(v == null || v == '')return;
	        		else qryOtherSysData(v);
				}
            }]
	});
	
	function qryOtherSysData(sysCode){
        Ext.getCmp('otherSysGrid').store.on('beforeload',
            function(store){
                if(Ext.getCmp('otherSysGrid').store.lastOptions != null){

                    Ext.getCmp('otherSysGrid').store.baseParams = {sysCode:sysCode};
                }
            }
        )
        Ext.getCmp('otherSysGrid').store.on('load',
            function(store){
                Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(0);
            }
        )
        Ext.getCmp('otherSysGrid').store.removeAll() ;
        Ext.getCmp('otherSysGrid').store.load({params:{start:0, limit:10}});
	}

 /**
  * 系统接口地址表

  */
  
function renderInterfaceVersion(v){
	switch(v){
		case null:
			return '无';
			break;
		case '1':
			return '方式1';
			break;
		case '2': 
			return '方式2';
			break;
		case '3': 
			return '方式3';
			break;
		default: 
			return 'error';
	}
}
function renderMethodType(v){
	switch(v){
	
		//case '1':
			//return '类名';
			//break;
		case '2': 
			return '接口';
			break;
		case '3': 
			return 'servlet';
			break;
		default: 
			return 'error';
	}
}
	//列表初始化


	function initOtherSysGrid(){
		//创建列
		//'sysotherSysId', 'systemCode', 'methodType','methodotherSys','interfaceVersion','apkCode'
		var column = new Ext.grid.ColumnModel([
		    {header:'接口地址ID',dataIndex:'sysAddressId',hidden:true },		    
		    {header:'系统编码',dataIndex:'systemCode',hidden:true },
		    {header:'接口编码',dataIndex:'interfaceCode',width:100 },
		    {header:'方法类型',dataIndex:'methodType',width:100,renderer:function(v){return renderMethodType(v);}},
		    {header:'方法地址',dataIndex:'methodAddress',width:200 },
		    {header:'调用方式',dataIndex:'interfaceVersion',width:100,renderer:function(v){return renderInterfaceVersion(v);}},
		    {header:'空间名称',dataIndex:'interfaceNamespace',width:100 },
		    {header:'数据格式',dataIndex:'dataType',width:80,renderer:function(v){return v=='1'?'Json格式':'Xml格式';} },
		    {header:'接口方法',dataIndex:'interfaceMethod',width:100 },
		    {header:'APK编码',dataIndex:'apkCode',width:100 }	    
		]);
		
		var gridPanel = new ZTESOFT.Grid({
	    	id:'otherSysGrid',
	    	title : '系统接口地址表',
	    	region: 'north',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:10,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelOtherSysAction',
	        sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	qryInterfaceGridData(rec.data.sysAddressId);
		            }
		        }
		    })
			
		});
		return gridPanel;
    }
    
    function qryInterfaceGridData(sysAddressId){
        Ext.getCmp('interfaceGrid').store.on('beforeload',
            function(store){
                if(Ext.getCmp('interfaceGrid').store.lastOptions != null){

                    Ext.getCmp('interfaceGrid').store.baseParams = {sysAddressId:sysAddressId};
                }
            }
        );
        Ext.getCmp('interfaceGrid').store.on('load',
            function(store){
                Ext.getCmp('interfaceGrid').getSelectionModel().selectRow(0);
            }
        );
        Ext.getCmp('interfaceGrid').store.removeAll() ;
        Ext.getCmp('interfaceGrid').store.load({params:{start:0, limit:10}});
	}
    
	    //定义菜单列表菜单
    function initGridMenu(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	function initGridEvent(){
		initGridMenu();
		Ext.getCmp('otherSysGrid').addListener('rowcontextmenu', rightClickFn);
		Ext.getCmp('otherSysGrid').addListener('contextmenu', nullRightClickFn);
	}
	
	//菜单组装
	function rightClickFn(otherSysGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加接口' ,handler: function() {
			rightClick.hide();
			otherSysAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改接口' ,handler: function() {
			rightClick.hide();
			otherSysMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除接口' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', otherSysOper.moduleDel);
		}}));
		
		Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	function nullRightClickFn(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加接口' ,handler: function() {
			nullRightClick.hide();
			otherSysAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

	//菜单方法
    function otherSysAdd(){
    	otherSysOper.showMenuInfoPage('add', Ext.getCmp('v_sysCode').getValue());
    }
    
    function otherSysMod(){
    	otherSysOper.showMenuInfoPage('mod', Ext.getCmp('v_sysCode').getValue());
    }
    

	//接口映射配置
    ///////////////////////////////////////
    function initInterfacePanel(){
		var column = new Ext.grid.ColumnModel([
			{header:'接口映射ID',dataIndex:'mappingId',hidden:true },
		    {header:'系统接口ID',dataIndex:'sysAddressId',hidden:true },
		    {header:'映射编码',dataIndex:'mappingCode',width:150},	  
		    {header:'接口方法类型',dataIndex:'mappingName',width:150 },  
		    {header:'接口参数名',dataIndex:'mappingMethod',width:150},
		    {header:'业务接口名称',dataIndex:'intefaceName',width:150},
		    {header:'业务接口方法',dataIndex:'intefaceMethod',width:150},
		    {header:'业务接口参数',dataIndex:'intefaceParams',width:150},
		    {header:'描述',dataIndex:'comments',width:150}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'interfaceGrid',
	    	title : '接口映射管理',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelMobInterfaceAction',
	        sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	qryXmljsonData(rec.data.mappingCode);
		            }
		        }
		    })
			
		});		
		return gridPanel;
    }
	
	function qryXmljsonData(mappingCode){
		var inputParams = new Object();
		inputParams.type = 'sel';
		inputParams.mappingCode = mappingCode==null?0:mappingCode;
		var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
		if(retMap.totalSize == '0' || retMap.totalSize == 0){
			operator = 'add';
			detailForm.getForm().reset();
		}
		else {
			operator = 'mod';
			detailForm.getForm().setValues(retMap.resultList[0]);
		}
	}
	
	//定义接口映射配置列表事件
	function initInterfaceGridEvent(){
		Ext.getCmp('interfaceGrid').addListener('rowcontextmenu', rightClickFn2);
		Ext.getCmp('interfaceGrid').addListener('contextmenu', nullRightClickFn2);
	}
	
	//接口映射配置组装
	function rightClickFn2(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '接口映射配置' ,handler: function() {
			rightClick.hide();
			interfaceAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改接口映射' ,handler: function() {
			rightClick.hide();
			interfaceMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除接口映射' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', interfaceOper.moduleDel);
		}}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '上传请求报文XML格式' ,handler: function() {
            rightClick.hide();
            wsXmlUploadView('req_up');
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '下载请求报文XML格式' ,handler: function() {
            rightClick.hide();
            wsXmlDownloadView('req_down');
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '上传响应报文XML格式' ,handler: function() {
            rightClick.hide();
            wsXmlUploadView('rep_up');
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '下载响应报文XML格式' ,handler: function() {
            rightClick.hide();
            wsXmlDownloadView('rep_down');
        }}));
		
		Ext.getCmp('interfaceGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	function nullRightClickFn2(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '接口映射配置' ,handler: function() {
			nullRightClick.hide();
			interfaceAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

      //接口映射配置操作方法
    function interfaceAdd(){
            interfaceOper.showMenuInfoPage('add');
    }
    function interfaceMod(){
            interfaceOper.showMenuInfoPage('mod');
    }
   
	
	var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [["listdata",'listdata'],["listdata1",'listdata1'],["listdata2",'listdata2'],
	["result",'result'],["bodyresult",'bodyresult'],["totalpage",'totalpage'],["pagesize",'pagesize'],
	["errordesc",'errordesc'],["pagenum",'pagenum'],["totalnum",'totalnum']]});
    
    var detailForm = new Ext.FormPanel({
    	id:'xml2jsonForm',
		region: 'south',
		labelAlign: 'left',
	 	frame:true,
        title:'XML转JSON管理',
        collapsible:true, 
        height:Ext.getBody().getSize().height*0.4,
        width:Ext.getBody().getSize().width*0.85,
        layout:'table',
        layoutConfig:{columns:4},
		autoScroll: true,
		defaults : {
       		//labelAlign: 'top'
       		labelWidth : 90
       		//width : 200
       	},
	    items:[{
			xtype:'panel',
			colspan:2,
			//width:Ext.getBody().getSize().width*0.75,
			layout:'form',
			defaultType: 'textfield',
       		items:[{
				xtype: 'hidden',
               	name: 'filedMappingId',
                id: 'm_filedMappingId'
			}]
       	},{
			xtype:'panel',
			colspan:2,
			//width:Ext.getBody().getSize().width*0.75,
			layout:'form',
			defaultType: 'textfield',
       		items:[{
				xtype: 'hidden',
               	name: 'mappingCode',
                id: 'n_mapping_code'
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段1',
               	id : 'm_system_fileds',
               	name : 'systemFileds',               	
				allowBlank: false,
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段1',
                id : 'm_mapping_fileds',
                name : 'mappingFileds',
                valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段2',
               	id : 'm_system_fileds2',
               	name : 'systemFileds2',
               	width : 270
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段2',
                id : 'm_mapping_fileds2',
                name : 'mappingFileds2',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段3',
               	id : 'm_system_fileds3',
               	name : 'systemFileds3',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段3',
                id : 'm_mapping_fileds3',
                name : 'mappingFileds3',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段4',
               	id : 'm_system_fileds4',
               	name : 'systemFileds4',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段4',
                id : 'm_mapping_fileds4',
                name : 'mappingFileds4',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段5',
               	id : 'm_system_fileds5',
               	name : 'systemFileds5',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段5',
                id : 'm_mapping_fileds5',
                name : 'mappingFileds5',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段6',
               	id : 'm_system_fileds6',
               	name : 'systemFileds6',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段6',
                id : 'm_mapping_fileds6',
                name : 'mappingFileds6',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段7',
               	id : 'm_system_fileds7',
               	name : 'systemFileds7',
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段7',
                id : 'm_mapping_fileds7',
                name : 'mappingFileds7',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段8',
               	id : 'm_system_fileds8',
               	name : 'systemFileds8',
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段8',
                id : 'm_mapping_fileds8',
                name : 'mappingFileds8',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	}],
       	buttons: [{
	            text: '更新',
	            listeners:{'click':function(){
			    	
       				var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
					var selItem2 = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
					var formValue = Ext.getCmp('xml2jsonForm').getForm().getValues();
			    	var resultStr;
			    	
			    	switch (operator){
			    		case 'add':{
			    			resultStr = '新增成功！';
							
			    			//var inputParams = new Object();
			    			var inputParams = formValue;
			    			inputParams.mappingCode = selItem2[0].data.mappingCode;
                            inputParams.methodAddress = selItem[0].data.methodAddress;
							inputParams.type = 'add';
							var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
			    			
							qryOtherSysData(Ext.getCmp('v_sysCode').getValue());
			    			break ;
			    		}
			    		case 'mod':{
			    			resultStr = '修改成功！';
		
			    			var inputParams = formValue;
			    			/*var inputParams = new Object();
			    			inputParams.filedMappingId = formValue.filedMappingId;
			    			inputParams.systemFileds = Ext.getCmp('m_system_fileds').getValue();
			    			inputParams.systemFileds2 = Ext.getCmp('m_system_fileds2').getValue();
			    			inputParams.systemFileds3 = Ext.getCmp('m_system_fileds3').getValue();
			    			inputParams.systemFileds4 = Ext.getCmp('m_system_fileds4').getValue();
			    			inputParams.systemFileds5 = Ext.getCmp('m_system_fileds5').getValue();			    			
			    			inputParams.systemFileds6 = Ext.getCmp('m_system_fileds6').getValue();
			    			inputParams.systemFileds7 = Ext.getCmp('m_system_fileds7').getValue();
			    			inputParams.systemFileds8 = Ext.getCmp('m_system_fileds8').getValue();
                            inputParams.mappingFileds = Ext.getCmp('m_mapping_fileds').getValue();
                            inputParams.mappingFileds2 = Ext.getCmp('m_mapping_fileds2').getValue();
                            inputParams.mappingFileds3 = Ext.getCmp('m_mapping_fileds3').getValue();
                            inputParams.mappingFileds4 = Ext.getCmp('m_mapping_fileds4').getValue();
                            inputParams.mappingFileds5 = Ext.getCmp('m_mapping_fileds5').getValue();
                            inputParams.mappingFileds6 = Ext.getCmp('m_mapping_fileds6').getValue();
                            inputParams.mappingFileds7 = Ext.getCmp('m_mapping_fileds7').getValue();
                            inputParams.mappingFileds8 = Ext.getCmp('m_mapping_fileds8').getValue();*/
                            inputParams.transferResult = '';
                            //inputParams.methodAddress = selItem[0].data.methodAddress;
							inputParams.type = 'mod';
							var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
			    			
			    			break ;
			    		}
			    	}
			    	
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
			        //Ext.getCmp('xmljsonGrid').store.removeAll();
			        //Ext.getCmp('xmljsonGrid').store.reload();
			    }}
	        },{
	            text: '调试', 
	            listeners:{'click':function(){
	        		debugXml();
			    }}
	        }]
	});  
    
    function debugXml(){
    	var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
		var selItem2 = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
    	var formValue1 = Ext.getCmp('xml2jsonForm').getForm().getValues();
    	var isSuccess = 'failure';
    	var infoPage1 = new Ext.FormPanel({
			region: 'center',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	        	layout:'column',       	
		        labelWidth : 80,
		        labelAlign: 'top',
	        	items: [{
	        		columnWidth:.5,
	        		layout:'form',  
	        		items: [{
	        			xtype : 'textarea',
		               	fieldLabel : '转换之前',
		               	id : 'v_transfer_before',
		               	name : 'v_transfer_before',
		               	value : '获取xml失败,接口不通',
						width: 350,
					    height : 200
	        		}]		        	
			    },{columnWidth:.02},{
			    	columnWidth:.48,layout:'form',  
	        		items: [{
			        	xtype : 'textarea',
		               	fieldLabel : '转换后结果',
		               	id : 'v_transfer_result',
		               	name : 'v_transfer_result',
		               	value : '转换失败',
						width: 350,
					    height : 200
					}]
			    }]
	        }],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
						if(isSuccess == 'success'){
							var inputParams = new Object();
			    			inputParams.filedMappingId = formValue1.filedMappingId;
			    			inputParams.transferBefore = Ext.getCmp('v_transfer_before').getValue();
							inputParams.transferResult = Ext.getCmp('v_transfer_result').getValue();
							
	                        inputParams.type = "updateTrsfRs";
							var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
							
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
					            Ext.example.msg('','调试结果存入数据库成功');
					        }, 1000);
					        menuWin.close();
						}	            		
			    	}
			    }
			},{
	            text: '取消',
	            listeners:{"click":function(){
	            		menuWin.close();
			    	}
			    }
			}]
		});
    	var menuWin = new Ext.Window({
		        title: 'xml转换调试',
			    closable:true,
			    width:800,
			    height:300,
			    plain:true, 
			    layout: 'border',
			    items: [infoPage1]
			});
		menuWin.show(this);
		
		var inputParams = formValue1;
		inputParams.methodAddress = selItem[0].data.methodAddress;	//接口  方法地址	
		inputParams.interfaceNamespace = selItem[0].data.interfaceNamespace;	//空间名称
		inputParams.interfParamsName = selItem2[0].data.mappingMethod;		//接口参数名

		inputParams.interfMethodType = selItem2[0].data.mappingName;		//接口方法类型
		inputParams.mappingCode = selItem2[0].data.mappingCode;		//映射编码
		inputParams.intefaceParams = selItem2[0].data.intefaceParams;		//接口参数
		inputParams.intefaceMethodParam = selItem2[0].data.intefaceMethod;		//接口方法参数
		inputParams.intefaceType=selItem[0].data.methodType;  //方法类型
		inputParams.dataType=selItem[0].data.dataType;  //数据格式
		inputParams.interfaceMethod=selItem[0].data.interfaceMethod;  //方法名称
		inputParams.type = "debug";
		var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
		Ext.getCmp('v_transfer_before').setValue(retMap.transferBefore);
		Ext.getCmp('v_transfer_result').setValue(retMap.transferResult);
		isSuccess = retMap.result;
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    function wsXmlUploadView(type) {
        if(!type) return;
        wsXmlUpload.showMenuInfoPage(type);
    }

    function  wsXmlDownloadView(type) {
        if(!type) return;
         wsXmlUpload.showMenuInfoPage(type);
    }
    
    initGridMenu();
    //系统接口地址表

	var otherSysGrid = initOtherSysGrid();
    initGridEvent();
	var interfaceGrid = initInterfacePanel();
	initInterfaceGridEvent();
	
	/*var tabs = new Ext.TabPanel({
			region: 'south',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[detailForm]
		});*/
	
	var viewport = new Ext.Viewport({
		el:'sysGird',
		layout: 'border',
		items:[qryForm1,{
			layout:'border',
			region:'center',
			items:[otherSysGrid,interfaceGrid,detailForm]
		}]
		//items:[qryForm,grid,detailForm]
	});

});
</script>