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


#
# route test
#
# test if the accountDTO contains a accountType
# if not, try to connection
# if not return to the welcome page
defaultResolve =
    testConnection: ($http, $rootScope, $location, downloadService,surveyDTOService) ->
        # if the current user is null...
        if not surveyDTOService.hasAccountType()
            console.log "je nai pas daccount type : "+surveyDTOService.surveyDTO
            downloadService.getJson '/authenticated',(result) ->
                if result.success
                    surveyDTOService.surveyDTO = result.data
                else
                    $location.path '/welcome'

testAuthenticationResolve =
    testConnection: ($http, $rootScope, $location, downloadService,surveyDTOService) ->
        downloadService.getJson '/authenticated',(result) ->
            if result.success
                surveyDTOService.surveyDTO = result.data
                if result.data.account.accountType == 'household'
                    $location.path '/household-profile'
                # TODO to complete

angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/welcome', {
            templateUrl: '$/angular/views/welcome.html'
            controller: 'WelcomeCtrl'
            resolve: angular.extend({
            }, testAuthenticationResolve)
        }
    )
    .when('/household-profile', {
            templateUrl: '$/angular/views/household/profile/household-profile.html'
            controller: 'FormCtrl'
            resolve: angular.extend({
                topic: ($route) ->
                    return $route.current.params.topic
            }, defaultResolve)

        }
    )
    .when('/household-actions', {
            templateUrl: '$/angular/views/household/actions/household-actions.html'
            controller: 'FormCtrl'
            resolve: angular.extend({
                topic: ($route) ->
                    return $route.current.params.topic
            }, defaultResolve)
        }
    )
    .when('/household-account', {
            templateUrl: '$/angular/views/household/account/household-account.html'
            controller: 'RegistrationCtrl'
            resolve: angular.extend({
                topic: ($route) ->
                    return $route.current.params.topic
            }, defaultResolve)
        }
    )
    .when('/controls-demo', {
            templateUrl: '$/angular/views/test/controls-demo.html'
            controller: 'ControlsDemoCtrl'
        }
    )
    .otherwise({ redirectTo: '/welcome' })
    return