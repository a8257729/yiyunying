/*
 * 
 * 菜单-配置开发页面
 */
 var flag ;
 var vMenuId;
 var osType ;
function ApkDev(){
    
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
        var sysStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'sysStore'  
            }, [   
                {name: 'name', mapping: 'sysName'},   
                {name: 'value', mapping: 'sysCode'}   
            ])   
          }); 
          sysStore.load({params:{start:0, limit:7}});
          
        var menuTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'营销高参'],[2,'装维助手'],[3,'看数'],[4,'日常']]});
        var infoPage = new Ext.FormPanel({
            id :'devinfoPage',
            region: 'center',
            labelAlign: 'left',
            frame:true,
            width:Ext.getBody().getSize().width,  
            height:Ext.getBody().getSize().height*0.5,
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
                                         addImageFile();
                                     }
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '图标地址',
                        name: 'v_icon_adr',
                        id: 'v_icon_adr',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '应用分类',
                        name: 'v_muneName',
                        id: 'v_muneName',
                        value:'应用分类',
                        readOnly: true,
                        allowBlank:false, 
                        blankText:"应用分类不能为空!",
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用名称',
                        name: 'v_apk_name',
                        id: 'v_apk_name',
                        allowBlank:false, 
                        blankText:"应用名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'textfield',
                        fieldLabel: '版本号',
                        name: 'v_apk_vision',
                        id: 'v_apk_vision',
                        allowBlank:false, 
                        emptyText: '版本号不能为空!',
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '应用图标',
                        name: 'v_apk_icon',
                        id: 'v_apk_icon',
                        allowBlank:false, 
                        blankText:"应用图标不能为空!",
                        anchor:'90%'    
                    },{
                        xtype:'hidden',
                        fieldLabel: '系统编码',
                        name: 'm_sys_code',
                        id: 'm_sys_code',
                        mode: 'local',
                        value: '',
                        allowBlank: true,
                        anchor:'90%'                           
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '所属分类',
                        name: 'v_menuType',
                        id: 'v_menuType',
                        valueField: 'id',
                        displayField: 'value',
                        mode: 'local',
                        triggerAction: 'all',
                        value: '1',
                        store: menuTypeStore,
                        allowBlank:false, 
                        blankText:"所属分类不能为空!",
                        anchor:'90%'
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用大小',
                        name: 'v_apk_size',
                        id: 'v_apk_size',
                        vtype: 'num' ,
                        allowBlank:false, 
                        blankText:"应用大小不能为空!",
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
                        xtype:'textarea',
                        fieldLabel: '软件介绍内容',
                        name: 'v_apk_content',
                        id: 'v_apk_content',
                        height : 120,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }],
           buttons: [{
               text: '确定',
               id:'btok',
               listeners:{"click":function(){
                   apkDev.doSubmit();
               }}
           },{
               text: '取消',
               listeners:{"click":function(){
                   moduleWin.close();
               }}
           }]
        });
        
                                
        moduleWin = new Ext.Window({
            title: '配置管理',
            closable:true,
            width:750,
            height:300,
            plain:true,
            layout: 'border',
            items: [infoPage]
        });
        
        moduleWin.show(this);
        
        flag = operator;
        vMenuId = pmId;
        osType = pmName;
        if(operator == 'devMod' || operator == 'devDetail'){
            
            var objq = new Object();
            objq.type = "selApkInfo";
            objq.muneId = vMenuId;
            var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
            if(tmpObj!=""  && tmpObj.length >0 ){
                Ext.getCmp("v_apk_name").setValue(tmpObj[0].apkName);
                Ext.getCmp("v_apk_icon").setValue(tmpObj[0].apkIcon);  
                Ext.getCmp("v_apk_size").setValue(tmpObj[0].apkSize);
                Ext.getCmp("v_apk_vision").setValue(tmpObj[0].apkVersionNo);
                Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent); 
                Ext.getCmp("v_icon_adr").setValue(tmpObj[0].iconAdr); 
                if(tmpObj[0].apkIcon != null && tmpObj[0].apkIcon != '' ){
                    if (Ext.isIE) {
                        var image = Ext.get('photoBrowse').dom;
                        image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片
                        image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = tmpObj[0].apkIcon;
                        //image.src = _resultObj.ldapId;
                    }// 支持FF
                    else {
                        //Ext.get('photoBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                    }
                }   
                
                Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent); 
                //Ext.getCmp("m_sys_code").setValue(tmpObj[0].sysCode);                
     
                
            }   
            
            
            if (operator == 'devDetail'){
                Ext.getCmp("btok").hide();
                var styles = {
                        'border-width': '0px 0px 0px 0px',
                        'background': 'transparent'
                    };
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_muneName').el.setStyle(styles);
                Ext.getCmp('v_apk_name').el.setStyle(styles);
                Ext.getCmp('v_apk_icon').el.setStyle(styles);
                Ext.getCmp('m_sys_code').el.setStyle(styles);
                
                Ext.getCmp('v_menuType').el.setStyle(styles);
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_apk_vision').el.setStyle(styles);
                Ext.getCmp('v_apk_content').el.setStyle(styles);
                Ext.getCmp('v_menuType').setDisabled(true);
                Ext.getCmp('m_sys_code').setDisabled(true);
                
            }
        }
     }
                  
     
     this.doSubmit = function () {
         
         if(!Ext.getCmp("devinfoPage").getForm().isValid()){
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
             case "devAdd":{
                 resultStr = '新增成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = selItem[0].data.muneId;
                 objq.muneName = Ext.getCmp("v_apk_name").getValue();
                 var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
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
                                                          
                 var objAttr;
                 var paramObj = new Object();
                 paramObj.parentId = selItem[0].data.muneId ;
                 paramObj.muneName = Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.pathName = selItem[0].data.pathName=='null'?Ext.getCmp("v_apk_name").getValue():selItem[0].data.pathName+"/"+Ext.getCmp("v_apk_name").getValue();
                 paramObj.pathCode = selItem[0].data.pathCode;
                // paramObj.sysCode = Ext.getCmp("m_sys_code").getValue();
                 paramObj.state = "10A";
                 paramObj.displayInedx = 1 ;
                 paramObj.isLeaf = "1";
                 paramObj.type = "devAdd";
                 paramObj.isbg="true" ;
                 paramObj.iconAdr= Ext.getCmp("v_icon_adr").getValue() ;
                 paramObj.menuType = Ext.getCmp("v_menuType").getValue();   
                 paramObj.osType = osType;
                 
                 //暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
                 paramObj.toPage = 'n';
                 paramObj.getMethod = 'n';
                 paramObj.displayType = 'n';                         
                 paramObj.otherSysCode = 'n';
                 paramObj.axisType = 'n';
                 
                 paramObj.apkName=Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.apkIcon=Ext.getCmp("v_apk_icon").getValue() ;
                 paramObj.apkSize=Ext.getCmp("v_apk_size").getValue() ;
                 paramObj.apkVersionNo=Ext.getCmp("v_apk_vision").getValue() ;
                 paramObj.apkContent=Ext.getCmp("v_apk_content").getValue() ;       
                 paramObj.apkState='10A' ;
                 paramObj.apkType = '2' ;  
                 paramObj.forceUpdate ='0';
                                 
                 var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
                 break ;
             }
             case "devMod":{
                 resultStr = '修改成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = selItem[0].data.muneId;
                 objq.muneName = Ext.getCmp("v_apk_name").getValue();
                 var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
                 if(tmpObj!=""){
                     for(var i=0;i<tmpObj.length;i++){
                         if(selItem[0].data.id!=tmpObj[i].ID){
                             Ext.MessageBox.show({
                                title: '提示',
                                msg: '该应用名称已存在，请重新指定！',
                                buttons: Ext.MessageBox.OK,
                                width:200,
                                icon: Ext.MessageBox.ERROR
                             });
                             return;
                         }
                     }             
                 }
                 

                 var selItemMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();        
                 var paramObj = new Object();
                 paramObj.muneId = selItemMenu[0].data.muneId ;             
                 paramObj.muneName = Ext.getCmp("v_apk_name").getValue() ;       
                 paramObj.displayInedx = 1 ;
                 paramObj.isbg="true" ;
                 paramObj.state = "10A";
                 paramObj.iconAdr= Ext.getCmp("v_icon_adr").getValue() ;
                 paramObj.menuType = Ext.getCmp("v_menuType").getValue();
                 
                 //暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
                 paramObj.toPage = 'n';
                 paramObj.getMethod = 'n';
                 paramObj.displayType = 'n';                         
                 paramObj.otherSysCode = 'n';
                 paramObj.axisType = 'n';
                // paramObj.sysCode = Ext.getCmp("m_sys_code").getValue();
                 
                 paramObj.apkId = selItemMenu[0].data.apkId ;  
                 paramObj.apkName = Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.apkIcon = Ext.getCmp("v_apk_icon").getValue() ;
                 paramObj.apkSize = Ext.getCmp("v_apk_size").getValue() ;
                 paramObj.apkVersionNo = Ext.getCmp("v_apk_vision").getValue() ;
                 paramObj.apkContent = Ext.getCmp("v_apk_content").getValue() ;             
                 paramObj.forceUpdate ='0';
                                  
                 paramObj.type = "devMod";
                 var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
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
     
    
     
     // 上传图片类型,在前台的拦截
     var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
     function addImageFile() {
         var fileForm = new Ext.FormPanel({
             region : 'center',
             labelWidth : 55,
             frame : true,
             //bodyStyle : 'padding:5px 5px 0',
             //autoScroll : true,
             border : false,
             fileUpload : true,
             //layout : 'form',
             items : [{
                 xtype : 'textfield',
                 fieldLabel : '选择文件',
                 name : 'upload',
                 id : 'upload',
                 inputType : 'file',
                 allowBlank : false,
                 blankText : '文件不能为空',
                 height : 25,
                 anchor : '80%'
             },{
                 xtype : 'box',
                 id : 'browseImage',
                 fieldLabel : "预览图片",
                 autoEl : {
                     width : 300,
                     height : 350,
                     tag : 'img',
                     // type : 'image',
                     src : Ext.BLANK_IMAGE_URL,
                     style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
                     complete : 'off',
                     id : 'imageBrowse'
                 }
             }],
             listeners : {
                 'render' : function(f) {

                     this.form.findField('upload').on('render', function() {
                     //通過change事件
                         Ext.get('upload').on('change', function(field, newValue, oldValue) {
                             var url =  Ext.get('upload').dom.value;
                     
                             if (img_reg.test(url)) {                
                                 if (Ext.isIE) {
                                     var image = Ext.get('imageBrowse').dom;
                                     image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片

                                     image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
                                 
                                 }// 支持FF
                                 else {
                                     Ext.get('imageBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                                 }
                             }
                         }, this);
                     }, this);
                 }
             },
             buttons : [{
                 text : '上传',
                 type : 'submit',
                 handler : function() {
                     var furl = "";
                     furl = fileForm.form.findField('upload').getValue();
                     var type = furl.substring(furl.lastIndexOf('.')).toLowerCase();
                     var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.lastIndexOf('.'));

                     if (filename == null || filename == "") {
                         alert('请先填写文件名');
                         return;
                     }
                     if (furl == null || furl == "") {
                         return;
                     }
                     
                     if (!img_reg.test(furl)) {
                         alert('仅支持jpg/jepg/png/bmp格式的文件');
                         return;
                     }
                     fileForm.form.submit({
                         url : '../uploadPhotoServlet?fileIdsStr=' + filename+'&type=' + type,
                         waitMsg : '正在上传......',
                         waitTitle : '请等待',
                         method : 'POST',
                         success : function(form, action) {                                                      
                             Ext.getCmp("v_icon_adr").setValue(action.result.fileName);
                             Ext.getCmp("v_apk_icon").setValue(action.result.fileURL);
                             if (Ext.isIE) {
                                 var image = Ext.get('photoBrowse').dom;
                                 //image.src = '\\MOBILE\\download\\photos\\Chrysanthemum_20121108100620.jpg';// 覆盖原来的图片
                                 //image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = action.result.fileURL;
                                 image.src = action.result.fileURL;                                
                             }// 支持FF
                             else {
                                 //Ext.get('photoBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                             }
                             
                             form.reset();
                             win.close();
                         },
                         failure : function(form, action) {
                             form.reset();
                             if (action.failureType == Ext.form.Action.SERVER_INVALID)
                                 Ext.MessageBox.alert('警告', '上传失败，仅支持jpg/jpeg/png/bmp格式的文件');
                         }
                     });
                 }
             }, {
                 text : '关闭',
                 type : 'submit',
                 handler : function() {
                     win.close(this);
                 }
             }]
         });
     
         var win = new Ext.Window( {
             title : "上传图片文件",
             id : 'uploadPhotoWin',
             width : 400,
             height : 450,
             modal : true,
             border : false,
             layout : 'fit',
             items : fileForm
     
         });
         
         win.show();
     
     }
}