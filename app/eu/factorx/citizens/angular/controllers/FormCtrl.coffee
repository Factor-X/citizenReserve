angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $filter, $log, downloadService, surveyDTOService, conditionService, $location,$flash) ->
    $scope.topicQuestions =
        profile:
            'presence': ['Q1300', 'Q1400', 'Q1500']
            'programs': ['Q1110', 'Q1120', 'Q1130']
            'dinner': ['Q1140', 'Q1150']
            'heating': ['Q1600', 'Q1900', 'Q1210']
            'lighting': ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040',
                         'Q1235']

    $scope.logout = () ->
        downloadService.postJson '/logout', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $location.path('/welcome')
                surveyDTOService.logout()
                $flash.success 'logout.success'
        return

    $scope.isAuthenticated = () ->
        surveyDTOService.isAuthenticated()

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalInstance = modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.isQuestionAnswered = (questionKey) ->
        answers = surveyDTOService.getAnswers(questionKey)
        if answers.length == 0
            return false
        for answer in answers
            for answerValue in answer.answerValues
                if !((!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue == 0))
                    return false
        return true

    $scope.isProfileTopicCompleted = (topicIdentifier) ->
        topicQuestionKeys = $scope.topicQuestions.profile[topicIdentifier]
        for questionKey in topicQuestionKeys
            if !$scope.isQuestionAnswered(questionKey)
                return false
        return true

    $scope.isProfileCompleted = ->
        $scope.topicQuestions.profile
        for topicIdentifier of $scope.topicQuestions.profile
            if (!$scope.isProfileTopicCompleted(topicIdentifier))
                return false
        return true

    $scope.potentialReduction = {}

    $scope.getPotentialReduction = ->
        downloadService.postJson '/reduction/potential', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $scope.potentialReduction = result.data
            else
                console.log(result.data)

    $scope.getPotentialReduction()

    $scope.effectiveReduction = {}

    $scope.getEffectiveReduction = ->
      downloadService.postJson '/reduction/effective', surveyDTOService.surveyDTO, (result) ->
        if result.success
          $scope.effectiveReduction = result.data
        else
          console.log(result.data)

    $scope.getEffectiveReduction()


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

    calcParabolaParameters = (x1, y1, x2, y2, x3, y3) ->
        denom = (x1 - x2) * (x1 - x3) * (x2 - x3)
        A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom
        B = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom
        C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom
        return {a:A, b:B, c:C}


    arr1 = calcParabolaParameters(17.5,100,18.5,120,19.5,110)
    arr2 = calcParabolaParameters(17.5,90,18.5,105,19.5,100)

    $scope.data = [
        {
            key: "MEAN OF ALL",
            color: '#d22323'
            area: true
            values: [
                { x: 17, y: 100 },
                { x: 18, y: 100 },
                { x: 18, y: 120 },
                { x: 19, y: 120 },
                { x: 19, y: 110 },
                { x: 20, y: 110 },
            ]
        },
        {
            key: "YOU",
            color: '#229913'
            area: true
            values: [
                { x: 17, y: 90 },
                { x: 18, y: 90 },
                { x: 18, y: 105 },
                { x: 19, y: 105 },
                { x: 19, y: 100 },
                { x: 20, y: 100 },
            ]
        },
        {
            key: "TREND ALL"
            color: '#F22626'
            values: _.map(_.range(17, 20.05, 0.1), (x) ->
                return { x: x, y: arr1.a * x*x + arr1.b*x + arr1.c }
            )
        },
        {
            key: "TREND YOU"
            color: '#28DB15'
            values: _.map(_.range(17, 20.05, 0.1), (x) ->
                return { x: x, y: arr2.a * x*x + arr2.b*x + arr2.c }
            )
        }
    ];