# Project structure

## Introduction

- **Play Framework** is used for the back-end, in conjunction with the **EBean** ORM.

- **AngularJS** is used for the front-end.

    - ".less" are files written in the **LESS** language to produce CSS files once compiled.
    - ".coffee" are files written in the **CoffeeScript** language to produce JavaScript files once compiled.
    - ".jade" are files using the **JADE** template language.

## AngularJS | LESS

**app/assets**

LESS files. LESS is a language to manipulate CSS with variables, functions and inheritance. LESS files are compiled
into regular CSS files used by Web applications. Cf. http://lesscss.org.

## AngularJS | CoffeeScript

The AngularJS part of the applications makes use of CoffeeScript. CoffeeScript is a language that compiles into
JavaScript and adds Ruby, Python and Haskell-like syntax. Cf. http://coffeescript.org/

**app/eu.factorx.citizens/angular**

Only contains main.coffee.

main.coffee is mainly feeding the AngularJS router.

**app/eu.factorx.citizens/angular/controllers**

The controllers of the AngularJS part of the application.

For example, the WelcomeCtrl.coffee file defines the controllers to which the welcome.html file is bound. The ng-click
attributes refer to:

toHousehold()
toEnterprise()
toAuthority()
login()
forgotPassword()

All these methods are implemented in the coffee file.

The login() method, for example, will populate a dto based on the email and password information entered in textfields
and post them as JSON to the /login route of Play.

**app/eu.factorx.citizens/angular/directives**

The directives of the AngularJS application. Directives are used whenever DOM manipulation is required.

There is one directive per 'package' divided into two files: a directive.coffee file and a template.html or
template.jade file (Jade is a template language. Cf. http://jade-lang.com).

For example, if we consider crLogin, directive.coffee defines a link function that will make available to the
application scope the fullname, isAuthenticated, toAccount and logout functionality required by the html snippet
of the template.html page.

**app/eu.factorx.citizens/angular/filters**

The filters of the AngularJS application.

**app/eu.factorx.citizens/angular/services**

The services of the AngularJS application.

**app/eu.factorx.citizens/angular/views**

The views of the application, such as welcome.html, login.html, etc.

AngularJS pages are not served directly through URLs. Instead, they are stored in the AngularJS template cache at
compile time. Cf. the AngularCompiler.scala file.

On Windows, we first had a problem when composing the urls used as the first parameter of:

$templateCache.put(url, content);

They were indeed containing backslashes. Their corresponding value would hence not be found when an appropriate url,
with slashes only, would be used as keys (i.e. we were having keys like $/angular/views\welcome.html instead of
$/angular/views/welcome.html).

## AngularJS | Compilation

Under the project[citizens-build] node, we can find a Build.scala file that executes the AngularCompilerTask that
can be found at the same place. The AngularCompilerTask delegates to AngularCompiler. The root of the AngularJS part
of the application is provided: app/eu/factorx/citizens/angular. Within this path, the compiler is pointed to '.coffee',
'.jade', views and other required files.

## Play Framework

**app/eu.factorx.citizens/controllers**

The application controllers. Receive and output DTO objects. Controllers convert DTO inputs in Business Objects,
invoke services, and send back results as DTO outputs.

DTO objects are defined in the 'dto' package.
Business objects are defined in the 'model' package.
Services are defined in the 'service' package.

Examples of controllers:

- AccountController with methods such as login(), changePassword(), etc.
- CalculationController with methods such as calculatePotentialReduction(), calculateEffectiveReduction(), etc.

**app/eu.factorx.citizens/converter**

Converts Business Objects to DTOs. This is done 'manually' (no Dozer or whatever). There is no DTO to Business
Object converter as information are directly extracted from DTOs as needed by services.

Examples of converters:

- AccountToAccountDTO Converter.
- AnswerToAnswerDTO Converter.

**app/eu.factorx.citizens/dto**

Data Transfer Objects used to convey data between the back-end and front-end parts of the application.

Examples of DTOs:

- AccountDTO with fields such as firstName, lastName, etc.
- AnswerDTO with fields such as questionKey, answerValues, etc.

**app/eu.factorx.citizens/model**

Business Objects of the application classified in specific packages.

Examples of Business Objects:

- account/Account with fields such as firstName, lastName, etc.
- survey/Answer with fields such as questionCode, answerValues, etc.

Business Objects are persisted into a PostgreSQL database using EBean. EBean is an object-relational mapping
product written in Java advertised to be simpler than JPA (cf. http://ebean-orm.github.io/docs/). EBean makes
use of JPA annotations. The datasource of the application is defined in the application.conf file of Play.

**app/eu.factorx.citizens/service**

Services of the application. Divided in interfaces and implementations (cf. 'impl' package).

Examples of services:

- AccountService implemented by impl/AccountServiceImpl with methods such as findByEmail() or saveOrUpdate().
- CalculationService implemented by impl/CalculationServiceImpl with methods such as calculatePotentialReduction(),
  calculateEffectiveReduction(), etc.

**app/eu.factorx.citizens/util**

Utility classes.

Examples of utilities:

- email/messages/EmailMessage that encapsulate the definition of email messages.
- security/LoginAttemptManager that manages attempts to connect to the application.

**app/eu.factorx.citizens/views**

Only contains index.scala.html file which is the root of the application.

## Internationalisation

Several resource bundles have been defined under conf/translation:

- Email
- Interfaces
- Messages
- Surveys

Each bundle encapsulates one properties file for French and one properties file for Dutch.

In the AngularJS part of the application, translations are typically handled the following way:

&inf;span ng-bind-html="'welcome.button.login' | translate"&sup;&inf;/span&sup;

ng-bind-html will here evaluate 'welcome.button.login' | translate and insert the resulting content into the span
element.

## Document generation with Velocity

The application makes use of Velocity (http://velocity.apache.org) to generate some documents, such as email content.

The Velocity templates are located in public/vm.
