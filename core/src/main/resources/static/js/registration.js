
app.controller('RegistrationController', ['$scope', '$http', '$window', '$rootScope', '$state', function ($scope, $http, $window, $rootScope, $state) {
    $scope.registrationData = {
        email: "",
        password: "",
        name: "",
        surname: "",
        sex: ""
    };

    $scope.registration = function () {
        $http.post('/api/v1/registration', $scope.registrationData).then(function (response) {
            $window.sessionStorage.setItem("userId",response.data.id);
            $state.go('myPage');
        })
    }
}]);