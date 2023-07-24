<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.set_flow_parameter")%> </title>
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
	<form name="parameterForm" id="parameterForm" onsubmit="return false">
	<tr>
		<td width="90%">
			<ZTESOFT:treelist id="paramTree" name="paramTree" class="treelist" width="100%" height="140">
				<ZTESOFT:columns>
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.parameter_name")%>" propertyName="paramName" />
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.data_type")%>" propertyName="dataType" />
					<ZTESOFT:column width="25%" display="true" displayText="<%=getI18NResource("designer.parameter_type")%>" propertyName="paramType" />
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.comment")%>" propertyName="description" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
		<td valign="middle" align="center">
			<input type="button" class="button_img" name="addButton" id="addButton" style="width:60px" value="<%=getI18NResource("designer.new")%>" onclick="parameterOperation.addParam();" />
			<br />
			<input type="button" class="button_img" name="editButton" id="editButton" style="width:60px" value="<%=getI18NResource("designer.edit")%>" onclick="parameterOperation.modifyParam();" />
			<br />
			<input type="button" class="button_img" name="deleteButton" id="deleteButton" style="width:60px" value="<%=getI18NResource("designer.delete")%>" onclick="parameterOperation.deleteParam();" />
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40">
			<input type="button" class="button" id="okButton" name="okButton" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="parameterOperation.submit();" />
			<input type="button" class="button" id="cancelButton" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="parameterOperation.cancel();" />
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
// commits: View or set flow parameters
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var parameterOperation = new ParameterOperation();
parameterOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
function ParameterOperation(){
	this.form = document.all.parameterForm;
	this.paramTree = document.all.paramTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){
		var obj = window.dialogArguments;
		
		//\u8fd9\u91cc\u662f\u6839\u636e\u4f20\u8fdb\u6765\u7684\u6d41\u7a0b\u5f62\u53c2\u6765\u521d\u59cb\u5316\u53c2\u6570\u5217\u8868		
		this.paramTree.loadByData(obj.formalParameters);
		
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
		result.formalParameters = new Array();
		
		//\u8fd9\u91cc\u7ec4\u88c5application
		for(var i=0; i<this.paramTree.items.length; i++){
			var param = new Object();
			param.paramName = this.paramTree.items[i].paramName;
			param.dataType = this.paramTree.items[i].dataType;
			param.paramType = this.paramTree.items[i].paramType;
			param.description = this.paramTree.items[i].description;
			
			result.formalParameters.push(param);
		}
		
		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
	
	//\u65b0\u589e\u53c2\u6570
	this.addParam = function(){
		var obj = new Object();
		obj.usedNames = this.getUsedNames(true);
		
		var newParam = OpenShowDlg("paramProperty.jsp", 270, 320, obj);
		if(newParam != null){
			var newNode = this.paramTree.createTreeNode();
			newNode.clone(newParam);
			this.paramTree.add(newNode);
		}
	}
	
	//\u4fee\u6539\u53c2\u6570
	this.modifyParam = function(){
		if(this.paramTree.selectedItem != null){
			var obj = new Object();
			obj.usedNames = this.getUsedNames(false);
			obj.paramItem = this.paramTree.selectedItem;
		
			var newParam = OpenShowDlg("paramProperty.jsp", 270, 320, obj);
			if(newParam != null){
				this.paramTree.selectedItem.clone(newParam);
				this.paramTree.selectedItem.refresh();
			}
		}
	}
	
	//\u5220\u9664\u53c2\u6570
	this.deleteParam = function(){
		if(this.paramTree.selectedItem != null){
			if(window.confirm("<%=getI18NResource("designer.confirm_delete_parameter")%>")){
				this.paramTree.selectedItem.remove();
			}
		}
	}
	
	//\u53d6\u5f97\u5f53\u524d\u5df2\u6709\u7684\u53c2\u6570\u540d\u79f0\u6570\u7ec4\uff0c\u7528\u4e8e\u9632\u6b62\u91cd\u590d\uff0cflag\u5982\u679c\u662ftrue\uff0c\u5c31\u628a\u9009\u4e2d\u9879\u4e5f\u5305\u542b\u8fdb\u53bb\uff0c\u5426\u5219\u4e0d\u5305\u542b
	this.getUsedNames = function(flag){
		var names = new Array();
		if(flag){
			for(var i=0; i<this.paramTree.items.length; i++){
				names.push(this.paramTree.items[i].paramName);
			}
		}else{
			for(var i=0; i<this.paramTree.items.length; i++){
				if(this.paramTree.items[i] != this.paramTree.selectedItem){
					names.push(this.paramTree.items[i].paramName);
				}
			}
		}
		
		return names;
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
