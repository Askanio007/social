app.service('EventService', ['$http', 'ENV', function ($http, ENV) {
    return {
        find: function (rootUserId) {
            return $http.get(ENV.API_URL + rootUserId + '/events').then(function (response) {
                return response.data.data;
            })
        }
    }
}]);
