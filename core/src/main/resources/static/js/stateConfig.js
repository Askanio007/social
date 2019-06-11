'use strict';

var app = angular.module('app', ['ui.router', 'ngCookies', 'pascalprecht.translate'])

app.config(stateConfig);
stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];
function stateConfig($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise("/login");
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
      .state('userPage', {
          url: '/id{userId}',
          templateUrl: '/views/user.html',
          controller: 'UserController',
          resolve: {
              user: ['$stateParams', 'UserService', function ($stateParams, UserService) {
                  return UserService.getUserById($stateParams.userId);
              }],
              wall: ['$stateParams', 'PublicMessageService', function ($stateParams, PublicMessageService) {
                  return PublicMessageService.findToUser($stateParams.userId);
              }]
          }
      })
      .state('profile', {
        url: '/profile',
        templateUrl: '/views/profile.html',
        controller: 'ProfileController'
      });
}

app.config(translateConfig);

translateConfig.$inject = ['$translateProvider'];

function translateConfig($translateProvider) {
    $translateProvider.useUrlLoader('/api/v1/resources');
    $translateProvider.useLocalStorage();
    $translateProvider.preferredLanguage('ru');
    $translateProvider.fallbackLanguage('ru');
}