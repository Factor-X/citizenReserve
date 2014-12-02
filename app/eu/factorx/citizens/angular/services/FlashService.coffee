# simple download service
angular
.module('app.services')
.service "$flash", ($filter) ->

    @success = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'success'


    @info = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'info'

    @error = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'error'

    @warning = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'warning'

    return