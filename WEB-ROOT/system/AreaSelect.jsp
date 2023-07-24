<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>

<!-- TemplBeginEditable name="doctitle" -->
<title>选择区域</title>

<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../public/css/style.css">
	<script language="javascript" src="../public/script/helper.js"></script>
	<script language="javascript" src="../public/script/XmlInter.js"></script>
	<script language="javascript" src="../public/script/FormExt.js"></script>

<!-- TemplBeginEditable name="head" -->
<?XML:NAMESPACE PREFIX="ZTESOFT" />
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../public/htc/TreeList/TreeList.htc" />

<!-- TemplEndEditable -->
</head>
<body style="width:100%; height:100%; overflow:auto">

<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../public/script/topTitle.js"></script>
<TABLE width="100%"  border="0" cellpadding="2" cellspacing="1">
    <TR valign="top">
      <TD >
				<ZTESOFT:treelist id="areaTree" class="treelist" height="260" width="100%">
					<ZTESOFT:columns>
						<ZTESOFT:column width="100%" display="true" displayText='区域名称' propertyName="areaName" />
						<ZTESOFT:column display="false" propertyName="areaId" />
					</ZTESOFT:columns>
				</ZTESOFT:treelist>
			</TD>
    </tr>
    <tr>
      <TD >
 			<table width="100%" border="0" cellspacing="1" cellpadding="2">
			<tr>
				<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
			  </tr>
			  <tr>
			  <td class="td_grey" align="center" colspan="2" height="40">
			<input class="button_img" type="button" id="okButton" name="okButton" value='确定' onclick="areaSelectOper.submit();" />
			<input class="button_img" type="button" id="cancelButton" name="cancelButton" value='取消' onclick="areaSelectOper.cancel();" />
			  </td>
				</tr>
				  <tr>
					<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
				  </tr>
				</form>
			</table>
	    </TD>
  </TR>
  </TABLE>
</body>
</html>
<script language="JScript" >
	<%@ include file="js/AreaSelect.js"%>
</script>
