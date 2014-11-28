# simple download service
angular
.module('app.services')
.service "surveyDTOService", ($rootScope, $modal) ->
    @surveyDTO = {
        account: {}
        answers: []
    }

    # add the search answer function into DTO
    @getAnswers = (questionCode) ->
        return _.where(@surveyDTO.answers, {questionKey: questionCode})

    @getAccount = () ->
        return @surveyDTO.account

    @getAnswerValue = (questionKey, periodKey) ->
        if !!periodKey
            answer = _.findWhere(@surveyDTO.answers, {questionKey: questionKey, periodKey: periodKey})
        else
            answer = _.findWhere(@surveyDTO.answers, {questionKey: questionKey})

        if not answer
            answer = {
                questionKey: questionKey
                periodKey: periodKey
                answerValues: []
            }
            @surveyDTO.answers.push answer

        if answer.answerValues.length == 0

            answerValue = {
                stringValue: null
                doubleValue: null
                booleanValue: null
            }

            answer.answerValues.push answerValue
        else
            answerValue = answer.answerValues[0]

        return answerValue

    return