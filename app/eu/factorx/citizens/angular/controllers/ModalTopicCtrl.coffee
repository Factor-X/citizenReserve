angular
.module('app.controllers')
.controller "ModalTopicCtrl", ($scope,surveyDTOService) ->

    console.log surveyDTOService.getAnswers('Q4000')