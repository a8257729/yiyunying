<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>数据汇总规则配置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="js/procDefiOper.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">			
	</body>
</html>

<script language="JScript">
var session1 = GetSession();
//验证正则表大式

var pdOper = new ProcDefiOper();


Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var servStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'etlRuleId','etlRuleName','etlType','state', 'createDate','stateDate','operManId','operManName','memo','procName','tacheName','tacheId'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=etl/QryAllEtlSumRuleExtAction'
        }),
        listeners:{
        	load: function(store){
				servGrid.getSelectionModel().selectRow(0);
			}
        }		
	});	

	
	//创建列   
	var servColumn = new Ext.grid.ColumnModel([ 
	    {header:'汇总汇总规则',dataIndex:'etlRuleName',width:150},	  
	    {header:'流程环节',dataIndex:'tacheName',width:120},
	    {header:'存储过程',dataIndex:'procName',width:200},
	    {header:'状态',dataIndex:'state',width:50,renderer:changeState},
	    {header:'备注',dataIndex:'memo',width:150},
	    {header:'最后操作人',dataIndex:'operManName',width:100},
	    {header:'最后操作时间',dataIndex:'stateDate',width:130}
	   
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
	       			xtype:'textfield',
					fieldLabel:'存储过程',
		            name:'procName',
		            id:'procName',
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
		title:'数据汇总规则列表',
        region: 'center',
        collapsible:true, 
        width:Ext.getBody().getSize().width,   
	    height:Ext.getBody().getSize().height*0.55,
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
    servStore.baseParams = {etlRuleName:"",etlType:"004"};
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
			height:Ext.getBody().getSize().height*0.3,
		 	frame:true,
	        layout:'column',
	        defaults:{
	        	columnWidth :'.5'
	        },
		    items:[{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'汇总规则',
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
	        		fieldLabel:'存储过程',
	        		anchor:'95%',
	       			name:'procName'
	       		}]
	       	},{
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
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		columnWidth:'1',
	       		items:[{
			        xtype:"textarea",
			        fieldLabel:"备注",
			        name:"memo",
			        height:50,
			        anchor:"100%"
                }]
	       	}]
		});	
		return servForm;
	}
	
	
	
	function doQry(){		
		var etlRuleName = Ext.getCmp('etlRuleName').getValue();
		var procName = Ext.getCmp('procName').getValue();
		var etlType = "004";
		servStore.baseParams = {etlRuleName:etlRuleName,
								etlType:etlType,
								procName:procName};
		servGrid.store.load({params:{start:getPageStart(), limit:12}});
	}
	
	function getPageStart(){
		var ap = servPb.getPageData().activePage;
		
		if(ap <= 1) return 0;
		
		return (ap-1) * 12;
	}

	function reset(){
		Ext.getCmp('etlRuleName').setValue('');
		Ext.getCmp('procName').setValue('');
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
			Ext.Msg.alert("操作提示","请选择需要修改的汇总规则!");
		}

		
	}
	
	function del(){
		var record = servGrid.getSelectionModel().getSelected();
		if(record){
			Ext.Msg.confirm("删除提示","确定删除所选汇总规则吗?",function(btn){
				if(btn=="yes"){
					delServDefine(record.data.etlRuleId);
				}
			});		
		}else{
			Ext.Msg.alert("操作提示","请选择需要删除的汇总规则!");
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
	    var result = invokeAction("/etl/DelEtlSumRuleAction",servDefine);
	   
   		if("success"==result){
   			Ext.example.msg("提示",'删除汇总规则成功');
   			doQry();	
   		}else{
   			Ext.example.msg("提示",'删除汇总规则失败');
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
		    items:[{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'汇总规则',
		       		name:'etlRuleName',		       		
		       		width:155,
		       		allowBlank:false, 
		            blankText:"汇总规则不能为空!"
				}]
	       	},{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'存储过程',
		       		name:'procName',		       		
		       		width:155,
		       		allowBlank:false, 
		            blankText:"存储过程不能为空!"   			
	       		}]
			},{
	       		xtype:"panel",
	       		layout:"form",
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
	       		colspan:2,
	       		items:[{
			        xtype:"textarea",
			        fieldLabel:"备注",
			        name:"memo",
			        height:50,
			        anchor:"100%"
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
		            	
		            	servDefine.oper=oper;
		            	servDefine.state="10A";
		            	servDefine.operManId = session1.staff.staffId;
		                servDefine.operManName = session1.staff.staffName;
		                
		                var act = "";
		                if(oper=="add"){
		                	act = "/etl/AddEtlSumRuleAction";
		                }else{
		                	act = "/etl/MdfEtlSumRuleAction";
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
            title: mess + "数据汇总",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',
            items: [operForm]               
        });
		     
        win.show();   
	}	    
	
});

function changeState(val){
   		
	if(val == '10A'){
		return '有效';
	}else {
		return '无效';
	}
	return val;
}			

</script>