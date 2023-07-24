/////////////////////////////////////////////////////
// ZTESoft corp. 2005-9-20
// Author : Xu.fei3
// commits: Implementation of XPDL activity
/////////////////////////////////////////////////////

function Activity(){
	_extends(this, AbstractElement);
	
	this.limit;
	this.implementation = new Object();
	this.implementation.tool = new Object();
	this.implementation.tool.actualParameters = new Array();
	
	this.route;
	this.blockActivity;
	this.performer;
	
	this.startMode = "Automatic";				//默认是自动

	this.finishMode = "Automatic";				//默认是自动

	this.priority;
	this.deadline;
	
	this.simulationInformation;
	this.icon;
	this.documentation;
	this.transitionRestrictions = new Array();
}