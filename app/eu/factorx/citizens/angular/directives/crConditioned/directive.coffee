angular
.module('app.directives')
.directive "crConditioned", (conditionService, surveyDTOService) ->
    restrict: "A"
    link: (scope, element, attrs) ->

        result = conditionService.checkCondition(attrs.crConditioned)
        if result
            $(element).show()
        else
            $(element).hide()

        handler = ->
            result = conditionService.checkCondition(attrs.crConditioned)
            if result
                $(element).slideDown()
            else
                $(element).slideUp()
            return

        scope.$watch (-> surveyDTOService.surveyDTO ), handler, true

        return
