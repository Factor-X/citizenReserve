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

    $scope.onTopicClose = (topic) ->
        console.log("onTopicClose: " + topic)
        topic.completed = isTopicCompleted(topic)
        if topic.completed
            $scope.profileCompleted = isProfileCompleted()
            if $scope.profileCompleted
               $scope.getPotentialReduction()
        if surveyDTOService.isAuthenticated()
            surveyDTOService.saveSurvey()

    $scope.openModal = (target, controller = 'ModalTopicCtrl') ->
        modalService.open({
            templateUrl: '$/angular/views/' + target + '.html',
            controller: controller,
            size: 'lg'
        })

    isTopicCompleted = (topic) ->
        res = true
        for questionKey in topic.questions
            if !surveyDTOService.isQuestionCompleted(questionKey)
                res = false
                break
        return res

    isProdileCompleted = () ->
        res = true
        for topic of $scope.topics
            if !topic.completed
                res = false
                break
        console.log("isProfileCompleted = " + res)
        $scope.isCompleted = res
        return res

    $scope.getPotentialReduction = ->
        downloadService.postJson '/reduction/potential', surveyDTOService.surveyDTO, (result) ->
            if result.success
                $scope.potentialReduction = $filter("number") parseFloat(result.data.potentialReduction.averagePowerReduction), 0

    $scope.getPotentialReduction()

