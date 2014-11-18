angular
.module('app.directives')
.directive "mmFieldAutoCompletion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-field-auto-completion.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.result = null

        scope.$watch 'result', ->
            if scope.result?
                scope.getInfo().field = scope.result.originalObject.key
            else
                scope.getInfo().field = null
            scope.isValid()

        scope.isValid = ->
            if scope.getInfo().disabled == true || scope.getInfo().hidden == true
                scope.getInfo().isValid = true
                return
            isValid = true
            if !scope.getInfo().field?
                isValid = false
            scope.getInfo().isValid = isValid
            return

        scope.isValid()

        scope.logField = ->
            console.log scope.getInfo()
