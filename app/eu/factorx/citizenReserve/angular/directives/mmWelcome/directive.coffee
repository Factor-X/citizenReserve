angular
.module('app.directives')
.directive "mmWelcome", ($location, $http, modalService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-welcome.html"
    transclude: true
    replace: true
    link: (scope) ->

        scope.open = ->
            console.log "je suis ouvrir test"
            modalService.show modalService.TEST