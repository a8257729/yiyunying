function InterfaceManagerOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
		
		
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请选择环节名称！', 
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        
        var serviceTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [['2','二次开发'],['3','透传']]});
        
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
                labelWidth : 80,
                items: [{
                	xtype: 'textfield',
            		fieldLabel: '服务编码',
            		name: 'm_mapping_code',
            		id: 'm_mapping_code',
            		width: '160',
					readOnly: true,
					emptyText: '将自动生成'
                },{
                	xtype : 'textfield',
                	fieldLabel : '接口方法类型',
                	id : 'm_mapping_name',
                	name : 'm_mapping_name',
					width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '接口参数名',
                	id : 'm_mapping_method',
                	name : 'm_mapping_method',
					width: '160'
				}]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 90,
                items: [{
					xtype:'combo',
                    fieldLabel: '服务类型',
                    name: 'm_service_type',
                    id: 'm_service_type',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false,
                    value: '2',
                    store: serviceTypeStore,
                    width: '160'
				},{
                	xtype : 'textfield',
                	fieldLabel : '业务接口名称',
                	id : 'm_interface_name',
                	name : 'm_interface_name',
                	allowBlank: false,
                	width: '160'
                },{
                    xtype : 'textfield',
                	fieldLabel : '业务接口方法',
                	id : 'm_interface_method',
                	name : 'm_interface_method',
                	allowBlank: false,
                	width: '160'
                },{
                    xtype : 'textfield',
                	fieldLabel : '描述',
                	id : 'm_comments',
                	name : 'm_comments',
                	width: '160'
                }]
            }]},{
            	xtype : 'textarea',
                fieldLabel : '业务接口参数',
                id : 'm_interface_params',
                name : 'm_interface_params',
 				width: '500',
 				height: '80'
            },{
            	xtype : 'displayfield',
                fieldLabel : '',
                id: 'm_d1',
                value: '',
 				width: '500'
            },{
            	xtype : 'displayfield',
                fieldLabel : '',                
                id: 'm_d2',
                value: '',
 				width: '500'
            },{
            	xtype : 'displayfield',
                fieldLabel : '',                
                id: 'm_d3',
                value: '',
 				width: '500'
            },{
            	xtype : 'displayfield',
                fieldLabel : '',                
                id: 'm_d4',
                value: '',
 				width: '500'
            }],
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
//			    			objq.type = "selInterfaceManagerIsExit";
//			    			objq.mappingCode = Ext.getCmp("m_mapping_code").getValue();
//			    			objq.sysAddressId = selItem[0].data.sysAddressId;
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
			    			inputParams.sysAddressId = selItem[0].data.sysAddressId;
                            inputParams.mappingName = Ext.getCmp("m_mapping_name").getValue();
                            inputParams.mappingMethod = Ext.getCmp("m_mapping_method").getValue();
                            inputParams.intefaceName = Ext.getCmp("m_interface_name").getValue();
                            inputParams.intefaceMethod = Ext.getCmp("m_interface_method").getValue();
                            inputParams.intefaceParams = Ext.getCmp("m_interface_params").getValue();
                            inputParams.restServiceType = Ext.getCmp("m_service_type").getValue();    
                            inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobInterfaceMngAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.mappingId = selGridItem[0].data.mappingId;			    			
			    			inputParams.sysAddressId = selItem[0].data.sysAddressId;
                            inputParams.restServiceCode = Ext.getCmp("m_mapping_code").getValue();//用于更新rest_service表时候用	modify by guo.jinjun at 2012-05-09 16:33:23
                            inputParams.mappingName = Ext.getCmp("m_mapping_name").getValue();
                            inputParams.mappingMethod = Ext.getCmp("m_mapping_method").getValue();
                            inputParams.intefaceName = Ext.getCmp("m_interface_name").getValue();
                            inputParams.intefaceMethod = Ext.getCmp("m_interface_method").getValue();
                            inputParams.intefaceParams = Ext.getCmp("m_interface_params").getValue();
                            inputParams.restServiceType = Ext.getCmp("m_service_type").getValue();   
                            inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobInterfaceMngAction", inputParams);
			    			
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
			        Ext.getCmp('interfaceGrid').store.removeAll();
			        Ext.getCmp('interfaceGrid').store.reload();
			    }}
	        },{
	            text: '取消', 
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    	    
		
		menuWin = new Ext.Window({
	        title: '流转管理',
		    closable:true,
		    width:650,
		    height:350,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){			
			Ext.getCmp("m_mapping_code").setValue(selGridItem[0].data.mappingCode);
			Ext.getCmp("m_mapping_name").setValue(selGridItem[0].data.mappingName);
			Ext.getCmp("m_mapping_method").setValue(selGridItem[0].data.mappingMethod);
            Ext.getCmp("m_interface_name").setValue(selGridItem[0].data.intefaceName);
            Ext.getCmp("m_interface_method").setValue(selGridItem[0].data.intefaceMethod);
            Ext.getCmp("m_interface_params").setValue(selGridItem[0].data.intefaceParams);
            Ext.getCmp("m_comments").setValue(selGridItem[0].data.comments);
            Ext.getCmp("m_service_type").setValue(selGridItem[0].data.restServiceType);  
           
		}
		if (selItem[0].data.methodType != '2') {
			Ext.getCmp('m_mapping_name').setDisabled(true);
			Ext.getCmp('m_mapping_method').setDisabled(true);
		}
		if(selItem[0].data.interfaceVersion == '1'){			
			Ext.getCmp("m_d1").setValue('* call.setOperationName(new QName("http://eoms.ztesoft.com", method))');
			Ext.getCmp("m_d2").setValue('  call.setOperationName(new QName("http://eoms.ztesoft.com", 业务接口方法))');
			Ext.getCmp("m_d3").setValue('* jsonData += (String)call.invoke(new Object[] {Json})');
			Ext.getCmp("m_d4").setValue('  jsonData += (String)call.invoke(new Object[] {业务接口参数})');
		} else if(selItem[0].data.interfaceVersion == '2'){
			Ext.getCmp("m_d1").setValue('* callWebService(url, null, "pfServicesForEBiz", {"infType","requestxml"}, {"queryWorkOrderForEBiz",xml})');
			Ext.getCmp("m_d2").setValue('  callWebService(url, null, "pfServicesForEBiz", { 接口方法类型, 接口参数名 }, { 业务接口方法, 业务接口参数 })');
			Ext.getCmp("m_d3").setValue('');
			Ext.getCmp("m_d4").setValue('');
		} else {
			Ext.getCmp("m_d1").setValue('');
			Ext.getCmp("m_d2").setValue('');
			Ext.getCmp("m_d3").setValue('');
			Ext.getCmp("m_d4").setValue('');
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selButGrid = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.mappingId = selButGrid[0].data.mappingId;
  			paramObj.restServiceCode = selButGrid[0].data.mappingCode;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobInterfaceMngAction", paramObj);
			 				
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
        Ext.getCmp('interfaceGrid').store.removeAll();
        Ext.getCmp('interfaceGrid').store.reload();
    }

} 