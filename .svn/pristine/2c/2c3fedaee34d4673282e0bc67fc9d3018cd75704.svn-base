<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.error_exception_list")%> </title>
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
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="exceptionForm" id="exceptionForm" onsubmit="return false">
	<tr>
		<td width="90%">
			<ZTESOFT:treelist id="configTree" name="configTree" class="treelist" width="100%" height="160">
				<ZTESOFT:columns>
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.exception_activity")%>" propertyName="startActivityName" />
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.exception_catalog")%>" propertyName="reasonCatalogName" />
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.exception_reason")%>" propertyName="returnReasonName" />
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.return_activity")%>" propertyName="endActivityName" />
					<ZTESOFT:column display="false" propertyName="startModeName" />
					<ZTESOFT:column display="false" propertyName="startMode" />
					<ZTESOFT:column display="false" propertyName="reasonCatalogId" />
					<ZTESOFT:column display="false" propertyName="returnReasonId" />
					<ZTESOFT:column display="false" propertyName="startActivityId" />
					<ZTESOFT:column display="false" propertyName="endActivityId" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40">			
			<input type="button" class="button" id="cancelButton" value="\u5173\u95ed" onclick="window.close()" />
		</td>
	</tr>
	 <tr>
			<td colspan="2" align="center">
				<span class="star" id="remark"><%=getI18NResource("designer.remark")%></span>\uff1a<span id="when_priv"><%=getI18NResource("designer.exception_not_valid")%>
				 </span>
			</td>
		 </tr>
		 <tr>
			<td colspan="2"  align="center" ><span class="star" id="remark"><%=getI18NResource("designer.return_activity")%></span></td>			
		</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript" src="../../../public/script/i18N.js"></script>

<script language="javascript" >
   initPage();
   function initPage(){
   		var treelist = document.all.configTree;
   		var recObj = window.dialogArguments;
   		treelist.loadByData(recObj);
   		
   		recObj = null;
   }
</script>
