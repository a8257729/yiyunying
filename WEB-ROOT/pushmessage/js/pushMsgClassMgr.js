function PushMsgClassOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();

        var classTypeSource = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'公告类'],[0,'消息类']]});

        var infoPage = new Ext.FormPanel({
			region: 'center',
		 	frame:true,
            bodyStyle:'padding:5px 5px 0',
            width: 380,
            defaults: {width: 230},
            defaultType: 'textfield',
            items: [{
                    fieldLabel: '分类类型',
                    xtype:"combo",
                    name: 'm_classType',
                    id: 'm_classType',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '0',
                    store: classTypeSource,
                    allowBlank:false
                },{
                    fieldLabel: '分类名称',
                    xtype:"textfield",
                    name: 'm_className',
                    id: 'm_className',
                    allowBlank:false
                },{
                    fieldLabel: '消息来源',
                    xtype:"textfield",
                    name: 'm_source',
                    id:"m_source"
                },{
                    fieldLabel: '备注',
                    xtype:"textarea",
                    height:100,
                    name: 'm_memo',
                    id:"m_memo"
                },{
                    fieldLabel: '分类ID',
                    xtype:"hidden",
                    name: 'm_pushMessageClassId',
                    id: 'm_pushMessageClassId'
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
			    			inputParams.className = Ext.getCmp("m_className").getValue();
                            inputParams.classType = Ext.getCmp("m_classType").getValue();
                            inputParams.staffId = session1.staff.staffId;
                            inputParams.staffName = session1.staff.staffName;
                            inputParams.source = Ext.getCmp("m_source").getValue();
                            inputParams.memo = Ext.getCmp("m_memo").getValue();
                            //操作类型
							inputParams.optype = "I";
							var retMap = invokeAction("/pushmessage/OptPushMessageClassAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "U":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
                            inputParams.pushMessageClassId = Ext.getCmp("m_pushMessageClassId").getValue();
                            inputParams.className = Ext.getCmp("m_className").getValue();
                            inputParams.classType = Ext.getCmp("m_classType").getValue();
                            inputParams.staffId = session1.staff.staffId;
                            inputParams.staffName = session1.staff.staffName;
                            inputParams.source = Ext.getCmp("m_source").getValue();
                            inputParams.memo = Ext.getCmp("m_memo").getValue();
                         
							inputParams.optype = "U";
							var retMap = invokeAction("/pushmessage/OptPushMessageClassAction", inputParams);
			    			
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
	        title: '分类管理',
		    closable:true,
		    width:380,
		    height:300,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'U') {
			Ext.getCmp("m_pushMessageClassId").setValue(selGridItem[0].data.pushMessageClassId);
            Ext.getCmp("m_className").setValue(selGridItem[0].data.className);
            Ext.getCmp("m_classType").setValue(selGridItem[0].data.classType);
            Ext.getCmp("m_source").setValue(selGridItem[0].data.source);
            Ext.getCmp("m_memo").setValue(selGridItem[0].data.memo);
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.pushMessageClassIds = selGridItem[0].data.pushMessageClassId;
			paramObj.optype = "D";
			var retMap = invokeAction("/pushmessage/OptPushMessageClassAction", paramObj);
			 				
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
        Ext.getCmp('menuGrid').store.removeAll();
        Ext.getCmp('menuGrid').store.reload();
    }
	
	

}