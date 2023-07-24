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
					<div id='rolePrivDiv'></div>
				</td>
				<td>
					<div id='spciPrivDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" height="40" class="td_grey">
					<input type="radio" name="Radio1" value="1"
						checkType="checkbox" checked>
					<font size="2">影响职位</font>
					&nbsp;&nbsp;
					<input type="radio" name="Radio1" value="2">
					<font size="2">不影响职位</font>
				</td>
			</tr>
			<tr>
				<td colspan = 5 align='center'><br><br>
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

var _jobId = _obj.jobId;	

//登陆人的Id
var staffId = session1.staff.staffId;

Ext.onReady(function(){
    var confPrivTree = new Ext.tree.TreePanel({
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PostPrivGrantTreeAction',
        	baseParams: {postId:_obj.postId}
        }),
        containerScroll: true,
        border: true,
        animCollapse: false, 
        width:Ext.getBody().getSize().width*0.3,
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
    
    var rolePrivTree = new Ext.tree.TreePanel({
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PostRolePrivTreeAction',
        	baseParams: {postId:_obj.postId}
        }),
        containerScroll: true,
        border: true,
        animCollapse: false, 
        width:Ext.getBody().getSize().width*0.3,
	    height:Ext.getBody().getSize().height*0.7,
        dropConfig: {appendOnly:true},
        rootVisible: false,
        checked: true,
        root: {
            nodeType: 'async'
        }
    });
    new Ext.tree.TreeSorter(rolePrivTree, {folderSort:true});
    rolePrivTree.render('rolePrivDiv');
    
    //职位特有角色列表
    var spciPrivTree = new Ext.tree.TreePanel({
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/PostPrivHoldTreeAction',
        	baseParams: {postId:_obj.postId}
        }),
        containerScroll: true,
        border: true,
        animCollapse: false, 
        width:Ext.getBody().getSize().width*0.3,
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
    	
    	var radiovalue = 0;
		var radio1= document.all.Radio1;											 
		for (var i=0; i< radio1.length; i++){
			if (radio1[i].checked == true){
				radiovalue = radio1[i].value;
			}			
		}
		
		if(radiovalue == 1){
			/**影响到职位*/
			callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "updatePrivs", _obj.postId, privs, true);
		}else if(radiovalue == 2){
			/**不影响到职位*/
			callRemoteFunction("com.zterc.uos.oaas.service.postprivmanager.PostPrivilegeManagerWeb", "updatePrivs", _obj.postId, privs, false);
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