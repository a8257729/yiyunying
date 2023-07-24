<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.flow_template_query")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->

	<?XML:NAMESPACE PREFIX="ZTESOFT" ?>
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<ZTESOFT:tabStrip id="queryCondition" class="tab_ioms" selectedIndex="0">
	<ZTESOFT:tabs height="190" width = "100%">
		<ZTESOFT:page Text="<%=getI18NResource("designer.query_by_template_information")%>">
<table border="0">
	<form name="queryForm" id="queryForm" onsubmit="return false">
	<tr>
		<td width="140"><%=getI18NResource("designer.flow_template_org")%></td>
		<td width="140">
			<input type="text" id="orgName" name="orgName" style="width:106px;" disabled /><img src="../../../images/button/popedit.gif" align="absmiddle" onclick="queryOperation.chooseOrganization()" /><font color="red">*</font>
			<input type="hidden" id="orgId" name="orgId" />
			<input type="hidden" id="areaId" name="areaId" />
		<td width="140"><%=getI18NResource("designer.flow_template_catalog")%></td>
		<td width="140">
			<input type="text" id="catalogName" name="catalogName" style="width:106px;" disabled /><img src="../../../images/button/popedit.gif" align="absmiddle" onclick="queryOperation.chooseCatalog()" />
			<input type="hidden" id="catalogId" name="catalogId" />
		</td>
	</tr>
	<tr>
		<td><%=getI18NResource("designer.flow_version_author")%></td>
		<td>
			<input type="text" id="author" name="author" style="width:106px;" disabled /><img src="../../../images/button/popedit.gif" align="absmiddle" onclick="queryOperation.chooseAuthor()" />
		</td>
		<td width="150"><%=getI18NResource("designer.flow_template_name")%></td>
		<td width="200">
			<input type="text" id="flowName" name="flowName" style="width:125px;" />
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<hr />
		</td>
	</tr>
	<tr>
		<td colspan="4"><%=getI18NResource("designer.flow_version_active_time_range")%></td>
	</tr>
	<tr>
		<td><%=getI18NResource("designer.start_time")%></td>
		<td>
			<ZTESOFT:dateTimeText name="effectStartDate" class="datetimetext" useDate="true" checkType="empty" width="105"/>
		</td>
		<td><%=getI18NResource("designer.end_time")%></td>
		<td>
			<ZTESOFT:dateTimeText name="effectEndDate" class="datetimetext" useDate="true" checkType="empty" width="105"/>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<hr />
		</td>
	</tr>
	<tr>
		<td colspan="4"><%=getI18NResource("designer.flow_version_expire_time_range")%></td>
	</tr>
	<tr>
		<td><%=getI18NResource("designer.start_time")%></td>
		<td>
			<ZTESOFT:dateTimeText name="expireStartDate" class="datetimetext" useDate="true" checkType="empty" width="105"/>
		</td>
		<td><%=getI18NResource("designer.end_time")%></td>
		<td>
			<ZTESOFT:dateTimeText name="expireEndDate" class="datetimetext" useDate="true" checkType="empty" width="105"/>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<hr />
		</td>
	</tr>
</table>
</ZTESOFT:page>

<ZTESOFT:page Text="<%=getI18NResource("designer.query_by_service")%>">
		   
		   
<table width="100%" align="center" border="0" cellpadding="2" cellspacing="1">	
  <tr>
		<td width="20%"><%=getI18NResource("designer.area")%></td>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="0" >
		    <tr>
		    	<td width='96%'>
				    <table height="30" cellSpacing="0" cellPadding="0" width="100%" border="0">
					    <tr>
					      <td width="100%" height="30" rowSpan="3">	
					        <input type="text" id="areaName" name="areaName" style="width:100%;overflow:hidden" disabled/>
					      	<!--div id="areaNameDIV" name="scrollMove" ></div-->
					      </td>					      
						  </tr>
						</table>
 					</td>
 					<td align="center" valign="middle" width="4%">
						<input type='button' class="button_pop" onclick="queryOperation.selectArea()" ><font color="red">*</font>
			    	<input type="hidden" id="areaIds" name="areaIds" class="input_array" value="">
 				 </td>
 				</tr>
			 </table>
		</td>
  </tr>

	<tr>
		<td><%=getI18NResource("designer.service")%></td>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="0" >
		    <tr>
		    <td width='96%'>
				  <table height="30" cellSpacing="0" cellPadding="0" width="100%" border="0">
	          <tr>
							<td width="100%" height="30" rowSpan="3">
								<!--div id="serviceName" name="scrollMove" style="width:100%;height:25;overflow:hidden"></div-->
								<input type="text" id="serviceName" name="serviceNameDIV" style="width:100%;overflow:hidden" disabled/>
						  </td>	
						</tr>					
	 			 </table>
      </td>
      <td align="center" valign="middle" width="4%">
				  <input type='button' class="button_pop" onclick="queryOperation.selectService()" ><font color="red">*</font>
    			<input type="hidden" name="serviceIds" class="input_array" value="">
    	</td>
    	</tr>
    </table>
   </td>
  </tr>
</table>
		
</ZTESOFT:page>

<ZTESOFT:page Text="<%=getI18NResource("designer.query_by_product")%>">
   
		   
<table width="100%" align="center" border="0" cellpadding="2" cellspacing="1">	
  <tr>
		<td width="20%"><%=getI18NResource("designer.area")%></td>
		<td >
			<table width="100%" border="0" cellspacing="1" cellpadding="0" >
		    <tr>
		    	<td width='96%'>
				    <table height="30" cellSpacing="0" cellPadding="0" width="100%" border="0">
					    <tr>
					      <td width="100%" height="30" rowSpan="3">	
					        <input type="text" id="prodAreaName" name="prodAreaName" style="width:100%;overflow:hidden" disabled/>
					      	<!--div id="prodAreaNameDIV" name="scrollMove" ></div-->
					      </td>					      
						  </tr>
						</table>
 					</td>
 					<td align="center" valign="middle" width="4%">
						<input type='button' class="button_pop" onclick="queryOperation.selectProdArea()" ><font color="red">*</font>
			    	<input type="hidden" id="prodAreaIds" name="prodAreaIds" class="input_array" value="">
 				 </td>
 				</tr>
			 </table>
		</td>
  </tr>

	<tr>
		<td><%=getI18NResource("designer.product")%></td>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="0" >
		    <tr>
		    <td width='96%'>
				  <table height="30" cellSpacing="0" cellPadding="0" width="100%" border="0">
	          <tr>
							<td width="100%" height="30" rowSpan="3">
								<input type="text" id="prodName" name="prodName" style="width:100%;overflow:hidden" disabled/>
						  </td>	
						</tr>					
	 			 </table>
      </td>
      <td align="center" valign="middle" width="4%">
				  <input type='button' class="button_pop" onclick="queryOperation.selectProduct()" ><font color="red">*</font>
    			<input type="hidden" name="prodId" class="input_array" value="">
    	</td>
  	</tr>  	
    </table>
   </td>   
  </tr>
  <tr>
  <td><%=getI18NResource("designer.product_name")%></td>
		<td width="200" height="30">
			<input type="text" id="prodNameInput" name="prodNameInput" style="width:410px;" />
		</td>
	</tr>
</table>		
	</ZTESOFT:page>

  </ZTESOFT:tabs>
</ZTESOFT:tabStrip>
<table>
  <tr>
		<td colspan="4" align="center">
			<input type="button" class="button" value="<%=getI18NResource("designer.query")%>" onclick="queryOperation.query();" />
			<input type="button" class="button" value="<%=getI18NResource("designer.reset")%>" onclick="queryOperation.reset();" />
		</td>
	</tr>
	<tr>
		<td colspan="4"><%=getI18NResource("designer.query_result")%></td>
	</tr>
	<tr>
		<td colspan="4">
			<ZTESOFT:treelist id="resultTree" name="resultTree" class="treelist" width="100%" height="200">
				<ZTESOFT:columns>
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.flow_catalog")%>" propertyName="packageCatalogName" />
					<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("designer.flow_name")%>" propertyName="packageName" />
					<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("designer.flow_version")%>" propertyName="processDefinitionVersion" />
					<ZTESOFT:column propertyName="processDefinitionId" />
					<ZTESOFT:column propertyName="areaId" />
					<ZTESOFT:column propertyName="pathCode" />
				</ZTESOFT:columns>
			</ZTESOFT:treelist>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<input type="button" class="button" id="okButton" style="width:60px" value="<%=getI18NResource("designer.ok")%>" onclick="queryOperation.submit();" />
			<input type="button" class="button" id="cancelButton" style="width:60px" value="<%=getI18NResource("designer.cancel")%>" onclick="queryOperation.cancel();" />
		</td>
	</tr>
	</form>
</table>

<!-- TemplEndEditable -->
</body>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2006-08-11
// Author : Xu.fei3
// commits: Handles query of a flow template
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var queryOperation = new QueryOperation();

/* \u521d\u59cb\u5316 */

//////////////////////////////////////////
//Class\u7c7b\u533a

//\u6d41\u7a0b\u6a21\u677f\u67e5\u8be2\u64cd\u4f5c
function QueryOperation(){
	_extends(this, FormExt, '"form1"');
	this.form = document.all.queryForm;
	this.resultTree = document.all.resultTree;
	
	var session = window.dialogArguments;
	
	//\u67e5\u8be2
	this.query = function(){
		
		var selTabIndex = document.all.queryCondition.selectedIndex;
		if(selTabIndex==0){
			/*\u6d41\u7a0b\u6a21\u677f\u67e5\u8be2*/
			if(this.form.areaId.value == ""){
				ErrorHandle("<%=getI18NResource("designer.org_cannot_empty")%>");
				return;
			}
	
			var condition = new Object();			
			condition.catalogid = this.form.catalogId.value;
			condition.name = this.form.flowName.value;
			condition.author = this.form.author.value;			
			//\u533a\u57dfID\u662f\u591a\u9009\u7684\uff0c\u7528\u6570\u7ec4\u53d1\u8fc7\u53bb

			condition.ownerAreaId = new Array();
			condition.ownerAreaId.push(this.form.areaId.value);			
			//\u751f\u6548\u65f6\u95f4
			condition.validDate = new Array();
			
			var effectStartDate = this.form.effectStartDate.value;
			var effectEndDate = this.form.effectEndDate.value;
			if(effectStartDate!='' && effectEndDate!='' && effectStartDate>effectEndDate){
				ErrorHandle("<%=getI18NResource("designer.end_time_after_start_time")%>");
				return;
			}
			if(effectStartDate!=null && effectStartDate!=''){
				 condition.validDate[0]=effectStartDate;
				 condition.validDate[1]=null;	
			}
			if(effectEndDate!=null && effectEndDate!='') condition.validDate[1]=effectEndDate;	
			
			//\u5931\u6548\u65f6\u95f4		
			condition.invalidDate = new Array();
			
			var expireStartDate = this.form.expireStartDate.value;
			var expireEndDate = this.form.expireEndDate.value;
			
			if(expireStartDate!='' && expireEndDate!='' && expireStartDate>expireEndDate){
				ErrorHandle("<%=getI18NResource("designer.end_time_after_start_time")%>");
				return;
			}		
			if(expireStartDate!=''){
				condition.invalidDate[0]=expireStartDate;	
				condition.invalidDate[1]=null;
			}
			if(expireEndDate!='')	condition.invalidDate[1]=expireEndDate;
	
			
			var result = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryPackage", condition);
			this.resultTree.loadByXML(result);
			
			if(this.resultTree.items.length == 0){
				ErrorHandle("<%=getI18NResource("designer.no_result")%>");
			}
		}
		else if(selTabIndex==1){			
			this.queryFlowByServic();	/*\u6839\u636e\u5730\u57df\u548c\u670d\u52a1\u67e5\u8be2*/
		}else{
			this.queryFlowByProd();	/*\u6839\u636e\u5730\u57df\u548c\u4ea7\u54c1\u67e5\u8be2*/
		}
	}
	
	/*\u6839\u636e\u5730\u57df\u548c\u4ea7\u54c1\u67e5\u8be2*/
	this.queryFlowByProd = function(){		
		//\u53d6\u5730\u57df\u548c\u670d\u52a1
		var tempArea = this.form.prodAreaIds.value;
		var tempProdId = this.form.prodId.value;	
		var tempService;
		if(tempProdId!=''){
			var serviceIdArr =callRemoteFunctionNoTrans("com.ztesoft.iom.product.ProductManager","selectServiceByPrdId",tempProdId);
			tempService =serviceIdArr.toString();
		}else{
			tempService =0;
		}
		
		if(this.form.prodNameInput.value!=''){
			var serviceIds = callRemoteFunctionNoTrans("com.ztesoft.iom.product.ProductManager", "selectServiceByPrdName", this.form.prodNameInput.value);
			if(serviceIds!=null && serviceIds.length>0){
				if(tempService != 0 ){
					tempService = tempService+ ","+serviceIds.toString();					
				}else{
					tempService =serviceIds.toString();	
				}
			}			
		}
		
		if(tempArea=="" || (tempProdId=="" && this.form.prodNameInput.value=="")){
			ErrorHandle("<%=getI18NResource("designer.please_choose_area_product")%>");
			return;
		}		
		var transData = new Array();		
		 try{
	      var objData;
	      if(tempService!=null && tempService.length>0) 
	         objData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient",	"findProcessByAreaIdServiceIds",tempService,tempArea);
	      else 
	        { 
	           alert("<%=getI18NResource("designer.product_no_relation_with_service")%>");
	           return;
	        }	    
	        															  
	      if(objData == null){
		  		ErrorHandle("<%=getI18NResource("designer.no_result")%>",3);		  		
	      }	      
	      else if(objData.length == 0){
		  		ErrorHandle("<%=getI18NResource("designer.no_result")%>",3);		  		
	      }else{	      
		      var processDefDto = null;
		      var tempDto = null;
		      for(var i=0;i<objData.length;i++){
		      	tempDto = objData[i];
		      	
		      	processDefDto = new Object();
		      	processDefDto.packageCatalogName = tempDto.catalogName;
		      	processDefDto.packageName = tempDto.name ;	      	
		      	processDefDto.processDefinitionVersion = tempDto.version ;
		      	processDefDto.processDefinitionId = tempDto.packageId ;
		      	processDefDto.areaId = tempDto.areaId ;
		      	processDefDto.pathCode = tempDto.pathCode ;
		      	
		      	transData[transData.length] = processDefDto;
		      }
		    }	      
		  	this.resultTree.loadByData(transData);
	  }catch(ex){	      
	      ErrorHandle("<%=getI18NResource("designer.query_flow_result_exception")%>"+ex,1);
	  }
		
	}
	
	/*\u6839\u636e\u5730\u57df\u548c\u670d\u52a1\u67e5\u8be2*/
	this.queryFlowByServic = function(){		
		//\u53d6\u5730\u57df\u548c\u670d\u52a1
		var areaArr = null;
		var serviceArr = null;
		var tempArea = this.form.areaIds.value;
		var tempService = this.form.serviceIds.value;	
		
		if(tempArea=="" || tempService==""){
			ErrorHandle("<%=getI18NResource("designer.area_service_cannot_empty")%>");
			return;
		}
		var transData = new Array();		
		 try{
	      var objData;
	      objData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient",
	      																	  "findProcessDefinitionDtos",tempService,tempArea,null);
	      if(objData == null){
		  		ErrorHandle("<%=getI18NResource("designer.no_result")%>",3);		  		
	      }
	      else{
		      var processDefDto = null;
		      var tempDto = null;
		      for(var i=0;i<objData.length;i++){
		      	tempDto = objData[i];
		      	
		      	processDefDto = new Object();
		      	processDefDto.packageCatalogName = tempDto.catalogName;
		      	processDefDto.packageName = tempDto.name ;	      	
		      	processDefDto.processDefinitionVersion = tempDto.version ;
		      	processDefDto.processDefinitionId = tempDto.packageId ;
		      	processDefDto.areaId = tempDto.areaId ;
		      	processDefDto.pathCode = tempDto.pathCode ;
		      	
		      	transData[transData.length] = processDefDto;
		      }
		    }	      
		  	this.resultTree.loadByData(transData);
	  }catch(ex){	      
	      ErrorHandle("<%=getI18NResource("designer.query_flow_result_exception")%>"+ex,1);
	  }
		
	}
	
	//\u9009\u62e9\u7ec4\u7ec7
	this.chooseOrganization = function(){
		var org = OpenShowDlg("chooseOrganization.jsp", 420, 300, session);
		if(org != null){
			this.form.orgName.value = org.orgName;
			this.form.orgId.value = org.orgId;
			this.form.areaId.value = org.areaId;
		}
	}
	
	//\u9009\u62e9\u76ee\u5f55
	this.chooseCatalog = function(){
		if(this.form.areaId.value == ""){
			ErrorHandle("<%=getI18NResource("designer.please_choose_org")%>");
			return;
		}
		
		var catalog = OpenShowDlg("chooseCatalog.jsp", 420, 300, this.form.areaId.value);
		if(catalog != null){
			this.form.catalogName.value = catalog.catalogName;
			this.form.catalogId.value = catalog.catalogId;
		}
	}
	
	//\u9009\u62e9\u4f5c\u8005

	this.chooseAuthor = function(){
		if(this.form.orgId.value == ""){
			ErrorHandle("<%=getI18NResource("designer.please_choose_org")%>");
			return;
		}
		
		var author = OpenShowDlg("chooseAuthor.jsp", 420, 300, this.form.orgId.value);
		if(author != null){
			this.form.author.value = author.staffName;
		}
	}
	
	
	/**\u9009\u62e9\u533a\u57df*/
	this.selectArea = function(){
		var reqSel = OpenShowDlg("../../../product/areaSel.jsp",280,500,null);
		if(reqSel){
			this.setValue(this.form.areaIds, reqSel.id);
			this.setValue(this.form.areaName, reqSel.name);
			//SetInnerHTML(document.all.areaNameDIV,reqSel.name);
		}
	}
	
	/**\u4ea7\u54c1\u9875\u9762\u9009\u62e9\u533a\u57df*/
	this.selectProdArea = function(){
		var reqSel = OpenShowDlg("../../../product/areaSel.jsp",280,500,null);
		if(reqSel){
			this.setValue(this.form.prodAreaIds, reqSel.id);
			this.setValue(this.form.prodAreaName, reqSel.name);
		}
	}
	
	/**\u9009\u62e9\u670d\u52a1*/
	this.selectService = function(){
		var reqSel = OpenShowDlg("../../../common/serviceSingleSel.jsp",400,500,null);
		if(reqSel){
			this.setValue(this.form.serviceIds, reqSel.id);
			this.setValue(this.form.serviceName, reqSel.name);
		}
	}
	
	//\u9009\u62e9\u4ea7\u54c1
	this.selectProduct = function(){
		var reqSel = OpenShowDlg("../../../product/singleProdSel.jsp",400,500,null);
		if(reqSel){
			this.setValue(this.form.prodId, reqSel.ids);
			this.setValue(this.form.prodName, reqSel.names);
		}
	}
	
	//\u91cd\u7f6e
	this.reset = function(){
		this.form.reset();
		document.all.serviceName.value = "";
		this.form.serviceIds.value = "";
		document.all.areaName.value = "";
		this.form.areaIds.value = "";		
	  this.form.prodAreaIds.value= "";		
		this.form.prodId.value= "";		
		this.form.prodNameInput.value= "";		
	}
	
	//\u786e\u5b9a
	this.submit = function(){
		var result = new Object();
		
		if(this.resultTree.selectedItem != null){
			result.packageName = this.resultTree.selectedItem.packageName;
			result.processDefinitionVersion = this.resultTree.selectedItem.processDefinitionVersion;
			result.processDefinitionId = this.resultTree.selectedItem.processDefinitionId;
			result.areaId = this.resultTree.selectedItem.areaId;
			result.pathCode = this.resultTree.selectedItem.pathCode;
			
			window.returnValue = result;
		}
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}

//////////////////////////////////////////
//function\u51fd\u6570\u533a


</script>
</html>