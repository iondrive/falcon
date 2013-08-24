Template.runGrid.helpers({
    runs: function() {return Runs.find({userId: Meteor.userId()}, {sort: {date: -1}, limit: 50});}
});