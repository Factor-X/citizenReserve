var changeLanguageResolve, defaultResolve, testAuthenticationResolve;
angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui-rangeSlider', 'nvd3']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngLocale', 'gettext', 'ui.router']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module('app').run(function(gettextCatalog) {
  gettextCatalog.setCurrentLanguage('nl');
  return gettextCatalog.loadRemote("/translations");
});
defaultResolve = {
  testConnection: function($http, $rootScope, $stateParams, $state, downloadService, surveyDTOService) {
    if (!surveyDTOService.hasAccountType()) {
      return downloadService.getJson('/authenticated', function(result) {
        console.log("je nai pas daccount type : authentication : " + result.success);
        if (result.success) {
          return surveyDTOService.surveyDTO = result.data;
        } else {
          return $state.go('root', $stateParams);
        }
      });
    }
  }
};
testAuthenticationResolve = {
  testConnection: function($http, $rootScope, $state, $stateParams, downloadService, surveyDTOService) {
    return downloadService.getJson('/authenticated', function(result) {
      if (result.success) {
        surveyDTOService.surveyDTO = result.data;
        if (result.data.account.accountType === 'household') {
          return $state.go('root.householdProfile', $stateParams);
        }
      }
    });
  }
};
changeLanguageResolve = {
  changeLanguage: function($stateParams, gettextCatalog) {
    return gettextCatalog.setCurrentLanguage($stateParams.lang);
  }
};
angular.module('app.controllers').config(function($stateProvider, $urlRouterProvider) {
  var langPrefix;
  langPrefix = '/{lang}';
  $stateProvider.state('root', {
    url: langPrefix,
    template: '<div ui-view></div>',
    controller: function($scope, $state, $stateParams) {
      var l;
      l = $stateParams.lang;
      if (l === 'fr' || l === 'nl' || l === 'en') {
        if ($state.current.name === 'root') {
          return $state.go('root.welcome');
        }
      } else {
        return $state.go('root.welcome', {
          lang: 'fr'
        });
      }
    }
  }).state('root.welcome', {
    url: '/welcome',
    templateUrl: '$/angular/views/welcome.html',
    controller: 'WelcomeCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), testAuthenticationResolve)
  }).state('root.householdProfile', {
    url: '/household-profile',
    templateUrl: '$/angular/views/household/profile/household-profile.html',
    controller: 'FormCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
  }).state('root.householdActions', {
    url: '/household-actions',
    templateUrl: '$/angular/views/household/actions/household-actions.html',
    controller: 'FormCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
  }).state('root.householdResults', {
    url: '/household-results',
    templateUrl: '$/angular/views/household/results/household-results.html',
    controller: 'ResultsCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
  }).state('root.householdAccount', {
    url: '/household-account',
    templateUrl: '$/angular/views/household/account/household-account.html',
    controller: 'RegistrationCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
  }).state('root.controlsDemo', {
    url: '/controls-demo',
    templateUrl: '$/angular/views/test/controls-demo.html',
    controller: 'ControlsDemoCtrl',
    resolve: {}
  }).state('root.superAdminLogin', {
    url: '/admin',
    templateUrl: '$/angular/views/admin/login.html',
    controller: 'SuperAdminLoginCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), testAuthenticationResolve)
  }).state('root.superAdminMain', {
    url: '/admin/main',
    templateUrl: '$/angular/views/admin/main.html',
    controller: 'SuperAdminMainCtrl',
    resolve: angular.extend(angular.extend({}, changeLanguageResolve), defaultResolve)
  });
  return $urlRouterProvider.otherwise('/fr/welcome');
});
Messenger.options = {
  extraClasses: 'messenger-fixed messenger-on-top messenger-on-center cr-messenger',
  theme: 'block'
};angular.module('app.services').service("directiveService", function($sce) {
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
});angular.module('app.services').service("$flash", function($filter) {
  this.success = function(key) {
    return Messenger().post({
      message: $filter('translate')(key),
      type: 'success'
    });
  };
  this.info = function(key) {
    return Messenger().post({
      message: $filter('translate')(key),
      type: 'info'
    });
  };
  this.error = function(key) {
    return Messenger().post({
      message: $filter('translate')(key),
      type: 'error'
    });
  };
  this.warning = function(key) {
    return Messenger().post({
      message: $filter('translate')(key),
      type: 'warning'
    });
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
});angular.module('app.services').service("optionService", function($rootScope, $modal, gettextCatalog, $filter) {
  var cleanKey, options;
  options = {};
  this.getNumericOptions = function(questionKey, min, max, step) {
    var element, maxF, minF, optionList, stepF, _i, _len, _ref;
    if (options[questionKey] != null) {
      return options[questionKey];
    }
    optionList = [
      {
        value: null,
        label: null
      }
    ];
    minF = parseFloat(min);
    maxF = parseFloat(max) + parseFloat(step);
    stepF = parseFloat(step);
    _ref = _.range(minF, maxF, stepF);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      element = _ref[_i];
      optionList.push({
        value: element + "",
        label: element + ""
      });
    }
    options[questionKey] = optionList;
    return optionList;
  };
  this.getOptions = function(questionKey) {
    var list, opt, optionList, pattern, _i, _len;
    if (options[questionKey] != null) {
      return options[questionKey];
    }
    pattern = new RegExp(questionKey + "\.options\.*");
    list = _.filter(Object.keys(gettextCatalog.strings.fr), function(key) {
      return pattern.test(key);
    });
    list.sort();
    optionList = [
      {
        value: null,
        label: null
      }
    ];
    for (_i = 0, _len = list.length; _i < _len; _i++) {
      opt = list[_i];
      optionList.push({
        value: cleanKey(questionKey, opt),
        label: $filter('translate')(opt)
      });
    }
    options[questionKey] = optionList;
    return optionList;
  };
  cleanKey = function(questionKey, key) {
    var match, pattern;
    pattern = new RegExp(questionKey + "\.options\.([^\.]+)\.label");
    match = pattern.exec(key);
    return match[1];
  };
  return;
});angular.module('app.services').service("modalService", function($rootScope, $modal) {
  this.open = function(parameters) {
    return $modal.open(parameters);
  };
  return;
});angular.module('app.services').service("surveyDTOService", function($rootScope, $modal) {
  this.surveyDTO = {
    account: {
      otherEmailAddresses: []
    },
    answers: []
  };
  this.login = function(surveyDTO) {
    return this.surveyDTO = surveyDTO;
  };
  this.isAuthenticated = function() {
    return (this.surveyDTO.account.id != null) && this.surveyDTO.account.id !== null;
  };
  this.logout = function() {
    return this.surveyDTO = {
      account: {
        otherEmailAddresses: []
      },
      answers: []
    };
  };
  this.setAccount = function(account) {
    return this.surveyDTO.account.id = account.id;
  };
  this.hasAccountType = function() {
    var result;
    result = (this.surveyDTO.account.accountType != null);
    return result;
  };
  this.getAnswers = function(questionCode) {
    return _.where(this.surveyDTO.answers, {
      questionKey: questionCode
    });
  };
  this.getAccount = function() {
    return this.surveyDTO.account;
  };
  this.getAnswerValue = function(questionKey, periodKey) {
    var answer, answerValue;
    if (!!periodKey) {
      answer = _.findWhere(this.surveyDTO.answers, {
        questionKey: questionKey,
        periodKey: periodKey
      });
    } else {
      answer = _.findWhere(this.surveyDTO.answers, {
        questionKey: questionKey
      });
    }
    if (!answer) {
      answer = {
        questionKey: questionKey,
        periodKey: periodKey,
        answerValues: []
      };
      this.surveyDTO.answers.push(answer);
    }
    if (answer.answerValues.length === 0) {
      answerValue = {
        stringValue: null,
        doubleValue: null,
        booleanValue: null
      };
      answer.answerValues.push(answerValue);
    } else {
      answerValue = answer.answerValues[0];
    }
    return answerValue;
  };
  return;
});angular.module('app.services').service("conditionService", function(surveyDTOService) {
  var getNumericValue, resetAnswerValues, testAnswerEquals, testAnswerIsGreaterThan, testAnswerIsLowerThan, testAnswerIsTrue, testAnswerNotEquals, testAnswerNotNull, testAnyAnswerNotEquals, testIsNotAlwaysOut, tests, tooltips;
  getNumericValue = function(questionKey, periodKey) {
    var answer;
    answer = surveyDTOService.getAnswerValue(questionKey, periodKey);
    if (answer.doubleValue !== null) {
      return answer.doubleValue;
    } else if (answer.stringValue !== null && parseFloat(answer.stringValue)) {
      return parseFloat(answer.stringValue);
    }
    return 0;
  };
  testAnswerIsTrue = function(questionKey, periodKey) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue;
    if (answerValue === null) {
      return false;
    }
    return answerValue;
  };
  testAnswerNotNull = function(questionKey, periodKey) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).booleanValue;
    if (answerValue === null) {
      return false;
    }
    return true;
  };
  testAnswerNotEquals = function(questionKey, periodKey, stringValue) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue;
    if (answerValue === null) {
      return false;
    }
    return answerValue !== stringValue;
  };
  testAnswerEquals = function(questionKey, periodKey, stringValue) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).stringValue;
    if (answerValue === null) {
      return false;
    }
    return answerValue === stringValue;
  };
  testAnyAnswerNotEquals = function(questionKey, stringValue) {
    var periodKey, _i, _len, _ref;
    _ref = ["FIRST", "SECOND", "THIRD"];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      periodKey = _ref[_i];
      if (testAnswerNotEquals(questionKey, periodKey, stringValue)) {
        return true;
      }
    }
    return false;
  };
  testAnswerIsGreaterThan = function(questionKey, periodKey, numericValue) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).doubleValue;
    if (answerValue === null) {
      return false;
    }
    return answerValue > numericValue;
  };
  testAnswerIsLowerThan = function(questionKey, periodKey, numericValue) {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue(questionKey, periodKey).doubleValue;
    if (answerValue === null) {
      return false;
    }
    return answerValue < numericValue;
  };
  testIsNotAlwaysOut = function() {
    var answerValue;
    answerValue = surveyDTOService.getAnswerValue("Q3211", null).stringValue;
    return answerValue !== "4";
  };
  resetAnswerValues = function(answers) {
    var answer, answerValue, _i, _j, _len, _len2, _ref;
    for (_i = 0, _len = answers.length; _i < _len; _i++) {
      answer = answers[_i];
      _ref = answer.answerValues;
      for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
        answerValue = _ref[_j];
        if (!!answerValue.stringValue) {
          answerValue.stringValue = null;
        }
        if (!!answerValue.doubleValue) {
          answerValue.doubleValue = null;
        }
        if (!!answerValue.booleanValue) {
          answerValue.booleanValue = null;
        }
      }
    }
    return;
  };
  tests = {
    Q3211: function() {
      return testAnswerIsTrue("Q3210", null);
    },
    Q3110: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1110", "0");
    },
    Q3120: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1120", "0");
    },
    Q3130: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1130", "0");
    },
    Q3310: function() {
      return testIsNotAlwaysOut() && testAnswerIsGreaterThan("Q1600", null, 0);
    },
    Q3320: function() {
      return testIsNotAlwaysOut() && testAnswerIsGreaterThan("Q1900", null, 0);
    },
    Q3330: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q1210", null, "0");
    },
    Q3410: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q1220", null, "0");
    },
    Q3420: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q1230", null, "0");
    },
    Q3510: function() {
      return testIsNotAlwaysOut();
    },
    Q3530: function() {
      return testIsNotAlwaysOut() && testAnswerIsGreaterThan("Q1800", null, 0);
    },
    Q3610: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q2010", null, "0");
    },
    Q3620: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q2020", null, "0");
    },
    Q3630: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q1700", null, "0");
    },
    Q3631: function() {
      return testIsNotAlwaysOut() && testAnswerIsTrue("Q3630", null);
    },
    Q3640: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1160", "0");
    },
    Q3810: function() {
      return testAnswerNotEquals("Q1235", null, "0");
    },
    Q3710: function() {
      return testIsNotAlwaysOut() && (testAnyAnswerNotEquals("Q1140", "0") || testAnyAnswerNotEquals("Q1150", "0") || testAnswerNotEquals("Q2030", null, "0") || testAnswerNotEquals("Q2040", null, "0"));
    },
    Q3711: function() {
      return testAnswerIsTrue("Q3710", null);
    },
    Q3720: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1140", "0") && testAnswerNotEquals("Q3711", null, "4");
    },
    Q3730: function() {
      return testIsNotAlwaysOut() && testAnyAnswerNotEquals("Q1150", "0") && testAnswerNotEquals("Q3711", null, "4");
    },
    Q3750: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q2030", null, "0") && testAnswerNotEquals("Q3711", null, "4");
    },
    Q3760: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q2040", null, "0") && testAnswerNotEquals("Q3711", null, "4");
    },
    Q3740: function() {
      return testIsNotAlwaysOut() && testAnswerNotEquals("Q3711", null, "4");
    },
    Q3741: function() {
      return testAnswerNotEquals("Q3740", null, "0");
    }
  };
  this.checkCondition = function(questionKey) {
    var res, testFct;
    testFct = tests[questionKey];
    if (!!testFct) {
      res = testFct();
      if (res === false) {
        resetAnswerValues(surveyDTOService.getAnswers(questionKey));
      }
      return res;
    }
    return true;
  };
  tooltips = {
    Q1300: function() {
      if (testAnswerEquals('Q1300', null, '20')) {
        return 'Q1300.label.option20.warning';
      }
      return null;
    },
    Q1400: function() {
      if (testAnswerEquals('Q1400', null, '5')) {
        return 'Q1400.option5.warning';
      }
      return null;
    },
    Q1110: {
      FIRST: function() {
        var val;
        val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110', 'THIRD');
        if (val >= 5) {
          return 'Q1110.label.toomany.warning';
        }
        return null;
      },
      SECOND: function() {
        var val;
        val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110', 'THIRD');
        if (val >= 5) {
          return 'Q1110.label.toomany.warning';
        }
        return null;
      },
      THIRD: function() {
        var val;
        val = getNumericValue('Q1110', 'FIRST') + getNumericValue('Q1110', 'SECOND') + getNumericValue('Q1110', 'THIRD');
        if (val >= 5) {
          return 'Q1110.label.toomany.warning';
        }
        return null;
      }
    },
    Q1800: function() {
      if (testAnswerIsLowerThan('Q1800', null, '10')) {
        return 'Q1800.low-value.warning';
      }
      return null;
    },
    Q3420: function() {
      if (testAnswerNotNull('Q3420', null)) {
        return 'Q3420.tooltip';
      }
      return null;
    },
    Q3510: function() {
      if (testAnswerNotNull('Q3510', null)) {
        return 'Q3510.tooltip';
      }
      return null;
    },
    Q3740: function() {
      if (testAnswerNotNull('Q3740', null)) {
        return 'Q3740.tooltip';
      }
      return null;
    },
    Q3211: function() {
      if (testAnswerNotNull('Q3211', null)) {
        return 'Q3211.tooltip';
      }
      return null;
    }
  };
  this.getTooltip = function(questionKey, periodKey) {
    if (periodKey == null) {
      periodKey = null;
    }
    if (periodKey === null) {
      return tooltips[questionKey];
    } else if (tooltips[questionKey] != null) {
      return tooltips[questionKey][periodKey];
    }
    return null;
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
    return console.log("MessageFlash : type:" + type + "/content:" + message);
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
});angular.module('app.filters').filter("toWatts", function(translateFilter) {
  return function(input) {
    if (input != null) {
      return input + translateFilter('filter.toWatts.w.suffix');
    }
  };
});angular.module('app.filters').filter("toSquareMeters", function(translateFilter) {
  return function(input) {
    if (input != null) {
      return input + translateFilter('filter.toSquareMeters.m2.suffix');
    }
  };
});angular.module('app.filters').filter("stringToFloat", function() {
  return function(input) {
    if (input != null) {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("toHour", function(translateFilter) {
  return function(input) {
    if (input != null) {
      return input + translateFilter('filter.toHour.hour.suffix');
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
});angular.module('app.directives').directive("crTootipOnRespond", function(directiveService, $filter, $parse, conditionService) {
  return {
    restrict: "A",
    require: 'ngModel',
    link: function(scope, elem, attrs, ngModel) {
      var o, periodKey, questionKey;
      o = $parse(attrs.crTootipOnRespond)(scope);
      questionKey = o.key;
      periodKey = o.period;
      if (conditionService.getTooltip(questionKey, periodKey) != null) {
        if ($(elem).closest('.modal').length > 0) {
          scope.$lbl = conditionService.getTooltip(questionKey, periodKey)();
          scope.$oldLbl = scope.$lbl;
        }
        scope.$on('$destroy', function() {
          if (scope.$trip) {
            scope.$trip.stop();
            return scope.$trip = null;
          }
        });
        scope.$$childHead.$watch('ngModel', function(n, o) {
          scope.$lbl = conditionService.getTooltip(questionKey, periodKey)();
          if (scope.$lbl !== scope.$oldLbl) {
            if (scope.$trip) {
              scope.$trip.stop();
              scope.$trip = null;
            }
            if (scope.$lbl) {
              scope.$trip = new Trip([
                {
                  sel: $(elem),
                  content: $filter('translate')(scope.$lbl),
                  position: 'w',
                  delay: 10000,
                  animation: 'bounceInLeft',
                  showCloseBox: true
                }
              ], {
                overlayHolder: '.modal-body'
              });
              scope.$trip.start();
            }
            return scope.$oldLbl = scope.$lbl;
          }
        });
      }
      return;
    }
  };
});angular.module('app.directives').directive("crConditioned", function(conditionService, surveyDTOService) {
  return {
    restrict: "A",
    link: function(scope, element, attrs) {
      var handler, result;
      result = conditionService.checkCondition(attrs.crConditioned);
      if (result) {
        $(element).show();
      } else {
        $(element).hide();
      }
      handler = function() {
        result = conditionService.checkCondition(attrs.crConditioned);
        if (result) {
          $(element).slideDown();
        } else {
          $(element).slideUp();
        }
        return;
      };
      scope.$watch((function() {
        return surveyDTOService.surveyDTO;
      }), handler, true);
      return;
    }
  };
});angular.module('app.directives').directive("crSection", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '='
    }),
    templateUrl: "$/angular/templates/cr-section.html",
    replace: false,
    transclude: true,
    link: function(scope, elem, attrs, ngModel) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("crMultiText", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngRegex: '='
    }),
    require: 'ngModel',
    templateUrl: "$/angular/templates/cr-multi-text.html",
    replace: true,
    link: function(scope, elem, attrs) {
      directiveService.autoScopeImpl(scope);
      scope.getRange = function() {
        if (!scope.ngModel) {
          return [0];
        } else {
          return _.range(scope.ngModel.length + 1);
        }
      };
      scope.validation = [];
      return scope.$watch('ngModel', function(n, o) {
        var k, r, v, _ref, _ref2;
        scope.ngModel = _.reject(scope.ngModel, function(e) {
          return !e;
        });
        scope.validation = [];
        if (scope.getRegex()) {
          r = new RegExp(scope.getRegex());
          _ref = scope.ngModel;
          for (k in _ref) {
            v = _ref[k];
            scope.validation[k] = r.test(v);
          }
        } else {
          _ref2 = scope.ngModel;
          for (k in _ref2) {
            v = _ref2[k];
            scope.validation[k] = true;
          }
        }
        return scope.validation[scope.ngModel.length] = true;
      }, true);
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
});angular.module('app.directives').directive("crRadio", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
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
});angular.module('app.directives').directive("crQuestion", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngLabel: '=',
      ngInline: '='
    }),
    templateUrl: "$/angular/templates/cr-question.html",
    replace: false,
    transclude: true,
    link: function(scope, elem, attrs, ngModel) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("crText", function(directiveService, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngMultiline: '=',
      ngType: '=',
      ngName: '=',
      ngValidation: '=',
      ngDisabled: '='
    }),
    require: 'ngModel',
    templateUrl: "$/angular/templates/cr-text.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      $timeout(function() {
        if (scope.getType()) {
          return $('input', elem).attr('type', scope.getType());
        }
      }, 0);
      if (scope.getValidation() != null) {
        return scope.$watch('getModel()', function() {
          var isValid;
          isValid = false;
          if (scope.getModel() != null) {
            if (scope.getValidation().validation != null) {
              isValid = scope.getValidation().validation();
            } else {
              isValid = scope.getValidation().pattern.test(scope.getModel());
            }
          }
          return scope.getValidation().valid = isValid;
        });
      }
    }
  };
});angular.module('app.directives').directive("crSlider", function(directiveService, $filter, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngSteps: '=',
      ngFreeAllowed: '=',
      ngMin: '=',
      ngMax: '=',
      ngStep: '=',
      ngVisible: '=',
      ngVertical: '=',
      ngFilter: '=',
      ngTheme: '='
    }),
    templateUrl: "$/angular/templates/cr-slider.html",
    replace: false,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      scope.labelFilter = $filter('translate');
      scope.opened = false;
      scope.computedOptionsFiltered = [
        {
          label: null,
          value: null
        }
      ];
      scope.setValue = function(v) {
        if (v.value === null) {
          scope.ngModel = null;
          scope.expanded = false;
          return scope.updateComputedOptionsFiltered();
        } else if (scope.getVertical() && scope.computedOptions.length > 3 && !scope.expanded) {
          scope.expanded = true;
          return scope.updateComputedOptionsFiltered();
        } else {
          scope.ngModel = v.value;
          return scope.expanded = false;
        }
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
      scope.isVisible = function() {
        var v;
        v = scope.ngVisible;
        if (v === void 0) {
          return true;
        } else if (v === null) {
          return false;
        } else {
          return !!v;
        }
      };
      scope.$watch(['ngFilter', 'filter'], function() {
        var n;
        n = scope.getFilter();
        if (n) {
          return scope.labelFilter = $filter(n);
        } else {
          return scope.labelFilter = $filter('translate');
        }
      });
      scope.$watch(['ngSteps', 'ngMax', 'ngMin', 'ngStep', 'max', 'min', 'step'], function(n, o) {
        var element, max, min, step, _i, _j, _len, _len2, _ref, _ref2;
        scope.computedOptions = [];
        if (scope.ngSteps) {
          _ref = scope.ngSteps;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            element = _ref[_i];
            if (element === null) {
              scope.computedOptions.push({
                value: element,
                label: element
              });
            } else if (typeof element === 'object') {
              scope.computedOptions.push(element);
            } else {
              scope.computedOptions.push({
                value: element,
                label: element
              });
            }
          }
        } else if (scope.getMin() !== void 0 && scope.getMax() !== void 0 && scope.getStep() !== void 0) {
          scope.computedOptions.push({
            value: null,
            label: null
          });
          min = parseFloat(scope.getMin());
          max = parseFloat(scope.getMax()) + parseFloat(scope.getStep());
          step = parseFloat(scope.getStep());
          _ref2 = _.range(min, max, step);
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            element = _ref2[_j];
            scope.computedOptions.push({
              value: element,
              label: element
            });
          }
        }
        if (scope.getVertical()) {
          return scope.updateComputedOptionsFiltered();
        }
      });
      scope.$watch('ngModel', function() {
        return scope.updateComputedOptionsFiltered();
      });
      scope.updateComputedOptionsFiltered = function() {
        if (scope.expanded) {
          return scope.computedOptionsFiltered = scope.computedOptions;
        } else {
          scope.computedOptionsFiltered = _.where(scope.computedOptions, {
            value: scope.ngModel
          });
          if (_.where(scope.computedOptionsFiltered, {
            value: null
          }).length === 0) {
            scope.computedOptionsFiltered.unshift({
              label: null,
              value: null
            });
          }
          return scope.computedOptionsFiltered.push({
            value: "$SELECT_A_VALUE$",
            label: null
          });
        }
      };
      scope.toggleExpanded = function() {
        scope.expanded = !scope.expanded;
        return scope.updateComputedOptionsFiltered();
      };
      scope.scrollToSelected = function(delayed) {
        var $scroller, cnt, idx, o, offset, target, v, w, _i, _len, _ref;
        if (scope.getVertical() != 'true') {
          $scroller = $(".cr-slider-not-null-box-holder", elem);
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
          if (Math.abs(target - $scroller.scrollLeft()) > 1) {
            v = $scroller;
            if (delayed) {
              v = v.delay(1000);
            }
            return v = v.animate({
              scrollLeft: target
            }, {
              easing: 'easeInOutBack'
            });
          }
        }
      };
      return $timeout(function() {
        $(elem).mouseenter(function() {
          var $scroller;
          $scroller = $(".cr-slider-not-null-box-holder", this);
          return $scroller.stop(true);
        });
        $(elem).mouseleave(function() {
          return scope.scrollToSelected(true);
        });
        return scope.scrollToSelected(false);
      }, 0);
    }
  };
});angular.module('app.directives').directive("crNumber", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
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
});angular.module('app.directives').directive("crTopic", function(directiveService, modalService, $log) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngActive: '=',
      ngImage: '=',
      ngView: '=',
      ngController: '=',
      ngWindowClass: '=',
      ngLabel: '=',
      ngCallback: '&'
    }),
    templateUrl: "$/angular/templates/cr-topic.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      return scope.open = function() {
        var cb, modalInstance;
        console.log(scope.getWindowClass());
        modalInstance = modalService.open({
          templateUrl: scope.getView(),
          controller: scope.getController(),
          size: 'lg',
          windowClass: scope.getWindowClass(),
          resolve: {}
        });
        cb = scope.ngCallback;
        if (!cb) {
          cb = angular.noop;
        }
        return modalInstance.result.then(cb, cb);
      };
    }
  };
});angular.module('app.directives').directive("crBoolean", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/cr-boolean.html",
    replace: false,
    link: function(scope, elem, attrs, ngModel) {
      directiveService.autoScopeImpl(scope);
      return scope.steps = [
        {
          value: null,
          label: null
        }, {
          value: true,
          label: "directive.boolean.yes"
        }, {
          value: false,
          label: "directive.boolean.no"
        }
      ];
    }
  };
});angular.module('app.directives').directive("crDoubleRange", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
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
});angular.module('app.directives').directive("crMegaButton", function(directiveService, modalService, $log) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDisabled: '=',
      ngImage: '=',
      ngLabel: '=',
      ngClick: '='
    }),
    templateUrl: "$/angular/templates/cr-mega-button.html",
    replace: true,
    link: function(scope, elem, attrs, ngModel) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("crDropdown", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
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
});angular.module('app.directives').directive("crLogin", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/cr-login.html",
    replace: false,
    link: function(scope, elem, attrs) {}
  };
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, modalService, $filter, $log, downloadService, surveyDTOService) {
  $scope.isAuthenticated = function() {
    return surveyDTOService.isAuthenticated();
  };
  $scope.options = {
    chart: {
      type: 'lineChart',
      forceY: [0],
      height: 450,
      margin: {
        top: 20,
        right: 20,
        bottom: 60,
        left: 55
      },
      x: function(d) {
        return d.x;
      },
      y: function(d) {
        return d.y;
      },
      showValues: true,
      transitionDuration: 500,
      xAxis: {
        axisLabel: '',
        tickValues: [17, 18, 19, 20],
        showMaxMin: false,
        tickFormat: function(d) {
          return $filter('toHour')(parseInt(d));
        }
      },
      yAxis: {
        axisLabel: '',
        axisLabelDistance: 30,
        showMaxMin: false,
        tickFormat: function(d) {
          return $filter('toWatts')(parseInt(d));
        }
      }
    }
  };
  $scope.data = [];
  return downloadService.getJson('/batch/last', function(result) {
    var arr2, calcParabolaParameters, v1, v2, v3;
    if (result.success) {
      v1 = result.data.effectiveBatch.batchResultItemList[0].powerReduction;
      v2 = result.data.effectiveBatch.batchResultItemList[1].powerReduction;
      v3 = result.data.effectiveBatch.batchResultItemList[2].powerReduction;
      console.log(v1, v2, v3);
      calcParabolaParameters = function(x1, y1, x2, y2, x3, y3) {
        var A, B, C, denom;
        denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
        A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
        B = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom;
        C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
        return {
          a: A,
          b: B,
          c: C
        };
      };
      arr2 = calcParabolaParameters(17.5, v1, 18.5, v2, 19.5, v3);
      return $scope.data = [
        {
          key: "YOU",
          color: '#229913',
          area: true,
          values: [
            {
              x: 17,
              y: v1
            }, {
              x: 18,
              y: v1
            }, {
              x: 18,
              y: v2
            }, {
              x: 19,
              y: v2
            }, {
              x: 19,
              y: v3
            }, {
              x: 20,
              y: v3
            }
          ]
        }, {
          key: "TREND YOU",
          color: '#28DB15',
          values: _.map(_.range(17, 20.05, 0.1), function(x) {
            return {
              x: x,
              y: arr2.a * x * x + arr2.b * x + arr2.c
            };
          })
        }
      ];
    }
  });
});angular.module('app.controllers').controller("ModalChangePasswordCtrl", function($scope, modalService, $log, downloadService, $modalInstance, $flash) {
  $scope.noSubmitYet = true;
  $scope.loading = false;
  $scope.validation = {
    oldPassword: {
      pattern: /^[a-zA-Z0-9-_%]{6,18}$/,
      valid: false
    },
    newPassword: {
      pattern: /^[a-zA-Z0-9-_%]{6,18}$/,
      valid: false
    },
    repeatPassword: {
      validation: function() {
        return $scope.o.newPassword === $scope.o.repeatPassword;
      },
      valid: false
    }
  };
  $scope.o = {
    oldPassword: "",
    newPassword: "",
    repeatPassword: ""
  };
  $scope.save = function() {
    var dto;
    $scope.noSubmitYet = false;
    if ($scope.checkValidity()) {
      dto = {
        oldPassword: $scope.o.oldPassword,
        newPassword: $scope.o.newPassword
      };
      $scope.loading = true;
      return downloadService.postJson('/account/changePassword', dto, function(result) {
        $scope.loading = false;
        if (result.success) {
          $flash.success('account.changePassword.success');
          return $scope.close();
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
  $scope.checkValidity = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.validation);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if ($scope.validation[key].valid === false) {
        return false;
      }
    }
    return true;
  };
  return $scope.close = function() {
    return $modalInstance.close();
  };
});angular.module('app.controllers').controller("ModalChangeEmailCtrl", function($scope, modalService, $log, downloadService, $modalInstance, surveyDTOService, $flash) {
  $scope.noSubmitYet = true;
  $scope.loading = false;
  $scope.validation = {
    oldPassword: {
      pattern: /^[a-zA-Z0-9-_%]{6,18}$/,
      valid: false
    },
    newEmail: {
      pattern: /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
      valid: false
    }
  };
  $scope.o = {
    oldPassword: "",
    newEmail: ""
  };
  $scope.save = function() {
    var dto;
    $scope.noSubmitYet = false;
    if ($scope.checkValidity()) {
      dto = {
        oldPassword: $scope.o.oldPassword,
        email: $scope.o.newEmail
      };
      $scope.loading = true;
      return downloadService.postJson('/account/changeEmail', dto, function(result) {
        $scope.loading = false;
        if (result.success) {
          $flash.success('account.changeEmail.success');
          surveyDTOService.getAccount().email = $scope.o.newEmail;
          return $scope.close();
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
  $scope.checkValidity = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.validation);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if ($scope.validation[key].valid === false) {
        return false;
      }
    }
    return true;
  };
  return $scope.close = function() {
    return $modalInstance.close();
  };
});angular.module('app.controllers').controller("SuperAdminLoginCtrl", function($scope, modalService, $state, $log, $location, surveyDTOService, downloadService, $flash) {
  $scope.loginParams = {
    email: "",
    password: ""
  };
  $scope.loading = false;
  return $scope.login = function() {
    var dto;
    if ($scope.loading === false) {
      dto = {
        email: $scope.loginParams.email,
        password: $scope.loginParams.password
      };
      $scope.loading = true;
      return downloadService.postJson('/superAdmin/login', dto, function(result) {
        $scope.loading = false;
        if (result.success) {
          surveyDTOService.login(result.data);
          $flash.success('account.login.success');
          return $state.go('root.superAdminMain');
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
});angular.module('app.controllers').controller("SuperAdminMainCtrl", function($scope, modalService, $state, $log, $location, surveyDTOService, downloadService, $flash) {
  $scope.loginParams = {
    email: "",
    password: ""
  };
  $scope.batchs = null;
  $scope.logout = function() {
    downloadService.postJson('/logout', surveyDTOService.surveyDTO, function(result) {
      if (result.success) {
        $location.path('/welcome');
        surveyDTOService.logout();
        return $flash.success('logout.success');
      }
    });
    return;
  };
  $scope.loadingReductionData = function() {
    return downloadService.getJson('/superAdmin/reductionData', function(result) {
      $scope.loading = false;
      if (result.success) {
        console.log("------------------------------------------------");
        console.log(result.data.list);
        return $scope.batchs = result.data.list;
      } else {
        return $flash.error(result.data.message);
      }
    });
  };
  $scope.loadingReductionData();
  return $scope.getValue = function(map, day, period) {
    var el, _i, _len;
    for (_i = 0, _len = map.length; _i < _len; _i++) {
      el = map[_i];
      if ((day === null || el.dayKey === day) && (el.periodKey = period)) {
        return el.powerReduction;
      }
    }
  };
});angular.module('app.controllers').controller("WelcomeCtrl", function($scope, modalService, $state, $log, $location, surveyDTOService, downloadService, $flash) {
  $scope.toHouseHold = function() {
    surveyDTOService.getAccount().accountType = 'household';
    return $state.go('root.householdProfile');
  };
  $scope.loginParams = {
    email: "",
    password: ""
  };
  $scope.forgotPasswordParams = {
    email: ""
  };
  $scope.loading = false;
  $scope.login = function() {
    var dto;
    if ($scope.loading === false) {
      dto = {
        email: $scope.loginParams.email,
        password: $scope.loginParams.password
      };
      $scope.loading = true;
      return downloadService.postJson('/login', dto, function(result) {
        $scope.loading = false;
        if (result.success) {
          $flash.success('account.login.success');
          surveyDTOService.login(result.data);
          if (result.data.account.accountType === 'household') {
            return $state.go('root.householdProfile');
          }
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
  return $scope.forgotPassword = function() {
    var dto;
    if ($scope.loading === false) {
      dto = {
        email: $scope.forgotPasswordParams.email
      };
      $scope.loading = true;
      return downloadService.postJson('/forgotPassword', dto, function(result) {
        $scope.loading = false;
        if (result.success) {
          $flash.success('account.forgotPassword.success');
          return $scope.forgotPasswordParams.email = null;
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
});angular.module('app.controllers').controller("FormCtrl", function($scope, modalService, $filter, $log, downloadService, surveyDTOService, conditionService, $location, $flash) {
  $scope.topicQuestions = {
    profile: {
      'presence': ['Q1300', 'Q1400', 'Q1500'],
      'programs': ['Q1110', 'Q1120', 'Q1130'],
      'dinner': ['Q1140', 'Q1150'],
      'heating': ['Q1600', 'Q1900', 'Q1210'],
      'lighting': ['Q1160', 'Q1220', 'Q1230', 'Q1700', 'Q1750', 'Q1800', 'Q2010', 'Q2020', 'Q2030', 'Q2040', 'Q1235']
    }
  };
  $scope.logout = function() {
    downloadService.postJson('/logout', surveyDTOService.surveyDTO, function(result) {
      if (result.success) {
        $location.path('/welcome');
        surveyDTOService.logout();
        return $flash.success('logout.success');
      }
    });
    return;
  };
  $scope.save = function() {
    if (surveyDTOService.isAuthenticated()) {
      return downloadService.postJson('/survey/update', surveyDTOService.surveyDTO, function(result) {
        if (result.success) {
          return $flash.success('account.save.success');
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
  $scope.isAuthenticated = function() {
    return surveyDTOService.isAuthenticated();
  };
  $scope.openModal = function(target, controller) {
    var modalInstance;
    if (controller == null) {
      controller = 'ModalTopicCtrl';
    }
    return modalInstance = modalService.open({
      templateUrl: '$/angular/views/' + target + '.html',
      controller: controller,
      size: 'lg'
    });
  };
  $scope.isQuestionAnswered = function(questionKey) {
    var answer, answerValue, answers, _i, _j, _len, _len2, _ref;
    answers = surveyDTOService.getAnswers(questionKey);
    if (answers.length === 0) {
      return false;
    }
    for (_i = 0, _len = answers.length; _i < _len; _i++) {
      answer = answers[_i];
      _ref = answer.answerValues;
      for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
        answerValue = _ref[_j];
        if (!((!!answerValue.booleanValue) || (!!answerValue.stringValue) || (!!answerValue.doubleValue) || (answerValue.doubleValue === 0))) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.isProfileTopicCompleted = function(topicIdentifier) {
    var questionKey, topicQuestionKeys, _i, _len;
    topicQuestionKeys = $scope.topicQuestions.profile[topicIdentifier];
    for (_i = 0, _len = topicQuestionKeys.length; _i < _len; _i++) {
      questionKey = topicQuestionKeys[_i];
      if (!$scope.isQuestionAnswered(questionKey)) {
        return false;
      }
    }
    return true;
  };
  $scope.isProfileCompleted = function() {
    var topicIdentifier;
    $scope.topicQuestions.profile;
    for (topicIdentifier in $scope.topicQuestions.profile) {
      if (!$scope.isProfileTopicCompleted(topicIdentifier)) {
        return false;
      }
    }
    return true;
  };
  $scope.potentialReduction = {};
  $scope.getPotentialReduction = function() {
    return downloadService.postJson('/reduction/potential', surveyDTOService.surveyDTO, function(result) {
      if (result.success) {
        return $scope.potentialReduction = result.data;
      } else {
        return console.log(result.data);
      }
    });
  };
  $scope.getPotentialReduction();
  $scope.effectiveReduction = {};
  $scope.getEffectiveReduction = function() {
    return downloadService.postJson('/reduction/effective', surveyDTOService.surveyDTO, function(result) {
      if (result.success) {
        return $scope.effectiveReduction = result.data;
      } else {
        return console.log(result.data);
      }
    });
  };
  return $scope.getEffectiveReduction();
});angular.module('app.controllers').controller("MainCtrl", function($scope, modalService, $log, gettextCatalog) {
  return $scope.setLanguage = function(lang) {
    return gettextCatalog.setCurrentLanguage(lang);
  };
});
angular.module('app').run(function($rootScope, $location) {
  return $rootScope.redirectTo = function(target) {
    return $location.path(target);
  };
});angular.module('app.controllers').controller("ModalTopicCtrl", function($scope, surveyDTOService, optionService, $modalInstance) {
  $scope.getOptions = function(questionKey) {
    return optionService.getOptions(questionKey);
  };
  $scope.getNumericOptions = function(questionKey, min, max, step) {
    return optionService.getNumericOptions(questionKey, min, max, step);
  };
  $scope.getAnswerValue = function(questionKey, periodKey) {
    return surveyDTOService.getAnswerValue(questionKey, periodKey);
  };
  $scope.getAccount = function() {
    return surveyDTOService.getAccount();
  };
  return $scope.close = function() {
    return $modalInstance.close();
  };
});angular.module('app.controllers').controller("ControlsDemoCtrl", function($scope, modalService, $log, gettextCatalog, $flash) {
  $scope.setLanguage = function(lang) {
    return gettextCatalog.setCurrentLanguage(lang);
  };
  $scope.x = {
    sel: 'Human',
    items: ['Human', 'B@t', '-', 'Vampire'],
    cnt: 10,
    firstName: 'Winston',
    password: '123',
    comment: 'Attitude is a little thing that makes a big difference.',
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
    yesno: {
      value: null
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
        templateUrl: '$/angular/views/test/modal-confirm-vampire.html',
        controller: 'NiceModalCtrl',
        size: 'sm',
        resolve: {
          chosenValue: function() {
            return $scope.x.sel;
          }
        }
      });
      return modalInstance.result.then(function(result) {
        $log.info(result);
        $flash.success('success');
        $flash.info('info');
        $flash.error('error');
        return $flash.warning('warning');
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
});angular.module('app.controllers').controller("RegistrationCtrl", function($scope, modalService, $log, downloadService, surveyDTOService, optionService, $location, $flash) {
  $scope.noSubmitYet = true;
  $scope.loading = false;
  $scope.getOptions = function(questionKey) {
    return optionService.getOptions(questionKey);
  };
  $scope.getAnswerValue = function(questionKey, periodKey) {
    return surveyDTOService.getAnswerValue(questionKey, periodKey);
  };
  $scope.getAccount = function() {
    return surveyDTOService.getAccount();
  };
  $scope.getNumericOptions = function(questionKey, min, max, step) {
    return optionService.getNumericOptions(questionKey, min, max, step);
  };
  $scope.openModal = function(target, controller) {
    var modalInstance;
    if (controller == null) {
      controller = 'ModalTopicCtrl';
    }
    return modalInstance = modalService.open({
      templateUrl: '$/angular/views/' + target + '.html',
      controller: controller,
      size: 'lg'
    });
  };
  $scope.logout = function() {
    downloadService.postJson('/logout', surveyDTOService.surveyDTO, function(result) {
      if (result.success) {
        $location.path('/welcome');
        surveyDTOService.logout();
        return $flash.success('logout.success');
      }
    });
    return;
  };
  $scope.validation = {
    firstName: {
      pattern: /^.{2,100}$/,
      valid: false
    },
    lastName: {
      pattern: /^.{2,100}$/,
      valid: false
    },
    emailAddress: {
      pattern: /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
      valid: false
    },
    password: {
      pattern: /^[a-zA-Z0-9-_%]{6,18}$/,
      valid: false
    },
    repeatPassword: {
      validation: function() {
        return $scope.getAccount().password === $scope.o.repeatPassword;
      },
      valid: false
    },
    terms: {
      valid: false
    }
  };
  $scope.zip = {
    pattern: /^.{0,20}$/,
    valid: false
  };
  $scope.o = {
    errorMessage: "",
    repeatPassword: $scope.getAccount().password,
    acceptAgreement: false
  };
  $scope.save = function() {
    $scope.noSubmitYet = false;
    if ($scope.checkValidity()) {
      $scope.loading = true;
      console.log("je save ce dto : ");
      console.log(surveyDTOService.surveyDTO);
      return downloadService.postJson('/registration', surveyDTOService.surveyDTO, function(result) {
        $scope.loading = false;
        if (result.success) {
          surveyDTOService.setAccount(result.data.account);
          return $flash.success('account.save.success');
        } else {
          return $flash.error(result.data.message);
        }
      });
    }
  };
  $scope.checkValidity = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.validation);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if ($scope.validation[key].valid === false) {
        return false;
      }
    }
    return true;
  };
  return $scope.isAuthenticated = function() {
    return surveyDTOService.isAuthenticated();
  };
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/templates/cr-boolean.html', "<cr-slider ng-model=\"ngModel\" ng-steps=\"steps\"></cr-slider>");$templateCache.put('$/angular/templates/cr-dropdown.html', "<div class=\"cr-dropdown\">\n\n    <div class=\"btn-group cr-dropdown-control\"\n         dropdown>\n\n        <button type=\"button\"\n                class=\"btn btn-default dropdown-toggle cr-dropdown-button\"\n                dropdown-toggle\n                ng-disabled=\"getDisabled()\">\n            {{ ngModel }} <span class=\"caret\"></span>\n        </button>\n\n        <ul class=\"dropdown-menu cr-dropdown-menu\"\n            role=\"menu\">\n            <li ng-repeat=\"o in ngOptions\" class=\"cr-dropdown-menu-item\" ng-class=\"{divider: (o == '-')}\">\n                <a class=\"cr-dropdown-menu-item-link\" ng-if=\"o != '-'\" ng-click=\"$select(o)\">{{ o }}</a>\n            </li>\n        </ul>\n    </div>\n\n</div>");$templateCache.put('$/angular/templates/cr-double-range.html', "<div class=\"cr-range\">\n\n    <div range-slider min=\"ngRangeMin\" max=\"ngRangeMax\" model-min=\"ngMin\" model-max=\"ngMax\" step=\"ngStep\"\n         disabled=\"ngDisabled\"></div>\n\n</div>");$templateCache.put('$/angular/templates/cr-question.html', "<div class=\"cr-question\">\n\n    <div class=\"row\">\n        <div ng-class=\"{'col-md-1': getInline() == 'true', 'col-md-12': getInline() != 'true'}\">\n            <label class=\"cr-question-label\" ng-bind-html=\"getLabel() | translate\"></label>\n        </div>\n        <div ng-class=\"{'col-md-11': getInline() == 'true', 'col-md-12': getInline() != 'true'}\">\n            <div class=\"cr-question-editor\" ng-transclude></div>\n        </div>\n    </div>\n\n\n</div>");$templateCache.put('$/angular/templates/cr-radio.html', "<div class=\"cr-radio\">\n\n    <div class=\"btn-group\" ng-hide=\"edit\">\n        <button class=\"btn btn-default  \"\n                ng-class=\"{active: isValue(o.value)}\"\n                ng-repeat=\"o in computedOptions\"\n                ng-click=\"setValue(o.value)\"\n                ng-bind-html=\"('' + o.label) | translate\"\n                ></button>\n\n        <button ng-if=\"getFreeAllowed() == 'true'\"\n                class=\"btn btn-danger\"\n                ng-click=\"toggle()\">\n            <span class=\"fa fa-pencil\"></span>\n        </button>\n\n    </div>\n\n    <div class=\"input-group\" ng-show=\"edit\">\n\n        <input type=\"text\" class=\"form-control\" ng-model=\"ngModel\"/>\n\n        <span class=\"input-group-btn\">\n            <button\n                    ng-show=\"getFreeAllowed() == 'true'\"\n                    class=\"btn btn-danger\"\n                    ng-click=\"toggle()\">\n                <span class=\"fa fa-eraser\"></span>\n            </button>\n      </span>\n\n    </div>\n\n</div>");$templateCache.put('$/angular/templates/cr-mega-button.html', "<button class=\"cr-mega-button\" ng-disabled=\"{disabled: (getDisabled() == 'true')}\" ng-click=\"ngClick()\" >\n\n    <img class=\"cr-mega-button-image-default\"\n         ng-hide=\"getDisabled() == 'true'\"\n         ng-src=\"/assets/images/{{getImage()}}.png\"/>\n\n    <img class=\"cr-mega-button-image-disabled\"\n         ng-show=\"getDisabled() == 'true'\"\n         ng-src=\"/assets/images/{{getImage()}}_disabled.png\"/>\n\n    <img class=\"cr-mega-button-image-hover\"\n         ng-src=\"/assets/images/{{getImage()}}_hover.png\"/>\n\n    <div class=\"cr-mega-button-text \" ng-bind-html=\"getLabel() | translate\"></div>\n</button>");$templateCache.put('$/angular/templates/cr-topic.html', "<button class=\"cr-topic {{ getWindowClass() }}\" ng-class=\"{active: (getActive() == 'true')}\" ng-click=\"open()\" >\n\n    <img class=\"cr-topic-image-active\"\n         ng-src=\"/assets/images/{{getImage()}}_active.png\"/>\n\n    <img class=\"cr-topic-image-inactive\"\n         ng-src=\"/assets/images/{{getImage()}}_inactive.png\"/>\n\n    <img class=\"cr-topic-image-hover\"\n         ng-src=\"/assets/images/{{getImage()}}_hover.png\"/>\n\n    <div class=\"cr-topic-text \" ng-bind-html=\"getLabel() | translate\"></div>\n</button>");$templateCache.put('$/angular/templates/mm-field-text.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><div class=\"field_error_message_flash\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><input ng-disabled=\"getInfo().disabled\" placeholder=\"{{getInfo().placeholder}}\" numbers-only=\"{{getInfo().numbersOnly}}\" focus-me=\"getInfo().focus()\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" ng-model=\"getInfo().field\" type=\"{{fieldType}}\"></div><div><div ng-if=\"isValidationDefined\"><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/mm-field-date.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><div class=\"dropdown\"></div><a class=\"dropdown-toggle\" data-target=\"#\" id=\"{{id}}\" role=\"button\" data-toggle=\"dropdown\" href=\"\"><div class=\"input-group\"><input class=\"form-control\" ng-model=\"resultFormated\" type=\"text\"><span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span></div><ul class=\"dropdown-menu date_input\" aria-labelledby=\"dLabel\" role=\"menu\"><datetimepicker data-ng-model=\"result\" data-datetimepicker-config=\"{ dropdownSelector: '{{idHtag}}',minView:'day' }\"></datetimepicker></ul></a></div><div><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/mm-field-auto-completion.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div ng-click=\"logField()\">{{getInfo().fieldTitle}}</div><div><angucomplete minlength=\"1\" pause=\"400\" ng-disabled=\"getInfo().disabled\" id=\"members\" titlefield=\"content\" inputclass=\"form-control form-control-small\" placeholder=\"{{getInfo().placeholder}}\" selectedobject=\"result\" datafield=\"values\" url=\"{{getInfo().url}}\"></angucomplete></div><div><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div><div ng-transclude></div></div></div>");$templateCache.put('$/angular/templates/cr-number.html', "<div class=\"cr-number\">\n\n    <input\n            class=\"form-control cr-number-input\"\n            ng-model=\"ngModel\"/>\n\n</div>");$templateCache.put('$/angular/templates/cr-text.html', "<div class=\"cr-text\" ng-switch=\"getMultiline() == 'true'\">\n\n    <textarea\n            ng-switch-when=\"true\"\n            ng-attr-name=\"{{ $parent.getName() }}\"\n            class=\"form-control cr-text-textarea\"\n            ng-model=\"$parent.ngModel\"\n            ng-disabled=\"getDisabled() == true\"></textarea>\n\n    <input\n            ng-switch-default\n            ng-attr-name=\"{{ $parent.getName() }}\"\n            class=\"form-control cr-text-input\"\n            ng-model=\"$parent.ngModel\"\n            ng-disabled=\"$parent.getDisabled() == true\"/>\n\n</div>");$templateCache.put('$/angular/templates/cr-login.html', "<a class=\"pull-right\"\n   ng-click=\"logout()\"\n   ng-show=\"isAuthenticated()\">\n    <i class=\"fa fa-sign-out\"></i>\n    <span>Logout</span>\n</a>");$templateCache.put('$/angular/templates/cr-slider.html', "<div class=\"cr-slider\" collapse=\"!isVisible()\">\n\n    <div class=\"cr-slider-container\" ng-if=\"!getVertical()\">\n\n        <!-- Sections -->\n            <span class=\"cr-slider-section first\"\n                  style=\"left: 0px\"\n                >\n            </span>\n\n        <div class=\"cr-slider-null-box\">\n              <span class=\"cr-slider-tick first\"\n                    ng-mousedown=\"setValue(computedOptions[0])\"\n                    style=\"left: 0px\"\n                  ></span>\n\n        </div>\n\n        <div class=\"cr-slider-not-null-box\">\n\n            <div class=\"cr-slider-not-null-box-holder\">\n                <!-- Sections -->\n                <span class=\"cr-slider-section\"\n                      style=\"left: 0px; width: {{ (computedOptions.length-2) * 50.0 }}px\"\n                    >\n                </span>\n\n                <!-- Ticks -->\n                <span class=\"cr-slider-tick\"\n                      ng-repeat=\"o in computedOptions\"\n                      ng-if=\"o.value != null\"\n                      ng-class=\"{active: isValue(o), first: ($index == 0)}\"\n                      ng-mousedown=\"setValue(o)\"\n                      style=\"left: {{ ($index - 1) * 50 }}px\"\n                    ></span>\n\n\n                <!-- Labels -->\n                <span class=\"cr-slider-text\"\n                      ng-repeat=\"o in computedOptions\"\n                      ng-if=\"o.label != null\"\n                      ng-class=\"{active: isValue(o)}\"\n                      ng-mousedown=\"setValue(o)\"\n                      style=\"left: {{ ($index - 1 )* 50}}px\"\n                      ng-bind-html=\"labelFilter('' + o.label)\"\n                    ></span>\n\n            </div>\n        </div>\n    </div>\n\n    <div class=\"cr-slider-container-vertical\" ng-if=\"getVertical()\"\n         style=\"height: {{ computedOptions.length * 50.0 }}px\" ng-show=\"computedOptions.length <= 3\">\n\n\n        <!-- Sections -->\n            <span class=\"cr-slider-section first\"\n                  ng-repeat=\"o in computedOptions\"\n                  style=\"left: 0px\"\n                >\n            </span>\n\n        <div class=\"cr-slider-null-box\">\n              <span class=\"cr-slider-tick first\"\n                    ng-mousedown=\"setValue(computedOptions[0])\"\n                    style=\"left: 0px\"\n                  ></span>\n\n        </div>\n\n        <div class=\"cr-slider-not-null-box\" style=\"height: {{ computedOptions.length * 50.0 }}px\">\n\n            <!-- Sections -->\n            <span class=\"cr-slider-section\"\n                  style=\"top: 0px; height: {{ (computedOptionsFiltered.length-2) * 50.0 }}px\"\n                >\n            </span>\n\n            <!-- Ticks -->\n\n            <span class=\"cr-slider-tick\"\n                  ng-repeat=\"o in computedOptions\"\n                  ng-if=\"o.value != null\"\n                  ng-class=\"{active: isValue(o), first: ($index == 0)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"top: {{ ($index - 1) * 50 }}px\"\n                ></span>\n\n\n            <!-- Labels -->\n\n            <span class=\"cr-slider-text\"\n                  ng-repeat=\"o in computedOptions\"\n                  ng-if=\"o.label != null\"\n                  ng-class=\"{active: isValue(o)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"top: {{ ($index - 1 )* 50}}px\"\n                  ng-bind-html=\"labelFilter('' + o.label)\"\n                ></span>\n\n\n        </div>\n    </div>\n\n\n    <div class=\"cr-slider-container-vertical\" ng-if=\"getVertical() == 'true'\"\n         style=\"height: {{ computedOptionsFiltered.length * 50.0 }}px\" ng-hide=\"computedOptions.length <= 3\">\n\n\n        <!-- Sections -->\n            <span class=\"cr-slider-section first\"\n                  ng-repeat=\"o in computedOptionsFiltered\"\n                  style=\"left: 0px\"\n                >\n            </span>\n\n        <div class=\"cr-slider-null-box\">\n              <span class=\"cr-slider-tick first\"\n                    ng-mousedown=\"setValue(computedOptionsFiltered[0])\"\n                    style=\"left: 0px\"\n                  ></span>\n\n        </div>\n\n        <div class=\"cr-slider-not-null-box\" style=\"height: {{ computedOptionsFiltered.length * 50.0 }}px\">\n\n\n            <!-- Sections -->\n            <span class=\"cr-slider-section\"\n                  style=\"top: 0px; height: {{ (computedOptionsFiltered.length-2) * 50.0 }}px\"\n                >\n            </span>\n\n            <!-- Ticks -->\n\n            <span class=\"cr-slider-tick\"\n                  ng-repeat=\"o in computedOptionsFiltered\"\n                  ng-show=\"o.value != null\"\n                  ng-class=\"{active: isValue(o), first: ($index == 0)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"top: {{ ($index - 1) * 50 }}px\"\n                ></span>\n\n\n            <!-- Labels -->\n\n            <span class=\"cr-slider-text\"\n                  ng-repeat=\"o in computedOptionsFiltered\"\n                  ng-show=\"o.label != null\"\n                  ng-class=\"{active: isValue(o)}\"\n                  ng-mousedown=\"setValue(o)\"\n                  style=\"top: {{ ($index - 1 )* 50}}px\"\n                  ng-bind-html=\"labelFilter('' + o.label)\"\n                ></span>\n\n            <span class=\"cr-slider-text\"\n                  ng-hide=\"expanded\"\n                  ng-mousedown=\"toggleExpanded()\"\n                  style=\"top: {{ (computedOptionsFiltered.length - 2 )* 50}}px\"\n                >...</span>\n\n\n        </div>\n\n\n    </div>\n</div>");$templateCache.put('$/angular/templates/cr-section.html', "<div class=\"cr-section\">\n    <div class=\"row\">\n        <div class=\"col-md-12\">\n            <label class=\"cr-section-label\" ng-bind-html=\"getLabel() | translate\"></label>\n        </div>\n    </div>\n    <div class=\"row\">\n        <div class=\"col-xs-11 col-xs-offset-1\">\n            <div class=\"cr-section-editor\" ng-transclude></div>\n        </div>\n    </div>\n</div>");$templateCache.put('$/angular/templates/cr-multi-text.html', "<div class=\"cr-multi-text\">\n    <input ng-repeat=\"i in getRange() track by $index\"\n           type=\"text\"\n           class=\"form-control cr-multi-text-input\"\n           ng-class=\"{'cr-multi-text-input-empty': !ngModel[$index], 'cr-multi-text-input-invalid': !validation[$index] }\"\n           ng-model=\"ngModel[$index]\"/>\n</div>");$templateCache.put('$/angular/views/test/modal-confirm-vampire.html', "<div class=\"modal-header\">\n    <h3 class=\"modal-title\">Vampire</h3>\n</div>\n<div class=\"modal-body\">\n    Are you sure to be a <b>{{ selected }}</b> ?!\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"ok()\">Yes</button>\n    <button class=\"btn btn-warning\" ng-click=\"cancel()\">No</button>\n</div>");$templateCache.put('$/angular/views/test/controls-demo.html', "<div class=\"cr-directives-demo\">\n\n    <div class=\"container\">\n        <div class=\"row\">\n            <div class=\"col-md-12\">\n                <h3>Controls</h3>\n            </div>\n        </div>\n        <div class=\"row\">\n            <div class=\"col-md-12\">\n                <tabset class=\"cr-tabset\">\n\n                    <tab>\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Dropdown\n                        </tab-heading>\n\n                        <cr-question label=\"profile.Q1110.label\">\n                            <cr-dropdown ng-options=\"x.items\" ng-model=\"x.sel\"></cr-dropdown>\n                        </cr-question>\n\n                    </tab>\n\n                    <tab>\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Text\n                        </tab-heading>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-text ng-model=\"x.firstName\"></cr-text>\n                        </cr-question>\n                        <cr-question label=\"Q6000\">\n                            <cr-text multiline=\"true\" ng-model=\"x.comment\"></cr-text>\n                        </cr-question>\n                        <cr-question label=\"Q6000\">\n                            <cr-text type=\"password\" ng-model=\"x.password\"></cr-text>\n                        </cr-question>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-multi-text ng-model=\"x.items\" regex=\"^[^@]*$\"></cr-multi-text>\n                        </cr-question>\n\n                    </tab>\n\n\n                    <tab>\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Number\n                        </tab-heading>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-number ng-model=\"x.cnt\"></cr-number>\n                        </cr-question>\n                    </tab>\n\n\n                    <tab>\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Range 2 inputs\n                        </tab-heading>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-double-range ng-range-min=\"x.doubleRange.rangeMin\" ng-range-max=\"x.doubleRange.rangeMax\"\n                                             ng-min=\"x.doubleRange.min\" ng-max=\"x.doubleRange.max\"></cr-double-range>\n                        </cr-question>\n                    </tab>\n\n                    <tab select=\"alertMe()\">\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Radio\n                        </tab-heading>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-radio ng-model=\"x.radio.value\" ng-options=\"x.radio.options\"></cr-radio>\n                        </cr-question>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-radio ng-model=\"x.radio.value\" ng-options=\"x.radio.simpleOptions\"></cr-radio>\n                        </cr-question>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-radio ng-model=\"x.radio.value\" ng-options=\"x.radio.options\"\n                                      free-allowed=\"true\"></cr-radio>\n                        </cr-question>\n                    </tab>\n\n\n                    <tab select=\"alertMe()\">\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Slider\n                        </tab-heading>\n\n                        <cr-question label=\"BAD (static labels)\">\n                            <cr-slider ng-model=\"x.slider.value\" ng-steps=\"x.slider.steps\"></cr-slider>\n                        </cr-question>\n                        <cr-question label=\"GOOD (labels by filter)\">\n                            <cr-slider ng-model=\"x.slider.value\" ng-steps=\"x.slider.simpleSteps\"\n                                       filter=\"toHour\"></cr-slider>\n                        </cr-question>\n                        <cr-question label=\"Q6000\">\n                            <cr-slider ng-model=\"x.continuousSlider.value\" min=\"10\" max=\"2000\" step=\"10\"></cr-slider>\n                        </cr-question>\n\n                        <cr-question label=\"language\">\n                            <button class=\"btn btn-default\" ng-click=\"setLanguage('fr')\">Français</button>\n                            <button class=\"btn btn-default\" ng-click=\"setLanguage('nl')\">Nederlands</button>\n                        </cr-question>\n                    </tab>\n\n\n                    <tab select=\"alertMe()\">\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> YES/NO\n                        </tab-heading>\n\n                        <cr-question label=\"Q6000\">\n                            <cr-boolean ng-model=\"x.yesno.value\"></cr-boolean>\n                            <cr-slider ng-visible=\"x.yesno.value\" ng-model=\"x.continuousSlider.value1\" min=\"10\"\n                                       max=\"2000\" step=\"10\"></cr-slider>\n                            <cr-slider ng-visible=\"x.yesno.value\" ng-model=\"x.continuousSlider.value2\" min=\"10\"\n                                       max=\"2000\" step=\"10\"></cr-slider>\n                            <cr-slider ng-visible=\"x.yesno.value\" ng-model=\"x.continuousSlider.value3\" min=\"10\"\n                                       max=\"2000\" step=\"10\"></cr-slider>\n                        </cr-question>\n                    </tab>\n\n                    <tab select=\"alertMe()\">\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Topic\n                        </tab-heading>\n\n                        <div class=\"col-md-3 col-sm-4 col-xs-6\">\n                            <cr-topic\n                                image=\"household/topics/topic_presence\"\n                                view=\"$/angular/views/household/profile/topics/profile-presence.html\"\n                                controller=\"ModalTopicCtrl\"\n                                window-class=\"cr-theme-green\"\n                                ></cr-topic>\n                        </div>\n\n\n                        <div class=\"col-md-3 col-sm-4 col-xs-6\">\n                            <cr-topic\n                                image=\"household/topics/topic_programs\"\n                                view=\"$/angular/views/household/profile/topics/profile-programs.html\"\n                                controller=\"ModalTopicCtrl\"\n                                window-class=\"cr-theme-blue\"\n                                ></cr-topic>\n                        </div>\n\n                        <div class=\"col-md-3 col-sm-4 col-xs-6\">\n                            <cr-topic\n                                image=\"household/topics/topic_heating\"\n                                view=\"$/angular/views/household/profile/topics/profile-heating.html\"\n                                controller=\"ModalTopicCtrl\"\n                                window-class=\"cr-theme-red\"\n                                ></cr-topic>\n                        </div>\n\n                        <div class=\"col-md-3 col-sm-4 col-xs-6\">\n                            <cr-topic\n                                active=\"true\"\n                                image=\"household/topics/topic_lighting\"\n                                view=\"$/angular/views/household/profile/topics/profile-lighting.html\"\n                                controller=\"ModalTopicCtrl\"\n                                window-class=\"cr-theme-orange\"></cr-topic>\n                        </div>\n\n                        <div class=\"col-md-3 col-sm-4 col-xs-6\">\n                            <cr-topic\n                                image=\"household/topics/topic_dinner\"\n                                view=\"$/angular/views/household/profile/topics/profile-dinner.html\"\n                                controller=\"ModalTopicCtrl\"\n                                window-class=\"cr-theme-magenta\"></cr-topic>\n                        </div>\n                    </tab>\n                </tabset>\n\n            </div>\n        </div>\n    </div>\n\n    <br/> <br/> <br/> <br/> <br/>\n    <hr/>\n    <div class=\"container\">\n        <div class=\"row\">\n            <div class=\"col-md-12 text-left\">\n                <a href=\"#welcome\">< Back</a>\n            </div>\n        </div>\n    </div>\n\n</div>");$templateCache.put('$/angular/views/admin/login.html', "<div>\n    <div class=\"container\">\n        <div class=\"row\">\n            <h1>Administrator access</h1>\n        </div>\n        <div class=\"row\">\n            <div class=\"col-md-5\">\n\n                <cr-question label=\"generic.emailAddress\">\n                    <cr-text ng-model=\"loginParams.email\" name=\"email\"></cr-text>\n                </cr-question>\n                <cr-question label=\"generic.password\">\n                    <cr-text ng-model=\"loginParams.password\" name=\"password\" type=\"password\"></cr-text>\n                </cr-question>\n                <br/>\n                <button class=\"btn btn-primary\" ng-click=\"login()\"\n                        ng-bind-html=\"'welcome.button.login' | translate\"></button>\n            </div>\n        </div>\n    </div>\n</div>");$templateCache.put('$/angular/views/admin/main.html', "<div class=\"cr-directives-demo tc-super-admin\">\n\n\n    <div class=\"container\">\n        <div class=\"row\">\n            <div class=\"col-md-9\">\n                <h3>Administrator panel</h3>\n\n            </div>\n            <div class=\"col-md-3\">\n                <a ng-click=\"logout()\"> Logout</a>\n            </div>\n        </div>\n        <div class=\"row\">\n            <div class=\"col-md-12\">\n                <tabset class=\"cr-tabset\">\n\n                    <tab>\n                        <tab-heading>\n                            <i class=\"fa fa-gear\"></i> Réductions\n                        </tab-heading>\n                        <table style=\"width: 100%\">\n                            <tr style=\"border-bottom:1px solid #b0b0b0;\">\n                                <th rowspan=\"2\" colspan=\"3\"></th>\n                                <th rowspan=\"2\" colspan=\"3\" style=\"border-left : 1px solid #b0b0b0;\">Potential</th>\n                                <th colspan=\"12\" style=\"border-left : 1px solid #b0b0b0;\">Effective</th>\n                            </tr>\n                            <tr  style=\"border-bottom:1px solid #b0b0b0;\">\n                                <th colspan=\"3\" style=\"border-left : 1px solid #b0b0b0;\">Day 1</th>\n                                <th colspan=\"3\" style=\"border-left : 1px solid #b0b0b0;\">Day 2</th>\n                                <th colspan=\"3\" style=\"border-left : 1px solid #b0b0b0;\">Day 3</th>\n                                <th colspan=\"3\" style=\"border-left : 1px solid #b0b0b0;\">Day 4</th>\n                            </tr>\n                            <tr>\n                                <th>Date</th>\n                                <th>nb survey</th>\n                                <th>nb participants</th>\n                                <th>17-18H</th>\n                                <th>18-19H</th>\n                                <th>19-20H</th>\n                                <th>17-18H</th>\n                                <th>18-19H</th>\n                                <th>19-20H</th>\n                                <th>17-18H</th>\n                                <th>18-19H</th>\n                                <th>19-20H</th>\n                                <th>17-18H</th>\n                                <th>18-19H</th>\n                                <th>19-20H</th>\n                                <th>17-18H</th>\n                                <th>18-19H</th>\n                                <th>19-20H</th>\n                            </tr>\n                            <tr ng-repeat=\"batch in batchs | orderBy:'date':'reverse'\">\n                                <td>{{batch.date | date:\"yyyy-MM-dd\" }}</td>\n                                <td>  {{batch.potentialBatch.nbSurveys}} </td>\n                                <td>  {{batch.potentialBatch.nbParticipants}} </td>\n\n                                <td>  {{getValue(batch.potentialBatch.batchResultItemList, null,\"FIRST\") | number:2}} </td>\n                                <td>  {{getValue(batch.potentialBatch.batchResultItemList, null,\"SECONDE\") | number:2}} </td>\n                                <td>  {{getValue(batch.potentialBatch.batchResultItemList, null,\"THIRD\") | number:2}} </td>\n\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY1\",\"FIRST\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY1\",\"SECONDE\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY1\",\"THIRD\") | number:2}} </td>\n\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY2\",\"FIRST\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY2\",\"SECONDE\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY2\",\"THIRD\") | number:2}} </td>\n\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY3\",\"FIRST\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY3\",\"SECONDE\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY3\",\"THIRD\") | number:2}} </td>\n\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY4\",\"FIRST\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY4\",\"SECONDE\") | number:2}} </td>\n                                <td>  {{getValue(batch.effectiveBatch.batchResultItemList, \"DAY4\",\"THIRD\") | number:2}} </td>\n\n                            </tr>\n                        </table>\n                    </tab>\n                </tabset>\n            </div>\n        </div>\n    </div>\n</div>");$templateCache.put('$/angular/views/household/profile/topics/profile-lighting.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_lighting_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\"  ng-bind-html=\"'topic.lighting.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-section label=\"Q1160.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1160',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1160','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1160',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1160',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1160','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1160',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1160',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1160','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1160',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n    <cr-question label=\"Q1220.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1220',period:null}\" ng-model=\"getAnswerValue('Q1220').stringValue\" ng-steps=\"getNumericOptions('Q1220',0,5,1)\"></cr-slider>\n    </cr-question>\n    <cr-question label=\"Q1230.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1230',period:null}\" ng-model=\"getAnswerValue('Q1230').stringValue\" ng-steps=\"getNumericOptions('Q1230',0,5,1)\"></cr-slider>\n    </cr-question>\n    <cr-question label=\"Q1700.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1700',period:null}\" ng-model=\"getAnswerValue('Q1700').stringValue\" ng-steps=\"getNumericOptions('Q1700',0,5,1)\"></cr-slider>\n    </cr-question>\n    <cr-question label=\"Q1750.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1750',period:null}\" ng-model=\"getAnswerValue('Q1750').stringValue\" ng-steps=\"getOptions('Q1750')\" vertical=\"true\"></cr-slider>\n    </cr-question>\n    <cr-question label=\"Q1800.label\">\n        <cr-slider ng-model=\"getAnswerValue('Q1800').doubleValue\" min=\"0\" max=\"2000\" step=\"100\" filter=\"toWatts\"></cr-slider>\n    </cr-question>\n\n    <cr-section label=\"Q2000.label\">\n        <cr-question label=\"Q2010.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q2010',period:null}\" ng-model=\"getAnswerValue('Q2010').stringValue\" ng-steps=\"getOptions('Q2010')\" vertical=\"true\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"Q2020.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q2020',period:null}\" ng-model=\"getAnswerValue('Q2020').stringValue\" ng-steps=\"getOptions('Q2020')\" vertical=\"true\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"Q2030.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q2030',period:null}\" ng-model=\"getAnswerValue('Q2030').stringValue\" ng-steps=\"getOptions('Q2030')\" vertical=\"true\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"Q2040.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q2040',period:null}\" ng-model=\"getAnswerValue('Q2040').stringValue\" ng-steps=\"getOptions('Q2040')\" vertical=\"true\"></cr-slider>\n        </cr-question>\n    </cr-section>\n\n    <cr-question label=\"Q1235.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1235',period:null}\" ng-model=\"getAnswerValue('Q1235').stringValue\" ng-steps=\"getNumericOptions('Q1235',0,5,1)\"></cr-slider>\n    </cr-question>\n\n\n\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/profile/topics/profile-presence.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_presence_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.presence.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"Q1300.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1300',period:null}\" ng-model=\"getAnswerValue('Q1300').doubleValue\" min=\"0\" max=\"20\" step=\"1\"></cr-slider>\n    </cr-question>\n\n    <cr-question label=\"Q1500.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1500',period:null}\" ng-model=\"getAnswerValue('Q1500').stringValue\" ng-steps=\"getOptions('Q1500')\" vertical=\"true\"></cr-slider>\n    </cr-question>\n\n    <cr-question label=\"Q1400.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1400',period:null}\" ng-model=\"getAnswerValue('Q1400').stringValue\" ng-steps=\"getNumericOptions('Q1400',0,5,1)\"></cr-slider>\n    </cr-question>\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/profile/topics/profile-programs.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_programs_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.programs.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-section label=\"Q1110.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1110',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1110','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1110',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1110',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1110','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1110',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1110',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1110','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1110',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n    <cr-section label=\"Q1120.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1120',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1120','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1120',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1120',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1120','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1120',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1120',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1120','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1120',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n    <cr-section label=\"Q1130.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1130',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1130','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1130',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1130',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1130','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1130',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1130',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1130','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1130',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/profile/topics/profile-heating.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_heating_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\"  ng-bind-html=\"'topic.heating.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n    <cr-question label=\"Q1600.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1600',period:null}\" ng-model=\"getAnswerValue('Q1600').doubleValue\" min=\"0\" max=\"1000\" step=\"10\" filter=\"toSquareMeters\"></cr-slider>\n    </cr-question>\n    <cr-question label=\"Q1900.label\">\n        <cr-slider ng-model=\"getAnswerValue('Q1900').doubleValue\" min=\"0\" max=\"20000\" step=\"500\" filter=\"toWatts\"></cr-slider>\n    </cr-question>\n\n    <cr-question label=\"Q1210.label\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q1210',period:null}\" ng-model=\"getAnswerValue('Q1210').stringValue\" ng-steps=\"getNumericOptions('Q1210',0,5,1)\"></cr-slider>\n    </cr-question>\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/profile/topics/profile-dinner.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_dinner_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\"  ng-bind-html=\"'topic.dinner.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-section label=\"Q1140.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1140',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1140','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1140',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1140',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1140','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1140',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1140',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1140','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1140',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n\n    <cr-section label=\"Q1150.label\">\n        <cr-question label=\"period.1.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1150',period:'FIRST'}\" ng-model=\"getAnswerValue('Q1150','FIRST').stringValue\" ng-steps=\"getNumericOptions('Q1150',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.2.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1150',period:'SECOND'}\" ng-model=\"getAnswerValue('Q1150','SECOND').stringValue\"  ng-steps=\"getNumericOptions('Q1150',0,5,1)\"></cr-slider>\n        </cr-question>\n        <cr-question label=\"period.3.label\">\n            <cr-slider cr-tootip-on-respond=\"{key:'Q1150',period:'THIRD'}\" ng-model=\"getAnswerValue('Q1150','THIRD').stringValue\" ng-steps=\"getNumericOptions('Q1150',0,5,1)\"></cr-slider>\n        </cr-question>\n    </cr-section>\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/profile/household-profile.html', "<div class=\"cr-profile\">\n\n    <div class=\"container\">\n\n        <div class=\"row cr-profile-header\">\n\n            <div class=\"col-md-6\">\n                <h1 class=\"cr-profile-title\">Ménage > Profil de consommation</h1>\n            </div>\n            <div class=\"col-md-6\">\n                <cr-login></cr-login>\n            </div>\n        </div>\n\n        <div class=\"row cr-profile-content\">\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6 col-md-offset-1\">\n                <cr-topic\n                    image=\"household/topics/topic_presence\"\n                    view=\"$/angular/views/household/profile/topics/profile-presence.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-green\"\n                    label=\"topic.presence.label\"\n                    active=\"{{ isProfileTopicCompleted('presence') }}\"\n                    ></cr-topic>\n            </div>\n\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_programs\"\n                    view=\"$/angular/views/household/profile/topics/profile-programs.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-blue\"\n                    label=\"topic.programs.label\"\n                    active=\"{{ isProfileTopicCompleted('programs') }}\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_heating\"\n                    view=\"$/angular/views/household/profile/topics/profile-heating.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-red\"\n                    label=\"topic.heating.label\"\n                    active=\"{{ isProfileTopicCompleted('heating') }}\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_lighting\"\n                    view=\"$/angular/views/household/profile/topics/profile-lighting.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-orange\"\n                    label=\"topic.lighting.label\"\n                    active=\"{{ isProfileTopicCompleted('lighting') }}\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_dinner\"\n                    view=\"$/angular/views/household/profile/topics/profile-dinner.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-magenta\"\n                    label=\"topic.dinner.label\"\n                    active=\"{{ isProfileTopicCompleted('dinner') }}\"\n                    ></cr-topic>\n            </div>\n\n        </div>\n\n        <div class=\"row\">\n            <div class=\"col-md-12\">\n\n                <button ui-sref=\"root.welcome\"\n                        ng-show=\"!isAuthenticated()\"\n                        class=\"cr-mega-button pull-left\">\n                    <i class=\"fa fa-caret-left fa-2x\"></i>\n                    <span>&nbsp;</span>\n                    <span ng-bind-html=\"'nav.back-to-welcome' | translate\"></span>\n                </button>\n\n\n                <button ui-sref=\"root.householdActions\"\n                        ng-disabled=\" !isProfileTopicCompleted('presence') || !isProfileTopicCompleted('programs') || !isProfileTopicCompleted('heating') || !isProfileTopicCompleted('lighting') || !isProfileTopicCompleted('dinner')\"\n                        class=\"cr-mega-button pull-right\">\n                    <span ng-bind-html=\"'nav.to-actions' | translate\"></span>\n                    <span>&nbsp;</span>\n                    <i class=\"fa fa-caret-right fa-2x\"></i>\n                </button>\n            </div>\n        </div>\n\n        <div class=\"row cr-profile-result\">\n            <div class=\"col-md-12\">\n                <p>Puissance effaçable potentielle: {{ potentialReduction.averagePowerReduction | numberToI18N: 0 }}\n                    Watt</p>\n\n                <p>Mon Potentiel par jour sur la période 17h-20h: {{ potentialReduction.energyReduction | numberToI18N\n                    }} kWh</p>\n                <button class=\"btn btn-primary\" ng-click=\"getPotentialReduction()\">Refresh</button>\n            </div>\n        </div>\n\n\n    </div>\n\n</div>");$templateCache.put('$/angular/views/household/terms.html', "\n<div class=\"modal-header\">\n    <h3 class=\"modal-title cr-modal-title-text\"  ng-bind-html=\"'account.terms.modal.title' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n</div>\n<div class=\"modal-footer\">\n</div>");$templateCache.put('$/angular/views/household/results/household-results.html', "<div class=\"cr-results\">\n\n    <div class=\"container\">\n\n        <div class=\"row cr-results-header\">\n\n            <div class=\"col-md-6\">\n                <h1 class=\"cr-profile-title\">Ménage > Profil de consommation</h1>\n            </div>\n            <div class=\"col-md-6\">\n                <cr-login></cr-login>\n            </div>\n        </div>\n\n        <div class=\"row\" class=\"graphHolder\" ng-if=\"data.length > 0\">\n            <nvd3 options='$parent.options' data='$parent.data'></nvd3>\n        </div>\n\n        <div class=\"row\">\n            <div class=\"col-md-12\">\n                <button ui-sref=\"root.householdActions\"\n                        class=\"pull-left cr-mega-button\">\n                    <i class=\"fa fa-caret-left fa-2x\"></i>\n                    <span>&nbsp;</span>\n                    <span ng-bind-html=\"'nav.back-to-actions' | translate\"></span>\n                </button>\n            </div>\n        </div>\n\n\n    </div>\n\n</div>");$templateCache.put('$/angular/views/household/account/account-change-email.html', "<div class=\"modal-header\">\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'account.button.changeEmail' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"account.info.oldPassword\">\n        <cr-text ng-model=\"o.oldPassword\" type=\"password\" name=\"password\"\n                 ng-validation=\"validation.oldPassword\"></cr-text>\n        <p class=\"cr-error\" ng-show=\"validation.oldPassword.valid == false && noSubmitYet == false\">\n            {{'account.errorMessage.password' | translate}}</p>\n    </cr-question>\n    <cr-question label=\"account.info.newEmail\">\n        <cr-text ng-model=\"o.newEmail\" name=\"email\"\n                 ng-validation=\"validation.newEmail\"></cr-text>\n        <p class=\"cr-error\" ng-show=\"validation.newEmail.valid == false && noSubmitYet == false\">\n            {{'account.errorMessage.emailAddress' | translate}}</p>\n    </cr-question>\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-disabled=\"loading == true\" ng-click=\"save()\"\n            ng-bind-html=\"'generic.save' | translate\"></button>\n    <img ng-show=\"loading == true\" ng-src=\"/assets/images/modal-loading.gif\"/>\n</div>");$templateCache.put('$/angular/views/household/account/account-change-password.html', "<div class=\"modal-header\">\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'account.button.changePassword' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"account.info.oldPassword\">\n        <cr-text ng-model=\"o.oldPassword\" type=\"password\" name=\"password\"\n                 ng-validation=\"validation.oldPassword\"></cr-text>\n        <p class=\"cr-error\" ng-show=\"validation.oldPassword.valid == false && noSubmitYet == false\">\n            {{'account.errorMessage.password' | translate}}</p>\n    </cr-question>\n    <cr-question label=\"account.info.newPassword\">\n        <cr-text ng-model=\"o.newPassword\" type=\"password\" name=\"password\"\n                 ng-validation=\"validation.newPassword\"></cr-text>\n        <p class=\"cr-error\" ng-show=\"validation.newPassword.valid == false && noSubmitYet == false\">\n            {{'account.errorMessage.password' | translate}}</p>\n    </cr-question>\n    <cr-question label=\"account.info.repeatPassword\">\n        <cr-text ng-model=\"o.repeatPassword\"type=\"password\" name=\"password\"\n                 ng-validation=\"validation.repeatPassword\"></cr-text>\n        <p class=\"cr-error\" ng-show=\"validation.repeatPassword.valid == false && noSubmitYet == false\">\n            {{'account.errorMessage.repeatPassword' | translate}}</p>\n    </cr-question>\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-disabled=\"loading == true\" ng-click=\"save()\"\n            ng-bind-html=\"'generic.save' | translate\"></button>\n    <img ng-show=\"loading == true\" ng-src=\"/assets/images/modal-loading.gif\"/>\n</div>");$templateCache.put('$/angular/views/household/account/household-account.html', "<div class=\"cr-account\">\n    <div class=\"container\">\n\n        <div class=\"row cr-actions-header\">\n\n            <div class=\"col-md-6\">\n                <h1 class=\"cr-actions-title\">Ménage > Account</h1>\n            </div>\n\n            <div class=\"col-md-6 cr-navigation\">\n\n                <a class=\"cr-profile-login-link\" ng-click=\"logout()\" ng-show=\"isAuthenticated()\"> Logout</a>\n\n                <a\n                        class=\"cr-actions-profile-link\"\n                        ng-bind-html=\"'nav.back-to-action' | translate\"\n                        ui-sref=\"root.householdActions\">\n                </a>\n\n            </div>\n\n        </div>\n\n        <div class=\"row\">\n\n            <div class=\"col-md-2\">\n            </div>\n            <div class=\"col-md-8\">\n\n\n                <!-- Q5110 generic.firstName -->\n                <cr-question label=\"generic.firstName\">\n                    <cr-text ng-model=\"getAccount().firstName\" name=\"firstname\"\n                             ng-validation=\"validation.firstName\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.firstName.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.firstName' | translate}}</p>\n                </cr-question>\n\n                <!-- Q5120 generic.lastName -->\n                <cr-question label=\"generic.lastName\">\n                    <cr-text ng-model=\"getAccount().lastName\" name=\"lastname\"\n                             ng-validation=\"validation.lastName\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.lastName.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.lastName' | translate}}</p>\n                </cr-question>\n\n                <!-- Q5130 generic.emailAddress -->\n                <cr-question label=\"generic.emailAddress\">\n                    <cr-text ng-model=\"getAccount().email\" name=\"email\"\n                             ng-validation=\"validation.emailAddress\"\n                             ng-disabled=\"isAuthenticated() == true\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.emailAddress.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.emailAddress' | translate}}</p>\n                    <button class=\"btn btn-primary\"\n                            ng-show=\"isAuthenticated() == true\"\n                            ng-bind-html=\"'account.button.changeEmail' | translate\"\n                            ng-click=\"openModal('household/account/account-change-email','ModalChangeEmailCtrl')\"></button>\n                </cr-question>\n\n                <!-- Q5140 generic.password -->\n                <cr-question label=\"generic.password\">\n                    <cr-text ng-model=\"getAccount().password\" type=\"password\" name=\"password\"\n                             ng-validation=\"validation.password\"\n                             ng-disabled=\"isAuthenticated() == true\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.password.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.password' | translate}}</p>\n                    <button class=\"btn btn-primary\"\n                            ng-show=\"isAuthenticated() == true\"\n                            ng-bind-html=\"'account.button.changePassword' | translate\"\n                            ng-click=\"openModal('household/account/account-change-password','ModalChangePasswordCtrl')\"></button>\n                </cr-question>\n\n                <!-- repeat password -->\n                <cr-question label=\"account.info.repeatPassword\"\n                             ng-hide=\"isAuthenticated() == true\">\n                    <cr-text ng-model=\"o.repeatPassword\" type=\"password\" name=\"password\"\n                             ng-validation=\"validation.repeatPassword\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.repeatPassword.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.repeatPassword' | translate}}</p>\n                </cr-question>\n\n                <!-- Q5150 account.zip.label -->\n                <cr-question label=\"account.zip.label\">\n                    <cr-text ng-model=\"getAccount().zipCode\" name=\"zip\" ng-validation=\"zip\"></cr-text>\n                    <p class=\"cr-error\" ng-show=\"validation.zip.valid == false && noSubmitYet == false\">\n                        {{'account.errorMessage.zip' | translate}}</p>\n                </cr-question>\n\n                <!-- Q5210 account.powerprovider.label -->\n                <cr-question label=\"account.powerprovider.label\">\n                    <cr-slider ng-steps=\"getOptions('Q5210')\" ng-model=\"getAccount().powerProvider\"\n                               vertical=\"true\"></cr-slider>\n                </cr-question>\n\n                <!-- Q5220 account.powerconsumercategory.labe -->\n                <cr-question label=\"account.powerconsumercategory.label\">\n                    <cr-slider ng-steps=\"getOptions('powerconsumercategory')\"\n                               ng-model=\"getAccount().powerComsumerCategory\" vertical=\"true\"></cr-slider>\n                </cr-question>\n\n                <!-- Q5310  account.otheremails.label DTO : otherEmailAddresses -->\n                <cr-question label=\"account.otheremails.label\">\n                    <cr-multi-text ng-model=\"getAccount().otherEmailAddresses\"></cr-multi-text>\n                </cr-question>\n\n                <!-- agreement -->\n                <cr-question label=\"account.info.terms&conditions.label\">\n                    <button class=\"btn btn-primary\"\n                            ng-bind-html=\"'account.info.term.button.consult' | translate\"\n                            ng-click=\"openModal('household/terms')\">\n                    </button>\n                    <cr-boolean ng-model=\"validation.terms.valid\"></cr-boolean>\n                </cr-question>\n                <p class=\"cr-error\" ng-show=\"validation.terms.valid == false && noSubmitYet == false\">\n                    {{'account.errorMessage.terms' | translate}}</p>\n\n                <!-- advertisemnt -->\n                <p ng-bind-html=\"'account.info.summaryemail.label' | translate\"></p>\n\n\n                <button class=\"btn btn-primary\" ng-disabled=\"loading == true\" ng-click=\"save()\" ng-bind-html=\"'generic.save' | translate\"></button>\n                <img ng-show=\"loading == true\" ng-src=\"/assets/images/modal-loading.gif\" />\n            </div>\n\n            <div class=\"col-md-2\">\n            </div>\n        </div>\n        <div class=\"row cr-actions-footer\">\n        </div>\n\n    </div>\n</div>");$templateCache.put('$/angular/views/household/actions/household-actions.html', "<div class=\"cr-actions\">\n\n    <div class=\"container\">\n\n        <div class=\"row cr-actions-header\">\n\n            <div class=\"col-md-6\">\n                <h1 class=\"cr-actions-title\">Ménage > Actions</h1>\n            </div>\n\n            <div class=\"col-md-6\">\n                <cr-login></cr-login>\n            </div>\n\n        </div>\n\n        <div class=\"row cr-actions-content\">\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6 col-md-offset-1\">\n                <cr-topic\n                    image=\"household/topics/topic_presence\"\n                    view=\"$/angular/views/household/actions/topics/actions-presence.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-green\"\n                    label=\"topic.presence.label\"\n                    ng-callback=\"save()\"\n                    ></cr-topic>\n            </div>\n\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_programs\"\n                    view=\"$/angular/views/household/actions/topics/actions-programs.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-blue\"\n                    label=\"topic.programs.label\"\n                    ng-callback=\"save()\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_heating\"\n                    view=\"$/angular/views/household/actions/topics/actions-heating.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-red\"\n                    label=\"topic.heating.label\"\n                    ng-callback=\"save()\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_lighting\"\n                    view=\"$/angular/views/household/actions/topics/actions-lighting.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-orange\"\n                    label=\"topic.lighting.label\"\n                    ng-callback=\"save()\"\n                    ></cr-topic>\n            </div>\n\n            <div class=\"col-md-2 col-sm-4 col-xs-6\">\n                <cr-topic\n                    image=\"household/topics/topic_dinner\"\n                    view=\"$/angular/views/household/actions/topics/actions-dinner.html\"\n                    controller=\"ModalTopicCtrl\"\n                    window-class=\"cr-theme-magenta\"\n                    label=\"topic.dinner.label\"\n                    ng-callback=\"save()\"\n                    ></cr-topic>\n            </div>\n\n        </div>\n\n\n        <div class=\"row\">\n            <div class=\"col-md-412 cr-navigation\">\n                <button ui-sref=\"root.householdProfile\"\n                        class=\"pull-left cr-mega-button\">\n                    <i class=\"fa fa-caret-left fa-2x\"></i>\n                    <span>&nbsp;</span>\n                    <span ng-bind-html=\"'nav.back-to-profile' | translate\"></span>\n                </button>\n                <button ui-sref=\"root.householdResults\"\n                        ng-show=\"isAuthenticated()\"\n                        class=\"pull-right cr-mega-button\">\n                    <span ng-bind-html=\"'nav.to-results' | translate\"></span>\n                    <span>&nbsp;</span>\n                    <i class=\"fa fa-caret-right fa-2x\"></i>\n                </button>\n\n                <button ui-sref=\"root.householdAccount\"\n                        ng-hide=\"isAuthenticated()\"\n                        class=\"pull-right cr-mega-button\">\n                    <span ng-bind-html=\"'nav.to-identification' | translate\"></span>\n                    <span>&nbsp;</span>\n                    <i class=\"fa fa-caret-right fa-2x\"></i>\n                </button>\n            </div>\n        </div>\n\n        <div class=\"row cr-profile-result\">\n            <div class=\"col-md-12\">\n                <p>Day 1 - Mon effectif en W: {{ effectiveReduction.reductions[0].averagePowerReduction }}</p>\n\n                <p>Day 1 - Mon effectif en kWh par jour sur la période 17h-20h: {{\n                    effectiveReduction.reductions[0].energyReduction }}</p>\n            </div>\n            <div class=\"col-md-12\">\n                <p>Day 2 - Mon effectif en W: {{ effectiveReduction.reductions[1].averagePowerReduction }}</p>\n\n                <p>Day 2 - Mon effectif en kWh par jour sur la période 17h-20h: {{\n                    effectiveReduction.reductions[1].energyReduction }}</p>\n            </div>\n            <div class=\"col-md-12\">\n                <p>Day 3 - Mon effectif en W: {{ effectiveReduction.reductions[2].averagePowerReduction }}</p>\n\n                <p>Day 3 - Mon effectif en kWh par jour sur la période 17h-20h: {{\n                    effectiveReduction.reductions[2].energyReduction }}</p>\n            </div>\n            <div class=\"col-md-12\">\n                <p>Day 4 - Mon effectif en W: {{ effectiveReduction.reductions[3].averagePowerReduction }}</p>\n\n                <p>Day 4 - Mon effectif en kWh par jour sur la période 17h-20h: {{\n                    effectiveReduction.reductions[3].energyReduction }}</p>\n            </div>\n            <div class=\"col-md-12\">\n                <button class=\"btn btn-primary\" ng-click=\"getEffectiveReduction()\">Refresh</button>\n            </div>\n\n        </div>\n\n    </div>\n</div>");$templateCache.put('$/angular/views/household/actions/topics/actions-programs.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_programs_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.programs.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"Q3110.label\" cr-conditioned=\"Q3110\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3110',period:null}\" ng-model=\"getAnswerValue('Q3110').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3120.label\" cr-conditioned=\"Q3120\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3120',period:null}\" ng-model=\"getAnswerValue('Q3120').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3130.label\" cr-conditioned=\"Q3130\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3130',period:null}\" ng-model=\"getAnswerValue('Q3130').booleanValue\"></cr-boolean>\n    </cr-question>\n    \n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/actions/topics/actions-heating.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_heating_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.heating.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n\n    <cr-question label=\"Q3310.label\"  cr-conditioned=\"Q3310\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3310',period:null}\" ng-model=\"getAnswerValue('Q3310').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3320.label\" cr-conditioned=\"Q3320\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3320',period:null}\" ng-model=\"getAnswerValue('Q3320').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3330.label\" cr-conditioned=\"Q3330\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3330',period:null}\" ng-model=\"getAnswerValue('Q3330').booleanValue\"></cr-boolean>\n    </cr-question>\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/actions/topics/actions-presence.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_presence_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.presence.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"Q3210.label\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3210',period:null}\" ng-model=\"getAnswerValue('Q3210').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3211.label\" cr-conditioned=\"Q3211\" >\n        <cr-slider cr-tootip-on-respond=\"{key:'Q3211',period:null}\" ng-model=\"getAnswerValue('Q3211').stringValue\" ng-steps=\"getNumericOptions('Q3211',1,4,1)\"></cr-slider>\n    </cr-question>\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/actions/topics/actions-lighting.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_lighting_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.lighting.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n    <cr-question label=\"Q3410.label\"  cr-conditioned=\"Q3410\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3410',period:null}\" ng-model=\"getAnswerValue('Q3410').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3420.label\" cr-conditioned=\"Q3420\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3420',period:null}\" ng-model=\"getAnswerValue('Q3420').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3510.label\" cr-conditioned=\"Q3510\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3510',period:null}\" ng-model=\"getAnswerValue('Q3510').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3530.label\" cr-conditioned=\"Q3530\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3530',period:null}\" ng-model=\"getAnswerValue('Q3530').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3610.label\" cr-conditioned=\"Q3610\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3610',period:null}\" ng-model=\"getAnswerValue('Q3610').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3620.label\" cr-conditioned=\"Q3620\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3620',period:null}\" ng-model=\"getAnswerValue('Q3620').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3630.label\" cr-conditioned=\"Q3630\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3630',period:null}\" ng-model=\"getAnswerValue('Q3630').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3631.label\" cr-conditioned=\"Q3631\">\n        <cr-slider cr-tootip-on-respond=\"{key:'Q3631',period:null}\" ng-model=\"getAnswerValue('Q3631').stringValue\" ng-steps=\"getNumericOptions('Q3631',1,4,1)\"></cr-slider>\n    </cr-question>\n\n\n    <cr-question label=\"Q3640.label\" cr-conditioned=\"Q3640\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3640',period:null}\" ng-model=\"getAnswerValue('Q3640').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3810.label\" cr-conditioned=\"Q3810\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3810',period:null}\" ng-model=\"getAnswerValue('Q3810').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/household/actions/topics/actions-dinner.html', "<div class=\"modal-header\">\n    <img class=\"cr-modal-title-icon\" src=\"/assets/images/modal/topic_dinner_title.png\" alt=\"\"/>\n    <img class=\"cr-modal-title-separator\" src=\"/assets/images/modal/title_verticalSeparator.png\" alt=\"\"/>\n    <h3 class=\"modal-title cr-modal-title-text\" ng-bind-html=\"'topic.dinner.label' | translate\"></h3>\n</div>\n<div class=\"modal-body\">\n\n\n    <cr-question label=\"Q3710.label\" cr-conditioned=\"Q3710\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3710',period:null}\" ng-model=\"getAnswerValue('Q3710').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3711.label\" cr-conditioned=\"Q3711\" >\n        <cr-slider cr-tootip-on-respond=\"{key:'Q3711',period:null}\" ng-model=\"getAnswerValue('Q3711').stringValue\" ng-steps=\"getNumericOptions('Q3711',1,4,1)\"></cr-slider>\n    </cr-question>\n\n    <cr-question label=\"Q3720.label\" cr-conditioned=\"Q3720\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3720',period:null}\" ng-model=\"getAnswerValue('Q3720').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3730.label\" cr-conditioned=\"Q3730\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3730',period:null}\" ng-model=\"getAnswerValue('Q3730').booleanValue\"></cr-boolean>\n    </cr-question>\n\n\n    <cr-question label=\"Q3750.label\" cr-conditioned=\"Q3750\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3750',period:null}\" ng-model=\"getAnswerValue('Q3750').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3760.label\" cr-conditioned=\"Q3760\">\n        <cr-boolean cr-tootip-on-respond=\"{key:'Q3760',period:null}\" ng-model=\"getAnswerValue('Q3760').booleanValue\"></cr-boolean>\n    </cr-question>\n\n    <cr-question label=\"Q3740.label\" cr-conditioned=\"Q3740\" >\n        <cr-slider cr-tootip-on-respond=\"{key:'Q3740',period:null}\" ng-model=\"getAnswerValue('Q3740').stringValue\" ng-steps=\"getNumericOptions('Q3740',1,4,1)\"></cr-slider>\n    </cr-question>\n\n\n    <cr-question label=\"Q3741.label\" cr-conditioned=\"Q3741\" >\n        <cr-slider cr-tootip-on-respond=\"{key:'Q3741',period:null}\" ng-model=\"getAnswerValue('Q3741').stringValue\" ng-steps=\"getNumericOptions('Q3741',1,4,1)\"></cr-slider>\n    </cr-question>\n\n\n</div>\n<div class=\"modal-footer\">\n    <button class=\"btn btn-primary\" ng-click=\"close()\" ng-bind-html=\"'generic.close' | translate\"></button>\n</div>");$templateCache.put('$/angular/views/welcome.html', "<div class=\"cr-welcome\">\n\n    <div class=\"container\">\n        <div class=\"row\">\n\n            <div class=\"col-md-12\">\n\n                <h1 ng-bind-html=\"'welcome.title' | translate\"></h1>\n                <h2 ng-bind-html=\"'welcome.title2' | translate\"></h2>\n\n            </div>\n        </div>\n\n        <div class=\"row\">\n\n            <div class=\"col-md-4 col-md-offset-1\">\n                <h3 ng-bind-html=\"'welcome.intro' | translate\"></h3>\n\n                <div class=\"well\">\n                    <button class=\"btn cr-theme-green cr-mega-button cr-centered\" ng-click=\"toHouseHold()\">\n                        <i class=\"fa fa-home fa-2x\"></i>\n                        <span ng-bind-html=\"'generic.household' | translate\"></span>\n                    </button>\n                    <br/>\n                    <button class=\"btn cr-theme-orange cr-mega-button cr-centered\"\n                            ng-disabled=\"true\"\n                            ng-click=\"alert('not implemented yet !')\">\n                        <i class=\"fa fa-building fa-2x\"></i>\n                        <span ng-bind-html=\"'generic.enterprise' | translate\"></span>\n                    </button>\n                </div>\n\n                <p ng-bind-html=\"'welcome.outro' | translate\"></p>\n            </div>\n\n            <div class=\"col-md-4 col-md-offset-2\">\n\n                <h2 ng-bind-html=\"'welcome.login_message' | translate\"></h2>\n\n                <cr-question label=\"generic.emailAddress\">\n                    <cr-text ng-model=\"loginParams.email\" name=\"email\"></cr-text>\n                </cr-question>\n                <cr-question label=\"generic.password\">\n                    <cr-text ng-model=\"loginParams.password\" name=\"password\" type=\"password\"></cr-text>\n                </cr-question>\n                <center>\n                    <button class=\"btn btn-small cr-mega-button\" ng-click=\"login()\">\n                        <i class=\"fa fa-sign-in\"></i>\n                        <span ng-bind-html=\"'welcome.button.login' | translate\"></span>\n                    </button>\n                </center>\n\n                <h3 ng-bind-html=\"'welcome.forgot_password' | translate\"></h3>\n\n                <p ng-bind-html=\"'welcome.forgot_password.desc' | translate\"></p>\n\n                <cr-question label=\"generic.emailAddress\">\n                    <cr-text ng-model=\"forgotPasswordParams.email\" name=\"email\"></cr-text>\n                </cr-question>\n                <center>\n                    <button class=\"btn btn-small cr-mega-button\" ng-click=\"forgotPassword()\">\n                        <i class=\"fa fa-gear\"></i>\n                        <span ng-bind-html=\"'welcome.button.forgotPassword' | translate\"></span>\n                    </button>\n                </center>\n\n            </div>\n        </div>\n    </div>\n\n    <!-- Controls demo -->\n    <br/> <br/> <br/> <br/> <br/> <br/> <br/>\n    <hr/>\n    <div class=\"container\">\n        <div class=\"row\">\n            <div class=\"col-md-12 text-left\">\n                <a ui-sref=\"root.controlsDemo\">Controls demo ></a>\n            </div>\n        </div>\n    </div>\n\n\n</div>");});