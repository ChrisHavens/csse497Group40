'use strict';

/* Controllers */
var phonecatControllers = angular.module('phonecatControllers', ['phonecatServices']);

phonecatControllers.controller('LoginCtrl', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
	
	$scope.Login=function(userid){
		$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/person/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			$rootScope.uID = userid;
			$location.path('/home');
			
		}
	});
		
		
	}
	
	}]);
phonecatControllers.controller('HomeCtrl', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
	
	
		
		
	
	
	}]);
phonecatControllers.controller('ProjectListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/project?show_hidden=true').success(function(data){
		$scope.projects = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('ProjectDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/project/'+$routeParams.projectId).success(function(data){
		$scope.project=data;
	
	});
	$scope.DeleteProject=function(){
		var projID = $scope.project._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Project',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this project?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/project/'+projID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/projects');
			
			}
        });
		
	});
		
		
	}
	}]);
	
phonecatControllers.controller('GroupListCtrl', ['$scope', '$http',function($scope, $http){
		
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/group?show_hidden=true').success(function(data){
		$scope.groups = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	

	
phonecatControllers.controller('GroupDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/group/'+$routeParams.groupId).success(function(data){
		$scope.group=data;
	
	});
	$scope.DeleteGroup=function(){
		var groupID = $scope.group._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Group',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this group?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/group/'+groupID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/groups');
			
			}
        });
		
	});
	}
	}]);

phonecatControllers.controller('PersonListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
		$scope.persons = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('PersonDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/person/'+$routeParams.personId).success(function(data){
		$scope.person=data;
	
	});
	$scope.DeletePerson=function(){
		var persID = $scope.person._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Person',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this person?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/person/'+persID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/persons');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('LocationListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/location?show_hidden=true').success(function(data){
		$scope.locations = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('LocationDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/location/'+$routeParams.locationId).success(function(data){
		$scope.loc=data;
	
	});
	$scope.DeleteLocation=function(){
		var locID = $scope.loc._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Location',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this location?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/location/'+locID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/locations');
			
			}
        });
		
	});
	}
	}]);

phonecatControllers.controller('ShipmentListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/shipment?show_hidden=true').success(function(data){
		$scope.shipments = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ShipmentDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/shipment/'+$routeParams.shipmentId).success(function(data){
		$scope.ship=data;
	
	});
	$scope.DeleteShipment=function(){
		var shipID = $scope.ship._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Shipment',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this shipment?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/shipment/'+shipID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/shipments');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('NoteListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/note?show_hidden=true').success(function(data){
		$scope.notes = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('NoteDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/note/'+$routeParams.noteId).success(function(data){
		$scope.note=data;
	
	});
	$scope.DeleteNote=function(){
		var noteID = $scope.note._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Note',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this note?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/note/'+noteID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/notes');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('ChecklistListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/checklist?show_hidden=true').success(function(data){
		$scope.clists = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ChecklistDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/checklist/'+$routeParams.checklistId).success(function(data){
		$scope.clist=data;
	
	});
	$scope.DeleteChecklist=function(){
		var clistID = $scope.clist._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Checklist',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this checklist?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/checklist/'+clistID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/checklists');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('ThreadListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/thread?show_hidden=true').success(function(data){
		$scope.threads = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ThreadDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope','$location', function($scope, $routeParams, $http, $timeout, modalService,$rootScope, $location){
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/thread/'+$routeParams.threadId).success(function(data){
		$scope.thread=data;
	
	});
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/thread/'+$routeParams.threadId+'/messages').success(function(data){
		$scope.messages=data.hits.hits;
	
	});
	$http.get('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
		$scope.people=data.hits.hits;
		
	});
	$scope.DeleteThread=function(){
		var tID = $scope.thread._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Thread',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this thread?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('http://s40server.csse.rose-hulman.edu:8080/WrappingServer/rest/api/thread/'+tID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/threads');
			
			}
        });
		
	});
	}
	}]);