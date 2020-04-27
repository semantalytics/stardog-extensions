[![Build Status](https://travis-ci.org/semantalytics/stardog-kibbles.svg?branch=master)](https://travis-ci.org/semantalytics/stardog-kibbles)

# Stardog Kibbles

A  [Stardog](http://stardog.com) plugin for running javascript

Namespace: http://semantalytics.com/2017/09/ns/stardog/kibble/javascript/

Suggested prefix: js: javascript:

Functions:

	exec	usage: javascript:exec("values[0].stringValue() + ' ' + values[1].stringValue()", "Hello", "world!")

	The first argument is the javascript to execute and any remaining arguments are passed to the script in a global array called "values". The return value is the final value executed by the script

