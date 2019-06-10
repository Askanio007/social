app.service('UserService', ['$http', function ($http) {
    return {
        getUserById: function (userId) {
            return $http.get('/api/v1/user/' + userId).then(function (response) {
                return response.data;
            })
        }
    }

}]);