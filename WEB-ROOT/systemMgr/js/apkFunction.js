function ApkFunctionOper(){
	
	var radioIsInit =  new Object();
	
	this.showMenuInfoPage = function(operator,pmName,pmId){
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var myselItem = Ext.getCmp('funGrid').getSelectionModel().getSelections();

		if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择菜单目录项！',
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
	        labelWidth: 80,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '功能编码',
                    name: 'funCode',
                    id: 'm_funCode',
                    readOnly: true,
                    emptyText: '将自动生成',
                    width: '160'
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '功能名称',
                    name: 'funName',
                    id: 'm_funName',
	                allowBlank:false, 
	                blankText:"不能为空!",
                    width: '160'
                }]
            },{columnWidth:.1,layout: 'form'}]
            },{
            	xtype:'textfield',
                fieldLabel: '功能类名',
                name: 'funClass',
                id: 'm_funClass',
                allowBlank:false, 
                blankText:"不能为空!",
                width: '500'
            }],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	var resultStr ;
			    	
//			    	var objq = new Object();
//	    			objq.type = "selIsUnique";
//	    			objq.fristPage = '1';  
//	    			objq.muneId = selNode.id; 
//	    			objq.pageType = '0';
//	 				var tmpObj = invokeAction("/mobsystem/InsertMobileApkFunctionAction", objq);
//	 				if(tmpObj.totalRecords > 1 && Ext.getCmp("m_frist_page").getValue().inputValue == '1'){
//					  	Ext.MessageBox.show({
//				          	title: '提示',
//					        msg: '只能允许一个(首页)存在，请重新指定！',
//					        buttons: Ext.MessageBox.OK,
//					        width:200,
//					        icon: Ext.MessageBox.ERROR
//					    });
//						return;
//					}			    	
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';

			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.apkCode = selItem[0].data.apkCode;
			    			inputParams.verId = selItem[0].data.verId;
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertMobileApkFunctionAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = infoPage.getForm().getValues();
			    			inputParams.funId = myselItem[0].data.funId;
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobileApkFunctionAction", inputParams);
							
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
			        Ext.getCmp('funGrid').store.removeAll();
			        Ext.getCmp('funGrid').store.reload();
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
		    height:230,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			infoPage.getForm().loadRecord(myselItem[0]);
		}
	}
	
	this.moduleDel = function(btn){
		if(btn == 'no'){
    		return ;
    	}
    	var selIndexGrid = Ext.getCmp('funGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.funId = selIndexGrid[0].data.funId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobileApkFunctionAction", paramObj);
			 				
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
            Ext.example.msg('','删除页面成功！');
        }, 1000);
        Ext.getCmp('funGrid').store.removeAll();
        Ext.getCmp('funGrid').store.reload();
    }
	
}