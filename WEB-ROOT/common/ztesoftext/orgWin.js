Ext.ns("ZTESOFT");
ZTESOFT.OrgWin = Ext.extend(Ext.Window,{
	constructor:function(config){
		if(Ext.isEmpty(config.title)){
			 this.title = '组织选择';
		}
		if(Ext.isEmpty(config.id)){
			 this.id = 'orgWin';
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
		                    fieldLabel: '组织名称',
		                    name: 'form.orgName',
		                    id: 'form.orgName',
		                    anchor:'95%'
		                }]
		            },{
		                columnWidth:.3,
		                layout: 'form',
		                items: [{
		                    xtype:'textfield',
		                    fieldLabel: '组织编码',
		                    name: 'form.orgCode',
		                    id: 'form.orgCode',
		                    anchor:'95%'
		                }]
		            }]
		        }],
	            
	               buttons:[{
	            		  text: '查询',
	            		  listeners:{"click":function(){ Ext.getCmp("orgWin").qryGridData();  }
				          
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
					Ext.getCmp("orgWin").qryGridData(node);
		
			    });
			return moduelTree;
		}
		this.initGrid = function(){
			var column = new Ext.grid.ColumnModel([
					    {id:'orgId',header:'标识',dataIndex:'orgId',hidden:true},
					    {header:'目录标识',dataIndex:'catalogId',hidden:true},
					    {header:'组织编码',dataIndex:'orgCode',sororg:true,width:200},
					    {header:'组织名称',dataIndex:'orgName',sororg:true,width:200},
					    {header:'组织模版',dataIndex:'orgTmpName',sororg:true,width:200},
					    {header:'组织路径',dataIndex:'orgPath',sororg:true,width:200}
					    
			]); 
		
			var dataGrid = new ZTESOFT.Grid({
		    	id:'dataGrid',
		    	title : '组织列表',
		    	region: 'center',
		      	width:this.width*0.8,
				height:this.height,
		        pageSize:10,
		        cm:column,
		        paging:true,
				url:'/MOBILE/ExtServlet?actionName=etl/QryOrgForPopupExt'
			});
			return dataGrid;
		}
		this.qryGridData = function(node){
			var selNode;
		    var orgName = Ext.getCmp("form.orgName").getValue();
		    var orgCode = Ext.getCmp("form.orgCode").getValue();
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
							Ext.getCmp('dataGrid').store.baseParams = {orgName:orgName,orgCode:orgCode,catalogId:catalogId};
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
								var orgId = record.data.orgId;
								var orgName = record.data.orgName;
								var orgCode = record.data.orgCode;
								Ext.getCmp('orgWin').parentWin.setRetValue(orgId,orgName,orgCode);
		            			
		            		}
		            		Ext.getCmp('orgWin').close();
						}
		            },{
		            	text:'取消',
		            	onClick:function(){
							 Ext.getCmp('orgWin').close();
						}
		            }];
		this.modal = true;
		this.items=[this.moduelTree(),this.initMainPanel()];
		ZTESOFT.OrgWin.superclass.constructor.apply(this, arguments);
		 
	}
	
	
});

