angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, modalService, $filter, $log, downloadService, surveyDTOService) ->

    $scope.isAuthenticated = ->
        return surveyDTOService.isAuthenticated()

    $scope.options =
        chart:
            type: 'lineChart',
            forceY: [0]
            height: 450,
            margin:
                top: 20,
                right: 20,
                bottom: 60,
                left: 55
            x: (d) ->
                return d.x
            y: (d) ->
                return d.y
            showValues: true
            interactive: false
            transitionDuration: 500,
            xAxis:
                axisLabel: ''
                tickValues: [17, 18, 19, 20]
                showMaxMin: false
                tickFormat: (d)->
                    return $filter('toHour')(parseInt(d))
            yAxis:
                axisLabel: '',
                axisLabelDistance: 30
                showMaxMin: false
                tickFormat: (d)->
                    return $filter('toWatts')(parseInt(d))

    $scope.data = []

    downloadService.getJson '/batch/last', (result) ->
        if result.success

            v1 = result.data.effectiveBatch.batchResultItemList[0].powerReduction
            v2 = result.data.effectiveBatch.batchResultItemList[1].powerReduction
            v3 = result.data.effectiveBatch.batchResultItemList[2].powerReduction

            console.log v1, v2, v3

            calcParabolaParameters = (x1, y1, x2, y2, x3, y3) ->
                denom = (x1 - x2) * (x1 - x3) * (x2 - x3)
                A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom
                B = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom
                C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom
                return {a: A, b: B, c: C}


            arr2 = calcParabolaParameters(17.5, v1, 18.5, v2, 19.5, v3)

            $scope.data = [

#                {
#                    key: $filter('translate')('results.stack.name'),
#                    color: '#229913'
#                    area: true
#                    values: [
#                        { x: 17, y: v1 },
#                        { x: 18, y: v1 },
#                        { x: 18, y: v2 },
#                        { x: 19, y: v2 },
#                        { x: 19, y: v3 },
#                        { x: 20, y: v3 },
#                    ]
#                },
                {
                    key: $filter('translate')('results.trend.name')
                    color: '#28DB15'
                    values: _.map(_.range(17, 20.05, 0.1), (x) ->
                        return { x: x, y: arr2.a * x * x + arr2.b * x + arr2.c }
                    )
                }
            ];

