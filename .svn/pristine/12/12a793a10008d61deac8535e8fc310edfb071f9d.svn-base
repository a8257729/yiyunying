/*
 * 
 * 菜单-应用分类页面
 */
 var oldDisplayIndex;
function ClassOper(){
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [["true",'是'],["false",'否']]});
        var staticdistypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'二级菜单'],[2,'list列表'],[3,'明细页面'],[4,'回单页面'],[5,'树型'],[6,'内置功能'],[7,'listTree树'],[8,'list对话框'],[9,'流程展显'],[10,'数据传输'],[11,'调用外系统'],[12,'统计报表'],[13,'初始页面'],[14,'信息选择'],[15,'组合页面']]});
        var menuTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'营销高参'],[2,'装维助手'],[3,'看数'],[4,'日常']]});
        var infoPage = new Ext.FormPanel({
            region: 'center',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,
            height:150,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 70,
            items: [{
                layout:'column',
                items:[{
                    columnWidth:.45,
                    layout: 'form',
                    items: [{
                        xtype:'textfield',
                        fieldLabel: '应用分类',
                        name: 'v_name',
                        id: 'v_name',
                        allowBlank:false, 
                        blankText:"应用分类不能为空!",
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '图片地址',
                        name: 'v_menuIconUri',
                        id: 'v_menuIconUri',
                        allowBlank:true, 
                        blankText:"图片地址不能为空!",
                        anchor:'90%'    
                    },{  
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.45,
                    layout: 'form',
                    items: [{
                        xtype:'textfield',
                        fieldLabel: '显示顺序',
                        name: 'v_menuIndex',
                        id: 'v_menuIndex',
                        vtype: 'num' ,
                        allowBlank:false, 
                        blankText:"显示顺序不能为空!",
                        anchor:'90%'
                    },{
                        xtype:'hidden',
                        fieldLabel: '权限代码',
                        name: 'v_priv_code',
                        id: 'v_priv_code',
                        readOnly: true,
                        emptyText: '将自动生成',
                        anchor:'90%'    
                    },{                    
                    }]
               },{columnWidth:.1,layout: 'form'}]
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
                            var objq = new Object();
                            objq.type = "selIsExist";
                            objq.parentId = 0;
                            objq.menuName = Ext.getCmp("v_name").getValue();
                            var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                            if(tmpObj != ""){
                                Ext.MessageBox.show({
                                    title: '提示',
                                    msg: '该应用分类已存在，请重新指定！',
                                    buttons: Ext.MessageBox.OK,
                                    width:200,
                                    icon: Ext.MessageBox.ERROR
                                });
                                return;
                            }
                            
                            if (Ext.getCmp("v_menuIndex").getValue()!=null && Ext.getCmp("v_menuIndex").getValue() >9){
                                Ext.MessageBox.show({
                                    title: '提示',
                                    msg: '显示顺序必须小于10！',
                                    buttons: Ext.MessageBox.OK,
                                    width:200,
                                    icon: Ext.MessageBox.ERROR
                                });
                                return;                               
                            }
                            
                            var obj1 = new Object();
                            obj1.type = "selDisplayInedx";
                            obj1.osType = pmId;
                            obj1.menuIndex = Ext.getCmp("v_menuIndex").getValue();
                            var tmpObj = invokeAction("/appmanage/SelAppMenuAction", obj1);
                            if(tmpObj.icount > "0"){
                                Ext.MessageBox.show({
                                    title: '提示',
                                    msg: '该显示顺序已存在，请重新指定！',
                                    buttons: Ext.MessageBox.OK,
                                    width:200,
                                    icon: Ext.MessageBox.ERROR
                                });
                                return;
                            }

                            var paramObj = new Object();
                            paramObj.parentId = 0 ;
                            paramObj.menuName = Ext.getCmp("v_name").getValue() ;
                            paramObj.pathName = Ext.getCmp("v_name").getValue();
                            paramObj.pathCode = "null";

                            paramObj.menuIndex = Ext.getCmp("v_menuIndex").getValue() ;
                            paramObj.menuIconUri= Ext.getCmp("v_menuIconUri").getValue() ;
                            paramObj.menuType = "1";
                            paramObj.type = "menuAdd"; 
                            paramObj.osType = pmId;//0为安卓，1为ios，2为winphone
                            
                            var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
                            break ;
                        }
                        case "mod":{
                            resultStr = '修改成功！';
                            var objq = new Object();
                            objq.type = "selIsExist";
                            objq.parentId = 0;
                            objq.menuName = Ext.getCmp("v_name").getValue();
                            var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                            if(tmpObj!=""){
                                for(var i=0;i<tmpObj.length;i++){
                                    if(selItem[0].data.menuId!=tmpObj[i].ID){
                                        Ext.MessageBox.show({
                                           title: '提示',
                                           msg: '该应用分类已存在，请重新指定！',
                                           buttons: Ext.MessageBox.OK,
                                           width:200,
                                           icon: Ext.MessageBox.ERROR
                                        });
                                        return;
                                    }
                                }             
                            }
                            
                            if (Ext.getCmp("v_menuIndex").getValue()!=null && Ext.getCmp("v_menuIndex").getValue() >9){
                                Ext.MessageBox.show({
                                    title: '提示',
                                    msg: '显示顺序必须小于10！',
                                    buttons: Ext.MessageBox.OK,
                                    width:200,
                                    icon: Ext.MessageBox.ERROR
                                });
                                return;                               
                            }
                            
                            var obj1 = new Object();
                            obj1.type = "selDisplayInedx";
                            obj1.osType = pmId;
                            obj1.menuIndex = Ext.getCmp("v_menuIndex").getValue();
                            var tmpObj = invokeAction("/appmanage/SelAppMenuAction", obj1);
                            if(tmpObj.icount > "0"  && Ext.getCmp("menuIndex").getValue() != oldDisplayIndex){
                                Ext.MessageBox.show({
                                    title: '提示',
                                    msg: '该显示顺序已存在，请重新指定！',
                                    buttons: Ext.MessageBox.OK,
                                    width:200,
                                    icon: Ext.MessageBox.ERROR
                                });
                                return;
                            }
                            
                            var paramObj = new Object();
                            paramObj.menuId = selItem[0].data.menuId;
                            paramObj.parentId = 0 ;
                            paramObj.menuName = Ext.getCmp("v_name").getValue() ;
                            paramObj.pathName = Ext.getCmp("v_name").getValue();
                            paramObj.pathCode = "null";

                            paramObj.menuIndex = Ext.getCmp("v_menuIndex").getValue() ;
                            paramObj.menuIconUri= Ext.getCmp("v_menuIconUri").getValue() ;
                            paramObj.menuType = "1";
                            paramObj.type = "mod"; 
                            paramObj.osType = selItem[0].data.osType;//0为安卓，1为ios，2为winphone
                            
                            paramObj.type = "menuMod";
                            var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
                            break ;
                        }
                    }
                    
                    setTimeout(function(){
                        Ext.MessageBox.hide();
                        Ext.example.msg('',resultStr);
                    }, 500);
                    moduleWin.close();
                    oper.QryClassGrid();

                }}
            },{
                text: '取消',
                listeners:{"click":function(){
                    moduleWin.close();
                }}
            }]
        });
        
        moduleWin = new Ext.Window({
            title: '应用分类管理',
            closable:true,
            width:650,
            height:150,
            plain:true,
            layout: 'border',
            items: [infoPage]
        });
        
        moduleWin.show(this);
        
        if(operator == 'mod'){
            Ext.getCmp("v_name").setValue(selItem[0].data.menuName);
            Ext.getCmp("v_menuIndex").setValue(selItem[0].data.menuIndex);       
            oldDisplayIndex = selItem[0].data.menuIndex;              
               
        }
     }
     
     this.showSystemInfoPage = function(operator,pmName,pmId){
         var selItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
        
         var infoPage = new Ext.FormPanel({
             region: 'center',
             labelAlign: 'left',
             frame:true,
             width:Ext.getBody().getSize().width,
             height:150,
             bodyStyle:'padding:5px',
             buttonAlign: 'center',
             labelWidth: 70,
             items: [{
                 layout:'column',
                 items:[{
                     columnWidth:.8,
                     layout: 'form',
                     items: [{
                         xtype:'textfield',
                         fieldLabel: '终端平台',
                         name: 'osTypeName',
                         readOnly: false,
                         id: 'osTypeName',
                         allowBlank: false,
                         anchor:'90%'    
                     }]
                 },{columnWidth:.05,layout: 'form'}]
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
                                              
                             var paramObj = new Object();
                             paramObj.osTypeName = Ext.getCmp("osTypeName").getValue();
                             paramObj.type = "sysAdd";
                             var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);  

                             break ;
                         }
                         case "mod":{
                             resultStr = '修改成功！';
                                                                                    
                             var paramObj = new Object(); 
                             paramObj.id = selItem[0].data.id;
                             paramObj.osType = selItem[0].data.osType;
                             paramObj.osTypeName = Ext.getCmp("osTypeName").getValue();                             
                             paramObj.type = "sysMod";
                             var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);  
                             break ;
                         }
                     }
                     
                     setTimeout(function(){
                         Ext.MessageBox.hide();
                         Ext.example.msg('',resultStr);
                     }, 500);
                     sysWin.close();
                     oper.QryApkclassGrid();

                 }}
             },{
                 text: '取消',
                 listeners:{"click":function(){
                     sysWin.close();
                 }}
             }]
         });
         
         sysWin = new Ext.Window({
             title: '终端平台管理',
             closable:true,
             width:350,
             height:150,
             plain:true,
             layout: 'border',
             items: [infoPage]
         });
         
         sysWin.show(this);
         
         if(operator == 'mod'){
             Ext.getCmp("osTypeName").setValue(selItem[0].data.osTypeName);                  
                
         }
      }
}