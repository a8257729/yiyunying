<%@ page contentType="text/html; charset=utf-8" %>
<html>  
<head> 
<title>移动服务平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">

<link title="slate" href="../public/css/style01.css" rel="stylesheet" type="text/css">     
<link title="blue" href="../resources/css/style1.css" rel="stylesheet" type="text/css" disabled="true">
<link title="olive" href="../resources/css/style3.css" rel="stylesheet" type="text/css" disabled="true">
<link title="red" href="../resources/css/style2.css" rel="stylesheet" type="text/css" disabled="true">
<link title="purple" href="../resources/css/style4.css" rel="stylesheet" type="text/css" disabled="true">
<link title="midnight" href="../resources/css/style5.css" rel="stylesheet" type="text/css" disabled="true">
<link title="orange" href="../resources/css/style6.css" rel="stylesheet" type="text/css" disabled="true">
<link title="green" href="../resources/css/style7.css" rel="stylesheet" type="text/css" disabled="true">
<link title="access" href="../resources/css/style8.css" rel="stylesheet" type="text/css" disabled="true">

<script language="javascript" src="../public/script/helper.js"></script>
<script language="javascript" src="../public/script/XmlInter.js"></script>
<script language="javascript" src="../public/script/FormExt.js"></script>
<script language="javascript" src="../public/script/SessionJs.jsp"></script>
<script language="javascript" src="../public/script/xworkAction.js"></script>
<script type="text/javascript"> 
				function getStyle(){
					  
					var session1 = GetSession();
					var styles = document.getElementsByTagName("link");
					for(var i=0;i<styles.length;i++){
						var a = styles[i];
						if(a.getAttribute("rel").indexOf("style") != -1&& a.getAttribute("title") ){
							a.disabled = true;
							
							if(a.getAttribute("title")==session1.style){
								a.disabled=false;
							}
						}
					}
				}
				getStyle();
		</script>
 
</head>  
<body style="margin:0;overflow:hidden">  
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
<tr valign="top">
<td height="75"><iframe name="topFrame" src="top.html" frameborder=0 width="100%" height="100%"></iframe></td>
</tr> 
<tr>
<td valign="top" id = 'mainTd' width="100%"><iframe style="border-style:none" name="mainFrame" src="mainTab.jsp?type=1" frameborder=0 width="100%" height="100%"></iframe></td>
</tr>
<tr align="center" >

  <td height="23"> 
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../public/image/login_caiqie_01.png" >
  		<tr>
  		  <td width="60%" align="right" class="copyright-1">南京中兴软创科技有限责任公司 版权所有 1999-2030 </td>
  		  <td width="40%" align="center" class="navmess">
  		  	<marquee width="40%" scrollamount="2" onmouseover=this.stop() onmouseout=this.start() style="padding: 0px; white-space: nowrap;">
  		  		<div id="divUserInfo"></div>
  		  	</marquee>  		  	
  		  </td>
  		</tr>
  	</table>
  </td>
   <!-- 
  <td height="23" background="../public/img/login_caiqie_01.png" class="copyright-1">南京中兴软创科技有限责任公司 
    版权所有 1999-2030 </td> -->
</tr>
</table> 
</body> 
</html> 
  


<script language="JScript">
var session1 = GetSession();
function setUserInfoHTML(){
	var html = session1.job.jobName + " ：" + session1.staff.staffName+"欢迎您！";
	html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	html += "组织路径 ：" + session1.org.orgPathName;
	
	document.all.divUserInfo.innerHTML = html;
}
setUserInfoHTML();
<%@ include file="js/logon.js"%>
<%@ include file="js/main.js"%>
</script>

