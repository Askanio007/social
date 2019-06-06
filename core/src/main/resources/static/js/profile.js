
app.controller('ProfileController', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $scope.userId = $rootScope.userId;
}])