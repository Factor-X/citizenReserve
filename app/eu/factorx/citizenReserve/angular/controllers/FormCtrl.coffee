angular
.module('app.controllers')
.controller "FormCtrl", ($scope, $http,modalService,downloadService) ->

    $scope.text="example"

    $scope.open = ->
        modalService.show(modalService.TEST)

    # TODO TEMP
    downloadService.getJson "/account", (result) ->
        if result.success
            $scope.accounts = result.data.list
