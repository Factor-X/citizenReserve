angular
.module('app.directives')
.directive "crSection", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
    templateUrl: "$/angular/templates/cr-section.html"
    replace: false
    transclude: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

