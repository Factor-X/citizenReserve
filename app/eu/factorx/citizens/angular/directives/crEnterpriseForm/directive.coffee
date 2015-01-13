angular
.module('app.directives')
.directive "crEnterpriseForm", (directiveService, surveyDTOService, optionService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestions: '='
        ngFreeQuestions: '='
        ngReadOnly: '='
        ngModel: '='
    templateUrl: "$/angular/templates/cr-enterprise-form.html"
    replace: false
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope

        console.log surveyDTOService.surveyDTO
        scope.getActionAnswers = (questionKey) ->
            if scope.ngReadOnly
                return _.filter(surveyDTOService.getActionAnswers(questionKey), scope.isComplete)
            else
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

        scope.isComplete = (aa) ->
            if scope.isFree(aa.questionKey)
                return !!aa.title and !!aa.power && !!aa.begin && !!aa.duration
            else
                return !!aa.power && !!aa.begin && !!aa.duration

        scope.isEmpty = (aa) ->
            if scope.isFree(aa.questionKey)
                return !aa.title && !aa.power && !aa.begin && !aa.duration
            else
                return !aa.power && !aa.begin && !aa.duration

        scope.isFree = (q) ->
            return _.contains(scope.ngFreeQuestions, q)

        scope.removeActionAnswer = (aa) ->
            surveyDTOService.removeActionAnswer(aa)
            return undefined

        scope.getAccount = () ->
            return surveyDTOService.getAccount()

