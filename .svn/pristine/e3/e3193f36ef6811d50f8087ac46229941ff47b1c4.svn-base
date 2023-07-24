/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ux.Portlet = Ext.extend(Ext.Panel, {
    anchor : '100%',
    frame : true,
    collapsible : true,
    draggable : true,
    cls : 'x-portlet',
    onRender : function(ct, position) {
		Ext.ux.Portlet.superclass.onRender.call(this, ct, position);

		this.resizer = new Ext.Resizable(this.el, {
			animate : true,
			duration : .6,
			easing : 'backIn',
			handles : 's',
			minHeight : this.minHeight || 100,
			
			pinned : false
		});
		this.resizer.on("resize", this.onResizer, this);
	},

	onResizer : function(oResizable, iWidth, iHeight, e) {
		this.setHeight(iHeight);
		
		
	}
});

Ext.reg('portlet', Ext.ux.Portlet);
