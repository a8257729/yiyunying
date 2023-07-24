<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>ETL清洗规则</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script type="text/css" src="../ext/ux/css/Spinner.css"></script>
		<script type="text/javascript" src="../ext/ux/DateTimeField.js"></script>
		<script type="text/javascript" src="../ext/ux/Spinner.js"></script>
		<script type="text/javascript" src="../ext/ux/SpinnerField.js"></script>
		
		<script language="javascript" src="js/etlCleanRuleMng.js"></script>
		<script language="javascript" src="js/etlCleanRuleComMng.js"></script>
		<script type="text/javascript" src="js/procDefiOper.js"></script>
		
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
		<div id="scheduleMngDiv"></div>
	</body>
</html>

<script language="JScript">

var session1 = GetSession();
var pdOper = new ProcDefiOper();

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();

var cleanRuleMng = new CleanRuleMng();
var cleanRuleComMng = new CleanRuleComMng() ;
												 
Ext.onReady(function(){
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
    //查询条件面板
    var qryPanel = new Ext.FormPanel({
		region: 'north',
     	labelAlign: 'left',
        labelWidth :100,
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
                    fieldLabel: '清洗规则名称',
                    name: 'etlRuleName',
                    id: 'etlRuleName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    name: 'state',
                    fieldLabel: '方案状态',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    id: 'state',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: '10A',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['','--所有--'],['10A','有效'],['10P','无效']]
                    })
                },{
                    buttons: [{
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	var etlRuleName = Ext.getCmp("etlRuleName").getValue() ;
			                	var state = Ext.getCmp("state").getValue() ;
			                	store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {etlRuleName:etlRuleName,state:state};
									}
								})
								store.removeAll() ;
								store.load({params:{start:0, limit:8}});
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
    
    // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [
        	'etlRuleId','etlRuleName','state','targetTbName','operManName','stateDate','sourceDsName','targetName','etlTypeName','datasetTypeName','dsType','dsTypeName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryCleanRoleListAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
    store.baseParams = {state:'10A'};
    store.load({params:{start:0, limit:8}});
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
		{header:'etlRuleId',dataIndex:'etlRuleId',hidden:true },
	    {header:'ETL规则名称',dataIndex:'etlRuleName',width:swidth*0.2},
	    {header:'ETL类型',dataIndex:'etlTypeName',width:swidth*0.1},
	    {header:'目标表',dataIndex:'targetTbName',width:swidth*0.2},
	    {header:'状态',dataIndex:'state',renderer: renderStateName,width:swidth*0.1},
	    {header:'最后操作人',dataIndex:'operManName',width:swidth*0.1},
	    {header:'最后操作时间',dataIndex:'stateDate',width:swidth*0.1}
	]);   
	column.defaultSortable = true;//默认可排序   
	
    var pagingBar = new Ext.PagingToolbar({
		pageSize: 8,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	
	var grid = new Ext.grid.GridPanel({
		id: 'cleanRuleGrid',
		region: 'center',
	    title:'规则列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
		            ruleStore.on('beforeload',function(store){
						if(ruleStore.lastOptions != null){
							ruleStore.baseParams = {etlRuleId:rec.data.etlRuleId};
						}
					})
					ruleStore.removeAll() ;
					ruleStore.load({params:{start:0, limit:5}});
	            }
	        }
	    }),
        bbar: pagingBar
    });
    
    //规则列表信息
    var ruleStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [
        	'sqEtlCleanRuleId','etlCleanCompId','sqEtlCleanRuleName','cleanColName','createDate','stateDate','state','operManId','operManName','memo','cleanCompName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryRoleComListAction'
        })
    });
	
	//创建列   
	var ruleColumn = new Ext.grid.ColumnModel([
		{header:'sqEtlCleanRuleId',dataIndex:'sqEtlCleanRuleId',hidden:true },
		{header:'etlCleanCompId',dataIndex:'etlCleanCompId',hidden:true },
	    {header:'清洗规则名称',dataIndex:'sqEtlCleanRuleName',width:swidth*0.2},
	    {header:'清洗组件名称',dataIndex:'cleanColName',width:swidth*0.2},
	    {header:'备注',dataIndex:'memo',width:swidth*0.3},
	    {header:'最后操作时间',dataIndex:'createDate',width:swidth*0.1}
	]);   
	ruleColumn.defaultSortable = true;//默认可排序

	
    var rulePgBar = new Ext.PagingToolbar({
		pageSize: 6,
		store: ruleStore,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
    
	var cleanComGrid = new Ext.grid.GridPanel({
		id: 'cleanComGrid',
	    height:Ext.getBody().getSize().height*0.4,
	    title: '清洗组件列表',
		region: 'south',
        store: ruleStore,
        trackMouseOver: false,
        autoScroll: true,
        loadMask: true,
		cm: ruleColumn,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true
	    }),
        bbar: rulePgBar
    });
    
	var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});
	
	var nullRightClick = new Ext.menu.Menu({
	    id:'nullRightClick'
	});
		
	var ruleRightClick = new Ext.menu.Menu({
	    id:'ruleRightClick'
	});
	
	var nullRuleRightClick = new Ext.menu.Menu({
	    id:'nullRuleRightClick'
	});		

	grid.addListener('rowcontextmenu', rightClickFn);
	grid.addListener('contextmenu', nullRightClickFn);
	
	function rightClickFn(grid,rowIndex,e){
		rightClick.removeAll();
		var i = 0 ;
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增清洗规则' ,handler: function() {
			rightClick.hide();
			cleanRuleMng.showCleanRuleInfo('add');
		}}));
	
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改清洗规则' , handler: function() {
			rightClick.hide();
			cleanRuleMng.showCleanRuleInfo('mod');
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除清洗规则' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '是否确定要删除该清洗规则？', scheduleDel);
		}}));
		
		grid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    function nullRightClickFn(e){
    	nullRightClick.removeAll();
    	var i = 0 ;
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增清洗规则' ,handler: function() {
			nullRightClick.hide();
			cleanRuleMng.showCleanRuleInfo('add');
		}}));
    	nullRightClick.showAt(e.getXY());
    }	
    
    cleanComGrid.addListener('rowcontextmenu', ruleRightClickFn);
	cleanComGrid.addListener('contextmenu', nullRuleRightClickFn);
	
	function ruleRightClickFn(grid,rowIndex,e){
		ruleRightClick.removeAll();
		var i = 0 ;
		
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '增加规则组件' ,handler: function() {
			ruleRightClick.hide();
			cleanRuleComMng.showCleanRuleComInfo('add'); 
		}}));
		
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '修改规则组件' ,handler: function() {
			ruleRightClick.hide();
			cleanRuleComMng.showCleanRuleComInfo('mod'); 
		}}));
		
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '删除规则组件' ,handler: function() {
			ruleRightClick.hide();
			Ext.MessageBox.confirm('提示', '是否确定要删除该清洗规则组件？', schRuleComDel);
		}}));
		
		cleanComGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    ruleRightClick.showAt(e.getXY());
	}
    function nullRuleRightClickFn(e){
    	nullRuleRightClick.removeAll();
    	var i = 0 ;
		nullRuleRightClick.insert(i++,new Ext.menu.Item({ text: '增加规则组件' ,handler: function() {
			nullRuleRightClick.hide();
			cleanRuleComMng.showCleanRuleComInfo('add'); 
		}}));
    	nullRuleRightClick.showAt(e.getXY());
    }
	
	//方案新增方法
	function startSchedule(){
		//打开子页面

		var selDb = Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections();
		
		
	    var obj = new Object();
		obj.scheduleId = selDb[0].data.scheduleId ;
		obj.etlStartDate = selDb[0].data.execStartDate ;
		obj.execRate = selDb[0].data.execRate ;
		obj.memo = selDb[0].data.memo ;
		obj.operManId = session1.staff.staffId;
		obj.operMan = session1.staff.staffName;
		obj.state = '10W';
		obj.operManType = 'SYS'; 
		
		var icount = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "checkIsEist","SCHEDULE_ID", obj.scheduleId,"SQ_ETL_SCHEDULE_INST","1");
		if(icount == 0){
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
			    Ext.example.msg('','启动成功！');
			}, 1000);
		}else {
		    Ext.MessageBox.show({
			    title: '提示',
			    msg: '此方案已启动!',
			    buttons: Ext.MessageBox.OK,
			    width:200,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
	}
	
	//清洗规则删除方法
	function scheduleDel(btn){
		if (btn == 'yes'){
			var selDb = Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections();
			var flag = callRemoteFunction("com.ztesoft.mobile.etl.service.EtlRuleManagerClient","delEtlRule", selDb[0].data.etlRuleId);
			if(flag){
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
				    Ext.example.msg('','清洗规则删除成功！');
				}, 1000);
			}else{
				Ext.MessageBox.show({
		            title: '提示',
		            msg: '清洗规则删除失败！',
		            buttons: Ext.MessageBox.OK,
		            width:200,
		            icon: Ext.MessageBox.ERROR
		       	});
			}
			Ext.getCmp('cleanRuleGrid').store.removeAll();
			Ext.getCmp('cleanRuleGrid').store.load({params:{start:0, limit:8,state:'10A'}});
		}
	}
	
	function schRuleComDel(btn){
		if (btn == 'yes'){
			var selCom = Ext.getCmp('cleanComGrid').getSelectionModel().getSelections();
			var cleanRule = new Object();
			cleanRule.sqEtlCleanRuleId = selCom[0].data.sqEtlCleanRuleId ;
			cleanRule.operManId = session1.staff.staffId;
			cleanRule.operManName = session1.staff.staffName;
			var flag = invokeAction('/etl/DeleteEtlCleanRuleAction',cleanRule);
		
			if(flag){
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
				    Ext.example.msg('','清洗规则组件删除成功！');
				}, 1000);
			}else{
				Ext.MessageBox.show({
		            title: '提示',
		            msg: '清洗规则组件删除失败！',
		            buttons: Ext.MessageBox.OK,
		            width:250,
		            icon: Ext.MessageBox.ERROR
		       	});
			}
			Ext.getCmp('cleanComGrid').store.removeAll();
			Ext.getCmp('cleanComGrid').store.load({params:{start:0, limit:8}});
		}
	}
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[qryPanel,grid,cleanComGrid]
	});
	
	var viewport = new Ext.Viewport({
		el:'scheduleMngDiv',
		layout: 'border',
		items:[earthviewport]
	});
});

</script>