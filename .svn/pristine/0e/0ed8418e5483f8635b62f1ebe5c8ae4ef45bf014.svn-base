/////////////////////////////////////////////////////
// ZTESoft corp. 2005-9-21
// Author : Xu.fei3
// commits: Operations on tache tree
/////////////////////////////////////////////////////

//环节库的操作
function TacheOperation(){
	this.tacheTree = document.all.tacheLibrary;
	
	//初始化
	this.init = function(){
		var data = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "getTacheCatalogWithActiveTacheXML");
		this.tacheTree.loadByXML(data);
		
		if(this.tacheTree.items != null){
			for(var i=0; i<this.tacheTree.items.length; i++){
				if(this.tacheTree.items[i].flag == "C"){
					this.tacheTree.items[i].setImage("../images/folder.gif");
				}
			}
		}
	}
	this.queryTacheByWord=function(){
	var keyWord=document.all.tacheKeyWord.value;
	var data = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "getTacheCatalogWithTacheXMLByKey",keyWord);
	//alert(data);
	this.tacheTree.loadByXML(data);
	if(this.tacheTree.items != null){
			for(var i=0; i<this.tacheTree.items.length; i++){			    
				if(this.tacheTree.items[i].flag == "C"){				    
				    this.tacheTree.items[i].expand(true);
				    this.tacheTree.items[i].setImage("../images/folder.gif");   
				}
			}
		}
	}
}