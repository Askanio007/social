
app.controller('ProfileController', ['$scope', '$state', 'UserService', 'ImageService', function ($scope, $state, UserService, ImageService) {
    $scope.profile = $scope.$resolve.user;
    $scope.profile.details.birthday = new Date($scope.profile.details.birthday);
    $scope.tab = 1;

    $scope.uploadFile = function () {
        ImageService.upload($scope.profile.id, $scope.myFile, true)
    };

    $scope.editProfile = function () {
        UserService.saveProfile($scope.profile).then(function (response) {
            $state.go('myPage');
        })
    };

    $scope.setTab = function(newTab){
        $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
        return $scope.tab === tabNum;
    };

}]);