app.service('GroupService', ['$http', 'ENV', function ($http, ENV) {
    return {
        findByUserId: function (rootUserId) {
            return $http.get(ENV.API_URL + rootUserId + '/groups').then(function (response) {
                return response.data.data;
            })
        },
        createGroup: function (rootUserId, newGroup) {
            return $http.post(ENV.API_URL + rootUserId + '/groups/create', newGroup);
        },
        find: function (groupId) {
            return $http.get(ENV.API_URL + 'group/' + groupId).then(function (response) {
                return response.data.data;
            })
        },
        isUserInGroup: function (userId, groupId) {
            return $http.get(ENV.API_URL + userId + '/groups/existence/' + groupId)
        },
        join: function (groupId, userId) {
            return $http.post(ENV.API_URL + userId + '/groups/' + groupId + '/join', {})
        },
        search: function (groupName) {
            return $http.get(ENV.API_URL + 'group/search?groupName=' + groupName);
        },
        exit: function (groupId, userId) {
            return $http.post(ENV.API_URL + userId + '/groups/' + groupId + '/exit', {})
        }
    }
}]);
