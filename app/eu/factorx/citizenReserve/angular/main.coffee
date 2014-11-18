#
# Modules
#

angular.module 'app.directives', []

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute', 'ngTable',
                                   'angular-flash.service', 'angular-flash.flash-alert-directive', 'angucomplete',
                                   'ui.bootstrap', 'ui.bootstrap.datetimepicker']

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