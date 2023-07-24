/////////////////////////////////////////////////////
// ZTESoft corp. 2005-9-17
// Author : Xu.fei3
// commits: Implementation of the start node
/////////////////////////////////////////////////////

function StartNode(){
	_extends(this, SimpleNode);
	
	this.setIcon("start");
	this.setText("开始节点");
	this.setNodeType("Start");
	this.icon.oncontextmenu = menuHandler;
	
	//空的函数，使得点右键菜单没反应
	function menuHandler(){
		return;
	}
	
	//生成Activity的XPDL
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
			xpdl += "<xpdl:ExtendedAttribute Name='" + this.activity.extendedAttributes[i].name
				 + "' Value='" + this.activity.extendedAttributes[i].value +"' />";
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		xpdl += "</Activity>";
		
		return xpdl;
	}
}