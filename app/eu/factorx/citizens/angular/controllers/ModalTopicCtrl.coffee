angular
.module('app.controllers')
.controller "ModalTopicCtrl", ($scope, surveyDTOService, optionService) ->

    $scope.getOptions = (questionKey) ->
        opt=optionService.getOptions(questionKey)
        console.log "OPTION"
        console.log opt
        return opt

    $scope.getAnswerValue = (questionKey, periodKey) ->
        return surveyDTOService.getAnswerValue(questionKey, periodKey)