app.service('AuthService', ['$http', function ($http) {
    return {
        registration: function (registrationModel) {
            return $http.post('/api/v1/registration', registrationModel)
        },
        login: function (loginModel) {
            return $http({
                url: "/api/v1/login",
                params: {email: loginModel.email, password: loginModel.password},
                method: "GET"
            })
        }
    }
}]);
