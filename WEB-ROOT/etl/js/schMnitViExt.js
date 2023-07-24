/*
 * Developed by fang.li 2011-04
 * 调度方案监控
 */
var rulesRowCount = 6 ;
var picksRowCount = 9 ;
var cleanRowCount = 9 ;

var descHTMLStr = "图例：<img src='./js/elements/resources/wait.gif' width='12' height='12' />待执行 &nbsp;&nbsp;<img src='./js/elements/resources/init.gif' width='12' height='12' />执行中 &nbsp;&nbsp;<img src='./js/elements/resources/error.gif' width='12' height='12' />异常 &nbsp;&nbsp;<img src='./js/elements/resources/finish.gif' width='12' height='12' />执行完成 &nbsp;&nbsp;<img src='./js/elements/resources/del.gif' width='12' height='12' />无效 &nbsp;&nbsp;<img src='./js/elements/resources/unexe.gif' width='12' height='12' />未执行 &nbsp;&nbsp;" ;
 
function SchMnitView(){
	
	this.showSchMnitView = function(schInstId){ 
		var htmlPanel = this.initdescPanel();
		
		var rulesGrids = this.initGridRules(schInstId);
		var picksGrids = this.initGridPicks();
		var cleanGrids = this.initGridClean();
		
		var tabPanel = new Ext.TabPanel({
			id: 'tabPanel',
			region: 'south',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width,   
		    height:Ext.getBody().getSize().height*0.485, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[picksGrids,cleanGrids]
		});
		
		var configWin = new Ext.Window({
       		id: 'configWin',
            closable: true,
            width: swidth*0.7,
            height: sheight*0.7,
            layout: 'border',
            plain: true,
            items: [htmlPanel,rulesGrids,tabPanel]
        });
    	configWin.show();
	}
	
	this.initdescPanel = function(){
		var htmlPanel = new Ext.Panel({
			id: 'htmlPanel',
			region: 'north',
			border: false ,
			frame : true,
			autoScroll : true,
			html: descHTMLStr
		});
		return htmlPanel ;
	}
	
	this.initGridRules = function(schInstId){
	
		function renderStateHtml(value, p, r){
	        if(value == '10W'){
	        	return "<img src='./js/elements/resources/wait.gif' width='13' height='13' />";
	        }else if(value == '10I'){
	        	return "<img src='./js/elements/resources/init.gif' width='13' height='13' />";
	        }else if(value == '10E'){
	        	return "<img src='./js/elements/resources/error.gif' width='13' height='13' />";
	        }else if(value == '10F'){
	        	return "<img src='./js/elements/resources/finish.gif' width='13' height='13' />";
	        }else if(value == '10P'){
	        	return "<img src='./js/elements/resources/del.gif' width='13' height='13' />";
	        }else if(value == '1UE'){
	        	return "<img src='./js/elements/resources/unexe.gif' width='13' height='13' />";
	        }
	    }
	
		var column = new Ext.grid.ColumnModel([
		    {header:'规则实例编号',dataIndex:'etlInstId',hidden:true },
		    {header:'规则类型',dataIndex:'etlType',hidden:true },
		    {header:'执行顺序',dataIndex:'schSequ',width:swidth*0.1},
		    {header:'规则名称',dataIndex:'etlRuleName',width:swidth*0.3},
		    {header:'规则类型',dataIndex:'etlTypeName',width:swidth*0.18},
		    {header:'规则状态',dataIndex:'state',renderer:renderStateHtml,width:swidth*0.1}
		]); 
		
		var gridRules = new ZTESOFT.Grid({
	    	id: 'gridRules',
	    	title: '可添加规则列表',
			border: false ,
	    	region: 'center',
	      	width: 10,
	        pageSize: rulesRowCount,
	        cm: column,
	        paging: true,
			url:'/MOBILE/ExtServlet?actionName=etl/QryMonitorRulesAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec) {
		            	
		            	var etlType = rec.data.etlType;
		            	if('000'==etlType){
			            	Ext.getCmp("tabPanel").unhideTabStripItem(0);
			            	Ext.getCmp("gridPicks").show();
			            	Ext.getCmp("tabPanel").hideTabStripItem(1);
			            	Ext.getCmp("gridClean").hide();
			            	
				            Ext.getCmp("gridPicks").store.on('beforeload',function(store){
				            
								if(Ext.getCmp("gridPicks").store.lastOptions != null){
									Ext.getCmp("gridPicks").store.baseParams = {etlInstId: rec.data.etlInstId,
																				etlType:rec.data.etlType};
								}
							})
							Ext.getCmp("gridPicks").store.removeAll() ;
							Ext.getCmp("gridPicks").store.load({params:{start:0, limit:picksRowCount}});
						}else{
							Ext.getCmp("tabPanel").unhideTabStripItem(1);
							Ext.getCmp("gridClean").show();
		            		Ext.getCmp("tabPanel").hideTabStripItem(0);
		            		Ext.getCmp("gridPicks").hide();
		            		
							Ext.getCmp("gridClean").store.on('beforeload',function(store){
								if(Ext.getCmp("gridClean").store.lastOptions != null){
									Ext.getCmp("gridClean").store.baseParams = {etlInstId: rec.data.etlInstId,
																				etlType:rec.data.etlType};
								}
							})
							Ext.getCmp("gridClean").store.removeAll() ;
							Ext.getCmp("gridClean").store.load({params:{start:0, limit:picksRowCount}});
						}
		            }
		        }
		    })
		});
		gridRules.store.baseParams = {schInstId:schInstId};
		gridRules.store.load({params:{start:0, limit:rulesRowCount}}) ;
		
		gridRules.store.on("load",function(store){
			gridRules.getSelectionModel().selectRow(0);
		});
		
		return gridRules ;
	}
	
	this.initGridPicks = function(){
	
		var column = new Ext.grid.ColumnModel([
		    {header:'系统名称',dataIndex:'sysName',width:swidth*0.1 },
		    {header:'原表名',dataIndex:'sourceTableName',width:swidth*0.1},
		    {header:'目标表名',dataIndex:'tableName',width:swidth*0.1},
		    {header:'开始时间',dataIndex:'beginTime',width:swidth*0.1},
		    {header:'结束时间',dataIndex:'endTime',width:swidth*0.1},
		    {header:'应导入',dataIndex:'mustbeNum',renderer:renderMustbeNum,width:swidth*0.1 },
		    {header:'实际导入',dataIndex:'actualNum',renderer:renderMustbeNum,width:swidth*0.1 },
		    {header:'是否正常',dataIndex:'isException',renderer:renderIsException,width:swidth*0.1 },
		    {header:'异常描述',dataIndex:'logDesc',width:swidth*0.1 }
		]); 
		
		var gridPicks = new ZTESOFT.Grid({
	    	id: 'gridPicks',
	    	title: '抽取日志',
	    	region: 'center',
	      	width: 10,
	        pageSize: picksRowCount,
	        cm: column,
	        paging: true,
			url:'/MOBILE/ExtServlet?actionName=etl/QryPickLogListAction'
		});
		return gridPicks ;
	}
	
	this.initGridClean = function(){
		function renderState(value, p, r){
	        if(value == '10A'){
	        	return '<span style="color:green;">有效</span>' ;
	        }else {
	        	return '<span style="color:red;">无效</span>' ;
	        }
	    }
		
		var column = new Ext.grid.ColumnModel([
			{header:'标识',dataIndex:'etlLogId',hidden:true },
		    {header:'原表名',dataIndex:'sourceTablesName',width:swidth*0.1},
		    {header:'目标表名',dataIndex:'targetTabelsName',width:swidth*0.1},
		    {header:'开始时间',dataIndex:'beginTime',width:swidth*0.1},
		    {header:'结束时间',dataIndex:'endTime',width:swidth*0.1},
		    {header:'需要清洗转换总数据量',dataIndex:'cleanTransTotal',width:swidth*0.1 },
		    {header:'被清洗了的数据量',dataIndex:'rightCount',renderer:renderMustbeNum,width:swidth*0.1 },
		    {header:'剩余数据量',dataIndex:'errorCount',renderer:renderMustbeNum,width:swidth*0.1 },
		    {header:'是否正常',dataIndex:'isException',renderer:renderIsException,width:swidth*0.1 },
		    {header:'异常描述',dataIndex:'logDesc',width:swidth*0.1 }
		]); 
		
		var gridClean = new ZTESOFT.Grid({
	    	id: 'gridClean',
	    	title: '转换日志',
	    	region: 'center',
	      	width: 10,
	        pageSize: cleanRowCount,
	        cm: column,
	        paging: true,
			url:'/MOBILE/ExtServlet?actionName=etl/QryCleanLogListAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec) {
		            	
		            }
		        }
		    })        
		});
		//双击事件
		gridClean.on("rowdblclick",function(){
			var rec = gridClean.getSelectionModel().getSelected();
			
			initLogWin(rec.data.etlLogId);
		});
		
		return gridClean ;
	}
	
	function initLogWin(etlLogId){
	//
	  var params = new Object();
	  params.etlLogId = etlLogId;
	  var result = invokeAction("/etl/QryTransFieldLogAction",params);
	  
	  if(!result){
	  	return;
	  }
		
	  var logWin = new Ext.Window({
	       	id:'logWin',
	        title: '字段清洗转换异常日志信息',
	        closable:true,
	        modal:true,
	        width:600,
	        height:400,
	        buttonAlign:'center',
	        items:[{
	        	xtype:"textarea",
	        	value:result.logDesc,
		        name:"itemDesc",
		        width: swidth*0.5,
            	height: sheight*0.5
	        
	        }],
            buttons:[{
            	text:'关闭',
            	onClick:function(){
					 Ext.getCmp('logWin').close();
				}
            }]
        });  
		logWin.show();
        return logWin;       
	
	}
	
		function renderMustbeNum(value, p, r){
	        if(value == '' || value == null){
	        	return '0' ;
	        }else {
	        	return value ;
	        }
	    }
	    
	    function renderIsException(value, p, r){
	    //'1是异常日志，0正常日志';
	        if(value == '' || value == 0){
	        	return '<span style="color:green;">正常</span>' ;
	        }else {
	        	return '<span style="color:red;">执行异常</span>' ;
	        }
	    }
}