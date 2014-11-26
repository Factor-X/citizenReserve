angular
.module('app.directives')
.directive "crDoubleRange", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngRangeMin: '='
        ngRangeMax: '='
        ngMin: '='
        ngMax: '='
        ngDisabled: '='
    templateUrl: "$/angular/templates/cr-double-range.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

