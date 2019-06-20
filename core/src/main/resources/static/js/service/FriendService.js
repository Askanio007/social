app.service('FriendService', ['$http', function ($http) {
    return {
        acceptRequest: function (rootUserId, friendshipRequestId) {
            return $http.post('/api/v1/' + rootUserId + '/friends/request/accept', friendshipRequestId).then(function (response) {
                return response.data.data;
            })
        },
        declineRequest: function (rootUserId, friendshipRequestId) {
            return $http.post('/api/v1/' + rootUserId + '/friends/request/decline', friendshipRequestId).then(function (response) {
                return response.data.data;
            })
        },
        remove: function (userId) {
            return $http.get('/api/v1/user/' + userId).then(function (response) {
                return response.data.data;
            })
        }
    }
}]);
