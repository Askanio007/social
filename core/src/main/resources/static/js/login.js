
app.controller('LoginController', ['$scope', '$http', '$state', '$rootScope', function ($scope, $http, $state, $rootScope) {
    $scope.email = '';
    $scope.password = '';

    $scope.login = function () {
        $http({
            url: "/api/v1/login",
            params: {email: $scope.email, password: $scope.password},
            method: "GET"
        }).then(function (response) {
            $rootScope.userId = response.data.id;
            $state.go('userPage', {userId: response.data.id});
        })
    }
}]);