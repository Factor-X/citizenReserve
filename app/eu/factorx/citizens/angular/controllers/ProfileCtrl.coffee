angular
.module('app.controllers')
.controller "ProfileCtrl", ($scope, modalService, surveyDTOService, $filter, $state, $stateParams) ->
    householdProfileQuestions =
        presence: ['Q1300', 'Q1400', 'Q1500']
        programs: ['Q1110', 'Q1120', 'Q1130']
        heating: ['Q1600', 'Q1900', 'Q1210']
        lighting: ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040', 'Q1235']
        dinner: ['Q1140', 'Q1150']

    $scope.topics = {}
    for topicKey, topicQuestions of householdProfileQuestions
        $scope.topics[topicKey] = {questions: topicQuestions, completed: false}

    $scope.profileCompleted = false
    $scope.averagePotentialPowerReduction = null

    # A profile topic is 'completed' if it does not contain any unanswered question
    updateTopicState = (topic) ->
        topic.completed = !_.find topic.questions, (questionKey) ->
            return !surveyDTOService.isQuestionCompleted(questionKey)
        return

    # A profile is 'completed' if it does not contain any not completed topic
    updateProfileState = ->
        $scope.profileCompleted = !_.find $scope.topics, (topic) ->
            return !topic.completed
        return

    updateAveragePotentialPowerReduction = ->
        surveyDTOService.getPotentialReductionDTO (reductionDto) ->
            $scope.averagePotentialPowerReduction = $filter("number")(reductionDto.averagePowerReduction, 0)
            return

    $scope.onLoad = ->
        _.each($scope.topics, updateTopicState)
        updateProfileState()
        if $scope.profileCompleted
            updateAveragePotentialPowerReduction()

    $scope.onTopicClose = (topicKey) ->
        updateTopicState($scope.topics[topicKey])
        updateProfileState()
        if $scope.profileCompleted
            updateAveragePotentialPowerReduction()
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()
        return

    $scope.isTopicCompleted = (topicKey) ->
        return $scope.topics[topicKey].completed

    $scope.isProfileCompleted = ->
        return $scope.profileCompleted

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.onLoad()
