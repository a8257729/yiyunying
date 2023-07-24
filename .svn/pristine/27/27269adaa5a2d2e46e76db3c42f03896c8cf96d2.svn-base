/*
 * 
 * 
 * 服务页面
 */
 var flag ;
 var vMenuId;
 var apkFlag="";
var parentMenuName;
 var osType ;
 var menuObject=new Object();
function RestOper(){
   //查询菜单类型     
	   this.funcInfoPage = function(operator,pmName,pmId,className){
		   flag=operator;
		   
	     var stateStore = new Ext.data.JsonStore({
			id: 'stateStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SERV_STATUS'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	        
	    });
	     
	      var typeStore = new Ext.data.JsonStore({
			id: 'typeStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SERV_TYPE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	        
	    });
        var restInfo = new Ext.FormPanel({
            id :'restInfo',
            region: 'center',
            labelAlign: 'left',
            frame:true,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 70,
           items:[
        	      {
                        xtype:'textfield',
                        fieldLabel: '服务名称',
                        name: 'v_servName',
                        id: 'v_servName',
                        allowBlank:false, 
                        blankText:"功能名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'combo',
                        fieldLabel: '服务状态',
                        name: 'v_servStatus',
                        id: 'v_servStatus',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                  
                        store: stateStore,
                        allowBlank: false,
                        anchor:'90%'                           
                    },{
                        xtype:'combo',
                        fieldLabel: '服务类型',
                        name: 'v_servType',
                        id: 'v_servType',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                  
                        store: typeStore,
                        allowBlank: false,
                        anchor:'90%'                           
                    },
                    {
                        xtype:'textfield',
                        fieldLabel: '服务地址',
                        name: 'v_servAddr',
                        id: 'v_servAddr',
                        editable : false ,
                        anchor:'90%'                           
                    }
                
                ],
           buttons: [{ 
               text: '确定',
               listeners:{"click":function(){
                   if(!Ext.getCmp("restInfo").getForm().isValid()){
                       return ;
                   }
                   var resultStr ;
                   restOper.doSubmit();
                   funcWin.close();

               }}
           },{
               text: '取消',
               listeners:{"click":function(){
                   funcWin.close();
               }}
           }]
        });
                                         
        funcWin = new Ext.Window({
            title: '服务管理',
            closable:true,
            width:600,
            height:350,
            plain:true,
            layout: 'border',
            items: [restInfo]
        });
        
        funcWin.show(this);
        
        if(operator == 'mod'){
          	var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_servName").setValue(selGridItem[0].data.servName);   
               Ext.getCmp("v_servStatus").store.load({
            	   callback : function(r,opt,t){
            	     Ext.getCmp("v_servStatus").setValue(selGridItem[0].data.servStaus); 
            	   } 
               });
             Ext.getCmp("v_servType").store.load({
            	   callback : function(r,opt,t){
            	     Ext.getCmp("v_servType").setValue(selGridItem[0].data.servType); 
            	   } 
               });
            //.store parseInt(
                         
             Ext.getCmp("v_servAddr").setValue(selGridItem[0].data.servAddr);
          //   Ext.getCmp("v_stateTime").setValue(selGridItem[0].stateDate);
          //   Ext.getCmp("v_state").setValue(selGridItem[0].state);
        }

     }
	   
		

     //确定
     this.doSubmit = function () {
         if(!Ext.getCmp("restInfo").getForm().isValid()){
             return ;
         }
                                                        
         var resultStr ;
         
         switch (flag){
             case "add":{
                 resultStr = '新增成功！';
                 var objAttr;
                 var paramObj = new Object();
                 paramObj.parentId = vMenuId ;
                 paramObj.servName   = Ext.getCmp("v_servName").getValue() ;
                 paramObj.servStatus  = Ext.getCmp("v_servStatus").getValue() ;
                  paramObj.servType  = Ext.getCmp("v_servType").getValue() ;
                 paramObj.servAddr   = Ext.getCmp("v_servAddr").getValue() ;
              //   paramObj.stateDate  = Ext.getCmp("v_stateTime").getValue() ;
               //  paramObj.state      = Ext.getCmp("v_state").getValue() ;
                 paramObj.type='add';
                 paramObj.state=1 ;
                
                 paramObj.staffId= session1.staff.staffId ;
                 
                 var retMap = invokeAction("/rest/OptRestServiceAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
              var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
                       var paramObj = new Object();
                 paramObj.parentId = vMenuId ;
                 paramObj.restServiceId=selGridItem[0].data.restServiceId;
                   paramObj.servName   = Ext.getCmp("v_servName").getValue() ;
                 paramObj.servStatus  = Ext.getCmp("v_servStatus").getValue() ;
                 paramObj.servType  = Ext.getCmp("v_servType").getValue() ;
                 paramObj.servAddr   = Ext.getCmp("v_servAddr").getValue() ;
              //   paramObj.stateDate  = Ext.getCmp("v_stateTime").getValue() ;
                 //paramObj.state      = Ext.getCmp("v_state").getValue() ;
                   paramObj.buildTime    = selGridItem[0].data.buildTime  ;
                 paramObj.type='mod';
                  paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;

                 var retMap = invokeAction("/rest/OptRestServiceAction", paramObj);
                 break ;
             }

         }
                  
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         funcWin.close();
         oper.QryMonitorGrid();
     }
  
}