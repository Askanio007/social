app.service('PublicMessageService', ['$http', function ($http) {
    return {
        sendMessage: function (params) {
            return $http.post('/api/v1/message/public/save', params)
        },
        findToUser: function (userId) {
            return $http.get('/api/v1/message/public/' + userId + '/' + 'true').then(function (response) {
                return response.data;
            })
        },
        findToGroup: function (groupId) {
            return $http.get('/api/v1/message/public/' + groupId + '/' + 'false').then(function (response) {
                return response.data;
            })
        }
    }
}]);