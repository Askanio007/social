app.service('DialogService', ['$http', 'ENV', function ($http, ENV) {
    return {
        find: function (rootUserId) {
            return $http.get(ENV.API_URL + rootUserId + '/dialog').then(function (response) {
                return response.data.data;
            })
        },
        findMessages: function (rootUserId, dialogId) {
            return $http.get(ENV.API_URL + rootUserId + '/dialog/' + dialogId + '/message').then(function (response) {
                return response.data.data;
            })
        }
    }
}]);
