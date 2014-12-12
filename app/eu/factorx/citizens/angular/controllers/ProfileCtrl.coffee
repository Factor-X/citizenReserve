angular
.module('app.controllers')
.controller "ProfileCtrl", ($scope, modalService, $filter, $log, downloadService, surveyDTOService, $filter) ->
    $scope.topics =
        presence:
            questions: ['Q1300', 'Q1400', 'Q1500']
        programs:
            questions: ['Q1110', 'Q1120', 'Q1130']
        dinner:
            questions: ['Q1140', 'Q1150']
        heating:
            questions: ['Q1600', 'Q1900', 'Q1210']
        lighting:
            questions: ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040', 'Q1235']

    $scope.profileCompleted = false
    $scope.potentialReduction = null

    $scope.onLoad = ->
        updateTopicState(topic)
        updateProfileState()
        if $scope.profileCompleted
            $scope.potentialReduction = surveyDTOService.getAveragePotentialPowerReduction()

    $scope.onTopicClose = (topic) ->
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()
        updateTopicState(topic)
        updateProfileState()
        if $scope.profileCompleted
            $scope.potentialReduction = surveyDTOService.getAveragePotentialPowerReduction()

    updateTopicState = (topic) ->
        completed = true
        for questionKey in topic.questions
            if !surveyDTOService.isQuestionCompleted(questionKey)
                completed = false
                break
        topic.completed = completed
        return

    updateProfileState = () ->
        completed = true
        for topicKey of $scope.topics
            if !$scope.topics[topicKey].completed
                completed = false
                break
        $scope.profileCompleted = completed
        return

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

