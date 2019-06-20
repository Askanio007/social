'use strict';

(function() {
    angular.module('app')
        .constant('ENV', {
            API_URL: 'http://localhost:8080/api/v1/',
            BASE_URL: '/'
        });
})();
