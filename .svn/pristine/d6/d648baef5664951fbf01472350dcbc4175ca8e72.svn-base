<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>员工信息管理</title>
		<%@ include file="../public/common.jsp"%>		
		<!--<%@ include file="../public/style.jsp"%>
		-->
		<link rel="stylesheet" type="text/css" title="slate" href="/MOBILE/ext/resources/css/xtheme-slate.css" /> 
		<link rel="stylesheet" type="text/css" title="blue" href="/MOBILE/ext/resources/css/xtheme-blue.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="olive" href="/MOBILE/ext/resources/css/xtheme-olive.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="red" href="/MOBILE/ext/resources/css/xtheme-silverCherry.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="midnight" href="/MOBILE/ext/resources/css/xtheme-midnight.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="orange" href="/MOBILE/ext/resources/css/xtheme-orange.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="green" href="/MOBILE/ext/resources/css/xtheme-green.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="purple" href="/MOBILE/ext/resources/css/xtheme-purple.css" disabled="true"/>
		<link rel="stylesheet" type="text/css" title="access" href="/MOBILE/ext/resources/css/xtheme-access.css" disabled="true"/> 
		
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
		
	</head> 
	<style type="text/css">
		.x-window-dlg .ext-mb-download {
		    background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
		    height:46px;
		}
	</style>
	<body onContextMenu="return false;"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="hello"></div>
	</body>
</html> 

<script language="JScript">
var otherApkOper = new OtherApkOper();


Ext.onReady(function(){
		
	var btn = new Ext.Button({
		text : '显示',
		region: 'north',
		height : 100,
		listeners : {"click" : function(){
				otherApkOper.showMenuInfoPage('add');
			}
		}
	});	
	
	new Ext.Viewport({
		layout : 'border',
		items : [btn]
	});
	

	
});
function OtherApkOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		//var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
        
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
					 	//readOnly: true, 
					 	//editable : false ,
                		blankText:"图片下载地址不能为空!"
					    },{
					    xtype: 'button',
					    id : 'a_b_version_url',
					    text: '..',
					    handler: function(){
					    		var fileName = otherApkOper.addFile();
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
                    allowBlank: false,
                    anchor:'100%'
	            },{
					xtype: 'compositefield',
					fieldLabel: '图片下载地址',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'picVersionUrl',
                	  	id: 'a_pic_version_url',
					  	allowBlank:false, 
					 	//readOnly: true, 
					 	//editable : false ,
                		blankText:"图片下载地址不能为空!"
					    },{
					    xtype: 'button',
					    id : 'a_b_pic_version_url',
					    text: '..',
					    handler: function(){
					    		var fileName = otherApkOper.addFile('a_pic_version_url', '.zip');
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
/*			    			var objq = new Object();
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
							}*/
							
							
			    			resultStr = '新增成功！';
			    			
							//'versionId', 'versionNo', 'versionUrl', 'forceUpdate','publicDate','picVersionNo','picVersionUrl','comments'
//			    			var inputParams = new Object();
			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.forceUpdate = Ext.getCmp("a_force_update").getValue();
//                            inputParams.versionUrl = Ext.getCmp("a_version_url").getValue();
//                            inputParams.picVersionUrl = Ext.getCmp("a_pic_version_url").getValue();
                            
							inputParams.type = "add";
//							var retMap = invokeAction("/mobsystem/InsertOtherApkManagerAction", inputParams);
			    			var types = ".jpg";
			    			//var types[1] = ".bmp";
			    			
			    			var t = new Array();
			    			t[0] = ".jpg";
			    			t[1] = ".bmp";
			    			var tt = ['.jpg','.bmp','.png','.gif'];
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			//inputParams.apkId = selGridItem[0].data.apkId;
			    			inputParams.apkCode = Ext.getCmp("m_apk_code").getValue();
                            inputParams.apkVersionNo = Ext.getCmp("m_apk_version_no").getValue();
                            inputParams.apkPackage = Ext.getCmp("m_apk_package").getValue();
                            inputParams.apkUrl = Ext.getCmp("m_apk_url").getValue();
                            inputParams.forceUpdate = Ext.getCmp("m_force_update").getValue();
                            //inputParams.createDate = Ext.getCmp("m_create_date").getValue();
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
		
		if(operator == 'mod'){	//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'		
			//Ext.getCmp("m_apk_code").setValue(selGridItem[0].data.apkCode);
            Ext.getCmp("m_apk_version_no").setValue(selGridItem[0].data.apkVersionNo);
            Ext.getCmp("m_apk_package").setValue(selGridItem[0].data.apkPackage);
            Ext.getCmp("m_apk_url").setValue(selGridItem[0].data.apkUrl);
            Ext.getCmp("m_force_update").setValue(selGridItem[0].data.forceUpdate);
            //Ext.getCmp("m_create_date").setValue(selGridItem[0].data.createDate);
            Ext.getCmp("m_comments").setValue(selGridItem[0].data.comments);
          	Ext.getCmp("m_apk_code").setReadOnly(true);
		}
	}	
	this.moduleDel = function(btn){
    	//var selGridItem = Ext.getCmp('menuGrid2').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			//paramObj.apkId = selGridItem[0].data.apkId;
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
			title : "上传'+ types.toString() +'文件",
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
</script>