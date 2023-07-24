/*
 * Developed by fang.li 2011-04
 * 调度方案管理
 */
var gridRowCount = 8 ;
 
function RuleListMng(){

	this.showScheduleInfo = function(operator){
		var gridsPanel = this.initGridRules();
		var configWin = new Ext.Window({
       		id: 'configWin',
            closable: true,
            width: swidth*0.6,
            height: sheight*0.5,
            layout: 'border',
            plain: true,
            items: [gridsPanel],
            buttonAlign: 'center',
            buttons: [{
	            text: '确定',
	            onClick: function(){
	            	ruleListMng.commit();
	            }
	        },{
	            text: '关闭',
	            onClick: function(){
	            	configWin.close();
	            }
	        }]
        });
    	configWin.show();
	}
	
	this.initGridRules = function(){
		var ruleSM = new Ext.grid.CheckboxSelectionModel();
		
		var column = new Ext.grid.ColumnModel([ruleSM,
		    {header:'规则编号',dataIndex:'etlRuleId',hidden:true },
		    {header:'规则名称',dataIndex:'etlRuleName',width:swidth*0.2},
		    {header:'规则类型',dataIndex:'etlTypeName',width:swidth*0.17}
		    //{header:'源数据源',dataIndex:'sourceDsName',width:swidth*0.1},
		    //{header:'目标数据源',dataIndex:'targetDsName',width:swidth*0.1}
		]); 
		
		var gridRules = new ZTESOFT.Grid({
	    	id:'gridRules',
	    	title : '可添加规则列表',
	    	region: 'center',
	      	width:10,
	        pageSize: gridRowCount,
	        cm:column,
	        sm: ruleSM,
	        paging:true,
			url:'/MOBILE/ExtServlet?actionName=etl/QryOutSchRlsAction'
		});
		gridRules.store.load({params:{start:0, limit:gridRowCount}});
		return gridRules;
	}
	
	//提交数据
	this.commit = function(){
		var etlRuleId = new Array();
		
		var selSch = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections() ;
		
		if(selSch.length == 0){
			Ext.MessageBox.show({
	            title: '提示',
	            msg: '请选择调度方案 ！',
	            buttons: Ext.MessageBox.OK,
	            width:250,
	            icon: Ext.MessageBox.ERROR
	       	});
			return ;
		}
		var selRules = Ext.getCmp('gridRules').getSelectionModel().getSelections();
		var scheduleId = Ext.getCmp('scheduleGrid').getSelectionModel().getSelections()[0].data.scheduleId ;
		
		for(var i = 0 ;i < selRules.length ;i++){
			etlRuleId.push(selRules[i].data.etlRuleId);
		}
		
		if(etlRuleId.length > 0){
			var obj = new Object();
			obj.scheduleId = scheduleId;
			obj.etlRuleId = etlRuleId.join();
			obj.state = "10A";
			obj.createDate = new Date();
			obj.operManId = session1.staff.staffId;
			obj.operManName = session1.staff.staffName	
			var objId = callRemoteFunctionNoTrans("com.ztesoft.mobile.etl.service.ScheduleSequMangerClient", "addDataSource", obj);
		
			var reStr = '' ;
			if(objId){
				reStr = '保存配置成功！' ; 
				Ext.getCmp("configWin").close();
			}else{
				reStr = '保存配置失败！' ;
			}
			
			Ext.getCmp('ruleGrid').store.removeAll();
			Ext.getCmp('ruleGrid').store.load({params:{start:0, limit:gridRowCount}});
			
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
	            Ext.example.msg('',reStr);
	        }, 1000);
		}
	}
}