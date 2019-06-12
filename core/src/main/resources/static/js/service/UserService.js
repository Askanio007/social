app.service('UserService', ['$http', function ($http) {
    return {
        getUserById: function (userId) {
            return $http.get('/api/v1/user/' + userId).then(function (response) {
                return response.data;
            })
        },
        getFriendsById: function (userId) {
            return $http.get('/api/v1/' + userId + '/friends').then(function (response) {
                return response.data;
            })
        },
        getFriendsRequestById: function (userId) {
            return $http.get('/api/v1/' + userId + '/friends/request').then(function (response) {
                return response.data;
            })
        }
    }
}]);