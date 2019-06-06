
app.controller('RegistrationController', ['$scope', '$http', '$window', '$rootScope', '$state', function ($scope, $http, $window, $rootScope, $state) {
        $scope.email = 'Enter email';
        $scope.password = 'Enter password';

        $scope.registration = function () {
            $http.post('/registration', {email: $scope.email, password: $scope.password}).then(function (response) {
                console.log(response.data);
                $rootScope.userId = response.data.id;
                $state.go('profile');
            })
        }
}]);