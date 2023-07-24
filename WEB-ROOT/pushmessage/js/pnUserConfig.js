;(function(w){

var MESSAGE_CLASSES = {
	"0" : "应用升级推送",
	"1" : "预警信息推送",
	"2" : "公告消息推送"
};
	
var MESSAGE_CLASSES_LIST = {
	fields:['id','value'], 
	data: [
		[0,'应用升级推送'],
		[1,'预警信息推送'],
		[2,'公告消息推送']
	]
};
	
var MESSAGE_TYPE =  {
	"0" : "立即发送"
};

var MESSAGE_TYPE_LIST = {
	fields:['id','value'], 
	data: [
		[0,'立即推送']
	]
};

var USER_IS_ONLINE = {
		"1" : "是",
		"0" : "否"
};

var USER_IS_ONLINE_LIST = {
		fields:['id','value'], 
		data: [
			[0,'是'],
			[1,'否']
		]
};

var MESSAGE_QUEUE_STATUS = {
	"0": "未推送",
	"1": "已推送",
	"2": "推送中",
	"3": "已取消"
};


var MESSAGE_QUEUE_STATUS_LIST = {
	fields:['id','value'], 
	data: [
		[0,"未推送"],
		[1,"已推送"],
		[2,"推送中"],
		[3,"已取消"]
	]
};

var configPuJson = {

		"pn_user_store" : {
			"id": "pn_user_store",
    		"root": "Body",
    		"totalProperty": "totalCount",
    		//"remoteSort": true,
			"baseParams": {"optype": "ALL","pagin":"N"}, 
			"proxy": new Ext.data.HttpProxy({url: '/MOBILE/ExtServlet?actionName=system/SelMobilePnUserAction'}),
    		"fields": ["pnUserId", "pnUserName", "pnUserPassword", 
				"pnCreateDate", "pnUpdateDate", "pnPlatformType", 
				"pnPhoneNo", "pnOnline", "pnStaffId", 
				"pnStaffName", "pnAccount", "pnImsi"
			]
	   	},
		
		//推送列表显示字段
		"pn_user_headers": [
			{"header":"ID","dataIndex":"pnUserId",width:100,hidden:true},  
			{"header":"令牌用户","dataIndex":"pnUserName",width:100,hidden:true},  
			{"header":"令牌字串","dataIndex":"pnUserPassword",width:100},  
			{"header":"创建时间","dataIndex":"pnCreateDate",width:100,hidden:true},  
			{"header":"更新时间","dataIndex":"pnUpdateDate",width:100,hidden:true},  
			{"header":"系统平台","dataIndex":"pnPlatformType",width:100,hidden:true},  
			{"header":"手机号码","dataIndex":"pnPhoneNo",width:100},  
			{"header":"是否在线","dataIndex":"pnOnline",width:100, renderer:function(v){return USER_IS_ONLINE[v];}},  
			{"header":"员工ID","dataIndex":"pnStaffId",width:100,hidden:true},  
			{"header":"人员名称","dataIndex":"pnStaffName",width:100},  
			{"header":"用户名","dataIndex":"pnAccount",width:100},  
			{"header":"IMSI码","dataIndex":"pnImsi",width:100} 
		], 
		
		"pn_user_grid" :  {
			id:"pn_user_grid",
			region:"center",
			width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.9,
		    title:"在线人员列表",
		    trackMouseOver:false,
		    autoScroll: true,
		    loadMask: true,
		    stripeRows: true
		},
		
		"pn_user_optbutton_panel" :{
			id:'pn_user_optbutton_panel',
			region: 'south',
			border:false,
			buttonAlign: 'center',
			collapsible:false, 
			height:Ext.getBody().getSize().height*0.9,
			buttons: [{
					text: '确定',
					id:'submitBtn',
					listeners:{"click":function(){
						var selItem = Ext.getCmp('pn_user_grid').getSelectionModel().getSelections(); 
						window.returnValue = {"result":selItem, "optype":"Y"};
						window.close();
					}}
				}, {
					text: '清空',
					id:'clearBtn',
					listeners:{"click":function(){
						window.returnValue = {"result":{}, "optype":"C"};
						window.close();
					}}
				}, {
					text: '取消',
					 id:'cancelBtn',
					 listeners:{"click":function(){
						window.returnValue = {"result":{}, "optype":"N"};
						window.close();
					}}
				}]
		}
};
	w.configPuJson = configPuJson;
	
})(window);
