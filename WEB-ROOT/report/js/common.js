/*
*  方法:Array.remove(dx) 通过遍历,重构数组
*  功能:删除数组元素.
*  参数:dx删除元素的下标.
*/
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

var dateArr = [];

function getDateArray() {
	if(dateArr.length > 0) {
		return dateArr;
	} else {
		return createDates();
	}
}

function getCurrPickerObj() {
	return dateArr[dateArr.length - 2];;
}

function createDates() {
	console.log("生成时间开始");
	//1：上半月，2：下半月
	//设置日期
	var myDate = new Date(); //获取今天日期
	var currDate = new Date(); //获取今天日期
	currDate.setMonth(currDate.getMonth() + 1);

	//设置日期
	myDate.setFullYear(myDate.getFullYear() - 2);
	myDate.setMonth(0);

	var addStep = 1;
	while(myDate <= currDate) {
		var year = myDate.getFullYear();
		var month = myDate.getMonth() + 1;
		var day = myDate.getDate();
		month = month > 9 ? "" + month : "0" + month;

		var dateTemp1 = year + "年" + month + "月上半月";
		dateArr.push({
			value: dateTemp1,
			text: dateTemp1,
			y: year,
			m: month,
			d: "1"
		});
		var dateTemp2 = year + "年" + month + "月下半月";
		dateArr.push({
			value: dateTemp2,
			text: dateTemp2,
			y: year,
			m: month,
			d: "2"
		});

		myDate.setMonth(myDate.getMonth() + addStep);

	}

	//dateArr.push(currDateObj);
	console.log("生成时间");
	//	setSelectedIndex = dateArr.length - 2;
	return dateArr;
}

var cityArr = ["全省",
	"版纳",
	"保山",
	"楚雄",
	"大理",
	"德宏",
	"迪庆",
	"红河",
	"昆明",
	"丽江",
	"临沧",
	"怒江",
	"普洱",
	"曲靖",
	"文山",
	"玉溪",
	"昭通"
];

var countyArr = [
	"安宁市",
	"呈贡县",
	"东川区",
	"富民县",
	"官渡区",
	"晋宁县",
	"禄劝县",
	"盘龙区",
	"石林县",
	"嵩明县",
	"五华区",
	"西山区",
	"寻甸县",
	"宜良县"
];

function randomBy(under, over) {
	switch(arguments.length) {
		case 1:
			return parseInt(Math.random() * under + 1);
		case 2:
			return parseInt(Math.random() * (over - under + 1) + under);
		default:
			return 0;
	}
}

function randomCityArray(col) {
	var row = cityArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		var itemArr = [];
		itemArr[0] = cityArr[i];
		for(var j = 1; j < col; j++) {
			itemArr[j] = randomBy(100, 1000);
		}
		retArr.push(itemArr);
	}
	return retArr;
}

function randomCountyArray(col) {
	var row = countyArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		var itemArr = [];
		itemArr[0] = countyArr[i];
		for(var j = 1; j < col; j++) {
			itemArr[j] = randomBy(100, 1000);
		}
		retArr.push(itemArr);
	}
	return retArr;
}

function randomCityArrayOne() {
	var row = cityArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		if(i==0){
			retArr.push(950);
		}else{
			retArr.push(randomBy(1, 500));
		}

	}
	return retArr;
}

function randomCountyArrayOne() {
	var row = countyArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {

		if(i==0){
			retArr.push(950);
		}else{
			retArr.push(randomBy(1, 700));
		}
	}
	return retArr;
}

function randomCityArrayPercent(col) {
	var row = cityArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		var itemArr = [];
		itemArr[0] = cityArr[i];
		for(var j = 1; j < col; j++) {
			itemArr[j] = randomBy(10, 100)+"%";
		}
		retArr.push(itemArr);
	}
	return retArr;
}

function randomCountyArrayPercent(col) {
	var row = countyArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		var itemArr = [];
		itemArr[0] = countyArr[i];
		for(var j = 1; j < col; j++) {
			itemArr[j] = randomBy(10, 100)+"%";
		}
		retArr.push(itemArr);
	}
	return retArr;
}

function randomCityArrayOnePercent() {
	var row = cityArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {

		if(i==0){
			retArr.push(95);
		}else{
			retArr.push(randomBy(1, 60));
		}
	}
	return retArr;
}

function randomCountyArrayOnePercent() {
	var row = countyArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {

		if(i==0){
			retArr.push(95);
		}else{
			retArr.push(randomBy(1, 60));
		}
	}
	return retArr;
}

function randomDateArray(col) {
	row = 15;
	var retArr = [];
	for(var i = 1; i <= row; i++) {
		var itemArr = [];
		itemArr[0] = "05-" + i;
		for(var j = 1; j <= col; j++) {
			itemArr[j] = randomBy(100, 1000);
		}
		retArr.push(itemArr);
	}
	return retArr;
}

function randomCityArrayPie() {
	var row = cityArr.length;
	var retArr = [];
	for(var i = 0; i < row; i++) {
		var obj = {};
		obj.name = cityArr[i];
		obj.value = randomBy(100, 1000);

		retArr.push(obj);
	}
	return retArr;
}

//打开页面
function hsOpen(url, argv) {
	if(argv == "NOBACK"){
		window.location.replace(encodeURI("../" + url));
	}else{
		window.location.href = encodeURI("../" + url);
	}
}