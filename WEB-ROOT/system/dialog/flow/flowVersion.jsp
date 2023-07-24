<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.flow_version")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>

<style>
.m_arrow{width: 16px;height: 8px;font-family: "Webdings";font-size: 7px;line-height: 2px;padding-left: 2px;}
</style>
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
	<form name="flowVersionForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt" width="160"><%=getI18NResource("designer.flow_version_number")%></td>
		<td class="td_grey">
			<table>
				<tr>
					<td>
						V<input type="text" id="majorVersionNum" name="majorVersionNum" checkType="integer" style="width:67px" value="1" />
					</td>
					<td>
			      <table width="100%" border="0" cellspacing="2" cellpadding="0">
			        <tr>
			          <td><input type="button" class="m_arrow" value="5" onMouseUp="flowVersionOperation.addMajor(1)" /></td>
			        </tr>
			        <tr>
			          <td><input type="button" class="m_arrow" value="6" onMouseUp="flowVersionOperation.addMajor(-1)" /></td>
			        </tr>
			      </table>
					</td>
					<td>
						.<input type="text" id="minorVersionNum" name="minorVersionNum" checkType="integer" style="width:67px" value="0" />
					</td>
					<td>
			      <table width="100%" border="0" cellspacing="2" cellpadding="0">
			        <tr>
			          <td><input type="button" class="m_arrow" value="5" onMouseUp="flowVersionOperation.addMinor(1)" /></td>
			          <td rowspan=2><font color="red">*</font> </td>
			        </tr>
			        <tr>
			          <td><input type="button" class="m_arrow" value="6" onMouseUp="flowVersionOperation.addMinor(-1)" /></td>
			          <td> </td>
			        </tr>
			      </table>
						<input type="hidden" disabled id="packageDefineId" name="packageDefineId" />
						<input type="hidden" disabled id="packageId" name="packageId" />
						<input type="hidden" id="versionName" name="versionName" />
						<input type="hidden" id="state" name="state" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.flow_full_path")%></td>
		<td class="td_grey">
			<input type="text" name="flowPath" style="width:182px;" readonly /><input type="button" id="chooseFlowDir" name="chooseFlowDir" class="button_pop" onclick="flowVersionOperation.chooseFlowDir();" disabled="true" />
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.effect_time")%></td>
		<td class="td_grey">
			<ZTESOFT:dateTimeText name="validFromDate" class="datetimetext" useDate="true" useTime="true" checkType="empty" width="182"/>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.expire_time")%></td>
		<td class="td_grey">
			<ZTESOFT:dateTimeText name="validToDate" class="datetimetext" useDate="true" useTime="true" checkType="empty" width="182"/>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.comment")%></td>
		<td class="td_grey">
			<textarea cols="40" rows="4" style="width:200px;" id="description" name="description"></textarea>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40" valign="bottom">
			<input type="button" class="button" id="confirm_id" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="flowVersionOperation.submit();" />
			<input type="button" class="button" id="cancel_id" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="window.close()" />
			<input type="button" class="button" id="check_time_id" value="<%=getI18NResource("designer.view_occupied_time")%>" onclick="flowVersionOperation.viewExistTime();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-11-06
// Author : Xu.fei3
// commits: View or set flow version Properties
/////////////////////////////////////////////////////

_import("Date");

/* \u5168\u5c40\u53d8\u91cf */
var flowVersionOperation = new FlowVersionOperation();

/* \u521d\u59cb\u5316 */
ExecWait("flowVersionOperation.init()");

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u6d41\u7a0b\u7248\u672c\u5c5e\u6027\u64cd\u4f5c

function FlowVersionOperation(){
	_extends(this, FormExt, '"flowVersionForm"');
	this.form = document.all.flowVersionForm;
	var obj = window.dialogArguments;
	var oldPackageId = obj.packageId;
	var version;

	//\u521d\u59cb\u5316

	this.init = function(){
		switch(obj.operation){
			case "add":{
				this.setValue(this.form.packageId, obj.packageId);

				var today = new Date();
				today.set("hh", 0);
				today.set("mi", 0);
				today.set("ss", 0);
				today.add("dd", 1);

				this.setValue(this.form.validFromDate, today);
				this.setValue(this.form.validToDate, StringToDate("2008-01-01"));

				var maxMajorVersion = 1;
				var maxMinorVersion = 0;

				//\u6700\u5927\u4e3b\u7248\u672c\u53f7

				for(var i=0; i<obj.siblingNames.length; i++){
					var newArr = obj.siblingNames[i].split(".");
					if(parseInt(newArr[0]) > maxMajorVersion){
						maxMajorVersion = parseInt(newArr[0]);
					}
				}

				//\u6700\u5927\u6b21\u7248\u672c\u53f7

				for(var i=0; i<obj.siblingNames.length; i++){
					var newArr = obj.siblingNames[i].split(".");
					if(parseInt(newArr[0]) == maxMajorVersion){
						if(parseInt(newArr[1]) > maxMinorVersion){
							maxMinorVersion = parseInt(newArr[1]);
						}
					}
				}

				if(obj.siblingNames.length > 0){
					if(maxMinorVersion == 9){
						this.form.majorVersionNum.value = maxMajorVersion + 1;
						this.form.minorVersionNum.value = 0;
					}else{
						this.form.majorVersionNum.value = maxMajorVersion;
						this.form.minorVersionNum.value = maxMinorVersion + 1;
					}
				}

				var pathStr = "";
				for(var i=obj.path.length-1; i>0; i--){
					pathStr += obj.path[i].name + " / ";
				}
				pathStr += obj.path[i].name;

				this.form.flowPath.value = pathStr;

				break;
			}
			case "view":{
				document.all.majorVersionNum.disabled = true;
				document.all.minorVersionNum.disabled = true;
				document.all.chooseFlowDir.disabled = true;
				document.all.validFromDate.disabled = true;
				document.all.validToDate.disabled = true;
				document.all.description.disabled = true;
				
				document.all.confirm_id.style.display = "none";
				document.all.cancel_id.value = "<%=getI18NResource("designer.close")%>";

				for(var i=0; i<document.all.length; i++){
					if((document.all[i].tagName.toLowerCase() == "input") && (document.all[i].id == "")){
						document.all[i].disabled = true;
					}
				}

				//\u8fd9\u91cc\u4e0d\u7528break\u4e86\uff0c\u76f4\u63a5\u5229\u7528\u8fd9\u4e2a\u8d2f\u7a7f
			}
			case "update":{
				version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", obj.packageDefineId);

				this.form.packageDefineId.value = version.packageDefineId;
				this.form.packageId.value = version.packageId;
				this.form.versionName.value = version.name || "";
				this.form.state.value = version.state || "";
				this.form.validFromDate.value = version.validFromDate || "";
				this.form.validToDate.value = version.validToDate || "";
				this.form.description.value = version.description || "";

				var versionArray = version.version.split(".");
				this.form.majorVersionNum.value = versionArray[0];
				this.form.minorVersionNum.value = versionArray[1];

				var pathStr = "";
				for(var i=obj.path.length-1; i>0; i--){
					pathStr += obj.path[i].name + " / ";
				}
				pathStr += obj.path[i].name;

				this.form.flowPath.value = pathStr;

				break;
			}
			case "saveas":{
				this.form.chooseFlowDir.disabled = false;

				version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", obj.packageDefineId);

				this.form.packageDefineId.value = version.packageDefineId;
				this.form.packageId.value = version.packageId;
				this.form.versionName.value = version.name || "";
				this.form.state.value = version.state || "";
				this.form.validFromDate.value = version.validFromDate || "";
				this.form.validToDate.value = version.validToDate || "";
				this.form.description.value = version.description || "";

				var versionArray = version.version.split(".");
				this.form.majorVersionNum.value = versionArray[0];
				this.form.minorVersionNum.value = versionArray[1];

				var pathStr = "";
				for(var i=obj.path.length-1; i>0; i--){
					pathStr += obj.path[i].name + " / ";
				}
				pathStr += obj.path[i].name;

				this.form.flowPath.value = pathStr;
				var maxMajorVersion = 1;
				var maxMinorVersion = 0;
				for(var i=0; i<obj.siblingNames.length; i++){
					var newArr = obj.siblingNames[i].split(".");
					if(parseInt(newArr[0]) > maxMajorVersion){
						maxMajorVersion = parseInt(newArr[0]);
					}

					if(parseInt(newArr[1]) > maxMinorVersion){
						maxMinorVersion = parseInt(newArr[1]);
					}
				}

				if(maxMinorVersion == 9){
					this.form.majorVersionNum.value = maxMajorVersion + 1;
					this.form.minorVersionNum.value = 0;
				}else{
					this.form.majorVersionNum.value = maxMajorVersion;
					this.form.minorVersionNum.value = maxMinorVersion + 1;
				}

				break;
			}
		}
	}

	//\u589e\u52a0\u4e3b\u7248\u672c\u53f7
	this.addMajor = function(amount){
		var majorVersionNum;
		if(this.form.majorVersionNum.value == ""){
			this.form.majorVersionNum.value = 0;
		}
		try{
			majorVersionNum = parseInt(this.form.majorVersionNum.value);
			majorVersionNum += amount;
			if(majorVersionNum < 1){
				majorVersionNum = 1;
			}
			this.form.majorVersionNum.value = majorVersionNum;
		}catch(e){
			ErrorHandle("<%=getI18NResource("designer.major_version_greater_than_0")%>");
		}
	}

	//\u589e\u52a0\u6b21\u7248\u672c\u53f7
	this.addMinor = function(amount){
		var minorVersionNum;
		if(this.form.minorVersionNum.value == ""){
			this.form.minorVersionNum.value = 9;
		}
		try{
			minorVersionNum = parseInt(this.form.minorVersionNum.value);
			minorVersionNum += amount;
			if(minorVersionNum < 0){
				minorVersionNum = 9;
			}
			if(minorVersionNum > 9){
				minorVersionNum = 0;
			}
			this.form.minorVersionNum.value = minorVersionNum;
		}catch(e){
			ErrorHandle("<%=getI18NResource("designer.minor_version_between_0_and_9")%>");
		}
	}

	//\u67e5\u770b\u5df2\u5360\u7528\u65f6\u95f4\u6bb5
	this.viewExistTime = function(){
		var packageId = this.form.packageId.value;

		var versions = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryProDefByPackageId", packageId);
		var usedTimes = new Array();
		for(var i=0; i<versions.length; i++){
			//\u5931\u6548\u72b6\u6001\u4e0d\u8003\u8651\u4e86

			if(versions[i].state != "10C"){
				var time = new Object();
				time.validFromDate = versions[i].validFromDate;
				time.validToDate = versions[i].validToDate;
				time.version = versions[i].version;

				usedTimes.push(time);
			}
		}

		OpenShowDlg("./existTime.jsp", 400, 540, usedTimes);
	}

	//\u9009\u62e9\u4fdd\u5b58\u5230\u7684\u6d41\u7a0b\u6a21\u677f
	this.chooseFlowDir = function(){
		var result = OpenShowDlg("./versionChooseDir.jsp", 400, 300, obj);

		if(result != null){
			this.form.flowPath.value = result.pathStr;
			this.form.packageId.value = result.tmpId;
			this.form.majorVersionNum.value = 1;
			this.form.minorVersionNum.value = 0;

			var versions = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryProDefByPackageId", result.tmpId);

			var maxMajorVersion = 1;
			var maxMinorVersion = 0;
			for(var i=0; i<versions.length; i++){
				var newArr = obj.versions[i].version.split(".");
				if(parseInt(newArr[0]) > maxMajorVersion){
					maxMajorVersion = parseInt(newArr[0]);
				}

				if(parseInt(newArr[1]) > maxMinorVersion){
					maxMinorVersion = parseInt(newArr[1]);
				}
			}

			if(maxMinorVersion == 9){
				this.form.majorVersionNum.value = maxMajorVersion + 1;
				this.form.minorVersionNum.value = 0;
			}else{
				this.form.majorVersionNum.value = maxMajorVersion;
				this.form.minorVersionNum.value = maxMinorVersion + 1;
			}
		}
	}

	//\u63d0\u4ea4
	this.submit = function(){
		//\u63d0\u4ea4\u4e4b\u524d\u5e94\u5f53\u5148\u4f5c\u57fa\u672c\u7684\u6821\u9a8c

		if(this.validator()){
			return;
		}
		if(this.form.majorVersionNum.value <= 0){
			ErrorHandle("<%=getI18NResource("designer.major_version_greater_than_0")%>");
			return;
		}

		if(this.form.minorVersionNum.value < 0 || this.form.minorVersionNum.value > 9){
			ErrorHandle("<%=getI18NResource("designer.minor_version_between_0_and_9")%>");
			return;
		}

		//\u5982\u679c\u5e76\u975e\u4fdd\u5b58\u5728\u539f\u6a21\u677f\u4e0b\uff0c\u90a3\u4e48\u4e0d\u9700\u8981\u6821\u9a8c\u4e0b\u9762\u8fd9\u4e9b

		if(oldPackageId == this.form.packageId.value){
			for(var i=0; i<window.dialogArguments.siblingNames.length; i++){
				var versionName = this.form.majorVersionNum.value + "." + this.form.minorVersionNum.value;

				if(versionName == window.dialogArguments.siblingNames[i]){
					ErrorHandle("<%=getI18NResource("designer.flow_version_conflict")%>");
					return;
				}
			}
					

			//\u6821\u9a8c\u65f6\u95f4\uff0c\u8fd9\u91cc\u6bd4\u8f83\u9ebb\u70e6

			var packageId = this.form.packageId.value;
			var versions = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryProDefByPackageId", packageId);

			var usedTimes = new Array();
			if(obj.operation == "update"){
				for(var i=0; i<versions.length; i++){
					if((versions[i].packageDefineId != this.form.packageDefineId.value)
						&& (versions[i].state != "10C")){
						var time = new Object();
						time.validFromDate = versions[i].validFromDate;
						time.validToDate = versions[i].validToDate;
						usedTimes.push(time);
					}
				}
			}else{
				for(var i=0; i<versions.length; i++){
					if(versions[i].state != "10C"){
						var time = new Object();
						time.validFromDate = versions[i].validFromDate;
						time.validToDate = versions[i].validToDate;
						usedTimes.push(time);
					}
				}
			}

			var validFromDate = this.getValue(this.form.validFromDate);
			var validToDate = this.getValue(this.form.validToDate);
			for(var i=0; i<usedTimes.length; i++){
				if((validFromDate.diff("ms", usedTimes[i].validFromDate) >= 0)
					 && (validFromDate.diff("ms", usedTimes[i].validToDate) <= 0)){
					ErrorHandle("<%=getI18NResource("designer.start_time_occupied")%>");
					return;
				}

				if((validToDate.diff("ms", usedTimes[i].validFromDate) >= 0)
					 && (validToDate.diff("ms", usedTimes[i].validToDate) <= 0)){
					ErrorHandle("<%=getI18NResource("designer.end_time_occupied")%>");
					return;
				}
			}

			//\u751f\u6210\u7248\u672c\u53f7

			var maxMajorVersion = 1;
			var maxMinorVersion = 0;

			//\u627e\u6700\u5927\u4e3b\u7248\u672c\u53f7

			for(var i=0; i<window.dialogArguments.siblingNames.length; i++){
				var versionArray = window.dialogArguments.siblingNames[i].split(".");
				if(versionArray[0] > maxMajorVersion){
					maxMajorVersion = versionArray[0];
				}
			}

			//\u627e\u5230\u4e86\u6700\u5927\u7684\u4e3b\u7248\u672c\u53f7\uff0c\u73b0\u5728\u5f00\u59cb\u627e\u6700\u5927\u6b21\u7248\u672c\u53f7

			for(var i=0; i<window.dialogArguments.siblingNames.length; i++){
				var versionArray = window.dialogArguments.siblingNames[i].split(".");
				if(versionArray[0] == maxMajorVersion){
					if(versionArray[1] > maxMinorVersion){
						maxMinorVersion = versionArray[1];
					}
				}
			}

			//\u5982\u679c\u662f\u4fee\u6539\uff0c\u90a3\u4e48\u4e0d\u7528\u6821\u9a8c\u7248\u672c\u53f7\u7684\u5927\u5c0f\u95ee\u9898\u4e86

			if(obj.operation != "update"){
				if(parseInt(this.form.majorVersionNum.value) < maxMajorVersion){
					ErrorHandle("<%=getI18NResource("designer.major_version_cannot_less_than_max")%>");
					return;
				}else if((this.form.majorVersionNum.value == maxMajorVersion) && (parseInt(this.form.minorVersionNum.value) < maxMinorVersion)){
					ErrorHandle("<%=getI18NResource("designer.minor_version_cannot_less_than_max")%>");
					return;
				}
			}
		}else{
			if((this.form.majorVersionNum.value != 1) || (this.form.minorVersionNum.value != 0)){
				ErrorHandle("<%=getI18NResource("designer.version_number_must_be_1.0")%>");
				return;
			}
		}

		var diff = (this.getValue(this.form.validFromDate)).diff("ms", this.getValue(this.form.validToDate));
		if(diff >= 0){
			ErrorHandle("<%=getI18NResource("designer.effect_time_before_expire_time")%>");
			return;
		}
		
		//\u4e0d\u80fd\u5c0f\u4e8e\u5f53\u524d\u65f6\u95f4\uff0c\u5f53\u524d\u65f6\u95f4\u53d6\u670d\u52a1\u5668\u65f6\u95f4

		currentDate = callRemoteFunctionNoTrans("com.ztesoft.iom.dataquery.ToolQuery","getServerDate");
		if(this.form.validFromDate.value!=""){
			effDate = this.getValue(this.form.validFromDate);
			if(effDate.getTime()< currentDate.getTime()){
				ErrorHandle("<%=getI18NResource("designer.effect_time_after_now")%>");
				return;
			}
		}
		if(this.form.validToDate.value!=""){
			expDate = this.getValue(this.form.validToDate);
			if(expDate.getTime()< currentDate.getTime()){
				ErrorHandle("<%=getI18NResource("designer.expire_time_after_now")%>");
				return;
			}
		}	

		var newVersion = new Object();
		newVersion.packageDefineId = this.form.packageDefineId.value;
		newVersion.packageId = this.form.packageId.value;
		newVersion.name = this.form.versionName.value;
		newVersion.state = this.form.state.value;
		newVersion.validFromDate = this.form.validFromDate.value;
		newVersion.validToDate = this.form.validToDate.value;
		newVersion.description = this.form.description.value;
		newVersion.version = this.form.majorVersionNum.value + "." + this.form.minorVersionNum.value;

		var session = GetSession();
		newVersion.edituser = session.staff.staffName;

		switch(obj.operation){
			case "add":{
				newVersion.author = session.staff.staffName;
				newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "addProcessDefinition", newVersion);
				break;
			}
			case "update":{
				newVersion.author = version.author;
				newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "updateProcessDefinition", newVersion);
				break;
			}
			case "saveas":{
				newVersion.author = version.author;
				newVersion.xpdlcontent = obj.xpdl;
				newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "saveasProcessDefinition", newVersion);
				break;
			}
		}

		window.returnValue = newVersion;
		window.close();
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
