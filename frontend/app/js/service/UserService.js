app.service('UserService', ['$http', 'ENV', function ($http, ENV) {
    return {
        getUserById: function (userId) {
            return $http.get(ENV.API_URL + 'user/' + userId).then(function (response) {
                return response.data.data;
            })
        },
        getFriendsById: function (userId) {
            return $http.get(ENV.API_URL + userId + '/friends').then(function (response) {
                return response.data.data;
            })
        },
        getFriendsRequestById: function (userId) {
            return $http.get(ENV.API_URL + userId + '/friends/request').then(function (response) {
                return response.data.data;
            })
        },
        sendFriendRequest: function (friendshipRequest) {
            return $http.post(ENV.API_URL + friendshipRequest.fromUserId + '/friends/request/add', friendshipRequest)
        },
        isFriend: function (rootUserId, userId) {
            return $http.get(ENV.API_URL + rootUserId + '/friends/existence/' + userId);
        },
        isFriendRequest: function (rootUserId, userId) {
            return $http.get(ENV.API_URL + rootUserId + '/friends/request/' + userId);
        },
        saveProfile: function (profile) {
            return $http.post(ENV.API_URL + profile.id + '/profile', profile)
        },
        searchUser: function (rootUserId, userName) {
            return $http.get(ENV.API_URL + 'user/search?userName=' + userName + '&rootUserId=' + rootUserId);
        }
    }
}]);
