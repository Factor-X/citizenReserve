angular
.module('app.controllers')
.controller "WelcomeCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService,$flash,$stateParams) ->

    $scope.toHouseHold = ->
        surveyDTOService.createPreAccount('household')
        $state.go 'root.householdProfile'


    $scope.loginParams = {
        email: ""
        password: ""
    }

    $scope.forgotPasswordParams = {
        email: ""
    }

    $scope.loading = false

    $scope.login = ->
        if $scope.loading == false
            dto =
                email: $scope.loginParams.email
                password: $scope.loginParams.password

            $scope.loading = true

            downloadService.postJson '/login', dto, (result)->
                $scope.loading = false
                if result.success
                    surveyDTOService.login(result.data)
                    if result.data.account.accountType == 'household'
                        if (result.data.account.passwordToChange)
                            $scope.openChangePasswordModal($scope.loginParams.password)
                        else
                            $flash.success 'account.login.success'
                            $state.go 'root.householdProfile'
                else
                    $flash.error result.data.message
    #TODO to complete

    $scope.forgotPassword = ->
        if $scope.loading == false
            dto =
                email: $scope.forgotPasswordParams.email

            $scope.loading = true

            downloadService.postJson '/forgotPassword', dto, (result)->
                $scope.loading = false
                if result.success
                    $flash.success 'account.forgotPassword.success'
                    $scope.forgotPasswordParams.email = null
                else
                    $flash.error result.data.message

    $scope.openChangePasswordModal = () ->
        modalService.open({
            templateUrl: '$/angular/views/household/account/account-change-password.html',
            controller: 'ModalChangePasswordCtrl',
            size: 'lg',
            scope: $scope
        })


