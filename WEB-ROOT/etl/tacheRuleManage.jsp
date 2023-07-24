<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>ETL任务单派发规则管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>
						
	</body>
</html>
<script type="text/javascript"	src="js/tacheRuleManage.js"></script>
<script type="text/javascript" src="/MOBILE/common/ztesoftext/staffWin.js"></script>
<script type="text/javascript" src="/MOBILE/common/ztesoftext/jobWin.js"></script>
<script type="text/javascript" src="/MOBILE/common/ztesoftext/orgWin.js"></script>
<script language="JScript">
/**	by tang.lin **/
//全局变量
//获取分辨率


var session1 = GetSession();
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var body_height = Ext.getBody().getSize().height;
var body_width =Ext.getBody().getSize().width;
var cnt = 15; //列表每页显示多少条数据


var oper = new Oper();
var manager = new TacheRuleManager();

Ext.onReady(function(){
	    var tree = oper.initTreePanel();
		var mainPanel = oper.initMainPanel();
		
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			margins: '5 5 5 5',
			items:[tree,mainPanel]
	});
});
//操作类定义



function Oper(){
	//初始化主面板
	
	this.initMainPanel = function(){
		 //查询面板
		 var tachePanel = this.initTacheGrid();
		 //列表
		 var rulePanel = this.initRuleGrid();
		 //主面板


		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[tachePanel,rulePanel]
	 	});
	 	return mainPanel;
	}
	
	this.initTreePanel = function(){
		 //qery
		 var qryFrom = this.initQry();
		 //tree
		 var tree = this.initTaskCatalog();
		 //主面板


		 var treePanel = new Ext.Panel({
	 		region: 'west',
	 		layout:'border',
	 		width: body_width * 0.2,
	 		items:[tree,qryFrom]
	 	});
	 	return treePanel;	
	}
	
	this.initQry = function(){
		var qryForm = new Ext.FormPanel({
			id:'qryForm',
			region: 'center',
			height: body_height * 0.4,
			frame:true,
			//title:'查询条件',
			labelWidth: 60,
	        //collapsible:true,
	        //width:200,
	        defaultType: 'textfield',
	        items:[{
		    		fieldLabel:'环节名称',
		            name:'tacheName',
		            id:'tacheName',
		            width:120
	         	},{
		       		xtype:"panel",
		       		layout:"form",
		       		items:[{
		       			xtype:'textfield',
						fieldLabel:'环节编码',
			            name:'tacheCode',
			            id:'tacheCode',
			            width:120
		       		}]
		       	}],
	        buttons:[{
	        	text: '查询',
	        	onClick:function(){
	        		oper.qryTacheGrid();
	        	}
	        },{
	        	text: '重置',
	        	onClick:function(){
	        		Ext.getCmp('tacheName').setValue('');
					Ext.getCmp('tacheCode').setValue('');	
	        	}
	        }]
		});	
		return qryForm;
	};
	
	
	this.initTaskCatalog = function(){
		var cataTree = new Ext.tree.TreePanel({
			id: 'cataTree',
	    	title:'任务目录',
	    	region: 'north',
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({//QryTacheCataTreeAction
	        	dataUrl:'/MOBILE/ExtServlet?actionName=etl/QryUosTacheCataTreeAction'
	        }),
	        containerScroll: true,
	        border: false,
	        height: body_height * 0.6,
		    //height:Ext.getBody().getSize().height,
	        rootVisible: false,
	        root: new Ext.tree.AsyncTreeNode({
	            id:'-1'
		    })
		 });
			this.initTreeEvent();
			return cataTree;
	}
	//初始化组织树事件
	this.initTreeEvent = function(){
		Ext.getCmp('cataTree').on('click', function(node){ //使节点可以单击展开收起，默认是双击的


		    oper.qryTacheGrid(node);
	    });
	    
			   
	}

	//查询任务列表
	this.qryTacheGrid = function(node){
		//目录树当前节点 node
		var tacheCatalogId = 0;
		if(node==null){
			//return;
		}else{
			tacheCatalogId = node.id;
		}
		//绑定发送请求参数


		Ext.getCmp('tache_grid').store.on('beforeload',function(store){
			//获取查询参数
			var tacheCode = Ext.getCmp("tacheCode").getValue() ;
	        var tacheName  = Ext.getCmp("tacheName").getValue() ;
			
    		if(Ext.getCmp('tache_grid').store.lastOptions != null){
				Ext.getCmp('tache_grid').store.baseParams = {tacheCode:tacheCode,tacheName:tacheName,tacheCatalogId:tacheCatalogId};
			}
		});
		Ext.getCmp('tache_grid').store.removeAll() ;
		Ext.getCmp('tache_grid').store.load({params:{start:0, limit:cnt}});
		//load数据后自动选择第一行数据,load事件为加载数据完成后触发
		Ext.getCmp('tache_grid').store.on('load',function(){
			Ext.getCmp('tache_grid').getSelectionModel().selectFirstRow();
		});
			
	}
		
	this.initTacheGrid = function(){
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'任务标识',dataIndex:'tacheId',hidden:true },
		    {header:'目录标识',dataIndex:'tacheCatalogId',hidden:true },
		    {header:'状态',dataIndex:'state',hidden:true },
		    {header:'任务编码',dataIndex:'tacheCode',width:swidth*0.2},
		    {header:'任务名称',dataIndex:'tacheName',width:swidth*0.2},
		    {header:'版本',dataIndex:'edition',width:swidth*0.1},
		    {header:'操作时间',dataIndex:'stateDate',width:swidth*0.15}
		   
		]);
		  //人员信息
		var grid =  new ZTESOFT.Grid({
			id:'tache_grid',
	    	region: 'north',
	    	height: body_height*0.5,
	        title:'流程环节列表',
			cm:column,
			pageSize:cnt,
			paging:true,
			//tbar:oper.initGridToolsBar(),
			url:'/MOBILE/ExtServlet?actionName=etl/QryTacheListExtAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	oper.qryRule(rec.data.tacheId);
	                        }
	                    }
	        })
	    });
	   
	    return grid;
	
	}
	
	this.qryRule = function(tacheId){
		if(tacheId==null || tacheId.length==0){
			return;
		}
		Ext.getCmp('rule_grid').store.on('beforeload',function(store){
    		if(Ext.getCmp('rule_grid').store.lastOptions != null){
				Ext.getCmp('rule_grid').store.baseParams = {tacheId:tacheId};
			}
		});
		Ext.getCmp('rule_grid').store.removeAll() ;
		Ext.getCmp('rule_grid').store.load({params:{start:0, limit:cnt}});
		//load数据后自动选择第一行数据,load事件为加载数据完成后触发
		Ext.getCmp('rule_grid').store.on('load',function(){
			Ext.getCmp('rule_grid').getSelectionModel().selectFirstRow();
		});	
	}

	this.initRuleGrid = function(){
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'规则标识',dataIndex:'ruleId',hidden:true },
		    {header:'环节标识',dataIndex:'tacheId',hidden:true },
		    {header:'执行人标识',dataIndex:'partyId',hidden:true },
		    {header:'规则名称',dataIndex:'ruleName',width:swidth*0.2},
		    {header:'执行人类型',dataIndex:'partyType',width:swidth*0.2,renderer:changeParty},
		    {header:'执行人名称',dataIndex:'partyName',width:swidth*0.15}
		   
		]);
		  //人员信息
		var grid =  new ZTESOFT.Grid({
			id:'rule_grid',
	    	region: 'center',
	    	height: body_height*0.5,
	        title:'ETL任务单派发规则列表',
			cm:column,
			pageSize:cnt,
			paging:true,
			tbar:oper.initGridToolsBar(),
			url:'/MOBILE/ExtServlet?actionName=etl/QryTacheRuleListExtAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	
	                        }
	                    }
	        })
	    });
	   
	    return grid;
	
	}

	this.initGridToolsBar = function(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'增加',
            onClick:function(){
            	oper.detail('add');
            }
        });
        tb.add({
            text:'修改',
            onClick:function(){
            	oper.detail('mod');
            }
        });
        tb.add({
            text:'删除',
            onClick:function(){
            	oper.del();
            }
        });
		 
        return tb;
	}	

	this.del = function (){
		var record = Ext.getCmp('rule_grid').getSelectionModel().getSelected();
		if(record){
			Ext.Msg.confirm("删除提示","确定删除所选规则吗?",function(btn){
				if(btn=="yes"){
					delServDefine(record.data.ruleId,record.data.tacheId);
				}
			});		
		}else{
			Ext.Msg.alert("操作提示","请选择需要删除的规则!");
		}
	}
		
	function delServDefine(ruleId,tacheId){
		
		var servDefine = new Object();
	    servDefine.ruleId = ruleId;
	    //servDefine.operManId = session1.staff.staffId;
		//servDefine.operManName = session1.staff.staffName;
	    var result = invokeAction("/etl/DelEtlTacheRuleAction",servDefine);
	   
   		if("success"==result){
   			Ext.example.msg("提示",'删除规则成功');
   			oper.qryRule(tacheId);	
   		}else{
   			Ext.example.msg("提示",'删除规则失败');
   		}		
	}
		
	this.detail = function (operStr){
		var rec = Ext.getCmp('tache_grid').getSelectionModel().getSelected();
		if(rec==null){
			Ext.Msg.alert('操作提示','请先选择一个环节');
			return;
		}
		var param = new Object();
		param.tacheId = rec.data.tacheId;
		if(operStr == 'add'){
			
		}else if(operStr == 'mod'){
			var rule = Ext.getCmp('rule_grid').getSelectionModel().getSelected();
			if(rule==null){
				Ext.Msg.alert('操作提示','请先选择一个规则');
				return;
			}
		
			param.ruleId = rule.data.ruleId;
			param.ruleName = rule.data.ruleName;
			param.partyId = rule.data.partyId;
			param.partyName = rule.data.partyName;
			param.partyType = rule.data.partyType;
		}
		
		
		manager.initWindow(operStr,param);
		//alert(rec.data.tacheId);
	}
		
}

////执行人类型  1人员  2职位  3 组织  4系统
function changeParty(val){
   		
	if(val == 1){
		return '人员';
	}else if(val == 2){
		return '职位';
	}else if(val == 3){
		return '组织';
	}else if(val == 4){
		return '系统';
	}
	return val;
}
</script>