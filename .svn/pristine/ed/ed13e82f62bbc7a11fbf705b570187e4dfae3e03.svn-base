<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.org_position_staff_choose")%> </title>
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
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td width="100%">
			<ZTESOFT:treelist id="participantTree" name="participantTree" class="treelist" width="100%" height="260" onItemExpand ="participantOperation.expand()">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.org_position_staff")%>" propertyName="Name" />
					<ZTESOFT:column propertyName="staffType" />
					<ZTESOFT:column propertyName="Id" />
					<ZTESOFT:column propertyName="flag" />
					<ZTESOFT:column propertyName="areaId" />
					<ZTESOFT:column propertyName="areaName" />
					<ZTESOFT:column display="false" propertyName="orgTypeId" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" value="<%=getI18NResource("designer.ok")%>" onclick='participantOperation.submit()' NAME='btnQry'>
	  	<input name="cancelButton" type="button" class="button_blue" value="<%=getI18NResource("designer.cancel")%>" onclick="participantOperation.cancel()">
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
// ZTESoft corp. 2005-12-15
// Author : Xu.fei3
// commits: Choose a organization/position/staff
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var session = window.dialogArguments;
var participantOperation = new ParticipantOperation();

/* \u521d\u59cb\u5316 */
participantOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
function ParticipantOperation(){
	this.participantTree = document.all.participantTree;

	this.init = function(){

		var _orgJobData = null;
		try{
//			if(session1.area.parentId == 0){
			//\u5168\u67e5
			_orgJobData = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findTopOrgs",-1);
//			}
//			else{//\u67e5\u672c\u7ec4\u7ec7\u53ca\u4e0b\u7ea7

//				_orgJobData = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findTopOrgs",session1.org.orgId);
//			}
			if(!_orgJobData){
				ErrorHandle("<%=getI18NResource("designer.no_org_found")%>");
				return;
			}
			for(var i=0;i<_orgJobData.length;i++){
  			_orgJobData[i].Id = _orgJobData[i].jobId;
  			_orgJobData[i].Name = _orgJobData[i].jobName;

 		 		if(_orgJobData[i].orgPathName!=""){
      		_orgJobData[i].staffType =  "JOB";
      		_orgJobData[i].children=new Array();
				}
				else{
    			_orgJobData[i].staffType =  "ORG";
					_orgJobData[i].children=new Array();
				}
			}
			_orgJobData[0].children=new Array();
			this.participantTree.loadByData(_orgJobData);

			//\u8bbe\u7f6e\u56fe\u6807
			this.setImage();
		}
		catch(ex){
			ErrorHandle(ex.message,1);
		}
	}


	/**\u8bbe\u7f6e\u56fe\u6807*/
	this.setImage = function(){
		var allItems = this.participantTree.items;
		var itemsLen = allItems.length;
		for(i = 0;i < itemsLen;i++){
			//\u7ec4\u7ec7
			if(allItems[i].staffType == 'ORG'){
				//\u7535\u4fe1\u7ec4\u7ec7
				if(allItems[i].orgTypeId == "A01"){
					allItems[i].setImage("../../images/icon/org.gif");
				}
				//\u4ee3\u7ef4\u7ec4\u7ec7
				else if(allItems[i].orgTypeId == "A02"){
					allItems[i].setImage("../../images/icon/orgInstead.gif");
				}
			}
			//\u804c\u4f4d
			else if(allItems[i].staffType == 'JOB'){
				allItems[i].setImage("../../images/icon/job.gif");
			}
			else if(allItems[i].staffType == 'STA'){
				allItems[i].setImage("../../images/icon/people.gif");
			}
		}
	}


	/**\u5c55\u5f00*/
  this.expand = function(){
		var selItem = this.participantTree.selectedItem;
 		if(!selItem.isLoad){
			var orgId = selItem.Id;
      var type = selItem.staffType;
      //\u7ec4\u7ec7\u67e5\u804c\u4f4d

      if(type=='ORG'){
				var jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findDrirectOrgsJobs",orgId);
				for(var i=0;i<jobs.length;i++){
        	jobs[i].Id = jobs[i].jobId;
          jobs[i].Name = jobs[i].jobName;

					if(jobs[i].orgPathName!=""){
            jobs[i].staffType =  "JOB";
            jobs[i].children=new Array();
					}
					else{
          	jobs[i].staffType =  "ORG";
						jobs[i].children=new Array();
					}
				}
				selItem.insertByData(jobs);
				selItem.isLoad = true;
				selItem.expand(true);
			}
			//\u804c\u4f4d\u67e5\u4eba\u5458

			else if(type=='JOB'){
				var jobId = orgId;
				var staffs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManager", "findStaffsByJob",jobId);
				for(var i = 0;i < staffs.length;i++){
					staffs[i].Id = staffs[i].staffId;
          staffs[i].Name = staffs[i].staffName;
					staffs[i].staffType = "STA";
				}
				selItem.insertByData(staffs);
				selItem.isLoad = true;
				selItem.expand(true);
			}
			this.setImage();
		}
	}

//	//\u521d\u59cb\u5316

//	this.init = function(){
//		var orgId = session.org.orgId;
//		var orgJobData= callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findTopOrgs",orgId);
//
//		if(orgJobData!=null){
//			for(var i=0; i<orgJobData.length; i++){
//				orgJobData[i].Id = orgJobData[i].jobId;
//				orgJobData[i].Name = orgJobData[i].jobName;
//				orgJobData[i].staffType = "ORG";
//				orgJobData[i].children = new Array();
//			}
//			this.participantTree.loadByData(orgJobData);
//
//			var allItems = this.participantTree.items;
//			var itemsLen = allItems.length;
//			for(i=0; i<itemsLen; i++){
//				if(allItems[i].staffType=='ORG'){
//					allItems[i].flag = true;
//					allItems[i].setImage("../../images/icon/org.gif");
//				}else if(allItems[i].staffType=='JOB'){
//					allItems[i].flag = true;
//					allItems[i].setImage("../../images/icon/job.gif");
//				}else if(allItems[i].staffType=='STA'){
//					allItems[i].flag = false;
//					allItems[i].setImage("../../images/icon/people.gif");
//				}
//			}
//		}
//	}
//
//	//\u5c55\u5f00
//	this.expand = function(){
//		var selItem = this.participantTree.selectedItem;
//		if(!selItem.isLoad){
//			var orgId = selItem.Id;
//			var type = selItem.staffType;
//			if (type=='ORG'){
//				var jobs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findDrirectOrgsJobs",orgId);
//				for(var i=0; i<jobs.length; i++){
//					jobs[i].Id =  jobs[i].jobId;
//					jobs[i].Name =  jobs[i].jobName;
//					//\u804c\u4f4d
//					if(jobs[i].orgPathName!=""){
//						jobs[i].IMG_SRC ="../../images/icon/job.gif";
//						jobs[i].staffType =  "JOB";
//						jobs[i].children=new Array();
//					}else{
//						jobs[i].IMG_SRC ="../../images/icon/org.gif";
//						jobs[i].staffType =  "ORG";
//						//\u7ec4\u7ec7
//						jobs[i].children=new Array();
//					}
//				}
//				//////
//				selItem.insertByData(jobs);
//				selItem.isLoad = true;
//				selItem.expand(true);
//			}else if (type=='JOB'){
//				var jobId = selItem.Id;
//				var staffs = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManager", "findStaffsByJob",jobId);
//				for(var i=0; i<staffs.length; i++){
//					staffs[i].IMG_SRC = "../../images/icon/people.gif";
//					staffs[i].Id = staffs[i].staffId;
//					staffs[i].Name = staffs[i].staffName;
//					staffs[i].staffType = "STA";
//					staffs[i].areaName = selItem.areaName;
//					staffs[i].areaId = selItem.areaId;
//				}
//				//////
//				selItem.insertByData(staffs);
//				selItem.isLoad = true;
//				selItem.expand(true);
//			}
//			var allItems = this.participantTree.items;
//			var itemsLen = allItems.length;
//			for(i=0; i<itemsLen; i++){
//				if(allItems[i].staffType=='ORG'){
//					allItems[i].flag = true;
//					allItems[i].setImage("../../images/icon/org.gif");
//				}else if(allItems[i].staffType=='JOB'){
//					allItems[i].flag = true;
//					allItems[i].setImage("../../images/icon/job.gif");
//				}else if(allItems[i].staffType=='STA'){
//					allItems[i].flag = false;
//					allItems[i].setImage("../../images/icon/people.gif");
//				}
//			}
//		}
//	}

	//\u63d0\u4ea4
	this.submit = function(){
		var result = new Object();

		var selectedItem = this.participantTree.selectedItem;

		result.type = selectedItem.staffType;

		if(result.type == "STA"){
			result.id = selectedItem.Id + "," + selectedItem.getParentItem().Id;
		}else{
			result.id = selectedItem.Id;
		}

		var name = "";				//\u540d\u79f0\u5168\u8def\u5f84

		if(this.participantTree.selectedItem != null){
			name = selectedItem.Name;
			while(selectedItem.getParentItem() != null){
				selectedItem = selectedItem.getParentItem();
				name = selectedItem.Name + "-" + name;
			}
		}
		result.name = name;


		window.returnValue = result;
		window.close();
	}

	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
