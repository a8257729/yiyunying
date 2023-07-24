<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.view_occupied_time")%> </title>
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
		<td width="100%">
			<ZTESOFT:treelist id="timeTree" name="timeTree" class="treelist" width="100%" height="260">
				<ZTESOFT:columns>
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.version")%>" propertyName="version" />
					<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("designer.effect_time")%>" propertyName="validFromDate" />
					<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("designer.expire_time")%>" propertyName="validToDate" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>		
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" id="close_id" value="<%=getI18NResource("designer.close")%>" onclick="window.close()">
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
<!--
document.all.timeTree.loadByData(window.dialogArguments);
-->
</script>
