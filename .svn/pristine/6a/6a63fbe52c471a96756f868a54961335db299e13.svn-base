function PhoneVersionOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
        
        var forceUpdateStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth : 80,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '最新版本号',
                    name: 'versionNo',
                    id: 'a_version_no',
                    allowBlank: false,
                    anchor:'100%'
                },{
                    xtype: 'compositefield',
					fieldLabel: '下载地址',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'versionUrl',
                	  	id: 'a_version_url',
					  	allowBlank:false, 
					 	readOnly: true, 
					 	width: '160',
                		blankText:"下载地址不能为空!"
					    },{
					    xtype: 'button',
					    id : 'a_b_version_url',
					    text: '..',
					    handler: function(){
					    		var fileName = phoneVersionOper.addFile('a_version_url', '.apk');
//						    	Ext.getCmp('m_apk_url').setValue(fileName);
							}
					}]
                },{
                    xtype:'combo',
	                fieldLabel: '是否强制升级',
	                name: 'forceUpdate',
	                id: 'a_force_update',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: forceUpdateStore,
                    allowBlank: false,
	                anchor:'100%'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '图片版本号',
                    name: 'picVersionNo',
                    id: 'a_pic_version_no',
                    anchor:'100%'
	            },{
					xtype: 'compositefield',
					fieldLabel: '图片地址',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'picVersionUrl',
                	  	id: 'a_pic_version_url',
					 	width: '180'
					    },{
					    xtype: 'button',
					    id : 'a_b_pic_version_url',
					    text: '..',
					    handler: function(){
					    		var fileName = phoneVersionOper.addFile('a_pic_version_url', '.zip');
//						    	Ext.getCmp('m_apk_url').setValue(fileName);
							}
					}]
	         	}]
            }]
           	},{
			    xtype:'textarea',
			    fieldLabel: '更新内容',
			    name: 'comments',
			    id: 'a_comments',
			    height : 100,
			    anchor:'100%'
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
			    			var objq = new Object();
							objq.type = "selPhoneVersionIsExit";
							objq.versionNo = Ext.getCmp("a_version_no").getValue();
							var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
							if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示(最新版本号)',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
							
			    			resultStr = '新增成功！';
			    			
//			    			var inputParams = new Object();
			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.forceUpdate = Ext.getCmp("a_force_update").getValue();
//                            inputParams.versionUrl = Ext.getCmp("a_version_url").getValue();
//                            inputParams.picVersionUrl = Ext.getCmp("a_pic_version_url").getValue();                            
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobilePhoneVersionAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.versionId = selGridItem[0].data.versionId;
			    			inputParams.forceUpdate = Ext.getCmp("a_force_update").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobilePhoneVersionAction", inputParams);
			    			
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
			        Ext.getCmp('menuGrid2').store.removeAll();
			        Ext.getCmp('menuGrid2').store.reload();
			    }}
	        },{
	            text: '取消', 
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    	    
		
		menuWin = new Ext.Window({
	        title: 'APK管理',
		    closable:true,
		    width:650,
		    height:250,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){		
			//Ext.getCmp("m_apk_code").setValue(selGridItem[0].data.apkCode);
            var record = Ext.getCmp('menuGrid2').getSelectionModel().getSelected(); //获取当前选中的整条记录，前提是必须设置为行选择模式
            infoPage.getForm().loadRecord(record);
          	Ext.getCmp("a_version_no").setReadOnly(true);
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.versionId = selGridItem[0].data.versionId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobilePhoneVersionAction", paramObj);
			 				
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
        Ext.getCmp('menuGrid2').store.removeAll();
        Ext.getCmp('menuGrid2').store.reload();
    }
	
	this.addFile = function (cmp, types) {
		var fileForm = new Ext.FormPanel({
			region : 'center',
			labelWidth : 55,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			autoScroll : true,
			border : false,
			fileUpload : true,
			items : [{
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'userfile',
				id : 'userfile',
				inputType : 'file',
				allowBlank : false,
				blankText : '文件不能为空',
				height : 25,
				anchor : '98%'
			}],
			buttons : [{
				text : '上传',
				type : 'submit',
				handler : function() {
					var furl = "";
					furl = fileForm.form.findField('userfile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					
					if (type != types) {
						alert('仅支持'+ types.toString() +'格式的文件');
						return;
					}
										
					fileForm.form.submit({
						url : '../addFileServlet?fileIdsStr=' + filename +'&type=' + type,
						waitMsg : '正在上传......',
						waitTitle : '请等待',
						method : 'POST',
						success : function(form, action) {
							thisSLoc = self.location.href.split("MOBILEPJ");
							thisUPage = thisSLoc[0];
							var element = document.createElement("a");
							element.src = "/MOBILEPJ/" + action.result.fileURL;
							element.target = '_blank';
							element.innerHTML = filename;
							
							Ext.getCmp(cmp).setValue(action.result.fileName);
//							window.returnValue = action.result.fileName;	
//							win.returnValue = action.result.fileName;	
							form.reset();
							win.close();
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，仅支持'+ types.toString() +'格式的文件');
						}
					});
				}
			}, {
				text : '关闭',
				type : 'submit',
				handler : function() {
					win.close(this);
				}
			}]
		});
	
		var win = new Ext.Window( {
			title : "上传"+ types.toString() +"文件",
			id : 'fileWin',
			width : 400,
			height : 120,
			modal : true,
			border : false,
			layout : "fit",
			items : fileForm
	
		});
		
		win.show();
	
	}

}