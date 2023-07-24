/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-16
// Author : Xu.fei3
// commits: Implementation of the control node
/////////////////////////////////////////////////////

//控制节点
function ControlNode(){
	_extends(this, AbstractFlowNode);
	
	this.setNodeType("Control");
	this.activity.name = "控制节点";
	this.type = "single";					//单分支

	this.endNodes = new Array();		//结束节点集合
	this.parentNode = null;					//父节点
	
	this.maxMultipleExtent = 0;			//控制节点所跨过的并行分支最大宽度，不得不保存在这里
	
	//设置基准坐标
	this.baseLeft = 0;
	this.baseTop = 0;
	
	//上面、下面、左面、右面的连接点，用来连线
	this.topPoint = new Object();
	this.topPoint.x = 0;
	this.topPoint.y = 0;
	
	this.bottomPoint = new Object();
	this.bottomPoint.x = 0;
	this.bottomPoint.y = 0;
	
	this.leftPoint = new Object();
	this.leftPoint.x = 0;
	this.leftPoint.y = 0;
	
	this.rightPoint = new Object();
	this.rightPoint.x = 0;
	this.rightPoint.y = 0;
	
	//面板
	this.panel = document.createElement("div");
	this.panel.style.position = "absolute";
	this.panel.style.pixelLeft = this.baseLeft;
	this.panel.style.pixelTop = this.baseTop;
	
	//控制结构的图标

	this.icon = document.createElement("img");
	this.icon.style.position = "absolute";
	this.icon.style.width = "42px";
	this.icon.style.height = "37px";
	this.icon.src = "./js/flowElement/resources/control.gif";
	this.icon.onclick = Controller.nodeClick;
	//this.icon.ondblclick = Controller.viewControlNodeProperty;
	this.icon.oncontextmenu = Controller.showControlMenu;
	
	//转向线条
	this.controlLines = new Array();
	
	this.setMaxDepth(1);			//单个节点，总深度记为1
	this.setMaxExtent(1);			//单个节点，总深度记为1
	
	this.panel.appendChild(this.icon);
	
	//添加一个结束节点
	this.addEndNode = function(endNode, transition){
		//先看看是否已经存在这两个节点之间的线条，如果存在，什么都不做
		for(var i=0; i<this.endNodes.length; i++){
			if(this.endNodes[i] == endNode){
				alert("已经存在这样的跳转线条了！");
				return;
			}
		}
		
		this.endNodes.push(endNode);
		
		var controlLine = new ControlLine();
		controlLine.startNode = this;
		controlLine.endNode = endNode;
		
		if(transition != null){
			controlLine.transition = transition;
		}
		
		this.controlLines.push(controlLine);
		this.parentNode.panel.appendChild(controlLine.startLine);
		this.parentNode.panel.appendChild(controlLine.centerLine);
		this.parentNode.panel.appendChild(controlLine.endLine);
		this.parentNode.panel.appendChild(controlLine.icon);
	}
	
	//删除一条控制线条

	this.removeControlLine = function(lineIndex){
		this.parentNode.panel.removeChild(this.controlLines[lineIndex].startLine);
		this.parentNode.panel.removeChild(this.controlLines[lineIndex].centerLine);
		this.parentNode.panel.removeChild(this.controlLines[lineIndex].endLine);
		this.parentNode.panel.removeChild(this.controlLines[lineIndex].icon);
		
		this.controlLines.splice(lineIndex, 1);
		this.endNodes.splice(lineIndex, 1);
	}
	
	//清除内部元素
	this.clear = function(){
		while(this.endNodes.length > 0){
			this.removeControlLine(this.endNodes.length-1);
		}
		
		Controller.cancelNodeSelect();
	}
	
	//更新数据
	this.updateData = function(){
		for(var i=0; i<this.activity.extendedAttributes.length; i++){			
			if(this.activity.extendedAttributes[i].name == "branchIndex"){
				this.activity.extendedAttributes[i].value = this.getBranchIndex();
			}
			
			if(this.activity.extendedAttributes[i].name == "nodeIndex"){
				this.activity.extendedAttributes[i].value = this.getNodeIndex();
			}
		}
		
		this.topPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.topPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY();
		
		this.bottomPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.bottomPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 35;
		
		this.leftPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() - 3;
		this.leftPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 21;
		
		this.rightPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 39;
		this.rightPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 21;
	}
	
	//重绘
	this.repaint = function(){
		this.updateData();
		
		this.panel.style.pixelLeft = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() - 2;
		this.panel.style.pixelTop = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY();
		
		var maxLeftExtent = 0;
		var maxRightExtent = 0;
		
		for(var i=0; i<this.controlLines.length; i++){
			if(i%2 == 1){
				//this.controlLines[i].hDirection = "left";
				
				if(this.controlLines[i].maxExtent <= maxLeftExtent){
					//this.controlLines[i].maxExtent = maxLeftExtent + 1;
				}else{
					maxLeftExtent = this.controlLines[i].maxExtent;
				}
				
			}else{
				//this.controlLines[i].hDirection = "right";
				
				if(this.controlLines[i].maxExtent <= maxRightExtent){
					//this.controlLines[i].maxExtent = maxRightExtent + 1;
				}else{
					maxRightExtent = this.controlLines[i].maxExtent;
				}
			}
			
			this.controlLines[i].setSpaceX(this.getSpaceX());
			this.controlLines[i].setSpaceY(this.getSpaceY());
			this.controlLines[i].repaint();
		}
		
		if(maxLeftExtent <= maxRightExtent){
			this.setMaxExtent(maxRightExtent + 1);
		}else{
			this.setMaxExtent(maxLeftExtent + 1);
		}
	}
	
	//生成节点的XPDL
	this.generateActivityXPDL = function(){
		var xpdl = "<Activity Id='" + this.activity.id + "' Name='" + this.activity.name + "'>";
		
		xpdl += "<Route />";
		
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
		
		//加上描述
		if(this.activity.description){
			xpdl += "<Description>" + this.activity.description + "</Description>";
		}else{
			xpdl += "<Description />";
		}
		
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
	
	//生成线条的XPDL
	this.generateTransitionXPDL = function(){
		var xpdl = "";
		
		for(var i=0; i<this.controlLines.length; i++){
			xpdl += this.controlLines[i].generateTransitionXPDL();
		}
		
		return xpdl;
	}
}