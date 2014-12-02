angular
.module('app.directives')
.directive "crTootipOnRespond", (directiveService, $filter) ->
    restrict: "A"
    require: 'ngModel'
    link: (scope, elem, attrs, ngModel) ->
        console.log scope

        scope.$$childHead.$watch 'ngModel', (n, o) ->
            if o == null and n != null

                Messenger().post
                    message: $filter('translate')(attrs.crTootipOnRespond)
                    type: 'info'
                    id: attrs.crTootipOnRespond
                    showCloseButton: true
                    singleton: true

        return