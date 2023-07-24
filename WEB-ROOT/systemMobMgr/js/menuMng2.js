
 
function MenuOper(){

	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var moduleItem = Ext.getCmp('moduelTree').getSelectionModel().selNode;
        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'webservice方式'],[2,'其它方式']]});
        var staticdistypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[2,'list列表'],[3,'明细页面'],[4,'回单页面']]});
        if(moduleItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请选择菜单目录！',
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
                items: [{
                    xtype:'textfield',
                    fieldLabel: '环节名称',
                    name: 'm_form_name',
                    id: 'm_form_name',
	                allowBlank:false, 
	                blankText:"环节名称不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '环节代码',
                    name: 'm_teach_name',
                    id: 'm_teach_name',
	                allowBlank:false, 
	                blankText:"环节名称不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '接口方法',
                    name: 'm_inteface_name',
                    id: 'm_inteface_name',
	                allowBlank:false, 
	                blankText:"接口方法不能为空!",
                    width: '160'
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'combo',
                    fieldLabel: '请求类型',
                    name: 'm_inteface_type',
                    id: 'm_inteface_type',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: staticStore,
                    allowBlank:false, 
	                blankText:"环节名称不能为空!",
                    width: '160'
                },{
                    xtype:'combo',
                    fieldLabel: '显示类型',
                    name: 'm_display_type',
                    id: 'm_display_type',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: staticdistypeStore,
                    allowBlank:false, 
	                blankText:"环节名称不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '关键字段',
                    name: 'm_key_name',
                    id: 'm_key_name',
	                allowBlank:false, 
	                blankText:"关键字段不能为空!",
                    width: '160'
                }]
            },{columnWidth:.1,layout: 'form'}]},{
                    xtype:'textfield',
                    fieldLabel: '接口地址',
                    name: 'm_inteface_url',
                    id: 'm_inteface_url',
	                allowBlank:false, 
	                blankText:"接口地址不能为空!",
                    anchor:'95%'
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
			    			
			    			var objq = new Object();
			    			objq.type = "selMeuIsExit";
			    			objq.teachName = Ext.getCmp("m_teach_name").getValue();
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该下一环节已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
			    			var inputParams = new Object();
			    			inputParams.formName = Ext.getCmp("m_form_name").getValue();
			    			inputParams.teachName = Ext.getCmp("m_teach_name").getValue();
			    			inputParams.muneId = moduleItem.id;

			    			inputParams.displayType = Ext.getCmp("m_display_type").getValue();		    			
			    			inputParams.intefaceType = Ext.getCmp("m_inteface_type").getValue();			    			
			    			inputParams.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
			    			inputParams.intefaceName = Ext.getCmp("m_inteface_name").getValue();
			    			inputParams.keyName = Ext.getCmp("m_key_name").getValue();
			    			inputParams.state = '10A';		
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertJsonCreateAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.formId = selItem[0].data.formId;
                            inputParams.formName = Ext.getCmp("m_form_name").getValue();
                            inputParams.teachName = Ext.getCmp("m_teach_name").getValue();
			    			inputParams.muneId = moduleItem.id;
			    			inputParams.displayType = Ext.getCmp("m_display_type").getValue();		    			
			    			inputParams.intefaceType = Ext.getCmp("m_inteface_type").getValue();
			    			inputParams.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
			    			inputParams.intefaceName = Ext.getCmp("m_inteface_name").getValue();
			    			inputParams.keyName = Ext.getCmp("m_key_name").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertJsonCreateAction", inputParams);
							
			    			
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
	        title: '环节管理',
		    closable:true,
		    width:650,
		    height:200,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_form_name").setValue(selItem[0].data.formName);
			Ext.getCmp("m_teach_name").setValue(selItem[0].data.teachName);
			Ext.getCmp("m_display_type").setValue(selItem[0].data.displayType);		
	    	Ext.getCmp("m_inteface_type").setValue(selItem[0].data.intefaceType);
	    	Ext.getCmp("m_inteface_url").setValue(selItem[0].data.intefaceUrl);
	    	Ext.getCmp("m_inteface_name").setValue(selItem[0].data.intefaceName);
	    	Ext.getCmp("m_key_name").setValue(selItem[0].data.keyName);

		}
	}	
}