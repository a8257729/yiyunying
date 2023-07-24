/*
 * by fang.li   2011-05	
 * ETL - 清洗规则组件配置
 */
 
function CleanRuleComMng(){
	this.showCleanRuleComInfo = function(operator){
		var ruleComStore = new Ext.data.JsonStore({
	        remoteSort: true,
	        fields: ['sqEtlCleanCompId', 'cleanCompName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/RoleTypeSelAction'
	        })
	    });
	    ruleComStore.load();
	    
		var cleanRuleComInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '清洗规则名称',
	                name: 'sqEtlCleanRuleName3',
	                id: 'sqEtlCleanRuleName3',
	                allowBlank:false, 
	                blankText:"清洗规则名称不能为空!",
	                anchor:'90%'
	            },{
	                xtype:'textfield',
	                fieldLabel: '字段名称',
	                name: 'cleanColName3',
	                id: 'cleanColName3',
	                allowBlank:false, 
	                blankText:"字段名称不能为空!",
	                anchor:'90%'
	            },{
					id: 'etlCleanComp3',
					name: 'etlCleanComp3',
					fieldLabel: '清洗组件',
					xtype: 'combo',
					valueField: 'sqEtlCleanCompId',
					displayField: 'cleanCompName',
					mode: 'local',
					anchor:'90%',
					triggerAction: 'all',
					editable : false ,
					allowBlank:false, 
					blankText:"清洗组件不能为空!",
					store: ruleComStore
	            },{
	                xtype:'textarea',
				    fieldLabel: '备注',
				    name: 'memo3',
				    id: 'memo3',
				    height : 100,
				    anchor:'90%'
	            }
	        ],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
	            	var str = '' ;
	            	var selRule = Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections();
	            	var selCom = Ext.getCmp('cleanComGrid').getSelectionModel().getSelections();
					
					var rule = new Object();
					rule.sqEtlCleanRuleName = Ext.getCmp("sqEtlCleanRuleName3").getValue();
					rule.cleanColName = Ext.getCmp("cleanColName3").getValue();
					rule.etlCleanCompId = Ext.getCmp("etlCleanComp3").getValue();
					rule.operManId = session1.staff.staffId;
					rule.operManName = session1.staff.staffName;
					rule.memo = Ext.getCmp("memo3").getValue();
					rule.etlRuleId = selRule[0].data.etlRuleId ;
					rule.cleanTbName = selRule[0].data.targetTbName ;
					
					//表单验证
					if(!cleanRuleComInfoForm.getForm().isValid()){
						return;
					}
	            	switch (operator){
						case "add":
						{	
							rule.sqEtlCleanRuleId = 0;
							str = '成功增加规则组件！'
							var flag = invokeAction('/etl/InsertEtlCleanRuleAction',rule);
							break ;
						}
						case "mod":
						{	
							rule.sqEtlCleanRuleId = selCom[0].data.sqEtlCleanRuleId ;
							debugger;
							str = '成功修改规则组件！'
							var sqEtlCleanRuleId = invokeAction('/etl/UpdateEtlCleanRuleAction',rule);
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
			            Ext.example.msg('',str);
			        }, 1000);
			        
					win.close();
					Ext.getCmp('cleanComGrid').store.removeAll();
					Ext.getCmp('cleanComGrid').store.load({params:{start:0, limit:5}});
	            }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '清洗规则组件配置',
		    closable:true,
		    width:440,
		    height:290,
		    plain:true,
		    layout: 'border',
		    items: [cleanRuleComInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			var selCom = Ext.getCmp('cleanComGrid').getSelectionModel().getSelections();
			Ext.getCmp("memo3").setValue(selCom[0].data.memo);
			Ext.getCmp("sqEtlCleanRuleName3").setValue(selCom[0].data.sqEtlCleanRuleName);
			Ext.getCmp("cleanColName3").setValue(selCom[0].data.cleanColName);
			
			ruleComStore.on('load',function(ds,records,o){
	    		Ext.getCmp("etlCleanComp3").setValue(Ext.getCmp('cleanComGrid').getSelectionModel().getSelections()[0].data.etlCleanCompId);
			});
		}
	}
}