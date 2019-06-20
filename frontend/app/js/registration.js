
app.controller('RegistrationController', ['$scope', '$window', '$state', 'AuthService', function ($scope, $window, $state, AuthService) {
    $scope.registrationData = {
        email: "",
        password: "",
        name: "",
        surname: "",
        sex: null
    };
    $scope.errors = [];

    $scope.registration = function () {
        $scope.errors = [];
        AuthService.registration($scope.registrationData).then(function (response) {
            var data = response.data;
            if (response.success == true) {
                $window.sessionStorage.setItem("userId", response.data.id);
                $state.go('myPage');
            } else {
                $scope.errors = response.errors;
            }
        });
    };

    $scope.hasError = function () {
        return $scope.errors.length !== 0;
    }
}]);
