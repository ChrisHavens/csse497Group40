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


}).filter('checkismarked', function(){
	return function(input){
	return input.hasOwnProperty('dateArchived') ? true : false;
	};


})