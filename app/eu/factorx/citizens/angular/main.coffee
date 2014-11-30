#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui-rangeSlider']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute', 'ngLocale', 'gettext']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]


angular.module('app').run (gettextCatalog) ->
    gettextCatalog.setCurrentLanguage('fr')
    gettextCatalog.loadRemote("/translations")


angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/welcome', {
            templateUrl: '$/angular/views/welcome.html'
            controller: 'WelcomeCtrl'
        }
    )
    .when('/household-profile', {
            templateUrl: '$/angular/views/household/profile/household-profile.html'
            controller: 'FormCtrl'
            resolve:
                topic: ($route) ->
                    return $route.current.params.topic
        }
    )
    .when('/household-actions', {
            templateUrl: '$/angular/views/household/actions/household-actions.html'
            controller: 'FormCtrl'
            resolve:
                topic: ($route) ->
                    return $route.current.params.topic
        }
    )
    .when('/controls-demo', {
            templateUrl: '$/angular/views/test/controls-demo.html'
            controller: 'ControlsDemoCtrl'
        }
    )
    .otherwise({ redirectTo: '/welcome' })
    return