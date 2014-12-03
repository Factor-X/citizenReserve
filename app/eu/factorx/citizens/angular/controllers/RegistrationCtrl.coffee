angular
.module('app.controllers')
.controller "RegistrationCtrl", ($scope, modalService, $log,  downloadService, surveyDTOService, optionService,$location) ->

    $scope.noSubmitYet=true
    $scope.loading=false

    $scope.getOptions = (questionKey) ->
        return optionService.getOptions(questionKey)

    $scope.getAnswerValue = (questionKey, periodKey) ->
        return surveyDTOService.getAnswerValue(questionKey, periodKey)

    $scope.getAccount = () ->
        return surveyDTOService.getAccount()

    $scope.getNumericOptions = (questionKey,min,max,step) ->
        return optionService.getNumericOptions(questionKey,min,max,step)

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalInstance = modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.logout = () ->
        downloadService.postJson '/logout', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $location.path('/welcome')
                surveyDTOService.logout()
        return

    $scope.validation = {
        firstName:
            pattern:/^.{2,100}$/
            valid:false
        lastName:
            pattern:/^.{2,100}$/
            valid:false
        emailAddress:
            pattern:/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            valid:false
        password:
            pattern:/^[a-zA-Z0-9-_%]{6,18}$/
            valid:false
        repeatPassword:
            validation: ->
                return $scope.getAccount().password == $scope.o.repeatPassword
            valid:false
        zip:
            pattern:/^.{4,20}$/
            valid:false
        terms:
            valid:false
    }

    $scope.o = {
        errorMessage:""
        repeatPassword:$scope.getAccount().password
        acceptAgreement:false
    }

    $scope.save = () ->
        $scope.noSubmitYet=false
        if $scope.checkValidity()
            $scope.loading=true
            console.log "je save ce dto : "
            console.log surveyDTOService.surveyDTO
            downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
                $scope.loading=false
                if result.success
                    console.log "je suis un success"
                else
                    console.log "je suis un echec"


    $scope.checkValidity = () ->
        for key in Object.keys($scope.validation)
            if $scope.validation[key].valid==false
                return false
        return true

    $scope.isAuthenticated = ->
        return surveyDTOService.isAuthenticated()
