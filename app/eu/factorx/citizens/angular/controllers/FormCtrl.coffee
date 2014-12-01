angular
.module('app.controllers')
.controller "FormCtrl", ($scope, modalService, $log, topic, downloadService, surveyDTOService, conditionService,$location) ->
    $scope.topic = topic

    $scope.topicQuestions =
        profile:
            'presence': ['Q1300', 'Q1400', 'Q1500']
            'programs': ['Q1110', 'Q1120', 'Q1130']
            'dinner': ['Q1140', 'Q1150']
            'heating': ['Q1600', 'Q1900', 'Q1210']
            'lighting': ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750']

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


    $scope.openModal = (target) ->
        modalInstance = modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: 'ModalTopicCtrl',
            size: 'lg'
        })

    $scope.isQuestionAnswered = (questionKey) ->
        for answer in surveyDTOService.getAnswers(questionKey)
            for answerValue in answer.answerValues
                if (!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue)
                    return true
        return false

    $scope.isTopicCompleted = (topicIdentifier) ->
        topicQuestionKeys = $scope.topicQuestions.profile[topicIdentifier]
        for questionKey in topicQuestionKeys
            if !$scope.isQuestionAnswered(questionKey)
                return false
        return true
