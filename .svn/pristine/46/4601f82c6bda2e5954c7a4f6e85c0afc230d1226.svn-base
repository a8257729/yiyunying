function StaffSecConfigOper(){

    this.moduleDel = function(btn){
        if(btn == 'no'){
            return ;
        }
        var selGridItem = Ext.getCmp('staffSecConfigGrid').getSelectionModel().getSelections();

        var paramObj = new Object();
        paramObj.mobileStaffSecConfigIds = selGridItem[0].data.mobileStaffSecConfigId;
        paramObj.optype = "D";
        var retMap = invokeAction("/security/OptMobileStaffSecConfigAction", paramObj);

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
            Ext.example.msg('','删除成功！');
        }, 1000);
        Ext.getCmp('staffSecConfigGrid').store.removeAll();
        Ext.getCmp('staffSecConfigGrid').store.reload();
    }

	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('staffSecConfigGrid').getSelectionModel().getSelections();

		var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth: 70,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'p_username',
                    id: 'p_username',
	                allowBlank:false, 
	                blankText:"用户名不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '手机号码',
                    name: 'p_mobile',
                    id: 'p_mobile',
                    width: '160'
                },{
                    fieldLabel: '安全设置类型',
                    xtype:"combo",
                    name: 'p_securityType',
                    id: 'p_securityType',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '0',
                    store: securityTypeStore,
                    allowBlank:false
                    }
                ]},{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '人员名称',
                    name: 'p_staffName',
                    id: 'p_staffName',
                    allowBlank:true,
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: 'IMSI码',
                    name: 'p_imsiCode',
                    id: 'p_imsiCode',
                    allowBlank:true,
                    width: '160'
                },{
                    fieldLabel: '绑定状态',
                    xtype:"combo",
                    name: 'p_bindingStatus',
                    id: 'p_bindingStatus',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '0',
                    store: bindingStatusStore,
                    allowBlank:false
                    },{
                    xtype:'hidden',
                    fieldLabel: 'IMSI码',
                    name: 'p_staffId',
                    id: 'p_staffId',
                    allowBlank:true,
                    width: '160'
                    }]
                }]
            }],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	var resultStr;

			    	switch (operator){
			    		case "I":{
			    			resultStr = '新增成功！';

			    			var inputParams = new Object();
			    			inputParams.staffId = Ext.getCmp("p_staffId").getValue();
			    			inputParams.staffName = Ext.getCmp("p_staffName").getValue();
                            inputParams.username = Ext.getCmp("p_username").getValue();
                            inputParams.mobile = Ext.getCmp("p_mobile").getValue();
			    			inputParams.securityType = Ext.getCmp("p_securityType").getValue();
			    			inputParams.bindingStatus = Ext.getCmp("p_bindingStatus").getValue();
							inputParams.optype = "I";
							var retMap = invokeAction("/security/OptMobileStaffSecConfigAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "U":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
                            inputParams.mobileStaffSecConfigId = Ext.getCmp("p_mobileStaffSecConfigId").getValue();
                            inputParams.staffId = Ext.getCmp("p_staffId").getValue();
                            inputParams.staffName = Ext.getCmp("p_staffName").getValue();
                            inputParams.username = Ext.getCmp("p_username").getValue();
                            inputParams.mobile = Ext.getCmp("p_mobile").getValue();
                            inputParams.securityType = Ext.getCmp("p_securityType").getValue();
                            inputParams.bindingStatus = Ext.getCmp("p_bindingStatus").getValue();
                            inputParams.optype = "U";
							var retMap = invokeAction("/security/OptMobileStaffSecConfigAction", inputParams);
							
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
			        Ext.getCmp('staffSecConfigGrid').store.removeAll();
			        Ext.getCmp('staffSecConfigGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
		
		menuWin = new Ext.Window({
	        title: '用户安全配置',
		    closable:true,
		    width:650,
		    height:200,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'U'){
			Ext.getCmp("p_mobileStaffSecConfigId").setValue(selItem[0].data.mobileStaffSecConfigId);
			Ext.getCmp("p_staffId").setValue(selItem[0].data.staffId);
			Ext.getCmp("p_staffName").setValue(selItem[0].data.staffName);
	    	Ext.getCmp("p_securityType").setValue(selItem[0].data.securityType);
	    	Ext.getCmp("p_imsiCode").setValue(selItem[0].data.imsiCode);
	    	Ext.getCmp("p_username").setValue(selItem[0].data.username);
	    	Ext.getCmp("p_mobile").setValue(selItem[0].data.mobile);
			Ext.getCmp("p_bindingStatus").setValue(selItem[0].data.bindingStatus);
		}
	}	
}