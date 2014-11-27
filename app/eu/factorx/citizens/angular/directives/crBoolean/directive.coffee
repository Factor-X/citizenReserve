angular
.module('app.directives')
.directive "crBoolean", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/cr-boolean.html"
    replace: false
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        scope.steps = [
            {value: null, label: null},
            {value: true, label: "YES"},
            {value: false, label: "NO"}
        ]
