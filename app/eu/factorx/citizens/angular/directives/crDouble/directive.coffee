angular
.module('app.directives')
.directive "crDouble", ($filter) ->
    restrict: "E"
    scope:
        ngModel: '='
        ngReadonly: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-double.html"
    replace: true
    link: (scope, elem, attrs, ngModelCtrl) ->

        scope.myModel = 0.001 * parseFloat(scope.ngModel)

        scope.$watch 'myModel', (n, o) ->
            n = n.replace /,/g, '.'
            f = Number(n)
            if f < 0
                f = -f
                scope.myModel = f
            scope.ngModel = f * 1000.0


