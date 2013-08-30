Template.routeMap.invokeAfterLoad = function() {
    var init = function() {
        map = new OpenLayers.Map('map');
        var osm = new OpenLayers.Layer.OSM();
        var fromProjection = new OpenLayers.Projection("EPSG:4326");
        var toProjection = new OpenLayers.Projection("EPSG:900913");
        var startingPosition = new OpenLayers.LonLat(-74.00918139, 40.70371369).transform(fromProjection, toProjection);

        var path = new OpenLayers.Layer.Vector("Line Layer");
        var rawPoints = [
                {"x": -74.00918139, "y": 40.70371369},
            {"x": -74.00928139, "y": 40.70381369},
            {"x": -74.00958139, "y": 40.70391369}
        ];

        var points = [];
        rawPoints.forEach(function(p) {
            points.push(new OpenLayers.Geometry.Point(p.x, p.y).transform(fromProjection, toProjection));
        });
        var line = new OpenLayers.Geometry.LineString(points);

        var style = {
            strokeColor: '#0000ff',
            strokeOpacity: 0.5,
            strokeWidth: 5
        };

        var lineFeature = new OpenLayers.Feature.Vector(line, null, style);
        path.addFeatures([lineFeature]);



        map.addLayer(osm);
        map.addLayer(path);
        map.setCenter(startingPosition, 15);

    };

    if (typeof(map) == 'undefined') {
        if (typeof(OpenLayers) == 'undefined') {
            $.getScript('/OpenLayers.js', init);
        } else {
            init();
        }
    }

};

