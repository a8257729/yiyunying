function StaffConfigOper(){
	
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('staffGrid').getSelectionModel().getSelections();
		var outsysselItem = Ext.getCmp('outSysStaffGird').getSelectionModel().getSelections();

		var sysStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/QryMobileBusiSysAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'sysStore'  
            }, [   
                {name: 'name', mapping: 'sysProvider'},   
                {name: 'value', mapping: 'busiSysId'}   
            ])   
        }); 
        sysStore.load({params:{flag:1,start:0, limit:20}});
        
		if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择人员！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
		var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth: 80,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'hidden',
                    fieldLabel: '人员id',
                    name: 'staffId',
                    id: 'staffId',
                    value:'',
                    anchor:'95%'    
                },{
                    xtype:'hidden',
                    fieldLabel: '人员名称',
                    name: 'staffName',
                    id: 'staffName',
                    allowBlank:false, 
                    value:'',
                    anchor:'95%'    
                },{
                    xtype:'combo',
                    fieldLabel: '业务系统',
                    name: 'busiSysId',
                    id: 'busiSysId',
                    valueField: 'value',
                    displayField: 'name',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: sysStore,
                    allowBlank: false,
                    width: '160'                
                },{
                    xtype:'textfield',
                    fieldLabel: '映射账号ID',
                    name: 'mappingStaffId',
                    id: 'mappingStaffId',
                    allowBlank:false, 
                    readOnly: false,
                    width: '160'
                  },{
                      xtype:'textfield',
                      fieldLabel: '映射职位ID',
                      name: 'mappingJobId',
                      id: 'mappingJobId',
                      readOnly: false,
                      width: '160'
                  },{
                        xtype:'textfield',
                        fieldLabel: '映射角色ID',
                        name: 'mappingRoleId',
                        id: 'mappingRoleId',
                        readOnly: false,
                        width: '160'
                  }]
	            },
                {columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '映射登陆名称',
                    name: 'mappingUserName',
                    id: 'mappingUserName',
                    allowBlank:false, 
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '映射账号名称',
                    name: 'mappingStaffName',
                    id: 'mappingStaffName',
	                allowBlank:false, 
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '映射职位名称',
                    name: 'mappingJobName',
                    id: 'mappingJobName',
                    readOnly: false,
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '映射角色名称',
                    name: 'mappingRoleName',
                    id: 'mappingRoleName',
                    readOnly: false,
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
			    	var resultStr ;
			    		   			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';

			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.busiSysId = Ext.getCmp("busiSysId").getValue();
			    			if (inputParams.mappingJobId ==""){
			    			    inputParams.mappingJobId = null;
			    			}
			    			if (inputParams.mappingRoleId ==""){
			    			    inputParams.mappingRoleId = null;
                            }
			    			inputParams.staffId = pmId;
			    			inputParams.staffName = pmName;
							inputParams.type = "add";
							var retMap = invokeAction("/mappingService/OptMobileStaffMappingAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.busiSysId = Ext.getCmp("busiSysId").getValue();
			    			inputParams.staffMappingId = outsysselItem[0].data.staffMappingId;
			    			if (inputParams.mappingJobId ==""){
                                inputParams.mappingJobId = null;
                            }
                            if (inputParams.mappingRoleId ==""){
                                inputParams.mappingRoleId = null;
                            }
							inputParams.type = "mod";
							var retMap = invokeAction("/mappingService/OptMobileStaffMappingAction", inputParams);
							
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
			        }, 300);
			        menuWin.close();
			        Ext.getCmp('outSysStaffGird').store.removeAll();
			        Ext.getCmp('outSysStaffGird').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
		
		menuWin = new Ext.Window({
	        title: '外系统人员管理',
		    closable:true,
		    width:650,
		    height:230,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
           
           Ext.getCmp("mappingStaffId").setValue(outsysselItem[0].data.mappingStaffId);
           Ext.getCmp("mappingRoleId").setValue(outsysselItem[0].data.mappingRoleId);
           Ext.getCmp("mappingJobId").setValue(outsysselItem[0].data.mappingJobId);
            
           Ext.getCmp("mappingUserName").setValue(outsysselItem[0].data.mappingUserName);
           Ext.getCmp("mappingStaffName").setValue(outsysselItem[0].data.mappingStaffName);
           Ext.getCmp("mappingJobName").setValue(outsysselItem[0].data.mappingJobName);
           Ext.getCmp("mappingRoleName").setValue(outsysselItem[0].data.mappingRoleName);
           
           sysStore.load({
               params:{flag:1,start:0, limit:20},
               callback: function () {
                   //等待数据加载完成才进行赋值，不然由于异步会出现先赋值后加载完成。
                   Ext.getCmp("busiSysId").setValue(outsysselItem[0].data.busiSysId);
               },
               scope: sysStore,//表示作用范围
               add: false //为false表示数据不累加
           });
		}
	}
		
}