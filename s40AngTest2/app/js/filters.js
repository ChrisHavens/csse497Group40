'use strict';

/* Filters */
angular.module('phonecatFilters', []).filter('checkmark', function(){
	return function(input){
	return input.hasOwnProperty('dateArchived') ? '\u2713' : '\u2718';
	};


});