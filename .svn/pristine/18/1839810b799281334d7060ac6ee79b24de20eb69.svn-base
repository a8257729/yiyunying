<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>ETL调度方案</title>
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
		
		<script language="javascript" src="js/scheduleManageExt.js"></script>
		<script language="javascript" src="js/ruleListExt.js"></script>
		
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
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();

var scheduleMng = new ScheduleMng();
var ruleListMng = new RuleListMng();

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
        height:Ext.getBody().getSize().height*0.22,
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
				    xtype: 'compositefield',
				    fieldLabel: '方案目录',
				    items: [{
				        xtype: 'textfield',
				        name: 'scheCatalogName',
				        id: 'scheCatalogName',
				        anchor:'95%',
				        readOnly: true
				    },{
				        xtype: 'button',
				        text: '..',
				        listeners:{
				        	"click":function(){
				        		scheduleMng.showScheCatalog("scheCatalogId","scheCatalogName");
				        	}
				        }
				    }]
                }]
            },{
		    	xtype:'hidden',
		    	name:'scheCatalogId',
		    	id:'scheCatalogId'            
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
                    	data: [['','--所有--'],['10A','有效'],['10S','启动'],['1PU','挂起']]
                    })
                },{
                    buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	var scheduleName = Ext.getCmp("scheduleName").getValue() ;
			                	var state = Ext.getCmp("state").getValue() ;
			                	var scheCataId = Ext.getCmp("scheCatalogId").getValue();
			                	store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {scheduleName:scheduleName,scheCataId:scheCataId,state:state};
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
        }else if(value == '10S'){
        	return '<span style="color:green;">启动</span>';
        }else if(value == '1PU'){
        	return '<span style="color:red;">挂起</span>';
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
        	'scheduleId','scheduleName','scheCatalogId','scheCatalogName','packageDefineId','processDefinName','execStartDate','execRate','state','createDate','stateDate','operManId','operManName','memo'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryScheduleListAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
    store.load({params:{start:0, limit:8,state:'10A'}});
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'方案名称',dataIndex:'scheduleName',width:200},
	    {header:'所在目录',dataIndex:'scheCatalogName',width:150},
	    {header:'相关流程',dataIndex:'processDefinName',width:150},
	    {header:'执行开始时间',dataIndex:'execStartDate',width:120},
	    {header:'执行频率（小时）',dataIndex:'execRate',width:50},
	    {header:'状态',dataIndex:'state',renderer: renderStateName,width:80},
	    {header:'最后操作人',dataIndex:'operManName',width:130},
	    {header:'最后操作时间',dataIndex:'stateDate',width:150}
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
		id: 'scheduleGrid',
		region: 'center',
	    title:'调度方案列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
		            Ext.getCmp("scheduleName2").setValue(rec.data.scheduleName);
		            Ext.getCmp("processDefinName2").setValue(rec.data.processDefinName);
 					Ext.getCmp("scheCatalogName2").setValue(rec.data.scheCatalogName);
		            Ext.getCmp("operManName2").setValue(rec.data.operManName);
		            Ext.getCmp("execStartDate2").setValue(rec.data.execStartDate);
		            Ext.getCmp("execRate2").setValue(rec.data.execRate);
		            Ext.getCmp("operManName2").setValue(rec.data.operManName);
		            Ext.getCmp("memo2").setValue(rec.data.memo);
		            
		            ruleStore.on('beforeload',function(store){
						if(ruleStore.lastOptions != null){
							ruleStore.baseParams = {scheduleId:rec.data.scheduleId};
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
        	//'schSequId','schedule','etlRule','schSequ','','etlRuleName','etlType','sourceDsId','targetDsId','state','createDate','operManName','etlTypeName','sourceDsName','targetDsName'
        	'schSequId','schedule','etlRule','schSequ','','etlRuleName','etlType','state','createDate','operManName','etlTypeName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryScheduleRulesAction'
        })
    });
	
	//创建列   
	var ruleColumn = new Ext.grid.ColumnModel([
		{header:'ETL规则编号',dataIndex:'schSequId',hidden:true },
	    {header:'执行顺序',dataIndex:'schSequ',width:100},
	    {header:'ETL规则',dataIndex:'etlRuleName',width:220},
	    {header:'ETL类型',dataIndex:'etlTypeName',width:100},
	    //{header:'源数据源',dataIndex:'sourceDsName',width:100},
	    //{header:'目标数据源',dataIndex:'targetDsName',width:100},
	    {header:'状态',dataIndex:'state',renderer: renderStateName,width:80},
	    {header:'最后操作人',dataIndex:'operManName',width:140},
	    {header:'最后操作时间',dataIndex:'createDate',width:150}
	]);   
	ruleColumn.defaultSortable = true;//默认可排序



	
    var rulePgBar = new Ext.PagingToolbar({
		pageSize: 6,
		store: ruleStore,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
    
    var tb = new Ext.Toolbar();
	tb.add({
	    text:'添加',
	    onClick:function(){
	    	ruleListMng.showScheduleInfo();
	    }
	});
       
    tb.add({
        text:'删除',
        onClick:function(){
        	schRuleDelVlt();
        }
    });
       
    tb.add({
        text:'上移',
        onClick:function(){
        	up();
        }
    });
       
    tb.add({
        text:'下移',
        onClick:function(){
        	down();
        }
    });
    
	var ruleGrid = new Ext.grid.GridPanel({
		id: 'ruleGrid',
	    title: '方案规则列表',
        store: ruleStore,
        trackMouseOver: false,
        autoScroll: true,
        loadMask: true,
		cm: ruleColumn,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true
	    }),
        bbar: rulePgBar,
        tbar: tb
    });
    
	//方案详情
    var infoPanel = new Ext.FormPanel({
    	region: 'south',
     	labelAlign: 'left',
     	labelWidth : 150,
     	height:Ext.getBody().getSize().height*0.37,
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
                },{
			        xtype: 'textfield',
			        fieldLabel: '流程名称',
			        name: 'processDefinName2',
			        id: 'processDefinName2',
			        anchor:'95%'
				
				},{
                    xtype:'textfield',
                    fieldLabel: '执行开始时间',
                    name: 'execStartDate2',
                    id: 'execStartDate2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '执行频率（小时）',
                    name: 'execRate2',
                    id: 'execRate2',
                    anchor:'95%'
                },{
				    xtype: 'textfield',
				    fieldLabel: '方案目录',
			        name: 'scheCatalogName2',
			        id: 'scheCatalogName2',
			        anchor:'95%'
				
				},{
                    xtype:'textfield',
                    fieldLabel: '最后操作人',
                    name: 'operManName2',
                    id: 'operManName2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]
            
        },{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'memo2',
		    id: 'memo2',
		    height : 65,
		    anchor:'95%'
		}]
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
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '启动调度方案' ,handler: function() {
			rightClick.hide();
			startSchedule();
		}}));
		
		//------------------add by feng.yang 2011/7.11 
		rightClick.insert(i++,new Ext.menu.Item({ text: '挂起调度方案' ,handler: function() {
			rightClick.hide();
			suspendSchedule();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '重启调度方案' ,handler: function() {
			rightClick.hide();
			restartSchedule();
		}}));
		
		//-------------------
		
		rightClick.add(new Ext.menu.Separator());
		i++ ;
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增调度方案' ,handler: function() {
			rightClick.hide();
			scheduleAdd();
		}}));
	
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改调度方案' , handler: function() {
			rightClick.hide();
			scheduleMod();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除调度方案' ,handler: function() {
			rightClick.hide();
			scheduleDelVlt();
		}}));
		
		grid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    function nullRightClickFn(e){
    	nullRightClick.removeAll();
    	var i = 0 ;
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增调度方案' ,handler: function() {
			nullRightClick.hide();
			//dataBaseAdd();
			scheduleAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }	
    
    ruleGrid.addListener('rowcontextmenu', ruleRightClickFn);
	ruleGrid.addListener('contextmenu', nullRuleRightClickFn);
	
	function ruleRightClickFn(grid,rowIndex,e){
		ruleRightClick.removeAll();
		var i = 0 ;
		
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '增加方案规则' ,handler: function() {
			ruleRightClick.hide();
			ruleListMng.showScheduleInfo();
		}}));
		
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '删除方案规则' ,handler: function() {
			ruleRightClick.hide();
			schRuleDelVlt();
		}}));
		
		ruleRightClick.add(new Ext.menu.Separator());
		i++ ;
	
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '上移顺序' , handler: function() {
			ruleRightClick.hide();
			up();
		}}));
		ruleRightClick.insert(i++,new Ext.menu.Item({ text: '下移顺序' ,handler: function() {
			ruleRightClick.hide();
			down();
		}}));
		
		ruleGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    ruleRightClick.showAt(e.getXY());
	}
    function nullRuleRightClickFn(e){
    	nullRuleRightClick.removeAll();
    	var i = 0 ;
		nullRuleRightClick.insert(i++,new Ext.menu.Item({ text: '增加方案规则' ,handler: function() {
			nullRuleRightClick.hide();
			//dataBaseAdd();
			ruleListMng.showScheduleInfo();
		}}));
    	nullRuleRightClick.showAt(e.getXY());
    }
	
	//方案挂起方法
	function suspendSchedule(){
		//打开子页面



		var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
	    
	    var state = selDb[0].data.state ;

	    if(state != '10S'){
	    	Ext.MessageBox.show({
			    title: '提示',
			    msg: '此方案不是启动状态，不能挂起!',
			    buttons: Ext.MessageBox.OK,
			    width:200,
			    icon: Ext.MessageBox.ERROR
			});
			return;
	    }
	    
		var scheduleId = selDb[0].data.scheduleId ;
		var operManId = session1.staff.staffId;
		var operMan = session1.staff.staffName;
		
		callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", 
											"suspendSchedule",scheduleId,operManId,operMan);
		Ext.MessageBox.hide();
			    Ext.example.msg('','挂起成功!');
		Ext.getCmp('scheduleGrid').store.removeAll();
			Ext.getCmp('scheduleGrid').store.load({params:{start:0, limit:8}});	    								
		return;
		
	}
	
	//方案重启方法
	function restartSchedule(){
		//打开子页面



		var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
	    
	    var state = selDb[0].data.state ;
	    
	    if(state != '1PU'){
	    	Ext.MessageBox.show({
			    title: '提示',
			    msg: '此方案不是挂起状态，无法重新启动!',
			    buttons: Ext.MessageBox.OK,
			    width:200,
			    icon: Ext.MessageBox.ERROR
			});
			return;
	    }
	    
		var scheduleId = selDb[0].data.scheduleId ;
		var operManId = session1.staff.staffId;
		var operMan = session1.staff.staffName;
		
		callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", 
											"restartSchedule",scheduleId,operManId,operMan);
		Ext.MessageBox.hide();
			    Ext.example.msg('','重新启动成功!');
		Ext.getCmp('scheduleGrid').store.removeAll();
			Ext.getCmp('scheduleGrid').store.load({params:{start:0, limit:8}});	    								
		return;
		
	}
		
	//方案新增方法
	function startSchedule(){
		//打开子页面



		var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
		
		var state = selDb[0].data.state ;

		if(state == '10P'){
	    	Ext.MessageBox.show({
			    title: '提示',
			    msg: '此方案已经删除!',
			    buttons: Ext.MessageBox.OK,
			    width:200,
			    icon: Ext.MessageBox.ERROR
			});
			return;
	    }
	    
	    if(state != '10A'){
	    	Ext.MessageBox.show({
			    title: '提示',
			    msg: '此方案已经启动!',
			    buttons: Ext.MessageBox.OK,
			    width:200,
			    icon: Ext.MessageBox.ERROR
			});
			return;
	    }
		
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
			callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "addScheduleInst",obj);
			setTimeout(function(){
			    Ext.MessageBox.hide();
			    Ext.example.msg('','启动成功！');
			}, 1000);
			Ext.getCmp('scheduleGrid').store.removeAll();
			Ext.getCmp('scheduleGrid').store.load({params:{start:0, limit:8}});
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
	
	//方案新增方法
	function scheduleAdd(){
		//打开子页面



		scheduleMng.showScheduleInfo('add');
	}
	
	//方案修改方法
	function scheduleMod(){
		scheduleMng.showScheduleInfo('mod');
	}
	
	function scheduleDelVlt(){
		var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
		var state = selDb[0].data.state ;
		    
		if(state != '10A' &&  state != '1PU' ){
		    Ext.MessageBox.show({
				    title: '提示',
				    msg: '此方案已经启动或者无效，不能删除!',
				    buttons: Ext.MessageBox.OK,
				    width:250,
				    icon: Ext.MessageBox.ERROR
			});
			return;
	    }
	    	
		Ext.MessageBox.confirm('提示', '是否确定要删除该调度方案？', scheduleDel);
	}
	
	//调度方案删除方法
	function scheduleDel(btn){
		if (btn == 'yes'){
			var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
			var objId = selDb[0].data.scheduleId ;
			var operManId = session1.staff.staffId;
			var operManName = session1.staff.staffName;
			
			
			var icount = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", 
												"checkIsRunning",objId);
			if(icount != 0){
				Ext.MessageBox.show({
				    title: '提示',
				    msg: '此方案已经在执行中，暂时不能删除!',
				    buttons: Ext.MessageBox.OK,
				    width:200,
				    icon: Ext.MessageBox.ERROR
				});
				return;
			}
			
			callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "deleteDataSource", objId,operManId,operManName);			
			
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
			    Ext.example.msg('','调度方案删除成功！');
			}, 1000);
			
			Ext.getCmp('scheduleGrid').store.removeAll();
			Ext.getCmp('scheduleGrid').store.load({params:{start:0, limit:8,state:''}});
		}
	}
	
	function schRuleDelVlt(){
		Ext.MessageBox.confirm('提示', '是否确定要删除该方案规则？', schRuleDel);
	}
	
	
	
	//规则删除方法
	function schRuleDel(btn){
		if (btn == 'no'){
			return ;
		}else{
			var selSchRule = Ext.getCmp('ruleGrid').getSelectionModel().getSelections();
			if(selSchRule.length == 0){
				Ext.MessageBox.show({
		            title: '提示',
		            msg: '请选择方案规则 ！',
		            buttons: Ext.MessageBox.OK,
		            width:250,
		            icon: Ext.MessageBox.ERROR
		       	});
				return ;
			}
		
			var objId = selSchRule[0].data.schSequId ;
			var operManId = session1.staff.staffId ;
            var operManName = session1.staff.staffName ;
			callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleSequMangerClient", "deleteDataSource", objId,operManId,operManName);
			
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
			    Ext.example.msg('','方案规则删除成功！');
			}, 1000);
			
			Ext.getCmp('ruleGrid').store.removeAll();
			Ext.getCmp('ruleGrid').store.load({params:{start:0, limit:5}});
		}
	}
	
	//上移
	function up(){
		var selSchRule = Ext.getCmp('ruleGrid').getSelectionModel().getSelections();
		if(selSchRule.length == 0){
			Ext.MessageBox.show({
	            title: '提示',
	            msg: '请选择方案规则 ！',
	            buttons: Ext.MessageBox.OK,
	            width:250,
	            icon: Ext.MessageBox.ERROR
	       	});
			return ;
		}
		
		var schRules = Ext.getCmp('ruleGrid').store.data.items ;
		var sequIdArray = "";
		
		for(var i = 0; i < schRules.length; i++){
			if(i > 0) sequIdArray += ",";
			sequIdArray += schRules[i].data.schSequId ;
		}
		var schSequId = selSchRule[0].data.schSequId ;
		var objId = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleSequMangerClient", "upSequSch", sequIdArray,schSequId);
		Ext.getCmp('ruleGrid').store.removeAll();
		Ext.getCmp('ruleGrid').store.load({params:{start:0, limit:5}});
	}
  
	function down() {
		var selSchRule = Ext.getCmp('ruleGrid').getSelectionModel().getSelections();
		if(selSchRule.length == 0){
			Ext.MessageBox.show({
	            title: '提示',
	            msg: '请选择方案规则 ！',
	            buttons: Ext.MessageBox.OK,
	            width:250,
	            icon: Ext.MessageBox.ERROR
	       	});
			return ;
		}
	
		var schRules = Ext.getCmp('ruleGrid').store.data.items ;
		var sequIdArray = "";
		
		for(var i = 0; i < schRules.length; i++){
			if(i > 0) sequIdArray += ",";
			sequIdArray += schRules[i].data.schSequId ;
		}
		var schSequId = selSchRule[0].data.schSequId ;
		var objId = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleSequMangerClient", "downSequSch", sequIdArray,schSequId);
		Ext.getCmp('ruleGrid').store.removeAll();
		Ext.getCmp('ruleGrid').store.load({params:{start:0, limit:5}});
	}

	/*
	var tabs2 = new Ext.TabPanel({
		region: 'south',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.8,
	    height:Ext.getBody().getSize().height*0.375,
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[infoPanel]
	    //items:[infoPanel,ruleGrid]
	});*/
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[qryPanel,grid,infoPanel]
		//items:[qryPanel,grid,tabs2]
	});
	
	var viewport = new Ext.Viewport({
		el:'scheduleMngDiv',
		layout: 'border',
		items:[earthviewport]
	});
});

</script>