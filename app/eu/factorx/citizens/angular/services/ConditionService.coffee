angular
.module('app.services')
.service "conditionService", (surveyDTOService) ->
    getNumericValue = (questionKey, periodKey) ->
        answer = surveyDTOService.getAnswerValue(questionKey, periodKey)
        if (answer.doubleValue != null)
            return answer.doubleValue
        else if (answer.stringValue != null && parseFloat(answer.stringValue))
            return parseFloat(answer.stringValue)
        return 0

    testAnswerIsTrue = (questionKey, periodKey) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue
        if (answerValue == null)
            return false
        return answerValue

    testAnswerIsFalse = (questionKey, periodKey) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue
        if (answerValue == null)
            return false
        return !answerValue

    testAnswerNotNull = (questionKey, periodKey) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue
        if (answerValue == null)
            return false
        return true

    testAnswerNotEquals = (questionKey, periodKey, stringValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue
        if (answerValue == null)
            return false
        return answerValue != stringValue

    testAnswerNotEqualsOrNull = (questionKey, periodKey, stringValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue
        if (answerValue == null)
            return true
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

    testAnswerIsLowerThan = (questionKey, periodKey, numericValue) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).doubleValue
        if (answerValue == null)
            return false
        return answerValue < numericValue

    testIsNotAlwaysOut = ->
        answerValue = surveyDTOService.getAnswerValue("Q3211", null).stringValue
        return (answerValue != "4")

    resetAnswerValues = (answers) ->
        for answer in answers
            for answerValue in answer.answerValues
                if !!answerValue.stringValue
                    answerValue.stringValue = null
                if !!answerValue.doubleValue
                    answerValue.doubleValue = null
                if !!answerValue.booleanValue
                    answerValue.booleanValue = null
        return

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
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q1235", null, "0")
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
                testAnswerNotEqualsOrNull("Q3711", null, "4")
        Q3730: ->
            return testIsNotAlwaysOut() &&
                testAnyAnswerNotEquals("Q1150", "0") &&
                testAnswerNotEqualsOrNull("Q3711", null, "4")
        Q3750: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2030", null, "0") &&
                testAnswerNotEqualsOrNull("Q3711", null, "4")
        Q3760: ->
            return testIsNotAlwaysOut() &&
                testAnswerNotEquals("Q2040", null, "0") &&
                testAnswerNotEqualsOrNull("Q3711", null, "4")
        Q3740: ->
            return testIsNotAlwaysOut()
        Q3741: ->
            return testAnswerNotEquals("Q3740", null, "0")

    #
    # check condition defining if a question can be displayed
    #
    @checkCondition = (questionKey) ->
        testFct = tests[questionKey]
        if !!testFct
            res = testFct()
            if res == false
                resetAnswerValues(surveyDTOService.getAnswers(questionKey))
            return res
        return true

    tooltips =
        Q1300: ->
            if testAnswerEquals('Q1300', null, '20')
                return 'Q1300.label.option20.warning'
            return null
        Q1400: ->
            if testAnswerEquals('Q1400', null, '5')
                return 'Q1400.option5.warning'
            return null
        Q1110:
        # display only on the first period
            FIRST: ->
                val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110',
                    'THIRD')
                if  val >= 5
                    return 'Q1110.label.toomany.warning'
                return null

            SECOND: ->
                val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110',
                    'THIRD')
                if  val >= 5
                    return 'Q1110.label.toomany.warning'
                return null

            THIRD: ->
                val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110',
                    'THIRD')
                if  val >= 5
                    return 'Q1110.label.toomany.warning'
                return null
        Q1800: ->
            if testAnswerIsLowerThan('Q1800', null, '10')
                return 'Q1800.low-value.warning'
            return null
        Q3420: ->
            if testAnswerNotNull('Q3420', null)
                return 'Q3420.tooltip'
            return null
        Q3510: ->
            if testAnswerNotNull('Q3510', null)
                return 'Q3510.tooltip'
            return null
        Q3740: ->
            if testAnswerNotNull('Q3740', null)
                return 'Q3740.tooltip'
            return null
        Q3211: ->
            if testAnswerNotNull('Q3211', null)
                return 'Q3211.tooltip'
            return null


    @getTooltip = (questionKey, periodKey = null) ->
        if periodKey == null
            return tooltips[questionKey]
        else if tooltips[questionKey]?
            return tooltips[questionKey][periodKey]
        return null


    return
