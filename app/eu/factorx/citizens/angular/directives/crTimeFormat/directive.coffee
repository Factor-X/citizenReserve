angular
.module('app.directives')
.directive 'crTimeFormat', () ->
    restrict: 'A'
    require: 'ngModel'
    link: (scope, element, attrs, controller) ->
        parser = (viewValue) ->
            result = null
            if viewValue?

                patt = /^([0-9]{1,2}):([0-9]{1,2})$/
                if patt.test(viewValue)
                    matches = viewValue.match(patt)
                    console.log matches
                    h = parseInt(matches[1])
                    m = parseInt(matches[2])

                    if h >= 0 and h <= 23 and m >= 0 and m <= 59
                        if h < 10
                            h = '0' + h
                        if m < 10
                            m = '0' + m
                        result = h + ':' + m
                        controller.$setValidity(attrs.ngModel, true)
                    else
                        controller.$setValidity(attrs.ngModel, false)
                else
                    controller.$setValidity(attrs.ngModel, false)

            console.log "result", result
            return result

        formatter = (modelValue) ->
            result = ''
            if modelValue?
                patt = /^([0-9]{1,2}):([0-9]{1,2})$/
                if patt.test(modelValue)
                    matches = modelValue.match(patt)
                    console.log matches
                    h = parseInt(matches[1])
                    m = parseInt(matches[2])

                    if h >= 0 and h <= 23 and m >= 0 and m <= 59
                        if h < 10
                            h = '0' + h
                        if m < 10
                            m = '0' + m
                        result = h + ':' + m

            return result

        controller.$parsers.unshift(parser)
        controller.$formatters.push(formatter)

        element.bind 'blur', () ->
            viewValue = controller.$modelValue
            angular.forEach controller.$formatters, (formatter) ->
                viewValue = formatter(viewValue);
            controller.$viewValue = viewValue
            controller.$render()

