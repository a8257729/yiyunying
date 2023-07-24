<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>调度方案监控</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script language="javascript" src="js/schMnitViExt.js"></script>
		
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

var gridRowCount = 8 ;
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();

var session1 = GetSession();
var schMnitView = new SchMnitView();
									 
Ext.onReady(function(){
	
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
                    fieldLabel: '方案名称',
                    name: 'scheduleName',
                    id: 'scheduleName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    name: 'state',
                    id: 'state',
                    fieldLabel: '方案状态',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['','所有状态'],['10W','待执行'],['10I','执行中'],['10E','异常'],['10F','执行完成'], ['1PU','暂停']]
                    })
                },{
                    buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	var scheduleName = Ext.getCmp("scheduleName").getValue() ;
			                	var state = Ext.getCmp("state").getValue() ;
			                	grid.store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {scheduleName:scheduleName,state:state};
									}
								})
								grid.store.removeAll() ;
								grid.store.load({params:{start:0, limit:gridRowCount}});
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
        if(value == '10W'){
        	return '待执行' ;
        }else if(value == '10I'){
        	return '执行中' ;
        }else if(value == '10F'){
        	return '执行完成' ;
        }else if(value == '1PU'){
        	return '暂停' ;
        }else if(value == '10P'){
        	return '无效' ;
        }
    }
    
    var column = new Ext.grid.ColumnModel([
	    {header:'schInstId',dataIndex:'schInstId',hidden:true }, 
		{header:'schedule',dataIndex:'schedule',hidden:true }, 
		{header:'createDate',dataIndex:'createDate',hidden:true }, 
		{header:'memo',dataIndex:'memo',hidden:true }, 
		{header:'operMan',dataIndex:'operMan',hidden:true }, 
		{header:'operNameId',dataIndex:'operNameId',hidden:true }, 
		{header:'operManType',dataIndex:'operManType',hidden:true },
		{header:'调度方案名称',dataIndex:'scheduleName',width:swidth*0.26},  
		{header:'ETL开始时间',dataIndex:'etlStartDate',width:swidth*0.15}, 
		{header:'下次ETL开始时间',dataIndex:'etlNextDate',width:swidth*0.15}, 
		{header:'状态',dataIndex:'state',renderer:renderStateName,width:swidth*0.1},
		{header:'状态时间',dataIndex:'stateDate',width:swidth*0.15}
	]); 
		
	var grid = new ZTESOFT.Grid({
    	id:'grid',
    	title : '方案列表',
    	region: 'center',
      	width:10,
        pageSize: gridRowCount,
        cm:column,
        paging:true,
		url:'/MOBILE/ExtServlet?actionName=etl/QrySchListAction',
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
		            Ext.getCmp("scheduleName2").setValue(rec.data.scheduleName);
		            Ext.getCmp("operMan2").setValue(rec.data.operMan);
		            Ext.getCmp("createDate2").setValue(rec.data.createDate);
		            Ext.getCmp("etlNextDate2").setValue(rec.data.etlNextDate);
		            Ext.getCmp("memo2").setValue(rec.data.memo);
	            }
	        }
	    })
	});
	grid.store.load({params:{start:0, limit:gridRowCount}});
	
	
	grid.store.on("load",function(store){
		grid.getSelectionModel().selectRow(0);
	});
	
		//定义菜单
	var treeMenu = new Ext.menu.Menu({
        id : 'treeMenu'
	});
	//在Gird表单内弹出的菜单
	function onRowMenu(node,rowIndex,e){
		node.getSelectionModel().selectRow(rowIndex);			//选中所在的选择项上
		treeMenu.removeAll();
		treeMenu.addItem(new Ext.menu.Item({text:'优先',handler:function(item,e){
			treeMenu.hide();
			//add();		//新增操作
		}}));
			
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	grid.on("rowcontextmenu",onRowMenu);
	
	//双击事件
	grid.on("rowdblclick",function(){
		var schInstId = Ext.getCmp('grid').getSelectionModel().getSelections()[0].data.schInstId ;
		schMnitView.showSchMnitView(schInstId);
	});
	
	//详情tab
    var infoPanel = new Ext.FormPanel({
     	labelAlign: 'left',
	 	frame:true,
        title: '方案信息',
        bodyStyle:'padding:5px 5px 0',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '方案名称',
                    name: 'scheduleName2',
                    id: 'scheduleName2',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '创建时间',
                    name: 'createDate2',
                    id: 'createDate2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '操作人',
                    name: 'operMan2',
                    id: 'operMan2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'etlNextDate2',
                    id: 'etlNextDate2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]
            
        },{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'memo2',
		    id: 'memo2',
		    height : 80,
		    anchor:'95%'
		}]
    });
    
	var tabs2 = new Ext.TabPanel({
		region: 'south',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.8,   
	    height:Ext.getBody().getSize().height*0.39, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[infoPanel]
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