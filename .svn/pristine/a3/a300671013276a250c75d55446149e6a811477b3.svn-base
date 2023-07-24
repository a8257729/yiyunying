<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.flow_template")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<!-- TemplBeginEditable name="head" -->

<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>

<table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="flowTmpForm" id="flowTmpForm" onsubmit="return false">
		<tr id="areaTr">
	    <td class="td_blue_txt" width="35%"><%=getI18NResource("designer.area")%></td>
	    <td class="td_grey" width="65%">
	    	<input type="text" name="areaName" checkType="empty" readonly style="width:200px;" ><INPUT TYPE="button" id="btnSelArea" class="button_pop" onclick="flowTmpOperation.selectArea()" ><font color="red">*</font>
	    	<input type="hidden" name="areaId"  value="">
	    	</td>
	  </tr>
	  <tr>
	    <td class="td_blue_txt"><%=getI18NResource("designer.flow_catalog")%></td>
	    <td class="td_grey">
	    	<input type="text" name="catalogName" checkType="empty" readonly style="width:200px;" ><INPUT TYPE="button" id="btnSelCatalog" class="button_pop" onclick="flowTmpOperation.selectCatalog()" ><font color="red">*</font>
	    	<input type="hidden" id="catalogId" name="catalogId"  value="">
	    	</td>
	  </tr>
		<tr>
			<td class="td_blue_txt"><%=getI18NResource("designer.flow_template_name")%></td>
			<td class="td_grey">
				<input type="hidden" id="oldAreaId" name="oldAreaId" />
				<!--input type="hidden" id="catalogId" name="catalogId" /-->
				<input type="hidden" id="packageId" name="packageId" />
				<input type="hidden" id="state" name="state" />
				<input type="text" id="packageName" name="packageName" style="width:200px;" /><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="td_blue_txt"><%=getI18NResource("designer.comment")%></td>
			<td class="td_grey">
				<textarea cols=40 rows=4 style="width:200px;" id="description" name="description"></textarea>
			</td>
		</tr>
		<tr>
	   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
	  </tr>
		<tr>
			<td colspan="2" align="center" height="40" valign="bottom">
				<input type="button" class="button" id="okBtn" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="flowTmpOperation.submit();" />
				<input type="button" class="button" id="cancelBtn" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="flowTmpOperation.cancel();" />
			</td>
		</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-15
// Author : Xu.fei3
// commits: View or set flow template Properties
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var flowTmpOperation = new FlowTmpOperation();

/* \u521d\u59cb\u5316 */
flowTmpOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u6d41\u7a0b\u6a21\u677f\u5c5e\u6027\u64cd\u4f5c

function FlowTmpOperation(){
	_extends(this, FormExt, '"form1"');

	var obj = window.dialogArguments;
	this.form = document.all.flowTmpForm;	
	this.txtAreaId = this.form.areaId;
	this.txtAreaName = this.form.areaName;
	
	this.txtPackageName = this.form.packageName;
	this.txtPackageId = this.form.packageId;
	this.txtCatalogId = this.form.catalogId;
	this.txtCatalogName = this.form.catalogName;
	//\u521d\u59cb\u5316

	this.init = function(){
		if(obj.ownerAreaName == null){
			document.all.areaTr.style.display = "none";
		}

		this.setValue(this.txtAreaName, obj.ownerAreaName);
		this.setValue(this.txtAreaId, obj.ownerAreaId);		
		this.txtCatalogId.value = obj.catalogId;			
		this.txtCatalogName.value = obj.catalogName;	
		
		switch(obj.operation){
			case "add":{	
				document.all.btnSelArea.disabled = true;	
				document.all.btnSelCatalog.disabled = true;	
				break;
			}
			default:{				
				if(obj.operation=="update"){
					document.all.btnSelArea.disabled = true;	
					document.all.btnSelCatalog.disabled = true;	
				}
				var package = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackageById", obj.packageId);
				this.txtPackageId.value = obj.packageId;
				this.txtPackageName.value = package.name;
				this.form.description.value = package.description || "";
			}
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		if(!this.validateDate()) return;
		
		if(Trim(this.txtPackageName.value) == ""){
			ErrorHandle("<%=getI18NResource("designer.template_name_cannot_empty")%>");
			return;
		}
		
		//\u6839\u636e\u9009\u62e9\u7684\u76ee\u5f55\u53d6\u5176\u4e0b\u7684\u6d41\u7a0b\u6a21\u677f

		if(obj.operation != "update"){
			var pkgs = this.getPackages();
			var size = pkgs.length;
			for(var i=0; i<size; i++){
				if(this.txtPackageName.value == pkgs[i].name){
					ErrorHandle("<%=getI18NResource("designer.flow_template_conflict")%>");
					return;
				}
			}
		}
		
		var newPackage = new Object();
		newPackage.ownerAreaId = this.txtAreaId.value;
		newPackage.catalogId = this.txtCatalogId.value || 0;
		newPackage.packageId = this.txtPackageId.value || 0;
		newPackage.name = this.txtPackageName.value || "";
		newPackage.description = this.form.description.value || "";
		newPackage.state = "10A";
		
		switch(obj.operation){
			case "add":{
				newPackage = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "addPackage", newPackage);
				break;
			}
			case "update":{
				newPackage = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "updatePackage", newPackage);
				break;
			}
			case "saveas":{
				newPackage = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "saveasPackage", newPackage);
				break;
			}
		}
		
		window.returnValue = newPackage;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
	
	/*\u9009\u62e9\u533a\u57df*/
	this.selectArea = function(){
		var session = GetSession();
		var areaId = session.area.areaId;
		var retObj = OpenShowDlg('../../../common/areaSel.jsp',420,300,areaId);
		this.clearTxtCatalog();
		if(retObj){
			this.setValue(this.txtAreaName, retObj.name);
			this.setValue(this.txtAreaId, retObj.id);				
		}
	}
	/*\u9009\u62e9\u6d41\u7a0b\u76ee\u5f55*/
	this.selectCatalog = function(){
		var sendObj = new Object();
		sendObj.areaId = this.txtAreaId.value;
		var retObj = OpenShowDlg('catalogSel.jsp',450,300,sendObj);
		if(retObj){		
			this.setValue(this.txtCatalogId, retObj.id);		
			this.setValue(this.txtCatalogName, retObj.name);
		}
	}
	
	this.getPackages = function(){
		var selCatalogId = this.txtCatalogId.value;
		
		//\u53d6\u5f97\u9009\u4e2d\u76ee\u5f55\u4e0b\u9762\u6d41\u7a0b\u6a21\u677f
		var pkgDtoArr = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackagesByCatalogId",selCatalogId);
		return pkgDtoArr;
	}
	
	this.clearTxtCatalog= function(){
			this.setValue(this.txtCatalogId,0);
			this.setValue(this.txtCatalogName,"");	
			//this.setValue(this.txtPackageId,0);
			//this.setValue(this.txtPackageName, "");			
	}
	
	this.validateDate = function(){
		if(this.isEmpty(this.txtCatalogId.value) || this.isEmpty(this.txtAreaId.value)){
			ErrorHandle("<%=getI18NResource("designer.area_catalog_cannot_empty")%>");
			return false;
		}
		return true;
	}
	
	this.isEmpty = function(inVal){
		if(inVal==0 || inVal==""){
			return true;
		}
		return false;
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
