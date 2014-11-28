angular
.module('app.controllers')
.controller "ModalTopicCtrl", ($scope, surveyDTOService, optionService,$modalInstance) ->

    $scope.getOptions = (questionKey) ->
        return optionService.getOptions(questionKey)

    $scope.getNumericOptions = (questionKey,min,max,step) ->
        return optionService.getNumericOptions(questionKey,min,max,step)

    $scope.getAnswerValue = (questionKey, periodKey) ->
        return surveyDTOService.getAnswerValue(questionKey, periodKey)

    $scope.getAccount = () ->
        return surveyDTOService.getAccount()

    $scope.close = ->
        $modalInstance.close()

