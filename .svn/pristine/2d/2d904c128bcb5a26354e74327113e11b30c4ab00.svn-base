Ext.ns("Morik", "Morik.Office", "Morik.Util", "Morik.Office.Department");
Morik.Office.LeftMenu = function(config) {
	var d = Ext.apply( {// default set
				width : 210,
				split : true,
				region : 'west',
				collapseMode :'mini',
				
				defaults : {
					border : false
				},
				layoutConfig : {
					animate : true
				}
			}, config || {});

	config = Ext.apply(d, {
		layout : 'accordion',
		collapsible : true
	});
	Morik.Office.LeftMenu.superclass.constructor.call(this, config);
	//改进，并为增加了个配置项tree!
	for(var i=0;i<this.trees.length;i++)		 
	 	this.add({title:this.trees[i].getRootNode().text,items:[this.trees[i]]});	

	// 事件处理
	this.addEvents('nodeClick');
	
	this.initTreeEvent();
	}

Ext.extend(Morik.Office.LeftMenu, Ext.Panel, {
	selNode:'',
	initTreeEvent : function() {
		if(!this.items) return;
		for (var i = 0;i < this.items.length; i++) {
			var p = this.items.itemAt(i);
			if (p)
			var t = p.items.itemAt(0);
			if(t)
			t.on( {
				'click' : function(node, event) {
					if (node && node.isLeaf()) {
						event.stopEvent();
						this.fireEvent('nodeClick', node.attributes);
						//如果访问的是外系统，则插入访问外系统的日志
						var paramObj = new Object();
				 		paramObj.menuName = node.attributes.menuName;
				 		paramObj.url = node.attributes.url;
				 		var retObj= invokeAction("/system/InsertOuterSysLoginLogAction",paramObj);
					}
				},
				scope : this
			});
			t.on({
				'contextmenu':function(node,event){
					event.preventDefault();
					Ext.getCmp("rightmenuNode").showAt(event.getXY());
					node.select(); 
					this.selNode = node;
					
				},
				scope:this
			});
		}
	}
})
var rightmenuNode = new Ext.menu.Menu({
	    id:'rightmenuNode',
	    items:[{
	    	text:'添加到工作台-待办任务',
	    	menuType:1,
	    	handler: addMenuToPlat
	    },{
	    	text:'添加到工作台-个人工作区',
	    	menuType:2,
	    	handler: addMenuToPlat
	    }]
});

function addMenuToPlat(item){
	var selNode = Ext.getCmp('leftmenu').selNode.attributes;
	var paramObj = new Object();
	var  session1 = new Session();
	paramObj.staffId = session1.staff.staffId;
	paramObj.jobId = session1.job.jobId;
	paramObj.menuId = toNumber(selNode.menuId);
	paramObj.menuName = selNode.menuName;
	paramObj.menuType = item.menuType;
	var result = invokeAction("/personalPlat/InsertPersonalAction",paramObj);
	
	if(result =="SUCCESS"){
		Ext.example.msg('','添加菜单成功'); 
			
	}else{
		Ext.example.msg('','菜单已添加到个人工作台'); 
	}
	
	var node = new Object();
	node.id = 'mod5183';
	node.menuId = 5183;
	node.text = '个人工作台';
	node.menuName = '个人工作台';
	node.url = "/MOBILE/systemMgr/personalWorkPlatform.jsp";
	node.showModel = '0';
	node.leaf = true;
	Ext.getCmp("mainTab").loadTab(node);
	
	
}


