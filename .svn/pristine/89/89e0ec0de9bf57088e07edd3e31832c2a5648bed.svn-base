/////////////////////////////////////////////////////
// ZTESoft corp. 2005-9-17
// Author : Xu.fei3
// commits: Implementation of the exception node
/////////////////////////////////////////////////////

function ExceptionNode(){
	_extends(this, SimpleNode);
	
	this.setIcon("exception");
	this.setText("异常节点");
	this.setNodeType("Exception");
	this.icon.oncontextmenu = Controller.showExceptionMenu;
	
	//生成XPDL
	this.generateActivityXPDL = function(){
		var xpdl = "<Activity Id='" + this.activity.id + "' Name='" + this.activity.name + "'>";
		
		//加上描述
		if(this.activity.description){
			xpdl += "<Description>" + this.activity.description + "</Description>";
		}else{
			xpdl += "<Description />"
		}
		
		//限制
		if(this.activity.limit){
			xpdl += "<Limit>" + this.activity.limit + "</Limit>";
		}else{
			xpdl += "<Limit />";
		}
		
		//执行者
		if(this.activity.performer){
			xpdl += "<Performer>" + this.activity.performer + "</Performer>";
		}else{
			xpdl += "<Performer />";
		}
		
		//开始模式
		xpdl += "<StartMode>" + this.activity.startMode + "</StartMode>";
		
		//结束模式
		xpdl += "<FinishMode>" + this.activity.finishMode + "</FinishMode>";
		
		//优先级
		if(this.activity.priority){
			xpdl += "<Priority>" + this.activity.priority + "</Priority>";
		}else{
			xpdl += "<Priority />"
		}
		
		xpdl += "<Implementation><No/></Implementation>";
		
		//扩展属性
		xpdl += "<xpdl:ExtendedAttributes>";
		for(var i=0; i<this.activity.extendedAttributes.length; i++){	
			//异常配置
			if(this.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				xpdl += "<xpdl:ExtendedAttribute Name='" + this.activity.extendedAttributes[i].name + "'>";
				xpdl += "<xpdl:ExExceptionConfigs>";
				
				var configs = this.activity.extendedAttributes[i].value;
				
				for(var j=0; j<configs.length; j++){
					xpdl += "<xpdl:ExExceptionConfig ExStartActivityId='" + configs[j].startActivityId
						 + "' ExStartActivityName='" + configs[j].startActivityName
						 + "' ExEndActivityId='" + configs[j].endActivityId
						 + "' ExEndActivityName='" + configs[j].endActivityName
						 + "' ExReasonCatalogId='" + configs[j].reasonCatalogId
						 + "' ExReasonCatalogName='" + configs[j].reasonCatalogName
						 + "' ExReturnReasonId='" + configs[j].returnReasonId
						 + "' ExReturnReasonName='" + configs[j].returnReasonName
						 + "' ExAutoToManual='" + configs[j].autoToManual
						 + "' ExStartMode='" + configs[j].startMode + "' />";
				}
				xpdl += "</xpdl:ExExceptionConfigs>";
				xpdl += "</xpdl:ExtendedAttribute>";
				
			}else{
				xpdl += "<xpdl:ExtendedAttribute Name='" + this.activity.extendedAttributes[i].name
					 + "' Value='" + this.activity.extendedAttributes[i].value +"' />";
			}
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		xpdl += "</Activity>";
		
		return xpdl;
	}
}