<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>配置职位</title>
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
					<div id='confJobDiv'></div>
				</td>
				<td valign = 'middle' align='center'>
					<input type="button" value="->" id="confGo"/>&nbsp; &nbsp;
					<br />
					<br />
					<input type="button" value="<-" id="confCome"/> &nbsp; &nbsp;
				</td>
				<td>
					<div id='spciJobDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan = 4 align='center'>
					<br><FONT SIZE="2" color = 'red'>注：</FONT>
					<FONT SIZE="2">职位前打勾代表该职位为默认职位！</FONT>
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

//登陆人的Id
var staffId = session1.staff.staffId;

//所选择人员
var _staffId = _obj.staffId ;

//登陆人的职位
var jobId = session1.job.jobId;
var orgPathCode = staffId == 0 ? "":session1.org.orgPathCode ;
var orgId = staffId == 0 ? 0:session1.org.orgId ;

Ext.onReady(function(){
    
    //可配置职位列表
    var confJobTree = new Ext.tree.TreePanel({
        animate:false,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/GetJobOrgAction',
        	baseParams: {orgPathCode:orgPathCode,orgId:orgId}
        }),
        enableDD:true,
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.45,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        root: {
            nodeType: 'async'
        }
    });
    
    new Ext.tree.TreeSorter(confJobTree, {folderSort:true});
    confJobTree.expandAll();
    
    confJobTree.render('confJobDiv');
    
    
    //人员特有角色列表
    var spciJobTree = new Ext.tree.TreePanel({
        animate:false,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/GetHoldJobAction',
        	baseParams: {orgPathCode:orgPathCode,orgId:orgId,staffId:_staffId}
        }),
        enableDD:true,
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.45,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        root: {
            nodeType: 'async'
        }
    });
    
    new Ext.tree.TreeSorter(spciJobTree, {folderSort:true});
    spciJobTree.expandAll();
    spciJobTree.render('spciJobDiv');
    
    
    Ext.get('confGo').on('click',function(e){
    	
    	if(!confJobTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的职位！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
    	
		//获得所选节点
    	var selNode = confJobTree.getSelectionModel().selNode ;
    	//获得所选节点的父节点ID
    	var privConTypeId = selNode.parentNode.id ;
    	
    	if(selNode.leaf == true){
    		spciJobTree.getNodeById(privConTypeId).appendChild(selNode);
    	}else{
    		//遍历该职位类别下的职位
    		var selChildNodes = selNode.childNodes;
    		selChildNodesLen = selChildNodes.length ;
    		
    		for(i = 0 ;i<selChildNodesLen ;i++ ){
    			if(selChildNodes[0].leaf){
    				spciJobTree.getNodeById(selNode.id).appendChild(selChildNodes[0]);
    			}
    		}
    	}
    	spciJobTree.expandAll();
    });
    
    Ext.get('confCome').on('click',function(e){
    	if(!spciJobTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的职位！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
    	
    	//获得所选节点
    	var selNode = spciJobTree.getSelectionModel().selNode ;
    	//获得所选节点的父节点ID
    	var privConTypeId = selNode.parentNode.id ;
    	
    	if(selNode.leaf == true){
    		if(spciJobTree.getNodeById(privConTypeId)){
    			Ext.Msg.show({
	                title: '提示', 
	                msg: '所选人员已经拥有该职位！',
	                icon: Ext.Msg.INFO,
	                minWidth: 200,
	                buttons: Ext.Msg.OK
	            });
	            return ;
    		}
    		confJobTree.getNodeById(privConTypeId).appendChild(selNode);
    	}
    	confJobTree.expandAll();
    });
    
    Ext.get('comitBtn').on('click',function(e){
    
    	//判断默认职位的合法性
    	var checkedNodes = spciJobTree.getChecked();
    	if(checkedNodes.length != 1){
    		Ext.Msg.show({
	                title: '提示', 
	                msg: '默认职位有且只能有一个！',
	                icon: Ext.Msg.INFO,
	                minWidth: 200,
	                buttons: Ext.Msg.OK
	            });
	            return ;
    	}
    	var selNode = spciJobTree.getRootNode().childNodes[0] ;
    	
    	var privs = new Array();
		var grantPrivs = new Array();
    	
    	getPrivs(selNode,privs,grantPrivs);
    	
		callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "updateJobs", _staffId, privs,grantPrivs);
		
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
    
    
   	function getPrivs(node,privs,grantPrivs){
   		var childNodes = node.childNodes;
    	var childLenth = childNodes.length;
		if(childLenth > 0){
			for(var i = 0 ;i<childLenth ;i++){
				if(childNodes[i].leaf){
					privs[privs.length] = childNodes[i].id ;
					if(childNodes[i].attributes.checked){
						grantPrivs[grantPrivs.length] = 1 ;
					}else{
						grantPrivs[grantPrivs.length] = 0 ;
					}
				}else{
					getPrivs(childNodes[i],privs,grantPrivs);
				}
			}
		}
	}
    
});

</script>
