app.service('EventService', ['$http', function ($http) {
    return {
        find: function (rootUserId) {
            return $http.get('/api/v1/' + rootUserId + '/events').then(function (response) {
                return response.data;
            })
        }
    }
}]);