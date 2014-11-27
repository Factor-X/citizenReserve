angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui-rangeSlider']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'ngLocale', 'gettext']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module('app').run(function(gettextCatalog) {
  gettextCatalog.setCurrentLanguage('fr');
  return gettextCatalog.loadRemote("/translations");
});
angular.module('app.controllers').config(function($routeProvider) {
  $routeProvider.when('/test', {
    templateUrl: '$/angular/views/form.html',
    controller: 'FormCtrl'
  }).otherwise({
    redirectTo: '/'
  });
  return;
});angular.module('app.services').service("modalService", function($rootScope, $modal) {
  this.open = function(parameters) {
    return $modal.open(parameters);
  };
  return;
});angular.module('app.services').service("downloadService", function($http, $q, messageFlash) {
  this.downloadsInProgress = 0;
  this.getDownloadsInProgress = function() {
    return this.downloadsInProgress;
  };
  this.getJson = function(url, callback) {
    var deferred, promise;
    console.log("GET URL TO " + url);
    deferred = $q.defer();
    this.downloadsInProgress++;
    promise = $http({
      method: "GET",
      url: url,
      headers: {
        "Content-Type": "application/json",
        "Pragma": "no-cache"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(data);
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  this.postJson = function(url, data, callback, options) {
    var deferred, promise;
    console.log("POST URL TO " + url);
    deferred = $q.defer();
    if (data === null) {
      data = {};
    }
    this.downloadsInProgress++;
    promise = $http({
      method: "POST",
      url: url,
      data: data,
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json,application/octet-stream"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(data);
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  return;
});angular.module('app.services').service("messageFlash", function() {
  this.display = function(type, message, opts) {
    var options;
    options = {
      message: message,
      type: type,
      hideAfter: 5,
      showCloseButton: true
    };
    return Messenger().post(angular.extend(options, angular.copy(opts)));
  };
  this.displaySuccess = function(message, opts) {
    return this.display('success', message, opts);
  };
  this.displayInfo = function(message, opts) {
    return this.display('info', message, opts);
  };
  this.displayError = function(message, opts) {
    return this.display('error', message, opts);
  };
  return;
});angular.module('app.services').service("generateId", function($rootScope) {
  this.generate = function() {
    var i, possible, text;
    text = "";
    possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    i = 0;
    while (i < 20) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
      i++;
    }
    return text;
  };
  return;
});angular.module('app.services').service("directiveService", function($sce) {
  this.autoScope = function(s) {
    var k, res, v;
    res = {};
    for (k in s) {
      v = s[k];
      res[k] = v;
      if (k.slice(0, 2) === 'ng' && v === '=') {
        res[k[2].toLowerCase() + k.slice(3)] = '@';
      }
    }
    return res;
  };
  this.autoScopeImpl = function(s, name) {
    var fget, key, val;
    s.$$NAME = name;
    for (key in s) {
      val = s[key];
      if (key.slice(0, 2) === 'ng') {
        fget = function(scope, k) {
          return function() {
            var v;
            v = 0;
            if (scope[k] === void 0 || scope[k] === null || scope[k] === '') {
              v = scope[k[2].toLowerCase() + k.slice(3)];
            } else {
              v = scope[k];
            }
            if (scope['decorate' + k.slice(2)]) {
              return scope['decorate' + k.slice(2)](v);
            } else {
              return v;
            }
          };
        };
        s['get' + key.slice(2)] = fget(s, key);
      }
    }
    s.isTrue = function(v) {
      return v === true || v === 'true' || v === 'y';
    };
    s.isFalse = function(v) {
      return v === false || v === 'false' || v === 'n';
    };
    s.isNull = function(v) {
      return v === null;
    };
    return s.html = function(v) {
      return $sce.trustAsHtml(v);
    };
  };
  return;
});angular.module('app.filters').filter("stringToFloat", function() {
  return function(input) {
    if (input != null) {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("numberToI18N", function($filter) {
  return function(input, nbDecimal) {
    if (nbDecimal == null) {
      nbDecimal = 2;
    }
    if (input != null) {
      return $filter("number")(parseFloat(input), nbDecimal);
    }
    return "";
  };
});angular.module('app.directives').directive("crRadio", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngModel: '=',
      ngOptions: '=',
      ngFreeAllowed: '='
    }),
    templateUrl: "$/angular/templates/cr-radio.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      scope.setValue = function(v) {
        return scope.ngModel = v;
      };
      scope.isValue = function(v) {
        return scope.ngModel == v;
      };
      scope.$watch('ngOptions', function(n, o) {
        var element, _i, _len, _results;
        console.log(n);
        scope.computedOptions = [];
        _results = [];
        for (_i = 0, _len = n.length; _i < _len; _i++) {
          element = n[_i];
          _results.push(typeof element === 'object' ? scope.computedOptions.push(element) : scope.computedOptions.push({
            value: element,
            label: element
          }));
        }
        return _results;
      });
      return scope.toggle = function() {
        var o, _i, _len, _ref;
        scope.edit = !scope.edit;
        if (!scope.edit) {
          if (scope.ngOptions.length > 0) {
            _ref = scope.ngOptions;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              o = _ref[_i];
              if (o.value == scope.ngModel) {
                return;
              }
            }
            return scope.ngModel = scope.ngOptions[0].value;
          }
        }
      };
    }
  };
});angular.module('app.directives').directive("crNumber", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngModel: '='
    }),
    require: 'ngModel',
    templateUrl: "$/angular/templates/cr-number.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      ngModel.$parsers.push(function(inputValue) {
        var firstParse, max, min, n, prepParse, returnValue, safeParse, secondParse, transformedInput;
        if (inputValue == null) {
          return "";
        }
        firstParse = inputValue.replace(/[^0-9 . -]/g, "");
        safeParse = firstParse.charAt(0);
        prepParse = firstParse.substring(1, firstParse.length);
        secondParse = safeParse + prepParse.replace(/[^0-9 .]/g, "");
        n = secondParse.indexOf(".");
        transformedInput = void 0;
        if (n === -1) {
          transformedInput = secondParse;
        } else {
          safeParse = secondParse.substring(0, n + 1);
          firstParse = (secondParse.substring(n + 1, secondParse.length)).replace(/[^0-9]/g, "");
          n = 2;
          if (firstParse.length <= n) {
            transformedInput = safeParse + firstParse;
          } else {
            transformedInput = safeParse + firstParse.substring(0, n);
          }
        }
        min = parseInt(attrs.minvalue);
        max = parseInt(attrs.maxvalue);
        if (transformedInput !== inputValue || transformedInput < min || transformedInput > max) {
          returnValue = void 0;
          if (transformedInput < min || transformedInput > max) {
            returnValue = transformedInput.substring(0, transformedInput.length - 1);
          } else {
            returnValue = transformedInput;
          }
          ngModel.$setViewValue(returnValue);
          ngModel.$render();
        }
        returnValue;
        return;
      });
      ngModel.$formatters.push(function(value) {
        return 0 + parseInt(value);
      });
      scope.g = function() {
        return typeof scope.ngModel;
      };
      return scope;
    }
  };
});angular.module('app.directives').directive("crSlider", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngModel: '=',
      ngSteps: '=',
      ngFreeAllowed: '=',
      ngMin: '=',
      ngMax: '=',
      ngStep: '='
    }),
    templateUrl: "$/angular/templates/cr-slider.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      scope.setValue = function(v) {
        return scope.ngModel = v.value;
      };
      scope.isValue = function(v) {
        var indexOfNgModel, indexOfV, o, _i, _j, _len, _len2, _ref, _ref2;
        return scope.ngModel === v.value;
        indexOfNgModel = -1;
        _ref = scope.computedOptions;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          o = _ref[_i];
          if (o.value == scope.ngModel) {
            break;
          } else {
            indexOfNgModel++;
          }
        }
        indexOfV = -1;
        _ref2 = scope.computedOptions;
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          o = _ref2[_j];
          if (o.value == v.value) {
            break;
          } else {
            indexOfV++;
          }
        }
        if (indexOfNgModel === -1 || indexOfV === -1) {
          return false;
        } else {
          return indexOfV <= indexOfNgModel;
        }
      };
      scope.$watch(['ngSteps', 'ngMax', 'ngMin', 'ngStep', 'max', 'min', 'step'], function(n, o) {
        var element, max, min, step, _i, _j, _len, _len2, _ref, _ref2, _results, _results2;
        scope.computedOptions = [];
        if (scope.ngSteps) {
          _ref = scope.ngSteps;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            element = _ref[_i];
            _results.push(element === null ? scope.computedOptions.push({
              value: element,
              label: element
            }) : typeof element === 'object' ? scope.computedOptions.push(element) : scope.computedOptions.push({
              value: element,
              label: element
            }));
          }
          return _results;
        } else if (scope.getMin() !== void 0 && scope.getMax() !== void 0 && scope.getStep() !== void 0) {
          scope.computedOptions.push({
            value: null,
            label: null
          });
          min = parseFloat(scope.getMin());
          max = parseFloat(scope.getMax()) + parseFloat(scope.getStep());
          step = parseFloat(scope.getStep());
          _ref2 = _.range(min, max, step);
          _results2 = [];
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            element = _ref2[_j];
            _results2.push(scope.computedOptions.push({
              value: element,
              label: element
            }));
          }
          return _results2;
        }
      });
      $(elem).mouseenter(function() {
        var $scroller;
        $scroller = $(".cr-slider-not-null-box", this);
        return $scroller.stop(true);
      });
      return $(elem).mouseleave(function() {
        var $scroller, cnt, idx, o, offset, target, w, _i, _len, _ref;
        $scroller = $(".cr-slider-not-null-box", this);
        w = $scroller[0].scrollWidth;
        idx = -1;
        _ref = scope.computedOptions;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          o = _ref[_i];
          if (o.value == scope.ngModel) {
            break;
          }
          idx++;
        }
        cnt = scope.computedOptions.length - 1;
        offset = 1.0 * idx / cnt;
        target = w * offset - $scroller.width() / 2;
        if (target < 0) {
          target = 0;
        }
        if (target > w - $scroller.width() / 2 - 50) {
          target = w - $scroller.width() / 2 - 50;
        }
        console.log(target);
        console.log($scroller.scrollLeft());
        if (Math.abs(target - $scroller.scrollLeft()) > 1) {
          return $scroller.delay(1000).animate({
            scrollLeft: target
          }, {
            easing: 'easeInOutBack'
          });
        }
      });
    }
  };
});angular.module('app.directives').directive("crDropdown", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngOptions: '=',
      ngModel: '=',
      ngDisabled: '=',
      ngOpened: '='
    }),
    templateUrl: "$/angular/templates/cr-dropdown.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.$select = function(o) {
        return scope.ngModel = o;
      };
      return scope.$opened = function() {
        console.log('ok');
        return !!scope.getOpened();
      };
    }
  };
});angular.module('app.directives').directive("focusMe", function($timeout, $parse) {
  return {
    restrict: 'A',
    scope: {
      focusMe: '='
    },
    link: function(scope, element, attrs) {
      scope.$watch('focusMe', function() {
        if (scope.focusMe === true) {
          return element[0].focus();
        }
      });
      return;
    }
  };
});angular.module('app.directives').directive("mmFieldText", function(directiveService, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-field-text.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          return directiveService.autoScopeImpl(scope);
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.isValidationDefined = (scope.getInfo().validationRegex != null) || (scope.getInfo().validationFct != null);
          scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon;
          scope.fieldType = (scope.getInfo().fieldType != null) ? scope.getInfo().fieldType : "text";
          if (!(scope.getInfo().field != null)) {
            scope.getInfo().field = "";
          }
          if (!(scope.getInfo().isValid != null)) {
            scope.getInfo().isValid = !scope.isValidationDefined;
          }
          if (scope.isValidationDefined) {
            scope.$watch('getInfo().field', function(n, o) {
              if (n !== o) {
                return scope.isValid();
              }
            });
          }
          scope.isValid = function() {
            var isValid;
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            if (!scope.getInfo().field) {
              scope.getInfo().field = "";
            }
            /*
            scope.getInfo().isValid = false
            return
            */
            isValid = true;
            if (typeof scope.getInfo().field !== 'string') {
              scope.getInfo().field += "";
            }
            if (scope.getInfo().validationRegex != null) {
              isValid = scope.getInfo().field.match(scope.getInfo().validationRegex);
            }
            if (scope.getInfo().validationFct != null) {
              isValid = isValid && scope.getInfo().validationFct();
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          scope.logField = function() {
            return console.log(scope.getInfo());
          };
          scope.errorMessage = "";
          return scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("numbersOnly", function($filter, $locale) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      var convertToFloat, convertToString, displayError, errorMessage, filterFloat, nbDecimal;
      if (attrs.numbersOnly === "integer" || attrs.numbersOnly === "double") {
        if (attrs.numbersOnly === "integer") {
          errorMessage = 'Only integer';
        } else {
          errorMessage = 'Only number';
        }
        nbDecimal = 3;
        if (attrs.numbersOnly === "integer") {
          nbDecimal = 0;
        }
        scope.$root.$on('$localeChangeSuccess', function(event, current, previous) {
          var result;
          if (modelCtrl.$modelValue != null) {
            result = convertToString(parseFloat(modelCtrl.$modelValue));
            if (result != null) {
              modelCtrl.$setViewValue(result.toString());
              return modelCtrl.$render();
            }
          }
        });
        modelCtrl.$parsers.unshift(function(viewValue) {
          var result, resultString, resultToDisplay;
          if (viewValue === "") {
            return null;
          }
          result = convertToFloat(viewValue);
          if (isNaN(result)) {
            displayError();
            if (scope.lastValidValue != null) {
              resultString = scope.lastValidValue.toString();
              if (attrs.numbersOnly === "percent") {
                resultToDisplay = (scope.lastValidValue * 100).toString();
              } else {
                resultToDisplay = scope.lastValidValue.toString();
              }
            } else {
              resultString = "";
              resultToDisplay = "";
            }
            modelCtrl.$setViewValue(resultToDisplay);
            modelCtrl.$render();
          } else {
            if (attrs.numbersOnly === "percent") {
              result = result / 100;
            }
            scope.lastValidValue = result;
            resultString = result.toString();
          }
          if (resultString === "") {
            return null;
          }
          return resultString;
        });
        modelCtrl.$formatters.unshift(function(modelValue) {
          var result;
          result = parseFloat(modelValue);
          if (attrs.numbersOnly === "percent") {
            result = result * 100;
          }
          return convertToString(result);
        });
        displayError = function() {
          if (scope.setErrorMessage != null) {
            return scope.setErrorMessage(errorMessage);
          }
        };
        convertToString = function(value) {
          var formats, result;
          if (!(value != null) || isNaN(value)) {
            return "";
          }
          value = value.toFixed(nbDecimal);
          formats = $locale.NUMBER_FORMATS;
          return result = value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP);
        };
        convertToFloat = function(viewValue) {
          var decimalRegex, formats, value;
          if (viewValue === "") {
            return NaN;
          }
          formats = $locale.NUMBER_FORMATS;
          decimalRegex = formats.DECIMAL_SEP;
          if (decimalRegex === ".") {
            decimalRegex = "\\.";
          }
          value = viewValue.replace(new RegExp(decimalRegex, "g"), ".");
          return filterFloat(value);
        };
        filterFloat = function(value) {
          var regexFloat;
          if (attrs.numbersOnly === "integer") {
            regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)$");
          } else {
            regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)$");
          }
          if (regexFloat.test(value)) {
            return Number(value);
          }
          return NaN;
        };
        return scope.$root.$on('FORM_LOADING_FINISH', function(event, current, previous) {
          if (modelCtrl.$modelValue != null) {
            return scope.lastValidValue = modelCtrl.$modelValue;
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("ngEscape", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 27) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEscape);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmFieldDate", function(directiveService, $filter, generateId) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-field-date.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          scope.id = generateId.generate();
          return scope.idHtag = '#' + scope.id;
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.result = null;
          scope.$watch('result', function() {
            return scope.resultFormated = $filter('date')(scope.result, 'yyyy-MM-dd');
          });
          scope.$watch('getInfo().field', function() {
            if (scope.getInfo().field != null) {
              return scope.result = new Date(Number(scope.getInfo().field));
            }
          });
          scope.$watch('result', function() {
            if (scope.result != null) {
              scope.getInfo().field = scope.result.getTime();
            } else {
              scope.getInfo().field = null;
            }
            return scope.isValid();
          });
          scope.isValid = function() {
            var isValid;
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            isValid = true;
            if (!(scope.getInfo().field != null)) {
              isValid = false;
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          return scope.logField = function() {
            return console.log(scope.getInfo());
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("ngEnter", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 13) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEnter);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmFieldAutoCompletion", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-field-auto-completion.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.result = null;
      scope.$watch('result', function() {
        if (scope.result != null) {
          scope.getInfo().field = scope.result.originalObject.key;
        } else {
          scope.getInfo().field = null;
        }
        return scope.isValid();
      });
      scope.isValid = function() {
        var isValid;
        if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
          scope.getInfo().isValid = true;
          return;
        }
        isValid = true;
        if (!(scope.getInfo().field != null)) {
          isValid = false;
        }
        scope.getInfo().isValid = isValid;
        return;
      };
      scope.isValid();
      return scope.logField = function() {
        return console.log(scope.getInfo());
      };
    }
  };
});angular.module('app.directives').directive("crDoubleRange", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngRangeMin: '=',
      ngRangeMax: '=',
      ngMin: '=',
      ngMax: '=',
      ngDisabled: '='
    }),
    templateUrl: "$/angular/templates/cr-double-range.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.controllers').controller("MainCtrl", function($scope, modalService, $log) {
  $scope.x = {
    sel: 'Human',
    items: ['Human', 'Bat', '-', 'Vampire'],
    cnt: 10,
    doubleRange: {
      min: 2,
      max: 3,
      rangeMin: 0,
      rangeMax: 5
    },
    radio: {
      value: null,
      options: [
        {
          value: 0,
          label: 'OPT_0'
        }, {
          value: 1,
          label: 'OPT_1'
        }, {
          value: 2,
          label: 'OPT_2'
        }, {
          value: 3,
          label: 'OPT_3'
        }, {
          value: 4,
          label: 'OPT_4'
        }
      ],
      simpleOptions: [0, 1, 2, 3, 4]
    },
    slider: {
      value: null,
      steps: [
        {
          value: null,
          label: null
        }, {
          value: 18,
          label: '18h'
        }, {
          value: 19,
          label: '19h'
        }, {
          value: 20,
          label: '20h'
        }, {
          value: 21,
          label: '21h'
        }, {
          value: 22,
          label: '22h'
        }
      ],
      simpleSteps: [null, 18, 19, 20, 21, 22]
    },
    continuousSlider: {
      value: 100,
      step: 10,
      min: 20,
      max: 2000
    },
    grid: [
      {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?2165438536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216548536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216438536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216543836158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216543853615851968161'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/128/512?3216543853615519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3215438536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216543853615851981651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216548536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216543536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/800?3165438536158519681651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3216543853615851981651'
      }, {
        title: 'AAA',
        content: 'Hello world',
        image: 'http://placekitten.com/512/512?3215438536158519681651'
      }
    ]
  };
  return $scope.$watch('x.sel', function(n, o) {
    var modalInstance;
    if (n === $scope.x.items[3]) {
      modalInstance = modalService.open({
        templateUrl: '$/angular/views/modal-confirm-vampire.html',
        controller: 'NiceModalCtrl',
        size: 'sm',
        resolve: {
          chosenValue: function() {
            return $scope.x.sel;
          }
        }
      });
      return modalInstance.result.then(function(result) {
        return $log.info(result);
      }, function() {
        $scope.x.sel = o;
        return $log.info('Modal dismissed at: ' + new Date());
      });
    }
  });
});angular.module('app.controllers').controller("NiceModalCtrl", function($scope, $modalInstance, chosenValue) {
  $scope.selected = chosenValue;
  $scope.ok = function() {
    return $modalInstance.close($scope.selected);
  };
  return $scope.cancel = function() {
    return $modalInstance.dismiss('cancel');
  };
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/templates/cr-dropdown.html', "<div class=\"cr-dropdown\">\n\n\n    <label class=\"cr-dropdown-label\" ng-bind-html=\"getLabel() | translate\"></label>\n\n    <!-- Single button -->\n    <div class=\"btn-group cr-dropdown-control\"\n         dropdown>\n\n        <button type=\"button\"\n                class=\"btn btn-default dropdown-toggle cr-dropdown-button\"\n                dropdown-toggle\n                ng-disabled=\"getDisabled()\">\n            {{ ngModel }} <span class=\"caret\"></span>\n        </button>\n\n        <ul class=\"dropdown-menu cr-dropdown-menu\"\n            role=\"menu\">\n            <li ng-repeat=\"o in ngOptions\" class=\"cr-dropdown-menu-item\" ng-class=\"{divider: (o == '-')}\">\n                <a class=\"cr-dropdown-menu-item-link\" ng-if=\"o != '-'\" ng-click=\"$select(o)\">{{ o }}</a>\n            </li>\n        </ul>\n    </div>\n\n</div>");$templateCache.put('$/angular/templates/cr-double-range.html', "<div class=\"cr-range\">\n\n    <label class=\"cr-range-label\" ng-bind-html=\"getLabel() | translate\"></label>\n\n\n    <div range-slider min=\"ngRangeMin\" max=\"ngRangeMax\" model-min=\"ngMin\" model-max=\"ngMax\" step=\"ngStep\"\n         disabled=\"ngDisabled\"></div>\n\n</div>");$templateCache.put('$/angular/templates/cr-radio.html', "<div class=\"cr-radio\">\n\n    <label class=\"cr-radio-label\" ng-bind-html=\"getLabel() | translate\"></label>\n\n    <div class=\"btn-group\" ng-hide=\"edit\">\n        <button class=\"btn btn-default  \"\n                ng-class=\"{active: isValue(o.value)}\"\n                ng-repeat=\"o in computedOptions\"\n                ng-click=\"setValue(o.value)\"\n                ng-bind-html=\"('' + o.label) | translate\"\n                ></button>\n\n        <button ng-if=\"getFreeAllowed() == 'true'\"\n                class=\"btn btn-danger\"\n                ng-click=\"toggle()\">\n            <span class=\"fa fa-pencil\"></span>\n        </button>\n\n    </div>\n\n    <div class=\"input-group\" ng-show=\"edit\">\n\n        <input type=\"text\" class=\"form-control\" ng-model=\"ngModel\"/>\n\n        <span class=\"input-group-btn\">\n            <button\n                    ng-show=\"getFreeAllowed() == 'true'\"\n                    class=\"btn btn-danger\"\n                    ng-click=\"toggle()\">\n                <span class=\"fa fa-eraser\"></span>\n            </button>\n      </span>\n\n    </div>\n\n</div>");$templateCache.put('$/angular/templates/mm-field-text.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><div class=\"field_error_message_flash\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><input ng-disabled=\"getInfo().disabled\" placeholder=\"{{getInfo().placeholder}}\" numbers-only=\"{{getInfo().numbersOnly}}\" focus-me=\"getInfo().focus()\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" ng-model=\"getInfo().field\" type=\"{{fieldType}}\"></div><div><div ng-if=\"isValidationDefined\"><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/mm-field-date.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><div class=\"dropdown\"></div><a class=\"dropdown-toggle\" data-target=\"#\" id=\"{{id}}\" role=\"button\" data-toggle=\"dropdown\" href=\"\"><div class=\"input-group\"><input class=\"form-control\" ng-model=\"resultFormated\" type=\"text\"><span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span></div><ul class=\"dropdown-menu date_input\" aria-labelledby=\"dLabel\" role=\"menu\"><datetimepicker data-ng-model=\"result\" data-datetimepicker-config=\"{ dropdownSelector: '{{idHtag}}',minView:'day' }\"></datetimepicker></ul></a></div><div><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/mm-field-auto-completion.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><angucomplete minlength=\"1\" pause=\"400\" ng-disabled=\"getInfo().disabled\" id=\"members\" titlefield=\"content\" inputclass=\"form-control form-control-small\" placeholder=\"{{getInfo().placeholder}}\" selectedobject=\"result\" datafield=\"values\" url=\"{{getInfo().url}}\"></angucomplete></div><div><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/cr-number.html', "<div class=\"cr-number\">\n\n\n    <label class=\"cr-number-label\" ng-bind-html=\"getLabel() | translate\"></label>\n\n    <input\n            class=\"form-control cr-number-input\"\n            ng-model=\"ngModel\"/>\n\n</div>");$templateCache.put('$/angular/templates/cr-slider.html', "<div class=\"cr-slider\">\n\n    <label class=\"cr-slider-label\" ng-bind-html=\"getLabel() | translate\"></label>\n\n    <div class=\"cr-slider-container\">\n\n        <!-- Sections -->\n            <span class=\"cr-slider-section first\"\n                  ng-repeat=\"o in computedOptions\"\n                  style=\"left: 0px\"\n                >\n            </span>\n\n        <div class=\"cr-slider-null-box\">\n              <span class=\"cr-slider-tick first\"\n                    ng-mousedown=\"setValue(computedOptions[0])\"\n                    style=\"left: 0px\"\n                  ></span>\n\n        </div>\n\n        <div class=\"cr-slider-not-null-box\">\n\n            <!-- Sections -->\n            <span class=\"cr-slider-section\"\n                  ng-repeat=\"o in computedOptions\"\n                  ng-if=\"$index > 1\"\n                  style=\"left: {{ ($index - 2 )* 50}}px\"\n                >\n            </span>\n\n            <!-- Ticks -->\n\n            <span class=\"cr-slider-tick\"\n                  ng-repeat=\"o in computedOptions\"\n                  ng-if=\"o.value != null\"\n                  ng-class=\"{active: isValue(o), first: ($index == 0)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"left: {{ ($index - 1) * 50 }}px\"\n                ></span>\n\n\n            <!-- Labels -->\n\n            <span class=\"cr-slider-text\"\n                  ng-repeat=\"o in computedOptions\"\n                  ng-if=\"o.label != null\"\n                  ng-class=\"{active: isValue(o)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"left: {{ ($index - 1 )* 50}}px\"\n                  ng-bind-html=\"('' + o.label) | translate\"\n                ></span>\n\n\n        </div>\n    </div>\n</div>");$templateCache.put('$/angular/views/modal-confirm-vampire.html', "<div class=\"modal-header\">\n    <h3 class=\"modal-title\">Vampire</h3>\n</div>\n<div class=\"modal-body\">\n    Are you sure to be a <b>{{ selected }}</b> ?!\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"ok()\">Yes</button>\n    <button class=\"btn btn-warning\" ng-click=\"cancel()\">No</button>\n</div>");$templateCache.put('$/angular/views/form.html', "<div>\n    <h1>Je suis un {{text}} =></h1>\n\n    <div ng-bind-html=\"'hello' | translate\"></div>\n    <button ng-click=\"open()\">Click-me !</button>\n\n\n    <table>\n        <tr>\n            <th>\n                First name\n            </th>\n            <th>\n                Last name\n            </th>\n            <th>\n                Email\n            </th>\n        </tr>\n        <tr ng-repeat=\"account in accounts\">\n            <td>\n                {{account.firstName}}\n            </td>\n            <td>\n                {{ account.lastName}}\n            </td>\n            <td>\n                {{ account.email}}\n            </td>\n        </tr>\n    </table>\n\n</div>");});