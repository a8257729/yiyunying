/** by tang.lin 2010-10-26 配置外系统人员 **/
//全局变量
var gridNorthRowCount = 5;
var gridSouthRowCount = 5;
function ConfigStaff(){
	//主窗口
	this.staffRow;//选中的人员数据
	this.initWindow = function(){
	
		this.staffRow = Ext.getCmp("staffGrid").getSelectionModel().getSelected();
	
		//查询面板
		var configQryPanel = this.initConfigQryPanel();
		var configGridsPanel = this.itConifgGridsPanel();
		var configWin = new Ext.Window({
       		id:'configWin',
            title: '外系统人员配置',
            closable:true,
            width:swidth*0.7,
            height:sheight*0.65,
            layout: 'border',
            plain:true,
            items: [configQryPanel,configGridsPanel],
            buttonAlign:'center',
            buttons: [{
	            text: '确定',
	            onClick:function(){
	            	configStaff.commit();
	            }
	        },{
	            text: '关闭',
	            onClick:function(){
	            	configWin.close();
	            }
	        }]
        });
    	 configWin.show();
	}
	//配置页面查询面板
	this.initConfigQryPanel = function(){
		var sysStore = this.initSysStore();
		var qPanel = new Ext.FormPanel({
			id:'configQryPanel',
     		region: 'west',
     	   	frame:true,
       		title: '查询条件',
      		labelWidth: swidth*0.06,
       		collapsible:true,
       	    width:swidth*0.2,
            defaultType: 'textfield',
            items:[{ fieldLabel: '用户名',
                name: 'conf.userName',
                id:  'conf.userName',
                width : swidth*0.1
                },{
                	fieldLabel: '姓名',
	                name: 'conf.staffName',
	                id:  'conf.staffName',
	                width : swidth*0.1
                },{             	
                	xtype:'combo',
                    fieldLabel: '所属系统',
                    name: 'sys',
                    id: 'sys',
                    xtype: 'combo',
                    valueField: 'sysId',
                    displayField: 'sysName',
                    anchor:'90%',
                    width:swidth*0.1,
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    store:sysStore
                }],
               buttons:[{
            		  text: '查询',
            		  listeners:{"click":function(){ configStaff.doQry();  }
			          
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
	//读取系统下拉列表
	this.initSysStore = function(){
		var staffId = this.staffRow.data.staffId;
	    var jobId = this.staffRow.data.jobId;
    
	     var sysStore = new Ext.data.JsonStore({
	        remoteSort: true,
	        fields: ['sysId', 'sysName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QryAllSystemsAction'
	        })
	    });
	    return sysStore;
	}
	//主面板
	this.itConifgGridsPanel = function(){
		//可配置人员列表
		var gridNorth = this.initGridNorth();
		//已配置人员列表
		var gridSouth = this.initGridSouth();
		//可配置人员右键菜单
		var gridNorthContextMenu = this.gridNorthContextMenu();
		//已配置人员右键菜单
		var gridSouthContextMenu = this.gridSouthContextMenu();
		//添加行 右击事件
		gridNorth.on("rowcontextmenu",function(grid,rowIndex,e){
			e.preventDefault();
			Ext.getCmp("gridNorthContextMenu").showAt(e.getXY());
			grid.getSelectionModel().selectRow(rowIndex);
		});
		//删除行 右击事件
		gridSouth.on("rowcontextmenu",function(grid,rowIndex,e){
			e.preventDefault();
			Ext.getCmp("gridSouthContextMenu").showAt(e.getXY());
			grid.getSelectionModel().selectRow(rowIndex);
		});
		//添加行 双击事件
		gridNorth.on("rowdblclick",function(){
			configStaff.addData(Ext.getCmp("gridNorth"));
		});
		//删除行双击事件
		gridSouth.on("rowdblclick",function(){
			configStaff.delData(Ext.getCmp("gridSouth"));
		});
		
		//列表面板
		var gridsPanel = new Ext.Panel({
	    	region: 'center',
			layout: 'border',
			items:[gridNorth,gridSouth]
		});
		return gridsPanel;
	}
	//可配置人员右键菜单
	this.gridNorthContextMenu = function(){
		var contextMenu = new Ext.menu.Menu({
		    id:'gridNorthContextMenu',
		    items:[{
		    	text:'添加行',
		    	handler: function(){
		    		configStaff.addData(Ext.getCmp("gridNorth"));
		    	}
		    }]
		});
	}
	//已配置人员右键菜单
	this.gridSouthContextMenu = function(){
		var contextMenu = new Ext.menu.Menu({
		    id:'gridSouthContextMenu',
		    items:[{
		    	text:'删除行',
		    	handler: function(){
		    		configStaff.delData(Ext.getCmp("gridSouth"));
		    	}
		    }]
		});
	}
	//可配置人员列表
	this.initGridNorth = function(){
		var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'系统编号',dataIndex:'sysId',hidden:true },
		    {header:'角色编号',dataIndex:'roleId',hidden:true },
		    {header:'映射编号',dataIndex:'staffMapBaseId',hidden:true },
		    {header:'系统编码',dataIndex:'sysCode',width:swidth*0.1},
		    {header:'系统名称',dataIndex:'sysName',width:swidth*0.1},
		    {header:'姓名',dataIndex:'staffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'username',width:swidth*0.1},
		    {header:'角色(职位)',dataIndex:'roleName',width:swidth*0.1},
		    {header:'组织路径',dataIndex:'orgPathName',width:swidth*0.2}
		]); 
		
		var gridNorth = new ZTESOFT.Grid({
	    	id:'gridNorth',
	    	title : '可配置人员列表',
	    	region: 'center',
	      	width:10,
	        pageSize:gridNorthRowCount,
	        cm:column,
	        tbar:this.initGridNorthToolBars(),
	        paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QryThirdStaffAllPaging'
		});
		
		return gridNorth;
	}
	//初始化可配置人员工具栏
	this.initGridNorthToolBars = function(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'添加',
            onClick:function(){
            	configStaff.addData(Ext.getCmp("gridNorth"));
            }
        });
		 
        return tb;
	}
	//已配置人员列表
	this.initGridSouth = function(){
		 var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'thirdStaffId',hidden:true },
		    {header:'系统编号',dataIndex:'sysId',hidden:true },
		    {header:'角色编号',dataIndex:'thirdRoleId',hidden:true },
		    {header:'映射编号',dataIndex:'staffMapBaseId',hidden:true },
		    {header:'系统编码',dataIndex:'sysCode',width:swidth*0.1},
		    {header:'系统名称',dataIndex:'sysName',width:swidth*0.1},
		    {header:'姓名',dataIndex:'thirdStaffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'thirdUsername',width:swidth*0.1},
		    {header:'角色(职位)',dataIndex:'thirdRoleName',width:swidth*0.1},
		    {header:'组织路径',dataIndex:'thirdOrgPathName',width:swidth*0.2}
		]); 
		var gridSouth = new ZTESOFT.Grid({
	    	id:'gridSouth',
	    	title : '已配置人员列表',
	    	region: 'south',
	    	width:10,
	    	tbar:this.initGirdSouthToolBars(),
	        pageSize:gridSouthRowCount,
	        cm:column,
	        url:'/MOBILE/ExtServlet?actionName=system/QryThirdStaffPaging'
			
		});
		return gridSouth;
	}
	//初始化已配置人员工具栏
	this.initGirdSouthToolBars = function(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'删除',
             onClick:function(){
            	configStaff.delData(Ext.getCmp("gridSouth"));
            }
        });
		 
        return tb;
	}
	//查询可配置人员列表
	this.girdNorthQryData = function(){
		    var staffName = Ext.getCmp("conf.staffName").getValue();
		    var userName = Ext.getCmp("conf.userName").getValue();
		    var sysId = Ext.getCmp("sys").getValue();
	   		var jobId = this.staffRow.data.jobId;
			
			Ext.getCmp('gridNorth').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('gridNorth').store.lastOptions != null){
							Ext.getCmp('gridNorth').store.baseParams = {staffName:staffName,userName:userName,sysId:sysId};
						}
					}
			)
			Ext.getCmp('gridNorth').store.load({params:{ start:0, limit:gridNorthRowCount}});
	}
	//查询已配置人员列表
	this.gridSouthQryData = function(){
		var staffId = this.staffRow.data.staffId;
	    var jobId = this.staffRow.data.jobId;
		
		Ext.getCmp('gridSouth').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('gridSouth').store.lastOptions != null){
						Ext.getCmp('gridSouth').store.baseParams = {staffId:staffId,jobId:jobId};
					}
				}
		)
		Ext.getCmp('gridSouth').store.load({params:{ start:0, limit:gridSouthRowCount}});
	}
	//增加配置数据
	this.addData = function(grid){
		 
		 if(Ext.isEmpty(grid.getSelectionModel().getSelected())){
		 	return;
		 }
		 
		 var g2Data = grid.getSelectionModel().getSelected().data;
		 for(var i=0;i<Ext.getCmp("gridSouth").getStore().getCount();i++){
		 	if(g2Data.staffMapBaseId == Ext.getCmp("gridSouth").getStore().getAt(i).data.staffMapBaseId){
		 		return;
		 	}
		 }

		 var row = new Ext.data.Record({
		 	   staffMapBaseId:g2Data.staffMapBaseId,
		       thirdStaffId:g2Data.staffId,                  
		       sysId:g2Data.sysId,
		       thirdRoleId:g2Data.roleId,
		       sysCode:g2Data.sysCode,
		       sysName:g2Data.sysName,
		       thirdStaffName:g2Data.staffName,
		       thirdUsername:g2Data.username,
		       thirdRoleName:g2Data.roleName,
		       thirdOrgPathName:g2Data.orgPathName
		   }); 
  
 		Ext.getCmp("gridSouth").getStore().add(row);
	}
	//删除配置数据
	this.delData = function(grid){
		 var g1Row = grid.getSelectionModel().getSelected();
		 if(Ext.isEmpty(g1Row)){
		 	return;
		 }
		 grid.getStore().remove(g1Row);
	}
	//查询列表数据
	this.doQry = function(){
		configStaff.girdNorthQryData(); 
		configStaff.gridSouthQryData();
	}
	//提交数据
	this.commit = function(){
		//门户人员信息
		var staffData = this.staffRow.data;
		//配置外系统人员信息
		var staffMapBaseIds = ""
		for(var i=0;i<Ext.getCmp('gridSouth').getStore().getCount();i++){
			if(i!=Ext.getCmp('gridSouth').getStore().getCount()-1){
				staffMapBaseIds+=Ext.getCmp('gridSouth').getStore().getAt(i).data.staffMapBaseId+",";
			}else{
				staffMapBaseIds+=Ext.getCmp('gridSouth').getStore().getAt(i).data.staffMapBaseId;
			}
		}
		var paramObj = Ext.apply(new Object(),staffData);
		paramObj.staffMapBaseIds = staffMapBaseIds;
		var result = invokeAction("/mappingService/InsertStaffMappingAction",paramObj);
		
		if(result =="success"){
				Ext.example.msg('','保存配置成功'); 
				oper.outSysStaffGirdQry(this.staffRow);
				Ext.getCmp("configWin").close();
		}else{
				Ext.example.msg('','保存配置失败'); 
		}
	}

}
