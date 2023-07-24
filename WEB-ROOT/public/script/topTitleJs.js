function getParam(){var col=document.all.tags("script");var jsrc=col.item(col.length-1).src;return((jsrc.indexOf("?")!=-1)?jsrc.substr(parseInt(jsrc.indexOf("?"))+1,jsrc.length):null);}var suffParam=getParam();
document.write("<table width=\"100%\"><tr><td height=\"1\"></td></tr></table><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\""+gWebAbsPath+"/images/icon/main_top_bg.gif\"><tr>");
document.write("<td valign=\"middle\"><img src=\""+gWebAbsPath+((suffParam)?"/images/icon/"+suffParam:"/images/icon/def_title.gif")+"\" align=\"absmiddle\"><span style=\"font-size:12px;font-weight:bold;color:#356498; FILTER:Dropshadow(color=#F7F7F7, Offx=1, OffY=1, Positive=1)\" id=\"topTitle\">"+document.title+"</span></td>");
document.write("<td align=\"right\" valign=\"middle\"> <a href=\"javascript:showHelp()\"><img src=\""+gWebAbsPath+"/images/icon/icon_help_zh_cn.gif\" border=\"0\" align=\"absmiddle\" title=\""+'帮助'+"\"></a>");
document.write("&nbsp;<a href=\"javascript:location.reload()\"><img src=\""+gWebAbsPath+"/images/icon/icon_refresh_zh_cn.gif\" border=\"0\" align=\"absmiddle\" title=\""+'刷新'+"\"></a>");
document.write("&nbsp;<a href=\"javascript:window.history.back(1)\"><img src=\""+gWebAbsPath+"/images/icon/icon_back_zh_cn.gif\" border=\"0\" align=\"absmiddle\" title=\""+'返回'+"\"></a>&nbsp;&nbsp;</td></tr></table><table width=\"100%\"><tr><td height=\"1\"></td></tr></table>");
/*document.write("</tr><tr><td colspan=\"2\" bgcolor=\"#BBBBBB\"><spacer height=\"1\" type=\"block\"></td></tr>");
document.write("<tr><td colspan=\"2\" height=\"8\"><spacer height=\"1\" type=\"block\"></td></tr></table>");*/
suffParam=null;
document.body.insertAdjacentHTML('beforeEnd','<div id="p_o_p_9" style="top:'+ (parseInt(document.body.offsetHeight)/2-55) +'; left:'+ (parseInt(document.body.offsetWidth)/2-130) +'; height:26px; background-color:#FFFFE7; display:none; position:absolute; z-index:3;"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="247" height="81"><param name=movie value="'+gWebAbsPath+'/images/main/loading_'+gLangCode+'.swf"><param name=quality value=high><embed src="'+gWebAbsPath+'/images/main/loading_'+gLangCode+'.swf" quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="247" height="81"></embed></object></div>');

function showHelp() {
	var helpId = window.parent.leftFrame.currentModuleId;
	var result = "";

	try {
		var content = callRemoteFunctionNoTrans("com.ztesoft.iom.system.HelpManager", "queryHelpById", helpId);

		var xmlDom = new ActiveXObject("msxml2.DOMDocument.4.0");
		var xslDom = new ActiveXObject("msxml2.DOMDocument.4.0");
		
		xmlDom.async = false;
		xmlDom.validateOnParse = false;
		xmlDom.resolveExternals = false;
		var xmlDoc = xmlDom.loadXML(content);
		
		xslDom.async = false;
		xslDom.validateOnParse = false;
		xslDom.resolveExternals = false;
		var xslDoc = xslDom.load(gWebAbsPath + "/help/style/helpModule.xsl");
		
		result = xmlDom.transformNode(xslDom);

	}
	catch (ex) {
		alert("该模块暂无帮助！");
		return;
	}
	
	var newWin = window.open("","newWin","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,height=600, width=600,left=0,top=0");
	
	newWin.document.open();
	newWin.document.write(result);
	newWin.document.close();
}