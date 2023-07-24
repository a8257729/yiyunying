<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.activity_config")%> </title>
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
<table width="100%">
<form name="nodeForm" id="nodeForm" onsubmit="return false">
<tr>
<td>
	<ZTEsoft:tabStrip class="tab_ioms" selectedIndex="0" id="firstTabScript">
		<ZTEsoft:tabs height="180">
			<ZTEsoft:page Text="<%=getI18NResource("designer.basic_property")%>" width="100%" height="100%">
				<table width="100%">
					<tr>
						<td width="80"></td>
						<td width="130"></td>
						<td width="80"></td>
						<td width="130"></td>
					</tr>
					<tr>
						<td class="td_blue_txt"><%=getI18NResource("designer.tache_full_path")%></td>
						<td class="td_grey" colspan="3">
							<input type="text" id="tachePath" name="tachePath" style="width:343;" readonly />
						</td>
					</tr>
					<tr>
						<td class="td_blue_txt"><%=getI18NResource("designer.activity_name")%></td>
						<td class="td_grey">
							<input type="hidden" id="nodeId" name="nodeId" />
							<input type="hidden" id="nodeCode" name="nodeCode" />
							<input type="text" id="nodeName" name="nodeName" />
						</td>
						<td class="td_blue_txt"><%=getI18NResource("designer.operation_mode")%></td>
						<td class="td_grey">
							<select class="select_htc" id="operType" name="operType">
								<option value="0"><%=getI18NResource("designer.uninstall")%></option>
								<option value="1" selected><%=getI18NResource("designer.install")%></option>
							</select>
						</td>
					</tr>
					<tr style="display:none">
						<td class="td_blue_txt"><%=getI18NResource("designer.start_mode")%></td>
						<td class="td_grey">
							<select class="select_htc" id="startMode" name="startMode">
								<option value="Automatic" selected><%=getI18NResource("designer.automatic")%></option>
								<option value="Manual"><%=getI18NResource("designer.manual")%></option>
							</select>
						</td>
						<td class="td_blue_txt"><%=getI18NResource("designer.end_mode")%></td>
						<td class="td_grey">
							<select class="select_htc" id="finishMode" name="finishMode">
								<option value="Automatic" selected><%=getI18NResource("designer.automatic")%></option>
								<option value="Manual"><%=getI18NResource("designer.manual")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_blue_txt"><%=getI18NResource("designer.in_mode")%></td>
						<td class="td_grey">
							<select class="select_htc" id="joinType" name="joinType">
								<option value="JoinAnd" selected><%=getI18NResource("designer.parallel")%></option>
								<option value="JoinXor"><%=getI18NResource("designer.select")%></option>
							</select>
						</td>
						<td class="td_blue_txt"><%=getI18NResource("designer.out_mode")%></td>
						<td class="td_grey">
							<select class="select_htc" id="splitType" name="splitType">
								<option value="SplitAnd" selected><%=getI18NResource("designer.parallel")%></option>
								<option value="SplitXor"><%=getI18NResource("designer.select")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_blue_txt" id="repealable_or_not"><%=getI18NResource("designer.can_rollback_order_or_not")%></td>
						<td class="td_grey">
							<select class="select_htc" id="withdraw" name="withdraw">
								<option value="True" selected><%=getI18NResource("designer.yes")%></option>
								<option value="False"><%=getI18NResource("designer.no")%></option>
							</select>
						</td>
						<td class="td_blue_txt" id="modify_or_not"><%=getI18NResource("designer.can_change_or_not")%></td>
						<td class="td_grey">
							<select class="select_htc" id="change" name="change">
								<option value="True" selected><%=getI18NResource("designer.yes")%></option>
								<option value="False"><%=getI18NResource("designer.no")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_blue_txt" colspan="2" id="xietong"><%=getI18NResource("designer.is_collaborate_tache")%></td>
						<td class="td_grey" colspan="2">
							<select class="select_htc" id="collaborate" name="collaborate">
								<option value="True"><%=getI18NResource("designer.yes")%></option>
								<option value="False" selected><%=getI18NResource("designer.no")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_blue_txt"><%=getI18NResource("designer.comment")%></td>
						<td colspan="3" class="td_grey">
							<textarea cols=50 rows=3 id="description" name="description"></textarea>
						</td>
					</tr>
				</table>
			</ZTEsoft:page>
			<ZTEsoft:page Text="<%=getI18NResource("designer.parameter")%>" width="100%" height="100%">
				<table width="100%">
					<tr>
						<td width="90%">
							<ZTESOFT:treelist id="paramTree" name="paramTree" class="treelist" width="100%" height="170">
								<ZTESOFT:columns>
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.parameter_name")%>" propertyName="paramName" />
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.data_type")%>" propertyName="dataType" />
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.parameter_type")%>" propertyName="paramType" />
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.binding_relation")%>" propertyName="actualParameter" />
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.display_or_not")%>" propertyName="isDisplay" />
									<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.comment")%>" propertyName="description" />
								</ZTESOFT:columns>
							</ZTESOFT:treelist>
						</td>
						<td valign="middle" align="center" name="buttonTd" id="buttonTd">
							<input type="button" name="addButton" id="addButton" class="button_img" style="width:60px" value="<%=getI18NResource("designer.new")%>" onclick="nodeOperation.addParam();" />
							<br />
							<br />
							<input type="button" name="editButton" id="editButton" class="button_img" style="width:60px" value="<%=getI18NResource("designer.edit")%>" onclick="nodeOperation.modifyParam();" />
							<br />
							<br />
							<input type="button" name="deleteButton" id="deleteButton" class="button_img" style="width:60px" value="<%=getI18NResource("designer.delete")%>" onclick="nodeOperation.deleteParam();" />
						</td>
					</tr>
				</table>
			</ZTEsoft:page>
			<ZTEsoft:page Text="<%=getI18NResource("designer.participant")%>" width="100%" height="100%">
				<table width="100%">
					<tr>
						<td width="30" class="td_grey">
							<input type="radio" class="checkbox" name="participantRadio" onclick="nodeOperation.radioClick();" />
						</td>
						<td class="td_blue_txt" id="starter"><%=getI18NResource("designer.starter")%></td>
						<td class="td_grey" colspan="2">
							<select style="width:90px" class="select_htc" id="starter" name="starter">
								<option value="StarterStaff" id="StarterStaff" selected><%=getI18NResource("designer.starter_staff")%></option>
								<option value="StarterOrganization" id="start_org"><%=getI18NResource("designer.starter_org")%></option>
								<option value="StarterPosition" id="StarterPosition"><%=getI18NResource("designer.starter_position")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="30" class="td_grey">
							<input type="radio" class="checkbox" name="participantRadio" onclick="nodeOperation.radioClick();" />
						</td>
						<td class="td_blue_txt" id="some_tache"><%=getI18NResource("designer.some_tache")%></td>
						<td class="td_grey">
							<input type="hidden" id="tacheId" name="tacheId" />
							<input type="text" id="tacheName" name="tacheName" style="width:100px;" disabled /><input type="button" id="chooseTacheImg" name="chooseTacheImg" class="button_pop" onclick="nodeOperation.chooseTache();" />
						</td>
						<td class="td_grey">
							<select style="width:90px" class="select_htc" id="tachePerformer" name="tachePerformer">
								<option value="PerformerStaff" id="PerformerStaff" selected><%=getI18NResource("designer.tache_performer")%></option>
								<option value="PerformerOrganization" id="PerformerOrg"><%=getI18NResource("designer.tache_performer_org")%></option>
								<option value="PerformerPosition" id="PerformerPosi"><%=getI18NResource("designer.tache_performer_position")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="30" class="td_grey">
							<input type="radio" class="checkbox" name="participantRadio" onclick="nodeOperation.radioClick();" />
						</td>
						<td class="td_blue_txt" id="org_or_position"><%=getI18NResource("designer.some_org_position")%></td>
						<td colspan="2" class="td_grey">
							<input type="hidden" id="participantId" name="participantId" />
							<input type="hidden" id="participantType" name="participantType" />
							<input type="text" id="participantName" name="participantName" style="width:220px;" disabled /><input type="button" id="chooseOrgImg" name="chooseOrgImg" class="button_pop" onclick="nodeOperation.chooseParticipant();" />
						</td>
					</tr>
					<tr>
						<td width="30" class="td_grey">
							<input type="radio" class="checkbox" name="participantRadio" onclick="nodeOperation.radioClick();" />
						</td>
						<td class="td_blue_txt" id="decide_by_tache"><%=getI18NResource("designer.decide_by_former_tache")%></td>
						<td colspan="2" class="td_grey">
						</td>
					</tr>
					<tr>
						<td width="30" class="td_grey">
							<input type="radio" class="checkbox" name="participantRadio" checked onclick="nodeOperation.radioClick();" />
						</td>
						<td class="td_blue_txt" id="decide_by_rule"><%=getI18NResource("designer.decide_by_rule")%></td>
						<td colspan="2" class="td_grey">
						</td>
					</tr>
				</table>
			</ZTEsoft:page>
		</ZTEsoft:tabs>
	</ZTEsoft:tabstrip>
	</td>
</tr>
<tr>
	<td align="center" height="20" valign="bottom">
			<input type="button" class="button" id="submitButton" style="width:60px" name="submitButton" width="80" value="<%=getI18NResource("designer.ok")%>" onclick="nodeOperation.submit();" />
			<input type="button" class="button" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" id="cancelButton" name="cancelButton" onclick="nodeOperation.cancel();" />
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
// commits: View or set node Properties
/////////////////////////////////////////////////////

_import("designer.js.constant");

_import("designer.js.core.abstractElement");
_import("designer.js.core.activity");
_import("designer.js.core.participant");
_import("designer.js.core.application");

/* \u5168\u5c40\u53d8\u91cf */
var nodeOperation = new NodeOperation();
ExecWait("nodeOperation.init()");

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function NodeOperation(){
	this.form = document.all.nodeForm;
	this.paramTree = document.all.paramTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){
		var obj = window.dialogArguments;
		
		this.form.tachePath.value = obj.tachePath;
		
		//\u8fd9\u91cc\u662f\u6839\u636eactivity\u6765\u521d\u59cb\u5316\u57fa\u672c\u5c5e\u6027

		this.form.nodeId.value = obj.activity.id;
		this.form.nodeName.value = obj.activity.name;
		
		for(var i=0; i<obj.activity.extendedAttributes.length; i++){
			if(obj.activity.extendedAttributes[i].name == "ExTacheCode"){
				this.form.nodeCode.value = obj.activity.extendedAttributes[i].value;
			}
			
			if(obj.activity.extendedAttributes[i].name == "ExOperType"){
				this.form.operType.selectedIndex = parseInt(obj.activity.extendedAttributes[i].value);
			}
			
			if(obj.activity.extendedAttributes[i].name == "ExWithdraw"){
				if(obj.activity.extendedAttributes[i].value == "False"){
					this.form.withdraw.selectedIndex = 1;
				}
			}
			
			if(obj.activity.extendedAttributes[i].name == "ExChange"){
				if(obj.activity.extendedAttributes[i].value == "False"){
					this.form.change.selectedIndex = 1;
				}
			}
			
			if(obj.activity.extendedAttributes[i].name == "ExCollaborate"){
				if(obj.activity.extendedAttributes[i].value == "True"){
					this.form.collaborate.selectedIndex = 0;
				}
			}
		}
		
		//\u542f\u52a8\u3001\u7ed3\u675f\u65b9\u5f0f

		if(obj.activity.startMode == "Manual"){
			this.form.startMode.selectedIndex = 1;
		}
		
		if(obj.activity.finishMode == "Manual"){
			this.form.finishMode.selectedIndex = 1;
		}
		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f\u521d\u59cb\u5316

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
		
		this.form.description.value = obj.activity.description || "";
		
		//\u8fd9\u91cc\u662f\u6839\u636eapplication\u6765\u521d\u59cb\u5316\u53c2\u6570\u5217\u8868
		//\u56e0\u4e3a\u5b9e\u53c2\u662f\u4fdd\u5b58\u5728activity\u91cc\u9762\u7684\uff0c\u6240\u4ee5\u8fd9\u91cc\u8981\u5408\u5e76
		var paramArray = new Array();
		for(var i=0; i<obj.application.formalParameters.length; i++){
			var param = new Object();
			param.paramName = obj.application.formalParameters[i].paramName;
			param.dataType = obj.application.formalParameters[i].dataType;
			param.paramType = obj.application.formalParameters[i].paramType;
			param.isDisplay = obj.application.formalParameters[i].isDisplay;
			param.description = obj.application.formalParameters[i].description;
			param.actualParameter = obj.activity.implementation.tool.actualParameters[i];
			paramArray.push(param);
		}
		
		this.paramTree.loadByData(paramArray);
		
		//\u8fd9\u91cc\u662f\u6839\u636eparticipant\u6765\u521d\u59cb\u5316\u53c2\u4e0e\u8005\u5217\u8868

		//\u53d6\u5f97\u6269\u5c55\u5c5e\u6027

		var exTempletCode = RULE;
		var exTempletParams = "";
		var exName = "";
		
		for(var i=0; i<obj.participant.extendedAttributes.length; i++){
			if(obj.participant.extendedAttributes[i].name == "ExTempletCode"){
				exTempletCode = obj.participant.extendedAttributes[i].value;
			}
			
			if(obj.participant.extendedAttributes[i].name == "ExTempletParams"){
				exTempletParams = obj.participant.extendedAttributes[i].value;
			}
			
			if(obj.participant.extendedAttributes[i].name == "ExName"){
				exName = obj.participant.extendedAttributes[i].value;
			}
		}
		
		//\u8bbe\u7f6e\u4e00\u4e0b\u5355\u9009\u6846\u7684\u9009\u62e9\u72b6\u6001	
		if((exTempletCode >= TACHE_STARTER) && (exTempletCode < TACHE_PERFORMER)){
			this.form.participantRadio[0].checked = true;
		}else if((exTempletCode >= TACHE_PERFORMER) && (exTempletCode < ORGANIZATION)){
			this.form.participantRadio[1].checked = true;
		}else if((exTempletCode >= ORGANIZATION) && (exTempletCode < LAST_TACHE)){
			this.form.participantRadio[2].checked = true;
		}else if(exTempletCode == LAST_TACHE){
			this.form.participantRadio[3].checked = true;
		}else{
			this.form.participantRadio[4].checked = true;
		}
		
		//\u8fd9\u91cc\u662f\u8bbe\u7f6e\u6570\u636e

		switch(parseInt(exTempletCode)){
			case TACHE_STARTER:{
				this.form.participantRadio[0].checked = true;
				this.form.starter.options[0].selected = true;
				break;
			}
			case TACHE_STARTER_ORGANIZATION:{
				this.form.participantRadio[0].checked = true;
				this.form.starter.options[1].selected = true;
				break;
			}
			case TACHE_STARTER_POSITION:{
				this.form.participantRadio[0].checked = true;
				this.form.starter.options[2].selected = true;
				break;
			}
			case TACHE_PERFORMER:{
				this.form.participantRadio[1].checked = true;
				this.form.tachePerformer.options[0].selected = true;
				this.form.tacheId.value = exTempletParams;
				this.form.tacheName.value = exName;
				break;
			}
			case TACHE_PERFORMER_ORGANIZATION:{
				this.form.participantRadio[1].checked = true;
				this.form.tachePerformer.options[1].selected = true;
				this.form.tacheId.value = exTempletParams;
				this.form.tacheName.value = exName;
				break;
			}
			case TACHE_PERFORMER_POSITION:{
				this.form.participantRadio[1].checked = true;
				this.form.tachePerformer.options[2].selected = true;
				this.form.tacheId.value = exTempletParams;
				this.form.tacheName.value = exName;
				break;
			}
			case ORGANIZATION:{
				this.form.participantRadio[2].checked = true;
				this.form.participantType.value = "ORG";
				this.form.participantId.value = exTempletParams;
				this.form.participantName.value = exName;
				break;
			}
			case POSITION:{				
				this.form.participantRadio[2].checked = true;
				this.form.participantType.value = "JOB";
				this.form.participantId.value = exTempletParams;
				this.form.participantName.value = exName;
				break;
			}
			case STAFF:{
				this.form.participantRadio[2].checked = true;
				this.form.participantType.value = "STA";
				this.form.participantId.value = exTempletParams;
				this.form.participantName.value = exName;
				break;
			}
			case LAST_TACHE:{
				this.form.participantRadio[3].checked = true;
				break;
			}
			case RULE:{
				this.form.participantRadio[4].checked = true;
				break;
			}
		}
		
		this.radioClick();

		//\u4e0d\u5728\u5e76\u884c\u7ed3\u6784\u4e2d\u7684\u4e0d\u80fd\u8bbe\u7f6e\u4e3b\u534f\u540c

		if (obj.parentParallel == null){
			this.form.collaborate.disabled = true;
		}
		
		//\u67e5\u770b\u72b6\u6001\u4e0b\u7981\u6b62\u64cd\u4f5c
		if(obj.operation == "view"){
			this.form.submitButton.style.display = "none";
			this.form.cancelButton.value = "<%=getI18NResource("designer.close_window")%>";
			
			//\u57fa\u672c\u4fe1\u606f
			this.form.nodeName.disabled = true;
			this.form.operType.disabled = true;
			this.form.startMode.disabled = true;
			this.form.finishMode.disabled = true;
			this.form.joinType.disabled = true;
			this.form.splitType.disabled = true;
			this.form.change.disabled = true;
			this.form.withdraw.disabled = true;
			this.form.collaborate.disabled = true;
			this.form.description.disabled = true;
			
			//\u53c2\u6570\u6309\u94ae
			this.form.addButton.style.display = "none";
			this.form.editButton.style.display = "none";
			this.form.deleteButton.style.display = "none";
			document.all.buttonTd.style.display = "none";
			
			//\u53c2\u4e0e\u8005

			for(var i=0; i<this.form.participantRadio.length; i++){
				this.form.participantRadio[i].disabled = true;
			}
			
			this.form.starter.disabled = true;
			this.form.tacheName.disabled = true;
			document.all.chooseTacheImg.disabled = true;
			this.form.tachePerformer.disabled = true;
			this.form.participantName.disabled = true;
			document.all.chooseOrgImg.disabled = true;
		}
	}
	
	//\u70b9\u51fb\u53c2\u4e0e\u8005\u79cd\u7c7b\u7684\u5355\u9009\u6309\u94ae

	this.radioClick = function(){
		//\u5148\u7981\u7528\u6240\u6709\u8f93\u5165

		this.disableParticipantForm();
		
		for(var i=0; i<this.form.participantRadio.length; i++){
			if(this.form.participantRadio[i].checked){
				switch(i){
					//\u542f\u52a8\u8005

					case 0:{
						this.form.starter.disabled = false;
						break;
					}
					//\u67d0\u73af\u8282

					case 1:{
						this.form.tacheName.disabled = false;
						document.all.chooseTacheImg.disabled = false;
						this.form.tachePerformer.disabled = false;
						break;
					}
					//\u67d0\u7ec4\u7ec7\u6216\u8005\u804c\u4f4d

					case 2:{
						this.form.participantName.disabled = false;
						document.all.chooseOrgImg.disabled = false;
						break;
					}
					//\u7531\u524d\u9762\u73af\u8282\u6307\u5b9a

					case 3:{
						break;
					}
					//\u7531\u5de5\u5355\u6d3e\u53d1\u89c4\u5219\u6307\u5b9a

					case 4:{
						break;
					}
				}
			}
		}
	}
	
	//\u7981\u7528\u53c2\u4e0e\u8005\u9009\u62e9\u8868\u5355
	this.disableParticipantForm = function(){
		this.form.starter.disabled = true;
		
		this.form.tacheName.disabled = true;
		this.form.tachePerformer.disabled = true;
		
		this.form.participantName.disabled = true;

		document.all.chooseTacheImg.disabled = true;
		document.all.chooseOrgImg.disabled = true;
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Object();
		result.activity = new Activity();
		result.application = new Application();
		result.participant = new Participant();
		
		//\u7ec4\u88c5activity\u57fa\u672c\u5c5e\u6027

		result.activity.id = this.form.nodeId.value;
		result.activity.name = this.form.nodeName.value;
		
		if(this.form.startMode.selectedIndex == 1){
			result.activity.startMode = "Manual";
		}else{
			result.activity.startMode = "Automatic";
		}
		
		if(this.form.finishMode.selectedIndex == 1){
			result.activity.finishMode = "Manual";
		}else{
			result.activity.finishMode = "Automatic";
		}
		
		result.activity.extendedAttributes = window.dialogArguments.activity.extendedAttributes;
		
		//\u64cd\u4f5c\u65b9\u5f0f
		for(var i=0; i<result.activity.extendedAttributes.length; i++){
			if(result.activity.extendedAttributes[i].name == "ExOperType"){
				result.activity.extendedAttributes.splice(i, 1);
				break;
			}
		}
		
		var operType = new Object();
		operType.name = "ExOperType";
		operType.value = this.form.operType.selectedIndex;
		result.activity.extendedAttributes.push(operType);
		
		//\u662f\u5426\u53ef\u64a4\u5355

		for(var i=0; i<result.activity.extendedAttributes.length; i++){
			if(result.activity.extendedAttributes[i].name == "ExWithdraw"){
				result.activity.extendedAttributes.splice(i, 1);
				break;
			}
		}
		
		var withdraw = new Object();
		withdraw.name = "ExWithdraw";
		if(this.form.withdraw.selectedIndex == 0){
			withdraw.value = "True";
		}else{
			withdraw.value = "False";
		}
		result.activity.extendedAttributes.push(withdraw);
		
		//\u662f\u5426\u53ef\u53d8\u66f4

		for(var i=0; i<result.activity.extendedAttributes.length; i++){
			if(result.activity.extendedAttributes[i].name == "ExChange"){
				result.activity.extendedAttributes.splice(i, 1);
				break;
			}
		}
		
		var change = new Object();
		change.name = "ExChange";
		if(this.form.change.selectedIndex == 0){
			change.value = "True";
		}else{
			change.value = "False";
		}
		result.activity.extendedAttributes.push(change);
		
		//\u534f\u540c\u73af\u8282
		for(var i=0; i<result.activity.extendedAttributes.length; i++){
			if(result.activity.extendedAttributes[i].name == "ExCollaborate"){
				result.activity.extendedAttributes.splice(i, 1);
				break;
			}
		}
		
		var collaborate = new Object();
		collaborate.name = "ExCollaborate";
		if(this.form.collaborate.selectedIndex == 0){
			collaborate.value = "True";
		}else{
			collaborate.value = "False";
		}
		result.activity.extendedAttributes.push(collaborate);
		
		//\u6d41\u5165\u6d41\u51fa\u65b9\u5f0f
		if(result.activity.transitionRestrictions.length == 0){
			result.activity.transitionRestrictions[0] = new Object();
		}
		
		if(this.form.joinType.selectedIndex == 0){
			result.activity.transitionRestrictions[0].joinType = "AND";
		}else{
			result.activity.transitionRestrictions[0].joinType = "XOR";
		}
		
		if(this.form.splitType.selectedIndex == 0){
			result.activity.transitionRestrictions[0].splitType = "AND";
		}else{
			result.activity.transitionRestrictions[0].splitType = "XOR";
		}
		
		//\u8fd9\u91cc\u7ec4\u88c5application
		for(var i=0; i<this.paramTree.items.length; i++){
			var param = new Object();
			param.paramName = this.paramTree.items[i].paramName;
			param.dataType = this.paramTree.items[i].dataType;
			param.paramType = this.paramTree.items[i].paramType;
			param.isDisplay = this.paramTree.items[i].isDisplay;
			param.description = this.paramTree.items[i].description;
			
			result.application.formalParameters.push(param);
			
			//\u53c2\u6570\u6570\u7ec4\u7684\u5b9e\u53c2\u8981\u653e\u5728activity\u7684implementation\u4e2d

			var actualParameter = this.paramTree.items[i].actualParameter;
			result.activity.implementation.tool.actualParameters.push(actualParameter);
		}
		
		//application\u7684\u6269\u5c55\u5c5e\u6027

		for(var i=0; i<result.application.extendedAttributes.length; i++){
			result.application.extendedAttributes.pop()
		}
		
		var activityId = new Object();
		activityId.name = "ExActivityID";
		activityId.value = result.activity.id;
		
		result.application.extendedAttributes.push(activityId);
		
		//\u8fd9\u91cc\u8981\u7ec4\u88c5\u53c2\u4e0e\u8005\u4fe1\u606f

		for(var i=0; i<this.form.participantRadio.length; i++){
			if(this.form.participantRadio[i].checked){
				//\u5173\u8054\u7684\u6d3b\u52a8Id,\u8868\u793a\u4e3a\u6b64\u6d3b\u52a8\u5b9a\u4e49\u7684\u6d41\u7a0b\u53c2\u4e0e\u8005

				var exActivityID = new Object();
				exActivityID.name = "ExActivityID";
				exActivityID.value = result.activity.id;
				
				//\u53c2\u4e0e\u8005\u6a21\u677f\u7684\u7f16\u7801
				var exTempletCode = new Object();
				exTempletCode.name = "ExTempletCode";
				exTempletCode.value = "";
				
				//\u53c2\u4e0e\u8005\u6a21\u677f\u7684\u53c2\u6570
				var exTempletParams = new Object();
				exTempletParams.name = "ExTempletParams";
				exTempletParams.value = "";
				
				switch(i){
					//\u542f\u52a8\u8005

					case 0:{
						switch(this.form.starter.options[this.form.starter.selectedIndex].value){
							//\u542f\u52a8\u4eba

							case "StarterStaff":{
								result.participant.participantType = "HUMAN";
								exTempletCode.value = TACHE_STARTER;
								break;
							}
							//\u542f\u52a8\u8005\u7ec4\u7ec7

							case "StarterOrganization":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = TACHE_STARTER_ORGANIZATION;
								break;
							}
							//\u542f\u52a8\u8005\u804c\u4f4d

							case "StarterPosition":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = TACHE_STARTER_POSITION;
								break;
							}
						}
						break;
					}
					//\u67d0\u73af\u8282

					case 1:{
						switch(this.form.tachePerformer.options[this.form.tachePerformer.selectedIndex].value){
							//\u542f\u52a8\u4eba

							case "PerformerStaff":{
								result.participant.participantType = "HUMAN";
								exTempletCode.value = TACHE_PERFORMER;
								break;
							}
							//\u542f\u52a8\u8005\u7ec4\u7ec7

							case "PerformerOrganization":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = TACHE_PERFORMER_ORGANIZATION;
								break;
							}
							//\u542f\u52a8\u8005\u804c\u4f4d

							case "PerformerPosition":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = TACHE_PERFORMER_POSITION;
								break;
							}
						}
						exTempletParams.value = this.form.tacheId.value;
						
						if(this.form.tacheName.value == ""){
							ErrorHandle("<%=getI18NResource("designer.tache_cannot_empty")%>");
							return;
						}
						
						var exName = new Object();
						exName.name = "ExName";
						exName.value = this.form.tacheName.value;
						result.participant.extendedAttributes.push(exName);
						break;
					}
					//\u67d0\u7ec4\u7ec7\u6216\u8005\u804c\u4f4d

					case 2:{
						result.participant.exTempletParams = this.form.participantId.value;
						
						//\u9009\u4e2d\u7684\u7ec4\u7ec7\u3001\u804c\u4f4d\u6216\u8005\u4eba\u7684ID
						exTempletParams.value = this.form.participantId.value;
						
						if(this.form.participantName.value == ""){
							ErrorHandle("<%=getI18NResource("designer.org_position_cannot_empty")%>");
							return;
						}
						
						//\u9009\u4e2d\u7684\u7ec4\u7ec7\u3001\u804c\u4f4d\u6216\u8005\u4eba\u7684\u540d\u79f0

						var exName = new Object();
						exName.name = "ExName";
						exName.value = this.form.participantName.value;
						result.participant.extendedAttributes.push(exName);
						
						switch(this.form.participantType.value){
							case "ORG":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = ORGANIZATION;
								break;
							}
							case "JOB":{
								result.participant.participantType = "ORGANIZATIONAL_UNIT";
								exTempletCode.value = POSITION;
								break;
							}
							case "STA":{
								result.participant.participantType = "HUMAN";
								exTempletCode.value = STAFF;
								break;
							}
						}
						break;
					}
					//\u7531\u524d\u9762\u73af\u8282\u6307\u5b9a

					case 3:{
						result.participant.participantType = "HUMAN";
						exTempletCode.value = LAST_TACHE;
						break;
					}
					//\u7531\u5de5\u5355\u6d3e\u53d1\u89c4\u5219\u6307\u5b9a

					case 4:{
						result.participant.participantType = "HUMAN";
						exTempletCode.value = RULE;
						break;
					}
				}
				
				result.participant.extendedAttributes.push(exActivityID);
				result.participant.extendedAttributes.push(exTempletCode);
				result.participant.extendedAttributes.push(exTempletParams);
			}
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
		var dialogParam = new Object();
		dialogParam.dataFields = window.dialogArguments.dataFields;
		dialogParam.formalParameters = window.dialogArguments.formalParameters;
		dialogParam.usedNames = this.getUsedNames(true);
		
		var newParam = OpenShowDlg("paramProperty.jsp", 300, 300, dialogParam);
		if(newParam != null){
			var newNode = this.paramTree.createTreeNode();
			newNode.clone(newParam);
			this.paramTree.add(newNode);
		}
	}
	
	//\u4fee\u6539\u53c2\u6570
	this.modifyParam = function(){
		if(this.paramTree.selectedItem != null){
			var dialogParam = new Object();
			dialogParam.dataFields = window.dialogArguments.dataFields;
			dialogParam.formalParameters = window.dialogArguments.formalParameters;
			dialogParam.treeItem = this.paramTree.selectedItem;
			dialogParam.usedNames = this.getUsedNames(false);
			
			var newParam = OpenShowDlg("paramProperty.jsp", 300, 300, dialogParam);
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
	
	//\u9009\u62e9\u7ec4\u7ec7\u3001\u804c\u4f4d\u3001\u4eba
	this.chooseParticipant = function(){
		var session = GetSession();
		var result = OpenShowDlg("participant.jsp", 420, 300, session);
		
		if(result != null){
			this.form.participantId.value = result.id;
			this.form.participantType.value = result.type;
			this.form.participantName.value = result.name;
		}
	}
	
	//\u9009\u62e9\u73af\u8282
	this.chooseTache = function(){
		var obj = window.dialogArguments;
		
		//\u8fd9\u91cc\u7528\u6765\u9650\u5236\u9009\u62e9\u73af\u8282\u7684\u65f6\u5019\u4e0d\u80fd\u9009\u5230\u5f53\u524d\u8282\u70b9\u540e\u9762\u7684\u73af\u8282

		var activityId = obj.activity.id;
		var taches = new Array();
		
		//\u4e0b\u9762\u8fd9\u4e00\u6bb5\u662f\u641c\u7d22\u5df2\u7ecf\u8fc7\u7684\u8282\u70b9\u7684\u8fc7\u7a0b

		var nodePointer;
		
		for(var i=0; i<obj.allNodes.length; i++){
			if(obj.allNodes[i].activity.id == activityId){
				nodePointer = obj.allNodes[i];
				break;
			}
		}
		
		var linePointer = null;
		
		//\u4e0b\u9762\u7684\u627e\u6cd5\u57fa\u4e8e\u4e00\u4e2a\u5148\u51b3\u6761\u4ef6\uff1anodePointer\u6307\u5411\u7684\u8282\u70b9\u59cb\u7ec8\u662f\u73af\u8282\u8282\u70b9\uff0c\u56e0\u6b64\u80af\u5b9a\u53ea\u6709\u4e00\u6839\u7ebf\u6761\u6d41\u5165

		while(true){
			//\u53d6\u5f97\u4ee5\u8fd9\u4e2a\u8282\u70b9\u4f5c\u4e3a\u7ec8\u6b62\u8282\u70b9\u7684\u7ebf\u6761\uff0c\u65e0\u8bba\u5982\u4f55\uff0c\u8fd9\u4e2a\u603b\u662f\u6709\u7684
			for(var i=0; i<obj.allLines.length; i++){
				if(obj.allLines[i].endNode == nodePointer){
					linePointer = obj.allLines[i];
					break;
				}
			}
			
			//\u627e\u5230\u8fd9\u91cc\u5c31\u662f\u7ed3\u675f\u4e86

			if(linePointer == null){
				break;
			}
			
			//\u6709\u5f00\u59cb\u8282\u70b9\uff0c\u90a3\u4e48\u5f88\u597d\u529e

			if(linePointer.startNode != null){
				nodePointer = linePointer.startNode;
			}else{
				//\u6ca1\u6709\u5f00\u59cb\u8282\u70b9\uff0c\u90a3\u4e48\u8fd9\u4e2a\u7ebf\u6761\u662f\u5e76\u884c\u8282\u70b9\u67d0\u5206\u652f\u7684\u7b2c\u4e00\u6839\u7ebf\u6761

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
			
			if(nodePointer.getNodeType() == "Tache"){
				taches.push(nodePointer.activity);
			}else if(nodePointer.getNodeType() == "Parallel"){
				this.gatherTachesInParallel(nodePointer, taches);
			}
			linePointer = null;
		}
		
		var result = OpenShowDlg("tacheChoose.jsp", 420, 300, taches);
		
		if(result != null){
			this.form.tacheId.value = result.id;
			this.form.tacheName.value = result.name;
		}
	}

	//\u6536\u96c6\u5e76\u884c\u7ed3\u6784\u4e2d\u6240\u6709\u7684\u73af\u8282\u8282\u70b9
	this.gatherTachesInParallel = function(parallel, resultList){
		for(var i=0; i<parallel.branches.length; i++){
			for(var j=0; j<parallel.branches[i].nodes.length; j++){
				if(parallel.branches[i].nodes[j].getNodeType() == "Tache"){
					resultList.push(parallel.branches[i].nodes[j].activity);
				}else if(parallel.branches[i].nodes[j].getNodeType() == "Parallel"){
					this.gatherTachesInParallel(parallel.branches[i].nodes[j], resultList);
				}
			}
		}
	}
	
	//\u53d6\u5f97\u5df2\u7ecf\u4f7f\u7528\u4e86\u7684\u53c2\u6570\u540d\u79f0
	this.getUsedNames = function(selectedContained){
		var names = new Array();
		if(selectedContained){
			for(var i=0; i<this.paramTree.items.length; i++){
				names.push(this.paramTree.items[i].paramName);
			}
		}else{
			for(var i=0; i<this.paramTree.items.length; i++){
				if(this.paramTree.items[i].paramName != this.paramTree.selectedItem.paramName){
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
