function ProcDefiOper (){
	var tache = null;
	
	this.getTacheStore = function(){
		var tacheStore = new Ext.data.JsonStore({
		    id:'tacheStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        fields: [ 
	        	'tacheId','tacheName','tacheCode','tacheCatalogId'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=etl/QryUosTacheListAction'
	        })	
		});	
		
		return tacheStore;
	}
	
	this.getTacheGrid = function(tacheStore){
		var tacheGrid = new Ext.grid.GridPanel({
			id:'tacheGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width*0.45,
	        store:tacheStore,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : new Ext.grid.GridView({
				sortAscText:'升序',
				sortDescText:'降序',
				columnsText:'列名'
			}),
			cm: new Ext.grid.ColumnModel([
			    {header:'环节名',dataIndex:'tacheName',sortable:true,width:150},
			    {header:'环节编码',dataIndex:'tacheCode',sortable:true,width:150}
			]),
			bbar: new Ext.PagingToolbar({
				pageSize: 15,
				store:tacheStore,
				displayInfo: true,
				displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg: "没有记录"
		    }),
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
	                   tache = rec.data;  				
	               }
				}
			})
	    });		
	    
	    return tacheGrid;
	}
    
    this.getTacheCataTree = function(tacheStore){
		var tacheCataTree = new Ext.tree.TreePanel({
			id:'tacheCataTree',
		    region:'west',
		    width:Ext.getBody().getSize().width*0.15,
	    	useArrows: true,
		    autoScroll: true,
		    animate: true,
		    enableDD: true,
		    containerScroll: true,
		    lines:true,
		    border: false,
		    loader: new Ext.tree.TreeLoader({ 
		    	url: '/MOBILE/ExtServlet?actionName=etl/QryUosTacheCataTreeAction'
		    }),			    
		    root: new Ext.tree.AsyncTreeNode({
		        expanded: true,
		        nodeType: 'async',
		        id:'-1',
		        text:'目录'
		        //singleClickExpand:true
			}),
			listeners:{
		    	'click':function(node,e){
		    		tacheStore.baseParams={tacheCatalogId:node.attributes.id};
		    		tacheStore.load({params:{start:0,limit:15}});
		    	}
			}
		    
		});     
    	
    	return tacheCataTree;
    }
	
	this.showCataWin = function(){
		var tacheStore = pdOper.getTacheStore();
		var tacheGrid = pdOper.getTacheGrid(tacheStore);
		var tacheCataTree = pdOper.getTacheCataTree(tacheStore);
		
		var cataWin = new Ext.Window({
			id:'cataWin',
	        title: '选择环节',
		    closable:true,
		    width:Ext.getBody().getSize().width*0.5,
		    height:Ext.getBody().getSize().height*0.8,
		    plain:true,
		    layout: 'border',
		    items: [tacheCataTree,tacheGrid],
		    buttonAlign:'center',
		    buttons :[{
		    	text:'确定',
		    	listeners:{
			    	'click':function(){
			    		 if(tache == null){
		   		 			Ext.Msg.show({
								title:'操作提示',
								msg:'请选择环节',
								width:'250',
								buttons: Ext.MessageBox.OK,
					          	icon: Ext.MessageBox.INFO
							});	
			    		 }else{
			    		 	Ext.getCmp("tacheId").setValue(tache.tacheId);
			    		 	Ext.getCmp("tacheName").setValue(tache.tacheName);
			    		 	Ext.getCmp("cataWin").close();
			    		 }
			    	}
				}
		    },{
		    	text:'取消',
		    	listeners:{
			    	'click':function(){
			    		Ext.getCmp("cataWin").close();
			    	}
				}
		    }]
		});
		
		cataWin.show();	 	
	}
}