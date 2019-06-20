
app.controller('FriendController', ['$scope', '$window', '$state', 'FriendService', 'UserService',
    function ($scope, $window, $state, FriendService, UserService) {
        $scope.requestFriends = $scope.$resolve.requestFriends;
        $scope.friends = $scope.$resolve.friends;
        $scope.tab = 1;
        $scope.rootUserId = $window.sessionStorage.getItem("userId");
        $scope.searchText = '';

        $scope.setTab = function(newTab){
            $scope.tab = newTab;
        };

        $scope.isSet = function(tabNum){
            return $scope.tab === tabNum;
        };

        $scope.accept = function (friendRequestId) {
            FriendService.acceptRequest($scope.rootUserId, friendRequestId).then(function (response) {
                if (response.data.success == true) {
                    $state.reload();
                }
            });
        };

        $scope.decline = function (friendRequestId) {
            FriendService.declineRequest($scope.rootUserId, friendRequestId).then(function (response) {
                if (response.data.success == true) {
                    $state.reload();
                }
            });
        };

        $scope.remove = function (friendRequestId) {
            FriendService.remove($scope.rootUserId, friendRequestId).then(function (response) {
                if (response.data.success == true) {
                    $state.reload();
                }
            });
        };

        $scope.search = function() {
            UserService.searchUser($scope.rootUserId, $scope.searchText).then(function (response) {
                $scope.foundUser = response.data;
            });
        };

        $scope.sendFriendRequest = function (userId) {
            var params = {
                fromUserId: $scope.rootUserId,
                toUserId: userId
            };
            UserService.sendFriendRequest(params).then(function (response) {
                $scope.isRequestFriend = true;
            });
        }
}]);
