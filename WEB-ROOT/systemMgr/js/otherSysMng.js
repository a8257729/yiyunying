function OtherSysOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		if(pmName == undefined || pmName == null ){
			var selItem = Ext.getCmp('outerSystemGrid').getSelectionModel().getSelections();
		} else {
			var selItem = new Array();
			var sysCode = pmName;
			var data = new Object();
			selItem[0] = new Object();
			data.sysCode = sysCode;
			selItem[0].data = data;
		}
		var selGridItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
	
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
        
        var methodTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'类名'],[2,'接口'],[3,'servlet'],[4,'测试数据']]});
        var interfaceVersionStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'方式1'],[2,'方式2'],[3,'方式3']]});
        var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype: 'textfield',
            		fieldLabel: '接口编码',
            		name: 'm_interface_code',
            		id: 'm_interface_code',
            		width: '160',
					readOnly: true,
					emptyText: '将自动生成'
                },{
                	xtype:'combo',
                    fieldLabel: '方法类型',
                    name: 'm_method_type',
                    id: 'm_method_type',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    store: methodTypeStore,
                    width: '160',
                    allowBlank: false,
                    listeners: {
                		collapse : function(c){                   					
                					var v = c.getValue();    
                					var versionStore = Ext.getCmp('m_interface_version').store;
                					/*
                					if(v == '1'){//类名
                						Ext.getCmp('m_apk_code').setDisabled(false);
                						Ext.getCmp('m_b_apk_code').setDisabled(false);
                					}else{
                						Ext.getCmp('m_apk_code').setDisabled(true);
                						Ext.getCmp('m_b_apk_code').setDisabled(true);
                					}
                					*/
                					if(v == '3' && versionStore.getCount() <= 3){//servlet
                						var addRecord = Ext.data.Record.create([
    										{name: 'id', type: 'string'},
    										{name: 'value', type: 'string'}
										]);
                						var record = new addRecord({id:'', value:'无'});
                						versionStore.add(record);
                					}else if(v != '3' && versionStore.getCount() > 3){
                						versionStore.removeAt(versionStore.getCount()-1);
                						Ext.getCmp('m_interface_version').setValue('1');
                					}
                					
                					if(v == '2'){//接口
                						Ext.getCmp('m_interface_namespace').setDisabled(false);
                						Ext.getCmp('m_interface_version').setDisabled(false);
                					}else{
                						Ext.getCmp('m_interface_namespace').setDisabled(true);
                						Ext.getCmp('m_interface_version').setDisabled(true);
                					}
                		}
                    }
				},{
                	xtype: 'textfield',
            		fieldLabel: '方法地址',
            		name: 'm_method_address',
            		id: 'm_method_address',
            		width: '160'
                },{
                	xtype: 'textfield',
            		fieldLabel: '空间名称',
            		name: 'm_interface_namespace',
            		id: 'm_interface_namespace',
            		width: '160'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 60,
                items: [{
                	xtype:'combo',
                    fieldLabel: '调用方式',
                    name: 'm_interface_version',
                    id: 'm_interface_version',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: interfaceVersionStore,
                    width: '160',
                    disabled: true
                },{
                    xtype:'radiogroup',
                    fieldLabel: '数据格式',
                    name: 'm_data_type',
                    id: 'm_data_type',
                	columns: 3,
                    items: [
                		{boxLabel: 'Json', name: 'dataType', inputValue:'1', checked: true},
                		{boxLabel: 'Xml', name: 'dataType', inputValue:'2'}
                	]/*,
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioIsShowLabel = RadioChecked.inputValue;
                		}
                	}*/
                },{
                	xtype: 'textfield',
            		fieldLabel: '接口方法',
            		name: 'm_interface_method',
            		id: 'm_interface_method',
            		width: '160'
                },{
                	xtype: 'textfield',
            		fieldLabel: '接口名称',
            		name: 'm_apk_code',
            		id: 'm_apk_code',
            		width: '160'
                }]
            }]}],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	/*
			    	if(1 == Ext.getCmp('m_method_type').getValue()){ 
			    		if(Ext.getCmp('m_apk_code').getValue()==null || Ext.getCmp('m_apk_code').getValue()=='') {
			    			Ext.MessageBox.alert('不允许', 'APK编码不能为空!');
			    			return;
			    		}
			    	}else{
			    		Ext.getCmp('m_apk_code').setValue('');
			    	}
			    	*/
			    	var resultStr ;
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';
			    			
//			    			var objq = new Object();
//			    			objq.type = "selAddressIsExit";
//			    			objq.interfaceCode = Ext.getCmp("m_interface_code").getValue();
//			    			//objq.formId = selItem[0].data.formId;
//			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
//			 				if(tmpObj != ""){
//							  	Ext.MessageBox.show({
//						          	title: '提示',
//							        msg: '该字段已存在，请重新指定！',
//							        buttons: Ext.MessageBox.OK,
//							        width:200,
//							        icon: Ext.MessageBox.ERROR
//							    });
//								return;
//							}
							
			    			var inputParams = new Object();
			    			inputParams.systemCode = selItem[0].data.sysCode;
//			    			inputParams.interfaceCode = Ext.getCmp("m_interface_code").getValue();
			    			inputParams.interfaceVersion = Ext.getCmp("m_interface_version").getValue();
                            inputParams.methodType = Ext.getCmp("m_method_type").getValue();
                            inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                            inputParams.methodAddress = Ext.getCmp("m_method_address").getValue();
                            inputParams.interfaceNamespace = Ext.getCmp('m_interface_namespace').getValue();
                            inputParams.interfaceMethod = Ext.getCmp('m_interface_method').getValue();
                            inputParams.dataType = Ext.getCmp('m_data_type').getValue().inputValue;
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobileOtherSysManagerAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.sysAddressId = selGridItem[0].data.sysAddressId;
			    			inputParams.interfaceVersion = Ext.getCmp("m_interface_version").getValue();
                            inputParams.methodType = Ext.getCmp("m_method_type").getValue();
                            inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                            inputParams.methodAddress = Ext.getCmp("m_method_address").getValue();
                            inputParams.interfaceNamespace = Ext.getCmp('m_interface_namespace').getValue();
                            inputParams.interfaceMethod = Ext.getCmp('m_interface_method').getValue();
                            inputParams.dataType = Ext.getCmp('m_data_type').getValue().inputValue;
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobileOtherSysManagerAction", inputParams);
			    			
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
			        Ext.getCmp('otherSysGrid').store.removeAll();
			        Ext.getCmp('otherSysGrid').store.reload();
			    }}
	        },{
	            text: '取消', 
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    	    
		
		menuWin = new Ext.Window({
	        title: '接口管理',
		    closable:true,
		    width:650,
		    height:200,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_interface_code").setValue(selGridItem[0].data.interfaceCode);
			Ext.getCmp("m_interface_version").setValue(selGridItem[0].data.interfaceVersion);
            Ext.getCmp("m_method_type").setValue(selGridItem[0].data.methodType);
            Ext.getCmp("m_apk_code").setValue(selGridItem[0].data.apkCode);
            Ext.getCmp("m_method_address").setValue(selGridItem[0].data.methodAddress);
            Ext.getCmp("m_interface_namespace").setValue(selGridItem[0].data.interfaceNamespace);
            Ext.getCmp("m_interface_method").setValue(selGridItem[0].data.interfaceMethod);
            Ext.getCmp("m_data_type").setValue(selGridItem[0].data.dataType);
//           	Ext.getCmp("m_interface_code").setReadOnly(true);
/*
           	if (Ext.getCmp('m_method_type').getValue() == '1') {
				Ext.getCmp('m_apk_code').setDisabled(false);
				Ext.getCmp('m_b_apk_code').setDisabled(false);
			}
*/			
           	if (Ext.getCmp('m_method_type').getValue() == '2') {
				Ext.getCmp('m_interface_namespace').setDisabled(false);
				Ext.getCmp('m_interface_version').setDisabled(false);
			}
		}
		
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.sysAddressId = selGridItem[0].data.sysAddressId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobileOtherSysManagerAction", paramObj);
			 				
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
        Ext.getCmp('otherSysGrid').store.removeAll();
        Ext.getCmp('otherSysGrid').store.reload();
    }

}