/**
 * GET /api/routemetadata?timestamp=125931216125
 * Returns all route metadata for the user which is newer than the timestamp submitted in the querystring
 *
 * timestamp:
 *   UTC seconds since
 *
 * returns: {id: FLKG09aghnDF4hs, name: 'High Five', distance: 11.5}
 */
Meteor.Router.add('/api/routemetadata', 'GET', function() {
    var res = {};
    res.body = {};
    var timestamp = 0;
    if (this.request.query) {
        timestamp = this.request.query.timestamp | 0;
    }
    var r = Routes.find({timestamp: {$gt: timestamp}, active: true});
    var deleted = Routes.find({timestamp: {$gt: timestamp}, active: false});
    if (r.count() > 0 || deleted.count() > 0) {
        res.status = '200';
        res.body.active = r.map(function(doc) {
            return {id: doc._id, name: doc.name, distance: doc.distance};
        });
        res.body.deleted = deleted.map(function(doc) {
            return {id: doc._id};
        })
    } else {
        // Nothing has changed, so just send an empty array
        res.status = '200';
        res.body = "[]";
    }
    return [res.status, JSON.stringify(res.body)];
})

/**
 * GET /api/routes/id
 * Gets the route data points for an active route
 *
 * returns {id: id, data: [array of lon/lat points]}
 */
Meteor.Router.add('/api/routes/:id', 'GET', function(id) {
    var res = {};
    var r = Routes.findOne({_id: id, active: true});
    if (r) {
        res.status = '200';
        res.body = JSON.stringify({id: r._id, data: r.data});
    } else {
        res.status = '404';
        res.body = 'Not Found';
    }
    return [res.status, res.body]
})

/**
 * POST /api/routes
 * {name: 'new name', data: []}
 *
 * Adds a new route.  Always returns success.
 */
Meteor.Router.add('/api/routes', 'POST', function(doc) {
    var res = {};
    var doc = this.request.body;

    if (doc) {
        doc.timestamp = Date.now();
        Routes.insert(doc, function(err, res) {
            if (err) {
                console.log(err.message);
            }
        });
        return ['200', ''];
    }
    else {
        return ['500', 'invalid post document'];
    }
});

/**
 * POST /api/runs
 * {name: 'new name', data: []}
 *
 * Adds a new route.  Always returns success.
 */
Meteor.Router.add('/api/runs', 'POST', function(doc) {
    debugger;
    var res = {};
    var doc = this.request.body;

    if (doc) {
        doc.timestamp = Date.now();
            Runs.insert(doc, function(err, res) {
                if (err) {
                    console.log(err.message);
                }
            });
        return ['200', ''];
    }
    else {
        return ['500', 'invalid post document'];
    }
});