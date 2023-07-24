<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>组织管理</title>
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
	
	var tree = new ZTESOFT.OrgTree({
		region: 'west',
        split: true,
       	allOrgs:false 
	});
	
	
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
					store.baseParams = {orgId:orgId};
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
	var addOrgMenuItem = new Ext.menu.Item({ text: '增加组织' ,handler: function() {
		treeMenu.hide();
		orgAdd();
	}});
	var modOrgMenuItem = new Ext.menu.Item({ text: '修改组织' ,handler: function() {
		treeMenu.hide();
		orgMod();
	}});
	var delOrgMenuItem = new Ext.menu.Item({ text: '删除组织' ,handler: function() {
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', orgDel);
		treeMenu.hide();
	}});
	
	var addSubOrgMenuItem = new Ext.menu.Item({ text: '增加下属组织' ,handler: function() {
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
			treeMenu.insert(i++,lineItem);
			treeMenu.insert(i++,addSubOrgMenuItem);
		}
		
	    treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	var rtnUrlDto = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager","findParameter","orgEditUrl");
	
	//增加同级别组织
	function orgAdd() {
		var objOrg;
		var orgItem = tree.getSelectionModel().selNode ;
		
		if(orgItem != null ){
			objOrg= callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", orgItem.attributes.orgId);
			if(!orgItem.parentNode ){
				Ext.MessageBox.show({
		           	title: '提示',
		           	msg: '最高层组织不能建立同级组织',
		           	buttons: Ext.MessageBox.OK,
		           	width:220,
		           	icon: Ext.MessageBox.ERROR
		       	});
				return;
			}else{
				if(orgItem.parentNode.id == -1){
					Ext.MessageBox.show({
			           	title: '提示',
			           	msg: '最高层组织不能建立同级组织',
			           	buttons: Ext.MessageBox.OK,
			           	width:220,
			           	icon: Ext.MessageBox.ERROR
			       	});
					return;
				}
			}
		}
		var parentOrgItem;
		var parentObj;
		if(orgItem == null)	{
			parentOrgItem = null;
			parentObj =null;
		}else{
			parentOrgItem = orgItem.parentNode;
			parentObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", orgItem.attributes.parentId);
		}
	
		//这里需要判断能否建立组织,主要是判断时候具有相应的模板
		if(parentOrgItem == null){
			var tempOrgTempId =0;
			if(objOrg!=null &&  objOrg.orgTmpId!=null)
				tempOrgTempId = objOrg.orgTmpId;
	
			var canCreate = callRemoteFunction("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManager", "canCreateOrg",tempOrgTempId);
			if(canCreate == false){
				Ext.MessageBox.show({
		           	title: '提示',
		           	msg: '没有最高层组织模板，无法建立最高层组织',
		           	buttons: Ext.MessageBox.OK,
		           	width:220,
		           	icon: Ext.MessageBox.ERROR
		       	});
				return;
			}
		}
		var _obj = new Object();
		//给parentId和组织模板赋值
	
		if(parentOrgItem)	{
			_obj.orgTmpId = parentObj.orgTmpId;
			_obj.parentId = parentObj.orgId;
		}else{
			_obj.orgTmpId = 0;
			_obj.parentId = 0;
		}
	
		//给区域赋值
		if(loginOrgId==0){
			if(parentOrgItem){
				_obj.areaId = parentObj.areaId; //显示上级组织和本级组织
	
			}else{
				_obj.areaId = 0;	//不存在上级组织
			}
		}else{
			if(parentOrgItem){
				_obj.areaId = parentObj.areaId; //显示上级组织和本级组织
	
			}else{
				_obj.areaId = -1;
			}
		}
		_obj.operation = "add";
		_obj.index = 0;
		_obj.orgTree = tree ;
		
		/********************************************/
		if (rtnUrlDto && rtnUrlDto.value!=null && rtnUrlDto.value!=""){
			var newObj = window.showModalDialog(rtnUrlDto.value, _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
		}else{
			var newObj = window.showModalDialog("OrgInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
		}
		if(newObj){
			tree.root.reload();
		}
 	}
 	
 	//修改组织
 	function orgMod() {
 		var orgItem = tree.getSelectionModel().selNode ;
		var parentOrgItem;
		var parentObj;
		
		parentOrgItem = orgItem.parentNode;
		
		if(parentOrgItem == null){
			parentObj = null;
		}else{
			parentObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", parentOrgItem.id);
		}

		var _obj = new Object();
		_obj.operation = "mod";
		_obj.orgId = orgItem.id ;
		
		if(parentOrgItem){
			_obj.orgTmpId = parentObj.orgTmpId;
			_obj.parentId = parentObj.orgId;
		}else{
			_obj.orgTmpId = 0;
			_obj.parentId = 0;
		}

		//给区域赋值
		if(loginOrgId==0){
			if(parentOrgItem)	{
				_obj.areaId = parentObj.areaId; //显示上级组织和本级组织

			}else{
				_obj.areaId = 0;	//不存在上级组织
			}
		}else{
			if(parentOrgItem){
				_obj.areaId = parentObj.areaId; //显示上级组织和本级组织

			}else{
				_obj.areaId = -1;
			}
		}
		//added by caozz 2006-03-13
		/********************************************/
		_obj.index = 1;
		_obj.orgTree = tree ;
		/********************************************/
		if (rtnUrlDto && rtnUrlDto.value!=null && rtnUrlDto.value!=""){
			var newObj = window.showModalDialog(rtnUrlDto.value, _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
		}else{
			var newObj = window.showModalDialog("OrgInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
			if(newObj){
				tree.root.reload();
			}
		}
 	}
 	
 	//删除组织
 	function orgDel(btn) {
		if(btn == 'no'){
			return ;
		} 
		var orgItem = tree.getSelectionModel().selNode ;
		var orgId = orgItem.id;
		var tempOrgs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findDriectSubOrgs",orgId);
		if(tempOrgs!=null && tempOrgs.length>0)	{
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除子组织',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
		var hostCount =callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.hostmanager.HostManager", "findHostCount", orgId);
		if(hostCount>0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除该组织对应的主机',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
		if(store.getTotalCount() != 0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '该组织下有职位，请先删除职位',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}else{
			var org ;
            var projectObj=callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager","findParameter","OaasCanOper4App");
			if(projectObj != null){
				var method = new Array();
		      	method = projectObj.value.split(':');
				if(method.length==2){
					if(method[0]!=null && method[0]!="" && method[1]!=null && method[1]!=""){
				    	var resObj=callRemoteFunctionNoTrans(method[0],method[1],"ORG",orgId);
			        	if(resObj!="0"){
			            	Ext.MessageBox.show({
								title: '提示',
								msg: resObj,
								buttons: Ext.MessageBox.OK,
								width:220,
								icon: Ext.MessageBox.ERROR
					       	});
			                return;
				 		}
				    }
				}
		    }
			if (rtnUrlDto && rtnUrlDto.value!=null && rtnUrlDto.value!=""){
				var obj_1 = callRemoteFunctionNoTrans("com.ztesoft.eoms.insteadmanage.bl.InsteadManage", "findInsteadOrgInfoByOrgId",orgId);
				if(null != obj_1){
					callRemoteFunction("com.ztesoft.eoms.insteadmanage.bl.InsteadManage", "deleteInsteadOrgInfo",orgId, obj_1.agreementId);
				}
	 		}
	 		
	 		var _resultObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.dao.OrganizationDAOImpl", "findByKey", orgId);
        	var oldOrgPathName = _resultObj.orgPathName;
        	
			callRemoteFunction("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "deleteWithLog",orgId,session1.staff.staffId,session1.staff.staffName);
			Ext.MessageBox.alert('提示','删除成功');
			
			//add by fengyang 2011-1-5
			//call out system
			var orgObj = new Object();
			orgObj.orgId = orgId;
			
        						
			//var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction","excuteOrg", orgObj,oldOrgPathName,"DELETE_DEPARTMENT");
										
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
	            Ext.example.msg('','删除组织成功！');
	        }, 1000);
			
			tree.root.reload();
		}
 	}
 	
 	//增加子组织
 	function subOrgAdd() {
		var orgItem = tree.getSelectionModel().selNode ;
		var orgId = orgItem.id;
		if(orgItem == null){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '没有选择组织，无法建立子组织',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
		var objOrg = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", orgId);
		//这里需要判断能否建立组织,主要是判断时候具有相应的模板
		var canCreate = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManager", "canCreateOrg", objOrg.orgTmpId);
		if(canCreate == false){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '在所选组织上无法建立下属组织，请检查组织模板',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
		var _obj = new Object();
		_obj.operation = "add";
		_obj.orgTmpId = objOrg.orgTmpId;
		_obj.parentId = objOrg.orgId;
		_obj.areaId = objOrg.areaId;
		_obj.index = 3;
		_obj.orgTree = tree ;
		
		/********************************************/
		if (rtnUrlDto && rtnUrlDto.value!=null && rtnUrlDto.value!=""){
			var newObj = window.showModalDialog(rtnUrlDto.value, _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
		}else{
			var newObj = window.showModalDialog("OrgInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:350px;status:no");
		}
		if(newObj){
			tree.root.reload();	
		}
 	}
    
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'jobId', 'jobName', 'postId', 'postName','comments','orgPathCode','areaId', 'areaName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/OrgJobGridAction'
        }),
        baseParams:{orgId:0},
        listeners:{
        	load: function(store){
				jobGrid.getSelectionModel().selectRow(0);
			}
        }
    });
	
	//创建列
	var column = new Ext.grid.ColumnModel([
	    {header:'职位编号',dataIndex:'jobId',hidden:true },
	    {header:'职位名称',dataIndex:'jobName',width:200 },
	    {header:'组织模板编号',dataIndex:'postId',hidden:true},
	    {header:'职位模板',dataIndex:'postName',width:300},
	    {header:'备注',dataIndex:'comments',width:330},
	    {header:'组织编码',dataIndex:'orgPathCode',hidden:true},
	    {header:'区域编号',dataIndex:'areaId',hidden:true},
	    {header:'地区',dataIndex:'areaName',hidden:true}
	]);   
	column.defaultSortable = true;//默认可排序
	
	var jobGrid = new Ext.grid.GridPanel({
        region: 'center',
        split: true,
        width:Ext.getBody().getSize().width*0.8,
	    height:Ext.getBody().getSize().height*0.5,
	    title:'职位列表',
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
										roleStore.baseParams = {jobId:sm.getSelections()[0].data.jobId};
									}
								}
							)
						    roleStore.removeAll() ;
							roleStore.load();
							break ;
						}
						case "staffGrid":
						{
							staffStore.on('beforeload',
								function(store){ 
									if(staffStore.lastOptions != null){
										staffStore.baseParams = {jobId:sm.getSelections()[0].data.jobId};
									}
								}
							)
						    staffStore.removeAll() ;
							staffStore.load({params:{start:0, limit:8}});
							break ;
						}
						case "privClassTree":
						{
							privClassTree.getLoader().baseParams._jobId = sm.getSelections()[0].data.jobId ;
	            			privClassTree.getLoader().load(privClassTree.root);
							break ;
						}
						case "allPrivClassTree":
						{	
							allPrivClassTree.getLoader().baseParams.jobId = sm.getSelections()[0].data.jobId ;
	            			allPrivClassTree.getLoader().load(allPrivClassTree.root);
							break ;
						}
	            	}
	            }
	        }
	    })
    });
	store.load({params:{orgId:orgId }});
	
    
    //角色信息
    function renderIscanGrant(value, p, r){
        if(value == 0){
        	return '<span style="color:red;">否</span>';
        }else if(value == 1){
        	return '<span style="color:green;">可</span>';
        }
    }
    
    var roleStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'roleId', 'areaId', 'roleName', 'canGrant','areaName'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/JobRolesGridAction'
        }),
        baseParams:{jobId:0}
    });
    
    var roleColumn = new Ext.grid.ColumnModel([
	    {header:'角色编号',dataIndex:'roleId',hidden:true },
	    {header:'区域编号',dataIndex:'areaId',hidden:true },
	    {header:'角色名称',dataIndex:'roleName',width:430},
	    {header:'可否授出',dataIndex:'canGrant',renderer: renderIscanGrant,width:200},
	    {header:'区域',dataIndex:'areaName',width:200}
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
		cm:roleColumn
    });
    
    
    //人员信息
    function renderIsBasic(value, p, r){
        if(value == 1){
        	return '<span style="color:blue;">是</span>';
        }else {
        	return '否';
        }
    }
    
    var staffStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'staffId', 'jobId', 'orgId', 'staffName','userName','jobName','isbasic','orgPathName','officeTel','mobile'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/JobStaffsGridAction'
        }),
        baseParams:{jobId:0}
    });
    
    var staffsm = new Ext.grid.CheckboxSelectionModel();
    
    var staffColumn = new Ext.grid.ColumnModel([staffsm,
    	{header:'人员编号',dataIndex:'staffId',hidden:true },
    	{header:'职位编号',dataIndex:'jobId',hidden:true },
    	{header:'组织编号',dataIndex:'orgId',hidden:true },
	    {header:'姓名',dataIndex:'staffName',width:100 },
	    {header:'用户名',dataIndex:'userName',width:100 },
	    {header:'职位',dataIndex:'jobName',width:100},
	    {header:'职位是否缺省',dataIndex:'isbasic',renderer: renderIsBasic,width:100},
	    {header:'组织',dataIndex:'orgPathName',width:210},
	    {header:'办公电话',dataIndex:'officeTel',width:100},
	    {header:'移动电话',dataIndex:'mobile',width:100}
	]);   
	staffColumn.defaultSortable = true;//默认可排序
	
	var rolePagingBar = new Ext.PagingToolbar({
		pageSize: 8,
		store: staffStore,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录",
        items:[
            '-', {
            enableToggle:true,
            text: '批量解除人员',
            cls: 'x-btn-text-icon details',
            toggleHandler: function(btn, pressed){
                var selStaff = staffGrid.getSelectionModel().getSelections();
                if(selStaff.length < 1){
		    		Ext.MessageBox.show({
			           title: '提示',
			           msg: '请先选择需要解除的人员',
			           buttons: Ext.MessageBox.OK,
			           width:200,
			           icon: Ext.MessageBox.ERROR
			       	});
					return;
		    	}
                var jobId = jobGrid.getSelectionModel().getSelections()[0].data.jobId ;
                if(selStaff.length > 0){
                	var onlyJobStaff = "";
					var flag = false;
					
                	for(var i =0 ; i<selStaff.length ;i++){
                		var selStaffId = selStaff[i].data.staffId ;
                		var selStaffName = selStaff[i].data.staffName ;
                		var selIsBasic = selStaff[i].data.isBasic ;
                		
                		if(isOnlyJob(selStaffId)){
                			onlyJobStaff = onlyJobStaff + selStaffName + ",\r\n" ;
                		}
                		if(selIsBasic == selIsBasic ){
                			flag = true;
                		}
                	}
                	
                	var msg = '确实要解除吗？';
					if(flag && onlyJobStaff==""){
						msg = '当前选中职位中有缺省职位，是否继续解除？';
					}
			
					
					if(onlyJobStaff!=""){
						msg = '该职位为以下人员唯一的职位'+": \r\n"+
						onlyJobStaff.substring(0,onlyJobStaff.length)+"\r\n"+'确实要解除吗？';
					}
					Ext.MessageBox.confirm('提示', '你确定要删除吗？', jobStaffsDel);
										
                }
            }
        }]
    });
    
    function jobStaffsDel(btn){
    	if(btn == 'no'){
			return ;
		}
    	var selStaff = staffGrid.getSelectionModel().getSelections();
    	
   		var jobId = jobGrid.getSelectionModel().getSelections()[0].data.jobId ;
    	for(var i=0;i<selStaff.length;i++){
			var selStaffId = selStaff[i].data.staffId ;
			callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb","removeJob", selStaffId, jobId);
		}
		staffStore.reload();
    }
    
    //判断是否唯一职位
	function isOnlyJob(btn){
		if(btn == 'no'){
			return ;
		}
		var jobCnt = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "findJobCount", staffId);
		if(jobCnt <= 1)	{
			return true;
		}
		return false;
	}
    
    var staffGrid = new Ext.grid.GridPanel({
    	id : 'staffGrid',
        title: '人员',
        height:Ext.getBody().getSize().height*0.4,
        width:Ext.getBody().getSize().width*0.85,
        store: staffStore ,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:staffColumn,
		sm:staffsm,
		bbar: rolePagingBar
    });
    
	var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});
	
	var nullRightClick = new Ext.menu.Menu({
	    id:'nullRightClick'
	});
		
	var lineItem = new Ext.menu.Separator();
	
	var addJobMenuItem = new Ext.menu.Item({ text: '新增职位' ,handler: function() {
		rightClick.hide();
		jobAdd();
	}});
	
	var modJobMenuItem = new Ext.menu.Item({ text: '修改职位' ,handler: function() {
		rightClick.hide();
		jobMod();
	}});
	
	var delJobMenuItem = new Ext.menu.Item({ text: '删除职位' ,handler: function() {
		rightClick.hide();
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', jobDel);
	}});
	
	var confJobRoleMenuItem = new Ext.menu.Item({ text: '配置角色' ,handler: function() {
		rightClick.hide();
		confJobRole();
	}});
	
	var confJobPrivMenuItem = new Ext.menu.Item({ text: '配置权限' ,handler: function() {
		rightClick.hide();
		confJobPriv();
	}});
   var confMobJobPrivMenuItem = new Ext.menu.Item({ text: '配置移动权限' ,handler: function() {
		rightClick.hide();
		confMobJobPriv();
	}});
	jobGrid.addListener('rowcontextmenu', rightClickFn);
	jobGrid.addListener('contextmenu', nullRightClickFn);
	
	function rightClickFn(jobGrid,rowIndex,e){
		var i = 0 ;
		if(session1.hasPriv("oaas_job_add")){
			rightClick.insert(i++,addJobMenuItem);
		}
		if(session1.hasPriv("oaas_job_update")){
			rightClick.insert(i++,modJobMenuItem);
		}
		if(session1.hasPriv("oaas_job_delete")){
			rightClick.insert(i++,delJobMenuItem);
		}
		if(session1.hasPriv("oaas_job_cfg_role")){
			rightClick.insert(i++,lineItem);
			rightClick.insert(i++,confJobRoleMenuItem);
		}
		if(session1.hasPriv("oaas_job_cfg_priv")){
			rightClick.insert(i++,confJobPrivMenuItem);
		}
		if(session1.hasPriv("oaas_job_cfg_priv")){
			rightClick.insert(i++,confMobJobPrivMenuItem);
		}
		
		jobGrid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    function nullRightClickFn(e){
    	if(session1.hasPriv("oaas_staff_add")){
			nullRightClick.add(addJobMenuItem);
		}
    	nullRightClick.showAt(e.getXY());
    }
    
    function jobAdd(){
    	var selJob = jobGrid.getSelectionModel().getSelections();
    	
    	var orgItem = tree.getSelectionModel().selNode ;
    	if (orgItem == null){
    	   Ext.MessageBox.show({
	           title: '提示',
	           msg: '请选择组织',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
    	}
		var orgId = orgItem.id;
		
    	var objOrg = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", orgId);
    	
    	//这里要判断是否能建立职位
		var hasPosts = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgtmpmanager.OrgTemplateManager", "hasPosts", objOrg.orgTmpId);
		if(hasPosts == false){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '没有职位模板，无法增加职位',
	           buttons: Ext.MessageBox.OK,
	           width:220,
	           icon: Ext.MessageBox.ERROR
	       	});
			return;
		}
		newInfoPage(objOrg.orgTmpId,'add',orgId);
    }
    
    function jobMod(){
    	var selJob = jobGrid.getSelectionModel().getSelections();
    	var jobId = jobGrid.getSelectionModel().getSelections()[0].data.jobId ;
    	
    	var orgItem = tree.getSelectionModel().selNode ;
		var orgId = orgItem.id;
		
    	var objOrg = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findByKey", orgId);
    	newInfoPage(objOrg.orgTmpId,'mod',orgId,selJob);
    }
    
    function jobDel(btn){
    	if(btn == 'no'){
    		return ;
    	}
		//要删除的职位ID
		var jobId = jobGrid.getSelectionModel().getSelections()[0].data.jobId ;
		
		callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManager", "deleteWithLog", jobId,session1.staff.staffId,session1.staff.staffName);
		
		var paramObj = new Object();
		paramObj.jobId = jobId ;    	
		paramObj.type = "postPrivDel";													
		var retMap = invokeAction("/mobsystem/InsertPostPrivAction", paramObj);
		
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
            Ext.example.msg('','删除职位成功！');
        }, 1000);
        store.reload();
    }
	
	function confJobRole(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		
		var _obj = new Object();
		_obj.type = "Job";
		_obj.jobId = jobItem.data.jobId ;
		window.showModalDialog("RoleSelectForJob.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
	}
	
	function confJobPriv(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		//配置职位权限
		var _obj = new Object();
		_obj.type = "Job";
		_obj.jobId = jobItem.data.jobId;
		window.showModalDialog("PrivSelectForJob.jsp", _obj, "dialogWidth:750px;dialogHeight:560px;status:no");
	}
	function confMobJobPriv(){
		var jobItem = jobGrid.getSelectionModel().getSelections()[0];
		//配置职位权限
		var _obj = new Object();
		_obj.type = "Job";
		_obj.jobId = jobItem.data.jobId;
		window.showModalDialog("../systemMobMgr/MobPrivSelectForJobNew.jsp", _obj, "dialogWidth:750px;dialogHeight:560px;status:no");
	}
	//特有权限树
	var privClassTree = new Ext.ux.tree.TreeGrid({
		id: 'privClassTree',
        title: '特有权限',
        columns:[{
            header: '权限名称',
            width: 500,
            dataIndex: 'name',
            align: 'center'
        },{
            header: '可否授出',
            width: 310,
            dataIndex: 'canGrant',
            align: 'center'
        }],
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/JobPrivHoldTreeAction',
        	baseParams: {_jobId:0}
        }),
        root: new Ext.tree.AsyncTreeNode({
            id:'XXXX'
        })
    });
    //privClassTree.getLoader().load(privClassTree.root);
    
    //所有权限树
    var allPrivClassTree = new Ext.ux.tree.TreeGrid({
    	id: 'allPrivClassTree' ,
        title: '所有权限',
        columns:[{
            header: '权限名称',
            width: 500,
            dataIndex: 'name',
            align: 'center'
        },{
            header: '可否授出',
            width: 310,
            dataIndex: 'canGrant',
            align: 'center'
        }],
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/JobAllPrivTreeGridAction',
        	baseParams:{jobId:0}
        }),
        root: new Ext.tree.AsyncTreeNode({
            id:'0'
        })
    });
	
    // second tabs built from JS
	var tabs2 = new Ext.TabPanel({
	    region: 'south',
        split: true,
	    activeTab: 0,
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[roleGrid,staffGrid,privClassTree,allPrivClassTree],
	    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs2.activeTab.id ;
	            	var jobId ;
	            	if(jobGrid.getSelectionModel().getSelections().length>0){
	            		jobId = jobGrid.getSelectionModel().getSelections()[0].data.jobId ;
	            	}
	            	switch (activeTab){
						case "roleGrid":
						{
							roleStore.on('beforeload',
								function(store){ 
									if(roleStore.lastOptions != null){
										roleStore.baseParams = {jobId:jobId};
									}
								}
							)
						    roleStore.removeAll() ;
							roleStore.load();
							break ;
						}
						case "staffGrid":
						{
							staffStore.on('beforeload',
								function(store){ 
									if(staffStore.lastOptions != null){
										staffStore.baseParams = {jobId:jobId};
									}
								}
							)
						    staffStore.removeAll() ;
							staffStore.load({params:{start:0, limit:8}});
							break ;
						}
						case "privClassTree":
						{
	            			privClassTree.getLoader().baseParams._jobId = jobId ;
							privClassTree.getLoader().load(privClassTree.root);
							break ;
						}
						case "allPrivClassTree":
						{	
							allPrivClassTree.getLoader().baseParams.jobId = jobId ;
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
	
	var win ;
	
	function newInfoPage(orgTmpId,operator,orgId,selJob){
		
		var postCombosto = new Ext.data.JsonStore({
	        remoteSort: true,
	        fields: ['postId', 'postName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/PostCmoSelAction&orgTmpId='+ orgTmpId 
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
	                name: 'jobName',
	                id: 'jobName',
	                allowBlank:false, 
	                blankText:"名称不能为空!",
	                anchor:'90%'
	            },{
                    xtype:'combo',
                    fieldLabel: '职位模板',
                    name: 'post',
                    id: 'post',
                    xtype: 'combo',
                    valueField: 'postId',
                    displayField: 'postName',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"职位模板不能为空!",
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
					
					var itemsArr = jobGrid.getStore().data.items ;
					if(operator == 'add'){
						for(var i=0;i<itemsArr.length;i++){
							if (itemsArr[i].data.jobName == Ext.getCmp("jobName").getValue()){
								//--ErrorHandle('指定的职位与现有职位重名，请指定另一职位名称');
								Ext.MessageBox.show({
						           title: '提示',
						           msg: '指定的职位与现有职位重名，请指定另一职位名称',
						           buttons: Ext.MessageBox.OK,
						           width:220,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
						}
					}else if(operator = 'mod'){
						for(var i=0;i<itemsArr.length;i++){
							if (itemsArr[i].data.jobName == Ext.getCmp("jobName").getValue() && Ext.getCmp("jobName").getValue() != itemsArr[i].data.jobName){
								//--ErrorHandle('指定的职位与现有职位重名，请指定另一职位名称');
								Ext.MessageBox.show({
						           title: '提示',
						           msg: '指定的职位与现有职位重名，请指定另一职位名称',
						           buttons: Ext.MessageBox.OK,
						           width:220,
						           icon: Ext.MessageBox.ERROR
						       	});
								return;
							}
						}
					}
					
					var jobName = Ext.getCmp("jobName").getValue();
					var index = Ext.getCmp("post").getValue();
					
					var obj = new Object();
					obj.orgId = orgId;
					obj.orgName = orgName || "";
					obj.jobName = jobName || "";
					obj.comments = Ext.getCmp("comments").getValue() || "";
					obj.postId = Ext.getCmp("post").getValue() || 0;
					switch (operator){
						case "add":
						{
							obj.jobId = 0;
							obj = callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "createWithLog", obj,session1.staff.staffId,session1.staff.staffName);
							var paramObj = new Object();
					    	paramObj.jobId = obj.jobId ;    	
					    	paramObj.postId = Ext.getCmp("post").getValue();
							paramObj.type = "postPrivAdd";			
										
							var retMap = invokeAction("/mobsystem/InsertPostPrivAction", paramObj);
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
					            Ext.example.msg('','职位新增成功！');
					        }, 1000);
					        break;
						}
						case "mod":
						{	
							obj.jobId = selJob[0].data.jobId ;
							callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "updateWithLog", obj,session1.staff.staffId,session1.staff.staffName);
							//因为iom的报里修改职位时不能修改职位模板，故屏蔽下面的修改移动权限
						/*	var paramObj = new Object();
					    	paramObj.jobId = obj.jobId ;    	
					    	paramObj.postId = Ext.getCmp("post").getValue();
							paramObj.type = "postPrivMod";			
										
							var retMap = invokeAction("/mobsystem/InsertPostPrivAction", paramObj);
						*/	
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
					            Ext.example.msg('','职位修改成功！');
					        }, 1000);
					        break;
						}
					}
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
	        title: '职位',
		    closable:true,
		    width:450,
		    height:275,
		    plain:true,
		    layout: 'border',
		    items: [jobInfoForm]
		});
		
		win.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("jobName").setValue(selJob[0].data.jobName);
			Ext.getCmp("comments").setValue(selJob[0].data.comments);
			
			postCombosto.on('load',function(ds,records,o){
	    		Ext.getCmp("post").setValue(selJob[0].data.postId);
			});
		}
	}
});

</script>