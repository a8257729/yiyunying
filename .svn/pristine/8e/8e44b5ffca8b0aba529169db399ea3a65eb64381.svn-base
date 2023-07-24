<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title> <%=getI18NResource("designer.prompt")%> </title>
  <link rel="stylesheet" type="text/css" href="../../public/css/style.css">
	<script language="javascript" src="../../public/script/helper.js"></script>
	<script language="javascript" src="../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../public/script/FormExt.js"></script>
</head>
<body style="margin:0; overflow:hidden" background="../../images/icon/errorwin_bg.jpg">
<table width="100%" height="153" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="110" rowspan="2" align="center">
			<img id="imgD" src="../../images/icon/icon_pop_prompt.gif">
		</td>
		<td height="80" align="center" valign="middle">
			<table><tr><td id="contentD" align="center" valign="middle" style="word-break:break-all">&nbsp;</td></tr></table>
		</td>
	</tr>
	<tr>
		<td height="30" align="center">
			<input type="button" name="yesButton" id="yesButton" class="button_img" value="<%=getI18NResource("designer.yes")%>" onClick="submitYes()" style="width:60px;" />
			<input type="button" name="noButton" id="noButton" class="button_img" value="<%=getI18NResource("designer.no")%>"	onClick="submitNo()" style="width:60px;" />
			<input type="button" name="cancelButton" id="cancelButton" class="button_img" value="<%=getI18NResource("designer.cancel")%>" onClick="window.close()" style="width:60px;" />
		</td>
	</tr>
</table>
</div>

</body>
</html>
<script language="javascript">
<!--

var paramObj = window.dialogArguments;

if(paramObj.title) document.title = paramObj.title;
document.all.contentD.innerText = paramObj.content;

//////////////////////////////////////////////////////////////////////////
function submitYes(){
	window.returnValue = "YES";
	window.close();
}

function submitNo(){
	window.returnValue = "NO";
	window.close();
}

//-->
</script>
