angular
.module('app.controllers')
.controller "LoginModalCtrl", ($scope, $modalInstance, downloadService,surveyDTOService) ->

    $scope.o={
        email:""
        password:""
    }
    $scope.valid = ->
        dto =
            email: $scope.o.email
            password: $scope.o.password

        downloadService.postJson '/login', dto, (result)->
            if result.success
                surveyDTOService.surveyDTO = result.data
                $scope.close()

    $scope.close = ->
        $modalInstance.close()



