
/////////////////////////////////////////////////////////////////////////////////////////////
var PushMessage = ({

	"init" : function() {
		
		/*var pm_store_config = configJson["push_message_store"];
		pm_store_config["listeners"] = {
			beforeload: function(store) {
				if(store) {
					store.removeAll();
				}
				//alert("PM被触发了"+ store.data.length);
			},
			load: function(store){
				Ext.getCmp('push_message_grid').getSelectionModel().selectFirstRow();
			}
	    }; */

		//var push_message_store = new Ext.data.JsonStore(pm_store_config);

		var pm_column_config = new Ext.grid.ColumnModel(configJson["push_message_headers"]);
		pm_column_config.defaultSortable = true;
		
		/*var pm_PagingBar = new Ext.PagingToolbar(
			pageSize: 10,
			store: push_message_store,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
			/*items:[
				'-', {
				pressed: true,
				enableToggle:true,
				text: '刷新',
				cls: 'x-btn-text-icon details',
				toggleHandler: function(btn, pressed){
					var view = pushMessageGrid.getView();
					view.showPreview = pressed;
					view.refresh();
				}
			}]
		});*/
			
		var pm_grid_config = configJson["push_message_grid"];
		
		//pm_grid_config["bbar"] = pm_PagingBar;
		pm_grid_config["cm"] = pm_column_config;
		//pm_grid_config["store"] = pm_store_config;
		pm_grid_config["sm"] = new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {
				rowselect: function(sm, row, rec){
				}	
			}
		});
		
		//1. 创建Grid对象
		var pushMessageGrid  = new ZTESOFT.Grid(pm_grid_config);
		pushMessageGrid.store.baseParams = {"optype":"ALL", "pagin":"Y"};
		pushMessageGrid.store.on('load', function(){
			Ext.getCmp('push_message_grid').getSelectionModel().selectFirstRow();
		}); 
		pushMessageGrid.store.load({params:{start:0, limit:10}});
		
		//2. 创建Menu菜单
		PushMessage.initMenu();
		//3. 创建事件数据集
		var eventJson = PushMessage.combineEventData(pushMessageGrid["id"]);
		//4. 绑定事件
		PushMessage.bindEvent(pushMessageGrid["id"], eventJson);
		//5. 执行
		//push_message_store.load({params:{"optype":"ALL", "pagin":"Y", start:0, limit:10}});
		
		return pushMessageGrid;
	},

	//定义列表菜单
    "initMenu" : function() {
    	var pm_rightClick = new Ext.menu.Menu({
		    id:'pm_rightClick'
		});
		var pm_nullRightClick = new Ext.menu.Menu({
		    id:'pm_nullRightClick'
		});
    },
	
	//组合事件数据
	"combineEventData": function(comp_name) {
		//根据组件名称获取组件
		//var comp = Ext.getCmp(comp_name);

		var eventJson = {};
		if("push_message_grid" == comp_name) {
			//var comp = Ext.getCmp(comp_name);
			eventJson["rightClickFn"] = PushMessage.rightClickFn;
			eventJson["nullRightClickFn"]  = PushMessage.nullRightClickFn;
		}
		return eventJson;
	},

	//为组件绑定事件
	"bindEvent" : function(comp_name, eventJson) {

		if ("push_message_grid" == comp_name) {
			var comp = Ext.getCmp(comp_name);
			comp.selModel = new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					rowselect : function(sm, row, rec) {
						//qryFunData(rec.data.apkCode);
						var fields = ["messageTitle","messageStaffName", "messageClasses","messageType","messageContent", "remarks"];
						for(i=0;i < fields.length; i++) {
							Ext.getCmp("p_" + fields[i]).setValue(rec.data[fields[i]]);
						}
						//
						PushMessageQueue.queryByPushMessageId(rec.data["pushMessageId"]);
					}
				}
			});
			comp.store.removeAll();
			comp.store.load();
			comp.addListener('rowcontextmenu', eventJson["rightClickFn"]);
			comp.addListener('contextmenu', eventJson["nullRightClickFn"]);
		}
	},

	// 右键事件
	"rightClickFn" : function(compGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('pm_rightClick');

		rightClick.removeAll();

		rightClick.insert(i++,new Ext.menu.Item({ text: '新建推送消息' ,handler: function() {
			rightClick.hide();
			PushMessage.menuOpt("I");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '修改推送消息' ,handler: function() {
			rightClick.hide();
			//判断是否有推送队列，有则不允许进行修改
			var pmqGrid = Ext.getCmp('push_message_queue_grid');
			if(0 != pmqGrid.store.getCount()) {
				Ext.MessageBox.alert("操作提示", "该消息已有推送队列，不允许进行修改操作！");
				return;
			}
			PushMessage.menuOpt("U");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '删除推送消息' ,handler: function() {
			rightClick.hide();
			var pmqGrid = Ext.getCmp('push_message_queue_grid');
			//判断是否有推送队列，有则不允许删除
			if(0 != pmqGrid.store.getCount()) {
				Ext.MessageBox.alert("操作提示", "该消息已有推送队列，不允许进行删除操作！");
				return;
			}
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', PushMessage.doDelete);
		}}));


		compGrid.getSelectionModel().selectRow(rowIndex);
		e.preventDefault();
		rightClick.showAt(e.getXY());
	},
	
	//删除
	"doDelete" : function(scope){
		if (scope == 'no'){
			return ;
		}
		var selGridItem = Ext.getCmp('push_message_grid').getSelectionModel().getSelections();
				
		var dataObj = new Object();
		dataObj.pushMessageIds = selGridItem[0].data.pushMessageId;
		dataObj.optype = "D";
		var retMap = invokeAction("/pushmessage/OptPushMessageAction", dataObj);
		
		var resultStr = "删除失败！";
		if(retMap && "Y" == retMap.flag) {
			resultStr  = '删除成功！';
		}
			 				
		Ext.MessageBox.show({
           	msg: '系统正在提交数据……',
           	progressText: 'Saving...',
           	width:300,
           	wait:true,
           	waitConfig: {interval:100},
           	icon:'ext-mb-download'
       	});
        setTimeout(function(){
            Ext.MessageBox.hide();
            Ext.example.msg('',resultStr);
        }, 1000);
		
        Ext.getCmp('push_message_grid').store.removeAll();
        Ext.getCmp('push_message_grid').store.reload();
    },

	//空白区域的右键事件
	"nullRightClickFn" : function(e){ 
		//alert('nt');
		var nullRightClick = Ext.getCmp('pm_nullRightClick');
		nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '新建推送消息' ,handler: function() {
			nullRightClick.hide();
			PushMessage.menuOpt("I");
		}}));
		nullRightClick.showAt(e.getXY());
    },

    //右键菜单操作
    "menuOpt" : function(optype) {

		var optGrid = Ext.getCmp('push_message_grid');
    	var selGridItem = optGrid.getSelectionModel().getSelections();
		
		var info_fields_config = configJson["push_message_config_fields"];
		var info_buttons_config = configJson["push_message_config_buttons"];
		
		var btn_ok_calllback = function(eventName, scope, options) {
			PushMessage.doOptAction(infoPage, push_message_config_win, optype);
		};
		
		var btn_cancel_calllback = function(eventName, scope, options) {
			push_message_config_win.close();
		};
		
		info_buttons_config[0]["listeners"] = {"click":btn_ok_calllback};
		info_buttons_config[1]["listeners"] = {"click":btn_cancel_calllback};
	
		var info_page_config = {
			id:"push_message_config_panel",
			region: 'center',
			labelAlign: 'right',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign:'center',
	        labelWidth:80,
	        items:info_fields_config,
			buttons:info_buttons_config
		};

		var infoPage = new Ext.FormPanel(info_page_config);
		
		var pmcw_config = configJson["push_message_config_win"];
		pmcw_config["items"] = [infoPage];
		
		var push_message_config_win = new Ext.Window(pmcw_config);
		push_message_config_win.show(this);
					
		//创建人不允许修改
		//Ext.getCmp("m_messageStaffName").disabled = true;
		Ext.getCmp("m_messageStaffName").setValue(session1.staff.staffName);
		Ext.getCmp("m_messageStaffId").setValue(session1.staff.staffId);
		
		//若为新增，则默认			
					
		//若为修改，则附上原值
		if(optype == 'U'){	
			var val_fields = ["pushMessageId", "messageClasses", "messageType", "messageStaffId", "messageStaffName", "messageTitle", "remarks", "messageContent","messageUrl"];
			for(i = 0; i<val_fields.length; i++) {
				Ext.getCmp("m_" + val_fields[i]).setValue(selGridItem[0].data[val_fields[i]]);
			}
		}	
    },
	
	"doOptAction" : function(comp, win, optype) {

		//验证
		if(!comp.getForm().isValid()){
			//alert("验证不通过");
			return ;
		}
		 				
		var resultStr ='操作失败！';			   
		var url = "/pushmessage/OptPushMessageAction";
		var opt_fields = ["messageClasses", "messageType", "messageStaffId", "messageStaffName", "messageTitle", "remarks", "messageContent","messageUrl"];
		var dataJson = new Object();
		dataJson["optype"] = optype;	//设置操作类型
		
		
		if("I" == optype){	//新增
			resultStr = '新增成功！';
			for(i=0; i<opt_fields.length; i++) {
				dataJson[opt_fields[i]]= Ext.getCmp("m_" + opt_fields[i]).getValue();
			}	
		}else if("U" == optype) {	//修改
			resultStr = '修改成功！';
			opt_fields.push("pushMessageId");	//添加获取ID值
			for(i=0; i<opt_fields.length; i++) {
				dataJson[opt_fields[i]]= Ext.getCmp("m_" + opt_fields[i]).getValue();
			}
			
		}
		//发出请求
		var retMap = invokeAction(url, dataJson);
		
		if(retMap && retMap.flag=='Y') {
			//刷新数据集
			var optGrid = Ext.getCmp('push_message_grid');
			optGrid.store.removeAll();
			optGrid.store.reload();
			//关闭窗口
			win.close();
		}
		
		//显示操作提示信息
		Ext.MessageBox.show({
			msg: '系统正在提交数据……',
			progressText: 'Saving...',
			width:300,
			wait:true,
			waitConfig: {interval:100},
			icon:'ext-mb-download'
		});
		setTimeout(function(){
			Ext.MessageBox.hide();
			Ext.example.msg('',resultStr);
		}, 1000);		
	}
});

/////////////////////////////////////////////////////////////////////////////////////////////
var PushMessageDetails = ({

	"init" : function() {
		var pmd_config = configJson["push_message_details_panel"];

		var push_message_details_panel = new Ext.FormPanel(pmd_config);

		return push_message_details_panel;
	}

});

/////////////////////////////////////////////////////////////////////////////////////////////
var PushMessageQueue = ({

	"init" : function() {
		//var STORE_NAME = "push_message_queue_store";
		var HEASERS_NAME = "push_message_queue_headers";
		var GRID_NAME = "push_message_queue_grid";
		
		/*var pmq_store_config = configJson[STORE_NAME];
		pmq_store_config["listeners"] = {
			beforeload: function(store) {
//				/alert('PMQ我被触发了');
			},
			load: function(store){
				Ext.getCmp(GRID_NAME).getSelectionModel().selectFirstRow();
			}
	    }; 

		var push_message_queue_store = new Ext.data.JsonStore(pmq_store_config);*/ 

		var pmq_column_config = new Ext.grid.ColumnModel(configJson[HEASERS_NAME]);
		pmq_column_config.defaultSortable = true;

		/*var pmq_PagingBar = new Ext.PagingToolbar({
			pageSize: 10,
			store: push_message_queue_store,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
			/*items:[
				'-', {
				pressed: true,
				enableToggle:true,
				text: '刷新',
				cls: 'x-btn-text-icon details',
				toggleHandler: function(btn, pressed){
					var view = pushMessageGrid.getView();
					view.showPreview = pressed;
					view.refresh();
				}
			}]
		});*/
		
		var pmq_grid_config = configJson[GRID_NAME];
		//pmq_grid_config["bbar"] = pmq_PagingBar;
		pmq_grid_config["cm"] = pmq_column_config;
		//pmq_grid_config["store"] = pmq_store_config;
		pmq_grid_config["sm"] = new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {
				rowselect: function(sm, row, rec){
					//
				}	
			}
		});

		//1. 创建Grid对象
		var pushMessageQueueGrid  = new ZTESOFT.Grid(pmq_grid_config);
		pushMessageQueueGrid.store.baseParams = {"optype":"ALL", "pagin":"Y"};
		pushMessageQueueGrid.store.load({params:{start:0, limit:10}});

		
		//2. 创建Menu菜单
		PushMessageQueue.initMenu();
		//3. 创建事件数据集
		var eventJson = PushMessageQueue.combineEventData(pushMessageQueueGrid["id"]);
		//4. 绑定事件
		PushMessageQueue.bindEvent(pushMessageQueueGrid["id"], eventJson);
		//5
		//push_message_queue_store.load({params:{"optype":"ALL", "pagin":"Y", start:0, limit:10}});

		return pushMessageQueueGrid;
	},
	
	//定义列表菜单
    "initMenu" : function() {
    	var pmq_rightClick = new Ext.menu.Menu({
		    id:'pmq_rightClick'
		});
		var pmq_nullRightClick = new Ext.menu.Menu({
		    id:'pmq_nullRightClick'
		});
    },
	
	//组合事件数据
	"combineEventData": function(comp_name) {
		//根据组件名称获取组件
		//var comp = Ext.getCmp(comp_name);

		var eventJson = {};
		if("push_message_queue_grid" == comp_name) {
			eventJson["rightClickFn"] = PushMessageQueue.rightClickFn;
			eventJson["nullRightClickFn"]  = PushMessageQueue.nullRightClickFn;
		}
		return eventJson;
	},

	//为组件绑定事件
	"bindEvent" : function(comp_name, eventJson) {

		if ("push_message_queue_grid" == comp_name) {
			var comp = Ext.getCmp(comp_name);
			comp.selModel = new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					rowselect : function(sm, row, rec) {
						//TODO
					}
				}
			});
			comp.store.removeAll();
			comp.store.load();
			comp.addListener('rowcontextmenu', eventJson["rightClickFn"]);
			comp.addListener('contextmenu', eventJson["nullRightClickFn"]);
		}
	},

	// 右键事件
	"rightClickFn" : function(compGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('pmq_rightClick');

		rightClick.removeAll();

		rightClick.insert(i++,new Ext.menu.Item({ text: '新建推送队列' ,handler: function() {
			rightClick.hide();
			PushMessageQueue.menuOpt("I");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '修改推送队列' ,handler: function() {
			rightClick.hide();			
			//判断是否有推送队列，有则不允许删除
			var selGridItem = Ext.getCmp('push_message_queue_grid').getSelectionModel().getSelections();
			var status = selGridItem[0]["data"]["messageQueueStatus"];
			if(0 == status) {
				Ext.MessageBox.alert("操作提示", "该消息已推送，不允许修改！");
				return;
			}
			PushMessageQueue.menuOpt("U");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '删除推送队列' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', PushMessageQueue.doDelete);
		}}));


		compGrid.getSelectionModel().selectRow(rowIndex);
		e.preventDefault();
		rightClick.showAt(e.getXY());
	},
	
	//删除
	"doDelete" : function(scope){
		if (scope == 'no'){
			return ;
		}
		var selGridItem = Ext.getCmp('push_message_queue_grid').getSelectionModel().getSelections();
		
		var dataObj = new Object();
		dataObj.pushMessageQueueIds = selGridItem[0].data.pushMessageQueueId;
		dataObj.optype = "D";
		var retMap = invokeAction("/pushmessage/OptPushMessageQueueAction", dataObj);
		
		var resultStr = "删除失败！";
		if(retMap && "Y" == retMap.flag) {
			resultStr  = '删除成功！';
		}
			 				
		Ext.MessageBox.show({
           	msg: '系统正在提交数据……',
           	progressText: 'Saving...',
           	width:300,
           	wait:true,
           	waitConfig: {interval:100},
           	icon:'ext-mb-download'
       	});
        setTimeout(function(){
            Ext.MessageBox.hide();
            Ext.example.msg('',resultStr);
        }, 1000);
		
        Ext.getCmp('push_message_queue_grid').store.removeAll();
        Ext.getCmp('push_message_queue_grid').store.reload();
    },

	//空白区域的右键事件
	"nullRightClickFn" : function(e){ 
		//alert('nt');
		var nullRightClick = Ext.getCmp('pmq_nullRightClick');
		nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '新建推送队列' ,handler: function() {
			nullRightClick.hide();
			PushMessageQueue.menuOpt("I");
		}}));
		nullRightClick.showAt(e.getXY());
    },

    //右键菜单操作
    "menuOpt" : function(optype) {

		var pmGrid = Ext.getCmp('push_message_grid');
		var selPmGridItem = pmGrid.getSelectionModel().getSelections();
		var optGrid = Ext.getCmp('push_message_queue_grid');
    	var selGridItem = optGrid.getSelectionModel().getSelections();
		
		var info_fields_config = configJson["push_message_queue_config_fields"];
		var info_buttons_config = configJson["push_message_queue_config_buttons"];
		
		/*info_buttons_config[0]["listeners"] = {
				'click' : Ext.MessageBox.confirm('提示', '确定要立即推送？', function(btn){
					if('no' == btn) {
						return;
					}
				})
		}; */
		
		//0 : 立即发送；1 : 不发送
		var btn_ok_send_calllback = function(eventName, scope, options) {
			PushMessageQueue.doOptAction(infoPage, push_message_queue_config_win, optype, 0);
		};
		
		var btn_ok_unsend_calllback = function(eventName, scope, options) {
			PushMessageQueue.doOptAction(infoPage, push_message_queue_config_win, optype, 1);
		};
		
		var btn_cancel_calllback = function(eventName, scope, options) {
			push_message_queue_config_win.close();
		};
		
		info_buttons_config[0]["listeners"] = {"click":btn_ok_send_calllback};
		info_buttons_config[1]["listeners"] = {"click":btn_ok_unsend_calllback};
		info_buttons_config[2]["listeners"] = {"click":btn_cancel_calllback};
	
		var info_page_config = {
			id:"push_message_queue_config_panel",
			region: 'center',
			labelAlign: 'right',
		 	frame:true,
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
	        bodyStyle:'padding:5px',
	        buttonAlign:'center',
	        labelWidth:80,
	        items:info_fields_config,
			buttons:info_buttons_config
		};

		var infoPage = new Ext.FormPanel(info_page_config);
		
		var pmqcw_config = configJson["push_message_queue_config_win"];
		pmqcw_config["items"] = [infoPage];
		
		var push_message_queue_config_win = new Ext.Window(pmqcw_config);
		push_message_queue_config_win.show(this);
		
		//创建人默认为当前的
		Ext.getCmp("k_senderId").setValue(session1.staff.staffId);
		Ext.getCmp("k_senderName").setValue(session1.staff.staffName);
	
		//其它的信息
		Ext.getCmp("k_messageTitle").setValue(selPmGridItem[0].data["messageTitle"]);
		Ext.getCmp("k_messageContent").setValue(selPmGridItem[0].data["messageContent"]);		
		Ext.getCmp("k_messageUrl").setValue(selPmGridItem[0].data["messageUrl"]);	
		
		if(optype == "I") {
			Ext.getCmp("k_pushMessageId").setValue(selPmGridItem[0].data["pushMessageId"]);
		}
		
		//若为修改，则附上原值
		if(optype == 'U'){	
			var val_fields =["pushMessageQueueId", "pushMessageId", "receiverId", "receiverName", "messageQueueStatus","remarks"];
			for(i = 0; i<val_fields.length; i++) {
				Ext.getCmp("k_" + val_fields[i]).setValue(selGridItem[0].data[val_fields[i]]);
			}
			Ext.getCmp("j_pn_user_name").setValue(selGridItem[0].data["receiverName"]);
		}	
    },
	
	"doOptAction" : function(comp, win, optype, sendtype) {

		//验证
		if(!comp.getForm().isValid()){
			//alert("验证不通过");
			return ;
		}
		
		/*if(0 == sendtype) {	//若是立即发送，则提示
			Ext.MessageBox.confirm('提示', '确定要立即推送？', function(btn){
				if('no' == btn) {
					return;
				}
			});
		} */
		
		var resultStr ='操作失败！';			   
		var url = "/pushmessage/OptPushMessageQueueAction";
		var opt_fields = ["pushMessageId", "receiverId", "receiverName", "receiverTokens", "senderId", "senderName",  "remarks"];
		var dataJson = new Object();
		dataJson["optype"] = optype;	//设置操作类型
		dataJson["messageQueueStatus"] = sendtype;
		dataJson["pushTitle"] = 	Ext.getCmp("k_messageTitle").getValue();
		dataJson["pushContent"] = Ext.getCmp("k_messageContent").getValue();
		dataJson["pushUrl"] = Ext.getCmp("k_messageUrl").getValue();
		
		if("I" == optype){	//新增
			resultStr = '新增成功！';
			for(i=0; i<opt_fields.length; i++) {
				dataJson[opt_fields[i]]= Ext.getCmp("k_" + opt_fields[i]).getValue();
			}	
		}else if("U" == optype) {	//修改
			resultStr = '修改成功！';
			opt_fields.push("pushMessageQueueId");	//添加获取ID值
			for(i=0; i<opt_fields.length; i++) {
				dataJson[opt_fields[i]]= Ext.getCmp("k_" + opt_fields[i]).getValue();
			}
			
		}
		//发出请求
		var retMap = invokeAction(url, dataJson);
		
		if(retMap && retMap.flag=='Y') {
			//刷新数据集
			var optGrid = Ext.getCmp('push_message_queue_grid');
			optGrid.store.removeAll();
			optGrid.store.reload();
			//关闭窗口
			win.close();
		}
		
		//显示操作提示信息
		Ext.MessageBox.show({
			msg: '系统正在提交数据……',
			progressText: 'Saving...',
			width:300,
			wait:true,
			waitConfig: {interval:100},
			icon:'ext-mb-download'
		});
		setTimeout(function(){
			Ext.MessageBox.hide();
			Ext.example.msg('',resultStr);
		}, 1000);		
	},
	
	////////////
	"queryByPushMessageId" : function(pushMessageId) {
		var GRID_NAME = "push_message_queue_grid";
		
		Ext.getCmp(GRID_NAME).store.on('beforeload',
			function(store){ 
				if(Ext.getCmp(GRID_NAME).store.lastOptions != null){
					Ext.getCmp(GRID_NAME).store.baseParams = {"pushMessageId":pushMessageId,"optype":"ALL", "pagin":"Y"};
				}
			}
		)
		Ext.getCmp(GRID_NAME).store.removeAll() ;
		Ext.getCmp(GRID_NAME).store.load({params:{"start":0, "limit":10}});
	},
	
	"queryOnlineUser": function() {
		 var returnData =  window.showModalDialog("/MOBILE/pushmessage/mobilePnUserMgr.jsp",null,"dialogWidth=600px;dialogHeight=400px");
		 
		 if(returnData && "Y" == returnData["optype"]) {
		
			 var dataJson = {
				 "receiverId": returnData["result"][0]["data"]["pnStaffId"],
				 "receiverName"  : returnData["result"][0]["data"]["pnStaffName"],
				 "receiverTokens" : returnData["result"][0]["data"]["pnUserName"]
			 }
			 
			 
			 var f = ["receiverId", "receiverName","receiverTokens"];
			 for(i=0; i<f.length; i++) {
				 //alert(dataJson[f[i]]);
				 Ext.getCmp("k_" + f[i]).setValue(dataJson[f[i]]);
			 }
			 Ext.getCmp("j_pn_user_name").setValue(dataJson["receiverName"]);
			
		 } else if(returnData && "C" == returnData["optype"]) {
			 var f = ["receiverId", "receiverName","receiverTokens"];
			 for(i=0; i<f.length; i++) {
				 Ext.getCmp("k_" + f[i]).setValue("");
			 }
			 Ext.getCmp("j_pn_user_name").setValue("");
		 }

	}, 
	
	"renderPushButton" : function() {
		if(!window.confirm("确定要推送？")) {
			return;
		}

		var selGridItem0 = Ext.getCmp('push_message_grid').getSelectionModel().getSelections();
		var selGridItem1 = Ext.getCmp('push_message_queue_grid').getSelectionModel().getSelections();
		
		var url = "/pushmessage/OptPushMessageQueueAction";
		var dataJson = {
			"pushTitle": selGridItem0[0]["data"]["messageTitle"],
			"pushContent": selGridItem0[0]["data"]["messageContent"],
			"pushUrl": selGridItem0[0]["data"]["messageUrl"],
			"pushMessageQueueId" : selGridItem1[0]["data"]["pushMessageQueueId"],
			//"receiverTokens" : selGridItem1[0]["data"]["receiverTokens"],
			"receiverId" : selGridItem1[0]["data"]["receiverId"],
			"optype" : "P"
		};
		//发出请求
		var retMap = invokeAction(url, dataJson);
		
		if(!retMap) {
			Ext.Msg.alert('操作提示', "系统内部错误！");
		} else {
			if("N" == retMap.flag) {
				Ext.Msg.alert('操作提示', retMap.reason);
			} else {
				PushMessageQueue.queryByPushMessageId(selGridItem0[0]["data"]["pushMessageId"]);
				Ext.Msg.alert('操作提示', "消息已推送！");
				
			}
		}
		
	}

});



/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
var PushMessageGridsPanel = ({
	"init": function() {
		var pm_grid = PushMessage.init();
		var pmq_grid = PushMessageQueue.init();
		var pmd_panel = PushMessageDetails.init();
	
		//读取配置
		//var north_panel_config = configJson["grids_panel_config"];		
		//north_panel_config["items"] = [pm_grid];
		//创建上半部的面板
		//var north_panel = new Ext.Panel(north_panel_config);
		
		var south_tabs_config = configJson["grids_panel_tabs"];
		south_tabs_config["items"] = [pmq_grid,pmd_panel];
		//创建下半部的面板
		var soutch_tabs_panel =  new Ext.TabPanel(south_tabs_config);

		var viewport = new Ext.Viewport({
			el:'push_msg_vp',
			layout: 'border',
			items:[pm_grid,soutch_tabs_panel]
		});
	}
});
