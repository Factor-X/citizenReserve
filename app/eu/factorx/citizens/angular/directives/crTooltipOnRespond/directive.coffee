angular
.module('app.directives')
.directive "crTootipOnRespond", (directiveService, $filter, $parse, conditionService) ->
    restrict: "A"
    require: 'ngModel'
    link: (scope, elem, attrs, ngModel) ->

        o=$parse(attrs.crTootipOnRespond)(scope)

        questionKey =o.key
        periodKey =o.period

        if conditionService.getTooltip(questionKey)?

            if $(elem).closest('.modal').length > 0
                scope.$lbl = conditionService.getTooltip(questionKey,periodKey)()
                scope.$oldLbl = scope.$lbl

            scope.$on '$destroy', () ->
                if scope.$trip
                    scope.$trip.stop()
                    scope.$trip = null

            scope.$$childHead.$watch 'ngModel', (n, o) ->
                scope.$lbl = conditionService.getTooltip(questionKey,periodKey)()
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