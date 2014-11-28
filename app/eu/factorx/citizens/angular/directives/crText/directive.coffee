angular
.module('app.directives')
.directive "crText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMultiline: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-text.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope


