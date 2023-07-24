<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>数据抽取规则配置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="/MOBILE/common/ztesoftext/tableWin.js"></script>
		<script type="text/javascript" src="js/procDefiOper.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">			
	</body>
</html>

<script language="JScript">
var session1 = GetSession();
var pdOper = new ProcDefiOper();
Ext.apply(Ext.form.VTypes,{
    telNo:function(val,field){			//val指这里的文本框值，field指这个文本框组件，大家要明白这个意思	
       if(val){
           var telReg = /^0{0,1}(1[3|5|8][0-9])[0-9]{8}$/;
           return regExp(telReg,val);
       }
       return true;
    },telNoText: '不是有效的手机号码！',
    
    num:function(val,field){
    	var numReg = /^[0-9]*[1-9][0-9]*$/;
    	return regExp(numReg,val);
    },numText: '请输入正整数！'
});

//验证正则表大式




function regExp(reg,value){
	var re = new RegExp(reg);
	
	if(!value.match(re)) {
		 return false
	}
	
	return true;
}

Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var servStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'etlRuleId','etlRuleName','etlType','state',  'createDate','stateDate','operManId','operManName','memo','stateText','fetchModeText',
        	'sourceDsId','sourceDsName','sourceTbName','targetTablesId','targetTablesName','fetchMode','fetchScript','tacheId','tacheName'
			//'primaryColumnId','primaryColumnName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryAllEtlRuleAction'
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
	
	//'etlRuleId','etlRuleName','etlType','state',  'createDate','stateDate','operManId','operManName','memo',
    //'sourceDsId','sourceDsName','sourceTbName','targetTablesId','targetTablesName','fetchMode'
	//'primaryColumnId','primaryColumnName'
	//创建列   
	var servColumn = new Ext.grid.ColumnModel([
	    {header:'抽取规则',dataIndex:'etlRuleName',width:120},
	    {header:'流程环节',dataIndex:'tacheName',width:120},	  
	    {header:'源数据源',dataIndex:'sourceDsName',width:120},
	    {header:'源表名',dataIndex:'sourceTbName',width:120},	    
	    {header:'目标表名',dataIndex:'targetTablesName',width:120},
	    {header:'抽取模式',dataIndex:'fetchModeText',width:120},		   
	    {header:'抽取条件',dataIndex:'fetchScript',width:120},
	    {header:'状态',dataIndex:'stateText',width:50},
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
	    height:Ext.getBody().getSize().height*0.8,
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
                    Ext.getCmp('servForm').getForm().loadRecord(rec);					
                }
			}
		})
    });
    servStore.baseParams = {etlRuleName:"",etlType:"000"};
    servStore.load({params:{start:0,limit:12}});
     //添加右键事件
	servGrid.on("rowcontextmenu",onRowMenu);
	servGrid.on("contextmenu",outRowMenu);
	
	/*var servTabs = new Ext.TabPanel({
		region: 'south',
		height:Ext.getBody().getSize().height*0.35,
        width:Ext.getBody().getSize().width*0.85,
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
        	title:'基本信息',
        	items:[getServForm()]
        }]
    });	*/
	
	var viewport = new Ext.Viewport({
		el:Ext.getBody(),
		layout: 'border',
		items:[qryForm,{
			layout:'border',
			region:'center',
			items:[servGrid,getServForm()]
		}]
	});
	
	function getServForm(){
	   var servForm = new Ext.FormPanel({
	   		id:'servForm',
			region: 'south',
			labelAlign: 'left',
			height:Ext.getBody().getSize().height*0.2,
		 	frame:true,
	        layout:'column',
	        defaults:{
	        	columnWidth:'.33'
	        },
		    items:[{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'抽取规则',
					anchor:'95%',
		       		name:'etlRuleName'
				}]
	       	},{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'流程环节',
					anchor:'95%',
		       		name:'tacheName'
				}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'抽取模式',
	        		anchor:'95%',
	       			name:'fetchModeText'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'状态',
	        		anchor:'95%',
	       			name:'stateText'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'源数据源',
	        		anchor:'95%',
	       			name:'sourceDsName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'源表名',
	        		anchor:'95%',
	       			name:'sourceTbName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'目标表名',
	        		anchor:'95%',
	       			name:'targetTablesName'
	       		}]
	       	},
	       	/**{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'主键',
	       			name:'primaryColumnName'
	       		}]
	       	},
	       	*/{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'最后操作人',
	        		anchor:'95%',
	       			name:'operManName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'最后操作时间',
	        		anchor:'95%',
	       			name:'stateDate'
	       		}]
	       	}]
		});	
		return servForm;
	}
	
	
	
	function doQry(){		
		var etlRuleName = Ext.getCmp('etlRuleName').getValue();
		var sourceDsId = Ext.getCmp('sourceDsId2').getValue();
		var sourceTableName = Ext.getCmp('sourceTableName').getValue();
		var targetTableName = Ext.getCmp('targetTableName').getValue();
		var etlType = "000";
		servStore.baseParams = {etlRuleName:etlRuleName,
								etlType:etlType,
								sourceDsId:sourceDsId,
								sourceTableName:sourceTableName,
								targetTableName:targetTableName};
		servGrid.store.load({params:{start:getPageStart(), limit:12}});
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
	
	

	function showPrivWin(servName,servId){
	    var privForm = new Ext.FormPanel({
	    	id:'privForm',
	    	labelAlign: 'left',
	 		frame:true,
	 		height:Ext.getBody().getSize().height * 0.92,
	 		buttonAlign:'center',
	 		defaultType: 'fieldset', // each item will be a radio button
		    items:[{
            	title: '基本信息',
            	autoHeight: true,
            	items:[{
            		xtype:'panel',
            		layout: 'column',
    				defaults: {
    					 columnWidth: 0.55
					},
	        		items:privBaseItems
            	}]
		    }],
	       	buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
            		var result= saveServPriv(privForm);
	           		if(result){
	           			Ext.example.msg("操作提示",'权限分配成功');
	           			doQry();		//重新查询,更新列表
	           			win.close();
	           		}else{
	           			Ext.Msg.alert("操作提示",'权限分配失败');
	           		}	            	
			    }}
		    },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
		    }]
        	
	    });
	   
	    privForm.getForm().findField("servicesName").setValue(servName);
	    privForm.getForm().findField("servicesId").setValue(servId);
		
		var win = new Ext.Window({
			id:"outerWin",   
            title: "配置权限",
        	width:Ext.getBody().getSize().width*0.70, 
            plain: true,                       
            layout: 'anchor',   
            items: [privForm]               
        });
          
        win.show();   
	}
	
		
		
	function delServDefine(etlRuleId){
		
		var servDefine = new Object();
	    servDefine.etlRuleId = etlRuleId;
	    servDefine.operManId = session1.staff.staffId;
		servDefine.operManName = session1.staff.staffName;
		servDefine.mothed = "delete";
	    var result = invokeAction("/etl/EtlRuleManagerAction",servDefine);
	   
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
	    	height:Ext.getBody().getSize().height*0.4,
	 		frame:true,
	 		defaultType: 'textfield',
	 		buttonAlign:'center',
	 		layout:'column',
		    items:[{
				xtype:"panel",
				layout:"form",
				columnWidth:'.5',
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'抽取规则',
		       		name:'etlRuleName',
		       		anchor:'95%',
		       		allowBlank:false, 
		            blankText:"抽取规则不能为空!"
				}]
	       	},{
	       		xtype:"panel",	       		
	       		layout:"form",
	       		columnWidth:'.5',
	       		labelAlign:"right",
	       		items:[{
					xtype:'combo',
	        		fieldLabel:'抽取模式',
	       			name:'fetchMode',
	              	id: 'fetchMode',
	              	anchor:'95%',
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
	       		columnWidth:'.5',
	       		items:[{
	       		    xtype:'combo',
	              	fieldLabel: '源数据源',
	              	name: 'sourceDsId',
	              	id: 'sourceDsId',
	              	anchor:'95%',
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
	       		columnWidth:'.5',
	       		defaultType: 'textfield',
	       		labelAlign:"right",
	       		items:[{
	        		fieldLabel:'源表名',
	       			name:'sourceTbName',
	       			id:'sourceTbName',
	       			anchor:'95%',
	       			allowBlank:false, 
	            	blankText:"源表名不能为空!"       		
	       		}]			
			},{
	       		xtype:"panel",
	       		layout:"form",
	       		columnWidth:'.5',
	       		defaultType: 'textfield',
	       		items:[{
                       xtype: 'compositefield',
                       fieldLabel: '目标表选择',

                       items: [{
                           xtype: 'textfield',
                           name: 'targetTablesName',
                           id: 'targetTablesName',
                           allowBlank: false,
                       	   blankText: "目标表不能为空!",
                           anchor:'95%'
                       },{
                           xtype: 'button',
                           text: '..',
                           handler: selTable
                       },{
                           xtype: 'hidden',
                           name: 'targetTablesId',
                           id: 'targetTablesId'
                         
                       }]

                  }]		
			},{
	       		xtype:"panel",
	       		layout:"form",
	       		columnWidth:'.5',
	       		labelAlign:"right",
	       		items:[{
				    xtype: 'compositefield',
				    fieldLabel: '流程环节名',
				    items: [{
				        xtype: 'textfield',
				        name: 'tacheName',
				        id: 'tacheName',
				        anchor:'95%',
				        readOnly: true,
				        allowBlank:false, 
		            	blankText:"流程环节不能为空!"
				    },{
				        xtype: 'button',
				        text: '..',
				        listeners:{
				        	"click":function(){
				        		pdOper.showCataWin();
				        	}
				        }
				    },{
				        xtype: 'button',
				        text: '清空',
				        listeners:{
				        	"click":function(){
				        		Ext.getCmp("tacheId").setValue("");
		    		 			Ext.getCmp("tacheName").setValue("");
				        	}
				        }			    
				    }]
	            }]			
			},{
	       		xtype:"panel",
	       		layout:"form",
	       		columnWidth:'1',
	       		items:[{
			        xtype:"textarea",
			        fieldLabel:"脚本",
			        id:"fetchScript",
			        name:"fetchScript",
			        height:50,
			        anchor:'95%'
                }]			
			},{	
		    	xtype:'hidden',
		    	name:'tacheId',
		    	id:'tacheId'
		    },{
       			xtype:"hidden",
       			name:"etlRuleId"
       		},{
       			xtype:"hidden",
       			name:"etlType",
       			value:"000"
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
		            	
		            	var targetTablesId = operForm.getForm().findField("targetTablesId").getValue();
		            	var fetchScript = operForm.getForm().findField("fetchScript").getValue();
		            	//var primaryColumnId = operForm.getForm().findField("primaryColumnId").getValue();
		            	/**
		            	alert('etlRuleId:'+etlRuleId +' # '
		            		  + 'etlRuleName:'+etlRuleName +' # '
		            		  +'fetchMode:'+fetchMode +' # '
		            		  +'sourceDsId:'+sourceDsId +' # '
		            		  +'sourceTbName:'+sourceTbName +' # '
		            		  +'targetTablesId:'+targetTablesId +' # '
		            		  +'primaryColumnId:'+primaryColumnId +' # ');
		            	*/
		            	servDefine.etlRuleId = etlRuleId;
		            	servDefine.etlRuleName = etlRuleName;
		            	servDefine.sourceDsId = sourceDsId;
		            	servDefine.sourceTbName = sourceTbName;
		            	servDefine.targetTablesId = targetTablesId;
		            	servDefine.fetchMode = fetchMode;
		            	//servDefine.primaryColumnId = primaryColumnId;
		            	servDefine.fetchScript=fetchScript;
		            	
		            	servDefine.oper=oper;
		            	servDefine.state="10A";
		            	servDefine.operManId = session1.staff.staffId;
		                servDefine.operManName = session1.staff.staffName;
		                
		                var result = invokeAction("/etl/InsertEtlRuleAction",servDefine);
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
        	width:Ext.getBody().getSize().width*0.65, 
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
			
			Ext.getCmp("targetTablesId").setValue(tableId);
			Ext.getCmp("targetTablesName").setValue(tableName);
			
		}     
		win.show();
	}
	
	function selColumn(){
		var targetTablesId = Ext.getCmp("targetTablesId").getValue();
		
		selMeterColumn(targetTablesId,'primaryColumnId','primaryColumnName');
	}
});


		function selMeterColumn(tableId,id_filed,name_filed){

				var meterdataStore = new Ext.data.JsonStore({
			        root: 'Body',
			        totalProperty: 'totalCount',
			        fields: [ 
			        	'columnId', 'columnName','columnCode','dataType','remark'
			        ],
			        proxy: new Ext.data.HttpProxy({
			            url: '/MOBILE/ExtServlet?actionName=etl/QryMeterdataColumnsExtAction',
			            method: 'POST'  
			        }),
			        listeners:{
			        	load: function(store){
							
						}
			        }
			    });
			    meterdataStore.baseParams = {tableId:tableId};
			    meterdataStore.reload({params:{start:0, limit:10}});
		    
	        	//'columnId', 'columnName','columnCode','dataType','remark'
	        	//数据类型  NUM : 数值类型  DATE: 日期类型  VAR:字符类型			    
				var column = new Ext.grid.ColumnModel([
					    {id:'columnsId',header:'标识',dataIndex:'columnId',hidden:true},
					    {header:'字段编码',dataIndex:'columnCode',sortable:true,width:200},
					    {header:'字段名称',dataIndex:'columnName',sortable:true,width:200},
					    {header:'数据类型',dataIndex:'dataType',sortable:true,width:200,renderer:changeColumnType},
					    {header:'备注',dataIndex:'remark',sortable:true,width:200}
					]); 
					
					var pagingBar = new Ext.PagingToolbar({
						pageSize: 10,
						store: meterdataStore,
						displayInfo: true,
						displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg: "没有记录"
				    });
				    
				var meterdataPanel = new Ext.grid.GridPanel({
						id:'meterdataPanel',
				        region: 'center', 
				        layout:"fit",
					    width:Ext.getBody().getSize().width,
				    	height:Ext.getBody().getSize().height,
					    //title:'系统表字段列表',
				        store: meterdataStore,
				        trackMouseOver:false,
				        autoScroll: true,
				        loadMask: true,
				        viewConfig : gridView,
						cm:column,
						bbar: pagingBar,
						sm:new Ext.grid.RowSelectionModel({
							singleSelect:true,
							listeners:{
								rowselect:function(sm,row,rec) {

				                }
							}
						})
				    }); 
				
				var gridView = new Ext.grid.GridView({
						sortAscText:'升序',
						sortDescText:'降序',
						columnsText:'列名'
					});			    
		
				var win = new Ext.Window({
			       	id:'selSysTable',
			        title: '选择字段',
			        closable:true,
			        width:600,
			        height:400,
			        layout:'border',
			        buttonAlign:'center',
			        items:[meterdataPanel],
		            buttons:[{
		            	text:'确定',
		            	onClick:function(){
		            		var meterdataRecord = meterdataPanel.getSelectionModel().getSelected();
							if(meterdataRecord){
							//'columnId', 'columnName','columnCode','dataType','remark'
								var columnId = meterdataRecord.data.columnId;
								var columnName = meterdataRecord.data.columnName;
		            		//alert('columnId:' + columnId + 'columnName:'+columnName);
		            		//id_filed,name_filed
		            		Ext.getCmp(id_filed).setValue(columnId);
		            		Ext.getCmp(name_filed).setValue(columnName);
		            		
		            		
		            		}
		            		Ext.getCmp('selSysTable').close();
						}
		            },{
		            	text:'取消',
		            	onClick:function(){
							 Ext.getCmp('selSysTable').close();
						}
		            }]
		        });
		
		
			win.show();
		}//end selMeterColumn
			
	    function changeColumnType(val){
	    //数据类型  NUM : 数值类型  DATE: 日期类型  VAR:字符类型
	        if(val == 'NUM'){
	            return '数值类型';
	        }else if(val == 'DATE'){
	            return '日期类型';
	        }else if(val == 'VAR'){
	            return '字符类型';
	        }else{
	        	return val;
	        }
	    }			    
 

</script>