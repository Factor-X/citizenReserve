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
        sel: 'Human'
        items: [
            'Human'
            'Bat'
            '-'
            'Vampire'
        ]
        cnt: 10
        doubleRange:
            min: 2
            max: 3
            rangeMin: 0
            rangeMax: 5
        radio:
            value: null
            options: [
                {value: 0, label: 'OPT_0'}
                {value: 1, label: 'OPT_1'}
                {value: 2, label: 'OPT_2'}
                {value: 3, label: 'OPT_3'}
                {value: 4, label: 'OPT_4'}
            ],
            simpleOptions: [ 0, 1, 2, 3, 4 ]







        grid: [
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?2165438536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216548536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216438536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216543836158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216543853615851968161'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/128/512?3216543853615519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3215438536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216543853615851981651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216548536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216543536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/800?3165438536158519681651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3216543853615851981651'}
            {title: 'AAA', content: 'Hello world', image: 'http://placekitten.com/512/512?3215438536158519681651'}
        ]


    $scope.$watch 'x.sel', (n, o) ->
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