<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!-- TemplBeginEditable name="java" -->

<html>
	<head>

		<title>选择组织</title>
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />
		<script language="javascript" src="../public/script/helper.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../ext/ext-all.js"></script>
		<script type="text/javascript" src="../common/ztesoftext/orgTree.js"></script>
		
        <%@ include file="../public/style.jsp"%>	
       
</head>
<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
<table width="100%" border="0" cellpadding="2" cellspacing="1">      
    <tr valign="top"><td><div id="orgDiv"></div></td></tr>
    <tr><td align="center"><input type="button" name="commit" value="确认" onclick="returnData()"></td></tr>
</table>
</body>
<script type="text/javascript">
var session1 = GetSession();

var selType = GetUrlParameter("sel_type");//选择种类{1单选，2多选}

var tree ;

Ext.onReady(function(){
	tree = new ZTESOFT.OrgTree({
		id:'orgTree',
       	//region: 'west', 
       	allOrgs:true,
       	renderTo: 'orgDiv',
       	width:Ext.getBody().getSize().width,
       	height:Ext.getBody().getSize().height*0.9,
        checked:true
	});
	
    //tree.expandAll();
    tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
	    if(node.leaf == false){
	     if(node.expanded == false){
	     	node.expand();//展开
	     }else{
	     	node.collapse();//收起
	    	} 
	    }
	    orgId = node.attributes.orgId ;
	    orgName = node.attributes.text;
	    orgPathCode = node.attributes.orgPathCode ;
    });
	tree.on('checkchange', function(node, checked) {  
	 	node.expand();  
		node.attributes.checked = checked;  
		node.eachChild(function(child) {  
		child.ui.toggleCheck(checked);  
		child.attributes.checked = checked;  
		child.fireEvent('checkchange', child, checked);  
	 });  
	}, tree);
	
	
});

function returnData(){
       selNodes = tree.getChecked();
       if (selType ==1 && selNodes != null && selNodes.length >1){
          Ext.MessageBox.show({
                          title: '提示',
                          msg: '只能选择一个组织！',
                          buttons: Ext.MessageBox.OK,
                          width:200,
                          icon: Ext.MessageBox.ERROR
                      });
                      return;
       }
       window.returnValue = selNodes;
       window.close();
}
</script>
