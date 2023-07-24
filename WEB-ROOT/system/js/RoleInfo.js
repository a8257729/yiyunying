/*
 * by fang.li   2010-10	
 * 角色管理页面
 */
 
function RoleOper(){
	this.showRoleInfo = function(operator){
		var roleInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '区域',
	                name: 'areaName',
	                id: 'areaName',
	                readOnly: true,
	                value:areaName,
	                anchor:'90%'
	            },{
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'roleName',
	                id: 'roleName',
	                allowBlank:false, 
	                blankText:"名称不能为空!",
	                anchor:'90%'
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
	            	
			    	//表单验证
					if(!roleInfoForm.getForm().isValid()){
						return;
					}
					var itemsArr = Ext.getCmp('roleGrid').getStore().data.items ;
					//新增角色
					if(operator == 'add'){
						for(var i=0;i<itemsArr.length;i++){
							if (itemsArr[i].data.roleName == Ext.getCmp("roleName").getValue()){
								//--ErrorHandle('指定的角色与现有职位重名，请指定另一角色名称');
								Ext.MessageBox.show({
						           title: '提示',
						           msg: '指定的角色名与现有角色重名，请指定另一角色名称！',
						           buttons: Ext.MessageBox.OK,
						           width:320,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
						}
					}else if(operator = 'mod'){
						var roleName = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleName ;
					
						for(var i=0;i<itemsArr.length;i++){
							if (itemsArr[i].data.roleName == Ext.getCmp("roleName").getValue() && Ext.getCmp("roleName").getValue() != roleName){
								Ext.MessageBox.show({
						           title: '提示',
						           msg: '指定的角色名与现有角色重名，请指定另一角色名称！',
						           buttons: Ext.MessageBox.OK,
						           width:320,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
						}
					}
					
					var _obj = new Object();
					_obj.roleName = Ext.getCmp("roleName").getValue();
					_obj.comments = Ext.getCmp("comments").getValue();
					_obj.areaId = areaId;
					
					switch (operator){
						case "add":
						{	
							_obj.roleId = "0";
							var _result = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.rolemanager.RoleManagerWeb", "create", _obj);
							
							
							//默认把新建角色赋予创建人				
							var staffId = session1.staff.staffId;//登陆人的Id
							var jobId = session1.job.jobId; //登陆人的职位id
			
							if(jobId>0 && staffId){
								var selectedRoles = new Array();
								var canGrant = new Array();
								selectedRoles[selectedRoles.length] = _result.roleId;
								canGrant[canGrant.length] = 1;
								callRemoteFunction("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "insertStaffRoles", staffId, selectedRoles, canGrant);					
							}
							Ext.MessageBox.show({
					           	msg: '系统正在提交数据…',
					           	progressText: 'Saving...',
					           	width:300,
					           	wait:true,
					           	waitConfig: {interval:100},
					           	icon:'ext-mb-download'
					       	});
					        setTimeout(function(){
					        	Ext.MessageBox.hide();
					            Ext.example.msg('','新增角色成功！');
					        }, 1000);
							break;
						}
						case "mod":
						{
							var roleId = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleId ;
							_obj.roleId = roleId ;
							callRemoteFunction("com.zterc.uos.oaas.service.rolemanager.RoleManagerWeb", "update", _obj);
							Ext.MessageBox.show({
					           	msg: '系统正在提交数据…',
					           	progressText: 'Saving...',
					           	width:300,
					           	wait:true,
					           	waitConfig: {interval:100},
					           	icon:'ext-mb-download'
					       	});
					       	setTimeout(function(){
					        	Ext.MessageBox.hide();
					            Ext.example.msg('','修改角色成功！');
					        }, 1000);
							break;
						}
					}
					win.close();
					Ext.getCmp('roleGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '角色',
		    closable:true,
		    width:450,
		    height:275,
		    plain:true,
		    layout: 'border',
		    items: [roleInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("comments").setValue(Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.comments);
			Ext.getCmp("roleName").setValue(Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleName);
			
		}
	}
}