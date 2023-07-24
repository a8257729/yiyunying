/*
 * 
 * 菜单-功能注册页面
 */
 var flag ;
 var vMenuId;
 var apkFlag="";
 var osType ;
 var parentMenuName;
function unFuncOper(){
       
    var appStore = new Ext.data.Store({   
        proxy: new Ext.data.HttpProxy({   
            url: '/MOBILE/ExtServlet?actionName=system/AppManagementAction'
        }),   
        reader: new Ext.data.JsonReader({   
        root: 'Body',   
        id: 'appStore'  
        }, [   
            {name: 'name', mapping: 'appName'},   
            {name: 'value', mapping: 'appId'}   
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
	          baseParams:{flag:1}
	    });
	
        sysStore.load({params:{flag:4,gcode:'SERVER_SYS',start:0, limit:20}});
        var unfuncInfo = new Ext.FormPanel({
            id :'unfuncInfo',
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
                                 id : 'unphotoView',
                                 
                                 autoEl : {
                                     width: 50,
                                     height: 50,
                                     tag: 'img',
                                     src: 'image/001.png',
                                     style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
                                     complete : 'off',
                                     id : 'unphotoBrowse'
                                 }
                             },{
                                     xtype: 'button',
                                     id:'unbtUpload',
                                     text: '上传图标',
                                     handler: function(){
                                        unfuncOper.unaddImageFile();
                                     }
                             }]                                                  
                     }]                
                   },{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'hidden',
                        fieldLabel: '图标地址',
                        id: 'unv_menuIconUrl',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '功能名称',
                        name: 'v_menuName',
                        id: 'v_menuName',
                        allowBlank:false, 
                        blankText:"功能名称不能为空!",
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
                          xtype:'textfield',
                          fieldLabel: '功能路径',
                          name: 'v_menuClass',
                          id: 'v_menuClass',
                          allowBlank:false, 
                          emptyText: '功能路径不能为空!',
                          anchor:'90%'    
                      },{
                        xtype:'textarea',
                        fieldLabel: '功能内容描述',
                        name: 'v_menuContent',
                        id: 'v_menuContent',
                        height : 120,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }],
           buttons: [{ 
        	   id:'unsubutten',
               text: '确定',
               listeners:{"click":function(){
                   if(!Ext.getCmp("unfuncInfo").getForm().isValid()){
                       return ;
                   }
                   var resultStr ;
                   unfuncOper.doSubmit();
                   funcWin.close();

               }}
           },{
        	   id:'unrestbutten',
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
            items: [unfuncInfo]
        });
        
        funcWin.show(this);
        
        if(operator == 'mod'){
          	var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_menuName").setValue(selGridItem[0].data.menuName);
               Ext.getCmp("menu_type").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("menu_type").setValue(selGridItem[0].data.menuType);
            	   } 
               });
          
            Ext.getCmp("v_menuClass").setValue(selGridItem[0].data.menuUri);   
            Ext.getCmp("menu_index").setValue(selGridItem[0].data.menuIndex);   
             Ext.getCmp("v_menuContent").setValue(selGridItem[0].data.menuInfo);   
               Ext.getCmp("unv_menuIconUrl").setValue(selGridItem[0].data.menuIconURL);   
                  }
             else if(operator=='detail'){
            	 var selGridItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_menuName").setValue(selGridItem[0].data.menuName);
               Ext.getCmp("menu_type").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("menu_type").setValue(selGridItem[0].data.menuType);
            	   } 
               });
          
            Ext.getCmp("v_menuClass").setValue(selGridItem[0].data.menuUri);   
            Ext.getCmp("menu_index").setValue(selGridItem[0].data.menuIndex);   
             Ext.getCmp("v_menuContent").setValue(selGridItem[0].data.menuInfo);   
               Ext.getCmp("unv_menuIconUrl").setValue(selGridItem[0].data.menuIconURL);   
               Ext.getCmp("unsubutten").hide();
               Ext.getCmp("unrestbutten").hide();
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
        appStore.load({params:{flag:1,appClass:vysId,osType:osType}});
    }
this.unaddImageFile = function () {
		var unfileForm = new Ext.FormPanel({
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
					furl = unfileForm.form.findField('imageFile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					unfileForm.form.submit({
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
							Ext.getCmp('unv_menuIconUrl').setValue(filePath);
							Ext.getCmp('unphotoView').getEl().dom.src=filePath;
							unFuncImagePath=filePath;
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
			id : 'unfileWin',
			width : 400,
			height : 200,
			modal : true,
			border : false,
			layout : "fit",
			items : [unfileForm]
	
		});
		
		win.show();
	
	}

     //确定
     this.doSubmit = function () {
         if(!Ext.getCmp("unfuncInfo").getForm().isValid()){
         
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
                 objq.menuName = Ext.getCmp("v_menuName").getValue();
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
                 paramObj.menuName = Ext.getCmp("v_menuName").getValue() ;
                 paramObj.pathCode = selItem[0].data.pathCode;
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_menuName").getValue();  
                 paramObj.menuIconUri= Ext.getCmp("unv_menuIconUrl").getValue() ;
                 paramObj.type = "add"; 
                 paramObj.osType = osType;//0为安卓，1为ios，2为winphone
                 paramObj.privType = 2; //1为集成类，2为内置功能
                 paramObj.isLeaf=1 //是否是跟节点                                                  
                 paramObj.menuName=Ext.getCmp("v_menuName").getValue() ;
                 paramObj.menuUri=Ext.getCmp("v_menuClass").getValue() ;
                 paramObj.funcContent=Ext.getCmp("v_menuContent").getValue() ;
                 paramObj.menuType=Ext.getCmp("menu_type").getValue() ;
                 paramObj.menuIntro=Ext.getCmp("v_menuContent").getValue();
                 paramObj.isMain=1;
                 paramObj.menuIndex=Ext.getCmp("menu_index").getValue();
                 paramObj.state=1 ;
                 paramObj.staffId= session1.staff.staffId ;
                // paramObj.menuIconUri=unFuncImagePath;
                 var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                    
                 var objq = new Object();
                 objq.type = "selIsExist";
                 objq.parentId = vMenuId;
                 objq.menuName = Ext.getCmp("v_menuName").getValue();
                 var tmpObj = invokeAction("/appmanage/SelAppMenuAction", objq);
                 if(tmpObj!=""){
                     for(var i=0;i<tmpObj.length;i++){
                         if(selItem[0].data.id!=tmpObj[i].ID){
                             Ext.MessageBox.show({
                                title: '提示',
                                msg: '该功能名称已存在，请重新指定！',
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
                 paramObj.parentId = selItemMenu[0].data.parentId ;
                 paramObj.menuId=selItemMenu[0].data.menuId;
                 paramObj.menuConfigId=selItemMenu[0].data.menuConfigId;
                 paramObj.menuName = Ext.getCmp("v_menuName").getValue() ;
                 paramObj.pathCode = selItem[0].data.pathCode+"/"+paramObj.menuId;
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_menuName").getValue();  
                 paramObj.menuIconUri= Ext.getCmp("unv_menuIconUrl").getValue() ;
                 paramObj.type = "mod"; 
                 paramObj.osType = osType;//0为安卓，1为ios，2为winphone
                 paramObj.privType = 2; //1为集成类，2为内置功能
                  paramObj.privCode="MENU_"+paramObj.menuId;
                 paramObj.isLeaf=1 //是否是跟节点                                                  
                  paramObj.menuIntro=Ext.getCmp("v_menuContent").getValue();
                 paramObj.menuName=Ext.getCmp("v_menuName").getValue() ;
                 paramObj.menuUri=Ext.getCmp("v_menuClass").getValue() ;
                 paramObj.funcContent=Ext.getCmp("v_menuContent").getValue() ;
                 paramObj.menuType=Ext.getCmp("menu_type").getValue() ;
                 paramObj.isMain=1;
                 paramObj.menuIndex=Ext.getCmp("menu_index").getValue();
                 paramObj.state=1 ;
               //  paramObj.menuIconUri=unFuncImagePath;
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