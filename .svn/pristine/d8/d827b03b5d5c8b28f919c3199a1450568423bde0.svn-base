<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.add_other_rule")%> </title>
<meta http-equiv="Expires" CONTENT="0"> 
<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="Pragma" CONTENT="no-cache"> 

<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<!-- TemplBeginEditable name="head" -->
<?XML:NAMESPACE PREFIX="ZTESOFT" ?>
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeListImg.htc" />
<!-- TemplEndEditable -->
</head>
<body style="width:100%; height:100%; overflow:auto">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<div id='applyRuleDiv'></div>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
<tr><td height="40" align="center">
<input type='button' id='btnQry2' value='<%=getI18NResource("designer.add")%>' onclick='valueAddOper.addRow()'  class="button"  NAME='btnQry'>
</td></tr>
<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="1" cellpadding="2"  id='packageRuleTbl'>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="2" >
<tr><td align='center' height="40" valign="bottom">
<input type='button' id='btnQry1' value='<%=getI18NResource("designer.ok")%>'  onclick='valueAddOper.submit()'  class="button"  NAME='btnQry'> 
<input type='button' id='btnQry' value='<%=getI18NResource("designer.cancel")%>'  onclick='window.close()'  class="button"  NAME='btnQry'>
</td></tr>
</table>
 
<!-- TemplEndEditable -->
</body>

<script>


	/**\u5168\u5c40\u53d8\u91cf*/
	
	var valueAddOper = new ValueAddOper();
	
	var bObj = window.dialogArguments;
	var ruleId = null;
	var packageId = '1';
	var existRows = null;
	var elementTypeDtos ;
	
	var isSingle = false;
	
	/**\u521d\u59cb\u5316*/
	valueAddOper.init();

  
  /*\u7c7b*/
	function ValueAddOper(){
		this.submit = function(){
			if(packageRuleTbl.rows.length<=1){
				ErrorHandle("<%=getI18NResource("designer.please_add_rule")%>");
			}
			
			var ruleValues = new Array();
			for(var i = 1;i < packageRuleTbl.rows.length;i++){
				var row = packageRuleTbl.rows[i];
				var ruleValue = new Array();
				for(var j = 0;j < row.cells.length-1;j++){
					var acell = row.cells[j];
					if(acell.children.length>1){
						var obj = {'elementType':acell.children[0].value,'elementValue':acell.children[1].value,'elementValueName':acell.children[2].value};
						ruleValue[ruleValue.length] = obj;
					}else{
						ruleValue[ruleValue.length] = null; //\u5982\u679c\u6ca1\u6709\u9009\u67d0\u4e2a\u5143\u7d20\u7c7b\u578b\uff0c\u5219\u8be5\u9879\u4e3a\u7a7a

					}
				}
				ruleValues[ruleValues.length] = ruleValue;
			}
			var res = callRemoteFunction('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','addPackageRuleValues',ruleId,ruleValues);
			window.returnValue = res;
			window.close();
		}
	
		this.init = function(){
			if(bObj){
				ruleId = bObj.ruleId;
				packageId = bObj.packageId;
				areaId = bObj.areaId;
			}

			//\u5143\u7d20\u7c7b\u578b\u5217\u8868
			elementTypeDtos = callRemoteFunctionNoTrans('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','qryElementTypeByPackage',packageId);
			var applyRuleDivHtm =  new Array();
			applyRuleDivHtm[applyRuleDivHtm.length]="<table width='100%' border='0' cellspacing='1' cellpadding='2' bgcolor='#CCCCCC'><tr>";
			if(elementTypeDtos){
				
				if(elementTypeDtos.length == 1)
				{
					isSingle = true;
				}
			
				for(var i=0;i<elementTypeDtos.length;i++){
					applyRuleDivHtm[applyRuleDivHtm.length]="<td  class='td_grey'>";
					
					if(isSingle)
					{
						applyRuleDivHtm[applyRuleDivHtm.length]="<ZTESOFT:treelist id='"+elementTypeDtos[i].elementType+"' class='treelist' width='100%' height='200' showCheck='true'>";
					}
					else
					{
						applyRuleDivHtm[applyRuleDivHtm.length]="<ZTESOFT:treelist id='"+elementTypeDtos[i].elementType+"' class='treelist' width='100%' height='200' showCheck='true' showTitleCheck='false' onItemChecked='valueAddOper.singleChecked(this);'>";
					}
					
					applyRuleDivHtm[applyRuleDivHtm.length]="<ZTESOFT:columns>";
		        	applyRuleDivHtm[applyRuleDivHtm.length]="<ZTESOFT:column width='95%' display='true' displayText='"+elementTypeDtos[i].elementTypeName+"' propertyName='name' />";
		    		applyRuleDivHtm[applyRuleDivHtm.length]="<ZTESOFT:column display='false' propertyName='id' />";
			 		applyRuleDivHtm[applyRuleDivHtm.length]="</ZTESOFT:columns></ZTESOFT:treelist>";
		  			applyRuleDivHtm[applyRuleDivHtm.length]="</td>";
				}
				applyRuleDivHtm[applyRuleDivHtm.length]="</tr></table>";
				applyRuleDiv.innerHTML = applyRuleDivHtm.join('');
				try{  
					var elementTypeXMLS = callRemoteFunctionNoTrans('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','qryElementTypeXMLS',packageId,areaId);
					for(var i=0;i<elementTypeXMLS.length;i++){
						var objXML = elementTypeXMLS[i][1];
						eval('document.all.'+elementTypeXMLS[i][0]).loadByXML(objXML);
					}
				}
				catch(ex){
					ErrorHandle(ex,1,ex.detail);
				}
				
				//\u5df2\u6709\u6761\u4ef6
				existRows = callRemoteFunctionNoTrans('com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient','qryRuleValueArrsByRuleId',ruleId);

			}else{
				ErrorHandle("<%=getI18NResource("designer.config_other_rule_first")%>", 3);
			  	window.close();
			}
			
		}
		
		this.getIdName = function(eType,vTlv){
			
			if(vTlv.checkedItems.length>0){
			
				if(isSingle)
				{
					var eleTypeValues = new Array();
				
					for(i=0;i<vTlv.checkedItems.length;i++)
					{
						var eObj = new Object();
						eObj.elementType = eType;
						eObj.elementValue = vTlv.checkedItems[i].id;
						eObj.elementValueName = vTlv.checkedItems[i].name;
						eleTypeValues.push(eObj);
					}
					return eleTypeValues;
				}
				else
				{
					vlvCheckItem = vTlv.checkedItems[0];
					return {'elementType':eType,'elementValue':vlvCheckItem.id,'elementValueName':vlvCheckItem.name};
				}
			}else{
				return null;
			}
			
		}
		//\u589e\u52a0\u884c\uff0c\u5982\u679c\u4f4d\u4e8e\u884c\u9996\uff0c\u5219\u9700\u8981\u589e\u52a0\u884c\u5934

		this.addRow = function(){
			//\u627e\u5230\u53f6\u9762\u4e2d\u6240\u6709tree\uff0c\u5e76\u83b7\u5f97\u6bcf\u4e2atree \u7684\u5f53\u524d\u53d6\u503c\uff0c\u6bcf\u4e2atree\u7684\u6807\u9898

			var rowAddValue = new Array();
			
			for(var i=0;i<elementTypeDtos.length;i++){
			
				if(isSingle)
				{
					var eleTypeValues = this.getIdName(elementTypeDtos[i].elementType,eval(elementTypeDtos[i].elementType));
					if(eleTypeValues != null)
					{
						for(i=0;i<eleTypeValues.length;i++)
						{
							rowAddValue.push(eleTypeValues[i]);
						}
					}
					else
						rowAddValue.push(null);
				}
				else
				{
					var eleTypeValue = this.getIdName(elementTypeDtos[i].elementType,eval(elementTypeDtos[i].elementType));
					rowAddValue[rowAddValue.length] = eleTypeValue;
				}
			}
			// \u81f3\u5c11\u9009\u62e9\u4e00\u4e2a\u5143\u7d20\u624d\u80fd\u6dfb\u52a0

			var continueFlag = false;
			for(var i=0;i<rowAddValue.length;i++){
				if(rowAddValue[i]){
					continueFlag = true;
					break;
				}
			}
			if(!continueFlag) {
				ErrorHandle("<%=getI18NResource("designer.please_at_least_select_one")%>");
				return;
			}
			if(packageRuleTbl.rows.length==0){
				var row = packageRuleTbl.insertRow(packageRuleTbl.rows.length);
				row.style.background = '#CEE3FF'
				for(var i=0;i<elementTypeDtos.length;i++){
					var cell = row.insertCell();
					cell.innerHTML = elementTypeDtos[i].elementTypeName;
				}
			}
			this.addRuleRow(rowAddValue);
		
	}
	 //\u589e\u52a0\u4e00\u884c\uff0c\u8fd9\u4e2a\u6839\u636e\u5143\u7d20\u957f\u5ea6\u4e0d\u540c\u662f\u53ef\u8c03\u7684
	 //\u9700\u8981\u5224\u65ad\u662f\u5426\u5df2\u7ecf\u5b58\u5728\u540c\u6837\u7684\u6761\u4ef6,\u5982\u679c\u5b58\u5728,\u5219\u4e0d\u5141\u8bb8\u518d\u6b21\u6dfb\u52a0\u540c\u6837\u7684\u884c
	this.addRuleRow = function(){
		  var argArr = arguments[0];
		  if(this.validate(argArr)){
		  
		  		if(isSingle)
		  		{
		  			for(var i=0;i<argArr.length;i++)
		  			{
		  				if(argArr[i])
		  				{
		  					var row = packageRuleTbl.insertRow(packageRuleTbl.rows.length);
			  				var cell = row.insertCell();
			  				cell.innerHTML = argArr[i].elementValueName;
							cell.innerHTML +="<input type=hidden name='elementType' id='elementType'  value='"+argArr[i].elementType+"'>";
							cell.innerHTML +="<input type=hidden name='elementValue' id='elementValue'  value='"+argArr[i].elementValue+"'>";
							cell.innerHTML +="<input type=hidden name='elementValue' id='elementValueName'  value='"+argArr[i].elementValueName+"'>";
							cell = row.insertCell();
				  			cell.innerHTML = "<img src='../../../images/icon/delete.gif' border=0 style='cursor:hand' onclick='javascript:delNowRow(this)'>";
		  				}
		  			}
		  		}
		  		else
		  		{
		  			var row = packageRuleTbl.insertRow(packageRuleTbl.rows.length);
					for(var i=0;i<argArr.length;i++){
						var cell = row.insertCell();
						if(argArr[i]){
							cell.innerHTML = argArr[i].elementValueName;
						 	cell.innerHTML +="<input type=hidden name='elementType' id='elementType'  value='"+argArr[i].elementType+"'>";
						 	cell.innerHTML +="<input type=hidden name='elementValue' id='elementValue'  value='"+argArr[i].elementValue+"'>";
						 	cell.innerHTML +="<input type=hidden name='elementValue' id='elementValueName'  value='"+argArr[i].elementValueName+"'>";
						}else{
							cell.innerHTML = '&nbsp;'
						}
					}
					cell = row.insertCell();
				  	cell.innerHTML = "<img src='../../../images/icon/delete.gif' border=0 style='cursor:hand' onclick='javascript:delNowRow(this)'>";
		  		}
				
			}else{
				ErrorHandle("<%=getI18NResource("designer.rule_conflict_rechoose")%>",2);
			}
	 }
	 
	 //\u5224\u65ad
	 this.validate = function(argArr){
	 	var newRowBol = true;
	 	//\u4e24\u6b21\u63d2\u5165\u540c\u6837\u7684\u6570\u636e

	 	for(var j=1;j<packageRuleTbl.rows.length && newRowBol ;j++){
	 		var existRow = packageRuleTbl.rows[j];
		 	var cells = existRow.cells;
		 	//\u67d0\u4e00\u884c\u5224\u65ad\u662f\u5426\u76f8\u540c,\u6bcf\u4e00\u4e2a\u5143\u7d20\u90fd\u8981\u5224\u65ad,\u5982\u679c\u4e00\u884c\u4e2d\u6bcf\u4e2a\u5143\u7d20\u90fd\u7b49,\u9000\u51fa\u5faa\u73af,\u5df2\u5b58\u5728\u76f8\u540c\u7684
		 	var nowRowEqual = true;
	  	for(var k=0;k<cells.length-1 && nowRowEqual;k++){
	  		if(!cells[k].children[0]) continue;
	  		var elementType = cells[k].children[0].value;
	  		var elementValue = cells[k].children[1].value;
	  		var elementValueName = cells[k].children[2].value;
	  		for(var m=0;m<argArr.length;m++){
		  		if(argArr[m]!=null){
		  			if(argArr[m].elementType==elementType){
				  		if(argArr[m].elementValue != elementValue){
				  			nowRowEqual = false;
				  			break;
				  		}
			  		}
		  		}
	  		}
	  	}
	  	if(nowRowEqual) {
	  		newRowBol =false;
	  		break;
	  	}
		}
		if(!newRowBol) return newRowBol;
		//\u6570\u636e\u5e93\u91cc\u9762\u5df2\u6709\u7684\u6570\u636e\u4e0d\u80fd\u63d2\u5165\u3002

		if(existRows){
			for(var i=0;i<existRows.length && newRowBol;i++){
				var nowRowEqual = true;
				var exPackageRuleDtos = existRows[i];
				for(var j=0;j<exPackageRuleDtos.length&& nowRowEqual;j++){
					if(exPackageRuleDtos[j]==null) continue;
					for(var k=0;k<argArr.length;k++){
						if(argArr[k]!=null && argArr[k].elementType==exPackageRuleDtos[j].elementType){
							if(argArr[k].elementValue!=exPackageRuleDtos[j].elementValue){
								nowRowEqual = false;
								break;
							}
						}
					}
				}
				if(nowRowEqual) {
		  		newRowBol =false;
		  		break;
		  	}
			}
		}
		return newRowBol;
	 	
	 }
	 
	 this.singleChecked = function(tl){
		 var selItem = tl.selectedItem;
	   if(selItem==null)return;
	   var selItems = tl.checkedItems;
	   for(var i=0;i<selItems.length;i++)	
	  	 if (selItems[i]!=selItem)  	
	 		 	 selItems[i].setChecked(false);
	 }	
	 
}
	

function delNowRow(btn){
	var row = GetRow(btn);
	packageRuleTbl.deleteRow(row.rowIndex);
}

function GetRow(elm){
  return (elm)? ((elm.tagName=="TR")? elm : GetRow(elm.parentElement)) : elm;
}
</script>
</html>