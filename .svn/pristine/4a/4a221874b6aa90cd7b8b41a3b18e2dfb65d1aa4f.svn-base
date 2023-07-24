var __war="/MOBILE/ActionInvocation";
var allParaDoc=null;
var allParaEle=null;

function invokeAction(actionPath){
		var lastIndexInt = _validateActionPath(actionPath);
		if(lastIndexInt==-1){
			return;
		}
        var xmlhttp = makeHTTP();
		__clear();
		allParaEle.setAttribute("actionSpace",actionPath.substr(0,lastIndexInt));
		allParaEle.setAttribute("actionName",actionPath.substr(lastIndexInt+1,actionPath.length));
	  allParaEle.setAttribute("actionPath",actionPath);
	  allParaDoc.appendChild(allParaEle);

	  for(var i=1;i<arguments.length;i++){
	  	__add(arguments[i]);
	  }
		xmlhttp.open("POST",__war,false);
        if(browerType=='firefox'){
            var x = new XMLSerializer().serializeToString(allParaDoc)
            xmlhttp.send(new XMLSerializer().serializeToString(allParaDoc));
        }else{
            xmlhttp.send(allParaDoc.xml);
        }
		try{
			if(xmlhttp.status==200){
				var rtStr = xmlhttp.responseText;
				//window.prompt("xml", rtStr);
			  return _xmlToObject(rtStr);
			}else
				throw new Error(0,"report...\n\nxmlhttp.status:"+xmlhttp.status +"\n\n\nxmlhttp.responseText:"+xmlhttp.responseText);
		}finally{allParaEle = allParaDoc = null;}
}

function _validateActionPath(actionPath){
		if(actionPath==undefined || actionPath==null || actionPath==""){
			alert(actionPath+'\nactionPath ��ʽ����!\n��׼��ʽΪ"action����/action��".');
			return -1;
		}
		var lastIndexInt = actionPath.lastIndexOf("/");
		if(lastIndexInt==-1 || lastIndexInt==0 || (lastIndexInt == actionPath.length-1)){
			alert(actionPath+'\nactionPath ��ʽ����!\n��׼��ʽΪ"action����/action��".');
			return -1;
		}
		return lastIndexInt;
}
function __clear(){
      if(browerType=='firefox'){
         allParaDoc = document.implementation.createDocument( "text/xml", "",null);//
      }else{
         allParaDoc = makeDOM();
      }
	  var node = allParaDoc.createProcessingInstruction("xml","version='1.0' encoding='GBK'");
	  allParaDoc.appendChild(node);
	  allParaEle = allParaDoc.createElement("input");
}
function __add(obj){
	  var type = _getObjectType(obj);
	  if (type == 'c'){
			childEle=_getXmlDocFromArr(obj);
	  }else if (type == 'm'){
			childEle=_getXmlDocFromObj(obj);
	  }else if (type == 't'){
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("value",DateToString(obj,true));
	  }else if (type == 't2'){
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("value",obj.t);
	  }else{
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("value",obj==null?"":obj.toString());
	  }
	  childEle.setAttribute("type",type);
          allParaEle.appendChild(childEle);
}

function _getXmlDocFromObj(obj,name){
	var param = allParaDoc.createElement("param");
	param.setAttribute("type","m");
	if (name!=null){
		param.setAttribute("name",name);
	}
	for(var nameT in obj){
	  var valueT = obj[nameT];
	  var type = _getObjectType(valueT);
	  if (type == 'c'){
			param.appendChild(_getXmlDocFromArr(valueT,nameT));
	  }else if (type == 'm'){
			param.appendChild(_getXmlDocFromObj(valueT,nameT));
	  }else if (type == 't'){
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("type",type);
			childEle.setAttribute("name",nameT);
			childEle.setAttribute("value",DateToString(valueT,true));
			param.appendChild(childEle);
	  }else if (type == 't2'){
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("type",type);
			childEle.setAttribute("name",nameT);
			childEle.setAttribute("value",valueT.t);
			param.appendChild(childEle);
	  }else{
			var childEle = allParaDoc.createElement("param");
			childEle.setAttribute("type",type);
			childEle.setAttribute("name",nameT);
			childEle.setAttribute("value",valueT==null?"":valueT.toString());
			param.appendChild(childEle);
	  }
	}
	return param;
}
function _getXmlDocFromArr(arr,name){
	var param = allParaDoc.createElement("param");
	param.setAttribute("type","c");
	if (name!=null){
		param.setAttribute("name",name);
	}
	for(var i=0;i<arr.length;i++){
	  var type = _getObjectType(arr[i]);
	  var childEle = allParaDoc.createElement("param");
	  if (type == 'c'){
			param.appendChild(_getXmlDocFromArr(arr[i]));
	  }else if (type == 'm'){
			param.appendChild(_getXmlDocFromObj(arr[i]));
	  }else{
			var childEle = allParaDoc.createElement("param");
	    childEle.setAttribute("type",type);
			childEle.setAttribute("value",arr[i].toString());
			param.appendChild(childEle);
	  }
	}
	return param;
}

function _getObjectType(o){
  if(o==null) return 'n';
  switch(o.constructor){
    case Number:
      if((o%1)==0) return 'i';
      else return 'f';
    case Boolean:
      return 'b';
    case String:
      return 's';
    default:
      if(o.constructor.toString().indexOf("Array")>0)
        return 'c';
      else if(o.constructor.toString().indexOf("Date2")>0)
        return 't2'
      else if(o.constructor.toString().indexOf("Date")>0)
        return 't'
      else return 'm';
  }
}

function _xmlToObject(xmlstr){
	
    if(browerType =='firefox'){
        var dp = new DOMParser(); 
        var doc = dp.parseFromString(xmlstr, "text/xml");
        var root = doc.firstChild;
    }else{
        var doc = makeDOM();
        doc.loadXML(xmlstr);
        var root = doc.selectSingleNode("//Output");
    }
	if(!root)return null;
	try{
	   return _getObjectFromEle(root);
	}catch(e){
	   throw e;
	}

}
function _showWarnWindow(exceptionStr){
	alert("���?"+exceptionStr);
}

function _getObjectFromEle(ele){
	var rtType = ele.getAttribute("type");
	if (rtType == "i"||rtType == "s"||rtType == "l"||rtType == "b"||rtType == "f"){
		return ele.childNodes[0].nodeValue;
	}else if (rtType == "t"||rtType == "t2"){
		return ele.childNodes[0].nodeValue;
	}else if (rtType == "e"){
		_showWarnWindow(ele.childNodes[0].nodeValue);
		return "error";
	}else if (rtType == "m"){
		return _getMFromEle(ele);
	}else if (rtType == "c"){
		return _getCFromEle(ele);
	}else if (rtType == "ele"){
		return _getEleFromEle(ele);
	}else if (rtType == "ve"){
		var eO = new Object();
		eO.code=ele.childNodes[0].getAttribute("code");
		eO.level=ele.childNodes[0].getAttribute("code");
		eO.e=ele.childNodes[0].childNodes[0].nodeValue;
		showWarnWindow(eO.level+"\n"+eO.code+"\n"+eO.e);
		throw eO;
	}
	else{
		return null;
	}
}
function _getEleFromEle(ele){
	var items = ele.childNodes[0].childNodes;
	var rtObj = new Object();
	for(var i=0;i<items.length;i++){
	    var childE = items[i];
	    var rtType = childE.getAttribute("type");
		if (rtType == "s"){
		    rtObj[childE.getAttribute("name")] = childE.getAttribute("value");
		}else if (rtType == "c"){
			rtObj[childE.getAttribute("name")] = childE.childNodes[0].xml;
		}
	}
	return rtObj;
}
function _getCFromEle(ele){
	var items = ele.childNodes;
	var arr = new Array();
	for(var i=0;i<items.length;i++){
	    var childE = items[i];
	    var rtType = childE.getAttribute("type");
		if (rtType == "c"){
			arr.push(_getCFromEle(childE));
		}else if (rtType == "m"){
			arr.push(_getMFromEle(childE));
		}else{
			arr.push(childE.childNodes[0].text);
		}
	}
	return arr;
}
function _getMFromEle(ele){
	var items = ele.childNodes;
	var rtObj = new Object();
	for(var i=0;i<items.length;i++){
		var childE = items[i];
		var rtType = childE.getAttribute("type");
		var rtName = childE.getAttribute("name");
		if (rtType == "c"){
			rtObj[rtName] = _getCFromEle(childE);
		}else if(rtType == "m"){
			rtObj[rtName] = _getMFromEle(childE);
		}else{
			rtObj[rtName] = childE.childNodes[0]==null?"":childE.childNodes[0].nodeValue;
		}
	}
	return rtObj;
}
function makeDOM(){
if(browerType=='firefox'){
     return document.implementation.createDocument( "text/xml", "",null);//
  }
  var o,suffixs=[".4.0", ".3.0", ".2.0", ""];
  for(var i=0;i<suffixs.length;i++){
    try{
      o = new ActiveXObject("msxml2.DOMDocument"+ suffixs[i]);
      break;
    }catch(ex){};
  }
  o.async = false;o.validateOnParse = false;o.resolveExternals = false;return o;
}
//function makeHTTP(){
//  var o,suffixs=[".3.0", ""];
//  for(var i=0;i<suffixs.length;i++){
//    try{
//      o = new ActiveXObject("Msxml2.XMLHTTP"+ suffixs[i]);
//      break;
//    }catch(ex){};
//  }
//  o = new ActiveXObject("Microsoft.XMLHTTP");
//  return o;
//}
var browerType = 'ie';
function makeHTTP() {
	var A = null;
	try {
		A = new ActiveXObject('Msxml2.XMLHTTP')
	} catch (e) {
		try {
			A = new ActiveXObject('Microsoft.XMLHTTP')
		} catch (oc) {
			A = null
		}
	}
	if (!A && typeof XMLHttpRequest != 'undefined') {
        browerType = 'firefox'
		A = new XMLHttpRequest()
        
	}
	return A

}


function GetRemoteURL(url){
	
	var session1 = GetSession();
	var tocken = session1.loginInfo.loginTocken;
	
	if(url.indexOf("http://")==-1){
	
		return url;	
	}
	var ipAddress = url.substring(7,url.indexOf('/',7));
	var systems = session1.systems;
	for(var i=0;i<systems.length;i++){
		
		if(systems[i].sysIpAddress==ipAddress){
			if(systems[i].ssoTypeId==1){
				if(url.indexOf("?")==-1){
			    	url+="?tocken="+session1.loginInfo.loginTocken;
			    }else{
			    	url+="&tocken="+session1.loginInfo.loginTocken;
			    }
				url+="&username="+systems[i].thirdUsername;
				url+="&roleId="+systems[i].thirdRoleId;
				url+="&reqSrc=ossmh";
			}else if(systems[i].ssoTypeId==2){
				var loginKey = invokeAction('/loginService/SSOAction',{username:systems[i].thirdUsername});
				if(url.indexOf("?")==-1){
			    	url+="?loginKey="+loginKey;
				}else{
				    url+="&loginKey="+loginKey;
				 }
				 url+="&username="+systems[i].thirdUsername;
				 url+="&userName="+systems[i].thirdUsername;
				 url+="&oper=logon";
				return url;
			}//�����¼)չ
			break;
		}
	}
	
	return url;
}