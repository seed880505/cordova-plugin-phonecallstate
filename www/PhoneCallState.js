var exec = require('cordova/exec');

var PhoneCallState = {
	watchState: function(success, error, args) {
		error = error || null;
        exec(success, error, "PhoneCallState", "watchState", []);
    }
};

module.exports = PhoneCallState;
