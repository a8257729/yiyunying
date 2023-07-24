Ext.ns("ZTESOFT");
ZTESOFT.OrgTree = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		if(Ext.isEmpty(config.title)){
			 this.title = '组织名称';
		}
		this.autoScroll =true;
        
        if(Ext.isEmpty(config.height)){
        	this.height = Ext.getBody().getSize().height*0.99;
        }
        if(Ext.isEmpty(config.width)){
        	this.width = Ext.getBody().getSize().width*0.2;
        }
        var url = '/MOBILE/ExtServlet?actionName=system/OrgTreeAction';
        if(!Ext.isEmpty(config.checked)){
        	if(config.checked){
        		url = '/MOBILE/ExtServlet?actionName=system/OrgTreeActionCheck';	
        	}else{
        		url = '/MOBILE/ExtServlet?actionName=system/OrgTreeAction'	;
        	}
        }
      
        if(!Ext.isEmpty(config.url)){
        	url = config.url;
        }
        this.loader =  new Ext.tree.TreeLoader({
        		dataUrl:url
       	});
       	this.useArrows = true;
   		
	    this.autoScroll = true;
	    this.animate = true;
	    this.enableDD = false;
	    this.containerScroll = true;
	    this.border =  true;
	    var session1 = GetSession();
		var staffId = session1.staff.staffId ;
		var orgId ;
		var orgName;
		var rootVisable;
		var allOrgs = false;
		var orgPathCode='';
		if(!Ext.isEmpty(config.allOrgs)){
			allOrgs = config.allOrgs;
		}
		if(staffId == 0||allOrgs){
			orgId = -1 ;
			rootVisiable = false ;
			
		}else{
			orgId = session1.org.orgId ;
			orgName = session1.org.orgName ;
			orgPathCode = session1.org.orgPathCode;
			rootVisable = true ;
		}
		this.rootVisible = rootVisable;
		var treeRoot = new Ext.tree.AsyncTreeNode();
		treeRoot.id = orgId ;
		treeRoot.attributes.orgId = orgId ;
		treeRoot.text = orgName ;
		treeRoot.attributes.orgPathCode = orgPathCode;
		this.root =  treeRoot;
		ZTESOFT.OrgTree.superclass.constructor.apply(this, arguments);
		 
	}
});

