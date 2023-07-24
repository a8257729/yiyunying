<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>请选择要推送短信人员</title>
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
		
		<script language="javascript" src="../public/script/helper.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		
		<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
    	<script type="text/javascript" src="../ext/ext-all.js"></script>
    	<script type="text/javascript"	src="/MOBILE/ext/examples/shared/examples.js"></script>
		<script language="javascript" src="/MOBILE/public/script/xworkAction.js"></script>
		
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
				</td>
				<td>
					<div id='spciPrivDiv'></div>
				</td>
			</tr>
			<tr>
				<td colspan = 4 align='center'><br>
					<p>
					 发送内容：
					</p>
					<br>
					<textarea name="msgContent" rows="4" cols="50">请输入发送内容</textarea>
					<br><br>
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
var _appUrl = _obj.appUrl;

//登陆人的Id
var staffId = session1.staff.staffId;
//登陆人的职位
var jobId = session1.job.jobId;
//登陆人的隐藏职位
var specialJobId = session1.job.specialJobId;

var areaId ;
var pathCode ;

Ext.onReady(function(){
	
	var orgId = 0 ;
	var orgPathCode ;
	var staffName ;
	var officeTel ;
	var userName  ;
	var mobileTel ;
	var qryType = 'qrySub';
	var qryMethod = 'pushMsg';
	var password ;
	
	document.getElementById("msgContent").value= '易运营手机客户端有新版本更新，点击下载：' + _appUrl;
	
    var confPrivTree = new Ext.tree.TreePanel({
        animate:true, 
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/StaffPagingForPushMsgAction',
        	baseParams: {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType,qryMethod:qryMethod,start:0, limit:5}
        }),
        enableDD:true,
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
    
    new Ext.tree.TreeSorter(confPrivTree, {folderSort:true});
//    confPrivTree.expand('true','false');
    confPrivTree.expandAll();
    confPrivTree.render('confPrivDiv');
    
    confPrivTree.on('checkchange', function(node, checked) {  
	 	node.expand();  
		node.attributes.checked = checked;  
		node.eachChild(function(child) {  
			child.ui.toggleCheck(checked);  
			child.attributes.checked = checked;  
			child.fireEvent('checkchange', child, checked);  
	 	});  
	}, confPrivTree);
	
    confPrivTree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的

	    if(node.isLeaf() == false){
			if(node.expanded == false){
				node.expand();//展开
			}else{
				node.collapse();//收起
			}
	    }
	    orgId = node.attributes.id;
	    orgPathCode = node.attributes.pathCode;
	    qryType = 'qryStaff';
		staffTree.on('beforeload', function(){ 
			staffTree.loader.dataUrl = '/MOBILE/ExtServlet?actionName=system/StaffPagingForPushMsgAction'; 
			staffTree.loader.baseParams = {orgPathCode:orgPathCode,orgId:orgId,qryType:qryType,limit:10,start:0}; 
		}); 
		staffTree.root.reload();
		staffTree.expandAll();
    });
    
    qryType = 'qryAllStaff';
    
    var staffTree = new Ext.tree.TreePanel({
        animate:true,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
        	dataUrl:'/MOBILE/ExtServlet?actionName=system/StaffPagingForPushMsgAction',
        	baseParams: {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType,limit:20,start:0}
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
    
    new Ext.tree.TreeSorter(staffTree, {folderSort:true});
    staffTree.expandAll();
    staffTree.render('spciPrivDiv');
    
    staffTree.on('checkchange', function(node, checked) {  
	 	node.expand();  
		node.attributes.checked = checked;  
		node.eachChild(function(child) {  
			child.ui.toggleCheck(checked);  
			child.attributes.checked = checked;  
			child.fireEvent('checkchange', child, checked);  
	 	});  
	}, staffTree);
	
    Ext.get('comitBtn').on('click',function(e){
    	
    	var checkedNodes = confPrivTree.getChecked();  //这是勾选的Nodes集合
    	
    	var staffNodes = staffTree.getChecked();  //这是勾选的Nodes集合
    	var inputParams = new Object();
//    	inputParams.msgContent = '易运营手机客户端有新版本更新，点击下载：' + _appUrl;
    	inputParams.msgContent = document.getElementById("msgContent").value; 
    	if (checkedNodes.length != 0) {
    		var nodesArr = new Array();
	   		for(var i=0;i<checkedNodes.length;i++){
	   			var nodes = new Object();
	   			nodes.id = checkedNodes[i].attributes.id;
	   			nodes.name = checkedNodes[i].attributes.text;
	   			nodes.pathCode = checkedNodes[i].attributes.pathCode;
	   			nodesArr.push(nodes);
	    	}
	    	
	    	inputParams.type = "pushMsg";
			inputParams.nodesArr = nodesArr;
			
			var retMap = invokeAction("/appmanage/MobileFrameAppAction", inputParams); 
    	} else if (staffNodes != 0) {
    		var staffArr = new Array();
	   		for(var i=0;i<staffNodes.length;i++){
	   			var nodes = new Object();
	   			nodes.id = staffNodes[i].attributes.id;
	   			nodes.name = staffNodes[i].attributes.text;
	   			nodes.mobileTel = staffNodes[i].attributes.mobileTel;
	   			if (nodes.mobileTel != null && nodes.mobileTel != '') {
	   				staffArr.push(nodes);
	   			}
	    	}
	    	
	    	inputParams.type = "pushMsgForStaff";
			inputParams.staffArr = staffArr;
			
			var retMap = invokeAction("/appmanage/MobileFrameAppAction", inputParams); 
			
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
            Ext.example.msg('','发送成功');
        }, 2000);
        setTimeout(function(){
            window.close();
        }, 3500);
        
    });
    
    Ext.get('exitBtn').on('click',function(e){
    	window.close();
    });
});
</script>
