app.controller('UserController', ['$scope', 'UserService', function ($scope, UserService) {
    $scope.user = $scope.$resolve.user;
}]);