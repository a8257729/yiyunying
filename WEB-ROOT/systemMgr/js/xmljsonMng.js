function XmljsonOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('outerSystemGrid').getSelectionModel().getSelections();
		var selItem2 = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('xmljsonGrid').getSelectionModel().getSelections();
		
		
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择环节名称！', 
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        
        //var staticShowStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth : 60,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 100,
                items: [{
					xtype: 'compositefield',
					fieldLabel: '映射编码',
					items: [{
						xtype: 'textfield',
	                	name: 'm_mapping_code',
	                  	id: 'm_mapping_code',
					   	width: '120',
					  	allowBlank:false, 
					 	readOnly: true
					    },{
					    xtype: 'button',
					    id : 'm_b_mapping_code',
					    text: '..',
					    handler: function(){selOper.selectOtherSys("interfaceGrid", "1", "m_mapping_code", "mappingCode");}
					}]
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务系统字段1',
                	id : 'm_system_fileds',
                	name : 'm_system_fileds',
					allowBlank:false,
					width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务系统字段2',
                	id : 'm_system_fileds2',
                	name : 'm_system_fileds2',
					width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务系统字段3',
                	id : 'm_system_fileds3',
                	name : 'm_system_fileds3',
					width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务系统字段4',
                	id : 'm_system_fileds4',
                	name : 'm_system_fileds4',
					width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务系统字段5',
                	id : 'm_system_fileds5',
                	name : 'm_system_fileds5',
					width: '160'
				}]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 100,
                items: [{
                	xtype : 'hidden',
                	id : 'm_transfer_result',
                	value : ''
                },{
                	xtype : 'textfield',
                	fieldLabel : '映射成字段1',
                	id : 'm_mapping_fileds',
                	name : 'm_mapping_fileds',
                	width: '160'
                },{
                	xtype : 'textfield',
                	fieldLabel : '映射成字段2',
                	id : 'm_mapping_fileds2',
                	name : 'm_mapping_fileds2',
                	width: '160'
                },{
                	xtype : 'textfield',
                	fieldLabel : '映射成字段3',
                	id : 'm_mapping_fileds3',
                	name : 'm_mapping_fileds3',
                	width: '160'
                },{
                	xtype : 'textfield',
                	fieldLabel : '映射成字段4',
                	id : 'm_mapping_fileds4',
                	name : 'm_mapping_fileds4',
                	width: '160'
                },{
                	xtype : 'textfield',
                	fieldLabel : '映射成字段5',
                	id : 'm_mapping_fileds5',
                	name : 'm_mapping_fileds5',
                	width: '160'
                }]
            }]}],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	
			    	var resultStr ;
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';
			    			
//			    			var objq = new Object();
//			    			objq.type = "selxmljsonManagerIsExit";
//			    			objq.mappingCode = Ext.getCmp("m_system_fileds").getValue();
//			    			objq.mappingCode = selItem[0].data.mappingCode;
//			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
//			 				if(tmpObj != ""){
//							  	Ext.MessageBox.show({
//						          	title: '提示(映射编码)',
//							        msg: '该字段已存在，请重新指定！',
//							        buttons: Ext.MessageBox.OK,
//							        width:200,
//							        icon: Ext.MessageBox.ERROR
//							    });
//								return;
//							}
							
			    			var inputParams = new Object();
			    			inputParams.mappingCode = Ext.getCmp("m_mapping_code").getValue();
			    			inputParams.systemFileds = Ext.getCmp("m_system_fileds").getValue();
			    			inputParams.systemFileds2 = Ext.getCmp("m_system_fileds2").getValue();
			    			inputParams.systemFileds3 = Ext.getCmp("m_system_fileds3").getValue();
			    			inputParams.systemFileds4 = Ext.getCmp("m_system_fileds4").getValue();
			    			inputParams.systemFileds5 = Ext.getCmp("m_system_fileds5").getValue();
                            inputParams.mappingFileds = Ext.getCmp("m_mapping_fileds").getValue();
                            inputParams.mappingFileds2 = Ext.getCmp("m_mapping_fileds2").getValue();
                            inputParams.mappingFileds3 = Ext.getCmp("m_mapping_fileds3").getValue();
                            inputParams.mappingFileds4 = Ext.getCmp("m_mapping_fileds4").getValue();
                            inputParams.mappingFileds5 = Ext.getCmp("m_mapping_fileds5").getValue();
//                            inputParams.transferResult = Ext.getCmp("m_transfer_result").getValue();
                            inputParams.methodAddress = selItem2[0].data.methodAddress;
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.filedMappingId = selGridItem[0].data.filedMappingId;
			    			inputParams.systemFileds = Ext.getCmp("m_system_fileds").getValue();
			    			inputParams.systemFileds2 = Ext.getCmp("m_system_fileds2").getValue();
			    			inputParams.systemFileds3 = Ext.getCmp("m_system_fileds3").getValue();
			    			inputParams.systemFileds4 = Ext.getCmp("m_system_fileds4").getValue();
			    			inputParams.systemFileds5 = Ext.getCmp("m_system_fileds5").getValue();
                            inputParams.mappingFileds = Ext.getCmp("m_mapping_fileds").getValue();
                            inputParams.mappingFileds2 = Ext.getCmp("m_mapping_fileds2").getValue();
                            inputParams.mappingFileds3 = Ext.getCmp("m_mapping_fileds3").getValue();
                            inputParams.mappingFileds4 = Ext.getCmp("m_mapping_fileds4").getValue();
                            inputParams.mappingFileds5 = Ext.getCmp("m_mapping_fileds5").getValue();
                            inputParams.transferResult = '';
                            inputParams.methodAddress = selItem2[0].data.methodAddress;
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
			    			
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
			            Ext.example.msg('',resultStr);
			        }, 1000);
			        menuWin.close();
			        Ext.getCmp('xmljsonGrid').store.removeAll();
			        Ext.getCmp('xmljsonGrid').store.reload();
			    }}
	        },{
	            text: '取消', 
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    	    
		
		menuWin = new Ext.Window({
	        title: '接口映射管理',
		    closable:true,
		    width:650,
		    height:250,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){			
			Ext.getCmp("m_mapping_code").setReadOnly(true);
			Ext.getCmp("m_b_mapping_code").setDisabled(true);
			Ext.getCmp("m_mapping_code").setValue(selGridItem[0].data.mappingCode);
			Ext.getCmp("m_system_fileds").setValue(selGridItem[0].data.systemFileds);
			Ext.getCmp("m_system_fileds2").setValue(selGridItem[0].data.systemFileds2);
			Ext.getCmp("m_system_fileds3").setValue(selGridItem[0].data.systemFileds3);
			Ext.getCmp("m_system_fileds4").setValue(selGridItem[0].data.systemFileds4);
			Ext.getCmp("m_system_fileds5").setValue(selGridItem[0].data.systemFileds5);
            Ext.getCmp("m_mapping_fileds").setValue(selGridItem[0].data.mappingFileds);
            Ext.getCmp("m_mapping_fileds2").setValue(selGridItem[0].data.mappingFileds2);
            Ext.getCmp("m_mapping_fileds3").setValue(selGridItem[0].data.mappingFileds3);
            Ext.getCmp("m_mapping_fileds4").setValue(selGridItem[0].data.mappingFileds4);
            Ext.getCmp("m_mapping_fileds5").setValue(selGridItem[0].data.mappingFileds5);
//            Ext.getCmp("m_transfer_result").setValue(selGridItem[0].data.transferResult);
           
		}
	}	
	this.moduleDel = function(btn){
    	var selButGrid = Ext.getCmp('xmljsonGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.filedMappingId = selButGrid[0].data.filedMappingId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", paramObj);
			 				
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
            Ext.example.msg('','删除菜单成功！');
        }, 1000);
        Ext.getCmp('xmljsonGrid').store.removeAll();
        Ext.getCmp('xmljsonGrid').store.reload();
    }

} 