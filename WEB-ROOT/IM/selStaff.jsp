<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery-powerFloat-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery-smartMenu.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery-class.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.core-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.widget-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.mouse-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.draggable-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.droppable-min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.sortable.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.effect.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.effect-fade.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.ui.effect-blind.js"></script>
		<script  type="text/javascript" src="../webos/js/data.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>人员查询</title>
	</head> 
	<body >
		<div id='staffAdd'></div>
	</body>
</html>
<script>
	var opener = art.dialog.data('opener');//window.dialogArguments;
	Ext.onReady(function(){
		var tree = new ZTESOFT.OrgTree({
			id:'orgTree',
	       	region: 'west',
	       	width:getTypeOrgWinWidth()*0.35,
	       	title:'',
	       	allOrgs:true
	       	//checked:true    
		});

	    //创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'姓名',dataIndex:'staffName',width:getTypeOrgWinWidth()*0.2},
		    {header:'用户名',dataIndex:'userName',width:getTypeOrgWinWidth()*0.2},
		    {header:'组织',dataIndex:'orgPathName',width:getTypeOrgWinWidth()*0.235}
		]);

    
		var grid =  new ZTESOFT.Grid({
			id:'grid',
	    	region: 'center',
	        title:'人员列表',
	        width:getTypeOrgWinWidth()*0.65,
			cm:column,
			url:'/MOBILE/ExtPagingServlet?actionName=StaffPagingAction',
			sm: new Ext.grid.RowSelectionModel({
	            singleSelect: true,
	            listeners: {
	                rowselect: function(sm, row, rec) {
	            	}
	            }
	        })
	    });
	    
		tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的



	    	if(node.isLeaf() == false){
	     		if(node.expanded == false){
	     			node.expand();//展开
	     		}else{
	     			node.collapse();//收起
	    		} 
	   		}
		    grid.store.on('beforeload',
				function(store){ 
					if(grid.store.lastOptions != null){
						grid.store.baseParams = {orgId:node.id,qryType:'qrySelf'};
					}
				}
			)
		    grid.store.removeAll() ;
			Ext.getCmp('grid').store.load({params:{start:0, limit:100}});
		
    	});
   		var mainPanel = new Ext.Panel({
   			 title: '人员选择',
   			 region: 'center',
           	 closable:true,
           	 plain:true,
          	  layout:'border',
          	  items:[tree,grid],
          	   buttonAlign:'center',
            buttons: [{
	            text: '确定',
	            onClick:function(){
	              	if(Ext.isEmpty(grid.getSelectionModel().getSelected())){
	            		Ext.MessageBox.show({
				           title: '错误',
				           msg: '请选择人员!',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.ERROR
				       });
				       return;
	            	}
	            	var data = grid.getSelectionModel().getSelected().data;
	            	//window.returnValue = data; 
	      
	            	art.dialog.data('staff', data);
	                art.dialog.close();
	            	//window.close();
	            }
	        },{
	            text: '取消',
	            onClick:function(){
	            	//window.close();
	            	art.dialog.close();
	            }
	        }]
   		});
		var win = new Ext.Viewport({
       		id:'typeOrgWin',
     		layout:'border',
            items:[mainPanel]
           
        });
      	
		
	
	});
//设置查找添加好友的窗口长宽


function getTypeOrgWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.545;
	}else{
		return screen.availWidth*0.595;
	}
}

function getTypeOrgWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.65;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.62;
	}
}	
</script>

