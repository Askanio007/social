app.controller('UserController', ['$rootScope', '$scope', 'PublicMessageService', function ($rootScope, $scope, PublicMessageService) {
    $scope.user = $scope.$resolve.user;
    $scope.wall = $scope.$resolve.wall;
    $scope.message = "";
    console.log($scope.wall);
    $scope.sendMessage = function () {
        var params = {
            message: $scope.message,
            recipientId: $scope.user.id,
            recipientUser: true,
            senderId: $rootScope.userId
        };
        PublicMessageService.sendMessage(params).then(function (response) {
            $scope.wall.push(response.data);
            $state.reload();
        });
    }
}]);