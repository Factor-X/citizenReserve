angular
.module('app.directives')
.directive "crDropdown", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabel: '='
        ngOptions: '='
        ngModel: '='
    templateUrl: "$/angular/templates/cr-dropdown.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.computeOptionLabel = (o) ->
            return undefined unless o
            if o.icon
                return '<i class=\'fa fa-' + o.icon + ' cr-dropdown-icon\'></i>' + o.label
            else
                return o.label