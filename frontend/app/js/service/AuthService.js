app.service('AuthService', ['$http', 'ENV', function ($http, ENV) {
    return {
        registration: function (registrationModel) {
            return $http.post(ENV.API_URL + 'registration', registrationModel)
        },
        login: function (loginModel) {
            return $http({
                url: ENV.API_URL + 'login',
                params: {email: loginModel.email, password: loginModel.password},
                method: "GET"
            })
        }
    }
}]);
