<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>  </title>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<script language="javascript" src="helper.js"></script>
</head>
<body style="margin:0; overflow:hidden" background="../../images/icon/errorwin_bg.jpg">
  <table width="100%" height="153" border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td height="4" colspan="2"></td>
	</tr>
    <tr>
      <td width="108" align="center" valign="top"><img id="imgD" src="../../images/icon/icon_pop_prompt_zh_cn.gif"></td>
      <td height="139" align="center" valign="middle">
      <table><tr><td id="contentD" align="center" valign="middle" style="word-break:break-all">&nbsp;</td></tr></table>
      <div id="clickD" style="display:none;color:#666666;"><span id="click_id">点击</span> <A href="#" onclick="detailClick()"><font color="#ff0000"id="this_id">这里</font></a> <span id="addonS">查看详细信息</span></div>
      <div id="contentDetailD" style="display:none;height:110; overflow-y:auto;word-break:break-all"></div>    </td>
    </tr>
    <tr>
	<td height="33" colspan="2" valign="middle" align="right" background="../../images/icon/errorwin_bottom.gif">
      <input type="submit" name="btn1" id="btn1" class="button_img" value="确定" onClick="submitOK()">&nbsp;<span id="cancelS" style="display:none"><input type="button" name="btn2" id="btn2" class="button" value="取消"  onClick="window.close()"></span>&nbsp;</td>
    </tr>
</table>
</div>

</body>
</html>
<script language="javascript">
<!--

var paramObj = window.dialogArguments;

switch(paramObj.type){
  case 1:
    document.title = "出错";
    document.all.imgD.src="../../images/icon/icon_pop_error_"+gLangCode+".gif";
    break;
  case 2:
    document.title = "警告";
    document.all.imgD.src="../../images/icon/icon_pop_alarm_"+gLangCode+".gif";
    break;
  case 3:
    document.title = "提示";
    document.all.imgD.src="../../images/icon/icon_pop_prompt_"+gLangCode+".gif";
    break;
  case 4:
    document.title = "成功";
    document.all.imgD.src="../../images/icon/icon_pop_success_"+gLangCode+".gif";
    break;
}
if(paramObj.title) document.title = paramObj.title;
document.all.contentD.innerText = paramObj.content;
if(paramObj.contentDetail){
  document.all.contentD.parentElement.parentElement.parentElement.height="75%";
  document.all.clickD.style.display = "";
  document.all.contentDetailD.innerHTML = paramObj.contentDetail;
}
if(paramObj.isReturn) document.all.cancelS.style.display = "";
//////////////////////////////////////////////////////////////////////////
function submitOK(){
  if(paramObj.isReturn)
    window.returnValue = true;
  window.close();
}
function detailClick(){
  if(document.all.contentDetailD.style.display==""){
     document.all.addonS.innerText = "查看详细信息";
     document.all.contentDetailD.style.display="none";
     document.all.contentD.style.display="";
  }else{
     document.all.addonS.innerText = "返回值";
     document.all.contentDetailD.style.display="";
     document.all.contentD.style.display="none";
  }

}

//-->
</script>
