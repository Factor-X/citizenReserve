angular
.module('app.controllers')
.controller "MainCtrl", ($scope, translationService) ->
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

#rootScope
angular.module('app').run ($rootScope, $location, translationService)->