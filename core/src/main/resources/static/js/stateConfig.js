'use strict';

var app = angular.module('app', ['ui.router', 'ngCookies', 'pascalprecht.translate']);

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
          templateUrl: '/views/editProfile.html',
          controller: 'ProfileController',
          resolve: {
              user: ['$window', 'UserService', function ($window, UserService) {
                  return UserService.getUserById($window.sessionStorage.getItem("userId"));
              }]
          }
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
      })
      .state('groups', {
          url: '/groups',
          templateUrl: '/views/groups.html',
          controller: 'GroupsController',
          resolve: {
              groups: ['$window', 'GroupService', function ($window, GroupService) {
                  return GroupService.findByUserId($window.sessionStorage.getItem("userId"));
              }]
          }
      })
      .state('groupPage', {
          url: '/group{groupId}',
          templateUrl: '/views/group.html',
          controller: 'GroupController',
          resolve: {
              group: ['$stateParams', 'GroupService', function ($stateParams, GroupService) {
                  return GroupService.find($stateParams.groupId);
              }],
              wall: ['$stateParams', 'PublicMessageService', function ($stateParams, PublicMessageService) {
                  return PublicMessageService.findToGroup($stateParams.groupId);
              }]

          }
      })
      .state('groupCreate', {
          url: '/createGroup',
          templateUrl: '/views/createGroup.html',
          controller: 'CreateGroupController'
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