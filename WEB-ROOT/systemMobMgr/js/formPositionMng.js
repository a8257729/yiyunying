function FormPositionOper(){//3
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    //var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('posiGrid').getSelectionModel().getSelections();
		
		var staticShowStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});
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
                labelWidth : 80,
                items: [{
                	xtype: 'compositefield',
					fieldLabel: '对应环节',
					items: [{
						xtype: 'textfield',
	                	name: 'm_nextFormId_name',
	                  	id: 'm_nextFormId_name',
					   	width: '140',
					  	allowBlank:false, 
					 	readOnly: true
					    },{
					    xtype: 'button',
					    id : 'm_b_form',
					    text: '..',
					    handler: function(){selOper.selFormIdInTransfer();}//selFormIdInTransfer()原本是用于statTransferMng.js,但是在这里通用
					}]
				},{
                	xtype : 'hidden',
                	id : 'm_nextFormId',
                	value : '0'
                },{
                	xtype : 'textfield',
                	fieldLabel : '顺序',
                	id : 'm_seq_no',
                	name : 'm_seq_no',
                	width: '140',
                	allowBlank: false,
                	vtype: 'num'
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                //labelWidth : 80,
                items: [{
                	xtype : 'textfield',
                	fieldLabel : 'X轴',
                	id : 'm_x',
                	name : 'm_x',
                	width: '140',
                	allowBlank: true,
                	vtype: 'num'
                },{
                	xtype : 'textfield',
                	fieldLabel : 'Y轴',
                	id : 'm_y',
                	name : 'm_y',
                	width: '140',
                	allowBlank: true,
                	vtype: 'num'
                }]
            },{columnWidth:.1,layout: 'form'}]}],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	
			    	var resultStr;
			    	var url = "/mobsystem/OptMobileFormPositionAction";
			    	
			    	var inputParams = new Object();
			    	inputParams.formId = selItem[0].data.formId;
	    			inputParams.nextFormId = Ext.getCmp("m_nextFormId").getValue();
                    inputParams.seqNo = Ext.getCmp("m_seq_no").getValue();
                    if(Ext.getCmp("m_x").getValue())  inputParams.x = Ext.getCmp("m_x").getValue();
                    if(Ext.getCmp("m_y").getValue())  inputParams.y = Ext.getCmp("m_y").getValue();
					
			    	if('add' == operator){
			    		resultStr = '新增成功！';
			    		inputParams.optType = "I";
			    	} else if('mod' == operator) {
			    		inputParams.positionId = selGridItem[0].data.positionId;
			    		resultStr = '修改成功！';
			    		inputParams.optType = "U";
			    	}
			    	
			    	var retMap = invokeAction(url, inputParams);
			    	
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
			        Ext.getCmp('posiGrid').store.removeAll();
			        Ext.getCmp('posiGrid').store.reload();
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
		    height:180,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){			
			Ext.getCmp("m_nextFormId").setValue(selGridItem[0].data.nextFormId);
			Ext.getCmp("m_nextFormId_name").setValue(selGridItem[0].data.nextFormIdName);
            Ext.getCmp("m_seq_no").setValue(selGridItem[0].data.seqNo);
            Ext.getCmp("m_x").setValue(selGridItem[0].data.x);
            Ext.getCmp("m_y").setValue(selGridItem[0].data.y);
		}
	}	
	
	this.moduleDel = function(btn){
		if('no' == btn) {
			return;
		}
    	var selButGrid = Ext.getCmp('posiGrid').getSelectionModel().getSelections();
    	var url = "/mobsystem/OptMobileFormPositionAction";
    	
    	//alert("id is: " + selButGrid[0].data.positionId);
    	var positionIds = new Array();
    	for(var i=0;i<selButGrid.length; i++) {
    		positionIds.push(selButGrid[i].data.positionId);
    	}
    
		var paramObj = new Object();
  			paramObj.positionIds= positionIds.join(',');
			paramObj.optType = "D";
			var retMap = invokeAction(url, paramObj);
			 				
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
        Ext.getCmp('posiGrid').store.removeAll();
        Ext.getCmp('posiGrid').store.reload();
    }

} 