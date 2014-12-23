# simple download service
angular
.module('app.services')
.service "modalService", ($rootScope, $modal) ->
    @open = (parameters) ->
        return $modal.open(parameters)
    return
