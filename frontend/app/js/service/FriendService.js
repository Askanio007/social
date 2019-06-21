app.service('FriendService', ['$http', 'ENV', function ($http, ENV) {
    return {
        acceptRequest: function (rootUserId, friendshipRequestId) {
            return $http.post(ENV.API_URL + rootUserId + '/friends/request/accept', friendshipRequestId)
        },
        declineRequest: function (rootUserId, friendshipRequestId) {
            return $http.post(ENV.API_URL + rootUserId + '/friends/request/decline', friendshipRequestId)
        },
        remove: function (rootUserId, userId) {
            return $http.post(ENV.API_URL + rootUserId + '/friends/remove/' + userId);
        }
    }
}]);
