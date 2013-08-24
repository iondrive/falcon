Template.running.helpers({
    routes: function() {return Routes.find({userId: Meteor.userId()});}
});

Template.running.events({
    "click .logRun": function(event) {
        if (!Meteor.userId()) {
            alert('log in');
            return;
        }
        if ($("duration").val() != '' && $("#datetimepicker input").val() != '') {
            var run = {
                routeId: $("#routepicker").val(),
                duration: $("#duration").val(),
                durationSec: Utils.durationStringToSeconds($("#duration").val()),
                date: new Date($("#datetimepicker input").val())
            };
            Meteor.call('logRun', run);
        } else {
            alert('fill in the values');
        }
    },
    "click .saveNewRoute": function(event) {
        if (!Meteor.userId()) {
            alert('log in');
            return;
        }
        if ($("#routename").val() != '' && $("#routedistance").val() != '') {
            var route = {
                name: $("#routename").val(),
                distance: $("#routedistance").val(),
                distanceMiles: parseInt($("#routedistance").val())
            };
            Meteor.call('newRoute', route);
            $("#createRouteModal").modal("hide");
        } else {
            alert('fill in the values');
        }
    }
})

Template.running.invokeAfterLoad =  function() {
    Meteor.defer(function() {
        $('#datetimepicker').datetimepicker({
            language: 'en',
            pick12HourFormat: true
        });
    })
};