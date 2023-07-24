
/////////////////////////////////////////////////////////////////////////////////////////////
var MobilePnUser = ({

	"init" : function() {
		var pu_store_config = configPuJson["pn_user_store"];
		/*pu_store_config["listeners"] = {
			load: function(store){
				//Ext.getCmp('pn_user_grid').getSelectionModel().selectFirstRow();
			}
	    }; */

		var pn_user_store = new Ext.data.JsonStore(pu_store_config);

		/*var sm = new Ext.grid.ColumnModel({
			//checkOnly: true,
		    singleSelect: true,
			listeners: {
				rowselect: function(sm, row, rec){
                    //var selItem = Ext.getCmp('pn_user_grid').getSelectionModel().getSelections(); 
			    	//window.returnValue = sm.getSelections();
                   // window.close();
				}
			}
		}); */
		
		var pn_user_headers_config = configPuJson["pn_user_headers"];
//		pn_user_headers_config.push(sm);
		var pu_column_config = new Ext.grid.ColumnModel(pn_user_headers_config);
		//pu_column_config.defaultSortable = true;
		
		//构建分页组件
		/*var pagingBar = new Ext.PagingToolbar({
			pageSize: 10,
			store: pn_user_store,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
		}); */

		var pu_grid_config = configPuJson["pn_user_grid"];
		pu_grid_config["cm"] = pu_column_config;
		pu_grid_config["store"] = pu_store_config;
		//pu_grid_config["sm"] = sm;
		pu_grid_config["listeners"] =  {
            "rowdblclick": function() {
            	var selItem = Ext.getCmp('pn_user_grid').getSelectionModel().getSelections(); 
            	window.returnValue = {"result":selItem, "optype":"Y"};
                window.close();
            }
        };

		//1. 创建Grid对象
		var pnUserGrid  = new ZTESOFT.Grid(pu_grid_config);
		//2. 创建Menu菜单
		//MobilePnUser.initMenu();
		//3. 创建事件数据集
		//var eventJson = MobilePnUser.combineEventData(pushMessageGrid["id"]);
		//4. 绑定事件
		//MobilePnUser.bindEvent(pushMessageGrid["id"], eventJson);
		//5. 
//		pnUserGrid.store.removeAll() ;
		pnUserGrid.store.load();
		
		return pnUserGrid;
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
		if("pn_user_grid" == comp_name) {
			//var comp = Ext.getCmp(comp_name);
			eventJson["rightClickFn"] = MobilePnUser.rightClickFn;
			eventJson["nullRightClickFn"]  = MobilePnUser.nullRightClickFn;
		}
		return eventJson;
	},

	//为组件绑定事件
	"bindEvent" : function(comp_name, eventJson) {

		if ("pn_user_grid" == comp_name) {
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
						MobilePnUserQueue.queryByMobilePnUserId(rec.data["pushMessageId"]);
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
			MobilePnUser.menuOpt("I");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '修改推送消息' ,handler: function() {
			rightClick.hide();
			MobilePnUser.menuOpt("U");
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '删除推送消息' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', MobilePnUser.doDelete);
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
		var selGridItem = Ext.getCmp('pn_user_grid').getSelectionModel().getSelections();

		var dataObj = new Object();
		dataObj.pushMessageIds = selGridItem[0].data.pushMessageId;
		dataObj.optype = "D";
		var retMap = invokeAction("/pushmessage/OptMobilePnUserAction", dataObj);

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

        Ext.getCmp('pn_user_grid').store.removeAll();
        Ext.getCmp('pn_user_grid').store.reload();
    },

	//空白区域的右键事件
	"nullRightClickFn" : function(e){
		var nullRightClick = Ext.getCmp('pm_nullRightClick');
		nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '新建推送消息' ,handler: function() {
			nullRightClick.hide();
			MobilePnUser.menuOpt("I");
		}}));
		nullRightClick.showAt(e.getXY());
    },

    //右键菜单操作
    "menuOpt" : function(optype) {

		var optGrid = Ext.getCmp('pn_user_grid');
    	var selGridItem = optGrid.getSelectionModel().getSelections();

		var info_fields_config = configPuJson["pn_user_config_fields"];
		var info_buttons_config = configPuJson["pn_user_config_buttons"];

		var btn_ok_calllback = function(eventName, scope, options) {
			MobilePnUser.doOptAction(infoPage, pn_user_config_win, optype);
		};

		var btn_cancel_calllback = function(eventName, scope, options) {
			pn_user_config_win.close();
		};

		info_buttons_config[0]["listeners"] = {"click":btn_ok_calllback};
		info_buttons_config[1]["listeners"] = {"click":btn_cancel_calllback};

		var info_page_config = {
			id:"pn_user_config_panel",
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

		var pmcw_config = configPuJson["pn_user_config_win"];
		pmcw_config["items"] = [infoPage];

		var pn_user_config_win = new Ext.Window(pmcw_config);
		pn_user_config_win.show(this);

		//创建人不允许修改
		//Ext.getCmp("m_messageStaffName").disabled = true;
		Ext.getCmp("m_messageStaffName").setValue(session1.staff.staffName);
		Ext.getCmp("m_messageStaffId").setValue(session1.staff.staffId);

		//若为新增，则默认

		//若为修改，则附上原值
		if(optype == 'U'){
			var val_fields = ["pushMessageId", "messageClasses", "messageType", "messageStaffId", "messageStaffName", "messageTitle", "remarks", "messageContent"];
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
		var url = "/pushmessage/OptMobilePnUserAction";
		var opt_fields = ["messageClasses", "messageType", "messageStaffId", "messageStaffName", "messageTitle", "remarks", "messageContent"];
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
			var optGrid = Ext.getCmp('pn_user_grid');
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

var OptButtonPanel = ({
	
	"init" : function() {
		var btnPanel = new Ext.FormPanel(configPuJson["pn_user_optbutton_panel"]);
		return btnPanel;	
	}
	
});


var PnUserGridsPanel = ({
	
	"init": function() {
		var puGrid = MobilePnUser.init();
		var optButtonPanel = OptButtonPanel.init();
		//puGrid.load();

		var earthviewport = new Ext.Viewport({
			region: 'center',
			layout: 'border',
			items:[puGrid,optButtonPanel]
		});
	    
	    var viewport = new Ext.Viewport({
			el:'pn_user_vp',
			layout: 'border',
			items:[earthviewport]
		});
		

	}
});