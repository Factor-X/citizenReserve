angular
.module('app.controllers')
.controller "FormCtrl", ($scope, $http,modalService) ->

    $scope.text="example"

    $scope.open = ->
        modalService.show(modalService.TEST)

