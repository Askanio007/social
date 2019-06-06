'use strict';

var myApp = angular.module('app', []);

myApp.controller('RegistrationController', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
        $scope.email = 'Enter email';
        $scope.password = 'Enter password';

        $scope.registration = function () {
            $http.post('/registration', {email: $scope.email, password: $scope.password}).then(function (response) {
                console.log(response.data);
            })
        }
    }]);