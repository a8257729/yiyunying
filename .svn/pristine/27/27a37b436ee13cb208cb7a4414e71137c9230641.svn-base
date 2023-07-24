/*
 * by fang.li   2011-05	
 * ETL - 清洗规则配置
 */
 
function CleanRuleMng(){
	this.showCleanRuleInfo = function(operator){
		var cleanRuleInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '清洗规则名称',
	                name: 'etlRuleName2',
	                id: 'etlRuleName2',
	                allowBlank:false, 
	                blankText:"清洗规则名称不能为空!",
	                anchor:'90%'
	            },{
	                xtype:'textfield',
	                fieldLabel: '表名',
	                name: 'targetTbName2',
	                id: 'targetTbName2',
	                allowBlank:false, 
	                blankText:"表名不能为空!",
	                anchor:'90%'
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
		    }],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
	            	var str = '' ;
	            	var selRule = Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections();
	            	
					var etlRule = new Object();
					etlRule.etlRuleName = Ext.getCmp("etlRuleName2").getValue();
					etlRule.etlType = '001';
					etlRule.targetTbName = Ext.getCmp("targetTbName2").getValue();
					etlRule.tacheId = Ext.getCmp("tacheId").getValue();
					etlRule.operManId = session1.staff.staffId;
					etlRule.operManName = session1.staff.staffName;
					
					//表单验证
					if(!cleanRuleInfoForm.getForm().isValid()){
						return;
					}
	            	
	            	switch (operator){
						case "add":
						{	
							etlRule.etlRuleId = 0;
							etlRule.state = '10A';
							str = '增加清洗规则成功!'
							var etlRuleId = callRemoteFunction("com.ztesoft.mobile.etl.service.EtlRuleManagerClient","createEtlCleanRule",etlRule);
							break;
						}
						case "mod":
						{
							etlRule.etlRuleId = selRule[0].data.etlRuleId ;
							etlRule.state = selRule[0].data.state ;
							str = '修改清洗规则成功!'
							var flag = callRemoteFunction("com.ztesoft.mobile.etl.service.EtlRuleManagerClient","updateEtlCleanRule",etlRule);
							break;
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
					Ext.getCmp('cleanRuleGrid').store.removeAll();
					Ext.getCmp('cleanRuleGrid').store.load({params:{start:0, limit:8}});
	            }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '清洗规则',
		    closable:true,
		    width:400,
		    height:200,
		    plain:true,
		    layout: 'border',
		    items: [cleanRuleInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("etlRuleName2").setValue(Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections()[0].data.etlRuleName);
			Ext.getCmp("targetTbName2").setValue(Ext.getCmp('cleanRuleGrid').getSelectionModel().getSelections()[0].data.targetTbName);
		}
	}
}