angular
.module('app.controllers')
.controller "ProfileCtrl", ($scope, modalService, surveyDTOService, $filter) ->
    questionsByAccountTypes =
        household:
            presence: ['Q1300', 'Q1400', 'Q1500']
            programs: ['Q1110', 'Q1120', 'Q1130']
            heating: ['Q1600', 'Q1900', 'Q1210']
            lighting: ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040', 'Q1235']
            dinner: ['Q1140', 'Q1150']
    #enterprise: {}
    #institution: {}

    profileCompleted = false

    topics = {}
    for topicKey, topicQuestions of questionsByAccountTypes['household']
        topics[topicKey] = {questions: topicQuestions, completed: false}

    # A profile topic is 'completed' if it does not contain any unanswered question
    updateTopicState = (topic) ->
        topic.completed = !_.find topic.questions, (questionKey) ->
            return !surveyDTOService.isQuestionCompleted(questionKey)
        return

    # A profile is 'completed' if it does not contain any not completed topic
    updateProfileState = ->
        profileCompleted = !_.find topics, (topic) ->
            return !topic.completed
        return

    $scope.onLoad = ->
        _.each(topics, updateTopicState)
        updateProfileState()
        if profileCompleted
            surveyDTOService.updatePotentialPowerReduction()

    $scope.onTopicClose = (topicKey) ->
        updateTopicState(topics[topicKey])
        updateProfileState()
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()
        if profileCompleted
            surveyDTOService.updatePotentialPowerReduction()
        return

    $scope.getAveragePotentialPowerReduction = ->
        return $filter("number")(surveyDTOService.getAveragePotentialPowerReduction(), 0)

    $scope.isTopicCompleted = (topicKey) ->
        return topics[topicKey].completed

    $scope.isProfileCompleted = ->
        return profileCompleted

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.onLoad()
