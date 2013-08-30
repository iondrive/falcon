Template.routeMap.invokeAfterLoad = function() {
    var init = function() {
        map = new OpenLayers.Map('map');
        var osm = new OpenLayers.Layer.OSM('Simple OSM Map', null, {
            eventListeners: {
                tileloaded: function(evt) {
                    var ctx = evt.tile.getCanvasContext();
                    if (ctx) {
                        var imgd = ctx.getImageData(0, 0, evt.tile.size.w, evt.tile.size.h);
                        var pix = imgd.data;
                        for (var i = 0, n = pix.length; i < n; i += 4) {
                            pix[i] = pix[i + 1] = pix[i + 2] = (3 * pix[i] + 4 * pix[i + 1] + pix[i + 2]) / 8;
                        }
                        ctx.putImageData(imgd, 0, 0);
                        evt.tile.imgDiv.removeAttribute("crossorigin");
                        evt.tile.imgDiv.src = ctx.canvas.toDataURL();
                    }
                }
            }
        });
        var fromProjection = new OpenLayers.Projection("EPSG:4326");
        var toProjection = new OpenLayers.Projection("EPSG:900913");
        var startingPosition = new OpenLayers.LonLat(-74.00918139, 40.70371369).transform(fromProjection, toProjection);

        path = new OpenLayers.Layer.Vector("Line Layer");

        var style = {
            strokeColor: '#fdaa44',
            strokeOpacity: 0.5,
            strokeWidth: 5
        };

        if (Session.get("activeRoute")) {
            // draw the active route
        }

        if (Session.get("activeRun")) {
            // draw the active run
        }

        var run = Runs.findOne({});
        var activeRunLine = new OpenLayers.Geometry.LineString(_.map(run.Data, function(point) {
            return new OpenLayers.Geometry.Point(point.x, point.y).transform(fromProjection, toProjection);
        }));
        var activeRunFeature = new OpenLayers.Feature.Vector(activeRunLine, null, style);
        path.addFeatures([activeRunFeature]);

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

