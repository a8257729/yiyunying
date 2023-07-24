<%@ page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>无标题文档</title>
		<%@ include file="../public/common.jsp"%>
		<link rel="stylesheet" type="text/css" title="blue" href="/MOBILE/ext/resources/css/xtheme-blue.css" />
		<link href="css/main.css" rel="stylesheet" type="text/css" />
		<link href="../webos/css/themesetting.css" rel="stylesheet"  type="text/css" >
		<link href="../webos/css/skins/black.css" rel="stylesheet" />
		<link href="../webos/css/powerFloat.css" rel="stylesheet" type="text/css"/>	
		<link href="../webos/css/smartMenu.css" rel="stylesheet"  type="text/css" />
		<link href="../webos/css/ebizIndex.css" rel="stylesheet"  type="text/css" />
		
		<script src="../webos/js/jquery-1.2.1.pack.js" language="javascript"></script>
		<script language="javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script language="javascript" src="../webos/js/jquery-powerFloat-min.js"></script>
		<script language="javascript" src="../webos/js/jquery-smartMenu.js"></script>
		<script language="javascript" src="../webos/js/jquery-class.js"></script>
		<script language="javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script language="javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.core-min.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.widget-min.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.mouse-min.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.draggable-min.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.droppable-min.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.sortable.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.effect.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.effect-fade.js"></script>
		<script language="javascript" src="../webos/js/jquery.ui.effect-blind.js"></script>
		<script language="javascript" src="../webos/js/data.js"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
<style type="text/css">
		
<!--
.moth-title {
	font-family: "微软雅黑";
	font-size: 15px;
	color: 026777;
	font-weight: bold;
}
.font {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	color: 044551;
	text-decoration: none;
}
.button {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	height: 24px;
	width: 100px;
}
.title {
	font-family: "微软雅黑";
	font-size: 14px;
	font-weight: bold;
	color: 044551;
}
.font1 {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	font-weight: bold;
	color: 044551;
}
.table {
	border-bottom-width: 1px;
	border-bottom-style: dotted;
	border-bottom-color: 2e98ab;
}
-->
</style>
<style type="text/css">
<!--
.title2 {
	font-family: "微软雅黑";
	font-size: 14px;
	font-weight: bold;
	color: 3f7781;
}
.adv {position:relative;float:left;margin-left:10px; width:546px; height:189px; overflow:hidden;}
.adv_list img{width:600px; height:300px;border:0}
.adv_bg {position:absolute; bottom:0;background-color:#000;height:28px;filter: Alpha(Opacity=50);opacity:0.5;z-index:1;cursor:pointer; width:600px; }
.adv_info{position:absolute; bottom:0; left:5px;height:22px;color:#fff;z-index:2;font-size:14px;cursor:pointer; padding-right:150px}
.adv_text {position:absolute;width:120px;z-index:3; right:3px; bottom:3px;}
.adv_ul {position:absolute;list-style-type:none;z-index:4;margin:0; padding:0; bottom:5px; right:3px;}
.adv_ul li {width:33px;line-height:16px;text-align:center;float:left;display:block;color:#0066aa;background:#cccccc;cursor:pointer;margin-right:1px}
.adv_ul .adv_on {background:#0066aa;color:#cccccc}
.adv_list a{position:absolute;}
.msg_title_p {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	font-weight: bold;
	color: 044551;
}
.msg_content_p {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	color: 044551;
	text-decoration: none;
	height:28px;
	overflow:hidden;
}

-->
</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$.fn.myScroll = function(options){
				var defaults = {
					speed:10,  
					rowHeight:45 
				};
				var opts = $.extend({}, defaults, options),intId = [];
				function marquee(obj, step){
					obj.find("ul").animate({
						marginTop: '-=1'
					},0,function(){
							var s = Math.abs(parseInt($(this).css("margin-top")));
							if(s >= step+24){
								$(this).find("li").slice(0, 1).appendTo($(this));
								$(this).css("margin-top", 0);
							}
						});
					}
					this.each(function(i){
						var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
						intId[i] = setInterval(function(){
							if(_this.find("ul").height()<=_this.height()){
								clearInterval(intId[i]);
							}else{
								marquee(_this, sh);
							}
						}, speed);
						_this.hover(function(){
							clearInterval(intId[i]);
						},function(){
							intId[i] = setInterval(function(){
								if(_this.find("ul").height()<=_this.height()){
									clearInterval(intId[i]);
								}else{
									marquee(_this, sh);
								}
							}, speed);
						});
					});
			}
			var formMsg = function(msgInfo, flag) {
				var tblStr1 = '<li class="msg_li" id=' + msgInfo["noticeId"] 
					+ '><p class="msg_title_p">'+ msgInfo["title"]
					+'</p><p class="msg_content_p">'+ msgInfo["content"]
					+'</p><br></li>';
				
				var tblStr2 = '';

				$("#msg_row").append(tblStr1);
				//
			};
			$.ajax({
				"async": true,
				"cache": false,
				"type": "POST",
				"dataType":"json",
				"url": "/MOBILE/ExtServlet?actionName=system/SelMobileNoticeAction",
				"contentType": "application/x-www-form-urlencoded",
				"data" :{"optype":"ALL", "pagin":"N"},
				"success":function(data) {
				
					if(data["totalCount"] != 0) {
						for(var i=0; i<data["Body"].length; i++) {
							formMsg(data["Body"][i]);
						}
						$("#msg_div").myScroll({
							speed:60,
							rowHeight:45
						});
					}
				},
				"error": function(xhr) {
					alert("公告数据获取失败！");
				}
			});
			$.ajax({
				"async": false,
				"cache": false,
				"type": "POST",
				"dataType":"json",
				"url": '/MOBILE/api/param/list/APP_OS_TYPE',
				"contentType": "application/x-www-form-urlencoded",
			
				"success":function(data) {
					if(data!= '') {
						for(var i=0; i<data.length; i++) {
					 $("#osType").append("<option value="+data[i].mcode+">"+data[i].mname+"</option>");
						}
						
					}
				},
				"error": function(xhr) {
					alert("公告数据获取失败！");
				}
			});
			//$.post("http://localhost:17080/MOBILE/ExtServlet?actionName=system/SelMobileMessageAction", $.param({"optype":"ALL"}));
		});
	</script>
</head>

<body bgcolor="edfbfd" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" >
	<tr><td colspan="3"><br></td></tr>
  <tr> 
    <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png"><br></td>
          <td width="3%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td background="img/index_09.png">&nbsp;</td>
          <td valign="top" background="img/index_10.png"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="43%" valign="top"><img src="img/user.png" width="90" height="102"></td>
                <td valign="top" background="img/index_10.png"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <td width="27%" valign="top" id = "staffName" class="font">姓名</td>
                </tr>
                <tr>
                <td width="27%" valign="top" id = "jobName" class="font">职位：管理员</td>
                </tr>
                <tr>
                <td width="27%" valign="top" id = "userName" class="font">用户名：张凯</td>
                </tr>
                </table></td>
              </tr>
              
              <tr> 
                <td><input name="Submit" type="submit" OnClick="perWorkInfo()" class="button" value="个人工作台"></td>
                <td><input name="staffInfo" type="submit" OnClick="staffInfo()" class="button" value=" 个人信息 "></td>
              </tr>
              <tr> 
                <td><input name="Submit3" type="submit" OnClick="apkResInfo()" class="button" value=" 应用注册 "></td>
                <td><input name="Submit4" type="submit" class="button" value=" 服务注册 "></td>
              </tr>
            </table></td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table></td>
    <td width="564" align="center" valign="middle">
    	<div id="adv" class="adv">
		    <div id="adv_bg" class="adv_bg"></div>
		    <div id="adv_info" class="adv_info"></div>
		    <div id="adv_list" >
		        <a href="#" target="_blank"><img src="img/banner.jpg"  /></a>
		        <a href="#" target="_blank"><img src="img/banner2.jpg"  /></a>
		        <a href="#" target="_blank"><img src="img/banner3.jpg"  /></a>
		        <a href="#" target="_blank"><img src="img/banner4.jpg"  /></a>
		    </div>
			</div>
    </td>
    <td width="218" valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png" class="moth-title">月度好评<br></td>
          <td width="3%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td height="149" background="img/index_09.png">&nbsp;</td>
          <td background="img/index_10.png"><img src="img/ad.png"></td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td rowspan="2" valign="top"> <table id="msgMain" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png" class="title">公告信息<br></td>
          <td width="3%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td height="149" background="img/index_09.png">&nbsp;</td>
          <td height="524px"   valign="top" background="img/index_10.png"> 
          	<div style=" overflow:hidden;height:524px" id="msg_div" >
          		<ul id="msg_row"></ul>
          	</div>
		  </td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table>
	</td>
    <td height="416" valign="top"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png" class="title">应用列表
                &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;系统类型：   <select id ="osType"  onchange="osTypeChange()">
               </select></td>
          <td width="3%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td height="149" background="img/index_09.png"></td>
          <td valign="top" background="img/index_10.png">
          	<div  width="535" height="364" id='container-1'>
	          	<ul>
	               
	            </ul>
	           
            </div>
          </td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table></td>
    <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png" class="title">应用排行</td>
          <td width="3%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td height="364px" background="img/index_09.png" >&nbsp;</td>
          <td valign="top" background="img/index_10.png">
          	<div class="rankIndexTb">
          	<table  id="appRanking" >
              
           	</table> 	
         		</div>
          </td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td colspan="2" valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%" align="right" valign="bottom"><img src="img/index_03.png" width="11" height="36"></td>
          <td width="92%" background="img/index_05.png" class="title">专题应用<br></td>
          <td width="1%" align="left" valign="bottom"><img src="img/index_07.png" width="13" height="36"></td>
        </tr>
        <tr> 
          <td height="100" background="img/index_09.png">&nbsp;</td>
          <td valign="top" background="img/index_10.png">&nbsp; </td>
          <td background="img/index_11.png"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"><img src="img/index_18.png" width="11" height="12"></td>
          <td background="img/index_19.png">&nbsp;</td>
          <td align="left" valign="top"><img src="img/index_22.png" width="13" height="12"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
<script src="js/ebizIndex.js" type="text/javascript"></script>
<script src="../systemMobMgr/js/apkDev.js" type="text/javascript"></script>
<script type="text/javascript">
    var session1 = GetSession();
      document.all.staffName.innerText = "姓名："+session1.staff.staffName;
      document.all.jobName.innerText = "职位："+session1.job.jobName;
      document.all.userName.innerText = "用户名："+session1.staff.userName;
    var apkDev=new ApkDev();
     function staffInfo(){
     	
    	var _obj = new Object();

			_obj.operation = "mod";
			_obj.orgId = session1.org.orgId;
			_obj.staffOrgId = session1.org.orgId;
			_obj.staffId = session1.staff.staffId ;
			_obj.jobId =session1.job.jobId ;
			_obj.password = "" ;
			_obj.isBasic = session1.job.isBasic;
			var newObj = window.showModalDialog("../system/StaffInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:520px;status:no");
    }
    
    function apkResInfo(){

    	var _obj = new Object();
	    var newObj = window.showModalDialog("../systemMobMgr/apkLifeManager.jsp", _obj, "dialogWidth:1000px;dialogHeight:680px;status:no");
    }
    
    function perWorkInfo(){

    	var _obj = new Object();
	    var newObj = window.showModalDialog("../webos/perWorkench.jsp", _obj, "dialogWidth:1000px;dialogHeight:680px;status:no");
    }
    
    
    
</script>
</html>
