<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>配置角色</title>
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
		
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../common/js/xml2json.js"></script>
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
					&nbsp;<input type="button" value="->" id="confGo"/>&nbsp;
					<br />
					<br />
					&nbsp;<input type="button" value="<-" id="confCome"/>&nbsp;
				</td>
				<td>
					<div id='spciRoleDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center" height="40" class="td_grey">
					<input type="radio" name="Radio1" value="1"
						checkType="checkbox" checked>
					<font size="2">影响职位</font>
					&nbsp;&nbsp;
					<input type="radio" name="Radio1" value="2">
					<font size="2">不影响职位</font>
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

Ext.onReady(function(){
    var orgTree = new Ext.tree.TreePanel({
        animate:true, 
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/AreaTreeSelAction',
        	baseParams:{staffId:session1.staff.staffId}
        }),
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
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PostRoleConfSelAction',
        	baseParams: {postId:_obj.postId,areaId:0}
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
    
    
    //职位角色列表
    var spciRoleTree = new Ext.tree.TreePanel({
        animate:true,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PostRoleHoldSelAction',
        	baseParams: {postId:_obj.postId}
        }),
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.35,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true}
    });
    
    var spciRoleRoot = new Ext.tree.AsyncTreeNode({
        text: '已有角色',
        draggable : false,
        id:'1'
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
	    var areaId = node.attributes.id ;
	    
	    confRoleTree.getLoader().baseParams.areaId = areaId ;
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
		for (var i=0; i<chiNodes.length; i++){
			selectedRoles[selectedRoles.length] = chiNodes[i].id;
		}
		
		var radiovalue = 0;
		var radio1= document.all.Radio1;											 
		for (var i=0; i< radio1.length; i++){
			if (radio1[i].checked == true){
				radiovalue = radio1[i].value;
			}			
		}
		
		if(radiovalue == 1){    
    		//影响职位    
			callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "updateRoles", _obj.postId, selectedRoles, true);
		}else if(radiovalue == 2){		
			//只影响模板
			callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "updateRoles", _obj.postId, selectedRoles, false);
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
		    Ext.example.msg('','配置成功');
		}, 1000);
		window.close();
    });
    
    Ext.get('exitBtn').on('click',function(e){
    	window.close();
    });
});
</script>
