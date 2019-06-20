app.service('PublicMessageService', ['$http', 'ENV', function ($http, ENV) {
    return {
        sendMessage: function (params) {
            return $http.post(ENV.API_URL + 'message/public/save', params)
        },
        findToUser: function (userId) {
            return $http.get(ENV.API_URL + 'message/public/' + userId + '/' + 'true').then(function (response) {
                return response.data.data;
            })
        },
        findToGroup: function (groupId) {
            return $http.get(ENV.API_URL + 'message/public/' + groupId + '/' + 'false').then(function (response) {
                return response.data.data;
            })
        }
    }
}]);
