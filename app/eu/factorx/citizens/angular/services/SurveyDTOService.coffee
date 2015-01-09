# simple download service
angular
.module('app.services')
.service "surveyDTOService", (downloadService, $flash, $filter) ->
    @surveyDTO =
        account:
            otherEmailAddresses: []
        answers: []
        actionAnswers: []

    @createPreAccount = (accountType) ->
        @surveyDTO.account.accountType = accountType

    @setLanguage = (lang) ->
        @surveyDTO.account.languageAbrv = lang

    @login = (surveyDTO) ->
        @surveyDTO = surveyDTO

    @isAuthenticated = () ->
        return (@surveyDTO.account.id? && @surveyDTO.account.id != null)

    @isHousehold = () ->
        return (@surveyDTO.account.accountType == "household")

    @logout = () ->
        @surveyDTO =
            account:
                otherEmailAddresses: []
            answers: []

    @setAccount = (account) ->
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

    @getActionAnswers = (questionKey) ->
        actionAnswers = _.where(@surveyDTO.actionAnswers, {questionKey: questionKey})
        if actionAnswers.length == 0
            @addEmptyActionAnswer(questionKey)
            actionAnswers = _.where(@surveyDTO.actionAnswers, {questionKey: questionKey})
        return actionAnswers

    @addEmptyActionAnswer = (questionKey) ->
        @surveyDTO.actionAnswers.push {
            questionKey: questionKey
            title: null
            power: null
            begin: null
            duration: null
            description: null
        }
        return undefined

    @removeActionAnswer = (actionAnswer) ->
        @surveyDTO.actionAnswers = _.reject(@surveyDTO.actionAnswers, actionAnswer)

    @isQuestionCompleted = (questionKey) ->
        answers = @getAnswers(questionKey)
        if answers.length == 0
            return false
        for answer in answers
            for answerValue in answer.answerValues
                if !((!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue == 0))
                    return false
        return true

    @saveSurvey = ->
        downloadService.postJson '/survey/update', @surveyDTO, (result) ->
            if result.success
                $flash.success 'account.save.success'
            else
                console.error("@saveSurvey() thrown error! Response = ", result)
            return

    @getPotentialReductionDTO = (cb) ->
        downloadService.postJson '/reduction/potential', @surveyDTO, (result) ->
            if result.success
                # ReductionDTO
                cb(result.data)
            else
                console.error("@getPotentialReductionDTO() thrown error! Response = ", result)
            return

    @getEffectiveReductionDTO = (cb) ->
        downloadService.postJson '/reduction/effective', @surveyDTO, (result) ->
            if result.success
                # EffectiveReductionDTO
                cb(result.data)
            else
                console.error("@getEffectiveReductionDTO() thrown error! Response = ", result)
            return

    @getEnterpriseEffectiveReductionDTO = (cb) ->
        downloadService.postJson '/reduction/effective-enterprise', @surveyDTO, (result) ->
            if result.success
                # EffectiveReductionDTO
                cb(result.data)
            else
                console.error("@getEnterpriseEffectiveReductionDTO() thrown error! Response = ", result)
            return

    return
