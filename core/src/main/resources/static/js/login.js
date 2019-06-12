
app.controller('LoginController', ['$scope', '$http', '$state', '$window', function ($scope, $http, $state, $window) {
    $scope.email = '';
    $scope.password = '';

    $scope.login = function () {
        $http({
            url: "/api/v1/login",
            params: {email: $scope.email, password: $scope.password},
            method: "GET"
        }).then(function (response) {
            $window.sessionStorage.setItem("userId",response.data.id);
            $state.go('myPage');
        })
    }
}]);