angular
.module('app.controllers')
.controller "MainCtrl", ($scope, translationService, modalService) ->
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
        sel: "Camera"
        items: [
            {value: "Gear", icon: 'gear', label: "Gear"},
            {value: "Globe", icon: 'globe', label: "Globe"},
            {value: "Heart", icon: 'heart', label: "Heart"},
            {value: "Camera", icon: 'camera', label: "Camera"}
        ]
        cnt: 10
    $scope.m = () ->
        modalService.open('nice-modal', $scope.$new({a: 12}))

#rootScope
angular.module('app').run ($rootScope, $location, translationService)->