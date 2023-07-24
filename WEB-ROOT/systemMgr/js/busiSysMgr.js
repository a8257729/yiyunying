
function BusiSysOper(){
	
	this.initSysFieldTypeStore = function(){
	     var sysFieldTypeSource = new Ext.data.JsonStore({
			id: 'sysFieldTypeSource',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SERVER_SYS'
	        }),
	          baseParams:{flag:1}
	    });
	    return sysFieldTypeSource;
	}
	
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();

        var sysFieldTypeSource = this.initSysFieldTypeStore();

        var infoPage = new Ext.FormPanel({
			region: 'center',
		 	frame:true,
            bodyStyle:'padding:5px 5px 0',
            width: 380,
            defaults: {width: 230},
            defaultType: 'textfield',
            items: [{
                    fieldLabel: '系统域类型',
                    xtype:"combo",
                    name: 'm_sysFieldType',
                    id: 'm_sysFieldType',
                    valueField: 'mcode',
	                displayField: 'mname',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:sysFieldTypeSource,
                    allowBlank:false
                },{
                    fieldLabel: '系统名称',
                    xtype:"textfield",
                    name: 'm_sysName',
                    id: 'm_sysName',
                    allowBlank:false
                },{
                    fieldLabel: '系统提供商',
                    xtype:"textfield",
                    name: 'm_sysProvider',
                    id: 'm_sysProvider',
                    allowBlank:false
                },{
                    fieldLabel: '系统地址',
                    xtype:"textfield",
                    name: 'm_sysAddr',
                    id:"m_sysAddr"
                },{
                    fieldLabel: '分类ID',
                    xtype:"hidden",
                    name: 'm_busiSysId',
                    id: 'm_busiSysId'
                }
            ],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    				  
			 				
			    	var resultStr ;			    	
			    	switch (operator){
			    		case "I":{
			    			resultStr = '新增成功！';
			    			var inputParams = new Object();
			    			inputParams.sysFieldType = Ext.getCmp("m_sysFieldType").getValue();
                            inputParams.sysName = Ext.getCmp("m_sysName").getValue();
                            inputParams.sysProvider = Ext.getCmp("m_sysProvider").getValue();
                            inputParams.sysAddr = Ext.getCmp("m_sysAddr").getValue();
                            //操作类型
							inputParams.optype = "I";
							var retMap = invokeAction("/interf/OptBusiSysAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "U":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
                            inputParams.busiSysId = Ext.getCmp("m_busiSysId").getValue();
			    			inputParams.sysFieldType = Ext.getCmp("m_sysFieldType").getValue();
                            inputParams.sysName = Ext.getCmp("m_sysName").getValue();
                            inputParams.sysProvider = Ext.getCmp("m_sysProvider").getValue();
                            inputParams.sysAddr = Ext.getCmp("m_sysAddr").getValue();
                         
							inputParams.optype = "U";
							var retMap = invokeAction("/interf/OptBusiSysAction", inputParams);
			    			
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
			        Ext.getCmp('menuGrid').store.removeAll();
			        Ext.getCmp('menuGrid').store.reload();
			    }}
	        },{
	            text: '取消', 
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    	    
		
		menuWin = new Ext.Window({
	        title: '业务系统管理',
		    closable:true,
		    width:380,
		    height:300,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'U') {
			Ext.getCmp("m_busiSysId").setValue(selGridItem[0].data.busiSysId);
            Ext.getCmp("m_sysName").setValue(selGridItem[0].data.sysName);
            Ext.getCmp("m_sysProvider").setValue(selGridItem[0].data.sysProvider);
            Ext.getCmp("m_sysFieldType").setValue(selGridItem[0].data.sysFieldType);
            Ext.getCmp("m_sysAddr").setValue(selGridItem[0].data.sysAddr);
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.busiSysIds = selGridItem[0].data.busiSysId;
			paramObj.optype = "D";
			var retMap = invokeAction("/interf/OptBusiSysAction", paramObj);
			 				
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
        Ext.getCmp('menuGrid').store.removeAll();
        Ext.getCmp('menuGrid').store.reload();
    }
	
	

}