angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $filter, $log, downloadService, surveyDTOService, conditionService, $location, $flash) ->
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

    $scope.save = () ->
        if surveyDTOService.isAuthenticated()
            downloadService.postJson '/survey/update', surveyDTOService.surveyDTO, (result) ->
                if result.success
                    $flash.success 'account.save.success'
                else
                    $flash.error result.data.message

    $scope.isAuthenticated = ->
        return surveyDTOService.isAuthenticated()

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

