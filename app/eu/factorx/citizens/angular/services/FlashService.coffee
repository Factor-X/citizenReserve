# simple download service
angular
.module('app.services')
.service "$flash", ($filter) ->

    @success = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'success'
            showCloseButton: true

    @info = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'info'
            showCloseButton: true

    @error = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'error'
            showCloseButton: true

    @warning = (key) ->
        Messenger().post
            message: $filter('translate')(key)
            type: 'warning'
            showCloseButton: true

    return
