# simple download service
angular
.module('app.services')
.service "optionService", ($rootScope, $modal, gettextCatalog, $filter) ->

    options = {}

    @getNumericOptions = (questionKey,min, max, step) ->

        if options[questionKey]?
            return options[questionKey]

        optionList = [
            {
                value: null
                label: null
            }
        ]
        minF = parseFloat(min)
        maxF = parseFloat(max) + parseFloat(step)
        stepF = parseFloat(step)
        for element in _.range(minF, maxF, stepF)
            optionList.push {value: element+"", label: element+""}

        options[questionKey] = optionList

        return optionList

    @getOptions = (questionKey) ->
        if options[questionKey]?
            return options[questionKey]

        pattern = new RegExp(questionKey + "\.options\.*");

        list = _.filter(Object.keys(gettextCatalog.strings.fr), (key) ->
            return pattern.test(key)
        )

        list.sort()
        optionList = [
            {
                value: null
                label: null
            }
        ]

        for opt in list
            optionList.push {value: cleanKey(questionKey, opt), label: $filter('translate')(opt)}

        options[questionKey] = optionList

        return optionList

    cleanKey = (questionKey, key) ->
        pattern = new RegExp(questionKey + "\.options\.([^\.]+)\.label");
        match = pattern.exec(key);
        return match[1]
    return
