<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.flow_template_rule")%> </title>
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<!-- TemplBeginEditable name="head" -->
<?XML:NAMESPACE PREFIX="ZTESOFT" />
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
<!-- TemplEndEditable -->
<style>
.cell {
	word-break : keep-all;
	white-space: nowrap;
}
</style>
</head>
<body >
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2" id='packageAppleRuleTbl'>
<tr>
        <td class="td_blue_title">&nbsp;<img src="../../../images/icon/title_dot.gif" align="absmiddle"><%=getI18NResource("designer.rule_list")%></td>
      </tr>
<tr><td>
	<ZTESOFT:treelist id="tlvApplyRule" height="200" width="100%" class="treelist" onItemClick="packageApplyRuleListOper.click()" onItemContextMenu="packageApplyRuleListOper.selectContextMenu()" onDivContextMenu="packageApplyRuleListOper.divContextMenu()">
		<ZTESOFT:columns>
			<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.rule_name")%>" propertyName="packageApplyRuleName" />
			<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.area_name")%>" propertyName="areaName" />
			<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("designer.service_name")%>" propertyName="serviceName" />
			<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("designer.effect_time")%>" propertyName="effDate" />
			<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("designer.expire_time")%>" propertyName="expDate" />
			<ZTESOFT:column display="false" propertyName="id" />
			<ZTESOFT:column display="false" propertyName="areaId" />
			<ZTESOFT:column display="false" propertyName="serviceId" />
		</ZTESOFT:columns>
	</ZTESOFT:treelist>
</td></tr>
<tr>
        <td class="td_blue_title">&nbsp;<img src="../../../images/icon/title_dot.gif" align="absmiddle">&nbsp;<span id="other_use_id"><%=getI18NResource("designer.other_rule")%></span></td>
      </tr>
  <tr>
    <td width="100%" id='ruleValueTd'></td>
  </tr>
  <tr>
  <td align="center" height="40" valign="bottom">
			<input type="button" class="button" id="close_windows_btn" value="<%=getI18NResource("designer.close_window")%>" onclick="window.close();" />
  </td>
  </tr>
</table>	
<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript">
//////////////////////////////////////////
// ZTESoft corp. 2005- -
// Author :
// commits:
//////////////////////////////////////////
/* \u5168\u5c40\u53d8\u91cf */
_import("ContextMenu");

var applyRuleMenu = new ContextMenu("applyRuleMenu", 180);
var packageApplyRuleListOper = new PackageApplyRuleListOper();
var ruleList;
var packageDto = window.dialogArguments;
var packageName = "";
var packageid = 1;
/* \u521d\u59cb\u5316 */
packageApplyRuleListOper.init();
packageApplyRuleListOper.buildMenu();


//////////////////////////////////////////
//Class\u7c7b\u533a

function PackageApplyRuleListOper(){
	this.treeList = document.all.tlvApplyRule;
	this.init = function(){

		if(packageDto!=null){
		  packageid = packageDto.id;
			packageName = packageDto.name;
		}
		ruleList = callRemoteFunctionNoTrans('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','qryPackageApplyRuleByPackageId',packageid);
		if(ruleList!=null){
			document.all.tlvApplyRule.loadByData(ruleList);
			this.click();
		}
		
	}
	this.click = function(){
		this.clearTbl();
		if(document.all.tlvApplyRule.selectedItem){
			var retStr = callRemoteFunctionNoTrans('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','qryPackageApplyRuleValuesByRuleId',document.all.tlvApplyRule.selectedItem.id);
			retStr = retStr.replace(/<td/g, "<td class='cell'");
			ruleValueTd.innerHTML = retStr;
		}
	}
	this.buildMenu = function(){
		applyRuleMenu.addItem("<%=getI18NResource("designer.add_rule")%>", "packageApplyRuleListOper.addApplyRule()");
		applyRuleMenu.addItem("<%=getI18NResource("designer.add_other_rule")%>", "packageApplyRuleListOper.addRuleValues()");
		applyRuleMenu.addItem("<%=getI18NResource("designer.delete_rule")%>", "packageApplyRuleListOper.delApplyRule()");
		
		applyRuleMenu.create();
	}
	this.selectContextMenu = function(){
		if(this.treeList.selectedItem){
			applyRuleMenu.slice("0,1,2");
			applyRuleMenu.popMenu();
		}else{
			applyRuleMenu.slice("0");
			applyRuleMenu.popMenu();
		}
	}
	this.divContextMenu = function(){
			applyRuleMenu.slice("0");
			applyRuleMenu.popMenu();
	}
	this.addApplyRule = function(){
		var sendObj = new Object();
		sendObj.packageDto = packageDto;
		sendObj.applyRules = this.treeList.items;
		
		var req = window.OpenShowDlg('packageApplyRule.jsp',270,300,sendObj);
		if(req){
			this.init();
			for(var i=0;i<this.treeList.items.length;i++){
				if(this.treeList.items[i].id==req.id){
					this.treeList.items[i].setSelected();
					this.click();
					break;
				}
			}
		}
	}
	this.delApplyRule = function(){
		if(this.treeList.selectedItem){
			if(window.confirm("<%=getI18NResource("designer.confirm_delete_rule")%>")){
				var res = callRemoteFunction('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','deletePackageApplyRule',document.all.tlvApplyRule.selectedItem.id);
				this.treeList.selectedItem.remove();
				this.click();
			}
		}
	}
	this.addRuleValues = function(){
		if(this.treeList.selectedItem){
			var vObj = {'packageId':packageid,'ruleId':this.treeList.selectedItem.id,'areaId':this.treeList.selectedItem.areaId};
			var req = window.OpenShowDlg('packageRuleValueAdd.jsp',460,550,vObj);
			if(req){
					this.click();
			}
		}
	}
	this.clearTbl = function(){
		ruleValueTd.innerHTML = '';
	}
}
function delNowRow(btn){
	if(window.confirm("<%=getI18NResource("designer.confirm_delete_rule")%>")){
		//\u5220\u9664\u67d0\u4e00\u4e2a\u9002\u5e94\u6761\u4ef6
		var row = GetRow(btn);
		var groupId = row.cells[row.cells.length-1].children[1].value;
	  var res = callRemoteFunction('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','deletePackageRuleValueByGroup',groupId);
		
		ruleValeTbl.deleteRow(row.rowIndex);
	}
}
function GetRow(elm){
  return (elm)? ((elm.tagName=="TR")? elm : GetRow(elm.parentElement)) : elm;
}
//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
