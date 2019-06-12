
app.controller('RegistrationController', ['$scope', '$http', '$window', '$rootScope', '$state', function ($scope, $http, $window, $rootScope, $state) {
    $scope.email = '';
    $scope.password = '';
    $scope.name = '';
    $scope.surname = '';

    $scope.registration = function () {
        var params = {
            email: $scope.email,
            password: $scope.password,
            name: $scope.name,
            surname: $scope.surname
        };
        $http.post('/api/v1/registration', params).then(function (response) {
            $window.sessionStorage.setItem("userId",response.data.id);
            $state.go('myPage');
        })
    }
}]);