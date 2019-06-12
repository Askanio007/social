
app.controller('FriendController', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $scope.requestFriends = $scope.$resolve.requestFriends;
    $scope.friends = $scope.$resolve.friends;
    $scope.tab = 'friends';

    $scope.changeTab = function (tab) {
        $scope.tab = tab;
        $scope.reload();
    }
}]);