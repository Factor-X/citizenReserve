angular
.module('app.directives')
.directive "crTootipOnRespond", (directiveService, $filter, $parse, conditionService) ->
    restrict: "A"
    require: 'ngModel'
    link: (scope, elem, attrs, ngModel) ->
        console.log scope

        if $(elem).closest('.modal').length > 0
            scope.$lbl = conditionService.getTooltip(attrs.crTootipOnRespond)()
            scope.$oldLbl = scope.$lbl

        scope.$on '$destroy', () ->
            if scope.$trip
                scope.$trip.stop()
                scope.$trip = null

        scope.$$childHead.$watch 'ngModel', (n, o) ->
            scope.$lbl = conditionService.getTooltip(attrs.crTootipOnRespond)()
            if scope.$lbl != scope.$oldLbl
                if scope.$trip
                    scope.$trip.stop()
                    scope.$trip = null

                if scope.$lbl
                    scope.$trip = new Trip([
                        {
                            sel: $(elem),
                            content: $filter('translate')(scope.$lbl),
                            position: 'w'
                            delay: 10000
                            animation: 'bounceInLeft'
                            showCloseBox: true
                        }
                    ], {});
                    scope.$trip.start();
                scope.$oldLbl = scope.$lbl

        return