app.service('GroupService', ['$http', function ($http) {
    return {
        findByUserId: function (rootUserId) {
            return $http.get('/api/v1/' + rootUserId + '/groups').then(function (response) {
                return response.data;
            })
        },
        createGroup: function (rootUserId, name, description) {
            var params = {
                name: name,
                description: description
            };
            return $http.post('/api/v1/' + rootUserId + '/groups/create', params);
        },
        find: function (groupId) {
            return $http.get('/api/v1/group/' + groupId).then(function (response) {
                return response.data;
            })
        },
        isUserInGroup: function (userId, groupId) {
            return $http.get('/api/v1/' + userId + '/groups/existence/' + groupId)
        },
        join: function (groupId, userId) {
            return $http.post('/api/v1/' + userId + '/groups/' + groupId + '/join', {})
        }
    }
}]);