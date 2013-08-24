RouteStat = function(route) {
    _.extend(this, route);
}

// A route has been run many times
_.extend(RouteStat.prototype, {
    runs: function() {
        return Runs.find({routeId: this._id, userId: Meteor.userId()}).fetch();
    }
});

// A route can have average, best, and worst performance times
_.extend(RouteStat.prototype, {
    avgPace: function() {
        if (this.runs().length > 0) {
            var sum = _.reduce(this.runs(), function(memo, run) { return memo + run.durationSec; }, 0);
            return Utils.secondsToHrMinSec(sum/(this.runs().length*this.distance));
        }
        return 'Never Ran';
    },
    avgTime: function() {
        if (this.runs().length > 0) {
            var sum = _.reduce(this.runs(), function(memo, run) { return memo + run.durationSec; }, 0);
            return Utils.secondsToHrMinSec(sum/(this.runs().length));
        }
        return 'Never Ran';
    },
    bestTimeSec: function() {
        var r = _.min(this.runs(), function(run) { return run.durationSec; });
        return r && r.durationSec ? r.durationSec : null;
    },
    bestTime: function() {
        var s = this.bestTimeSec();
        return s ? Utils.secondsToHrMinSec(s) : 'Never Ran';
    },
    worstTimeSec: function() {
        var r = _.max(this.runs(), function(run) { return run.durationSec; });
        return r && r.durationSec ? r.durationSec : null;
    },
    worstTime: function() {
        var s = this.worstTimeSec();
        return s ? Utils.secondsToHrMinSec(s) : 'Never Ran';
    }
});

Routes = new Meteor.Collection('routes', {
    transform: function(route) { return new RouteStat(route); }
});

Meteor.methods({
    newRoute: function(route) {
        if (!Meteor.userId()) {
            throw new Meteor.Error(413, "You must be logged in.");
        }
        route.userId = Meteor.userId();
        Routes.insert(route);
    }
})

// FIXTURE DATA
if (Meteor.isServer && Routes.find().count() == 0) {
    var routes = [
        {name: "Four and a Bore", distance: 4.25, timestamp: 10, active: true},
        {name: "High Five", distance: 5, timestamp: 10, active: true},
        {name: "Two bad", distance: 2, timestamp: 11, active: false}
    ];

    _.each(routes, function(route) {
        Routes.insert(route);
    })
}
