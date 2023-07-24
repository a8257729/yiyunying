<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>异常监控</title>
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
			title: '异常监控',
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
	
	this.initServeStore = function(){
	     var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: ['serviceId', 'serviceName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction'
	        }),
	        baseParams:{flag:1}
	        
	    });

	    return sysStore;
	}
	
    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },		    
		    {header:'异常类型ID',dataIndex:'exceptionType',hidden:true },
		    {header:'发生系统ID',dataIndex:'sysCode',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',hidden:true },
		    {header:'异常类型',dataIndex:'exceptionTypeName',width:swidth*0.05},		    		    
		    {header:'异常内容',dataIndex:'exceptionContent',width:swidth*0.13},		    
		    {header:'参考解决意见',dataIndex:'handleSuggestionName',width:swidth*0.13},
		    {header:'发生系统',dataIndex:'sysName',width:swidth*0.13},
		    {header:'所属业务',dataIndex:'serviceName',width:swidth*0.13},		    
		    {header:'创建时间',dataIndex:'createTime',width:swidth*0.13}

		   	    
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'异常监控',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QryExceptionMonitorAction',
			
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
	    var sysCode = Ext.getCmp('sysCode').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();

	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {serviceId:serviceId,sysCode:sysCode,flag:1};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initQryPanel = function(){
	    var serveStore = this.initServeStore();
	    var sysStore = new Ext.data.Store({   
            proxy: new Ext.data.HttpProxy({   
                url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
            }),   
            reader: new Ext.data.JsonReader({   
            root: 'Body',   
            id: 'sysStore'  
            }, [   
                {name: 'name', mapping: 'sysName'},   
                {name: 'value', mapping: 'sysCode'}   
            ])   
          }); 
          sysStore.load({params:{start:0, limit:7}});
	    
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
                        xtype:'combo',
                        fieldLabel: '系统编码',
                        name: 'sysCode',
                        id: 'sysCode',
                        valueField: 'value',
                        displayField: 'value',
                        mode: 'local',
                        triggerAction: 'all',
                        editable : false ,
                        value: '',
                        store: sysStore,
                        allowBlank: false,
                        listeners: {
                            
                        },
                        anchor:'90%'    
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    fieldLabel: '所属业务',
                    xtype: 'combo',
                    name: 'serviceId',
	                id: 'serviceId',
	                valueField: 'serviceId',
	                displayField: 'serviceName',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:serveStore
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
		Ext.getCmp('sysCode').setValue('');
		Ext.getCmp('serviceId').setValue('');
		//Ext.getCmp('serviceName').setValue('');
		//Ext.getCmp('beginDate').setValue('');
		//Ext.getCmp('endDate').setValue('');
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
        objq.connectLimit = 0;
        objq.username =  selGridItem[0].data.username;
        var tmpObj = invokeAction("/outerSystem/UpdateConnectLimitAction", objq);
        oper.QryMonitorGrid();
    }
    
    this.recoverCust = function(){
    	var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
    	var objq = new Object();
    	objq.flag = 1;
        objq.connectLimit = 1;
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