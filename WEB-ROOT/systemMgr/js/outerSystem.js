/**
 * 增加或修改外系统弹出框封装类
 * @param {} oper		操作动作  add为新增  update为更新
 * @author xsh 2010-10-27
 * 
 */
function OuterSystem(oper){
	
	this.mess = "add"==oper?"新增":"更新";
	this.outerForm = null;
	
	/**
	 * 初始化form表单
	 * @param {} record
	 */
	this.initOuterForm= function(record){
	    this.outerForm = new Ext.FormPanel({
	    	id:"operOuterForm",
	    	labelAlign: 'left',
	 		frame:true,
	 		height:Ext.getBody().getSize().height*0.55,
	 		//layout:"table",
	 		//layoutConfig:{columns:3},
        	items:[{
        		layout:'column',
        		items:[{
        			layout:'form',
        			columnWidth:.5,
        			defaultType: 'textfield',
        			items:[{
	        			fieldLabel:'系统名称',
			       		name:'sysName',
			       		allowBlank:false, 
			            blankText:"系统名称不能为空!"
        			},{
 		        		fieldLabel:'系统IP地址',
		       			name:'sysIpAddress',
		       			allowBlank:false, 
		            	blankText:"系统IP地址不能为空!"       			
        			},{
        				xtype:'combo',
		       			fieldLabel:"厂家名称",
		       			name:"companyId",
		       			id:'companyId_1',
		       			valueField: 'companyId',
                		displayField: 'companyName',
                		mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
		       			allowBlank:false, 
		            	blankText:"厂家名称不能为空!",
		            	editable :false,
		                store:new Ext.data.JsonStore({
					    	remoteSort:true,
					    	fields:['companyId','companyName'],
					    	proxy: new Ext.data.HttpProxy({
					    		url:'/MOBILE/ExtServlet?actionName=system/QryAllSysCompanyAction'
					    	})
					    }) 		
        			}]
        			
        		},{
        			layout:'form',
        			columnWidth:.5,
        			defaultType: 'textfield',
        			items:[{
		        		fieldLabel:'系统编码',
		       			name:'sysCode',
		       			allowBlank:false, 
		            	blankText:"系统编码不能为空!"        			
        			},{
						xtype:'combo',
		              	fieldLabel: '系统所属域',
		              	name: 'sysRegionId',
		              	id: 'sysRegionId_1',
		                valueField: 'sysRegionId',
		                displayField: 'sysRegionName',
		                mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
						editable :false,		                
		       			allowBlank:false, 
		            	blankText:"系统所属域不能为空!",		                
						vtype:'sysRegionChk',
		                store:sysStore       			
        			},{
	 					//fieldLabel:'系统ID',
			       		name:'sysId',
			       		id:'sysId_1',
			       		hidden:true  //隐藏       			
        			}]
        		}]
        	},{
      			xtype:'textfield',
        		fieldLabel:'系统应用路径',
       			name:'sysAppAddress',
       			allowBlank:false, 
            	blankText:"系统应用路径不能为空!",
            	vtype: 'url' ,
                vtypeText : '请输入正确的URL路径!',
                maxLength:200,
                maxLengthText:'系统应用路径的最大长度不能超过200',
       			width:400   			
	       	},{
	       		xtype:'textfield',
       			fieldLabel:"系统图标路径",
       			name:"sysIcon",
       			maxLength:90,
       			maxLengthText:'系统图标路径的最大长度不能超过90',
       			width:400   			
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"系统描述",
       			name:"sysDesc",
       			maxLength:200,
       			maxLengthText:'系统描述的最大长度不能超过200'
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"备注",
       			name:"remark",
       			maxLength:200,
       			maxLengthText:'备注的最大长度不能超过200'
	       	}],
	       	buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
	            	if(outerForm.getForm().isValid()){
		            	var outerSys = outerForm.getForm().getValues();
		            	var sysRegionId_1 = Ext.getCmp("sysRegionId_1").getValue();
		            	var companyId_1 = Ext.getCmp("companyId_1").getValue();
		            	outerSys.sysRegionId = sysRegionId_1;
		            	outerSys.companyId = companyId_1;
		            	var result = invokeAction("/outerSystem/InsertOuterSysemAction",outerSys);
		           		if("success"==result){
		           			Ext.example.msg(this.mess +"提示",this.mess+'成功');
		           			
		           			doQry();		//重新查询,更新列表
		           			win.close();
		           		}else{
		           			Ext.Msg.alert(this.mess +"提示",this.mess+'失败');
		           		}		            	
	            	}
			    }}
		    },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
		    }]
        	
	    });
	    
	    //如果记录不为空，则导入记录初始化表单
	    if(record){
	    	this.outerForm.getForm().loadRecord(record);
	    }
	}
	
	
	this.show = function(){
		
		//如果为空,则初始化一个空的表单
		if(this.outerForm == null){
			this.initOuterForm();
		}
		 
		var win = new Ext.Window({
			id:"outerWin",   
            title: this.mess + "系统",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',   
            items: [this.outerForm]               
        });
          
        win.show();   		
	}
}

