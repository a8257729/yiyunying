<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.combine_condition_config")%> </title>
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
	<form name="relationForm" id="relationForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt" id="way_in_id"><%=getI18NResource("designer.in_mode")%></td>
		<td class="td_grey">
			<select style="width:130px" class="select_htc" id="joinType" name="joinType">
				<option value="JoinAnd" selected id="bingxing_0"><%=getI18NResource("designer.parallel")%></option>
				<option value="JoinXor" id="select_0"><%=getI18NResource("designer.select")%></option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt" id="way_out_id"><%=getI18NResource("designer.out_mode")%></td>
		<td class="td_grey">
			<select style="width:130px" class="select_htc" id="splitType" name="splitType">
				<option value="SplitAnd" selected id="bingxing_1"><%=getI18NResource("designer.parallel")%></option>
				<option value="SplitXor" id="select_1"><%=getI18NResource("designer.select")%></option>
			</select>
			<input type="hidden" id="relationId" name="relationId" />
			<input type="hidden" id="relationName" name="relationName" />
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" name="okButton" id="okButton" value="<%=getI18NResource("designer.ok")%>" onclick="relationOperation.submit();" />
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
// ZTESoft corp. 2005-12-14
// Author : Xu.fei3
// commits: Set relation Properties in a parallel
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var relationOperation = new RelationOperation();
relationOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function RelationOperation(){
	this.form = document.all.relationForm;
	var obj;
	
	this.init = function(){
		obj = window.dialogArguments;
		
		this.form.relationId.value = obj.id;
		this.form.relationName.value = obj.name;
		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f
		if(obj.transitionRestrictions.length > 0){
			if(obj.transitionRestrictions[0].joinType == "AND"){
				this.form.joinType.selectedIndex = 0;
			}else{
				this.form.joinType.selectedIndex = 1;
			}
			
			if(obj.transitionRestrictions[0].splitType == "AND"){
				this.form.splitType.selectedIndex = 0;
			}else{
				this.form.splitType.selectedIndex = 1;
			}
		}
		
		if(obj.operation == "view"){
			this.form.okButton.style.display = "none";
			this.form.joinType.disabled = true;
			this.form.splitType.disabled = true;
		}
	}
	
	this.submit = function(){
		obj.id = this.form.relationId.value;
		obj.name = this.form.relationName.value;
		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f
		if(obj.transitionRestrictions.length == 0){
			obj.transitionRestrictions[0] = new Object();
		}
		
		if(this.form.joinType.selectedIndex == 0){
			obj.transitionRestrictions[0].joinType = "AND";
		}else{
			obj.transitionRestrictions[0].joinType = "XOR";
		}
		
		if(this.form.splitType.selectedIndex == 0){
			obj.transitionRestrictions[0].splitType = "AND";
		}else{
			obj.transitionRestrictions[0].splitType = "XOR";
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
