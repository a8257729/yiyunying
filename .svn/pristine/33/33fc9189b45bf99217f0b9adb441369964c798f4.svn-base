<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>个人帐号映射</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	 <div id='sysGird' style="width: 100%; height: 100%; overflow: hidden"></div>
						
	</body>
</html>

<script language="JScript">
/** by tang.lin 2010-10-27 个人帐号映射 **/
//全局变量
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var session1 = GetSession();
var configStaff = new ConfigStaff();
var gridNorthRowCount = 10;
var gridSouthRowCount = 5;
Ext.onReady(function(){
	//给staffRow赋session值,配置个人帐号
	configStaff.staffRow = new Object();
	configStaff.staffRow.data = Ext.apply(new Object(),session1.staff);
	configStaff.staffRow.data = Ext.apply(configStaff.staffRow.data,session1.job);
	configStaff.staffRow.data = Ext.apply(configStaff.staffRow.data,session1.org);
	var configQryPanel = configStaff.initConfigQryPanel();
	var configGridsPanel = configStaff.itConifgGridsPanel();
	var configWin = new Ext.Viewport({
       		el:'sysGird',
	        layout: 'border',
            items: [configQryPanel,configGridsPanel]
    });
    configStaff.doQry();
	
});
function ConfigStaff(){
	//主窗口

	this.staffRow;//选中的人员数据


	//配置页面查询面板
	this.initConfigQryPanel = function(){
		var sysStore = this.initSysStore();
		var qPanel = new Ext.FormPanel({
			id:'configQryPanel',
     		region: 'west',
     	   	frame:true,
       		title: '查询条件',
      		labelWidth: 60,
       		collapsible:true,
       	    width:200,
       	    
            defaultType: 'textfield',
            items:[{ fieldLabel: '用户名',
                name: 'conf.userName',
                id:  'conf.userName',
                width : 120
                },{
                	fieldLabel: '姓名',
	                name: 'conf.staffName',
	                id:  'conf.staffName',
	                width : 120
                },{             	
                	xtype:'combo',
                    fieldLabel: '所属系统',
                    name: 'sys',
                    id: 'sys',
                    xtype: 'combo',
                    valueField: 'sysId',
                    displayField: 'sysName',
                    anchor:'90%',
                    width:120,
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
		    {header:'系统编码',dataIndex:'sysCode',width:100},
		    {header:'系统名称',dataIndex:'sysName',width:100},
		    {header:'姓名',dataIndex:'staffName',width:100},
		    {header:'用户名',dataIndex:'username',width:100},
		    {header:'角色(职位)',dataIndex:'roleName',width:120},
		    {header:'组织路径',dataIndex:'orgPathName',width:280}
		]); 
		
		var gridNorth = new ZTESOFT.Grid({
	    	id:'gridNorth',
	    	title : '可配置人员列表',
	    	region: 'center',
	      	width:10,
	      	height:sheight*0.4,
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
		    {header:'系统编码',dataIndex:'sysCode',width:100},
		    {header:'系统名称',dataIndex:'sysName',width:100},
		    {header:'姓名',dataIndex:'thirdStaffName',width:100},
		    {header:'用户名',dataIndex:'thirdUsername',width:100},
		    {header:'角色(职位)',dataIndex:'thirdRoleName',width:120},
		    {header:'组织路径',dataIndex:'thirdOrgPathName',width:280}
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
		 	Ext.example.msg('','请选择需要添加的记录'); 
		 	return;
		 }
		 
		 var g2Data = grid.getSelectionModel().getSelected().data;
		 for(var i=0;i<Ext.getCmp("gridSouth").getStore().getCount();i++){
		 	if(g2Data.staffMapBaseId == Ext.getCmp("gridSouth").getStore().getAt(i).data.staffMapBaseId){
		 		Ext.example.msg('','记录已存在请误重复添加'); 
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
 		this.commit();
	}
	//删除配置数据
	this.delData = function(grid){
		 var g1Row = grid.getSelectionModel().getSelected();
		 if(Ext.isEmpty(g1Row)){
		 	Ext.example.msg('','请选择需要删除的记录'); 
		 	return;
		 }
		 grid.getStore().remove(g1Row);
		 this.commit();
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
				Ext.example.msg('','操作成功'); 
				
		}else{
				Ext.example.msg('','操作失败'); 
		}
	}

}
</script>