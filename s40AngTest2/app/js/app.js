'use strict';

/* App Module */

var phonecatApp= angular.module('phonecatApp', [
	'ngRoute', 'phonecatControllers', 'phonecatFilters']



);

phonecatApp.config(['$routeProvider',
	function($routeProvider){
		$routeProvider.when('/projects', 
		{
			templateUrl: 'partials/project-list.html',
			controller: 'ProjectListCtrl'
		}).when('/projects/:projectId', {
			templateUrl: 'partials/project-detail.html',
			controller: 'ProjectDetailCtrl'
		}).when('/home', {
			templateUrl: 'partials/login.html',
			controller: 'LoginCtrl'
		}).when('/groups',
		{
			templateUrl: 'partials/group-list.html',
			controller: 'GroupListCtrl'
		}).when('/groups/:groupId',
		{
			templateUrl: 'partials/group-detail.html',
			controller: 'GroupDetailCtrl'
		}).when('/persons',
		{
			templateUrl: 'partials/person-list.html',
			controller: 'GroupDetailCtrl'
		}).when('/persons/:personId',
		{
			templateUrl: 'partials/person-detail.html',
			controller: 'GroupDetailCtrl'
		})
		
		
		
		
		.otherwise({
			redirectTo: '/home'
		});
		}]);
		
	