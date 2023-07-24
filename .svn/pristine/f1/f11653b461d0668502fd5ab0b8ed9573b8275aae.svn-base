<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>帐号映射(旧)</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>
						
	</body>
</html>
<script type="text/javascript"	src="js/configStaff.js"></script>
<script language="JScript">
/**	by tang.lin **/
//全局变量
//获取分辨率


var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var staffRowCount = 5; //人员列表每页显示多少条数据


var outSysStaffGirdRowCount = 5 //外系统人员每页显示多少数据


var oper = new Oper(); 
var configStaff = new ConfigStaff();
Ext.onReady(function(){
	var orgTree = oper.initOrgTree();
		var mainPanel = oper.initMainPanel();
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			margins: '5 5 5 5',
			items:[orgTree,mainPanel]
	});
});
//操作类定义


function Oper(){
	//初始化主面板
	this.initMainPanel = function(){
		 //右键菜单
		 var initContextMenu = this.initContextMenu();
		 //查询面板
		 var qryPanel = this.initQryPanel();
		 //人员列表
		 var staffGrid = this.initStaffGrid();
		 //外系统人员列表

		 var outSysStaffGird = this.initOutSysStaffGird();
		 //主面板


		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,staffGrid,outSysStaffGird]
	 	});
	 	return mainPanel;
	}
	//初始化右键菜单


	this.initContextMenu = function(){
		var rightClick = new Ext.menu.Menu({
		    id:'rightMenu',
		    items:[{
		    	text:'配置外系统帐号',
		    	handler: oper.configStaff
		    }]
		});
		return rightClick;
	}
	//初始化组织树
	this.initOrgTree = function(){
			var orgTree = new ZTESOFT.OrgTree({
				id:'orgTree',
		       	region: 'west',
		       	allOrgs:true//是否查询全组织


		       	//checked:true 是否需要复选框    
			});
			this.initOrgTreeEvent();
			return orgTree;
	}
	//初始化组织树事件
	this.initOrgTreeEvent = function(){
		Ext.getCmp('orgTree').on('click', function(node){ //使节点可以单击展开收起
		    if(node.isLeaf() == false){
			     if(node.expanded == false){
			     	node.expand();//展开
			     }else{
			     	node.collapse();//收起
			     } 
		 	}
			oper.initStaffGridQry(node);
		 });
		
	   
	}
	//初始化查询面板




	this.initQryPanel = function(){
		var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
     	region: 'north',
     	frame:true,
        title: '查询条件',
        bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true, 
        height:Ext.getBody().getSize().height*0.20,
        width:Ext.getBody().getSize().width*0.8,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '姓名',
                    name: 'staffName',
                    id: 'staffName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName',
                    id: 'userName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    name: 'qryType',
                    fieldLabel: '查询类别',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    id: 'qryType',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: 'qrySelf',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['qrySelf','查询本组织'],['qrySub','查询本组织及下属组织'],['qryNull','查询无职位人员']]
                    })
                },{
                    
                }]
            }]
        }],
        buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
                    			oper.initStaffGridQry(Ext.getCmp('orgTree').getSelectionModel().getSelectedNode());
                    		}	
			            }},{
                    	text: '重置' ,
                    	listeners:{"click":function(){
			                Ext.getCmp('qryPanel').form.reset();
			            }} 
                    }]
    	});
    	return qryPanel;
	}
	
	this.initStaffGrid = function(){
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'职位编号',dataIndex:'jobId',hidden:true },
		    {header:'组织编号',dataIndex:'orgId',hidden:true },
		    {header:'姓名',dataIndex:'staffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'userName',width:swidth*0.1},
		    {header:'职位',dataIndex:'jobName',width:swidth*0.1},
		    {header:'职位是否缺省',dataIndex:'isbasic',width:swidth*0.1},
		    {header:'组织',dataIndex:'orgPathName',width:swidth*0.15},
		    {header:'办公电话',dataIndex:'officeTel',width:swidth*0.1},
		    {header:'移动电话',dataIndex:'mobileTel',width:swidth*0.1}
		]);
		  //人员信息
		var staffGrid =  new ZTESOFT.Grid({
			id:'staffGrid',
	    	region: 'center',
	        title:'人员列表',
			cm:column,
			pageSize:5,
			paging:true,
			tbar:oper.initStaffGridToolsBar(),
			url:'/MOBILE/ExtServlet?actionName=system/StaffPagingAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	oper.outSysStaffGirdQry(rec);
	                        }
	                    }
	        })
	    });
	    this.initContextMenuEvent();	   
	    return staffGrid;
	
	}
	
	//人员列表右键菜单
	this.initContextMenuEvent = function (){
		
		Ext.getCmp('staffGrid').addListener('rowcontextmenu',function(grid,rowIndex,e){
				e.preventDefault();
				Ext.getCmp("rightMenu").showAt(e.getXY());
				Ext.getCmp('staffGrid').getSelectionModel().selectRow(rowIndex);
		});
	}
	//查询人员列表
	this.initStaffGridQry = function(node){
		//组织树当前节点 node
		if(node==null){
			Ext.example.msg('','请选择组织'); 
			return;
		}
		//绑定发送请求参数




		Ext.getCmp('staffGrid').store.on('beforeload',function(store){
			//获取查询参数
			var staffName = Ext.getCmp("staffName").getValue() ;
	        var userName  = Ext.getCmp("userName").getValue() ;
			var qryType   = Ext.getCmp("qryType").getValue() ;
			var orgPathCode = node.attributes.orgPathCode;
			var orgId = node.attributes.orgId;
			if(Ext.getCmp('staffGrid').store.lastOptions != null){
				Ext.getCmp('staffGrid').store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:'',userName:userName,mobileTel:'',qryType:qryType};
			}
		});
		Ext.getCmp('staffGrid').store.removeAll() ;
		Ext.getCmp('staffGrid').store.load({params:{start:0, limit:staffRowCount}});
		//load数据后自动选择第一行数据,load事件为加载数据完成后触发
		Ext.getCmp('staffGrid').store.on('load',function(){
			Ext.getCmp('staffGrid').getSelectionModel().selectFirstRow();
		});
		 
		
	}
	this.initOutSysStaffGird = function(){
		var columnOutSysStaffGird = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'thirdStaffId',hidden:true },
		    {header:'系统编号',dataIndex:'sysId',hidden:true },
		    {header:'角色编号',dataIndex:'thirdRoleId',hidden:true },
		    {header:'映射编号',dataIndex:'staffMapBaseId',hidden:true },
		    {header:'系统编码',dataIndex:'sysCode',width:swidth*0.1},
		    {header:'系统名称',dataIndex:'sysName',width:swidth*0.1},
		    {header:'姓名',dataIndex:'thirdStaffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'thirdUsername',width:swidth*0.1},
		    {header:'角色(职位)',dataIndex:'thirdRoleName',width:swidth*0.1},
		    {header:'组织路径',dataIndex:'thirdOrgPathName'}
		]); 

	    var outSysStaffGird = new ZTESOFT.Grid({
	    	id:'outSysStaffGird',
	    	title : '外系统人员列表',
	    	region: 'south',
	    	width:10,
	        pageSize:5,
	        paging:true,
	        cm:columnOutSysStaffGird,
			url:'/MOBILE/ExtPagingServlet?actionName=QryThirdStaffPaging'
		});
		return outSysStaffGird;
	}
	//查询外系统人员


	this.outSysStaffGirdQry = function(staffRow){
		var staffId = staffRow.data.staffId;
		var jobId = staffRow.data.jobId;
		//绑定发送请求参数


		Ext.getCmp('outSysStaffGird').store.on('beforeload',function(store){
			if(Ext.getCmp('outSysStaffGird').store.lastOptions != null){
				Ext.getCmp('outSysStaffGird').store.baseParams = {staffId:staffId,jobId:jobId, start:0, limit:outSysStaffGirdRowCount};
			}
		});
		
		Ext.getCmp('outSysStaffGird').store.load({params:{staffId:staffId,jobId:jobId, start:0, limit:outSysStaffGirdRowCount}});
	}
	//人员列表工具栏


	this.initStaffGridToolsBar = function(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'配置外系统帐号',
            onClick:function(){
            	oper.configStaff();
            }
        });
		 
        return tb;
	}
	//配置外系统人员




	this.configStaff = function(){
		if(Ext.isEmpty(Ext.getCmp("staffGrid").getSelectionModel().getSelected())){
			return;
		};
		configStaff.initWindow();
		configStaff.doQry();
	}
}


</script>