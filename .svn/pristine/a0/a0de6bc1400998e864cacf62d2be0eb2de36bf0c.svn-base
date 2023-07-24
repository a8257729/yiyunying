<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>帐号映射</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>
						
	</body>
</html>
<script type="text/javascript"	src="js/staffMappingCfg.js"></script>
<script type="text/javascript"	src="js/configStaff.js"></script>
<script language="JScript">
/**	by tang.lin **/
//全局变量
//获取分辨率


var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var staffRowCount = 5; //人员列表每页显示多少条数据


var outSysStaffGirdRowCount = 5 //外系统人员每页显示多少数据

var staffConfigOper = new StaffConfigOper();
var oper = new Oper(); 
var configStaff = new ConfigStaff();

Ext.onReady(function(){
	var orgTree = oper.initOrgTree();
		var mainPanel = oper.initMainPanel();
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			margins: '5 5 5 5',
			items:[orgTree,mainPanel]
	});
});
//操作类定义


function Oper(){
	//初始化主面板
	this.initMainPanel = function(){
		 //右键菜单
		 var initContextMenu = this.initContextMenu();
		 //查询面板
		 var qryPanel = this.initQryPanel();
		 //人员列表
		 var staffGrid = this.initStaffGrid();
		 //外系统人员列表
		 
		 var outSysStaffGird = this.initOutSysStaffGird();
		 //主面板
		 oper.initGridMenu();
         oper.initGridEvent();

		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,staffGrid,outSysStaffGird]
	 	});
	 	return mainPanel;
	}
	//初始化右键菜单


	this.initContextMenu = function(){
		var rightClick = new Ext.menu.Menu({
		    id:'rightMenu',
		    items:[{
		    	text:'配置外系统帐号',
		    	handler: oper.configStaff
		    }]
		});
		return rightClick;
	}
	//初始化组织树
	this.initOrgTree = function(){
			var orgTree = new ZTESOFT.OrgTree({
				id:'orgTree',
		       	region: 'west',
		       	allOrgs:true//是否查询全组织


		       	//checked:true 是否需要复选框    
			});
			this.initOrgTreeEvent();
			return orgTree;
	}
	//初始化组织树事件
	this.initOrgTreeEvent = function(){
		Ext.getCmp('orgTree').on('click', function(node){ //使节点可以单击展开收起
		    if(node.isLeaf() == false){
			     if(node.expanded == false){
			     	node.expand();//展开
			     }else{
			     	node.collapse();//收起
			     } 
		 	}
			oper.initStaffGridQry(node);
		 });		
	   
	}
	//初始化查询面板

	this.initQryPanel = function(){
		var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
     	region: 'north',
     	frame:true,
        title: '查询条件',
        bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true, 
        height:Ext.getBody().getSize().height*0.20,
        width:Ext.getBody().getSize().width*0.8,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '姓名',
                    name: 'staffName',
                    id: 'staffName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName',
                    id: 'userName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    name: 'qryType',
                    fieldLabel: '查询类别',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    id: 'qryType',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: 'qrySelf',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['qrySelf','查询本组织'],['qrySub','查询本组织及下属组织'],['qryNull','查询无职位人员']]
                    })
                },{
                    
                }]
            }]
        }],
        buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
                    			oper.initStaffGridQry(Ext.getCmp('orgTree').getSelectionModel().getSelectedNode());
                    		}	
			            }},{
                    	text: '重置' ,
                    	listeners:{"click":function(){
			                Ext.getCmp('qryPanel').form.reset();
			            }} 
                    }]
    	});
    	return qryPanel;
	}
	
	this.initStaffGrid = function(){
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'职位编号',dataIndex:'jobId',hidden:true },
		    {header:'组织编号',dataIndex:'orgId',hidden:true },
		    {header:'姓名',dataIndex:'staffName',width:swidth*0.1},
		    {header:'用户名',dataIndex:'userName',width:swidth*0.1},
		    {header:'职位',dataIndex:'jobName',width:swidth*0.1},
		    {header:'职位是否缺省',dataIndex:'isbasic',width:swidth*0.1},
		    {header:'组织',dataIndex:'orgPathName',width:swidth*0.15},
		    {header:'办公电话',dataIndex:'officeTel',width:swidth*0.1},
		    {header:'移动电话',dataIndex:'mobileTel',width:swidth*0.1}
		]);
		  //人员信息
		var staffGrid =  new ZTESOFT.Grid({
			id:'staffGrid',
	    	region: 'center',
	        title:'人员列表',
			cm:column,
			pageSize:5,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/StaffPagingAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	oper.outSysStaffGirdQry(rec);
	                        }
	                    }
	        })
	    });
	    this.initContextMenuEvent();	   
	    return staffGrid;
	
	}
	
	//人员列表右键菜单
	this.initContextMenuEvent = function (){
		
		Ext.getCmp('staffGrid').addListener('rowcontextmenu',function(grid,rowIndex,e){
				e.preventDefault();
				Ext.getCmp("rightMenu").showAt(e.getXY());
				Ext.getCmp('staffGrid').getSelectionModel().selectRow(rowIndex);
		});
	}
	//查询人员列表
	this.initStaffGridQry = function(node){
		//组织树当前节点 node
		if(node==null){
			Ext.example.msg('','请选择组织'); 
			return;
		}
		//绑定发送请求参数

		Ext.getCmp('staffGrid').store.on('beforeload',function(store){
			//获取查询参数
			var staffName = Ext.getCmp("staffName").getValue() ;
	        var userName  = Ext.getCmp("userName").getValue() ;
			var qryType   = Ext.getCmp("qryType").getValue() ;
			var orgPathCode = node.attributes.orgPathCode;
			var orgId = node.attributes.orgId;
			if(Ext.getCmp('staffGrid').store.lastOptions != null){
				Ext.getCmp('staffGrid').store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:'',userName:userName,mobileTel:'',qryType:qryType};
			}
		});
		Ext.getCmp('staffGrid').store.removeAll() ;
		Ext.getCmp('staffGrid').store.load({params:{start:0, limit:staffRowCount}});
		//load数据后自动选择第一行数据,load事件为加载数据完成后触发
		Ext.getCmp('staffGrid').store.on('load',function(){
			Ext.getCmp('staffGrid').getSelectionModel().selectFirstRow();
		});
		 
		
	}
	this.initOutSysStaffGird = function(){
		var columnOutSysStaffGird = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'staffMappingId',hidden:true },
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'业务系统编号',dataIndex:'busiSysId',hidden:true },
		    {header:'业务系统名称',dataIndex:'sysProvider',width:swidth*0.1},
		    {header:'映射登陆名',dataIndex:'mappingUserName',width:swidth*0.1},		    
		    {header:'映射人员编号',dataIndex:'mappingStaffId',width:swidth*0.07 },
		    {header:'映射人员姓名',dataIndex:'mappingStaffName',width:swidth*0.07},
		    {header:'职位编号',dataIndex:'mappingJobId',width:swidth*0.1 },		    		    		    
		    {header:'映射职位',dataIndex:'mappingJobName',width:swidth*0.1},
		    {header:'角色编号',dataIndex:'mappingRoleId',width:swidth*0.1 },
		    {header:'映射角色',dataIndex:'mappingRoleName',width:swidth*0.1}
		]); 

	    var outSysStaffGird = new ZTESOFT.Grid({
	    	id:'outSysStaffGird',
	    	title : '外系统人员列表',
	    	region: 'south',
	    	width:10,
	        pageSize:5,
	        paging:true,
	        cm:columnOutSysStaffGird,
			url:'/MOBILE/ExtServlet?actionName=system/QryMobileStaffMappAction'
		});
		return outSysStaffGird;
	}
	//查询外系统人员

	this.outSysStaffGirdQry = function(staffRow){
		var staffId = staffRow.data.staffId;
		//绑定发送请求参数
		Ext.getCmp('outSysStaffGird').store.on('beforeload',function(store){
			if(Ext.getCmp('outSysStaffGird').store.lastOptions != null){
				Ext.getCmp('outSysStaffGird').store.baseParams = {staffId:staffId,start:0, limit:outSysStaffGirdRowCount};
			}
		});
		
		Ext.getCmp('outSysStaffGird').store.load({params:{staffId:staffId,start:0, limit:outSysStaffGirdRowCount}});
	}

	//配置外系统人员

	//定义菜单列表菜单
    this.initGridMenu =  function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	this.initGridEvent = function(){
		Ext.getCmp('outSysStaffGird').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('outSysStaffGird').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//菜单组装
	this.rightClickFn = function(outSysStaffGird,rowIndex,e){
	    	   		
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		Ext.getCmp('outSysStaffGird').getSelectionModel().selectRow(rowIndex);
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增外系统人员' ,handler: function() {
			rightClick.hide();
			oper.outAdd();
		}}));

		rightClick.insert(i++,new Ext.menu.Item({ text: '修改外系统人员' ,handler: function() {
			rightClick.hide();
			oper.outMod();
		}}));
			
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除外系统人员' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.outDel);
		}}));
							
		Ext.getCmp('outSysStaffGird').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){

	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '新增外系统人员' ,handler: function() {
			nullRightClick.hide();
			oper.outAdd();
		}}));

    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    	
    }
    
    this.outAdd = function(){
        var selItem = Ext.getCmp('staffGrid').getSelectionModel().getSelections();
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择人员！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        var staffId = selItem[0].data.staffId;
        var staffName = selItem[0].data.staffName;
        staffConfigOper.showMenuInfoPage('add',staffName,staffId);
    }
    this.outMod = function(){
        var selItem = Ext.getCmp('outSysStaffGird').getSelectionModel().getSelections();
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择外系统人员！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        var staffMappingId = selItem[0].data.staffMappingId;
        staffConfigOper.showMenuInfoPage('mod','',staffMappingId);
    }
    this.outDel = function(btn){
      if (btn == 'yes'){
        var selItem = Ext.getCmp('outSysStaffGird').getSelectionModel().getSelections();
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请择外系统人员！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        var inputParams = new Object();
		inputParams.staffMappingId = selItem[0].data.staffMappingId;
		inputParams.type = "del";
	    var retMap = invokeAction("/mappingService/OptMobileStaffMappingAction", inputParams);
	    Ext.getCmp('outSysStaffGird').store.removeAll();
	    Ext.getCmp('outSysStaffGird').store.reload();
	  }
    }
    
    this.configStaff = function(){
		if(Ext.isEmpty(Ext.getCmp("staffGrid").getSelectionModel().getSelected())){
			return;
		};
		configStaff.initWindow();
		configStaff.doQry();
	}
}


</script>