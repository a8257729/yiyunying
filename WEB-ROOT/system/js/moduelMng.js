/*
 * by fang.li   2010-10	
 * 菜单-菜单模块管理页面
 */
 
function ModuelOper(){
	 this.showModuleInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		
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
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '父模块名称',
	                    name: 'v_pmName',
	                    id: 'v_pmName',
	                	readOnly: true,
	                	value: pmName,
	                    anchor:'90%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '图标文件',
	                    name: 'v_iconFileName',
	                    id: 'v_iconFileName',
	                	allowBlank:false, 
	                	blankText:"图标文件不能为空!",
	                    anchor:'90%'
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '模块名称',
	                    name: 'v_name',
	                    id: 'v_name',
	                	allowBlank:false, 
	                	blankText:"模块名称不能为空!",
	                    anchor:'90%'	
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '显示顺序',
	                    name: 'v_displayIndex',
	                    id: 'v_displayIndex',
	                    vtype: 'num' ,
	                	allowBlank:false, 
	                	blankText:"显示顺序不能为空!",
	                    anchor:'90%'
	                }]
	            },{columnWidth:.05,layout: 'form'}]
	            
	        },{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'v_comment',
			    id: 'v_comment',
			    height : 100,
			    anchor:'90%'
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
			    			fext.clearParam();
					    	fext.addParam(1, "PARENT_ID",pmId);
							fext.addParam(2,"NAME",Ext.getCmp("v_name").getValue());
			 				var tmpObj = fext.callRemoteFunctionQuery("qryRepeatModuleName");
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该菜单目录名称已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
							var objAttr;
							var paramObj = new Object();
							paramObj.parentId = pmId ;
							paramObj.name = Ext.getCmp("v_name").getValue() ;
							paramObj.iconFileName = Ext.getCmp("v_iconFileName").getValue() || "";
							paramObj.displayIndex = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.comments = Ext.getCmp("v_comment").getValue() || "";
							var obj = callRemoteFunction("com.ztesoft.iom.system.MenuManager","addMenuCatalogDto",paramObj);
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
			    			fext.clearParam();
					    	fext.addParam(1, "PARENT_ID",pmId);
							fext.addParam(2,"NAME",Ext.getCmp("v_name").getValue());
			 				var tmpObj = fext.callRemoteFunctionQuery("qryRepeatModuleName");
			 				if(tmpObj!=""){
								for(var i=0;i<tmpObj.length;i++){
									if(selItem.id!=tmpObj[i].ID){
										Ext.MessageBox.show({
								           title: '提示',
								           msg: '该菜单目录名称已存在，请重新指定！',
								           buttons: Ext.MessageBox.OK,
								           width:200,
								           icon: Ext.MessageBox.ERROR
								       	});
										return;
									}
								}			  
							}
							
							var paramObj = new Object();
							paramObj.id = selItem.id ;
							paramObj.name = Ext.getCmp("v_name").getValue() ;
							paramObj.iconFileName = Ext.getCmp("v_iconFileName").getValue() || "";
							paramObj.displayIndex = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.comments = Ext.getCmp("v_comment").getValue() || "";
							
							var obj = callRemoteFunction("com.ztesoft.iom.system.MenuManager","modMenuCatalogDto",paramObj);
			    			break ;
			    		}
			    		case "addSub":{
			    			resultStr = '新增成功！';
			    			fext.clearParam();
					    	fext.addParam(1, "PARENT_ID",pmId);
							fext.addParam(2,"NAME",Ext.getCmp("v_name").getValue());
			 				var tmpObj = fext.callRemoteFunctionQuery("qryRepeatModuleName");
			 				if(tmpObj != ""){
							  Ext.MessageBox.show({
						           title: '提示',
						           msg: '该菜单目录名称已存在，请重新指定！',
						           buttons: Ext.MessageBox.OK,
						           width:200,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
							
							var objAttr;
							var paramObj = new Object();
							paramObj.parentId = pmId ;
							paramObj.name = Ext.getCmp("v_name").getValue() ;
							paramObj.iconFileName = Ext.getCmp("v_iconFileName").getValue() || "";
							paramObj.displayIndex = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.comments = Ext.getCmp("v_comment").getValue() || "";
							var obj = callRemoteFunction("com.ztesoft.iom.system.MenuManager","addMenuCatalogDto",paramObj);
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
			        moduleWin.close();
			        Ext.getCmp('moduelTree').root.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	moduleWin.close();
			    }}
	        }]
	    });
		
		moduleWin = new Ext.Window({
	        title: '模块管理',
		    closable:true,
		    width:650,
		    height:280,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		moduleWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("v_name").setValue(selItem.text);
			Ext.getCmp("v_iconFileName").setValue(selItem.attributes.iconFileName);
			Ext.getCmp("v_displayIndex").setValue(selItem.attributes.displayIndex);
			Ext.getCmp("v_comment").setValue(selItem.attributes.comments);
		}
	}
}