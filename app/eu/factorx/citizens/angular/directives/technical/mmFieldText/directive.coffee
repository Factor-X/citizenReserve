angular
.module('app.directives')
.directive "mmFieldText", (directiveService, $timeout) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-field-text.html"
    replace: true
    transclude: true
    compile: () ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

        post: (scope) ->
            directiveService.autoScopeImpl scope


            scope.isValidationDefined = scope.getInfo().validationRegex? || scope.getInfo().validationFct?
            scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon
            scope.fieldType = if (scope.getInfo().fieldType?) then scope.getInfo().fieldType else "text"

            if !scope.getInfo().field?
                scope.getInfo().field = ""

            if !scope.getInfo().isValid?
                scope.getInfo().isValid = !scope.isValidationDefined

            if scope.isValidationDefined
                scope.$watch 'getInfo().field', (n, o) ->
                    if n != o
                        scope.isValid()

            scope.isValid = ->
                if scope.getInfo().disabled == true || scope.getInfo().hidden == true
                    scope.getInfo().isValid = true
                    return
                if not scope.getInfo().field
                    scope.getInfo().field = ""
                ###
                scope.getInfo().isValid = false
                return
                ###

                isValid = true

                if typeof scope.getInfo().field != 'string'
                    scope.getInfo().field += ""

                if scope.getInfo().validationRegex?
                    isValid = scope.getInfo().field.match(scope.getInfo().validationRegex)
                if scope.getInfo().validationFct?
                    isValid = isValid && scope.getInfo().validationFct()
                scope.getInfo().isValid = isValid
                return

            scope.isValid()

            scope.logField = ->
                console.log scope.getInfo()


            scope.errorMessage = ""
            #
            # display a error message before the input
            #
            scope.setErrorMessage = (errorMessage)->
                scope.errorMessage = errorMessage
                if scope.lastTimeOut?
                    $timeout.cancel(scope.lastTimeOut)

                scope.lastTimeOut = $timeout(->
                    scope.errorMessage = ""
                    scope.lastTimeOut = null
                , 2000)
