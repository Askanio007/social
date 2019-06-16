app
    .controller('GroupsController', ['$scope', function ($scope) {
        $scope.groups = $scope.$resolve.groups;
        $scope.tab = 1;

        $scope.setTab = function(newTab){
            $scope.tab = newTab;
        };

        $scope.isSet = function(tabNum){
            return $scope.tab === tabNum;
        };
    }])

    .controller('GroupController', ['$scope', '$window', '$state', 'PublicMessageService', 'GroupService',
        function ($scope, $window, $state, PublicMessageService, GroupService) {
        $scope.group = $scope.$resolve.group;
        $scope.wall = $scope.$resolve.wall;
        $scope.rootUserId = $window.sessionStorage.getItem("userId");
        GroupService.isUserInGroup($scope.rootUserId, $scope.group.id).then(function (response) {
            $scope.isYouGroup = response.data;
        });

        $scope.message = "";

        $scope.sendMessage = function () {
            var params = {
                message: $scope.message,
                recipientId: $scope.group.id,
                recipientUser: false,
                senderId: $scope.rootUserId
            };
            PublicMessageService.sendMessage(params).then(function (response) {
                $scope.wall.push(response.data);
                $state.reload();
            });
        };

        $scope.join = function () {
            GroupService.join($scope.group.id, $scope.rootUserId).then(function (response) {
                $scope.isYouGroup = true;
                $state.reload();
            });
        };

        $scope.isYou = function () {
            return $scope.isYouGroup;
        }
    }])

    .controller('CreateGroupController', ['$scope', '$window', '$state', 'GroupService',
        function ($scope, $window, $state, GroupService) {
        $scope.name = "";
        $scope.description = "";
        $scope.rootUserId = $window.sessionStorage.getItem("userId");

        $scope.create = function () {
            GroupService.createGroup($scope.rootUserId, $scope.name, $scope.description).then(function (response) {
                $state.go("group" + response.data.id);
            });
        }
    }]);

