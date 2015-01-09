angular
.module('app.directives')
.directive 'crTimeFormat', () ->
    restrict: 'A'
    require: 'ngModel'
    link: (scope, element, attrs, controller) ->
        parser = (viewValue) ->
            result = null
            if viewValue?

                patt = /^([0-9]{0,2})[:uh]([0-9]{0,2})$/
                patt2 = /^([0-9]{1,3})m$/
                if patt.test(viewValue)
                    matches = viewValue.match(patt)
                    if matches[1] == ''
                        matches[1] = '0'
                    h = parseInt(matches[1])
                    if matches[2] == ''
                        matches[2] = '0'
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
                else if patt2.test(viewValue)
                    matches = viewValue.match(patt2)
                    m = parseInt(matches[1])

                    h = Math.floor(m / 60)
                    m = m % 60

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

            return result

        formatter = (modelValue) ->
            result = ''
            if modelValue?
                patt = /^([0-9]{0,2})[:hu]([0-9]{0,2})$/
                if patt.test(modelValue)
                    matches = modelValue.match(patt)
                    if matches[1] == ''
                        matches[1] = '0'
                    h = parseInt(matches[1])
                    if matches[2] == ''
                        matches[2] = '0'
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

