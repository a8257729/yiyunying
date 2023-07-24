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
 
 
function FuncOper(){
       
    var appStore = new Ext.data.Store({   
        proxy: new Ext.data.HttpProxy({   
            url: '/MOBILE/ExtServlet?actionName=system/AppManagementAction'
        }),   
        reader: new Ext.data.JsonReader({   
        root: 'Body',   
        id: 'appStore'  
        }, [   
            {name: 'name', mapping: 'appName'},   
            {name: 'value', mapping: 'appId'},
             {name: 'busiSysId', mapping: 'busiSysId'}  
        ])   
    });
    
     var funStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/SelAppFunctionAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'funStore'   
            }, [   {name: 'appFuncId', mapping: 'appFuncId'},  
                {name: 'funcName', mapping: 'funcName'},   
                {name: 'funcCode', mapping: 'funcCode'}   ,
                {name: 'funcContent', mapping: 'funcContent'},
                {name: 'appId', mapping: 'appId'} ,
                {name: 'accessPackage', mapping: 'accessPackage'} ,
                {name: 'funcClass', mapping: 'funcClass'} ,
                {name: 'accessClass', mapping: 'accessClass'} ,
                 {name: 'versionCode', mapping: 'versionCode'},
                  {name: 'actionName', mapping: 'actionName'}
            ])
	       
        }); 
        
        
    this.funcInfoPage = function(operator,pmName,pmId,className){
        osType =   pmName;
        vMenuId = pmId;
        flag = operator;
     
        parentMenuName=className;
        var selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
        
        var sysStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/AppManagementAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'sysStore'  
            }, [   
                {name: 'name', mapping: 'mname'},   
                {name: 'value', mapping: 'mcode'}   
            ])   
        }); 
      

        
   //查询菜单类型     
	     var menuTypeStore = new Ext.data.JsonStore({
			id: 'menuTypeStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/MENU_TYPE'
	        }),
	          baseParams:{flag:1},
	         autoLoad :  true,
	          load:function(){
                             Ext.getCmp('menu_type').setValue("集成功能菜单");
                               }
                          
	    });
	
	
        sysStore.load({params:{flag:4,gcode:'SERVER_SYS',start:0, limit:20}});
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
                                          funcOper.addImageFile();
                                     }
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '图标地址',
                        name: 'v_menuIconUrl',
                        id: 'v_menuIconUrl',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '功能名称',
                        name: 'v_functionName',
                        id: 'v_functionName',
                        allowBlank:false, 
                        blankText:"功能名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'combo',
                        fieldLabel: '系统域',
                        name: 'v_app_class',
                        id: 'v_app_class',
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: sysStore,
                        allowBlank: false,
                        listeners: {
                            
                         'change':function(c){
                    	 Ext.getCmp("v_app_id").setValue(''); 
                             
                             // alert(c.getValue()+"  22"+ Ext.getCmp("v_app_id").getValue()+"11 2");
                                  funcOper.setAppInf(c.getValue());
                            }
                            
                        },
                        anchor:'90%'                           
                    },
                    {
                        xtype:'combo',
                        fieldLabel: '功能类型',
                        name: 'menu_type',
                        id: 'menu_type',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'remote',
                        triggerAction: 'all',
                        disabled :true,
                        editable : false ,
                        store: menuTypeStore,
                        allowBlank: false,
                     
                        listeners: {
                         
                            
                        },
                        anchor:'90%'                           
                    }
                    ]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                    xtype:'textfield',
                    fieldLabel: '功能编码',
                    name: 'v_functionCode',
                    id: 'v_functionCode',
                     disabled :true,
                    allowBlank:false, 
                    //emptyText: '功能编码不能为空!',
                    anchor:'90%'    
                  },{
                        xtype:'combo',
                        fieldLabel: '所属应用',
                        name: 'v_app_id',
                        id: 'v_app_id',
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: appStore,
                        allowBlank: false,
                        listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                                }
                            },
                        select :function (dom,filed){
                             if(dom.getValue()!=''){
                               funcOper.setfunInf(dom.getValue());
                               menuObject.busiSysId=filed.data.busiSysId;
                        }}
                        },
                        anchor:'90%'                           
                    },{
                        xtype:'textfield',
                        fieldLabel: '排列顺序',
                        name: 'menu_index',
                        id: 'menu_index',
                        allowBlank:false, 
                        emptyText: '请输入排列顺序!',
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
                          xtype:'combo',
                          fieldLabel: '请选择开放类路径',
                          name: 'v_functionClass',
                          id: 'v_functionClass',
                          allowBlank:false, 
                          emptyText: '开放类路径不能为空!',
                          valueField: 'funcCode',
                          displayField: 'funcName',
                          mode: 'local',
                          triggerAction: 'all',
                          editable : false ,
                          store: funStore,
                           forceSelection: true,
                          anchor:'90%'   , 
                      listeners: {
                          select: function (dom,filed){
                            if(dom.getValue()!=''){
                         
                               Ext.getCmp("v_functionContent").setValue(filed.data.funcContent);
                               menuObject.accessPackage=filed.data.accessPackage;
                               menuObject.accessClass=filed.data.accessClass;
                               menuObject.funcClass=filed.data.funcClass;
                               menuObject.versionCode=filed.data.versionCode;
                               menuObject.appId=filed.data.appId;
                               menuObject.funcCode=filed.data.funcCode;
                               menuObject.appFuncId=filed.data.appFuncId;
                               menuObject.actionName=filed.data.actionName;
                            }
                          }
                            
                        }
                      },{
                        xtype:'textarea',
                        fieldLabel: '功能内容',
                        name: 'v_functionContent',
                        id: 'v_functionContent',
                        height : 120,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }],
           buttons: [{ 
               text: '确定',
               id:'subutten',
               listeners:{"click":function(){
                   if(!Ext.getCmp("funcInfo").getForm().isValid()){
                       return ;
                   }
                   var resultStr ;
                   funcOper.doSubmit();
                   funcWin.close();

               }}
           },{ id:'restbutten',
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
            items: [funcInfo]
        });
        
        funcWin.show(this);
        if(operator == 'mod'){
          	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_functionName").setValue(selGridItem[0].data.menuName);
             var menuAppClass= funcOper.getMenuAppClass();
              if(menuAppClass==null){
            	  Ext.MessageBox.show({
                         title: '提示',
                         msg: '此功能未配置!',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
            	  return
              }
               Ext.getCmp("v_app_class").store.load({params:{flag:4,gcode:'SERVER_SYS',start:0, limit:20},
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_app_class").setValue(menuAppClass[0].appClass);
            	   } 
               });
                 Ext.getCmp("v_app_id").store.load({params:{flag:1,appClass:menuAppClass.appClass,osType:1},
                	                           callback: function(r,opt,t){
            	                                      Ext.getCmp("v_app_id").setValue(menuAppClass[0].appId);
            	                         } });
             Ext.getCmp("v_functionClass").setValue(selGridItem[0].data.menuUri);   
             Ext.getCmp("menu_index").setValue(selGridItem[0].data.menuIndex);   
             Ext.getCmp("v_functionContent").setValue(selGridItem[0].data.menuInfo);
             Ext.getCmp('v_menuIconUrl').setValue(selGridItem[0].data.menuIconURL);
             Ext.getCmp('v_functionCode').setValue(selGridItem[0].data.privCode)
             var imgsrc=selGridItem[0].data.menuIconURL;
          	
           if(imgsrc==null || imgsrc==''){
        	  imgsrc='/MOBILE/appManage/image/001.png';
          }
           Ext.getCmp('photoView').getEl().dom.src=imgsrc;
          //   alert(selGridItem[0].data.menuName)     ;                         
        }
        else if(operator=='detail'){
        	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_functionName").setValue(selGridItem[0].data.menuName);
             var menuAppClass= funcOper.getMenuAppClass();
              if(menuAppClass==null){
            	  Ext.MessageBox.show({
                         title: '提示',
                         msg: '此功能未配置!',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
            	  return
              }
               Ext.getCmp("v_app_class").store.load({params:{flag:4,gcode:'SERVER_SYS',start:0, limit:20},
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_app_class").setValue(menuAppClass[0].appClass);
            	   } 
               });
                 Ext.getCmp("v_app_id").store.load({params:{flag:1,appClass:menuAppClass.appClass,osType:1},
                	                           callback: function(r,opt,t){
            	                                      Ext.getCmp("v_app_id").setValue(menuAppClass[0].appId);
            	                         } });
             Ext.getCmp("v_functionClass").setValue(selGridItem[0].data.menuUri);   
             Ext.getCmp("menu_index").setValue(selGridItem[0].data.menuIndex);   
             Ext.getCmp("v_functionContent").setValue(selGridItem[0].data.menuInfo);
             Ext.getCmp('v_menuIconUrl').setValue(selGridItem[0].data.menuIconURL);
             Ext.getCmp('v_functionCode').setValue(selGridItem[0].data.privCode)
               var imgsrc=selGridItem[0].data.menuIconURL;
          	
           if(imgsrc==null || imgsrc==''){
        	  imgsrc='/MOBILE/appManage/image/001.png';
          }
           Ext.getCmp('photoView').getEl().dom.src=imgsrc;
             Ext.getCmp('subutten').hide();
             Ext.getCmp('restbutten').hide();
        }

     }

    this.setClassinfo = function (id,appid){
       if ( Ext.getCmp(id).getValue() == ""){
            Ext.MessageBox.show({
                title: '提示',
                msg: '请先选择集成功能！',
                buttons: Ext.MessageBox.OK,
                width:200,
                icon: Ext.MessageBox.ERROR
            });
            return;
        }
        appStore.removeAll();
        appStore.load({params:{flag:1,appClass:vysId,osType:osType}});
    }
    
    
    this.setAppInf = function(vysId){
        if ( Ext.getCmp("v_app_class").getValue() == ""){
            Ext.MessageBox.show({
                title: '提示',
                msg: '请先选择系统域！',
                buttons: Ext.MessageBox.OK,
                width:200,
                icon: Ext.MessageBox.ERROR
            });
            return;
        }
        appStore.removeAll();
       //  osType=Ext.getCmp("v_app_class").getValue();
        
        appStore.load({params:{flag:1,appClass:vysId,osType:1}});
    }
    
      this.setfunInf = function(vysId){
        if ( Ext.getCmp("v_app_id").getValue() == ""){
            Ext.MessageBox.show({
                title: '提示',
                msg: '请先选择所属应用！',
                buttons: Ext.MessageBox.OK,
                width:200,
                icon: Ext.MessageBox.ERROR
            });
            return;
        }
        funStore.removeAll();
        funStore.load({params:{flag:1,appId:vysId,osType:osType,selfun:'selfun'}});
    }
  this.addImageFile = function () {
		var fileForm = new Ext.FormPanel({
			region : 'center',
			labelWidth : 55,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			autoScroll : true,
			border : false,
			fileUpload : true,
			
			items : [
				{					
				xtype : 'textfield',
				fieldLabel : '选择图片',
				name : 'imageFile',
				id : 'imageFile',
				inputType : 'file',
				allowBlank : false,
				blankText : '图片不能为空',
				height : 25,
				anchor : '98%'
			}],
			buttons : [{
				text : '上传',
				type : 'submit',
				handler : function() {
					var furl = "";
					furl = fileForm.form.findField('imageFile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					fileForm.form.submit({
						url : '/MOBILE/api/server/upload/image',
						waitMsg : '正在上传......',
						waitTitle : '请等待',
						scope:this,
						method : 'POST',
						success : function(form, action) {
							var jsonData = Ext.util.JSON.decode(action.response.responseText);
							var success = jsonData.success;
							var fileName = jsonData.fileName;
							var filePath = jsonData.filePath;
							var fileSize= jsonData.fileSize;
							Ext.getCmp('v_menuIconUrl').setValue(filePath);
							Ext.getCmp('photoView').getEl().dom.src=filePath;
							form.reset();
							win.close();
						
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，文件格式不对');
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
			title : "上传图片",
			id : 'fileWin',
			width : 400,
			height : 200,
			modal : true,
			border : false,
			layout : "fit",
			items : fileForm
	
		});
		
		win.show();
	
	}
    
          //修改的时候通过菜单id得到菜单的域，和应用，功能
   this.getMenuAppClass =function(){
	   
	    var obji = new Object();
                 obji.type = "selMenuAppClass";
                 obji.menuId = vMenuId;
                 var tmpObji = invokeAction("/appmanage/SelAppMenuAction", obji);
                
	     return tmpObji;
   }

     //确定
     this.doSubmit = function () {
         if(!Ext.getCmp("funcInfo").getForm().isValid()){
             return ;
         }
         osType=1;
         var selItem;
         if (osType == 1){
            selItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();      
         }else if (osType == 2){
            selItem = Ext.getCmp('iosGrid').getSelectionModel().getSelections();      
         }else if (osType == 3){
            selItem = Ext.getCmp('windowGrid').getSelectionModel().getSelections();      
         }                                                      
         var resultStr ;
         
         switch (flag){
             case "add":{
                 resultStr = '新增成功！';
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = vMenuId;
                 objq.menuName = Ext.getCmp("v_functionName").getValue();
                 var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                 if(tmpObj != ""){
                     Ext.MessageBox.show({
                         title: '提示',
                         msg: '该功能名称已存在，请重新指定！',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
                     return;
                 }
                // 判断这个顺序是否存在
                   var obji = new Object();
                 obji.type = "selMaxIndex";
                 obji.parentId = vMenuId;
                 var menuIndex=Ext.getCmp("menu_index").getValue();
                 obji.menuIndex=menuIndex;
                 var tmpObji = invokeAction("/appmanage/SelAppMenuAction", obji);
                 if(parseInt(tmpObji.cindex) !=0 ){
                     Ext.MessageBox.show({
                         title: '提示',
                         msg: '此编号已存在'+tmpObji.cindex+'!',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
                     return;
                 }
                                                          
                 var objAttr;
                 var paramObj = new Object();
               
                 paramObj.parentId = vMenuId ;
                 paramObj.menuName = Ext.getCmp("v_functionName").getValue() ;
                 //paramObj.pathName = selItem[0].data.pathName=='null'?parentMenuName:selItem[0].data.pathName+"/"+Ext.getCmp("v_functionName").getValue();
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_functionName").getValue();
             
                 paramObj.pathCode = selItem[0].data.pathCode;
                 
                 paramObj.menuIconUri= Ext.getCmp("v_menuIconUrl").getValue();
                 paramObj.menuType = "1";
                 paramObj.type = "add"; 
                 paramObj.osType = osType;//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isLeaf=1 //是否是跟节点           
                 paramObj.isMain=1;                                   
                 paramObj.appId=Ext.getCmp("v_app_id").getValue() ;
                 paramObj.appFuncId=menuObject.appFuncId //共能id
                 //paramObj.funcCode=Ext.getCmp("v_functionCode").getValue() ;
                 paramObj.funcName=Ext.getCmp("v_functionName").getValue() ;
               //  paramObj.accessClass=Ext.getCmp("v_functionClass").getValue() ;
           //     paramObj.menuUri=Ext.getCmp("v_functionClass").getValue();
            
                var menuUri=""
                
                     menuUri=(menuObject.accessPackage==null?'':menuObject.accessPackage)+"@"+
                     (menuObject.accessClass==null?'':menuObject.accessClass)+"@"+
                     (menuObject.funcClass==null?'':menuObject.funcClass)+"@"+
                  //   (menuObject.versionCode==null?'':menuObject.versionCode)+"@"+
                     (menuObject.appId==null?'':menuObject.appId)+"@"+
                      (menuObject.busiSysId==null?'':menuObject.busiSysId)+"@"+
                      (menuObject.actionName==null?'':menuObject.actionName);
             
                 paramObj.funcCode=menuObject.funcCode;
                 paramObj.menuUri=menuUri;
                 paramObj.funcContent=Ext.getCmp("v_functionContent").getValue() ;
                 paramObj.menuIndex=Ext.getCmp("menu_index").getValue();
                 paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;
                 
                 var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = vMenuId;
                 objq.menuName = Ext.getCmp("v_functionName").getValue();
                 var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                 if(tmpObj != ""){
                     Ext.MessageBox.show({
                         title: '提示',
                         msg: '该功能名称已存在，请重新指定！',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
                     return;
                 }
                // 判断这个顺序是否存在
                   var obji = new Object();
                 obji.type = "selMaxIndex";
                 obji.parentId = vMenuId;
                 var menuIndex=Ext.getCmp("menu_index").getValue();
                 obji.menuIndex=menuIndex;
                 var tmpObji = invokeAction("/appmanage/SelAppMenuAction", obji);
                 if(parseInt(tmpObji.cindex) !=0 ){
                     Ext.MessageBox.show({
                         title: '提示',
                         msg: '此编号已存在'+tmpObji.cindex+'!',
                         buttons: Ext.MessageBox.OK,
                         width:200,
                         icon: Ext.MessageBox.ERROR
                     });
                     return;
                 }
                       var paramObj = new Object();
                 var selItemMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();  
                paramObj.parentId = selItemMenu[0].data.parentId ;
                 paramObj.menuId=selItemMenu[0].data.menuId;
                 paramObj.menuConfigId=selItemMenu[0].data.menuConfigId;

                 paramObj.menuName = Ext.getCmp("v_functionName").getValue() ;
                 //paramObj.pathName = selItem[0].data.pathName=='null'?parentMenuName:selItem[0].data.pathName+"/"+Ext.getCmp("v_functionName").getValue();
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_functionName").getValue();
                 paramObj.privCode=Ext.getCmp('v_functionCode').getValue();
                 paramObj.pathCode = selItemMenu[0].data.pathCode;
               //  alert(paramObj.parentId);
                 paramObj.menuIconUri= Ext.getCmp("v_menuIconUrl").getValue();
                 paramObj.menuType = "2";
                 paramObj.type = "mod"; 
                 paramObj.osType = osType;//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isLeaf=1 //是否是跟节点           
                 paramObj.isMain=1;                                   
                 paramObj.appId=Ext.getCmp("v_app_id").getValue() ;
                 //paramObj.funcCode=Ext.getCmp("v_functionCode").getValue() ;
                 paramObj.funcName=Ext.getCmp("v_functionName").getValue() ;
               //  paramObj.accessClass=Ext.getCmp("v_functionClass").getValue() ;
           //     paramObj.menuUri=Ext.getCmp("v_functionClass").getValue();
                var menuUri=""
                
                     menuUri=(menuObject.accessPackage==null?'':menuObject.accessPackage)+"@"+
                   ( menuObject.accessClass==null?'':menuObject.accessClass)+"@"+
                   (menuObject.funcClass==null?'':menuObject.funcClass)+"@"+
               //    ( menuObject.versionCode==null?'':menuObject.versionCode)+"@"+
                   (  menuObject.appId==null?'':menuObject.appId)+"@"+
                  (menuObject.busiSysId==null?'':menuObject.busiSysId)+"@"+
                  (menuObject.actionName==null?'':menuObject.actionName);
                paramObj.funcCode=menuObject.funcCode;
                paramObj.menuUri=menuUri;
                paramObj.appFuncId=menuObject.appFuncId;
                 paramObj.funcContent=Ext.getCmp("v_functionContent").getValue() ;
                // paramObj.menuType=Ext.getCmp("menu_type").getValue() ;
                 paramObj.menuIndex=Ext.getCmp("menu_index").getValue();
                 paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;
                 var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
                 break ;
             }
         }
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         funcWin.close();
         oper.QryMenuGrid();
     }
  
}