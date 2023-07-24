function ApkVerOper(){
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
                    fieldLabel: '版本号',
                    name: 'm_apk_version_no',
                    id: 'm_apk_version_no',
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
                } ]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.5,
                layout: 'form',
                labelWidth : 80,
                items: [{
                    xtype:'textfield',
	                fieldLabel: 'APK包名',
	                name: 'm_apk_pkg',
	                id: 'm_apk_pkg',
	               allowBlank: false,
                 anchor:'100%'
                } ,{
					xtype: 'compositefield',
					fieldLabel: '上传路径',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'm_apk_url',
                	  	id: 'm_apk_url',
					  	allowBlank:false, 
					 	readOnly: true, 
					 	width: '180',
                		blankText:"上传路径!"
					    },{
					    xtype: 'button',
					    id : 'm_b_apk_url',
					    text: '..', 
					    handler: function(){
					    		var fileName = apkVerOper.addFile();
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
			    allowBlank:false, 
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
							objq.type = "selApkIsVerExit";
							objq.apkCode =  selSysGridItem[0].data.apkCode;
							objq.apkVersionNo= Ext.getCmp("m_apk_version_no").getValue();
							var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
							if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示(APK版本号)',
							        msg: '该字段值已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
							
			    			resultStr = '新增成功！';
			    			
							//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
			    			var inputParams = new Object();
			    			inputParams.sysCode = selSysGridItem[0].data.sysCode;	
			    			inputParams.apkCode = selSysGridItem[0].data.apkCode;
                inputParams.apkVersionNo = Ext.getCmp("m_apk_version_no").getValue();
                
                inputParams.apkPackage =Ext.getCmp("m_apk_pkg").getValue();
                inputParams.apkUrl = Ext.getCmp("m_apk_url").getValue();
                inputParams.forceUpdate = Ext.getCmp("m_force_update").getValue();
                //inputParams.createDate = Ext.getCmp("m_create_date").getValue();
                inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "addApkVer";
							var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", inputParams); 
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			
			    			
			    			inputParams.apkCode = selGridItem[0].data.apkCode;
			    			inputParams.apkVerState=selGridItem[0].data.apkVerState;
			    			inputParams.apkVersionNo = Ext.getCmp("m_apk_version_no").getValue();
			    			inputParams.apkPackage = Ext.getCmp("m_apk_pkg").getValue();
                inputParams.apkUrl = Ext.getCmp("m_apk_url").getValue();
                inputParams.forceUpdate = Ext.getCmp("m_force_update").getValue();
                //inputParams.createDate = Ext.getCmp("m_create_date").getValue();
                inputParams.comments = Ext.getCmp("m_comments").getValue();
			    		
							inputParams.type = "modApkVer";
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
			    }
			    }
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
			
            Ext.getCmp("m_apk_version_no").setValue(selGridItem[0].data.apkVerNo);
            Ext.getCmp("m_apk_pkg").setValue(selGridItem[0].data.apkVerPkg);
            Ext.getCmp("m_apk_url").setValue(selGridItem[0].data.apkVerUrl);
            Ext.getCmp("m_force_update").setValue(selGridItem[0].data.forceUpdate);
          
            Ext.getCmp("m_comments").setValue(selGridItem[0].data.comments);
          	Ext.getCmp("m_apk_version_no").setReadOnly(true);
		}
	}	
	
	this.showApkMenuInfoPage = function(operator,pmName,pmId){
		var selSysGridItem = Ext.getCmp('sysGrid').getSelectionModel().getSelections();
       
        var forceUpdateStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var sysStore = new Ext.data.Store({   
		      proxy: new Ext.data.HttpProxy({   
		          url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
		      }),   
		      reader: new Ext.data.JsonReader({   
		      root: 'Body',   
		      id: 'sysStore'  
		      }, [   
		          {name: 'name', mapping: 'sysName'},   
		          {name: 'value', mapping: 'sysCode'}   
		      ])   
		  	}); 
		  	sysStore.load({params:{start:0, limit:7}});

  
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
            items:[
            {
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
                },{
                	xtype:'textfield',
                    fieldLabel: 'APK名称',
                    name: 'm_apk_name',
                    id: 'm_apk_name',
                    allowBlank: false,
                    anchor:'100%'
                }]
 			},{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{xtype:'combo',
	                fieldLabel: '系统编码',
	                name: 'm_sys_code',
	                id: 'm_sys_code',
	                valueField: 'value',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: sysStore,
                    allowBlank: false,
	                anchor:'100%'
                	}]
                	}]
           	},{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'm_comments',
			    id: 'm_comments',
			    height : 100,
			    allowBlank:false, 
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
			    			
							//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
			    			var inputParams = new Object();
			    			inputParams.sysCode = Ext.getCmp("m_sys_code").getValue();
			    			inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                inputParams.apkName = Ext.getCmp("m_apk_name").getValue();
                inputParams.comments = Ext.getCmp("m_comments").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", inputParams); 
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			
			    			
			    			inputParams.apkId = selSysGridItem[0].data.apkId;
			    			inputParams.sysCode = Ext.getCmp("m_sys_code").getValue();
                inputParams.apkName = Ext.getCmp("m_apk_name").getValue();
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
			        Ext.getCmp('sysGrid').store.removeAll();
			        Ext.getCmp('sysGrid').store.reload();
			    }
			    }
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
			
            Ext.getCmp("m_sys_code").setValue(selSysGridItem[0].data.sysCode);
            Ext.getCmp("m_apk_code").setValue(selSysGridItem[0].data.apkCode);
            Ext.getCmp("m_apk_name").setValue(selSysGridItem[0].data.apkName);
            Ext.getCmp("m_comments").setValue(selSysGridItem[0].data.comments);
          	Ext.getCmp("m_apk_code").setReadOnly(true);
		}
	}
	this.selectOtherSys = function(layer, pos0, component0, value0, pos1, component1, value1, pos2, component2, value2) {
       var selNodes =  window.showModalDialog("/MOBILE/systemMgr/otherApkMng.jsp",layer,"dialogWidth=700px;dialogHeight=500px");
        if (selNodes != null && selNodes != undefined) {
        	if(pos0  != null && pos0 != undefined 
        		&&component0  != null && component0 != undefined 
        		&& value0  != null && value0 != undefined){
           		var v0 = eval("selNodes["+pos0+"][0].data."+value0+";");
        		Ext.getCmp(component0).setValue(v0);
        	}
        	if(pos1  != null && pos1 != undefined 
        		&&component1  != null && component1 != undefined 
        		&& value1  != null && value1 != undefined){
           		var v1 = eval("selNodes["+pos1+"][0].data."+value1+";");
        		Ext.getCmp(component1).setValue(v1);
        	}
        	if(pos2  != null && pos2 != undefined 
        		&&component2  != null && component2 != undefined 
        		&& value2  != null && value2 != undefined){
           		var v2 = eval("selNodes["+pos2+"][0].data."+value2+";");
        		Ext.getCmp(component2).setValue(v2);
        	}
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