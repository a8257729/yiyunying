var Utils = ({
    Browser: (function(){
        var ua = navigator.userAgent;
        var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]';
        return {
            IE:             !!window.attachEvent && !isOpera,
            Opera:          isOpera,
            WebKit:         ua.indexOf('AppleWebKit/') > -1,
            Gecko:          ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1,
            MobileSafari:   /Apple.*Mobile/.test(ua)
        }
    })()
});

// Fix bug for variable browerType undefined.
var browerType = Utils.Browser.Gecko? "firefox" : "ie";

function callRemoteFunctionNoTrans(serviceName, funcName){
  var begin = (new Date()).getTime();//fj
  var url = gWebAbsPath+"/busifacadeservlet";
  xmlHttp = makeHTTP();
  var str = typeof xmlHttp ;
  var doc = makeDOM();
  
  var node = doc.createProcessingInstruction("xml","version='1.0'");
  doc.appendChild(node);
  node = doc.appendChild(doc.createElement("Function"));
  node.setAttribute("name",funcName);
  node.setAttribute("serviceName",serviceName);
  //为事务处理新加的属性


  node.setAttribute("userTransaction","false");
  node.setAttribute("generatedTime",new Date());

  for(var i=2;i<arguments.length;i++){
    var elm = doc.createElement("Param");
    var type = getObjectType(arguments[i]);
    elm.setAttribute("type", type);
    packageObject(elm, type, arguments[i]);
    node.appendChild(elm);
  }
  //window.prompt("xml", doc.xml);
  //发送到后台
  //alert(doc.xml);
  var retVal;
  //var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
  xmlHttp.open("POST", url, false);
  if(browerType == 'firefox'){
        var x = new XMLSerializer().serializeToString(doc);
        xmlHttp.send(new XMLSerializer().serializeToString(doc));
    }else{
        xmlHttp.send(doc.xml);
    } 

    
//  newWindow.document.write("<br>函数名:"+funcName+"-组装并发送XML到后台，花费时间:"+((new Date()).getTime()-begin));//fj
  begin = (new Date()).getTime();
  
  if(xmlHttp.status!=200){
    throw new Error(0,"Network issue or remote server issue");
    return;
  }else retVal = xmlHttp.responseText;
  
//  newWindow.document.write("<br>函数名:"+funcName+"-后台执行并传回结果XML，花费时间:"+((new Date()).getTime()-begin));//fj
//  begin = (new Date()).getTime();
  	
  //window.prompt("xml", retVal);
  //xml转为object
  doc = makeDOM();
  try{
    if(browerType =='firefox'){
        var dp = new DOMParser(); 
        var doc = dp.parseFromString(retVal, "text/xml");
        var root = doc.firstChild;
        switch(root.nodeName){
          case "Output":
            return getObjectFromXml(root,root.getAttribute("type"));
          case "Error":
            throw packageError(root);
          default:
            throw new Error(0,"Remote server returns invalid xml");
        }
    }else{
       if(!doc.loadXML(retVal)){
            throw new Error(0,"The format of remote server return is not an Xml");
        }
        node = doc.documentElement;
	    switch(node.tagName){
	      case "Output":
	        return getObjectFromXml(node,node.getAttribute("type"));
	      case "Error":
	        throw packageError(node);
	      default:
	        throw new Error(0,"Remote server returns invalid xml");
	    }
	  }}
      finally{retVal = xmlHttp = node = doc = null;}
}
function callRemoteFunction(serviceName, funcName){
	var begin = (new Date()).getTime();//fj
	
  var url = gWebAbsPath+"/busifacadeservlet";
  xmlHttp = makeHTTP();
  var doc = makeDOM();
  var node = doc.createProcessingInstruction("xml","version='1.0'");
  doc.appendChild(node);
  //node = null;
  node = doc.appendChild(doc.createElement("Function"));
  node.setAttribute("name",funcName);
  node.setAttribute("serviceName",serviceName);
  //为事务处理新加的属性


  node.setAttribute("userTransaction","true");
  node.setAttribute("generatedTime",new Date());

  for(var i=2;i<arguments.length;i++){
    var elm = doc.createElement("Param");
    var type = getObjectType(arguments[i]);
    elm.setAttribute("type", type);
    packageObject(elm, type, arguments[i]);
    node.appendChild(elm);
  }
  //window.prompt("xml", doc.xml);
  //alert(doc.xml);
  //发送到后台
  var retVal;
  //var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
  
  xmlHttp.open("POST", url, false);
//  alert("url="+url);
    if(browerType == 'firefox'){
        var x = new XMLSerializer().serializeToString(doc);
        xmlHttp.send(new XMLSerializer().serializeToString(doc));
    }else{
        xmlHttp.send(doc.xml);
    } 

//  newWindow.document.write("<br>函数名:"+funcName+"-组装并发送XML到后台，花费时间:"+((new Date()).getTime()-begin));//fj
  begin = (new Date()).getTime();
  
  if(xmlHttp.status!=200){
    throw new Error(0,"Network issue or remote server issue");
    return;
  }else retVal = xmlHttp.responseText;
  	
//  newWindow.document.write("<br>函数名:"+funcName+"-后台执行并传回结果XML，花费时间:"+((new Date()).getTime()-begin));//fj
//  begin = (new Date()).getTime();
  
  //window.prompt("xml", retVal);
  //xml转为object
  doc = makeDOM();
  try{
    if(browerType =='firefox'){
        var dp = new DOMParser(); 
        var doc = dp.parseFromString(retVal, "text/xml");
        var root = doc.firstChild;
        switch(root.nodeName){
          case "Output":
            return getObjectFromXml(root,root.getAttribute("type"));
          case "Error":
            throw packageError(root);
          default:
            throw new Error(0,"Remote server returns invalid xml");
        }
    }else{
       if(!doc.loadXML(retVal)){
            throw new Error(0,"The format of remote server return is not an Xml");
        }
        node = doc.documentElement;
        switch(node.tagName){
          case "Output":
            return getObjectFromXml(node,node.getAttribute("type"));
          case "Error":
            throw packageError(node);
          default:
            throw new Error(0,"Remote server returns invalid xml");
        }
      }}finally{retVal = xmlHttp = node = doc = null;}
 	
//  newWindow.document.write("<br>函数名:"+funcName+"-前台XML转化为javascript对象，花费时间:"+((new Date()).getTime()-begin));//fj
}

//------------------------------------
//add by fang.jin 2006.8.16

var xmlHttp = false;
//var responseObj = null;
function callRemoteFunctionNoTransAsyn(recallfunction,serviceName, funcName){
	var begin = (new Date()).getTime();//fj
	
  var url = gWebAbsPath+"/busifacadeservlet";
  var doc = makeDOM();
  var node = doc.createProcessingInstruction("xml","version='1.0'");
  doc.appendChild(node);
  node = doc.appendChild(doc.createElement("Function"));
  node.setAttribute("name",funcName);
  node.setAttribute("serviceName",serviceName);
  //为事务处理新加的属性


  node.setAttribute("userTransaction","false");
  node.setAttribute("generatedTime",new Date());

  for(var i=3;i<arguments.length;i++){
    var elm = doc.createElement("Param");
    var type = getObjectType(arguments[i]);
    elm.setAttribute("type", type);
    packageObject(elm, type, arguments[i]);
    node.appendChild(elm);
  }
  //window.prompt("xml", doc.xml);
  //发送到后台
  //alert(doc.xml);
  var retVal;
  //var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
  xmlHttp = new ActiveXObject("MSXML2.XmlHttp");
  xmlHttp.onreadystatechange = recallfunction;//fj
  xmlHttp.open("POST", url, true);
  xmlHttp.send(doc.xml);  
}

function callRemoteFunctionAsyn(recallfunction,serviceName, funcName){
	var begin = (new Date()).getTime();//fj
	
  var url = gWebAbsPath+"/busifacadeservlet";
  var doc = makeDOM();
  var node = doc.createProcessingInstruction("xml","version='1.0'");
  doc.appendChild(node);
  //node = null;
  node = doc.appendChild(doc.createElement("Function"));
  node.setAttribute("name",funcName);
  node.setAttribute("serviceName",serviceName);
  //为事务处理新加的属性


  node.setAttribute("userTransaction","true");
  node.setAttribute("generatedTime",new Date());

  for(var i=3;i<arguments.length;i++){
    var elm = doc.createElement("Param");
    var type = getObjectType(arguments[i]);
    elm.setAttribute("type", type);
    packageObject(elm, type, arguments[i]);
    node.appendChild(elm);
  }
  //window.prompt("xml", doc.xml);
  //alert(doc.xml);
  //发送到后台
  var retVal;
  //var xmlHttp = new ActiveXObject("Microsoft.XmlHttp");
  xmlHttp = new ActiveXObject("MSXML2.XmlHttp");
  xmlHttp.onreadystatechange = recallfunction;//fj
  xmlHttp.open("POST", url, true);
//  alert("url="+url);
  xmlHttp.send(doc.xml);  
  if(xmlHttp.readyState != 4){
	 		return;
	}
}

function getResponseXML(){
//	alert(xmlHttp.status);
	 if(xmlHttp.readyState != 4){
	 		return;
	 }
	 else{
		  if(xmlHttp.status!=200){
			    throw new Error(0,"Network issue or remote server issue");    
			    return;
		  }else retVal = xmlHttp.responseText;
	 }
  	//alert(retVal);
  	
//  newWindow.document.write("<br>函数名:"+funcName+"-后台执行并传回结果XML，花费时间:"+((new Date()).getTime()-begin));//fj
//  begin = (new Date()).getTime();
  
  //window.prompt("xml", retVal);
  //xml转为object
  var doc = makeDOM();
//  alert(doc);
  try{
    if(!doc.loadXML(retVal)){
      throw new Error(0,"The format of remote server return is not an Xml");return;
    }
    var node = doc.documentElement;
    switch(node.tagName){
      case "Output":
        return getObjectFromXml(node,node.getAttribute("type"));       
      case "Error":
        throw packageError(node);
      default:
        throw new Error(0,"Remote server returns invalid xml");
    }
  }finally{retVal = xmlHttp = node = doc = null;}	
}


//----------------------------------------

<!--private -->
function IsInt(num){
  return ((num%1)==0)
}

function packageError(oN){
  var e1 =new Error(0, oN.childNodes[1].text);
  e1.code = oN.childNodes[0].text;
  e1.resolve = oN.childNodes[2].text;
  e1.detail = oN.childNodes[3].text;
  e1.toString = function(){return (e1.description +((e1.resolve&&""!=e1.resolve)?"\n"+e1.resolve:""));};
  return e1;
}
function makeDOM(){
   var o,suffixs=[".4.0", ".3.0", ".2.0", ""];
  for(var i=0;i<suffixs.length;i++){
    try{
      o = new ActiveXObject("msxml2.DOMDocument"+ suffixs[i]);
      break;
    }catch(ex){};
  }
  try{
    o.async = false;o.validateOnParse = false;o.resolveExternals = false;return o;
  }catch(ex1){
    return document.implementation.createDocument( "text/xml", "",null);//
   }
}
function getObjectType(o){
  //n=null,b,i,f,s,d,o,a
  if(o==null) return 'n';
  switch(o.constructor){
    case Number:
      if(IsInt(o)) return 'i';
      else return 'f';
    case Boolean:
      return 'b';
    case String:
      return 's';
    case Date:
      return 'd';
    case Array:
      return 'a';
    default:
      if(o.constructor.toString().indexOf("Array")>0)
        return 'a';
      else if(o.constructor.toString().indexOf("Date")>0)
        return 'd'
      else return 'o';
  }
}

function packageObject(elm,type,arg){
  switch(type){
    case 'n':
      break;
    case 'b':
    case 'i':
    case 'l':
    case 'f':
    case 's':
        if(elm.text!= undefined){
            elm.text = StrEnCode(arg.toString());break;
        }else{
            elm.textContent = StrEnCode(arg.toString());break;
        }
    case 'd':
      if(elm.text!= undefined){
            elm.text = DateToString(new Date(arg.valueOf()), true);break;
        }else{
            elm.textContent = DateToString(new Date(arg.valueOf()), true);break;
        }
    case 'o':
      for(var key in arg){
        var child =arg[key];
        var subtype = getObjectType(child);
        //it's empty deal to its minValue in b/i/f
        var childElm = elm.ownerDocument.createElement(subtype+key);
        elm.appendChild(childElm);
        packageObject(childElm,subtype,child);
      }
      break;
    case 'a':
      for(var i=0;i<arg.length;i++){
        var child =arg[i];
        var subtype = getObjectType(child);
        var childElm = elm.ownerDocument.createElement(subtype+"Item");
        elm.appendChild(childElm);
        packageObject(childElm,subtype,child);
      }
      break;
  }
}
function StrEnCode(s){
  return (s)? s.replace(/'/g, "’").replace(/"/g, "”").replace(/</g, "＜").replace(/>/g, "＞").replace(/&/g, "＆") : "";
}
function getObjectFromXml(elm,type){
  if(type==null) return null;
  switch(type){
    case 'n':
      return null;
    case 'b':
    case 'B':
    if(elm.text!= undefined){
        return (elm.text=="true");
    }else{
        return (elm.textContent=="true");
    }
      
    case 'i':
    case 'I':
    case 'l':
    case 'L':
	    if(elm.text!= undefined){
	        var val = parseInt(elm.text,10);
	    }else{
	        var val = parseInt(elm.textContent,10);
	    }
      if(isNaN(val)) throw new Error(0, elm.tagName+" must be an integer");
      return val;
    case 'f':
    case 'F':
       if(elm.text!= undefined){
            var val = parseFloat(elm.text);
        }else{
            var val = parseFloat(elm.textContent);
        }
      
      if(isNaN(val)) throw new Error(0,elm.tagName +" must be an float");
      return val;
    case 's':
    case 'S':
        if(elm.text!= undefined){
            return elm.text;
        }else{
            return elm.textContent;
        }
    case 'd':
    case 'D':
      try{
         if(elm.text!= undefined){
            return StringToDate(elm.text);
        }else{
            return StringToDate(elm.textContent);
        }
      }catch(ex){throw new Error(0,elm.tagName +" must be in a DateTime format(yyyy-MM-dd HH:mm:ss)");}
    case 'o':
      {
        var obj = new Object();
        var nodes = elm.childNodes;
        for(var i=0;i<nodes.length;i++){
          var child = nodes[i];
          if(child.nodeType==1){  //NODE_ELEMENT
            var childtype = child.tagName.charAt(0);
            var key = child.tagName.substring(1);
            obj[key] = getObjectFromXml(child,childtype);
          }
        }
        return obj;
      }
    case 'a':
      {
        var arr = new Array();
        var nodes = elm.childNodes;
        for(var i=0;i<nodes.length;i++){
          var child = nodes[i];
          if(child.nodeType==1){   //NODE_ELEMENT
            var childtype = child.tagName.charAt(0);
            var key = child.tagName.substring(1);
            if(key=="Item"){
              arr[arr.length] = getObjectFromXml(child,childtype);
            }
          }
        }
        return arr;
      }
    case 'v':
      return null;
    default:
      throw new Error(0,"type '"+ type +"' can't be recognized");
  }
}