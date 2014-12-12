# simple download service
angular
.module('app.services')
.service "surveyDTOService", (downloadService, $flash) ->

    @surveyDTO = {
        account:{
            otherEmailAddresses:[]
        }
        answers: []
    }

    @createPreAccount = (accountType) ->
        @surveyDTO.account.accountType = accountType

    @setLanguage = (lang) ->
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

    @saveSurvey = ->
        downloadService.postJson '/survey/update', @surveyDTO, (result) ->
            if result.success
                $flash.success 'account.save.success'
            else
                $flash.error result.data.message

    @isQuestionCompleted = (questionKey) ->
        answers = @getAnswers(questionKey)
        if answers.length == 0
            return false
        for answer in answers
            for answerValue in answer.answerValues
                if !((!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue == 0))
                    return false
        return true

    @getAveragePotentialPowerReduction = ->
        downloadService.postJson '/reduction/potential', @surveyDTO, (result) ->
            if result.success
                return $filter("number") parseFloat(result.data.potentialReduction.averagePowerReduction), 0
            return
        return


    return
