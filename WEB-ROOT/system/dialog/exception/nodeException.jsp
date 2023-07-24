<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title> <%=getI18NResource("designer.node_exception_config")%> </title>
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
		<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
	</tr>
	<form name="configForm" onsubmit="return false">
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.exception_activity")%></td>
		<td class="td_grey">
			<input type="text" id="startActivityName" name="startActivityName" readonly/>
			<input type="hidden" id="startActivityId" name="startActivityId"/>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.return_activity")%></td>
		<td class="td_grey">
			<input type="text" id="endActivityName" name="endActivityName" readonly/>
			<input type="hidden" id="endActivityId" name="endActivityId"/>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.auto_to_manual")%></td>
		<td>
			<input type="radio" class="checkbox" name="autoToManual" checked/><%=getI18NResource("designer.no")%>
			<input type="radio" class="checkbox" name="autoToManual"/><%=getI18NResource("designer.yes")%>
		</td>
	</tr>
	<tr>
		<td class="td_blue_txt"><%=getI18NResource("designer.exception_type")%></td>
		<td>
			<input type="radio" class="checkbox" name="reasonType" checked onclick="exceptionOperation.queryReturnReason()"/><%=getI18NResource("designer.rollback_order")%>
			<input type="radio" class="checkbox" name="reasonType" onclick="exceptionOperation.queryWaitReason()"/><%=getI18NResource("designer.wait_install")%>
			<input type="radio" class="checkbox" name="reasonType" onclick="exceptionOperation.queryPauseReason()"/><%=getI18NResource("tache.backTai_id")%>
		</td>
	</tr>
	<!-- add by chen.jixin 2010-09-09 for ur:61223 begin -->
	<tr>
		<td class="td_blue_txt">
		<%=getI18NResource("designer.exception_name")%>
		</td>
		<td>
		<input type="text" id="exceptionName" name="exceptionName" /><input class="button" type="button" id="queryExceptionButton" name="queryExceptionButton" value="<%=getI18NResource("designer.query")%>" onclick="exceptionOperation.queryReason()" />
		</td>
	</tr>
	<!-- ur:61233 end -->
	<!-- add by chen.jixin 2010-08-19 for ur:60240 begin-->
	<tr id="displayCheckbox" name="displayCheckbox" style="display:none">
	<td>
	</td>
	<td>
	<input type="checkbox" name="isDisplay" id="isDisplay" class="checkbox" checked onclick="exceptionOperation.showLocalAreaReturnReason()">
	<%=getI18NResource("designer.display_higher_area_exception_reason")%>
	</td>
	</tr>
	<!-- ur:60240 end -->
	<tr>
		<td colspan="2">
			<fieldset>
				<legend id="exception_reason"><%=getI18NResource("designer.exception_reason")%></legend>
				<ZTESOFT:treelist showCheck="true" id="reasonTree" name="reasonTree" sorted="true" class="treelist" width="100%" height="250">
					<ZTESOFT:columns>
						<ZTESOFT:column width="60%" display="true" displayText="<%=getI18NResource("designer.exception_name")%>" propertyName="RETURN_REASON_NAME" sortType="String"/>
						<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("designer.type")%>" propertyName="REASON_TYPE_NAME" />
						<ZTESOFT:column display="false" propertyName="AUDIT_FLAG_NAME" />
						<ZTESOFT:column display="false" propertyName="ID" />
						<ZTESOFT:column display="false" propertyName="REASON_TYPE" />
					</ZTESOFT:columns>
				</ZTESOFT:treelist>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_blue_txt"><%=getI18NResource("designer.dest_activity_start_mode")%></td>
		<td class="td_grey">
			<input type="radio" class="checkbox" name="startMode" /><span id="auto"><%=getI18NResource("designer.automatic")%></span>
			<input type="radio" class="checkbox" name="startMode" checked /><span id="back"><%=getI18NResource("designer.return_back")%></span>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="40" valign="bottom">
			<input type="button" class="button" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick="exceptionOperation.submit();" />
			<input type="button" class="button" id="canBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="exceptionOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>
</body>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-09-20
// Author : Xu.fei3
// commits: View or set exception configuration Properties
/////////////////////////////////////////////////////
//add by chen.jixin 2010-09-09 for ur:61223 begin
_import("public.script.BSCommon");
//ur:61223 end
var args = window.dialogArguments;
var exceptionOperation = new ExceptionOperation();
//add by ji.dong 2010-06-07 ur:56260 begin
var isLeach = null; 
var areaId = "";
//end

exceptionOperation.init();

//////////////////////////////////////////
function ExceptionOperation(){
	//add by chen.jixin 2010-09-09 for ur:61223 begin
	_extends(this, BSCommon, '"configForm"');
	//ur:61223 end
	this.form = document.all.configForm;
	this.reasonTree = document.all.reasonTree;

	this.init = function(){
		//add by ji.dong 2010-06-07 ur:56260 begin
		isLeach = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager", "findParameter", "IS_LEACH_RETURNREASON");
		if (isLeach && isLeach.value == 'Y'){
			areaId = callRemoteFunctionNoTrans("com.ztesoft.iom.orderdesign.dao.CommonDAOImpl", "getAreaByFlowId", args.flowId);
		}
		//end
		//add by chen.jixin 2010-08-19 for ur:60240 begin
		if (isLeach && isLeach.value == 'Y'){
		document.all.displayCheckbox.style.display= "";
		}
		//ur:60240 end
		this.form.startActivityId.value = args.startNode.activity.id;
		this.form.startActivityName.value = args.startNode.activity.name;
		this.form.endActivityId.value = args.endNode.activity.id;
		this.form.endActivityName.value = args.endNode.activity.name;

		this.queryReturnReason();
	}
	//add by chen.jixin 2010-08-19 for ur:60240 begin
	this.showLocalAreaReturnReason =function(){
		var isDisplay=document.all.isDisplay.checked;
		if(this.form.reasonType[0].checked == true){
			this.queryReturnReason();
		}else if(this.form.reasonType[1].checked == true){
			this.queryWaitReason();
		}else if(this.form.reasonType[2].checked == true){
			this.queryPauseReason();
		}
	}
	//ur:60240 end
	
	//add by chen.jixin 2010-09-09 for ur:61223 begin
	this.queryReason = function(){
		var obj = window.dialogArguments;
		var exceptionName= this.form.exceptionName.value;
		if( exceptionName == ""){
			this.showLocalAreaReturnReason();
			return;
		}else{
			var isDisplay = document.all.isDisplay.checked;
			var tacheId;
			for(var i=0; i<args.startNode.activity.extendedAttributes.length; i++){
				if(args.startNode.activity.extendedAttributes[i].name == "ExTacheId"){
					tacheId = args.startNode.activity.extendedAttributes[i].value;
					break;
				}
			}	
			if(this.form.reasonType[0].checked){//退单
				if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryReturnReasonByAreaTacheByExceptionName");		
				}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryReturnReasonByLocalAreaTacheByExceptionName");		
				}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasonData = this.callRemoteFunctionQuery("qryReturnReasonByTacheByExceptionName");
				}			
			}else if(this.form.reasonType[1].checked){//待装
				if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryWaitReasonByAreaTacheByExceptionName");		
				}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryWaitReasonByLocalAreaTacheByExceptionName");		
				}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasonData = this.callRemoteFunctionQuery("qryWaitReasonByTacheByExceptionName");
				}		
			}else if(this.form.reasonType[2].checked){//后台缓装
				if (isLeach && isLeach.value == 'Y' && isDisplay == true){	
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryPauseReasonByAreaTacheByExceptionName");		
				}else if(isLeach && isLeach.value == 'Y' && isDisplay == false){//只显示当地异常原因
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					this.addParam(2, "AREA_ID", areaId);
					var reasonData = this.callRemoteFunctionQuery("qryPauseReasonByLocalAreaTacheByExceptionName");		
				}else{
					this.clearParam();
					this.addParam(2, "EXCEPTION_NAME", exceptionName);
					this.addParam(2, "TACHE_ID", tacheId);
					var reasonData = this.callRemoteFunctionQuery("qryPauseReasonByTacheByExceptionName");
				}				
			}			
			this.reasonTree.clear();
			this.reasonTree.loadByData(reasonData);
		}
	}
	//ur:61223 end
	
	this.queryReturnReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = args.startNode.activity.id;
		var tacheId;
		for(var i=0; i<args.startNode.activity.extendedAttributes.length; i++){
			if(args.startNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = args.startNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin
		if (isLeach && isLeach.value == 'Y'){
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

		for (var i=0; i<args.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((args.selectedReasons[i].startActivityId == activityId)
					&& (args.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.reasonTree.loadByXML(xmlDoc.xml);
	}

	this.queryWaitReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = args.startNode.activity.id;
		var tacheId;
		for(var i=0; i<args.startNode.activity.extendedAttributes.length; i++){
			if(args.startNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = args.startNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin
		if (isLeach && isLeach.value == 'Y'){
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

		for (var i=0; i<args.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((args.selectedReasons[i].startActivityId == activityId)
					&& (args.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.reasonTree.loadByXML(xmlDoc.xml);
	}

	this.queryPauseReason = function(){
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		this.form.exceptionName.value="";
		//ur:61223 end
		var activityId = args.startNode.activity.id;
		var tacheId;
		for(var i=0; i<args.startNode.activity.extendedAttributes.length; i++){
			if(args.startNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = args.startNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		//modify by chen.jixin 2010-08-19 for ur:60240 begin
		var isDisplay = document.all.isDisplay.checked;
		//modify by ji.dong 2010-06-07 ur:56260 begin
		if (isLeach && isLeach.value == 'Y'){
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

		for (var i=0; i<args.selectedReasons.length; i++){
			for(var j=0; j<root.childNodes.length; j++){
				if((args.selectedReasons[i].startActivityId == activityId)
					&& (args.selectedReasons[i].returnReasonId == root.childNodes[j].getAttribute("ID"))){
					root.removeChild(root.childNodes[j]);
					j--;
					break;
				}
			}
		}
		this.reasonTree.loadByXML(xmlDoc.xml);
	}

	this.submit = function(){
		var result = new Array();

		var startMode;
		if(this.form.reasonType[0].checked){
			if(this.form.startMode[0].checked){
				startMode = "Automatic";
			}else{
				startMode = "Returnback";
			}
		}else if(this.form.reasonType[1].checked){
			startMode = "Wait";
		}else {
			startMode = "Pause";
		}

		var autoToManual = false;
		if(this.form.autoToManual[1].checked){
			autoToManual = true;
		}

		for(var i=0; i<this.reasonTree.itemsLength; i++){
			if(this.reasonTree.items[i].getChecked()){
				var reason = new Object();
				reason.reasonCatalogId = this.reasonTree.items[i].REASON_TYPE;
				reason.reasonCatalogName = this.reasonTree.items[i].REASON_TYPE_NAME;
				reason.returnReasonId = this.reasonTree.items[i].ID;
				reason.returnReasonName = this.reasonTree.items[i].RETURN_REASON_NAME;
				reason.startActivityId = this.form.startActivityId.value;
				reason.startActivityName = this.form.startActivityName.value;
				reason.endActivityId = this.form.endActivityId.value;
				reason.endActivityName = this.form.endActivityName.value;
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