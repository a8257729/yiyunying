/*
 * 
 * 菜单-应用注册页面
 */
 var flag ;
 var vMenuId;
 var apkFlag="";
var parentMenuName;
 var osType ;
 

 var menuObject=new Object();
 
 var appId;
function AppOper(){
       
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
    
     var funStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/SelAppFunctionAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'funStore'  
            }, [   
                {name: 'funcName', mapping: 'funcName'},   
                {name: 'funcCode', mapping: 'funcCode'}   ,
                {name: 'funcContent', mapping: 'funcContent'},
                {name: 'appId', mapping: 'appId'} ,
                {name: 'accessPackage', mapping: 'accessPackage'} ,
                {name: 'funcClass', mapping: 'funcClass'} ,
                {name: 'accessClass', mapping: 'accessClass'} ,
                 {name: 'versionCode', mapping: 'versionCode'}
            ])
	       
        }); 
   
            //应用分类
	     var appClassStore = new Ext.data.Store({
			
	         proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/AppManagementAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
           id: 'appClassStore'
            }, [   
                {name: 'value', mapping: 'menuId'},   
                {name: 'name', mapping: 'menuName'}   
            ])   
                          
	    });
	     
	      //应用分类
	     var sysFieldType = new Ext.data.Store({
			
	         proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/SelAppFunctionAction&seleAppType=select'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
           id: 'sysFieldType',
            autoLoad : true
            }, [   
                {name: 'value', mapping: 'mcode'},   
                {name: 'name', mapping: 'mname'}   
            ])   
                          
	    });

	     
	     
	 this.setSysInf = function (vysId){
	//	 alert(session1.staff.staffId);
        if ( Ext.getCmp("v_appClass").getValue() == ""){
            Ext.MessageBox.show({
                title: '提示',
                msg: '请先选择所属应用！',
                buttons: Ext.MessageBox.OK,
                width:200,
                icon: Ext.MessageBox.ERROR
            });
            return;
        }
        sysFieldType.removeAll();
        var appClass= Ext.getCmp("v_appClass").getValue();
          // appTypeStore.load({params:{flag:3,osType:'SERVER_SYS'}});
       sysFieldType.load({params:{flag:3,appClass:appClass,seleAppType:'select',start:0, limit:20}});
    	 
     }
    this.funcInfoPage = function(operator,pmName,pmId,className,appid){
        osType =   pmName;
        vMenuId = pmId;
        flag = operator;
     appId=appid;
        parentMenuName=className;
        
         var sysStore = new Ext.data.JsonStore({   
            id:'sysStore',
	    	remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/APP_OS_TYPE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
         
   //应用状态     
	     var appStatusStore = new Ext.data.JsonStore({
			id: 'appStatusStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/APP_STATUS'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	//应用类型
	
	       var appTypeStore = new Ext.data.JsonStore({   
            id: 'appTypeStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SERVER_SYS'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
        }); 
	     
        var funcInfo = new Ext.FormPanel({
            id :'funcInfo',
            region: 'north',
            labelAlign: 'left',
            frame:true,
            title: '表单',
	        bodyStyle:'padding:5px 5px 0',
            buttonAlign: 'center',
            labelWidth: 70,
            height:290,
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
                        name: 'v_menuIconUrl',
                        id: 'v_menuIconUrl',
                        value:'',
                        readOnly: true,
                        allowBlank:true, 
                        anchor:'90%'    
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用名称',
                        name: 'v_functionName',
                        id: 'v_functionName',
                        allowBlank:false, 
                        blankText:"应用名称不能为空!",
                        anchor:'90%'        
                    },{
                        xtype:'combo',
                        fieldLabel: '操作系统',
                        name: 'os_Type',
                        id: 'os_Type',
                        valueField: 'mcode',
	                    displayField: 'mname',
	                    mode:'remote',
	                    triggerAction: 'all',
	                    forceSelection: true,
				    	editable :false,
				    	anchor:'90%',
	                    store:sysStore,
	                     listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                             
                                }
                            }
                        }
                    },{
                        xtype:'combo',
                        fieldLabel: '应用分类',
                        name: 'v_appClass',
                        id: 'v_appClass',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        store: appTypeStore,
                        value:'',
                        anchor:'90%' ,
                        listeners: {
                            collapse : function(c){
                                if (c.getValue() != ""){
                              appOper.setSysInf(c.getValue());
                                }
                            }
                        }
                       
                    } 
                   ,
                       { xtype:'textfield', 
                    	fieldLabel: '外部版本号',
                         name: 'versionName',
                         id: 'versionName',
                         blankText:"功能名称不能为空!",
                         triggerAction: 'all',
                         editable : true ,
                         anchor:'90%'                           
                    }
                    ]
                },{columnWidth:.05,layout: 'form'},{
                    columnWidth:.4,
                    layout: 'form',
                    items: [{
                        xtype:'combo',
                        fieldLabel: '应用状态',
                        name: 'v_app_status',
                        id: 'v_app_status',
                        valueField: 'mcode',
                        displayField: 'mname',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        store: appStatusStore,
                        anchor:'90%'                           
                    },
                     {
                    xtype: 'compositefield',
					fieldLabel: '上传文件',
                	anchor:'100%',
					items: [{
						xtype: 'textfield',
            	    	name: 'appFilePath',
                	  	id: 'appFilePath',
					  	allowBlank:false, 
					 	readOnly: true, 
					 	disabled: true,
					 	width: '180',
                		blankText:"上传文件不能为空!"
					    },{
					    xtype: 'button',
					    id : 'appFilePath_url',
					    text: '..', 
					    handler: function(){
					    		var fileName = appOper.addFile();
							}
					}]
                },{
                        xtype:'combo',
                        fieldLabel: '应用类型',
                        name: 'v_appType',
                        id: 'v_appType',
                        valueField: 'value',
                        displayField: 'name',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        store: sysFieldType,
                        anchor:'90%'                           
                    },{
                        xtype:'textfield',
                        fieldLabel: '应用大小(MB)',
                        name: 'appSize',
                        id: 'appSize',
                        triggerAction: 'all',
                        editable : false ,
                        anchor:'90%'  
                    },{
			   	xtype : 'hidden',
            	id : 'appSizeHid',
            	value : ''
				}
                      ]
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
                        name: 'v_functionContent',
                        id: 'v_functionContent',
                        height : 30,
                        value: '',
                        anchor:'95%'    
                      },{
                        xtype:'textarea',
                        fieldLabel: '备注',
                        name: 'v_appMemo',
                        id: 'v_appMemo',
                        height : 40,
                        value: '',
                        anchor:'95%'    
                      }]
            }]
           }  ,{ buttons: [{ 
        	   id:'appsubmite',
               text: '确定',
               listeners:{"click":function(){
//                   if(!Ext.getCmp("funcInfo").getForm().isValid()){
//                       return ;
//                   }
                  appOper.doSubmit();
                  appWin.close();

               }}
           },{
        	   id:'apprest',
               text: '取消',
               listeners:{"click":function(){
                  appWin.close();
               }}
           }]}]
           
          
        });

		var column = new Ext.grid.ColumnModel([
		    {header:'应用ID',dataIndex:'appId',hidden:true },		   
		    {header:'功能ID',dataIndex:'appFuncID',hidden:true},
		  //  {header:'应用图标路径',dataIndex:'iconUri',width:swidth*0.13,renderer:function (val){ if (val == 0) {return "未签到"} else if (val == 1) {return "已签到"} else if(val == 2){return "占缓签到"}}},
		    {header:'功能编码',dataIndex:'funcCode',hidden:true },
		    {header:'功能名称',dataIndex:'funcName'},	
		   
		    {header:'开放包名',dataIndex:'accessPackage',width:swidth*0.13}, 
		    {header:'开放类路径',dataIndex:'accessClass',width:swidth*0.13}, 
		    {header:'功能类路径',dataIndex:'funcClass',width:swidth*0.13  ,hidden:true},
		    {header:'动作名称',dataIndex:'actionName',width:swidth*0.13}, 
		    {header:'创建时间',dataIndex:'buildTime',width:swidth*0.13}, 
		    {header:'内部版本号',dataIndex:'versionCode',width:swidth*0.13}, 
		    {header:'外部版本号',dataIndex:'versionName',width:swidth*0.13}, 
		     {header:'功能描述',dataIndex:'funcContent',width:swidth*0.13},  
		    {header:'开始时间',dataIndex:'stateDate',width:swidth*0.13}
		]);

		var appFunGrid=  new ZTESOFT.Grid({
			id: 'appFunGrid',
		    region: 'center',				
	        title:'功能列表',
		    cm:column,
		    heigth:400,
		    pageSize:10,
         	paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelAppFunHisAction&selfun=selfun&appId='+appId,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
				
		            }
		        }
		    })
	    });
     this.QryMonitorGrid = function(){
	    Ext.getCmp('appFunGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('appFunGrid').store.lastOptions != null){//,appId:
				   Ext.getCmp('appFunGrid').store.baseParams = {selfun:'selfun',flag:1};
				}
			}
	    )
	    Ext.getCmp('appFunGrid').store.removeAll() ;
		Ext.getCmp('appFunGrid').store.load({params:{start:0, limit:16}});
	}
	var appTab = new Ext.TabPanel({
			region: 'center',
			id : 'appTab',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[appFunGrid],
		    		    listeners: {
	            tabchange: function(){
		    	 appOper.initGridEventFunc();
	            	var activeTab = appTab.activeTab.id ;
	            	switch (activeTab){
						case "appFunGrid":
						{  
							appOper.QryMonitorGrid();
							
							break ;
						}						
					}	
	    		}
	    }
		});
//	 if(operator=='add'){
//      funcWin = new Ext.Window({
//            title: '应用管理',
//            closable:true,
//            width:'80%',
//            height:316,
//            id:'appAddWindow',
//            autoScroll:true,
//            plain:true,
//            layout:'anchor',
//            items: [funcInfo]
//        });
//      }else if(operator == 'mod'){
    	   appWin = new Ext.Window({
            title: '应用管理',
            closable:true,
            width:'80%',
            height:570,
            id:'appModWindow',
            autoScroll:true,
            plain:true,
            layout:'border',
            items: [funcInfo,appTab]
        });
//      }
	  
        appWin.show(this);
      if(operator == 'mod'){
           appOper.setTextValue();
      }else if(operator=='detail'){
    	  appOper.setTextValue();
    	   Ext.getCmp('appsubmite').hide();
    	   Ext.getCmp('apprest').hide();
    	  
      }
       }
      this.setTextValue=function(){
    	  var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
            Ext.getCmp("v_functionName").setValue(selGridItem[0].data.appName);
             Ext.getCmp("os_Type").store.load({
            	   callback : function(r,opt,t){
            	      Ext.getCmp("os_Type").setValue(selGridItem[0].data.osType);
            	   } 
               });
               Ext.getCmp("v_app_status").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_app_status").setValue(selGridItem[0].data.appStatus);
            	   } 
               });
             
                 Ext.getCmp("v_appClass").store.load({
            	   callback : function(r,opt,t){
            	       Ext.getCmp("v_appClass").setValue(selGridItem[0].data.appClass);
            	   } 
               });
             // appOper.setSysInf(Ext.getCmp("os_Type"));
                   Ext.getCmp("v_appType").store.load({params:{flag:3,appClass:selGridItem[0].data.appClass,selfun:'select',start:0, limit:20},
            	   callback : function(r,opt,t){
            	   Ext.getCmp("v_appType").setValue(selGridItem[0].data.busiSysId);
            	   } 
               });
        //      Ext.getCmp("v_appClass").setValue('');

           // Ext.getCmp("appFilePath").setValue(selGridItem[0].data.downloadUrl);   
            Ext.getCmp("v_functionContent").setValue(selGridItem[0].data.appIntro);   
            Ext.getCmp("v_appMemo").setValue(selGridItem[0].data.memo);
            Ext.getCmp("versionName").setValue(selGridItem[0].data.versionName);
            Ext.getCmp("appSize").setValue(parseInt((parseInt(selGridItem[0].data.appSize)/1024/1024).toFixed(2))+0.01);
            Ext.getCmp("v_menuIconUrl").setValue(selGridItem[0].data.iconUri)
          
          var imgsrc=selGridItem[0].data.iconUri;
          	
           if(imgsrc==null || imgsrc==''){
        	  imgsrc='/MOBILE/appManage/image/001.png';
          }
           Ext.getCmp('photoView').getEl().dom.src=imgsrc;
      }
    
      //定义菜单列表菜单
    this.initGridMenu = new function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClickFun'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClickFun'
		});
    }
     
   	//定义菜单列表事件
	this.initGridEventFunc = function(){
		Ext.getCmp('appFunGrid').addListener('rowcontextmenu', appOper.rightClickFn);
		Ext.getCmp('appFunGrid').addListener('contextmenu',appOper.nullRightClickFn);
	}
	    
	//菜单组装
	this.rightClickFn = function(appFunGrid,rowIndex,e){
	
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClickFun');
		rightClick.removeAll();
//		Ext.getCmp('appFunGrid').getSelectionModel().selectRow(rowIndex);
//		
//			rightClick.insert(i++,new Ext.menu.Item({ text: '添加功能' ,handler: function() {
//					rightClick.hide();
//					appOper.apkReg();
//			}}));
//		


			rightClick.insert(i++,new Ext.menu.Item({ text: '修改功能' ,handler: function() {
				rightClick.hide();
				appOper.apkMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除功能' ,handler: function() {
				rightClick.hide();
				if(flag=='add'){
					var deleteRow= Ext.getCmp('appFunGrid').getSelectionModel().getSelected();
					Ext.MessageBox.confirm('提示', '你确定要删除表格中的记录吗？', function (btn){
						if(btn=='yes'){
							 Ext.getCmp('appFunGrid').store.remove(deleteRow);
						}
					});
                    	
			    }else if (flag=='mod'){  //这个是删除数据库中的记录的
			    	Ext.MessageBox.confirm('提示', '此操作将会提交到数据库中，你确定要删除吗？', appOper.delReg);
			    	
			    }
			}}));
			
		Ext.getCmp('appFunGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	   this.nullRightClickFn = function(e){
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClickFun');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '功能添加' ,handler: function() {
			nullRightClick.hide();
			appOper.apkReg();
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    }
	//添加调用的弹出框
	  
   this.apkReg = function(){
	
	    var selItemMenu = Ext.getCmp('monitorGrid').getSelectionModel().getSelections(); 
	    if(flag=='add'){  //从app添加菜单到func添加菜单
	    	funOper.funcInfoPage('add',null,selItemMenu[0].data.appId,null,'addToadd');
	    }else if(flag=='mod'){  //从app修改菜单到func增加菜单
	    	funOper.funcInfoPage('add',null,selItemMenu[0].data.appId,null,'modToadd');
	    }
          
    }
    this.apkMod = function(){
  
	    var selItemMenu = Ext.getCmp('monitorGrid').getSelectionModel().getSelections(); 
	     if(flag=='add'){  //从app添加菜单到func修改菜单
	    	 
          funOper.funcInfoPage('mod',null,selItemMenu[0].data.appId,null,'addTomod');
          }else if(flag=='mod'){//从app修改菜单到fucn修改菜单
        	   funOper.funcInfoPage('mod',null,selItemMenu[0].data.appId,null,'modTomod');
          }
    }
   this.delReg = function (btn){
	    if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('appFunGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "del";
        paramObj.optfunmenu='fun';
        paramObj.appFuncId= selGridItem[0].data.appFuncID;
       
        var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
        if (retMap){
           appOper.QryMonitorGrid();
        }
      }
    	
   }
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
			   	xtype : 'hidden',
            	id : 'staffId',
            	value : v_staffId
				},{
			   	xtype : 'hidden',
            	id : 'staffName',
            	value : v_staffName
				},{
			   	xtype : 'hidden',
            	id : 'username',
            	value : v_username
				},
				{					
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'appFile',
				id : 'appFile',
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
					furl = fileForm.form.findField('appFile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					if (!(type == '.apk' || type == '.ipa')) {
						alert('仅支持APK格式或IPA格式的文件');
						return;
					}
					fileForm.form.submit({
						url : '/MOBILE/api/server/upload/file',
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
							Ext.getCmp('appFilePath').setValue(filePath);
							Ext.getCmp('appSize').setValue((parseInt(fileSize)/1024/1024).toFixed(2));
//							alert(filePath);
							Ext.getCmp('appSizeHid').setValue(fileSize);
							form.reset();
							win.close();
						
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，仅支持APK格式或IPA格式的文件');
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
			title : "上传APP文件",
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
 

   
     //确定
     this.doSubmit = function () {
         switch (flag){
             case "add":{
                 resultStr = '新增成功！';
                 var objAttr;
                 var paramObj = new Object();
                 paramObj.optfunmenu='app';//判断此添加是应用添加，还是功能添加；
                 paramObj.parentId = vMenuId ;
                 paramObj.appName = Ext.getCmp("v_functionName").getValue() ;
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_functionName").getValue();
                 paramObj.iconUri= Ext.getCmp("v_menuIconUrl").getValue();
                 paramObj.menuType = "1";
                 paramObj.type = "add"; 
                 paramObj.osType = Ext.getCmp("os_Type").getValue();//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isMain=1;                                   
            //     paramObj.iconUri="image/1.gif";
                 paramObj.appType=Ext.getCmp("v_appType").getValue() ;
                 paramObj.appClass=Ext.getCmp("v_appClass").getValue() ;
             //    paramObj.menuUri=Ext.getCmp("v_functionClass").getValue(); /client/downloads/app/
                 paramObj.downloadUrl="/MOBILE/server/downloads/app/"
                 paramObj.appFilePath=Ext.getCmp('appFilePath').getValue();
                 paramObj.appStatus=Ext.getCmp("v_app_status").getValue();
                 paramObj.busiSysId=appType=Ext.getCmp("v_appType").getValue() ;
                 paramObj.appSize=Ext.getCmp("appSizeHid").getValue() ; 
               //  alert(paramObj.appSize);
                 paramObj.memo=Ext.getCmp("v_appMemo").getValue(); 
                 paramObj.appIntro=Ext.getCmp("v_functionContent").getValue(); 
                 paramObj.state=1 ;
                 paramObj.versionName=Ext.getCmp("versionName").getValue();
                 paramObj.staffId= session1.staff.staffId ;
                  var addFuncStore = Ext.getCmp('appFunGrid').getStore();//得到说有要添加的addFuncStore;
                  // alert(addFuncStore.data+"  "+addFuncStore.data.length);
                  var funcArray=new Array();
                 if(addFuncStore.data!=null &&addFuncStore.data.length>0){
                	 //	alert(addFuncStore.fields.keys[3]+" : "+addFuncStore.fields.items[3]);
                    for(var i=0;i<addFuncStore.data.length;i++){
                    var	funcObj =new Object();
                            funcObj.funcName=addFuncStore.getAt(i).get("funcName");
                        	funcObj.funcClass=addFuncStore.getAt(i).get("funcClass");
                        	funcObj.accessPackage=addFuncStore.getAt(i).get("accessPackage");
                        	funcObj.accessClass=addFuncStore.getAt(i).get("accessClass");
                        	funcObj.buildTime=addFuncStore.getAt(i).get("buildTime");
                        	funcObj.versionName=addFuncStore.getAt(i).get("versionName");
                        	funcObj.funcContent=addFuncStore.getAt(i).get("funcContent");
                        	funcObj.funcCode=addFuncStore.getAt(i).get("funcCode");
                        	funcObj.state=1 ;
                        	funcObj.stateDate=addFuncStore.getAt(i).get("stateDate");
                        	funcObj.actionName=addFuncStore.getAt(i).get("actionName");
                        	funcObj.staffId=paramObj.staffId;
                   	funcArray.push(funcObj);
                   
                    }
                      paramObj.funcArray=funcArray;
                    }
                   
                 var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
                 break ;
             }
             case "mod":{
                 resultStr = '修改成功！';
                 var objAttr; 
                 var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
               var paramObj = new Object();
                 paramObj.optfunmenu='app';//判断此添加是应用添加，还是功能添加；
                 paramObj.parentId = vMenuId ;
                 paramObj.appId=selGridItem[0].data.appId;
                 paramObj.appName = Ext.getCmp("v_functionName").getValue() ;
                 paramObj.pathName = parentMenuName+"/"+Ext.getCmp("v_functionName").getValue();
                 paramObj.busiSysId=appType=Ext.getCmp("v_appType").getValue() ;
                 paramObj.iconUri= Ext.getCmp("v_menuIconUrl").getValue();
                 paramObj.menuType = "1";
                 paramObj.type = "mod"; 
                 paramObj.osType = Ext.getCmp("os_Type").getValue();//1为安卓，2为ios，3为winphone
                 paramObj.privType = 1; //1为集成类，2为内置功能
                 paramObj.isMain=1;                                   
             //    paramObj.iconUri="image/1.gif";
                 paramObj.appType=Ext.getCmp("v_appType").getValue() ;
                     paramObj.appSize=Ext.getCmp("appSizeHid").getValue() ; 
                     if(paramObj.appSize==null||paramObj.appSize==''){
                    	 paramObj.appSize=selGridItem[0].data.appSize;
                     }
                 paramObj.appClass=Ext.getCmp("v_appClass").getValue() ;
             //    paramObj.menuUri=Ext.getCmp("v_functionClass").getValue();
                 paramObj.appFilePath=Ext.getCmp('appFilePath').getValue();
                 paramObj.appStatus=Ext.getCmp("v_app_status").getValue();
            //     paramObj.appIntro=Ext.getCmp("appIntro").getValue() ; 
                 paramObj.downloadUrl="/MOBILE/server/downloads/app/"+paramObj.appId;
                 paramObj.memo=Ext.getCmp("v_appMemo").getValue(); 
                 paramObj.appIntro=Ext.getCmp("v_functionContent").getValue(); 
                 paramObj.state=1 ;
                 paramObj.versionName=Ext.getCmp("versionName").getValue();
                 paramObj.staffId= session1.staff.staffId ;
                 
                 var retMap = invokeAction("/appmanage/OptAppFunHisAction", paramObj);
                 break ;
             }

         }
                  
         setTimeout(function(){
             Ext.MessageBox.hide();
             Ext.example.msg('',resultStr);
         }, 500);
         appWin.close();
       
         oper.QryMonitorGrid();
           
         }
  
}