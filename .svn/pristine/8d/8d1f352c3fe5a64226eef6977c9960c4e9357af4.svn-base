Ext.ns("Morik", "Morik.Office", "Morik.Util", "Morik.Office.Department");
Morik.Office.MainingPanel = Ext.extend(Ext.TabPanel, {
	initComponent : function() {
		// 一些初始化工作
		Morik.Office.MainingPanel.superclass.initComponent.call(this);
		this._cache = {};

	},
	loadTab : function(node) {
		
		var n = this.getComponent(node.id);
		if (n) {
			this.setActiveTab(n);
		//点击菜单刷新	
		
		document.getElementById("div_"+node.id).innerHTML='<iframe id="f_'+node.id+'" width="100%" height="100%" src="'+node.url+'"/>';
		
		} else {
			var c = {
				'id' : node.id,
				'title' : node.text,
				nodeId :node.id,
				nodeUrl:node.url,
				draggable :true,
				closable : true,
				listeners:{   
				    activate:function(tab){   
				    	//点击标签页刷新
				        //window.frames[tab.id].location.reload();    
				    } 
				}  
			};
			var pn = this.findPanel(node.id);
			
			if(node.id=="mainPage"){
				n = this.insert(0,pn ? new pn(c) : Ext.apply(c, {
					html:'<div id=div_'+node.id+' width="100%" height="100%"><iframe id="f_'+node.id+'" width="100%" height="100%" src="'+node.url+'"/></div>'
					}))
				this.setActiveTab(n);
			}else{
				if(node.showModel ==1){
				window.open(node.url,'',"toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,height="+ (window.screen.availHeight-30) +",width="+ (window.screen.availWidth-10) +",left=0,top=0");
				}else{
					n = this.add(pn ? new pn(c) : Ext.apply(c, {
					html:'<div id=div_'+node.id+' width="100%" height="100%"><iframe id="f_'+node.id+'" width="100%" height="100%" src="'+node.url+'"/></div>'
					}))
					n.show().doLayout();
			}
		}
			
		}
		
	},
	findPanel : function(name) {
		var ret = this._cache[name];
		if (!ret) {
			var pn = (this.ns ? this.ns : 'Morik.Office') + "."
					+ Ext.util.Format.capitalize(name) + 'Panel';
			
			var ret = eval(pn);
		}
		return ret;
	},
	addPanel : function(name, panel) {
		if (!this._cache)
			this._cache = {};
		this._cache[name] = panel;
	}
});

