angular
.module('app.directives')
.directive "mmModalManager", (directiveService,$compile) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-modal-manager.html"
    replace: true
    link: (scope,element) ->

        # catch event
        scope.$on 'SHOW_MODAL',(event,args) ->
            if args.show == true
                scope.displayModal(args.target,args.params)
            else
                scope.removeModal(args.target)

            return

        # insert modal into html
        scope.displayModal = (target,params) ->


            paramName = 'params_'+target.replace(/-/g,"_")

            scope[paramName] = params


            console.log "display mode"
            console.log params
            console.log "<mm-modal-"+target+" ng-params=\""+paramName+"\" ></mm-modal-"+target+">"

            directive = $compile("<mm-modal-"+target+" ng-params=\""+paramName+"\" ></mm-modal-"+target+">")(scope)
            element.append(directive)

            return


        # remove when the modal is closed
        scope.removeModal = (target)->
            for child in element.children()
                if child.tagName.toLowerCase() == 'mm-modal-'+target.toLowerCase()
                    angular.element(child).remove()

            return
