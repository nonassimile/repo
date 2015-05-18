var comp;
var map;
var coords;

Ext.define('GM.view.MapViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.map',

    onMapBoxReady: function(component, width, height, eOpts) {
		if( navigator.geolocation )
        {

              // Call getCurrentPosition with success and failure callbacks
			  comp = component;
              navigator.geolocation.getCurrentPosition( success, fail );
        }

    }

});

function success(position)
{
	
		// Define the coordinates as a Google Maps LatLng Object
           coords = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

           // Prepare the map options
           var mapOptions =
           {
                      zoom: 14,
                      center: coords,
                      mapTypeControl: false,
                      navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
                      mapTypeId: google.maps.MapTypeId.ROADMAP
            };
			
        this.map = new google.maps.Map(comp.getEl().dom, mapOptions);
		
}
	 
function fail(){}

Ext.define('GM.view.Map', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.map',

    requires: [
        'GM.view.MapViewController'
    ],

    controller: 'map',
    layout: 'fit',
    title: 'Google Map',

    listeners: {
        boxready: 'onMapBoxReady'
    }

});

Ext.define('GM.view.Main', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.main',

    requires: [
        'Ext.panel.Panel'
    ],

    layout: 'border',

    items: [
        {
            xtype: 'map',
            region: 'center'
        },
        {
            xtype: 'panel',
            region: 'north',
            height: 150,
            animCollapse: true,
            collapsible: true,
            title: 'Norte',
            titleCollapse: true
        },
        {
            xtype: 'panel',
            region: 'west',
            animCollapse: true,
            collapsible: true,
            width: 200,
            title: 'Oeste',
            titleCollapse: true
        },
        {
            xtype: 'panel',
            region: 'east',
            animCollapse: true,
            collapsible: true,
            width: 200,
            title: 'Este',
            titleCollapse: true
        }
    ]

});

Ext.application({
    name : 'GM',

    launch : function() {
        
        new GM.view.Main({
            renderTo : document.body
        });
    }
});