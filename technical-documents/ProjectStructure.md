
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

    converter

    dto

    model

    service

    util

    views

        The visual parts of the application.



