
app.controller('FriendController', ['$scope', '$window', '$state', 'FriendService',
    function ($scope, $window, $state, FriendService) {
    $scope.requestFriends = $scope.$resolve.requestFriends;
    $scope.friends = $scope.$resolve.friends;
    $scope.tab = 1;
    $scope.rootUserId = $window.sessionStorage.getItem("userId");

    $scope.setTab = function(newTab){
        $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
        return $scope.tab === tabNum;
    };

    $scope.accept = function (friendRequestId) {
        FriendService.acceptRequest($scope.rootUserId, friendRequestId);
        $state.reload();
    };

    $scope.decline = function (friendRequestId) {
        FriendService.declineRequest($scope.rootUserId, friendRequestId);
        $state.reload();
    };

    $scope.goToUser = function (userId) {
        $state.go('id' + userId);
    }
}]);