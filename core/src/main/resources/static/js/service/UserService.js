app.service('UserService', ['$http', function ($http) {
    return {
        getUserById: function (userId) {
            return $http.get('/api/v1/user/' + userId).then(function (response) {
                return response.data.data;
            })
        },
        getFriendsById: function (userId) {
            return $http.get('/api/v1/' + userId + '/friends').then(function (response) {
                return response.data.data;
            })
        },
        getFriendsRequestById: function (userId) {
            return $http.get('/api/v1/' + userId + '/friends/request').then(function (response) {
                return response.data.data;
            })
        },
        sendFriendRequest: function (friendshipRequest) {
            return $http.post('/api/v1/' + friendshipRequest.fromUserId + '/friends/request/add', friendshipRequest)
        },
        isFriend: function (rootUserId, userId) {
            return $http.get('/api/v1/' + rootUserId + '/friends/existence/' + userId);
        },
        isFriendRequest: function (rootUserId, userId) {
            return $http.get('/api/v1/' + rootUserId + '/friends/request/' + userId);
        },
        saveProfile: function (profile) {
            return $http.post('/api/v1/' + profile.id + '/profile', profile)
        },
        searchUser: function (rootUserId, userName) {
            return $http.get('/api/v1//user/search?userName=' + userName + '&rootUserId=' + rootUserId);
        }
    }
}]);
