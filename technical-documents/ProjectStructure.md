
# Project Structure

- Play Framework is used for the back-end.

- AngularJS is used for the front-end.

    - ".less" are files written in the LESS language to produce CSS files once compiled.
    - ".coffee" are files written in the CoffeeScript language to produce JavaScript files once compiled.
    - ".jade" are files using the JADE template language.

## AngularJS | LESS

app/assets

    LESS files. LESS is a language to manipulate CSS with variables, functions and inheritance. LESS files are compiled
    into regular CSS files used by Web applications. Cf. http://lesscss.org.

    QU: How are less files compiled in the project?

## AngularJS | CoffeeScript

    The AngularJS part of the applications makes use of CoffeeScript. CoffeeScript is a language that compiles into
    JavaScript and adds Ruby, Python and Haskell-like syntax. Cf. http://coffeescript.org/

    QU: How are coffeescript files compiled in the project?

app/eu.factorx.citizens/angular

    controllers

    directives

        Some templates are written using JADE, which is a template language. Cf. http://jade-lang.com.

    filters

    services

    views

## Play Framework

app/eu.factorx.citizens

    controllers

        The application controllers. Receive and output DTO objects. Controllers convert DTO inputs in Business Objects,
        invoke services, and send back results as DTO outputs.

        DTO objects are defined in the 'dto' package.
        Business objects are defined in the 'model' package.
        Services are defined in the 'service' package.

        Examples of controllers:

        - AccountController with methods such as login(), changePassword(), etc.
        - CalculationController with methods such as calculatePotentialReduction(), calculateEffectiveReduction(), etc.

    converter

        Converts Business Objects to DTOs. This is done 'manually' (no Dozer or whatever). There is no DTO to Business
        Object converter as information are directly extracted from DTOs as needed by services.

        Examples of converters:

        - AccountToAccountDTO Converter.
        - AnswerToAnswerDTO Converter.

    dto

        Data Transfer Objects used to convey data between the back-end and front-end parts of the application.

        Examples of DTOs:

        - AccountDTO with fields such as firstName, lastName, etc.
        - AnswerDTO with fields such as questionKey, answerValues, etc.

    model

    service

    util

    views

        The visual parts of the application.

## Routes definition

