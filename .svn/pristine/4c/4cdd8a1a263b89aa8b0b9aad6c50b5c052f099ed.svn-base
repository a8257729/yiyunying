//格式化日期原型
Date.prototype.pattern = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份         
		"d+": this.getDate(), //日         
		"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时         
		"H+": this.getHours(), //小时         
		"m+": this.getMinutes(), //分         
		"s+": this.getSeconds(), //秒         
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度         
		"S": this.getMilliseconds() //毫秒         
	};
	var week = {
		"0": "/u65e5",
		"1": "/u4e00",
		"2": "/u4e8c",
		"3": "/u4e09",
		"4": "/u56db",
		"5": "/u4e94",
		"6": "/u516d"
	};
	if(/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if(/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

var hsTools = {
	isEmpty: function(param) {
		if(null == param || "" == param) {
			return true;
		};
		return false;
	},
	isNotEmpty: function(variable) {
		if(variable != null && variable != undefined && variable != '') {
			return true;
		}
		return false;

	},
	jsonDateFormat: function(jsonDate) { //json日期格式转换为正常格式
		try {
			var date = new Date();
			date.setTime(jsonDate.time);
			date.setHours(jsonDate.hours);
			date.setMinutes(jsonDate.minutes);
			date.setSeconds(jsonDate.seconds);
			return date.pattern("yyyy-MM-dd hh:mm:ss");

		} catch(ex) {
			return "";
		}
	},
	diffDayAndHour: function(dateStart, dateEnd) {
		var diffDate = dateEnd.getTime() - dateStart.getTime(); //时间差的毫秒数
		//计算出相差天数
		var days = Math.floor(diffDate / (24 * 3600 * 1000));
		//计算出小时数
		var leave = diffDate % (24 * 3600 * 1000); //计算天数后剩余的毫秒数
		var hours = Math.floor(leave / (3600 * 1000));
		return days + "天" + hours + "小时";
	},
	parseLocationSearch: function() { //解析本地url为对象
		var queryObject = {};
		var locationSearch = decodeURI(window.location.search);
		if(locationSearch != '') {
			var pairs = locationSearch.substring(1).split('&');
			for(var i = 0; i < pairs.length; i++) {
				var pair = pairs[i];
				var keyAndValue = pair.split('=');
				var key = keyAndValue[0];
				var value = keyAndValue[1];
				queryObject[key] = value;
			}
		}
		return queryObject;
	},
	
	
	
	
	parseUrlObj: function(url) { //解析url的get参数为对象
		var queryObject = {};
		var pairs = url.split('?');
		for(var i = 0; i < pairs.length; i++) {
			var pair = pairs[i];
			var keyAndValue = pair.split('=');
			var key = keyAndValue[0];
			var value = keyAndValue[1];
			queryObject[key] = value;
		}
		return queryObject;
	},

	putLocalStorage: function(key, value) { //把数据放到本地存储
		plus.storage.setItem(key, JSON.stringify(value));
	},
	getLocalStorage: function(key) { /*获取本地存储数据*/
		var obj = plus.storage.getItem(key);

		if(obj != null) {
			return JSON.parse(obj);
		} else {
			return null;
		}
	},
	removeLocalStorage: function(key) { /*清除本地数据*/
		plus.storage.removeItem(key);
	},
	
	
	//获取url中的参数
	getChineseParam: function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if(r != null) {
		//增加UTF-8解码处理
		return unescape(getCharFromUtf8(r[2]));
	} 
	else{return null; }//返回参数值
	},
	
	
	
	
	
	
	getParam: function(paramName) {
		var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if(r != null) {
			return unescape(r[2]);
		} else {
			return null;
		}
	},
	getParamFromStr: function(str, paramName) {
		var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)", "i");
		var r = str.match(reg);
		if(r != null) {
			return unescape(r[2]);
		} else {
			return null;
		}
	},
	modifyParam: function(str, paramName, newValue) {
		var value = this.getParamFromStr(str, paramName);
		var search = str;
		if(typeof value == "string") {
			var oldParam = paramName + "=" + value;
			var newParam = paramName + "=" + newValue;
			search = search.replace("&" + oldParam, "&" + newParam);
			search = search.replace("?" + oldParam, "?" + newParam);
			return search;
		} else {
			return null;
		}
	},
	formatUIDateField: function(str) {
		if(str.length == 10) {
			return str;
		} else {
			var dateArr = str.split("-");
			if(dateArr[1].length == 1) {
				dateArr[1] = "0" + dateArr[1];
			}
			if(dateArr[2].length == 1) {
				dateArr[2] = "0" + dateArr[2];
			}
			return str[0] + "-" + str[1] + "-" + str[2];
		}
	},
	substr: function(str, start, len) {
		var strlen = 0;
		var s = "";
		for(var i = start; i < str.length; i++) {
			if(str.charCodeAt(i) > 128) {
				strlen += 2;
			} else {
				strlen++;
			}
			s += str.charAt(i);
			if(strlen >= len) {
				return s;
			}
		}
		return s;
	}
};
//将URL中的UTF-8字符串转成中文字符串  
function getCharFromUtf8(str) {  
var cstr = "";  
var nOffset = 0;  
if (str == "")  
return "";  
    str = str.toLowerCase();  
    nOffset = str.indexOf("%e");  
if (nOffset == -1)  
return str;  
while (nOffset != -1) {  
        cstr += str.substr(0, nOffset);  
        str = str.substr(nOffset, str.length - nOffset);  
if (str == "" || str.length < 9)  
return cstr;  
        cstr += utf8ToChar(str.substr(0, 9));  
        str = str.substr(9, str.length - 9);  
        nOffset = str.indexOf("%e");  
    }  
return cstr + str;  
} 

//将编码转换成字符  
function utf8ToChar(str) {  
var iCode, iCode1, iCode2;  
    iCode = parseInt("0x" + str.substr(1, 2));  
    iCode1 = parseInt("0x" + str.substr(4, 2));  
    iCode2 = parseInt("0x" + str.substr(7, 2));  
return String.fromCharCode(((iCode & 0x0F) << 12) | ((iCode1 & 0x3F) << 6) | (iCode2 & 0x3F));  
}
function createQueryBeginDateStr(beginDate) {
	beginDate.setHours(0);
	beginDate.setMinutes(0);
	beginDate.setSeconds(0);
	beginDate.setMilliseconds(0);
	return beginDate.pattern("yyyy-MM-dd HH:mm:ss");
}

function createQueryEndDateStr(endDate) {
	endDate.setHours(23);
	endDate.setMinutes(59);
	endDate.setSeconds(59);
	endDate.setMilliseconds(0);
	return endDate.pattern("yyyy-MM-dd HH:mm:ss");
}