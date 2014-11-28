angular
.module('app.filters')
.filter "toSquareMeters", (translateFilter) ->
    (input) ->
        if input?
            return input + translateFilter('filter.toSquareMeters.m2.suffix')
