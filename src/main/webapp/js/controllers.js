'use strict';

/* Controllers */


//var ngdemoControllers = angular.module('ngdemo.controllers', []);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
/*app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});*/

/*
app.controller('MyCtrl1', ['$scope', 'UserFactory', function ($scope, UserFactory) {
    $scope.bla = 'bla from controller';
    UserFactory.get({}, function (userFactory) {
        $scope.firstname = userFactory.firstName;
    })
}]);

app.controller('MyCtrl2', ['$scope', 'UserFactory', function ($scope, UserFactory) {
    $scope.bla = 'bla bla from controller';
    UserFactory.get({}, function (userFactory) {
        $scope.lastname = userFactory.lastName;
    })
}]);

app.controller('MyCtrl3', ['$scope', 'UserFactory', function ($scope, UserFactory) {
    $scope.bla = 'bla bla from controller';
    UserFactory.get({}, function (userFactory) {
        $scope.lastname = userFactory.lastName;
    })
}]);

*/

ngdemo.controller('baseController', ['$scope', '$location','$translate','ngdemo.services','sharedScope', function ($scope, $location,$translate,demoService,sharedScope) {
	if ($location.path().indexOf("user") === -1) {
        return;
    }
	$scope.SITE_BASE_URL = $location.absUrl().substr(0, $location.absUrl().indexOf('/ngdemo'));
	$scope.alerts = [];
	$scope.QueryString = $location.search(); //https://docs.angularjs.org/guide/$location
	$scope.Lang = [{ID: "en-US", Text: "en-US" },{ ID: "en-CA", Text: "en-CA" },{ ID: "hi-IN", Text: "hi-IN" }];
	$scope.ddlLang = $scope.Lang[0]; 
	$scope.UserName = $translate.instant("UserName");
	$scope.showUID = true;
	$scope.showUserDetails = true;
	$scope.SetScopeValues = function(userData){
		$scope.uid = userData.uid;
		$scope.firstName = userData.firstName;
        $scope.lastName = userData.lastName;        
    };    
    $scope.EncodeSpecialChars = function(str){
        return str.replace(/"/g, '\\"').replace(/\&/g, "_amp_");
    };
}]);

ngdemo.controller('userController', ['$scope', '$controller','$location','$translate','$filter','ngdemo.services','sharedScope','NgTableParams', function ($scope, $controller,$location,$translate,$filter,demoService,sharedScope,NgTableParams) {
	
   $controller('baseController', { $scope: $scope });
   $scope.AllowGo = true;
    //http://jsfiddle.net/6GA27/1/   
    
   $scope.SwitchLanguage = function(){
        var val = $scope.ddlLang.ID; //"en-CA";//$scope.GetSelectedLang("ID", $scope.selectedLang).ID    	
        //$translate.use(key);
    	//$scope.UserName = $translate.instant("DemoPages.UserName");
        $translate.use(val).then(function (translation) {
            // then translate here
    		$scope.UserName = $translate.instant("DemoPages.UserName");
        });
        $scope.EncodeSpecialChars("");
        $location.path($location.path()).search($scope.QueryString);
    };
    
    $scope.GetSelectedLang = function (column, value) {
        var selectedLang = null;
        angular.forEach($scope.Lang, function (lang) {
            if (column === "ID") {
                if (value === lang.ID) {
                	selectedLang = lang;
                }
            }
            else if (column === "Text") {
                if (value === lang.Text) {
                	selectedLang = lang;
                }
            }            
        });
        if (selectedLang === null && $scope.Lang !== undefined)
        	selectedLang = $scope.Lang[0];
        return selectedLang;
    };
    
    $scope.LoadUsers = function(){
    	demoService.GetUsers(function (returnData) {
	    	$scope.users =  returnData;
	    	sharedScope.store("users", $scope.users);
	    	if($scope.users !== undefined)
	    		$scope.SetScopeValues($scope.users[0]);	    	
	        //var userList = $scope.users;	        
	        $scope.tableParams = new NgTableParams(
	        		{page: 1, 
	        			count: 10}, 
	            { total: $scope.users.length,	            	
	            	getData: function ($defer, params) {
	                    // use build-in angular filter
	                    var orderedData = params.sorting() ?
	                                    $filter('orderBy')($scope.users, params.orderBy()) :
	                                    	$scope.users;
	                    orderedData = params.filter() ?
	                                    $filter('filter')(orderedData, params.filter()) :
	                                    orderedData;	                    
	                    
	                    params.total(orderedData.length); // set total for recalc pagination
	                    $defer.resolve(orderedData = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
	                }});
	    	//$scope.$apply();
	    });
    };
    
    $scope.LoadUser = function(){
    	var uid = 0;
    	//Get QueryString Parameters
	    if($scope.QueryString !== undefined && $scope.QueryString.uid !== undefined)
	    	uid = $scope.QueryString.uid;
	    if(uid != 0)
	    {
		    var userData = sharedScope.get("userData"+uid);
		    if (userData !== undefined) {
		    	$scope.user = userData;
		    	$scope.SetScopeValues($scope.user);
		    }
		    else {
			    demoService.GetUser(uid,function (returnData) {
			    	$scope.user =  returnData;
			    	sharedScope.store("userData"+uid, $scope.user);
			    	$scope.SetScopeValues($scope.user);
			        //$scope.$apply();
			    });
		    }
	    }
    };
    
    $scope.EncodeSpecialChars = function(str){
        return str.replace(/"/g, '\\"').replace(/\&/g, "_amp_");
    };
    $scope.SetScopeValues = function(userData){
		$scope.uid = userData.uid;
		$scope.firstName = userData.firstName;
        $scope.lastName = userData.lastName;        
    };
    $scope.SubmitForm = function(isValid){
    	if($scope.uid == undefined)
    		$scope.uid = 0;
    	var user = "{\"uid\":"+$scope.uid+", \"firstName\":\""+$scope.EncodeSpecialChars($scope.firstName)+"\", \"lastName\":\""+$scope.EncodeSpecialChars($scope.lastName) +"\"}";
        //var user = JSON.stringify(json);
    	// check to make sure the form is completely valid
    	$scope.submitted = true;
    	demoService.SaveUser(user,function (returnData) {
	    	$scope.user =  returnData;
	    	sharedScope.store("userData"+$scope.user.uid, $scope.user);
	    	$scope.SetScopeValues($scope.user);	        
	    });
        if (isValid) {
         // alert('our form is amazing');
        }
    };
    
    $scope.btnGo_clicked = function(){
    	$scope.LoadUser($scope.UserID);
    	//var path = $location.path()+"?uid="+$scope.UserID;
    	$location.path($location.path()).search({ uid: $scope.UserID });
    };
    
    $scope.userLink_clicked = function(uid){
    	//$scope.LoadUser($scope.UserID);
    	//var path = $location.path()+"?uid="+$scope.UserID;
    	$location.path("/user").search({ uid: uid });
    };
    
    $scope.deleteLink_clicked = function(uid){    	
    	demoService.DeleteUser(uid,function (returnData) {
	    	//alert("User deleted successfully!");
	    	//$location.path($location.path()).search($location.search());
	    	$location.url("/users");
	    });    	
    };
    
    $scope.copyLink_clicked = function(uid){
    	demoService.CopyUser(uid,function (returnData) {
	    	//alert("User copied successfully!");
	    	//$location.path($location.path()).search($location.search());
	    	$location.url("/users");
	    });    	
    };
    
    if ($location.path().indexOf("users") > -1)
    {	
    	$scope.showUID = false; 
    	$scope.LoadUsers();    	
    }
    if ($location.path().indexOf("user") > -1)
    {	
    	$scope.showUID = false; 
    	$scope.showUserDetails = false;
    	$scope.LoadUser();    	
    }
    /*else
    {
    	$scope.LoadUser(1);
    }*/   
}]);

/* http://stackoverflow.com/questions/31056868/angularjs-location-path-not-reloading-data-of-the-destination-view
$rootScope.$broadcast('data:updated', $scope.data);

$rootScope.$on('data:updated',function(listener,data) {
 $scope.data.push(data);
});

$rootScope.$on('data:updated',function()
{
   callAjax.then(function(data) {
     $scope.data = data;
   }
});
 */
