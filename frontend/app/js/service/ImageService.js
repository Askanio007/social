app.service('ImageService', ['$http', 'ENV', function ($http, ENV) {
    return {
        upload: function (rootId, file, isUser) {
            var url = isUser? ENV.API_URL + 'image/user/' + rootId + '/upload' : '/api/v1/image/group/' + rootId + '/upload';
            var fileFormData = new FormData();
            fileFormData.append('file', file);
            var options = {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            };
            return $http.post(url, fileFormData, options).then(function (response) {
                return response.data.success;
            })
        }
    }
}]);
