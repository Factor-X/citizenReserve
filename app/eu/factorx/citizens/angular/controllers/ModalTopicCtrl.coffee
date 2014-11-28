angular
.module('app.controllers')
.controller "ModalTopicCtrl", ($scope, surveyDTOService, optionService) ->

    $scope.getOptions = (questionKey) ->
        return optionService.getOptions(questionKey)

    $scope.getAnswerValue = (questionKey, periodKey) ->
        return surveyDTOService.getAnswerValue(questionKey, periodKey)

    $scope.getAccount = () ->
        return surveyDTOService.getAccount()