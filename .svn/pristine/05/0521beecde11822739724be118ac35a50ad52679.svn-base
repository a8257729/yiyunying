//全局变量
function TacheRuleManager(){
	//主窗口
	this.winTitle = '增加任务';
	this.initWindow = function(operStr,cataObj){
		var formPanel = this.initFormPanel(cataObj);
		
		if(operStr == 'add'){
			this.winTitle = '增加任务';
		}else if(operStr == 'mod'){
			this.winTitle = '修改任务';
			//this.initUpdate(cataObj);
		}
	
		
      	var formWin = new Ext.Window({
       		id:'pdcaWin',
            title: this.winTitle,
            closable:true,
            width:swidth*0.4,
            height:sheight*0.3,
            layout: 'border',
            plain:true,
            items: [formPanel],
            buttonAlign:'center',
            buttons: [{
	            text: '确定',
	            onClick:function(){
	            	if(operStr=='add'){
	            		manager.addCommit(cataObj);
	            	}else{
	            		manager.updateCommit(cataObj);
	            	}
	            }
	        },{
	            text: '关闭',
	            onClick:function(){
	            	Ext.getCmp('pdcaWin').close();
	            }
	        }]
        });
     	 formWin.show();
     	 
	}
	
	//执行人类型  1人员  2职位  3 组织  4系统
	this.partyTypeStore = new Ext.data.ArrayStore({
		fields: ['value','name'],
		data:[
			['1','人员'],
			['2','职位'],
			['3','组织'],
			['4','系统']
		]
	});
	
	this.initFormPanel = function(cataObj){
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
		                    fieldLabel: '规则名称',
		                    name: 'ruleName',
		                    id: 'ruleName',
			                allowBlank:false, 
			                blankText:"任务名称不能为空!",
		                    anchor:'95%',
		                    value:cataObj.ruleName
	                	},{
                            xtype: 'hidden',
                            name: 'ruleId',
                            id: 'ruleId'
                          
                       },{
                            xtype: 'hidden',
                            name: 'tacheId',
                            id: 'tacheId',
                            value:cataObj.tacheId
                          
                       }]
	            	},{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
							xtype:'combo',
			        		fieldLabel:'执行人类型',
			       			name:'partyType',
			              	id: 'partyType',
			                valueField: 'value',
			                displayField: 'name',
			                mode:'local',
			                triggerAction: 'all',
			                forceSelection: true,
							editable :false,		                
			       			allowBlank:false, 
			            	blankText:"执行人类型不能为空!",		                
			                store: this.partyTypeStore,	
		                    anchor:'90%',
		                    value:cataObj.partyType
	                	}]
		            },
	            	{
	                columnWidth:1,
	                layout: 'form',
	                items: [{
                        xtype: 'compositefield',
                        fieldLabel: '执行人名称',
                        allowBlank: false,
                        blankText: "执行人名称不能为空!",
                        items: [{
                            xtype: 'textfield',
                            name: 'partyName',
                            id: 'partyName',
                            value:cataObj.partyName,
                            anchor:'90%'
                        },
                        {
                            xtype: 'button',
                            text: '..',
                            handler: manager.selTable
                        },
                        {
                            xtype: 'hidden',
                            name: 'partyId',
                            id: 'partyId',
                            value:cataObj.partyId
                          
                        }]
	                  }]
		            }
	           ]}
	    ]});
	    return infoPage;
	}
	
	//增加任务提交
	this.addCommit = function(cataObj){
		var partyType = Ext.getCmp('partyType').getValue();
		var partyId = Ext.getCmp('partyId').getValue();
		var partyName = Ext.getCmp('partyName').getValue();
		var ruleName = Ext.getCmp('ruleName').getValue();
		var obj = new Object();
		obj.tacheId = cataObj.tacheId;
		obj.ruleName = ruleName;
		obj.partyType = partyType;
		obj.partyId = partyId;
		obj.partyName = partyName;
		
		var retValue = invokeAction('/etl/AddEtlTacheRuleAction',obj);
		if(retValue=='failure'){
				Ext.example.msg('','增加任务失败！');
		}else{
				Ext.example.msg('','增加任务成功！');
				Ext.getCmp('pdcaWin').close();
				oper.qryRule(cataObj.tacheId);
		}
	}
	//修改任务提交
	this.updateCommit = function(cataObj){
		var partyType = Ext.getCmp('partyType').getValue();
		var partyId = Ext.getCmp('partyId').getValue();
		var partyName = Ext.getCmp('partyName').getValue();
		var ruleName = Ext.getCmp('ruleName').getValue();
		
		var obj = new Object();
		obj.ruleId = cataObj.ruleId;
		obj.ruleName = ruleName;
		obj.partyType = partyType;
		obj.partyId = partyId;
		obj.partyName = partyName;
		var retValue = invokeAction('/etl/MdfEtlTacheRuleAction',obj);
		if(retValue=='failure'){
				Ext.example.msg('','修改任务失败！');
		}else{
				Ext.example.msg('','修改任务成功！');
				Ext.getCmp('pdcaWin').close();
				oper.qryRule(cataObj.tacheId);
		}
	}
	//修改初始化
	this.initUpdate = function(cataObj){
			
		//var taskObj = invokeAction('/flowmanager/QryTaskByIdAction',{taskId:taskId});
		//Ext.getCmp("ruleId").setValue(cataObj.ruleId);
		//Ext.getCmp("ruleName").setValue(cataObj.ruleName);
		//Ext.getCmp("partyType").setValue(cataObj.partyType);
		//Ext.getCmp("partyId").setValue(cataObj.partyId);
		//Ext.getCmp("partyName").setValue(cataObj.partyName);

	}

	this.selTable = function(){
		
		//执行人类型  1人员  2职位  3 组织  4系统
		var partyType = Ext.getCmp('partyType').getValue();
		if(Ext.isEmpty(partyType)){
			Ext.Msg.alert('操作提示','请先选择执行人类型');
			return;
		}
		if(partyType==1){
			var parentWin = new Object();
			var win = new ZTESOFT.StaffWin({parentWin:parentWin});
			parentWin.setRetValue = function(partyId,partyName,partyCode){
				
				Ext.getCmp("partyId").setValue(partyId);
				Ext.getCmp("partyName").setValue(partyName);
			} 
			win.show();		
		}else if(partyType==2){
			var parentWin = new Object();
			var win = new ZTESOFT.JobWin({parentWin:parentWin});
			parentWin.setRetValue = function(partyId,partyName){
				Ext.getCmp("partyId").setValue(partyId);
				Ext.getCmp("partyName").setValue(partyName);
			} 
			win.show();	
		}else if(partyType==3){
			var parentWin = new Object();
			var win = new ZTESOFT.OrgWin({parentWin:parentWin});
			parentWin.setRetValue = function(partyId,partyName,partyCode){
				Ext.getCmp("partyId").setValue(partyId);
				Ext.getCmp("partyName").setValue(partyName);
			} 
			win.show();	
		}else if(partyType==4){
			Ext.getCmp("partyId").setValue('-1');
			Ext.getCmp("partyName").setValue('SYS');
		}
		
	}	

}
