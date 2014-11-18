# simple download service
angular
.module('app.services')
.service "modalService", ($rootScope) ->

    @TEST = 'test'

    @show = (modalName,params) ->

        console.log 'et plop ? '

        args = []
        args.show = true
        args.params = params
        args.target = modalName
        $rootScope.displayModalBackground = true
        $rootScope.$broadcast('SHOW_MODAL', args)

    @close = (modalName) ->

        args = []
        args.show = false
        args.target = modalName
        $rootScope.displayModalBackground = false
        $rootScope.$broadcast('SHOW_MODAL', args)

    return