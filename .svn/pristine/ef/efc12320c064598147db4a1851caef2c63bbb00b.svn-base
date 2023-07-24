<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.variable_property_config")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<script language="javascript" src="../../../public/script/BSCommon.js"></script>
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
	<form name="variableForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.variable_name")%></td>
		<td class="td_grey">
			<input type="text" id="variableName" name="variableName" style="width:180px;" checkType="empty" /><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.data_type")%></td>
		<td class="td_grey">
			<select class="select_htc" id="dataType" name="dataType" style="width:180px;">
				<option value="STRING" selected>String</option>
				<option value="FLOAT">Float</option>
				<option value="INTEGER">Integer</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.initialize_value")%></td>
		<td class="td_grey">
			<input type="text" id="initialValue" name="initialValue" style="width:180px;" checkType="empty" /><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.comment")%></td>
		<td class="td_grey">
			<textarea cols=40 rows=4 style="width:180px;" id="description" name="description"></textarea>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick="variableOperation.submit();" />
			<input type="button" class="button" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="variableOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-09
// Author : Xu.fei3
// commits: View or set flow variable Properties
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var obj = window.dialogArguments;
var variableOperation = new VariableOperation();
variableOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function VariableOperation(){
	_extends(this, FormExt, '"variableForm"');
	
	//\u521d\u59cb\u5316

	this.init = function(){
		if(obj.variableItem != null){
			this.objectToForm(obj.variableItem);
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		//\u63d0\u4ea4\u4e4b\u524d\u5e94\u5f53\u5148\u4f5c\u57fa\u672c\u7684\u6821\u9a8c

		if(this.validator()){
			return;
		}
		
		for(var i=0; i<obj.usedNames.length; i++){
			if(this.form.variableName.value == obj.usedNames[i]){
				ErrorHandle("<%=getI18NResource("designer.variable_conflict")%>");
				return;
			}
		}
		
		var result = this.formToObject();
		
		window.returnValue = result;
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
