Ext.ns("ZTESOFT");
ZTESOFT.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		 this.trackMouseOver = false;
		 this.autoScroll =  true;
         this.loadMask = true;
         this.defaults = {checked:false};
         config.cm.defaultSortable = true;   
		 this.viewConfig =  new Ext.grid.GridView({
								sortAscText:'升序',
								sortDescText:'降序',
								columnsText:'列名'
							});
		
		var fileds = new Array();  
		for(var i=0;i<config.cm.getColumnCount();i++){
			fileds.push({name:config.cm.getDataIndex(i)});
		}	
		
		var storeCfgObj = {
				id:config.id+'Store',
				root : 'Body',
		        totalProperty: 'totalCount',
		        remoteSort: true,
		        fields: fileds,
		        proxy: new Ext.data.HttpProxy({
		            url: config.url
		        })
		}
		if(!Ext.isEmpty(config.idProperty)){
			storeCfgObj.idProperty = config.idProperty
		}
		this.store = new Ext.data.JsonStore(storeCfgObj);
		if(config.paging == true){
			this.bbar = new Ext.PagingToolbar({
						pageSize: config.pageSize,
						store: this.store,
						displayInfo: true,
						displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg: "没有记录"
			});
		}

		ZTESOFT.Grid.superclass.constructor.apply(this, arguments);
		 
	}
});

