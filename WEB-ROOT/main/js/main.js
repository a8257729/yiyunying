/* 初始化 */
if(document.all.staffNameSpan){
	session1 = GetSession();
	document.all.staffNameSpan.innerHTML = session1.staff.staffName;
}
/* 显示帮助*/
function help() { 
	var newWin = window.open("../systemHelper.jsp","","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,height="+ (window.screen.availHeight-30) +",width="+ (window.screen.availWidth-10) +",left=0,top=0");
} 
/* 显示登陆人员信息*/
function showStaffInfo(){
	var session1 = GetSession();
	var _resultObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb", "findByKey", session1.staff.staffId);   
	var _obj=ObjectClone(_resultObj);
	_obj.operation = "update";
	_obj.jobId = session1.job.jobId;		
	_obj.jobName = session1.job.jobName;
	var newObj = window.showModalDialog("../../oaas/SelfStaffInfo.jsp", _obj, "dialogWidth:650px;dialogHeight:580px;status:no");	
}
/* 开启/关闭左边窗口 */
function changLeftBar(){
	if ((document.all("imgLeft").src).indexOf("arrow_left")!=-1){
		document.all("imgLeft").src="../../images/icon/arrow_right.gif";
		document.all("treeTd").style.display="none";
	}else{
		document.all("imgLeft").src="../../images/icon/arrow_left.gif";
		document.all("treeTd").style.display="";
	}
}
/* 开启/关闭上面窗口 */
function changTopBar(){
	if ((document.all("imgTop").src).indexOf("icon_hidden")!=-1){
		document.all("imgTop").src=Gi18n.imgShow_msg;
		document.all("topTd").style.display="none";
	}else{
		document.all("imgTop").src=Gi18n.imgHidden_msg;
		document.all("topTd").style.display="";
	}
}


/* 退出登陆 */
function logonOff(){
	//设置关闭状态
	window.frames['mainFrame'].isClosed=true;
	//退出IM
	if(typeof(window.frames['mainFrame'].imWin)!='undefined'){
		window.frames['mainFrame'].imWin.close();
	}
	//退出日志记录
	window.location.href = "/MOBILE/LogOutStaffLogServlet";
	
	logonOper.callLogon("com.zterc.uos.oaas.web.WebLoginManager","logoff");
	
	window.close();
	window.open('../logon.jsp','','');
}
//
//模块点击事件
function moduleClick(i,moduleId,moduleName){
	mainFrame.reloadLeftMenu(moduleId,moduleName);
	//mainFrame.location.href = "mainTab.jsp?type=0&moduleId="+moduleId+"&moduleName="+moduleName;
}

window.onunload = logonOff;
/*风格切换*/ 

function changeStyle(){
	var recVal = window.OpenShowDlg("../changeStyle.jsp",530,650);
	if(recVal){top.location.href = "../"+ recVal +"/main.jsp";window.onunload=null;}
}