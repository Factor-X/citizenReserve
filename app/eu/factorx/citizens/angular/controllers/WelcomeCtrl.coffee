angular
.module('app.controllers')
.controller "WelcomeCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService) ->
    $scope.toHouseHold = ->
        surveyDTOService.getAccount().accountType = 'household'
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
                        $state.go 'root.householdProfile'
    #TODO to complete

    $scope.forgotPassword = ->
        if $scope.loading == false
            dto =
                email: $scope.forgotPasswordParams.email

            $scope.loading = true

            downloadService.postJson '/forgotPassword', dto, (result)->
                $scope.loading = false
                if result.success
                    #TODO display success message
                    $scope.forgotPasswordParams.email = null


