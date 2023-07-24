<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title>BPM</title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../public/css/style.css">
	<script language="javascript" src="../public/script/helper.js"></script>
	<script language="javascript" src="../public/script/XmlInter.js"></script>
	<script language="javascript" src="../public/script/FormExt.js"></script>
	<style>
	.m_arrow{width: 16px;height: 8px;font-family: "Webdings";font-size: 7px;line-height: 2px;padding-left: 2px;}
	<!--
	v\:*
	{
		Behavior: url(#default#VML);
	}
	-->
	</style>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" ?>
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../public/htc/TreeList/TreeList.htc" />
	<!-- TemplEndEditable -->

</head>
<body background="../images/main/background.gif" >
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../public/script/topTitleJs.js"></script>
<table width="100%" height="400" border="0" cellpadding="1" cellspacing="3" id="mainTable" name="mainTable">
<form name="form1" onSubmit="return false;">
	<tr>
		<td width="200" valign="top">
			<ZTEsoft:tabStrip class="tab_ioms" selectedIndex="0">
				<ZTEsoft:tabs width="200" height="100%">
					<ZTEsoft:page Text="流程库">
						<ZTESOFT:treelist id="areaTree" name="areaTree" class="treelist" width="100%" height="200"  onItemClick="flowOperation.areaClick();">
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" display="true" displayText="区域" propertyName="areaName" />
								<ZTESOFT:column display="false" propertyName="areaId" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						  <tr>
						    <td height="5"></td>
						  </tr>
						</table>
						<ZTESOFT:treelist id="flowLibrary" name="flowLibrary" class="treelist" width="100%" height="340" onItemClick="flowOperation.flowClick()" onItemContextMenu="flowOperation.showMenu();" onDivContextMenu="flowOperation.showMenu();">
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" display="true" displayTip="true" displayText="流程库" propertyName="name" />
								<ZTESOFT:column display="false" propertyName="id" />
								<ZTESOFT:column display="false" propertyName="type" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
						    <td>
							<table width="100%">
							<tr>
								<td width='49%' nowrap norwap>产品名称</td><td width="51%"><input width="100%" type="text" id="prodNameInput" name="prodNameInput"/></td>
							</tr>
						</table>
							</td>
						  </tr>
						  <tr>
						    <td>
							<table width="100%">
							<tr>
								<td width='49%' nowrap norwap>服务名称</td><td width="51%"><input width="100%" type="text" id="serviceNameInput" name="serviceNameInput" /></td>
							</tr>
						</table>
							</td>
						  </tr>
						  <tr>
						    <td >
							<table width="100%">
							<tr>
								<td width='49%' nowrap norwap>流程版本</td><td>
						V<input id="majorVersionNum" name="majorVersionNum" style="width:20px" value="0" checkType="integer" maxLength="20">
					</td>
					<td>
						<table>
							<tr>
								<td><input type="button" class="m_arrow" value="5" onMouseUp="flowOperation.addMajor(1)" /></td>
							</tr>
							<tr>
								<td><input type="button" class="m_arrow" value="6" onMouseUp="flowOperation.addMajor(-1)" /></td>
							</tr>
						</table>
					</td>
					<td>
						.<input id="minorVersionNum" name="minorVersionNum" style="width:20px" value="0" checkType="integer" maxLength="20">
					</td>
					<td>
						<table border=0>
							<tr>
								<td><input type="button" class="m_arrow" value="5" onMouseUp="flowOperation.addMinor(1)" /></td>
								<td rowspan=2><font color="red">*</font> </td>
							</tr>
							<tr>
								<td><input type="button" class="m_arrow" value="6" onMouseUp="flowOperation.addMinor(-1)" /></td>
              	<td> </td>
							</tr>
						</table>
					</td>
							</tr>
						</table>
							</td>
							
						  </tr>
						  <tr>
						    <td>
							<input type="button" value="查询流程模版" class="button" onClick="flowOperation.qryFlow();">
							<input type="button" value="高级查询" class="button" onClick="flowOperation.queryFlowTmp();">				
							</td>
						  </tr>
						</table>
						
					</ZTEsoft:page>
					<ZTEsoft:page Text="监控环节">
						<ZTESOFT:treelist id="tacheLibrary" name="tacheLibrary" class="treelist" width="100%" height="545">
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" display="true" displayText="环节名称" propertyName="name" />
								<ZTESOFT:column display="false" propertyName="id" />
								<ZTESOFT:column display="false" propertyName="flag" />
								<ZTESOFT:column display="false" propertyName="path_code" />
								<ZTESOFT:column display="false" propertyName="TACHE_CODE" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
						<div style="display:none">
						<input type="text" name="tacheKeyWord" style="width:80">
						<input type="button" value="查询结果" class="button" onClick="tacheOperation.queryTacheByWord();">
						</div>
					</ZTEsoft:page>
				</ZTEsoft:tabs>
			</ZTEsoft:tabStrip>
		</td>
		<td name="nodeBarTd" id="nodeBarTd" width="20" height="100%">
			<div id="nodeBarDiv" name="nodeBarDiv" style="width:100%; height:100%;" />
		</td>
		<td name="workAreaTd" id="workAreaTd" width="100%" height="100%" valign="top">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr height="20">
					<td height="20" name="flowTabs" id="flowTabs"><br>
						<ZTEsoft:tabStrip class="tab_ioms" selectedIndex="0">
							<ZTEsoft:tabs width="100%" height="0">
								<ZTEsoft:page Text="当前没有打开的流程">
								</ZTEsoft:page>
							</ZTEsoft:tabs>
						</ZTEsoft:tabStrip>
					<br></td>
				</tr>
				<tr height="20">
					<td width="100%" height="40" class="td_blue_title">
						&nbsp;<img src="../images/icon/title_dot.gif" align="absmiddle">
						<span id="workAreaTitle" name="workAreaTitle" align="absmiddle"></span>
					</td>
				</tr>
				<tr height="*">
					<td bgcolor="#FFFFFF" valign="top">
						<div id="workAreaDiv" name="workAreaDiv" style="width:100%; height:100%; border:1px solid #c0c0c0; overflow:scroll; z-index:0" />
						<input type="hidden" name="showType" id="showType" value="0" >
					</td>
				</tr>
		  </table>
		</td>
	</tr>
	</form>
</table>
<iframe src="save.jsp" style="border:none; width:0px; height:0px" name="htmlFrame">
</iframe>
</body>
</html>

<script language="JScript">
_import("ContextMenu");
</script>

<script language="javascript" src="./js/constant.js"></script>
<script language="javascript" src="./js/nodeBar.js"></script>
<script language="javascript" src="./js/controller.js"></script>
<script language="javascript" src="./js/workArea.js"></script>

<script language="javascript" src="./js/operations/flowOperation.js"></script>
<script language="javascript" src="./js/operations/tacheOperation.js"></script>

<script language="javascript" src="./js/flowElement/nodeFactory.js"></script>
<script language="javascript" src="./js/flowElement/abstractFlowLine.js"></script>
<script language="javascript" src="./js/flowElement/abstractFlowNode.js"></script>
<script language="javascript" src="./js/flowElement/flowLine.js"></script>
<script language="javascript" src="./js/flowElement/controlLine.js"></script>
<script language="javascript" src="./js/flowElement/simpleNode.js"></script>
<script language="javascript" src="./js/flowElement/tacheNode.js"></script>
<script language="javascript" src="./js/flowElement/startNode.js"></script>
<script language="javascript" src="./js/flowElement/finishNode.js"></script>
<script language="javascript" src="./js/flowElement/subFlowNode.js"></script>
<script language="javascript" src="./js/flowElement/exceptionNode.js"></script>
<script language="javascript" src="./js/flowElement/controlNode.js"></script>
<script language="javascript" src="./js/flowElement/abstractFlowNode.js"></script>
<script language="javascript" src="./js/flowElement/parallelNode.js"></script>

<script language="javascript" src="./js/core/XPDLParser.js"></script>
<script language="javascript" src="./js/core/package.js"></script>
<script language="javascript" src="./js/core/workflowProcess.js"></script>
<script language="javascript" src="./js/core/abstractElement.js"></script>
<script language="javascript" src="./js/core/activity.js"></script>
<script language="javascript" src="./js/core/transition.js"></script>
<script language="javascript" src="./js/core/participant.js"></script>
<script language="javascript" src="./js/core/application.js"></script>
<script language="javascript" src="./js/main.js"></script>

<!-- 

 -->
