
# Project Structure

- Play Framework is used for the back-end, in conjunction with the EBean ORM.

- AngularJS is used for the front-end.

    - ".less" are files written in the LESS language to produce CSS files once compiled.
    - ".coffee" are files written in the CoffeeScript language to produce JavaScript files once compiled.
    - ".jade" are files using the JADE template language.

## AngularJS | LESS

**app/assets**

LESS files. LESS is a language to manipulate CSS with variables, functions and inheritance. LESS files are compiled
into regular CSS files used by Web applications. Cf. http://lesscss.org.

QU: How are less files compiled in the project?

## AngularJS | CoffeeScript

The AngularJS part of the applications makes use of CoffeeScript. CoffeeScript is a language that compiles into
JavaScript and adds Ruby, Python and Haskell-like syntax. Cf. http://coffeescript.org/

QU: How are coffeescript files compiled in the project?

**app/eu.factorx.citizens/angular**

**app/eu.factorx.citizens/angular/controllers**

**app/eu.factorx.citizens/angular/directives**

Some templates are written using JADE, which is a template language. Cf. http://jade-lang.com.

**app/eu.factorx.citizens/angular/filters**

**app/eu.factorx.citizens/angular/services**

**app/eu.factorx.citizens/angular/views**

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

The visual parts of the application.

## Routes definition

