'use strict';

/* Controllers */
var phonecatControllers = angular.module('phonecatControllers', ['phonecatServices']);

phonecatControllers.controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location','$q', function($scope, $rootScope, $http, $location, $q){
	
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
			$location.path('/home');
		}
	});
	}
	
	}]);
phonecatControllers.controller('HomeCtrl', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};

	}]);
phonecatControllers.controller('ProjectListCtrl', ['$scope', '$rootScope',  '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/all/').success(function(data){
		$scope.projects = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('ProjectDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/'+$routeParams.projectId).success(function(data){
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
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/'+projID+'?person='+$rootScope.uID).success(function(data){
				$location.path('/projects');
        });
		
	});
		
		
	}
	}]);
phonecatControllers.controller('ProjectEditCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/'+$routeParams.projectId).success(function(data){
		$scope.project=data;
	
	});
	$scope.EditProject=function(projName){
		var projID = $scope.project._id;
			var param = JSON.stringify({doc:{name:projName}});
            $http.post('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/'+projID+'/update?person='+$rootScope.uID,param
			).success(function(data){
		
			
				$location.path('/projects/'+$scope.project._id);
        });
		
	};
		
		
	}]);
phonecatControllers.controller('ProjectCreateCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$scope.NewProject=function(projName){
			$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/idgen?type=project&person='+$rootScope.uID).success(function(data){
			$scope.id=data;
			var projID = $scope.id;
			var param = JSON.stringify({name:projName});
            $http.put('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/project/'+projID+'?person='+$rootScope.uID,param
			).success(function(data){
		
			
				$location.path('/projects/'+$scope.id);
        });
		});
	};
		
		
	}]);
phonecatControllers.controller('GroupListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};	
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/all/').success(function(data){
		$scope.groups = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	

	
phonecatControllers.controller('GroupDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/'+$routeParams.groupId).success(function(data){
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
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/'+groupID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/groups');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('GroupEditCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/'+$routeParams.groupId).success(function(data){
		$scope.group=data;
	
	});
	$scope.EditGroup=function(groupName){
		var groupID = $scope.group._id;
			var param = JSON.stringify({doc:{name:groupName}});
            $http.post('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/'+groupID+'/update?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/groups/'+$scope.group._id);
			
        });
		
	};
		
		
	}]);
phonecatControllers.controller('GroupCreateCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$scope.CreateGroup=function(groupName,projID){
			$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/idgen?type=project&person='+$rootScope.uID).success(function(data){
			$scope.id=data;
			var groupID = $scope.id;
			var param = JSON.stringify({name:groupName, projectID: projID});
            $http.put('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/group/'+groupID+'?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/groups/'+groupID);
			
        });
		
	});
	};	
		
	}]);
phonecatControllers.controller('PersonListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
		$scope.persons = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('PersonDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person/'+$routeParams.personId).success(function(data){
		$scope.person=data;
	
	});
	$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			}
	$scope.DeletePerson=function(){
		var persID = $scope.person._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Person',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this person?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person/'+persID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/persons');
			
			}
        });
		
	});
	}
	}]);
	
phonecatControllers.controller('PersonEditCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person/'+$routeParams.personId).success(function(data){
		$scope.person=data;
	
	});
	$scope.EditPerson=function(personName,personEmail,personPhone){
		var perID = $scope.person._id;
			var param = JSON.stringify({doc:{name:personName, email:personEmail,phone:personPhone}});
            $http.post('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person/'+perID+'/update',param
			).success(function(data){
			$location.path('/persons/'+$scope.person._id);
			
        });
		
	};
	}]);
phonecatControllers.controller('PersonCreateCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$scope.CreatePerson=function(personName,personEmail,personPhone){
			$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/idgen?type=project&person='+$rootScope.uID).success(function(data){
			$scope.id=data;
			var personID = $scope.id;
			var param = JSON.stringify({name:personName, email:personEmail, phone:personPhone});
            $http.put('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person/'+personID+'?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/persons/'+personID);
			
        });
		
	});
	};
	}]);
phonecatControllers.controller('LocationListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location?show_hidden=true').success(function(data){
		$scope.locations = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
	
phonecatControllers.controller('LocationDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location,$q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location/'+$routeParams.locationId).success(function(data){
		$scope.loc=data;
	
	});$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			}
	$scope.DeleteLocation=function(){
		var locID = $scope.loc._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Location',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this location?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location/'+locID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/locations');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('LocationEditCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location/'+$routeParams.locationId).success(function(data){
		$scope.loc=data;
	
	});
	$scope.EditLocation=function(locName,locLat,locLng){
			var locID = $scope.loc._id;
			var parents = $scope.loc._source.parentIDs[0].parentID;
			var param = JSON.stringify({doc:{name:locName, lat:locLat,lng:locLng,parentIDs:[{parentID:parents}]}});
            $http.post('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location/'+locID+'/update?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/locations/'+$scope.loc._id);
			
        });
		
	};
	}]);
phonecatControllers.controller('LocationCreateCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$scope.CreateLocation=function(locName,locLat,locLng,parents){
			$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/idgen?type=project&person='+$rootScope.uID).success(function(data){
			$scope.id=data;
			var locationID = $scope.id;
			var param = JSON.stringify({name:locName, lat:locLat, lng:locLng, parentIDs:[{parentID:parents}]});
            $http.put('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/location/'+locationID+'?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/locations/'+locationID);
			
        });
		
	});
	};
	}]);
	
phonecatControllers.controller('ShipmentListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){

	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/shipment?show_hidden=true').success(function(data){
		$scope.shipments = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ShipmentDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/shipment/'+$routeParams.shipmentId).success(function(data){
		$scope.ship=data;
	
	});$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			}
	$scope.DeleteShipment=function(){
		var shipID = $scope.ship._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Shipment',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this shipment?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/shipment/'+shipID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/shipments');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('NoteListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/note?show_hidden=true').success(function(data){
		$scope.notes = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('NoteDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location,$q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/note/'+$routeParams.noteId).success(function(data){
		$scope.note=data;
	
	});$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			}
	$scope.DeleteNote=function(){
		var noteID = $scope.note._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Note',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this note?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/note/'+noteID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/notes');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('NoteEditCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService,  $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/note/'+$routeParams.noteId).success(function(data){
		$scope.note=data;
	
	});
	$scope.EditNote=function(noteName,noteBody){
			var noteID = $scope.note._id;
			var param = JSON.stringify({doc:{name:noteName, contents:noteBody}});
			alert(param);
            $http.post('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/note/'+noteID+'/update?person='+$rootScope.uID,param
			).success(function(data){
			$location.path('/notes/'+$scope.note._id);
			
        });
		
	};
	}]);
phonecatControllers.controller('ChecklistListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/checklist?show_hidden=true').success(function(data){
		$scope.clists = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ChecklistDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location,$q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/checklist/'+$routeParams.checklistId).success(function(data){
		$scope.clist=data;
	
	});$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			}
	$scope.DeleteChecklist=function(){
		var clistID = $scope.clist._id;
		var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Checklist',
            headerText: 'Are you sure?',
            bodyText: 'Are you sure you want to delete this checklist?'
        };
		 modalService.showModal({}, modalOptions).then(function (result) {
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/checklist/'+clistID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/checklists');
			
			}
        });
		
	});
	}
	}]);
phonecatControllers.controller('ThreadListCtrl', ['$scope', '$rootScope', '$http',function($scope, $rootScope, $http){

	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/thread?show_hidden=true').success(function(data){
		$scope.threads = data.hits.hits
	
	});
	$scope.orderprop='timeModified';
	}]);
phonecatControllers.controller('ThreadDetailCtrl', ['$scope', '$rootScope', '$routeParams' ,'$http','$timeout','modalService','$location','$q', function($scope, $rootScope, $routeParams, $http, $timeout, modalService, $location, $q){
	$scope.Login=function(userid){
		$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/login/'+userid).success(function(data){
			
			
		$scope.loginresponse = data
		
		if ($scope.loginresponse.found){
			console.log("ID: " + data.personId);
			$rootScope.uID = data.personId;
		}else{
						$location.path('/login');
		}
	});
	};$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/thread/'+$routeParams.threadId).success(function(data){
		$scope.thread=data;
	
	});
	$scope.getNameFromID = function (id){
				var promise = $q.defer();
				$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/utils/names/'+userid).success(function(data){
					promise.resolve(data);
			});
				return promise.promise
			};
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/thread/'+$routeParams.threadId+'/messages').success(function(data){
		$scope.messages=data.hits.hits;
	
	});
	$http.get('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/person?show_hidden=true').success(function(data){
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
            $http.delete('https://s40server.csse.rose-hulman.edu:8443/WrappingServer/rest/api/thread/'+tID+'?person='+$rootScope.uID).success(function(data){
		
			$scope.response = data;
			if ($scope.response.found){
			
				$location.path('/threads');
			
			}
        });
		
	});
	}
	}]);