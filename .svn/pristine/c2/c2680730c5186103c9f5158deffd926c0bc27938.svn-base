function OtherApkOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selSysGridItem = Ext.getCmp('sysGrid').getSelectionModel().getSelections();
        
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
                    fieldLabel: 'APK编码',
                    name: 'm_apk_code',
                    id: 'm_apk_code',
                    allowBlank: false,
                    anchor:'100%'
                }, {
                    xtype:'combo',
	                fieldLabel: '是否强制升级',
	                name: 'm_force_update',
	                id: 'm_force_update',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: forceUpdateStore,
                    allowBlank: false,
	                anchor:'100%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '版本号',
                    name: 'm_apk_version_no',
                    id: 'm_apk_version_no',
                    allowBlank: false,
                    anchor:'100%'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: 'APK包名',
                    name: 'm_apk_package',
                    id: 'm_apk_package',
                    allowBlank: false,
                    anchor:'100%'
	            },{
                	xtype:'textfield',
                    fieldLabel: '开放类名',
                    name: 'm_open_class',
                    id: 'm_open_class',
                    allowBlank: false,
                    anchor:'100%'
	            },{
					xtype: 'compositefield',
					fieldLabel: 'apk名称',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'm_apk_url',
                	  	id: 'm_apk_url',
					  	allowBlank:false, 
					 	readOnly: true, 
					 	width: '180',
                		blankText:"APK名称不能为空!"
					    },{
					    xtype: 'button',
					    id : 'm_b_apk_url',
					    text: '..', 
					    handler: function(){
					    		var fileName = otherApkOper.addFile();
//						    	Ext.getCmp('m_apk_url').setValue(fileName);
							}
					}]
	         	}]
            }]
           	},{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'm_comments',
			    id: 'm_comments',
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
							objq.type = "selOtherApkIsExit";
							objq.apkCode = Ext.getCmp("m_apk_code").getValue();
							var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
							if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示(APK编码)',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
							
			    			resultStr = '新增成功！';
			    			
			    			var inputParams = new Object();
			    			inputParams.sysCode = selSysGridItem[0].data.sysCode;	
			    			inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                            inputParams.apkVersionNo = Ext.getCmp("m_apk_version_no").getValue();
                            inputParams.apkPackage = Ext.getCmp("m_apk_package").getValue();
                            inputParams.apkUrl = Ext.getCmp("m_apk_url").getValue();
                            inputParams.forceUpdate = Ext.getCmp("m_force_update").getValue();
                            //inputParams.createDate = Ext.getCmp("m_create_date").getValue();
                            inputParams.openClass = Ext.getCmp("m_open_class").getValue();
                            inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "addApkVer";
							var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", inputParams); 
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.apkId = selGridItem[0].data.apkId;
			    			inputParams.sysCode = selSysGridItem[0].data.sysCode;			    			
			    			inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                            inputParams.apkVersionNo = Ext.getCmp("m_apk_version_no").getValue();
                            inputParams.apkPackage = Ext.getCmp("m_apk_package").getValue();
                            inputParams.apkUrl = Ext.getCmp("m_apk_url").getValue();
                            inputParams.forceUpdate = Ext.getCmp("m_force_update").getValue();
                            //inputParams.createDate = Ext.getCmp("m_create_date").getValue();
                            inputParams.openClass = Ext.getCmp("m_open_class").getValue();
                            inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", inputParams);
			    			
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
	        title: 'APK管理',
		    closable:true,
		    width:650,
		    height:250,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){	//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'		
			Ext.getCmp("m_apk_code").setValue(selGridItem[0].data.apkCode);
            Ext.getCmp("m_apk_version_no").setValue(selGridItem[0].data.apkVersionNo);
            Ext.getCmp("m_apk_package").setValue(selGridItem[0].data.apkPackage);
            Ext.getCmp("m_apk_url").setValue(selGridItem[0].data.apkUrl);
            Ext.getCmp("m_force_update").setValue(selGridItem[0].data.forceUpdate);
            //Ext.getCmp("m_create_date").setValue(selGridItem[0].data.createDate);
            Ext.getCmp("m_open_class").setValue(selGridItem[0].data.openClass);
            Ext.getCmp("m_comments").setValue(selGridItem[0].data.comments);
          	Ext.getCmp("m_apk_code").setReadOnly(true);
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  		paramObj.apkId = selGridItem[0].data.apkId;
		paramObj.type = "del";
		var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", paramObj);
			 				
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
        Ext.getCmp('menuGrid').store.removeAll();
        Ext.getCmp('menuGrid').store.reload();
    }
	
	this.addFile = function () {
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
					if (type != '.apk') {
						alert('仅支持apk格式的文件');
						return;
					}
					fileForm.form.submit({
						url : '../addFileServlet?fileIdsStr=' + filename+'&type=.apk',
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
							
							Ext.getCmp('m_apk_url').setValue(action.result.fileName);
//							window.returnValue = action.result.fileName;	
//							win.returnValue = action.result.fileName;	
							form.reset();
							win.close();
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，仅支持apk格式的文件');
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
			title : "上传apk文件",
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