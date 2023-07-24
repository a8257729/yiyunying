/*
 * 
 * 菜单-菜单模块管理页面
 */
 
function ModuelOper(){
	 this.showModuleInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [["true",'是'],["false",'否']]});
        var staticdistypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'二级菜单'],[2,'list列表'],[3,'明细页面'],[4,'回单页面'],[5,'树型'],[6,'内置功能'],[7,'listTree树'],[8,'list对话框'],[9,'流程展显'],[10,'数据传输'],[11,'调用外系统'],[12,'统计报表'],[13,'初始页面'],[14,'信息选择'],[15,'组合页面']]});
		var menuTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'营销高参'],[2,'装维助手'],[3,'看数'],[4,'日常']]});
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
	                    fieldLabel: '父菜单名称',
	                    name: 'v_pmName',
	                    id: 'v_pmName',
	                	readOnly: true,
	                	value: pmName,
	                    anchor:'90%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '菜单名称',
	                    name: 'v_name',
	                    id: 'v_name',
	                	allowBlank:false, 
	                	blankText:"菜单名称不能为空!",
	                    anchor:'90%'	
	                },{
	                    xtype:'combo',
	                    fieldLabel: '是否背景',
	                    name: 'v_isbg',
	                    id: 'v_isbg',
	                	 valueField: 'id',
	                    displayField: 'value',
	                    mode: 'local',
	                    triggerAction: 'all',
	                    value: '',
	                    store: staticStore,
	                    allowBlank:false, 
	                	blankText:"是否背景不能为空!",
	                    anchor:'90%'	
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '图片地址',
	                    name: 'v_icon_adr',
	                    id: 'v_icon_adr',
	                	allowBlank:false, 
	                	blankText:"图片地址不能为空!",
	                    anchor:'90%'	
	                },{  //暂时废弃  modify by guo.jinjun at 2012-05-04 15:40:39
//	                	xtype: 'compositefield',
//						fieldLabel: '接口编码',
//						anchor:'90%',
//						items: [{
//							xtype: 'textfield',
//	            	    	name: 'v_axis_type',
//	                	  	id: 'v_axis_type',
//						 	readOnly: true
//						    },{
//						    xtype: 'button',
//						    id : 'v_b_axis_type',
//						    text: '..',
//						    handler: function(){
//						    	selOper.selectOtherSys("otherSysGrid", "0", "v_axis_type", "interfaceCode", "0", "v_other_sys_code", "systemCode");
//						    	}
//						}]
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'combo',
	                    fieldLabel: '所属分类',
	                    name: 'v_menuType',
	                    id: 'v_menuType',
	                	valueField: 'id',
	                    displayField: 'value',
	                    mode: 'local',
	                    triggerAction: 'all',
	                    value: '1',
	                    store: menuTypeStore,
	                    allowBlank:false, 
	                	blankText:"所属分类不能为空!",
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
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '权限代码',
	                    name: 'v_priv_code',
	                    id: 'v_priv_code',
						readOnly: true,
						emptyText: '将自动生成',
	                    anchor:'90%'	
	                },{						//暂时废弃	modify by guo.jinjun at 2012-05-04 15:40:54
//	                    xtype:'combo',
//	                    fieldLabel: '显示类型',
//	                    name: 'v_displayType',
//	                    id: 'v_displayType',
//	                	valueField: 'id',
//	                    displayField: 'value',
//	                    mode: 'local',
//	                    triggerAction: 'all',
//	                    value: '',
//	                    store: staticdistypeStore,
//	                    allowBlank:false, 
//	                	blankText:"显示类型不能为空!",
//	                    anchor:'90%',
//                    	listeners: {
//                			collapse : function(c){
//	                			if(c.getValue() == 11){
//	                				Ext.getCmp('v_axis_type').setDisabled(true);
//	                				Ext.getCmp('v_b_axis_type').setDisabled(true);
//	                				Ext.getCmp('v_to_page').setDisabled(false);
//									Ext.getCmp('v_b_to_page').setDisabled(false);
//									Ext.getCmp('v_other_sys_code').setDisabled(false);
//	                			}else if (c.getValue() == 6){
//									Ext.getCmp('v_axis_type').setDisabled(true);
//									Ext.getCmp('v_b_axis_type').setDisabled(true);
//									Ext.getCmp('v_to_page').setDisabled(true);									
//									Ext.getCmp('v_b_to_page').setDisabled(true);
//									Ext.getCmp('v_other_sys_code').setDisabled(true);
//								}else{
//									Ext.getCmp('v_axis_type').setDisabled(false);
//									Ext.getCmp('v_b_axis_type').setDisabled(false);
//									Ext.getCmp('v_to_page').setDisabled(false);
//									Ext.getCmp('v_b_to_page').setDisabled(false);
//									Ext.getCmp('v_other_sys_code').setDisabled(false);
//								}
//                			}
//	                	}
//	                },{
//	                    xtype:'textfield',
//	                    fieldLabel: '调用功能',
//	                    name: 'v_get_method',
//	                    id: 'v_get_method',
//	                    anchor:'90%'	
//	                },{
//	                	xtype: 'compositefield',
//						fieldLabel: '下一环节',
//						anchor:'90%',
//						items: [{
//							xtype: 'textfield',
//	            	    	name: 'v_to_page',
//	                	  	id: 'v_to_page',
////						  	allowBlank:false, 
//						 	readOnly: true
//						    },{
//						    xtype: 'button',
//						    id : 'v_b_to_page',
//						    text: '..',
//						    handler: function(){
//						    	if(Ext.getCmp('v_displayType').getValue() == '11'){
//						    		selOper.selectOtherSys("interfaceGrid", "0", "v_other_sys_code", "systemCode", "1", "v_to_page", "mappingCode");
//						    	}else{
//						    		selOper.selectTeachName();
//						    	}
//						    	}
//						}]
//	                },{
//	                    xtype:'textfield',
//	                    fieldLabel: '系统编码',
//	                    name: 'v_other_sys_code',
//	                    id: 'v_other_sys_code',
////	                	allowBlank:false, 
////	                	blankText:"系统标识不能为空!",
//	                    readOnly: true,
//	                    anchor:'90%'	
	                }]
	           },{columnWidth:.1,layout: 'form'}]},{
                    xtype:'hidden',
                    fieldLabel: '接口地址',
                    name: 'm_inteface_url',
                    id: 'm_inteface_url',
					value: ''
                }],
			buttons: [{ 
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	
			    	//暂时废弃这几个字段	modify by guo.jinjun at 2012-05-04 15:50:48
//			    	if(Ext.getCmp("v_displayType").getValue() != 6){
//			    		if(Ext.getCmp("v_other_sys_code").getValue()==''){
//			    			Ext.MessageBox.show({
//					          	title: '提示',
//						        msg: '字段(系统编码)不能为空！',
//						        buttons: Ext.MessageBox.OK,
//						        width:200,
//						        icon: Ext.MessageBox.ERROR
//						    });
//			    			return; 
//			    		}else if(Ext.getCmp("v_to_page").getValue()==''){
//			    			Ext.MessageBox.show({
//					          	title: '提示',
//						        msg: '字段(下一环节)不能为空！',
//						        buttons: Ext.MessageBox.OK,
//						        width:200,
//						        icon: Ext.MessageBox.ERROR
//						    });
//			    			return; 
//			    		}else if(Ext.getCmp("v_axis_type").getValue()=='' && Ext.getCmp("v_displayType").getValue() != 11){
//			    			Ext.MessageBox.show({
//					          	title: '提示',
//						        msg: '字段(接口编码)不能为空！',
//						        buttons: Ext.MessageBox.OK,
//						        width:200,
//						        icon: Ext.MessageBox.ERROR
//						    });
//			    			return; 
//			    		}
//			    	}else if(Ext.getCmp("v_displayType").getValue() != 11 && Ext.getCmp("v_displayType").getValue() != 6){	
//			    		if(Ext.getCmp("v_to_page").getValue()==''){
//			    			Ext.MessageBox.show({
//					          	title: '提示',
//						        msg: '字段(下一环节)不能为空！',
//						        buttons: Ext.MessageBox.OK,
//						        width:200,
//						        icon: Ext.MessageBox.ERROR
//						    });
//			    			return; 
//			    		}else if(Ext.getCmp("v_other_sys_code").getValue()==''){
//			    			Ext.MessageBox.show({
//					          	title: '提示',
//						        msg: '字段(系统编码)不能为空！',
//						        buttons: Ext.MessageBox.OK,
//						        width:200,
//						        icon: Ext.MessageBox.ERROR
//						    });
//			    			return; 
//			    		}
//			    	}
//			    	
//			    	if(Ext.getCmp("v_displayType").getValue() == 6
//			    		&& Ext.getCmp("v_get_method").getValue() ==''){
//			    		Ext.MessageBox.show({
//				          	title: '提示',
//					        msg: '字段(调用功能)不能为空！',
//					        buttons: Ext.MessageBox.OK,
//					        width:200,
//					        icon: Ext.MessageBox.ERROR
//					    });
//		    			return; 
//			    	}
			    	
			    	
			    	
			    	var resultStr ;
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';
			    			var objq = new Object();
			    			objq.type = "selIsExist";
			    			objq.parentId = pmId;
			    			objq.muneName = Ext.getCmp("v_name").getValue();
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该菜单名称已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
//							var objp = new Object();
//			    			objp.type = "selPrivIsExist";
//			    			objp.privCode = Ext.getCmp("v_priv_code").getValue();
//			 				var tmpObjp = invokeAction("/mobsystem/SelMobMuneAction", objp);
//			 				if(tmpObjp != ""){
//							  	Ext.MessageBox.show({
//						          	title: '提示',
//							        msg: '该权限名称已存在，请重新指定！',
//							        buttons: Ext.MessageBox.OK,
//							        width:200,
//							        icon: Ext.MessageBox.ERROR
//							    });
//								return;
//							}
							
							
							var objAttr;
							var paramObj = new Object();
							paramObj.parentId = pmId ;
							paramObj.muneName = Ext.getCmp("v_name").getValue() ;
							paramObj.pathName = selItem.attributes.pathName=='null'?Ext.getCmp("v_name").getValue():selItem.attributes.pathName+"/"+Ext.getCmp("v_name").getValue();
							paramObj.pathCode = selItem.attributes.pathCode
							paramObj.state = "10A";
							paramObj.displayInedx = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.isLeaf = "1";
							paramObj.type = "add";
							paramObj.isbg=Ext.getCmp("v_isbg").getValue() ;
							paramObj.iconAdr=Ext.getCmp("v_icon_adr").getValue() ;
//							paramObj.privCode = Ext.getCmp("v_priv_code").getValue() ;
							paramObj.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
							paramObj.menuType = Ext.getCmp("v_menuType").getValue();							
							
							//暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
							paramObj.toPage = 'n';
							paramObj.getMethod = 'n';
							paramObj.displayType = 'n';							
							paramObj.otherSysCode = 'n';
							paramObj.axisType = 'n';
							
							var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
			 				var objq = new Object();
			 				objq.type = "selIsExist";
			    			objq.parentId = pmId;
			    			objq.muneName = Ext.getCmp("v_name").getValue();
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj!=""){
								for(var i=0;i<tmpObj.length;i++){
									if(selItem.id!=tmpObj[i].ID){
										Ext.MessageBox.show({
								           title: '提示',
								           msg: '该菜单名称已存在，请重新指定！',
								           buttons: Ext.MessageBox.OK,
								           width:200,
								           icon: Ext.MessageBox.ERROR
								       	});
										return;
									}
								}			  
							}
			    			
			    			var paramObj = new Object();
							paramObj.muneId = selItem.id ;
						
							paramObj.muneName = Ext.getCmp("v_name").getValue() ;		
							paramObj.displayInedx = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.isbg=Ext.getCmp("v_isbg").getValue() ;
							paramObj.iconAdr=Ext.getCmp("v_icon_adr").getValue() ;
							paramObj.privCode = Ext.getCmp("v_priv_code").getValue() ;
							paramObj.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
							paramObj.menuType = Ext.getCmp("v_menuType").getValue();
							
							//暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
							paramObj.toPage = 'n';
							paramObj.getMethod = 'n';
							paramObj.displayType = 'n';							
							paramObj.otherSysCode = 'n';
							paramObj.axisType = 'n';
							
							paramObj.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);
							break ;
			    		}
			    		case "addSub":{
			    			resultStr = '新增成功！';
			 				var objq = new Object();
			 				objq.type = "selIsExist";
			    			objq.parentId = pmId;
			    			objq.muneName = Ext.getCmp("v_name").getValue();
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				
			 				if(tmpObj != ""){
							  Ext.MessageBox.show({
						           title: '提示',
						           msg: '该菜单名称已存在，请重新指定！',
						           buttons: Ext.MessageBox.OK,
						           width:200,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
							
							
			    			var objAttr;
							var paramObj = new Object();
							paramObj.parentId = pmId ;
							paramObj.muneName = Ext.getCmp("v_name").getValue() ;
							paramObj.pathName = selItem.attributes.pathName+"/"+Ext.getCmp("v_name").getValue();
							paramObj.pathCode = selItem.attributes.pathCode
							paramObj.state = "10A";
							paramObj.displayInedx = Ext.getCmp("v_displayIndex").getValue() ;
							paramObj.isLeaf = "1";
							paramObj.type = "add";
							
							paramObj.isbg=Ext.getCmp("v_isbg").getValue() ;
							paramObj.iconAdr=Ext.getCmp("v_icon_adr").getValue() ;
//							paramObj.privCode = Ext.getCmp("v_priv_code").getValue() ;
							paramObj.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
							paramObj.menuType = Ext.getCmp("v_menuType").getValue();
							
							//暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
							paramObj.toPage = 'n';
							paramObj.getMethod = 'n';
							paramObj.displayType = 'n';							
							paramObj.otherSysCode = 'n';
							paramObj.axisType = 'n';
							
							var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);
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
	        title: '菜单管理',
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
			Ext.getCmp("v_displayIndex").setValue(selItem.attributes.displayIndex);
			Ext.getCmp("v_isbg").setValue(selItem.attributes.isbg);			
			Ext.getCmp("v_icon_adr").setValue(selItem.attributes.iconAdr);
            Ext.getCmp("v_priv_code").setValue(selItem.attributes.privCode);
            Ext.getCmp("m_inteface_url").setValue(selItem.attributes.intefaceUrl);
            Ext.getCmp("v_menuType").setValue(selItem.attributes.menuType);            
            
            //暂时废弃这几个字段	modify by guo.jinjun at 2012-05-04 15:47:38
//			Ext.getCmp("v_other_sys_code").setValue(selItem.attributes.otherSysCode);
//			Ext.getCmp("v_to_page").setValue(selItem.attributes.toPage);
//			Ext.getCmp("v_get_method").setValue(selItem.attributes.getMethod);
//            Ext.getCmp("v_axis_type").setValue(selItem.attributes.axisType);
//			Ext.getCmp("v_displayType").setValue(selItem.attributes.displayType);
            
		}
		
//		if (Ext.getCmp("v_displayType").getValue() == 11) {
//			Ext.getCmp('v_axis_type').setDisabled(true);
//			Ext.getCmp('v_b_axis_type').setDisabled(true);
//		} else if (Ext.getCmp("v_displayType").getValue() == 6){
//			Ext.getCmp('v_axis_type').setDisabled(true);
//			Ext.getCmp('v_b_axis_type').setDisabled(true);
//			Ext.getCmp('v_to_page').setDisabled(true);								
//			Ext.getCmp('v_b_to_page').setDisabled(true);
//			Ext.getCmp('v_other_sys_code').setDisabled(true);
//		}
	}
}