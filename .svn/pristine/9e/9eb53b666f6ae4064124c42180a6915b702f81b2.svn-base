/*
 * 
 * 菜单-连接监控页面
 */

function ConnectOper(){
    
     this.showFlowModuleInfoPage = function(username,userFlowLimit){

        var infoPage = new Ext.FormPanel({
            id :'flowPage',
            region: 'center',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,
            height:Ext.getBody().getSize().height*0.5,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 80,
            buttons: [{ 
                text: '确定',
                listeners:{"click":function(){
                    if(!infoPage.getForm().isValid()){
                        return ;
                    }
                    
                    var objq = new Object();
                    objq.flag = 3;
                    objq.userFlowLimit = Ext.getCmp("userFlowLimit").getValue();
                    objq.username =  username;
                    var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
                    moduleWin.close();
                    oper.QryMonitorGrid();
                }}
            },{
                text: '取消',
                listeners:{"click":function(){
                    moduleWin.close();
                }}
            }],

            items: [{
                layout:'column',
                items:[{
                    columnWidth:.8,
                    layout: 'form',
                    items: [{
                        xtype:'textfield',
                        fieldLabel: '流量阀值(M)(*仅限数字)',
                        name: 'userFlowLimit',
                        readOnly: false,
                        id: 'userFlowLimit',
                        vtype: 'num' ,
                        allowBlank: false,
                        anchor:'90%'    
                    }]
                },{columnWidth:.05,layout: 'form'}]
            }]
        });
                                  
              
        moduleWin = new Ext.Window({
            title: '调整流量阀值',
            closable:true,
            width:400,
            height:150,
            plain:true,
            layout: 'border',
            items: [infoPage]
        });
        
        moduleWin.show(this);       
        Ext.getCmp("userFlowLimit").setValue(userFlowLimit);
     }   
     
     this.showConnModuleInfoPage = function(username){

         var infoPage = new Ext.FormPanel({
             id :'connPage',
             region: 'center',
             labelAlign: 'left',
             frame:true,
             width:Ext.getBody().getSize().width,
             height:Ext.getBody().getSize().height*0.5,
             bodyStyle:'padding:5px',
             buttonAlign: 'center',
             labelWidth: 120,
             buttons: [{ 
                 text: '确定',
                 listeners:{"click":function(){
                     if(!infoPage.getForm().isValid()){
                         return ;
                     }
                     
                     var objq = new Object();
                     objq.flag = 4;
                     objq.userConnLimit = Ext.getCmp("userConnLimit").getValue();
                     var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
                     moduleWin.close();
                     oper.QryMonitorGrid();
                 }}
             },{
                 text: '取消',
                 listeners:{"click":function(){
                     moduleWin.close();
                 }}
             }],

             items: [{
                 layout:'column',
                 items:[{
                     columnWidth:.8,
                     layout: 'form',
                     items: [{
                         xtype:'textfield',
                         fieldLabel: '用户连接数阀值',
                         name: 'userConnLimit',
                         readOnly: false,
                         id: 'userConnLimit',
                         vtype: 'num' ,
                         allowBlank: false,
                         anchor:'90%'    
                     }]
                 },{columnWidth:.05,layout: 'form'}]
             }]
         });
                                   
               
         moduleWin = new Ext.Window({
             title: '调整用户连接数阀值',
             closable:true,
             width:400,
             height:150,
             plain:true,
             layout: 'border',
             items: [infoPage]
         });
         
         moduleWin.show(this);       

         var objq = new Object();
         objq.flag = 5;

         var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
         Ext.getCmp("userConnLimit").setValue(tmpObj.userConnLimit);
         
      }
         
     this.showPrivModuleInfoPage = function(username,userConnPriv){

         var PrivStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'普通'],[2,'高级']]});     
         var infoPage = new Ext.FormPanel({
             id :'privPage',
             region: 'center',
             labelAlign: 'left',
             frame:true,
             width:Ext.getBody().getSize().width,
             height:Ext.getBody().getSize().height*0.5,
             bodyStyle:'padding:5px',
             buttonAlign: 'center',
             labelWidth: 80,
             buttons: [{ 
                 text: '确定',
                 listeners:{"click":function(){
                     if(!infoPage.getForm().isValid()){
                         return ;
                     }
                     
                     var objq = new Object();
                     objq.flag = 2;
                     objq.userConnPriv = Ext.getCmp("userConnPriv").getValue();;
                     objq.username =  username;
                     var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
                     moduleWin.close();
                     oper.QryMonitorGrid();
                 }}
             },{
                 text: '取消',
                 listeners:{"click":function(){
                     moduleWin.close();
                 }}
             }],

             items: [{
                 layout:'column',
                 items:[{
                     columnWidth:.8,
                     layout: 'form',
                     items: [{
                         xtype:'combo',
                         fieldLabel: '用户权限',
                         name: 'userConnPriv',
                         id: 'userConnPriv',
                         valueField: 'id',
                         displayField: 'value',
                         mode: 'local',
                         triggerAction: 'all',
                         value: '1',
                         store: PrivStore,
                         allowBlank:false, 
                         blankText:"用户权限不能为空!",
                         anchor:'90%'
                     }]
                 },{columnWidth:.05,layout: 'form'}]
             }]
         });
                                   
               
         moduleWin = new Ext.Window({
             title: '调整用户权限',
             closable:true,
             width:400,
             height:150,
             plain:true,
             layout: 'border',
             items: [infoPage]
         });
         
         moduleWin.show(this);     
         Ext.getCmp("userConnPriv").setValue(userConnPriv);

      }
}