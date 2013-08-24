/**
 * GET /api/routemetadata?timestamp=125931216125
 * Returns all route metadata for the user which is newer than the timestamp submitted in the querystring
 *
 * timestamp:
 *   UTC seconds since
 */
Meteor.Router.add('/api/routemetadata', 'GET', function() {
    var res = {};
    var r = Routes.find({});
    if (r.count() > 0) {
        res.status = '200';
        res.body = this.request.query.timestamp;
    } else {
        res.status = '200';
        res.body = "";
    }
    return [res.status, res.body];
})

Meteor.Router.add('/api/routes/:id', 'GET', function(id) {
    return ['200', Routes.find({_id: id})];
})