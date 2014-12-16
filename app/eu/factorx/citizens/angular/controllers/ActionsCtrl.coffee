angular
.module('app.controllers')
.controller "ActionsCtrl", ($scope, modalService, surveyDTOService, conditionService, $filter) ->
    questionsByAccountTypes =
        household:
            presence: ['Q3210', 'Q3211']
            programs: ['Q3110', 'Q3120', 'Q3130']
            heating: ['Q3310', 'Q3320', 'Q3330']
            lighting: ['Q3410', 'Q3420', 'Q3510', 'Q3530', 'Q3610', 'Q3620', 'Q3630', 'Q3631', 'Q3640', 'Q3810']
            dinner: ['Q3710', 'Q3711', 'Q3720', 'Q3730', 'Q3750', 'Q3760', 'Q3740', 'Q3741']
        # enterprise: {}
        # institution: {}

    topics = {}
    for topicKey, topicQuestions of questionsByAccountTypes['household']
        topics[topicKey] = {questions: topicQuestions, empty: false}

    # An action topic is 'empty' (and then disabled) if it does not contain any visible question
    updateTopicState = (topic) ->
        topic.empty = !_.find topic.questions, (questionKey) ->
            return conditionService.checkCondition(questionKey)
        return

    $scope.onLoad = ->
        _.each(topics, updateTopicState)
        surveyDTOService.updateEffectivePowerReduction()

    $scope.onTopicClose = ->
        _.each(topics, updateTopicState)
        surveyDTOService.updateEffectivePowerReduction()
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()
        return

    $scope.isEffectivePowerReductionNotNull = ->
        epr = surveyDTOService.getAverageEffectivePowerReduction()
        return (!!epr) && (epr > 0)

    $scope.getAverageEffectivePowerReduction = ->
        return $filter("number")(surveyDTOService.getAverageEffectivePowerReduction(), 0)

    $scope.isTopicEmpty = (topicKey) ->
        return topics[topicKey].empty

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.onLoad()
