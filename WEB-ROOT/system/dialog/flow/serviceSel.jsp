<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.service")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<script language="javascript" src="../../../public/script/ErrorHandle.js"></script>
<!-- TemplBeginEditable name="head" -->
<script language="javascript" src="../../../public/script/BSCommon.js"></script>
<?XML:NAMESPACE PREFIX="ZTESOFT" />
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
<!-- TemplEndEditable -->
</head>
<body style="width:100%; height:100%; overflow:auto">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<TABLE width="100%"  border="0" cellpadding="0" cellspacing="5">
<TR valign="top">
<TD >
<ZTESOFT:treelist id="pcTree" height="260" width="100%" showCheck="true" class="treelist" onItemClick="fext.setService()" onItemExpand="fext.expand()">
<ZTESOFT:columns>
<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.name")%>" propertyName="name"/>
<ZTESOFT:column display="false" propertyName="id"/>
<ZTESOFT:column display="false" propertyName="pathcode"/>
<ZTESOFT:column display="false" propertyName="flag"/>
<ZTESOFT:column display="false" propertyName="isClick"/>
</ZTESOFT:columns>
</ZTESOFT:treelist>
</TD>
</tr>
<tr>
<TD >
<table width="100%" border="0" cellspacing="1" cellpadding="2" height="60">
<form name="form1" onSubmit="return false">
<tr>
<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
</tr>
<tr>

<td class="td_grey" colspan="2" align="center" height="40"><input type="button" name="Add" id="Add" style="width:50"  value=" " class="button_blue" onclick="fext.submitForm()"> &nbsp;&nbsp;<input type="button" name="Cancel" id="Cancel" value=" " class="button_blue" onclick="window.close()"> </td>
</tr>
<tr>
<td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
</tr>
</form>
</table>
</TD>
</TR>
</TABLE>

<!-- TemplEndEditable -->
</body>
</html>
<script language="javascript">
	//////////////////////////////////////////
// ZTESoft corp. 2005-06-26
// Author :Lihg
// commits:
//////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
	var fext = new MyForm();
	var catalogTL = document.all.pcTree;

/*\u521d\u59cb\u5316*/
fext.loadIniData();

 //Class extend FormExt\u7c7b\u5b9a\u4e49

function MyForm(){
	_extends(this, BSCommon, '"form1"');

	this.loadIniData =  function(){
		var objXML;
		try{
			objXML = callRemoteFunctionNoTrans("com.ztesoft.iom.sla.TacheLimitManager","getServiceCatalogXML");
			catalogTL.loadByXML(objXML);
			for (var i=0; i<catalogTL.items.length; i++){
				if (catalogTL.items[i].flag == "C"){
					catalogTL.items[i].setImage("../../../images/folder.gif");
					catalogTL.items[i].setCheckDisabled(true);
				}
			}
		}catch(ex){
			ErrorHandle(ex);
		}
	}
	
  /*\u5c55\u5f00*/
  this.expand = function(){
  	var selItem = catalogTL.selectedItem;	 	  
		if(selItem.isClick=="0"&&selItem.flag=='C'){
		 	selItem.isClick="1";						
	  	var objAttr = callRemoteFunctionNoTrans("com.ztesoft.iom.sla.TacheLimitManager","getServiceListXML",selItem.id);
		  selItem.expand(true);
		  if (objAttr!=null){
		  	selItem.insertByXML(objAttr);
		  	selItem.expand(true);
		  }		   		  		 
		}
  }
    
  /*\u751f\u6210\u670d\u52a1\u6811*/
  this.setService = function(){
  	var selItem = catalogTL.selectedItem;	 	  
		if(selItem.isClick=="0"&&selItem.flag=='C'&&!selItem.items){
		 	selItem.isClick="1";						
	  	var objAttr = callRemoteFunctionNoTrans("com.ztesoft.iom.sla.TacheLimitManager","getServiceListXML",selItem.id);
		  //selItem.expand(true);
		  if (objAttr!=null){
		  	selItem.insertByXML(objAttr);
		  	selItem.expand(true);
		  }		   		  		 
		}
  }

	this.submitForm = function(){
		var items = catalogTL.checkedItems;
		if(items.length == 0){
			alert("<%=getI18NResource("designer.please_choose_service")%>");
			return;
		}

		var result = new Object();
		var serviceIds = "";
		var serviceNames = "";
		for(var i=0; i<items.length-1; i++){
			serviceIds += items[i].id + ",";
			serviceNames += items[i].name + ",";
		}
		serviceIds += items[items.length-1].id;
		serviceNames += items[items.length-1].name;

		result.serviceIds = serviceIds;
		result.serviceNames = serviceNames;
		window.returnValue = result;
		window.close();

		/*
		var selItem = catalogTL.selectedItem;
		if(selItem==null)return;
		var flag = selItem.flag;
		if (flag=='C') return;
		this.setValue(this.form.name,selItem.name);
		this.setValue(this.form.id,selItem.id);
		if(this.form.name.value==""){
			alert(Gi18n.please);
			return;
		}
		var dArg = new Object();
		dArg.id = this.getValue(this.form.id);
		dArg.name =  this.getValue(this.form.name);
		window.returnValue = dArg;
		window.close();
		*/
	}
}
</script>
