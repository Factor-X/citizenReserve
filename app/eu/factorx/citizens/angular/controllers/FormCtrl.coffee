angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $log, downloadService, surveyDTOService, conditionService, $location) ->
    $scope.topicQuestions =
        profile:
            'presence': ['Q1300', 'Q1400', 'Q1500']
            'programs': ['Q1110', 'Q1120', 'Q1130']
            'dinner': ['Q1140', 'Q1150']
            'heating': ['Q1600', 'Q1900', 'Q1210']
            'lighting': ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040',
                         'Q1235']

    $scope.save = () ->
        downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
            if result.success
                console.log "je suis un success"
            else
                console.log "je suis un echec"

    $scope.logout = () ->
        downloadService.postJson '/logout', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $location.path('/welcome')
                surveyDTOService.logout()
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
        for answer in surveyDTOService.getAnswers(questionKey)
            for answerValue in answer.answerValues
                if (!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue == 0)
                    return true
        return false

    $scope.isProfileTopicCompleted = (topicIdentifier) ->
        topicQuestionKeys = $scope.topicQuestions.profile[topicIdentifier]
        for questionKey in topicQuestionKeys
            if !$scope.isQuestionAnswered(questionKey)
                return false
        return true

    $scope.isProfileCompleted = ->
        $scope.topicQuestions.profile
        #        for topicIdentifier of $scope.topicQuestions.profile
        return

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


    $scope.data =
        labels: [
            "17h"
            "18h30"
            "20h"
        ]
        datasets: [
            {
                label: "My First dataset"
                fillColor: "rgba(220,220,220,0.2)"
                strokeColor: "rgba(220,220,220,1)"
                pointColor: "rgba(220,220,220,1)"
                pointStrokeColor: "#fff"
                pointHighlightFill: "#fff"
                pointHighlightStroke: "rgba(220,220,220,1)"
                data: [
                    65
                    81
                    40
                ]
            }
            {
                label: "My Second dataset"
                fillColor: "rgba(151,187,205,0.2)"
                strokeColor: "rgba(151,187,205,1)"
                pointColor: "rgba(151,187,205,1)"
                pointStrokeColor: "#fff"
                pointHighlightFill: "#fff"
                pointHighlightStroke: "rgba(151,187,205,1)"
                data: [
                    28
                    19
                    90
                ]
            }
        ]


    # Chart.js Options
    $scope.options =

    # Sets the chart to be responsive
        responsive: true

    #/Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: true

    #String - Colour of the grid lines
        scaleGridLineColor: "rgba(0,0,0,.05)"

    #Number - Width of the grid lines
        scaleGridLineWidth: 1

    #Boolean - Whether the line is curved between points
        bezierCurve: true

    #Number - Tension of the bezier curve between points
        bezierCurveTension: 0.4

    #Boolean - Whether to show a dot for each point
        pointDot: false

    #Number - Radius of each point dot in pixels
        pointDotRadius: 4

    #Number - Pixel width of point dot stroke
        pointDotStrokeWidth: 1

    #Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius: 20

    #Boolean - Whether to show a stroke for datasets
        datasetStroke: true

    #Number - Pixel width of dataset stroke
        datasetStrokeWidth: 2

    #Boolean - Whether to fill the dataset with a colour
        datasetFill: true

        showTooltips: false

    # Function - on animation progress
        onAnimationProgress: ->


            # Function - on animation complete
        onAnimationComplete: ->


            #String - A legend template
        legendTemplate: "<ul class=\"tc-chart-js-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"