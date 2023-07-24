<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>人员管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="staffMngDiv"></div>
	</body>
</html>

<script language="JScript">

var session1 = GetSession();

Ext.onReady(function(){
	var orgId = 0 ;
	var orgPathCode ;
	var staffName ;
	var officeTel ;
	var userName  ;
	var mobileTel ;
	var qryType = 'qrySub';
	var password ;
	
	var tree = new ZTESOFT.OrgTree({
		region: 'west',
       	allOrgs: false
	});
	
    tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
	    if(node.isLeaf() == false){
			if(node.expanded == false){
				node.expand();//展开
			}else{
				node.collapse();//收起
			}
	    }
	    orgId = node.attributes.orgId ;
	    orgPathCode = node.attributes.orgPathCode ;
	    store.on('beforeload',
			function(store){ 
				if(store.lastOptions != null){
					store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType};
				}
			}
		)
	    store.removeAll() ;
		store.load({params:{start:0, limit:5}});
    });
    
    //查询条件面板
    var qryPanel = new Ext.FormPanel({
		region: 'north',
     	labelAlign: 'left',
        labelWidth :70,
	 	frame:true,
        title: '查询条件',
        bodyStyle:'padding:5px 5px 5px 5px',
        height:Ext.getBody().getSize().height*0.2,
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
                    anchor:'95%',
                    listeners: {
						specialkey: function(f,e){
							if (e.getKey() == e.ENTER) {
								qryPanel.getForm().submit();
							}
						}}
					}, {
                    xtype:'textfield',
                    fieldLabel: '办公电话',
                    name: 'officeTel',
                    id: 'officeTel',
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
                },{
                    xtype:'textfield',
                    fieldLabel: '移动电话',
                    name: 'mobileTel',
                    id: 'mobileTel',
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
                    value: 'qrySub',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['qrySub','查询本组织及下属组织'],['qrySelf','查询本组织'],['qryNull','查询无职位人员']]
                    })
                },{
                    buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	staffName = Ext.getCmp("staffName").getValue() ;
			                	officeTel = Ext.getCmp("officeTel").getValue() ;
			                	userName  = Ext.getCmp("userName").getValue() ;
			                	mobileTel = Ext.getCmp("mobileTel").getValue() ;
			                	qryType   = Ext.getCmp("qryType").getValue() ;
			                	store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType};
									}
								})
								store.removeAll() ;
								store.load({params:{start:0, limit:5}});
			            	}
			            }},{
                    	text: '重置' ,
                    	listeners:{"click":function(){
			                qryPanel.form.reset();
			            }} 
                    }]
                }]
            }]
        }]
    });
    
    function renderIsBasic(value, p, r){
        if(value == 1){
        	return '是';
        }else{
        	return '否';
        }
    }
    
    function renderIscanGrant(value, p, r){
        if(value == 0){
        	return '<span style="color:red;">否</span>';
        }else{
        	return '<span style="color:green;">可</span>';
        }
    }
    
    function renderSource(value, p, r){
        if(value == 2){
        	return '特有';
        }else{
        	return '职位';
        }
    }
    
    // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'staffId', 'jobId', 'orgId', 'staffName','userName','jobName',
        	'isbasic', 'orgPathName', 'officeTel', 'mobileTel'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/StaffPagingAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'人员编号',dataIndex:'staffId',hidden:true },
	    {header:'职位编号',dataIndex:'jobId',hidden:true },
	    {header:'组织编号',dataIndex:'orgId',hidden:true },
	    {header:'姓名',dataIndex:'staffName',width:100},
	    {header:'用户名',dataIndex:'userName',width:100},
	    {header:'职位',dataIndex:'jobName',width:120},
	    {header:'职位是否缺省',dataIndex:'isbasic',renderer: renderIsBasic,width:80},
	    {header:'组织',dataIndex:'orgPathName',width:230},
	    {header:'办公电话',dataIndex:'officeTel',width:100},
	    {header:'移动电话',dataIndex:'mobileTel',width:100}
	]);   
	column.defaultSortable = true;//默认可排序   
	
    var pagingBar = new Ext.PagingToolbar({
		pageSize: 5,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	
	var grid = new Ext.grid.GridPanel({
		region: 'center',
	    title:'人员列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
	            	//人员信息填充
	                var _resultObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "findByKey", rec.data.staffId);
	            	Ext.getCmp("staffName2").setValue(_resultObj.staffName);
	            	Ext.getCmp("eMail2").setValue(_resultObj.email);
	            	Ext.getCmp("homeTel2").setValue(_resultObj.homeTel);
	            	Ext.getCmp("effectDate2").setValue(_resultObj.effectDate);
	            	Ext.getCmp("updateDate2").setValue(_resultObj.updateDate);
	            	Ext.getCmp("userName2").setValue(_resultObj.userName);
	            	Ext.getCmp("nation2").setValue(_resultObj.nationName);
	            	Ext.getCmp("lockDate2").setValue(_resultObj.lockDate);
	            	Ext.getCmp("expireDate2").setValue(_resultObj.expireDate);
	            	Ext.getCmp("certNo2").setValue(_resultObj.certNo);
	            	Ext.getCmp("address1").setValue(_resultObj.address1);
	            	Ext.getCmp("address2").setValue(_resultObj.address2);
	            	Ext.getCmp("comment2").setValue(_resultObj.comments);
	            	password = _resultObj.password ;
					roleStore.removeAll() ;
					roleStore.load({params:{staffId:rec.data.staffId, jobId:rec.data.jobId}});
	            }
	        }
	    }),
        bbar: pagingBar
    });
	
	//人员详情tab
    var infoPanel = new Ext.FormPanel({
     	labelAlign: 'left',
	 	frame:true,
        title: '人员信息',
        bodyStyle:'padding:5px 5px 0',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '姓名',
                    name: 'staffName2',
                    id: 'staffName2',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '电子邮箱',
                    name: 'eMail2',
                    id: 'eMail2',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '家庭电话',
                    name: 'homeTel2',
                    id: 'homeTel2',
                    anchor:'95%'
                }, {
                    xtype:'datefield',
                    fieldLabel: '生效日期',
                    name: 'effectDate2',
                    id: 'effectDate2',
                    format :'Y-m-d',
                    anchor:'95%'
                }, {
                    xtype:'datefield',
                    fieldLabel: '修改日期',
                    name: 'updateDate2',
                    id: 'updateDate2',
                    format :'Y-m-d',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'},{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName2',
                    id: 'userName2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '名族',
                    name: 'nation2',
                    id: 'nation2',
                    anchor:'95%'
                },{
                    xtype:'datefield',
                    fieldLabel: '锁定日期',
                    name: 'lockDate2',
                    id: 'lockDate2',
                    format :'Y-m-d',
                    anchor:'95%'
                }, {
                    xtype:'datefield',
                    fieldLabel: '失效日期',
                    name: 'expireDate2',
                    id: 'expireDate2',
                    format :'Y-m-d',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '身份证',
                    name: 'certNo2',
                    id: 'certNo2',
                    anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]
            
        },{
		    xtype:'textfield',
		    width: 300,
		    fieldLabel: '地址1',
		    name: 'address1',
		    id: 'address1',
		    anchor:'95%'
		},{
		    xtype:'textfield',
		    fieldLabel: 'IMSI码',
		    name: 'address2',
		    id: 'address2',
		    anchor:'95%'
		},{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'comment2',
		    id: 'comment2',
		    height : 50,
		    anchor:'95%'
		}]
    });
    
    //权限信息
    var roleStore = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'staffId', 'roleId', 'roleName', 'canGrant','source'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/StaffRoleAction'
        })
    });
    
    var roleColumn = new Ext.grid.ColumnModel([
	    {header:'人员编号',dataIndex:'staffId',hidden:true },
	    {header:'角色编号',dataIndex:'roleId',hidden:true },
	    {header:'角色名称',dataIndex:'roleName',width:430},
	    {header:'可否授出',dataIndex:'canGrant',renderer: renderIscanGrant,width:200},
	    {header:'角色来源',dataIndex:'source',renderer: renderSource,width:200}
	]);   
	roleColumn.defaultSortable = true;//默认可排序   
    
    var roleGrid = new Ext.grid.GridPanel({
        title: '人员角色',
        store: roleStore ,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:roleColumn
    });
    
	var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});
	
	var nullRightClick = new Ext.menu.Menu({
	    id:'nullRightClick'
	});
		
	var lineItem = new Ext.menu.Separator();

	grid.addListener('rowcontextmenu', rightClickFn);
	grid.addListener('contextmenu', nullRightClickFn);
	
	function rightClickFn(grid,rowIndex,e){
		rightClick.removeAll();
		var i = 0 ;
		if(session1.hasPriv("oaas_staff_add")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '新增人员' ,handler: function() {
				rightClick.hide();
				staffAdd();
			}}));
		}
		if(session1.hasPriv("oaas_staff_update")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '修改人员' , handler: function() {
				rightClick.hide();
				staffMod();
			}}));
		}
		if(session1.hasPriv("oaas_staff_delete")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除人员' ,handler: function() {
				rightClick.hide();
				staffDelVlt();
			}}));
		}
		//增加配置角色菜单
		if(session1.hasPriv("oaas_staff_cfg_role"))	{
			rightClick.add(new Ext.menu.Separator());
			i++ ;
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置角色' ,handler: function() {
				rightClick.hide();
				confRole();
			}}));
		}
		//增加配置权限菜单
		if(session1.hasPriv("oaas_staff_cfg_priv"))	{
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置权限' ,handler: function() {
				rightClick.hide();
				confPriv();
			}}));
		}
		//增加配置权限菜单
		if(session1.hasPriv("oaas_staff_cfg_priv"))	{
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置移动菜单权限' ,handler: function() {
				rightClick.hide();
				confMobPriv();
			}}));
		}
		//增加配置职位菜单
		if(session1.hasPriv("oaas_staff_cfg_job")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '配置职位' ,handler: function() {
				rightClick.hide();
				confJob();
			}}));
		}
		
		//增加解除职位菜单
		if(session1.hasPriv("oaas_staff_cfg_job")){
			rightClick.insert(i++,new Ext.menu.Item({ text: '解除职位' ,handler: function() {
				rightClick.hide();
				delJobVlt();
			}}));
		}
		
		
		grid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    function nullRightClickFn(e){
    	nullRightClick.removeAll();
    	if(session1.hasPriv("oaas_staff_add")){
			nullRightClick.add(new Ext.menu.Item({ text: '新增人员' ,handler: function() {
					staffAdd();
					rightClick.hide();
			}}));
		}
    	nullRightClick.showAt(e.getXY());
    }	
	
	//人员新增方法
	function staffAdd(){
		var selections = grid.getSelectionModel().getSelections();
		var hasJobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "hasJobs", orgId);
		if(hasJobs == false){
			Ext.Msg.alert('提示', '所选组织下没有职位，不能增加人员');
			return;
		}
		var _obj = new Object();
		_obj.operation = "add";
		_obj.orgId = orgId;
		_obj.staffOrgId = orgId ;
		_obj.isBasic = 1 ;
		var newObj = window.showModalDialog("StaffInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:520px;status:no");
	
		if(newObj){
			store.reload();
		}
	}
	
	//人员修改方法
	function staffMod(){
		
		var _obj = new Object();
		_obj.operation = "mod";
		_obj.orgId = orgId;
		_obj.staffOrgId = grid.getSelectionModel().getSelected().data.orgId;
		_obj.staffId = grid.getSelectionModel().getSelected().data.staffId ;
		_obj.jobId = grid.getSelectionModel().getSelected().data.jobId ;
		_obj.password = password ;
		_obj.isBasic = grid.getSelectionModel().getSelected().data.isbasic ;
		var newObj = window.showModalDialog("StaffInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:520px;status:no");
	
		store.reload();
	}
	
	function staffDelVlt(){
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		if(staffId == session1.staff.staffId){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您无法删除自己',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', staffDel);
	}
	
	//人员删除方法
	function staffDel(btn){
		if (btn == 'yes'){
			var staffId = grid.getSelectionModel().getSelected().data.staffId ;
			var _result = callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "deleteWithLog", staffId,session1.staff.staffId,session1.staff.staffName);
			
			//add by fengyang 2011-1-5
			//call out system.
			var staffObj = new Object();
			staffObj.staffId = staffId;
			var userName = grid.getSelectionModel().getSelected().data.userName ;
			staffObj.userName = userName;
			/*
			var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction",
										"executeStaff", staffObj,"DELETE_USERPROFILE");
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
			    Ext.example.msg('','人员删除成功！');
			}, 1000);
			store.reload();
		}
	}
	
	//配置角色
	function confRole(){
		var staff=session1.staff.staffId;
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		//判断登录人与要修该的人是否相同，如果相同，则推出
		if(staff==staffId){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您无法配置自己的角色',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		if(staffId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您不能给超级管理员配置角色',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		if(grid.getSelectionModel().getSelected().data.jobId==0){
			//--ErrorHandle("请先给人员配置职位，再配置角色");
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '请先给人员配置职位，再配置角',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
		  	return;
		}
		var defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId );
		while(defaultJobId == '-1'){
			callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb","createJob",orgId,staffId);
			defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId );
		}
		var jobId=grid.getSelectionModel().getSelected().data.jobId;
		if (defaultJobId != "-1"){
			var _obj = new Object();
			_obj.staffId = staffId;
			_obj.jobId = jobId;
			_obj.defaultJobId=defaultJobId;
			window.showModalDialog("RoleSelectForStaff.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
		}else{
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '该人员没有默认职位，无法配置角色',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
		}
	}
	
	function confPriv(){
		var staff=session1.staff.staffId;
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		var orgId = grid.getSelectionModel().getSelected().data.orgId ;
		
		//判断登录人与要修该的人是否相同，如果相同，则推出
		if(staff==staffId){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您无法配置自己的权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		//您不能给超级管理员配置权限
		if(staffId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您不能给超级管理员配置权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		//先给人员配置职位，再配置权限
		if(grid.getSelectionModel().getSelected().data.jobId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '请先给人员配置职位，再配置权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		
		//隐藏职位ID，也就是说，对人员配置特有权限，就是对该职位进行配置
		var defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId);
		
		while(defaultJobId == '-1'){
		   	callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb","createJob",orgId,staffId);
		  	defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId );
		}
		
		//人员的真正职位
	 	var jobId = grid.getSelectionModel().getSelected().data.jobId;
		if (defaultJobId != "-1"){
			var _obj = new Object();
			_obj.staffId=staffId;
			_obj.jobId=jobId;
			_obj.defaultJobId=defaultJobId;
			window.showModalDialog("PrivSelectForStaff.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
		}else{
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '该人员没有默认职位，无法修改权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return ;
		}
	}
	function confMobPriv(){
		var staff=session1.staff.staffId;
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		var orgId = grid.getSelectionModel().getSelected().data.orgId ;
		
		//判断登录人与要修该的人是否相同，如果相同，则推出
		if(staff==staffId){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您无法配置自己的权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		//您不能给超级管理员配置权限
		if(staffId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您不能给超级管理员配置权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		//先给人员配置职位，再配置权限
		if(grid.getSelectionModel().getSelected().data.jobId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '请先给人员配置职位，再配置权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		
		//隐藏职位ID，也就是说，对人员配置特有权限，就是对该职位进行配置
		var defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId);
		
		while(defaultJobId == '-1'){
		   	callRemoteFunction("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb","createJob",orgId,staffId);
		  	defaultJobId = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManagerWeb", "findJobIdByName", "JOB_" + staffId );
		}
		
		//人员的真正职位
	 	var jobId = grid.getSelectionModel().getSelected().data.jobId;
		if (defaultJobId != "-1"){
			var _obj = new Object();
			_obj.staffId=staffId;
			_obj.jobId=jobId;
			_obj.defaultJobId=defaultJobId;
			window.showModalDialog("../systemMobMgr/MobPrivSelectForStaffNew.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
		}else{
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '该人员没有默认职位，无法修改权限',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return ;
		}
	}
	
	function confJob(){
		var staff = session1.staff.staffId;
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		var jobId = grid.getSelectionModel().getSelected().data.jobId ;
		if(staffId==0){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您不能给超级管理员配置职位',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		if(staff==staffId){
			Ext.MessageBox.show({
			    title: '错误',  
			    msg: '您无法配置自己的职位',
			   	width:220,
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.ERROR
			});
			return;
		}
		var _obj= new Object();
		_obj.staffId = staffId;
		_obj.jobId = jobId;
		window.showModalDialog("JobAssign.jsp", _obj, "dialogWidth:680px;dialogHeight:560px;status:no");
	}
	
	function delJobVlt(){
		var staff = session1.staff.staffId;
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		if(staff==staffId){
			Ext.MessageBox.show({
				title: '提示',
				msg: '您无法解除自己的职位',
			   	buttons: Ext.MessageBox.OK,
			   	width:220,
			   	icon: Ext.MessageBox.ERROR
			});
			return;
		}
		var jobCnt = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "findJobCount", staffId);
		if(jobCnt == 0)	{
			//该人员没有职位，无法解除
			Ext.MessageBox.show({
				title: '提示',
				msg: '该人员没有职位，无法解除',
			   	buttons: Ext.MessageBox.OK,
			   	width:220,
			   	icon: Ext.MessageBox.ERROR
			});
			return;
		}
		if(jobCnt <= 1)	{
			Ext.MessageBox.show({
				title: '提示',
				msg: '该职位为当前人员唯一的职位，不能解除',
			   	buttons: Ext.MessageBox.OK,
			   	width:220,
			   	icon: Ext.MessageBox.ERROR
			});
			return;
		}

		if(grid.getSelectionModel().getSelected().data.isbasic == '1'){
			Ext.MessageBox.show({
				title: '提示',
				msg: '该职位为当前人员缺省职位，不能解除',
			   	buttons: Ext.MessageBox.OK,
			   	width:220,
			   	icon: Ext.MessageBox.ERROR
			});
			return;
		}
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', delJob);
	}
	
	function delJob(btn){
		var staffId = grid.getSelectionModel().getSelected().data.staffId ;
		var jobId = grid.getSelectionModel().getSelected().data.jobId ;
		if(btn == 'yes'){
			callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "removeJob", staffId, jobId);
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
	            Ext.example.msg('','解除成功');
	        }, 1000);
	        store.reload();
		}
	}
	
    // second tabs built from JS
	var tabs2 = new Ext.TabPanel({
		region: 'south',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.8,   
	    height:Ext.getBody().getSize().height*0.485, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[infoPanel,roleGrid]
	});
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[qryPanel,grid,tabs2]
	}); 
	
	var viewport = new Ext.Viewport({
		el:'staffMngDiv',
		layout: 'border',
		items:[tree,earthviewport]
	});
});

</script>