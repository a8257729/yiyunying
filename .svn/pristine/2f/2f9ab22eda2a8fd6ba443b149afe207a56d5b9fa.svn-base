<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>数据源管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script language="javascript" src="js/dataFileMng.js"></script>
		<script language="javascript" src="js/dataBaseMng.js"></script>
		
		<style type="text/css">
	        .x-window-dlg .ext-mb-download {
	            background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
	            height:46px;
	        }
	    </style>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="staffMngDiv"></div>
	</body>
</html>

<script language="JScript">

var session1 = GetSession();
var dataBaseMng = new DataBaseMng();
var dataFileMng = new DataFileMng();

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
	
    //查询条件面板
    var qryPanel = new Ext.FormPanel({
		region: 'north',
     	labelAlign: 'left',
        labelWidth :70,
	 	frame:true,
        title: '查询条件',
        bodyStyle:'padding:5px 5px 5px 5px',
        height:Ext.getBody().getSize().height*0.2,
        width:Ext.getBody().getSize().width*0.8,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '数据源名称',
                    name: 'dsName',
                    id: 'dsName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    name: 'dsType',
                    fieldLabel: '数据源类别',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    id: 'dsType',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['','所有类别'],['001','文件'],['000','数据库']]
                    })
                },{
                    buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	var dsType = Ext.getCmp("dsType").getValue() ;
			                	var dsName = Ext.getCmp("dsName").getValue() ;
			                	store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {dsType:dsType,dsName:dsName};
									}
								})
								store.removeAll() ;
								store.load({params:{start:0, limit:5}});
			            	}
			            }},{
                    	text: '重置' ,
                    	listeners:{"click":function(){
			                qryPanel.form.reset();
			            }} 
                    }]
                }]
            }]
        }]
    });
    
    function renderStateName(value, p, r){
        if(value == '10A'){
        	return '<span style="color:green;">有效</span>';
        }else{
        	return '<span style="color:red;">无效</span>';
        }
    }
    
    function renderDBTypeName(value, p, r){
        if(value == '001'){
        	return '<span style="color:green;">文件</span>';
        }else{
        	return '<span style="color:red;">数据库</span>';
        }
    }
    
    // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'dsId','dsName','dsType','state','dbDriver','dbUrl','userName','password','initNum','incNum',
        	'maxNum','ftpIp','serCop','createDate','stateDate','memo','operManId','operManName','dblinkName',
        	'dbType','dbTypeName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryDataBaseListAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
    store.load({params:{start:0, limit:5}});
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
		{header:'dsId',dataIndex:'dsId',hidden:true },
		{header:'dbDriver',dataIndex:'dbDriver',hidden:true },
		{header:'dbUrl',dataIndex:'dbUrl',hidden:true },
		{header:'userName',dataIndex:'userName',hidden:true },
		{header:'password',dataIndex:'password',hidden:true },
		{header:'initNum',dataIndex:'initNum',hidden:true },
		{header:'incNum',dataIndex:'incNum',hidden:true },
		{header:'maxNum',dataIndex:'maxNum',hidden:true },
		{header:'ftpIp',dataIndex:'ftpIp',hidden:true },
		{header:'createDate',dataIndex:'createDate',hidden:true },
		{header:'memo',dataIndex:'memo',hidden:true },
		{header:'operManId',dataIndex:'operManId',hidden:true },
		{header:'dblinkName',dataIndex:'dblinkName',hidden:true },
		{header:'dbType',dataIndex:'dbType',hidden:true },
		{header:'dbTypeName',dataIndex:'dbTypeName',hidden:true },
	    {header:'数据库名',dataIndex:'dsName',width:200},
	    {header:'数据源类型',dataIndex:'dsType',renderer: renderDBTypeName,width:100},
	    {header:'提供厂家',dataIndex:'serCop',width:200},
	    {header:'对象状态',dataIndex:'state',renderer: renderStateName,width:80},
	    {header:'最后操作人',dataIndex:'operManName',width:230},
	    {header:'最后操作时间',dataIndex:'stateDate',width:150}
	]);   
	column.defaultSortable = true;//默认可排序   
	
    var pagingBar = new Ext.PagingToolbar({
		pageSize: 5,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	
	var grid = new Ext.grid.GridPanel({
		id: 'dataSourceGrid',
		region: 'center',
	    title:'数据源列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
	            	if(rec.data.dsType == '001'){
		            	Ext.getCmp("dsName2").setValue(rec.data.dsName);
		            	Ext.getCmp("ftpIp2").setValue(rec.data.ftpIp);
		            	Ext.getCmp("serCop2").setValue(rec.data.serCop);
		            	Ext.getCmp("dsType2").setValue("文件");
		            	Ext.getCmp("userName2").setValue(rec.data.userName);
		            	Ext.getCmp("password2").setValue(rec.data.password);
		            	Ext.getCmp("memo2").setValue(rec.data.memo);
		            	tabs2.hideTabStripItem(1);
		            	tabs2.unhideTabStripItem(0);
		            	infoPanel.show();
		            	infoPanel2.hide();
	            	}else{
	            		Ext.getCmp("dsName3").setValue(rec.data.dsName);
		            	Ext.getCmp("dbDriver3").setValue(rec.data.dbDriver);
		            	Ext.getCmp("serCop3").setValue(rec.data.serCop);
		            	Ext.getCmp("incNum3").setValue(rec.data.incNum);
		            	Ext.getCmp("dbUrl3").setValue(rec.data.dbUrl);
		            	Ext.getCmp("dsType3").setValue("数据库");
		            	Ext.getCmp("userName3").setValue(rec.data.userName);
		            	Ext.getCmp("password3").setValue(rec.data.password);
		            	Ext.getCmp("maxNum3").setValue(rec.data.maxNum);
		            	Ext.getCmp("initNum3").setValue(rec.data.initNum);
		            	Ext.getCmp("memo3").setValue(rec.data.memo);
		            	
		            	Ext.getCmp("dbType3").setValue(rec.data.dbType);
		            	Ext.getCmp("dbTypeName3").setValue(rec.data.dbTypeName);
		            	
		            	tabs2.hideTabStripItem(0);
		            	tabs2.unhideTabStripItem(1);
		            	infoPanel2.show();
		            	infoPanel.hide();
	            	}
	            }
	        }
	    }),
        bbar: pagingBar
    });
	
	//详情tab
    var infoPanel = new Ext.FormPanel({
     	labelAlign: 'left',
	 	frame:true,
        title: '文件信息',
        bodyStyle:'padding:5px 5px 0',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '数据源名',
                    name: 'dsName2',
                    id: 'dsName2',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '文件主机IP地址',
                    name: 'ftpIp2',
                    id: 'ftpIp2',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '提供厂家',
                    name: 'serCop2',
                    id: 'serCop2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '类型',
                    name: 'dsType2',
                    id: 'dsType2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName2',
                    id: 'userName2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '密码',
                    name: 'password2',
                    id: 'password2',
                    inputType: 'password',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]
            
        },{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'memo2',
		    id: 'memo2',
		    height : 100,
		    anchor:'95%'
		}]
    });
    
    //详情tab
    var infoPanel2 = new Ext.FormPanel({
     	labelAlign: 'left',
	 	frame:true,
        title: '数据库信息',
        bodyStyle:'padding:5px 5px 0',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '数据源名',
                    name: 'dsName3',
                    id: 'dsName3',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '驱动类名',
                    name: 'dbDriver3',
                    id: 'dbDriver3',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '提供厂家',
                    name: 'serCop3',
                    id: 'serCop3',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '连接池自增大小',
                    name: 'incNum3',
                    id: 'incNum3',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: 'URL',
                    name: 'dbUrl3',
                    id: 'dbUrl3',
                    anchor:'95%'
                }, {
                    xtype:'hidden',
                    name: 'dbType3',
                    id: 'dbType3'
                }, {
                    xtype:'textfield',
                    fieldLabel: '数据库类型',
                    name: 'dbTypeName3',
                    id: 'dbTypeName3',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '类型',
                    name: 'dsType3',
                    id: 'dsType3',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName3',
                    id: 'userName3',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '密码',
                    name: 'password3',
                    id: 'password3',
                    inputType: 'password',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '最大连接数',
                    name: 'maxNum3',
                    id: 'maxNum3',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '初始连接数',
                    name: 'initNum3',
                    id: 'initNum3',
                    anchor:'95%'
                } ]
            },{columnWidth:.1,layout: 'form'}]
            
        },{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'memo3',
		    id: 'memo3',
		    height : 60,
		    anchor:'90%'
		}]
    });
    
	var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});
	
	var nullRightClick = new Ext.menu.Menu({
	    id:'nullRightClick'
	});
		
	var lineItem = new Ext.menu.Separator();

	grid.addListener('rowcontextmenu', rightClickFn);
	grid.addListener('contextmenu', nullRightClickFn);
	
	function rightClickFn(grid,rowIndex,e){
		rightClick.removeAll();
		var i = 0 ;
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增数据文件' ,handler: function() {
			rightClick.hide();
			dataFileAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增数据库' ,handler: function() {
			rightClick.hide();
			dataBaseAdd();
		}}));
	
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改数据源' , handler: function() {
			rightClick.hide();
			dataSourceMod();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除数据源' ,handler: function() {
			rightClick.hide();
			staffDelVlt();
		}}));
		
		grid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    function nullRightClickFn(e){
    	nullRightClick.removeAll();
    	var i = 0 ;
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增数据文件' ,handler: function() {
				dataSourceAdd();
				nullRightClick.hide();
		}}));
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增数据库' ,handler: function() {
			nullRightClick.hide();
			dataBaseAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }	
	
	//数据文件新增方法
	function dataFileAdd(){
		//打开子页面


		dataFileMng.showDataFileInfo('add');
	}
	
	//数据库信息新增方法


	function dataBaseAdd(){
		//打开子页面


		dataBaseMng.showDataBaseInfo('add');
	}
	
	//数据源修改方法


	function dataSourceMod(){
		var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
		var dsType = selDb[0].data.dsType ;
		if(dsType == '000'){
			dataBaseMng.showDataBaseInfo('mod');
		}else if(dsType == '001'){
			dataFileMng.showDataFileInfo('mod');
		}
		
	}
	
	function staffDelVlt(){
		Ext.MessageBox.confirm('提示', '是否确定要删除该数据源？', staffDel);
	}
	
	//数据源删除方法


	function staffDel(btn){
		if (btn == 'yes'){
			var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
			var objId = selDb[0].data.dsId ;
			var staffId = session1.staff.staffId ;
			var staffName = session1.staff.staffName ;
			
			callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.DataSourceManagerClient", "deleteDataSource", objId,staffId,staffName);
			
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
			    Ext.example.msg('','数据源删除成功！');
			}, 1000);
			
			Ext.getCmp('dataSourceGrid').store.removeAll();
			Ext.getCmp('dataSourceGrid').store.load({params:{start:0, limit:5}});
		}
	}
	
    // second tabs built from JS
	var tabs2 = new Ext.TabPanel({
		region: 'south',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.8,   
	    height:Ext.getBody().getSize().height*0.485, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[infoPanel,infoPanel2]
	});
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[qryPanel,grid,tabs2]
	}); 
	
	var viewport = new Ext.Viewport({
		el:'staffMngDiv',
		layout: 'border',
		items:[earthviewport]
	});
});

</script>