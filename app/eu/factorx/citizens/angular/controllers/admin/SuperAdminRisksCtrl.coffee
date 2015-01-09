angular
.module('app.controllers')
.controller "SuperAdminRisksCtrl", ($scope, modalService, $state, $log, $location, surveyDTOService, downloadService, $flash) ->
    $scope.newRiskDate = null
    $scope.allRisks = []
    $scope.minDate = new Date()

    $scope.dateOptions =
        startingDay: 1
        "show-weeks": false

    $scope.open = ($event) ->
        $event.preventDefault()
        $event.stopPropagation()
        $scope.opened = true
        return

    $scope.load = () ->
        downloadService.getJson '/risks/load', (result) ->
            if result.success
                allRisks = result.data.list
                if (allRisks == null)
                    $scope.allRisks = []
                else
                    $scope.allRisks = allRisks
            else
                $flash.error result.data.message
            return
        return

    $scope.save = () ->
        data =
            riskDate: $scope.newRiskDate
        downloadService.postJson '/risks/create', data, (result) ->
            if result.success
                $flash.success 'risk.save.success'
                $scope.allRisks.push(result.data)
            else
                $flash.error result.data.message
            return
        return

    $scope.sendAlerts = (sheddingRiskDto) ->
        downloadService.postJson '/risks/sendAlerts', sheddingRiskDto, (result) ->
            if result.success
                $flash.success 'risk.alerts.success'
            else
                $flash.error result.data.message
            return
        return

    $scope.load()
