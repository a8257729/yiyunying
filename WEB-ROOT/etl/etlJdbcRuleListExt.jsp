<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>数据抽取规则配置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="/MOBILE/common/ztesoftext/tableWin.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">			
	</body>
</html>

<script language="JScript">
var session1 = GetSession();

Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var servStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'etlRuleId','etlRuleName','etlType','state',  'createDate','stateDate','operManId','operManName','memo',
        	'sourceDsId','sourceDsName','sourceTbName','targetDsId','targetDsName','targetTbId','targetTbName','targetTbCode',
        	'fetchMode','fetchModeText','fetchScript'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryAllEtlJdbcRuleExtAction'
        }),
        listeners:{
        	load: function(store){
				servGrid.getSelectionModel().selectRow(0);
			}
        }		
	});	
	//初使化下拉框的值,信息类型
        var dataSourceStore = new Ext.data.JsonStore({
            remoteSort: true,
            fields: ['ds_id', 'ds_name'],
            proxy: new Ext.data.HttpProxy({
                url: '/MOBILE/ExtServlet?actionName=etl/DataSourceForRuleAction&dsType=000'
            })
        });
        dataSourceStore.load();
	
	var stateStore = new Ext.data.ArrayStore({
		fields: ['value','text'],
		data:[
			['10A','有效'],
			['10P','无效']
		]
	});
	var fetchModeStore = new Ext.data.ArrayStore({
		fields: ['fetchModeId','fetchModeName'],
		data:[
			['1','全量'],
			['2','增量']
		]
	});
	
	/*
	'etlRuleId','etlRuleName','etlType','state',  'createDate','stateDate','operManId','operManName','memo',
        	'sourceDsId','sourceDsName','sourceTbName','targetDsId','targetDsName','targetTbName','fetchMode','fetchScript'
    */    	
	//创建列   
	var servColumn = new Ext.grid.ColumnModel([
	  	{header:'状态ID',dataIndex:'state',hidden:true},
	    {header:'规则ID',dataIndex:'etlRuleId',hidden:true},
	    {header:'目标表ID',dataIndex:'targetTbId',hidden:true},
	    {header:'抽取模式ID',dataIndex:'fetchMode',hidden:true},
	    {header:'源数据源ID',dataIndex:'sourceDsId',hidden:true},
	    {header:'目标数据源ID',dataIndex:'targetDsId',hidden:true},
	    {header:'抽取规则',dataIndex:'etlRuleName',width:120},	  
	    {header:'源数据源',dataIndex:'sourceDsName',width:120},
	    {header:'源表名',dataIndex:'sourceTbName',width:120},	 
	    {header:'目标数据源',dataIndex:'targetDsName',width:120},   
	    {header:'目标表编码',dataIndex:'targetTbCode',width:120},
	    {header:'抽取模式',dataIndex:'fetchModeText',width:120},		   
	    {header:'抽取条件',dataIndex:'fetchScript',width:120},
	    {header:'最后操作人',dataIndex:'operManName',width:120},
	    {header:'最后操作时间',dataIndex:'stateDate',width:150}
	   
	]);
	
    var servPb = new Ext.PagingToolbar({
		pageSize: 12,
		store: servStore,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
    
	var servView = new Ext.grid.GridView({
		sortAscText:'升序',
		sortDescText:'降序',
		columnsText:'列名'
	});

	//定义菜单
	var treeMenu = new Ext.menu.Menu({
        id : 'treeMenu'
	});
	
	//在Gird表单外弹出的菜单
	function outRowMenu(e){
		treeMenu.removeAll();
		treeMenu.addItem(new Ext.menu.Item({text:'增加',handler:function(){
			treeMenu.hide();
			add();		//新增操作
		}}));
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	//在Gird表单内弹出的菜单
	function onRowMenu(node,rowIndex,e){
		node.getSelectionModel().selectRow(rowIndex);			//选中所在的选择项上
		treeMenu.removeAll();
		treeMenu.addItem(new Ext.menu.Item({text:'增加',handler:function(item,e){
			treeMenu.hide();
			add();		//新增操作
		}}));
		treeMenu.addItem(new Ext.menu.Item({text:'修改',handler:function(){
			treeMenu.hide();
			update();		//更新操作			 
		}}));
		treeMenu.addItem(new Ext.menu.Item({text:'删除',handler:function(){
			treeMenu.hide();
			del();
		}}));
			
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	function initToolBars(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'增加',
             // <-- icon
             onClick:add// assign menu by instance
        });
		tb.add({
            text:'修改',
              // <-- icon
            onClick:update// assign menu by instance
        });
        tb.add({
            text:'删除',
             // <-- icon
            onClick:del
        });
            
        return tb;
	}
		
	var qryForm = new Ext.FormPanel({
		id:'qryForm',
		region: 'west',
		frame:true,
		title:'查询条件',
		labelWidth: 60,
        collapsible:true,
        width:200,
        defaultType: 'textfield',
        items:[{
	    		fieldLabel:'规则名称',
	            name:'etlRuleName',
	            id:'etlRuleName',
	            width:120
         	},{
	       		xtype:"panel",
	       		layout:"form",
	       		
	       		items:[{
	       		    xtype:'combo',
	              	fieldLabel: '源数据源',
	              	width:120,
	              	name: 'sourceDsId2',
	              	id: 'sourceDsId2',
	                valueField: 'ds_id',
	                displayField: 'ds_name',
	                mode:'local',
	                triggerAction: 'all',
	                forceSelection: true,		                
	                store:dataSourceStore 
	        		
	       		}]
	     	},{
	       		xtype:"panel",
	       		layout:"form",
	       		items:[{
	       			xtype:'textfield',
					fieldLabel:'源表名',
		            name:'sourceTableName',
		            id:'sourceTableName',
		            width:120
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		
	       		items:[{
	       		    xtype:'combo',
	              	fieldLabel: '源数据源',
	              	width:120,
	              	name: 'targetDsId2',
	              	id: 'targetDsId2',
	                valueField: 'ds_id',
	                displayField: 'ds_name',
	                mode:'local',
	                triggerAction: 'all',
	                forceSelection: true,		                
	                store:dataSourceStore 
	        		
	       		}]
	     	},{
	       		xtype:"panel",
	       		layout:"form",
	       		items:[{
	       			xtype:'textfield',
					fieldLabel:'目标表名',
		            name:'targetTableName',
		            id:'targetTableName',
		            width:120
	       		}]
	       	}],
        buttons:[{
        	text: '查询',
        	onClick:function(){
        		doQry();
        	}
        },{
        	text: '重置',
        	onClick:function(){
        		reset();
        	}
        }]
	});
    
	var servGrid = new Ext.grid.GridPanel({
		title:'数据抽取规则列表',
        region: 'center',
        collapsible:true, 
        width:Ext.getBody().getSize().width,   
	    height:Ext.getBody().getSize().height*0.90,
        store: servStore,
        autoScroll: true,
        loadMask: true,
        viewConfig : servView,
		cm:servColumn,
		tbar:initToolBars(),
		bbar:servPb,
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true,
			listeners:{
				rowselect:function(sm,row,rec) {
                    //Ext.getCmp('servForm').getForm().loadRecord(rec);					
                }
			}
		})
    });
    servStore.baseParams = {etlRuleName:"",etlType:"005"};
    servStore.load({params:{start:0,limit:50}});
     //添加右键事件
	servGrid.on("rowcontextmenu",onRowMenu);
	servGrid.on("contextmenu",outRowMenu);
	
	var viewport = new Ext.Viewport({
		el:Ext.getBody(),
		layout: 'border',
		items:[qryForm,{
			layout:'border',
			region:'center',
			items:[servGrid]
		}]
	});
	
	
	
	function doQry(){		
		var etlRuleName = Ext.getCmp('etlRuleName').getValue();
		var sourceDsId = Ext.getCmp('sourceDsId2').getValue();
		var targetDsId = Ext.getCmp('targetDsId2').getValue();
		var sourceTableName = Ext.getCmp('sourceTableName').getValue();
		var targetTableName = Ext.getCmp('targetTableName').getValue();
		var etlType = "005";
		servStore.baseParams = {etlRuleName:etlRuleName,
								etlType:etlType,
								sourceDsId:sourceDsId,
								sourceTableName:sourceTableName,
								targetDsId:targetDsId,
								targetTableName:targetTableName};
		servGrid.store.load({params:{start:getPageStart(), limit:50}});
	}
	
	function getPageStart(){
		var ap = servPb.getPageData().activePage;
		
		if(ap <= 1) return 0;
		
		return (ap-1) * 12;
	}

	function reset(){
		Ext.getCmp('etlRuleName').setValue('');
		Ext.getCmp('sourceTableName').setValue('');
		Ext.getCmp('targetTableName').setValue('');	
	}
	
	function add(){
		operServDefine('add');
	}
	
	function update(){
		var record = servGrid.getSelectionModel().getSelected(); //获取当前选中的整条记录，前提是必须设置为行选择模式
		if(record){
			operServDefine("update");		//更新操作
			Ext.getCmp('operForm').getForm().loadRecord(record);
		}else{
			Ext.Msg.alert("操作提示","请选择需要修改的抽取规则!");
		}

		
	}
	
	function del(){
		var record = servGrid.getSelectionModel().getSelected();
		if(record){
			Ext.Msg.confirm("删除提示","确定删除所选抽取规则吗?",function(btn){
				if(btn=="yes"){
					delServDefine(record.data.etlRuleId);
				}
			});		
		}else{
			Ext.Msg.alert("操作提示","请选择需要删除的抽取规则!");
		}
	}
		
	function delServDefine(etlRuleId){
		
		var servDefine = new Object();
	    servDefine.etlRuleId = etlRuleId;
	    servDefine.operManId = session1.staff.staffId;
		servDefine.operManName = session1.staff.staffName;
	    var result = invokeAction("/etl/DelEtlJdbcRuleAction",servDefine);
	   
   		if("success"==result){
   			Ext.example.msg("提示",'删除抽取规则成功');
   			doQry();	
   		}else{
   			Ext.example.msg("提示",'删除抽取规则失败');
   		}		
	}
	
	function operServDefine(oper){
		var mess = "add"==oper?"新增":"更新";
	    var operForm = new Ext.FormPanel({
	    	id:'operForm',
	    	labelAlign: 'left',
	 		frame:true,
	 		height:Ext.getBody().getSize().height*0.4,
	 		defaultType: 'textfield',
	 		buttonAlign:'center',
	 		layout:'table',
	        layoutConfig:{columns:2},//将父容器分成3列



		    items:[{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'抽取规则',
		       		name:'etlRuleName',		       		
		       		width:155,
		       		allowBlank:false, 
		            blankText:"抽取规则不能为空!"
				}]
	       	},{
	       		xtype:"panel",	       		
	       		layout:"form",
	       		labelAlign:"right",
	       		items:[{
					xtype:'combo',
	        		fieldLabel:'抽取模式',
	       			name:'fetchMode',
	              	id: 'fetchMode',
	                valueField: 'fetchModeId',
	                displayField: 'fetchModeName',
	                mode:'local',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,		                
	       			allowBlank:false, 
	            	blankText:"抽取模式不能为空!",		                
	                store:fetchModeStore 	       			
	       		}]
			},{
	       		xtype:"panel",
	       		layout:"form",
	       		
	       		items:[{
	       		    xtype:'combo',
	              	fieldLabel: '源数据源',
	              	name: 'sourceDsId',
	              	id: 'sourceDsId',
	                valueField: 'ds_id',
	                displayField: 'ds_name',
	                mode:'local',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,		                
	       			allowBlank:false, 
	            	blankText:"源数据源不能为空!",		                
	                store:dataSourceStore 
	        		
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		labelAlign:"right",
	       		items:[{
	        		fieldLabel:'源表名',
	       			name:'sourceTbName',
	       			id:'sourceTbName',
	       			width:155,
	       			allowBlank:false, 
	            	blankText:"源表名不能为空!"       		
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		
	       		items:[{
	       		    xtype:'combo',
	              	fieldLabel: '目标数据源',
	              	name: 'targetDsId',
	              	id: 'targetDsId',
	                valueField: 'ds_id',
	                displayField: 'ds_name',
	                mode:'local',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,		                
	       			allowBlank:false, 
	            	blankText:"目标数据源不能为空!",		                
	                store:dataSourceStore 
	        		
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		colspan:2,
	       		defaultType: 'textfield',
	       		items:[{
                        xtype: 'compositefield',
                        fieldLabel: '目标表选择',
                        allowBlank: false,
                        blankText: "目标表不能为空!",
                        items: [{
                            xtype: 'textfield',
                            name: 'targetTbName',
                            id: 'targetTbName',
                            width: '140'
                        },
                        {
                            xtype: 'button',
                            text: '..',
                            handler: selTable
                        },
                        {
                            xtype: 'hidden',
                            name: 'targetTbId',
                            id: 'targetTbId'
                          
                        }]

                    }]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		colspan:2,
	       		items:[{
			        xtype:"textarea",
			        fieldLabel:"脚本",
			        id:"fetchScript",
			        name:"fetchScript",
			        height:50,
			        anchor:"100%"
                    }]
	       	},{
	       			xtype:"hidden",
	       			name:"etlRuleId"
	       		},{
	       			xtype:"hidden",
	       			name:"etlType",
	       			value:"005"
	       		},{
	       			xtype:"hidden",
	       			name:"TARGET_TABLES_ID",
	       			value:"1"
	       		}],
	       	buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
	          
	            	if(operForm.getForm().isValid()){
	            	
		            	var servDefine = operForm.getForm().getValues();
		            	
		            	var etlRuleId = operForm.getForm().findField("etlRuleId").getValue();
		            	var etlRuleName = operForm.getForm().findField("etlRuleName").getValue();
		            	var fetchMode = operForm.getForm().findField("fetchMode").getValue();
		            	
		            	var sourceDsId = operForm.getForm().findField("sourceDsId").getValue();
		            	var sourceTbName = operForm.getForm().findField("sourceTbName").getValue();
		            	
		            	var targetDsId = operForm.getForm().findField("targetDsId").getValue();
		            	var targetTbId = operForm.getForm().findField("targetTbId").getValue();
		            	var fetchScript = operForm.getForm().findField("fetchScript").getValue();

		            	/*
		            	alert('etlRuleId:'+etlRuleId +' # '
		            		  + 'etlRuleName:'+etlRuleName +' # '
		            		  +'fetchMode:'+fetchMode +' # '
		            		  +'sourceDsId:'+sourceDsId +' # '
		            		  +'sourceTbName:'+sourceTbName +' # '
		            		  +'targetTbId:'+targetTbId +' # '
		            		  +'targetDsId:'+targetDsId +' # ');
		            	**/
		            	servDefine.etlRuleId = etlRuleId;
		            	servDefine.etlRuleName = etlRuleName;
		            	servDefine.sourceDsId = sourceDsId;
		            	servDefine.sourceTbName = sourceTbName;
		            	servDefine.targetDsId = targetDsId;
		            	servDefine.targetTbId = targetTbId;
		            	servDefine.fetchMode = fetchMode;
		            	servDefine.fetchScript=fetchScript;
		            	
		            	servDefine.oper=oper;
		            	servDefine.state="10A";
		            	servDefine.operManId = session1.staff.staffId;
		                servDefine.operManName = session1.staff.staffName;
		                
		                var act = "";
		                if(oper=="add"){
		                	act = "/etl/AddEtlJdbcRuleAction";
		                }else{
		                	act = "/etl/MdfEtlJdbcRuleAction";
		                }
		                var result = invokeAction(act,servDefine);
		                
		           		if("success"==result){
		           			Ext.example.msg(mess +"提示",mess+'成功');
		           			doQry();		//重新查询,更新列表
		           			win.close();
		           		}else{
		           			Ext.Msg.alert(mess +"提示",mess+'失败');
		           		}		            	
	            	}
			    }}
		    },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
		    }]
        	
	    });
		
		var win = new Ext.Window({
			id:"outerWin",   
            title: mess + "数据抽取",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',
            items: [operForm]               
        });
		     
        win.show();   
	}	    
	function selTable(){
		var parentWin = new Object();
		var win = new ZTESOFT.TableWin({parentWin:parentWin});
		parentWin.setRetValue = function(tableId,tableName,tableCode){
			
			Ext.getCmp("targetTbId").setValue(tableId);
			Ext.getCmp("targetTbName").setValue(tableName);
			
		}     
		win.show();
	}
	
});
//初始化工具栏
	

</script>