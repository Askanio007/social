app.controller('UserController', ['$scope', 'PublicMessageService', '$window', 'UserService',
    function ($scope, PublicMessageService, $window, UserService) {
    $scope.user = $scope.$resolve.user;
    $scope.wall = $scope.$resolve.wall;
    $scope.rootUserId = $window.sessionStorage.getItem("userId");
    $scope.message = "";
    UserService.isFriend($scope.rootUserId, $scope.user.id).then(function (response) {
        $scope.isYouFriend = response.data;
    });
    UserService.isFriendRequest($scope.rootUserId, $scope.user.id).then(function (response) {
        $scope.isFriendRequestFromYou = response.data;
    });

    $scope.sendMessage = function () {
        var params = {
            message: $scope.message,
            recipientId: $scope.user.id,
            recipientUser: true,
            senderId: $scope.rootUserId
        };
        PublicMessageService.sendMessage(params).then(function (response) {
            $scope.wall.push(response.data);
            $state.reload();
        });
    };

    $scope.isYouPage = function () {
        return $scope.rootUserId === $scope.user.id;
    };

    $scope.isFriendRequest = function () {
        return $scope.isFriendRequestFromYou;
    };

    $scope.isFriend = function () {
        return $scope.isYouFriend;
    };

    $scope.sendFriendRequest = function () {
        var params = {
            fromUserId: $scope.rootUserId,
            toUserId: $scope.user.id
        };
        UserService.sendFriendRequest(params).then(function (response) {
            $scope.isRequestFriend = true;
            $state.reload();
        });
    }
}]);