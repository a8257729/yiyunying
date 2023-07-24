/*
 * by fang.li   2010-10	
 * 权限管理-权限类别管理页面
 */
 
function PrivClassOper(){
	this.showPrivClassInfo = function(operator){
		var privClassInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'privClassName',
	                id: 'privClassName',
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
					if(!privClassInfoForm.getForm().isValid()){
						return;
					}
					
					var privClassTree = Ext.getCmp('privClassTree') ;
					var selNode = privClassTree.getSelectionModel().selNode ;
					
	            
	            	var _obj = new Object();
					_obj.privClassName = Ext.getCmp("privClassName").getValue();
					_obj.comments = Ext.getCmp("comments").getValue();
					switch (operator){
						case "add":
						{
							if(selNode == null){
								_obj.parentId = 0 ;
								_obj.pathCode = "";
							}else{
								_obj.parentId = selNode.parentNode.id ;
								_obj.pathCode = selNode.parentNode.attributes.pathCode ;
							}
							_obj.privClassId = 0;
							
							//验证有无同名权限类别
							var brotherNodes = selNode.parentNode.childNodes ;

							for(var i=0;i<brotherNodes.length;i++){
								if ( brotherNodes[i].attributes.privClassName == Ext.getCmp("privClassName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '同一权限类别中有重名，请指定另一权限类别名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							var _result= callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.privclassmanager.PrivilegeClassManagerWeb", "create",_obj);
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
					            Ext.example.msg('','增加权限类别成功！');
					        }, 1000);
							break;
						}
						case "mod":
						{
							//验证有无同名权限类别
							var brotherNodes = selNode.parentNode.childNodes ;
							_obj.privClassId = selNode.id ;
							for(var i=0;i<brotherNodes.length;i++){
								if ( brotherNodes[i].attributes.privClassName == Ext.getCmp("privClassName").getValue() && selNode.attributes.privClassName != Ext.getCmp("privClassName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '同一权限类别中有重名，请指定另一权限类别名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							callRemoteFunction("com.zterc.uos.oaas.service.privclassmanager.PrivilegeClassManagerWeb", "update", _obj);
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
					            Ext.example.msg('','修改权限类别成功！');
					        }, 1000);
							break;
						}
						case "addSub":
						{
							_obj.parentId = selNode.id ;
							_obj.pathCode = selNode.attributes.pathCode ;
							_obj.privClassId = 0;
							
							//验证有无同名权限类别
							var brotherNodes = selNode.childNodes ;

							for(var i=0;i<brotherNodes.length;i++){
								if ( brotherNodes[i].attributes.privClassName == Ext.getCmp("privClassName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '同一权限类别中有重名，请指定另一权限类别名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							var _result= callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.privclassmanager.PrivilegeClassManagerWeb", "create",_obj);
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
					            Ext.example.msg('','增加下属权限类别成功！');
					        }, 1000);
							break;
						}
					}
					privClassTree.root.reload();
					win.close();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '权限类别',
		    closable:true,
		    width:440,
		    height:240,
		    plain:true,
		    layout: 'border',
		    items: [privClassInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("comments").setValue(Ext.getCmp('privClassTree').getSelectionModel().selNode.attributes.comments);
			Ext.getCmp("privClassName").setValue(Ext.getCmp('privClassTree').getSelectionModel().selNode.attributes.privClassName);
		}
	}
}