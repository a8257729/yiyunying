<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>配置权限</title>
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
					<div id='confPrivDiv'></div>
				</td>
				<td valign = 'middle' align='center'>
					<input type="button" value="->" id="confGo"/>&nbsp; &nbsp;
					<br />
					<br />
					<input type="button" value="<-" id="confCome"/> &nbsp; &nbsp;
				</td>
				<td>
					<div id='spciPrivDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan = 4 align='center'>
					<br><FONT SIZE="2" color = 'red'>注：</FONT>
					<FONT SIZE="2">授权的时候，可以选择是否允许这个权限的授予对象能够向其他对象授予本权限，打勾则为允许。</FONT>
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

var _selectedStaffId =_obj.staffId;		
var _jobId = _obj.jobId;	
var _defaultJobId =_obj.defaultJobId;

//登陆人的Id
var staffId = session1.staff.staffId;
//登陆人的职位
var jobId = session1.job.jobId;
//登陆人的隐藏职位
var specialJobId = session1.job.specialJobId;
var methodName = getMethodName();

var areaId ;
var pathCode ;

function getMethodName(){
	if(staffId == 0){
		 return "1" ;
	}else if(jobId != -1 && specialJobId == -1){
		 return "2" ;
	}else if(jobId == -1 && specialJobId != -1){
		 return "3" ;
	}else{
		 return "4" ;
	}
}

Ext.onReady(function(){

    var confPrivTree = new Ext.tree.TreePanel({
        animate:true, 
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PrivConfSelAction',
        	baseParams: {jobId:jobId,specialJobId:specialJobId,_jobId:_jobId,_defaultJobId:_defaultJobId,methodName:methodName}
        }),
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
    
    new Ext.tree.TreeSorter(confPrivTree, {folderSort:true});
    confPrivTree.expandAll();
    
    confPrivTree.render('confPrivDiv');
    
    
    //人员特有角色列表
    var spciPrivTree = new Ext.tree.TreePanel({
        animate:true,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PrivHoldSelAction',
        	baseParams: {jobId:_defaultJobId}
        }),
        containerScroll: true,
        border: true,
        width:Ext.getBody().getSize().width*0.45,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        checked: true,
        root: {
            nodeType: 'async'
        }
    });
    
    new Ext.tree.TreeSorter(spciPrivTree, {folderSort:true});
    spciPrivTree.expandAll();
    spciPrivTree.render('spciPrivDiv');
    
    spciPrivTree.on('checkchange', function(node, checked) {  
	 	node.expand();  
		node.attributes.checked = checked;  
		node.eachChild(function(child) {  
			child.ui.toggleCheck(checked);  
			child.attributes.checked = checked;  
			child.fireEvent('checkchange', child, checked);  
	 	});  
	}, spciPrivTree);
    
    
    Ext.get('confGo').on('click',function(e){
    	if(!confPrivTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的权限！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
		//获得所选节点
    	var selNode = confPrivTree.getSelectionModel().selNode ;
    	//获得所选节点的父节点ID
    	var nodePath = selNode.getPath();
    	//找出节点的父级节点
   		var nodes = nodePath.split("/");
   		var nodesNew = new Array();
   		for(var i=0;i<nodes.length;i++){
   			//找出需要添加到目标树的节点
   			if(i>1&&Ext.isEmpty(spciPrivTree.getNodeById(nodes[i]))){
   				nodesNew.push(nodes[i]);
   			}
    	}
   		var nodeArr = new Array();
   		for(var i=0;i<nodesNew.length;i++){
   			var n = new Ext.tree.TreeNode({
   				id:confPrivTree.getNodeById(nodesNew[i]).id,
   				text:confPrivTree.getNodeById(nodesNew[i]).text,
   				leaf:confPrivTree.getNodeById(nodesNew[i]).leaf,
   				checked:false
   			});
   			nodeArr.push(n);
   		}
   		for(var i=1;i<nodeArr.length;i++){
   			nodeArr[i-1].appendChild(nodeArr[i]);
   		}
   		
    	if(selNode.leaf == true){//叶子节点
	    	//得到节点的全路径
	   		var privConTypeId = confPrivTree.getNodeById(nodesNew[0]).parentNode.id ;
    		spciPrivTree.getNodeById(privConTypeId).appendChild(nodeArr[0]);
   			removeParentNode(confPrivTree.getNodeById(nodeArr[nodeArr.length-1].id)) ;
    	}else{
    		var privConTypeId = selNode.id;
    		if(nodesNew.length >0){
    			privConTypeId = confPrivTree.getNodeById(nodesNew[0]).parentNode.id ;
    		}
    		if(nodeArr.length > 0){
    			spciPrivTree.getNodeById(privConTypeId).appendChild(nodeArr[0]);
    			selNode.cascade(moveLowerNodes,selNode);
    			removeParentNode(confPrivTree.getNodeById(nodeArr[nodeArr.length-1].id)) ;
    		}else{
    			selNode.cascade(moveLowerNodes,selNode);
    			selNode.remove();
    		}
    	}
    	spciPrivTree.expandAll();
    });
    
    Ext.get('confCome').on('click',function(e){
    	if(!spciPrivTree.getSelectionModel().selNode){
    		Ext.Msg.show({
                title: '提示', 
                msg: '请选择需要操作的权限！',
                icon: Ext.Msg.INFO,
                minWidth: 200,
                buttons: Ext.Msg.OK
            });
            return ;
    	}
    	
    	//获得所选节点
    	var selNode = spciPrivTree.getSelectionModel().selNode ;
    	//获得所选节点的父节点ID
    	var nodePath = selNode.getPath();
    	//找出节点的父级节点
   		var nodes = nodePath.split("/");
   		var nodesNew = new Array();
   		for(var i=0;i<nodes.length;i++){
   			//找出需要添加到目标树的节点
   			if(i>1&&Ext.isEmpty(confPrivTree.getNodeById(nodes[i]))){
   				nodesNew.push(nodes[i]);
   			}
    	}
   		var nodeArr = new Array();
   		for(var i=0;i<nodesNew.length;i++){
   			var n = new Ext.tree.TreeNode({
   				id:spciPrivTree.getNodeById(nodesNew[i]).id,
   				text:spciPrivTree.getNodeById(nodesNew[i]).text,
   				leaf:spciPrivTree.getNodeById(nodesNew[i]).leaf,
   				checked:false
   			});
   			nodeArr.push(n);
   		}
   		for(var i=1;i<nodeArr.length;i++){
   			nodeArr[i-1].appendChild(nodeArr[i]);
   		}
   		
    	if(selNode.leaf == true){//叶子节点
	    	//得到节点的全路径
	   		var privConTypeId = spciPrivTree.getNodeById(nodesNew[0]).parentNode.id ;
    		confPrivTree.getNodeById(privConTypeId).appendChild(nodeArr[0]);
   			removeParentNode(spciPrivTree.getNodeById(nodeArr[nodeArr.length-1].id))
    	}else{
    		var privConTypeId = selNode.id;
    		if(nodesNew.length!=0){
    			privConTypeId = spciPrivTree.getNodeById(nodesNew[0]).parentNode.id ;
    		}
    		if(nodesNew.length >0){
    			confPrivTree.getNodeById(privConTypeId).appendChild(nodeArr[0]);
    			selNode.cascade(moveLowerNodes2,selNode);
    			removeParentNode(spciPrivTree.getNodeById(nodeArr[nodeArr.length-1].id))
    		}
    		else{
    			selNode.cascade(moveLowerNodes2,selNode);
    			selNode.remove();
    		}
    	}
    	confPrivTree.expandAll();
    });
    
    Ext.get('comitBtn').on('click',function(e){
    	
    	var selNode = spciPrivTree.getNodeById("0");
    	
    	var privs = new Array();
		var grantPrivs = new Array();
    	
    	getPrivs(selNode,privs,grantPrivs);
    	
		callRemoteFunction("com.zterc.uos.oaas.service.jobprivmanager.JobPrivilegeManagerWeb", "updateJobPrivs", _defaultJobId, privs, grantPrivs);
		
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
    
    function moveLowerNodes(node){
    	var n = new Ext.tree.TreeNode({
			id:node.id,
			text:node.text,
			leaf:node.leaf,
			checked:false
		});
		
		var parentNode = spciPrivTree.getNodeById(node.parentNode.id) ;
		var pointNode = spciPrivTree.getNodeById(node.id) ;
		if(Ext.isEmpty(pointNode)){
			parentNode.appendChild(n);
		}else{
			return ;
		}
    }
    
    function moveLowerNodes2(node){
    	var n = new Ext.tree.TreeNode({
			id:node.id,
			text:node.text,
			leaf:node.leaf
		});
		
		var parentNode = confPrivTree.getNodeById(node.parentNode.id) ;
		var pointNode = confPrivTree.getNodeById(node.id) ;
		if(Ext.isEmpty(pointNode)){
			parentNode.appendChild(n);
		}else{
			return ;
		}
    }
    
    //删除没有叶子节点的父级节点
    function removeParentNode(node){
    	var parentNode = node.parentNode;
    	node.remove();
    	if(!parentNode.hasChildNodes()){
    		if(parentNode.id==0){
    			return
    		}
    		removeParentNode(parentNode);
    	}
    }
    
    
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
