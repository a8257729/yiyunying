<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.choose_tache")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../public/css/style.css">
	<script language="javascript" src="../../public/script/helper.js"></script>
	<script language="javascript" src="../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" />
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../public/htc/TreeList/TreeList.htc" />
	<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
  	<tr>
		<td width="100%">		
			<ZTESOFT:treelist id="tacheTree" name="tacheTree" class="treelist" width="100%" height="260">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.tache_name")%>" propertyName="name" />
					<ZTESOFT:column propertyName="id" />					
				</ZTESOFT:columns>
			</ZTESOFT:treelist>		
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick='tacheOperation.submit()'  NAME='btnQry'>
	  	<input name="cancelButton" type="button" id="cnlBtn" class="button_blue" value="<%=getI18NResource("designer.cancel")%>" onclick="tacheOperation.cancel()">
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
// ZTESoft corp. 2005-11-06
// Author : Xu.fei3
// commits: Choose a existing tache
/////////////////////////////////////////////////////

var tacheOperation = new TacheOperation();
tacheOperation.init();

function TacheOperation(){
	this.tacheTree = document.all.tacheTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){	
		var taches = window.dialogArguments;
		this.tacheTree.loadByData(taches);

		if(this.tacheTree.items.length == 0){
			ErrorHandle("<%=getI18NResource("designer.first_activity_cannot_be_participant_tache")%>");
			window.close();
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		var result;
		
		result = new Object();
		result.name = this.tacheTree.selectedItem.name;
		result.id = this.tacheTree.selectedItem.id;

		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
