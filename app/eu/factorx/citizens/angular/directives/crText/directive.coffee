angular
.module('app.directives')
.directive "crText", (directiveService, $timeout) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMultiline: '='
        ngType: '='
        ngName: '='
        ngValidation: '='
        ngDisabled: '='
        ngReturn: '&'
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-text.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        $timeout () ->
            $('input', elem).keydown (e) ->
                if e.keyCode == 13
                    if scope.ngReturn
                        scope.ngReturn()

            if scope.getType()
                $('input', elem).attr('type', scope.getType())
        , 0

        if scope.getValidation()?
            scope.$watch 'getModel()', ->
                isValid =false
                if scope.getModel()?
                    if scope.getValidation().validation?
                        isValid = scope.getValidation().validation()
                    else
                        isValid = scope.getValidation().pattern.test scope.getModel()
                scope.getValidation().valid = isValid

