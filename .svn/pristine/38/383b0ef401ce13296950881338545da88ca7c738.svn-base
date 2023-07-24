/*
 * Developed by fang.li 2011-04
 * 数据源管理
 */
 
function DataBaseMng(){

	this.showDataBaseInfo = function(operator){
	/*
		var dbTypeStore = new Ext.data.ArrayStore({
			fields: ['value','text','url','driver'],
			data:[
				['000','oracle','url000','driver'],
				['001','sql-server','url111','driver']
			]
		});
	 */ 
	//初使化下拉框的值,信息类型
	
        var dbTypeStore = new Ext.data.JsonStore({
            remoteSort: true,
            fields: ['value', 'text','url','driver'],
            proxy: new Ext.data.HttpProxy({
                url: '/MOBILE/ExtServlet?actionName=etl/DataBaseTypeExtAction'
            })
        });
        
        dbTypeStore.load();
	    
        var dataBasePanel = new Ext.FormPanel({
	     	region: 'center',
	     	labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '数据源名',
                    name: 'dsName5',
                    id: 'dsName5',
	                allowBlank:false, 
	                blankText:"数据源名不能为空!",
                    anchor:'95%'
                },{
					xtype:'combo',
		        	fieldLabel:'数据库类型',
		       		name:'dbType5',
		            id: 'dbType5',
		            valueField: 'value',
		            displayField: 'text',
		            mode:'local',
		            triggerAction: 'all',
		            forceSelection: true,
					editable :false,		                
		       		allowBlank:false, 
		           	blankText:"数据库类型不能为空!",	
		           	anchor:'95%',	                
		            store:dbTypeStore,
		            listeners:{
	                    select: function(combo ,record,value) { 
	                  		//alert(record.data.url);
	                  		Ext.getCmp("dbDriver5").setValue(record.data.driver);
	                  		Ext.getCmp("dbUrl5").setValue(record.data.url);
	                    }
	                }
                }, {
                    xtype:'textfield',
                    fieldLabel: '驱动类名',
                    name: 'dbDriver5',
                    id: 'dbDriver5',
	                allowBlank:false, 
	                blankText:"驱动类名不能为空!",
                    anchor:'95%'
                },{
	                xtype:'textfield',
	                fieldLabel: '用户名',
	                name: 'userName5',
	                id: 'userName5',
		            allowBlank:false, 
		            blankText:"用户名不能为空!",
	                anchor:'95%'
	            },{
	                xtype:'textfield',
	                fieldLabel: '密码',
	                name: 'password5',
	                id: 'password5',
	                inputType: 'password',
		            allowBlank:false, 
		            blankText:"密码不能为空!",
	                anchor:'95%'
	        }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '提供厂家',
	                    name: 'serCop5',
	                    id: 'serCop5',
		                allowBlank:false, 
		                blankText:"提供厂家不能为空!",
	                    anchor:'95%'
                	},{
	                    xtype:'textfield',
	                    fieldLabel: '最大连接数',
	                    name: 'maxNum5',
	                    id: 'maxNum5',
		                allowBlank:false, 
		                blankText:"最大连接数不能为空!",
	                    vtype: 'num' ,
	                    anchor:'95%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '初始连接数',
	                    name: 'initNum5',
	                    id: 'initNum5',
		                allowBlank:false, 
		                blankText:"初始连接数不能为空!",
	                    vtype: 'num' ,
	                    anchor:'95%'
	                },  {
	                    xtype:'textfield',
	                    fieldLabel: '连接池自增大小',
	                    name: 'incNum5',
	                    id: 'incNum5',
		                allowBlank:false, 
		                blankText:"连接池自增大小不能为空!",
	                    vtype: 'num' ,
	                    anchor:'95%'
                	}]
	            },{columnWidth:.05,layout: 'form'}]
	            
	        }, {
				xtype:'textfield',
				fieldLabel: 'URL',
				name: 'dbUrl5',
				id: 'dbUrl5',
				allowBlank:false, 
				blankText:"URL不能为空!",
				anchor:'95%'
                },{//
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'memo5',
			    id: 'memo5',
			    height : 60,
			    anchor:'95%'
			}],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
					var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
					var reStr = '' ;
					//表单验证
					if(!dataBasePanel.getForm().isValid()){
						return;
					}
					
					//对传入后台对象赋值
					var _obj = new Object();
					_obj.dsName = Trim(Ext.getCmp("dsName5").getValue());
					_obj.ftpIp = "";
					_obj.serCop = Trim(Ext.getCmp("serCop5").getValue());
					_obj.dsType = '000';
					_obj.userName = Trim(Ext.getCmp("userName5").getValue());
					_obj.password = Trim(Ext.getCmp("password5").getValue());
					_obj.memo = Trim(Ext.getCmp("memo5").getValue());
					_obj.operManId = session1.staff.staffId;
					_obj.operManName = session1.staff.staffName;
					
					_obj.dbDriver=	 Trim(Ext.getCmp("dbDriver5").getValue());
				    _obj.dbUrl	=  Trim(Ext.getCmp("dbUrl5").getValue());
					_obj.initNum = Trim(Ext.getCmp("initNum5").getValue());
					_obj.incNum = Trim(Ext.getCmp("incNum5").getValue());
					_obj.maxNum = Trim(Ext.getCmp("maxNum5").getValue());
					
					_obj.dbType = Trim(Ext.getCmp("dbType5").getValue());
					
					if(operator == 'add'){
						_obj.state = '10A' ;
						var count = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.DataSourceManagerClient", 
												  "isNameExist", _obj);
						if(count > 0){
							Ext.MessageBox.show({
					            title: '提示',
					            msg: '数据源名称已经存在，请重新指定！',
					            buttons: Ext.MessageBox.OK,
					            width:250,
					            icon: Ext.MessageBox.ERROR
					       	});
							return;
						}
						var objId = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.DataSourceManagerClient", "addDataSource", _obj);
						reStr = '新增数据源成功' ;					  
					}else if(operator == 'mod'){
						_obj.dsId = selDb[0].data.dsId ;
						_obj.state = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections()[0].data.state;
						callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.DataSourceManagerClient", "updateDataSource", _obj);
						reStr = '修改数据源成功' ;	
					}
					
					Ext.MessageBox.show({
			           	msg: '系统正在提交数据……',
			           	progressText: 'Saving...',
			           	width:300,
			           	wait:true,
			           	waitConfig: {interval:100},
			           	icon:'ext-mb-download'
			       	});
			        setTimeout(function(){
			            Ext.MessageBox.hide();
			            Ext.example.msg('',reStr);
			        }, 1000);
			        
					Ext.getCmp('dataSourceGrid').store.removeAll();
					Ext.getCmp('dataSourceGrid').store.load({params:{start:0, limit:5}});
			        win.close();
	            }}
	        },{
	            text: '测试连接',
	            listeners:{"click":function(){
			    	var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
			    	//对传入后台对象赋值
					var _obj = new Object();
					_obj.dsName = Trim(Ext.getCmp("dsName5").getValue());
					_obj.ftpIp = "";
					_obj.serCop = Trim(Ext.getCmp("serCop5").getValue());
					_obj.dsType = '000';
					_obj.userName = Trim(Ext.getCmp("userName5").getValue());
					_obj.password = Trim(Ext.getCmp("password5").getValue());
					_obj.memo = Trim(Ext.getCmp("memo5").getValue());
					_obj.operManId = session1.staff.staffId;
					_obj.operManName = session1.staff.staffName;
					
					_obj.dbDriver=	 Trim(Ext.getCmp("dbDriver5").getValue());
				    _obj.dbUrl	=  Trim(Ext.getCmp("dbUrl5").getValue());
					_obj.initNum = Trim(Ext.getCmp("initNum5").getValue());
					_obj.incNum = Trim(Ext.getCmp("incNum5").getValue());
					_obj.maxNum = Trim(Ext.getCmp("maxNum5").getValue());
					
					_obj.dbType = Trim(Ext.getCmp("dbType5").getValue());
					
					try{
						var success = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.DataSourceManagerClient", "connTest", _obj);
						Ext.MessageBox.show({
				           	msg: '系统正在提交数据……',
				           	progressText: 'Saving...',
				           	width:300,
				           	wait:true,
				           	waitConfig: {interval:100},
				           	icon:''
				       	});
				        setTimeout(function(){
				            Ext.MessageBox.hide();
				            Ext.example.msg('','连接成功!');
				        }, 1000);
					}catch(e){
						Ext.MessageBox.show({
				           	msg: '系统正在提交数据……',
				           	progressText: 'Saving...',
				           	width:300,
				           	wait:true,
				           	waitConfig: {interval:100},
				           	icon:''
				       	});
				        setTimeout(function(){
				            Ext.MessageBox.hide();
				            Ext.example.msg('','连接失败，请检查参数是否正确!');
				        }, 1000);
					}
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
	    });
		
		win = new Ext.Window({
	        title: '数据源',
		    closable:true,
		    width:700,
		    height:330,
		    plain:true,
		    layout: 'border',
		    items: [dataBasePanel]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
			Ext.getCmp("dsName5").setValue(selDb[0].data.dsName);
			Ext.getCmp("dbDriver5").setValue(selDb[0].data.dbDriver);
			Ext.getCmp("serCop5").setValue(selDb[0].data.serCop);
			Ext.getCmp("incNum5").setValue(selDb[0].data.incNum);
			Ext.getCmp("dbUrl5").setValue(selDb[0].data.dbUrl);
			Ext.getCmp("maxNum5").setValue(selDb[0].data.maxNum);
			Ext.getCmp("initNum5").setValue(selDb[0].data.initNum);
			
			Ext.getCmp("userName5").setValue(selDb[0].data.userName);
			Ext.getCmp("password5").setValue(selDb[0].data.password);
			Ext.getCmp("memo5").setValue(selDb[0].data.memo);
			
			Ext.getCmp("dbType5").setValue(selDb[0].data.dbType);
			Ext.getCmp("dbType5").setRawValue(selDb[0].data.dbTypeName);
			
			Ext.getCmp("dsName5").setDisabled(true);
		}
	}
}