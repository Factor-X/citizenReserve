angular
.module('app.directives')
.directive "crAdminNavigationBar", () ->
    restrict: "E"
    scope:
        ngModel: '='
    templateUrl: "$/angular/templates/cr-admin-navigation-bar.html"
    replace:true
    controller: ($scope) ->

