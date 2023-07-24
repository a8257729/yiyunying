<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>移动菜单管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
	    <script language="javascript" src="js/moduelMng.js"></script>
		<script language="javascript" src="js/menuMng.js"></script>
		<script language="javascript" src="js/selMng.js"></script>
		<script language="javascript" src="js/indeMng.js"></script>
		<script language="javascript" src="js/privButMng.js"></script>
		<script language="javascript" src="js/privNodeMng.js"></script>
		<script language="javascript" src="js/statOper.js"></script>
		<script language="javascript" src="js/statTransferMng.js"></script>		
		<script language="javascript" src="js/searchTabMng.js"></script>
		<script language="javascript" src="js/formPositionMng.js"></script>
		
	</head> 
	<style type="text/css">
		.x-window-dlg .ext-mb-download {
		    background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
		    height:46px;
		}
	</style>
	<body onContextMenu="return false;"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="menuMngDiv"></div>
	</body>
</html> 

<script language="JScript">
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var session1 = GetSession();
var staffId = session1.staff.staffId ;
var fext = new BSCommon();
var oper = new Oper();
var moduelOper = new ModuelOper(); 
var menuOper = new MenuOper();
var selOper = new SelOper();
var indeOper = new IndeOper();
var priButOper = new PrivButOper();
var priNodeOper = new PrivNodeOper();
var statOper = new StatOper();//统计界面布局
var transOper = new StatTransferOper();//统计转取设置
var tabOper = new SearchTabOper(); //查询TAB页配置3
var posiOper = new FormPositionOper();
//var tabOper2 = new SearchTabOper();//test
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
Ext.apply(Ext.form.VTypes, {
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

    var moduelTree = oper.initModuelTree();
    var menuGrid = oper.initMenuGrid();
    
    oper.initTreeEvent();
    oper.initGridEvent();
    
    var treeMenu = oper.initTreeMenu();
    var indPanel = oper.initIndPanel();
    var butPanel = oper.initButPanel();
    var nodePanel = oper.initNodePanel();
//    var statPanel = oper.initStatPanel();
//    var transPanel = oper.initTransPanel();
    var tabPanel = oper.initTabPanel();
    //
//    var posiTanel = oper.initPosiPanel();
    
    oper.initIndGridEvent();
    oper.initButGridEvent();
    oper.initNOdeGridEvent();
//    oper.initStatGridEvent();
//    oper.initTransGridEvent();
    oper.initTabGridEvent();
//    oper.initPosiGridEvent();
	// second tabs built from JS
	var tabs2 = new Ext.TabPanel({
			region: 'south',
			id : 'tabs2',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.5,   
		    height:Ext.getBody().getSize().height*0.55, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[nodePanel,indPanel,butPanel,tabPanel]
		});
	
    var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[menuGrid,tabs2]
	}); 
    
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[moduelTree,earthviewport]
	});
});

function Oper(){
    var moduleId = -1;
	//目录树初始化
	this.initModuelTree = function(){
		var moduelTree = new Ext.tree.TreePanel({
			id: 'moduelTree',
	    	title:'移动菜单目录',
	    	region: 'west',
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/MobMuneTreeAction&type=model'
	        }),
	        containerScroll: true,
	        border: false,
	        width:Ext.getBody().getSize().width*0.2,
		    height:Ext.getBody().getSize().height
	    });
	     var confRoleRoot = new Ext.tree.AsyncTreeNode({
	        text: '移动菜单目录',
	        draggable:false,
	        parentId:'null',
	        pathName:'null',
	        pathCode:'null',
	        id:'0'
	    });
	    moduelTree.setRootNode(confRoleRoot);
	    moduelTree.expandAll();
	    //删除排序，因为影响了后台排序 mod by li.guoyang
	    //new Ext.tree.TreeSorter(moduelTree, {folderSort:true});
	    return moduelTree ;
	}
	 
	//列表初始化

	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'formId', 'formName', 'muneId','formName','teachName','intefaceType','displayTypeName','displayType','intefaceType','intefaceUrl','intefaceName','keyName','fristPage'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelJsonCreateAction'
	        }),
	        baseParams:{moduleId:0},//根据moduleId来载入
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
				}
	        }
	    });
		
		//创建列

		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'菜单ID',dataIndex:'formId',hidden:true },		    
		    {header:'目录ID',dataIndex:'muneId',hidden:true },
		    
		    {header:'页面名称',dataIndex:'formName',width:100 },
		    {header:'页面代码',dataIndex:'teachName',width:100 },
		    {header:'是否首页',dataIndex:'fristPage',width:80,renderer:function(v){return v=='1'?'是':'否';} }, 
		    {header:'请求类型',dataIndex:'intefaceType',width:100,width:100,renderer:function(v){return renderMethodType(v);}},
		    {header:'显示类型',dataIndex:'displayType',width:100,renderer:function(v){return staticdistypeStore.getAt(staticdistypeStore.find('id', v)).data.value;} },
		    {header:'服务编码',dataIndex:'intefaceName',width:100 },
		    {header:'关键字段',dataIndex:'keyName',width:100 }	    
		    
		]);   
		menuColumn.defaultSortable = true;
		
	   var pagingBar = new Ext.PagingToolbar({
			pageSize: 15,
			store: menuStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
       });
    
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'菜单页面列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			bbar: pagingBar,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){	            
		            		                
		                if(rec.data.displayType=='13' || rec.data.displayType=='15'){
		                	//Ext.getCmp('tabs2').unhideTabStripItem('indexGrid');
		                	Ext.getCmp('tabs2').hideTabStripItem('nodGrid');
		                	Ext.getCmp('tabs2').unhideTabStripItem('tabGrid');
		                	oper.qryNodeGirdData(rec.data.formId);
		                	oper.qryTabGirdData(rec.data.formId);
		                	if(rec.data.displayType=='13'){
		                		Ext.getCmp('tabs2').hideTabStripItem('butGrid');
		                	} else {
		                		Ext.getCmp('tabs2').unhideTabStripItem('butGrid');
		                		oper.qryButGirdData(rec.data.formId);
		                	}    
		                } else {
		                	//Ext.getCmp('tabs2').unhideTabStripItem('indexGrid');
		                	Ext.getCmp('tabs2').unhideTabStripItem('nodGrid');
		                	Ext.getCmp('tabs2').unhideTabStripItem('butGrid');
		                	Ext.getCmp('tabs2').hideTabStripItem('tabGrid');
		                	oper.qryNodeGirdData(rec.data.formId);
		                	oper.qryButGirdData(rec.data.formId);
		                } 
		                oper.qryGirdData(rec.data.formId);	
		            }
		        }
		    })
	    });
		
		
		return menuGrid;
	}
	
	//
	this.qryGirdData = function(formId){
			
			Ext.getCmp('indexGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('indexGrid').store.lastOptions != null){
							var nodeName = (Ext.getCmp('nodGrid').getSelectionModel().getSelections()[0]==undefined ||Ext.getCmp('nodGrid').getSelectionModel().getSelections()[0]==null)?'0':Ext.getCmp('nodGrid').getSelectionModel().getSelections()[0].data.nodeName;
							Ext.getCmp('indexGrid').store.baseParams = {formId:formId, nodeName:nodeName};
						}
					}
			)
			Ext.getCmp('indexGrid').store.removeAll() ;
			Ext.getCmp('indexGrid').store.load({params:{start:0, limit:50}});
		
	}
	
	this.qryButGirdData = function(formId){
			
			Ext.getCmp('butGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('butGrid').store.lastOptions != null){
							
							Ext.getCmp('butGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('butGrid').store.removeAll() ;
			Ext.getCmp('butGrid').store.load({params:{start:0, limit:50}});
	}
	this.qryNodeGirdData = function(formId){
			
			Ext.getCmp('nodGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('nodGrid').store.lastOptions != null){
							
							Ext.getCmp('nodGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('nodGrid').store.removeAll() ;
			Ext.getCmp('nodGrid').store.load({params:{start:0, limit:50}});
	}
	this.qryStatGirdData = function(formId){
			
			Ext.getCmp('statGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('statGrid').store.lastOptions != null){
							
							Ext.getCmp('statGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('statGrid').store.removeAll() ;
			Ext.getCmp('statGrid').store.load({params:{start:0, limit:50}});
	}
	this.qryTransGirdData = function(formId){
			
			Ext.getCmp('transGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('transGrid').store.lastOptions != null){
							
							Ext.getCmp('transGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('transGrid').store.removeAll() ;
			Ext.getCmp('transGrid').store.load({params:{start:0, limit:50}});
	}
	this.qryTabGirdData = function(formId){
			
			Ext.getCmp('tabGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('tabGrid').store.lastOptions != null){
							
							Ext.getCmp('tabGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('tabGrid').store.removeAll() ;
			Ext.getCmp('tabGrid').store.load({params:{start:0, limit:50}});
	}
	
	this.qryPosiGirdData = function(formId){
		
		Ext.getCmp('posiGrid').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('posiGrid').store.lastOptions != null){
						Ext.getCmp('posiGrid').store.baseParams = {formId:formId, optType:'ALL', pagin:'Y'};
					}
				}
		)
		Ext.getCmp('posiGrid').store.removeAll() ;
		Ext.getCmp('posiGrid').store.load({params:{start:0, limit:50}});
	}
	
	this.initIndPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'属性ID',dataIndex:'filedId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'是否显示ID',dataIndex:'isDisplay',hidden:true },
		    {header:'是否传值ID',dataIndex:'isPassValue',hidden:true },
            {header:'组件类型ID',dataIndex:'filedType',hidden:true},
		    {header:'字段名称',dataIndex:'filedName',width:100},
		    {header:'中文名称',dataIndex:'filedLable',width:100},
		    {header:'所属节点',dataIndex:'nodeName',width:100},
		    {header:'组件类型',dataIndex:'typeName',width:100},
		    {header:'是否显示',dataIndex:'isDisplayName',width:70},
		    {header:'是否传值',dataIndex:'isPassValueName',width:70},
		    {header:'组件属性编码',dataIndex:'attrCode',width:100},
			{header:'事件编码',dataIndex:'actionCode',width:70},
		    {header:'数据来源',dataIndex:'dataForm',width:70,renderer:function(v){return v=='1'?'静态数据':'业务系统'}},
		    {header:'事件类型',dataIndex:'actionType',width:70,renderer:function(v){return v=='1'?'弹出事件':'打开页面'}},
		    {header:'事件函数',dataIndex:'actionEvent',width:100},
			{header:'是否主键',dataIndex:'isKeyid',width:70,renderer:function(v){return v=='1'?'是':'否';}},
			{header:'是否查询',dataIndex:'isSearchField',width:70,renderer:function(v){return v=='1'?'是':'否';}},
			{header:'显示位置',dataIndex:'position',width:70},
			{header:'默认值',dataIndex:'defaultValue',width:70},
			{header:'是否显示中文',dataIndex:'isShowLabel',width:100,renderer:function(v){return v=='1'?'是':'否';}},
		    {header:'是否必填',dataIndex:'isRequired',width:70},		    
		    {header:'下拉数据',dataIndex:'checkedData',width:150},
		    {header:'选择数据',dataIndex:'selectData',width:150},
			{header:'显示顺序',dataIndex:'seqId',width:70},
		    {header:'同级顺序',dataIndex:'indexId',width:70}
			
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'indexGrid',
	    	title : '页面字段配置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelFiledInfoAction'
			
		});
		return gridPanel;
    }
	this.initButPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'按钮ID',dataIndex:'buttonId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
            {header:'菜单ID',dataIndex:'muneId',hidden:true },
            {header:'下页面ID',dataIndex:'nextFormId',hidden:true },
            {header:'是否显示Id',dataIndex:'isShow',hidden:true },
		    {header:'是否底部Id',dataIndex:'isBottom',hidden:true },
		    {header:'排版方式Id',dataIndex:'orientation',hidden:true },
		    {header:'按钮类型Id',dataIndex:'buttonType',hidden:true },
		    {header:'操作名称',dataIndex:'buttonName',width:100},
		    {header:'下一页面',dataIndex:'formName',width:100},
		    {header:'页面代码',dataIndex:'toPage',width:100},
		    {header:'调用方法',dataIndex:'getMethod',width:100},
		    {header:'权限代码',dataIndex:'privCode',width:100},
		    {header:'是否显示',dataIndex:'isShowName',width:100},
		    {header:'是否底部',dataIndex:'isBottomName',width:100},
		    {header:'排版方式',dataIndex:'orientationName',width:100},
		    {header:'按钮类型',dataIndex:'buttonTypeName',width:100},
		    {header:'顺序',dataIndex:'buttonSequ',width:100},
		    {header:'提示信息',dataIndex:'errorInfo',width:100}
		    
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'butGrid',
	    	title : '页面操作配置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelButtonRightAction'
			
		});
		return gridPanel;
    }
    
    this.initNodePanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'节点ID',dataIndex:'nodeId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'节点',dataIndex:'nodeName',width:150},
		    {header:'节点名',dataIndex:'nodeLabel',width:150},
		    {header:'是否子节点',dataIndex:'isLeaf',width:150},
		    {header:'顺序',dataIndex:'seqId',width:150}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'nodGrid',
	    	title : '页面字段节点设置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelFiledNodeAction'
			
		});
		return gridPanel;
    }
	//定义菜单目录树事件

	this.initTreeEvent = function(){
		Ext.getCmp('moduelTree').on('click', function(node){ //使节点可以单击展开收起，默认是双击的

		    if(node.isLeaf() == false){
				if(node.expanded == false){
					node.expand();//展开
				}else{
					node.collapse();//收起
				} 
		    }
		    moduleId = node.id ;

            //var selProductId = Ext.getCmp("selProductId").getValue() ;
            Ext.getCmp('menuGrid').store.on('beforeload',
            	function(store){ 
					store.baseParams = {start:0,limit:50,moduleId:moduleId};//根据moduleId来载入
				}
			)
		   
		    Ext.getCmp('menuGrid').store.removeAll() ;
			Ext.getCmp('menuGrid').store.load();
	    });
	    
	    Ext.getCmp('moduelTree').on("contextmenu",oper.menuContextMenu,Ext.getCmp('moduelTree'));
	}
	
	//定义菜单目录右键菜单
	this.initTreeMenu = function(){
		var treeMenu = new Ext.menu.Menu({
	        id : 'treeMenu'
		});
		return treeMenu ;
	}
	
	//装菜单目录邮件菜单

	this.menuContextMenu = function(node,e){
		var treeMenu = Ext.getCmp('treeMenu');
    	node.select();
    	var parentId = node.attributes.parentId ;
    	var pathCode = node.attributes.pathCode ;
    	
    	var i = 1 ;
    	treeMenu.removeAll();
    	if(parentId == 'null'){       //根目录
    	    treeMenu.insert(i++,new Ext.menu.Item({ text: '增加子菜单' ,handler: function() {
				treeMenu.hide();
				oper.subModuelAdd();
			}}));
    	}else if(parentId == 0){
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '修改菜单' ,handler: function() {
				treeMenu.hide();
				oper.moduelMod();
			}}));
			treeMenu.insert(i++,new Ext.menu.Item({ text: '删除菜单' ,handler: function() {
					treeMenu.hide();
					oper.muduleDelVlt();
				}}));
				//暂时不支持多级菜单
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加子菜单' ,handler: function() {
				treeMenu.hide();
				oper.subModuelAdd();
			}}));
    	}else{
    	    var pathCodes = pathCode.split("/");  //只能创建二级菜单，手机中好控制菜单权限
    	    
    	    treeMenu.insert(i++,new Ext.menu.Item({ text: '修改菜单' ,handler: function() {
					treeMenu.hide();
					oper.moduelMod();
				}}));
	    		treeMenu.insert(i++,new Ext.menu.Item({ text: '删除菜单' ,handler: function() {
					treeMenu.hide();
					oper.muduleDelVlt();
				}}));
            if(pathCodes.length<2){
           /*
		    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加菜单' ,handler: function() {
						treeMenu.hide();
						oper.moduelAdd();
					}}));		
					treeMenu.add(new Ext.menu.Separator());
					i++ ;
			*/	
			/*	
		    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加子菜单' ,handler: function() {
						treeMenu.hide();
						oper.subModuelAdd();
					}}));
			*/	
			}
			
    	}
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
    }
    
    this.moduelAdd = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.parentNode.text ;
    	var pmId = selModuleNode.parentNode.id ;
    	
    	moduelOper.showModuleInfoPage('add',pmName,pmId);
    }
    
    this.moduelMod = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.parentNode.text ;
    	var pmId = selModuleNode.parentNode.id ;
    	
    	moduelOper.showModuleInfoPage('mod',pmName,pmId);
    }
    
    this.muduleDelVlt = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var selMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();

		if(selModuleNode.childNodes.length > 0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '此菜单下有子节点,不能删除!',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
			return false;
		}
		if(selMenu.length > 0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '此菜单下有菜单列表,不能删除!',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
			return false;
		}
		
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.moduleDel);
    }
    
    this.moduleDel = function(btn){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	if(btn == 'no'){
    		return ;
    	}
		
		var paramObj = new Object();
  			paramObj.muneId = selModuleNode.id;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertMobMuneAction", paramObj);
			 				
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
            Ext.example.msg('','删除菜单成功！');
        }, 1000);
        Ext.getCmp('moduelTree').root.reload();
    }
    
    this.subModuelAdd = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.text ;
    	var pmId = selModuleNode.id ;
    	
    	moduelOper.showModuleInfoPage('addSub',pmName,pmId);
    }
    
    //定义菜单列表菜单
    this.initGridMenu = new function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	this.initGridEvent = function(){
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//菜单组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加页面' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改页面' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除页面' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.menuDel);
		}}));
		/*
		rightClick.add(new Ext.menu.Separator());
		i++ ;
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '更改菜单' ,handler: function() {
			rightClick.hide();
			oper.menuModuelMod();
		}}));
		*/
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
		var moduleItem = Ext.getCmp('moduelTree').getSelectionModel().selNode;
		//如果是非叶子菜单，则不能增加页面 mod by li.guoyang
		if(moduleItem==null||moduleItem.leaf){
			nullRightClick.removeAll();
			//rightClick.removeAll();
			nullRightClick.add(new Ext.menu.Item({ text: '增加页面' ,handler: function() {
				nullRightClick.hide();
				oper.menuAdd();
			}}));
			nullRightClick.showAt(e.getXY());
		}else{
			e.stopEvent();
		}
    	
    }
    
    //定义页面属性列表事件
	this.initIndGridEvent = function(){
		Ext.getCmp('indexGrid').addListener('rowcontextmenu', oper.rightClickFn2);
		Ext.getCmp('indexGrid').addListener('contextmenu', oper.nullRightClickFn2);
	}
	
	//页面属性组装
	this.rightClickFn2 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '配置字段' ,handler: function() {
			rightClick.hide();
			oper.indeAdd();
		}}));
		
	   rightClick.insert(i++,new Ext.menu.Item({ text: '修改字段' ,handler: function() {
			rightClick.hide();
			oper.indeMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除字段' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', indeOper.moduleDel);
		}}));
		
		Ext.getCmp('indexGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn2 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '配置字段' ,handler: function() {
			nullRightClick.hide();
			oper.indeAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
     //定义页面操作按钮列表事件
	this.initButGridEvent = function(){
		Ext.getCmp('butGrid').addListener('rowcontextmenu', oper.rightClickFn3);
		Ext.getCmp('butGrid').addListener('contextmenu', oper.nullRightClickFn3);
	}
	
	//页面操作按钮组装
	this.rightClickFn3 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '操作按钮配置' ,handler: function() {
			rightClick.hide();
			oper.butAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改操作按钮' ,handler: function() {
			rightClick.hide();
			oper.butMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除操作按钮' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', priButOper.moduleDel);
		}}));
	
		
		Ext.getCmp('butGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn3 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '操作按钮配置' ,handler: function() {
			nullRightClick.hide();
			oper.butAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
     //定义页面字段节点列表事件
	this.initNOdeGridEvent = function(){
		Ext.getCmp('nodGrid').addListener('rowcontextmenu', oper.rightClickFn4);
		Ext.getCmp('nodGrid').addListener('contextmenu', oper.nullRightClickFn4);
	}
	
	//页面节点组装
	this.rightClickFn4 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '节点配置' ,handler: function() {
			rightClick.hide();
			oper.nodeAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改节点' ,handler: function() {
			rightClick.hide();
			oper.nodeMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除节点' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', priNodeOper.moduleDel);
		}}));
	
		
		Ext.getCmp('nodGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn4 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '节点配置' ,handler: function() {
			nullRightClick.hide();
			oper.nodeAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //菜单方法
    this.menuAdd = function(){
    	menuOper.showMenuInfoPage('add');
    }
    
    this.menuMod = function(){
    	menuOper.showMenuInfoPage('mod');
    }
    
    this.menuDel = function(btn){
    	if(btn == 'no'){
    		return ;
    	}
    	var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		
		var paramObj = new Object();
  			paramObj.formId = selItem[0].data.formId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertJsonCreateAction", paramObj);
		
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
            Ext.example.msg('','成功删除菜单！');
        }, 1000);
        
        Ext.getCmp('menuGrid').store.removeAll();
        Ext.getCmp('menuGrid').store.reload();
    }
    
    this.menuModuelMod = function(){
    	//menuSelOper.showModuleSelPage();
    }
    
    //页面属性方法
    this.indeAdd = function(){
            indeOper.showMenuInfoPage('add');
    }
    this.indeMod = function(){
    	    indeOper.showMenuInfoPage('mod');
    }
    
     //页面操作方法
    this.butAdd = function(){
            priButOper.showMenuInfoPage('add');
    }
    this.butMod = function(){
            priButOper.showMenuInfoPage('mod');
    }
    
      //页面操作方法
    this.nodeAdd = function(){
            priNodeOper.showMenuInfoPage('add');
    }
    this.nodeMod = function(){
            priNodeOper.showMenuInfoPage('mod');
    }
    
    //统计界面布局
    ///////////////////////////////////////
    this.initStatPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'布局ID',dataIndex:'statId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'统计周期ID',dataIndex:'statCycle',hidden:true },
		    {header:'统计范围ID',dataIndex:'statRange',hidden:true },
		    {header:'是否主页ID',dataIndex:'isMainPage',hidden:true },
		    {header:'统计周期',dataIndex:'statCycleName',width:150},
		    {header:'统计范围',dataIndex:'statRangeName',width:150},
		    {header:'是否主页',dataIndex:'isMainPageName',width:150},
		    {header:'统计方式ID',dataIndex:'statType',hidden:true },
		    {header:'统计方式',dataIndex:'statTypeName',width:150},
		    {header:'其他别名',dataIndex:'statCycleDisplayName',hidden:true },
		    {header:'省级别名',dataIndex:'prvDisplayName',hidden:true},
		    {header:'市级别名',dataIndex:'cityDisplayName',hidden:true},
		    {header:'省级对应页面ID',dataIndex:'prvNextFormId',hidden:true },
		    {header:'省级对应页面',dataIndex:'prvNextFormIdName',width:150},
		    {header:'市级对应页面ID',dataIndex:'cityNextFormId',hidden:true },
		    {header:'市级对应页面',dataIndex:'cityNextFormIdName',width:150},
		    {header:'每行记录数',dataIndex:'rowCount',width:150},
		    {header:'最后一行ID',dataIndex:'isSum',hidden:true },
		    {header:'最后一行',dataIndex:'isSumName',width:150}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'statGrid',
	    	title : '统计界面布局',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelStatDisplayAction'
			
		});
		return gridPanel;
    }
    //定义统计界面布局列表事件
	this.initStatGridEvent = function(){
		Ext.getCmp('statGrid').addListener('rowcontextmenu', oper.rightClickFn5);
		Ext.getCmp('statGrid').addListener('contextmenu', oper.nullRightClickFn5);
	}
	
	//统计界面布局组装
	this.rightClickFn5 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '界面布局配置' ,handler: function() {
			rightClick.hide();
			oper.statAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改界面布局' ,handler: function() {
			rightClick.hide();
			oper.statMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除界面布局' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', statOper.moduleDel);
		}}));
	
		
		Ext.getCmp('statGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn5 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '界面布局配置' ,handler: function() {
			nullRightClick.hide();
			oper.statAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

      //界面布局操作方法
    this.statAdd = function(){
            statOper.showMenuInfoPage('add');
    }
    this.statMod = function(){
            statOper.showMenuInfoPage('mod');
    }
   
   
    //统计转取设置
    ///////////////////////////////////////
    this.initTransPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'转取ID',dataIndex:'statId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'转取页面属性ID',dataIndex:'nextFiledId',hidden:true },
		    {header:'下一页面表ID',dataIndex:'nextFormId',hidden:true },
		    {header:'操作类型ID',dataIndex:'operateType',hidden:true },
		    {header:'操作名称',dataIndex:'operateName',width:150},
		    {header:'操作类型',dataIndex:'operateTypeName',width:150},
		    {header:'转取页面属性名',dataIndex:'nextFiledIdName',width:150},
		    {header:'下一页面表名',dataIndex:'nextFormIdName',width:150}
		    	   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'transGrid',
	    	title : '统计转取设置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelStatTransferAction'
			
		});
		return gridPanel;
    }
    //定义统计转取设置列表事件
	this.initTransGridEvent = function(){
		Ext.getCmp('transGrid').addListener('rowcontextmenu', oper.rightClickFn6);
		Ext.getCmp('transGrid').addListener('contextmenu', oper.nullRightClickFn6);
	}
	
	//统计转取设置组装
	this.rightClickFn6 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '转取设置配置' ,handler: function() {
			rightClick.hide();
			oper.transAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改转取设置' ,handler: function() {
			rightClick.hide();
			oper.transMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除转取设置' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', transOper.moduleDel);
		}}));
	
		
		Ext.getCmp('transGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn6 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '转取设置配置' ,handler: function() {
			nullRightClick.hide();
			oper.transAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

      //统计转取设置操作方法
    this.transAdd = function(){
            transOper.showMenuInfoPage('add');
    }
    this.transMod = function(){
            transOper.showMenuInfoPage('mod');
    }
    
    
    function renderPosition(v){
		switch(v){
			case null:
				return '无';
				break;
			case 1:
				return '顶部';
				break;
			case 2: 
				return '底部';
				break;
			case 3: 
				return '任意布局';
				break;
			default: 
				return 'error';
		}
	}
    
    //查询TAB页配置
    ///////////////////////////////////////
    this.initTabPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'查询TAB页ID',dataIndex:'tabId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'TAB页名称',dataIndex:'tabName',width:150},    
		    {header:'对应页面ID',dataIndex:'nextFormId',hidden:true },
		    {header:'对应页面',dataIndex:'nextFormIdName',width:150},
		    {header:'顺序',dataIndex:'seqId',width:150},
		    {header:'是否显示',dataIndex:'isShow',width:150},
		    {header:'显示坐标',dataIndex:'position',width:150,renderer:function(v){return renderPosition(v);}}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'tabGrid',
	    	title : '查询TAB页配置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelSearchTabAction'
			
		});
		return gridPanel;
    }
    //定义查询TAB页配置列表事件
	this.initTabGridEvent = function(){
		Ext.getCmp('tabGrid').addListener('rowcontextmenu', oper.rightClickFn7);
		Ext.getCmp('tabGrid').addListener('contextmenu', oper.nullRightClickFn7);
	}
	
	//查询TAB页配置组装
	this.rightClickFn7 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '查询TAB页配置' ,handler: function() {
			rightClick.hide();
			oper.tabAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改查询TAB页' ,handler: function() {
			rightClick.hide();
			oper.tabMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除查询TAB页' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', tabOper.moduleDel);
		}}));
	
		
		Ext.getCmp('tabGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	this.nullRightClickFn7 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '查询TAB页配置' ,handler: function() {
			nullRightClick.hide();
			oper.tabAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

      //查询TAB页配置操作方法
    this.tabAdd = function(){
            tabOper.showMenuInfoPage('add');
    }
    this.tabMod = function(){
            tabOper.showMenuInfoPage('mod');
    }
   
    //####页面定位配置####
    this.initPosiPanel = function() {
		var column = new Ext.grid.ColumnModel([
  			{header:'位置ID',dataIndex:'positionId',hidden:true },
  		    {header:'页面ID',dataIndex:'formId',hidden:true },
  		    {header:'对应页面ID',dataIndex:'nextFormId',hidden:true },
  		    {header:'对应页面',dataIndex:'nextFormIdName',width:150},
  		    {header:'顺序',dataIndex:'seqNo',width:150},
  		    {header:'X轴',dataIndex:'x',width:150},
  		  	{header:'Y轴',dataIndex:'y',width:150}
  		]); 
		
   		var gridPanel = new ZTESOFT.Grid({
   	    	id:'posiGrid',
   	    	title : '页面定位配置',
   	    	region: 'center',
   	    	width:10,
   	    	//tbar:this.initGridToolsBar(),
   	        pageSize:50,
   	        cm:column,
   	        paging:true,
   	        url:'/MOBILE/ExtServlet?actionName=system/SelMobileFormPositionAction'
   		});
   		
   		return gridPanel;
    }
    
    //定义查询TAB页配置列表事件
	this.initPosiGridEvent = function(){
		Ext.getCmp('posiGrid').addListener('rowcontextmenu', oper.rightClickFn8);
		Ext.getCmp('posiGrid').addListener('contextmenu', oper.nullRightClickFn8);
	}
    
	//查询TAB页配置组装
	this.rightClickFn8 = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '页面定位配置' ,handler: function() {
			rightClick.hide();
			posiOper.showMenuInfoPage('add');
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改页面定位' ,handler: function() {
			rightClick.hide();
			posiOper.showMenuInfoPage('mod');
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除页面定位' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', posiOper.moduleDel);
		}}));
	
		
		Ext.getCmp('posiGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn8 = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '页面定位配置' ,handler: function() {
			nullRightClick.hide();
			posiOper.showMenuInfoPage('add');
		}}));
    	nullRightClick.showAt(e.getXY());
    }
}
function renderMethodType(v){
	switch(v){
		case '1':
			return '类名';
			break;
		case '2': 
			return '接口';
			break;
		case '3': 
			return 'servlet';
			break;
		case '4': 
			return '测试数据';
			break;	
		default: 
			return 'error';
	}
}
</script>