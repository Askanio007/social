
app.controller('EventController', ['$scope', function ($scope) {
    $scope.events = $scope.$resolve.events;

}]);