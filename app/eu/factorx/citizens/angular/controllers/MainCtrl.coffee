angular
.module('app.controllers')
.controller "MainCtrl", ($scope, modalService, $log, gettextCatalog) ->
    #
    # Initialize
    #
    $scope.setLanguage = (lang) ->
        gettextCatalog.setCurrentLanguage(lang)


#rootScope
angular.module('app').run ($rootScope, $location)->
    $rootScope.redirectTo = (target) ->
        $location.path(target)
