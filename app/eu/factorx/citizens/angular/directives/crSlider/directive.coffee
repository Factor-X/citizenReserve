angular
.module('app.directives')
.directive "crSlider", (directiveService, $filter) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngSteps: '='
        ngFreeAllowed: '='

        ngMin: '='
        ngMax: '='
        ngStep: '='

        ngVisible: '='
        ngVertical: '='

        ngFilter: '='

        ngTheme: '='

    templateUrl: "$/angular/templates/cr-slider.html"
    replace: false
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        scope.labelFilter = $filter('translate')
        scope.opened = false
        scope.computedOptionsFiltered = [
            {label: null, value: null}
        ]

        scope.setValue = (v) ->
            if v.value == null
                scope.ngModel = null
                scope.expanded = false
                scope.updateComputedOptionsFiltered()
            else if scope.getVertical() and scope.computedOptions.length > 3 and !scope.expanded
                scope.expanded = true
                scope.updateComputedOptionsFiltered()
            else
                scope.ngModel = v.value
                scope.expanded = false

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

        scope.isVisible = () ->
            v = scope.ngVisible
            if (v == undefined)
                return true
            else if (v == null)
                return false
            else
                return !!v


        scope.$watch ['ngFilter', 'filter'], () ->
            n = scope.getFilter()
            if n
                scope.labelFilter = $filter(n);
            else
                scope.labelFilter = $filter('translate')

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

            if scope.getVertical()
                scope.updateComputedOptionsFiltered()

        scope.$watch 'ngModel', () ->
            scope.updateComputedOptionsFiltered()

        scope.updateComputedOptionsFiltered = () ->
            if scope.expanded
                scope.computedOptionsFiltered = scope.computedOptions
            else
                scope.computedOptionsFiltered = _.where(scope.computedOptions, {value: scope.ngModel})
                if _.where(scope.computedOptionsFiltered, {value: null}).length == 0
                    scope.computedOptionsFiltered.unshift {label: null, value: null}
                scope.computedOptionsFiltered.push {value: "$SELECT_A_VALUE$", label: null}


        scope.toggleExpanded = () ->
            scope.expanded = !scope.expanded
            scope.updateComputedOptionsFiltered()

        $(elem).mouseenter () ->
            $scroller = $(".cr-slider-not-null-box-holder", this)
            $scroller.stop(true)

        $(elem).mouseleave () ->
            $scroller = $(".cr-slider-not-null-box-holder", this)
            w = $scroller[0].scrollWidth
            idx = -1
            for o in scope.computedOptions
                if `o.value == scope.ngModel`
                    break
                idx++
            cnt = scope.computedOptions.length - 1
            offset = 1.0 * idx / cnt

            target = w * offset - $scroller.width() / 2

            if target < 0
                target = 0
            if target > w - $scroller.width() / 2 - 50
                target = w - $scroller.width() / 2 - 50

            if Math.abs(target - $scroller.scrollLeft()) > 1
                $scroller.delay(1000).animate({scrollLeft: target}, { easing: 'easeInOutBack'})

