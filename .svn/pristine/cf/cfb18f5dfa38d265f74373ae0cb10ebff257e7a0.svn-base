function StatTransferOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('transGrid').getSelectionModel().getSelections();
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
        
            
        var typeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'单击方式'],[2,'点击方式']]});
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
                labelWidth : 80,
                items: [{
                	xtype: 'compositefield',
					fieldLabel: '转取环节属性名称',
					items: [{
						xtype: 'textfield',
	                	name: 'm_nextFiledId_name',
	                  	id: 'm_nextFiledId_name',
					   	width: '140',
					  	allowBlank:false, 
					 	readOnly: true
					    },{
					    xtype: 'button',
					    id : 'm_b_filed',
					    text: '..',
					    handler: function(){selOper.selIndexInTransfer();}
					}]
				},{
                	xtype : 'hidden',
                	id : 'm_nextFiledId',
                	value : '0'
                },{
                	xtype: 'compositefield',
					fieldLabel: '转取下一环节表',
					items: [{
						xtype: 'textfield',
	                	name: 'm_nextFormId_name',
	                  	id: 'm_nextFormId_name',
					   	width: '140',
					  	allowBlank:false, 
					 	readOnly: true
					    },{
					    xtype: 'button',
					    id : 'm_b_form',
					    text: '..',
					    handler: function(){selOper.selFormIdInTransfer();}
					}]
				},{
                	xtype : 'hidden',
                	id : 'm_nextFormId',
                	value : '0'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                //labelWidth : 80,
                items: [{
                	xtype : 'textfield',
                	fieldLabel : '操作名称',
                	id : 'm_operate_name',
                	name : 'm_operate_name'
                },{
                    xtype:'combo',
                    fieldLabel: '操作类型',
                    name: 'm_operate_type',
                    id: 'm_operate_type',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: typeStore,
                    width: '160'
                }]
            },{columnWidth:.1,layout: 'form'}]}],
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
			    			
			    			var objq = new Object();
			    			objq.type = "selTransferIsExit";
			    			objq.operateName = Ext.getCmp("m_operate_name").getValue();
			    			objq.formId = selItem[0].data.formId;
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
			    			var inputParams = new Object();
			    			inputParams.nextFiledId = Ext.getCmp("m_nextFiledId").getValue();
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.nextFormId = Ext.getCmp("m_nextFormId").getValue();
                            inputParams.operateName = Ext.getCmp("m_operate_name").getValue();
                            inputParams.operateType = Ext.getCmp("m_operate_type").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertStatTransferAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.statId = selGridItem[0].data.statId;
			    			inputParams.nextFiledId = Ext.getCmp("m_nextFiledId").getValue();
                            inputParams.nextFormId = Ext.getCmp("m_nextFormId").getValue();
                            inputParams.operateName = Ext.getCmp("m_operate_name").getValue();
                            inputParams.operateType = Ext.getCmp("m_operate_type").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertStatTransferAction", inputParams);
			    			
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
			        Ext.getCmp('transGrid').store.removeAll();
			        Ext.getCmp('transGrid').store.reload();
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
		    height:230,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_nextFiledId").setValue(selGridItem[0].data.nextFiledId);
			Ext.getCmp("m_nextFiledId_name").setValue(selGridItem[0].data.filedIdName);
			Ext.getCmp("m_nextFormId").setValue(selGridItem[0].data.nextFormId);
			Ext.getCmp("m_nextFormId_name").setValue(selGridItem[0].data.nextFormIdName);
			Ext.getCmp("m_operate_name").setValue(selGridItem[0].data.operateName);
            Ext.getCmp("m_operate_type").setValue(selGridItem[0].data.operateType);
		}
	}	
	this.moduleDel = function(btn){
    	var selButGrid = Ext.getCmp('transGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.statId = selButGrid[0].data.statId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertStatTransferAction", paramObj);
			 				
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
        Ext.getCmp('transGrid').store.removeAll();
        Ext.getCmp('transGrid').store.reload();
    }

} 