function IndeOper(){
	var checkDataForm = '-1';
    var checkActionType = '-1';
    var radioIsDisplay = '1';
	var radioIsPassValue = '1';
	var radioIsRequired = 'Y';
	var radioPosition = '0';
	var radioIsShowLabel = '1';
        
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('indexGrid').getSelectionModel().getSelections();
		var selGridItem2 = Ext.getCmp('nodGrid').getSelectionModel().getSelections();
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请选择环节名称！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        
                
//        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[2,'否'],[3,'显示到最左边']]});
//        var staticdistypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[2,'否']]});
//        var showStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
//        var staticIsReqStore = new Ext.data.ArrayStore({fields:['id','value'], data: [['Y','Y'],['N','N']]});
//        var positionStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'靠左'],[1,'靠右'],[2,'居中']]});
        if(true||selGridItem2.length > 0){
        	var nodeStore =  new Ext.data.ArrayStore({fields:['id','value'], data: [['listdata','listdata'],['listdata1','listdata1'],['listdata2','listdata2'],['listdata3','listdata3'],['listdata4','listdata4'],['listdata5','listdata5'],['listdata6','listdata6'],['listdata7','listdata7']]});        	
        } else {
        	var nodeStore =  new Ext.data.ArrayStore({fields:['id','value'], data: [['listdata','listdata']]});        	
        }
        
        var filedTypeStore = new Ext.data.JsonStore({
			root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: ['typeName', 'typeLable'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelMobileFiledTypeAction'
	        }),
	        baseParams:{start:0, limit:20}
	    });
	    filedTypeStore.load();
	    filedTypeStore.on('load', function(){
	    	if(operator=='mod'){
	    		Ext.getCmp("m_filed_type").setValue(selGridItem[0].data.filedType);	
	    	} else {
	    		Ext.getCmp("m_filed_type").setValue('text');
	    	}
	    });
	    
	    var attrCodeStore = new Ext.data.JsonStore({
			root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: ['attrCode', 'attrName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelMobCompntAttributeAction'
	        }),
	        baseParams:{start:0, limit:20}
	    });
	    //attrCodeStore.load({params:{start:0, limit:20}});
	    
	    var actionCodeStore = new Ext.data.JsonStore({
			root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: ['actionName', 'actionCode', 'acitonFuntion'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelMobileActionTypeAction'
	        }),
	        baseParams:{start:0, limit:20}
	    });
	    //actionCodeStore.load({params:{start:0, limit:20}});
	    var addRecord = Ext.data.Record.create([
				{name: 'attrCode', type: 'string'},
				{name: 'attrName', type: 'string'}
			]);
		var record = new addRecord({attrCode:'-1', attrName:'无'});
		attrCodeStore.add(record);
		attrCodeStore.on('load',function(){
			var addRecord = Ext.data.Record.create([
					{name: 'attrCode', type: 'string'},
					{name: 'attrName', type: 'string'}
				]);
			var record = new addRecord({attrCode:'-1', attrName:'无'});
			attrCodeStore.add(record);
		});
	    
		var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth: 80,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype: 'compositefield',
				    fieldLabel: '字段名称',
				    items: [{
				        xtype: 'textfield',
                        name: 'm_filed_name',
                        id: 'm_filed_name',
	                	allowBlank:false,
	                	blankText:"字段名称不能为空!",
				        width: '160'
				    },{
				        xtype: 'checkbox',
						checked: false,
                    	boxLabel: '主键',
                    	name: 'm_is_keyId',
                    	id: 'm_is_keyId'
				    }]
                },{
                    xtype: 'compositefield',
				    fieldLabel: '中文名称',
				    items: [{
				        xtype: 'textfield',
                        name: 'm_file_lable',
                        id: 'm_file_lable',
	                	allowBlank:false,
	                	blankText:"中文名称不能为空!",
				        width: '160'
				    },{
				        xtype: 'checkbox',
						checked: false,
                    	boxLabel: '查询',
                    	name: 'm_is_search_field',
                    	id: 'm_is_search_field'
				    }]
                },{
                    xtype:'combo',
                    fieldLabel: '所属节点',
                    name: 'm_node_name',
                    id: 'm_node_name',
	                valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false,
                    value: 'listdata',
                    store: nodeStore,
                    width: '160'
                },{
                	xtype: 'checkboxgroup',
                	id : 'm_action_type',
                	name: 'm_action_type',
                	columns: 3,
                	items: [
                		{boxLabel: '打开页面', name: 'v_action_event', inputValue:'0'},
                		{boxLabel: '弹出事件', name: 'v_action_event', inputValue:'1'}
                	],
                	listeners: {
                		'change' : function(chkbxGrp, chkArr){
                			if(chkArr.length == 0){                				
                				checkActionType = '-1';
                				var addRecord = Ext.data.Record.create([
									{name: 'actionCode', type: 'string'},
									{name: 'actionName', type: 'string'}
								]);
        						var record = new addRecord({actionCode:'-1', actionName:'无'});
        						actionCodeStore.add(record);
                			} else if(chkArr.length == 1){
                				//Ext.getCmp("m_action_code").setValue('');
                				if(checkActionType == '-1'){
                					actionCodeStore.removeAt(actionCodeStore.getCount()-1);
                				}
                				checkActionType = chkArr[0].inputValue;                				
                			} else {
                				if(checkActionType == '0'){
                					chkbxGrp.items.itemAt(0).setValue(false);
                				} else if(checkActionType == '1'){
                					chkbxGrp.items.itemAt(1).setValue(false);
                				}   
                			}
                		}
                	}
                },{
                	xtype: 'checkboxgroup',
                	id : 'm_data_form',
                	name: 'm_data_form',
                	columns: 3,
                	items: [
                		{boxLabel: '业务', name: 'v_data_form', inputValue:'0'},
                		{boxLabel: '静态', name: 'v_data_form', inputValue:'1'}
                	],
                	listeners: {
                		'change' : function(chkbxGrp, chkArr){
                			if(chkArr.length == 0){
                				//Ext.getCmp("m_select_data").setValue('-1');
                				checkDataForm = '-1';
                			} else if(chkArr.length == 1){
                				//Ext.getCmp("m_select_data").setValue('');
                				checkDataForm = chkArr[0].inputValue;
                			} else {
                				if(checkDataForm == '0'){
                					chkbxGrp.items.itemAt(0).setValue(false);
                				} else if(checkDataForm == '1'){
                					chkbxGrp.items.itemAt(1).setValue(false);
                				}   
                				//Ext.getCmp("m_select_data").setValue('');
                			}
                		}
                	}
                },{
                    xtype:'radiogroup',
                    fieldLabel: '是否显示',
                    name: 'm_is_display',
                    id: 'm_is_display',
                	columns: 3,
                    items: [
                		{boxLabel: '是', name: 'v_is_display', inputValue:'1', checked: true},
                		{boxLabel: '否', name: 'v_is_display', inputValue:'0'}
                	],
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioIsDisplay = RadioChecked.inputValue;
                		}
                	}
                },{
                    xtype:'radiogroup',
                    fieldLabel: '是否传值',
                    name: 'm_pass_value',
                    id: 'm_pass_value',
                	columns: 3,
                    items: [
                		{boxLabel: '是', name: 'v_pass_value', inputValue:'1', checked: true},
                		{boxLabel: '否', name: 'v_pass_value', inputValue:'0'}
                	],
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioIsPassValue = RadioChecked.inputValue;
                		}
                	}
                },{
                    xtype:'radiogroup',
                    fieldLabel: '是否必填',
                    name: 'm_is_required',
                    id: 'm_is_required',
                	columns: 3,
                    items: [
                		{boxLabel: '是', name: 'v_is_required', inputValue:'Y', checked: true},
                		{boxLabel: '否', name: 'v_is_required', inputValue:'N'}
                	],
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioIsRequired = RadioChecked.inputValue;
                		}
                	}
                },{
                    xtype:'radiogroup',
                    fieldLabel: '显示位置',
                    name: 'm_position',
                    id: 'm_position',
                	columns: 3,
                    items: [
                		{boxLabel: '左', name: 'v_position', inputValue:'0', checked: true},
                		{boxLabel: '中', name: 'v_position', inputValue:'2'},
                		{boxLabel: '右', name: 'v_position', inputValue:'1'}
                	],
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioPosition = RadioChecked.inputValue;
                		}
                	}
                },{
                    xtype:'radiogroup',
                    fieldLabel: '是否显示中文',
                    name: 'm_is_show_label',
                    id: 'm_is_show_label',
                	columns: 3,
                    items: [
                		{boxLabel: '是', name: 'v_is_show_label', inputValue:'1', checked: true},
                		{boxLabel: '否', name: 'v_is_show_label', inputValue:'0'}
                	],
                	listeners: {
                		'change' : function(RadioGroup, RadioChecked){
                			radioIsShowLabel = RadioChecked.inputValue;
                		}
                	}
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'combo',
                    fieldLabel: '组件类型',
                    name: 'm_filed_type',
                    id: 'm_filed_type',
	                valueField: 'typeName',
                    displayField: 'typeLable',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: filedTypeStore,
                    allowBlank:false, 
	                blankText:"字段类型不能为空!",
                    width: '160',
                    listeners: {
                		collapse : function(c){
                			attrCodeStore.removeAll();
                			actionCodeStore.removeAll();
                			attrCodeStore.load({params:{start:0, limit:20, typeName: c.getValue()}});
                			actionCodeStore.load({params:{start:0, limit:20, typeName: c.getValue()}});
                		}
                    }
                },{
                    xtype:'combo',
                    fieldLabel: '组件属性编码',
                    name: 'm_attr_code',
                    id: 'm_attr_code',
	                valueField: 'attrCode',
                    displayField: 'attrName',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false,
                    value: '',
                    store: attrCodeStore,
                    width: '160'
                },{
                    xtype:'combo',
                    fieldLabel: '事件编码',
                    name: 'm_action_code',
                    id: 'm_action_code',
	                valueField: 'actionCode',
                    displayField: 'actionName',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '',
                    store: actionCodeStore,
                    width: '160'
                },{
                    xtype: 'compositefield',
				    fieldLabel: '下一环节',
				    items: [{
				        xtype: 'textfield',
                        name: 'm_checked_data',
                        id: 'm_checked_data',
				        width: '160',
				        readOnly: true
				    },{
				        xtype: 'button',
				        text: '..',
				        handler: function(){
				    		if(checkActionType == '-1'){
				    			//selOper.selectTeachName2();
				    		} else if(checkActionType == '0'){
				    			selOper.selectTeachName2('m_checked_data');
				    		} else if(checkActionType == '1'){
				    			selOper.selectOtherSys('interfaceGrid', '1', 'm_checked_data', 'mappingCode');
				    		}				    		
				        }
				    }]
                },{
                    xtype: 'compositefield',
                    fieldLabel: '选择数据',
				    items: [{
					    xtype: 'textfield',
	                    name: 'm_select_data',
	                    id: 'm_select_data',
				        width: '160'
				    },{
				        xtype: 'button',
				        text: '..',
				        handler: function(){
				    		if(checkDataForm == '0'){
				    			//selOper.selectOtherSys('interfaceGrid', '1', 'm_select_data', 'mappingCode');
				    		    //selOper.selectOtherSys('interfaceGrid', '1', 'm_select_data', 'mappingCode');
				    		    selOper.selectTeachName2('m_select_data');
				    		} else if(checkDataForm == '1'){
				    			alert('开发中~~')
				    		}
				        }
				    }]
                },{
                    xtype:'textfield',
                    fieldLabel: '显示顺序',
                    name: 'm_seq_id',
                    id: 'm_seq_id',
                    allowBlank:false, 
                    vtype: 'num' ,
                    blankText:"显示顺序不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '同级顺序',
                    name: 'm_index_id',
                    id: 'm_index_id',
                    allowBlank:false, 
                    vtype: 'num' ,
                    blankText:"同级顺序不能为空!",
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '默认值',
                    name: 'm_default_value',
                    id: 'm_default_value',
                    width: '160'
                },{
			        xtype: 'textarea',
                    fieldLabel: '脚本代码',
                    name: 'm_action_event',
                    id: 'm_action_event',
			        width: '160',
			        height: '70'
			    }]
            },{columnWidth:.1,layout: 'form'}]}],
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
			    			
			    			var objq = new Object();
			    			objq.type = "selFiledIsExit";
			    			objq.filedName = Ext.getCmp("m_filed_name").getValue();
			    			objq.formId = selItem[0].data.formId;
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
			    			var inputParams = new Object();
			    			inputParams.filedName = Ext.getCmp("m_filed_name").getValue();
			    			inputParams.filedLable =Ext.getCmp("m_file_lable").getValue();
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.filedType = Ext.getCmp("m_filed_type").getValue();		    			
			    			inputParams.checkedData = Ext.getCmp("m_checked_data").getValue();
			    			inputParams.selectData = Ext.getCmp("m_select_data").getValue();			    			
			    			inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
			    			inputParams.indexId = Ext.getCmp("m_index_id").getValue();
			    			inputParams.attrCode = Ext.getCmp("m_attr_code").getValue();
			    			inputParams.actionCode = Ext.getCmp("m_action_code").getValue();
			    			inputParams.isKeyid = Ext.getCmp("m_is_keyId").getValue()?'1':'0';
			    			inputParams.isSearchField = Ext.getCmp("m_is_search_field").getValue()?'1':'0';
			    			inputParams.defaultValue = Ext.getCmp("m_default_value").getValue();
			    			inputParams.nodeName = Ext.getCmp("m_node_name").getValue();
			    			inputParams.actionType = checkActionType;
			    			inputParams.dataForm = checkDataForm;			    			
			    			inputParams.isShowLabel = radioIsShowLabel;
			    			inputParams.isDisplay = radioIsDisplay;
			    			inputParams.isPassValue = radioIsPassValue;
			    			inputParams.isRequired = radioIsRequired;	
			    			inputParams.position = radioPosition;
			    			inputParams.actionEvent = Ext.getCmp("m_action_event").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertFiledInfoAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.filedId = selGridItem[0].data.filedId;
                            inputParams.filedName = Ext.getCmp("m_filed_name").getValue();
			    			inputParams.filedLable =Ext.getCmp("m_file_lable").getValue();
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.filedType = Ext.getCmp("m_filed_type").getValue();
			    			inputParams.checkedData = Ext.getCmp("m_checked_data").getValue();
			    			inputParams.selectData = Ext.getCmp("m_select_data").getValue();
			    			inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
			    			inputParams.indexId = Ext.getCmp("m_index_id").getValue();
			    			inputParams.attrCode = Ext.getCmp("m_attr_code").getValue();
			    			inputParams.actionCode = Ext.getCmp("m_action_code").getValue();
			    			inputParams.isKeyid = Ext.getCmp("m_is_keyId").getValue()?'1':'0';
			    			inputParams.isSearchField = Ext.getCmp("m_is_search_field").getValue()?'1':'0';
			    			inputParams.defaultValue = Ext.getCmp("m_default_value").getValue();
			    			inputParams.nodeName = Ext.getCmp("m_node_name").getValue();
			    			inputParams.actionType = checkActionType;
			    			inputParams.dataForm = checkDataForm;		    			
			    			inputParams.isShowLabel = radioIsShowLabel;
			    			inputParams.isDisplay = radioIsDisplay;
			    			inputParams.isPassValue = radioIsPassValue;
			    			inputParams.isRequired = radioIsRequired;	
			    			inputParams.position = radioPosition;
			    			inputParams.actionEvent = Ext.getCmp("m_action_event").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertFiledInfoAction", inputParams);
							
			    			
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
			        Ext.getCmp('indexGrid').store.removeAll();
			        Ext.getCmp('indexGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
		
		menuWin = new Ext.Window({
	        title: '字段配置',
		    closable:true,
		    width:650,
		    height:400,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_filed_name").setValue(selGridItem[0].data.filedName);
			Ext.getCmp("m_file_lable").setValue(selGridItem[0].data.filedLable);				
			//Ext.getCmp("m_filed_type").setValue(selGridItem[0].data.filedType);	
			Ext.getCmp("m_is_display").setValue(selGridItem[0].data.isDisplay);		
	    	Ext.getCmp("m_pass_value").setValue(selGridItem[0].data.isPassValue);
	    	Ext.getCmp("m_is_required").setValue(selGridItem[0].data.isRequired);
	    	Ext.getCmp("m_checked_data").setValue(selGridItem[0].data.checkedData);
	    	Ext.getCmp("m_select_data").setValue(selGridItem[0].data.selectData);	    	
	    	Ext.getCmp("m_seq_id").setValue(selGridItem[0].data.seqId);
	    	Ext.getCmp("m_index_id").setValue(selGridItem[0].data.indexId);
	    	
	    	Ext.getCmp("m_attr_code").setValue(selGridItem[0].data.attrCode);
	    	Ext.getCmp("m_action_code").setValue(selGridItem[0].data.actionCode);
	    	Ext.getCmp("m_is_keyId").setValue(selGridItem[0].data.isKeyid=='1'?true:false);
	    	Ext.getCmp("m_is_search_field").setValue(selGridItem[0].data.isSearchField=='1'?true:false);
	    	Ext.getCmp("m_position").setValue(selGridItem[0].data.position);
	    	Ext.getCmp("m_default_value").setValue(selGridItem[0].data.defaultValue);
	    	Ext.getCmp("m_is_show_label").setValue(selGridItem[0].data.isShowLabel);
	    	Ext.getCmp("m_node_name").setValue(selGridItem[0].data.nodeName);
	    	Ext.getCmp("m_action_event").setValue(selGridItem[0].data.actionEvent);
	    	Ext.getCmp("m_action_type").setValue(selGridItem[0].data.actionType);
	    	Ext.getCmp("m_data_form").setValue(selGridItem[0].data.dataForm);
	    	checkActionType = selGridItem[0].data.actionType;
	    	checkDataForm = selGridItem[0].data.dataForm;
	    	radioIsDisplay = selGridItem[0].data.isDisplay;
			radioIsPassValue = selGridItem[0].data.isPassValue;
			radioIsRequired = selGridItem[0].data.isRequired;
			radioPosition = selGridItem[0].data.position;
			radioIsShowLabel = selGridItem[0].data.isShowLabel;
	    		    	
	    	actionCodeStore.load({params:{start:0, limit:20, typeName:selGridItem[0].data.filedType}});
	    	attrCodeStore.load({params:{start:0, limit:20, typeName:selGridItem[0].data.filedType}});
	    	
		}
	}	
	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selIndexGrid = Ext.getCmp('indexGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.filedId = selIndexGrid[0].data.filedId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertFiledInfoAction", paramObj);
			 				
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
        Ext.getCmp('indexGrid').store.removeAll();
        Ext.getCmp('indexGrid').store.reload();
    }
	
}