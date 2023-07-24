<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.transition_config")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../public/css/style.css">
<script language="javascript" src="../../public/script/helper.js"></script>
<script language="javascript" src="../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../public/script/FormExt.js"></script>
<script language="javascript" src="../../public/script/title.js"></script>
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
	<form name="lineForm" id="lineForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.transition_name")%></td>
		<td class="td_grey" colspan="3">
			<input type="hidden" id="lineId" name="lineId" />
			<input type="text" id="lineName" name="lineName" style="width:300px;" />
		</td>
	</tr>
	<tr id="tr1" valign="top">
		<td colspan=4>
			<ZTESOFT:treelist id="componentTree" name="componentTree" height="150" width="100%" class="treelist" onItemDblClick="lineOperation.setBuziObj()">
				<ZTESOFT:columns>
					<ZTESOFT:column width="70%" display="true" displayText="<%=getI18NResource("designer.business_component")%>" propertyName="BUSINESS_OBJ_NAME" />
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.return_type")%>" propertyName="DTO_NAME" />
					<ZTESOFT:column display="false" propertyName="ID" />
					<ZTESOFT:column display="false" propertyName="type" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr id="tr2">
		<td width="120" class="td_blue_txt"><%=getI18NResource("designer.left_operation")%></td>
		<td width="80" class="td_grey"><%=getI18NResource("designer.operator")%></td>
		<td width="80" class="td_grey"><%=getI18NResource("designer.right_operation")%></td>
		<td width="40" class="td_grey">&nbsp;</td>
	</tr>
	<tr id="tr3">
		<td width="120" class="td_blue_txt">
			<input type="text" name="leftArea" checkType="empty" value="" readonly>
		</td>
		<td class="td_grey">
			<select name="operTypeSel" class="select_htc" style="width:80px;">
				<option value="="><%=getI18NResource("designer.equal")%></option>
				<option value=">="><%=getI18NResource("designer.greater_equal")%></option>
				<option value=">"><%=getI18NResource("designer.greater")%></option>
				<option value="<="><%=getI18NResource("designer.less_equal")%></option>
				<option value="<"><%=getI18NResource("designer.less")%></option>
				<option value="!="><%=getI18NResource("designer.not_equal")%></option>
				<option value="@"><%=getI18NResource("designer.contain")%></option>
				<option value="!@"><%=getI18NResource("designer.not_contain")%></option>
			</select>
		</td>
		<td class="td_grey">
			<input type="text" name="rightArea" checkType="empty" value="" onkeydown='lineOperation.ableAddBtn()'>
		</td>
		<td class="td_grey">
			<input type="button" name="addBtn" id="add_btn_id" class="button_img" value="<%=getI18NResource("designer.add")%>" onclick="lineOperation.addCond()">
		</td>
	</tr>
	<tr>
		<td colspan="4">
		<script>showTitle("<%=getI18NResource("designer.use_condition")%>")</script>
		</td>
	</tr>
	<tr>
		<td class="td_grey" colspan=4>
			<table width="100%"	border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_grey" colspan="2" align="center" height="40" valign="middle">
						<input type="button" style="width:60px" name="and" id="and" class="button_img" value="<%=getI18NResource("designer.and_operator")%>" onclick='lineOperation.operAnd()' />
						<input type="button" style="width:60px" name="or" id="or" class="button_img" value="<%=getI18NResource("designer.or_operator")%>" onclick='lineOperation.operOr()' />
						<input type="button" style="width:60px" name="leftParentheses" id="leftParentheses" class="button_img" value="(" onclick='lineOperation.operLeft()' />
						<input type="button" style="width:60px" name="rightParentheses" id="rightParentheses" class="button_img" value=")" onclick='lineOperation.operRight()' />
					</td>
				</tr>
				<tr>
					<td class="td_blue_txt"><%=getI18NResource("designer.transition_operation")%></td>
					<td class="td_grey">
						<textarea cols=40 rows=6 style="width:300px;" id="condition" name="condition"></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr	id='otherCondTr' style='display:none'>
		<td class="td_grey" colspan=4>
			<table width="100%"	border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_grey" colspan=2 id="condition_list_id"><%=getI18NResource("designer.condition_list")%></td>
				</tr>
				<tr>
					<td class="td_grey" width='85%' >
						<ZTESOFT:treelist id="condTv" height="70" width="100%"	 class="treelist" showHead= "false" >
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.condition")%>" propertyName="cond" />
								<ZTESOFT:column display="false" propertyName="ID" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</td>
					<td width='15%'>
						<input type="button" name="delete" id="delete_id" class="button_img" value="<%=getI18NResource("designer.delete")%>" onclick="lineOperation.delCond()" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="4" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="4" height="40" valign="bottom">
			<input type="button" class="button" id="okButton" name="okButton" value="<%=getI18NResource("designer.ok")%>" onclick="lineOperation.submit();" />
			<input type="button" class="button" id="cancelButton" name="cancelButton" value="<%=getI18NResource("designer.cancel")%>" onclick="lineOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-07-12
// Author : Xu.fei3
// commits: View or set line Properties
/////////////////////////////////////////////////////

_import("BSCommon");
_import("ContextMenu");

_import("designer.js.constant");

_import("designer.js.core.abstractElement");
_import("designer.js.core.transition");

/* \u5168\u5c40\u53d8\u91cf */
var lineOperation = new LineOperation();

/* \u521d\u59cb\u5316 */
ExecWait("lineOperation.init()");

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function LineOperation(){
	_extends(this,BSCommon,'"lineForm"');
	
	this.form = document.all.lineForm;
	this.treelist = document.all.componentTree;
	var param = window.dialogArguments;
	
	this.kind = "";				//\u5f53\u524d\u7684\u6761\u4ef6\u7c7b\u578b

	
	//\u521d\u59cb\u5316

	this.init = function(){		
		this.form.lineId.value = param.transition.id;
		this.form.lineName.value = param.transition.name;
		this.form.condition.value = param.transition.condition || "";
		
		var data = new Array();
		data[0] = new Object();
		data[0].BUSINESS_OBJ_NAME = "<%=getI18NResource("designer.business_component")%>";
		
		data[1] = new Object();
		data[1].BUSINESS_OBJ_NAME = "<%=getI18NResource("designer.flow_variable")%>";
		
		data[2] = new Object();
		data[2].BUSINESS_OBJ_NAME = "<%=getI18NResource("designer.flow_former_parameter")%>";
		
		var objXML;
		try{
			this.clearParam();
			this.addParam(2, "BUSINESS_CATALOG_CODE", "flow_condition");
			objXML = this.callRemoteFunctionQuery("qryBisiObjByCtagCode");
			
			data[0].children = objXML;
			this.form.addBtn.disabled = true;
			
			if(param.dataFields.length > 0){
				data[1].children = new Array();
				for(var i=0; i<param.dataFields.length; i++){
					data[1].children[i] = new Object();
					data[1].children[i].BUSINESS_OBJ_NAME = param.dataFields[i].variableName;
					data[1].children[i].DTO_NAME = param.dataFields[i].dataType;
					data[1].children[i].type = "variable";
				}
			}
			
			if(param.formalParameters.length > 0){
				data[2].children = new Array();
				for(var i=0; i<param.formalParameters.length; i++){
					data[2].children[i] = new Object();
					data[2].children[i].BUSINESS_OBJ_NAME = param.formalParameters[i].paramName;
					data[2].children[i].DTO_NAME = param.formalParameters[i].dataType;
					data[2].children[i].type = "parameter";
				}
			}
		}catch(ex){
			ErrorHandle(ex); 
			return;
		}
		this.treelist.loadByData(data);
		
		if(param.operation == "view"){
			this.form.okButton.style.display = "none";
			this.form.cancelButton.value = "<%=getI18NResource("designer.close_window")%>";
			document.all.tr1.style.display = "none";
			document.all.tr2.style.display = "none";
			document.all.tr3.style.display = "none";
			this.form.and.style.display = "none";
			this.form.or.style.display = "none";
			this.form.leftParentheses.style.display = "none";
			this.form.rightParentheses.style.display = "none";
			this.form.lineName.disabled = true;
			this.form.condition.disabled = true;
		}
	}
	
	//\u8bbe\u7f6e\u6761\u4ef6\u8868\u8fbe\u5f0f\u5de6\u8fb9\u7684\u4e1a\u52a1\u7ec4\u4ef6\u540d\u79f0
	this.setBuziObj = function(){
		if(this.treelist.selectedItem.DTO_NAME != null){
			this.form.leftArea.value =	this.treelist.selectedItem.BUSINESS_OBJ_NAME;
			
			if((this.treelist.selectedItem.type == null) || (this.treelist.selectedItem.type == "")){
				this.kind = "component";
			}else{
				this.kind = this.treelist.selectedItem.type;
			}
		}
		
		this.form.addBtn.disabled = false;

		if(this.treelist.selectedItem.DTO_NAME == "boolean"){
			this.form.operTypeSel.selectedIndex = 0;
			this.form.operTypeSel.disabled = true;
		}else{
			this.form.operTypeSel.disabled = false;
		}
	}
	
	//\u589e\u52a0\u6761\u4ef6\u8868\u8fbe\u5f0f

	this.addCond = function(){
		var rightAreaValue = this.form.rightArea.value;
		
		if((rightAreaValue == null) || (rightAreaValue == "")){
			alert("<%=getI18NResource("designer.right_expression_cannot_empty")%>");
			return;
		}
		
		switch(this.treelist.selectedItem.DTO_NAME){
			case "boolean":{
				if((rightAreaValue != "true") && (rightAreaValue != "false")){
					ErrorHandle("<%=getI18NResource("designer.bool_variable_restrict")%>");
					return;
				}
				
				if(this.getSelText(this.form.operTypeSel) != "<%=getI18NResource("designer.equal")%>"){
					ErrorHandle("<%=getI18NResource("designer.bool_operator_canbut_equal")%>");
					return;
				}
				break;
			}
			case 'int':
			case 'long':{
				if(!((rightAreaValue > 0)	&& (rightAreaValue%1 == 0))){
					if(!(ErrorHandle("<%=getI18NResource("designer.right_operation_be_int")%>", 1, '', true))){
						return;
					}
				}
				break;
			}
		}
		
		var operator = this.form.operTypeSel[this.form.operTypeSel.selectedIndex].value;

		var tempStr;
		switch(this.kind){
			case "component":{
				tempStr = "{$" + this.form.leftArea.value + "$" + operator + this.form.rightArea.value + "}";
				break;
			}
			case "variable":{
				tempStr = "/Process/" + this.form.leftArea.value + operator + this.form.rightArea.value;
				break;
			}
			case "parameter":{
				tempStr = "/Process/" + this.form.leftArea.value + operator + this.form.rightArea.value;
				break;
			}
		}
		
		this.form.condition.focus();
		document.selection.createRange().text = tempStr;
		//\u6dfb\u52a0\u5b8c\uff0c\u6309\u94ae\u7f6e\u7070\uff0c\u53f3\u8fb9\u8868\u8fbe\u5f0f\u6e05\u7a7a
		this.form.addBtn.disabled = true;
		this.form.rightArea.value = "";
	}
	
	this.operAnd = function(){
		this.form.condition.focus();
		document.selection.createRange().text = "<%=getI18NResource("designer.and")%>";
	}
	
	this.operOr	= function(){
		this.form.condition.focus();
		document.selection.createRange().text = "<%=getI18NResource("designer.or")%>";
	}
	
	this.operLeft = function(){
		this.form.condition.focus();
		document.selection.createRange().text = "(";
	}
	
	this.operRight = function(){
		this.form.condition.focus();
		document.selection.createRange().text = ")";
	}
	
	this.operCond = function(){
		var ctl1 = document.all.otherCondTr;
		if(ctl1.style.display == ""){
			ctl1.style.display = "none";
			this.form.condListBtn.value = "<%=getI18NResource("designer.show_condition_list")%>";
		}else{
			ctl1.style.display = "";
			this.form.condListBtn.value = "<%=getI18NResource("designer.hide_condition_list")%>";
		}
	}
	
	//\u6821\u9a8c\uff0c\u8be5\u600e\u4e48\u505a\u5462\uff1f

	this.validate = function(){
		ErrorHandle("<%=getI18NResource("designer.condition_verify_pass")%>");
	}
	
	//\u6fc0\u6d3b\u6dfb\u52a0\u6309\u94ae

	this.ableAddBtn = function(){
		this.form.addBtn.disabled = false;
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		var transition = new Transition();
		transition.id = this.form.lineId.value;
		transition.name = this.form.lineName.value;
		transition.condition = this.form.condition.value;
		transition.extendedAttributes = window.dialogArguments.transition.extendedAttributes;
		
		window.returnValue = transition;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
	
	//\u9009\u62e9\u53d8\u91cf
	this.chooseVariable = function(){
		var param = new Object();
		param.dataFields = obj.dataFields;
		param.formalParameters = obj.formalParameters;
		var variable = OpenShowDlg("chooseVariable.jsp", 180, 240, param);
		
		if(variable != null){
			this.form.condition.value += variable.condition;
		}
	}
	
	//\u9009\u62e9\u4e1a\u52a1\u7ec4\u4ef6
	this.chooseComponent = function(){
		var param = new Object();
		param.dataFields = obj.dataFields;
		param.formalParameters = obj.formalParameters;
		var component = OpenShowDlg("chooseComponent.jsp", 460, 600, param);
		
		if(component != null){
			this.form.condition.value += component.condition;
		}
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
