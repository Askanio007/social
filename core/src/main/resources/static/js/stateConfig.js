'use strict';

var app = angular.module('app', ['ui.router']);

app.config(stateConfig);
stateConfig.$inject = ['$stateProvider'];
function stateConfig($stateProvider) {
    $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: '/views/login.html',
            controller: 'LoginController'
        })
        .state('registration', {
            url: '/registration',
            templateUrl: '/views/registration.html',
            controller: 'RegistrationController'
        })
        .state('profile', {
            url: '/profile',
            templateUrl: '/views/profile.html',
            controller: 'ProfileController'
        });
}