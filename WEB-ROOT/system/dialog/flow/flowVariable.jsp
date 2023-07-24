<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.set_flow_variable")%> </title>
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
	<form name="variableForm" id="variableForm" onsubmit="return false">
	<tr>
		<td width="90%">
			<ZTESOFT:treelist id="variableTree" name="variableTree" class="treelist" width="100%" height="140">
				<ZTESOFT:columns>
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.variable_name")%>" propertyName="variableName" />
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.data_type")%>" propertyName="dataType" />
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.initialize_value")%>" propertyName="initialValue" />
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.comment")%>" propertyName="description" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
		<td valign="middle" align="center">
			<input type="button" class="button_img" name="addButton" id="addButton" style="width:60px" value="<%=getI18NResource("designer.new")%>" onclick="variableOperation.addVariable();" />
			<br />
			<input type="button" class="button_img" name="editButton" id="editButton" style="width:60px" value="<%=getI18NResource("designer.edit")%>" onclick="variableOperation.modifyVariable();" />
			<br />
			<input type="button" class="button_img" name="deleteButton" id="deleteButton" style="width:60px" value="<%=getI18NResource("designer.delete")%>" onclick="variableOperation.deleteVariable();" />
		</td>
	</tr>
	<tr>
  	<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40">
			<input type="button" class="button" id="okButton" name="okButton" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="variableOperation.submit();" />
			<input type="button" class="button" id="cancelButton" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="variableOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-14
// Author : Xu.fei3
// commits: View or set flow variables
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var variableOperation = new VariableOperation();
variableOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
function VariableOperation(){
	this.form = document.all.variableForm;
	this.variableTree = document.all.variableTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){
		var obj = window.dialogArguments;
		
		//\u8fd9\u91cc\u662f\u6839\u636e\u4f20\u8fdb\u6765\u7684\u6d41\u7a0b\u53d8\u91cf\u6765\u521d\u59cb\u5316\u53d8\u91cf\u5217\u8868		
		this.variableTree.loadByData(obj.dataFields);
		
		if(obj.operation == "view"){
			this.form.addButton.disabled = true;
			this.form.editButton.disabled = true;
			this.form.deleteButton.disabled = true;
			this.form.okButton.disabled = true;
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Object();
		result.dataFields = new Array();
		
		//\u8fd9\u91cc\u7ec4\u88c5\u6d41\u7a0b\u53d8\u91cf\u6570\u7ec4
		for(var i=0; i<this.variableTree.items.length; i++){
			var variable = new Object();
			variable.variableName = this.variableTree.items[i].variableName;
			variable.dataType = this.variableTree.items[i].dataType;
			variable.initialValue = this.variableTree.items[i].initialValue;
			variable.description = this.variableTree.items[i].description;
			
			result.dataFields.push(variable);
		}
		
		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
	
	//\u65b0\u589e\u53d8\u91cf
	this.addVariable = function(){
		var obj = new Object();
		obj.usedNames = this.getUsedNames(true);
		
		var newVariable = OpenShowDlg("variableProperty.jsp", 300, 320, obj);
		if(newVariable != null){
			var newNode = this.variableTree.createTreeNode();
			newNode.clone(newVariable);
			this.variableTree.add(newNode);
		}
	}
	
	//\u4fee\u6539\u53d8\u91cf
	this.modifyVariable = function(){
		if(this.variableTree.selectedItem != null){
			var obj = new Object();
			obj.usedNames = this.getUsedNames(false);
			obj.variableItem = this.variableTree.selectedItem;
			
			var newParam = OpenShowDlg("variableProperty.jsp", 300, 320, obj);
			if(newParam != null){
				this.variableTree.selectedItem.clone(newParam);
				this.variableTree.selectedItem.refresh();
			}
		}
	}
	
	//\u5220\u9664\u53d8\u91cf
	this.deleteVariable = function(){
		if(this.variableTree.selectedItem != null){
			if(window.confirm("<%=getI18NResource("designer.confirm_delete_variable")%>")){
				this.variableTree.selectedItem.remove();
			}
		}
	}
	
	//\u53d6\u5f97\u5f53\u524d\u5df2\u6709\u7684\u53d8\u91cf\u540d\u79f0\u6570\u7ec4\uff0c\u7528\u4e8e\u9632\u6b62\u91cd\u590d\uff0cflag\u5982\u679c\u662ftrue\uff0c\u5c31\u628a\u9009\u4e2d\u9879\u4e5f\u5305\u542b\u8fdb\u53bb\uff0c\u5426\u5219\u4e0d\u5305\u542b
	this.getUsedNames = function(flag){
		var names = new Array();
		if(flag){
			for(var i=0; i<this.variableTree.items.length; i++){
				names.push(this.variableTree.items[i].variableName);
			}
		}else{
			for(var i=0; i<this.variableTree.items.length; i++){
				if(this.variableTree.items[i] != this.variableTree.selectedItem){
					names.push(this.variableTree.items[i].variableName);
				}
			}
		}
		
		return names;
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
