TagUtils = {
    // We tagify a run BEFORE inserting it to the database
    tagify: function(run) {
        var tags = [];
        if (Runs.find({routeId: run.routeId}).count() == 0) {
            tags.push(Tags.NewRoute.id);
        } else {
            if (Routes.findOne({_id: run.routeId}).bestTimeSec() > run.durationSec) {
                tags.push(Tags.NewPR.id);
                tags.push(Tags.AllTimeBest.id);
                this.clearTagsForRoute(run.routeId, Tags.AllTimeBest.id)
            } else if (Routes.findOne({_id: run.routeId}).worstTimeSec() < run.durationSec) {
                tags.push(Tags.AllTimeWorst.id);
                this.clearTagsForRoute(run.routeId, Tags.AllTimeWorst.id);
            }
        }
        return _.extend(run, {tags: tags});
    },
    clearTagsForRoute: function(routeId, tagId) {
        Meteor.call('clearTagsForRoute', routeId, tagId);
    },
    getHTML: function(tag) {
        return '<span class="badge badge-' + tag.level + '">' + tag.text + '</span>';
    }
};

if (Meteor.isServer) {
    Meteor.methods({
        clearTagsForRoute: function(routeId, tagId) {
            Runs.update({routeId: routeId, tags: tagId}, {$pull: { tags: tagId} });
        }
    })
}