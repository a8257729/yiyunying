/////////////////////////////////////////////////////
// ZTESoft corp. 2005-12-07
// Author : Xu.fei3
// commits: Implementation of XPDL parser
/////////////////////////////////////////////////////

function XPDLParser(){
	var doc = new ActiveXObject("Msxml2.DOMDocument");
	doc.async = false;
	doc.resolveExternals = false;
	doc.validateOnParse = true;
	
	var root;
	
	//根据文件名加载XPDL
	this.loadXPDLFile = function(file){
		try{
			doc.load(file);
			root = doc.documentElement;
		}catch(e){
			ErrorHandle("解析失败");
		}
	}
	
	//根据XPDL字符串加载XPDL
	this.loadXPDLString = function(str){
		try{
			doc.loadXML(str);
			root = doc.documentElement;
		}catch(e){
			ErrorHandle("解析失败");
		}
	}
	
	//把XPDL解析成对象
	this.xpdlToObject = function(){
		//root现在是package，那么最后一个子节点是流程集合，但是如果要严谨一些，需要修改一下

		var processesNode = root.lastChild;
		var workflowProcesses = new Array();
		
		for(var flowIndex=0; flowIndex<processesNode.childNodes.length; flowIndex++){
			var processNode = processesNode.childNodes[flowIndex];
			var workflowProcess = new WorkflowProcess();
			
			var processHeaderNode;
			var redefinableHeaderNode;
			var formalParametersNode;
			var dataFieldsNode;
			var participantsNode;
			var applicationsNode;
			var activitiesNode;
			var transitionsNode;
			var extendedAttibutesNode;
			
			//初始化各种节点集合，准备组装对象
			for(var i=0; i<processNode.childNodes.length; i++){
				switch(processNode.childNodes[i].nodeName){
					case "ProcessHeader":{
						processHeaderNode = processNode.childNodes[i];
						break;
					}
					case "RedefinableHeader":{
						redefinableHeaderNode = processNode.childNodes[i];
						break;
					}
					case "xpdl:FormalParameters":{
						formalParametersNode = processNode.childNodes[i];
						break;
					}
					case "DataFields":{
						dataFieldsNode = processNode.childNodes[i];
						break;
					}
					case "Participants":{
						participantsNode = processNode.childNodes[i];
						break;
					}
					case "Applications":{
						applicationsNode = processNode.childNodes[i];
						break;
					}
					case "Activities":{
						activitiesNode = processNode.childNodes[i];
						break;
					}
					case "Transitions":{
						transitionsNode = processNode.childNodes[i];
						break;
					}
					case "xpdl:ExtendedAttributes":{
						extendedAttibutesNode = processNode.childNodes[i];
						break;
					}
				}
			}
			
			workflowProcess.id = processNode.getAttribute("Id");
			workflowProcess.name = processesNode.getAttribute("Name");
			workflowProcess.accessLevel = processesNode.getAttribute("AccessLevel");			
			
			workflowProcess.dataFields = this.getDataFields(dataFieldsNode);
			workflowProcess.formalParameters = this.getParameters(formalParametersNode);
			workflowProcess.applications = this.getApplications(applicationsNode);
			workflowProcess.participants = this.getParticipants(participantsNode);
			workflowProcess.activities = this.getActivities(activitiesNode);
			workflowProcess.transitions = this.getTransitions(transitionsNode);
			
			workflowProcesses.push(workflowProcess);
		}
		
		return workflowProcesses;
	}
	
	//从给定的XML节点组装出DataField数组
	this.getDataFields = function(dataFieldsNode){
		if(dataFieldsNode == null){
			return;
		}
		
		var dataFields = new Array();
		
		with(dataFieldsNode){
			for(var i=0; i<childNodes.length; i++){
				var variable = new Object();
				
				//在这里，Name跟Id是相同的
				variable.variableId = childNodes[i].getAttribute("Id");
				variable.variableName = childNodes[i].getAttribute("Name");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "xpdl:DataType":{
							//下面组装的是数据类型
							variable.dataType = childNodes[i].childNodes[j].firstChild.getAttribute("Type");
							break;
						}
						case "InitialValue":{
							variable.initialValue = childNodes[i].childNodes[j].text;
							break;
						}
						case "xpdl:Description":{
							variable.description = childNodes[i].childNodes[j].text;
							break;
						}
					}
				}
				dataFields.push(variable);
			}
		}
		
		return dataFields;
	}
	
	//从给定的XML节点组装出FormalParameter数组
	this.getParameters = function(parameterNode){
		if(parameterNode == null){
			return;
		}
		
		var formalParameters = new Array();
		
		with(parameterNode){
			for(var i=0; i<childNodes.length; i++){
				var param = new Object();
				
				//在这里，Name跟Id是相同的
				param.paramId = childNodes[i].getAttribute("Id");
				param.paramName = childNodes[i].getAttribute("Name");
				param.paramType = childNodes[i].getAttribute("Mode");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "xpdl:DataType":{
							//下面组装的是数据类型
							param.dataType = childNodes[i].childNodes[j].firstChild.getAttribute("Type");
							break;
						}
						case "xpdl:Description":{
							param.description = childNodes[i].childNodes[j].text;
							break;
						}
					}
				}
				formalParameters.push(param);
			}
		}
		
		return formalParameters;
	}
	
	//从给定的XML节点组装出Participant数组
	this.getParticipants = function(participantNode){
		var participants = new Array();
		
		with(participantNode){
			for(var i=0; i<childNodes.length; i++){
				var participant = new Participant();
				participant.id = childNodes[i].getAttribute("Id");
				participant.name = childNodes[i].getAttribute("Name");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "ParticipantType":{
							participant.participantType = childNodes[i].childNodes[j].getAttribute("Type");
							break;
						}
						case "Description":{
							participant.description = childNodes[i].childNodes[j].text;
							break;
						}
						case "xpdl:ExtendedAttributes":{
							participant.extendedAttributes = new Array();
							for(var k=0; k<childNodes[i].childNodes[j].childNodes.length; k++){
								var extendedAttribute = new Object();
								extendedAttribute.name = childNodes[i].childNodes[j].childNodes[k].getAttribute("Name");
								extendedAttribute.value = childNodes[i].childNodes[j].childNodes[k].getAttribute("Value");
								participant.extendedAttributes.push(extendedAttribute);
							}
							break;
						}
					}
				}
				
				participants.push(participant);
			}
		}
		
		return participants;
	}
	
	//从给定的XML节点组装出Application数组
	this.getApplications = function(applicationNode){
		var applications = new Array();
		
		with(applicationNode){
			for(var i=0; i<childNodes.length; i++){
				var application = new Application();
				application.id = childNodes[i].getAttribute("Id");
				application.name = childNodes[i].getAttribute("Name");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "xpdl:FormalParameters":{
							with(childNodes[i].childNodes[j]){
								for(var k=0; k<childNodes.length; k++){
									var param = new Object();
									
									//在这里，Name跟Id是相同的
									param.paramId = childNodes[k].getAttribute("Id");
									param.paramName = childNodes[k].getAttribute("Name");
									param.paramType = childNodes[k].getAttribute("Mode");
									param.isDisplay = childNodes[k].getAttribute("isDisplay");
									
									for(var m=0; m<childNodes[k].childNodes.length; m++){
										switch(childNodes[k].childNodes[m].nodeName){
											case "xpdl:DataType":{
												//下面组装的是数据类型
												param.dataType = childNodes[k].childNodes[m].firstChild.getAttribute("Type");
												break;
											}
											case "xpdl:Description":{
												param.description = childNodes[k].childNodes[m].text;
												break;
											}
										}
									}
									application.formalParameters.push(param);
								}
							}
							break;
						}
						case "Description":{
							application.description = childNodes[i].childNodes[j].text;
							break;
						}
						case "xpdl:ExtendedAttributes":{
							application.extendedAttributes = new Array();
							for(var k=0; k<childNodes[i].childNodes[j].childNodes.length; k++){
								var extendedAttribute = new Object();
								extendedAttribute.name = childNodes[i].childNodes[j].childNodes[k].getAttribute("Name");
								extendedAttribute.value = childNodes[i].childNodes[j].childNodes[k].getAttribute("Value");
								application.extendedAttributes.push(extendedAttribute);
							}
							break;
						}
					}
				}
				
				applications.push(application);
			}
		}
		
		return applications;
	}
	
	//从给定的XML节点组装出Activity数组
	this.getActivities = function(activityNode){
		var activities = new Array();
		
		with(activityNode){
			for(var i=0; i<childNodes.length; i++){
				var activity = new Activity();
				activity.id = childNodes[i].getAttribute("Id");
				activity.name = childNodes[i].getAttribute("Name");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "Description":{
							activity.description = childNodes[i].childNodes[j].text;
							break;
						}
						case "Performer":{
							activity.performer = childNodes[i].childNodes[j].text;
							break;
						}
						case "Limit":{
							activity.limit = childNodes[i].childNodes[j].text;
							break;
						}
						case "Priority":{
							activity.priority = childNodes[i].childNodes[j].text;
							break;
						}
						case "StartMode":{
							activity.startMode = childNodes[i].childNodes[j].text;
							break;
						}
						case "FinishMode":{
							activity.finishMode = childNodes[i].childNodes[j].text;
							break;
						}
						case "TransitionRestrictions":{
							if(childNodes[i].childNodes[j].childNodes.length == 0){
								break;
							}
							
							with(childNodes[i].childNodes[j]){
								//流入流出方式
								var transitionRestriction = new Object();
								for(var k=0; k<firstChild.childNodes.length; k++){
									if(firstChild.childNodes[k].nodeName == "Join"){
										transitionRestriction.joinType = firstChild.childNodes[k].getAttribute("Type");
									}
									
									if(firstChild.childNodes[k].nodeName == "Split"){
										transitionRestriction.splitType = firstChild.childNodes[k].getAttribute("Type");
									}
								}
								activity.transitionRestrictions.push(transitionRestriction);
							}
							break;
						}
						case "Implementation":{
							activity.implementation.tool.id = childNodes[i].childNodes[j].firstChild.getAttribute("Id");
							with(childNodes[i].childNodes[j]){
								//把实参装配起来


								var tool = firstChild;
								if(tool.childNodes.length != 0){
									for(var k=0; k<tool.firstChild.childNodes.length; k++){
										var param = tool.firstChild.childNodes[k].text;
										activity.implementation.tool.actualParameters.push(param);
									}
								}
							}
							break;
						}
						case "xpdl:ExtendedAttributes":{
							activity.extendedAttributes = new Array();
							for(var k=0; k<childNodes[i].childNodes[j].childNodes.length; k++){
								var extendedAttribute = new Object();
								
								extendedAttribute.name = childNodes[i].childNodes[j].childNodes[k].getAttribute("Name");
								
								//这里有个特例，异常节点的配置属性值是个对象


								if(extendedAttribute.name == "ExExceptionConfigs"){
									var configs = new Array();
									
									with(childNodes[i].childNodes[j].childNodes[k]){
										for(var m=0; m<firstChild.childNodes.length; m++){
											var config = new Object();
											config.reasonCatalogId = firstChild.childNodes[m].getAttribute("ExReasonCatalogId");
											config.reasonCatalogName = firstChild.childNodes[m].getAttribute("ExReasonCatalogName");
											config.returnReasonId = firstChild.childNodes[m].getAttribute("ExReturnReasonId");
											config.returnReasonName = firstChild.childNodes[m].getAttribute("ExReturnReasonName");
											config.startActivityId = firstChild.childNodes[m].getAttribute("ExStartActivityId");
											config.startActivityName = firstChild.childNodes[m].getAttribute("ExStartActivityName");
											config.endActivityId = firstChild.childNodes[m].getAttribute("ExEndActivityId");
											config.endActivityName = firstChild.childNodes[m].getAttribute("ExEndActivityName");
											config.startMode = firstChild.childNodes[m].getAttribute("ExStartMode");

											if (firstChild.childNodes[m].getAttribute("ExAutoToManual") == "true") {
												config.autoToManual = true;
											}
											else{
												config.autoToManual = false;
											}
											
											configs.push(config);
										}
									}
									
									extendedAttribute.value = configs;
								}else{
									extendedAttribute.value = childNodes[i].childNodes[j].childNodes[k].getAttribute("Value");
								}
								activity.extendedAttributes.push(extendedAttribute);
							}
							break;
						}
					}
				}
				
				activities.push(activity);
			}
		}
		
		return activities;
	}
	
	//从给定的XML节点组装出Transition数组
	this.getTransitions = function(transitionNode){
		var transitions = new Array();
		
		with(transitionNode){
			for(var i=0; i<childNodes.length; i++){
				var transition = new Transition();
				transition.id = childNodes[i].getAttribute("Id");
				transition.name = childNodes[i].getAttribute("Name");
				transition.from = childNodes[i].getAttribute("From");
				transition.to = childNodes[i].getAttribute("To");
				
				for(var j=0; j<childNodes[i].childNodes.length; j++){
					switch(childNodes[i].childNodes[j].nodeName){
						case "Description":{
							transition.description = childNodes[i].childNodes[j].text;
							break;
						}
						case "xpdl:Condition":{
							var expression = childNodes[i].childNodes[j].firstChild;
							if(expression.childNodes.length >0){
								transition.condition = expression.firstChild.text;
							}
							break;
						}
						case "xpdl:ExtendedAttributes":{
							transition.extendedAttributes = new Array();
							for(var k=0; k<childNodes[i].childNodes[j].childNodes.length; k++){
								var extendedAttribute = new Object();
								extendedAttribute.name = childNodes[i].childNodes[j].childNodes[k].getAttribute("Name");
								extendedAttribute.value = childNodes[i].childNodes[j].childNodes[k].getAttribute("Value");
								transition.extendedAttributes.push(extendedAttribute);
							}
							break;
						}
					}
				}
				transitions.push(transition);
			}
		}
		
		return transitions;
	}
}