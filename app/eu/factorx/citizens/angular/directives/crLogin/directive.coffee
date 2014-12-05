angular
.module('app.directives')
.directive "crLogin", (surveyDTOService, downloadService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/cr-login.html"
    replace: false
    link: (scope, elem, attrs) ->

        scope.fullname = () ->
            return surveyDTOService.getAccount().firstName + ' ' + surveyDTOService.getAccount().lastName

        scope.isAuthenticated = ->
            return surveyDTOService.isAuthenticated()

        scope.logout = () ->
            downloadService.postJson '/logout', surveyDTOService.surveyDTO, (result) ->
                if result.success
                    $state.go('root.welcome')
                    surveyDTOService.logout()
                    $flash.success 'logout.success'
            return