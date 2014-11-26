angular
.module('app.directives')
.directive "crRange", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngRangeMin: '='
        ngRangeMax: '='
        ngMin: '='
        ngMax: '='
        ngDisabled: '='
    templateUrl: "$/angular/templates/cr-range.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

