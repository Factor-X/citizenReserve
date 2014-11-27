angular
.module('app.directives')
.directive "crSlider", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngModel: '='
        ngSteps: '='
        ngFreeAllowed: '='

        ngMin: '='
        ngMax: '='
        ngStep: '='
    templateUrl: "$/angular/templates/cr-slider.html"
    replace: false
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


        scope.$watch ['ngSteps', 'ngMax', 'ngMin', 'ngStep', 'max', 'min', 'step'], (n, o) ->
            scope.computedOptions = []

            if scope.ngSteps

                for element in scope.ngSteps

                    if element == null
                        scope.computedOptions.push {value: element, label: element}
                    else if typeof(element) == 'object'
                        scope.computedOptions.push element
                    else
                        scope.computedOptions.push {value: element, label: element}

            else if scope.getMin() != undefined and scope.getMax() != undefined and scope.getStep() != undefined
                scope.computedOptions.push {value: null, label: null}
                min = parseFloat(scope.getMin())
                max = parseFloat(scope.getMax()) + parseFloat(scope.getStep())
                step = parseFloat(scope.getStep())
                for element in _.range(min, max, step)
                    scope.computedOptions.push {value: element, label: element}

        $(elem).mouseenter () ->
            $scroller = $(".cr-slider-not-null-box", this)
            $scroller.stop(true)

        $(elem).mouseleave () ->
            $scroller = $(".cr-slider-not-null-box", this)
            w = $scroller[0].scrollWidth
            idx = -1
            for o in scope.computedOptions
                if `o.value == scope.ngModel`
                    break
                idx++
            cnt = scope.computedOptions.length - 1
            offset = 1.0 * idx / cnt

            target = w * offset - $scroller.width() / 2

            if target<0
                target = 0
            if target >  w - $scroller.width() / 2 - 50
                target =  w - $scroller.width() / 2 - 50

            if Math.abs(target - $scroller.scrollLeft()) > 1
                $scroller.delay(1000).animate({scrollLeft: target}, { easing: 'easeInOutBack'})

