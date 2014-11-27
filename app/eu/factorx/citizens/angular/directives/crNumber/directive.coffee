angular
.module('app.directives')
.directive "crNumber", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-number.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        ngModel.$parsers.push (inputValue) ->
            return ""  unless inputValue?
            firstParse = inputValue.replace(/[^0-9 . -]/g, "")
            safeParse = firstParse.charAt(0)
            prepParse = firstParse.substring(1, firstParse.length)
            secondParse = safeParse + prepParse.replace(/[^0-9 .]/g, "")
            n = secondParse.indexOf(".")
            transformedInput = undefined
            if n is -1
                transformedInput = secondParse
            else
                safeParse = secondParse.substring(0, n + 1)
                firstParse = (secondParse.substring(n + 1, secondParse.length)).replace(/[^0-9]/g, "")
                n = 2
                if firstParse.length <= n
                    transformedInput = safeParse + firstParse
                else
                    transformedInput = safeParse + firstParse.substring(0, n)
            min = parseInt(attrs.minvalue)
            max = parseInt(attrs.maxvalue)
            if transformedInput isnt inputValue or transformedInput < min or transformedInput > max
                returnValue = undefined
                if transformedInput < min or transformedInput > max
                    returnValue = transformedInput.substring(0, transformedInput.length - 1)
                else
                    returnValue = transformedInput
                ngModel.$setViewValue returnValue
                ngModel.$render()
            returnValue

            return


        ngModel.$formatters.push (value) ->
            return 0 + parseInt(value)

        scope.g = () ->
            return typeof(scope.ngModel)

        return scope

