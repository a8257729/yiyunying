<%@ page contentType="text/html;charset=UTF-8" language="java" import = "com.zterc.uos.oaas.vo.*,java.util.* " %>
<%
/* @edit moejoe(lmh_user@hotmail.com) */
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);

Object obj = session.getAttribute("staff");

if(obj == null){
   out.print("function Session(){_import('AlternateData');var altData = new AlternateData();var obj = altData.getValue();if(obj&&obj.session1){altData.setValue(obj);session1.hasPriv=function(s){return session1.privStr.indexOf(s)>-1;};return obj.session1;};this.staff={staffId:0, staffName:'', userName:''};this.org={orgId:0, orgName:'', orgPathCode:''};this.orgs={orgIds:'', orgNames:''};this.job={jobId:0, jobName:'',specialJobId:-1};this.jobs={jobIds:'', jobNames:''};this.area={areaId:0, areaName:''};this.hasPriv=function(s){return false;};};");
   out.print("_import('AlternateData');var altData = new AlternateData();var obj = altData.getValue();if(!obj||!obj.session1){alert('Session Expired, please login!');top.location.href='"+ request.getContextPath() +"/logon.jsp';};");
   out.close();return;
}else{
  
  Map styleMap = (Map)session.getAttribute("style");
  List systems = (List)session.getAttribute("systems");
  Staff staff = (Staff)obj;
  Organization org = (Organization)session.getAttribute("org");
  Job job = (Job)session.getAttribute("job");
  Job specialJob = (Job)session.getAttribute("specialJob"); 
  Area area = (Area)session.getAttribute("area"); 
  Job[] jobs = (Job[]) session.getAttribute("jobs");
  Organization[] orgs = (Organization[])session.getAttribute("orgs");
  WorkOrderTranOrg[] workOrderTranOrgs = (WorkOrderTranOrg[])session.getAttribute("workOrderTranOrg");
  String jobIds = "";
  String jobNames = "";
  String orgIds = "";
  String orgNames = "";
  String workOrderTranOrgIds = "";
  String workOrderTranOrgNames = "";
  String staffLoginLogId = "0";
  String loginTocken = "";
  String systemsStr = "[]";
  String styleTitle = "slate";
  String confType = "0";
  System.out.print("styleMap:"+styleMap);
  if(styleMap!=null){
  	 styleTitle = styleMap.get("title").toString();
  	 confType = styleMap.get("type").toString();
  }
if (jobs!=null){
  for (int i=0;i<jobs.length;i++){
 	 jobIds += jobs[i].getJobId() + ",";
 	 jobNames += jobs[i].getJobName() + ",";
 	 orgIds += jobs[i].getOrgId() + ",";
 	 orgNames += jobs[i].getOrgName() + ",";
  }

  if(workOrderTranOrgs!=null){
		for (int i=0;i<workOrderTranOrgs.length;i++){
 			workOrderTranOrgIds += workOrderTranOrgs[i].getOrgId() + ",";
			workOrderTranOrgNames += workOrderTranOrgs[i].getOrgName() + ",";
		}
		if(!"".equals(workOrderTranOrgIds)){
			workOrderTranOrgIds = workOrderTranOrgIds.substring(0,workOrderTranOrgIds.length()-1);
			workOrderTranOrgNames = workOrderTranOrgNames.substring(0,workOrderTranOrgNames.length()-1);
		}
  }
  
  
  if(systems!=null){
  	StringBuffer sbuf= new StringBuffer("[");
	  for(int i=0;i<systems.size();i++){
	  	Map map = (Map)systems.get(i);
	  	if(i!=systems.size()-1){
	  		sbuf.append("{sysId:").append(map.get("sysId")).append(",ssoTypeId:").append(map.get("ssoTypeId")).append(",sysCode:'").append(map.get("sysCode")).append("',thirdUsername:'").append(map.get("thirdUsername")).append("',thirdRoleId:").append(map.get("thirdRoleId")).append(",sysIpAddress:'").append(map.get("sysIpAddress")).append("',sysPort:").append(map.get("sysPort")).append("}").append(",");	
	  	}else{
	  		sbuf.append("{sysId:").append(map.get("sysId")).append(",ssoTypeId:").append(map.get("ssoTypeId")).append(",sysCode:'").append(map.get("sysCode")).append("',thirdUsername:'").append(map.get("thirdUsername")).append("',thirdRoleId:").append(map.get("thirdRoleId")).append(",sysIpAddress:'").append(map.get("sysIpAddress")).append("',sysPort:").append(map.get("sysPort")).append("}");
	  	}
	  }
	  sbuf.append("]");
	  systemsStr = sbuf.toString();
  }
  
	
  jobIds = jobIds.substring(0,jobIds.length()-1);
  jobNames = jobNames.substring(0,jobNames.length()-1);
  orgIds = orgIds.substring(0,orgIds.length()-1);
  orgNames = orgNames.substring(0,orgNames.length()-1);
  String  remoteAddr = request.getRemoteAddr();
  String remoteServerHost = (String)System.getProperty("remoteServerHost");
  String runtimeTier = (String)System.getProperty("com.zterc.uos.RuntimeTier");
  out.print("function Session(){this.style='"+styleTitle+"';this.confType='"+confType+"';"+"this.systems="+systemsStr+";this.loginInfo={staffLoginLogId:"+staffLoginLogId+",loginTocken:'"+loginTocken+"'};this.staff={staffId:"+ staff.getStaffId() +", staffName:'"+ staff.getStaffName() +"', userName:'"+ staff.getUserName() +"',remoteAddr:'"+remoteAddr +"', remoteServerHost:'"+remoteServerHost+"',runtimeTier:'"+runtimeTier+"'};"+ "this.org={orgId:"+ org.getOrgId() +", orgName:'"+ org.getOrgName() +"', areaId:'"+ org.getAreaId() +"', orgPathCode:'"+ org.getOrgPathCode() +"', orgPathName: '"+ org.getOrgPathName() +"'};" + "this.job={jobId:"+ job.getJobId() +", jobName:'"+ job.getJobName() +"', isBasic:"+ job.getIsBasic() +", specialJobId:"+ ((specialJob!=null)?specialJob.getJobId():-1) +"};" + "this.jobs={jobIds:'"+ jobIds +"', jobNames:'"+ jobNames+"'};" + "this.orgs={orgIds:'"+ orgIds +"', orgNames:'"+ orgNames+"'};" + "this.area={areaId:"+ area.getAreaId() +", parentId:"+ area.getParentId() +", areaName:'"+ area.getAreaName() +"',pathCode:'"+ area.getPathCode() +"',areaCode:'"+ area.getAreaCode() +"'};"+ "this.hasPriv=function(s){return ('"+  (String)session.getAttribute("privilege") +"'.indexOf(s)>-1);};"+ "this.workOrderTranOrgs = {workOrderTranOrgIds:'" +workOrderTranOrgIds+"',workOrderTranOrgNames:'"+workOrderTranOrgNames+"'};"+"}");
}
}%>
