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
      .state('myPage', {
          url: '/myPage',
          templateUrl: '/views/user.html',
          controller: 'UserController',
          resolve: {
              user: ['$window', 'UserService', function ($window, UserService) {
                  return UserService.getUserById($window.sessionStorage.getItem("userId"));
              }],
              wall: ['$window', 'PublicMessageService', function ($window, PublicMessageService) {
                  return PublicMessageService.findToUser($window.sessionStorage.getItem("userId"));
              }]
          }
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
      })
      .state('friends', {
          url: '/friends',
          templateUrl: '/views/friends.html',
          controller: 'FriendController',
          resolve: {
              friends: ['$window', 'UserService', function ($window, UserService) {
                  return UserService.getFriendsById($window.sessionStorage.getItem("userId"));
              }],
              requestFriends:['$window', 'UserService', function ($window, UserService) {
                  return UserService.getFriendsRequestById($window.sessionStorage.getItem("userId"));
              }]
          }
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