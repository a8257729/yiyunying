/////////////////////////////////////////////////////
// ZTESoft corp. 2005-8-18
// Author : Xu.fei3
// commits: Implementation of the workflow line
/////////////////////////////////////////////////////

//所有流程线条的基类，包含通用的成员变量和方法
function AbstractFlowLine(){
	this.transition = new Transition();
	
	//线条的起始和终止节点
	this.startNode = null;
	this.endNode = null;
	
	var maxDepth = 0;				//本节点最大深度
	var maxExtent = 0;			//本节点最大广度

	var innerDepth = 0;			//记录这个节点位于某复合结构中的深度次序
	var innerExtent = 0;		//记录这个节点位于某复合结构中的广度次序
	
	var offsetLeft = 0;			//设置基准横坐标
	var offsetTop = 0;			//设置基准纵坐标
	
	var spaceX = 0;					//节点的横向间距
	var spaceY = 0;					//节点的纵向间距

	
	//获取最大深度
	this.getMaxDepth = function(){
		return maxDepth;
	}
	
	//获取最大广度
	this.getMaxExtent = function(){
		return maxExtent;
	}
	
	//获取深度次序
	this.getInnerDepth = function(){
		return innerDepth;
	}
	
	//设置深度次序
	this.setInnerDepth = function(depth){
		innerDepth = depth;
	}
	
	//获取广度次序
	this.getInnerExtent = function(){
		return innerExtent;
	}
	
	//设置广度次序
	this.setInnerExtent = function(extent){
		innerExtent = extent;
	}
	
	//设置左基准坐标

	this.setLeft = function(left){
		offsetLeft = left;
	}
	
	//设置上基准坐标
	this.setTop = function(top){
		offsetTop = top;
	}
	
	//设置横向间距
	this.setSpaceX = function(x){
		spaceX = x;
	}
	
	//设置纵向间距
	this.setSpaceY = function(y){
		spaceY = y;
	}
}