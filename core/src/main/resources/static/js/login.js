
app.controller('LoginController', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $scope.email = 'Enter email';
    $scope.password = 'Enter password';

    $scope.login = function () {
        $http.get('/login', {email: $scope.email, password: $scope.password}).then(function (response) {
            console.log(response.data);
        })
    }
}]);