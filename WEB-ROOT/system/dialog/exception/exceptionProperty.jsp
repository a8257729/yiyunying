<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.exception_config")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- add by chen.jixin 2010-09-09 for ur:61223 begin -->
	<script language="javascript" src="../../../public/script/BSCommon.js"></script>
	<!-- ur:61223 end -->
	<!-- TemplBeginEditable name="head" -->

	<?XML:NAMESPACE PREFIX="ZTESOFT" ?>
	<!-- modify by chen.jixin 2010-09-09 for ur:61223 -->
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeListSort.htc" />
	<!-- ur:61223 end -->
	<!-- TemplEndEditable -->
</head>

<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<form name="exceptionForm" id="exceptionForm" onsubmit="return false">
	<tr>
		<td width="90%">
			<!-- modify by chen.jixin 2010-09-09 for ur:61223-->
			<ZTESOFT:treelist id="configTree" name="configTree" class="treelist" sorted="true" width="100%" height="160">
				<ZTESOFT:columns>
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.exception_activity")%>" propertyName="startActivityName" sortType="String"/>
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.exception_catalog")%>" propertyName="reasonCatalogName" sortType="String"/>
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.exception_reason")%>" propertyName="returnReasonName" sortType="String"/>
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.return_activity")%>" propertyName="endActivityName" />
					<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.auto_to_manual")%>" propertyName="autoToManualText" />
					<ZTESOFT:column display="false" propertyName="startModeName" />
					<ZTESOFT:column display="false" propertyName="startMode" />
					<ZTESOFT:column display="false" propertyName="reasonCatalogId" />
					<ZTESOFT:column display="false" propertyName="returnReasonId" />
					<ZTESOFT:column display="false" propertyName="startActivityId" />
					<ZTESOFT:column display="false" propertyName="endActivityId" />
					<ZTESOFT:column display="false" propertyName="autoToManual" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
		<td valign="middle" align="center">
			<input type="button" class="button_img" id="addButton" name="addButton" style="width:60px" value="<%=getI18NResource("designer.new")%>" onclick="exceptionOperation.addConfig();" />
			<br /><br />
			<input type="button" class="button_img" id="editButton" name="editButton" style="width:60px" value="<%=getI18NResource("designer.edit")%>" onclick="exceptionOperation.modifyConfig();" />
			<br /><br />
			<input type="button" class="button_img" id="deleteButton" name="deleteButton" style="width:60px" value="<%=getI18NResource("designer.delete")%>" onclick="exceptionOperation.deleteConfig();" />
			<br /><br />
			<input type="button" class="button_img" id="clearButton" name="clearButton" style="width:60px" value="<%=getI18NResource("designer.clear")%>" onclick="exceptionOperation.clearConfig();" />
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td colspan="2" align="center" height="40">
			<!-- add by chen.jixin 2010-09-09 for ur:61223 begin-->
			<input type="button" class="button" id="exportBtn" style="width:60px" value="<%=getI18NResource("designer.export_reason")%>" onclick="exceptionOperation.print();" />
			<input type='hidden' name='parameter' >
			<!-- ur:61223 end-->
			<input type="button" class="button" id="submitButton" name="submitButton" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="exceptionOperation.submit();" />
			<input type="button" class="button" id="cancelButton" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="exceptionOperation.cancel();" />
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
// commits: View or set exception Properties
/////////////////////////////////////////////////////

_import("designer.js.constant");

_import("designer.js.core.abstractElement");
_import("designer.js.core.activity");
_import("designer.js.core.participant");
_import("designer.js.core.application");

/* \u5168\u5c40\u53d8\u91cf */
var exceptionOperation = new ExceptionOperation();
exceptionOperation.init();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a
//\u8282\u70b9\u5c5e\u6027\u64cd\u4f5c

function ExceptionOperation(){
	this.form = document.all.exceptionForm;
	this.configTree = document.all.configTree;
	//add by chen.jixin 2010-09-09 for ur:61223 begin
	_extends(this, BSCommon, '"exceptionForm"');
	_extends(this, FormExt, '"exceptionForm"');
	//ur:61223 end
	//\u521d\u59cb\u5316

	this.init = function(){
		var obj = window.dialogArguments;

		var configs;

		for(var i=0; i<obj.activity.extendedAttributes.length; i++){
			if(obj.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				configs = obj.activity.extendedAttributes[i].value;
				break;
			}
		}

		if((configs != null) && (configs.length != 0)){
			for(var i=0; i<configs.length; i++){
				if(configs[i].startMode == "Automatic"){
					configs[i].startModeName = "<%=getI18NResource("designer.automatic")%>";
				}else if(configs[i].startMode == "Manual"){
					configs[i].startModeName = "<%=getI18NResource("designer.manual")%>";
				}else if(configs[i].startMode == "Returnback"){
					configs[i].startModeName = "<%=getI18NResource("designer.return_back")%>";
				}else if(configs[i].startMode == "ChangeReturnback"){
					configs[i].startModeName = "<%=getI18NResource("designer.change_back")%>";
				}
				if(configs[i].autoToManual == true){
					configs[i].autoToManualText = "<%=getI18NResource("designer.yes")%>";
				}else{
					configs[i].autoToManualText = "<%=getI18NResource("designer.no")%>";
				}
			}
			this.configTree.loadByData(configs);
		}

		//\u67e5\u770b\u72b6\u6001\u4e0b\u5c4f\u853d\u64cd\u4f5c
		if(obj.operation == "view"){
			this.form.submitButton.style.display = "none";
			this.form.addButton.style.display = "none";
			this.form.editButton.style.display = "none";
			this.form.deleteButton.style.display = "none";
			this.form.clearButton.style.display = "none";
		}
	}

	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Array();

		for(var i=0; i<this.configTree.items.length; i++){
			var config = new Object();
			config.reasonCatalogId = this.configTree.items[i].reasonCatalogId;
			config.reasonCatalogName = this.configTree.items[i].reasonCatalogName;
			config.returnReasonId = this.configTree.items[i].returnReasonId;
			config.returnReasonName = this.configTree.items[i].returnReasonName;
			config.startActivityId = this.configTree.items[i].startActivityId;
			config.startActivityName = this.configTree.items[i].startActivityName;
			config.endActivityId = this.configTree.items[i].endActivityId;
			config.endActivityName = this.configTree.items[i].endActivityName;
			config.startMode = this.configTree.items[i].startMode;
			if(this.configTree.items[i].autoToManual == true){
				config.autoToManual = this.configTree.items[i].autoToManual;
			}else{
				config.autoToManual = false;
			}

			result.push(config);
		}

		window.returnValue = result;
		window.close();
	}
	//add by chen.jixin 2010-09-09 for ur:61233 begin
	this.print = function(){
			var obj = window.dialogArguments;
			var flowId = obj.flowId;//模板ID
			var str=obj.title;
			var tempStr=str.split("/");
			var flowPath="";//流程模板完整目录路径
			var tempFlowPath="";		
			for(var i=0;i<tempStr.length-2;i++){//去掉路径的动作名称
				flowPath+=tempStr[i]+ "/" ;
				tempFlowPath += Trim(tempStr[i])+"-";
			}
			flowPath += tempStr[tempStr.length-2];
			tempFlowPath += Trim(tempStr[tempStr.length-2]);
			var flowName = tempStr[tempStr.length-3];//模板名称
			var path_name_id = "";//excel文件名：流程模板完整目录路径_模板名称_模板ID	
			path_name_id += tempFlowPath + "_";
			path_name_id += flowName +　"_";
			path_name_id += flowId;
			var result = new Array();
			for(var i=0; i<this.configTree.items.length; i++){
				var config = new Object();
				config.flowId = flowId;
				config.flowPath = flowPath;
				config.flowName = flowName;
				config.startActivityName = this.configTree.items[i].startActivityName;
				config.reasonCatalogName = this.configTree.items[i].reasonCatalogName;
				config.returnReasonName = this.configTree.items[i].returnReasonName;
				config.endActivityName = this.configTree.items[i].endActivityName;
				config.autoToManualText = this.configTree.items[i].autoToManualText;
				result.push(config);
			}
		   	if(result){		
		   		//删除临时表EXCEPTION_TEMP里面的记录
		   		var deleteTempRecord = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.ReturnReasonManager", "deleteException");	
		   		if(deleteTempRecord){	   		
		   			//将异常原因放入暂时存于表EXCEPTION_TEMP;  
		   			var insert = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.ReturnReasonManager", "insertException", result);
					if(insert){
		   				document.all.exceptionForm.parameter.value = path_name_id;
						document.all.exceptionForm.action = '/SQMPROJ/orderprint?mode=exceptionName';
						document.all.exceptionForm.method = 'post';
		  				document.all.exceptionForm.target = '_blank';
		  				document.all.exceptionForm.submit();
					}
				}
		   }
	}
	//ur:61233 end
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}

	//\u8fd9\u91cc\uff0c\u8981\u628a\u5df2\u7ecf\u6709\u7684\u9009\u62e9\u9879\u4f20\u9012\u5230\u6dfb\u52a0\u5bf9\u8bdd\u6846\u4e2d\uff0c\u7528\u6765\u8fc7\u6ee4\u53ef\u9009\u9009\u9879
	this.getSelectedReasons = function(){
		var selectedReasons = new Array();
		for(var i=0; i<this.configTree.items.length; i++){
			var selectedReason = new Object();
			selectedReason.startActivityId = this.configTree.items[i].startActivityId;
			selectedReason.returnReasonId = this.configTree.items[i].returnReasonId;
			selectedReasons.push(selectedReason);
		}

		return selectedReasons;
	}

	//\u6dfb\u52a0\u914d\u7f6e
	this.addConfig = function(){
		var obj = new Object();
		obj.operation = "add";
		obj.flowId = window.dialogArguments.flowId;
		obj.allNodes = window.dialogArguments.allNodes;
		obj.allLines = window.dialogArguments.allLines;

		//\u8fd9\u91cc\uff0c\u8981\u628a\u5df2\u7ecf\u6709\u7684\u9009\u62e9\u9879\u4f20\u9012\u5230\u6dfb\u52a0\u5bf9\u8bdd\u6846\u4e2d\uff0c\u7528\u6765\u8fc7\u6ee4\u53ef\u9009\u9009\u9879
		obj.selectedReasons = this.getSelectedReasons();
		//modify by chen.jixin 2010-09-09 for ur:61223 begin
		var newConfigs = OpenShowDlg("configProperty.jsp", 460, 320, obj);
		//ur:61223 end
		if(newConfigs != null){
			for(var i=0; i<newConfigs.length; i++){
				if(newConfigs[i].startMode == "Automatic"){
					newConfigs[i].startModeName = "<%=getI18NResource("designer.automatic")%>";
				}else if(newConfigs[i].startMode == "Manual"){
					newConfigs[i].startModeName = "<%=getI18NResource("designer.manual")%>";
				}else if(newConfigs[i].startMode == "Returnback"){
					newConfigs[i].startModeName = "<%=getI18NResource("designer.return_back")%>";
				}else if(newConfigs[i].startMode == "ChangeReturnback"){
					newConfigs[i].startModeName = "<%=getI18NResource("designer.change_back")%>";
				}

				if(newConfigs[i].autoToManual == true){
					newConfigs[i].autoToManualText = "<%=getI18NResource("designer.yes")%>";
				}else{
					newConfigs[i].autoToManualText = "<%=getI18NResource("designer.no")%>";
				}
			}

			for(var i=0; i<newConfigs.length; i++){
				var newNode = this.configTree.createTreeNode();
				newNode.clone(newConfigs[i]);
				this.configTree.add(newNode);
			}
		}
	}

	//\u4fee\u6539\u914d\u7f6e
	this.modifyConfig = function(){
		if(this.configTree.selectedItem != null){
			var obj = new Object();
			obj.operation = "update";
			obj.flowId = window.dialogArguments.flowId;
			obj.allNodes = window.dialogArguments.allNodes;
			obj.allLines = window.dialogArguments.allLines;
			obj.selectedReasons = this.getSelectedReasons();

			//\u9009\u4e2d\u8282\u70b9\u7684\u5c5e\u6027

			obj.config = new Object();
			obj.config.reasonCatalogId = this.configTree.selectedItem.reasonCatalogId;
			obj.config.reasonCatalogName = this.configTree.selectedItem.reasonCatalogName;
			obj.config.returnReasonId = this.configTree.selectedItem.returnReasonId;
			obj.config.returnReasonName = this.configTree.selectedItem.returnReasonName;
			obj.config.startActivityId = this.configTree.selectedItem.startActivityId;
			obj.config.startActivityName = this.configTree.selectedItem.startActivityName;
			obj.config.endActivityId = this.configTree.selectedItem.endActivityId;
			obj.config.endActivityName = this.configTree.selectedItem.endActivityName;
			obj.config.startMode = this.configTree.selectedItem.startMode;
			obj.config.autoToManual = this.configTree.selectedItem.autoToManual;

			var newConfig = OpenShowDlg("configProperty.jsp", 430, 320, obj);
			if(newConfig != null){
				if(newConfig[0].startMode == "Automatic"){
					newConfig[0].startModeName = "<%=getI18NResource("designer.automatic")%>";
				}else if(newConfig[0].startMode == "Manual"){
					newConfig[0].startModeName = "<%=getI18NResource("designer.manual")%>";
				}else if(newConfig[0].startMode == "Returnback"){
					newConfig[0].startModeName = "<%=getI18NResource("designer.return_back")%>";
				}else if(newConfig[0].startMode == "ChangeReturnback"){
					newConfig[0].startModeName = "<%=getI18NResource("designer.change_back")%>";
				}

				if(newConfig[0].autoToManual == true){
					newConfig[0].autoToManualText = "<%=getI18NResource("designer.yes")%>";
				}else{
					newConfig[0].autoToManualText = "<%=getI18NResource("designer.no")%>";
				}

				this.configTree.selectedItem.clone(newConfig[0]);
				this.configTree.selectedItem.refresh();
			}
		}
	}

	//\u5220\u9664\u914d\u7f6e
	this.deleteConfig = function(){
		if(this.configTree.selectedItem != null){
			if(window.confirm("<%=getI18NResource("designer.confirm_delete_exception")%>")){
				this.configTree.selectedItem.remove();
			}
		}
	}

	//\u6e05\u9664\u914d\u7f6e
	this.clearConfig = function(){
		if(this.configTree.items.length > 0){
			if(window.confirm("<%=getI18NResource("designer.confirm_clear_exception")%>")){
				this.configTree.clear();
			}
		}
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
