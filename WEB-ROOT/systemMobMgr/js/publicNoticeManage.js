/*
 * 
 * 菜单-公告消息管理页面
 */

function PublicOper(){
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('publicGrid').getSelectionModel().getSelections();
      //  var publicStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'人员'],[2,'部门']]});  
          var publicStore = new Ext.data.JsonStore({
			id: 'publicStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/OBJECT_TYPE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
          var statusStore = new Ext.data.JsonStore({
			id: 'publicStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/NOTIFICATION_STATUS'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
          var typeStore = new Ext.data.JsonStore({
			id: 'publicStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/NOTIFICATION_TYPE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
          var priorityStore = new Ext.data.JsonStore({
			id: 'publicStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/NOTIFICATION_PRIORITY'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
        var infoPage = new Ext.FormPanel({
            region: 'center',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,
            height:150,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 90,
            items: [{
                layout:'column',
                items:[{
                    columnWidth:.45,
                    layout: 'form',
                    items: [{
                        xtype:'textfield',
                        fieldLabel: '公告标题',
                        name: 'v_title',
                        id: 'v_title',
                        allowBlank:false, 
                        blankText:"公告标题不能为空!",
                        anchor:'95%'    
                    },{
                        xtype:'combo',
                        fieldLabel: '发布对象类型',
                        name: 'v_publicType',
                        id: 'v_publicType',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: publicStore,
                        allowBlank: true,
                        anchor:'95%' 
                    },{   xtype:'combo',
                        fieldLabel: '优先级',
                        name: 'v_priority',
                        id: 'v_priority',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: priorityStore,
                        allowBlank: true,
                        anchor:'95%' 
                    },{   xtype:'combo',
                        fieldLabel: '公共类型',
                        name: 'v_noticeType',
                        id: 'v_noticeType',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: typeStore,
                        allowBlank: true,
                        anchor:'95%' 
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.45,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '公告发布人Id',
                        name: 'v_createStaffId',
                        id: 'v_createStaffId',
                        readOnly: true,
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '公告发布人',
                        name: 'v_createStaffName',
                        id: 'v_createStaffName',
                        readOnly: true,
                        anchor:'90%'    
                    },{                             
                            xtype: 'compositefield',
                            fieldLabel: '公告发布对象',
                            msgTarget: 'side',
                            items: [{
                                xtype: 'textfield',
                                name: 'v_publicObjectName',
                                id: 'v_publicObjectName',
                                anchor:'95%',
                                //width: '100',
                                readOnly: true,
                                fieldLabel: 'Start'
                            },{
                                xtype:'button',
                                name: 'v_up',
                                id: 'v_up',
                                columnWidth:0.05,
                                text:'',
                                handler:function(){
                                    if (Ext.getCmp("v_publicType").getValue()+"" ==""){
                                        Ext.MessageBox.show({
                                            title: '提示',
                                            msg: '请选择发布对象类型！',
                                            buttons: Ext.MessageBox.OK,
                                            width:200,
                                            icon: Ext.MessageBox.ERROR
                                        });
                                        return;
                                    }
                                    var selNodes;
                                    if (Ext.getCmp("v_publicType").getValue() =="1"){
                                        selNodes =  OpenShowDlg("seleOrg.jsp?sel_type=2", 500, 550, null);
                                       if(selNodes){
                                           var orgName = '';
                                           var orgId = '';
                                           if (selNodes != null && selNodes != undefined) {
                                              Ext.each(selNodes,
                                              function(node) {
                                                   if (orgName.length > 0) {
                                                      orgName += ',';
                                                      orgId += ',';
                                                   }
                                                   orgName += node.text;
                                                   orgId += node.id;
                                              });

                                           }
                                           Ext.getCmp("v_publicObject").setValue(orgId);
                                           Ext.getCmp("v_publicObjectName").setValue(orgName);                            
                                       }
                                    }else if (Ext.getCmp("v_publicType").getValue() =="0"){
                                        selNodes =  OpenShowDlg("seleStaff.jsp?sel_type=2", 500, 550, null);
                                       if(selNodes){
                                           Ext.getCmp("v_publicObject").setValue(selNodes.staffId);
                                           Ext.getCmp("v_publicObjectName").setValue(selNodes.staffName);                           
                                       }
                                    }
                                    
                                }
                            }]    
                    },{     
                        xtype:'hidden',
                        fieldLabel: '公告发布对象id',
                        name: 'v_publicObject',
                        id: 'v_publicObject',
                        readOnly: false,
                        anchor:'90%'   
                    },{   xtype:'combo',
                        fieldLabel: '公共状态',
                        name: 'v_status',
                        id: 'v_status',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: statusStore,
                        allowBlank: true,
                        anchor:'95%' 
                    }]
               },{columnWidth:.1,layout: 'form'}]},{
                   xtype:'textarea',
                   fieldLabel: '公告内容',
                   name: 'v_content',
                   id: 'v_content',
                   height : 120,
                   blankText:"公告内容不能为空!",
                   value: '',
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
                          if( Ext.getCmp("v_content").getValue()==''){
                            	
                            	  Ext.MessageBox.alert('提示', '公告内容不能为空！',null);
                            	  return ;
                            }
                            var paramObj = new Object();
                            paramObj.title = Ext.getCmp("v_title").getValue() ;
                            paramObj.content = Ext.getCmp("v_content").getValue() ;
                            paramObj.status = Ext.getCmp("v_status").getValue() ;
                            paramObj.priority = Ext.getCmp("v_priority").getValue() ;
                            paramObj.type = Ext.getCmp("v_noticeType").getValue() ;
                            paramObj.objectType = Ext.getCmp("v_publicType").getValue() ;
                            paramObj.noticeTime=new Date();
                            
                            
                            paramObj.createStaffId = Ext.getCmp("v_createStaffId").getValue() ;
                            paramObj.createStaffName = Ext.getCmp("v_createStaffName").getValue() ;
                           
                            paramObj.objectId = Ext.getCmp("v_publicObject").getValue() ;
                            paramObj.objectName = Ext.getCmp("v_publicObjectName").getValue();
                            paramObj.state = 1;
                            paramObj.optype = "I";
                            var retMap = invokeAction("/message/PublicNoticeManageAction", paramObj);

                            break ;
                        }
                        case "mod":{
                            resultStr = '修改成功！';
                            if( Ext.getCmp("v_content").getValue()==''){
                            	
                            	  Ext.MessageBox.alert('提示', '公告内容不能为空！',null);
                            	  return;
                            }
                            var paramObj = new Object();
                            paramObj.noticeId = selItem[0].data.noticeId ;
                            paramObj.title = Ext.getCmp("v_title").getValue() ;
                            paramObj.content = Ext.getCmp("v_content").getValue() ;
                            paramObj.status = Ext.getCmp("v_status").getValue() ;
                            paramObj.priority = Ext.getCmp("v_priority").getValue() ;
                            paramObj.type = Ext.getCmp("v_noticeType").getValue() ;
                            paramObj.objectType = Ext.getCmp("v_publicType").getValue() ;
                            paramObj.noticeTime=new Date();
                            paramObj.stateDates=new Date();
                            
                            paramObj.createStaffId = Ext.getCmp("v_createStaffId").getValue() ;
                            paramObj.createStaffName = Ext.getCmp("v_createStaffName").getValue() ;
                            
                            paramObj.objectId = Ext.getCmp("v_publicObject").getValue() ;
                            paramObj.objectName = Ext.getCmp("v_publicObjectName").getValue();
                            paramObj.state = 1;
                            paramObj.optype = "U";
                            var retMap = invokeAction("/message/PublicNoticeManageAction", paramObj);
                            break ;
                        }
                    }
                    
                    setTimeout(function(){
                        Ext.MessageBox.hide();
                        Ext.example.msg('',resultStr);
                    }, 500);
                    moduleWin.close();
                    oper.QryPublicGrid();

                }}
            },{
                text: '取消',
                listeners:{"click":function(){
                    moduleWin.close();
                }}
            }]
        });
        
        moduleWin = new Ext.Window({
            title: '消息发布管理',
            closable:true,
            width:650,
            height:350,
            plain:true,
            layout: 'border',
            items: [infoPage]
        });
        Ext.getCmp("v_createStaffId").setValue(session1.staff.staffId);
        Ext.getCmp("v_createStaffName").setValue(session1.staff.staffName);   
        
        moduleWin.show(this);
        
        if(operator == 'mod'){
            Ext.getCmp("v_title").setValue(selItem[0].data.title);
            
             Ext.getCmp("v_priority").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_priority").setValue(selItem[0].data.priority);
            	   } 
               });
             Ext.getCmp("v_noticeType").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_noticeType").setValue(selItem[0].data.type);
            	   } 
               });
              Ext.getCmp("v_status").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_status").setValue(selItem[0].data.status);
            	   } 
               });
               Ext.getCmp("v_publicType").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_publicType").setValue(selItem[0].data.objectType);
            	   } 
               });
                Ext.getCmp("v_content").setValue(selItem[0].data.content);       
            Ext.getCmp("v_publicObject").setValue(selItem[0].data.objectId);             
            Ext.getCmp("v_publicObjectName").setValue(selItem[0].data.objectName);                                          
        }
     }
     
}