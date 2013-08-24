if (Meteor.isServer) {
  Meteor.startup(function () {
    // code to run on server at startup
  });
}

if (Meteor.isClient) {
    console.log('restart at ' + Date());

    Meteor.Router.add({
        "/running": "running",
        "/location": "location"
    //    "/*": "not_found"
    })
}