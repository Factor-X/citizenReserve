angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $log, topic,downloadService,surveyDTOService ) ->
    $scope.topic = topic

    $scope.save= () ->
        console.log "surveyDTO"
        console.log surveyDTOService.surveyDTO
        downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
            if result.success
                console.log "je suis un success"
            else
                console.log "je suis un echec"

    $scope.load = () ->
        modalInstance = modalService.open({
            templateUrl: '$/angular/views/login_modal.html',
            controller: 'LoginModalCtrl',
            size: 'lg'
        })

    $scope.openTopic = (target) ->

        modalInstance = modalService.open({
            templateUrl: '$/angular/views/household/profile/topics/' + target + '.html',
            controller: 'ModalTopicCtrl',
            size: 'lg'
        })