angular
.module('app.controllers')
.controller "ActionsCtrl", ($scope, modalService, surveyDTOService, downloadService, conditionService, $filter, $flash, $state, $stateParams) ->
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
            # a topic is enabled when at least one action can be purposed => we might check if at least one condition is true
            # but we have to check all conditions, in order to reset all answers related to hidden questions...
            topic.disabled = checkAllConditionsAreFalse(topic)
        return

    updateAveragePowerReduction = ->
        surveyDTOService.getEffectiveReductionDTO (effectiveReductionDTO) ->
            apr = effectiveReductionDTO.reductions[0].averagePowerReduction
            if (apr > 0)
                $scope.averagePowerReduction = $filter("number")(apr, 0)
            else
                $scope.averagePowerReduction = null
            return


    $scope.updateEnterprise = () ->
        $scope.loading = true
        downloadService.postJson '/registration', surveyDTOService.surveyDTO, (result) ->
            $scope.loading = false
            if result.success
                surveyDTOService.setAccount(result.data.account)
                $flash.success 'account.save.success'
                $state.go 'root.' + $state.current.resolve.instanceName() + 'Results'
            else
                $flash.error result.data.message

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

    $scope.toAccount = () ->
        $state.go 'root.' + $state.current.resolve.instanceName() + 'Account'

    $scope.onLoad()
