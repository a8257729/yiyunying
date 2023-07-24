/////////////////////////////////////////////////////
// ZTESoft corp. 2005-10-19
// Author : Xu.fei3
// commits: Implementation of the tache node
/////////////////////////////////////////////////////

function TacheNode(){
	_extends(this, SimpleNode);
	
	this.setIcon("tache");
	this.setText("环节节点");
	this.setNodeType("Tache");
	
	//生成XPDL
	this.generateActivityXPDL = function(){
		var xpdl = "<Activity Id='" + this.activity.id + "' Name='" + this.activity.name + "'>";
		
		//加上描述
		if(this.activity.description){
			xpdl += "<Description>" + this.activity.description + "</Description>";
		}else{
			xpdl += "<Description />";
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
		
		//流入流出方式
		if(this.activity.transitionRestrictions.length > 0){
			xpdl += "<TransitionRestrictions>";
			for(var i=0; i<this.activity.transitionRestrictions.length; i++){
				xpdl += "<TransitionRestriction>"
					 + "<Join Type='"
					 + this.activity.transitionRestrictions[i].joinType
					 + "'/><Split Type='"
					 + this.activity.transitionRestrictions[i].splitType
					 + "'/></TransitionRestriction>";
			}
			xpdl += "</TransitionRestrictions>";
		}else{
			xpdl += "<TransitionRestrictions>";
			xpdl += "<TransitionRestriction>"
				 + "<Join Type='AND'/><Split Type='AND'/></TransitionRestriction>";
			xpdl += "</TransitionRestrictions>";
		}
		
		//优先级

		if(this.activity.priority){
			xpdl += "<Priority>" + this.activity.priority + "</Priority>";
		}else{
			xpdl += "<Priority />";
		}
		
		//实参
		xpdl += "<Implementation>";
		xpdl += "<Tool Id='" + this.application.id + "' Type='Application'>";
		if(this.activity.implementation.tool.actualParameters.length > 0){
			xpdl += "<ActualParameters>";
			for(var i=0; i<this.activity.implementation.tool.actualParameters.length; i++){
				xpdl += "<ActualParameter Id='" + this.application.formalParameters[i].paramName + "'>"
					+ this.activity.implementation.tool.actualParameters[i]
					+ "</ActualParameter>";
			}
			xpdl += "</ActualParameters>";
		}
		xpdl += "</Tool></Implementation>";
		
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
	
	//生成Applications的XPDL
	this.generateApplicationXPDL = function(){
		//没有参数
		if(this.application.formalParameters.length == 0){
			return "";
		}
		
		var applicationXpdl = "<Application Id='" + this.application.id + "' Name='" + this.application.name + "'>";
				
		//加上描述
		if(this.application.description){
			applicationXpdl += "<Description>" + this.application.description + "</Description>";
		}else{
			applicationXpdl += "<Description />";
		}
		
		//参数
		applicationXpdl += "<xpdl:FormalParameters>";
		for(var i=0; i<this.application.formalParameters.length; i++){
			applicationXpdl += "<xpdl:FormalParameter Id='" + this.application.formalParameters[i].paramName
				 + "' Name='" + this.application.formalParameters[i].paramName
				 + "' isDisplay='" + this.application.formalParameters[i].isDisplay
				 + "' Mode='" + this.application.formalParameters[i].paramType +"'>";
			
			//数据类型
			applicationXpdl += "<xpdl:DataType>";
			applicationXpdl += "<xpdl:BasicType Type='" + this.application.formalParameters[i].dataType + "'/>";
			applicationXpdl += "</xpdl:DataType>";
			
			//描述
			applicationXpdl +=
				 "<xpdl:Description>" + this.application.formalParameters[i].description + "</xpdl:Description>";
			
			applicationXpdl += "</xpdl:FormalParameter>";
		}
		applicationXpdl += "</xpdl:FormalParameters>";
		
		//扩展属性

		applicationXpdl += "<xpdl:ExtendedAttributes>";
		for(var i=0; i<this.application.extendedAttributes.length; i++){
			applicationXpdl += "<xpdl:ExtendedAttribute Name='" + this.application.extendedAttributes[i].name
				 + "' Value='" + this.application.extendedAttributes[i].value +"' />";
		}
		applicationXpdl += "</xpdl:ExtendedAttributes>";
		
		applicationXpdl += "</Application>";
		return applicationXpdl;
	}
	
	//生成participant的XPDL
	this.generateParticipantXPDL = function(){
		//只要有参与者，扩展属性就会存在

		if(this.participant.extendedAttributes.length == 0){
			return "";
		}
		
		var participantXpdl = "<Participant Id='" + this.participant.id + "' Name='" + this.participant.name + "'>";
		
		//参与者类型

		if((this.participant.participantType != null) && (this.participant.participantType != "")){
			participantXpdl += "<ParticipantType Type='" + this.participant.participantType + "'/>";
		}
		
		//扩展属性

		participantXpdl += "<xpdl:ExtendedAttributes>";
		for(var i=0; i<this.participant.extendedAttributes.length; i++){
			participantXpdl += "<xpdl:ExtendedAttribute Name='" + this.participant.extendedAttributes[i].name
				 + "' Value='" + this.participant.extendedAttributes[i].value +"' />";
		}
		participantXpdl += "</xpdl:ExtendedAttributes>";
		
		participantXpdl += "</Participant>";
		return participantXpdl;
	}
}