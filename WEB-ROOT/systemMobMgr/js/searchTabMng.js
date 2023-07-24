function SearchTabOper(){//3
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('tabGrid').getSelectionModel().getSelections();
		
		var staticShowStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
		var positionStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'顶部'],[2,'底部'],[3,'任意布局']]});
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
                	xtype : 'textfield',
                	fieldLabel : 'TAB页名称',
                	id : 'm_tab_name',
                	name : 'm_tab_name',
					allowBlank:false,
					width: '160'
				},{
                	xtype: 'compositefield',
					fieldLabel: '对应环节',
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
					    handler: function(){selOper.selFormIdInTransfer();}//selFormIdInTransfer()原本是用于statTransferMng.js,但是在这里通用
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
                	fieldLabel : '顺序',
                	id : 'm_seq_id',
                	name : 'm_seq_id',
                	allowBlank: false,
                	vtype: 'num'
                },{
                    xtype:'combo',
                    fieldLabel: '是否显示',
                    name: 'm_is_show',
                    id: 'm_is_show',
                     valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticShowStore,
                    width: '160'
                },{
                    xtype:'combo',
                    fieldLabel: '显示坐标',
                    name: 'm_position',
                    id: 'm_position',
                     valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: positionStore,
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
			    			objq.type = "selSearchTabIsExit";
			    			objq.operateName = Ext.getCmp("m_tab_name").getValue();
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
                            inputParams.tabName = Ext.getCmp("m_tab_name").getValue();
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.nextFormId = Ext.getCmp("m_nextFormId").getValue();
                            inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
                            inputParams.isShow = Ext.getCmp("m_is_show").getValue();
                            inputParams.position = Ext.getCmp("m_position").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertSearchTabAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.tabId = selGridItem[0].data.tabId;
			    			inputParams.tabName = Ext.getCmp("m_tab_name").getValue();
			    			inputParams.nextFormId = Ext.getCmp("m_nextFormId").getValue();
                            inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
                            inputParams.isShow = Ext.getCmp("m_is_show").getValue();
                            inputParams.position = Ext.getCmp("m_position").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertSearchTabAction", inputParams);
			    			
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
			        Ext.getCmp('tabGrid').store.removeAll();
			        Ext.getCmp('tabGrid').store.reload();
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
		    height:180,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){			
			Ext.getCmp("m_nextFormId").setValue(selGridItem[0].data.nextFormId);
			Ext.getCmp("m_nextFormId_name").setValue(selGridItem[0].data.nextFormIdName);
			Ext.getCmp("m_tab_name").setValue(selGridItem[0].data.tabName);
            Ext.getCmp("m_seq_id").setValue(selGridItem[0].data.seqId);
            Ext.getCmp("m_is_show").setValue(selGridItem[0].data.isShow);
            Ext.getCmp("m_position").setValue(selGridItem[0].data.position);
           
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selButGrid = Ext.getCmp('tabGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.tabId = selButGrid[0].data.tabId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertSearchTabAction", paramObj);
			 				
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
        Ext.getCmp('tabGrid').store.removeAll();
        Ext.getCmp('tabGrid').store.reload();
    }

} 