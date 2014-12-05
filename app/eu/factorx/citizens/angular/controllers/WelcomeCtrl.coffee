angular
.module('app.controllers')
.controller "WelcomeCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService,$flash,$stateParams) ->

    $scope.toHouseHold = ->
        surveyDTOService.createPreAccount('household', $stateParams.lang)
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
                    $flash.success 'account.login.success'
                    surveyDTOService.login(result.data)
                    if result.data.account.accountType == 'household'
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


