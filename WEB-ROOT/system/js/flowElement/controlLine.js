///////////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-28
// Author : Xu.fei3
// commits: Implementation of the control line with arrow
//////////////////////////////////////////////////////////

function ControlLine(){	
	_extends(this, AbstractFlowLine);
	
	this.startLine = document.createElement("v:line");
	this.startLine.strokecolor = "#5682ce";
	this.startLine.from = "0, 0";
	this.startLine.to = "0, 0";
	
	this.centerLine = document.createElement("v:line");
	this.centerLine.strokecolor = "#5682ce";
	this.centerLine.from = "0, 0";
	this.centerLine.to = "0, 0";
	
	this.endLine = document.createElement("v:line");
	this.endLine.strokecolor = "#5682ce";
	this.endLine.from = "0, 0";
	this.endLine.to = "0, 0";
	
	this.stroke = document.createElement("v:stroke");
	this.stroke.endarrow = "classic";
	this.endLine.appendChild(this.stroke);
	
	this.iconBase = "./js/flowElement/resources/arrow_";
	
	this.icon = document.createElement("img");
	this.icon.src = this.iconBase + "up.gif";
	this.icon.style.position = "absolute";
	this.icon.oncontextmenu = Controller.showControlLineMenu;
	
	this.spaceX = 100;
	this.spaceY = 100;
	
	this.maxExtent = 1;
	this.maxDepth = 1;
	
	//线条默认从控制节点的右边出来
	this.hDirection = "right";
	this.vDirection = "down";
	
	this.textBox = document.createElement("v:textbox");
	this.textBox.style.position = "absolute";
	this.textBox.style.width = 16;
	//this.textBox.style.height = 16;
	this.centerLine.appendChild(this.textBox);
	
	//线条类型，构造图形的时候需要用，但是业务中不使用

	var lineType = new Object();
	lineType.name = "LineType";
	lineType.value = "Control";
	this.transition.extendedAttributes.push(lineType);
	
	this.transition.name = "";
	
	//线条位置重绘
	this.repaint = function(){
		switch(this.hDirection){
			case "left":{
				this.startLine.from = this.startNode.leftPoint.x + "," + this.startNode.leftPoint.y;
				this.startLine.to = (this.startNode.leftPoint.x - this.maxExtent*this.spaceX/2 + 21) + "," + this.startNode.leftPoint.y;
				
				this.centerLine.from = this.startLine.to;
				this.centerLine.to = (this.startNode.leftPoint.x - this.maxExtent*this.spaceX/2 + 21) + "," + this.endNode.leftPoint.y; 
				
				this.endLine.from = this.centerLine.to;
				this.endLine.to = this.endNode.leftPoint.x + "," + this.endNode.leftPoint.y;
				
				this.icon.style.pixelLeft = this.startNode.leftPoint.x - this.maxExtent*this.spaceX/2 + 14;
				break;
			}
			case "right":{
				this.startLine.from = this.startNode.rightPoint.x + "," + this.startNode.rightPoint.y;
				this.startLine.to = (this.startNode.rightPoint.x + this.maxExtent*this.spaceX/2 - 20) + "," + this.startNode.rightPoint.y;
				
				this.centerLine.from = this.startLine.to;
				this.centerLine.to = (this.startNode.rightPoint.x + this.maxExtent*this.spaceX/2 - 20) + "," + this.endNode.rightPoint.y; 
				
				this.endLine.from = this.centerLine.to;
				this.endLine.to = this.endNode.rightPoint.x + "," + this.endNode.rightPoint.y;
				
				this.icon.style.pixelLeft = this.startNode.rightPoint.x + this.maxExtent*this.spaceX/2 - 27;
				break;
			}
		}
		
		//判断线条上的图标走向
		if(this.startNode.rightPoint.y < this.endNode.rightPoint.y){
			this.icon.src = this.iconBase + "down.gif";
			this.icon.style.pixelTop = this.startNode.rightPoint.y + 17;
			
			this.vDirection = "down";
		}else{
			this.icon.src = this.iconBase + "up.gif";
			this.icon.style.pixelTop = this.startNode.rightPoint.y - 33;
			
			this.vDirection = "up";
		}
		
		//文字框

		if(this.hDirection == "right"){
			this.textBox.style.left = this.centerLine.from.x;
		}else{
			this.textBox.style.left = this.centerLine.from.x - 30;
		}
		this.textBox.style.top = (this.centerLine.from.y + this.centerLine.to.y)/2 - 5;
		
		this.textBox.innerText = this.transition.name;
	}
	
	//生成XPDL
	this.generateTransitionXPDL = function(){
		var xpdl = "<Transition Id='" + this.transition.id + "' Name='" + this.transition.name
			 + "' From='" + this.startNode.activity.id + "' To='" + this.endNode.activity.id + "'>";
		
		//加上描述
		if(this.transition.description){
			xpdl += "<Description>" + this.transition.description + "</Description>";
		}else{
			xpdl += "<Description />"
		}
		
		if(this.transition.condition){
			xpdl += "<xpdl:Condition><xpdl:Xpression>"
				 + "<![CDATA[" + this.transition.condition + "]]>"
				 + "</xpdl:Xpression></xpdl:Condition>";
		}else{
			xpdl += "<xpdl:Condition><xpdl:Xpression/></xpdl:Condition>";
		}
		
		//扩展属性

		xpdl += "<xpdl:ExtendedAttributes>";
		for(var i=0; i<this.transition.extendedAttributes.length; i++){
			xpdl += "<xpdl:ExtendedAttribute Name='" + this.transition.extendedAttributes[i].name
				 + "' Value='" + this.transition.extendedAttributes[i].value +"' />";
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		xpdl += "</Transition>";
		return xpdl;
	}
}