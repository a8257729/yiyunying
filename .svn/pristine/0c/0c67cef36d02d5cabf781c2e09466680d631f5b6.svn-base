/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-30
// Author : Xu.fei3
// commits: Implementation of simple workflow node
/////////////////////////////////////////////////////

function SimpleNode(){
	_extends(this, AbstractFlowNode);
	this.application = new Application();
	this.participant = new Participant();
	
	this.panel = document.createElement("div");

	this.panel.style.position = "absolute";
	this.panel.style.left = "0px";
	this.panel.style.top = "0px";
	this.panel.style.height = "34px";
	
	this.table = document.createElement("table");
	this.table.insertRow();
	this.table.rows[0].insertCell();
	this.table.rows[0].insertCell();
	
	this.table.rows[0].cells[0].align = "center";
	this.table.rows[0].cells[1].valign = "middle";
	this.table.rows[0].cells[1].style.width = "50px";

	this.textSpan = document.createElement("span");
	this.textSpan.style.fontSize = "12px";
	this.textSpan.valign = "middle";
	this.textSpan.style.width = "75px";
	this.textSpan.style.height = "28px";
	this.textSpan.style.overflow = "hidden";
	
	var iconBase = FLOWNODEICONBASE;
	this.iconName = "tache";
	this.selected = false;
	
	this.icon = document.createElement("img");
	this.icon.style.width = "32px";
	this.icon.style.height = "32px";
	this.icon.src = iconBase + this.iconName + ".gif";
	this.icon.onclick = Controller.nodeClick;
	//this.icon.ondblclick = controller.viewNodeProperty;
	this.icon.oncontextmenu = Controller.showNodeMenu;
	
	this.panel.appendChild(this.table);
	this.table.rows[0].cells[0].appendChild(this.icon);
	this.table.rows[0].cells[1].appendChild(this.textSpan);
	
	this.setMaxDepth(1);			//单个节点，总深度记为1
	this.setMaxExtent(1);			//单个节点，总深度记为1

	this.type = "simple";		//简单节点
	
	this.setText = function(text){
		this.activity.name = text;
		this.textSpan.innerText = text;
	}
	
	this.getText = function(){
		return this.activity.name;
	}
	
	this.setIcon = function(iconName){
		this.iconName = iconName;
		this.icon.src = iconBase + this.iconName + ".gif";
	}
	
	//设置选中状态
	this.setSelected = function(flag){
		if(flag){
			this.panel.style.border = "1px solid #ff0000";
		}else{
			this.panel.style.border = "none";
		}
		this.selected = flag;
	}
	
	//获取选中状态
	this.getSelected = function(){
		return this.selected;
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
		
		this.setText(this.activity.name);
		
		this.topPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.topPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 3;
		
		this.bottomPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.bottomPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 38;
		
		this.leftPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 1;
		this.leftPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 18;
		
		this.rightPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 35;
		this.rightPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 18;
	}
	
	//重绘本节点

	this.repaint = function(){
		this.updateData();
		
		this.panel.style.pixelLeft = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX();
		this.panel.style.pixelTop = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY();
	}
}