<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>流量监控</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
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
var callRowCount = 5; 

var oper = new Oper(); 

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
          
		 var flowMonitorGrid = this.initFlowMonitorGrid();
		 this.QryFlowMonitorGrid();
		 
		 //var totalFlowMonitorGrid = this.initTotalFlowMonitorGrid();
		 //this.QryTotalFlowMonitorGrid();
		 
		 var avgFlowMonitorGrid = this.initAvgFlowMonitorGrid();
         this.QryAvgFlowMonitorGrid();
         
         var flowRankGrid = this.initFlowRankGrid();
         this.QryFlowRankGrid();
         
         var flowRankPanel = new Ext.Panel({
		    id:'flowRankPanel',
			border: false,
			title: '流量排名统计',
			region: 'center',
			layout: 'border',
			items:[flowRankGrid]
		}); 
		
		var flowChartPanel = new Ext.Panel({
		    id:'flowChartPanel',
			border: false,
			title: '流量监控统计图',
			/*region: 'center',
			layout: 'border',*/
			 html : '<iframe id="flowChart" src="/MOBILE/api/busi/stat/shanghai/flow_stat.html" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
		});
                		 				
		 var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[flowMonitorGrid, /*totalFlowMonitorGrid, */avgFlowMonitorGrid, flowRankPanel, flowChartPanel],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "flowMonitorGrid":
						{
							oper.QryFlowMonitorGrid();
							break ;
						}
						case "totalFlowMonitorGrid":
						{
                            oper.QryTotalFlowMonitorGrid();                           
							break ;
						}						
						case "avgFlowMonitorGrid":
						{
                            oper.QryAvgFlowMonitorGrid();                           
							break ;
						}
						case "flowRankPanel":
						{
                            oper.QryFlowRankGrid();
							break ;
						}
						/*
						case "flowChartPanel":
						{
							oper.showFlowFloatChart();
							break;
						}
						*/
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
	
    this.initFlowMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'restServLogId',hidden:true },		    
		    {header:'人员编号',dataIndex:'staffId',hidden:true },    
		    {header:'服务类型',dataIndex:'servLogType',hidden:true },
		    {header:'用户',dataIndex:'username',width:swidth*0.1},
		    {header:'服务编号',dataIndex:'restServiceId',width:swidth*0.1 },
		    {header:'服务名',dataIndex:'servName',width:swidth*0.1},
		    {header:'请求包大小(字节)',dataIndex:'inSize',width:swidth*0.15},
		    {header:'响应包大小(字节)',dataIndex:'outSize',width:swidth*0.15},
		    {header:'请求时间',dataIndex:'inTimestamp',width:swidth*0.15},
		    {header:'响应时间',dataIndex:'outTimestamp',width:swidth*0.15}
		]);

		var flowMonitorGrid =  new ZTESOFT.Grid({
			id: 'flowMonitorGrid',
			region: 'center',					
		    title:'流量监控',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return flowMonitorGrid;	
	}
	
	this.QryFlowMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('flowMonitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('flowMonitorGrid').store.lastOptions != null){
					debugger;
				   Ext.getCmp('flowMonitorGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:9};
				}
			}
	    )
	    Ext.getCmp('flowMonitorGrid').store.removeAll() ;
		Ext.getCmp('flowMonitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initQryPanel = function(){
	    var serveStore = this.initServeStore();
	    
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
                    fieldLabel: '服务',
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
			case "flowMonitorGrid":
			{
							oper.QryFlowMonitorGrid();
							break ;
			}
			case "totalFlowMonitorGrid":
			{
                            oper.QryTotalFlowMonitorGrid();                           
							break ;
			}						
			case "avgFlowMonitorGrid":
			{
                            oper.QryAvgFlowMonitorGrid();                           
							break ;
			}
			case "flowRankPanel":
			{
                            oper.QryFlowRankGrid();
							break ;
			}					
		}	
	    
	}

	function reset(){
		Ext.getCmp('username').setValue('');
		Ext.getCmp('serviceId').setValue('');
		//Ext.getCmp('serviceName').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}
	
	this.initAvgFlowMonitorGrid = function(){
	   
		var column = new Ext.grid.ColumnModel([
		    {header:'用户',dataIndex:'username',width:swidth*0.1},
		    {header:'服务编号',dataIndex:'restServiceId',width:swidth*0.1 },
		    {header:'服务名',dataIndex:'servName',width:swidth*0.1},		    
		    {header:'请求包平均大小(字节)',dataIndex:'avgInSize',width:swidth*0.2},
		    {header:'响应包平均大小(字节)',dataIndex:'avgOutSize',width:swidth*0.2}		  
		]);

		var avgFlowMonitorGrid =  new ZTESOFT.Grid({
			id:'avgFlowMonitorGrid',
	    	region: 'center',
	        title:'平均流量监控',
			cm:column,
			pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	
	                        }
	                    }
	        })
	    });
   
	    return avgFlowMonitorGrid;
		
	}
	
	this.QryAvgFlowMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('avgFlowMonitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('avgFlowMonitorGrid').store.lastOptions != null){
				   Ext.getCmp('avgFlowMonitorGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:8};
				}
			}
	    )
	    Ext.getCmp('avgFlowMonitorGrid').store.removeAll() ;
		Ext.getCmp('avgFlowMonitorGrid').store.load({params:{start:0, limit:16}});
		
	}
	
	this.initFlowRankGrid = function (){
  		
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',width:swidth*0.3},
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.3}
		]);

		var flowRankGrid =  new ZTESOFT.Grid({
			id:'flowRankGrid',
	    	region: 'center',
	        title:'服务列表',
			cm:column,
			pageSize:7,
			paging:true,
			height:230,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                           oper.QryServerAskRank10(rec);
	                           oper.QryServerRespondRank10(rec);

	                        }
	                    }
	        })
	    });
	    

		var column1 = new Ext.grid.ColumnModel([

		    {header:'第一名',dataIndex:'ServerAsk1',width:swidth*0.08},
		    {header:'第二名',dataIndex:'ServerAsk2',width:swidth*0.08},
		    {header:'第三名',dataIndex:'ServerAsk3',width:swidth*0.08},
		    {header:'第四名',dataIndex:'ServerAsk4',width:swidth*0.08},
		    {header:'第五名',dataIndex:'ServerAsk5',width:swidth*0.08},
		    {header:'第六名',dataIndex:'ServerAsk6',width:swidth*0.08},
		    {header:'第七名',dataIndex:'ServerAsk7',width:swidth*0.08},
		    {header:'第八名',dataIndex:'ServerAsk8',width:swidth*0.08},
		    {header:'第九名',dataIndex:'ServerAsk9',width:swidth*0.08},
		    {header:'第十名',dataIndex:'ServerAsk10',width:swidth*0.08}
		]);

		var serverAskRank10Grid =  new ZTESOFT.Grid({
			id:'serverAskRank10Grid',
	    	region: 'center',
	        title:'最大请求包前十名',
			cm:column1,
			pageSize:10,
			paging:false,
			height:75,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {

	                        }
	                    }
	        })
	    });
	    
		
		var column2 = new Ext.grid.ColumnModel([

		    {header:'第一名',dataIndex:'serverRespond1',width:swidth*0.08},
		    {header:'第二名',dataIndex:'serverRespond2',width:swidth*0.08},
		    {header:'第三名',dataIndex:'serverRespond3',width:swidth*0.08},
		    {header:'第四名',dataIndex:'serverRespond4',width:swidth*0.08},
		    {header:'第五名',dataIndex:'serverRespond5',width:swidth*0.08},
		    {header:'第六名',dataIndex:'serverRespond6',width:swidth*0.08},
		    {header:'第七名',dataIndex:'serverRespond7',width:swidth*0.08},
		    {header:'第八名',dataIndex:'serverRespond8',width:swidth*0.08},
		    {header:'第九名',dataIndex:'serverRespond9',width:swidth*0.08},
		    {header:'第十名',dataIndex:'serverRespond10',width:swidth*0.08}
		]);

		var serverRespondRank10Grid =  new ZTESOFT.Grid({
			id:'serverRespondRank10Grid',
	    	region: 'center',
	        title:'最大响应包前10名',
			cm:column2,
			height:75,		
			pageSize:10,
			paging:false,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {

	                        }
	                    }
	        })
	    });
        var flowRankPanel = new Ext.Panel({
			border: false,
			title: '流量大小排名统计',
			region: 'center',
			layout: 'form',
			items:[flowRankGrid,serverAskRank10Grid,serverRespondRank10Grid]
		}); 
		
	    return flowRankPanel;
	
	}
	
	this.showFlowFloatChart = function() {
		var flowChartFloatPanel = new Ext.Panel({
        layout : 'fit',
        html : '/MOBILE/api/busi/stat/shanghai/serv_stat.html',
        frame : true
        })
        var win = new Ext.Window({
                    title : '服务调用统计图',
                    width : 650,
                    height :570,
                    resizable : false,
                    closable : true,
                    draggable : true,
                    resizable : false,
                    layout : 'fit',
                    modal : false,
                    plain : true, // 表示为渲染window body的背景为透明的背景
                    bodyStyle : 'padding:5px;',
                    items : [flowChartFloatPanel],
                    buttonAlign : 'center',
                    buttons : [{
                            text : '关闭',
                            type : 'button',
                            handler : function() {
                                win .close();
                                }
                            }]
                });
                win.show();
	}
			
	this.QryFlowRankGrid = function (){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('flowRankGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('flowRankGrid').store.lastOptions != null){
				   Ext.getCmp('flowRankGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:1};
				}
			}
	    )
	    Ext.getCmp('flowRankGrid').store.removeAll() ;
		Ext.getCmp('flowRankGrid').store.load({params:{start:0, limit:7}});
	
	}	
	
	this.QryServerAskRank10 = function(serverRow){
		var serviceId = serverRow.data.serviceId;
        var username = Ext.getCmp('username').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		//绑定发送请求参数

		Ext.getCmp('serverAskRank10Grid').store.on('beforeload',function(store){
			if(Ext.getCmp('serverAskRank10Grid').store.lastOptions != null){
				Ext.getCmp('serverAskRank10Grid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:10};
			}
		});
		Ext.getCmp('serverAskRank10Grid').store.removeAll();
		Ext.getCmp('serverAskRank10Grid').store.load();
	}
	
	this.QryServerRespondRank10 = function(serverRow){
		var serviceId = serverRow.data.serviceId;
        var username = Ext.getCmp('username').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		//绑定发送请求参数
		debugger;

		Ext.getCmp('serverRespondRank10Grid').store.on('beforeload',function(store){
			if(Ext.getCmp('serverRespondRank10Grid').store.lastOptions != null){
				Ext.getCmp('serverRespondRank10Grid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:11};
			}
		});
		Ext.getCmp('serverRespondRank10Grid').store.removeAll();
		Ext.getCmp('serverRespondRank10Grid').store.load();
	}	
	
	this.initTotalFlowMonitorGrid = function(){
		var column = new Ext.grid.ColumnModel([   
		    {header:'用户',dataIndex:'username',width:swidth*0.1},
		    {header:'服务编号',dataIndex:'serviceId',hidden:true },
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.1},
		    {header:'最新请求包大小(字节)',dataIndex:'inMessageSize',width:swidth*0.12},
		    {header:'最新响应包大小(字节)',dataIndex:'outMessageSize',width:swidth*0.12},
		    {header:'请求包总大小(字节)',dataIndex:'totalInMessageSize',width:swidth*0.12},
		    {header:'响应包总大小(字节)',dataIndex:'totalOutMessageSize',width:swidth*0.12},
		    {header:'总流量大小(字节)',dataIndex:'totalFlow',width:swidth*0.12}
		]);

		var totalFlowMonitorGrid =  new ZTESOFT.Grid({
			id: 'totalFlowMonitorGrid',
			region: 'center',					
		    title:'总流量监控',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return totalFlowMonitorGrid;	
	}
	
	this.QryTotalFlowMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('totalFlowMonitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('totalFlowMonitorGrid').store.lastOptions != null){
				   Ext.getCmp('totalFlowMonitorGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:12};
				}
			}
	    )
	    Ext.getCmp('totalFlowMonitorGrid').store.removeAll() ;
		Ext.getCmp('totalFlowMonitorGrid').store.load({params:{start:0, limit:16}});
	
	}
}


</script>