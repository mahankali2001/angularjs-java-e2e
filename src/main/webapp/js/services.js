'use strict';

/* Services */
/*
var services = angular.module('ngdemo.services', ['ngResource']);

services.factory('UserFactory', function ($resource) {
    return $resource('/ngdemo/rest/app/user', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        }
    })
    
});
*/

ngdemo.service('ngdemo.services', ['$http', function ($http) {
	
this.GetUrl = function (methodName) {
    return "/ngdemo/rest/" + methodName;
},
/*this.GetSetupGridConfig = function (callback) {
    var url = $('#hdnAppPath').val() + "/Angular/Setup/SetupGridConfig.json";
        $http.get(url).success(function (data) {
            var result = (data) || [];
            if (typeof callback == 'function') callback(angular.fromJson(result));
        });
 },*/
this.GetUser = function (uid,callback) {
    $http.get(this.GetUrl("app/users/")+uid).success(function (data) {
        var result = (data) || [];
        if (typeof callback == 'function') callback(angular.fromJson(result));
    })
};
this.GetUsers = function (callback) {
    $http.get(this.GetUrl("app/users")).success(function (data) {
        var result = (data) || [];
        if (typeof callback == 'function') callback(angular.fromJson(result));
    });
};
this.SaveUser = function (user,callback) {
    $http.post(this.GetUrl("app/users/input"),user).success(function (data, status, headers, config) {
        var result = (data) || [];
        if (typeof callback == 'function') callback(angular.fromJson(result));
    }).
    error(function (data, status, headers, config) {
        alert("NOT SENT");
    });
};
this.DeleteUser = function (uid,callback) {
    $http.get(this.GetUrl("app/users/delete/")+uid).success(function (data) {
        var result = (data) || [];
        if (typeof callback == 'function') callback(angular.fromJson(result));
    });
};
this.CopyUser = function (uid,callback) {
    $http.get(this.GetUrl("app/users/copy/")+uid).success(function (data) {
        var result = (data) || [];
        if (typeof callback == 'function') callback(angular.fromJson(result));
    });
};
}]);

/*
services.factory("serviceFactory", function () {
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
*/