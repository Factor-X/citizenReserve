angular
.module('app.controllers')
.controller "WelcomeCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService, $flash, $stateParams) ->
    $scope.toHousehold = ->
        surveyDTOService.createPreAccount('household')
        $state.go 'root.householdProfile'

    $scope.toEnterprise = ->
        surveyDTOService.createPreAccount('enterprise')
        $state.go 'root.enterpriseAccount'

    $scope.toAuthority = ->
        $state.go 'root.authorityAccount'


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
                            # forcing change of password
                            $scope.openChangePasswordModal()
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

    #
    # Open change password dialog (passing login params)
    #
    $scope.openChangePasswordModal = () ->
        s = $scope.$new()
        s.loginParams = angular.copy($scope.loginParams)

        modalInstance = modalService.open({
            templateUrl: '$/angular/views/household/account/account-change-password.html',
            controller: 'ModalChangePasswordCtrl',
            size: 'lg',
            scope: s
        })

        # callback function (called on close): auto login with the new password
        modalInstance.result.then (newPassword) ->
            console.log("newPassword = " + newPassword)
            $scope.loginParams.password = newPassword
            $scope.login()
