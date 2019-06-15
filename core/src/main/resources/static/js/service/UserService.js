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
        },
        sendFriendRequest: function (friendshipRequest) {
            return $http.post('/api/v1/' + friendshipRequest.fromUserId + '/friends/request/add', friendshipRequest)
        },
        isFriend: function (rootUserId, userId) {
            return $http.get('/api/v1/' + rootUserId + '/friends/' + userId);
        },
        isFriendRequest: function (rootUserId, userId) {
            return $http.get('/api/v1/' + rootUserId + '/friends/request/' + userId);
        }
    }
}]);