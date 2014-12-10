#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui-rangeSlider', 'nvd3']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngLocale', 'gettext', 'ui.router']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]

angular.module('app').run (gettextCatalog) ->
    gettextCatalog.setCurrentLanguage('nl')
    gettextCatalog.loadRemote("/translations")


#
# route test
#
# test if the accountDTO contains a accountType
# if not, try to connection
# if not return to the welcome page
defaultResolve =
    testConnection: ($http, $rootScope, $stateParams, $state, downloadService, surveyDTOService) ->
        # if the current user is null...
        if not surveyDTOService.hasAccountType()
            downloadService.getJson '/authenticated', (result) ->
                console.log "je nai pas daccount type : authentication : " + result.success
                if result.success
                    surveyDTOService.surveyDTO = result.data
                else
                    $state.go 'root', $stateParams

testAuthenticationResolve =
    testConnection: ($http, $rootScope, $state, $stateParams, downloadService, surveyDTOService) ->
        downloadService.getJson '/authenticated', (result) ->
            if result.success
                surveyDTOService.surveyDTO = result.data
                if result.data.account.accountType == 'household'
                    $state.go 'root.householdProfile', $stateParams

changeLanguageResolve =
    changeLanguage: ($stateParams, gettextCatalog) ->
        gettextCatalog.setCurrentLanguage($stateParams.lang)

angular
.module('app.controllers')
.config ($stateProvider, $urlRouterProvider) ->
    langPrefix = '/{lang}'

    $stateProvider
    .state 'root',
        url: langPrefix
        template: '<div ui-view></div>'
        controller: ($scope, $state, $stateParams) ->
            l = $stateParams.lang
            if l == 'fr' or l == 'nl' or l == 'en'
                if $state.current.name == 'root'
                    $state.go 'root.welcome'
            else
                $state.go 'root.welcome', {lang: 'fr'}

    .state 'root.welcome',
        url: '/welcome'
        templateUrl: '$/angular/views/welcome.html'
        controller: 'WelcomeCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), testAuthenticationResolve)
    .state 'root.householdProfile',
        url: '/household-profile'
        templateUrl: '$/angular/views/household/profile/household-profile.html'
        controller: 'FormCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
    .state 'root.householdActions',
        url: '/household-actions'
        templateUrl: '$/angular/views/household/actions/household-actions.html'
        controller: 'FormCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
    .state 'root.householdResults',
        url: '/household-results'
        templateUrl: '$/angular/views/household/results/household-results.html'
        controller: 'ResultsCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
    .state 'root.householdAccount',
        url: '/household-account'
        templateUrl: '$/angular/views/household/account/household-account.html'
        controller: 'RegistrationCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
    .state 'root.controlsDemo',
        url: '/controls-demo'
        templateUrl: '$/angular/views/test/controls-demo.html'
        controller: 'ControlsDemoCtrl'
        resolve: {}
    .state 'root.superAdminLogin',
        url: '/admin'
        templateUrl: '$/angular/views/admin/login.html'
        controller: 'SuperAdminLoginCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), testAuthenticationResolve)
    .state 'root.superAdminMain',
        url: '/admin/main'
        templateUrl: '$/angular/views/admin/main.html'
        controller: 'SuperAdminMainCtrl'
        resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)

    $urlRouterProvider.otherwise('/fr/welcome');


Messenger.options = {
    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center cr-messenger',
    theme: 'block'
}

#