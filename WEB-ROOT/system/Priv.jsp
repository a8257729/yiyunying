<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>权限管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<link rel="stylesheet" type="text/css" href="../ext/examples/ux/treegrid/treegrid.css"/>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridSorter.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridColumnResizer.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridNodeUI.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridLoader.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridColumns.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGrid.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="js/privClassMng.js"></script>
		<script language="javascript" src="js/privMng.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="privMngDiv"></div>
	</body>
	
</html>

<script language="JScript">

/**	by fang.li   2010.10 **/

var session1 = GetSession();
var oper = new Oper(); 
var privClassOper = new PrivClassOper();
var privOper = new PrivOper();

Ext.onReady(function() {
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var treeMenu = oper.initTreeMenu();
	var gridMenu = oper.initGridMenu();
	
	var privClassTree = oper.initPrivClassTree();
	var privGrid = oper.initPrivGrid();
	
	var initTreeEvent = oper.initTreeEvent();
	var initGridEvent = oper.initGridEvent();
	
	var viewport = new Ext.Viewport({
		border: false,
		el:'privMngDiv',
		layout: 'border',
		items:[privClassTree,privGrid]
	});
});

function getPrivTreeWidth(){
		if(screen.availWidth >= 1024&&screen.availWidth < 1280){
			return screen.availWidth*0.85;
		}else if(screen.availWidth >= 1280){
			return screen.availWidth*0.9;
		}else{
			return screen.availWidth*0.8;
		}
	}

function Oper(){
	//初始化权限类别列表面板
	this.initPrivClassTree = function(){
		var privClassTree = new Ext.ux.tree.TreeGrid({
			id:'privClassTree',
	        title: '权限类别列表',
	        width:getPrivTreeWidth(),
		    height:Ext.getBody().getSize().height*0.5,
	        renderTo: Ext.getBody(),
	        enableDD: true,
			region: 'center',
			border : false,
	        columns:[{
	            header: '名称',
	            width: getPrivTreeWidth()*0.4,
	            dataIndex: 'privClassName',
	            align: 'center'
	        },{
	            header: '来源',
	            width: getPrivTreeWidth()*0.2,
	            dataIndex: 'source',
	            align: 'center'
	        },{
	            header: '备注',
	            width: getPrivTreeWidth()*0.4,
	            dataIndex: 'comments',
	            align: 'center'
	        }],
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PrivClassGridAction'
	        }),
	        root: new Ext.tree.AsyncTreeNode({
	            id:'0'
	        })
	    });
	    return privClassTree;
	}
	
	//初始化权限列表
	this.initPrivGrid = function(){
		function renderState(value, p, r){
	        if(value == '10A'){
	        	return '<span style="color:green;">有效</span>';
	        }else{
	        	return '<span style="color:red;">无效</span>';
	        }
	    }
	    
	    var privStore = new Ext.data.JsonStore({
	    	id:'privStore',
	        remoteSort: true,
	        fields: [ 
	        	'privCode', 'privName', 'comments', 'actionPriv','state'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/PrivGridAction'
	        }),
	        baseParams:{privClassId:0}
	    });
		
		//创建列
		var privColumn = new Ext.grid.ColumnModel([
		    {header:'权限编码',dataIndex:'privCode',width:200 },
		    {header:'权限名称',dataIndex:'privName',width:200 },
		    {header:'备注',dataIndex:'comments',width:340},
		    {header:'来源',dataIndex:'actionPriv',width:200},
		    {header:'状态',dataIndex:'state',renderer:renderState,width:100}
		]);
		privColumn.defaultSortable = true;//默认可排序
		
		var privGrid = new Ext.grid.GridPanel({
			id:'privGrid',
	        region: 'south',
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'权限列表',
	        store: privStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:privColumn,
			border : false,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true})
	    });
		privStore.load();
		
		return privGrid;
	}
	
	//定义权限类别树事件
	this.initTreeEvent = function(){
		var privClassTree = Ext.getCmp('privClassTree') ;
		var privStore = Ext.getCmp('privGrid').store ;
		
		privClassTree.on('click', function(node){
			if(node.isLeaf() == false){
				if(node.expanded == false){
					node.expand();//展开
				}else{
					node.collapse();//收起
				}
		    }
			
			//刷新权限列表
		    privStore.on('beforeload',
				function(store){ 
					if(privStore.lastOptions != null){
						privStore.baseParams = {privClassId:node.id};
					}
				}
			)
		    privStore.removeAll();
			privStore.load();
		});
		
		//注册权限类别树的右键事件（右键菜单）
		privClassTree.on("contextmenu",oper.privClassContextMenu,privClassTree);
	}
	
	//定义权限列表事件
	this.initGridEvent = function(){
		Ext.getCmp('privGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('privGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//权限类别树右键菜单定义
	this.initTreeMenu = function(){
		var treeMenu = new Ext.menu.Menu({
	        id : 'treeMenu'
		});
		return treeMenu ; 
	}
	
	//权限列表右键菜单定义
	this.initGridMenu = function(){
		var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
	}
	
	//权限类别树右键菜单组装
	this.privClassContextMenu = function (node,e){
		node.select();
		var i = 0 ;
		
		var treeMenu = Ext.getCmp('treeMenu') ;
		
		treeMenu.removeAll();
		//根据权限添加菜单项
		if(session1.hasPriv("oaas_priv_mng") && node.attributes.sourceCode == 'model'){
			treeMenu.insert(i++,new Ext.menu.Item({ text: '增加权限类别' ,handler: function() {
				treeMenu.hide();
				oper.privClassAdd();
			}}));
			treeMenu.add(new Ext.menu.Separator());
			i++ ;
			treeMenu.insert(i++,new Ext.menu.Item({ text: '增加下属权限类别' ,handler: function() {
				treeMenu.hide();
				oper.subPrivClassAdd();
			}}));
		}
		if(session1.hasPriv("oaas_priv_mng") && node.attributes.sourceCode == 'privMng'){
			treeMenu.insert(i++,new Ext.menu.Item({ text: '增加权限类别' ,handler: function() {
				treeMenu.hide();
				oper.privClassAdd();
			}}));
			treeMenu.insert(i++,new Ext.menu.Item({ text: '修改权限类别' ,handler: function() {
				treeMenu.hide();
				oper.privClassMod();
			}}));
			treeMenu.insert(i++,new Ext.menu.Item({ text: '删除权限类别' ,handler: function() {
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.privClassDel);
				treeMenu.hide();
			}}));
			treeMenu.add(new Ext.menu.Separator());
			i++ ;
			treeMenu.insert(i++,new Ext.menu.Item({ text: '增加下属权限类别' ,handler: function() {
				treeMenu.hide();
				oper.subPrivClassAdd();
			}}));
		}
	    treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	//权限列表右键菜单组装
	this.rightClickFn = function(privGrid,rowIndex,e){
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		var i = 0 ;
		if(session1.hasPriv("oaas_priv_mng")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '新增权限' ,handler: function() {
				rightClick.hide();
				oper.privAdd();
			}}));
			rightClick.insert(i++,new Ext.menu.Item({ text: '修改权限' ,handler: function() {
				rightClick.hide();
				oper.privMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除权限' ,handler: function() {
				rightClick.hide();
				oper.privDelVlt();
			}}));
		}
		
		privGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	//权限列表右键菜单组装
    this.nullRightClickFn = function(e){
    	var nullRightClick = Ext.getCmp('nullRightClick');
		var selNode = Ext.getCmp('privClassTree').getSelectionModel().selNode ;
    	nullRightClick.removeAll();
    	if(session1.hasPriv("oaas_priv_mng") && selNode){
			nullRightClick.add(new Ext.menu.Item({ text: '新增权限' ,handler: function() {
				nullRightClick.hide();
				oper.privAdd();
			}}));
    		nullRightClick.showAt(e.getXY());
		}
    }
	
	//增加权限类别
	this.privClassAdd = function(){
		privClassOper.showPrivClassInfo('add');
	}
	
	//修改权限类别
	this.privClassMod = function(){
		privClassOper.showPrivClassInfo('mod');
	}
	
	//增加下属权限类别
	this.subPrivClassAdd = function(){
		privClassOper.showPrivClassInfo('addSub');
	}
	
	//删除权限类别
	this.privClassDel = function(btn){
		if(btn == 'no'){
			return ;
		}
		var selNode = Ext.getCmp('privClassTree').getSelectionModel().selNode ;
		var hasPriv = callRemoteFunction("com.zterc.uos.oaas.service.privclassmanager.PrivilegeClassManagerWeb", "hasPrivs", selNode.attributes.pathCode);
		if(hasPriv == true){
			Ext.MessageBox.show({
	            title: '提示',
	            msg: '请先删除本权限类别上的权限',
	            buttons: Ext.MessageBox.OK,
	            width:200,
	            icon: Ext.MessageBox.ERROR
	       	});
			return;
		}else{
			callRemoteFunction("com.zterc.uos.oaas.service.privclassmanager.PrivilegeClassManagerWeb", "delete", selNode.id);
			Ext.example.msg('','删除权限类别成功！');
			Ext.getCmp('privClassTree').root.reload();
		}
	}
	
	//增加权限
	this.privAdd = function(){
    	privOper.showPrivInfo('add');
    }
    
    //修改权限
    this.privMod = function(){
    	privOper.showPrivInfo('mod');
    }
    
    //删除权限
    this.privDel = function(btn){
    	if(btn == 'no'){
			return ;
		}
		//获取选择权限
    	var privCode = Ext.getCmp('privGrid').getSelectionModel().getSelections()[0].data.privCode;
    	callRemoteFunction("com.zterc.uos.oaas.service.privmanager.PrivilegeManagerWeb", "delete", privCode);
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
            Ext.example.msg('','删除权限成功！');
        }, 1000);
		Ext.getCmp('privGrid').store.removeAll();
		Ext.getCmp('privGrid').store.load();
    }
    
    //删除权限验证
    this.privDelVlt = function(){
    	//获取选择权限
    	var selPriv = Ext.getCmp('privGrid').getSelectionModel().getSelections()[0];
    	//权限编号
		var privCode = selPriv.data.privCode;
		//查询职位模板
		var _posts = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "findPostsByPrivXml", privCode);
		//查询职位
		var _jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "findJobsByPrivXml", privCode);
		//查询人员
		var _staffs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "findStaffsByPrivXml",privCode);
		if(_posts == "<items></items>" && _jobs == "<items></items>" && _staffs == "<items></items>")
		{
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.privDel);
		}else{
			Ext.MessageBox.confirm('提示', '该权限已经被使用，是否要将其从职位模板、职位和人员上解除并删除它？', privOper.privDel);
		}
    }
}
</script>