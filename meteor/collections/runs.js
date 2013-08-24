RunExtensions = function(run) {
    _.extend(this, run);
}

_.extend(RunExtensions.prototype, {
    route: function() {
        return Routes.findOne({"_id": this.routeId, userId: Meteor.userId()});
    },
    paceSec: function() {
        return this.durationSec/this.route().distance;
    },
    pace: function() {
        return Utils.secondsToHrMinSec(this.paceSec());
    },
    durationTagHTML: function() {
        tags = ['&nbsp;&nbsp;'];
        if (_.contains(this.tags, Tags.NewPR.id)) {
            tags.push(TagUtils.getHTML(Tags.NewPR));
        }
        if (_.contains(this.tags, Tags.AllTimeBest.id)) {
            tags.push(TagUtils.getHTML(Tags.AllTimeBest));
        }
        if (_.contains(this.tags, Tags.AllTimeWorst.id)) {
            tags.push(TagUtils.getHTML(Tags.AllTimeWorst));
        }
        if (_.contains(this.tags, Tags.NewRoute.id)) {
            tags.push(TagUtils.getHTML(Tags.NewRoute));
        }
        return tags.join('');
    },
    paceTagHTML: function() {
        tags = ['&nbsp;&nbsp;'];
        if (_.contains(this.tags, Tags.BestPace.id)) {
            tags.push(TagUtils.getHTML(Tags.BestPace));
        }
        return tags.join('');
    },
    niceDate: function() {
        return moment(this.date).format('MMMM Do YYYY');
    }
});

Runs = new Meteor.Collection('runs', {
    transform: function (run) { return new RunExtensions(run);}
});

Meteor.methods({
    logRun: function(run) {
        if (!Meteor.userId()) {
            throw new Meteor.Error(413, "You must be logged in.");
        }
        run.userId = Meteor.userId();
        Runs.insert(TagUtils.tagify(run), function(e, doc) {
            if (e) {
                alert('something horrible happened\n' + e.message);
            } else {
                $("#duration").val('');
                $("#datetimepicker input").val('');
            }
        });
    }
})