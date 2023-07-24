Ext.app.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.app.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
       		
		this.filterTree = function(text){
		
		var groupTree = Ext.getCmp("groupTree") ;
		
		Ext.each(groupTree.hiddenPkgs, function(n){
			n.ui.show();
		});
		if(!text){
			groupTree.filter.clear();
			return;
		}
		
		groupTree.setKeyword(text);
		
		groupTree.filter.filterBy(function(n){
			if(n.leaf == 0 || new RegExp(groupTree.keyword).test(n.text)){
				return true ;
			}else{
				return false ;
			}
		});
		groupTree.root.cascade(function(n){
			if(n.leaf == 0 && n.ui.ctNode.offsetHeight < 3){
				n.ui.hide();
				groupTree.hiddenPkgs.push(n);
				n.bubble(function(node){
					if(node.leaf == 0 && node.ui.ctNode.offsetHeight < 3){
						node.ui.hide();
						groupTree.hiddenPkgs.push(node);
					}
				});
			}
		});
	}
    },
    
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',

    onTrigger1Click : function(){
    	
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
       
        this.filterTree(v);
       
       
    }
});