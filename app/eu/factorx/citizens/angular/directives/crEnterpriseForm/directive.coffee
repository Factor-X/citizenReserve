angular
.module('app.directives')
.directive "crEnterpriseForm", (directiveService, surveyDTOService, optionService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestions: '='
        ngModel: '='
    templateUrl: "$/angular/templates/cr-enterprise-form.html"
    replace: false
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope


        scope.getOptions = (questionKey) ->
            return optionService.getOptions(questionKey)

        scope.getNumericOptions = (questionKey, min, max, step) ->
            return optionService.getNumericOptions(questionKey, min, max, step)

        scope.getAnswerValue = (questionKey, periodKey) ->
            return surveyDTOService.getAnswerValue(questionKey, periodKey)

        scope.getAccount = () ->
            return surveyDTOService.getAccount()

