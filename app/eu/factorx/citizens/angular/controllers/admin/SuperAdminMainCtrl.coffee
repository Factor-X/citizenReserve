angular
.module('app.controllers')
.controller "SuperAdminMainCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService,$flash) ->

    $scope.loginParams = {
        email: ""
        password: ""
    }

    $scope.batchs = null

    $scope.loadingReductionData = () ->
        downloadService.getJson '/superAdmin/reductionData',  (result)->
            $scope.loading = false
            if result.success
                console.log "------------------------------------------------"
                console.log result.data.list
                $scope.batchs = result.data.list
            else
                $flash.error result.data.message

    $scope.loadingReductionData()


    $scope.getValue = (map, day, period) ->
        for el in map
            if (day==null || el.dayKey == day) && el.periodKey = period
                return el.powerReduction

