app.controller('DialogController', ['$scope', '$state', '$window', '$stateParams', 'PrivateMessageService',
    function ($scope, $state, $window, $stateParams, PrivateMessageService) {
        $scope.messages = $scope.$resolve.dialog;
        $scope.dialogId = $stateParams.dialogId;
        $scope.rootUserId = $window.sessionStorage.getItem("userId");
        $scope.textMessage = "";

        var ws = new WebSocket('ws://localhost:8080/message/websocket');

        ws.onmessage = function(e) {
            console.log(e.data)
        };

        $scope.sendMessage = function () {
            PrivateMessageService.save($scope.rootUserId, $scope.textMessage, $scope.dialogId).then(function (response) {
                var data = response.data;
                if (data.success == true) {
                    $scope.textMessage = "";
                    $state.reload()
                }
            });
        };

}]);

app.controller('DialogsController', ['$scope', '$window', function ($scope, $window) {
    $scope.dialogs = $scope.$resolve.dialogs;
    $scope.rootUserId = $window.sessionStorage.getItem("userId");
}]);
