<%@ page contentType="text/html; charset=utf-8" %>
<html>  
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>移动服务平台</title>
<link rel="stylesheet" type="text/css" href="public/css/style01.css">
<script language="javascript" src="public/script/helper.js"></script>
<script language="javascript" src="public/script/XmlInter.js"></script>
<script language="javascript" src="public/script/FormExt.js"></script>
<link href="style01.css" rel="stylesheet" type="text/css">
</head>
<body background="logo_bg.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1" onSubmit="return false;">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
      <td align="center" valign="middle"> 
        <table width="800" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><img src="logo1.png" width="557" height="502"></td>
          </tr>
          <tr> 
            <td height="67" align="center" background="logo_wbg.png"> 
              <table width="60%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="15%"  class="font1">用户名：</td>
                  <td><input name="username" id="username2" type="text" class="input3"
                                  		size="20" alt="帐 号" maxlength="20" onKeyDown="if(event.keyCode==13)event.keyCode=9"></td>
                  <td width="10%" class="font1">密&nbsp;&nbsp;码：</td>
                  <td><input name="password" id="password2" type="password" class="input3" 
                                  		size="20" maxlength="50" onKeyDown="if(event.keyCode==13)logonOper.submit();"></td>
                  <td>
                    <input type="submit" name="Submit" value="登录" onclick="logonOper.submit()" width="97" height="25" style="font-family:微软雅黑;cursor:hand"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="97" align="center" valign="bottom" class="copyright1">南京中兴软创科技有限责任公司 
              版权所有 1999-2030</td>
          </tr>
        </table> </td>
  </tr>
</table>
</form>
</body>
</html>
<script language="javascript">

/* 全局变量 */
//window.attachEvent("onload",fnInputOper);

var logonOper = new LogonOper();

try {
	logonOper.initLang();
}
catch(ex) {
}

try {
	logonOper.initLoad();
}
catch(ex) {
}
/* 初始化 */

//////////////////////////////////////////
//Class类区

/* 登陆操作Class */
function LogonOper(){
	_extends(this, FormExt, '"form1"');
	//
	this.initLoad = function(){
		this.form.username.focus();
	}

	/* 提交 */
	this.submit = function(){
		//if(this.validator())return;
		if(Trim(this.form.username.value) == ''){
			alert("用户名不能为空");
			this.form.username.focus();
			return;
		}
		if(this.form.password.value == ''){
			alert("密码不能为空");
			this.form.password.focus();
			return;
		}
		var _style = 'darkblue';
		/* 登陆系统 */
		var recObj = this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","login",document.all.username.value ,document.all.password.value, 'lan');
		if(recObj==null){
			alert("登陆失败");
			this.form.username.focus();
			return;
		}
		switch(recObj.returnStr){
		case '1':
			alert("用户名和密码不符！");
			this.form.password.focus();
			this.form.password.select();
			break;
		case '2':
			alert("您没有任何职位！");
			this.form.username.focus();
			break;
		case '3':
			this.handleLogOnNumber(recObj,_style);
			break;
		case '4':
			this.handlePwdAlert(recObj,_style);
			break;
		case '5':
			this.handlePwdExpire(recObj);
			break;
		case '6':
			this.logonSystem(_style);
			break;
		case '7':
			this.handleLogonErrNumber(recObj);
			break;
		case '8':
			alert("帐号已锁定");
			this.form.password.focus();
			this.form.password.select();
			break;
		default:
			alert(recObj.returnStr);
		}
	}

  /**错误登陆次数超过允许值处理*/
	this.handleLogonErrNumber = function(recObj){
		var str = "您错误登陆的次数已经超过" + recObj.logonErrAllowTimes + "次，不允许再试";
		alert(str);
		window.close();
	}

  /* 进入系统 */
	this.logonSystem = function(_style){
		fnInputOper(false);
		window.location.href="main/selectJob.jsp?_style=" + _style;
	}

   /**密码修改日期强制修改处理*/
	this.handlePwdExpire = function(recObj){
		var str = HtmlDecode("您的密码已经") + recObj.diffDay + HtmlDecode("天未修改了，为了确保安全性，必须每隔") + recObj.pwdExpireDate + HtmlDecode("天修改一次密码。请修改密码");
		alert(str);
		OpenShowDlg("./oaas/changePwd.jsp",210,310,recObj);
	}

  /**密码修改日期警告处理*/
	this.handlePwdAlert = function(recObj,_style){
  		var str = HtmlDecode("天未修改了，为了确保安全性，必须每隔") + recObj.diffDay + HtmlDecode("天修改一次密码。") + recObj.pwdExpireDate + HtmlDecode("天修改一次密码。") + "您是否现在修改密码？";
		if (window.confirm(str)){
			OpenShowDlg("./oaas/changePwd.jsp",210,310,recObj);
		}else{
			this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","addLogforced",this.getValue(this.form.username) ,this.getValue(this.form.password));
			this.logonSystem(_style);
		}
	}

  /* 超出允许登陆人数的处理 */
	this.handleLogOnNumber = function(recObj,_style){
  		if(recObj.permitLogonNumber ==0){
  			alert("该用户允许同时登陆的次数为零，请更换用户重新登陆");
  			return;
  		}
  		var confirStr = "目前已有"+recObj.logonNumber+"个用户同时登陆，已达到允许值，是否强制进入系统？";
		if (window.confirm(confirStr)){
			//请选择将下面IP地址的用户踢出系统



			try{
				var returnObj =OpenShowDlg("common/logonIpDetail.jsp",400,300,recObj);
				if(returnObj==null){
					return;
				}else{
					var ipArr =new Array(returnObj.length);
					for(var i=0;i<returnObj.length;i++){
						ipArr[i] =returnObj[i];
					}
				  this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","setAbnormalByIps",ipArr);
				}
			}catch(ex){
				alert("强制用户退出失败");
				return;
			}
			//强制进入的计日志
			this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","addLogforced",this.getValue(this.form.username) ,this.getValue(this.form.password));
	  		this.logonSystem(_style);
		}else{
			window.close();
		}
	}
  /* 查询职位 */
	this.queryJob = function(){
		var tmpStr = "";
		var recArr = this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","findJobs");
		if(recArr.length<1){ this.selectJob(-1); return;}
		if(recArr.length==1){ this.selectJob(recArr[0].jobId); return;}
		tmpStr += " <ul>"
		for(var i=0; i<recArr.length; i++){
			var job = "<li class=\"menu2\" onMouseOver=\"this.className='menu1'\" onMouseOut=\"this.className='menu2'\"><a  onclick='logonOper.selectJob("+ recArr[i].jobId  +")'>"+recArr[i].orgPathName+"/"+recArr[i].jobName+"</a></li>"
			//var job = '<div class="job_div" onmouseover="this.className=\'job_div_mouseover\';MM_swapImage(\'Image' + i+1 + '\',\'\',\'../images/main/arrow_over.gif\',1)" onmouseout="this.className=\'job_div_mouseout\';MM_swapImgRestore()">&nbsp;<img src="../images/main/arrow_out.gif" name="Image'+ i+1 + '" border="0" align="absmiddle"><a href="javascript:logonOper.selectJob('+ recArr[i].jobId  +')" class="humen">&nbsp;'+recArr[i].orgPathName+"/"+ recArr[i].jobName  +'</a></div>';
			tmpStr += job;
		}
		tmpStr+="</ul><ul>";
		tmpStr+="<li class=\"confStyle1\">系统个性化设置</li>";
		tmpStr+="<li class=\"confStyle2\">&nbsp;<input type='radio' name='confType' value='0' checked='chceked'>个人(默认)" +
				"&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='confType' value='1'>组织" +
				"&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='confType' value='2'>职位</li>";
		tmpStr+="</ul>";
		
		document.all.nav.innerHTML = tmpStr;
	}

  /* 设置本地变量 */
	this.changeLang = function(){
  		setCookie(this.getValue(this.form.langCode));
  		window.location.reload();
	}
 
	this.initLang = function(){
  		this.setValue(this.form.langCode,gLangCode);
	}

  /* 选择职位 */
	this.selectJob = function(jobIdP){
		if(jobIdP>0){
			if(!this.areaCheck(jobIdP)){
				alert('没有访问当前系统权限！');
				return;
			}
		}

		var recObj = this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","findPrivs",jobIdP);
		var _style = GetUrlParameter("_style");
		var newUrl = "main.jsp";
		if(recObj){
			alert(recObj);
		}else{
			var confType = this.getConfType();
			window.location.href = "/MOBILE/LoginStaffLogServlet?confType="+confType;
			var newWin = window.open(newUrl,"_blank","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,height="+ (window.screen.availHeight-30) +",width="+ (window.screen.availWidth-10) +",left=0,top=0");
			if(navigator.appVersion.indexOf('MSIE 7.0')>=0){
				window.open('','_self'); 
			}else window.opener=null;
			window.close();

			newWin.focus();
		}
	}
	this.browerType = 'ie';
	
	/*获取个性化设置类型*/
	this.getConfType = function(){
		var confTypes = document.getElementsByName("confType");
		if(confTypes){
			for(var i=0;i<confTypes.length;i++){
				var confType = confTypes[i];
				if(confType.checked == true){
					return confType.value;
				}
			}
		}
	}
	
  /* 内部调用 */
	this.callLogon = function(a1,b1){
		var xmlHttp= this.makeHTTP();
		var doc=makeDOM();var node=doc.createProcessingInstruction("xml","version='1.0'");var url=gWebAbsPath+"/logonin";
		doc.appendChild(node);node=doc.appendChild(doc.createElement("Function"));
		node.setAttribute("name",b1);
		node.setAttribute("serviceName",a1);
		node.setAttribute("userTransaction","true");
		for(var i=2;i<arguments.length;i++)
		{var elm=doc.createElement("Param");
		var type=getObjectType(arguments[i]);elm.setAttribute("type",type);
		packageObject(elm,type,arguments[i]);
		node.appendChild(elm);}
		var retVal;
		xmlHttp.open("POST",url,false);
		if(this.browerType=='firefox'){
			var x = new XMLSerializer().serializeToString(doc);
            xmlHttp.send(new XMLSerializer().serializeToString(doc));
        }else{
            xmlHttp.send(doc.xml);
        }
		if(xmlHttp.status!=200){
			throw new Error(0,"Network issue or remote server issue");return;
		}else 
			retVal=xmlHttp.responseText;
			if(this.browerType =='firefox'){
		        var dp = new DOMParser(); 
		        var doc = dp.parseFromString(retVal, "text/xml");
		        var root = doc.firstChild;
			    }else{
			        var doc = makeDOM();
			        doc.loadXML(retVal);
			        var root = doc.selectSingleNode("//Output");
			    }
			if(!root)return null;
			return getObjectFromXml(root,root.getAttribute("type"));
	}
	this.makeHTTP = function() {
		var A = null;
		try {
			A = new ActiveXObject('Msxml2.XMLHTTP')
		} catch (e) {
			try {
				A = new ActiveXObject('Microsoft.XMLHTTP')
			} catch (oc) {
				A = null
			}
		}
		if (!A && typeof XMLHttpRequest != 'undefined') {
			this.browerType='firefox'
			A = new XMLHttpRequest()
		}
		return A
	}

  /*职位所在区域校验：
		省级职位可以访问任何本地网系统，本地网职位只能访问本地系统




	add by liangli 2007.03.5
  */
	this.areaCheck = function(jobId){
		var ret = true;
		try{
			ret = this.callLogon("com.ztesoft.mobile.common.web.WebLoginManager","areaCheck",jobId);
		}
		catch(ex)	 {
			alert(ex);
		}
		return ret;
	}
}
//////////////////////////////////////////
//function函数区




function fnInputOper(isLoad){
	try {
		var oPersist=document.all.oPersistInput;
		if(isLoad){
			oPersist.load("oXMLBranch");
			oPersist.value = NullRepl(oPersist.getAttribute("sPersist"),"");
		}else{
			oPersist.setAttribute("sPersist",oPersist.value);
			oPersist.save("oXMLBranch");
		}
	}
	catch (ex){
	}
}


var radios = document.getElementsByName("lanRadio");
for (var i=0; i<radios.length; i++) {
	radios[i].onclick = lanClick;
}

function lanClick()
{
	for (var i=0; i<radios.length; i++) {
		if (radios[i].checked) {
			switch (i) {
				case 0: {
					location.href = "logon.jsp?language=zh_cn";
					break;
				}
				case 1: {
					location.href = "logon.jsp?language=en_us";
					break;
				}
			}
		}
	}
}
</script>
