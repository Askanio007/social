
app.controller('LoginController', ['$scope', '$state', '$window', 'AuthService', function ($scope, $state, $window, AuthService) {
    $scope.loginModel = {
        email: '',
        password: ''
    };

    $scope.errors = [];

    $scope.login = function () {
        $scope.errors = [];
        AuthService.login($scope.loginModel).then(function (response) {
            var data = response.data;
            if (data.success == true) {
                $window.sessionStorage.setItem("userId",data.data.id);
                $state.go('myPage');
            } else {
                $scope.errors = data.errors;
            }
        });

    };

    $scope.hasError = function () {
        return $scope.errors.length !== 0;
    }
}]);
