/*
 * Developed by fang.li 2011-04
 * 调度方案管理
 */
 
function ScheduleMng(){
	
	var selScheCata = null;

	this.showScheduleInfo = function(operator){
	
	    var schedulePanel = new Ext.FormPanel({
	     	region: 'center',
	     	buttonAlign: 'center',
		 	frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '方案名称',
	                    name: 'scheduleName3',
	                    id: 'scheduleName3',
		                allowBlank:false, 
		                blankText:"方案名称不能为空!",
	                    anchor:'95%'
	                },{
					    xtype: 'compositefield',
					    fieldLabel: '流程名称',
					    items: [{
					        xtype: 'textfield',
					        name: 'processDefinName',
					        id: 'processDefinName',
					        anchor:'95%',
					        readOnly: true,
					        allowBlank:false, 
			            	blankText:"流程名称不能为空!"
					    },{
					        xtype: 'button',
					        text: '..',
					        listeners:{
					        	"click":function(){
					        		scheduleMng.showProcessDefin();
					        	}
					        }
					    },{
					        xtype: 'button',
					        text: '清空',
					        listeners:{
					        	"click":function(){
					        		Ext.getCmp("processDefinId").setValue("");
			    		 			Ext.getCmp("processDefinName").setValue("");
					        	}
					        }
					    }]
					
					},{
					    xtype:'hidden',
				    	name:'processDefinId',
				    	id:'processDefinId'				
					},{
	                    xtype:'datetimefield',
	                    fieldLabel: '执行开始时间',
                   		format :'Y-m-d H:i:s',
	                    name: 'execStartDate3',
	                    id: 'execStartDate3',
	                    value: new Date(),
		                allowBlank:false, 
		                blankText:"执行开始时间不能为空!",
	                    anchor:'95%'
	                }]
	            },{columnWidth:.05,layout: 'form'},{
	                columnWidth:.45,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '频率/小时',
	                    name: 'execRate3',
	                    id: 'execRate3',
		                allowBlank:false, 
		                blankText:"频率不能为空!",
		                vtype: 'num' ,
	                    anchor:'95%'
	                },{
					    xtype: 'compositefield',
					    fieldLabel: '方案目录',
					    items: [{
					        xtype: 'textfield',
					        name: 'scheCataName',
					        id: 'scheCataName',
					        anchor:'95%',
					        readOnly: true,
					        allowBlank:false, 
			            	blankText:"方案目录不能为空!"
					    },{
					        xtype: 'button',
					        text: '..',
					        listeners:{
					        	"click":function(){
					        		scheduleMng.showScheCatalog("scheCataId","scheCataName");
					        	}
					        }
					    },{
					        xtype: 'button',
					        text: '清空',
					        listeners:{
					        	"click":function(){
					        		Ext.getCmp("scheCataId").setValue("");
			    		 			Ext.getCmp("scheCataName").setValue("");
					        	}
					        }
					    }]
					
					},{
				    	xtype:'hidden',
				    	name:'scheCataId',
				    	id:'scheCataId'
				    },{
	                    xtype:'textfield',
	                    fieldLabel: '最后操作人',
	                    name: 'operManName3',
	                    id: 'operManName3',
	                    value: session1.staff.staffName,
	                    disabled: true,
	                    anchor:'95%'
	                }]
	            },{columnWidth:.05,layout: 'form'}]
	            
	        },{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'memo3',
			    id: 'memo3',
			    height : 100,
			    anchor:'95%'
			}],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
					var selSch = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
					var reStr = '' ;
					//表单验证
					if(!schedulePanel.getForm().isValid()){
						return;
					}
					
					var obj = new Object();			
					obj.scheduleName = Ext.getCmp('scheduleName3').getValue() ;
					obj.execStartDate = Ext.getCmp('execStartDate3').getValue() ;
					obj.execRate = Ext.getCmp('execRate3').getValue() ;
					obj.scheCatalogId = parseInt(Ext.getCmp('scheCataId').getValue()) ;
					obj.packageDefineId = parseInt(Ext.getCmp('processDefinId').getValue()) ;					
					obj.memo = Ext.getCmp('memo3').getValue() ;
					obj.operManId = session1.staff.staffId;
					obj.operManName = session1.staff.staffName;
		 
					if(operator == 'add'){
						obj.state = '10A' ;
						obj.createDate = new Date();
						var icount = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "checkScheduleName", obj.scheduleName);
						if(icount==0){
							var objId = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "addDataSource", obj);
							reStr = '方案增加成功！' ;
						}else {
							Ext.MessageBox.show({
					            title: '提示',
					            msg: '方案名称已存在,请重新写入!',
					            buttons: Ext.MessageBox.OK,
					            width:250,
					            icon: Ext.MessageBox.ERROR
					       	});
							return;
						}
						
					}else if(operator == 'mod'){
						obj.state = selSch[0].data.state ;
						obj.createDate = selSch[0].data.createDate ;
						obj.scheduleId = selSch[0].data.scheduleId ;
						callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleMangerClient", "updateDataSource", obj);
						reStr = '方案修改成功！' ;
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
			        
					Ext.getCmp('scheduleGrid').store.removeAll();
					Ext.getCmp('scheduleGrid').store.load({params:{start:0, limit:8}});
			        win.close();
	            }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
	    });
		
		win = new Ext.Window({
	        title: '调度方案',
		    closable:true,
		    width:700,
		    height:300,
		    plain:true,
		    layout: 'border',
		    items: [schedulePanel]
		});
		
		win.show();
		
		if(operator == 'mod'){
			var selDb = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections();
			Ext.getCmp("scheduleName3").setValue(selDb[0].data.scheduleName);
			Ext.getCmp("scheCataName").setValue(selDb[0].data.scheCatalogName);
			Ext.getCmp("scheCataId").setValue(selDb[0].data.scheCatalogId);
			Ext.getCmp("processDefinName").setValue(selDb[0].data.processDefinName);
			Ext.getCmp("processDefinId").setValue(selDb[0].data.packageDefineId);
			Ext.getCmp("execStartDate3").setValue(selDb[0].data.execStartDate);
			Ext.getCmp("execRate3").setValue(selDb[0].data.execRate);
			Ext.getCmp("operManName3").setValue(selDb[0].data.operManName);
			Ext.getCmp("memo3").setValue(selDb[0].data.memo);
		}
	}
	
	this.showProcessDefin = function(){
	    var procDefin = null;
	    
		var pdStore = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        fields: [ 
	        	'packageDefineid','name','version','catalogName','editUser','description'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/QryProcessDefinAction'
	        })		
		});
		
		var pdColumn = new Ext.grid.ColumnModel([
		    {header:'流程名',dataIndex:'name',sortable:true,width:150},
		    {header:'版本号',dataIndex:'version',sortable:true,width:100},
		    {header:'所在目录',dataIndex:'catalogName',sortable:true,width:100},
		    {header:'最后编辑人',dataIndex:'editUser',sortable:true,width:100},
		    {header:'描述',dataIndex:'description',sortable:true,width:250}
		]);		
		
	    var pdPb = new Ext.PagingToolbar({
			pageSize: 15,
			store:pdStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
	    });
	    
		var pdView = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});
		
		var pdGrid = new Ext.grid.GridPanel({
			id:'pbGrid',
	        region: 'center',
	        store:pdStore,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig :pdView,
			cm: pdColumn,
			bbar: pdPb,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
					listeners:{
					rowselect:function(sm,row,rec) {
	                    procDefin = rec.data;  				
	                }
				}
			})
	    });	
	    pdStore.load({params:{start:0,limit:15}});
	    
		var pdWin = new Ext.Window({
	        title: '选择流程',
		    closable:true,
	        width:Ext.getBody().getSize().width*0.7,   
		    height:Ext.getBody().getSize().height*0.65,
		    plain:true,
		    layout: 'border',
		    items: [pdGrid],
		    buttonAlign:'center',
		    buttons :[{
		    	text:'确定',
		    	listeners:{
			    	'click':function(){
			    		 if(procDefin == null){
	    		 			scheduleMng.showMess("请选择流程");
			    		 }else{
			    		 	Ext.getCmp("processDefinId").setValue(procDefin.packageDefineid);
			    		 	Ext.getCmp("processDefinName").setValue(procDefin.name);
			    		 	pdWin.close();
			    		 }
			    	}
				}
		    },{
		    	text:'取消',
		    	listeners:{
			    	'click':function(){
			    		pdWin.close();
			    	}
				}
		    }]
		});
		
		pdWin.show();	    	
	}
	
	this.showScheCatalog = function(id,name){
		var pdTree = new Ext.tree.TreePanel({
			id:'pdTree',
		    region:'center',
	    	useArrows: true,
		    autoScroll: true,
		    animate: true,
		    enableDD: true,
		    containerScroll: true,
		    lines:true,
		    border: false,
		    loader: new Ext.tree.TreeLoader({ 
		    	url: '/MOBILE/ExtServlet?actionName=etl/QryScheCatalogAction'
		    }),			    
		    root: new Ext.tree.AsyncTreeNode({
		        expanded: true,
		        nodeType: 'async',
		        id:'-1',
		        text:'调度方案目录'
		        //singleClickExpand:true
			}),
			listeners:{
		    	'click':function(node,e){
		    		selScheCata= node.attributes;
		    	},
		    	'dblclick':function(node,e){
					Ext.getCmp(id).setValue(selScheCata.id);
	    		 	Ext.getCmp(name).setValue(selScheCata.text);
	    		 	scheWin.close();
		    	}
			}
		    
		});
		
		var scheWin = new Ext.Window({
	        title: '选择目录',
		    closable:true,
		    width:Ext.getBody().getSize().width*0.25,
		    height:Ext.getBody().getSize().height*0.8,
		    plain:true,
		    layout: 'border',
		    items: [pdTree],
		    buttonAlign:'center',
		    buttons :[{
		    	text:'确定',
		    	listeners:{
			    	'click':function(){
			    		 if(selScheCata == null){
	    		 			scheduleMng.showMess("请选择方案目录");
			    		 }else{
			    		 	Ext.getCmp(id).setValue(selScheCata.id);
			    		 	Ext.getCmp(name).setValue(selScheCata.text);
			    		 	scheWin.close();
			    		 }
			    	}
				}
		    },{
		    	text:'取消',
		    	listeners:{
			    	'click':function(){
			    		scheWin.close();
			    	}
				}
		    }]
		});
		
		scheWin.show();
	}
	
	this.showMess = function(msg,icon){
		if(!icon) icon = Ext.MessageBox.INFO;
		Ext.Msg.show({
			title:'操作提示',
			msg:msg,
			width:'250',
			buttons: Ext.MessageBox.OK,
          	icon: icon
		});	
	}
}