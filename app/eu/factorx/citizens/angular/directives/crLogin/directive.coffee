angular
.module('app.directives')
.directive "crLogin", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/cr-login.html"
    replace: false
    link: (scope, elem, attrs) ->
