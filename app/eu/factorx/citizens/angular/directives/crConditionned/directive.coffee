angular
.module('app.directives')
.directive "crConditionned", (conditionService, surveyDTOService) ->
    restrict: "A"
    link: (scope, element, attrs) ->

        result = conditionService.checkCondition(attrs.crConditionned)
        if result
            $(element).show()
        else
            $(element).hide()

        handler = ->
            result = conditionService.checkCondition(attrs.crConditionned)
            if result
                $(element).slideDown()
            else
                $(element).slideUp()
            return

        scope.$watch (-> surveyDTOService.surveyDTO ), handler, true

        return
