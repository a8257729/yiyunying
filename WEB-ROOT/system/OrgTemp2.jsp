<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>组织模版管理</title>

		
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="../ext/examples/shared/examples.css" />
		<script language="javascript" src="../public/script/helper.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>

		<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../ext/ext-all.js"></script>
		
		<script type="text/javascript" src="../ext/examples/shared/examples.js"></script>
		
		<script type="text/javascript"
			src="../ext/src/locale/ext-lang-zh_CN.js"></script>
    	<%@ include file="../public/style.jsp"%>

		
		<style type="text/css">
	        .x-window-dlg .ext-mb-download {
	            background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
	            height:46px;
	        }
	    </style>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="orgMngDiv"></div>
	</body>
</html>

<script language="JScript">
var session1 = GetSession();
var staffId = session1.staff.staffId ;
var orgId ;
var orgName;
var loginOrgId = session1.org.orgId;

Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

	var orgPathCode ;
	var staffName ;
	var officeTel ;
	var userName  ;
	var mobileTel ;
	var qryType = 'qrySelf';
	var password ;
	
	var tree = new Ext.tree.TreePanel({
		region: 'west',
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/OrgTmpTreeAction'
        }),
        containerScroll: true,
        border: false,
        animCollapse: false, 
        width:Ext.getBody().getSize().width*0.2,
	    height:Ext.getBody().getSize().height,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        root: new Ext.tree.AsyncTreeNode({
            id:'0'
        })
    });
    
    new Ext.tree.TreeSorter(tree, {folderSort:true});
	
	
    tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
	    if(node.isLeaf() == false){
			if(node.expanded == false){
				node.expand();//展开
			}else{
				node.collapse();//收起
			}
	    }
	    orgId = node.id ;
	    orgName = node.text ;
	    orgPathCode = node.attributes.orgPathCode ;
	    store.on('beforeload',
			function(store){ 
				if(store.lastOptions != null){
					store.baseParams = {orgTmpId:orgId};
				}
			}
		)
	    store.removeAll() ;
		store.load();
    });
	
	tree.on("contextmenu",orgContextMenu,tree);
	
	var treeMenu = new Ext.menu.Menu({
        id : 'treeMenu'
	});
	
	//组织操作邮件菜单项
	var addOrgMenuItem = new Ext.menu.Item({ text: '增加组织模板' ,handler: function() {
		treeMenu.hide();
		orgAdd();
	}});
	var modOrgMenuItem = new Ext.menu.Item({ text: '修改组织模板' ,handler: function() {
		treeMenu.hide();
		orgMod();
	}});
	var delOrgMenuItem = new Ext.menu.Item({ text: '删除组织模板' ,handler: function() {
		orgDelVlt();
		treeMenu.hide();
	}});
	
	var addSubOrgMenuItem = new Ext.menu.Item({ text: '增加下属组织模板' ,handler: function() {
		treeMenu.hide();
		subOrgAdd();
	}});
	
	function orgContextMenu(node,e){
		node.select();
		var i = 0 ;
		
		//根据权限添加菜单项
		if(session1.hasPriv("oaas_org_add")){
			treeMenu.insert(i++,addOrgMenuItem);
		}
		if(session1.hasPriv("oaas_org_update")){
			treeMenu.insert(i++,modOrgMenuItem);
		}
		if(session1.hasPriv("oaas_org_delete")){
			treeMenu.insert(i++,delOrgMenuItem);
		}
		if(session1.hasPriv("oaas_org_add")){
			treeMenu.add(lineItem);
			i++ ;
			treeMenu.insert(i++,addSubOrgMenuItem);
		}
		if(node.expanded == false){
			node.expand();//展开
		}
	    treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	var rtnUrlDto = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager","findParameter","orgEditUrl");
	
	//增加同级别组织模板
	function orgAdd() {
		showOrgTmpInfo('add');
 	}
 	
 	//修改组织模板
 	function orgMod() {
 		showOrgTmpInfo('mod');
 	}
 	
 	//删除组织模板
 	function orgDel(btn) {
 		var selNode = tree.getSelectionModel().selNode ;
 		if(btn == 'yes'){
 			callRemoteFunction("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManagerWeb", "delete", selNode.attributes.orgTmpId);
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
	            Ext.example.msg('','删除成功');
	        }, 1000);
			tree.root.reload();
 		}
 	}
 	
 	function orgDelVlt(){
 		var selNode = tree.getSelectionModel().selNode ;
 		
		//判断是否有子节点，如果有，提示先删除子节点
		if(selNode.childNodes.length >0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除子节点上的组织模板',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}				
		var _orgs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findOrgByOrgTempId",selNode.attributes.orgTmpId);
		var _postTemps = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.postmanager.PositionManagerWeb", "findByOrgTmpXml", selNode.attributes.orgTmpId)
					
		if(_orgs == "<items></items>" && _postTemps=="<items></items>"){
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', orgDel);
			 
		}else{
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除该组织模板下已经配置的组织或职位模板！',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;				
		}
 	}
 	
 	//增加子组织模板
 	function subOrgAdd() {
		showOrgTmpInfo('addSub');	
 	}
    
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'postId','postName','postLevelId','postLevelName','comments'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/OrgTmpJobTmpGridAction'
        }),
        baseParams:{orgTmpId:-1},
        listeners:{
        	load: function(store){
				jobGrid.getSelectionModel().selectRow(0);
			}
        }
    });
	
	//创建列
	var column = new Ext.grid.ColumnModel([
	    {header:'职位模板编号',dataIndex:'postId',hidden:true },
	    {header:'职位模板级别',dataIndex:'postLevelId',hidden:true },
	    {header:'职位模板名称',dataIndex:'postName',width:300},
	    {header:'职位级别',dataIndex:'postLevelName',width:200},
	    {header:'备注',dataIndex:'comments',width:330}
	]);   
	column.defaultSortable = true;//默认可排序
	
	var jobGrid = new Ext.grid.GridPanel({
        region: 'center',
        width:Ext.getBody().getSize().width*0.8,
	    height:Ext.getBody().getSize().height*0.5,
	    title:'职位模板列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
	            	var activeTab = tabs2.activeTab.id ;
	            	switch (activeTab){
						case "roleGrid":
						{
							roleStore.on('beforeload',
								function(store){ 
									if(roleStore.lastOptions != null){
										roleStore.baseParams = {postId:sm.getSelections()[0].data.postId};
									}
								}
							)
						    roleStore.removeAll() ;
							roleStore.load();
							break ;
						}
						case "privClassTree":
						{
							privClassTree.getLoader().baseParams.postId = sm.getSelections()[0].data.postId ;
	            			privClassTree.root.reload();
							break ;
						}
						case "allPrivClassTree":
						{	
							allPrivClassTree.getLoader().baseParams.postId = sm.getSelections()[0].data.postId ;
	            			allPrivClassTree.root.reload();
							break ;
						}
	            	}
	            }
	        }
	    })
    });
	store.load({params:{orgId:orgId }});
    
    var roleStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'roleId','roleName','areaName','comments'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/OrgTmpRoleGridAction'
        }),
        baseParams:{postId:0}
    });
    
    var roleColumn = new Ext.grid.ColumnModel([
	    {header:'角色编号',dataIndex:'roleId',hidden:true },
	    {header:'角色名称',dataIndex:'roleName',width:430},
	    {header:'区域',dataIndex:'areaName',width:200},
	    {header:'备注',dataIndex:'comments',width:200}
	]);
	roleColumn.defaultSortable = true;//默认可排序
    
    var roleGrid = new Ext.grid.GridPanel({
    	id : 'roleGrid',
        title: '角色',
        height: Ext.getBody().getSize().height*0.5,
        width: Ext.getBody().getSize().width*0.85,
        store: roleStore,
        trackMouseOver: false,
        autoScroll: true,
        loadMask: true,
		cm:roleColumn,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true
	    })
    });
    
    roleGrid.addListener('rowcontextmenu', roleRightClickFn);
	
	var roleRightClick = new Ext.menu.Menu({
	    id:'roleRightClick'
	});
    
    function roleRightClickFn(roleGrid,rowIndex,e){
    	roleRightClick.removeAll();
		var i = 0 ;
		if(session1.hasPriv("oaas_tmp_mng")){
			roleRightClick.insert(i++,new Ext.menu.Item({ text: '解除职位模板和相应职位角色' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要解除职位模板和相应职位角色吗？', rmvPostJobRole);
			}}));
			
			roleRightClick.insert(i++,new Ext.menu.Item({ text: '仅解除职位模板角色' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要解除职位模板角色吗？', rmvPostRole);
			}}));
		}
		
		roleGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    roleRightClick.showAt(e.getXY());
	}
	
	function rmvPostJobRole(btn){
		if(btn == 'no'){
			return ;
		}
		
		var postSel = jobGrid.getSelectionModel().getSelections()[0].data ;
		var roleSel = roleGrid.getSelectionModel().getSelections()[0].data ;
		
		var removedRoles = new Array();
		removedRoles[0] = roleSel.roleId ;
		callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "removeRolesToJob", postSel.postId, removedRoles,true);
		
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
            Ext.example.msg('','解除成功！');
        }, 1000);
        roleStore.reload();
	}
	
	function rmvPostRole(btn){
		if(btn == 'no'){
			return ;
		}
		
		var postSel = jobGrid.getSelectionModel().getSelections()[0].data ;
		var roleSel = roleGrid.getSelectionModel().getSelections()[0].data ;
		
		var removedRoles = new Array();
		removedRoles[0] = roleSel.roleId ;
		callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "removeRolesToJob", postSel.postId, removedRoles,false);
		
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
            Ext.example.msg('','解除成功！');
        }, 1000);
        roleStore.reload();
	}
    
	var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});
	
	var nullRightClick = new Ext.menu.Menu({
	    id:'nullRightClick'
	});
		
	var lineItem = new Ext.menu.Separator();
	
	var addJobMenuItem = new Ext.menu.Item({ text: '新增职位模板' ,handler: function() {
		rightClick.hide();
		jobAdd();
	}});
	
	var modJobMenuItem = new Ext.menu.Item({ text: '修改职位模板' ,handler: function() {
		rightClick.hide();
		jobMod();
	}});
	
	var delJobMenuItem = new Ext.menu.Item({ text: '删除职位模板' ,handler: function() {
		rightClick.hide();
		jobDelVlt();
	}});
	
	var confJobRoleMenuItem = new Ext.menu.Item({ text: '配置角色' ,handler: function() {
		rightClick.hide();
		confJobRole();
	}});
	
	var confJobPrivMenuItem = new Ext.menu.Item({ text: '配置权限' ,handler: function() {
		rightClick.hide();
		confJobPriv();
	}});
	
	var confJobPrivMenuItem = new Ext.menu.Item({ text: '配置移动权限' ,handler: function() {
		rightClick.hide();
		confMobileJobPriv();
	}});

	jobGrid.addListener('rowcontextmenu', rightClickFn);
	jobGrid.addListener('contextmenu', nullRightClickFn);
	
	function rightClickFn(jobGrid,rowIndex,e){
		var i = 0 ;
		if(session1.hasPriv("oaas_tmp_mng")){
			rightClick.insert(i++,addJobMenuItem);
			rightClick.insert(i++,modJobMenuItem);
			rightClick.insert(i++,delJobMenuItem);
		
			rightClick.insert(i++,lineItem);
			rightClick.insert(i++,confJobRoleMenuItem);
			rightClick.insert(i++,confJobPrivMenuItem);
		}
		
		jobGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
    function nullRightClickFn(e){
    	var selNode = tree.getSelectionModel().selNode ;
		if(!selNode){
			return ;
		}
    	if(session1.hasPriv("oaas_staff_add")){
			nullRightClick.add(addJobMenuItem);
		}
    	nullRightClick.showAt(e.getXY());
    }
    
    function jobAdd(){
		newJobTmpInfoPage('add');
    }
    
    function jobMod(){
    	newJobTmpInfoPage('mod');
    }
    
    function jobDel(btn){
    	if(btn == 'no'){
    		return ;
    	}
		//要删除的职位模板ID
		var postId = jobGrid.getSelectionModel().getSelections()[0].data.postId ;
		
		callRemoteFunction("com.zterc.uos.oaas.service.postmanager.PositionManagerWeb", "delete",postId);
		
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
            Ext.example.msg('','职位模板删除成功！');
        }, 1000);
        store.reload();
    }
    
    function jobDelVlt(){
    	//删除
		var postItem = jobGrid.getSelectionModel().getSelections()[0] ;
		var postId = postItem.data.postId;
		var _jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findByPostXml",postId);
		if(_jobs == "<items></items>"){
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', jobDel);
		}else{
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除该职位模板下已经配置的职位！',
	           buttons: Ext.MessageBox.OK,
	           width:250,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;	
		}
    }
	
	function confJobRole(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		
		var _obj = new Object();
		_obj.postId = jobItem.data.postId;
		window.showModalDialog("RoleSelect2.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
		roleStore.removeAll() ;
		roleStore.load();
	}
	
	function confJobPriv(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		//配置职位模板权限
		var _obj = new Object();
		_obj.postId = jobItem.data.postId;
		window.showModalDialog("PrivSelectForPost.jsp", _obj, "dialogWidth:750px;dialogHeight:560px;status:no");
		privClassTree.getLoader().load(privClassTree.root);
		allPrivClassTree.getLoader().load(allPrivClassTree.root);
	}
	
	function confMobileJobPriv(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		//配置移动职位模板权限
		var _obj = new Object();
		_obj.postId = jobItem.data.postId;
		window.showModalDialog("MobilePrivSelectForPost.jsp", _obj, "dialogWidth:750px;dialogHeight:560px;status:no");
		
	}
	
	//特有权限树
	var privClassTree = new Ext.tree.TreePanel({
        title: '权限',
		id: 'privClassTree',
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/RolePrivTreeAction',
        	baseParams: {postId:-1}
        }),
        containerScroll: true,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        root: {
            nodeType: 'async'
        }
    });
    
    //所有权限树
    var allPrivClassTree = new Ext.tree.TreePanel({
        title: '所有权限',
    	id: 'allPrivClassTree',
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/RoleAllPrivTreeGridAction',
        	baseParams: {postId:-1}
        }),
        containerScroll: true,
        animCollapse: false, 
        rootVisible: false,
        root: {
            nodeType: 'async'
        }
    });
	
    // second tabs built from JS
	var tabs2 = new Ext.TabPanel({
	    region: 'south',
	    activeTab: 0,
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[roleGrid,privClassTree,allPrivClassTree],
	    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs2.activeTab.id ;
	            	var postId ;
	            	if(jobGrid.getSelectionModel().getSelections().length>0){
	            		postId = jobGrid.getSelectionModel().getSelections()[0].data.postId ;
	            	}
	            	switch (activeTab){
						case "roleGrid":
						{
							roleStore.on('beforeload',
								function(store){ 
									if(roleStore.lastOptions != null){
										roleStore.baseParams = {postId:postId};
									}
								}
							)
						    roleStore.removeAll() ;
							roleStore.load();
							break ;
						}
						case "privClassTree":
						{
	            			privClassTree.getLoader().baseParams.postId = postId ;
	            			privClassTree.getLoader().load(privClassTree.root);
							break ;
						}
						case "allPrivClassTree":
						{	
							allPrivClassTree.getLoader().baseParams.postId = postId ;
	            			allPrivClassTree.getLoader().load(allPrivClassTree.root);
							break ;
						}
					}	
	    		}
	    }
	});
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[jobGrid,tabs2]
	}); 
	
	var viewport = new Ext.Viewport({
		el:'orgMngDiv',
		layout: 'border',
		items:[tree,earthviewport]
	});
	
	var orgTmpInfoWin ;
	function showOrgTmpInfo(operator){
		var orgTmpInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'orgTmpName',
	                id: 'orgTmpName',
	                allowBlank:false, 
	                blankText:"名称不能为空!",
	                anchor:'90%'
	            },{
	                xtype:'textarea',
				    fieldLabel: '备注',
				    name: 'comments',
				    id: 'comments',
				    height : 100,
				    anchor:'90%'
	            }
	        ],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
	            
	            	//表单验证
					if(!orgTmpInfoForm.getForm().isValid()){
						return;
					}
					var selNode = tree.getSelectionModel().selNode ;
					var brotherNodes = tree.getSelectionModel().selNode.parentNode.childNodes ;
					
					var _obj = new Object();
					
					switch (operator){
						case 'add':	{
							msg = '新增组织模板成功' ;
							if(!selNode || selNode.attributes.parentId == 0){
								Ext.MessageBox.show({
						            title: '提示',
						            msg: '最高层组织模板不能建立同级模板',
						            buttons: Ext.MessageBox.OK,
						            width:200,
						            icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
							
							for(var i = 0 ;i < brotherNodes.length; i++ ){
								if(brotherNodes[i].attributes.text == Ext.getCmp("orgTmpName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '指定的组织模板与现有同级组织模板重名，请指定另一组织模板名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							_obj.orgTmpName = Ext.getCmp("orgTmpName").getValue();
							_obj.orgTmpId = 0;
							_obj.parentId = selNode.attributes.parentId ;
							_obj.comments =  Ext.getCmp("comments").getValue();
							
							var _result = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManagerWeb", "create", _obj);
							
							break;
						}
						case 'mod':{
							msg = '修改组织模板成功' ;
							for(var i = 0 ;i < brotherNodes.length; i++ ){
								if(brotherNodes[i].attributes.text == Ext.getCmp("orgTmpName").getValue() && Ext.getCmp("orgTmpName").getValue() != selNode.text){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '指定的组织模板与现有同级组织模板重名，请指定另一组织模板名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							_obj.orgTmpName = Ext.getCmp("orgTmpName").getValue();
							_obj.orgTmpId = selNode.id;
							_obj.parentId = selNode.attributes.parentId ;
							_obj.comments =  Ext.getCmp("comments").getValue();
							callRemoteFunction("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManagerWeb", "update", _obj);
							break ;
						}
						case 'addSub':{
							msg = '新增下属组织模板成功' ;
							
							for(var i = 0 ;i < selNode.childNodes.length; i++ ){
								if(selNode.childNodes[i].attributes.orgTmpName == Ext.getCmp("orgTmpName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '指定的组织模板与现有同级组织模板重名，请指定另一组织模板名称',
							            buttons: Ext.MessageBox.OK,
							            width:200,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							_obj.orgTmpName = Ext.getCmp("orgTmpName").getValue();
							_obj.orgTmpId = 0;
							_obj.parentId = selNode.attributes.orgTmpId ;
							_obj.comments =  Ext.getCmp("comments").getValue();
							
							var _result = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManagerWeb", "create", _obj);
							
							break;
						}
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
			            Ext.example.msg('',msg);
			        }, 1000);
					tree.root.reload();
					
					orgTmpInfoWin.close();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	orgTmpInfoWin.close();
			    }}
	        }]
		});
		
		orgTmpInfoWin = new Ext.Window({
	        title: '组织模板',
		    closable:true,
		    width:440,
		    height:240,
		    plain:true,
		    layout: 'border',
		    items: [orgTmpInfoForm]
		});
		
		orgTmpInfoWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("comments").setValue(tree.getSelectionModel().selNode.attributes.comments);
			Ext.getCmp("orgTmpName").setValue(tree.getSelectionModel().selNode.attributes.orgTmpName);
		}
    }
	
	var win ;
	function newJobTmpInfoPage(operator){
		
		var postCombosto = new Ext.data.JsonStore({
	        remoteSort: true,
	        fields: ['POST_LEVEL_ID', 'POST_LEVEL_NAME'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/JobTmpLevelSelAction' 
	        })
	    });
	    postCombosto.load();
		
		var jobInfoForm = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'postName',
	                id: 'postName',
	                allowBlank:false, 
	                blankText:"名称不能为空!",
	                anchor:'90%'
	            },{
                    xtype:'combo',
                    fieldLabel: '级别',
                    name: 'postLevle',
                    id: 'postLevle',
                    xtype: 'combo',
                    valueField: 'POST_LEVEL_ID',
                    displayField: 'POST_LEVEL_NAME',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"级别不能为空!", 
                    store: postCombosto
                },{
	                xtype:'textarea',
				    fieldLabel: '备注',
				    name: 'comments',
				    id: 'comments',
				    height : 100,
				    anchor:'90%'
	            }
	        ],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	//表单验证
					if(!jobInfoForm.getForm().isValid()){
						return;
					}
					
					var msg ;
					var _obj = new Object();
					
					var selNode = tree.getSelectionModel().selNode ;
					var selJobTmp = jobGrid.getSelectionModel().getSelections()[0] ;
					
					var jobTmpList = store.data.items ;
					
					switch (operator){
						case 'add':	{
							msg = '新增职位模板成功！' ;
							for(var i = 0 ;i < jobTmpList.length ;i++ ){
								if(jobTmpList[i].data.postName == Ext.getCmp("postName").getValue()){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '指定的职位模板与现有职位模板重名，请指定另一职位模板名称',
							            buttons: Ext.MessageBox.OK,
							            width:250,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							_obj.postName = Ext.getCmp("postName").getValue() ;
							_obj.postId = 0;
							_obj.orgTmpId = selNode.attributes.orgTmpId ;
							_obj.comments = Ext.getCmp("comments").getValue() ;
							_obj.postLevelId = Ext.getCmp("postLevle").getValue() ;
							
							callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.postmanager.PositionManagerWeb", "create", _obj);
							break ;
						}
						case 'mod':	{
							msg = '修改职位模板成功！' ;
							for(var i = 0 ;i < jobTmpList.length ;i++ ){
								if(jobTmpList[i].data.postName == Ext.getCmp("postName").getValue() && Ext.getCmp("postName").getValue() != selJobTmp.data.postName){
									Ext.MessageBox.show({
							            title: '提示',
							            msg: '指定的职位模板与现有职位模板重名，请指定另一职位模板名称',
							            buttons: Ext.MessageBox.OK,
							            width:250,
							            icon: Ext.MessageBox.ERROR
							       	});
									return;
								}
							}
							
							_obj.postName = Ext.getCmp("postName").getValue() ;
							_obj.postId = selJobTmp.data.postId;
							_obj.orgTmpId = selNode.attributes.orgTmpId ;
							_obj.comments = Ext.getCmp("comments").getValue() ;
							_obj.postLevelId = Ext.getCmp("postLevle").getValue() ;
							callRemoteFunction("com.zterc.uos.oaas.service.postmanager.PositionManagerWeb", "update", _obj);
							break ;
						}
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
			            Ext.example.msg('',msg);
			        }, 1000);
					
					win.close();
					store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
	        }]
		});
		
		win = new Ext.Window({
	        title: '职位模板',
		    closable:true,
		    width:450,
		    height:275,
		    plain:true,
		    layout: 'border',
		    items: [jobInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			var selJobTmp = jobGrid.getSelectionModel().getSelections()[0] ;
			Ext.getCmp("postName").setValue(selJobTmp.data.postName);
			Ext.getCmp("comments").setValue(selJobTmp.data.comments);
			
			postCombosto.on('load',function(ds,records,o){
	    		Ext.getCmp("postLevle").setValue(selJobTmp.data.postLevelId);
			});
		}
	}
});

</script>