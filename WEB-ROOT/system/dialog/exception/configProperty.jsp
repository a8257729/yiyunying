<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title> <%=getI18NResource("designer.exception_config")%> </title>
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<script language="javascript" src="../../../public/script/BSCommon.js"></script>
	<?XML:NAMESPACE PREFIX="ZTESOFT" ?>
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeListSort.htc" />
</head>

<body scroll="no">
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td width="40%"></td>
		<td width="60%"></td>
	</tr>
	<tr>
		<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
	</tr>
	<form name="configForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.exception_activity")%></td>
		<td class="td_grey">
			<select style="width:140px" class="select_htc" id="startActivity" name="startActivity" onchange="configOperation.changeStart();">
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.auto_to_manual")%></td>
		<td>
			<input type="radio" class="checkbox" name="autoToManual" checked/><%=getI18NResource("designer.no")%>
			<input type="radio" class="checkbox" name="autoToManual"/><%=getI18NResource("designer.yes")%>
		</td>
	</tr>
	<tr name="changeReasonTr" id="changeReasonTr" style="display:none">
		<td class="td_blue_txt"><%=getI18NResource("designer.query_change_reason")%></td>
		<td>
			<input type="text" name="changeReasonText" id="changeReasonText"/><input type="button" class="button" value="<%=getI18NResource("designer.query")%>" onclick="configOperation.queryChangeReason()"/>
		</td>
	</tr>
	<tr name="reasonTypeTr" id="reasonTypeTr" style="display:none">
		<td class="td_blue_txt"><%=getI18NResource("designer.exception_type")%></td>
		<td>
			<input type="radio" class="checkbox" name="reasonType" checked onclick="configOperation.queryReturnReason()"/><%=getI18NResource("designer.rollback_order")%>
			<input type="radio" class="checkbox" name="reasonType" onclick="configOperation.queryWaitReason()"/><%=getI18NResource("designer.wait_install")%>
			<input type="radio" class="checkbox" name="reasonType" onclick="configOperation.queryPauseReason()"/><%=getI18NResource("tache.backTai_id")%>
		</td>
	</tr>
	<!-- add by chen.jixin 2010-09-09 for ur:61223 begin -->
	<tr id="displaySearch" name="displaySearch" style="display:none">
		<td class="td_blue_txt">
		<%=getI18NResource("designer.exception_name")%>
		</td>
		<td>
		<input type="text" id="exceptionName" name="exceptionName" /><input class="button" type="button" id="queryExceptionButton" name="queryExceptionButton" value="<%=getI18NResource("designer.query")%>" onclick="configOperation.queryReason()" />
		</td>
	</tr>
	<!-- ur:61233 end -->
	<!-- add by chen.jixin 2010-08-19 for ur:60240 begin-->
	<tr id="displayCheckbox" name="displayCheckbox" style="display:none">
	<td>
	</td>
	<td>
	<input type="checkbox" name="isDisplay" id="isDisplay" class="checkbox" checked onclick="configOperation.showLocalAreaReturnReason()">
	<%=getI18NResource("designer.display_higher_area_exception_reason")%>
	</td>
	</tr>
	<!-- ur:60240 end -->
	<tr>
		<td colspan="2">
			<ZTESOFT:treelist showCheck="true" id="catalogTree" sorted="true" name="catalogTree" class="treelist" width="100%" height="200">
				<ZTESOFT:columns>
					<ZTESOFT:column width="60%" display="true" displayText="<%=getI18NResource("designer.exception_name")%>" propertyName="RETURN_REASON_NAME" sortType="String"/>
					<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("designer.type")%>" propertyName="REASON_TYPE_NAME" />
					<ZTESOFT:column display="false" propertyName="AUDIT_FLAG_NAME" />
					<ZTESOFT:column display="false" propertyName="ID" />
					<ZTESOFT:column display="false" propertyName="REASON_TYPE" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.return_activity")%></td>
		<td class="td_grey">
			<select style="width:140px" class="select_htc" id="endActivity" name="endActivity">
			</select>
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_blue_txt" id="destActiv_start_mothed"><%=getI18NResource("designer.dest_activity_start_mode")%></td>
		<td class="td_grey">
			<select style="width:140px" class="select_htc" id="startMode" name="startMode">
				<option value="1" id="auto"><%=getI18NResource("designer.automatic")%></option>
				<option value="2" id="manual"><%=getI18NResource("designer.manual")%></option>
				<option value="3" id="back"><%=getI18NResource("designer.return_back")%></option>
			</select>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" id="okBtn" class="button" value="<%=getI18NResource("designer.ok")%>" onclick="configOperation.submit();" />
			<input type="button" id="cancelBtn" class="button" value="<%=getI18NResource("designer.cancel")%>" onclick="configOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>
</body>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-10-13
// Author : Xu.fei3
// commits: View or set exception configuration Properties
/////////////////////////////////////////////////////
_import("public.script.BSCommon");

var obj = window.dialogArguments;
var configOperation = new ConfigOperation();
var canSelCondTacheObj = null;
//add by ji.dong 2010-06-07 ur:56260 begin
var isLeach = null; 
var areaId = "";
//end
configOperation.init();
//////////////////////////////////////////
function ConfigOperation(){
	_extends(this, BSCommon, '"configForm"');

	this.form = document.all.configForm;
	this.catalogTree = document.all.catalogTree;
	
	this.init = function(){
		//add by ji.dong 2010-06-07 ur:56260 begin
		isLeach = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager", "findParameter", "IS_LEACH_RETURNREASON");				
		if (isLeach && isLeach.value == 'Y'){
			areaId = callRemoteFunctionNoTrans("com.ztesoft.iom.orderdesign.dao.CommonDAOImpl", "getAreaByFlowId", obj.flowId);
		}
		//end
		//add by chen.jixin 2010-08-19 for ur:60240 begin
		if (isLeach && isLeach.value == 'Y'){
			document.all.displayCheckbox.style.display= "";
		}
		//ur:60240 end
		canSelCondTacheObj = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager","findParameter","CAN_SEL_COND_TACHE");
		if(obj.operation == "update"){
			var option = document.createElement("option");
			option.value = obj.config.startActivityId;
			option.innerText = obj.config.startActivityName;
			this.form.startActivity.appendChild(option);
			this.form.startActivity.disabled = true;

			if(obj.config.autoToManual == true){
				this.form.autoToManual[1].checked = true;
			}
			
			if(obj.config.startMode == "Wait"){
				this.form.reasonType[1].checked = true;
			} else if(obj.config.startMode == "Pause"){
				this.form.reasonType[2].checked = true;
			}
			
			this.form.reasonType[0].disabled = true;
			this.form.reasonType[1].disabled = true;
			this.form.reasonType[2].disabled = true;
		}else if(obj.operation == "add"){
			//add by chen.jixin 2010-09-09 for ur:61223 begin
			document.all.displaySearch.style.display= "";
			//ur:61223 end
			for(var i=0; i<obj.allNodes.length; i++){
				if(obj.allNodes[i].getNodeType() == "Tache"){
					var option = document.createElement("option");
					option.value = obj.allNodes[i].activity.id;
					option.innerText = obj.allNodes[i].activity.name;
					this.form.startActivity.appendChild(option);
				}
			}
			var noStart = document.createElement("option");
			noStart.value = 0;
			noStart.innerText = "<%=getI18NResource("designer.no_start_tache")%>";
			this.form.startActivity.appendChild(noStart);
		}
		
		this.changeStart();
	}
	//add by chen.jixin 2010-08-19 for ur:60240 begin
	this.showLocalAreaReturnReason =function(){
	var isDisplay=document.all.isDisplay.checked;
	var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		if(parseInt(activityId) == 0){
			this.noStartReasonAndLocalSearch();
			return;
		}else if(this.form.reasonType[0].checked == true){
			this.queryReturnReason();
			return;
		}else if(this.form.reasonType[1].checked == true){
			this.queryWaitReason();
			return;
		}else if(this.form.reasonType[2].checked == true){
			this.queryPauseReason();
			return;
		}
	}
	//ur:60240 end
	//add by chen.jixin 2010-09-09 for ur:61223 begin
	this.queryReason = function(){
		var exceptionName= this.form.exceptionName.value;
		if( exceptionName == ""){
			this.changeStart();
		}else{
			var tacheId;
			var isDisplay = document.all.isDisplay.checked;
			var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
			for(var i=0; i<obj.allNodes.length; i++){
				if(obj.allNodes[i].activity.id == activityId){
					for(var j=0; j<obj.allNodes[i].activity.extendedAttributes.length; j++){
						if(obj.allNodes[i].activity.extendedAttributes[j].name == "ExTacheId"){
							tacheId = obj.allNodes[i].activity.extendedAttributes[j].value;
							break;
						}
					}
				}
			}		
			if(parseInt(activityId) == 0){//无起始环节
				if(isLeach && isLeach.value == 'Y' && isDisplay== false){//只显示本地区的异常原因
					this.clearParam();
					this.addParam(2, "AREA_ID", areaId);
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					var reasons = this.callRemoteFunctionQuery("qryLocalReturnChangeReasonByExceptionName");
					}else{	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);	
					var reasons = this.callRemoteFunctionQuery("qryReturnChangeReasonByExceptionName");	
					}
			}else{
				if(this.form.reasonType[0].checked){//退单
					if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryReturnReasonByAreaTacheByExceptionName");		
					}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryReturnReasonByLocalAreaTacheByExceptionName");		
					}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasons = this.callRemoteFunctionQuery("qryReturnReasonByTacheByExceptionName");
					}			
				}else if(this.form.reasonType[1].checked){//待装
					if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryWaitReasonByAreaTacheByExceptionName");		
					}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryWaitReasonByLocalAreaTacheByExceptionName");		
					}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasons = this.callRemoteFunctionQuery("qryWaitReasonByTacheByExceptionName");
					}		
				}else if(this.form.reasonType[2].checked){//后台缓装
					if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryPauseReasonByAreaTacheByExceptionName");		
					}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasons = this.callRemoteFunctionQuery("qryPauseReasonByLocalAreaTacheByExceptionName");		
					}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasons = this.callRemoteFunctionQuery("qryPauseReasonByTacheByExceptionName");
					}				
				}
			}		
			this.catalogTree.clear();
			this.catalogTree.loadByData(reasons);
		}
	}
	//ur:61223 end
	this.queryChangeReason = function(){
		if(this.form.changeReasonText.value == ""){
			alert("<%=getI18NResource("designer.please_input_query_text")%>");
			return;
		}
		//add by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay=document.all.isDisplay.checked;	
		if(isLeach && isLeach.value == 'Y' && isDisplay== false){//只显示本地区的异常原因

			this.clearParam();
			this.addParam(2, "AREA_ID", areaId);
			this.addParam(2, "RETURN_REASON_NAME", this.form.changeReasonText.value);
			var reasons = this.callRemoteFunctionQuery("qryLocalReturnChangeReasonByName");
		}else{
		//ur:60240 end
			this.clearParam();
			this.addParam(2, "RETURN_REASON_NAME", this.form.changeReasonText.value);
			var reasons = this.callRemoteFunctionQuery("qryReturnChangeReasonByName");
		}
		this.catalogTree.clear();
		this.catalogTree.loadByData(reasons);
		for(var i=0; i<this.catalogTree.itemsLength; i++){
			if(this.catalogTree.items[i].flag == "C"){
				this.catalogTree.items[i].setImage("../../../images/folder.gif");	
			}	
		}
		
		if(obj.operation == "add"){
			var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
			for(var i=0; i<obj.selectedReasons.length; i++){
				for(var j=0; j<this.catalogTree.itemsLength; j++){
					if((obj.selectedReasons[i].startActivityId == activityId)
						&& (obj.selectedReasons[i].returnReasonId == this.catalogTree.items[j].ID)){
						this.catalogTree.items[j].remove();
						j--;
						break;
					}
				}
			}
		}
	}
	
	//add by chen.jixin 2010-08-19 for ur:60240 begein
	this.noStartReasonAndLocalSearch = function(){
		var isDisplay=document.all.isDisplay.checked;	
		if(isLeach && isLeach.value == 'Y' && isDisplay== false){//只显示本地区的异常原因

			this.clearParam();
			this.addParam(2, "AREA_ID", areaId);
			this.addParam(2, "RETURN_REASON_NAME", this.form.changeReasonText.value);
			var reasons = this.callRemoteFunctionQuery("qryLocalReturnChangeReasonByName");
		}else{
			this.clearParam();
			this.addParam(2, "RETURN_REASON_NAME", this.form.changeReasonText.value);
			var reasons = this.callRemoteFunctionQuery("qryReturnChangeReasonByName");
		}
		this.catalogTree.clear();
		this.catalogTree.loadByData(reasons);
		for(var i=0; i<this.catalogTree.itemsLength; i++){
			if(this.catalogTree.items[i].flag == "C"){
				this.catalogTree.items[i].setImage("../../../images/folder.gif");	
			}	
		}	
		if(obj.operation == "add"){
			var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
			for(var i=0; i<obj.selectedReasons.length; i++){
				for(var j=0; j<this.catalogTree.itemsLength; j++){
					if((obj.selectedReasons[i].startActivityId == activityId)
						&& (obj.selectedReasons[i].returnReasonId == this.catalogTree.items[j].ID)){
						this.catalogTree.items[j].remove();
						j--;
						break;
					}
				}
			}
		}
	}
	//ur:60240 end
	this.queryReturnReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		var tacheId;
		for(var i=0; i<obj.allNodes.length; i++){
			if(obj.allNodes[i].activity.id == activityId){
				for(var j=0; j<obj.allNodes[i].activity.extendedAttributes.length; j++){
					if(obj.allNodes[i].activity.extendedAttributes[j].name == "ExTacheId"){
						tacheId = obj.allNodes[i].activity.extendedAttributes[j].value;
						break;
					}
				}
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin	
		if (isLeach && isLeach.value == 'Y' && isDisplay == true){//modify by chen.jixin 2010-08-19 for ur:60240	
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryReturnReasonByAreaTache", tacheId, areaId);
		}else if(isLeach && isLeach.value == 'Y' && isDisplay == false)//只显示当地异常原因

		{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryReturnReasonByLocalAreaTache", tacheId, areaId);
		}
		//ur:60240 end
		else{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryReturnReasonByTache", tacheId);
		}
		//end
		

		var xmlDoc = makeDOM();
		xmlDoc.loadXML(reasonData);

		var root = xmlDoc.documentElement;

		for (var i=0; i<obj.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((obj.selectedReasons[i].startActivityId == activityId)
					&& (obj.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.catalogTree.loadByXML(xmlDoc.xml);
	}

	this.queryWaitReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		var tacheId;
		for(var i=0; i<obj.allNodes.length; i++){
			if(obj.allNodes[i].activity.id == activityId){
				for(var j=0; j<obj.allNodes[i].activity.extendedAttributes.length; j++){
					if(obj.allNodes[i].activity.extendedAttributes[j].name == "ExTacheId"){
						tacheId = obj.allNodes[i].activity.extendedAttributes[j].value;
						break;
					}
				}
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin
		if (isLeach && isLeach.value == 'Y' && isDisplay == true){//modify by chen.jixin 2010-08-19 for ur:60240
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryWaitReasonByAreaTache", tacheId, areaId);
		}else if(isLeach && isLeach.value == 'Y' && isDisplay == false)//只显示当地异常原因

		{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryWaitReasonByLocalAreaTache", tacheId, areaId);
		}
		//ur:60240 end
		else{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryWaitReasonByTache", tacheId);
		}
		//end

		var xmlDoc = makeDOM();
		xmlDoc.loadXML(reasonData);

		var root = xmlDoc.documentElement;

		for (var i=0; i<obj.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((obj.selectedReasons[i].startActivityId == activityId)
					&& (obj.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.catalogTree.loadByXML(xmlDoc.xml);
	}
	
	this.queryPauseReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		var tacheId;
		for(var i=0; i<obj.allNodes.length; i++){
			if(obj.allNodes[i].activity.id == activityId){
				for(var j=0; j<obj.allNodes[i].activity.extendedAttributes.length; j++){
					if(obj.allNodes[i].activity.extendedAttributes[j].name == "ExTacheId"){
						tacheId = obj.allNodes[i].activity.extendedAttributes[j].value;
						break;
					}
				}
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin
		if (isLeach && isLeach.value == 'Y' && isDisplay == true){//modify by chen.jixin 2010-08-19 for ur:60240
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryPauseReasonByAreaTacheId", tacheId, areaId);
		}else if(isLeach && isLeach.value == 'Y' && isDisplay == false)//只显示当地异常原因

		{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryPauseReasonByLocalAreaTacheId", tacheId, areaId);
		}
		//ur:60240 end
		else{
			var reasonData = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryPauseReasonByTacheId", tacheId);
		}
		//end

		var xmlDoc = makeDOM();
		xmlDoc.loadXML(reasonData);

		var root = xmlDoc.documentElement;

		for (var i=0; i<obj.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((obj.selectedReasons[i].startActivityId == activityId)
					&& (obj.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.catalogTree.loadByXML(xmlDoc.xml);
	}

	this.noStartReason = function(){
		document.all.reasonTypeTr.style.display = "none";
		document.all.changeReasonTr.style.display = "";
		var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		//add by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay=document.all.isDisplay.checked;
		if(isLeach && isLeach.value == 'Y' && isDisplay== false){//只显示本地区的异常原因

		this.clearParam();
		this.addParam(2, "AREA_ID", areaId);
		var reasons = this.callRemoteFunctionQuery("qryLocalReturnChangeReason");	
		var pauseReasons = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryLocalPauseReason",areaId);
		}else{		
		//ur:60240 end
		var reasons = this.callRemoteFunctionQuery("qryReturnChangeReason");		
		//add by chen.zhikun2 2010-05-17 UR-54984
		var pauseReasons = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryPauseReason");
		}
		for(i=0;i<pauseReasons.length;i++)
		{
			var pReason = new Object();
			pReason.RETURN_REASON_NAME = pauseReasons[i].returnReasonName;
			pReason.REASON_TYPE_NAME = pauseReasons[i].reasonTypeName;
			pReason.ID = pauseReasons[i].id;
			pReason.REASON_TYPE = pauseReasons[i].reasonType;
			reasons.push(pReason);
		}
		
		this.catalogTree.loadByData(reasons);
		for(var i=0; i<this.catalogTree.itemsLength; i++){
			if(this.catalogTree.items[i].flag == "C"){
				this.catalogTree.items[i].setImage("../../../images/folder.gif");	
			}	
		}
		
		this.form.endActivity.innerHTML = "";
		
		for(var i=0; i<obj.allNodes.length; i++){
			if((obj.allNodes[i].getNodeType() == "Tache") || (obj.allNodes[i].getNodeType() == "Start")){
				var option = document.createElement("option");
				option.value = obj.allNodes[i].activity.id;
				option.innerText = obj.allNodes[i].activity.name;
				this.form.endActivity.appendChild(option);
			}
		}

		this.form.startMode.innerHTML = "";
		var option = document.createElement("option");
		option.innerText = "<%=getI18NResource("designer.manual")%>";
		this.form.startMode.appendChild(option);
	
		option = document.createElement("option");
		option.innerText = "<%=getI18NResource("designer.change_back")%>";
		this.form.startMode.appendChild(option);

		this.form.startMode.selectedIndex = 1;

		if(window.dialogArguments.operation == "add"){
			for(var i=0; i<obj.selectedReasons.length; i++){
				for(var j=0; j<this.catalogTree.itemsLength; j++){
					if((obj.selectedReasons[i].startActivityId == activityId)
						&& (obj.selectedReasons[i].returnReasonId == this.catalogTree.items[j].ID)){
						this.catalogTree.items[j].remove();
						j--;
						break;
					}
				}
			}
		}else{
			var configs = new Array();
			var config = new Object();
			
			config.REASON_TYPE = obj.config.reasonCatalogId;
			config.REASON_TYPE_NAME = obj.config.reasonCatalogName;
			config.ID = obj.config.returnReasonId;
			config.RETURN_REASON_NAME = obj.config.returnReasonName;
			configs.push(config);
			
			this.catalogTree.loadByData(configs);
			this.catalogTree.showTitleCheck = false;
			this.catalogTree.items[0].setChecked(true);
			this.catalogTree.items[0].setCheckDisabled(true);
			
			for(var i=0; i<this.form.endActivity.options.length; i++){
				if(window.dialogArguments.config.endActivityId == this.form.endActivity.options[i].value){
					this.form.endActivity.selectedIndex = i;
					break;
				}
			}

			if(window.dialogArguments.config.startMode == "Manual"){
				this.form.startMode.selectedIndex = 0;
			}else if(window.dialogArguments.config.startMode == "ChangeReturnback"){
				this.form.startMode.selectedIndex = 1;
			}
		}
	}
	
	this.changeStart = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;

		if(parseInt(activityId) == 0){
			this.noStartReason();
			return;
		}
		
		document.all.changeReasonTr.style.display = "none";
		document.all.reasonTypeTr.style.display = "";

		var EndActivities = new Array();
		
		var nodePointer;
		
		for(var i=0; i<obj.allNodes.length; i++){
			if(obj.allNodes[i].activity.id == activityId){
				nodePointer = obj.allNodes[i];
				break;
			}
		}
		
		var linePointer = null;
		
		while(true){
			for(var i=0; i<obj.allLines.length; i++){
				if(obj.allLines[i].endNode == nodePointer){
					linePointer = obj.allLines[i];
					break;
				}
			}
			
			if(linePointer == null){
				break;
			}
			
			if(linePointer.startNode != null){
				nodePointer = linePointer.startNode;
				//add by Liang.huawei 08.08.04 for ur:28264
				if(nodePointer.getNodeType() == "Parallel" && canSelCondTacheObj != null && canSelCondTacheObj.value=="Y"){
					for(var bl=0;bl<nodePointer.branches.length;bl++){
						for(var bnl=0;bnl<nodePointer.branches[bl].nodes.length;bnl++){
							var bNote = nodePointer.branches[bl].nodes[bnl];
							if(bNote.getNodeType() == "Tache" || bNote.getNodeType() == "Start"){
								EndActivities.push(bNote.activity);
							}
						}
					}
				}
			}else{
				var found = false;
				for(var i=0; i<obj.allNodes.length; i++){
					if(found){
						break;
					}
					
					if(obj.allNodes[i].getNodeType() == "Parallel"){
						for(var j=0; j<obj.allNodes[i].branches.length; j++){
							if(found){
								break;
							}
							
							for(var k=0; k<obj.allNodes[i].branches[j].nodes.length; k++){
								if(found){
									break;
								}
								
								if(obj.allNodes[i].branches[j].nodes[k].activity.id == nodePointer.activity.id){
									nodePointer = obj.allNodes[i];
									found = true;
									break;
								}
							}
						}
					}
				}
			}
			
			if((nodePointer.getNodeType() == "Tache") || (nodePointer.getNodeType() == "Start")){
				EndActivities.push(nodePointer.activity);
			}
			linePointer = null;
		}
		
		this.form.endActivity.innerHTML = "";
		for(var i=0; i<EndActivities.length; i++){
			var option = document.createElement("option");
			option.value = EndActivities[i].id;
			option.innerText = EndActivities[i].name;
			this.form.endActivity.appendChild(option);
		}
		
		this.form.startMode.innerHTML = "";
		var option = document.createElement("option");
		option.innerText = "<%=getI18NResource("designer.automatic")%>";
		this.form.startMode.appendChild(option);

		option = document.createElement("option");
		option.innerText = "<%=getI18NResource("designer.return_back")%>";
		this.form.startMode.appendChild(option);

		this.form.startMode.selectedIndex = 1;
		
		if(window.dialogArguments.operation == "add"){
			if(document.all.reasonType[1].checked){
				this.queryWaitReason();
			}else if(document.all.reasonType[2].checked){
				this.queryPauseReason();
			}else {
				this.queryReturnReason();
			}
		}else{
			var configs = new Array();
			var config = new Object();
			
			config.REASON_TYPE = obj.config.reasonCatalogId;
			config.REASON_TYPE_NAME = obj.config.reasonCatalogName;
			config.ID = obj.config.returnReasonId;
			config.RETURN_REASON_NAME = obj.config.returnReasonName;
			configs.push(config);
			
			this.catalogTree.loadByData(configs);
			this.catalogTree.showTitleCheck = false;
			this.catalogTree.items[0].setChecked(true);
			this.catalogTree.items[0].setCheckDisabled(true);
			
			for(var i=0; i<this.form.endActivity.options.length; i++){
				if(window.dialogArguments.config.endActivityId == this.form.endActivity.options[i].value){
					this.form.endActivity.selectedIndex = i;
					break;
				}
			}
			
			if(window.dialogArguments.config.startMode == "Automatic"){
				this.form.startMode.selectedIndex = 0;
			}else if(window.dialogArguments.config.startMode == "Returnback"){
				this.form.startMode.selectedIndex = 1;
			}else if(window.dialogArguments.config.startMode == "Manual"){
				this.form.startMode.selectedIndex = 2;
			}
		}
	}
	
	this.submit = function(){
		if(this.form.endActivity.options.length == 0){
			ErrorHandle("<%=getI18NResource("designer.no_returnable_node")%>");
			return;
		}
		
		var result = new Object();
		
		var startActivityId = this.form.startActivity.options[this.form.startActivity.selectedIndex].value;
		var startActivityName = this.form.startActivity.options[this.form.startActivity.selectedIndex].innerText;
		var endActivityId = this.form.endActivity.options[this.form.endActivity.selectedIndex].value;
		var endActivityName = this.form.endActivity.options[this.form.endActivity.selectedIndex].innerText;
		var startMode;
		
		if(parseInt(this.form.startActivity.options[this.form.startActivity.selectedIndex].value) == 0){
			switch(this.form.startMode.selectedIndex){
				case 0:{	
					startMode = "Manual";
					break;
				}
				case 1:{
					startMode = "ChangeReturnback";
					break;
				}
			}
		}else{
			if(this.form.reasonType[0].checked){
				switch(this.form.startMode.selectedIndex){
					case 0:{
						startMode = "Automatic";
						break;
					}
					case 1:{
						startMode = "Returnback";
						break;
					}
					case 2:{
						startMode = "Manual";
						break;
					}
				}
			}else if(this.form.reasonType[1].checked){
				startMode = "Wait";
			}else {
				startMode = "Pause";
			}
		}

		var autoToManual = false;
		if(this.form.autoToManual[1].checked){
			autoToManual = true;
		}
		
		result = new Array();
		
		for(var i=0; i<this.catalogTree.itemsLength; i++){
			if(this.catalogTree.items[i].getChecked()){
				var reason = new Object();
				reason.reasonCatalogId = this.catalogTree.items[i].REASON_TYPE;
				reason.reasonCatalogName = this.catalogTree.items[i].REASON_TYPE_NAME;
				reason.returnReasonId = this.catalogTree.items[i].ID;
				reason.returnReasonName = this.catalogTree.items[i].RETURN_REASON_NAME;
				reason.startActivityId = startActivityId;
				reason.startActivityName = startActivityName;
				reason.endActivityId = endActivityId;
				reason.endActivityName = endActivityName;
				//mod by chen.zhikun2 2010-05-17 UR-54984
				if(reason.reasonCatalogId == '10P')
					reason.startMode = 'Pause';
				else
					reason.startMode = startMode;
					
				reason.autoToManual = autoToManual;
				result.push(reason);
			}
		}
		
		if(result.length == 0){
			ErrorHandle("<%=getI18NResource("designer.exception_not_select")%>");
			return;
		}
		
		window.returnValue = result;
		window.close();
	}
	
	this.cancel = function(){
		window.close();
	}
}
//////////////////////////////////////////
</script>
</html>