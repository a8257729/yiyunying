<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.binding_relation_choose")%> </title>
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
   <td height="1" colspan="4" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="relationForm" id="relationForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt" id="bind_relation_id"><%=getI18NResource("designer.binding_relation_choose")%></td>
	</tr>
	<tr valign="top">
		<td>
			<ZTESOFT:treelist id="componentTree" name="componentTree" height="150" width="100%" class="treelist">
				<ZTESOFT:columns>
					<ZTESOFT:column width="70%" display="true" displayText="<%=getI18NResource("designer.binding_name")%>" propertyName="bindingName" />
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.return_type")%>" propertyName="returnType" />
					<ZTESOFT:column display="false" propertyName="ID" />
					<ZTESOFT:column display="false" propertyName="type" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" id="okButton" name="okButton" value="<%=getI18NResource("designer.ok")%>" onclick="relationOperation.submit();" />
			<input type="button" class="button" id="cancelButton" value="<%=getI18NResource("designer.cancel")%>" onclick="relationOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-11-16
// Author : Xu.fei3
// commits: choose variables or paramaters
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var relationOperation = new RelationOperation();

/* \u521d\u59cb\u5316 */
relationOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function RelationOperation(){	
	this.form = document.all.relationForm;
	this.treelist = document.all.componentTree;
	var param = window.dialogArguments;
	
	this.kind = "";				//\u5f53\u524d\u7684\u6761\u4ef6\u7c7b\u578b

	
	//\u521d\u59cb\u5316

	this.init = function(){
		var data = new Array();
		data[0] = new Object();
		data[0].bindingName = "<%=getI18NResource("designer.system_parameter")%>";
		
		data[1] = new Object();
		data[1].bindingName = "<%=getI18NResource("designer.flow_former_parameter")%>";
		
		if(param.dataFields.length > 0){
			data[0].children = new Array();
			for(var i=0; i<param.dataFields.length; i++){
				data[0].children[i] = new Object();
				data[0].children[i].bindingName = param.dataFields[i].variableName;
				data[0].children[i].returnType = param.dataFields[i].dataType;
				data[0].children[i].type = "variable";
			}
		}
		
		if(param.formalParameters.length > 0){
			data[1].children = new Array();
			for(var i=0; i<param.formalParameters.length; i++){
				data[1].children[i] = new Object();
				data[1].children[i].bindingName = param.formalParameters[i].paramName;
				data[1].children[i].returnType = param.formalParameters[i].dataType;
				data[1].children[i].type = "parameter";
			}
		}
		
		this.treelist.loadByData(data);
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		if((this.treelist.selectedItem.type == null) || (this.treelist.selectedItem.type == "")){
			ErrorHandle("<%=getI18NResource("designer.cannot_select_catalog")%>");
			return;
		}
		
		var result = "/Process/" + this.treelist.selectedItem.bindingName;
				
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
