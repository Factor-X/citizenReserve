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

angular.module('app').run (gettextCatalog,surveyDTOService) ->
    gettextCatalog.setCurrentLanguage('fr')
    gettextCatalog.loadRemote("/translations");
    surveyDTOService.initialize()
    return

angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/household-profile/:topic', {
            templateUrl: '$/angular/views/household-profile.html'
            controller: 'FormCtrl'
            resolve:
                topic: ($route) ->
                    return $route.current.params.topic
        }
    )
    .when('/household-action/:topic', {
            templateUrl: '$/angular/views/household-action.html'
            controller: 'FormCtrl'
            resolve:
                topic: ($route) ->
                    return $route.current.params.topic
        }
    )
    .when('/welcome', {
            templateUrl: '$/angular/views/welcome.html'
            controller: 'WelcomeCtrl'
        }
    )
    .otherwise({ redirectTo: '/welcome' })
    return