/* @edit moejoe(lmh_user@hotmail.com) */function BSCommon(fm1) {
	this.inputParams = new Array();
	this.dataSetFilter = null;
	this.addParam = function(c, l, v, r) {
		var _name;
		if (typeof (l) != "string") {
			_name = l.name;
			v = this.getValue(l);
		} else
			_name = l;
		for ( var i = 0; i < this.inputParams.length; i++) {
			if (this.inputParams[i].ParamName == _name) {
				this.inputParams[i].ParamValues.push(v);
				return;
			}
			;
		}
		;
		this.inputParams.push( {
			ParamName : _name,
			DataType : c,
			ParamValues : ((r) ? v : [ v ])
		});
	};
	this.clearParam = function() {
		this.inputParams.length = 0;
	};
	this.base = FormExt;
	this.formToParams = function(p) {
		this.clearParam();
		if (p) {
			for ( var i = 0; i < p.length; i++)
				this.addParam(_gfa1_(this.item(p[i])), this.item(p[i]));
		} else {
			var e = this.form.elements;
			var len = e.length;
			var _type, _name2;
			for ( var i = 0; i < len; i++) {
				_type = e[i].type;
				_name2 = e[i].name;
				if (e[i + 1])
					while (e[i + 1].name == _name2)
						i++;
				if (!(_type == "button" || _type == "reset" || _type == "submit"))
					this.addParam(_gfa1_(this.item(_name2)), this.item(_name2));
			}
			;
		}
		;
	};
	this.objectToParams = function(h, p) {
		this.clearParam();
		if (p) {
			for ( var i = 0; i < p.length; i++)
				this.addParam(_gfb1_(h[p[i]]), p[i], h[p[i]]);
		} else {
			for ( var i in h)
				this.addParam(_gfb1_(h[i]), i, h[i]);
		}
		;
	};
	this.base(fm1);
	this.paramsToObject = function(pars) {
		var o = new Object();
		var k;
		for ( var i = 0; i < pars.length; i++) {
			if (k = pars[i].paramName)
				o[k] = (pars[i].paramValues).toString();
			else
				throw new Error(0, "Parameter name can't be null");
		}
		;
		return o;
	};
	this.fb1 = function(pResult) {
		var a1 = new Array();
		var j, o1;
		var l1 = (pResult.dataSet) ? pResult.dataSet.length : 0;
		var l2 = pResult.columnList.length;
		for ( var i = 0; i < l1; i++) {
			o1 = new Object();
			for (j = 0; j < l2; j++)
				o1[pResult.columnList[j]] = pResult.dataSet[i][j];
			a1[i] = o1;
		}
		;
		return a1;
	};
	this.setDataSetFilter = function(isPagi, pPage, pageSize, limitCount,
			orderArr, showFieldArr) {
		this.dataSetFilter = (typeof (isPagi) != "object") ? {
			pageFlag : isPagi,
			pageIndex : pPage,
			pageLen : pageSize,
			rowCount : limitCount,
			orderFields : orderArr,
			showFields : showFieldArr
		} : isPagi;
	};
	this.callRemoteFunctionQuery = function(queryName) {
		var recObj = callRemoteFunctionNoTrans(
				"com.ztesoft.bs.datacommon.client.IomCommonServiceClient",
				"queryService", queryName, this.inputParams, this.dataSetFilter);
		return (recObj) ? this.fb1(recObj) : null;
	};
	this.callRemoteFunctionOper = function(operName, returnParams) {
		var _pars = callRemoteFunction(
				"com.ztesoft.bs.datacommon.client.IomCommonServiceClient",
				"callService", operName, this.inputParams);
		if (returnParams || !_pars)
			return _pars;
		return this.paramsToObject(_pars);
	};
};
function _gfa1_(d) {
	if (d.className == (CHK_TYPE_CON.key1 + "_datetime"))
		return 3;
	if (("number" == d.checkType) || ("integer" == d.checktype)
			|| ("double" == d.checktype) || ("money" == d.checktype))
		return 1;
	return 2;
};
function _gfb1_(v) {
	if (v) {
		if (v.constructor.toString().indexOf("Date") > 0)
			return 3;
		if (v.constructor == Number)
			return 1;
	}
	return 2;
}