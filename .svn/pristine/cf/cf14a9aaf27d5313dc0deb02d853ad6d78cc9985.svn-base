/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */

Ext.onReady(function(){
	alert(1);

    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
            xtype:'portal',
            region:'center',
            margins:'35 5 5 0',
            items:[{
                columnWidth:.50,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Grid in a Portlet',
                    layout:'fit',
                    tools: tools,
                    html: "aa"
                },{
                    title: 'Another Panel 1',
                    tools: tools,
                    html: "aa"
                }]
            },{
                columnWidth:.50,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Panel 2',
                    tools: tools,
                    html: "bbb"
                },{
                    title: 'Another Panel 2',
                    tools: tools,
                    html: "aa"
                }]
            }]
            
            
        }]
    });
});