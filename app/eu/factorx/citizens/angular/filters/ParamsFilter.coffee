angular
.module('app.filters')
.filter "params", (translateFilter) ->
    (input, params) ->
        for k,v of params
            input = input.replace(new RegExp('\\{' + k + '\\}', 'g'), v);
        return input
