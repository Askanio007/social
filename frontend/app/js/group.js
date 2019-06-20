app
    .controller('GroupsController', ['$scope', 'GroupService', function ($scope, GroupService) {
        $scope.groups = $scope.$resolve.groups;
        $scope.tab = 1;

        $scope.setTab = function(newTab){
            $scope.tab = newTab;
        };

        $scope.isSet = function(tabNum){
            return $scope.tab === tabNum;
        };

        $scope.search = function() {
            GroupService.search($scope.searchText).then(function (response) {
                $scope.foundGroups = response.data;
            });
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

        $scope.isAdmin = function () {
            return $scope.rootUserId == $scope.group.adminId;
        }
    }])

    .controller('CreateGroupController', ['$scope', '$window', '$state', 'GroupService',
        function ($scope, $window, $state, GroupService) {
            $scope.newGroup = {
                name: "",
                description: ""
            };
            $scope.errors = [];
            $scope.rootUserId = $window.sessionStorage.getItem("userId");

            $scope.create = function () {
                $scope.errors = [];
                GroupService.createGroup($scope.rootUserId, $scope.newGroup).then(function (response) {
                    var data = response.data;
                    if (data.success === true) {
                        $state.go("group" + data.data.id);
                    } else {
                        $scope.errors = data.errors;
                    }
                });
            };

            $scope.hasError = function () {
                return $scope.errors.length !== 0;
            }
    }])
    .controller('EditGroupController', ['$scope', '$window', '$state', 'GroupService', 'ImageService',
        function ($scope, $window, $state, GroupService, ImageService) {
            $scope.group = $scope.$resolve.group;
            $scope.rootUserId = $window.sessionStorage.getItem("userId");
            $scope.errors = [];

            $scope.edit = function () {
                $scope.errors = [];
                GroupService.createGroup($scope.rootUserId, $scope.group).then(function (response) {
                    var data = response.data;
                    if (data.success === true) {
                        $state.go("group" + data.data.id);
                    } else {
                        $scope.errors = data.errors;
                    }
                });
            };

            $scope.uploadFile = function () {
                ImageService.upload($scope.group.id, $scope.myFile, false)
            };

            $scope.tab = 1;

            $scope.setTab = function(newTab){
                $scope.tab = newTab;
            };

            $scope.isSet = function(tabNum){
                return $scope.tab === tabNum;
            };

            $scope.hasError = function () {
                return $scope.errors.length !== 0;
            }
        }]
    );

