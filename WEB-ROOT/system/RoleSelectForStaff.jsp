<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>配置角色</title>
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
		
		<script language="javascript" src="../public/script/helper.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
    	<script type="text/javascript" src="../ext/ext-all.js"></script>
		
	</head>
	<body style="width: 100%; height: 100%; overflow: auto">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr valign="top">
				<td>
					<div id='orgDiv'></div>
				</td>
				<td>
					<div id='confRoleDiv'></div>
				</td>
				<td valign = 'middle'>
					<input type="button" value="->" id="confGo"/>
					<br />
					<br />
					<input type="button" value="<-" id="confCome"/>
				</td>
				<td>
					<div id='spciRoleDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan = 4 align='center'>
					<br><FONT SIZE="2" color = 'red'>注：</FONT>
					<FONT SIZE="2">授权的时候，可以选择是否允许这个角色的授予对象能够向其他对象授予本角色，打勾则为允许。</FONT>
				</td>
			</tr>
			<tr>
				<td colspan = 4 align='center'><br><br>
					<input id = "comitBtn" type="button" value="提交" /> &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
					<input id = "exitBtn" type="button" value="取消" />
				</td>
			</tr>
		</table>
	</body>
</html>

<script language="JScript">

var _obj = window.dialogArguments;
var session1 = GetSession();

var _selectedStaffId=_obj.staffId;		
var _jobId = _obj.jobId;	
var _defaultJobId=_obj.defaultJobId;
var methodName ;

//登陆人的Id
var staffId = session1.staff.staffId;
//登陆人的职位
var jobId = session1.job.jobId;
//登陆人的隐藏职位
var specialJobId = session1.job.specialJobId;

var areaId = 1;
var pathCode ;

if(staffId == 0){
	 methodName = "1" ;
	 areaId = 1 ;
}else{
	if(jobId != -1 && specialJobId == -1){
		methodName = "2" ;
	}else if(jobId == -1 && specialJobId != -1){
		 methodName = "3" ;
	}else{
		 methodName = "4" ;
	}
	areaId = session1.area.areaId ;
}


Ext.onReady(function(){

    var orgTree = new Ext.tree.TreePanel({
        animate:true, 
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/AreaTreeSelAction',
        	baseParams:{staffId:session1.staff.staffId}
        }),
        enableDD:true,
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.3,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true}
    });
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '区域列表',
        draggable:false,
        id:1
    });
    orgTree.setRootNode(root);
    orgTree.render('orgDiv');
    root.expand(false, false);
    
    //可配置角色列表
    var confRoleTree = new Ext.tree.TreePanel({
        animate:true, 
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/RoleConfSelAction&areaId=' + areaId,
        	baseParams: {jobId:jobId,specialJobId:specialJobId,_jobId:_jobId,_defaultJobId:_defaultJobId,methodName:methodName}
        }),
        enableDD:true,
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.3,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true}
    });
    
    var confRoleRoot = new Ext.tree.AsyncTreeNode({
        text: '可配置角色',
        draggable:false,
        id:'1'
    });
    confRoleTree.setRootNode(confRoleRoot);
    confRoleTree.render('confRoleDiv');
    confRoleRoot.expand(false, false);
    
    
    //人员特有角色列表
    var spciRoleTree = new Ext.tree.TreePanel({
        animate:true,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/RoleHoldSelAction',
        	baseParams: {jobId:_defaultJobId}
        }),
        enableDD:true,
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.35,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true}
    });
    
    var spciRoleRoot = new Ext.tree.AsyncTreeNode({
        text: '人员特有角色',
        draggable : false,
        id:'1',
        checked : false
    });
    spciRoleTree.setRootNode(spciRoleRoot);
    spciRoleTree.render('spciRoleDiv');
    spciRoleRoot.expand(false, false);
    
    orgTree.on('click', function(node){
    	if(node.leaf == false){
			if(node.expanded == false){
				node.expand();//展开
			}else{
			 	node.collapse();//收起
			} 
	    }
	    areaId = node.attributes.id ;
	    
	  	confRoleTree.removeAll();
	  	confRoleTree.getLoader().dataUrl = '/MOBILE/ExtServlet?actionName=system/RoleConfSelAction&areaId='+areaId;
	  	confRoleTree.root.reload();
    });
    
    Ext.get('confGo').on('click',function(e){
    	if(!confRoleTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的角色！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
    	
    	var confSelNode = confRoleTree.getSelectionModel().selNode ;
    	spciRoleTree.getRootNode().appendChild(confSelNode);
    });
    
    Ext.get('confCome').on('click',function(e){
    	if(!spciRoleTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的角色！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
    	
    	var spciSelNode = spciRoleTree.getSelectionModel().selNode ;
    	confRoleTree.getRootNode().appendChild(spciSelNode);
    });
    
    Ext.get('comitBtn').on('click',function(e){
		var chiNodes = spciRoleTree.getRootNode().childNodes;
		var selNodes = spciRoleTree.getChecked();
        
		var selectedRoles = new Array();
		var canGrant = new Array();
		for (var i=0; i<chiNodes.length; i++){
			selectedRoles[selectedRoles.length] = chiNodes[i].id;
			if(chiNodes[i].attributes.checked ){
				canGrant[canGrant.length] = 1 ;
			}else{
				canGrant[canGrant.length] = 0 ;
			}
		}
		
		callRemoteFunction("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "updateJobRoles", _defaultJobId, selectedRoles, canGrant);
		
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
		    Ext.MessageBox.show({
			    title: '提示',  
			    msg: '配置成功',
			    buttons: Ext.MessageBox.OK,
			    icon: Ext.MessageBox.INFO
			});
		}, 2000);
		
		window.close();
        
    });
    
    Ext.get('exitBtn').on('click',function(e){
    	window.close();
    });
    
    
});
</script>
