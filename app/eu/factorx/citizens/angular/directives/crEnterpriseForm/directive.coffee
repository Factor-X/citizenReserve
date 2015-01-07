angular
.module('app.directives')
.directive "crEnterpriseForm", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestions: '='
        ngModel: '='
    templateUrl: "$/angular/templates/cr-enterprise-form.html"
    replace: false
    link: (scope, elem, attrs, ngModel) ->
        directiveService.autoScopeImpl scope
