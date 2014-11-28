# simple download service
angular
.module('app.services')
.service "surveyDTOService", ($rootScope, $modal) ->
    surveyDTO = null

    @initialize = ->
        surveyDTO = {
            account: null
            answers: []
        }

    # add the search answer function into DTO
    @getAnswers = (questionCode) ->
        return _.where(surveyDTO.answers, {questionKey: questionCode})

    @getAnswerValue = (questionKey, periodKey) ->

        console.log surveyDTO

        answer= _.findWhere(surveyDTO.answers, {questionKey: questionKey, periodKey: periodKey})

        if not answer
            answer = {
                questionKey: questionKey
                periodKey: periodKey
                answerValues: []
            }
        console.log answer
        if answer.answerValues.length == 0
            answer.answerValues.push {
                stringValue: null
                doubleValue: null
                booleanValue: null
            }

        return answer.answerValues[0]

    return