/**
 * GET /api/routes?timestamp=125931216125
 * Returns all route data for the user which is newer than the timestamp submitted in the querystring
 *
 * timestamp: seconds since Jan 1 1970 GMT
 *
 * returns: {id: FLKG09aghnDF4hs, name: 'High Five', distance: 11.5 etc...}
 */
Meteor.Router.add('/api/routes', 'GET', function() {
    var res = {};
    var timestamp = 0;
    var userId = getUserIdFromHeaders(this.request.headers);

    if (this.request.query) {
        timestamp = this.request.query.timestamp | 0;
    }
    if (userId) {
        var routes = [];
        var active = Routes.find({timestamp: {$gt: timestamp}, active: true, userId: userId});
        active.forEach(function(doc) {
            routes.push({
                Id: doc._id
                , Name: doc.name
                , Distance: doc.distance
                , SparseControlPoints: doc.sparseControlPoints
                , MapPoints: doc.mapPoints
                , Active: true
                , Timestamp: doc.timestamp
            })
        });

        var deleted = Routes.find({timestamp: {$gt: timestamp}, active: false, userId: userId});
        deleted.forEach(function(doc) {
            routes.push({
                Id: doc._id
                , Active: false
                , Timestamp: doc.timestamp
            })
        });

        return ['200', JSON.stringify(routes)];
    } else {
        return ['500', 'invalid request'];
    }
})

/**
 * GET /api/routes/id
 * Gets the route data points for an active route
 *
 * returns {id: id, data: [array of lon/lat points]}
 */
Meteor.Router.add('/api/routes/:id', 'GET', function(id) {
    var res = {};
    var userId = getUserIdFromHeaders(this.request.headers);

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
    var userId = getUserIdFromHeaders(this.request.headers);

    if (doc && userId) {
        doc.timestamp = Date.now();
        doc.userId = userId;
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
    var res = {};
    var doc = this.request.body;
    var userId = getUserIdFromHeaders(this.request.headers);

    if (doc && userId) {
        doc.timestamp = Date.now();
        doc.userId = userId;
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

/**
 * GET /api/runs.js
 */
Meteor.Router.add('/api/runs.js', 'GET', function() {
    return JSON.stringify(Runs.find().map(function(doc) {
        return {data: doc.Data};
    }));
})

/**
 * Returns the userId by parsing the information in the request headers
 * Right now the headers contain a usertoken value which is just the email address,
 * but in the future the usertoken will be a google signed auth token.
 * @param headers request headers
 * @returns {*}
 */
var getUserIdFromHeaders = function(headers) {
    var user;
    var userId;
    if (headers.usertoken) {
        userId = headers.usertoken;
        user = Meteor.users.findOne({"services.google.email": headers.usertoken});
    }
    if (user) {
        userId = user._id;
    }
    return userId;
};