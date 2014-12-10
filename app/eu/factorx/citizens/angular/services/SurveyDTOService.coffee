# simple download service
angular
.module('app.services')
.service "surveyDTOService", ($rootScope, $modal) ->

    @surveyDTO = {
        account:{
            otherEmailAddresses:[]
        }
        answers: []
    }

    @createPreAccount = (accountType, lang) ->
        @surveyDTO.account.accountType = accountType
        @surveyDTO.account.languageAbrv = lang

    @login = (surveyDTO) ->
        @surveyDTO = surveyDTO

    @isAuthenticated = () ->
        return (@surveyDTO.account.id? && @surveyDTO.account.id != null)

    @logout = () ->
        @surveyDTO = {
            account:{
                otherEmailAddresses:[]
            }
            answers: []
        }

    @setAccount =(account) ->
        @surveyDTO.account.id = account.id

    @hasAccountType = ()->
        result = (@surveyDTO.account.accountType?)
        return result

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

    @isQuestionAnswered = (questionKey) ->
        answers = @getAnswers(questionKey)
        if answers.length == 0
            return false
        for answer in answers
            for answerValue in answer.answerValues
                if !((!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue == 0))
                    return false
        return true

    return
