angular
.module('app.filters')
.filter "toWatts", (translateFilter) ->
    (input) ->
        if input?
            return input + translateFilter('filter.toWatts.w.suffix')
