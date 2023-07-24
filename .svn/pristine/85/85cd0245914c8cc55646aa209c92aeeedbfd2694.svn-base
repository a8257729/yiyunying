/*
 * 
 * 菜单-应用注册页面
 */
 var flag ;
 var vMenuId;
 var osType ;
function RegOper(){
       
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
        
        var sysStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/QryMobileBusiSysAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'sysStore'  
            }, [   
                {name: 'name', mapping: 'sysProvider'},   
                {name: 'value', mapping: 'busiSysId'}   
            ])   
        }); 
        sysStore.load({params:{flag:1,start:0, limit:20}});
          
        var infoPage = new Ext.FormPanel({
            id :'infoPage',
            region: 'center',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,
            height:Ext.getBody().getSize().height*0.5,
            bodyStyle:'padding:5px',
            buttonAlign: 'center',
            labelWidth: 80,
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
                                     src: 'image/001.png',
                                     style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
                                     complete : 'off',
                                     id : 'photoBrowse'
                                 }
                             },{
                                     xtype: 'button',
                                     id:'btUpload',
                                     text: '上传图标',
                                     handler: function(){
                                         var paramUrl ='v_app_icon_url';
                                         var paramName ='v_app_icon_name';
                                         var preview ='photoBrowse';
                                         uploadOper.addImageFile(paramUrl,paramName,preview);
                                     }
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '图标名称',
                        name: 'v_app_icon_name',
                        id: 'v_app_icon_name',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '图标地址',
                        name: 'v_app_icon_url',
                        id: 'v_app_icon_url',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '快照地址',
                        name: 'v_app_snapshot',
                        id: 'v_app_snapshot',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '应用ID',
                        name: 'v_app_id',
                        id: 'v_app_id',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用名称',
                        name: 'v_app_name',
                        id: 'v_app_name',
                        allowBlank:false, 
                        blankText:"应用名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用大小',
                        name: 'v_app_size',
                        id: 'v_app_size',
                        vtype: 'num' ,
                        allowBlank:false, 
                        blankText:"应用大小不能为空!",
                        anchor:'90%'
                    },{
                        xtype: 'compositefield',
                        fieldLabel: '下载链接',
                        anchor:'100%',
                        items: [{
                                xtype: 'textfield',
                                name: 'v_download_url',
                                id: 'v_download_url',
                                allowBlank:false, 
                                readOnly: true, 
                                width: '168',
                                blankText:"下载链接!"
                            },{
                                xtype: 'button',
                                id : 'v_b_download_url',
                                text: '..', 
                                handler: function(){
                                        var paramName ='v_download_url';
                                        var fileName = uploadOper.addFile(paramName);

                                }
                            }]
                    },{
                        xtype:'combo',
                        fieldLabel: '业务系统',
                        id: 'v_busiSysId',
                        name: 'v_busiSys_name',                       
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: sysStore,
                        allowBlank: false,
                        anchor:'90%'                           
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '更新时间',
                        name: 'v_updateTime',
                        id: 'v_updateTime',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '应用内部版本号',
                        name: 'v_versionCode',
                        id: 'v_versionCode',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用版本',
                        name: 'v_app_version',
                        id: 'v_app_version',
                        allowBlank:false, 
                        emptyText: '应用版本不能为空!',
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用关键字',
                        name: 'v_app_key_word',
                        id: 'v_app_key_word',
                        allowBlank:false, 
                        emptyText: '应用关键字不能为空!',
                        anchor:'90%'    
                    },{
                        xtype: 'compositefield',
                        fieldLabel: '应用快照',
                        anchor:'100%',
                        items: [{
                                xtype: 'textfield',
                                name: 'v_app_snapshot_name',
                                id: 'v_app_snapshot_name',
                                allowBlank:false, 
                                readOnly: true, 
                                width: '168',
                                blankText:"快照上传!"
                            },{
                                xtype: 'button',
                                id : 'v_b_app_snapshot',
                                text: '..', 
                                handler: function(){
                                    var paramUrl ='v_app_snapshot';
                                    var paramName ='v_app_snapshot_name';
                                    var preview ="";
                                    uploadOper.addImageFile(paramUrl,paramName,preview);
                            }
                        }]
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
                        xtype:'textarea',
                        fieldLabel: '应用介绍',
                        name: 'v_app_intro',
                        id: 'v_app_intro',
                        height : 80,
                        value: '',
                        anchor:'95%'    
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
                       xtype:'textarea',
                       fieldLabel: '更新日志',
                       name: 'v_update_log',
                       id: 'v_update_log',
                       height : 80,
                       value: '',
                       anchor:'95%'    
                     }]
                }]
          }],
           buttons: [{ 
               text: '确定',
               listeners:{"click":function(){
                   regOper.doSubmit();
               }}
           },{
               text: '取消',
               listeners:{"click":function(){
                   moduleWin.close();
               }}
           }]
        });
                                         
        moduleWin = new Ext.Window({
            title: '应用管理',
            closable:true,
            width:750,
            height:350,
            plain:true,
            layout: 'border',
            items: [infoPage]
        });
        
        moduleWin.show(this);
        flag = operator;
                             
        vMenuId = pmId;
        osType = pmName;
        
        Ext.get('v_update_log').up('.x-form-item').setDisplayed(false);
        Ext.getCmp("v_update_log").hide();
       
        if(operator == 'mod' || operator == 'upgrade' || operator == 'detail' ){
 
            var objq = new Object();
            objq.type = "selAppInfo";
            objq.appId = vMenuId;
            var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
            if(tmpObj!="" ){
                
                Ext.getCmp("v_app_id").setValue(tmpObj.appId) ;
                Ext.getCmp("v_app_name").setValue(tmpObj.appName) ;
                Ext.getCmp("v_app_icon_url").setValue(tmpObj.iconUri) ;
                Ext.getCmp("v_app_version").setValue(tmpObj.versionName) ;
                Ext.getCmp("v_app_size").setValue(tmpObj.appSize) ;
                Ext.getCmp("v_app_key_word").setValue(tmpObj.appKeyWord) ;              
                Ext.getCmp("v_app_intro").setValue(tmpObj.appIntro) ; 
                Ext.getCmp("v_app_snapshot_name").setValue(tmpObj.appSnapshot) ;
                Ext.getCmp("v_app_snapshot").setValue(tmpObj.appSnapshot) ;
                Ext.getCmp("v_download_url").setValue(tmpObj.downloadUrl) ;
                Ext.getCmp("v_versionCode").setValue(tmpObj.versionCode) ;
                Ext.getCmp("v_updateTime").setValue(tmpObj.updateTime) ;
                Ext.getCmp("v_update_log").setValue(tmpObj.updateLog) ;
                                
                sysStore.load({
                    params:{flag:1,start:0, limit:20},
                    callback: function () {
                        //等待数据加载完成才进行赋值，不然由于异步会出现先赋值后加载完成。
                        Ext.getCmp("v_busiSysId").setValue(tmpObj.busiSysId) ;
                    },
                    scope: sysStore,//表示作用范围
                    add: false //为false表示数据不累加
                });
                
                if (operator == 'upgrade'){

                    Ext.get('v_update_log').up('.x-form-item').setDisplayed(true);
                    Ext.getCmp("v_update_log").show();
                }
                                
                if(tmpObj.iconUri != null && tmpObj.iconUri != '' ){
                    if (Ext.isIE) {
                        var image = Ext.get('photoBrowse').dom;
                        image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片
                        image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = tmpObj.iconUri;
                        //image.src = _resultObj.ldapId;
                    }// 支持FF
                    else {
                        //Ext.get('photoBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                    }
                }   
                                
            }   
                       
            if (operator == 'detail'){
                var styles = {
                        'border-width': '0px 0px 0px 0px',
                        'background': 'transparent'
                };
                Ext.getCmp('v_app_name').el.setStyle(styles);
                Ext.getCmp('v_app_icon_url').el.setStyle(styles);
                Ext.getCmp('v_b_app_snapshot').hide();
                Ext.getCmp('v_app_version').el.setStyle(styles);

                Ext.getCmp('v_app_size').el.setStyle(styles);
                Ext.getCmp('v_app_key_word').el.setStyle(styles);
                Ext.getCmp('v_b_download_url').hide();
                
                Ext.getCmp('v_app_intro').el.setStyle(styles);
                Ext.getCmp('v_app_snapshot').el.setStyle(styles);
                Ext.getCmp('v_download_url').el.setStyle(styles);
                
            }
        }
     }
  
     //确定
     this.doSubmit = function () {
         
         if(!Ext.getCmp("infoPage").getForm().isValid()){
             return ;
         }
         var selItem;
         if (osType == 0){
            selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
         }else if (osType == 1){
            selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();      
         }else if (osType == 2){
            selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();      
         }                                                      
         var resultStr ;
         
         switch (flag){
             case "add":{
                 resultStr = '新增成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";                 
                 objq.parentId = vMenuId;
                 objq.menuName = Ext.getCmp("v_app_name").getValue();
                 var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                 if(tmpObj != ""){
                     Ext.MessageBox.show({
                         title: '提示',
                         msg: '该应用名称已存在，请重新指定！',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
                     return;
                 }
                                         
                 var paramObj = new Object();
                 paramObj.type = "add";                                                 
                 paramObj.appId=Ext.getCmp("v_app_id").getValue() ;
                 paramObj.appName=Ext.getCmp("v_app_name").getValue() ;
                 paramObj.iconUri=Ext.getCmp("v_app_icon_url").getValue() ;
                 paramObj.versionName=Ext.getCmp("v_app_version").getValue() ;

                 paramObj.appSize=Ext.getCmp("v_app_size").getValue() ;
                 paramObj.appKeyWord= Ext.getCmp("v_app_key_word").getValue() ;
                 paramObj.osType= osType ;
                 paramObj.appIntro=Ext.getCmp("v_app_intro").getValue() ;
                 paramObj.appSnapshot = Ext.getCmp("v_app_snapshot").getValue() ;
                 paramObj.downloadUrl = Ext.getCmp("v_download_url").getValue() ;
                 paramObj.appPublisher = session1.staff.staffId ;
                 paramObj.appClass = selItem[0].data.mcode ;
                 paramObj.appStatus = "1" ;  
                 paramObj.busiSysId = Ext.getCmp("v_busiSysId").getValue() ;
                 
                                  
                 var retMap = invokeAction("/appmanage/OptAppAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                 
                 var paramObj = new Object();
                 paramObj.type = "mod";                                                 
                 paramObj.appId=Ext.getCmp("v_app_id").getValue() ;
                 paramObj.appName=Ext.getCmp("v_app_name").getValue() ;
                 paramObj.iconUri=Ext.getCmp("v_app_icon_url").getValue() ;
                 paramObj.versionName=Ext.getCmp("v_app_version").getValue() ;
                 paramObj.appSize=Ext.getCmp("v_app_size").getValue() ;
                 paramObj.appKeyWord= Ext.getCmp("v_app_key_word").getValue() ;
                 paramObj.appIntro=Ext.getCmp("v_app_intro").getValue() ;
                 paramObj.appSnapshot = Ext.getCmp("v_app_snapshot").getValue() ;
                 paramObj.downloadUrl = Ext.getCmp("v_download_url").getValue() ;
                 paramObj.versionCode = Ext.getCmp("v_versionCode").getValue() ;
                 paramObj.updateLog = Ext.getCmp("v_update_log").getValue() ;
                 paramObj.updateTime = StringToDate(Ext.getCmp("v_updateTime").getValue()) ;
                 paramObj.busiSysId = Ext.getCmp("v_busiSysId").getValue() ;
                 
                 var retMap = invokeAction("/appmanage/OptAppAction", paramObj);
                 break ;
             }
             case "upgrade":{
                 resultStr = '修改成功！';
                 
                 var paramObj = new Object();
                 paramObj.type = "upgrade";                                                 
                 paramObj.appId=Ext.getCmp("v_app_id").getValue() ;
                 paramObj.appName=Ext.getCmp("v_app_name").getValue() ;
                 paramObj.iconUri=Ext.getCmp("v_app_icon_url").getValue() ;
                 paramObj.versionName=Ext.getCmp("v_app_version").getValue() ;
                 paramObj.appSize=Ext.getCmp("v_app_size").getValue() ;
                 paramObj.appKeyWord= Ext.getCmp("v_app_key_word").getValue() ;
                 paramObj.appIntro=Ext.getCmp("v_app_intro").getValue() ;
                 paramObj.appSnapshot = Ext.getCmp("v_app_snapshot").getValue() ;
                 paramObj.downloadUrl = Ext.getCmp("v_download_url").getValue() ;
                 paramObj.updateLog = Ext.getCmp("v_update_log").getValue() ;
                 paramObj.updateTime = StringToDate(Ext.getCmp("v_updateTime").getValue()) ;
                 paramObj.busiSysId = Ext.getCmp("v_busiSysId").getValue() ;
                 
                 var retMap = invokeAction("/appmanage/OptAppAction", paramObj);
                 break ;
             }

         }
                  
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         moduleWin.close();
         oper.QryMenuGrid();
     }
 
}