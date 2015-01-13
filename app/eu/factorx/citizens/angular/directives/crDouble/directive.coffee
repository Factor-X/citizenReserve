angular
.module('app.directives')
.directive "crDouble", () ->
    restrict: "E"
    scope:
        ngModel: '='
        ngReadonly: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-double.html"
    replace: true
    link: (scope, elem, attrs, ctrl) ->
        scope.$watch 'ngModel', (newValue, oldValue) ->
            if typeof(newValue) == 'string'
                arr = String(newValue).split("")
                if arr.length == 0
                    return
                if arr.length == 1 and arr[0] == '.'
                    return
                if isNaN(newValue)
                    scope.ngModel = oldValue
                else
                    scope.ngModel = parseFloat(newValue)

