<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>角色管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script language="javascript" src="js/RoleInfo.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="roleMngDiv"></div>
	</body>

</html>
<script language="JScript">

/**	by fang.li   2010.10 **/

var session1 = GetSession();
var staffId = session1.staff.staffId ;

var areaId ;
var areaName ;
var oper = new Oper();
var roleMngOper = new RoleOper();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var areaTree = oper.initAreaTree();
	var roleGrid = oper.initRoleGrid();
	var spciPrivTree = oper.initSpciPrivTree();
	
	oper.initRigthClick();
	
	oper.initAreaTreeEvent();
	oper.initRoleGridEvent();
	
	var rightPanel = new Ext.Panel({
		border: false,
		region: 'center',
		layout: 'border',
		items:[roleGrid,spciPrivTree]
	});
	
	var viewport = new Ext.Viewport({
		border: false,
		el:'roleMngDiv',
		layout: 'border',
		items:[areaTree,rightPanel]
	});
});

function Oper(){

	//初始化区域树
	this.initAreaTree = function(){
		var areaTree = new Ext.tree.TreePanel({
			id: 'areaTree',
	        region: 'west',
	        animate:true, 
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/AreaTreeSelAction',
	        	baseParams:{staffId:staffId}
	        }),
	        containerScroll: true,
	        border: true,
	        width:Ext.getBody().getSize().width*0.2,
		    height:Ext.getBody().getSize().height,
	        dropConfig: {appendOnly:true}
	    });
	    
	    var areaRoot = new Ext.tree.AsyncTreeNode({
	        text: '区域列表',
	        draggable:false,
	        id:1
	    });
	    areaTree.setRootNode(areaRoot);
	    areaRoot.expand(false, false);
	    
	    return areaTree;
	}
	
	//初始化角色列表
	this.initRoleGrid = function(){
		var roleStore = new Ext.data.JsonStore({
			id: 'roleStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'roleId', 'roleName', 'comments', 'areaId','areaName'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/RoleByAreaGridAction'
	        }),
	        baseParams:{areaId:0},
	        listeners:{
	        	load: function(store){
					roleGrid.getSelectionModel().selectRow(0);
				}
	        }
	    });
	
		//创建列
		var roleColumn = new Ext.grid.ColumnModel([
		    {header:'角色编号',dataIndex:'roleId',hidden:true },
		    {header:'角色名称',dataIndex:'roleName',width:230 },
		    {header:'备注',dataIndex:'comments',width:400 },
		    {header:'区域编号',dataIndex:'areaId',hidden:true},
		    {header:'区域名称',dataIndex:'areaName',width:200 }
		]);
	
		var roleGrid = new Ext.grid.GridPanel({
			id: 'roleGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'角色列表',
	        store: roleStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm: roleColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec) {
		            	var roleId = sm.getSelections()[0].data.roleId ;
		            	Ext.getCmp('spciPrivTree').getLoader().baseParams.roleId = roleId ;
		            	Ext.getCmp('spciPrivTree').root.reload();
		            }
		        }
		    })
	    });
	    return roleGrid ;
	}
	
	//初始化权限树
	this.initSpciPrivTree = function(){
		var spciPrivTree = new Ext.tree.TreePanel({
			id: 'spciPrivTree',
			title: '权限信息',
	        region: 'south',
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/RolePrivListAction',
	        	baseParams: {roleId:0},
	        	listeners: {
	        		load:function(){
	        			spciPrivTree.getNodeById('0').expand();
	        		}}
	        }),
	        containerScroll: true,
	        border: true,
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
	        dropConfig: {appendOnly:true},
	        rootVisible: false,
	        root: {
	            nodeType: 'async'
	        }
	    });
	    
	    //使节点可以单击展开收起，默认是双击的
	    spciPrivTree.on('click', function(node){ 
		    if(node.isLeaf() == false){
		     if(node.expanded == false){
		     	node.expand();//展开
		     }else{
		     	node.collapse();//收起
		    	} 
		    }
	    });
	    return spciPrivTree ;
	}
	
	//定义右键菜单
	this.initRigthClick = function(){
		var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
	}
	
	//初始化区域树事件
	this.initAreaTreeEvent = function(){
		var areaTree = Ext.getCmp('areaTree') ;
		var roleStore = Ext.getCmp('roleGrid').store ;
		areaTree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
		    areaId = node.id ;
		    areaName = node.text ;
		    roleStore.on('beforeload',
				function(store){ 
					if(roleStore.lastOptions != null){
						roleStore.baseParams = {areaId:areaId};
					}
				}
			)
		    roleStore.removeAll();
			roleStore.load();
	    });
	}
	
	//添加角色列表右键事件
	this.initRoleGridEvent = function(){
		var roleGrid = Ext.getCmp('roleGrid') ;
		roleGrid.addListener('rowcontextmenu', oper.rightClickFn);
		roleGrid.addListener('contextmenu', oper.nullRightClickFn);
	}
	
	this.rightClickFn = function(roleGrid,rowIndex,e){
		var rightClick = Ext.getCmp('rightClick') ;
		rightClick.removeAll();
		var i = 0 ;
		if(session1.hasPriv("oaas_role_mng")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '新增角色' ,handler: function() {
				rightClick.hide();
				oper.roleAdd();
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '修改角色' ,handler: function() {
				rightClick.hide();
				oper.roleMod();
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除角色' ,handler: function() {
				rightClick.hide();
				oper.delRoleVlit();
			}}));
			rightClick.add(new Ext.menu.Separator());
			i++ ;
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置权限' ,handler: function() {
				rightClick.hide();
				oper.rolePrivConf();
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置移动权限' ,handler: function() {
				rightClick.hide();
				oper.roleMobPrivConf();
			}}));
		}
		
		roleGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var selNode = Ext.getCmp('areaTree').getSelectionModel().selNode ;
		var nullRightClick = Ext.getCmp('nullRightClick') ;
    	nullRightClick.removeAll();
    	if(session1.hasPriv("oaas_role_mng") && selNode){
			nullRightClick.add(new Ext.menu.Item({ text: '新增角色' ,handler: function() {
				nullRightClick.hide();
				oper.roleAdd();
			}}));
			nullRightClick.showAt(e.getXY());
		}
	}
	
	this.roleAdd = function(){
		roleMngOper.showRoleInfo('add');
	}
	
	this.roleMod = function(){
		roleMngOper.showRoleInfo('mod');
	}
	
	this.roleDel = function(btn){
		//角色编号
		var roleId = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleId ;
		if(btn == 'yes'){
			callRemoteFunction("com.ztesoft.eoms.oaas.role.impl.ConfRoleDAOImpl", "removeRolesBatch",roleId);
			callRemoteFunction("com.zterc.uos.oaas.service.rolemanager.RoleManagerWeb", "delete",roleId);
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
	            Ext.example.msg('','角色删除成功！');
	        }, 1000);
	        Ext.getCmp('roleGrid').store.reload();
		}
	}
	
	this.delRoleVlit = function(){
		//角色编号
		var roleId = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleId ;
		//职位模板
		var _posts = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "findPostsByRoleXml", roleId);
		//职位
		var _jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "findJobsByRoleXml", roleId);
		//人员				
		var _staffs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "findStaffsByRoleXml",roleId);
		if(_posts == "<items></items>" && _jobs == "<items></items>" && _staffs == "<items></items>"){
			Ext.MessageBox.confirm('提示', '你确定要删除吗?', oper.roleDel);
		}else{
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '该角色已经被使用，不能执行删除操作!',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
	}
	
	this.rolePrivConf =function (){
    	var _obj = new Object();
		_obj.type = "Role";
		_obj.roleId = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleId ;
		window.showModalDialog("PrivSelect.jsp", _obj, "dialogWidth:750px;dialogHeight:550px;status:no");
       	Ext.getCmp('spciPrivTree').getLoader().baseParams.roleId = _obj.roleId ;
       	Ext.getCmp('spciPrivTree').root.reload();
    }
    this.roleMobPrivConf = function (){
    	var _obj = new Object();
		_obj.type = "Role";
		_obj.roleId = Ext.getCmp('roleGrid').getSelectionModel().getSelections()[0].data.roleId ;
		window.showModalDialog("../systemMobMgr/MobPrivSelect.jsp", _obj, "dialogWidth:750px;dialogHeight:550px;status:no");
       	Ext.getCmp('spciPrivTree').getLoader().baseParams.roleId = _obj.roleId ;
       	Ext.getCmp('spciPrivTree').root.reload();
    }
}
</script>