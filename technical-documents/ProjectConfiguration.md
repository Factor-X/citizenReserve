



## Routes definition

The router is the component in charge of translating each incoming HTTP request to a Play Action. It relies on
definitions part of the conf/routes file.

Example:

GET         /                                       @eu.factorx.citizens.controllers.ApplicationController.index()

This defines that a GET on the application root URL '/' should be handled by the index() method of the
eu.factorx.citizens.controllers.ApplicationController controller.
