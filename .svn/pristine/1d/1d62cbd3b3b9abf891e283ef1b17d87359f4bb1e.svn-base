
var session1 = GetSession();
var v_staffId = session1.staff.staffId ;
var v_staffName = session1.staff.staffName ;
var v_username = session1.staff.userName;

function MobileFrameAppOper(){
	
	this.showMenuInfoPage = function(operator,pmName,pmId){
		
		var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
		

        
		var yesOrNo  = new Ext.data.JsonStore({ 
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/YES_OR_NO'
	        })
		});
	        
		var appOsType  = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/OS_TYPE'
	        }) ,autoLoad : true
		});
        
        var infoPage = new Ext.FormPanel({
			region: 'center',	
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth : 80,
	        items: [
	        {
            layout:'column',
            items:[{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '内部版本号',
                    name: 'versionCode',
                    id: 'a_version_code',
                    allowBlank: false,
                    anchor:'100%'
                },{
                	xtype:'combo',
                    fieldLabel: '系统类型',
                    name: 'osType',
                    id: 'a_os_type',
                    valueField: 'mcode',
                    displayField: 'mname',
                    blankText:'请选择系统类型',
                    emptyText:'选择系统类型',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    store: appOsType,
                    allowBlank: false,
                    anchor:'100%'
                }]
 			},
	        {columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '外部版本号',
                    name: 'versionName',
                    id: 'a_version_name',
                    allowBlank: false,
                    anchor:'100%'
                },{
                    xtype: 'compositefield',
					fieldLabel: '上传文件',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'file_path',
                	  	id: 'a_file_path',
					  	allowBlank:false, 
					 	readOnly: true, 
					 	disabled: true,
					 	width: '180',
                		blankText:"上传文件不能为空!"
					    },{
					    xtype: 'button',
					    id : 'a_b_download_url',
					    text: '..', 
					    handler: function(){
					    		var fileName = mobileFrameAppOper.addFile();
							}
					}]
                }]
            }]
           	},{
			    xtype:'textarea',
			    fieldLabel: '更新日志',
			    name: 'updateLog',
			    id: 'a_update_log',
			    allowBlank: false,
			    height : 60,
			    anchor:'100%'
			},{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'memo',
			    id: 'a_memo',
			    height : 40,
			    anchor:'100%'
			}],
			buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	
			    	var resultStr ;			    	
			    	switch (operator){
			    		case "add":{
			    			var objq = new Object();
							objq.type = "selVersionIsExit";
							objq.versionName = Ext.getCmp("a_version_name").getValue();
							var versionName = Ext.getCmp("a_version_name").getValue();
							var tmpObj = invokeAction("/appmanage/SelMobileFrameAppAction", objq);
							if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示(外部版本号重复)',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
			    			resultStr = '新增成功！';
			    			
			    			var element = document.createElement("a");
			    			var inputParams = new Object();
			    			inputParams.versionCode = Ext.getCmp("a_version_code").getValue();
			    			inputParams.versionName = Ext.getCmp("a_version_name").getValue();
			    			inputParams.osType = Ext.getCmp("a_os_type").getValue();
			    			inputParams.updateLog = Ext.getCmp("a_update_log").getValue();
			    			inputParams.filePath = Ext.getCmp("a_file_path").getValue();
			    			inputParams.downloadUrl = "/MOBILE/api/server/downloads/appFrame/";
			    			inputParams.memo = Ext.getCmp("a_memo").getValue();
			    			inputParams.notiFlag = "1";
							inputParams.type = "add";
							
							var retMap = invokeAction("/appmanage/MobileFrameAppAction", inputParams); 
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
			    			
			    			var inputParams = new Object();
			    			inputParams.frameAppId = selGridItem[0].data.frameAppId;
			    			inputParams.versionCode = Ext.getCmp("a_version_code").getValue();
			    			inputParams.versionName = Ext.getCmp("a_version_name").getValue();
			    			inputParams.osType = Ext.getCmp("a_os_type").getValue();
			    			inputParams.filePath = Ext.getCmp("a_file_path").getValue();
			    			//判断修改的时候 是不是上传了新的apk;如果不是新上传的就执行if
			    		//	alert(selGridItem[0].data.downloadUrl==Ext.getCmp("a_file_path").getValue());
			    			if(selGridItem[0].data.downloadUrl==Ext.getCmp("a_file_path").getValue()){
			    				inputParams.filePath = selGridItem[0].data.filePath;
			    			}
			    			inputParams.downloadUrl = "/MOBILE/api/server/downloads/appFrame/"+inputParams.frameAppId;
			    			inputParams.downloadCount = selGridItem[0].data.downloadCount;
			    			inputParams.memo = Ext.getCmp("a_memo").getValue();
			    			inputParams.updateLog = Ext.getCmp("a_update_log").getValue();
			    			var buildTimestr=selGridItem[0].data.buildTime;
							buildTimestr =  buildTimestr.replace(/-/g,"/");
							inputParams.buildTime = new Date(buildTimestr);
			    			inputParams.notiFlag = "1";
							inputParams.type = "mod";
							
							var retMap = invokeAction("/appmanage/MobileFrameAppAction", inputParams); 
			    			
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
	        title: '终端框架应用管理',
		    closable:true,
		    width:750,
		    height:350,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if (operator == "mod") {
			
		//	Ext.getCmp("a_os_type").setValue(selGridItem[0].data.osType=='1'?'Android':'iOS');
			Ext.getCmp("a_version_code").setValue(selGridItem[0].data.versionCode);
	        Ext.getCmp("a_version_name").setValue(selGridItem[0].data.versionName);
	        
	        Ext.getCmp("a_file_path").setValue(selGridItem[0].data.downloadUrl);
	      
	        Ext.getCmp("a_memo").setValue(selGridItem[0].data.memo);
	        Ext.getCmp("a_update_log").setValue(selGridItem[0].data.updateLog);
	         Ext.getCmp("a_os_type").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("a_os_type").setValue(selGridItem[0].data.osType);
            	   } 
               });
		} else if (operator == "add") {  
			
			var objq = new Object();
			objq.type = "selCurrentVersion";
//			objq.versionCode = Ext.getCmp("a_version_name").getValue();
			var tmpObj = invokeAction("/appmanage/SelMobileFrameAppAction", objq);
			if(tmpObj != ""){
				Ext.getCmp("a_version_code").setValue(parseInt(tmpObj.versionCode) + 1);
				return;
			}
		}
	}	
	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  		paramObj.frameAppId = selGridItem[0].data.frameAppId;
		paramObj.type = "del";
		var retMap = invokeAction("/appmanage/MobileFrameAppAction", paramObj);
			 				
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
            Ext.example.msg('','删除成功！');
        }, 1000);
        Ext.getCmp('menuGrid2').store.removeAll();
        Ext.getCmp('menuGrid2').store.reload();
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
			   	xtype : 'hidden',
            	id : 'staffId',
            	value : v_staffId
				},{
			   	xtype : 'hidden',
            	id : 'staffName',
            	value : v_staffName
				},{
			   	xtype : 'hidden',
            	id : 'username',
            	value : v_username
				},{					
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'appFile',
				id : 'appFile',
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
					furl = fileForm.form.findField('appFile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					if (!(type == '.apk' || type == '.ipa')) {
						alert('仅支持APK格式或IPA格式的文件');
						return;
					}
					fileForm.form.submit({
						url : '/MOBILE/api/server/upload/file',
						waitMsg : '正在上传......',
						waitTitle : '请等待',
						scope:this,
						method : 'POST',
						success : function(form, action) {
							var jsonData = Ext.util.JSON.decode(action.response.responseText);
							var success = jsonData.success;
							var fileName = jsonData.fileName;
							var filePath = jsonData.filePath;
							Ext.getCmp('a_file_path').setValue(filePath);
//							alert(filePath);
							
							form.reset();
							win.close();
						
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，仅支持APK格式或IPA格式的文件');
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
			title : "上传APP文件",
			id : 'fileWin',
			width : 400,
			height : 220,
			modal : true,
			border : false,
			layout : "fit",
			items : fileForm
		});
		
		win.show();
	
	}
}