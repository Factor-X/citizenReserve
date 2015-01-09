# simple download service
angular
.module('app.services')
.service "$flash", ($filter) ->
    @success = (key, params) ->
        Messenger().post
            message: $filter('params')($filter('translate')(key), params)
            type: 'success'
            showCloseButton: true
            id: key

    @info = (key) ->
        Messenger().post
            message: $filter('params')($filter('translate')(key), params)
            type: 'info'
            showCloseButton: true
            id: key

    @error = (key) ->
        Messenger().post
            message: $filter('params')($filter('translate')(key), params)
            type: 'error'
            showCloseButton: true
            id: key

    @warning = (key) ->
        Messenger().post
            message: $filter('params')($filter('translate')(key), params)
            type: 'warning'
            showCloseButton: true
            id: key

    return
