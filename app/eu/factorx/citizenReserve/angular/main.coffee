#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap', 'picardy.fontawesome']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]

angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/test', {
            templateUrl: '$/angular/views/form.html'
            controller: 'FormCtrl'
        }
    )
    .otherwise({ redirectTo: '/' })
    return