/*
 * 
 * 菜单-应用注册页面
 */
 var flag ;
 var vMenuId;
 var apkFlag="";
 var osType ;
function RegOper(){
    
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
          
        var forceUpdateStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'是'],[0,'否']]});  
        var menuTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'营销高参'],[2,'装维助手'],[3,'看数'],[4,'日常']]});
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
                        fieldLabel: '应用ID',
                        name: 'v_apk_id',
                        id: 'v_apk_id',
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
                        xtype:'hidden',
                        fieldLabel: '应用图标',
                        name: 'v_apk_icon',
                        id: 'v_apk_icon',
                        allowBlank:false, 
                        blankText:"应用图标不能为空!",
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
                    },{
                        xtype:'textfield',
                        fieldLabel: 'apk包名',
                        name: 'v_apk_pkg',
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
                                width: '168',
                                blankText:"apk上传!"
                            },{
                                xtype: 'button',
                                id : 'm_b_apk_url',
                                text: '..', 
                                handler: function(){
                                        var fileName = regOper.addFile();
                                }
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
                        xtype:'combo',
                        fieldLabel: '系统编码',
                        name: 'm_sys_code',
                        id: 'm_sys_code',
                        valueField: 'value',
                        displayField: 'value',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: sysStore,
                        allowBlank: false,
                        listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                                  regOper.setApkInf(c.getValue());
                                }
                            }
                        },
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
                        xtype:'textfield',
                        fieldLabel: 'apk编码',
                        name: 'v_apk_code',
                        id: 'v_apk_code',
                        allowBlank:false, 
                        emptyText: 'apk编码不能为空!',
                        anchor:'90%'    
                    },{
                        xtype:'combo',
                        fieldLabel: '是否强制升级',
                        name: 'm_force_update',
                        id: 'm_force_update',
                        valueField: 'id',
                        displayField: 'value',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '0',
                        store: forceUpdateStore,
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
                    text: '确定',
                    listeners:{"click":function(){
                        regOper.doSubmit();
                    }}
                },{
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
       
              
        moduleWin = new Ext.Window({
            title: '应用管理',
            closable:true,
            width:750,
            height:560,
            plain:true,
            layout: 'border',
            items: [infoPage,funGrid]
        });
        
        moduleWin.show(this);
        flag = operator;
        
        
        
        if (flag == 'add' || flag == 'update'  ){
            Ext.getCmp('funGrid').addListener('rowcontextmenu', regOper.rightClickFn2);
            Ext.getCmp('funGrid').addListener('contextmenu', regOper.nullRightClickFn2);
          }
        
        vMenuId = pmId;
        osType = pmName;
        if(operator == 'add'  ){
          //  隐藏label
//            Ext.getCmp("v_apk_pkg").setVisible(true);  
//            Ext.get('v_apk_pkg').up('.x-form-item').setDisplayed(false);
          
        }
        
        
        if(operator == 'mod' || operator == 'detail' || operator == 'update' ){
 
            var objq = new Object();
            objq.type = "selApkInfo";
            objq.muneId = vMenuId;
            var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
            if(tmpObj!=""  && tmpObj.length >0 ){
                Ext.getCmp("v_apk_name").setValue(tmpObj[0].muneName);
                Ext.getCmp("v_apk_icon").setValue(tmpObj[0].apkIcon);
                Ext.getCmp("v_apk_code").setValue(tmpObj[0].apkCode);         
                Ext.getCmp("v_apk_size").setValue(tmpObj[0].apkSize);
                Ext.getCmp("v_apk_vision").setValue(tmpObj[0].apkVersionNo);
                Ext.getCmp("m_apk_url").setValue(tmpObj[0].apkUrl);
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
                
                Ext.getCmp("v_apk_pkg").setValue(tmpObj[0].apkPackage);
                Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent); 
                Ext.getCmp("m_sys_code").setValue(tmpObj[0].sysCode);                
                Ext.getCmp("m_force_update").setValue(tmpObj[0].forceUpdate);     
                
            }   
            
            regOper.QryFunGrid();
            
            if (operator == 'mod'){
                Ext.getCmp('v_apk_code').setDisabled(true);
                Ext.getCmp('m_sys_code').setDisabled(true);
                Ext.getCmp('m_force_update').setDisabled(true);
                
                Ext.getCmp("v_apk_pkg").setDisabled(true);  
                Ext.getCmp("v_apk_vision").setDisabled(true);  
                Ext.getCmp("m_apk_url").setDisabled(true);  
                Ext.getCmp("v_apk_content").setDisabled(true);             
                Ext.getCmp("v_apk_size").setDisabled(true) ;
                Ext.getCmp("m_b_apk_url").hide();
            }
            if ( operator == 'update' ){
                Ext.getCmp('v_apk_code').setDisabled(true);
                Ext.getCmp('m_sys_code').setDisabled(true);
                
            }
            if (operator == 'detail'){
                var styles = {
                        'border-width': '0px 0px 0px 0px',
                        'background': 'transparent'
                    };
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_apk_pkg').el.setStyle(styles);
                Ext.getCmp('btUpload').hide();
                Ext.getCmp('v_muneName').el.setStyle(styles);
                Ext.getCmp('v_apk_name').el.setStyle(styles);
               // Ext.getCmp('v_apk_icon').el.setStyle(styles);
                Ext.getCmp('m_sys_code').el.setStyle(styles);
                Ext.getCmp('m_apk_url').el.setStyle(styles);
                Ext.getCmp('m_b_apk_url').hide();
                
                Ext.getCmp('v_menuType').el.setStyle(styles);
                Ext.getCmp('v_apk_size').el.setStyle(styles);
                Ext.getCmp('v_apk_vision').el.setStyle(styles);
                Ext.getCmp('v_apk_code').el.setStyle(styles);
                Ext.getCmp('v_apk_content').el.setStyle(styles);
                
            }
        }
     }
     
     this.initFunGridMenu =  function(){
         
     }
     
     //定义菜单列表事件
     this.initFunGridEvent = function(){
       
     }
     //选择系统编码
     this.setApkInf = function(vSyscode){
         
         var objq = new Object();
         objq.type = "selBySysCode";
         objq.sysCode = vSyscode;
         var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
         if(tmpObj!=""  && tmpObj.length >0 ){
             
             
             Ext.getCmp("v_apk_id").setValue(tmpObj[0].apkId);  
             Ext.getCmp("v_apk_pkg").setValue(tmpObj[0].apkPackage);  
             Ext.getCmp("v_apk_vision").setValue(tmpObj[0].apkVersionNo);  
             Ext.getCmp("m_apk_url").setValue(tmpObj[0].apkUrl);  
             Ext.getCmp("v_apk_code").setValue(tmpObj[0].apkCode);  
             Ext.getCmp("v_apk_content").setValue(tmpObj[0].apkContent);             
             Ext.getCmp("v_apk_size").setValue(tmpObj[0].apkSize) ;
         
             Ext.getCmp("v_apk_pkg").setDisabled(true);  
             Ext.getCmp("v_apk_vision").setDisabled(true);  
             Ext.getCmp("m_apk_url").setDisabled(true);  
             Ext.getCmp("v_apk_code").setDisabled(true);  
             Ext.getCmp("v_apk_content").setDisabled(true);             
             Ext.getCmp("v_apk_size").setDisabled(true) ;
             Ext.getCmp('m_force_update').setDisabled(true);
             Ext.getCmp("m_b_apk_url").hide();
             

             regOper.QryFunGrid();
             apkFlag ="notOper";
         }else {
             Ext.getCmp("v_apk_id").setValue('');  
             Ext.getCmp("v_apk_pkg").setValue('');  
             Ext.getCmp("v_apk_vision").setValue('');  
             Ext.getCmp("m_apk_url").setValue('');  
             Ext.getCmp("v_apk_code").setValue('');  
             Ext.getCmp("v_apk_content").setValue('');             
             Ext.getCmp("v_apk_size").setValue('') ;
             
             Ext.getCmp("v_apk_pkg").setDisabled(false);  
             Ext.getCmp("v_apk_vision").setDisabled(false);  
             Ext.getCmp("m_apk_url").setDisabled(false);  
             Ext.getCmp("v_apk_code").setDisabled(false);  
             Ext.getCmp("v_apk_content").setDisabled(false);             
             Ext.getCmp("v_apk_size").setDisabled(false) ;
             Ext.getCmp('m_force_update').setDisabled(false);
             Ext.getCmp("m_b_apk_url").show();
             apkFlag ="insert";
         }
       
     }
     this.QryFunGrid = function(){
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
          }else{
              Ext.getCmp('funGrid').store.removeAll() ;
          }
     }
     
     
     //功能右键菜单
     this.rightClickFn2 = function(funGrid,rowIndex,e){
       if (apkFlag == 'insert' || flag == 'update' ){
         var i = 0 ;
         var rightClick = Ext.getCmp('rightClick2');
         rightClick.removeAll();
         
             rightClick.insert(i++,new Ext.menu.Item({ text: '新增功能' ,handler: function() {
                 rightClick.hide();
                 regOper.funcInfoPage('add');
             }}));
             rightClick.insert(i++,new Ext.menu.Item({ text: '修改功能' ,handler: function() {
                 rightClick.hide();
                 regOper.funcInfoPage('mod');
             }}));
          
             rightClick.insert(i++,new Ext.menu.Item({ text: '删除功能' ,handler: function() {
                 rightClick.hide();
                 Ext.MessageBox.confirm('提示', '你确定要删除吗？', regOper.delfuncInfo);
             }}));
         
             Ext.getCmp('funGrid').getSelectionModel().selectRow(rowIndex);
             e.preventDefault();
             rightClick.showAt(e.getXY());
        }
     }
     
     this.nullRightClickFn2 = function(e){
       if (apkFlag == 'insert' || flag == 'update' ){
         var i =0;
        var nullRightClick = Ext.getCmp('nullRightClick2');
         nullRightClick.removeAll();

         nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增功能' ,handler: function() {
                 nullRightClick.hide();
                 regOper.funcInfoPage('add');
         }}));

         e.preventDefault();
         nullRightClick.showAt(e.getXY());
       }
     }
     //删除功能
     this.delfuncInfo  = function(btn){
       if (btn == 'yes'){
         var selItem = Ext.getCmp('funGrid').getSelectionModel().getSelected();
         var store = Ext.getCmp('funGrid').store;
         store.remove(selItem);
       }   
     }
     //功能列表    
     this.funcInfoPage = function(operator){
       
         var funcInfo = new Ext.FormPanel({
             id :'funcInfo',
             region: 'center',
             labelAlign: 'left',
             frame:true,
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
                         fieldLabel: '功能编码',
                         name: 'funCode',
                         id: 'funCode',
                         readOnly: true,
                         emptyText: '将自动生成',
                         anchor:'90%'    
                     }]
                 },{columnWidth:.05,layout: 'form'},{
                     columnWidth:.45,
                     layout: 'form',
                     items: [{
                         xtype:'textfield',
                         fieldLabel: '功能名称',
                         name: 'funName',
                         id: 'funName',
                         allowBlank:false, 
                         blankText:"不能为空!",
                         anchor:'90%'    
                     }]
                }]
             },{
                 xtype:'textfield',
                 fieldLabel: '功能类名',
                 name: 'funClass',
                 id: 'funClass',
                 allowBlank:false, 
                 blankText:"不能为空!",
                 anchor:'90%'    
             }],
             buttons: [{ 
                 text: '确定',
                 listeners:{"click":function(){
                     if(!Ext.getCmp("funcInfo").getForm().isValid()){
                         return ;
                     }
                                                                             
                     var resultStr ;
                     
                     switch (operator){
                         case "add":{         
                                    var funCode = Ext.getCmp("funCode").getValue();
                                    var funName = Ext.getCmp("funName").getValue();
                                    var funClass = Ext.getCmp("funClass").getValue();
                                    var apkCode = Ext.getCmp("v_apk_code").getValue();
                                    var addRecord = Ext.data.Record.create([
                                            {name: 'funCode', type: 'string'},
                                            {name: 'funName', type: 'string'},
                                            {name: 'funClass', type: 'string'},
                                            {name: 'apkCode', type: 'string'}                                  
                                     ]);
                               
                                    var temp = Ext.getCmp("funGrid").getStore();
                                    for (j=0;j<temp.getCount();j++){
                                       if(temp.getAt(j).data.funCode==funCode && funCode !=""){
                                          Ext.MessageBox.show({
                                             title: '提示',
                                             msg: funCode+'已添加！',
                                             buttons: Ext.MessageBox.OK,
                                             width:200,
                                             icon: Ext.MessageBox.ERROR
                                          });
                                          return;
                                       }
                                    }
                                                   
                                  var record = new addRecord({funCode:funCode,funName:funName,funClass:funClass,apkCode:apkCode});  
                                  Ext.getCmp("funGrid").getStore().add(record);
                                  
                                  //如果是应用更新页面调用则允许直接新增功能列表数据
                                  if (flag =="update"){
                                      var paramObj = new Object();
                                      paramObj.type = "funcAdd";
 
                                      paramObj.funCode = funCode;
                                      paramObj.funName = funName;
                                      paramObj.funClass = funClass;
                                      paramObj.apkCode = apkCode;

                                      var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
                                      regOper.QryFunGrid();
                                  }
                               
                             break ;
                         }
                         case "mod":{
                             var selItem = Ext.getCmp('funGrid').getSelectionModel().getSelected();
                             var funCode = Ext.getCmp("funCode").getValue();
                             var funName = Ext.getCmp("funName").getValue();
                             var funClass = Ext.getCmp("funClass").getValue();
                             var apkCode = selItem.data.apkCode;
                             var funId = selItem.data.funId;
                             
                             var addRecord = Ext.data.Record.create([
                                     {name: 'funId', type: 'string'},
                                     {name: 'funCode', type: 'string'},
                                     {name: 'funName', type: 'string'},
                                     {name: 'funClass', type: 'string'},
                                     {name: 'apkCode', type: 'string'}                                  
                              ]);
                             
                             var store = Ext.getCmp('funGrid').store;
                             store.remove(selItem);
                                                               
                             var record = new addRecord({funId:funId,funCode:funCode,funName:funName,funClass:funClass,apkCode:apkCode});  
                             Ext.getCmp("funGrid").getStore().add(record);
                             
                           //如果是应用更新页面调用则允许直接修改功能列表数据
                             if (flag =="update"){
                                 var paramObj = new Object();
                                 paramObj.type = "funcMod";

                                 paramObj.funCode = funCode;
                                 paramObj.funName = funName;
                                 paramObj.funClass = funClass;
                                 paramObj.apkCode = apkCode;

                                 var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
                                 regOper.QryFunGrid();
                             }
                             break ;
                         }
                     }
                     
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
             height:150,
             plain:true,
             layout: 'border',
             items: [funcInfo]
         });
         
         funcWin.show(this);
         
         if(operator == 'mod'){
             var selItem = Ext.getCmp('funGrid').getSelectionModel().getSelected();
             Ext.getCmp("funCode").setValue(selItem.data.funCode);
             Ext.getCmp("funName").setValue(selItem.data.funName);
             Ext.getCmp("funClass").setValue(selItem.data.funClass);                                 
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
                 
                 var selItem1 = Ext.getCmp('funGrid').getSelectionModel().getSelections();
                 if(selItem1 ==null || selItem1.length ==0){
                     Ext.MessageBox.show({
                          title: '提示',
                          msg: '请选择功能编码！',
                          buttons: Ext.MessageBox.OK,
                          width:200,
                          icon: Ext.MessageBox.ERROR
                      });
                      return;
                }
                                         
                 var objAttr;
                 var paramObj = new Object();
                 paramObj.parentId = vMenuId ;
                 paramObj.muneName = Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.pathName = selItem[0].data.pathName=='null'?Ext.getCmp("v_apk_name").getValue():selItem[0].data.pathName+"/"+Ext.getCmp("v_apk_name").getValue();
                 paramObj.pathCode = selItem[0].data.pathCode;
                 paramObj.sysCode = Ext.getCmp("m_sys_code").getValue();
                 paramObj.state = "10A";
                 paramObj.displayInedx = 1 ;
                 paramObj.iconAdr= Ext.getCmp("v_icon_adr").getValue() ;
                 paramObj.isLeaf = "1";
                 paramObj.type = "add";
                 paramObj.isbg="true" ;
                 paramObj.menuType = Ext.getCmp("v_menuType").getValue();   
                 paramObj.osType = osType;//0为安卓，1为ios，2为winphone
                 
                 //暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
                 paramObj.toPage = 'n';
                 paramObj.getMethod = 'n';
                 paramObj.displayType = 'n';                         
                 paramObj.otherSysCode = 'n';
                 paramObj.axisType = 'n';
                 
                 
                 
                 paramObj.apkId=Ext.getCmp("v_apk_id").getValue() ;
                 paramObj.apkName=Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.apkIcon=Ext.getCmp("v_apk_icon").getValue() ;
                 paramObj.apkCode=Ext.getCmp("v_apk_code").getValue() ;
                 paramObj.apkSize=Ext.getCmp("v_apk_size").getValue() ;
                 paramObj.apkVersionNo=Ext.getCmp("v_apk_vision").getValue() ;
                 paramObj.apkUrl=Ext.getCmp("m_apk_url").getValue() ;
                 paramObj.apkContent=Ext.getCmp("v_apk_content").getValue() ;
                 paramObj.apkPackage = Ext.getCmp("v_apk_pkg").getValue() ;
                 paramObj.apkState='10A' ;
                 paramObj.apkType = '1' ;  
                 paramObj.forceUpdate =Ext.getCmp("m_force_update").getValue();
                 paramObj.apkFlag = apkFlag;
                 
                 var ProjectStore =  Ext.getCmp('funGrid').getStore();
                 var selItem3 = ProjectStore.getRange(0,ProjectStore.getCount());
                 var funList = new Array();
                 for(var i=0;i<selItem3.length;i++){
                     var funObj = new Object();
                     funObj.funId = selItem3[i].data.funId;
                     funObj.funCode = selItem3[i].data.funCode;
                     funObj.funName = selItem3[i].data.funName;
                     funObj.funClass = selItem3[i].data.funClass;
                     funObj.apkCode = selItem3[i].data.apkCode;
                     funObj.muneId = selItem3[i].data.muneId;
                     funObj.funcRegStaffId = staffId;
                     
                     if (selItem1[0].data.funClass == selItem3[i].data.funClass){
                         funObj.isSelect = "1";
                         funObj.funcStatus = "1";
                     }
                     funList.push(funObj);
                 }
                 paramObj.funList = funList;
                 
                 var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = vMenuId;
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
                 
                 var selItem1 = Ext.getCmp('funGrid').getSelectionModel().getSelections();
                 if(selItem1 ==null || selItem1.length ==0){
                     Ext.MessageBox.show({
                          title: '提示',
                          msg: '请选择功能编码！',
                          buttons: Ext.MessageBox.OK,
                          width:200,
                          icon: Ext.MessageBox.ERROR
                      });
                      return;
                }
                 var selItemMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();        
                 var paramObj = new Object();
                 paramObj.muneId = selItemMenu[0].data.muneId ;             
                 paramObj.muneName = Ext.getCmp("v_apk_name").getValue() ;       
                 paramObj.displayInedx = 1 ;
                 paramObj.isbg="true" ;
                 paramObj.state = "10A";
                 paramObj.menuType = Ext.getCmp("v_menuType").getValue();
                 paramObj.iconAdr= Ext.getCmp("v_icon_adr").getValue() ;
                 //暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
                 paramObj.toPage = 'n';
                 paramObj.getMethod = 'n';
                 paramObj.displayType = 'n';                         
                 paramObj.otherSysCode = 'n';
                 paramObj.axisType = 'n';
                 paramObj.sysCode = Ext.getCmp("m_sys_code").getValue();
                 
                 paramObj.apkId = selItemMenu[0].data.apkId ;  
                 paramObj.apkName = Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.apkIcon = Ext.getCmp("v_apk_icon").getValue() ;
                 paramObj.apkCode = Ext.getCmp("v_apk_code").getValue() ;
                 paramObj.apkSize = Ext.getCmp("v_apk_size").getValue() ;
                 paramObj.apkVersionNo = Ext.getCmp("v_apk_vision").getValue() ;
                 paramObj.apkUrl = Ext.getCmp("m_apk_url").getValue() ;
                 paramObj.apkContent = Ext.getCmp("v_apk_content").getValue() ;
                 paramObj.apkPackage = Ext.getCmp("v_apk_pkg").getValue() ;                 
                 paramObj.forceUpdate =Ext.getCmp("m_force_update").getValue();
                 paramObj.apkFlag = apkFlag;
                                  
                 var ProjectStore =  Ext.getCmp('funGrid').getStore();
                 var selItem3 = ProjectStore.getRange(0,ProjectStore.getCount());
                 var funList = new Array();
                 for(var i=0;i<selItem3.length;i++){
                     var funObj = new Object();
                     funObj.funId = selItem3[i].data.funId;
                     funObj.funCode = selItem3[i].data.funCode;
                     funObj.funName = selItem3[i].data.funName;
                     funObj.funClass = selItem3[i].data.funClass;
                     funObj.apkCode = selItem3[i].data.apkCode;
                     funObj.muneId = selItemMenu[0].data.muneId;
                     funObj.funcRegStaffId = staffId;
                     funObj.funcStatus = "1";
                     if (selItem1[0].data.funClass == selItem3[i].data.funClass){
                         funObj.isSelect = "1";
                     }
                     funList.push(funObj);
                 }
                 paramObj.funList = funList;

                 paramObj.type = "mod";

                 var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);
                 break ;
             }
             case "update":{
                 resultStr = '修改成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = vMenuId;
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
                 
                 var selItem1 = Ext.getCmp('funGrid').getSelectionModel().getSelections();
                 if(selItem1 ==null || selItem1.length ==0){
                     Ext.MessageBox.show({
                          title: '提示',
                          msg: '请选择功能编码！',
                          buttons: Ext.MessageBox.OK,
                          width:200,
                          icon: Ext.MessageBox.ERROR
                      });
                      return;
                }
                 var selItemMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();        
                 var paramObj = new Object();
                 paramObj.muneId = selItemMenu[0].data.muneId ;             
                 paramObj.muneName = Ext.getCmp("v_apk_name").getValue() ;       
                 paramObj.displayInedx = 1 ;
                 paramObj.isbg="true" ;
                 paramObj.state = "10A";
                 paramObj.menuType = Ext.getCmp("v_menuType").getValue();
                 paramObj.iconAdr= Ext.getCmp("v_icon_adr").getValue() ;
                 //暂时废弃这几个字段  modify by guo.jinjun at 2012-05-04 15:45:33
                 paramObj.toPage = 'n';
                 paramObj.getMethod = 'n';
                 paramObj.displayType = 'n';                         
                 paramObj.otherSysCode = 'n';
                 paramObj.axisType = 'n';
                 paramObj.sysCode = Ext.getCmp("m_sys_code").getValue();
                 
                 paramObj.apkId = selItemMenu[0].data.apkId ;  
                 paramObj.apkName = Ext.getCmp("v_apk_name").getValue() ;
                 paramObj.apkIcon = Ext.getCmp("v_apk_icon").getValue() ;
                 paramObj.apkCode = Ext.getCmp("v_apk_code").getValue() ;
                 paramObj.apkSize = Ext.getCmp("v_apk_size").getValue() ;
                 paramObj.apkVersionNo = Ext.getCmp("v_apk_vision").getValue() ;
                 paramObj.apkUrl = Ext.getCmp("m_apk_url").getValue() ;
                 paramObj.apkContent = Ext.getCmp("v_apk_content").getValue() ;
                 paramObj.apkPackage = Ext.getCmp("v_apk_pkg").getValue() ;                 
                 paramObj.forceUpdate =Ext.getCmp("m_force_update").getValue();
                 
                 var ProjectStore =  Ext.getCmp('funGrid').getStore();
                 var selItem3 = ProjectStore.getRange(0,ProjectStore.getCount());
                 var funList = new Array();
                 for(var i=0;i<selItem3.length;i++){
                     var funObj = new Object();
                     funObj.funId = selItem3[i].data.funId;
                     funObj.funCode = selItem3[i].data.funCode;
                     funObj.funName = selItem3[i].data.funName;
                     funObj.funClass = selItem3[i].data.funClass;
                     funObj.apkCode = selItem3[i].data.apkCode;
                     funObj.muneId = selItemMenu[0].data.muneId;
                     funObj.funcRegStaffId = staffId;
                     
                     if (selItem1[0].data.funClass == selItem3[i].data.funClass){
                         funObj.funcStatus = "4";
                         funObj.isSelect = "1";
                     }
                     funList.push(funObj);
                 }
                 paramObj.funList = funList;
                 

                 paramObj.type = "update";

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
     //上传APK
     this.addFile = function () {
         var fileForm = new Ext.FormPanel({
             region : 'center',
             labelWidth : 55,
             frame : true,
             bodyStyle : 'padding:5px 5px 0',
             autoScroll : true,
             border : false,
             fileUpload : true,
             items : [{
                 xtype : 'textfield',
                 fieldLabel : '选择文件',
                 name : 'userfile',
                 id : 'userfile',
                 inputType : 'file',
                 allowBlank : false,
                 blankText : '文件不能为空',
                 height : 25,
                 anchor : '98%'
             }],
             buttons : [{
                 text : '上传',
                 type : 'submit',
                 handler : function() {
                     var furl = "";
                     furl = fileForm.form.findField('userfile').getValue();
                     var type = furl.substring(furl.length - 4).toLowerCase();
                     var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
                     if (furl == null || furl == "") {
                         return;
                     }
                     if (type != '.apk') {
                         alert('仅支持apk格式的文件');
                         return;
                     }
                     fileForm.form.submit({
                         url : '../addFileServlet?fileIdsStr=' + filename+'&type=.apk',
                         waitMsg : '正在上传......',
                         waitTitle : '请等待',
                         method : 'POST',
                         success : function(form, action) {
                             thisSLoc = self.location.href.split("MOBILEPJ");
                             thisUPage = thisSLoc[0];
                             var element = document.createElement("a");
                             element.src = "/MOBILEPJ/" + action.result.fileURL;
                             element.target = '_blank';
                             element.innerHTML = filename;
                             
                             Ext.getCmp('m_apk_url').setValue(action.result.fileName);
//                           window.returnValue = action.result.fileName;    
//                           win.returnValue = action.result.fileName;   
                             form.reset();
                             win.close();
                         },
                         failure : function(form, action) {
                             form.reset();
                             if (action.failureType == Ext.form.Action.SERVER_INVALID)
                                 Ext.MessageBox.alert('警告', '上传失败，仅支持apk格式的文件');
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
             title : "上传apk文件",
             id : 'fileWin',
             width : 400,
             height : 120,
             modal : true,
             border : false,
             layout : "fit",
             items : fileForm
     
         });
         
         win.show();
     
     }
     
     //上传图标 上传图片类型,在前台的拦截
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

                             Ext.getCmp("v_apk_icon").setValue(action.result.fileURL);
                             Ext.getCmp("v_icon_adr").setValue(action.result.fileName);
                             
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