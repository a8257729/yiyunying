<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../public/I18N.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=getI18NResource("tache.tache_manage")%></title>
<link rel="stylesheet" type="text/css" href="../public/css/style.css">
<script language="javascript" src="../public/script/helper.js"></script>
<script language="javascript" src="../public/script/XmlInter.js"></script>
<script language="javascript" src="../public/script/FormExt.js"></script>
<?XML:NAMESPACE PREFIX="ZTESOFT" />
<?IMPORT NAMESPACE="ZTESOFT" IMPLEMENTATION="../public/htc/TreeList/TreeList.htc" />
<?XML:NAMESPACE PREFIX="ZTESOFT1" />
<?IMPORT NAMESPACE="ZTESOFT1" IMPLEMENTATION="../public/htc/TreeList/TreeListSort.htc" />

</head>
<body background="../images/main/background.gif" style="width:100%; height:100%; overflow:auto">
<script language="javascript" src="../public/script/topTitleJs.js"></script>
<table width="100%" border="0" cellpadding="2" cellspacing="1">
	<tr>
		<td width="180" valign="top">
			<table width="180"  border="0" cellspacing="1" cellpadding="2">
				<tr>
					<td>
						<ZTESOFT:treelist id="tlvTacheType" class="treelist" width="100%" height="515" onItemClick="tacheCtgManageOper.selItem()" onItemContextMenu="tacheCtgManageOper.selectContextMenu()"  onDivContextMenu="tacheCtgMenu1.popMenu()">
							<ZTESOFT:columns>
								<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("tache.tache_catalog_id")%>" propertyName="name"/>
								<ZTESOFT:column display="false" displayText="PATH_NAME" propertyName="PATH_NAME"/>
								<ZTESOFT:column displayText="id" propertyName="id"/>
								<ZTESOFT:column displayText="pathCode" propertyName="pathCode"/>
								<ZTESOFT:column displayText="COMMENTS" propertyName="COMMENTS"/>
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
						<br><br>
						<%=getI18NResource("tache.tacheName_id")%><%=getI18NResource("tache.colon")%>
						<input name="fuzzyQryInput" type="text" width="180" id="fuzzyQryInput" >
						<br>
						<input type="button" id="fuzzyQryBtn" value="<%=getI18NResource("tache.fuzzy_queryBtn")%>" onclick="tacheCtgManageOper.fuzzyQryTache()"  class="button_blue"  NAME="fuzzyQryBtn">
					</td>
				</tr>
			</table>
		</td>
		<td valign="top">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
				<tr>
					<td class="td_blue_title">&nbsp;<img src="../images/icon/title_dot.gif" align="absmiddle">&nbsp;<span id="tacheList_id"><%=getI18NResource("tache.tacheList_id")%></span></td>
				</tr>
				<tr>
					<td valign="top">
						<ZTESOFT:treelist id="tlvTache" height="250" width="100%" class="treelist" onItemClick="tacheManageOper.selectItem()" onItemContextMenu="tacheManageOper.selectContextMenu()" onDivContextMenu="tacheManageOper.popTacheMenu()">
							<ZTESOFT:columns>
								<ZTESOFT:column width="22%" display="true" displayText="<%=getI18NResource("tache.tacheName_id")%>" propertyName="TACH_NAME" />
								<ZTESOFT:column width="12%" display="true" displayText="<%=getI18NResource("tache.tacheCode_id")%>" propertyName="TACH_CODE" />
								<ZTESOFT:column width="11%" display="true" displayText="<%=getI18NResource("tache.concurrent_count")%>" propertyName="PARELLEL_NUM" />
								<ZTESOFT:column width="10%" display="true" displayText="<%=getI18NResource("tache.tache_state")%>" propertyName="STATE_NAME" />
								<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("tache.effTime_id")%>" propertyName="EFF_DATE" />
								<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("tache.expTime_id")%>" propertyName="EXP_DATE" />
								<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("tache.state_time")%>" propertyName="STATE_DATE" />
								<ZTESOFT:column display="false" displayText="ID"  propertyName="ID" />
								<ZTESOFT:column display="false" displayText="" propertyName="CURRENT_EDITION" />
								<ZTESOFT:column propertyName="TACHE_CATALOG_ID" />
								<ZTESOFT:column propertyName="AUTO_FLAG" />
								<ZTESOFT:column propertyName="RELEASE_STATE" />
								<ZTESOFT:column propertyName="TACHE_DEFINE_ID" />
								<ZTESOFT:column propertyName="SHOW_TYPE" />
								<ZTESOFT:column propertyName="IS_HINT" />
								<ZTESOFT:column propertyName="IS_HINT_NAME" />
								<ZTESOFT:column propertyName="IS_PRECONTRACT" />
								<ZTESOFT:column propertyName="IS_PRECONTRACT_NAME" />
								<ZTESOFT:column propertyName="LIMIT_VALUE" />
								<ZTESOFT:column propertyName="ALERT_VALUE" />
								<ZTESOFT:column propertyName="OPER_TYPE_ID" />
								<ZTESOFT:column propertyName="OPERTYPE_NAME" />
								<ZTESOFT:column propertyName="STATE" />
								<ZTESOFT:column propertyName="IS_DEL" />
								<ZTESOFT:column propertyName="IS_DEL_NAME" />
								<ZTESOFT:column propertyName="TIME_UNIT_NAME" />
								<ZTESOFT:column propertyName="DEFAULT_CONSTRUCTOR" />
								<ZTESOFT:column propertyName="DEFAULT_OPERFINISHDATE" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<br>
						<ZTESOFT:tabStrip id="tab2" class="tab_ioms" selectedIndex="0" >
							<ZTESOFT:tabs height="210" width="100%">
								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_detail")%>">
									<table width="100%" border="0" cellspacing="1" cellpadding="2" id='addBaseInfoTbl'>
										<form name="form1" onsubmit="return false;">
										<tr>
											<td height="1" colspan="4" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
										</tr>
										<tr>
											<td width="120" class="td_blue_txt" id="tacheName_id"><%=getI18NResource("tache.tacheName_id")%></td>
											<td class="td_grey"  colspan="3" id='TACH_NAME'></td>
										</tr>
										<tr>
											<td width="120" class="td_blue_txt" id="tacheCode_id"><%=getI18NResource("tache.tacheCode_id")%></td>
											<td class="td_grey" colspan="3" id='TACH_CODE'></td>
										</tr>
										<tbody id="tacheDefineInfo" style="display:none">
										<tr>
											<td width='120' height='30' class='td_blue_txt' id="edition_id"><%=getI18NResource("tache.edition_id")%></td>
											<td class='td_grey' id='CURRENT_EDITION'></td>
											<td width='120' height='30' class='td_blue_txt'><%=getI18NResource("tache.if_can_remove")%></td>
											<td class='td_grey' id='IS_DEL_NAME'></td>
										</tr>
										<tr>
											<td width='120' height='30' class='td_blue_txt' id="yesOrNo_id"><%=getI18NResource("tache.di_id")%></td>
											<td class='td_grey' id='IS_HINT_NAME'></td>
											<td width='120' height='30' class='td_blue_txt' id="yesOrNo1_id"><%=getI18NResource("tache.precontract_id")%></td>
											<td class='td_grey' id='IS_PRECONTRACT_NAME'></td>
										</tr>
										<tr>
											<td width='120' height='30' class='td_blue_txt'><%=getI18NResource("tache.is_default_constructor")%></td>
											<td class='td_grey' id='DEFAULT_CONSTRUCTOR'></td>
											<td width='120' height='30' class='td_blue_txt'><%=getI18NResource("tache.is_default_operfinishdate")%></td>
											<td class='td_grey' id='DEFAULT_OPERFINISHDATE'></td>
										</tr>
										<tr>
											<td class="td_blue_txt" id="effDate_id"><%=getI18NResource("tache.effTime_id")%></td>
											<td class="td_grey" id='EFF_DATE'></td>
											<td class="td_blue_txt" id="expDate_id"><%=getI18NResource("tache.expTime_id")%></td>
											<td class="td_grey" id='EXP_DATE'></td>
										</tr>
										<tr>
											<td class="td_blue_txt" id="limit_id"><%=getI18NResource("tache.limit_id")%></td>
											<td class="td_grey" id='LIMIT_VALUE'></td>
											<td class="td_blue_txt" id="alert_id"><%=getI18NResource("tache.alert_id")%></td>
											<td class="td_grey" id='ALERT_VALUE'></td>
										</tr>
										<tr>
											<td width="120" class="td_blue_txt" id="timeunit_name"><%=getI18NResource("tache.timeUnit_id")%></td>
											<td class="td_grey" align="left" valign="middle" id='TIME_UNIT_NAME'></td>
											<td width="120" class="td_blue_txt" id="ope_id"><%=getI18NResource("tache.operType_id")%></td>
											<td class="td_grey" align="left" valign="middle" id='OPERTYPE_NAME'></td>
										</tr>
										</tbody>
										<tr>
											<td height="1" colspan="4" class="td_blue_line"><img src="images/shim.gif" width="1" height="1"></td>
										</tr>
										</form>
									</table>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_return_reason")%>">
									<ZTESOFT:treelist id="tlvRr" height="190" width="100%" class="treelist"  onItemContextMenu="rrMenu.popMenu()"   onDivContextMenu="tacheManageOper.popRrMenu()"  sorted='true'>
										<ZTESOFT:columns>
											<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("tache.name_id")%>" propertyName="RETURN_REASON_NAME" />
											<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("tache.area_id")%>" propertyName="AREA_NAME" />
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("tache.reasonSort_id")%>" propertyName="REASON_TYPE_NAME" />
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("tache.auditOrNot_id")%>" propertyName="AUDIT_FLAG_NAME" />
											<ZTESOFT:column display="false" propertyName="ID" />
											<ZTESOFT:column	display="false" propertyName="AREA_ID" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_overtime_reason")%>">
									<ZTESOFT:treelist id="tlvOr" height="190" width="100%" class="treelist" onItemContextMenu="orMenu.popMenu()" onDivContextMenu="tacheManageOper.popOrMenu()">
										<ZTESOFT:columns>
											<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("tache.name_id")%>" propertyName="OVERTIME_REASON_NAME" />
											<ZTESOFT:column display="false" propertyName="ID" />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tahce_template")%>">
									<ZTESOFT:treelist id="tlvTemplate" height="190" width="100%" class="treelist" onItemContextMenu="tmMenu.popMenu()" onDivContextMenu="tacheManageOper.popTmMenu()">
										<ZTESOFT:columns>
											<ZTESOFT:column width="40%" display="true" displayText="<%=getI18NResource("tache.template_catalog")%>" propertyName="PATH_NAME"/>
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("tache.template_name")%>" propertyName="TEMPLATE_NAME"/>
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("tache.area_id")%>" propertyName="AREA_NAME"/>
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("template.is_his")%>" propertyName="isHisDesc"/>
											<ZTESOFT:column display="false" displayText="ID" propertyName="ID"/>
											<ZTESOFT:column display="false" propertyName="isHis"/>
											<ZTESOFT:column display="false" displayText="AREA_ID" propertyName="AREA_ID"/>
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_function")%>">
									<ZTESOFT1:treelist id="tlvBtn" height="190" width="100%" class="treelist" onItemContextMenu="btMenu.popMenu()" onDivContextMenu="tacheManageOper.popBtMenu()"  sorted='true'>
										<ZTESOFT1:columns>
											<ZTESOFT1:column width="25%" display="true" displayText="<%=getI18NResource("tache.display_name_id")%>" propertyName="DISPLAY_NAME" />
											<ZTESOFT1:column width="40%" display="true" displayText="<%=getI18NResource("tache.path")%>" propertyName="PATH_NAME" />
											<ZTESOFT1:column width="25%" display="true" displayText="<%=getI18NResource("tache.function_name")%>" propertyName="NAME" />
											<ZTESOFT1:column width="10%" display="true" displayText="<%=getI18NResource("tache.display_seq")%>" propertyName="DISPLAYINDEX" />
											<ZTESOFT1:column display="false" propertyName="ID" />
										</ZTESOFT1:columns>
									</ZTESOFT1:treelist>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_comment")%>">
									<ZTESOFT:treelist id="tlvTacheComment" class="treelist" width="100%" height="190" onItemClick="" onItemContextMenu="cmMenu.popMenu()" onDivContextMenu="tacheManageOper.popCmMenu()">
										<ZTESOFT:columns>
											<ZTESOFT:column width="15%" display="true" displayText="<%=getI18NResource("tache.area_id")%>" propertyName="AREA_NAME"/>
											<ZTESOFT:column width="55%" display="true" displayText="<%=getI18NResource("tache.tache_comment")%>" propertyName="TACHE_INFO"/>
											<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("tache.remark")%>" propertyName="COMMENTS"/>
											<ZTESOFT:column  propertyName="AREA_ID"/>
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>
								
								<ZTESOFT:page Text="<%=getI18NResource("tache.wo_init_destroy")%>">
									<ZTESOFT1:treelist id="tlvMethodCfg" height="190" width="100%" class="treelist"  onItemContextMenu="mdMenu.popMenu()">
										<ZTESOFT1:columns>
											<ZTESOFT1:column width="50%" display="true" displayText="<%=getI18NResource("tache.method")%>" propertyName="methodName" />
											<ZTESOFT1:column width="50%" display="true" displayText="<%=getI18NResource("tache.call_obj_name")%>" propertyName="bizObjName" />
											<ZTESOFT1:column display="false" propertyName="id" />
											<ZTESOFT1:column display="false" propertyName="flag" />
											<ZTESOFT1:column display="false" propertyName="bizObjId" />
											<ZTESOFT1:column display="false" propertyName="tacheDefineId" />
										</ZTESOFT1:columns>
									</ZTESOFT1:treelist>
								</ZTESOFT:page>

								<ZTESOFT:page Text="<%=getI18NResource("tache.tache_resource_relation")%>">
									<ZTESOFT:treelist id="tlvTacheDefineRes" class="treelist" width="100%" height="190" onItemClick="" onItemContextMenu="tdrMenu.popMenu()" onDivContextMenu="tacheManageOper.popTdrMenu()">
										<ZTESOFT:columns>
											<ZTESOFT:column width="50%" display="true" displayText="<%=getI18NResource("tache.resource_type_name")%>" propertyName="resTypeName"  />
											<ZTESOFT:column width="30%" display="true" displayText="<%=getI18NResource("tache.resource_count")%>" propertyName="resNbr"  />
											<ZTESOFT:column width="20%" display="true" displayText="<%=getI18NResource("tache.is_must")%>" propertyName="isMustName"  />
											<ZTESOFT:column display="false" propertyName="isMust"  />
											<ZTESOFT:column display="false" propertyName="tacheDefineId"  />
											<ZTESOFT:column display="false" propertyName="resType"  />
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>

									<ZTESOFT:page Text="<%=getI18NResource("tache.autoManual")%>">
									<ZTESOFT:treelist id="tlvTacheAutoToManual" class="treelist" width="100%" height="190" onItemClick="" onItemContextMenu="atmMenu.popMenu()" onDivContextMenu="tacheManageOper.popAtmMenu()">
										<ZTESOFT:columns>
											<ZTESOFT:column width="100%" display="true" displayText="<%=getI18NResource("tache.area_id")%>" propertyName="AREA_NAME"/>
											<ZTESOFT:column  propertyName="AREA_ID"/>
										</ZTESOFT:columns>
									</ZTESOFT:treelist>
								</ZTESOFT:page>

							</ZTESOFT:tabs>
						</ZTESOFT:tabStrip>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- TemplEndEditable -->
</body>
</html>

<script language="JScript"><%@ include file="js/tacheManage.js"%></script>
