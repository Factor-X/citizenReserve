angular
.module('app.directives')
.directive "crTopic", (directiveService, modalService, $log) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngActive: '='
        ngImage: '='
        ngView: '='
        ngController: '='
        ngWindowClass: '='

    templateUrl: "$/angular/templates/cr-topic.html"
    replace: true
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope


        scope.open = () ->

            console.log scope.getWindowClass()

            modalInstance = modalService.open({
                templateUrl: scope.getView(),
                controller: scope.getController(),
                size: 'lg'
                windowClass: scope.getWindowClass()
                resolve: {}
            });

            modalInstance.result.then (result) ->
                $log.info(result)
            , () ->
                $log.info('Modal dismissed at: ' + new Date())