function ContextMenu(pContName, pContWidth) {
	this.contName = pContName;
	this.v = new Array();
	this.x = 2;
	this.y = pContWidth || 150;
	this.z = document.createElement("table");
	this.z.id = this.contName;
	this.itemState = new Object();
	with (this.z.style) {
		display = "none";
		position = "absolute";
		posWidth = parseInt(this.y, 10) + 2;
		posHeight = 0;
		zIndex = 1000;
	}
	;
	(this.z.insertRow()).insertCell();
	document.body.appendChild(this.z);
	this.z.onblur = function() {
		this.style.display = "none";
	};
	this.clearItems = function() {
		this.v.length = 0;
	};
	this.addItem = function(pName, pEvent, pImg, pTitle) {
		this.v[this.v.length] = pName;
		this.v[this.v.length] = pEvent;
		this.v[this.v.length] = pImg;
		this.v[this.v.length] = pTitle;
	};
	this.addArrayItem = function(pArr) {
		for ( var i in pArr) {
			this.v[this.v.length] = pArr[i];
		}
		;
	};
	this.hiddenItem = function(pTitle) {
		if (pTitle)
			this.itemState[pTitle] = "hidden";
	};
	this.showItem = function(pTitle) {
		if (pTitle)
			this.itemState[pTitle] = "";
	};
	this.create = function(pMenuArr) {
		var m = pMenuArr || this.v;
		this.x = 2;
		var a = new Array(
				'<table class="menu_main" width="' + this.y + '" cellspacing="0" cellpadding="0"><thead><tr><td colspan="2"></td></tr></thead><tbody>');
		for ( var i = 0; i < m.length; i += 4) {
			if (m[i + 1]) {
				this.x += 22;
				if (this.itemState[m[i + 3]] == "hidden") {
					a
							.push('<tr class="menu_Item" style="display:none" onmousedown="'
									+ m[i + 1]
									+ ';this.className=\'menu_Item\'" onmouseover="this.className=\'menu_Itemover\'" onmouseout="this.className=\'menu_Item\'"><td class="menu_Image"><img src="'
									+ ((m[i + 2]) ? m[i + 2] : gWebAbsPath
											+ "/images/defMeunIcon.gif")
									+ '"></td><td class="menu_text"'
									+ ((m[i + 3]) ? ' title=' : '')
									+ '>'
									+ m[i] + '</td></tr>');
				} else {
					a
							.push('<tr class="menu_Item" onmousedown="'
									+ m[i + 1]
									+ ';this.className=\'menu_Item\'" onmouseover="this.className=\'menu_Itemover\'" onmouseout="this.className=\'menu_Item\'"><td class="menu_Image"><img src="'
									+ ((m[i + 2]) ? m[i + 2] : gWebAbsPath
											+ "/images/defMeunIcon.gif")
									+ '"></td><td class="menu_text"'
									+ ((m[i + 3]) ? ' title=' : '') + '>'
									+ m[i] + '</td></tr>');
				}
			} else {
				this.x += 3;
				a
						.push('<tr class="menu_Seperator"><td class="menu_Image"></td><td class="menu_text"><img height="1"></td></tr>');
			}
			;
		}
		;
		a.push('</tbody><tfoot><tr><td colspan="2"></td></tr></tfoot></table>');
		this.z.rows(0).cells(0).innerHTML = a.join("");
		a = null;
	};
	this.popMenu = function(l, a1, b1) {
		if (this.x > 5) {
			var m = parseInt(a1 || window.event.x);
			var n = parseInt(b1 || window.event.y);
			var h = parseInt((l == null) ? document.body.clientHeight
					: (l.offsetTop + l.offsetHeight));
			with (this.z.style) {
				display = "";
				posLeft = ((m + this.y > document.body.clientWidth) ? ((m > this.y) ? (m - this.y)
						: 3)
						: m);
				posTop = ((n + this.x > h) ? (n + document.body.scrollTop
						- this.x - 3) : (n + document.body.scrollTop));
				posHeight = this.x + 5;
			}
			;
			this.z.focus();
			window.event.cancelBubble = true;
			window.event.returnValue = false;
		}
		;
	};
	this.slice = function(pSplitStr) {
		var w = new Array();
		var a = pSplitStr.split(",");
		var d = 0, j = 0;
		for ( var i = 0; i < a.length; i++) {
			d = parseInt(a[i]);
			for (j = 0; j < 4; j++)
				w[i * 4 + j] = this.v[d * 4 + j];
		}
		;
		this.create(w);
	};
	this.hide = function() {
		this.z.style.display = "none";
	};
};