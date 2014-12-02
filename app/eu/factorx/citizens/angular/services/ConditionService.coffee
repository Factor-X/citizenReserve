angular
.module('app.services')
.service "conditionService", (surveyDTOService) ->
    svc = this
    hits = 0
    testAnswerIsTrue = (questionKey, periodKey) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue
        if (answerValue == null)
            return false
        return answerValue

    testAnswerNotEquals = (questionKey, periodKey, stringValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue
        if (answerValue == null)
            return false
        return answerValue != stringValue

    testAnswerEquals = (questionKey, periodKey, stringValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue
        if (answerValue == null)
            return false
        return answerValue == stringValue

    testAnyAnswerNotEquals = (questionKey, stringValue) ->
        for periodKey in ["FIRST", "SECOND", "THIRD"]
            if testAnswerNotEquals(questionKey, periodKey, stringValue)
                return true
        return false

    testAnswerIsGreaterThan = (questionKey, periodKey, numericValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).doubleValue
        if (answerValue == null)
            return false
        return answerValue > numericValue

    testIsNotAlwaysOut = ->
        answerValue = surveyDTOService.getAnswerValue("Q3211", null).stringValue
        return (answerValue != "4")

    resetAnswerValues = (answers) ->
        for answer in answers
            for answerValue in answer.answerValues
                if !!answerValue.stringValue
                    console.log("reset stringValue")
                    answerValue.stringValue = null
                if !!answerValue.doubleValue
                    console.log("reset doubleValue")
                    answerValue.doubleValue = null
                if !!answerValue.booleanValue
                    console.log("reset booleanValue")
                    answerValue.booleanValue = null

    tests =
        Q3211: ->
            return testAnswerIsTrue("Q3210", null)
        Q3110: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1110", "0")
        Q3120: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1120", "0")
        Q3130: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1130", "0")
        Q3310: ->
            return testIsNotAlwaysOut() &&
                testAnswerIsGreaterThan("Q1600", null, 0)
        Q3320: ->
            return testIsNotAlwaysOut() &&
                testAnswerIsGreaterThan("Q1900", null, 0)
        Q3330: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q1210", null, "0")
        Q3410: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q1220", null, "0")
        Q3420: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q1230", null, "0")
        Q3510: ->
            return testIsNotAlwaysOut()
        Q3530: ->
            return testIsNotAlwaysOut() &&
                testAnswerIsGreaterThan("Q1800", null, 0)
        Q3610: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2010", null, "0")
        Q3620: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2020", null, "0")
        Q3630: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q1700", null, "0")
        Q3631: ->
            return testIsNotAlwaysOut() &&
                testAnswerIsTrue("Q3630", null)
        Q3640: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1160", "0")
        Q3810: ->
            return testAnswerNotEquals("Q1235", null, "0")
        Q3710: ->
            return testIsNotAlwaysOut() &&
                (testAnyAnswerNotEquals("Q1140", "0") ||
                    testAnyAnswerNotEquals("Q1150", "0") ||
                    testAnswerNotEquals("Q2030", null, "0") ||
                    testAnswerNotEquals("Q2040", null, "0"))
        Q3711: ->
            return testAnswerIsTrue("Q3710", null)
        Q3720: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1140", "0") &&
                testAnswerNotEquals("Q3711", null, "4")
        Q3730: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1150", "0") &&
                testAnswerNotEquals("Q3711", null, "4")
        Q3750: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2030", null, "0") &&
                testAnswerNotEquals("Q3711", null, "4")
        Q3760: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2040", null, "0") &&
                testAnswerNotEquals("Q3711", null, "4")
        Q3740: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q3711", null, "4")
        Q3741: ->
            return testAnswerNotEquals("Q3740", null, "0")

    #
    # check condition defining if a question can be displayed
    #
    @checkCondition = (questionKey) ->
        hits++;
        console.log("checkCondition for questionKey = " + questionKey)
        console.log("checkCondition nb hits = " + hits)
        testFct = tests[questionKey]
        if !!testFct
            res = testFct()
            if res == false
                resetAnswerValues(surveyDTOService.getAnswers(questionKey))
            return res
        return true


    tooltips =
        Q1400: ->
            if testAnswerEquals('Q1400', null, '5')
                return 'Q1400.option5.warning'
            return null

    @getTooltip = (questionKey) ->
        return tooltips[questionKey]


    return
