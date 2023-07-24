///////////////////////////////////////////////////////////
// ZTESoft corp. 2006-01-12
// Author : Xu.fei3
// commits: Implementation of the workflow line with arrow
//////////////////////////////////////////////////////////

function FlowLine(){
	_extends(this, AbstractFlowLine);
	
	this.line = document.createElement("v:line");
	this.line.strokecolor = "#5682ce";
	this.line.from = "0, 0";
	this.line.to = "0, 0";
	
	var stroke = document.createElement("v:stroke");
	stroke.endarrow = "classic";
	this.line.appendChild(stroke);
	
	this.line.oncontextmenu = Controller.showLineMenu;
	
	this.oval = document.createElement("v:oval");
	this.oval.strokecolor = "#5682ce";
	
	this.oval.style.width = 10;
	this.oval.style.height = 10;
	this.oval.style.cursor = "hand";
	this.oval.style.position = "absolute";
	this.oval.onclick = Controller.ovalClick;
	
	this.textBox = document.createElement("v:textbox");
	this.textBox.style.width = 100;
	this.textBox.style.height = 12;
	this.line.appendChild(this.textBox);
	
	//线条类型，构造图形的时候需要用，但是业务中不使用
	var lineType = new Object();
	lineType.name = "LineType";
	lineType.value = "Normal";
	this.transition.extendedAttributes.push(lineType);
	
	this.transition.name = "";
	
	//设置是否有箭头
	this.setArrow = function(hasArrow){
		if(hasArrow){
			if(!this.line.contains(stroke)){
				this.line.appendChild(stroke);
				this.line.strokecolor = "#5682ce";
			}
		}else{
			if(this.line.contains(stroke)){
				this.line.removeChild(stroke);
				this.line.strokecolor = "#5682ce";
			}
		}
	}
	
	//设置流程线条颜色
	this.setColor = function(color){
		this.line.strokecolor = color;
	}
	
	//线条位置重绘
	this.repaint = function(){		
		//当没有开始或者结束节点的时候，起始点坐标需要从外界设置进来
		var startX;
		var startY;
		var endX;
		var endY;
		
		if(this.startNode != null){
			startX = this.startNode.bottomPoint.x;
			startY = this.startNode.bottomPoint.y;
			this.line.from = startX + "," + startY;
			
			this.transition.from = this.startNode.activity.id;
		}else{
			startX = this.line.from.x * 4 / 3;
			startY = this.line.from.y * 4 / 3;
		}
		
		if(this.endNode != null){
			endX = this.endNode.topPoint.x;
			endY = this.endNode.topPoint.y;
			this.line.to = endX + "," + endY;
			
			this.transition.to = this.endNode.activity.id;
		}else{
			endX = this.line.to.x * 4 / 3;
			endY = this.line.to.y * 4 / 3;
		}
		
		this.oval.style.pixelLeft = (startX + endX)/2 - 5;
		this.oval.style.pixelTop = (startY + endY)/2 - 5;
		
		//文字框
		this.textBox.style.pixelLeft = (startX + endX)/2 - 50;
		this.textBox.style.pixelTop = (startY + endY)/2 - 5;
		this.textBox.innerText = this.transition.name;
		this.textBox.title = this.transition.name;
	}
	
	//生成XPDL
	this.generateTransitionXPDL = function(){
		var xpdl = "";
		
		//如果开始节点是并行节点，那么线条开始节点的id应当是这个并行节点的合并条件
		if(this.startNode.getNodeType() == "Parallel"){
			xpdl += "<Transition Id='" + this.transition.id + "' Name='" + this.transition.name
				 + "' From='" + this.startNode.relationData.id + "' To='" + this.endNode.activity.id + "'>";
		}else{
			xpdl += "<Transition Id='" + this.transition.id + "' Name='" + this.transition.name
				 + "' From='" + this.startNode.activity.id + "' To='" + this.endNode.activity.id + "'>";
		}
		
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