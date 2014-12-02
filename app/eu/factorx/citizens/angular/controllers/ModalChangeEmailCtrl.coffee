angular
.module('app.controllers')
.controller "ModalChangeEmailCtrl", ($scope, modalService, $log,  downloadService, $modalInstance,surveyDTOService) ->

    $scope.noSubmitYet=true
    $scope.loading=false

    $scope.validation = {
        oldPassword:
            pattern:/^[a-zA-Z0-9-_%]{6,18}$/
            valid:false
        newEmail:
            pattern:/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            valid:false
    }

    $scope.o = {
        oldPassword:""
        newEmail:""
    }

    $scope.save = () ->
        $scope.noSubmitYet=false
        if $scope.checkValidity()

            dto=
                oldPassword:$scope.o.oldPassword
                email:$scope.o.newEmail


            $scope.loading=true
            downloadService.postJson '/account/changeEmail', dto, (result) ->
                $scope.loading=false
                if result.success
                    # TODO message
                    console.log "je suis un success"
                    surveyDTOService.getAccount().email = $scope.o.newEmail
                    $scope.close()
                else
                    # TODO message
                    console.log "je suis un echec"


    $scope.checkValidity = () ->
        for key in Object.keys($scope.validation)
            if $scope.validation[key].valid==false
                return false
        return true

    $scope.close = ->
        $modalInstance.close()

