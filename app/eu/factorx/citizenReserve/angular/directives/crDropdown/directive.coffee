angular
.module('app.directives')
.directive "crDropdown", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngOptions: '='
        ngModel: '='
        ngDisabled: '='
        ngOpened: '='
    templateUrl: "$/angular/templates/cr-dropdown.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.$select = (o) ->
            scope.ngModel = o

        scope.$opened = () ->
            console.log 'ok'
            return !!scope.getOpened()
