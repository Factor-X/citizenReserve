angular
.module('app.directives')
.directive "crDouble", (directiveService, $timeout) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngReadonly: '='
        ngMin: '='
        ngMax: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-double.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        ngModel.$parsers.push (inputValue) ->
            v = inputValue.replace(/,/, '.')

            patt = /[+-]?(?=\d*[.eE])(?=\.?\d)\d*\.?\d*(?:[eE][+-]?\d+)?/

            if not patt.test(v)
                ngModel.$setValidity('format', false)
                v = undefined
            else
                v = parseFloat(v)
                if (!!scope.ngMin and v < scope.ngMin) or (!!scope.ngMax and v > scope.ngMax)
                    v = undefined
                    ngModel.$setValidity('format', false)
                else
                    ngModel.$setValidity('format', true)

            return v

        ngModel.$formatters.push (value) ->
            return ('' + value).replace(/./, ',')


