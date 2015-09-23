# Project configuration

## Play Framework configuration

Play is configured via the **conf/application.conf** file.

**Database configuration**

db.default.driver = org.postgresql.Driver
db.default.url = "postgres://play:play@localhost/citizens-reserve"

**EBean configuration**

ebean.default = "eu.factorx.citizens.model.*"

This points to the package containing the entities. If not defined correctly, an EBean server error message may be
thrown.

**Logging configuration**

logger.root = ERROR

    Configuration of the root logger.

logger.play = INFO

    Configuration of the logger used by the Play Framework libraries themselves.

logger.application = INFO

    Configuration of the logger used by the application.

## Play routes definition

The Play router is the component in charge of translating each incoming HTTP request to a Play Action. It relies on
definitions part of the **conf/routes** file.

Example:

GET / @eu.factorx.citizens.controllers.ApplicationController.index()

This defines that a GET on the application root URL '/' should be handled by the index() method of the
eu.factorx.citizens.controllers.ApplicationController controller.

## AngularJS routes definition

The AngularJS part of the application makes use of AngularUI Router which is based on a finite state machine.

The main.coffee files contains all the routing. Cf. the .state sections.

Note that all AngularJS elements are stored in cache. As a result, there is never any call to the backend to serve HTML
files. Assets such as Javascript, images, CSS, etc. are served through the /assets/*file Play route.

## Batch processing

Batch processing is defined in the eu.factorx.citizens.Global class which overrides Play GlobalSettings. It relies
on the Akka scheduler (cf. http://doc.akka.io/docs/akka/snapshot/java/scheduler.html).
