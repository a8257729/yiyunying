var gLangCode="zh_cn";function setCookie(val){var Then=new Date();Then.setUTCDate(Then.getUTCDate()+1);document.cookie="cookie_lang1="+val+";expires="+Then.toGMTString();};function getCookie(){var cookieString=new String(document.cookie);var cookieHeader="cookie_lang1=";var beginPosition=cookieString.indexOf(cookieHeader);if(beginPosition>-1){var lastPosi=cookieString.indexOf(";",beginPosition+cookieHeader.length);gLangCode=cookieString.substring(beginPosition+cookieHeader.length,((lastPosi>-1)?lastPosi:cookieString.length));};};getCookie();
var gWebAbsPath="/MOBILE";
function DateToString(d,useTime){
		if(d)return d.getFullYear()+"-"+StrZeroAdd(d.getMonth()+1)+"-"+StrZeroAdd(d.getDate())+((useTime)?" "+StrZeroAdd(d.getHours())+":"+StrZeroAdd(d.getMinutes())+":"+StrZeroAdd(d.getSeconds()):"");else return "";
	
	};
	
function StringToDate(s){
		var s = s.substring(0,19);
		var aD=s.split(/[\/\-: ]/);
		if(aD.length<3)return null;
		if(aD.length<4)aD[3]=aD[4]=aD[5]="00";
		var d=new Date(aD[0],parseInt(aD[1]-1,10),aD[2],aD[3],aD[4],aD[5]);
		if(isNaN(d))return null;return d;
};
function Trim(s){
		return ((typeof(s)!="string")?"":s.replace(/(^\s*)|(\s*$)/g, ""));
};
function NullRepl(inStr,repStr){
	return(inStr!=null)?inStr:repStr;
};
function BlankRepl(s){
	return (typeof(s)=="string"&&s!='')?s:null;
};
function StrRepl(inStr,orgStr,repStr){
	return(inStr!=orgStr)?inStr:repStr;
};
function IsNull(inStr){
	return(inStr==null||inStr==""||typeof(inStr)=="undefined")?true:false;
};

function GetUrlParameter(param){
	var url=window.location.search;
	var pos1=0,pos2=0;pos1=url.indexOf("&"+param+"=");
	if(pos1<0)pos1=url.indexOf("?"+param+"=");
	if(pos1>-1){
		pos2=url.indexOf("&",pos1+1);
			if(pos2==-1)pos2=url.length;
			return url.substring(pos1+param.length+2,pos2);
	}else return null;
};
function _import(c1,b1){
	c1=Trim(c1);
	var x=makeHTTP();
	x.open("GET",gWebAbsPath+"/"+((c1.indexOf(".")>0)?(c1.replace(/\./g,"/")+".js"):("public/script/"+c1+((b1)?".jsp":".js"))),false);
	x.send();
	if(x.status==200||x.status==0){
		if(!!(window.attachEvent && !window.opera)){   
			//ie  execScript执行后的上下文是全局的
			execScript(x.responseText,"JScript");   
		}else{   
			//not ie   IE6/7/8中，eval和window.eval一样，写在自定义函数内是局部闭包，否则是全局闭包。
			//在Firefox中，window.eval()与直接eval()的效果是不同的。前者的效果接近execScript。
			window.eval(x.responseText);  
		}
	}
	else alert("Load "+ c1 +"Class Failed!");
};
function makeHTTP(){
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
        this.browerType='firefox'
        A = new XMLHttpRequest()
    }
    return A
}
eval('function StrZeroAdd(s){s="0"+s;return s.substr(s.length-2);};function _extends(o1,o2,a1,s1){eval("o1.base=o2;o1.base("+(a1||"")+");if(s1)o1._super=new o2("+(a1||"")+")");};Date.prototype.toString=function(){return DateToString(this,true);};function PopC__(pObj,evt){var co1={langCode:gLangCode, useDate:((pObj.useDate=="true")?true:false),useTime:((pObj.useTime=="true")?true:false),value:(pObj.value).substring(0,19)};var tmpStr=((co1.useDate&&co1.useTime)?"255":((co1.useDate)?"233":"142"));var retObj=window.showModalDialog(gWebAbsPath+"/public/htc/DateTimeText/DateTimeText.htm",co1,"dialogTop="+(window.event.screenY+13)+"px;dialogLeft="+(window.event.screenX-141)+"px;dialogHeight="+tmpStr+"px;dialogWidth=228px;status=0;center=0;scroll=0");if(retObj!=null&&co1.value!=retObj){pObj.value=retObj;eval(evt);};};function ObjectClone(o){if(o){switch(o.constructor){case Object:var o2=new Object();for(var i in o)o2[i]=o[i];return o2;case Array:var o2=new Array();for(var i=0;i<o.length;i++)o2[i]=o[i];return o2;default:return o;};}else return null;};function SelCommPop(prefixStr,dispType,title,xmlStr,isRet,height1,width1){var recObj=window.showModalDialog(gWebAbsPath+"/common/selCommAll.jsp?type="+(dispType||1)+((title)?"&tit="+title:""),((xmlStr)?new String(xmlStr):null),"dialogWidth:"+(width1||240)+"px;dialogHeight:"+(height1||"290")+"px;scroll:no");if(recObj){if(isRet==true){return recObj;}else{document.all(prefixStr+"Name").value=recObj.name;document.all(prefixStr+"Id").value=recObj.id;}}};function RemoveAttrs(o1){if(!o1)return;for(var i=1;i<arguments.length;i++)delete o1[arguments[i]];};var __func_;function ChangeBoxBar(pIndex){var _img=document.all("BoxImg"+pIndex);if((_img.src).indexOf("up")>-1){_img.src="../images/icon/arrow_layer_down.gif";document.all("BoxContent"+pIndex).style.display="";}else{_img.src="../images/icon/arrow_layer_up.gif";document.all("BoxContent"+pIndex).style.display="none";};};function ExecWait(func,mst){__func_=func;document.all.p_o_p_9.style.display="";gTimeout=window.setTimeout(_ExecWait_,mst||500);};function MergeAttrs(o1,o2,b){if(!o1&&!o2)return;for(var i in o2)if(!b||!o1[i])o1[i]=o2[i];};function _ExecWait_(){try{eval(__func_);}catch(e){ErrorHandle(e.message);}finally{window.clearTimeout(gTimeout);document.all.p_o_p_9.style.display="none";}}');
function ExportToPrint(objName, pMode){
	OpenWin(gWebAbsPath+"/public/script/ExportPrint.htm?tlnm="+objName+"&mode="+pMode,window.screen.availHeight-103,window.screen.availWidth-10,"77px","0px","_exportP");
};
function ErrorHandle(pContent, pType, pContentDetail, pIsReturn, pTitle){
	var paraError = {title:pTitle, content:pContent, contentDetail:pContentDetail, type:pType||3, isReturn:pIsReturn};
	var returnObj = OpenShowDlg(gWebAbsPath +"/public/script/ErrorHandle.jsp", 193, 300, paraError);
	if(pIsReturn) return returnObj;
};

function OpenWin(pUrl,pHeight,pWidth,pTop,pLeft,pWinName,pExtProp){
	
	try{
		if(session1){
			_import("AlternateData");
			var altData = new AlternateData();
			var obj = new Object();
			obj.session1 = session1;
			altData.setValue(obj);
		}
	}catch(ex){;};
	
	return window.open(pUrl,NullRepl(pWinName,""),"height="+pHeight+",width="+pWidth+",left="+(pLeft||((pWidth>screen.availWidth)?0:((screen.availWidth-pWidth)/2-10)))+",top="+(pTop||((pHeight>screen.availHeight)?0:((screen.availHeight-pHeight)/2-10)))+",scrollbars=yes,status=no,toolbar=no,menubar=no, "+NullRepl(pExtProp,"")+" location=no");
}

function OpenShowDlg(url,height,width,obj1,pExtProp){
	
	try{
		if(session1){
			_import("AlternateData");
			var altData = new AlternateData();
			var obj = new Object();
			obj.session1 = session1;
			altData.setValue(obj);
		}
	}catch(ex){;};
	
	return window.showModalDialog(url,obj1,"dialogWidth:"+width+"px;dialogHeight:"+(height+20)+"px;status:0;"+ NullRepl(pExtProp,""));
};


function GetSession(){
	try{
		if(window.top&&window.top.name=="_mainitweb_MOBILE"){
		
			return window.top.session1;
		}else if(window.parent&&window.parent.session1){
			return window.parent.session1;
		}
        else if(window.opener&&window.opener.session1){
            return window.opener.session1;
        }
        else{
			_import("SessionJs",true);
			return new Session();
		};
	}catch(ex){
		if(window.parent&&window.parent.session1){
			return window.parent.session1;
		}else{
			_import("SessionJs",true);
			return new Session();
		};
	}
};
document.onhelp = function() {
	if(window.top.mainFrame){
		window.top.mainFrame.showHelp();
	}
	event.returnValue = false;
	event.cancelBubble = true;
	return false;
}
var EventUtil = {
	addHandler:function(element, type, handler){  
	            	if (element.addEventListener){        
	            		     element.addEventListener(type, handler, false);        
	                 } else if (element.attachEvent){      
	                          element.attachEvent("on" + type, handler);          
	                 } else {           
	                         element["on" + type] = handler;       
	                 }        
	             },
	 removeHandler:function(element, type, handler){  
		             if (element.removeEventListener){                 
		                  element.removeEventListener(type, handler, false);  
		             } else if (element.detachEvent){              
		                  element.detachEvent("on" + type, handler);  
		             } else {                 
		                   element["on" + type] = null;      
		             }         
	      		 },  
	 getEvent:function(event){          
	 		     return event?event:window.event;
	           }, 
	 getTarget:function(event){
	             return event.target || event.srcElement;   
	           },
	 preventDefault:function(event,msg){       
					 	  if (event.preventDefault){    
					 	        event.preventDefault();            
						  } else {               
						  		event.returnValue = msg;   
				          }
     				 },
   	 stopPropagation: function(event){      
   	        if (event.stopPropagation){    
   	                event.stopPropagation();        
   	         } else {        
   	                event.cancelBubble = false;        
   	         }     
   	} 
 
   	
}

function GetScreenWidth(){
	return window.screen.width;
} 
function GetScreenHeight(){
	return window.screen.height;
}


