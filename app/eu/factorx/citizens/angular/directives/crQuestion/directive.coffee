angular
.module('app.directives')
.directive "crQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngInline: '='
    templateUrl: "$/angular/templates/cr-question.html"
    replace: false
    transclude: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

