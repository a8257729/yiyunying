function OtherApkOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
        
        var dataStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
        var typeDataStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'菜单tab页'],[2,'其它']]});
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
                    fieldLabel: '枚举ID',
                    name: 'm_enumId',
                    id: 'm_enumId',
                    allowBlank: false,
                    anchor:'100%'
                }, {
                    xtype:'combo',
	                fieldLabel: '枚举类型',
	                name: 'm_enumType',
	                id: 'm_enumType',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: typeDataStore,
                    allowBlank: false,
	                anchor:'100%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '枚举图标',
                    name: 'm_enumImage',
                    id: 'm_enumImage',
                    anchor:'100%'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '枚举名称',
                    name: 'm_enumName',
                    id: 'm_enumName',
                    allowBlank: false,
                    anchor:'100%'
	            },{
	            
	             xtype:'combo',
	                fieldLabel: '是否显示',
	                name: 'm_isShow',
	                id: 'm_isShow',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: dataStore,
                    allowBlank: false,
	                anchor:'100%'
	         
	         	},{
                	xtype:'textfield',
                    fieldLabel: '显示顺序',
                    name: 'm_displayIndex',
                    id: 'm_displayIndex',
                    allowBlank: false,
                    anchor:'100%'
	            }]
            }]
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
//			    			var objq = new Object();
//							objq.type = "selOtherApkIsExit";
//							objq.apkCode = Ext.getCmp("m_apk_code").getValue();
//							var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
//							if(tmpObj != ""){
//							  	Ext.MessageBox.show({
//						          	title: '提示(APK编码)',
//							        msg: '该字段已存在，请重新指定！',
//							        buttons: Ext.MessageBox.OK,
//							        width:200,
//							        icon: Ext.MessageBox.ERROR
//							    });
//								return;
//							}
							
							
			    			resultStr = '新增成功！';
			    			
							//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
			    			var inputParams = new Object();
			    			inputParams.enumId = Ext.getCmp("m_enumId").getValue();
                            inputParams.enumName = Ext.getCmp("m_enumName").getValue();
                            inputParams.enumImage = Ext.getCmp("m_enumImage").getValue();
                            inputParams.enumType = Ext.getCmp("m_enumType").getValue();
                            inputParams.isShow = Ext.getCmp("m_isShow").getValue();
                            inputParams.displayIndex = Ext.getCmp("m_displayIndex").getValue();
                         
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobEnumManAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.id = selGridItem[0].data.id;
			    			inputParams.enumId = Ext.getCmp("m_enumId").getValue();
                            inputParams.enumName = Ext.getCmp("m_enumName").getValue();
                            inputParams.enumImage = Ext.getCmp("m_enumImage").getValue();
                            inputParams.enumType = Ext.getCmp("m_enumType").getValue();
                            inputParams.isShow = Ext.getCmp("m_isShow").getValue();
                            inputParams.displayIndex = Ext.getCmp("m_displayIndex").getValue();
                         
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobEnumManAction", inputParams);
			    			
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
	        title: '枚举管理',
		    closable:true,
		    width:650,
		    height:200,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){	//'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'		
			Ext.getCmp("m_enumId").setValue(selGridItem[0].data.enumId);
            Ext.getCmp("m_enumName").setValue(selGridItem[0].data.enumName);
            Ext.getCmp("m_enumImage").setValue(selGridItem[0].data.enumImage);
            Ext.getCmp("m_enumType").setValue(selGridItem[0].data.enumType);
            Ext.getCmp("m_isShow").setValue(selGridItem[0].data.isShow);
            Ext.getCmp("m_displayIndex").setValue(selGridItem[0].data.displayIndex);
		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.id = selGridItem[0].data.id;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobEnumManAction", paramObj);
			 				
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
	
	

}