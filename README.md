# Falcon
Run your routes, skip the profile pic.  Falcon is a better way to run that rewards routine.

## How it works
You turn on the android app and go for a run.  When you're finished, the app will determine if you've run this route before, and if so it will immediately log your run.  If not, the app will prompt you to name this new route.

## Meteor App
The meteor app provides the full experience with all the stats on your activity you could ever want.

## Android App
The goal of the android app is minimum battery usage, so it communicates with the server as little as possible.  It doesn't even show a map by default.

Routes are stored in a sqlite db and updates are requested at application start.  
