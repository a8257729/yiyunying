<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>文件抽取规则配置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript"	src="js/servPriv.js"></script>
		<script type="text/javascript"	src="js/servVType.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">			
	</body>
</html>

<script language="JScript">
var session1 = GetSession();
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
        	'etlRuleName', 'sourceDsName', 'fileDir', 'fileName','targetDsName',
        	'fileBakDir','operManName','stateDate','stateText','state','etlRuleId','sourceDsId','targetDsId'
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
                url: '/MOBILE/ExtServlet?actionName=etl/DataSourceForRuleAction&dsType=001'
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
	
	//创建列   
	var servColumn = new Ext.grid.ColumnModel([
	  {header:'状态ID',dataIndex:'state',hidden:true},
	    {header:'规则ID',dataIndex:'etlRuleId',hidden:true},
	    {header:'源数据源ID',dataIndex:'sourceDsId',hidden:true},
	    {header:'目标数据源ID',dataIndex:'targetDsId',hidden:true},
	    {header:'抽取规则',dataIndex:'etlRuleName',width:120},
	    {header:'源数据源',dataIndex:'sourceDsName',width:120},
	    {header:'文件目录',dataIndex:'fileDir',width:120},
	    {header:'文件名',dataIndex:'fileName',width:120},
	    {header:'目标数据源',dataIndex:'targetDsName',width:150},
	    {header:'目标目录',dataIndex:'fileBakDir',width:120},
	     {header:'最后操作人',dataIndex:'operManName',width:120},
	    {header:'最后操作时间',dataIndex:'stateDate',width:150},
	    {header:'状态',dataIndex:'stateText',width:50}
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
	       		    xtype:'combo',
	              	fieldLabel: '目标数据',
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
		title:'文件抽取规则列表',
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
    servStore.baseParams = {etlRuleName:"",etlType:"003"};
    servStore.load({params:{start:0,limit:12}});
     //添加右键事件
	servGrid.on("rowcontextmenu",onRowMenu);
	servGrid.on("contextmenu",outRowMenu);
	
	var servTabs = new Ext.TabPanel({
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
    });	
	
	var viewport = new Ext.Viewport({
		el:Ext.getBody(),
		layout: 'border',
		items:[qryForm,{
			layout:'border',
			region:'center',
			items:[servGrid,servTabs]
		}]
	});
	
	function getServForm(){
	   var servForm = new Ext.FormPanel({
	   		id:'servForm',
			region: 'south',
			labelAlign: 'left',
			height:Ext.getBody().getSize().height*0.35,
		 	frame:true,
	        layout:'table',
	        layoutConfig:{columns:3},//将父容器分成3列


		    items:[{
				xtype:"panel",
				layout:"form",
				defaultType: 'textfield',
	       		items:[{
					fieldLabel:'抽取规则',
		       		name:'etlRuleName'
				}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'源数据源',
	       			name:'sourceDsName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'文件目录',
	       			name:'fileDir'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'文件名',
	       			name:'fileName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'目标数据源',
	       			name:'targetDsName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'目标目录',
	       			name:'fileBakDir'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'最后操作人',
	       			name:'operManName'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'最后操作时间',
	       			name:'stateDate'
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'状态',
	       			name:'stateText'
	       		}]
	       	}]
		});	
		return servForm;
	}
	
	
	
	function doQry(){		
		var etlRuleName = Ext.getCmp('etlRuleName').getValue();
		var targetDsId = Ext.getCmp('targetDsId2').getValue();
		var sourceDsId = Ext.getCmp('sourceDsId2').getValue();
		var etlType = "003";
		servStore.baseParams = {etlRuleName:etlRuleName,etlType:etlType,targetDsId:targetDsId,sourceDsId:sourceDsId};
		servGrid.store.load({params:{start:getPageStart(), limit:12}});
	}
	
	function getPageStart(){
		var ap = servPb.getPageData().activePage;
		
		if(ap <= 1) return 0;
		
		return (ap-1) * 12;
	}

	function reset(){
		Ext.getCmp('etlRuleName').setValue('');
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
	 		frame:true,
	 		height:Ext.getBody().getSize().height*0.3,
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
	       		items:[{
	        		fieldLabel:'文件目录',
	       			name:'fileDir',
	       			id:'fileDir',
	       			width:155,
	       			allowBlank:false, 
	            	blankText:"文件目录不能为空!"       		
	       		}]
	       	},{
	       		xtype:"panel",
	       		layout:"form",
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'文件名称',
	       			name:'fileName',
	       			width:155,
	       			allowBlank:false, 
	            	blankText:"文件名称不能为空!"       		
	       		}]
	       	},{
	       		xtype:"panel",	       		
	       		layout:"form",
	       		items:[{
					xtype:'combo',
	        		fieldLabel:'目标数据源',
	       			name:'targetDsId',
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
	       		defaultType: 'textfield',
	       		items:[{
	        		fieldLabel:'目标目录',
	       			name:'fileBakDir',
	       			width:155,
	       			allowBlank:false, 
	            	blankText:"目标目录不能为空!"       		
	       		}]
	       	},{
	       			xtype:"hidden",
	       			name:"etlRuleId"
	       		},{
	       			xtype:"hidden",
	       			name:"etlType",
	       			value:"003"
	       		}],
	       	buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
	          
	            	if(operForm.getForm().isValid()){
		            	var servDefine = operForm.getForm().getValues();
		            	var sourceDsId = operForm.getForm().findField("sourceDsId").getValue();
		            	var targetDsId = operForm.getForm().findField("targetDsId").getValue();
		            	servDefine.sourceDsId = sourceDsId;
		            	servDefine.targetDsId = targetDsId;
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
            title: mess + "共享对像",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',   
            items: [operForm]               
        });
          
        win.show();   
	}	    
});

//初始化工具栏
	

</script>