app.controller('UserController', ['$rootScope', '$scope', 'PublicMessageService', '$window', function ($rootScope, $scope, PublicMessageService, $window) {
    $scope.user = $scope.$resolve.user;
    $scope.wall = $scope.$resolve.wall;
    $scope.message = "";
    $scope.isFriend = "";
    $scope.isRequestFriend = "";

    $scope.rootUserId = $window.sessionStorage.getItem("userId");

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
    };

    $scope.isYouPage = function () {
        return $scope.rootUserId == $scope.user.id;
    };

    $scope.sendFriendRequest = function () {

    }
}]);