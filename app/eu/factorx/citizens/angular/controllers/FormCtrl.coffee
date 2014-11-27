angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $log, topic) ->
    $scope.topic = topic

    $scope.slider =
        schedule:
            value: null
            steps: [
                {value: null, label: null}
                {value: 18, label: '18h'}
                {value: 19, label: '19h'}
                {value: 20, label: '20h'}
                {value: 21, label: '21h'}
                {value: 22, label: '22h'}
            ]

    $scope.openTopic = (target) ->
        # TODO TEMP
        modalInstance = modalService.open({
            templateUrl: '$/angular/views/modal/household/profile/' + target + '.html',
            controller: 'ModalTopicCtrl',
            size: 'lg'
        ###
        resolve:
            chosenValue: () ->
                return $scope.x.sel
        ###
        });
###
modalInstance.result.then (result) ->
    $log.info(result)
, () ->
    $scope.x.sel = o
    $log.info('Modal dismissed at: ' + new Date())
###