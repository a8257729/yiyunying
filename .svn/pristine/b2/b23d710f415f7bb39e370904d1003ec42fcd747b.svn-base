/*
 * 
 * 菜单-功能注册页面
 */
 var flag ;
 var vMenuId;
 var apkFlag="";
var parentMenuName;
 var osType ;
 var menuObject=new Object();
 var insertId=0;
 var contorller="";
 
function FunOper(){
        
    this.funcInfoPage = function(operator,pmName,pmId,className,contor){
        osType =   pmName;
        vMenuId = pmId;
        flag = operator;
        parentMenuName=className;    
        contorller=contor;
     
        var appfuncInfo = new Ext.FormPanel({
            id :'appfuncInfo',
            region: 'center',
            labelAlign: 'left',
            frame:true,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 70,
            items: [{
                layout:'column',
                items:[{
                     columnWidth:.15,
                     layout: 'form',
                     items: [{                       
                             layout: 'form',
                             labelAlign:'top',
                             items: [{
                                 xtype : 'box',
                                 id : 'photoView',
                                 
                                 autoEl : {
                                     width: 50,
                                     height: 50,
                                     tag: 'img',
                                     src: '/MOBILE/appManage/image/001.png',
                                     style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
                                     complete : 'off',
                                     id : 'photoBrowse'
                                 }
                             },{
                                     xtype: 'button',
                                     id:'btUpload',
                                     text: '上传图标',
                                     handler: function(){
                                        uploadOper.addImageFile(null,null);
                                     }
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '图标地址',
                        name: 'v_funcIconUrl',
                        id: 'v_funcIconUrl',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '功能名称',
                        name: 'v_funcName',
                        id: 'v_funcName',
                        allowBlank:false, 
                        blankText:"功能名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'textfield',
                        fieldLabel: '版本号',
                        name: 'v_versionName',
                        id: 'v_versionName',
                        triggerAction: 'all',
                        editable : false ,
                          blankText:"版本号不能为空!",
                        allowBlank: false,
                        listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                                  funcOper.setAppInf(c.getValue());
                                }
                            }
                        },
                        anchor:'90%'                           
                    },{
                        xtype:'textfield',
                        fieldLabel: '开放包名',
                        name: 'accessPackage',
                        id: 'accessPackage',
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        allowBlank: false}
                    ]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                    xtype:'textfield',
                    fieldLabel: '功能编码',
                    name: 'v_funcCode',
                    id: 'v_funcCode',
                    allowBlank:false, 
                    //emptyText: '功能编码不能为空!',
                    anchor:'90%'    
                  },{
                        xtype:'textfield',
                        fieldLabel: '开放类路径',
                        name: 'accessClass',
                        id: 'accessClass',
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        allowBlank: false,
                        listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                                  funcOper.setfunInf(c.getValue());
                                }
                            }
                        },
                        anchor:'90%'                           
                    }]
               }]
            },{
                layout:'column',
                items:[{
                    columnWidth:.15,
                    layout: 'form'
                  },{ columnWidth:.85,
                      layout: 'form',
                      items: [{
                          xtype:'textfield',
                          fieldLabel: '功能类路径',
                          name: 'funcClass',
                          id: 'funcClass',
                          allowBlank:false, 
                          emptyText: '开放类路径不能为空!',
                          triggerAction: 'all',
                          editable : false ,
                           forceSelection: true,
                          anchor:'90%'   , 
                      listeners: {
                         
                            
                        }
                      },{
                          xtype:'textfield',
                          fieldLabel: '动作名称',
                          name: 'actionName',
                          id: 'actionName',
                          allowBlank:false, 
                          emptyText: '开放类路径不能为空!',
                          triggerAction: 'all',
                          editable : false ,
                           forceSelection: true,
                          anchor:'90%'   , 
                      listeners: {
                         
                            
                        }
                      },{
                        xtype:'textarea',
                        fieldLabel: '功能描述',
                        name: 'funcContent',
                        id: 'funcContent',
                        height : 120,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }],
           buttons: [{ 
               text: '确定',
               listeners:{"click":function(){
                   if(!Ext.getCmp("appfuncInfo").getForm().isValid()){
                       return ;
                   }
                   var resultStr ;
                   funOper.doSubmit();
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
            title: '功能管理',
            closable:true,
            width:600,
            height:450,
            plain:true,
            layout: 'border',
            items: [appfuncInfo]
        });
        
        funcWin.show(this);
      
        if( operator== 'mod'){
          	var selGridItem = Ext.getCmp('appFunGrid').getSelectionModel().getSelections();
        	
           Ext.getCmp("v_funcCode").setValue(selGridItem[0].data.funcCode);
            Ext.getCmp("v_funcName").setValue(selGridItem[0].data.funcName);
            Ext.getCmp("accessClass").setValue(selGridItem[0].data.accessClass);
            Ext.getCmp("accessPackage").setValue(selGridItem[0].data.accessPackage);   
            Ext.getCmp("funcClass").setValue(selGridItem[0].data.funcClass);   
            Ext.getCmp("v_versionName").setValue(selGridItem[0].data.versionName);
             Ext.getCmp("funcContent").setValue(selGridItem[0].data.funcContent);
            Ext.getCmp("actionName").setValue(selGridItem[0].data.actionName);
          //   alert(selGridItem[0].data.menuName)     ;                         
        }

     }

   
    
    
     

     //确定
     this.doSubmit = function () {
         if(!Ext.getCmp("appfuncInfo").getForm().isValid()){
             return ;
         }
                                                       
         var resultStr ;
         
         switch (flag){
             case "add":{
                 resultStr = '新增成功！';
            
                 var objAttr;
                 var paramObj = new Object();
                 paramObj.appId=vMenuId;//appid 功能
                 paramObj.optfunmenu='fun';//判断此添加是应用添加，还是功能添加；
                 paramObj.parentId = vMenuId ;
                 paramObj.funcName = Ext.getCmp("v_funcName").getValue() ;
                 paramObj.iconUri= Ext.getCmp("v_funcIconUrl").getValue();
                 paramObj.menuType = "1";
                 paramObj.type = "add"; 
                 paramObj.osType = osType;//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isLeaf=1 //是否是跟节点           
                 paramObj.isMain=1;                                   
                 paramObj.accessPackage=Ext.getCmp("accessPackage").getValue() ;
                 paramObj.accessClass=Ext.getCmp("accessClass").getValue() ;
                 //paramObj.menuIconUri="image/1.gif";
                 paramObj.funcCode=Ext.getCmp("v_funcCode").getValue() ;
                 paramObj.funcClass=Ext.getCmp("funcClass").getValue();
              //   alert(paramObj.accessPackage+"   "+paramObj.accessClass+"  "+paramObj.funcClass);
                 paramObj.versionName=Ext.getCmp("v_versionName").getValue();
                 paramObj.funcContent=Ext.getCmp("funcContent").getValue() ;
                 paramObj.actionName=Ext.getCmp("actionName").getValue() ;
                 paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;
                
                 if(contorller=='addToadd'){ //如果是从app 添加菜单经来到就把添加到func添加到列表中但是没有保存到数据库中
                 var dateType= Ext.getCmp('appFunGrid').store.recordType;
                    var a=new dateType({
                    	appId:'',
                    	appFuncID:'',
                    	funcCode:paramObj.funcCode,
                    	funcName:paramObj.funcName,
                    	funcClass:paramObj.funcClass,
                    	accessPackage:paramObj.accessPackage,
                    	accessClass:paramObj.accessClass,
                    	 actionName:paramObj.actionName,
                    	buildTime:new Date(),
                    	versionCode:'',
                    	versionName:paramObj.versionName,
                    	funcContent:paramObj.funcContent,
                    	stateDate:new Date()
                    });
                  Ext.getCmp('appFunGrid').store.insert(insertId++,a);//每家一条就向表格中添加一条数据但还没有保存到数据库中
             }
                 if(contorller=='modToadd'){ //如果是从app修改页面到添加页面的，添加到这条记录就保存的数据库中
                      var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
                 }
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                 var paramObj = new Object();
                 var selItemMenu = Ext.getCmp('appFunGrid').getSelectionModel().getSelections();  
               paramObj.parentId = vMenuId ;
                 paramObj.funcName = Ext.getCmp("v_funcName").getValue() ;
                 paramObj.iconUri= Ext.getCmp("v_funcIconUrl").getValue();
                 paramObj.type = "mod"; 
                 paramObj.osType = osType;//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isLeaf=1 //是否是跟节点           
                 paramObj.appFuncId=selItemMenu[0].data.appFuncID;     
                 paramObj.appId = selItemMenu[0].data.appId;  
                 paramObj.versionCode=selItemMenu[0].data.versionCode;  
                 paramObj.accessPackage=Ext.getCmp("accessPackage").getValue() ;
                 paramObj.accessClass=Ext.getCmp("accessClass").getValue() ;
                // paramObj.menuIconUri="image/1.gif";
                 paramObj.funcCode=Ext.getCmp("v_funcCode").getValue() ;
                 paramObj.funcClass=Ext.getCmp("funcClass").getValue();
                 paramObj.versionName=Ext.getCmp("v_versionName").getValue();
                 paramObj.funcContent=Ext.getCmp("funcContent").getValue() ;
                 paramObj.actionName=Ext.getCmp("actionName").getValue() ;
                 paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;
                 if(contorller=='addTomod'){//如果是从app添加菜单跳到func修改菜单
                
                 var rowId=Ext.getCmp('appFunGrid').getSelectionModel().lastActive; //修改用的是选删出在添加
                 var rowObj=Ext.getCmp('appFunGrid').store.getAt(rowId);   
                        rowObj.set('funcCode',paramObj.funcCode);
                        rowObj.set('funcName',paramObj.funcName);
                        rowObj.set('funcClass',paramObj.funcClass);
                        rowObj.set('accessPackage',paramObj.accessPackage);
                        rowObj.set('accessClass',paramObj.accessClass);
                        rowObj.set('buildTime',selItemMenu[0].data.buildTime);
                        rowObj.set('versionCode',paramObj.versionCode);
                        rowObj.set('versionName',paramObj.versionName);
                        rowObj.set('actionName', paramObj.actionName)
                        rowObj.set('funcContent',paramObj.funcContent);
                        rowObj.set('versionName',paramObj.funcName);
                      Ext.getCmp('appFunGrid').store.commitChanges();
                 }
                if(contorller=='modTomod'){//如果是从app修改页面跳到func修改页面的修改这条记录后就保存到书库中
                 var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
                 }
                 break ;
             }

         }
                  
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         funcWin.close();
         if(contorller=="modTomod" || contorller=='modToadd'){
        	 appOper.QryMonitorGrid();
         }
     }
  
}