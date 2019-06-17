
app.controller('ProfileController', ['$scope', '$state', 'UserService', function ($scope, $state, UserService) {
    $scope.profile = $scope.$resolve.user;
    $scope.profile.details.birthday = new Date($scope.profile.details.birthday);
    $scope.editProfile = function () {
        UserService.saveProfile($scope.profile).then(function (response) {
            $state.go('myPage');
        })
    }

}]);