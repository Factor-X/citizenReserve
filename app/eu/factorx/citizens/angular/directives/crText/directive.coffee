angular
.module('app.directives')
.directive "crText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMultiline: '='
        ngType: '='
        ngName: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-text.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        if scope.getType()
            $('input', elem).attr('type', scope.getType())

        $watch 'ngType'