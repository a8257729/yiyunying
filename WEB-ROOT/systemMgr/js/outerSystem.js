/**
 * ���ӻ��޸���ϵͳ�������װ��
 * @param {} oper		��������  addΪ����  updateΪ����
 * @author xsh 2010-10-27
 * 
 */
function OuterSystem(oper){
	
	this.mess = "add"==oper?"����":"����";
	this.outerForm = null;
	
	/**
	 * ��ʼ��form��
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
	        			fieldLabel:'ϵͳ����',
			       		name:'sysName',
			       		allowBlank:false, 
			            blankText:"ϵͳ���Ʋ���Ϊ��!"
        			},{
 		        		fieldLabel:'ϵͳIP��ַ',
		       			name:'sysIpAddress',
		       			allowBlank:false, 
		            	blankText:"ϵͳIP��ַ����Ϊ��!"       			
        			},{
        				xtype:'combo',
		       			fieldLabel:"��������",
		       			name:"companyId",
		       			id:'companyId_1',
		       			valueField: 'companyId',
                		displayField: 'companyName',
                		mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
		       			allowBlank:false, 
		            	blankText:"�������Ʋ���Ϊ��!",
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
		        		fieldLabel:'ϵͳ����',
		       			name:'sysCode',
		       			allowBlank:false, 
		            	blankText:"ϵͳ���벻��Ϊ��!"        			
        			},{
						xtype:'combo',
		              	fieldLabel: 'ϵͳ������',
		              	name: 'sysRegionId',
		              	id: 'sysRegionId_1',
		                valueField: 'sysRegionId',
		                displayField: 'sysRegionName',
		                mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
						editable :false,		                
		       			allowBlank:false, 
		            	blankText:"ϵͳ��������Ϊ��!",		                
						vtype:'sysRegionChk',
		                store:sysStore       			
        			},{
	 					//fieldLabel:'ϵͳID',
			       		name:'sysId',
			       		id:'sysId_1',
			       		hidden:true  //����       			
        			}]
        		}]
        	},{
      			xtype:'textfield',
        		fieldLabel:'ϵͳӦ��·��',
       			name:'sysAppAddress',
       			allowBlank:false, 
            	blankText:"ϵͳӦ��·������Ϊ��!",
            	vtype: 'url' ,
                vtypeText : '��������ȷ��URL·��!',
                maxLength:200,
                maxLengthText:'ϵͳӦ��·������󳤶Ȳ��ܳ���200',
       			width:400   			
	       	},{
	       		xtype:'textfield',
       			fieldLabel:"ϵͳͼ��·��",
       			name:"sysIcon",
       			maxLength:90,
       			maxLengthText:'ϵͳͼ��·������󳤶Ȳ��ܳ���90',
       			width:400   			
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"ϵͳ����",
       			name:"sysDesc",
       			maxLength:200,
       			maxLengthText:'ϵͳ��������󳤶Ȳ��ܳ���200'
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"��ע",
       			name:"remark",
       			maxLength:200,
       			maxLengthText:'��ע����󳤶Ȳ��ܳ���200'
	       	}],
	       	buttons:[{
	            text: 'ȷ��',
	            listeners:{"click":function(){
	            	if(outerForm.getForm().isValid()){
		            	var outerSys = outerForm.getForm().getValues();
		            	var sysRegionId_1 = Ext.getCmp("sysRegionId_1").getValue();
		            	var companyId_1 = Ext.getCmp("companyId_1").getValue();
		            	outerSys.sysRegionId = sysRegionId_1;
		            	outerSys.companyId = companyId_1;
		            	var result = invokeAction("/outerSystem/InsertOuterSysemAction",outerSys);
		           		if("success"==result){
		           			Ext.example.msg(this.mess +"��ʾ",this.mess+'�ɹ�');
		           			
		           			doQry();		//���²�ѯ,�����б�
		           			win.close();
		           		}else{
		           			Ext.Msg.alert(this.mess +"��ʾ",this.mess+'ʧ��');
		           		}		            	
	            	}
			    }}
		    },{
	            text: 'ȡ��',
	            listeners:{"click":function(){
			    	win.close();
			    }}
		    }]
        	
	    });
	    
	    //�����¼��Ϊ�գ������¼��ʼ����
	    if(record){
	    	this.outerForm.getForm().loadRecord(record);
	    }
	}
	
	
	this.show = function(){
		
		//���Ϊ��,���ʼ��һ���յı�
		if(this.outerForm == null){
			this.initOuterForm();
		}
		 
		var win = new Ext.Window({
			id:"outerWin",   
            title: this.mess + "ϵͳ",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',   
            items: [this.outerForm]               
        });
          
        win.show();   		
	}
}

