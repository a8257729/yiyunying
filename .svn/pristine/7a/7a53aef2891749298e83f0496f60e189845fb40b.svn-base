/*
 * 
 * 菜单-应用详情页面
 */
 var flag ;
 var vMenuId;
function ApkDetailOper(){
    
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
        var forceUpdateStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});     
        var infoPage = new Ext.FormPanel({
            id :'detailInfoPage',
            region: 'north',
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
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '应用分类',
                        name: 'v_muneName',
                        id: 'v_muneName',
                        value:pmName,
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用名称',
                        name: 'v_apk_name',
                        id: 'v_apk_name',
                        readOnly: true,
                        allowBlank:false, 
                        blankText:"应用名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用大小',
                        name: 'v_apk_size',
                        id: 'v_apk_size',
                        readOnly: true,
                        allowBlank:false, 
                        blankText:"应用大小不能为空!",
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
                        xtype:'textfield',
                        fieldLabel: 'apk包名',
                        name: 'v_apk_pkg',
                        readOnly: true,
                        id: 'v_apk_pkg',
                        allowBlank: false,
                        anchor:'90%'
                    },{
                        xtype: 'compositefield',
                        fieldLabel: 'apk上传',
                        anchor:'100%',
                        items: [{
                                xtype: 'textfield',
                                name: 'm_apk_url',
                                id: 'm_apk_url',
                                allowBlank:false, 
                                readOnly: true, 
                                width: '180',
                                blankText:"apk上传!"
                            }]
                    }]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '所属分类',
                        name: 'v_menuType',
                        id: 'v_menuType',
                        readOnly: true,
                        allowBlank:false, 
                        blankText:"所属分类不能为空!",
                        anchor:'90%'
                    },{
                        xtype:'textfield',
                        fieldLabel: '系统编码',
                        name: 'm_sys_code',
                        id: 'm_sys_code',                       
                        value: '',
                        readOnly: true,
                        allowBlank: false,
                        anchor:'90%'                           
                    },{
                        xtype:'textfield',
                        fieldLabel: '版本号',
                        name: 'v_apk_vision',
                        id: 'v_apk_vision',
                        readOnly: true,
                        allowBlank:false, 
                        emptyText: '版本号不能为空!',
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: 'apk编码',
                        name: 'v_apk_code',
                        id: 'v_apk_code',
                        readOnly: true,
                        allowBlank:false, 
                        emptyText: 'apk编码不能为空!',
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '是否强制升级',
                        name: 'm_force_update',
                        id: 'm_force_update',
                        value: '',
                        readOnly: true,
                        allowBlank: true,
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
                        readOnly: true,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }]
        });
        
                    
       var funColumn = new Ext.grid.ColumnModel([               
                {header:'功能ID',dataIndex:'funId',hidden:true },
                {header:'菜单ID',dataIndex:'muneId',hidden:true },
                {header:'APK编码',dataIndex:'apkCode',hidden:true },       
                {header:'功能编码',dataIndex:'funCode',width:100 },
                {header:'功能名称',dataIndex:'funName',width:150},
                {header:'功能类名',dataIndex:'funClass',width:300 },
                {header:'对应应用名称',dataIndex:'muneName',width:100 }
       ]);   
        
       var funGrid = new ZTESOFT.Grid({        
                id: 'funGrid',
                region: 'center',   
                width:Ext.getBody().getSize().width,
                height:Ext.getBody().getSize().height*0.5,              
                title:'功能列表',
                cm:funColumn,
                fbar:[{
                    text: '取消',
                    listeners:{"click":function(){
                        moduleWin.close();
                    }}
                }],
                buttonAlign: 'center',
                pageSize:10,
                paging:true,
                url: '/MOBILE/ExtServlet?actionName=system/AppClassManageAction',
            
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: function(sm, row, rec){
                        }
                    }
                })
         });
       
       var logColumn = new Ext.grid.ColumnModel([
            {header:'apkID',dataIndex:'apkId',hidden:true },
            {header:'应用名称',dataIndex:'apkName',width:100 },       
            {header:'应用大小',dataIndex:'apkSize',width:100 },
            {header:'版本号',dataIndex:'apkVersionNo',width:100 },
            {header:'软件介绍内容',dataIndex:'apkContent',width:150},
            {header:'创建时间',dataIndex:'createDate',width:150}

       ]);   
                                         
       var logGrid = new ZTESOFT.Grid({        
           id: 'logGrid',
           region: 'center',   
           width:Ext.getBody().getSize().width,
           height:Ext.getBody().getSize().height*0.5,              
           title:'版本信息列表',
           cm:logColumn,
           fbar:[{
               text: '取消',
               listeners:{"click":function(){
                  moduleWin.close();
               }}
           }],
           buttonAlign: 'center',
           pageSize:10,
           paging:true,
           url: '/MOBILE/ExtServlet?actionName=system/SelApkVersionInfoAction',
                                             
           sm: new Ext.grid.RowSelectionModel({
              singleSelect: true,
              listeners: {
                 rowselect: function(sm, row, rec){
                     
                 }
              }
           })
        });      
       
       var tabs = new Ext.TabPanel({
           region: 'center',
           id : 'tabs',
           activeTab: 0,

           width:Ext.getBody().getSize().width*0.85,   
           height:Ext.getBody().getSize().height*0.4, 
           plain:true,
           defaults:{autoScroll: true},
           items:[funGrid,logGrid],
                       listeners: {
               tabchange: function(){
                   var activeTab = tabs.activeTab.id ;
                   switch (activeTab){
                       case "funGrid":
                       {
                           //oper.QryTemplateGrid();
                           break ;
                       }
                       case "logGrid":
                       {
                           //oper.QryTemplateGrid();
                           break ;
                       }                       
                   }   
               }
       }
       });
              
        moduleWin = new Ext.Window({
            title: '应用详情',
            closable:true,
            width:750,
            height:560,
            plain:true,
            layout: 'border',
            items: [infoPage,tabs]
        });
        
        moduleWin.show(this);
        
        flag = operator;
        vMenuId = pmId;
        if(operator == 'mod' || operator == 'detail'){
 
            var objq = new Object();
            objq.type = "selApkInfo";
            objq.muneId = vMenuId;
            var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
            if(tmpObj!=""  && tmpObj.length >0 ){
                Ext.getCmp("v_apk_name").setValue(tmpObj[0].apkName);
                Ext.getCmp("v_apk_icon").setValue(tmpObj[0].apkIcon);
                Ext.getCmp("v_apk_code").setValue(tmpObj[0].apkCode);         
                Ext.getCmp("v_apk_size").setValue(tmpObj[0].apkSize);
                Ext.getCmp("v_apk_vision").setValue(tmpObj[0].apkVersionNo);
                Ext.getCmp("m_apk_url").setValue(tmpObj[0].apkUrl);
                Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent); 

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
                
                Ext.getCmp("v_apk_pkg").setValue(tmpObj[0].apkPackage);
                Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent); 
                Ext.getCmp("m_sys_code").setValue(tmpObj[0].sysCode);  
                if (tmpObj[0].forceUpdate =="1"){
                   Ext.getCmp("m_force_update").setValue('是');      
                }else if (tmpObj[0].forceUpdate =="0"){
                   Ext.getCmp("m_force_update").setValue('否');      
                }else {
                   Ext.getCmp("m_force_update").setValue('');      
                } 
                

                
            }   
            if (Ext.getCmp("v_apk_code").getValue() != ""){
                    Ext.getCmp('funGrid').store.on('beforeload',
                        function(store){ 
                            if(Ext.getCmp('funGrid').store.lastOptions != null){
                               Ext.getCmp('funGrid').store.baseParams = {flag:3,apkCode:Ext.getCmp("v_apk_code").getValue()};
                            }
                        }
                    )
                    Ext.getCmp('funGrid').store.removeAll() ;
                    Ext.getCmp('funGrid').store.load({params:{start:0, limit:16}});
            }
            
            if (tmpObj[0].apkId != ""){
                Ext.getCmp('logGrid').store.on('beforeload',
                     function(store){ 
                         if(Ext.getCmp('logGrid').store.lastOptions != null){
                            Ext.getCmp('logGrid').store.baseParams = {apkId:tmpObj[0].apkId};
                         }
                     }
                 )
                 Ext.getCmp('logGrid').store.removeAll() ;
                 Ext.getCmp('logGrid').store.load({params:{start:0, limit:16}});
             }
            
            if (operator == 'detail'){
                var styles = {
                        'border-width': '0px 0px 0px 0px',
                        'background': 'transparent'
                    };
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_apk_pkg').el.setStyle(styles);
                Ext.getCmp('v_muneName').el.setStyle(styles);
                Ext.getCmp('v_apk_name').el.setStyle(styles);
                Ext.getCmp('v_apk_icon').el.setStyle(styles);
                Ext.getCmp('m_sys_code').el.setStyle(styles);
                Ext.getCmp('m_force_update').el.setStyle(styles);
                
                Ext.getCmp('m_apk_url').el.setStyle(styles);
                
                Ext.getCmp('v_menuType').el.setStyle(styles);
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_apk_vision').el.setStyle(styles);
                Ext.getCmp('v_apk_code').el.setStyle(styles);
                Ext.getCmp('v_apk_content').el.setStyle(styles);
                
            }
        }
     }
     
     
         
     
}