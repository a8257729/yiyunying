/*
 * by fang.li   2010-10	
 * 菜单-菜单模块管理页面
 */
 
function MenuOper(){
	 this.isExistMenuOther = function(inMenuName,objAttr){
		//其它菜单目录下的所有菜单，如果菜单名有与入参相同的则直接返回false
		if(inMenuName==null || inMenuName.length<1) return false;
		inMenuName = this.Trim(inMenuName);
		
		var size  = entityTL.items.length;
		var tempMenu = null;
	
		for(var i=0;i<objAttr.length;i++){
			tempMenu = objAttr[i];
			if(tempMenu!=null && inMenuName==tempMenu.NAME){
				return true;
			}
		}
		return false;
	}
	
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var moduleItem = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		
		var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'非模态'],[1,'模态']]});
		
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
                    fieldLabel: '菜单名称',
                    name: 'm_name',
                    id: 'm_name',
	                allowBlank:false, 
	                blankText:"菜单名称不能为空!",
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '链接页面',
                    name: 'm_urlString',
                    id: 'm_urlString',
	                allowBlank:false, 
	                blankText:"链接页面不能为空!",
                    anchor:'95%'	
                }, {
                    xtype:'textfield',
                    fieldLabel: '图标文件',
                    name: 'm_iconFileName',
                    id: 'm_iconFileName',
	                allowBlank:false, 
	                blankText:"图标文件不能为空!",
                    anchor:'95%'
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    id: 'm_showModel',
                    name: 'm_showModel',
                    fieldLabel: '显示类型',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    anchor:'90%',
                    triggerAction: 'all',
                    editable : false ,
                    value: 0,
                    store: staticStore
                },{
                    xtype:'textfield',
                    fieldLabel: '显示顺序',
                    name: 'm_displayIndex',
                    id: 'm_displayIndex',
                    vtype: 'num',
	                allowBlank:false, 
	                blankText:"显示顺序不能为空!",
                    anchor:'90%'
                },{
                    xtype:'textfield',
                    fieldLabel: '权限代码',
                    name: 'm_privCode',
                    id: 'm_privCode',
	                allowBlank:false, 
	                blankText:"权限代码不能为空!",
                    anchor:'90%'
                }]
            },{columnWidth:.1,layout: 'form'}]},{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'm_comment',
			    id: 'm_comment',
			    height : 100,
			    anchor:'95%'
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
			    			
			    			//验证是否存在该权限
			    			var p_count = callRemoteFunction("com.ztesoft.mobile.system.dao.MenuDAOImpl","isExitPrivByCode",Ext.getCmp("m_privCode").getValue());
			    			if(p_count > 0){
			    				Ext.MessageBox.show({
						           title: '提示',
						           msg: '权限代码已存在，请重新指定！',
						           buttons: Ext.MessageBox.OK,
						           width:250,
						           icon: Ext.MessageBox.ERROR
						       	});
							  	return false;
			    			}
			    			
			    			var a_count = callRemoteFunction("com.ztesoft.mobile.system.dao.MenuDAOImpl","isExitActionByModule",moduleItem.id,Ext.getCmp("m_name").getValue());
			    			if(a_count > 0){
			    				Ext.MessageBox.show({
						           title: '提示',
						           msg: '菜单名称已存在，请重新指定！',
						           buttons: Ext.MessageBox.OK,
						           width:250,
						           icon: Ext.MessageBox.ERROR
						       	});
							  	return false;
			    			}
			    			
			    			var inputParams = new Array();
			    			
			    			inputParams.push({ParamName:'NAME', DataType:2, ParamValues:([Ext.getCmp("m_name").getValue()])});
			    			inputParams.push({ParamName:'SHOW_MODEL', DataType:2, ParamValues:([Ext.getCmp("m_showModel").getValue()])});
			    			inputParams.push({ParamName:'URL_STRING', DataType:2, ParamValues:([Ext.getCmp("m_urlString").getValue()])});
			    			inputParams.push({ParamName:'DISPLAY_INDEX', DataType:2, ParamValues:([Ext.getCmp("m_displayIndex").getValue()])});
			    			inputParams.push({ParamName:'ICON_FILE_NAME', DataType:2, ParamValues:([Ext.getCmp("m_iconFileName").getValue()])});
			    			
			    			inputParams.push({ParamName:'PRIV_CODE', DataType:2, ParamValues:([Ext.getCmp("m_privCode").getValue()])});
			    			inputParams.push({ParamName:'COMMENTS', DataType:2, ParamValues:([Ext.getCmp("m_comment").getValue()])});
			    			inputParams.push({ParamName:'MODULE_ID', DataType:1, ParamValues:([moduleItem.id])});
			    			inputParams.push({ParamName:'PRIV_CLASS_ID', DataType:1, ParamValues:([moduleItem.attributes.privClassId])});
			    			
			    			var ret = callRemoteFunction("com.ztesoft.iom.system.MenuManager","addMenu",inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
			    			
			    			var inputParams = new Array();
			    			inputParams.push({ParamName:'ID', DataType:1, ParamValues:([selItem[0].data.id])});
			    			inputParams.push({ParamName:'NAME', DataType:2, ParamValues:([Ext.getCmp("m_name").getValue()])});
			    			inputParams.push({ParamName:'SHOW_MODEL', DataType:2, ParamValues:([Ext.getCmp("m_showModel").getValue()])});
			    			inputParams.push({ParamName:'URL_STRING', DataType:2, ParamValues:([Ext.getCmp("m_urlString").getValue()])});
			    			inputParams.push({ParamName:'DISPLAY_INDEX', DataType:2, ParamValues:([Ext.getCmp("m_displayIndex").getValue()])});
			    			inputParams.push({ParamName:'ICON_FILE_NAME', DataType:2, ParamValues:([Ext.getCmp("m_iconFileName").getValue()])});
			    			inputParams.push({ParamName:'PRIV_CODE', DataType:2, ParamValues:([Ext.getCmp("m_privCode").getValue()])});
			    			inputParams.push({ParamName:'COMMENTS', DataType:2, ParamValues:([Ext.getCmp("m_comment").getValue()])});
			    			inputParams.push({ParamName:'MODULE_ID', DataType:1, ParamValues:([moduleItem.id])});
			    			inputParams.push({ParamName:'PRIV_CLASS_ID', DataType:1, ParamValues:([moduleItem.attributes.privClassId])});
			    			
			    			objAttr = callRemoteFunction("com.ztesoft.iom.system.MenuManager","modMenu",inputParams);
			    			
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
	        title: '菜单管理',
		    closable:true,
		    width:650,
		    height:320,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_name").setValue(selItem[0].data.name);
			Ext.getCmp("m_urlString").setValue(selItem[0].data.urlString);
			Ext.getCmp("m_iconFileName").setValue(selItem[0].data.iconFileName);
			Ext.getCmp("m_displayIndex").setValue(selItem[0].data.displayIndex);
			
			Ext.getCmp("m_privCode").setValue(selItem[0].data.privCode);
			Ext.getCmp("m_privCode").disable();
			
			Ext.getCmp("m_comment").setValue(selItem[0].data.comments);
			
	    	Ext.getCmp("m_showModel").setValue(selItem[0].data.showModel);
		}
	}	
}