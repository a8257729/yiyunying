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