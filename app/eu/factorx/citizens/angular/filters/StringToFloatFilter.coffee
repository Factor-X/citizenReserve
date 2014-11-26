angular
.module('app.filters')
.filter "stringToFloat", () ->
    (input) ->
        if input?
            return parseFloat(input)
