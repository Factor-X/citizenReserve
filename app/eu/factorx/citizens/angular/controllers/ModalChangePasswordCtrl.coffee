angular
.module('app.controllers')
.controller "ModalChangePasswordCtrl", ($scope, modalService, $log,  downloadService, $modalInstance,$flash) ->

    $scope.noSubmitYet=true
    $scope.loading=false

    console.log("$scope.loginParams", $scope.loginParams)
    $scope.validation = {
        oldPassword:
            pattern:/^[a-zA-Z0-9-_%]{6,18}$/
            valid:false
        newPassword:
            pattern:/^[a-zA-Z0-9-_%]{6,18}$/
            valid:false
        repeatPassword:
            validation: ->
                return $scope.o.newPassword == $scope.o.repeatPassword
            valid:false
    }

    $scope.o = {
        oldPassword:""
        newPassword:""
        repeatPassword:""
    }

    if !!$scope.loginParams
        $scope.o.oldPassword = angular.copy($scope.loginParams.password)
        $scope.o.generatedPassword = true
        $scope.validation.oldPassword.valid = true

    $scope.save = () ->
        $scope.noSubmitYet=false
        if $scope.checkValidity()

            dto=
                oldPassword:$scope.o.oldPassword
                newPassword:$scope.o.newPassword


            $scope.loading=true
            downloadService.postJson '/account/changePassword', dto, (result) ->
                $scope.loading=false
                if result.success
                    $flash.success 'account.changePassword.success'
                    if (!!$scope.loginParams)
                        $scope.loginParams.password = angular.copy($scope.o.newPassword)
                        $scope.login()
                    else
                        $flash.success 'account.changePassword.success'
                    $scope.close()
                else
                    $flash.error result.data.message


    $scope.checkValidity = () ->
        for key in Object.keys($scope.validation)
            if $scope.validation[key].valid==false
                return false
        return true

    $scope.close = ->
        $modalInstance.close()

