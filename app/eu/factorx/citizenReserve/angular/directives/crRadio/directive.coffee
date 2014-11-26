angular
.module('app.directives')
.directive "crRadio", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngModel: '='
        ngOptions: '='
        ngFreeAllowed: '='
    templateUrl: "$/angular/templates/cr-radio.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        scope.setValue = (v) ->
            scope.ngModel = v

        scope.isValue = (v) ->
            return `scope.ngModel == v`

        scope.$watch 'ngOptions', (n, o) ->
            console.log n

            scope.computedOptions = []
            for element in n
                if typeof(element) == 'object'
                    scope.computedOptions.push element
                else
                    scope.computedOptions.push {value: element, label: element}



        scope.toggle = () ->
            scope.edit = !scope.edit

            if !scope.edit
                if scope.ngOptions.length > 0
                    for o in scope.ngOptions
                        if `o.value == scope.ngModel`
                            return
                    scope.ngModel = scope.ngOptions[0].value