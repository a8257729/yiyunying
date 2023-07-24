<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.author_choose")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" />
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
	<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td width="100%">
			<ZTESOFT:treelist id="staffTree" name="staffTree" class="treelist" width="100%" height="260" onItemExpand ="authorOperation.expand()">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.name")%>" propertyName="Name" />
					<ZTESOFT:column propertyName="Id" />
					<ZTESOFT:column propertyName="staffType" />
					<ZTESOFT:column propertyName="flag" />			
					<ZTESOFT:column propertyName="areaId" />		
					<ZTESOFT:column propertyName="areaName" />	
				</ZTESOFT:columns>
			</ZTESOFT:treelist>		
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick='authorOperation.submit()'>
	  	<input type="button" class="button_blue" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="authorOperation.cancel()">
	  </td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
</table>
<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-10
// Author : Xu.fei3
// commits: Choose an author
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var orgId = window.dialogArguments;
var authorOperation = new AuthorOperation();

/* \u521d\u59cb\u5316 */
authorOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
function AuthorOperation(){
	this.staffTree = document.all.staffTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){	
		/**
		var _data;		
		_data = callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "findSubOrgJobStaffXml", orgId, 3);
		this.staffTree.loadByXML(_data);
		*/
		
	  var orgJobData= callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findTopOrgs",orgId);
		if(orgJobData!=null){
			for(var i=0;i<orgJobData.length;i++){
				orgJobData[i].Id = orgJobData[i].jobId;
				orgJobData[i].Name =  orgJobData[i].jobName;			
				orgJobData[i].staffType =  "ORG";
				orgJobData[i].children=new Array();			
			}		
			this.staffTree.loadByData(orgJobData);
		}	
		
	}
	
	//\u5c55\u5f00
	this.expand = function()	{
		var selItem = this.staffTree.selectedItem;
 		if(!selItem.isLoad){
			var orgId = selItem.Id;
			var type = selItem.staffType;
			if (type=='ORG'){
				var jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findDrirectOrgsJobs",orgId);
				for(var i=0;i<jobs.length;i++){
					jobs[i].Id =  jobs[i].jobId;
					jobs[i].Name =  jobs[i].jobName;
					if(jobs[i].orgPathName!=""){
						jobs[i].staffType =  "JOB";						
					 	jobs[i].children=new Array();
					}else{
						jobs[i].staffType =  "ORG";						
						jobs[i].children=new Array();
					}
				}
				selItem.insertByData(jobs);
				selItem.isLoad = true;
				selItem.expand(true);
			}else if (type=='JOB'){
				var jobId = selItem.Id;
				var staffs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManager", "findStaffsByJob",jobId);
				for(var i=0;i<staffs.length;i++){
					staffs[i].Id =  staffs[i].staffId;
					staffs[i].Name =  staffs[i].staffName;
					staffs[i].staffType =  "STA";
					staffs[i].areaName =  selItem.areaName;
					staffs[i].areaId =  selItem.areaId;
				}
				selItem.insertByData(staffs);
				selItem.isLoad = true;
				selItem.expand(true);
			}
			var allItems = this.staffTree.items;
		  var itemsLen = allItems.length;
			for(i=0;i<itemsLen;i++){				
				if(allItems[i].staffType=='ORG'){
					allItems[i].flag = true;
				}	else if(allItems[i].staffType=='JOB'){
					allItems[i].flag = true;
				}	else if(allItems[i].staffType=='STA'){
					allItems[i].flag = false;
				}			
			}	
    }
	}	
	
	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Object();
		
		if(this.staffTree.selectedItem.staffType != "STA"){
			ErrorHandle("<%=getI18NResource("designer.selection_not_staff")%>");
			return;
		}
		
		result.staffId = this.staffTree.selectedItem.Id;	
		result.staffName = this.staffTree.selectedItem.Name;
		
		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
