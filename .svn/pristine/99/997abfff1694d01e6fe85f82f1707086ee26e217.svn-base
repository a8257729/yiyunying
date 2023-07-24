<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>公告信息管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
        <script language="javascript" src="js/publicNoticeManage.js"></script>
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
var session1 = GetSession();
var callRowCount = 5; 
var publicOper = new PublicOper(); 
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
		 var publicGrid = this.initPublicGrid();		 
		 this.QryPublicGrid();
		 this.initGridMenu();  
		 this.initGridEvent();
		 		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '公告信息列表',
			region: 'center',
			layout: 'border',
			items:[publicGrid]
		}); 
		
				
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[publicGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "publicGrid":
						{
							oper.QryPublicGrid();
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
	
	
    this.initPublicGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'noticeId',hidden:true },	
		    {header:'公告发布人id',dataIndex:'createStaffId',hidden:true},
		   {header:'公告发布对象id',dataIndex:'objectId',hidden:true},
		    {header:'公告发布对象',dataIndex:'objectName',width:swidth*0.06}	,		    
		    {header:'公告标题',dataIndex:'title', width:swidth*0.1},
		    {header:'公告内容',dataIndex:'content',width:swidth*0.25 },
		    {header:'公告时间',dataIndex:'noticeTime',width:swidth*0.1},		    
		    		    
		    {header:'公告状态id',dataIndex:'status',hidden:true},
		     {header:'公共类型id',dataIndex:'type',hidden:true},
		      {header:'优先级id',dataIndex:'priority',hidden:true},
		      
		    {header:'优先级',dataIndex:'notificationPriority',width:swidth*0.05},
		    {header:'公告状态',dataIndex:'notificationStatus',width:swidth*0.05},
		    {header:'公告类型',dataIndex:'noticeType',width:swidth*0.05},
		    {header:'公告 发布类型id',dataIndex:'objectType',hidden:true},
		    {header:'发布对象类型',dataIndex:'objectTypeName',width:swidth*0.07}	     
		]);

		var publicGrid =  new ZTESOFT.Grid({
			id: 'publicGrid',
			region: 'center',					
		    title:'公告信息管理',
		    cm:column,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelMobileNoticeAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return publicGrid;	
	}
	
	this.QryPublicGrid = function(){
	    var pagin = "Y";
	    var optype = "ALL";
        var orgId = Ext.getCmp('v_org_id').getValue();
		var staffId = Ext.getCmp('v_staff_id').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	//	var publicType = Ext.getCmp('publicType').getValue();
		
	    Ext.getCmp('publicGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('publicGrid').store.lastOptions != null){
				   Ext.getCmp('publicGrid').store.baseParams = {orgId:orgId,staffId:staffId,optype:optype,pagin:pagin,beginDate:beginDate,endDate:endDate};
				}
			}
	    )
	    Ext.getCmp('publicGrid').store.removeAll() ;
		Ext.getCmp('publicGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initQryPanel = function(){
	//    var publicStore = new Ext.data.ArrayStore({fields:['id','value'], data: [['','全部'],[1,'人员'],[2,'部门']]});  
	    
	      var publicStore = new Ext.data.JsonStore({
			id: 'publicStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/OBJECT_TYPE'
	        }),
	          baseParams:{flag:1},
	          autoLoad : true
	    });
	    
	    var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
		region: 'north',
		frame:true,
		title: '查询条件',
	    bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true,
        height:Ext.getBody().getSize().height*0.26,
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
                        xtype: 'hidden',
                        fieldLabel: '部门ID',
                        name: 'v_org_id',
                        id: 'v_org_id'
                    },{
	                    xtype: "trigger",
		                fieldLabel: "部门",
		                id:'v_org_name',
		                name:'v_org_name',
		                allowBlank:true, 
		                blankText:"不能为空!",
		                editable:false,
		                anchor: "95%",
		                onTriggerClick: function(e) {
		                  selOrg();
		                }	
                },{
                    xtype:'datefield',
                    fieldLabel: '开始日期',
                    name: 'beginDate',
                    id: 'beginDate',
                    format :'Y-m-d',
                    anchor:'95%'              
                }
//                ,{
//                        xtype:'combo',
//                        fieldLabel: '发布类型',
//                        name: 'publicType',
//                        id: 'publicType',
//                        valueField: 'id',
//                        displayField: 'value',
//                        mode: 'local',
//                        triggerAction: 'all',
//                        editable : false ,
//                        value: '',
//                        store: publicStore,
//                        allowBlank: true,
//                        anchor:'95%'
//                    }
                    ]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        fieldLabel: '人员ID',
                        name: 'v_staff_id',
                        id: 'v_staff_id'
                    },{
	                    xtype: "trigger",
		                fieldLabel: "人员",
		                id:'v_staff_name',
		                name:'v_staff_name',
		                allowBlank:true, 
		                editable:false,
		                anchor: "95%",
		                onTriggerClick: function(e) {
		                  selStaff();
		                }	
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
	
	function selOrg(){
        
        var obj = new Object();
        //obj.cableName = Ext.getCmp("v_cableName").getValue();
        var selNodes = OpenShowDlg("seleOrg.jsp?sel_type=1", 500, 550, null);
        var orgName = '';
        var orgId = '';
        if (selNodes != null && selNodes != undefined) {
           Ext.each(selNodes,
           function(node) {
                if (orgName.length > 0) {
                   orgName += ',';
                   orgId += ',';
                }
                orgName += node.text;
                orgId += node.id;
           });

        }
        Ext.getCmp("v_org_id").setValue(orgId);
        Ext.getCmp("v_org_name").setValue(orgName);
        

     }
     
     function selStaff(){
        var obj = new Object();
        var selNodes = OpenShowDlg("seleStaff.jsp?sel_type=2", 500, 550, null);
        if (selNodes != null && selNodes != undefined) {
        	Ext.getCmp("v_staff_id").setValue(selNodes.staffId);
            Ext.getCmp("v_staff_name").setValue(selNodes.staffName);
        }
     }

	
	function doQry(){
	
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "publicGrid":
			{
				oper.QryPublicGrid();
				break ;
			}
			
		}	
	    

	}

	function reset(){
		Ext.getCmp('v_org_id').setValue('');
		Ext.getCmp('v_org_name').setValue('');
        Ext.getCmp('v_staff_id').setValue('');
		Ext.getCmp('v_staff_name').setValue('');
		//Ext.getCmp('publicType').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}
	
	this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
	var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClickFun'
		});
    }
	
	//定义连接状态事件

	this.initGridEvent = function(){
		Ext.getCmp('publicGrid').store.removeAll() ;
		Ext.getCmp('publicGrid').store.load();
		Ext.getCmp('publicGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('publicGrid').addListener('contextmenu',oper.nullRightClickFn);

	}
	
	//连接状态右键菜单

	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
//		rightClick.insert(i++,new Ext.menu.Item({ text: '新增公告' ,handler: function() {
//			rightClick.hide();
//			var pmName;
//			var pmId;
//			publicOper.showModuleInfoPage('add',pmName,pmId);
//		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改公告' ,handler: function() {
			rightClick.hide();
			var pmName;
			var pmId;
			publicOper.showModuleInfoPage('mod',pmName,pmId);
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除公告' ,handler: function() {
			rightClick.hide();
			oper.publicDel();
		}}));
							
		Ext.getCmp('publicGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	 this.nullRightClickFn = function(e){
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClickFun');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增公告' ,handler: function() {
			nullRightClick.hide();
			var pmName;
			var pmId;
			publicOper.showModuleInfoPage('add',pmName,pmId);
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    }
    
    this.publicDel = function(){
        var selGridItem = Ext.getCmp('publicGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
	    	var objq = new Object();
	    	objq.optype = "D";
	        objq.noticeId = selGridItem[0].data.noticeId;
	        var tmpObj = invokeAction("/message/PublicNoticeManageAction", objq);
	        oper.QryPublicGrid();
        }
    }
       
		
}


</script>