Ext.ns("ZTESOFT");
ZTESOFT.TableWin = Ext.extend(Ext.Window,{
	constructor:function(config){
		if(Ext.isEmpty(config.title)){
			 this.title = '系统表选择';
		}
		if(Ext.isEmpty(config.id)){
			 this.id = 'tableWin';
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
		                    fieldLabel: '表名称',
		                    name: 'form.tableName',
		                    id: 'form.tableName',
		                    anchor:'95%'
		                }]
		            },{
		                columnWidth:.3,
		                layout: 'form',
		                items: [{
		                    xtype:'textfield',
		                    fieldLabel: '表编码',
		                    name: 'form.tableCode',
		                    id: 'form.tableCode',
		                    anchor:'95%'
		                }]
		            }]
		        }],
	            
	               buttons:[{
	            		  text: '查询',
	            		  listeners:{"click":function(){ Ext.getCmp("tableWin").qryGridData();  }
				          
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
			var moduelTree = new Ext.tree.TreePanel({
					id: 'moduelTree',
			    	title:'系统表目录',
			    	region: 'west',
			        autoScroll:true,
			        loader: new Ext.tree.TreeLoader({
			        	dataUrl:'/MOBILE/ExtServlet?actionName=etl/QryMeterdataCatalogsExtAction'
			        }),
			        containerScroll: true,
			        border: false,
			        width:this.width*0.2,
				    height:this.height,
			        rootVisible: false,
			        root: new Ext.tree.AsyncTreeNode({
			            id:'0'
			        })
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
				    var isLeaf = node.attributes.isLeaf;
				    if(isLeaf == 1){
				    	
				    }
					    
					Ext.getCmp("tableWin").qryGridData(node);
		
			    });
			return moduelTree;
		}
		this.initGrid = function(){
			var column = new Ext.grid.ColumnModel([
					    {id:'tablesId',header:'标识',dataIndex:'tablesId',hidden:true},
					    {header:'目录标识',dataIndex:'catalogId',hidden:true},
					    {header:'系统标识',dataIndex:'sysId',hidden:true},
					    {header:'系统表编码',dataIndex:'tableCode',sortable:true,width:200},
					    {header:'系统表名',dataIndex:'tableName',sortable:true,width:200},
					    {header:'系统',dataIndex:'sysName',sortable:true,width:200},
					    {header:'备注',dataIndex:'remark',sortable:true,width:200}
			]); 
		
			var dataGrid = new ZTESOFT.Grid({
		    	id:'dataGrid',
		    	title : '系统表列表',
		    	region: 'center',
		      	width:this.width*0.8,
				height:this.height,
		        pageSize:10,
		        cm:column,
		        paging:true,
				url:'/MOBILE/ExtServlet?actionName=etl/QryMeterdataTabExtAction'
			});
			return dataGrid;
		}
		this.qryGridData = function(node){
			var selNode;
		    var tableName = Ext.getCmp("form.tableName").getValue();
		    var tableCode = Ext.getCmp("form.tableCode").getValue();
		    if(Ext.isEmpty(node)){
		    	if(!Ext.isEmpty(Ext.getCmp('moduelTree').getSelectionModel().selNode)){
		    		selNode = Ext.getCmp('moduelTree').getSelectionModel().selNode;
		    	}else{
		    		return 
		    	}
		    	
		    }else{
		    	selNode = node;
		    }
		    
		  	var catalogId = selNode.id;
			
			Ext.getCmp('dataGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('dataGrid').store.lastOptions != null){
							Ext.getCmp('dataGrid').store.baseParams = {tableName:tableName,tableCode:tableCode,catalogId:catalogId};
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
								var tableId = record.data.tablesId;
								var tableName = record.data.tableName;
								var tableCode = record.data.tableCode;
								Ext.getCmp('tableWin').parentWin.setRetValue(tableId,tableName,tableCode);
		            			
		            		}
		            		Ext.getCmp('tableWin').close();
						}
		            },{
		            	text:'取消',
		            	onClick:function(){
							 Ext.getCmp('tableWin').close();
						}
		            }];
		this.modal = true;
		this.items=[this.moduelTree(),this.initMainPanel()];
		ZTESOFT.TableWin.superclass.constructor.apply(this, arguments);
		 
	}
	
	
});

