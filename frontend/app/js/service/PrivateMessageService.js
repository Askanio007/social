app.service('PrivateMessageService', ['$http', 'ENV', function ($http, ENV) {
    return {
        save: function (senderId, message, dialogId) {
            var params = {
                senderId: senderId,
                message: message,
                dialogId: dialogId
            };
            return $http.post(ENV.API_URL + 'dialog/message/save', params);
        }
    }
}]);
