angular
.module('app.controllers')
.controller "SuperAdminLoginCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService,$flash) ->
    $scope.loginParams = {
        email: ""
        password: ""
    }

    $scope.loading = false

    $scope.login = ->
        if $scope.loading == false
            dto =
                email: $scope.loginParams.email
                password: $scope.loginParams.password

            $scope.loading = true

            downloadService.postJson '/superAdmin/login', dto, (result)->
                $scope.loading = false
                if result.success
                    surveyDTOService.login(result.data)
                    $flash.success 'account.login.success'
                    $state.go 'root.superAdminMain'
                else
                    $flash.error result.data.message