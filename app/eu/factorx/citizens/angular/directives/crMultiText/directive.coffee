angular
.module('app.directives')
.directive "crMultiText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngRegex: '='
    require: 'ngModel'
    templateUrl: "$/angular/templates/cr-multi-text.html"
    replace: true
    link: (scope, elem, attrs) ->
        directiveService.autoScopeImpl scope

        scope.getRange = () ->
            if !scope.ngModel
                return [0]
            else
                return _.range(scope.ngModel.length + 1)

        scope.validation = []


        scope.$watch 'ngModel', (n, o) ->
            scope.ngModel = _.reject(scope.ngModel, (e) ->
                return !e
            )
            scope.validation = []
            if scope.getRegex()
                r = new RegExp(scope.getRegex())
                for k,v of scope.ngModel
                    scope.validation[k] = r.test(v)
            else
                for k,v of scope.ngModel
                    scope.validation[k] = true

            scope.validation[scope.ngModel.length] = true

        , true
