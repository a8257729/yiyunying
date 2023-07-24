//此两个store会在mobMune.jsp中用到,因此放MenuOper,成全局的变量 	modify by guo.jinjun at 2012-05-09 14:22:36
var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'webservice接口'],[2,'servlet请求'],[3,'本地数据'],[4,'测试数据'],[5,'本地接口']]});
var staticdistypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[2,'list列表'],[3,'明细页面'],[4,'回单页面'],[5,'树型'],[6,'内置功能'],[7,'listTree树'],[8,'list对话框'],[9,'流程展显'],[10,'数据传输'],[11,'调用外系统'],[12,'统计报表'],[13,'初始页面'],[14,'选择页面'],[15,'组合页面']]});
       
 
function MenuOper(){

	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var moduleItem = Ext.getCmp('moduelTree').getSelectionModel().selNode;
       
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
                    fieldLabel: '页面名称',
                    name: 'm_form_name',
                    id: 'm_form_name',
	                allowBlank:false, 
	                blankText:"页面名称不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '页面代码',
                    name: 'm_teach_name',
                    id: 'm_teach_name',
					readOnly: true,
					emptyText: '将自动生成',
                    width: '160'
                },{  
                	xtype: 'compositefield',
					fieldLabel: '服务编码',
					id: 'm_change',
					anchor:'90%',
					items: [{
						xtype:'textfield',
	                    name: 'm_inteface_name',
	                    id: 'm_inteface_name',
		                blankText:"服务编码不能为空!",
	                    width: '160',
					 	readOnly: true
					    },{
					    xtype: 'button',
					    id : 'm_b_inteface_name',
					    text: '..',
					    handler: function(){
                            var selectValue = Ext.getCmp('m_display_type').getValue();
                            if(selectValue == '11'){
                                selOper.selectOtherApkMng('2', '0', 'm_inteface_url', 'sysCode', '2', 'm_inteface_name', 'funCode');
                            }else{
                                selOper.selectOtherSys("interfaceGrid", "1", "m_inteface_name", "mappingCode", "0", "m_inteface_url", "systemCode");
                            }
                        }
					}]
                },{
                    xtype:'radiogroup',
                    fieldLabel: '是否首页',
                    name: 'm_frist_page',
                    id: 'm_frist_page',
                	columns: 3,
                    items: [
                		{boxLabel: '是', name: 'fristPage', inputValue:'1'},
                		{boxLabel: '否', name: 'fristPage', inputValue:'0', checked: true}
                	]
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
	                blankText:"不能为空!",
                    width: '160',
                    listeners: {
                		collapse : function(c){
                			var v = c.getValue();
                			if(v == '3' || Ext.getCmp('m_display_type').getValue() == '6'){
        						Ext.getCmp('m_b_inteface_name').hide();
        					}else{
        						Ext.getCmp('m_b_inteface_name').show();
                			}
                		}
                	}
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
	                blankText:"不能为空!",
                    width: '160',
                    listeners: {
                		collapse : function(c){
                			var v = c.getValue();
                			if(v == '6'){
                				Ext.getCmp('m_b_inteface_name').hide();
                				Ext.getCmp('m_inteface_name').setReadOnly(false);                				
                				Ext.getCmp("m_change").el.parent().parent().first().dom.innerText = '功能名称:';
        					}else{
        						Ext.getCmp('m_inteface_name').setReadOnly(true);
        						Ext.getCmp('m_b_inteface_name').show();
                				Ext.getCmp("m_change").el.parent().parent().first().dom.innerText = '服务编码:';
                			}
                		}
                	}
                },{
                    xtype:'textfield',
                    fieldLabel: '关键字段',
                    name: 'm_key_name',
                    id: 'm_key_name',
                    //readOnly : true,
                    width: '160'
                }]
            },{columnWidth:.1,layout: 'form'}]},{
                    xtype:'hidden',
                    fieldLabel: '接口地址',
                    name: 'm_inteface_url',
                    id: 'm_inteface_url',
                    anchor:'95%',
					readOnly: true
                }],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	var resultStr ;


//是否初始页  有且只能有一个  add by guo.jinjun at 2012-05-04 17:54:55
	    			var objq = new Object();
	    			objq.type = "selMeuIsExit";
	    			objq.fristPage = '1';
	    			objq.muneId = moduleItem.id;
					objq.teachName = '0';
	 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
	 				if(tmpObj.length > 1 && Ext.getCmp("m_frist_page").getValue().inputValue == '1'){
					  	Ext.MessageBox.show({
				          	title: '提示',
					        msg: '只允许一个(是否初始页)的值为(是)，请重新指定！',
					        buttons: Ext.MessageBox.OK,
					        width:200,
					        icon: Ext.MessageBox.ERROR
					    });
						return;
					}
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';

							if('3' != Ext.getCmp('m_inteface_type').getValue()){ 
								if(Ext.getCmp('m_inteface_name').getValue()==null || Ext.getCmp('m_inteface_name').getValue()=='') {
									Ext.MessageBox.alert('不允许', '服务编码不能为空!');
									return;
								}
							}else{
								Ext.getCmp('m_inteface_name').setValue('');
							}
//							var objq = new Object();
//							objq.type = "selMeuIsExit";
//							objq.fristPage = '0';
//							objq.teachName = Ext.getCmp("m_teach_name").getValue();
//							var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
//							if(tmpObj != ""){
//								Ext.MessageBox.show({
//									title: '提示',
//									msg: '该下一页面已存在，请重新指定！',
//									buttons: Ext.MessageBox.OK,
//									width:200,
//									icon: Ext.MessageBox.ERROR
//								});
//								return;
//							}
			    			
			    			var inputParams = new Object();
			    			inputParams.formName = Ext.getCmp("m_form_name").getValue();
//			    			inputParams.teachName = Ext.getCmp("m_teach_name").getValue();
			    			inputParams.muneId = moduleItem.id;

			    			inputParams.displayType = Ext.getCmp("m_display_type").getValue();		    			
			    			inputParams.intefaceType = Ext.getCmp("m_inteface_type").getValue();			    			
			    			inputParams.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
			    			inputParams.intefaceName = Ext.getCmp("m_inteface_name").getValue();
			    			inputParams.keyName = Ext.getCmp("m_key_name").getValue();
							inputParams.fristPage = Ext.getCmp("m_frist_page").getValue().inputValue;
							inputParams.osType = 0;
							inputParams.osType = 0;
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
//                            inputParams.teachName = Ext.getCmp("m_teach_name").getValue();
			    			inputParams.muneId = moduleItem.id;
			    			inputParams.displayType = Ext.getCmp("m_display_type").getValue();		    			
			    			inputParams.intefaceType = Ext.getCmp("m_inteface_type").getValue();
			    			inputParams.intefaceUrl = Ext.getCmp("m_inteface_url").getValue();
			    			inputParams.intefaceName = Ext.getCmp("m_inteface_name").getValue();
			    			inputParams.keyName = Ext.getCmp("m_key_name").getValue();
							inputParams.fristPage = Ext.getCmp("m_frist_page").getValue().inputValue;
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
	        title: '页面管理',
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
//	    	Ext.getCmp("m_teach_name").setReadOnly(true);
			Ext.getCmp("m_frist_page").setValue(selItem[0].data.fristPage);

			Ext.getCmp("m_display_type").fireEvent('collapse', Ext.getCmp("m_display_type"));
		}
	}	
}