/*
 * Developed by fang.li 2011-04
 * 数据源管理
 */
 
function DataFileMng(){

	this.showDataFileInfo = function(operator){
	
	    var dataFilePanel = new Ext.FormPanel({
	     	region: 'center',
	     	labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '数据源名',
	                    name: 'dsName4',
	                    id: 'dsName4',
		                allowBlank:false, 
		                blankText:"数据源名不能为空!",
	                    anchor:'95%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '文件主机IP地址',
	                    name: 'ftpIp4',
	                    id: 'ftpIp4',
		                allowBlank:false, 
		                blankText:"文件主机IP地址不能为空!",
	                    anchor:'95%'
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '用户名',
	                    name: 'userName4',
	                    id: 'userName4',
		                allowBlank:false, 
		                blankText:"用户名不能为空!",
	                    anchor:'95%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '密码',
	                    name: 'password4',
	                    id: 'password4',
                    	inputType: 'password',
		                allowBlank:false, 
		                blankText:"密码不能为空!",
	                    anchor:'95%'
	                }]
	            },{columnWidth:.05,layout: 'form'}]
	            
	        }, {
				xtype:'textfield',
				fieldLabel: '提供厂家',
				name: 'serCop4',
				id: 'serCop4',
				allowBlank:false, 
				blankText:"提供厂家不能为空!",
				anchor:'95%'
	                },{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'memo4',
			    id: 'memo4',
			    height : 100,
			    anchor:'95%'
			}],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
					var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
					var reStr = '' ;
					//表单验证
					if(!dataFilePanel.getForm().isValid()){
						return;
					}
					
					//对传入后台对象赋值
					var _obj = new Object();
					_obj.dsName = Trim(Ext.getCmp("dsName4").getValue());
					_obj.ftpIp = Trim(Ext.getCmp("ftpIp4").getValue());
					_obj.serCop = Trim(Ext.getCmp("serCop4").getValue());
					_obj.dsType = '001';
					_obj.userName = Trim(Ext.getCmp("userName4").getValue());
					_obj.password = Trim(Ext.getCmp("password4").getValue());
					_obj.memo = Trim(Ext.getCmp("memo4").getValue());
					_obj.operManId = session1.staff.staffId;
					_obj.operManName = session1.staff.staffName;
					_obj.dbDriver=	 "";
				    _obj.dbUrl	= "";
					_obj.initNum = 1;
					_obj.incNum = 1;
					_obj.maxNum = 50;
					
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
					_obj.dsName = Trim(Ext.getCmp("dsName4").getValue());
					_obj.ftpIp = Trim(Ext.getCmp("ftpIp4").getValue());
					_obj.serCop = Trim(Ext.getCmp("serCop4").getValue());
					_obj.dsType = '001';
					_obj.userName = Trim(Ext.getCmp("userName4").getValue());
					_obj.password = Trim(Ext.getCmp("password4").getValue());
					_obj.memo = Trim(Ext.getCmp("memo4").getValue());
					_obj.operManId = session1.staff.staffId;
					_obj.operManName = session1.staff.staffName;
					_obj.dbDriver=	 "";
				    _obj.dbUrl	= "";
					_obj.initNum = 1;
					_obj.incNum = 1;
					_obj.maxNum = 50;
					
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
		    items: [dataFilePanel]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			var selDb = Ext.getCmp('dataSourceGrid').getSelectionModel().getSelections();
			Ext.getCmp("dsName4").setValue(selDb[0].data.dsName);
			Ext.getCmp("ftpIp4").setValue(selDb[0].data.ftpIp);
			Ext.getCmp("serCop4").setValue(selDb[0].data.serCop);
			Ext.getCmp("userName4").setValue(selDb[0].data.userName);
			Ext.getCmp("password4").setValue(selDb[0].data.password);
			Ext.getCmp("memo4").setValue(selDb[0].data.memo);
			
			Ext.getCmp("dsName4").setDisabled(true);
		}
	}
}