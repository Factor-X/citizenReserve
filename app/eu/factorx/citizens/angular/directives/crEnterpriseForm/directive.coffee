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

        scope.getActionAnswers = (questionKey) ->
            return surveyDTOService.getActionAnswers(questionKey)

        scope.addEmptyActionAnswer = (questionKey) ->
            actionAnswers = surveyDTOService.getActionAnswers(questionKey)

            found = null
            for aa in actionAnswers
                if !aa.title and !aa.power and !aa.begin and !aa.duration and !aa.description
                    found = aa

            if !found
                surveyDTOService.addEmptyActionAnswer(questionKey)

            return undefined

        scope.removeActionAnswer = (aa) ->
            surveyDTOService.removeActionAnswer(aa)
            return undefined

        scope.getAccount = () ->
            return surveyDTOService.getAccount()

