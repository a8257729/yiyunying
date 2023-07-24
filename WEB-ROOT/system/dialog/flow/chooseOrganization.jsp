<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.choose_org")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" />
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeListLoad.htc" />
	<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td width="100%">
			<ZTESOFT:treelist id="orgTree" name="orgTree" class="treelist" width="100%" height="260">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.org_name")%>" propertyName="orgName" />
					<ZTESOFT:column propertyName="orgId" />
					<ZTESOFT:column propertyName="areaId" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>		
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick='orgOperation.submit()'>
	  	<input type="button" class="button_blue" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="orgOperation.cancel()">
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
// ZTESoft corp. 2005-12-12
// Author : Xu.fei3
// commits: Choose an organization
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var session = window.dialogArguments;
var orgOperation = new OrgOperation();

/* \u521d\u59cb\u5316 */
orgOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
function OrgOperation(){
	this.orgTree = document.all.orgTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){	
		var _xmlData;
		
		_xmlData = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findOrgsXml");
		
		this.orgTree.loadByXML(_xmlData);
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Object();
		
		result.orgId = this.orgTree.selectedItem.orgId;	
		result.orgName = this.orgTree.selectedItem.orgName;
		result.areaId = this.orgTree.selectedItem.areaId;	
		
		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
