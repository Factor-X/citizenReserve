angular
.module('app.directives')
.directive "mmModalTest", (directiveService, messageFlash,$http,downloadService) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-modal-test.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.fields = {

            name:{
                fieldTitle: "Name"
                validationRegex: "^.{3,50}$"
                validationMessage: "between 3 and 50 characters"
                focus: ->
                    return true
            }
        }

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        #send the request to the server
        $scope.save = () ->
            if $scope.allFieldValid()

                $scope.isLoading = true

                #create DTO
                url = "??"
                data = {}

                #create currency
                downloadService.postJson url,data, (result) ->
                    $scope.isLoading = false
                    if result.success
                        #close window
                        $scope.close()


            return false

        $scope.close = ->
            modalService.close modalService.TEST

    link: (scope) ->

