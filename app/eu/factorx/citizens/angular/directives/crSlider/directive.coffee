angular
.module('app.directives')
.directive "crSlider", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngModel: '='
        ngSteps: '='
        ngFreeAllowed: '='
    templateUrl: "$/angular/templates/cr-slider.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        scope.setValue = (v) ->
            scope.ngModel = v.value

        scope.isValue = (v) ->
            return scope.ngModel == v.value

            indexOfNgModel = -1
            for o in scope.computedOptions
                if `o.value == scope.ngModel`
                    break
                else
                    indexOfNgModel++

            indexOfV = -1
            for o in scope.computedOptions
                if `o.value == v.value`
                    break
                else
                    indexOfV++

            if indexOfNgModel == -1 or indexOfV == -1
                return false
            else
                return indexOfV <= indexOfNgModel

        scope.$watch 'ngSteps', (n, o) ->
            console.log n

            scope.computedOptions = []
            for element in n

                if element == null
                    scope.computedOptions.push {value: element, label: element}
                else if typeof(element) == 'object'
                    scope.computedOptions.push element
                else
                    scope.computedOptions.push {value: element, label: element}

