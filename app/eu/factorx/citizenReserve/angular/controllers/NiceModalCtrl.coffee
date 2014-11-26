angular
.module('app.controllers')
.controller "NiceModalCtrl", ($scope, $modalInstance, chosenValue) ->
    $scope.selected = chosenValue

    $scope.ok = () ->
        $modalInstance.close $scope.selected


    $scope.cancel = () ->
        $modalInstance.dismiss 'cancel'
