<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<link rel="stylesheet" type="text/css" href="style/im.css" />
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>群成员添加</title>
	</head> 
	<body >
		<div id='groupStaffAdd'></div>
	</body>
</html>
<script>
var opener = art.dialog.data('opener');//window.dialogArguments;
var oper = new Oper();
oper.init();
Ext.onReady(function(){
		oper.win.show();
		
});
function Oper(){
	this.init = function(){
		 this.groupStaffTree = this.initGroupStaffTree();
	     this.staffGrid = this.initStaffGrid();
		 this.win = this.initWin();
		 this.staffGrid.addRow = function(data){
			var row = new Ext.data.Record(data); 
			oper.staffGrid.getStore().add(row);
		 }
		 this.staffGrid.getRowByStaffId = function(staffId){
		 	 for(var i=0;i< oper.staffGrid.getStore().getCount();i++){
				if(staffId== oper.staffGrid.getStore().getAt(i).data.staffId){
					var obj = new Object();	
					obj.staffId = oper.staffGrid.getStore().getAt(i).data.staffId;
					obj.staffName = oper.staffGrid.getStore().getAt(i).data.staffName;
					return obj;
				}
			}
			return null;
		 }
		 //删除配置数据
		 this.staffGrid.delData = function(){
			 var g1Rows = oper.staffGrid.getSelectionModel().getSelections();
			 if(Ext.isEmpty(g1Rows)){
			 	return;
			 }
			 for(var i=0;i<g1Rows.length;i++){
			 	oper.staffGrid.getStore().remove(g1Rows[i]);
			 }
			 
		}
	}
	this.initWin = function(){
		var win = new Ext.Window({
       		id:'typeOrgWin',
            closable:false,
            width:550,
            height:450,
            x:0,
            y:0,
            plain:true,
            layout:'border',
            items:[this.groupStaffTree,this.staffGrid],
            buttonAlign:'center',
            buttons: [{
	            text: '确定',
	            onClick:function(){
	              	
	              	var staffs =[];
	              	for(var i =0;i<oper.staffGrid.getStore().getCount();i++){
	              		var obj ={};
	              		obj.staffId = oper.staffGrid.getStore().getAt(i).data.staffId;
	              		obj.staffName = oper.staffGrid.getStore().getAt(i).data.staffName;
	              		staffs[staffs.length] = obj;
	              	}
	            	//window.returnValue = staffs;
	            	art.dialog.data('staffs', staffs);

	            	//window.close();
	            	art.dialog.close();
	            }
	        },{
	            text: '取消',
	            onClick:function(){
	            	//window.close();
	            	art.dialog.close();
	            }
	        }]
        });
        return win;
	}
	this.initGroupStaffTree = function(){
		var groupStaffTree = new Ext.tree.TreePanel({
			id:'groupStaffTree',
			title: '我的好友',
			region: 'center',
			useArrows: true,
			autoScroll: true,
			animate: true,
        	enableDD: false,
        	containerScroll: true,
			border:false,
			rootVisible:false,
			dataUrl:'/MOBILE/ExtPagingServlet?actionName=QryOnlineStaffGroupAction',
			root:new Ext.tree.AsyncTreeNode()
		});
		 Ext.getCmp('groupStaffTree').on('dblclick',function(item){
		 	if(item.isLeaf()){
		 		var obj = new Object();
		 		obj.staffId = item.id;
		 		obj.staffName = item.text;
		 		if(oper.staffGrid.getRowByStaffId(obj.staffId)==null){
		 			oper.staffGrid.addRow(obj);
		 		}
		 	}else{
		 		if(!Ext.isEmpty(item.attributes.children.length)){
		 			for(var i=0;i<item.attributes.children.length;i++){
		 				var obj = new Object();
		 				obj.staffId = item.attributes.children[i].id;
		 				obj.staffName = item.attributes.children[i].text;
		 				if(oper.staffGrid.getRowByStaffId(obj.staffId)==null){
		 					oper.staffGrid.addRow(obj);
		 				}
		 				
		 			}
		 		}
		 	}
		 		
		  }) ; 
		return groupStaffTree;
	}
	this.initStaffGrid = function(){
		
		var column = new Ext.grid.ColumnModel([
		    {header:'姓名',dataIndex:'staffName',width:200 }
		   
		]); 
		var staffGrid = new ZTESOFT.Grid({
	    	id:'staffGrid',
	    	title : '已添加人员',
	    	region: 'east',
	    	width:300,
	    	pageSize:10,
	    	tbar:this.initGridTbar(),
	        cm:column
		});
		staffGrid.on("rowdblclick",function(){
			oper.staffGrid.delData();
		});
		return staffGrid;
	}
	this.initGridTbar = function(){
		var tb = new Ext.Toolbar();
		tb.add({
            text:'删除成员',
             onClick:function(){
            	oper.staffGrid.delData();
            }
        });
		 
        return tb;
	}
	
	
}		
</script>

