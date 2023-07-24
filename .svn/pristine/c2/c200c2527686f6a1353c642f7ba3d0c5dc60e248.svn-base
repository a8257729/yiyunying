Ext.ns("ZTESOFT");
ZTESOFT.StaffWin = Ext.extend(Ext.Window,{
	constructor:function(config){
		if(Ext.isEmpty(config.title)){
			 this.title = '员工选择';
		}
		if(Ext.isEmpty(config.id)){
			 this.id = 'staffWin';
		}
		this.closable = true;
		this.width = Ext.getBody().getSize().width * 0.9;
		this.height = Ext.getBody().getSize().height*0.8;
		this.layout = 'border';
		this.buttonAlign = 'center';
		this.initMainPanel = function(){
			var qryPanel = this.initConfigQryPanel();
			var dataGrid = this.initGrid();
			 var mainPanel = new Ext.Panel({
				region: 'center',
				layout: 'border',
				items:[qryPanel,dataGrid]
			}); 
			return mainPanel;
		
		}
		this.initConfigQryPanel = function(){
			var qPanel = new Ext.FormPanel({
				id:'configQryPanel',
	     		region: 'north',
	     	   	frame:true,
	       		title: '查询条件',
	      		labelWidth: 60,
	      		height:this.height*0.25,
	       		collapsible:true,
	       	    width:this.width*0.8,
	       	    items: [{
		            layout:'column',
		            items:[{
		                columnWidth:.3,
		                layout: 'form',
		                items: [{
		                    xtype:'textfield',
		                    fieldLabel: '员工名称',
		                    name: 'form.staffName',
		                    id: 'form.staffName',
		                    anchor:'95%'
		                }]
		            },{
		                columnWidth:.3,
		                layout: 'form',
		                items: [{
		                    xtype:'textfield',
		                    fieldLabel: '用户名',
		                    name: 'form.userName',
		                    id: 'form.userName',
		                    anchor:'95%'
		                }]
		            }]
		        }],
	            
	               buttons:[{
	            		  text: '查询',
	            		  listeners:{"click":function(){ Ext.getCmp("staffWin").qryGridData();  }
				          
				          }
	            		 
	            		 },
	            		 {
	            		 	text: '重置',
	            		 	listeners:{"click":function(){Ext.getCmp('configQryPanel').form.reset();}
				            }
				          }
				        ]
		
	 	 	 });
	 	  return qPanel;
		}
		this.moduelTree = function(){
				var moduelTree = new ZTESOFT.OrgTree({
					id:'moduelTree',
			       	region: 'west',
			       	allOrgs:true//是否查询全组织
			       	//checked:true 是否需要复选框    
				});
			   	// moduelTree.expandAll();
			     moduelTree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
				    if(node.isLeaf() == false){
						if(node.expanded == false){
							node.expand();//展开
						}else{
							node.collapse();//收起
						} 
				    }
				    var moduleId = node.id ;
					Ext.getCmp("staffWin").qryGridData(node);
		
			    });
			return moduelTree;
		}
		this.initGrid = function(){
			var column = new Ext.grid.ColumnModel([
					    {id:'staffId',header:'标识',dataIndex:'staffId',hidden:true},
					    {header:'目录标识',dataIndex:'catalogId',hidden:true},
					    {header:'员工编码',dataIndex:'staffCode',sorstaff:true,width:200},
					    {header:'员工姓名',dataIndex:'staffName',sorstaff:true,width:200},
					    {header:'用户名',dataIndex:'userName',sorstaff:true,width:200},
					    {header:'职位',dataIndex:'jobName',sorstaff:true,width:200},
					    {header:'组织',dataIndex:'orgName',sorstaff:true,width:200},
					    {header:'办公电话',dataIndex:'officeTel',sorstaff:true,width:200},
					    {header:'手机',dataIndex:'mobileTel',sorstaff:true,width:200}
			]); 
		
			var dataGrid = new ZTESOFT.Grid({
		    	id:'dataGrid',
		    	title : '员工列表',
		    	region: 'center',
		      	width:this.width*0.8,
				height:this.height,
		        pageSize:10,
		        cm:column,
		        paging:true,
				url:'/MOBILE/ExtServlet?actionName=etl/QryStaffForPopupExt'
			});
			return dataGrid;
		}
		this.qryGridData = function(node){
			var selNode;
		    var staffName = Ext.getCmp("form.staffName").getValue();
		    var userName = Ext.getCmp("form.userName").getValue();
		    if(Ext.isEmpty(node)){
		    	if(!Ext.isEmpty(Ext.getCmp('moduelTree').getSelectionModel().selNode)){
		    		selNode = Ext.getCmp('moduelTree').getSelectionModel().selNode;
		    	}else{
		    		//return 
		    	}
		    	
		    }else{
		    	selNode = node;
		    }
		    
		  	var catalogId;
		    if(Ext.isEmpty(node)){
		    	
		    }else{
		    	catalogId = selNode.id;
		    }
			
			Ext.getCmp('dataGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('dataGrid').store.lastOptions != null){
							Ext.getCmp('dataGrid').store.baseParams = {staffName:staffName,userName:userName,catalogId:catalogId};
						}
					}
			)
			Ext.getCmp('dataGrid').store.load({params:{ start:0, limit:10}});
		}	    
		this.buttons=[{
		            	text:'确定',
		            	onClick:function(){
		            		var record = Ext.getCmp('dataGrid').getSelectionModel().getSelected();
							if(record){
								var staffId = record.data.staffId;
								var staffName = record.data.staffName;
								var staffCode = record.data.staffCode;
								Ext.getCmp('staffWin').parentWin.setRetValue(staffId,staffName,staffCode);
		            			
		            		}
		            		Ext.getCmp('staffWin').close();
						}
		            },{
		            	text:'取消',
		            	onClick:function(){
							 Ext.getCmp('staffWin').close();
						}
		            }];
		this.modal = true;
		this.items=[this.moduelTree(),this.initMainPanel()];
		ZTESOFT.StaffWin.superclass.constructor.apply(this, arguments);
		 
	}
	
	
});

