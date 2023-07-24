<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.flow_template_rule")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<SCRIPT language=javascript src="../../../public/script/texthint.js"></SCRIPT>
<SCRIPT language=javascript src="../../../public/script/bar.js"></SCRIPT>
<style type="text/css">
.opacity {
	FILTER: alpha(opacity=100)
}
</style>
<!-- TemplBeginEditable name="head" -->

<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2" id="packageAppleRuleTbl">
  <form name="form1" onsubmit="return false;">
  <tr>
    <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
   <tr>
    <td class="td_blue_txt" id="area_id" width="30%"><%=getI18NResource("designer.area")%></td>
    <td class="td_grey" width="70%">
    	<input type="text" name="areaName" checkType="empty" readonly  style="width:180" ><INPUT TYPE="button" class="button_pop" onclick="packageApplyRuleOper.selectArea()" ><font color="red">*</font>
    	<input type="hidden" name="areaId"  value="">
    	</td>
  </tr>
	<tr>
    <td class="td_blue_txt" id="service_id"><%=getI18NResource("designer.service")%></td>
    <td class="td_grey">
			<input type="hidden" name="serviceIds" class="input_array" value="" checkType="empty">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width='100%' class="td_grey">
						<table height="30" cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
							<td width="100%" height="30" rowSpan="3"><div id="serviceNameDiv" name="scrollMove" class="scrollMove" style="width:100%;height:25;overflow:hidden"></div></td>
							<td width="9" height="9" valign="top"><IMG onmouseup=movover();movstar(-1,20,'serviceNameDiv') class=opacity  onmousedown=movover();movstar(-3,2,'serviceNameDiv') onmouseover=movstar(-1,20,'serviceNameDiv');o_down(this) onmouseout=movover();o_up(this) src="../../../images/icon/scrollup.gif"></td>
							</tr>
							<tr>
							 <td width="9"></td>
							</tr>
							<tr>
							 <td width="9" height="9" valign="bottom"><IMG onmouseup=movover();movstar(1,20,'serviceNameDiv') class=opacity  onmousedown=movover();movstar(3,2,'serviceNameDiv') onmouseover=movstar(1,20,'serviceNameDiv');o_down(this) onmouseout=movover();o_up(this)  src="../../../images/icon/scrolldown.gif"></td>

							</tr>
						</table>
					</td>
					<td align="center" valign="middle" class="td_grey" width="5%">
					<input type='button' class="button_pop" onclick="packageApplyRuleOper.selectService()" ><font color="red">*</font>
					</td>
				</tr>
			</table>
		</td>
	</tr>
  <tr>
    <td class="td_blue_txt" id="rule_name_id"><%=getI18NResource("designer.rule_name")%></td>
    <td class="td_grey">
    	<input type="text" name="packageApplyRuleName" checkType="empty" style="width:198" ><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td class="td_blue_txt" id="work_time_id"><%=getI18NResource("designer.effect_date")%></td>
    <td class="td_grey"><ZTESOFT:dateTimeText name="effDate" width="180" class="datetimetext"  useDate="true" useTime = "true" checkType="empty"/><font color="red">*</font></td>
  </tr>
  <tr>
    <td class="td_blue_txt" id="expire_time_id"><%=getI18NResource("designer.expire_date")%></td>
    <td class="td_grey"><ZTESOFT:dateTimeText name="expDate" width="180" class="datetimetext" useDate="true" useTime = "true" checkType="empty"/><font color="red">*</font></td>
  </tr>
    </form>
	<tr>
    <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
    <td colspan="2" align="center" height="40" valign="bottom">
   	<input type="button" name="button" id="okBtn" value="<%=getI18NResource("designer.ok")%>" class="button" onclick='packageApplyRuleOper.submit()'>
    <input type="button" name="button" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" class="button" onclick='window.close()'>

  	</td>
  </tr>
 </table>


<!-- TemplEndEditable -->
</body>
</html>

<script>
	/* \u5168\u5c40\u53d8\u91cf */
var packageApplyRuleOper = new PackageApplyRuleOper();
var packageid = 1;
var packageName = ''
var areaId = null;
var receiveParams = window.dialogArguments;
var packageDto = receiveParams.packageDto;      //\u5f53\u524d\u6d41\u7a0b\u5bf9\u8c61
var existApplyRules = receiveParams.applyRules; //\u6d41\u7a0b\u5df2\u914d\u7684\u9002\u7528\u89c4\u5219

/* \u521d\u59cb\u5316 */
packageApplyRuleOper.init();
/**\u6d41\u7a0b\u6a21\u677f\u9002\u7528\u89c4\u5219\u7c7b*/
function PackageApplyRuleOper(){
	_extends(this, FormExt, '"form1"');
	this.init = function(){
		if(packageDto!=null){
			packageName = packageDto.name;
			packageid = packageDto.id;
			areaId = packageDto.areaId;
		}
		this.setValue(this.form.packageApplyRuleName, packageName);
	}
	this.selectArea = function(){
		var retObj = OpenShowDlg('../../../common/areaSel.jsp',420,300,areaId);
		if(retObj){
			this.setValue(this.form.areaName, retObj.name);
			this.setValue(this.form.areaId, retObj.id);
			this.setValue(this.form.packageApplyRuleName, packageName+'-'+this.form.areaName.value);
		}
	}

	/*
	this.selectService = function(){
		var retObj = OpenShowDlg('../../../tache/serviceSel.htm',420,300,null);
		if(retObj){
			this.setValue(this.form.serviceName, retObj.name);
			this.setValue(this.form.serviceId, retObj.id);
			this.setValue(this.form.packageApplyRuleName, packageName+'-'+this.form.areaName.value+'-'+this.form.serviceName.value);
		}
	}*/


  /*\u9009\u62e9\u670d\u52a1\uff0c\u6539\u7528\u7528Common\u4e0b\u7684\u5171\u7528\u670d\u52a1\u9009\u62e9\u529f\u80fd\u9875\u9762  liangli  2006.11.14*/
	this.selectService = function(){
		//\u4ee5\u524d\u7684\u670d\u52a1\u9009\u62e9\u9875\u9762\u592a\u6162\uff0c\u56e0\u4e3a\u53d6\u51fa\u503c\u540e\u8981\u904d\u5386\u6240\u6709\u8282\u70b9\uff0c\u4fee\u6539\u6240\u6709\u76ee\u5f55\u8282\u70b9\u7684\u56fe\u6807
		var retObj = OpenShowDlg('../../../common/serviceSelNew.jsp',280,500,true);
		if(retObj){
			//alert("\u670d\u52a1\u540d\uff1a"+retObj.name+"\r\n \u670d\u52a1id\uff1a"+retObj.id);
			document.all.serviceNameDiv.innerText = retObj.names;
			document.all.serviceIds.value = retObj.ids;		
		}
		
		
  /*	var retObj = OpenShowDlg('serviceSel.htm',420,300,null);
		if(retObj){
			document.all.serviceNameDiv.innerText = retObj.serviceNames;
			document.all.serviceIds.value = retObj.serviceIds;
			//this.setValue(this.form.serviceName, retObj.name);
			//this.setValue(this.form.serviceId, retObj.id);
			//this.setValue(this.form.packageApplyRuleName, packageName+'-'+this.form.areaName.value+'-'+this.form.serviceName.value);
		}*/
	}
	
	//\u9875\u9762\u63d0\u4ea4\uff0c\u786e\u5b9a\u589e\u52a0\u4e00\u4e2a\u89c4\u5219

	this.submit = function(){
				
		if(this.validator()) return;	
		
		if(this.form.effDate.value>=this.form.expDate.value){
			ErrorHandle("<%=getI18NResource("designer.effect_date_before_expire_date")%>",2);
			return;
		}
    var currentDate = callRemoteFunctionNoTrans("com.ztesoft.iom.dataquery.ToolQuery","getServerDate"); 
		if(this.form.effDate.value<DateToString(currentDate,true)){
			ErrorHandle("<%=getI18NResource("designer.effect_date_after_today")%>",2);
			return;
		}

		if(document.all.serviceIds.value == ""){
			ErrorHandle("<%=getI18NResource("designer.please_choose_service")%>",2);
			return;
		}

		
		

		var packageApplyRuleDto = this.formToObject();
		packageApplyRuleDto.packageid = packageid; 
		
		//\u53bb\u6389\u533a\u57df\u540d\u79f0\u548c\u670d\u52a1\u540d\u79f0

		RemoveAttrs(packageApplyRuleDto,'areaName','serviceIds');
		var serviceIdArr = document.all.serviceIds.value.split(",");
		var serviceNameArr = document.all.serviceNameDiv.innerText.split(",");
		var selAreaId  = document.all.areaId.value;
		
		//\u5224\u65ad\u9009\u4e2d\u670d\u52a1\u662f\u5426\u5df2\u5b58\u5728

		if(this.isExist(serviceIdArr)) return;
		
		
		//\u5224\u65ad\u9009\u4e2d\u7684\u670d\u52a1\u662f\u5426\u5df2\u6709\u5176\u5b83\u6548\u6d41\u7a0b\u6a21\u677f\uff0c\u6709\u5219\u63d0\u793a\u3002add by liangli 20061108    
	  var rulesArr = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient",
	  							"qryPackageApplyRuleByServiceIds",selAreaId,serviceIdArr);
	  
	  
	  var flag = false;				
	  if(rulesArr!=null && rulesArr.length>0){
	  	if(window.confirm("<%=getI18NResource("designer.service_have_rule_continue_config")%>")){
	  		flag = true;
	  	}
	  	
	  	/*
	  	//\u5b58\u5728\u5df2\u6709\u6d41\u7a0b\u6a21\u677f\uff0c\u5e76\u9009\u62e9\u8981\u914d\u7f6e\u7684\u670d\u52a1 	
	  	if(window.confirm("\u4f60\u9009\u4e2d\u7684\u670d\u52a1\u4e2d\u5df2\u6709\u5176\u4ed6\u6709\u6548\u6d41\u7a0b\u6a21\u677f\u3002\r\n\uff0d\u3010\u786e\u5b9a\u3011\u67e5\u770b\u8be6\u60c5\uff0c\r\n\uff0d\u3010\u53d6\u6d88\u3011\u7ee7\u7eed\u914d\u7f6e")){	  		  
	  			serviceIdArr = window.OpenShowDlg('pkgApplyRuleDetail.htm',400,460,rulesArr);
	  	
			}*/
	  }			
	  	  
		var result;		
		if(flag ||(!flag && (rulesArr==null || rulesArr.length<1))){
			for(var i=0; i<serviceIdArr.length; i++){
				try{
					packageApplyRuleDto.serviceId = serviceIdArr[i];
					packageApplyRuleDto.packageApplyRuleName =  packageName+'-'+this.form.areaName.value+'-'+serviceNameArr[i];
					result = callRemoteFunction('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','addPackageApplyRule',packageApplyRuleDto);
				}catch(e){
					ErrorHandle(e,2);
					return;
				}
			}
			window.returnValue = result;
			window.close();
	  }	
	}
	
	/*\u5224\u65ad\u9009\u4e2d\u7684\u670d\u52a1\u662f\u5426\u5df2\u5b58\u5728*/
	this.isExist = function(selServiceIds){
		var reFlage = false;
		if(existApplyRules==null || existApplyRules.length==0) return reFlage;
		
		var resSize = existApplyRules.length;
		var selId = null;
	  var selAreaId = document.all.areaId.value;
	  var resRulesObj = null;
	  
		for(var i=0;i<selServiceIds.length;i++){
			selId = selServiceIds[i];
			for(var j=0;j<resSize;j++){
				resRulesObj = existApplyRules[j];
				//\u5224\u65ad\u9009\u4e2d\u7684\u5730\u57df\u548c\u670d\u52a1id\uff0c\u4e0e\u5df2\u5b58\u5728\u7684\u9002\u7528\u89c4\u5219\u4e00\u81f4

				if(selId==resRulesObj.serviceId && selAreaId == resRulesObj.areaId){
					ErrorHandle("<%=getI18NResource("designer.rule_conflict")%>");
					return true;
				}
			}
		}		
		return false;		
	}
	
	
}

function delNowRow(btn){
	var row = GetRow(btn);
	packageAppleRuleTbl.deleteRow(row.rowIndex);
}
</script>
