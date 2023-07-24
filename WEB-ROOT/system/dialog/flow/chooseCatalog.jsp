<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- TemplBeginEditable name="doctitle" -->
	<title> <%=getI18NResource("designer.catalog_choose")%> </title>
	<!-- TemplEndEditable -->
	<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
	<script language="javascript" src="../../../public/script/helper.js"></script>
	<script language="javascript" src="../../../public/script/XmlInter.js"></script>
	<script language="javascript" src="../../../public/script/FormExt.js"></script>
	<!-- TemplBeginEditable name="head" -->
	<?XML:NAMESPACE PREFIX="ZTESOFT" />
	<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
	<!-- TemplEndEditable -->
</head>
<body scroll="no">
<!-- TemplBeginEditable name="body" -->
<script language="javascript" src="../../../public/script/topTitle.js"></script>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
	<tr>
		<td width="100%">
			<ZTESOFT:treelist id="catalogTree" name="catalogTree" class="treelist" width="100%" height="260" onItemClick="catalogOperation.cataClick()">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("designer.catalog_name")%>" propertyName="name" />
					<ZTESOFT:column propertyName="id" />					
				</ZTESOFT:columns>
			</ZTESOFT:treelist>		
		</td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
	<tr>
		<td align="center" height="40">
	  	<input type="button" class="button_blue" id="okBtn" value="<%=getI18NResource("designer.ok")%>" onclick='catalogOperation.submit()'>
	  	<input type="button" class="button_blue" id="cnlBtn" value="<%=getI18NResource("designer.cancel")%>" onclick="catalogOperation.cancel()">
	  </td>
	</tr>
	<tr>
   <td height="1" colspan="2" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
</table>
<!-- TemplEndEditable -->
</body>
</html>

<script language="javascript">
/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-10
// Author : Xu.fei3
// commits: Choose a catalog
/////////////////////////////////////////////////////

/* \u5168\u5c40\u53d8\u91cf */
var areaId = window.dialogArguments;
var catalogOperation = new CatalogOperation();

/* \u521d\u59cb\u5316 */
catalogOperation.init();

//////////////////////////////////////////
//Class\u7c7b\u533a
function CatalogOperation(){
	this.catalogTree = document.all.catalogTree;
	
	//\u521d\u59cb\u5316

	this.init = function(){	
		
		var data = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findTopPackageCatalogsXml", areaId);
		this.catalogTree.loadByXML(data);
		
		for(var i=0; i<this.catalogTree.items.length; i++){
			this.catalogTree.items[i].setImage("../../js/resources/catalog.gif");
		}
		
		/**
		var _data;		
		_data = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackageCatalogsByAreaId", areaId);
		this.catalogTree.loadByData(_data);
		*/
	}
	
	
	//\u5355\u51fb\uff0c\u5c55\u5f00
	this.cataClick = function(){
	  //\u76ee\u5f55\u4e0b\u9762\u53ef\u80fd\u6709\u5b50\u76ee\u5f55
		if((this.catalogTree.selectedItem.items == null) || (this.catalogTree.selectedItem.items.length == 0)){
			var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findSubCatgByCatalogIdXml", this.catalogTree.selectedItem.id);
			if(xmlData && xmlData.length >15){			
				this.catalogTree.selectedItem.insertByXML(xmlData);					
				this.catalogTree.selectedItem.expand(true);
				for(var i=0; i<this.catalogTree.selectedItem.items.length; i++){
					this.catalogTree.selectedItem.items[i].setImage("../../js/resources/catalog.gif");
				}
			}			
		}
	}
	
	//\u63d0\u4ea4
	this.submit = function(){
		if(this.catalogTree.selectedItem == null){
			alert("<%=getI18NResource("designer.please_choose_org")%>");
			return;
		}

		var result = new Object();
		
		result.catalogId = this.catalogTree.selectedItem.id;	
		result.catalogName = this.catalogTree.selectedItem.name;
		
		window.returnValue = result;
		window.close();
	}
	
	//\u53d6\u6d88
	this.cancel = function(){
		window.close();
	}
}
</script>
