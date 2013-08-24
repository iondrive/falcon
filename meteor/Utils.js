Utils = {
    durationStringToSeconds: function(str) {
        // Takes HH:mm:ss or mm:ss and converts to number of seconds
        var sec_min_hrs = str.match(/[0-9]+/g).reverse();
        var seconds = parseInt(sec_min_hrs[0]);
        seconds = sec_min_hrs[1] ? seconds + parseInt(sec_min_hrs[1])*60 : seconds;
        seconds = sec_min_hrs[2] ? seconds + parseInt(sec_min_hrs[2])*60*60 : seconds;
        return seconds;
    },
		secondsToHrMinSec: function(sec) {
				var hrs = Math.floor(sec/3600);
				var min = Math.floor((sec - hrs*3600)/60);
				var s = Math.floor(sec - hrs*3600 - min*60);
				if (hrs > 0) {
                    min = min < 10 ? "0" + min.toString() : min;
                    s = s < 10 ? "0" + s.toString() : s;
					return hrs.toString() + ':' + min.toString() + ':' + s.toString();
				} else {
                    s = s < 10 ? "0" + s.toString() : s;
					return min.toString() + ':' + s.toString();
				}
		}
}
