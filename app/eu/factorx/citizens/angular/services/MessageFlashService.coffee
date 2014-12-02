# simple download service
angular
.module('app.services')
.service "messageFlash", () ->

    #
    # display a success message
    @display = (type, message, opts) ->
        options =
            message: message
            type: type
            hideAfter: 5
            showCloseButton: true
        console.log "MessageFlash : type:"+type+"/content:"+message
        # TODO Messenger().post angular.extend(options, angular.copy(opts))

    #
    # display a success message
    @displaySuccess = (message, opts) ->
        @display('success', message, opts)

    #
    # display an info message
    @displayInfo = (message, opts) ->
        @display('info', message, opts)

    #
    # display an error message
    @displayError = (message, opts) ->
        @display('error', message, opts)

    return
