angular
.module('app.controllers')
.controller "SuperAdminMainCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService,$flash) ->

    $scope.loginParams = {
        email: ""
        password: ""
    }

    $scope.reductionData = null

    $scope.logout = () ->
        downloadService.postJson '/logout', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $location.path('/welcome')
                surveyDTOService.logout()
                $flash.success 'logout.success'
        return

    $scope.loadingReductionData = () ->
        downloadService.getJson '/superAdmin/reductionData',  (result)->
            $scope.loading = false
            if result.success
                console.log result.data
                $scope.reductionData = result.data
            else
                $flash.error result.data.message

    $scope.loadingReductionData()