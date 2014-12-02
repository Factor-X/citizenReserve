angular
.module('app.controllers')
.controller "RegistrationCtrl", ($scope, modalService, $log,  downloadService, surveyDTOService, $modalInstance,optionService) ->

    $scope.noSubmitYet=true

    $scope.getOptions = (questionKey) ->
        return optionService.getOptions(questionKey)

    $scope.getAnswerValue = (questionKey, periodKey) ->
        return surveyDTOService.getAnswerValue(questionKey, periodKey)

    $scope.getAccount = () ->
        return surveyDTOService.getAccount()

    $scope.getNumericOptions = (questionKey,min,max,step) ->
        return optionService.getNumericOptions(questionKey,min,max,step)

    $scope.close = ->
        $modalInstance.close()


    $scope.validation = {
        firstName:false
        lastName:false
        emailAddress:false
        password:false
        repeatPassword:false
        zip:false
        terms:false
    }

    $scope.pattern = {
        firstName:/^.{2,100}$/
        lastName:/^.{2,100}$/
        lastName:/^.{2,100}$/
        emailAddress:/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        password:/^[a-zA-Z0-9-_%]{6,18}$/
        zip:/^.{4,20}$/
    }

    $scope.o = {
        errorMessage:""
        repeatPassword:""
        acceptAgreement:false
    }

    $scope.save = () ->
        $scope.noSubmitYet=false
        if $scope.checkValidity()
            downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
                if result.success
                    console.log "je suis un success"
                else
                    console.log "je suis un echec"


    $scope.checkValidity = () ->
        for key in Object.keys($scope.validation)
            if $scope.validation[key]==false
                return false
        return true

