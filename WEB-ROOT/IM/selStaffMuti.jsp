<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	

		<title>人员查询</title>
	</head> 
	<body >
		<div id='staffAdd'></div>
	</body>
</html>
<script>
	var opener = window.dialogArguments;
	Ext.onReady(function(){
		var tree = new ZTESOFT.OrgTree({
			id:'orgTree',
	       	region: 'west',
	       	width:200,
	       	title:'',
	       	allOrgs:true
	       	//checked:true    
		});

	    //创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'姓名',dataIndex:'staffName',width:100},
		    {header:'用户名',dataIndex:'userName',width:100},
		    {header:'组织',dataIndex:'orgPathName',width:150}
		]);

    
		var grid =  new ZTESOFT.Grid({
			id:'grid',
	    	region: 'center',
	        title:'人员列表',
			cm:column,
			url:'/IOMPROJ/ExtPagingServlet?actionName=StaffPagingAction',
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
   
		var win = new Ext.Window({
       		id:'typeOrgWin',
            title: '人员选择',
            closable:true,
            width:550,
            height:450,
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
	            	window.returnValue = data; 
	            	
	            
	            	window.close();
	            }
	        },{
	            text: '取消',
	            onClick:function(){
	            	window.close();
	            }
	        }]
        });
        win.show();			
		
	
	});
		
</script>

