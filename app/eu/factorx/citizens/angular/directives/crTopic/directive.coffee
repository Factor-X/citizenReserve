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
        ngLabel: '='
        ngCallback: '&'

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

            cb = scope.ngCallback
            if !cb
                cb = angular.noop
            modalInstance.result.then cb, cb