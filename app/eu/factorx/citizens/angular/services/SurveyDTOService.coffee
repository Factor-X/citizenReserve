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
        surveyDTO.answers.push {questionKey: 'Q4000'}

    # add the search answer function into DTO
    @getAnswers = (questionCode) ->
        return _.where(surveyDTO.answers, {questionKey: questionCode})

    return