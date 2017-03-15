var exec = require('cordova/exec');

exports.openFile = function (arg0, success, error) {
    exec(success, error, "FileOpen", "openFile", [arg0]);
};