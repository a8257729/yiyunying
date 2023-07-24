<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>签到查询</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id='sysGird' />
	</body>
</html>
<script type="text/javascript">
   

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var callRowCount = 5; 

var oper = new Oper(); 
/**
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
*/
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
		
		 		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '签到查询',
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
	    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'staffSigninId',hidden:true },		    
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'用户',dataIndex:'staffName',width:swidth*0.05},		    		    
		    {header:'签到状态',dataIndex:'signinStatus',width:swidth*0.13,renderer:function (val){ if (val == 0) {return "未签到"} else if (val == 1) {return "已签到"} else if(val == 2){return "占缓签到"}}},
		    {header:'签到时间',dataIndex:'signinTime',width:swidth*0.13},
		    {header:'签到经度',dataIndex:'longitude',width:swidth*0.13},
		    {header:'签到纬度',dataIndex:'latitude',width:swidth*0.13},
		    {header:'签到地址',dataIndex:'signinAddr',width:swidth*0.13}   
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'签到列表',
		    cm:column,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/MobileStaffSigninAction',
			
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
	    var staffName = Ext.getCmp('staffName').getValue();
		var signinType = Ext.getCmp('signinType').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {signinType:signinType,staffName:staffName,beginDate:beginDate,endDate:endDate,flag:1};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	
	//  new Ext.data.SimpleStore({
			//  fields : ['signinType', 'signinName'],  
        //         data :  [[0,'未签到'],
	  	  //            [1,'已签到'],
	  //	              [2,'占缓签到']]
	//    });
	
	
	this.initServeStore = function(){
	     var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SIGNIN_TYPE'
	        }),
	          baseParams:{flag:1}
	    
	        
	    });

	    return sysStore;
	}
	
	this.initQryPanel = function(){
	 
	     var sysStore = this.initServeStore();
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
                    fieldLabel: '用户名称',
                    name: 'staffName',
                    id: 'staffName',
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
                    fieldLabel: '签到类型',
                    xtype: 'combo',
                    name: 'signinType',
	                id: 'signinType',
	                valueField: 'mcode',
	                displayField: 'mname',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:sysStore
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
		Ext.getCmp('staffName').setValue('');
		Ext.getCmp('signinType').setValue('');
		//Ext.getCmp('serviceName').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}

	}
	
</script>