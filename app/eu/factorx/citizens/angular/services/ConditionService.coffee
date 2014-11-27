# simple download service
angular
.module('app.services')
.service "conditionService", ($sce) ->
    @checkCondition = (questionKey, surveyDTO) ->

    return
#    getAnswers = (questionKey, surveyDTO) ->
#        surveyDTO
#

