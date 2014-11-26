# simple download service
angular
.module('app.services')
.config(($modalProvider) ->
    angular.extend($modalProvider.defaults, {
        html: true
    })
)
.service "modalService", ($rootScope, $modal) ->
    @open = (modalName, scope) ->
        myModal = $modal({template: '$/angular/views/' + modalName + '.html', scope: scope})

    return