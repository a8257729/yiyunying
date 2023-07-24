<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>数据转换规则配置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script type="text/javascript" src="/MOBILE/common/ztesoftext/tableWin.js"></script>
		<script type="text/javascript" src="../etl/js/procDefiOper.js"></script>
		
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden;padding: 0 0 0 0px;">
		<div id="main"></div>
	</body>
</html>

<script language="JavaScript">

	var session1 = GetSession();
	var pdOper = new ProcDefiOper();

	var staffId = session1.staff.staffId;			//创建人ID
	var staffName = session1.staff.staffName;		//创建人名
	
	
    var body_width = Ext.getBody().getSize().width;
    var body_height = Ext.getBody().getSize().height;
    
    var transRuleStore;
    var ruleItemStore;
    
	Ext.onReady(function() {

    
		 var oper = new Oper();
	    oper.initMainPanel(); 
	});




	//修改或增加



	function openRuleDetail(etlRuleId){
		
		//详细页面layout
		var transRuleForm = new Ext.form.FormPanel({
			id:'transRuleForm',
			//title:'编辑便签',
			region: 'center',
			frame:true,
			//collapsible :true,
			width: body_width,
			height:body_height*0.2,
			bodyStyle: 'padding: 5px 10px 0 10px;',
			anchor: '95%',
			labelWidth: 80,
			buttonAlign:'center',
			items:[
			{	
				xtype: 'textfield',
				fieldLabel:'规则名称',
				anchor:'75%',
				id:'etlRuleName',
				name:'etlRuleName'
			},{
	            columnWidth:.9,
	            layout:"form",
	            //labelWidth:80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
	            //labelAlign:"right",
	            items:[{
	                    xtype: 'hidden',
				        name: 'sourceTbId',
				        id: 'sourceTbId'
					    },{
	                    xtype: 'compositefield',
	                    fieldLabel: '源表',
	                    items: [{
		                         xtype: 'textfield',
		                         name: 'sourceTableName',
		                         id: 'sourceTableName',
		                         anchor:'90%',
		                         width: 200,
		                         disabled :true,
		                         allowBlank:false,
		                         blankText:"源表不能为空，请填写."
			                     },
			                     {
			                      xtype: 'button',
			                      text: '选择',
			                      handler: function(){
			                      		selSysTable('sourceTbId','sourceTableName');
			                      }
			                    }]
			           }]
			},{
	            columnWidth:.9,
	            layout:"form",
	            labelWidth:80,//注意，这个参数在这里可以调整简单fieldLabel的布局位置
	            //labelAlign:"right",
	            items:[{
	                    xtype: 'hidden',
				        name: 'targetTbId',
				        id: 'targetTbId'
					    },{
	                    xtype: 'compositefield',
	                    fieldLabel: '目标表',
	                    items: [{
		                         xtype: 'textfield',
		                         name: 'targetTableName',
		                         id: 'targetTableName',
		                         anchor:'90%',
		                         width: 200,
		                         disabled :true,
		                         allowBlank:false,
		                         blankText:"目标表不能为空，请填写."
			                     },
			                     {
			                      xtype: 'button',
			                      text: '选择',
			                      handler: function(){
			                      		selSysTable('targetTbId','targetTableName');
			                      }
			                    }]
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
		    	xtype:'hidden',
		    	name:'tacheId',
		    	id:'tacheId'
		    }]
		});
		
		var win = new Ext.Window({
	       	id:'transRuleDetail',
	        title: '查看转换规则',
	        closable:true,
	        width:600,
	        height:200,
	        layout:'border',
	        buttonAlign:'center',
	        items:[transRuleForm],
            buttons:[{
            	text:'确定',
            	onClick:function(){
	                if(transRuleForm.getForm().isValid()){
						var transRule = transRuleForm.getForm().getValues();
						//创建者为当前用户
						transRule.operManId = staffId;
						transRule.operManName = staffName;
						var result;
						//当ID存在的场合，执更新操作。



						if(etlRuleId){
							//ID
							transRule.etlRuleId = etlRuleId;
							//更新
							result = invokeAction("/etl/MdfTransRuleAction",transRule);
						}else{
							//插入
							result = invokeAction("/etl/AddTransRuleAction",transRule);
						}
		           		if("success"==result){
							transRuleStore.reload({ params: { start: 0, limit: 5 }});
							Ext.getCmp('transRuleDetail').close();
		           		}else{
							Ext.MessageBox.show({
								title:'操作提示',
								msg:'提交失败,请重试或与管理员联系!',
				 				buttons: Ext.MessageBox.OK,
				           		icon: Ext.MessageBox.ERROR
							});	           			
		           		}
	                }
				}
            },{
            	text:'取消',
            	onClick:function(){
					 Ext.getCmp('transRuleDetail').close();
				}
            }]
        });
	 win.show();
	 
	 if(etlRuleId){
		
			//更新场合下，获取选中的行ID
	 		var paramObj = new Object();
			paramObj.etlRuleId = etlRuleId;
			//alert(editNotepadId);
			var _result = invokeAction('/etl/QryTransRuleAction',paramObj);
			if(_result){
				
				transRuleForm.getForm().findField('etlRuleName').setValue(_result.etlRuleName);
				transRuleForm.getForm().findField('tacheId').setValue(_result.tacheId);
				transRuleForm.getForm().findField('tacheName').setValue(_result.tacheName);
				transRuleForm.getForm().findField('sourceTbId').setValue(_result.sourceTbId);
				transRuleForm.getForm().findField('sourceTableName').setValue(_result.sourceTableName);
				
				transRuleForm.getForm().findField('targetTbId').setValue(_result.targetTbId);
				transRuleForm.getForm().findField('targetTableName').setValue(_result.targetTableName);
				
			}
	 		win.setTitle('修改转换规则');
		}else{
			win.setTitle('新建转换规则');
		}
	}




	//修改或增加



	function openItemDetail(param){
	
		var etlRuleId =param.etlRuleId;
		
		var sourceTbId =param.sourceTbId;
		var sourceTableName =param.sourceTableName;
		
		var targetTbId =param.targetTbId;
		var targetTableName =param.targetTableName;
		
		var operItemId =param.operItemId;
		

		//详细页面layout
		var transRuleForm = new Ext.form.FormPanel({
			id:'transRuleForm',
			region: 'center',
			frame:true,
			//collapsible :true,
			width: Ext.getBody().getSize().width,
			height:Ext.getBody().getSize().height*0.5,
			bodyStyle: 'padding: 5px 10px 0 10px;',
			anchor: '95%',
			labelWidth: 80,
			buttonAlign:'center',
			layout:"form",//源表字段名,sourceField
			items:[{
              xtype:"panel",
              layout:"column",
              fieldLabel:"源表",
              isFormField:true,
              items:[{
	                    xtype: 'hidden',
				        name: 'sourceTbId',
				        id: 'sourceTbId',
				        value:sourceTbId
					},{
                        columnWidth:.3,
                        xtype:"textfield",
                        disabled :true,
                        name:"sourceTableName",
                        value:sourceTableName,
                        anchor:"50%"
              		},{
                        columnWidth:.5,
                        layout:"form",
                        //labelWidth:40,
                        labelAlign:"right",
                        items:[{
		                    xtype: 'hidden',
					        name: 'sourceColumnsId',
					        id: 'sourceColumnsId'
							},{
		                    xtype: 'compositefield',
		                    fieldLabel: '源表字段',
		                    items: [{
			                         xtype: 'textfield',
			                         name: 'sourceColumnsName',
			                         id: 'sourceColumnsName',
			                         anchor:'90%',
			                         width: 200,
			                         //disabled :true,
			                         allowBlank:false,
			                         blankText:"源表字段不能为空，请填写."
				                     },
				                     {
				                      xtype: 'button',
				                      text: '选择',
				                      handler: function(){
				                      		selMeterColumn(sourceTbId,'sourceColumnsId','sourceColumnsName');
				                      }
				                    }]
                        }]
              		}]
              },{
              xtype:"panel",
              layout:"column",
              fieldLabel:"目标表",
              isFormField:true,
              items:[{
	                    xtype: 'hidden',
				        name: 'targetTbId',
				        id: 'targetTbId',
				        value:targetTbId
					},{
                        columnWidth:.3,
                        xtype:"textfield",
                        disabled :true,
                        name:"targetTableName",
                        value:targetTableName,
                        anchor:"50%"
              		},{
                        columnWidth:.5,
                        layout:"form",
                        //labelWidth:40,
                        labelAlign:"right",
                        items:[{
		                    xtype: 'hidden',
					        name: 'targetColumnsId',
					        id: 'targetColumnsId'
							},{
		                    xtype: 'compositefield',
		                    fieldLabel: '目标字段',
		                    items: [{
			                         xtype: 'textfield',
			                         name: 'targetColumnsName',
			                         id: 'targetColumnsName',
			                         anchor:'90%',
			                         width: 200,
			                         //disabled :true,
			                         allowBlank:false,
			                         blankText:"目标源表字段不能为空，请填写."
				                     },
				                     {
				                      xtype: 'button',
				                      text: '选择',
				                      handler: function(){
				                      		selMeterColumn(targetTbId,'targetColumnsId','targetColumnsName');
				                      }
				                    }]
                        }]
              		}]
              },  
	          {//上面是第四行
		        xtype:"textarea",
		        fieldLabel:"备注",
		        name:"itemDesc",
		        height:30,
		        anchor:"95%"
    		}],
			buttons:[{
				text: '提交',
				handler:function(){
					//var param = transRuleForm.getForm().getValues();
					//queryNotepad(param);
					submitItem();	
				}
			},{
				text: '重置',
				handler:function(){
					transRuleForm.getForm().reset();
				}
			}]
		});
		
		function submitItem(){
	         if(transRuleForm.getForm().isValid()){
				var transRule = transRuleForm.getForm().getValues();
				//创建者为当前用户
				transRule.operManId = staffId;
				transRule.operManName = staffName;
				var result;
				//当ID存在的场合，执更新操作。



				if(operItemId){
					//ID
					transRule.etlRuleId = etlRuleId;
					transRule.transOperItemId = operItemId;
					
					//更新
					result = invokeAction("/etl/MdfTransRuleItemAction",transRule);
				}else{
					//ID
					transRule.etlRuleId = etlRuleId;
					//插入
					result = invokeAction("/etl/AddTransRuleItemAction",transRule);
				}
				
		        if(result && parseInt(result) > 1){
					Ext.Msg.alert("操作提示","提交成功!");
					operItemId = parseInt(result);
					ruleItemStore.reload({ params: { start: 0, limit: 5 }});
					loadItemComp();
		        }else{
					Ext.MessageBox.show({
						title:'操作提示',
						msg:'提交失败,请重试或与管理员联系!',
				 		buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.ERROR
					});	           			
		        }
	         }
		}

		var compStore = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        //remoteSort: true,
	        fields: [ 
	        	'transOperItemCompId','compId', 'compClass', 'compName', 'compType', 'compExpr','remark'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/QryTransRulesCompsListExtAction',
	            method: 'POST'  
	        }),
	        listeners:{
	        	load: function(store){
					compGrid.getSelectionModel().selectRow(0);
				}
	        }
	    });
	    
	    function loadItemComp(){
	  		compStore.baseParams = {transOperItemId:operItemId};
			compStore.reload({params:{start:0, limit:10}});  
	    }
	    

   
	   	//创建列   'compId', 'compClass', 'compName', 'compType', 'compExpr','remark'
	   	//  0: 验证组件   1:转换组件
	   	//  PRO: 存储过程
		var compCol = new Ext.grid.ColumnModel([
		    {id:'transOperItemCompId',header:'标识',dataIndex:'transOperItemCompId',hidden:true},
		    {header:'组件标识',dataIndex:'compId',hidden:true},
		    {header:'组件类别',dataIndex:'compClass',sortable:true,width:100,renderer:changeCompClass},
		    {header:'组件名',dataIndex:'compName',sortable:true,width:200},
		    {header:'组件类型',dataIndex:'compType',sortable:true,width:100,renderer:changeCompType},
		    {header:'表达式',dataIndex:'compExpr',sortable:true,width:200},
		    {header:'备注',dataIndex:'remark',sortable:true,width:200}
		]); 
		
		var compPbar = new Ext.PagingToolbar({
			pageSize: 10,
			store: compStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
	    });
 
		var compGv = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});

		var compTbar = new Ext.Toolbar();
		compTbar.add({
            text:'添加',
             // <-- icon
             onClick:addItemComp// assign menu by instance
        });
        compTbar.add({
            text:'删除',
             // <-- icon
            onClick:deleteItemComp
        });
        
        function addItemComp(){
            if(operItemId ==null || operItemId <=0){
            	Ext.Msg.alert("操作提示","请选新建一个规则定义再添加组件!");
            	return;
            }
            selMeterComp(compStore,operItemId);
                   
        }
        
       	function deleteItemComp(){
			var compRecord = compGrid.getSelectionModel().getSelected();
			if(!compRecord){
				Ext.Msg.alert("操作提示","请选择组件!");
				return;
			}

			Ext.Msg.confirm("操作提示","确定要删除该组件吗?",function(btn){
					if(btn=="yes"){
						var transRule = new Object();
						transRule.transOperItemCompId = compRecord.data.transOperItemCompId;
						//当前用户
						transRule.operManId = staffId;
						transRule.operManName = staffName;
								
						var result = invokeAction("/etl/DelTransRuleItemCompAction",transRule);
						
						if("success"==result){
							Ext.example.msg("删除提示","删除成功!");
							compStore.reload();
						}else{
							Ext.Msg.show({
								title:'删除提示',
								msg:'删除失败,请重试或与管理员联系!',
				 				buttons: Ext.MessageBox.OK,
				           		icon: Ext.MessageBox.ERROR
							});
						}				
					}
				});         			
		}
                		 
	    var compGrid = new Ext.grid.GridPanel({
			id:'compGrid',
	        region: 'south',
	        layout:"fit",
	        collapsible:true,  
			width: Ext.getBody().getSize().width,
			height:Ext.getBody().getSize().height*0.5,
		    title:'规则组件列表',
	        store: compStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : compGv,
			cm: compCol,
			tbar:compTbar,
			bbar: compPbar,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
						
	                }
				}
			})
	    }); 	
	    
	    	
		if(operItemId){

	 		winTitle = '修改规则定义';
		}else{
			var winTitle = '新建规则定义';
		}
		
		var win = new Ext.Window({
	       	id:'transRuleDetail',
	        title: winTitle,
	        closable:true,
	        width:800,
	        height:550,
	        layout:'border',
	        buttonAlign:'center',
	        items:[transRuleForm,compGrid],
            buttons:[{
            	text:'关闭',
            	onClick:function(){
					 Ext.getCmp('transRuleDetail').close();
				}
            }]
        });
	 win.show();
	 
	 if(operItemId){
		
			//更新场合下，获取选中的行ID
	 		var paramObj = new Object();
			paramObj.transOperItemId = operItemId;
			
			var _result = invokeAction('/etl/QryTransRuleItemAction',paramObj);
			if(_result){
				
				transRuleForm.getForm().findField('itemDesc').setValue(_result.itemDesc);
				
				transRuleForm.getForm().findField('sourceColumnsId').setValue(_result.sourceColumnsId);
				transRuleForm.getForm().findField('sourceColumnsName').setValue(_result.sourceColumnsName);
				
				transRuleForm.getForm().findField('targetColumnsId').setValue(_result.targetColumnsId);
				transRuleForm.getForm().findField('targetColumnsName').setValue(_result.targetColumnsName);
				
				loadItemComp();
			}
		}
	}		
	
		
    function Oper(){
    	//查询条件面板
		var qryPanel =  new Ext.FormPanel({
			id:'qryPanel',
			//renderTo:'main',
			title:'查询条件',
			region: 'north',
			frame:true,		
	        collapsible:true,
	        width: body_width,
			height:body_height*0.2,
			bodyStyle: 'padding: 5px 0px 0 5px;',
			labelWidth: 80,
			layout:'table',
			layoutConfig:{columns:3},
	        items:[{
	        	xtype:'panel',
				layout:'form',
				width:body_width*0.3,
	        	items:[{
	        		xtype:'textfield',
	        		anchor: '75%',
		 			fieldLabel:'转换规则名称',
					name:'etlRuleName'       	
	        	}]
	        },{
	        	xtype:'panel',
				layout:'form',
				width:body_width*0.3,
	        	items:[{
	        		xtype:'textfield',
	        		anchor: '75%',
					fieldLabel:'源表名',
					name:'sourceTableName'     	
	        	}]
	        },{
	        	xtype:'panel',
				layout:'form',
				width:body_width*0.3,
	        	items:[{
	        		xtype:'textfield',
	        		anchor: '75%',
					fieldLabel:'目标表名',
					name:'targetTableName'     	
	        	}]
	        }],
			buttons:[{
				text: '查询',
				handler:function(){
					var param = qryPanel.getForm().getValues();
					qryRulesList(param);
							
				}
			},{
				text: '重置',
				handler:function(){
					qryPanel.getForm().reset();
				}
			}]
		});//end qrypanel
		
		function qryRulesList(param){
			transRuleStore.baseParams = {etlRuleName: param.etlRuleName,
										 sourceTableName: param.sourceTableName,
										 targetTableName:param.targetTableName};
			transRuleStore.reload({ params: { start: 0, limit: 5 }});
		}

		//rule list
		transRuleStore = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        //remoteSort: true,
	        fields: [ 
	        	'etlRuleId', 'etlRuleName', 'state', 'operManName', 'stateDate',
	        	'sourceTbId', 'sourceTableName', 'targetTbId', 'targetTableName','tacheId','tacheName'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/QryTransRulesListExtAction',
	            method: 'POST'  
	        }),
	        listeners:{
	        	load: function(store){
					transRuleGrid.getSelectionModel().selectRow(0);
					//loadItems();
				}
	        }
	    });
	    
	    transRuleStore.reload({ params: { start: 0, limit: 5 }});
   

	   	//创建列   
		var transRuleCol = new Ext.grid.ColumnModel([
		    {header:'规则名称',dataIndex:'etlRuleName',sortable:true,width:200},
		    {header:'流程环节',dataIndex:'tacheName',sortable:true,width:150},
		    {header:'源表名',dataIndex:'sourceTableName',sortable:true,width:200},
		    {header:'目标表名',dataIndex:'targetTableName',sortable:true,width:200},
		    {header:'状态',dataIndex:'state',sortable:true,width:100,renderer:changeState},
		    {header:'最后操作人',dataIndex:'operManName',sortable:true,width:100},
		    {header:'最后操作时间',dataIndex:'stateDate',sortable:true,width:200}
		]); 
		
		var transRulePbar = new Ext.PagingToolbar({
			pageSize: 5,
			store: transRuleStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
	    });

		var transRuleGv = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});

        var transRuleTbar = new Ext.Toolbar();
		transRuleTbar.add({
            text:'添加',
             // <-- icon
            onClick:addTransRule// assign menu by instance
        });
		transRuleTbar.add({
            text:'编辑',
              // <-- icon
            onClick:updateTransRule// assign menu by instance
        });
        transRuleTbar.add({
            text:'删除',
             // <-- icon
            onClick:deleteTransRule
        });		 
	    var transRuleGrid = new Ext.grid.GridPanel({
			id:'transRuleGrid',
	        region: 'center',
	        layout:"fit",
	        collapsible:true,  
			width: body_width,
			height:body_height*0.4,
		    title:'转换规则列表',
	        store: transRuleStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : transRuleGv,
			cm: transRuleCol,
			tbar:transRuleTbar,
			bbar: transRulePbar,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
						
	                }
				}
			})
	    }); 

	//定义菜单
		var ruleMenu = new Ext.menu.Menu({
	        id : 'ruleMenu'
		});
	
	    //添加选择行数据时右键事件
		transRuleGrid.on("rowcontextmenu",function(node,rowIndex,e){
			node.getSelectionModel().selectRow(rowIndex);			//选中所在的选择项上
			ruleMenu.removeAll();
			ruleMenu.addItem(new Ext.menu.Item({text:'添加',handler:function(item,e){
				ruleMenu.hide();
				addTransRule();		//新增操作
			}}));
			ruleMenu.addItem(new Ext.menu.Item({text:'编辑',handler:function(){
				ruleMenu.hide();
				updateTransRule();		
				 
			}}));
			ruleMenu.addItem(new Ext.menu.Item({text:'删除',handler:function(){
				ruleMenu.hide();
				deleteTransRule();
				
			}}));
			
			ruleMenu.showAt([e.getXY()[0], e.getXY()[1]]);		
		});
	
		//添加右键事件
		transRuleGrid.on("contextmenu",function(e){
			ruleMenu.removeAll();
			ruleMenu.addItem(new Ext.menu.Item({text:'添加',handler:function(){
				ruleMenu.hide();
				addTransRule();
			}}));
			
			ruleMenu.showAt([e.getXY()[0], e.getXY()[1]]);	
		});
		
		//添加选中行双击事件


	
		transRuleGrid.on("rowdblclick",function(e){
			updateTransRule();
		});
		
		//单击事件，加载操作项
		transRuleGrid.on("rowclick",function(e){
			loadItems();
		});
		//*******************rule *********************
		
		function addTransRule(){
			openRuleDetail();
			
		}

		function updateTransRule(){
			var ruleRecord = transRuleGrid.getSelectionModel().getSelected();
			if(!ruleRecord){
				Ext.Msg.alert("操作提示","请选择规则!");
				return;
			}
			openRuleDetail(ruleRecord.data.etlRuleId);
		}

		function deleteTransRule(){
			var ruleRecord = transRuleGrid.getSelectionModel().getSelected();
			if(!ruleRecord){
				Ext.Msg.alert("操作提示","请选择规则!");
				return;
			}

			Ext.Msg.confirm("操作提示","确定要删除该规则吗?",function(btn){
					if(btn=="yes"){
						var transRule = new Object();
						transRule.etlRuleId = ruleRecord.data.etlRuleId;
						//当前用户
						transRule.operManId = staffId;
						transRule.operManName = staffName;
								
						var result = invokeAction("/etl/DelTransRuleAction",transRule);
						
						if("success"==result){
							Ext.example.msg("删除提示","删除成功!");
							transRuleStore.reload();
						}else{
							Ext.Msg.show({
								title:'删除提示',
								msg:'删除失败,请重试或与管理员联系!',
				 				buttons: Ext.MessageBox.OK,
				           		icon: Ext.MessageBox.ERROR
							});
						}				
					}
				});         			
		}
		
		function loadItems(){
		    var ruleRecord = transRuleGrid.getSelectionModel().getSelected();
		    if(ruleRecord){
		    	var etlRuleId = ruleRecord.data.etlRuleId;
				ruleItemStore.baseParams = {etlRuleId: etlRuleId};
				ruleItemStore.reload({ params: { start: 0, limit: 5 }});
		    }	
		}
				
		//*******************definde list*********************
		ruleItemStore = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        //remoteSort: true,
	        fields: [ 
	        	'transOperItemId', 'etlRuleId', 'itemDesc', 'sourceColumnsId', 'sourceColumnsName','sourceColumnsCode',
	        	'targetColumnsId', 'targetColumnsName','targetColumnsCode'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/QryTransRuleItemsListExtAction',
	            method: 'POST'  
	        }),
	        listeners:{
	        	load: function(store){
					ruleItemGrid.getSelectionModel().selectRow(0);
				}
	        }
	    });
	    

   
	   	//创建列   
		var ruleItemCol = new Ext.grid.ColumnModel([
		    {id:'transOperItemId',header:'转换操作项标识',dataIndex:'transOperItemId',hidden:true},
		    {header:'转换规则标识',dataIndex:'etlRuleId',hidden:true},
		    {header:'源表字段标识',dataIndex:'sourceColumnsId',hidden:true},
		    {header:'目标表字段标识',dataIndex:'targetColumnsId',hidden:true},
		    {header:'源表字段编码',dataIndex:'sourceColumnsCode',sortable:true,width:200},
		    {header:'源表字段名',dataIndex:'sourceColumnsName',sortable:true,width:200},
		    {header:'目标表字段编码',dataIndex:'targetColumnsCode',sortable:true,width:200},
		    {header:'目标表字段名',dataIndex:'targetColumnsName',sortable:true,width:200},
		    {header:'描述',dataIndex:'itemDesc',sortable:true,width:200}
		]); 
		
		var ruleItemPbar = new Ext.PagingToolbar({
			pageSize: 5,
			store: ruleItemStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
	    });
 
		var ruleItemGv = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});

		var itemTbar = new Ext.Toolbar();
		itemTbar.add({
            text:'添加',
             // <-- icon
             onClick:addOperItem// assign menu by instance
        });
		itemTbar.add({
            text:'编辑',
              // <-- icon
            onClick:updateOperItem// assign menu by instance
        });
        itemTbar.add({
            text:'删除',
             // <-- icon
            onClick:deleteOperItem
        });
                		 
	    var ruleItemGrid = new Ext.grid.GridPanel({
			id:'ruleItemGrid',
	        region: 'south',
	        layout:"fit",
	        collapsible:true,  
			width: body_width,
			height:body_height*0.4,
		    title:'转换规则定义列表',
	        store: ruleItemStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : ruleItemGv,
			cm: ruleItemCol,
			tbar:itemTbar,
			bbar: ruleItemPbar,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
						
	                }
				}
			})
	    }); 
	

		
			//定义菜单
		var itemMenu = new Ext.menu.Menu({
	        id : 'itemMenu'
		});
	
	    //添加选择行数据时右键事件
		ruleItemGrid.on("rowcontextmenu",function(node,rowIndex,e){
			node.getSelectionModel().selectRow(rowIndex);			//选中所在的选择项上
			itemMenu.removeAll();
			itemMenu.addItem(new Ext.menu.Item({text:'添加',handler:function(item,e){
				ruleMenu.hide();
				addOperItem();		//新增操作
			}}));
			itemMenu.addItem(new Ext.menu.Item({text:'编辑',handler:function(){
				itemMenu.hide();
				updateOperItem();		
				 
			}}));
			itemMenu.addItem(new Ext.menu.Item({text:'删除',handler:function(){
				itemMenu.hide();
				deleteOperItem();
				
			}}));
			
			itemMenu.showAt([e.getXY()[0], e.getXY()[1]]);		
		});
	
		//添加右键事件
		ruleItemGrid.on("contextmenu",function(e){
			itemMenu.removeAll();
			itemMenu.addItem(new Ext.menu.Item({text:'添加',handler:function(){
				itemMenu.hide();
				addOperItem();
			}}));
			
			itemMenu.showAt([e.getXY()[0], e.getXY()[1]]);	
		});
		
		//添加选中行双击事件


	
		ruleItemGrid.on("rowdblclick",function(e){
			updateOperItem();
		});		

		function updateOperItem(){
		    var ruleRecord = transRuleGrid.getSelectionModel().getSelected();
		    if(ruleRecord){
		    	var etlRuleId = ruleRecord.data.etlRuleId;
		    	var sourceTbId = ruleRecord.data.sourceTbId;
		    	var sourceTableName = ruleRecord.data.sourceTableName;
		    	
		    	var targetTbId = ruleRecord.data.targetTbId;
		    	var targetTableName = ruleRecord.data.targetTableName;

		    	var param = new Object();
		    		
		    	param.etlRuleId = etlRuleId;
		
				param.sourceTbId = sourceTbId;
				param.sourceTableName = sourceTableName;
					
				param.targetTbId = targetTbId;
				param.targetTableName = targetTableName;
							    	
		    	var itemRecord = ruleItemGrid.getSelectionModel().getSelected();

		    	if(itemRecord){
		    		
					param.operItemId = itemRecord.data.transOperItemId;
		
		    		openItemDetail(param);
		    	}else{
		    		openItemDetail(param);
		    	}
		    }else{
		    	Ext.Msg.alert("操作提示","请选择一个正确的规则!");
		    }
			
		}
				
		function addOperItem(){
		    var ruleRecord = transRuleGrid.getSelectionModel().getSelected();
		    if(ruleRecord){
		    	var etlRuleId = ruleRecord.data.etlRuleId;
		    	var sourceTbId = ruleRecord.data.sourceTbId;
		    	var sourceTableName = ruleRecord.data.sourceTableName;
		    	
		    	var targetTbId = ruleRecord.data.targetTbId;
		    	var targetTableName = ruleRecord.data.targetTableName;

		    	var param = new Object();
		    		
		    	param.etlRuleId = etlRuleId;
		
				param.sourceTbId = sourceTbId;
				param.sourceTableName = sourceTableName;
					
				param.targetTbId = targetTbId;
				param.targetTableName = targetTableName;
							    	
		    	openItemDetail(param);
		    	
		    }else{
		    	Ext.Msg.alert("操作提示","请选择一个正确的规则!");
		    }
			
		}
	

		function deleteOperItem(){
			var itemRecord = ruleItemGrid.getSelectionModel().getSelected();
			if(!itemRecord){
				Ext.Msg.alert("操作提示","请选择规则定义!");
				return;
			}

			Ext.Msg.confirm("操作提示","确定要删除该规则定义吗?",function(btn){
					if(btn=="yes"){
						var transRule = new Object();
						transRule.transOperItemId = itemRecord.data.transOperItemId;
						//当前用户
						transRule.operManId = staffId;
						transRule.operManName = staffName;
								
						var result = invokeAction("/etl/DelTransRuleItemAction",transRule);
						
						if("success"==result){
							Ext.example.msg("删除提示","删除成功!");
							ruleItemStore.reload({ params: { start: 0, limit: 5 }});
						}else{
							Ext.Msg.show({
								title:'删除提示',
								msg:'删除失败,请重试或与管理员联系!',
				 				buttons: Ext.MessageBox.OK,
				           		icon: Ext.MessageBox.ERROR
							});
						}				
					}
				});         			
		}		
		//***********************************************	
		this.initMainPanel = function(){
	
			var viewport = new Ext.Viewport({
				el:Ext.get('main'),
				frame:true,	
				layout: 'border',
				items:[qryPanel, transRuleGrid,ruleItemGrid]
			});
		};

	};//end oper    
	
	function selSysTable(id_filed,name_filed){		
		var parentWin = new Object();
		var win = new ZTESOFT.TableWin({parentWin:parentWin});
		parentWin.setRetValue = function(tableId,tableName,tableCode){
			
			Ext.getCmp(id_filed).setValue(tableId);
			Ext.getCmp(name_filed).setValue(tableName);
			
		}     
		win.show();
		}//end selSysTab
		
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
		
		
		function selMeterComp(compStore,transOperItemId){
		
				var meterdataStore = new Ext.data.JsonStore({
			        root: 'Body',
			        totalProperty: 'totalCount',
			        fields: [ 
			        	'compId', 'compClass','compName','compType','compExpr','remark','state'
			        ],
			        proxy: new Ext.data.HttpProxy({
			            url: '/MOBILE/ExtServlet?actionName=etl/QryMeterdataValidateCompExtAction',
			            method: 'POST'  
			        }),
			        listeners:{
			        	load: function(store){
							
						}
			        }
			    });
			    meterdataStore.reload({params:{start:0, limit:10}});
		    
	        	//'compId', 'compClass','compName','compType','compExpr','remark','state'			    
				var column = new Ext.grid.ColumnModel([
					    {id:'compId',header:'标识',dataIndex:'compId',hidden:true},
					    {header:'组件类别',dataIndex:'compClass',sortable:true,width:100,renderer:changeCompClass},
					    {header:'组件名',dataIndex:'compName',sortable:true,width:200},
					    {header:'组件类型',dataIndex:'compType',sortable:true,width:100,renderer:changeCompType},
					    {header:'表达式',dataIndex:'compExpr',sortable:true,width:200},
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
			        title: '选择组件',
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
							//'compId', 'compClass','compName','compType','compExpr','remark','state'	
							//transOperItemId
								var compId = meterdataRecord.data.compId;
		            		
		            		//alert('compId:' + compId + 'transOperItemId:'+transOperItemId);
		            		
								var transRule = new Object();
								
								transRule.compId = compId;
								transRule.transOperItemId = transOperItemId;
								
								//创建者为当前用户
								transRule.operManId = staffId;
								transRule.operManName = staffName;
								
								
								var result  = invokeAction("/etl/AddTransRuleItemCompAction",transRule);
				           		if("success"==result){
									//Ext.example.msg("删除提示","添加成功!");
									compStore.reload(); 
				           		}else{
									Ext.MessageBox.show({
										title:'操作提示',
										msg:'提交失败,请重试或与管理员联系!',
						 				buttons: Ext.MessageBox.OK,
						           		icon: Ext.MessageBox.ERROR
									});	           			
				           		}
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
		}//end selMeterComp		

   		function changeState(val){
   		
	        if(val == '10A'){
	            return '有效';
	        }else {
	            return '无效';
	        }
	        return val;
	    }
	    		
   		function changeCompClass(val){
	        if(val == 0){
	            return '验证组件';
	        }else {
	            return '转换组件';
	        }
	        return val;
	    }
	    
	    function changeCompType(val){
	        if(val == 'PRO'){
	            return '存储过程';
	        }else{
	        	return val;
	        }
	    }	

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