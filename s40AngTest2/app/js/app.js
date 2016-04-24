'use strict';

/* App Module */

var phonecatApp= angular.module('phonecatApp', [
	'ngRoute', 'phonecatControllers', 'phonecatFilters']



);

phonecatApp.config(['$routeProvider',
	function($routeProvider){
		$routeProvider.when('/projects', 
		{
			title: 'Projects',
			templateUrl: 'partials/project-list.html',
			controller: 'ProjectListCtrl'
		}).when('/projects/:projectId', {
			title: 'Project :projectId',
			templateUrl: 'partials/project-detail.html',
			controller: 'ProjectDetailCtrl'
		}).when('/login', {
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
			controller: 'PersonListCtrl'
		}).when('/persons/:personId',
		{
			templateUrl: 'partials/person-detail.html',
			controller: 'PersonDetailCtrl'
		}).when('/locations',
		{
			templateUrl: 'partials/location-list.html',
			controller: 'LocationListCtrl'
		}).when('/locations/:locationId',
		{
			templateUrl: 'partials/location-detail.html',
			controller: 'LocationDetailCtrl'
		}).when('/home', {
			title: 'Home',
			templateUrl: 'partials/home.html',
			controller: 'HomeCtrl'
		}).when('/shipments',
		{
			templateUrl: 'partials/shipment-list.html',
			controller: 'ShipmentListCtrl'
		}).when('/shipments/:shipmentId',
		{
			templateUrl: 'partials/shipment-detail.html',
			controller: 'ShipmentDetailCtrl'
		}).when('/notes',
		{
			templateUrl: 'partials/note-list.html',
			controller: 'NoteListCtrl'
		}).when('/notes/:noteId',
		{
			templateUrl: 'partials/note-detail.html',
			controller: 'NoteDetailCtrl'
		}).when('/checklists',
		{
			templateUrl: 'partials/checklist-list.html',
			controller: 'ChecklistListCtrl'
		}).when('/checklists/:checklistId',
		{
			templateUrl: 'partials/checklist-detail.html',
			controller: 'ChecklistDetailCtrl'
		}).when('/threads',
		{
			templateUrl: 'partials/thread-list.html',
			controller: 'ThreadListCtrl'
		}).when('/threads/:threadId',
		{
			templateUrl: 'partials/thread-detail.html',
			controller: 'ThreadDetailCtrl'
		})
		
		
		
		
		.otherwise({
			redirectTo: '/login'
		});
		}]);
		
	