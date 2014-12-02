angular
.module('app.directives')
.directive "crMultiText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-multi-text.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        scope.getRange = () ->
            _.range(scope.ngModel.length + 1)