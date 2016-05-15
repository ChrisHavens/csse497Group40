'use strict';

/* Filters */
angular.module('phonecatFilters', []).filter('checkmark', function(){
	return function(input){
	return input.hasOwnProperty('dateArchived') ? '\u2713' : '\u2718';
	};


}).filter('checklistmark', function(){
	return function(input){
	return input ? '\u2713' : '\u2718';
	};


})/*.filter('dereference',function() {
	return function(ID, set){
		var log = [];
		angular.forEach(set, function(value, key) {
			if value._id === ID
			this.push(value._source.name);
	}, log);
	};
})*/;