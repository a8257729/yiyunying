<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- TemplBeginEditable name="doctitle" -->
<title> <%=getI18NResource("designer.choose_flow_catalog")%> </title>
<!-- TemplEndEditable -->
<link rel="stylesheet" type="text/css" href="../../../public/css/style.css">
<script language="javascript" src="../../../public/script/helper.js"></script>
<script language="javascript" src="../../../public/script/XmlInter.js"></script>
<script language="javascript" src="../../../public/script/FormExt.js"></script>
<script language="javascript" src="../../../public/script/ErrorHandle.js"></script>
<!-- TemplBeginEditable name="head" -->
<script language="javascript" src="../public/script/BSCommon.js"></script>
<?XML:NAMESPACE PREFIX="ZTESOFT" />
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../../../public/htc/TreeList/TreeList.htc" />
<!-- TemplEndEditable -->
</head>
<body style="width:100%; height:100%; overflow:auto">
<script language="javascript" src="../../../public/script/topTitle.js"></script>
  <table width="100%"  border="0" cellpadding="0" cellspacing="5">
    <tr valign="top">
      <TD>
      	<ZTESOFT:treelist id="flowLibrary" name="flowLibrary" class="treelist" width="100%" height="340" onItemClick="fext.flowClick()" >
						<ZTESOFT:columns>
							<ZTESOFT:column width="100%" display="true" displayTip="true" displayText="<%=getI18NResource("designer.flow_library")%>" propertyName="name" />
							<ZTESOFT:column display="false" propertyName="id" />
							<ZTESOFT:column display="false" propertyName="type" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
      </td>
    </tr>
     <tr>
      <td>
			<table width="100%" border="0" cellspacing="1" cellpadding="2">
			<tr>
						    <td height="1" class="td_blue_line"><img src="../../../images/shim.gif" width="1" height="1"></td>
						  </tr>
						  <tr>
						    <td class="td_grey" align="center" height="40"><input type="button" name="Add" id="btn1" style="width:50" value="<%=getI18NResource("designer.ok")%>" class="button_blue" onclick="fext.submitForm()">
							<input type="button" name="Cancel" id="btn2"  value="<%=getI18NResource("designer.cancel")%>" class="button_blue" onclick="window.close()"> </td>
		</tr>
		<tr>
						    <td height="1" class="td_blue_line"><img src="../../../images/shim.gif" width="1" height="1"></td>
						  </tr>
  </table>
</td>
		</tr>
  </table>
<!-- TemplEndEditable -->
</body>
</html>
<script>
/* \u5168\u5c40\u53d8\u91cf */
	var fext = new MyForm();
	var inObj = window.dialogArguments;	
  var CATALOG = 1;
  var TEMPLATE = 2;
  var VERSION = 3;
  
  
	/*\u521d\u59cb\u5316*/
fext.loadIniData();

	//Class
	function MyForm(){
		_extends(this, FormExt, '"form1"');
		
		this.flowTree = document.all.flowLibrary;
		this.loadIniData =  function(){
			var areaId = null;
			if(inObj){
		  	areaId = inObj.areaId;
		  }
		
			var data = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findTopPackageCatalogsXml", areaId);
			this.flowTree.loadByXML(data);
			
			for(var i=0; i<this.flowTree.items.length; i++){
				this.flowTree.items[i].setImage("../../js/resources/catalog.gif");
			}
		}

		this.submitForm = function(){
			var selItem = this.flowTree.selectedItem;
			if(!selItem) return;
			
			if(selItem.type!=CATALOG){
				ErrorHandle("<%=getI18NResource("designer.please_choose_flow_catalog")%>");
				return;
			}
			
			var dArg = {"name":selItem.name,"id":selItem.id};
			window.returnValue = dArg;
	 		window.close();
		}
		
		//\u6d41\u7a0b\u6811\u4e0a\u9762\u4efb\u610f\u4e00\u4e2a\u8282\u70b9\u70b9\u51fb\u4e86\u4ee5\u540e\u505a\u7684\u4e8b\u60c5
	  this.flowClick = function(){
	  	var selItem = this.flowTree.selectedItem;
	  	
			//\u70b9\u51fb\u7684\u4f4d\u7f6e\u6ca1\u6539\u53d8\uff0c\u5c31\u4ec0\u4e48\u90fd\u4e0d\u505a
			switch(parseInt(selItem.type)){
				//\u6d41\u7a0b\u76ee\u5f55
				case CATALOG:{
					//\u76ee\u5f55\u4e0b\u9762\u53ef\u80fd\u6709\u5b50\u76ee\u5f55\u548c\u6a21\u677f

					if((selItem.items == null) || (selItem.items.length == 0)){
						var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackagesByCatalogIdXml", selItem.id);
						selItem.insertByXML(xmlData);
						selItem.expand(true);
			
						for(var i=0; i<selItem.items.length; i++){
							if(selItem.items[i].type == CATALOG){
								selItem.items[i].setImage("../../js/resources/catalog.gif");
							}else{
								selItem.items[i].setImage("../../js/resources/package.gif");
							}
						}
					}
					break;
				}
				//\u6d41\u7a0b\u6a21\u677f
				case TEMPLATE:{
					//\u6a21\u677f\u4e0b\u9762\u6709\u7248\u672c

					if((selItem.items == null) || (selItem.items.length == 0)){
						var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findDefinitionsByPackageIdXml", selItem.id);
						selItem.insertByXML(xmlData);
						selItem.expand(true);
						
						for(var i=0; i<selItem.items.length; i++){
							selItem.items[i].setImage("../../js/resources/version.gif");
						}
					}
					break;
				}
				//\u6d41\u7a0b\u7248\u672c
				case VERSION:{
					//\u4ec0\u4e48\u90fd\u4e0d\u505a
					break;
				}
				
			}
		}
	}
</script>

