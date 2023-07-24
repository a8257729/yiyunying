function PrivButOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('butGrid').getSelectionModel().getSelections();
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
        var staticShowStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var staticButtomStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var staticOrientStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'垂直'],[2,'水平']]});
        var staticButTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'查询'],[2,'查看'],[3,'增加'],[4,'修改'],[5,'删除'],[6,'验证'],[7,'提交'],[8,'取消'],[9,'执行并刷新']]});
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
                    fieldLabel: '按钮名称',
                    name: 'm_button_name',
                    id: 'm_button_name',
	                allowBlank:false, 
	                blankText:"按钮名称不能为空!",
                    width: '160'
                },{
					    xtype: 'compositefield',
					    fieldLabel: '下一环节',
					    items: [{
					        xtype: 'textfield',
	                        name: 'm_to_page_name',
	                        id: 'm_to_page_name',
	                        readOnly: true,
					        width: '160'
					    },{
					        xtype: 'button',
					        text: '..',
					        handler: selOper.selePriv
					    }]
					
					},{
		                    xtype:'textfield',
		                    fieldLabel: '环节代码',
		                    name: 'm_to_page',
		                    id: 'm_to_page',		             
			                readOnly: true,
		                    width: '160'
		                },{
				                xtype: 'hidden',
				                name: 'm_nextFormId',
				                id: 'm_nextFormId'
							},{
                    xtype:'combo',
                    fieldLabel: '排版方式',
                    name: 'm_orientation',
                    id: 'm_orientation',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticOrientStore,
                    width: '160'
                },{
                    xtype:'combo',
                    fieldLabel: '按钮类型',
                    name: 'm_button_type',
                    id: 'm_button_type',
                     valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticButTypeStore,
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '提示信息',
                    name: 'm_error_info',
                    id: 'm_error_info',
                    width: '160'
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '调用方法',
                    name: 'm_get_method',
                    id: 'm_get_method',
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '权限代码',
                    name: 'm_priv_code',
                    id: 'm_priv_code',
					readOnly: true,
					emptyText: '将自动生成',
                    width: '160'
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
                    fieldLabel: '是否底部',
                    name: 'm_is_bottom',
                    id: 'm_is_bottom',
                     valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticButtomStore,
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '显示顺序',
                    name: 'm_button_sequ',
                    id: 'm_button_sequ',
                    allowBlank:false, 
	                blankText:"顺序不能为空!",
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
			    			objq.type = "selButIsExit";
			    			objq.buttonName = Ext.getCmp("m_button_name").getValue();
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
			    			inputParams.buttonName = Ext.getCmp("m_button_name").getValue();
			    			inputParams.nextFormId =Ext.getCmp("m_nextFormId").getValue();
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.muneId = selItemtree.id;
			    			inputParams.getMethod = Ext.getCmp("m_get_method").getValue();
//                            inputParams.privCode = Ext.getCmp("m_priv_code").getValue();
                            inputParams.orientation = Ext.getCmp("m_orientation").getValue();
			    			inputParams.isShow = Ext.getCmp("m_is_show").getValue();
			    			inputParams.isBottom = Ext.getCmp("m_is_bottom").getValue();
			    			inputParams.buttonType = Ext.getCmp("m_button_type").getValue();
			    			inputParams.buttonSequ = Ext.getCmp("m_button_sequ").getValue();
			    			inputParams.errorInfo = Ext.getCmp("m_error_info").getValue();
			    			
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertButRightAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.buttonId = selGridItem[0].data.buttonId;
                            inputParams.buttonName = Ext.getCmp("m_button_name").getValue();
			    			inputParams.nextFormId =Ext.getCmp("m_nextFormId").getValue();
			    			inputParams.getMethod = Ext.getCmp("m_get_method").getValue();
//			    			inputParams.privCode = Ext.getCmp("m_priv_code").getValue();
			    			inputParams.orientation = Ext.getCmp("m_orientation").getValue();
			    			inputParams.isShow = Ext.getCmp("m_is_show").getValue();
			    			inputParams.isBottom = Ext.getCmp("m_is_bottom").getValue();
			    			inputParams.buttonType = Ext.getCmp("m_button_type").getValue();
			    			inputParams.buttonSequ = Ext.getCmp("m_button_sequ").getValue();
			    			inputParams.errorInfo = Ext.getCmp("m_error_info").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertButRightAction", inputParams);
							
			    			
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
			        Ext.getCmp('butGrid').store.removeAll();
			        Ext.getCmp('butGrid').store.reload();
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
		    height:250,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_button_name").setValue(selGridItem[0].data.buttonName);
			Ext.getCmp("m_to_page").setValue(selGridItem[0].data.toPage);
			Ext.getCmp("m_to_page_name").setValue(selGridItem[0].data.formName);
			Ext.getCmp("m_nextFormId").setValue(selGridItem[0].data.nextFormId);
			Ext.getCmp("m_get_method").setValue(selGridItem[0].data.getMethod);	
			Ext.getCmp("m_priv_code").setValue(selGridItem[0].data.privCode);
			
			Ext.getCmp("m_is_show").setValue(selGridItem[0].data.isShow);
			Ext.getCmp("m_is_bottom").setValue(selGridItem[0].data.isBottom);
			Ext.getCmp("m_orientation").setValue(selGridItem[0].data.orientation);	
			
			Ext.getCmp("m_button_type").setValue(selGridItem[0].data.buttonType);	
			Ext.getCmp("m_button_sequ").setValue(selGridItem[0].data.buttonSequ);	
			Ext.getCmp("m_error_info").setValue(selGridItem[0].data.errorInfo);	
			
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selButGrid = Ext.getCmp('butGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.buttonId = selButGrid[0].data.buttonId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertButRightAction", paramObj);
			 				
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
        Ext.getCmp('butGrid').store.removeAll();
        Ext.getCmp('butGrid').store.reload();
    }
}