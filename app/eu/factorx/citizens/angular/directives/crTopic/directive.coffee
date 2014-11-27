angular
.module('app.directives')
.directive "crTopic", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
    templateUrl: "$/angular/templates/cr-topic.html"
    replace: true
    transclude: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

