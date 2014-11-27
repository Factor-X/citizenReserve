# simple download service
angular
.module('app.services')
.service "directiveService", ($sce) ->
    @checkCondition = (questionKey, surveyDTO) ->

#    getAnswers = (questionKey, surveyDTO) ->
#        surveyDTO
#

