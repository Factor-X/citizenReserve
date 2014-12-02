angular
.module('app.directives')
.directive "crText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMultiline: '='
        ngType: '='
        ngName: '='
        ngValidation: '='
        ngDisabled: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-text.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        if scope.getType()
            $('input', elem).attr('type', scope.getType())

        if scope.getValidation()?
            scope.$watch 'getModel()', ->
                isValid =false
                if scope.getModel()?
                    if scope.getValidation().validation?
                        isValid = scope.getValidation().validation()
                    else
                        isValid = scope.getValidation().pattern.test scope.getModel()
                scope.getValidation().valid = isValid

