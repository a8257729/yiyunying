package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Node;

import com.ztesoft.mobile.v2.entity.workform.xinjiang.WorkOrder;

public class WorkOrderSa extends WorkOrder {


	public static final String WORK_ORDER_LIST_NODE = "WorkOrderList";
	public static final String ORDER_LIST_NODE = "OrderList";
	public static final String RES_LIST_NODE = "ResourceList";
	public static final String PORT_LIST_NODE = "PortList";
	
	// 装移定单属性
	public static final String ZY_ORDER_CODE_NODE = "OrderCode";
	public static final String ZY_ORDER_LEVEL_NODE = "Order_Level";
	//订单受理时间
	public static final String ZY_APPLIC_DATE_NODE = "Applic_Date";
	public static final String ZY_ORDER_CODEX_NODE = "Order_Codex";
	//业务号码
	public static final String ZY_LOGIC_NUM_NODE = "Logic_Num";
	public static final String ZY_PHY_NUM_NODE = "Phy_Num";
	public static final String ZY_APPOIN_DATE_NODE = "Appoin_Date";
	//用户名称
	public static final String ZY_CUSTNAME_NODE = "Cust_Name";
	//客户等级
	public static final String ZY_CUST_LEVEL_NODE = "Cust_Level";
	//客户品牌
	public static final String ZY_CUST_BRAND_NODE = "Cust_Brand";
	public static final String ZY_CONTACT_NODE = "Contact";
	public static final String ZY_CONTSCT_P_NODE = "Contact_P";
	public static final String ZY_ZJ_ADDR_NODE = "Zj_Addr";
	public static final String ZY_WORKORDERID_NODE = "WorkOrderID";
	//受理人
	public static final String ZY_ACCEP_U_NODE = "Accep_U";
	//受理 部门
	public static final String ZY_ACCEP_D_NODE = "Accep_D";
	public static final String ZY_ACCEP_R_NODE = "Accep_R";
	public static final String ZY_IS_REPEAT_NODE = "Is_Repeat";
	public static final String ZY_IS_URGE_NODE = "Is_Urge";
	public static final String ZY_TIME_COUNT_TYPE_NODE = "Time_Count_Type";
	public static final String ZY_TIME_COUNT_NUM_NODE = "Time_Count_Num";
	//用户等级
	public static final String ZY_USER_LEVEL_NODE = "User_Level";
	public static final String ZY_IS_NEED_FOCUS_NODE = "Is_Need_Focus";
	public static final String ZY_PROD_CODE_NODE = "Prod_Code";
	public static final String ZY_IP_PROPERTY_NODE = "Ip_Property";
	public static final String ZY_USER_IP_PROPERTY_NAME_NODE = "User_Ip_Property_Name";
	public static final String ZY_DEVICE_IP_PROPERTY_NAME_NODE = "Device_Ip_Property_Name";
	public static final String ZY_LAN_ID_NODE = "Lan_ID";
	//订单编码
	public static final String ZY_IOM_ORDER_CODE_NODE = "Iom_Order_Code";
	// 装移工单属性
	public static final String ZY_WORDER_STATU_NODE = "Worder_Statu";
	public static final String ZY_WORDER_STATU_NAME_NODE = "Worder_Statu_Name";
	public static final String ZY_WORDER_CODE_NODE = "Worder_Code";
	public static final String ZY_WORK_TYPE_NODE = "Work_Type";
	public static final String ZY_ORDER_ID_NODE = "Order_ID";
	public static final String ZY_STATUS_NODE = "Status";
	public static final String ZY_CON_BOK_DATE_NODE = "Con_Bok_Date";
	public static final String ZY_BUSIN_TYPE_NODE = "Busin_Type";
    //装移单资源信息
	public static final String ZY_NATUR_NODE = "Natur";
	public static final String ZY_ACCESSMODE_NODE = "Access_Mode";
	public static final String ZY_ACCESS_TYPE_NODE = "Access_Type";
	public static final String ZY_CHARGE_NODE = "Charge";
	public static final String ZY_BROA_ACCO_NODE = "Broa_Acco";
	public static final String ZY_BROA_PASS_NODE = "Broa_Pass";
	public static final String ZY_BROA_RATE_NODE = "Broa_Rate";
	public static final String ZY_RELA_NUM_NODE = "Rela_Num";
	public static final String ZY_BUSIN_PASS_NODE = "Busin_Pass";
	public static final String ZY_DEVCOD_NEW_NODE = "Devcod_New";
	public static final String ZY_DEVCOD_OLD_NODE = "Devcod_Old";
	public static final String ZY_PHYCODE_NEW_NODE = "Phycode_New";
	public static final String ZY_PHYCODE_OLD_NODE = "Phycode_Old";
	public static final String ZY_SECTYPE_NEW_NODE = "Sectype_New";
	public static final String ZY_SECTYPE_OLD_NODE = "Sectype_Old";
	public static final String ZY_ROW_NODE = "Row";
	public static final String ZY_ROW_OLD_NODE = "Row_Old";
	public static final String ZY_LINE_NODE = "Line";
	public static final String ZY_LINE_OLD_NODE = "Line_Old";
	public static final String ZY_AU_ROW_NODE = "Au_Row";
	public static final String ZY_AU_ROW_OLD_NODE = "Au_Row_Old";
	public static final String ZY_DA_ROW_NODE = "Da_Row";
	//数据横列(旧)
	public static final String ZY_DA_ROW_OLD_NODE = "Da_Row_Old";
	public static final String ZY_BROA_PORT_NODE = "Broa_Port";
	public static final String ZY_BROA_PORT_OLD_NODE = "Broa_Port_Old";
	public static final String ZY_TERM_INF_NODE = "Term_Inf";
	public static final String ZY_SPLI_DW_PORT_NODE = "Spli_Dw_Port";
	//终端上联局方端口(旧)
	public static final String ZY_TERM_UP_PORT_NODE = "Term_Up_Port";
	public static final String ZY_LO_SN_NUM_NODE = "Lo_Sn_Num";
	public static final String ZY_LINK_INF_NODE = "Link_Inf";
	public static final String ZY_LINK_INF_OLD_NODE = "Link_Inf_Old";
	public static final String ZY_ADSL_TEST_NODE = "Adsl_Test";
	public static final String ZY_JBOX_INF_NODE = "Jbox_Inf";
	public static final String ZY_LINK_INF_NEW_NODE = "Link_Inf_New";
	public static final String ZY_SIP_PASSWORD_NODE = "Sip_Password";
	public static final String ZY_IPTV_PASSWORD_NODE = "Iptv_Password";
	public static final String ZY_EXCH_NAME_NODE = "Exch_Name";
	public static final String ZY_NET_ACCO_NODE = "Net_Acco";
	public static final String ZY_RANGE_ADR_NODE = "Range_Adr";
	public static final String ZY_ODB_NAME_NODE = "Odb_Name";
	//OLT设备IP地址
	public static final String ZY_OLT_SB_IP_ADR_NODE = "Olt_Sb_Ip_Adr";
	public static final String ZY_IS_FTTH_NODE = "Is_Ftth";
	public static final String ZY_DEVICE_CLASS_NODE = "Device_Class";
	public static final String ZY_DEVICE_SOURCE_NODE = "Device_Source";
	public static final String ZY_IS_FLOW_ZLHT_NODE = "Is_Flow_Zlht";
	
	//端口信息
		public static final String ZY_DK_CUSTPRODID = "CustPortID";
		public static final String ZY_DK_CUSTDESEQ = "CustDeseq";
		public static final String ZY_DK_CUSTORDERCODE = "CustOrderCode";
		public static final String ZY_DK_CUSTORDERID = "CustOrderId";
		public static final String ZY_DK_CUSTPRODTYPE = "CustProdType";
		public static final String ZY_DK_CUSTOPRSTATE = "CustOprState";
		public static final String ZY_DK_CUSTNAME = "CustName";
		public static final String ZY_DK_CUSTDIRENAME = "CustDireName";
		public static final String ZY_DK_CUSTADDRNAME = "CustaddrName";
		public static final String ZY_DK_SPCCOMNAME = "SpcComName";
		
		public static final String ZY_DK_CUSTEQPNAME = "CustEqpName";
		public static final String ZY_DK_CUSTPORTNAME = "CustPortName";
		public static final String ZY_DK_CUSTMOALAIS = "CustMoalais";
		public static final String ZY_DK_CUSTRACKNAMEH = "CustRackName";
		public static final String ZY_DK_CUSTHTERMNO = "CustHtermNo";
		
		public static final String ZY_DK_CUSTDIREID = "CustDireId";
		public static final String ZY_DK_CUSTEQPID = "CustEqpId";
		public static final String ZY_DK_CUSTEQPNO = "CustEqpNo";
		public static final String ZY_DK_CUSTRACKNAMEZID = "CustRackNameZid";
		public static final String ZY_DK_CUSTSTERMNOID = "CustStermNoId";
		public static final String ZY_DK_CUSTPORTLINK = "CustPortLink";
		public static final String ZY_DK_CUSTPORTNO = "CustPortNo";
		public static final String ZY_DK_OLDACCESSID = "OldAccessId";
		
		public static final String ZY_DK_CUSTPORTHORIZNO = "CustPortHorizNo";
		public static final String ZY_DK_CUSTEQPRESTYPEID = "CustEqpResTypeId";
		public static final String ZY_DK_CUSTEQPRESTYPENAME = "CustEqpResTypeName";
		public static final String ZY_DK_CUSTMDFID = "CustMdfID";
		public static final String ZY_DK_CUSTMDFTYPEID = "CustMdfTypeID";
		public static final String ZY_DK_CUSTPORTHORIZID = "CustPortHorizID";
		public static final String ZY_DK_CUSTPORTHORIZTYPEID = "CustPortHorizTypeID";
		public static final String ZY_DK_CUSTPORTTYPE = "CustPortType";
		
		public static final String ZY_DK_DIREID = "DireId";
		public static final String ZY_DK_DIRENAME = "DireName";
		
	//流程信息
		public static final String ZY_TACHENAME = "TacheName";
		public static final String ZY_SYSTEMNAME = "SystemName";
		public static final String ZY_BEGINDATE = "BeginDate";
		public static final String ZY_FINISHDATE = "FinishDate";
		public static final String ZY_TOTALTIME = "TotalTime";
	//材料
		public static final String ZY_MATERIALID = "MaterialId";
		public static final String ZY_MATERIALNAME = "MaterialName";
		public static final String ZY_MATERIALCLASS = "MaterialClass";
		public static final String ZY_SPEC = "Spec";
		public static final String ZY_UNIT = "Unit";
		public static final String ZY_PRICE = "Price";
		public static final String ZY_COMPOSEFLAG = "ComposeFlag";
		
}

