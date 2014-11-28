angular
.module('app.filters')
.filter "toHour", (translateFilter) ->
    (input) ->
        if input?
            return input + translateFilter('filter.toHour.hour.suffix')
