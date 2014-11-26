angular
.module('app.controllers')
.controller "MainCtrl", ($scope, translationService, modalService, $log) ->
    $scope.initialLoad =
        translations: false

    translationService.initialize('fr')

    #
    # Initialize
    #
    $scope.missedTranslationLoadings = 0
    $scope.$on "LOAD_FINISHED", (event, args) ->
        if args.type is "TRANSLATIONS"
            if not args.success and $scope.missedTranslationLoadings < 3
                $scope.missedTranslationLoadings += 1
                translationService.initialize($rootScope.language)
            else
                if args.success
                    $scope.missedTranslationLoadings = 0
                $scope.initialLoad.translations = args.success
        return

    $scope.x =
        sel: "Human"
        items: [
            "Human"
            "Bat"
            "-"
            "Vampire"
        ]
        cnt: 10



    $scope.$watch 'x.sel', (n,o) ->
        if n == $scope.x.items[3]
            modalInstance = modalService.open({
                templateUrl: '$/angular/views/modal-confirm-vampire.html',
                controller: 'NiceModalCtrl',
                size: 'sm'
                resolve:
                    chosenValue: () ->
                        return $scope.x.sel

            });

            modalInstance.result.then (result) ->
                $log.info(result)
            , () ->
                $scope.x.sel = o
                $log.info('Modal dismissed at: ' + new Date())

#rootScope
angular.module('app').run ($rootScope, $location, translationService)->