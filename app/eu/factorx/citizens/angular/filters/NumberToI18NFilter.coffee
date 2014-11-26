angular
.module('app.filters')
.filter "numberToI18N", ($filter) ->
    (input, nbDecimal = 2) ->
        if input?
            return $filter("number") parseFloat(input), nbDecimal
        return ""
