<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>连接监控</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/connectMonitor.js"></script>
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>
						
	</body>
</html>

<script language="JScript">
/**	by liu.zhi **/

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var cOper = new ConnectOper();
var callRowCount = 5; 

var oper = new Oper(); 

Ext.apply(Ext.form.VTypes, {
    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },passwordText: '确认密码与密码不一致！',
    
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！',
    
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } 
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    }
});

Ext.onReady(function(){
		var mainPanel = oper.initMainPanel();
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			items:[mainPanel]
	});
});

function Oper(){

	

	this.initMainPanel = function(){
         
         var qryPanel = this.initQryPanel();        
		 var monitorGrid = this.initMonitorGrid();		 
		 this.QryMonitorGrid();
		 this.initGridMenu();  
		 this.initGridEvent();
		 		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '连接监控',
			region: 'center',
			layout: 'border',
			items:[monitorGrid]
		}); 
		
				
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[monitorGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "monitorGrid":
						{
							oper.QryMonitorGrid();
							break ;
						}						
					}	
	    		}
	    }
		});
	
		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,tabs]
	 	});
	 	return mainPanel;
	}
	
	this.initConnectStore = function(){
	    
	    var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/CONNECT_STATE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
	    return sysStore;
	}
	
    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },		    
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',hidden:true },
		    {header:'用户',dataIndex:'username',width:swidth*0.05},		    		    
		    {header:'连接状态',dataIndex:'connectState',width:swidth*0.13},
		    {header:'是否限制连接',dataIndex:'connectLimit',width:swidth*0.13},
		    {header:'流量阀值(M)',dataIndex:'userFlowLimit',width:swidth*0.13},
		    {header:'用户权限',dataIndex:'userConnPriv',width:swidth*0.13,renderer:function (val){ if (val == 1) {return "普通"} else if (val == 2) {return "高级"}}},
		    {header:'创建时间',dataIndex:'createTime',width:swidth*0.13},
		    {header:'最新访问时间',dataIndex:'lastVisitTime',width:swidth*0.1},
		    {header:'会话标识',dataIndex:'sessionId',hidden:true}
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'连接监控',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QryConnectMonitorAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return monitorGrid;	
	}
	
	this.QryMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var connectState = Ext.getCmp('connectState').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {connectState:connectState,username:username,beginDate:beginDate,endDate:endDate,flag:1};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initQryPanel = function(){
	    var connectStore = this.initConnectStore();
	    
	    var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
		region: 'north',
		frame:true,
		title: '查询条件',
	    bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true,
        height:Ext.getBody().getSize().height*0.23,
        width:Ext.getBody().getSize().width*0.8,
        buttons:[{
            text: '查询',onClick:doQry},{text: '重置',onClick:reset
        }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户',
                    name: 'username',
                    id: 'username',
                    anchor:'95%'
                },{
                    xtype:'datefield',
                    fieldLabel: '开始日期',
                    name: 'beginDate',
                    id: 'beginDate',
                    format :'Y-m-d',
                    anchor:'95%'              
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    fieldLabel: '连接状态',
                    xtype: 'combo',
                    name: 'connectState',
	                id: 'connectState',
	                valueField: 'mcode',
	                displayField: 'mname',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:connectStore
                },{
                    xtype:'datefield',
                    fieldLabel: '结束日期',
                    name: 'endDate',
                    id: 'endDate',
                    format :'Y-m-d',
                    anchor:'95%'
                }]
            }]
        }]
	});
	   
       return qryPanel;
	}
	
	function doQry(){
	
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "monitorGrid":
			{
				oper.QryMonitorGrid();
				break ;
			}
			
		}	
	    

	}

	function reset(){
		Ext.getCmp('username').setValue('');
		Ext.getCmp('connectState').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}
	
	this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});

    }
	
	//定义连接状态事件
	this.initGridEvent = function(){
		Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load();
		Ext.getCmp('monitorGrid').addListener('rowcontextmenu', oper.rightClickFn);

	}
	
	//连接状态右键菜单
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '限制用户接入' ,handler: function() {
			rightClick.hide();
			oper.limitCust();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '恢复用户接入' ,handler: function() {
			rightClick.hide();
			oper.recoverCust();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '调整用户权限' ,handler: function() {
			rightClick.hide();
			oper.adjustUserConnPriv();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '调整流量阀值' ,handler: function() {
			rightClick.hide();
			oper.adjustUserFlowLimit();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '限制连接数' ,handler: function() {
			rightClick.hide();
			oper.adjustUserConnLimit();
		}}));
					
		Ext.getCmp('monitorGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
    
    this.limitCust = function(){
        var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
    	var objq = new Object();
    	objq.flag = 1;
        objq.connectLimit = 1;
        objq.username =  selGridItem[0].data.username;
        var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
        oper.QryMonitorGrid();
    }
    
    this.recoverCust = function(){
    	var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
    	var objq = new Object();
    	objq.flag = 1;
        objq.connectLimit = 0;
        objq.username =  selGridItem[0].data.username;
        var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
        oper.QryMonitorGrid();
    }
    
    this.adjustUserConnPriv  = function(){
        var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
	    var username  = selGridItem[0].data.username;
	    var userConnPriv = selGridItem[0].data.userConnPriv;
        cOper.showPrivModuleInfoPage(username,userConnPriv);
    }
		
	this.adjustUserFlowLimit  = function(){
	    var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
	    var username  = selGridItem[0].data.username;
	    var userFlowLimit  = selGridItem[0].data.userFlowLimit;
        cOper.showFlowModuleInfoPage(username,userFlowLimit);
    }	
    
    this.adjustUserConnLimit  = function(){
	    var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
	    var username  = selGridItem[0].data.username;
        cOper.showConnModuleInfoPage(username);
    }	
		
}


</script>