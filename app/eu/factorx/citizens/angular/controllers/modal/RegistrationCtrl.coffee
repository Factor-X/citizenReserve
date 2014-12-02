angular
.module('app.controllers')
.controller "RegistrationCtrl", ($scope, modalService, $log, topic, downloadService, surveyDTOService, conditionService,$location) ->

    $scope.o = {
        errorMessage:""
        repeatPassword:""
        acceptAgreement:false
    }

    $scope.save = () ->
        if $scope.checkValidity()
            downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
                if result.success
                    console.log "je suis un success"
                else
                    console.log "je suis un echec"


    $scope.checkValidity = () ->

