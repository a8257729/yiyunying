/* @edit moejoe(lmh_user@hotmail.com) */function FormExt(formName) {
	this.form = document.all(formName);
	function fa(a, m, b) {
		while (a && a.tagName != "TD")
			a = a.parentElement;
		return ((a && a.previousSibling) ? _remXing_(a.previousSibling.innerText)
				: "")
				+ m + (b || "");
	}
	;
	this.validator = function() {
		var z = this.form.elements;
		var len = z.length;
		var c;
		for ( var i = 0; i < len; i++) {
			if ("hidden" == z[i].type)
				continue;
			if (z[i].minLength && this.fc(z[i], null))
				return true;
			if (z[i].maxLength && this.fc(z[i], "a"))
				return true;
			if (c = z[i].checkType) {
				switch (c) {
				case "empty":
					if (this.fb(z[i], CHK_TYPE_CON.pb))
						return true;
					break;
				case "number":
					if (this.fb(z[i], CHK_TYPE_CON.pc))
						return true;
					break;
				case "checkbox":
					var b = z[i].name;
					while (z[i + 1] && z[i + 1].name == b)
						i++;
					if (this.fd(this.item(b)))
						return true;
					break;
				case "select":
					if (this.fe(z[i], z[i].checkValue))
						return true;
					break;
				default:
					if (this.fb(z[i]))
						return true;
					break;
				}
				;
			}
			;
		}
		;
		return false;
	};
	this.objectToString = function(h) {
		var tmpStr = "";
		for ( var i in h)
			tmpStr += (i + " = " + h[i] + "\n");
		return tmpStr;
	};
	this.loadEditData = function(p) {
		if (p)
			this.objectToForm(p);
	};
	this.submit = function(p) {
		if (this.validator())
			return null;
		try {
			var formObject = this.formToObject();
			if (eval(p.condition)) {
				var b = eval(p.editMethod);
				if (b)
					formObject = b;
				alert(CHK_TYPE_CON.pl);
			} else {
				formObject = eval(p.addMethod);
				alert(CHK_TYPE_CON.pk);
			}
			;
			return formObject;
		} catch (e) {
			throw e;
			return null;
		}
		;
	};
	this.reset = function() {
		this.form.reset();
	};
	this.formToObject = function(p) {
		var h = new Object();
		if (p) {
			for ( var i = 0; i < p.length; i++)
				h[p[i]] = this.getValue(this.item(p[i]));
		} else {
			var z = this.form.elements;
			var c, b;
			for ( var i = 0; i < z.length; i++) {
				c = z[i].type;
				b = z[i].name;
				if (!b || c == "button" || c == "reset" || c == "submit")
					continue;
				while (z[i + 1] && z[i + 1].name == b)
					i++;
				h[b] = this.getValue(this.item(b));
			}
			;
		}
		;
		return h;
	};
	this.objectToForm = function(h, p, v) {
		if (!v)
			this.reset();
		if (p) {
			for ( var i = 0; i < p.length; i++)
				this.setValue(this.item(p[i]), h[p[i]]);
		} else {
			for ( var i in h)
				this.setValue(this.item(i), h[i]);
		}
		;
	};
	this.objectToHtml = function(h, p, m) {
		var d = (m) ? document.all(m).all : document.all;
		if (p)
			for ( var i = 0; i < p.length; i++)
				SetInnerHTML(d(p[i]), h[p[i]]);
		else
			for ( var i in h)
				SetInnerHTML(d(i), h[i]);
	};
	this.clearHtmlData = function(p, b) {
		var n = (b) ? document.all(b).all : this.form.all;
		if (p)
			for ( var i = 0; i < p.length; i++)
				SetInnerHTML(n(p[i]), "&nbsp;");
		else
			for ( var i = 0; i < n.length; i++) {
				if (n[i].id && n[i].tagName == "TD"
						&& (n[i].id).indexOf("_id") < 0)
					SetInnerHTML(n[i], "&nbsp;");
			}
		;
	};
	this.item = function(l) {
		return this.form.elements(l);
	};
	this.selOptionDelAll = function(d) {
		d.options.length = 0;
	};
	this.selOptionDel = function(d) {
		if (d.selectedIndex < 0)
			alert(CHK_TYPE_CON.ph);
		else
			d.remove(d.selectedIndex);
	};
	this.selOptionAdd = function(d, pText, pVal) {
		var len = d.length;
		for ( var i = 0; i < len; i++) {
			if (d[i].value == pVal) {
				alert(CHK_TYPE_CON.pi);
				return false;
			}
			;
		}
		;
		d.add(new Option(pText, pVal));
	};
	this.getSelText = function(d) {
		var _i = d.selectedIndex;
		return ((_i < 0) ? null : d[_i].text);
	};
	this.selOptionAddAll = function(d, n, l, v, c, p) {
		if (n) {
			if (p)
				this.selOptionDelAll(d);
			if (c)
				d.add(new Option("", ""));
			for ( var i = 0; i < n.length; i++)
				d.add(new Option(n[i][l], n[i][v]));
		}
		;
	};
	this.selOptionMove = function(r, d) {
		var _i = r.selectedIndex;
		if (_i < 0) {
			alert(CHK_TYPE_CON.pj);
			return false;
		} else
			this.selOptionAdd(d, r[_i].text, r[_i].value);
	};
	this.selOptionMultiMove = function(r, d) {
		var _i = r.selectedIndex;
		if (_i < 0) {
			alert(CHK_TYPE_CON.pj);
			return false;
		} else {
			var x = r.length;
			var s = d.length;
			var b = true;
			for ( var j = 0; j < x; j++) {
				if (r[j].selected) {
					for ( var i = 0; i < s; i++) {
						if (d[i].value == r[j].value) {
							b = false;
							break;
						}
						;
					}
					;
					if (b)
						d.add(new Option(r[j].text, r[j].value));
					else
						b = true;
				}
				;
			}
			;
		}
		;
	};
	this.getValue = function(a) {
		try {
			var len = a.length;
		} catch (e) {
			return null;
		}
		;
		if (typeof (len) != "undefined") {
			var n = [];
			switch (a.type) {
			case CHK_TYPE_CON.key2 + "-one":
				return (a.emptyNull) ? BlankRepl(a.value) : a.value;
			case CHK_TYPE_CON.key2 + "-multiple":
				for ( var i = 0; i < len; i++)
					if (a[i].selected)
						n.push(a[i].value);
				return n;
				break;
			default:
				if (a[0].type == "radio") {
					for ( var i = 0; i < len; i++)
						if (a[i].checked)
							return a[i].value;
				} else {
					for ( var i = 0; i < len; i++)
						if (a[i].checked)
							n.push(a[i].value);
					return n;
				}
				;
				break;
			}
			;
			return null;
		} else {
			switch (a.className) {
			case CHK_TYPE_CON.key1 + "_datetime":
				return ((a.useTime == "true" && a.useDate != "true") ? a.value
						: StringToDate(a.value));
			case CHK_TYPE_CON.key1 + "_array":
				return ((a.value != "") ? a.value.split(",")
						: ((a.emptyNull) ? null : new Array()));
			case "checkbox":
				return ((a.checked) ? a.value : null);
			default:
				return ((a.emptyNull) ? BlankRepl(Trim(a.value))
						: Trim(a.value));
			}
			;
		}
		;
	};
	this.setValue = function(a, v) {
		if (v == null)
			return;
		try {
			var len2 = a.length;
		} catch (e) {
			return;
		}
		;
		if (len2 && v instanceof Array) {
			var len1 = v.length;
			if (a.type == CHK_TYPE_CON.key2 + "-multiple") {
				for ( var j = 0; j < len1; j++)
					for ( var i = 0; i < len2; i++)
						if (v[j] == a[i].value) {
							a[i].selected = true;
							continue;
						}
				;
			} else {
				for ( var j = 0; j < len1; j++)
					for ( var i = 0; i < len2; i++)
						if (v[j] == a[i].value) {
							a[i].checked = true;
							continue;
						}
				;
			}
			;
		} else if (v instanceof Date) {
			a.value = DateToString(v, (a.useTime == "true"));
		} else if (!a.type) {
			for ( var i = 0; i < len2; i++)
				if (v == a[i].value) {
					a[i].checked = true;
					return;
				}
			;
		} else
			a.value = v;
	};
	this.setDisabled = function(a, v) {
		if (a.className != CHK_TYPE_CON.key1 + "_datetime")
			a.disabled = v;
		else
			a.nextSibling.disabled = v;
	};
	this.fc = function(a, b) {
		if (a.emptyed == "true" && a.value == "")
			return false;
		var v = (b) ? ((a.value).length > parseInt(a.maxLength, 10))
				: ((a.value).length < parseInt(a.minLength, 10));
		if (v) {
			alert(a.checkMsg
					|| eval("fa(a, "
							+ ((b) ? "CHK_TYPE_CON.pd,a.maxLength"
									: "CHK_TYPE_CON.pe,a.minLength") + ")"));
			if (!a.readOnly && a.focused != "false")
				a.focus();
			return true;
		} else
			return false;
	};
	this.fd = function(t) {
		var len = parseInt(t.length, 10);
		if (isNaN(len)) {
			if (!t.checked) {
				alert(t.checkMsg || fa(t[0], CHK_TYPE_CON.pg));
				if (t.focused != "false")
					t.focus();
				return true;
			} else
				return false;
		} else {
			for ( var i = 0; i < len; i++) {
				if (t[i].checked)
					return false;
			}
			;
			alert(t[0].checkMsg || fa(t[0], CHK_TYPE_CON.pg));
			if (t[0].focused != "false")
				t[0].focus();
			return true;
		}
		;
	};
	this.fe = function(a, v) {
		if (a.selectedIndex < 0 || a.value == NullRepl(v, "")) {
			alert(a.checkMsg || fa(a, CHK_TYPE_CON.pg));
			if (a.focused != "false")
				a.focus();
			return true;
		} else
			return false;
	};
	this.fb = function(a, g) {
		if (a.emptyed == "true" && a.value == "")
			return false;
		if (CHK_TYPE_CON[a.checkType]
				&& !(CHK_TYPE_CON[a.checkType].test(a.value))) {
			alert(a.checkMsg || fa(a, g || CHK_TYPE_CON.pf));
			if (!a.readOnly && a.focused != "false")
				a.focus();
			return true;
		} else
			return false;
	};
};
function _remXing_(v) {
	return ((v.indexOf("*") > -1) ? v.replace(/\*/g, "") : v);
};
function SetInnerHTML(a, v) {
	if (a)
		a.innerHTML = ((v != null) ? ((v.constructor.toString().indexOf("Date") > 0) ? DateToString(
				v, (a.useTime == "true"))
				: v)
				: "");
}