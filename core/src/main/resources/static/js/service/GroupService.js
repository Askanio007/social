app.service('GroupService', ['$http', function ($http) {
    return {
        findByUserId: function (rootUserId) {
            return $http.get('/api/v1/' + rootUserId + '/groups').then(function (response) {
                return response.data.data;
            })
        },
        createGroup: function (rootUserId, newGroup) {
            return $http.post('/api/v1/' + rootUserId + '/groups/create', newGroup);
        },
        find: function (groupId) {
            return $http.get('/api/v1/group/' + groupId).then(function (response) {
                return response.data.data;
            })
        },
        isUserInGroup: function (userId, groupId) {
            return $http.get('/api/v1/' + userId + '/groups/existence/' + groupId)
        },
        join: function (groupId, userId) {
            return $http.post('/api/v1/' + userId + '/groups/' + groupId + '/join', {})
        },
        search: function (groupName) {
            return $http.get('/api/v1/group/search?groupName=' + groupName);
        }
    }
}]);
