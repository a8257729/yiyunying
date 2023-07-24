<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.control_node_config")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../public/css/style.css">
<script language="javascript" src="../../public/script/helper.js"></script>
<script language="javascript" src="../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../public/script/FormExt.js"></script>
<!-- TemplBeginEditable name="head" -->

<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../public/script/topTitle.js"></script>

<table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="controlForm" id="controlForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt" id="way_in_id"><%=getI18NResource("designer.in_mode")%></td>
		<td class="td_grey">
			<select style="width:130px" class="select_htc" id="joinType" name="joinType">
				<option value="JoinAnd" selected id="bingxing_id"><%=getI18NResource("designer.parallel")%></option>
				<option value="JoinXor" id="choice_id"><%=getI18NResource("designer.select")%></option>
			</select>
			<input type="hidden" id="controlId" name="controlId" />
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="way_out_id"><%=getI18NResource("designer.out_mode")%></td>
		<td class="td_grey">
			<select style="width:130px" class="select_htc" id="splitType" name="splitType">
				<option value="SplitAnd" selected id="bingxing2_id"><%=getI18NResource("designer.parallel")%></option>
				<option value="SplitXor" id="choice2_id"><%=getI18NResource("designer.select")%></option>
			</select>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" id="okbtn" value="<%=getI18NResource("designer.ok")%>" id="okButton" name="okButton" onclick="controlOperation.submit();" />
			<input type="button" class="button" id="cnlbtn" value="<%=getI18NResource("designer.cancel")%>" onclick="controlOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-10-21
// Author : Xu.fei3
// commits: Set control node Properties
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var controlOperation = new ControlOperation();
controlOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function ControlOperation(){
	this.form = document.all.controlForm;
	var obj;
	
	this.init = function(){
		obj = window.dialogArguments;
		
		this.form.controlId.value = obj.activity.id;
		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f
		if(obj.activity.transitionRestrictions.length > 0){
			if(obj.activity.transitionRestrictions[0].joinType == "AND"){
				this.form.joinType.selectedIndex = 0;
			}else{
				this.form.joinType.selectedIndex = 1;
			}
			
			if(obj.activity.transitionRestrictions[0].splitType == "AND"){
				this.form.splitType.selectedIndex = 0;
			}else{
				this.form.splitType.selectedIndex = 1;
			}
		}
		
		if(obj.operation == "view"){
			this.form.joinType.disabled = true;
			this.form.splitType.disabled = true;
			this.form.okButton.disabled = true;
		}
	}
	
	this.submit = function(){		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f
		if(obj.activity.transitionRestrictions.length == 0){
			obj.activity.transitionRestrictions[0] = new Object();
		}
		
		if(this.form.joinType.selectedIndex == 0){
			obj.activity.transitionRestrictions[0].joinType = "AND";
		}else{
			obj.activity.transitionRestrictions[0].joinType = "XOR";
		}
		
		if(this.form.splitType.selectedIndex == 0){
			obj.activity.transitionRestrictions[0].splitType = "AND";
		}else{
			obj.activity.transitionRestrictions[0].splitType = "XOR";
		}
		
		window.returnValue = obj;
		window.close();
	}
	
	this.cancel = function(){
		window.close();
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
