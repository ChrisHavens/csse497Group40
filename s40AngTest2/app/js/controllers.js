'use strict';

/* Controllers */
var phonecatControllers = angular.module('phonecatControllers', ['phonecatServices']);

phonecatControllers.controller('LoginCtrl', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
	
	$scope.Login=function(userid){
		$http.get('http://localhost:8080/WrappingServer/rest/api/person/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			$rootScope.uID = userid;
			$location.path('/home');
			
		}
	});
		
		
	}
	
	}]);

phonecatControllers.controller('ProjectListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/project?show_hidden=true').success(function(data){
		$scope.projects = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('ProjectDetailCtrl', ['$scope', '$routeParams' ,'$http','$timeout','modalService','$rootScope', function($scope, $routeParams, $http, $timeout, modalService,$rootScope){
	$http.get('http://localhost:8080/WrappingServer/rest/api/project/'+$routeParams.projectId).success(function(data){
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
            $http.delete('http://localhost:8080/WrappingServer/rest/api/project/'+projID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/projects');
			
			}
        });
		
	});
		
		
	}
	}]);
	
phonecatControllers.controller('GroupListCtrl', ['$scope', '$http',function($scope, $http){
		
	$http.get('http://localhost:8080/WrappingServer/rest/api/group?show_hidden=true').success(function(data){
		$scope.groups = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	

	
phonecatControllers.controller('GroupDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/group/'+$routeParams.groupId).success(function(data){
		$scope.group=data;
	
	});
	
	}]);

phonecatControllers.controller('PersonListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
		$scope.persons = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('PersonDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/person/'+$routeParams.personId).success(function(data){
		$scope.person=data;
	
	});
	
	}]);
phonecatControllers.controller('LocationListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/location?show_hidden=true').success(function(data){
		$scope.locations = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('LocationDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/location/'+$routeParams.locationId).success(function(data){
		$scope.loc=data;
	
	});
	
	}]);

phonecatControllers.controller('ShipmentListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/shipment?show_hidden=true').success(function(data){
		$scope.shipments = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ShipmentDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/shipment/'+$routeParams.shipmentId).success(function(data){
		$scope.ship=data;
	
	});
	
	}]);
phonecatControllers.controller('NoteListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/note?show_hidden=true').success(function(data){
		$scope.notes = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('NoteDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/note/'+$routeParams.noteId).success(function(data){
		$scope.note=data;
	
	});
	
	}]);
phonecatControllers.controller('ChecklistListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/checklist?show_hidden=true').success(function(data){
		$scope.clists = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ChecklistDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/checklist/'+$routeParams.checklistId).success(function(data){
		$scope.clist=data;
	
	});
	
	}]);
phonecatControllers.controller('ThreadListCtrl', ['$scope', '$http',function($scope, $http){

	$http.get('http://localhost:8080/WrappingServer/rest/api/thread?show_hidden=true').success(function(data){
		$scope.threads = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ThreadDetailCtrl', ['$scope', '$routeParams' ,'$http', function($scope, $routeParams, $http){
	$http.get('http://localhost:8080/WrappingServer/rest/api/thread/'+$routeParams.threadId).success(function(data){
		$scope.thread=data;
	
	});
	$http.get('http://localhost:8080/WrappingServer/rest/api/thread/'+$routeParams.threadId+'/messages').success(function(data){
		$scope.messages=data.hits.hits;
	
	});
	$http.get('http://localhost:8080/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
		$scope.people=data.hits.hits;
		
	});
	
	}]);