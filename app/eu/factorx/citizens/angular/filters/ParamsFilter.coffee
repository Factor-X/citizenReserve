angular
.module('app.filters')
.filter "params", () ->
    (input, params) ->
        for k,v of params
            input = input.replace(new RegExp('\\{' + k + '\\}', 'g'), v);
        return input
