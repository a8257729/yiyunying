/*
 * 
 * 菜单-应用审核页面
 */

function ApkAuditOper(){
     this.showModuleInfoPage = function(operator,pmName,pmId){

        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [["1",'是'],["0",'否']]});
        var VName =  staffName;      
        var infoPage = new Ext.FormPanel({
            id:'infoPage',
            region: 'north',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,
            height:200,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 90,
            items: [{
                layout:'column',
                items:[{
                    columnWidth:.5,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '审核人id',
                        name: 'v_audit_staff_id',
                        id: 'v_audit_staff_id',
                        allowBlank:false, 
                        value:staffId,
                        blankText:"审核人不能为空!",
                        anchor:'95%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '审核人',
                        name: 'v_audit_staff_name',
                        id: 'v_audit_staff_name',
                        allowBlank:false, 
                        value:pmName,
                        blankText:"审核人不能为空!",
                        anchor:'95%'    
                    },{                             
                            xtype:'combo',
                            fieldLabel: '是否通过',
                            name: 'v_is_pass',
                            id: 'v_is_pass',
                            valueField: 'id',
                            displayField: 'value',
                            mode: 'local',
                            triggerAction: 'all',
                            value: '',
                            store: staticStore,
                            allowBlank:false, 
                            blankText:"是否通过不能为空!",
                            anchor:'95%'    
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.45,
                    layout: 'form',
                    items: [{
                            
                            xtype: 'datetimefield',
                            fieldLabel: '审核日期',
                            name: 'v_audit_date',
                            id: 'v_audit_date',
                            format: 'Y-m-d H:i:s',
                            allowBlank: false,
                            blankText: "审核日期不能为空!",
   
                            anchor:'95%'
                    }]
               }]
            },{
                xtype:'textarea',
                fieldLabel: '审核意见说明',
                name: 'v_audit_comment',
                id: 'v_audit_comment',
                height : 100,
                value: '',
                anchor:'95%'    

          }]
           
        });
        

        
        var checkshow = new Ext.grid.CheckboxSelectionModel();
        var selColumn = new Ext.grid.ColumnModel([
            checkshow,
            {header:'muneID',dataIndex:'muneId',hidden:true },
            {header:'apkID',dataIndex:'apkId',hidden:true },
            {header:'funId',dataIndex:'funId',hidden:true },
            {header:'funcRegStaffId',dataIndex:'funcRegStaffId',hidden:true },  
            {header:'应用名称',dataIndex:'muneName',width:150 },  
            {header:'软件介绍内容',dataIndex:'apkContent',width:300 },
            {header:'应用大小',dataIndex:'apkSize',width:100 }        
           ]); 
        
       
        var selGrid = new ZTESOFT.Grid({        
            id: 'selGrid',
            region: 'center',   
            width:Ext.getBody().getSize().width,
            height:Ext.getBody().getSize().height,              
            title:'应用列表',
            cm:selColumn,
            fbar:[{ 
                text: '确定',
                listeners:{"click":function(){
                    apkAuditOper.doSubmit();
                }}
            },{
                text: '取消',
                listeners:{"click":function(){
                    auditWin.close();
                }}
            }],
            buttonAlign: 'center', 
            pageSize:100,
            paging:true,
            url: '/MOBILE/ExtServlet?actionName=system/AppClassManageAction',       
            sm: checkshow
        });
        
        Ext.getCmp('selGrid').store.on('beforeload',
                function(store){ 
                    if(Ext.getCmp('selGrid').store.lastOptions != null){
                       Ext.getCmp('selGrid').store.baseParams = {apkIds:pmId,flag:2};
                    }
                }
        )
        Ext.getCmp('selGrid').store.removeAll() ;
        Ext.getCmp('selGrid').store.load({params:{start:0, limit:100}});
        
        auditWin = new Ext.Window({
            title: '审核',
            closable:true,
            width:650,
            height:500,
            plain:true,
            layout: 'border',
            items: [infoPage,selGrid]
        });
        
        auditWin.show(this);
        

     }
     
     this.doSubmit = function () {
         
         if(!Ext.getCmp("infoPage").getForm().isValid()){
             return ;
         }
         
         var resultStr ;
         

                 resultStr = '审核成功！';
                                                                                             
                 var paramObj = new Object();
                 
                 paramObj.auditStaffId = staffId ;
                 paramObj.auditDate = Ext.getCmp("v_audit_date").getValue() ;
                 paramObj.isPass = Ext.getCmp("v_is_pass").getValue();
                 paramObj.auditComment = Ext.getCmp("v_audit_comment").getValue();

                 var selItem3 = Ext.getCmp('selGrid').getSelectionModel().getSelections();
                 if(selItem3 ==null || selItem3.length ==0){
                      Ext.MessageBox.show({
                           title: '提示',
                           msg: '请选择应用！',
                           buttons: Ext.MessageBox.OK,
                           width:200,
                           icon: Ext.MessageBox.ERROR
                       });
                       return;
                 }
                 var valueArray = new Array();
                 for (j=0;j<selItem3.length;j++){
                     var inputParams = new Object();       
                     inputParams.apkId = selItem3[j].data.apkId;      
                     inputParams.apkName = selItem3[j].data.apkName;  
                     inputParams.muneId = selItem3[j].data.muneId;  
                     inputParams.funId = selItem3[j].data.funId;                    
                     inputParams.funcRegStaffId = selItem3[j].data.funcRegStaffId;
                     valueArray.push(inputParams);
                 }
                 paramObj.valueArray = valueArray;
                 
                 var retMap = invokeAction("/mobsystem/ApkAuditManageAction", paramObj);

         
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         auditWin.close();
         oper.QryMenuGrid();
     }
}