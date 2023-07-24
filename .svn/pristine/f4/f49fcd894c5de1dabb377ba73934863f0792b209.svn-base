<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>用户安全管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/staffSecConfig.js"></script>
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	    <div id='sysGird'/>
	</body>
</html>

<script language="JScript">
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var staffSecConfigOper = new StaffSecConfigOper();
//
var securityTypeMap = {"0":"手工输入", "1": "终端自动获取"};
var bindingStatusMap = {"0":"未绑定", "1":"已绑定"};
var securityTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'手工输入'],[1,"终端自动获取"]]});
var bindingStatusStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'未绑定'],[1,"已绑定"]]});

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
		 var staffSecConfigGrid = this.initStaffSecConfigGrid();		 
		 this.QryStaffSecConfigGrid();
		 this.initGridMenu();  
		 this.initGridEvent();
		 		 
         var staffSecConfigPanel = new Ext.Panel({
			border: false,
			title: '用户安全管理',
			region: 'center',
			layout: 'border',
			items:[staffSecConfigGrid]
		}); 

		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[staffSecConfigGrid],
                listeners: {
	                tabchange: function(){
                        var activeTab = tabs.activeTab.id ;
                        switch (activeTab){
                            case "staffSecConfigGrid":
                            {
                                oper.QryStaffSecConfigGrid();
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

    this.initStaffSecConfigGrid = function(){
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'mobileStaffSecConfigId',hidden:true },
		    {header:'人员ID',dataIndex:'staffId',hidden:true },
		    {header:'人员名称',dataIndex:'staffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'username',width:swidth*0.1},
            {header:'手机号码',dataIndex:'mobile',width:swidth*0.1},
		    {header:'安全设置类型',dataIndex:'securityType',width:swidth*0.1, renderer:function(v){return securityTypeMap[v];}},
		    {header:'IMSI码',dataIndex:'imsiCode',width:swidth*0.1, renderer:function(v){return bindingStatusMap[v];}},
		    {header:'绑定状态',dataIndex:'bindingStatus',width:swidth*0.1},
		    {header:'操作时间',dataIndex:'optTime',width:swidth*0.1}
		]);

		var staffSecConfigGrid =  new ZTESOFT.Grid({
			id: 'staffSecConfigGrid',
			region: 'center',					
		    title:'用户安全管理',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelMobileStaffSecConfigAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return staffSecConfigGrid;	
	}
	
	this.QryStaffSecConfigGrid = function(){

        var staffName = Ext.getCmp('m_staffName').getValue();
        var username = Ext.getCmp('m_username').getValue();
		var mobile = Ext.getCmp('m_mobile').getValue();
		var securityType = Ext.getCmp('m_securityType').getValue();
		var bindingStatus = Ext.getCmp('m_bindingStatus').getValue();
        var imsiCode =  Ext.getCmp('m_imsiCode').getValue();

	    Ext.getCmp('staffSecConfigGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('staffSecConfigGrid').store.lastOptions != null){
				   Ext.getCmp('staffSecConfigGrid').store.baseParams = {
                       staffName:staffName,
                       username:username,
                       mobile:mobile,
                       securityType:securityType,
                       imsiCode:imsiCode,
                       bindingStatus:bindingStatus
                   };
				}
			}
	    )
	    Ext.getCmp('staffSecConfigGrid').store.removeAll() ;
		Ext.getCmp('staffSecConfigGrid').store.load({params:{pagin:"Y", optype:"ALL", start:0, limit:10}});
	}
	
	this.initQryPanel = function(){
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
            text: '查询',onClick:doQuery},{text: '重置',onClick:doReset
        }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户',
                    name: 'm_staffName',
                    id: 'm_staffName',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: 'IMSI码',
                    name: 'm_imsiCode',
                    id: 'm_imsiCode',
                    anchor:'95%'
                }]
            },{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'm_username',
                    id: 'm_username',
                    anchor:'95%'
                },{
                    fieldLabel: '安全设置类型',
                    xtype: 'combo',
                    name: 'm_securityType',
                    id: 'm_securityType',
                    valueField: 'id',
                    displayField: 'value',
                    mode:'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable :false,
                    anchor:'95%',
                    store:securityTypeStore
                }]
            },{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '手机号码',
                    name: 'm_mobile',
                    id: 'm_mobile',
                    anchor:'95%'
                },{
                    fieldLabel: '绑定状态',
                    xtype: 'combo',
                    name: 'm_bindingStatus',
                    id: 'm_bindingStatus',
                    valueField: 'id',
                    displayField: 'value',
                    mode:'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable :false,
                    anchor:'95%',
                    store:bindingStatusStore
                }]
            }]
        }]
	});
       return qryPanel;
	}
	
	function doQuery(){
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "staffSecConfigGrid":
			{
				oper.QryStaffSecConfigGrid();
				break ;
			}
		}
	}

	function doReset(){
		Ext.getCmp('m_staffName').setValue('');
		Ext.getCmp('m_imsiCode').setValue('');
		Ext.getCmp('m_username').setValue('');
		Ext.getCmp('m_securityType').setValue('');
		Ext.getCmp('m_mobile').setValue('');
        Ext.getCmp('m_bindingStatus').setValue('');
	}
	
	this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
        var nullRightClick = new Ext.menu.Menu({
            id:'nullRightClick'
        });
    }
	
	//定义连接状态事件
	this.initGridEvent = function(){
		Ext.getCmp('staffSecConfigGrid').store.removeAll() ;
		Ext.getCmp('staffSecConfigGrid').store.load();
        Ext.getCmp('staffSecConfigGrid').addListener('contextmenu', oper.nullRightClickFn);
		Ext.getCmp('staffSecConfigGrid').addListener('rowcontextmenu', oper.rightClickFn);
	}

    this.nullRightClickFn = function(e){
        var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();
        nullRightClick.add(new Ext.menu.Item({ text: '新增用户安全设置' ,handler: function() {
            nullRightClick.hide();
            oper.addConfig();
        }}));
        nullRightClick.showAt(e.getXY());
    }

	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增用户安全设置' ,handler: function() {
			rightClick.hide();
			oper.addConfig();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改用户安全设置' ,handler: function() {
			rightClick.hide();
			oper.updateConfig();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除用户安全设置' ,handler: function() {
			rightClick.hide();
			oper.delConfig();
		}}));

		Ext.getCmp('staffSecConfigGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
    
    this.addConfig = function(){
        staffSecConfigOper.showMenuInfoPage('I');
    }
    
    this.updateConfig = function(){
        staffSecConfigOper.showMenuInfoPage('U');
    }
    
    this.delConfig  = function(){
        Ext.MessageBox.confirm('提示', '你确定要删除吗？', staffSecConfigOper.moduleDel);
    }
		
}
</script>