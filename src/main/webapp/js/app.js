'use strict';

// Declare app level module which depends on filters, and services
//http://stackoverflow.com/questions/16703215/how-to-reload-or-re-render-the-entire-page-using-angularjs
var ngdemo = angular.module('ngdemo', ["ngTable","pascalprecht.translate","ngRoute","ui.bootstrap"]).//['ngdemo.filters', 'ngdemo.services', 'ngdemo.directives', 'ngdemo.controllers']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        			.when('/user1/', 
        				{
        					templateUrl: 'partials/user1.html', 
        					controller: 'userController'
        				})
        			.when('/user2/', 
        				{
        					templateUrl: 'partials/user2.html', 
        					controller: 'userController'
        				})
        			.when('/users/', 
        				{
        					templateUrl: 'partials/users.html', 
        					controller: 'userController'
        				})
        			.when('/user/', 
        				{
        					templateUrl: 'partials/user.html', 
        					controller: 'userController'
        				})
        			.otherwise({redirectTo: '/users/'});
    	}]);

// app level cache to store/get data across controllers 
ngdemo.factory("sharedScope", function () {
    var mem = {},
    factory = {};

        factory.store = function (key, value) {
            mem[key] = value;
        };

        factory.get = function (key) {
            return mem[key];
        };

        return factory;
    });

ngdemo.directive('demoHeader', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: "partials/header.html",
        controller: ['$scope', '$filter', function ($scope, $filter) {
            // Your behaviour goes here :)
         } ]
    }
});

ngdemo.config(["$translateProvider", function ($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix: "locale/demo_",
        suffix: ".json"
    });
    // Tell the module what language to use by default
    $translateProvider.preferredLanguage("en-US");
    $translateProvider.useSanitizeValueStrategy("escaped");
} ]);