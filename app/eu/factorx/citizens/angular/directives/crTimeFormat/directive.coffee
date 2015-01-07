angular
.module('app.directives')
.directive 'crTimeFormat', () ->
    restrict: 'A'
    require: 'ngModel'
    link: (scope, element, attrs, controller) ->
        if !attrs.crTimeFormat
            throw 'cr-time-format must specify a time format'

        formatter = (viewValue) ->
            result = null
            if viewValue?
                momentValue = moment(viewValue)
                if momentValue.isValid()
                    controller.$setValidity(attrs.ngModel, true)
                    result = momentValue.format()
                else
                    controller.$setValidity(attrs.ngModel, false)
            console.log "formatter"
            console.log result
            return result

        parser = (modelValue) ->
            result = modelValue
            if modelValue && moment(modelValue).isValid()
                result = moment(modelValue).format(attrs.crTimeFormat)


            console.log "parser"
            console.log result
            return result

        controller.$parsers.unshift(parser)
        controller.$formatters.push(formatter)
