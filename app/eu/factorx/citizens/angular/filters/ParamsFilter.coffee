angular
.module('app.filters')
.filter "params", (translateFilter) ->
    (input, params) ->
        for k,v in params
            input = input.replace(new RegExp('\\{' + k + '\\}', 'g'), v);
        return input
