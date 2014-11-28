angular
.module('app.controllers')
.controller "LoginModalCtrl", ($scope, $modalInstance, downloadService,surveyDTOService) ->

    $scope.email = "plop"
    $scope.password = "pass"

    $scope.valid = ->

        console.log "=>"+$scope.email+" "+$scope.password

        dto =
            email: $scope.email
            password: $scope.password

        downloadService.postJson '/login', dto, (result)->
            if result.success
                surveyDTOService.surveyDTO = result.data
                $modalInstance.close


    $scope.close = ->
        $modalInstance.close()



