<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.parameter_property_config")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../public/css/style.css">
	<script language="javascript" src="../../public/script/helper.js"></script>
	<script language="javascript" src="../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../public/script/FormExt.js"></script>
	<script language="javascript" src="../../public/script/BSCommon.js"></script>
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
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="paramForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt" id="para_name"><%=getI18NResource("designer.parameter_name")%></td>
		<td class="td_grey">
			<input type="text" id="paramName" name="paramName" style="width:180px;" checkType="empty" /><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="data_type"><%=getI18NResource("designer.data_type")%></td>
		<td class="td_grey">
			<select class="select_htc" id="dataType" name="dataType" style="width:180px;">
				<option value="STRING" selected>String</option>
				<option value="FLOAT">Float</option>
				<option value="INTEGER">Integer</option>
				<option value="REFERENCE">Reference</option>
				<option value="DATETIME">DateTime</option>
				<option value="BOOLEAN">Boolean</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="para_type"><%=getI18NResource("designer.parameter_type")%></td>
		<td class="td_grey">
			<select class="select_htc" id="paramType" name="paramType" style="width:180px;">
				<option value="IN" id="input_id" selected><%=getI18NResource("designer.input")%></option>
				<option value="OUT" id="output_id"><%=getI18NResource("designer.output")%></option>
				<option value="INOUT" id="in_and_out_id"><%=getI18NResource("designer.input_output")%></option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="bind_rela_id"><%=getI18NResource("designer.binding_relation")%></td>
		<td class="td_grey">
			<input type="text" id="actualParameter" name="actualParameter" style="width:160px;" readonly /><input type="button" id="chooseBindingImg" name="chooseBindingImg" class="button_pop" onclick="paramOperation.chooseBinding();" />
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="isDisplay_id"><%=getI18NResource("designer.display_or_not")%></td>
		<td class="td_grey">
			<select class="select_htc" id="isDisplay" name="isDisplay" style="width:180px;">
				<option value="FALSE" id="nodisplay_id" selected><%=getI18NResource("designer.no")%></option>
				<option value="TRUE" id="display_id"><%=getI18NResource("designer.yes")%></option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="description_id"><%=getI18NResource("designer.comment")%></td>
		<td class="td_grey">
			<textarea cols=40 rows=4 style="width:180px;" id="description" name="description"></textarea>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick="paramOperation.submit();" />
			<input type="button" class="button" id="canlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="paramOperation.cancel();" />
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
// commits: View or set tache param Properties
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var obj = window.dialogArguments;
var paramOperation = new ParamOperation();
paramOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function ParamOperation(){
	_extends(this, FormExt, '"paramForm"');
	
	//\u521d\u59cb\u5316

	this.init = function(){
		if(obj.treeItem != null){
			this.objectToForm(obj.treeItem);
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		//\u63d0\u4ea4\u4e4b\u524d\u5e94\u5f53\u5148\u4f5c\u57fa\u672c\u7684\u6821\u9a8c

		if(this.validator()){
			return;
		}
		
		for(var i=0; i<obj.usedNames.length; i++){
			if(obj.usedNames[i] == this.form.paramName.value){
				ErrorHandle("<%=getI18NResource("designer.parameter_conflict")%>");
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
	
	//\u9009\u62e9\u7ed1\u5b9a\u5173\u7cfb
	this.chooseBinding = function(){
		var dialogParam = new Object();
		dialogParam.dataFields = window.dialogArguments.dataFields;
		dialogParam.formalParameters = window.dialogArguments.formalParameters;
		
		var bindingRelation = OpenShowDlg("bindingRelation.jsp", 300, 300, dialogParam);
		
		if(bindingRelation != null){
			document.all.actualParameter.value = bindingRelation;
		}
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
