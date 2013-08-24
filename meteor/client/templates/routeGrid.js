Template.routeGrid.helpers({
    routes: function() {return Routes.find({userId: Meteor.userId()});}
});