<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>   
<!-- TemplBeginEditable name="doctitle" -->
<title>选择职位</title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../public/css/style.css">
<script language="javascript" src="../public/script/helper.js"></script>
<script language="javascript" src="../public/script/XmlInter.js"></script>
<script language="javascript" src="js/FormExt.js"></script>
<script language="javascript" src="js/logon.js"></script>
<!-- TemplBeginEditable name="head" -->
<!-- \u9009\u62e9\u804c\u4f4d -->
<!-- TemplEndEditable -->   
<style>
body {
	margin:0;
	padding:0; 
}
body {
	background-color:white;
	font-size:12px;
	font-family:Arial, Helvetica, sans-serif;
	margin:0px;
	padding:0px;
	color:white;
}
ul, li {
	margin:0px;
	padding:0px;
}
li {
	display: list-item;
	list-style:none;
	list-style-position:outside;
	text-align:center;
	font-weight:bold;
	float:left;
}
a:link {
	color:#336601;
	text-decoration:none;
	float:left;
	width:100px;
	padding:3px 5px 0px 5px;
}
a:visited {
	color:#336601;
	text-decoration:none;
	float:left;
	padding:3px 5px 0px 5px;
	width:100px;
}
a:hover {
	color:white;
	float:left;
	padding:3px 3px 0px 20px;
	width:88px;
	text-decoration:none;
	background-color:#539D26;
}
a:active {
	color:white;
	float:left;
	padding:3px 3px 0px 20px;
	width:88px;
	text-decoration:none;
	background-color:#BD06B4;
}
#nav {
	width:400px;
	height:300px;
	overflow-x:no;
	overflow-y:auto;
	border-bottom:0px;
	padding:0px 5px;
	position:absolute;
	z-index:1;
	margin-left:15px;
}
.menu1 {
	width:380px;
	height:38px;
	line-height:26px;
	margin:6px 4px 0px 0px;
	border:1px solid #9CDD75;
	background-color:#F1FBEC;
	color:#336601;
	padding:6px 0px 0px 0px;
	cursor:hand;
	overflow-y:hidden;
}
.menu2 {
	width:380px;
	height:38px;
	line-height:26px;
	margin:6px 4px 0px 0px;
	background-color:#EFF7FF;
	color:#999999;
	border:1px solid #96C2F1;
	padding:6px 0px 0px 0px;
	overflow-y:hidden;
	cursor:hand;
}
.confStyle1{
	width:120px;
	height:28px;
	margin:6px 4px 0px 0px;
	color:#999999;
	padding:6px 0px 0px 0px;
}
.confStyle2{
	width:300px;
	height:28px;
	margin:6px 4px 0px 0px;
	color:#999999;
	padding:6px 0px 0px 0px;
}

</style>

</head>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="110"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="414"><img src="../resources/images/selectJob/selectjob01.gif" width="414" height="110"></td>
          <td style="background:url(../resources/images/selectJob/selectjob02.gif) repeat-x">&nbsp;</td>
          <td width="414"><img src="../resources/images/selectJob/selectjob03.gif" width="414" height="110"></td>
        </tr>
      </table></td>
  </tr>   
  <tr> 
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="849"><img src="../resources/images/selectJob/selectjob04.gif" width="849" height="130"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td> 
  </tr> 
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50"><img src="../resources/images/selectJob/selectjob05.gif" width="50" height="295"></td>
          <td width="431" valign="top" style="background:url(../resources/images/selectJob/selectjob06.gif) no-repeat"><div id="nav">
            
            </div></td>
          <td><img src="../resources/images/selectJob/selectjob07.gif" width="370" height="295"></td>
        </tr>
      </table></td> 
  </tr>
  <tr>
    <td><img src="../resources/images/selectJob/selectjob08.gif" width="849" height="126"></td>
    
  </tr>
</table>
</body>
</html>
<script language="javascript">
if (document.URL.indexOf("username") > 0){
//参数赋值



var username = GetUrlParameter("username");
var password = GetUrlParameter("password");

if(username == ''){
	alert("用户名不能为空");
	window.close();
}
if(password == ''){
	alert("密码不能为空");
	window.close();
}
var tmpObj = callRemoteFunctionNoTrans("com.ztesoft.iom.datacommon.ToolQuery","getServerDate");
/* 清除数据库中相同ip地址的记录 */
try{
	logonOper.callLogon("com.zterc.uos.oaas.web.WebLoginManager","clearBadOnlineInfo");
}catch(ex){
	alert(ex.message);
	window.close();
}
var _style = 'darkblue';
/* 登陆系统 */
var recObj = logonOper.callLogon("com.zterc.uos.oaas.web.WebLoginManager", "login", username, password, gLangCode);
if(recObj==null){
	alert("登陆失败");
	window.close();  
}
switch(recObj.returnStr){
	case '1':
		alert("用户名和密码不符！");
		window.close();
		break;
	case '2':
		alert("您没有任何职位！");
		window.close();
		break;
	case '3':
		logonOper.handleLogOnNumber(recObj,_style);
		window.close();
		break;
	case '4':
		logonOper.handlePwdAlert(recObj,_style);
		window.close();
		break;
	case '5':
		logonOper.handlePwdExpire(recObj);
		window.close();
		break;
	case '6':
		break;
	case '7':
		logonOper.handleLogonErrNumber(recObj);
		window.close();
		break;
	default:
		alert(recObj.returnStr);
		window.close();
}
}

DoFSCommand(1,1);

//
function DoFSCommand(command, args) {
//document.all.win.style.display="block";
logonOper.queryJob();
}
//
//var fatherId="";
//var firstClick=0;
var xmlDoc = new ActiveXObject("Msxml.DOMDocument");
var xmlText = new ActiveXObject("Microsoft.XMLHTTP");
var _style = GetUrlParameter("_style");

function LoadText(URL){
xmlText.open("POST",URL,false);
xmlText.setRequestHeader("Content-Type","text/xml; charset=UTF-8");
xmlText.send();
var ss=bytes2bstr(xmlText.responsebody);
return ss;
}
function LoadXML(URL){
xmlDoc.async = false;
xmlDoc.resolveExternals = false;
xmlDoc.load(URL);
return xmlDoc;
}
function custClick(custURL){
var thisTextUrl="/htm/cust"+custURL+".txt";
var thisJsUrl="/htm/cust"+custURL+".js";
var strText=LoadText(thisTextUrl);
myContent.innerHTML=strText;
document.all("textJs").src=thisJsUrl;
}
</script>

 