# simple download service
angular
.module('app.services')
.service "conditionService", (surveyDTOService) ->

    testBooleanAnswerEquals = (questionKey, periodKey, value) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue
        if (answerValue == null)
            return false
        return answerValue == value

    testStringAnswerValueNotEquals = (questionKey, periodKey, value) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue
        if (answerValue == null)
            return false
        return answerValue != value

    testNumericValueGreaterThan = (questionKey, periodKey, value) ->
        answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).doubleValue
        if (answerValue == null)
            return false
        return  answerValue > value


    @checkCondition = (questionKey) ->
        if (questionKey == "Q3210")
            return true
        if (questionKey == "Q3211")
            res = testBooleanAnswerEquals("Q3210", null, true)
            console.log("res", res)
            return res
        if (questionKey == "Q3110")
            return (testStringAnswerValueNotEquals("Q1110", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1110", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1110", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3120")
            return (testStringAnswerValueNotEquals("Q1120", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1120", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1120", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3130")
            return (testStringAnswerValueNotEquals("Q1130", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1130", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1130", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", "4")
        if (questionKey == "Q3310")
            return testNumericValueGreaterThan("Q1600", 0) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3320")
            return testNumericValueGreaterThan("Q1900", 0) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3330")
            return testStringAnswerValueNotEquals("Q1210", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3410")
            return testStringAnswerValueNotEquals("Q1220", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3420")
            return testStringAnswerValueNotEquals("Q1230", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3510")
            return testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3530")
            return testNumericValueGreaterThan("Q1800", 0) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3610")
            return testStringAnswerValueNotEquals("Q2010", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3620")
            return testStringAnswerValueNotEquals("Q2020", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3630")
            return testStringAnswerValueNotEquals("Q1700", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3631")
            return testBooleanAnswerEquals("Q3630", null, true) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3640")
            return (testStringAnswerValueNotEquals("Q1160", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1160", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1160", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3810")
            return testStringAnswerValueNotEquals("Q1235", null, "0")
        if (questionKey == "Q3710")
            return (testStringAnswerValueNotEquals("Q1140", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1140", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1140", "THIRD", "0") ||
                testStringAnswerValueNotEquals("Q1150", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1150", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1150", "THIRD", "0") ||
                testStringAnswerValueNotEquals("Q2030", null, "0") ||
                testStringAnswerValueNotEquals("Q2040", null, "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4")
        if (questionKey == "Q3711")
            return testBooleanAnswerEquals("Q3710", null, true)
        if (questionKey == "Q3720")
            return (testStringAnswerValueNotEquals("Q1140", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1140", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1140", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4") &&
                testStringAnswerValueNotEquals("Q3711", null, "4")
        if (questionKey == "Q3730")
            return (testStringAnswerValueNotEquals("Q1150", "FIRST", "0") ||
                testStringAnswerValueNotEquals("Q1150", "SECOND", "0") ||
                testStringAnswerValueNotEquals("Q1150", "THIRD", "0")) &&
                testStringAnswerValueNotEquals("Q3211", null, "4") &&
                testStringAnswerValueNotEquals("Q3711", null, "4")
        if (questionKey == "Q3750")
            return testStringAnswerValueNotEquals("Q2030", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4") &&
                testStringAnswerValueNotEquals("Q3711", null, "4")
        if (questionKey == "Q3760")
            return testStringAnswerValueNotEquals("Q2040", null, "0") &&
                testStringAnswerValueNotEquals("Q3211", null, "4") &&
                testStringAnswerValueNotEquals("Q3711", null, "4")
        if (questionKey == "Q3740")
            return testStringAnswerValueNotEquals("Q3211", null, "4") &&
                testStringAnswerValueNotEquals("Q3711", null, "4")
        if (questionKey == "Q3741")
            return testStringAnswerValueNotEquals("Q3740", null, "0")

    return
