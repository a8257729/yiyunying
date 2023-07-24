function PrivNodeOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('nodGrid').getSelectionModel().getSelections();
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
        
        var nodeStore =  new Ext.data.ArrayStore({fields:['id','value'], data: [['listdata','listdata'],['listdata1','listdata1'],['listdata2','listdata2'],['listdata3','listdata3'],['listdata4','listdata4'],['listdata5','listdata5'],['listdata6','listdata6'],['listdata7','listdata7']]});        	
        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'否'],[2,'是']]});
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
                    xtype:'combo',
                    fieldLabel: '节点名称',
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
                    xtype:'textfield',
                    fieldLabel: '中文名称',
                    name: 'm_name_label',
                    id: 'm_name_label',
	                allowBlank:false, 
	                blankText:"中文名称不能为空!",
                    width: '160'
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'combo',
                    fieldLabel: '是否父节点',
                    name: 'm_is_leaf',
                    id: 'm_is_leaf',
                   valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticStore,
                    width: '160'
                },{
                    xtype:'textfield',
                    fieldLabel: '顺序',
                    name: 'm_seq_id',
                    id: 'm_seq_id',
	                allowBlank:false, 
	                blankText:"顺序不能为空!",
                    width: '160'
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
			    			objq.type = "selNodeIsExit";
			    			objq.nodeName = Ext.getCmp("m_node_name").getValue();
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
			    			inputParams.nodeName = Ext.getCmp("m_node_name").getValue();
			    			inputParams.formId = selItem[0].data.formId
			    			inputParams.isLeaf = Ext.getCmp("m_is_leaf").getValue();
                            inputParams.nodeLabel = Ext.getCmp("m_name_label").getValue();
                            inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertFiledNodeAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.nodeId = selGridItem[0].data.nodeId;
			    			inputParams.nodeName = Ext.getCmp("m_node_name").getValue();
                            inputParams.isLeaf = Ext.getCmp("m_is_leaf").getValue();
                            inputParams.nodeLabel = Ext.getCmp("m_name_label").getValue();
                            inputParams.seqId = Ext.getCmp("m_seq_id").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertFiledNodeAction", inputParams);
			    			
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
			        Ext.getCmp('nodGrid').store.removeAll();
			        Ext.getCmp('nodGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
		
		menuWin = new Ext.Window({
	        title: '流转管理',
		    closable:true,
		    width:650,
		    height:220,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("m_node_name").setValue(selGridItem[0].data.nodeName);
			Ext.getCmp("m_name_label").setValue(selGridItem[0].data.nodeLabel);
			Ext.getCmp("m_is_leaf").setValue(selGridItem[0].data.isLeaf);
			Ext.getCmp("m_seq_id").setValue(selGridItem[0].data.seqId);

		}
	}	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selButGrid = Ext.getCmp('nodGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.nodeId = selButGrid[0].data.nodeId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertFiledNodeAction", paramObj);
			 				
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
        Ext.getCmp('nodGrid').store.removeAll();
        Ext.getCmp('nodGrid').store.reload();
    }
}