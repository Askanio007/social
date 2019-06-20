app.service('FriendService', ['$http', 'ENV', function ($http, ENV) {
    return {
        acceptRequest: function (rootUserId, friendshipRequestId) {
            return $http.post(ENV.API_URL + rootUserId + '/friends/request/accept', friendshipRequestId).then(function (response) {
                return response.data.data;
            })
        },
        declineRequest: function (rootUserId, friendshipRequestId) {
            return $http.post(ENV.API_URL + rootUserId + '/friends/request/decline', friendshipRequestId).then(function (response) {
                return response.data.data;
            })
        },
        remove: function (userId) {
            return $http.get(ENV.API_URL + 'user/' + userId).then(function (response) {
                return response.data.data;
            })
        }
    }
}]);
