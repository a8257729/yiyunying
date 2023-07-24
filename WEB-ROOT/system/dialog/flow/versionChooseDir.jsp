<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.flow_version_save_catalog_choose")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" />
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
	<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td>
			<ZTESOFT:treelist id="flowTree" name="flowTree" class="treelist" width="100%" height="300" onItemClick="versionOperation.flowClick()">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayTip="true" displayText="<%=getI18NResource("designer.flow_library")%>" propertyName="name" />
					<ZTESOFT:column display="false" propertyName="id" />
					<ZTESOFT:column display="false" propertyName="type" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40" colspan="2">
	  	<input type="button" class="button_blue" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick='versionOperation.submit()'>
	  	<input type="button" class="button_blue" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="versionOperation.cancel()">
	  </td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
</table>
<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-03-04
// Author : Xu.fei3
// commits: Choose an flow template
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var obj = window.dialogArguments;
var versionOperation = new VersionOperation();

/* \u521d\u59cb\u5316 */
versionOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
function VersionOperation(){
	this.flowTree = document.all.flowTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){		
		var data = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findTopPackageCatalogsXml", obj.areaId);
		this.flowTree.loadByXML(data);
	}
	
	//\u6d41\u7a0b\u6811\u4e0a\u9762\u5355\u51fb

	this.flowClick = function(){
		if(parseInt(this.flowTree.selectedItem.type) == 1){
			//\u662f\u6d41\u7a0b\u76ee\u5f55\uff0c\u76ee\u5f55\u4e0b\u9762\u53ef\u80fd\u6709\u5b50\u76ee\u5f55\u548c\u6a21\u677f

			if((this.flowTree.selectedItem.items == null) || (this.flowTree.selectedItem.items.length == 0)){
				var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackagesByCatalogIdXml", this.flowTree.selectedItem.id);
				this.flowTree.selectedItem.insertByXML(xmlData);
				this.flowTree.selectedItem.expand(true);
			}
		}
	}
	
	//\u6dfb\u52a0\u6d41\u7a0b\u6a21\u677f
	this.addTmp = function(){
		//\u8fd9\u4e2a\u5bf9\u8c61\u7528\u6765\u5b58\u653e\u65b0\u589e\u76ee\u5f55\u7684\u4fe1\u606f

		var arg = new Object();
		arg.operation = "add";
		
		arg.catalogId = this.flowTree.selectedItem.id;
		arg.catalogName = this.flowTree.selectedItem.name;
		arg.siblingNames = this.gatherSiblingNames(this.flowTree.selectedItem);
		arg.ownerAreaId = obj.areaId;
		arg.ownerAreaName = obj.areaName;
		
		result = OpenShowDlg("flowTemplate.jsp", 250, 330, arg);
		
		return result;
	}
	
	//\u6536\u96c6\u5b50\u8282\u70b9\u540d\u5b57\uff0c\u8fd4\u56de\u7c7b\u578b\u4e3a\u6570\u7ec4\uff0c\u7528\u6765\u6821\u9a8c\u91cd\u540d
	this.gatherSiblingNames = function(node){
		var childrenNames = new Array();
		
		var nodeParent;
		
		if(node.getParentItem() != null){
			nodeParent = node.getParentItem();
		}else{
			nodeParent = this.flowTree;
		}
		
		for(var i=0; i<nodeParent.items.length; i++){
			childrenNames.push(nodeParent.items[i].name);
		}
		
		return childrenNames;
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		//\u5982\u679c\u4e0d\u662f\u6d41\u7a0b\u76ee\u5f55\uff0c\u800c\u662f\u6a21\u677f\uff0c\u90a3\u662f\u4e0d\u884c\u7684
		if(this.flowTree.selectedItem.type != 1){
			var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findDefinitionsByPackageIdXml", this.flowTree.selectedItem.id);
			if(xmlData != "<items></items>"){
				ErrorHandle("<%=getI18NResource("designer.cannot_choose_template_has_versions")%>");
				return;
			}else{
				var result = new Object();
				
				var path = new Array();
				
				var pointer = this.flowTree.selectedItem;
				while(pointer.getParentItem() != null){
					path.push(pointer.name);
					pointer = pointer.getParentItem();
				}
				path.push(pointer.name);
				
				path.reverse();
				
				result.pathStr = path.join(" / ");
				result.tmpId = this.flowTree.selectedItem.id;
				
				window.returnValue = result;
				window.close();
			}
		}else{
			var flowTmp = this.addTmp();
			
			if(flowTmp == null){
				return;
			}
		
			var result = new Object();
			
			var path = new Array();
			
			var pointer = this.flowTree.selectedItem;
			while(pointer.getParentItem() != null){
				path.push(pointer.name);
				pointer = pointer.getParentItem();
			}
			path.push(pointer.name);
			
			path.reverse();
			
			path.push(flowTmp.name);
			
			result.pathStr = path.join(" / ");
			result.tmpId = flowTmp.packageId;
			
			window.returnValue = result;
			window.close();
		}
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
