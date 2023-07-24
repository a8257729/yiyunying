<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.flow_catalog")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<!-- TemplBeginEditable name="head" -->

<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>

<table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="catalogForm" id="catalogForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.flow_catalog_name")%></td>
		<td class="td_grey">
			<input type="hidden" id="areaId" name="areaId" />
			<input type="hidden" id="parentId" name="parentId" />
			<input type="hidden" id="pathCode" name="pathCode" />
			<input type="hidden" id="packageCatalogType" name="packageCatalogType" />
			<input type="hidden" id="catalogId" name="catalogId" />
			<input type="text" id="catalogName" name="catalogName" style="width:200px;" /><font color="red">*</font>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40" valign="bottom">
			<input type="button" class="button" id="okBtn" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="catalogOperation.submit();" />
			<input type="button" class="button" id="cnlBtn" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="catalogOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-15
// Author : Xu.fei3
// commits: View or set flow catalog Properties
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var catalogOperation = new CatalogOperation();

/* \u521d\u59cb\u5316 */
catalogOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u6d41\u7a0b\u76ee\u5f55\u5c5e\u6027\u64cd\u4f5c

function CatalogOperation(){
	this.form = document.all.catalogForm;
	var obj = window.dialogArguments;
	
	//\u521d\u59cb\u5316

	this.init = function(){
		switch(obj.operation){
			case "add":{
				this.form.areaId.value = obj.areaId;
				this.form.parentId.value = obj.parentId;
				break;
			}
			case "update":{
				var catalog = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackageCatalogById", obj.catalogId);
				
				this.form.areaId.value = obj.areaId;
				this.form.parentId.value = catalog.parentId;
				this.form.catalogId.value = catalog.catalogId;
				this.form.catalogName.value = catalog.catalogName;
				this.form.pathCode.value = catalog.pathCode;
				this.form.packageCatalogType.value = catalog.packageCatalogType || 0;
			}
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		if(Trim(this.form.catalogName.value) == ""){
			alert("<%=getI18NResource("designer.catalog_name_cannot_empty")%>");
			return;
		}
		
		for(var i=0; i<window.dialogArguments.siblingNames.length; i++){
			if(this.form.catalogName.value == window.dialogArguments.siblingNames[i]){
				alert("<%=getI18NResource("designer.flow_catalog_conflict")%>");
				return;
			}
		}
		
		var newCatalog = new Object();
		newCatalog.areaId = this.form.areaId.value;
		newCatalog.parentId = this.form.parentId.value || 0;
		newCatalog.catalogId = this.form.catalogId.value || 0;
		newCatalog.catalogName = this.form.catalogName.value;

		newCatalog.packageCatalogType = this.form.packageCatalogType.value || 0;
		newCatalog.pathCode = this.form.pathCode.value || 0;
		
		if(obj.operation == "add"){
			newCatalog = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "addPackageCatalog", newCatalog);
		}else{
			newCatalog = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "updatePackageCatalog", newCatalog);
		}
		
		window.returnValue = newCatalog;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a

</script>
