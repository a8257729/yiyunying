/*
 * by fang.li   2010-10	
 * 权限管理-权限管理页面
 */
 
function PrivOper(){
	this.showPrivInfo = function(operator){
		var stateSrote = new Ext.data.ArrayStore({
        	fields:['id','name'],
        	data: [['10A','有效'],['10X','失效']]
	    });
		var privClassInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '编码',
	                name: 'privCode',
	                id: 'privCode',
	                allowBlank:false, 
	                blankText:"编码不能为空!",
	                anchor:'90%'
	            },{
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'privName',
	                id: 'privName',
	                allowBlank:false, 
	                blankText:"名称不能为空!",
	                anchor:'90%'
	            },{
	                   name: 'state',
	                   fieldLabel: '状态',
	                   xtype: 'combo',
	                   valueField: 'id',
	                   displayField: 'name',
	                   id: 'state',
	                   mode: 'local',
	                   anchor:'90%',
	                   triggerAction: 'all',
	                   editable : false ,
	                   value: '10A',
	                   store: stateSrote
	               },{
	                xtype:'textarea',
				    fieldLabel: '备注',
				    name: 'comments',
				    id: 'comments',
				    height : 100,
				    anchor:'90%'
	            }
	        ],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
	            	var selPriv = Ext.getCmp('privGrid').getSelectionModel().getSelections();
	            	var selNode = Ext.getCmp('privClassTree').getSelectionModel().selNode ;
	            	
	            	var _obj = new Object();	
					_obj.privCode = Trim(Ext.getCmp("privCode").getValue());
					_obj.privName = Ext.getCmp("privName").getValue();
					_obj.comments = Ext.getCmp("comments").getValue();
					
					//表单验证
					if(!privClassInfoForm.getForm().isValid()){
						return;
					}
	            	
	            	switch (operator){
						case "add":
						{	
							//判断权限编号已经存在了
							var isExistPriv = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.privmanager.PrivilegeManagerWeb", "isExistPriv", _obj.privCode);
							if(isExistPriv == true){
								Ext.MessageBox.show({
						            title: '提示',
						            msg: '权限编码已经存在，请重新指定！',
						            buttons: Ext.MessageBox.OK,
						            width:200,
						            icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
							_obj.privClassId = selNode.id ;
							var _result = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.privmanager.PrivilegeManagerWeb", "create", _obj);
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
					            Ext.example.msg('','增加权限成功！');
					        }, 1000);
							break;
						}
						case "mod":
						{
							_obj.state = Ext.getCmp("state").getValue();
							callRemoteFunction("com.zterc.uos.oaas.service.privmanager.PrivilegeManagerWeb", "update", _obj);
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
					            Ext.example.msg('','修改权限成功！');
					        }, 1000);
							break;
						}
					}
					win.close();
					Ext.getCmp('privGrid').store.removeAll();
					Ext.getCmp('privGrid').store.load();
	            }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '权限',
		    closable:true,
		    width:440,
		    height:290,
		    plain:true,
		    layout: 'border',
		    items: [privClassInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("comments").setValue(Ext.getCmp('privGrid').getSelectionModel().getSelections()[0].data.comments);
			Ext.getCmp("privCode").setValue(Ext.getCmp('privGrid').getSelectionModel().getSelections()[0].data.privCode);
			Ext.getCmp("privName").setValue(Ext.getCmp('privGrid').getSelectionModel().getSelections()[0].data.privName);
			Ext.getCmp("privCode").setDisabled(true);
			
	    	Ext.getCmp("state").setValue(Ext.getCmp('privGrid').getSelectionModel().getSelections()[0].data.state);
			
		}else if (operator == 'add'){
			Ext.getCmp("state").setDisabled(true);
		}
	}
}