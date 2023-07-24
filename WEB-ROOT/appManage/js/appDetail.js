/*
 * 
 * 菜单-应用详情页面
 */
 var flag ;
 var vMenuId;
 var osType ;
function DetailOper(){
       
     this.showModuleInfoPage = function(operator,pmName,pmId){
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
                  
        var infoPage = new Ext.FormPanel({
            id :'infoPage',
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
          }]
        });
        
        var logColumn = new Ext.grid.ColumnModel([
             {header:'appHisId',dataIndex:'appHisId',hidden:true },                                     
             {header:'appId',dataIndex:'appId',hidden:true },                        
             {header:'appClass',dataIndex:'appClass',hidden:true },
             {header:'appSnapshot',dataIndex:'appSnapshot',hidden:true },    
             {header:'osType',dataIndex:'osType',hidden:true },  
             {header:'内部版本号',dataIndex:'versionCode',hidden:true },
             {header:'应用发布人id',dataIndex:'appPublisher',hidden:true},    
             {header:'应用状态',dataIndex:'appStatus',hidden:true },         
             {header:'应用下载链接',dataIndex:'downloadUrl',hidden:true },
             {header:'二维码下载链接',dataIndex:'qrcodeUrl',hidden:true },
             {header:'应用图标',dataIndex:'iconUri',width:80 ,hidden:true},
             {header:'应用名称',dataIndex:'appName',width:100 },                                     
             {header:'应用版本',dataIndex:'versionName',width:80 },
             {header:'应用大小',dataIndex:'appSize',width:80}, 
             {header:'应用关键字',dataIndex:'appKeyWord',width:80},
             {header:'应用状态',dataIndex:'appStatusName',width:80 },            
             {header:'应用下载次数',dataIndex:'downloadCount',width:80},   
             {header:'应用介绍',dataIndex:'appIntro',width:250},  
             {header:'更新日志',dataIndex:'updateLog',width:250},  
             {header:'应用发布人',dataIndex:'staffName',width:80},        
             {header:'更新时间',dataIndex:'updateTime',width:120}           
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
           url: '/MOBILE/ExtServlet?actionName=system/AppManagementAction',
                                                                                    
           sm: new Ext.grid.RowSelectionModel({
              singleSelect: true,
              listeners: {
                 rowselect: function(sm, row, rec){
                                                            
                       }
                 }
              })
          });      
                                              
          var detailTabs = new Ext.TabPanel({
              region: 'center',
              id : 'detailTabs',
              activeTab: 0,

              width:Ext.getBody().getSize().width*0.85,   
              height:Ext.getBody().getSize().height*0.4, 
              plain:true,
              defaults:{autoScroll: true},
              items:[logGrid],
              listeners: {
                 tabchange: function(){
                    var activeTab = detailTabs.activeTab.id ;
                    switch (activeTab){
//                     case "funGrid":
//                     {
//                        oper.QryTemplateGrid();
//                        break ;
//                     }
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
            height:600,
            plain:true,
            layout: 'border',
            items: [infoPage,detailTabs]
        });
        
        moduleWin.show(this);
        flag = operator;
                             
        vMenuId = pmId;
        osType = pmName;
              
        if(operator == 'detail' ){
 
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
                       
                var styles = {
                        'border-width': '0px 0px 0px 0px',
                        'background': 'transparent'
                };
                Ext.getCmp('v_app_name').el.setStyle(styles);
                Ext.getCmp('v_app_icon_url').el.setStyle(styles);               
                Ext.getCmp('v_app_version').el.setStyle(styles);
                Ext.getCmp('v_app_size').el.setStyle(styles);
                Ext.getCmp('v_app_key_word').el.setStyle(styles);                                        
                Ext.getCmp('v_app_intro').el.setStyle(styles);
                Ext.getCmp('v_update_log').el.setStyle(styles);
                Ext.getCmp('v_app_snapshot').el.setStyle(styles);
                Ext.getCmp('v_app_snapshot_name').el.setStyle(styles);                
                Ext.getCmp('v_download_url').el.setStyle(styles);
                Ext.getCmp('v_b_download_url').hide();
                Ext.getCmp('btUpload').hide();  
                Ext.getCmp('v_b_app_snapshot').hide();

        }
               
            Ext.getCmp('logGrid').store.on('beforeload',
                function(store){ 
                    if(Ext.getCmp('logGrid').store.lastOptions != null){
                       Ext.getCmp('logGrid').store.baseParams = {flag:5,appId:Ext.getCmp("v_app_id").getValue()};
                    }
                }
            )
            Ext.getCmp('logGrid').store.removeAll() ;
            Ext.getCmp('logGrid').store.load({params:{start:0, limit:16}});

     }
  
     
 
}