Accounts.onCreateUser(function(options, user) {
    // When we create a user, we need to associate any runs and routes that exist for that email with the new userId
    Routes.update({"usertoken": user.services.google.email}, {$set: {userId: user._id}}, {multi: true});
    Runs.update({"usertoken": user.services.google.email}, {$set: {userId: user._id}}, {multi: true});

    // Keep the default stuff
    user.profile = options.profile;
    return user;
});
