angular
.module('app.controllers')
.controller "WelcomeCtrl", ($scope, modalService, $log,$location,surveyDTOService) ->

    $scope.toHouseHold = ->
        surveyDTOService.getAccount().accoutType = 'household'
        $scope.$root.redirectTo('/household-profile/programs')


