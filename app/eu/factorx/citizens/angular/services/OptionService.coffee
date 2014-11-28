# simple download service
angular
.module('app.services')
.service "optionService", ($rootScope, $modal, gettextCatalog, $filter) ->

    options = {}

    @getOptions = (questionKey) ->

        if options[questionKey]?
            return options[questionKey]

        pattern = new RegExp(questionKey + "\.options\.*");

        list = _.filter(Object.keys(gettextCatalog.strings.fr), (key) ->
            return pattern.test(key)
        )

        list.sort()
        optionList = []

        for opt in list
            optionList.push {value: cleanKey(questionKey,opt), label: $filter('translate')(opt)}

        options[questionKey] = optionList

        return optionList

    cleanKey = (questionKey,key) ->
        pattern = new RegExp(questionKey + "\.options\.([^\.]+)\.label");
        match = pattern.exec(key);
        return match[1]
    return
