
app.controller('ProfileController', ['$scope', '$state', 'UserService', 'ImageService', function ($scope, $state, UserService, ImageService) {
    $scope.profile = $scope.$resolve.user;
    $scope.tab = 1;
    $scope.errors = [];
    $scope.detailsModel = {
        id: $scope.profile.id,
        name: $scope.profile.name,
        surname: $scope.profile.surname,
        sex: $scope.profile.details.sex,
        city: $scope.profile.details.city,
        country: $scope.profile.details.country,
        birthday: new Date($scope.profile.details.birthday),
        phone: $scope.profile.details.phone,
        about: $scope.profile.details.about
    };

    $scope.uploadFile = function () {
        ImageService.upload($scope.profile.id, $scope.myFile, true)
    };

    $scope.editProfile = function () {
        $scope.errors = [];
        UserService.saveProfile($scope.detailsModel).then(function (response) {
            var data = response.data;
            if (data.success === true) {
                $state.go('myPage');
            } else {
                $scope.errors = data.errors;
            }
        })
    };

    $scope.setTab = function(newTab){
        $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
        return $scope.tab === tabNum;
    };

    $scope.hasError = function () {
        return $scope.errors.length !== 0;
    }

}]);
