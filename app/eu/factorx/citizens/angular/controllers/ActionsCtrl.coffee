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

    $scope.averagePowerReduction = null

    $scope.topics = {}
    for topicKey, topicQuestions of questionsByAccountTypes['household']
        $scope.topics[topicKey] = {questions: topicQuestions, disabled: false}
    console.log("$scope.topics", $scope.topics)

    checkAllConditionsAreFalse = (topic) ->
        res = true
        for questionKey in topic.questions
            if conditionService.checkCondition(questionKey) && (res == true)
                res = false
        return res

    updateTopics = ->
        for topicKey, topic of $scope.topics
            # we may check if at least one condition is true (more fast),
            # but we have to check all conditions, in order to reset all answers related to hidden questions...
            topic.disabled = checkAllConditionsAreFalse(topic)
        return

    updateAveragePowerReduction = ->
        surveyDTOService.getEffectiveReductionDTO (effectiveReductionDTO) ->
            apr = effectiveReductionDTO.reductions[0].averagePowerReduction
            if (apr > 0)
                $scope.averagePowerReduction = $filter("number")(apr, 0)
            return

    $scope.onLoad = ->
        updateTopics()
        updateAveragePowerReduction()

    $scope.onTopicClose = ->
        updateTopics()
        updateAveragePowerReduction()
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()
        return

    $scope.isTopicDisabled = (topicKey) ->
        return $scope.topics[topicKey].disabled

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    $scope.onLoad()
