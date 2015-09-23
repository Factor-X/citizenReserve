# Project setup

## Introduction

To extract the project using Intellij IDEA:

File > New > Project from Version Control > GitHub

The repository URL is:

https://github.com/Factor-X/citizens-reserve.git

## Source root identification

We need to define the app folder as a source root.

Right click > Mark Directory As > Sources Root

## Scala installation

The Scala plugin is not installed by default. Open the AngularCompiler.scala file. A message is displayed at the top of
the editor to propose the installation of the Scala plugin. Install the plugin and let Intellij IDEA restart.

Now, we have at the top of the same file a message suggesting to install the Scala SDK. We go to:

http://www.scala-lang.org/download/

And install Scala (at the date of this writing in 2015.09, Scala 2.11.7).

To add Scala support, we will need to create a library by pointing the IDE to the Scala install folder.

We are now treating another message at the top of the editor telling that AngularComposite.scala is SBT build file.
We import the project as suggested.

## Play framework support

Some files, such as AccountController.java, can't resolve some symbols that belong to Play. We need to activate the
support. On the citizens-reserve project node:

Right click > Add Framework Support ... > Play 2.x

The resolution problem should disappear.

## AngularJS support

Nothing particular to install.

## Rebuild the project

Build > Rebuild Project

The output path is not defined for module 'citizens-reserve' so we have an error message. The 'Project Structure'
window pops up automatically.

We have three modules: 'citizens', 'citizens-build' and 'citizens-reserve'.

Actually, if we go to .idea > modules, we notice that we only have two modules, 'citizens' and 'citizens-build'.

On the 'citizens-reserve' module, we decide to remove the content root (so that there is no content root anymore for
that module).

We also fixed the problems (see left pane).

## Start the project

Run > Run 'Play 2'

Play server is starting and listening on port 9000. We go to http://localhost:9000. There is some compilation going on
(see the messages at the console). Finally, a page containing an error message is displayed.

This allows us to discover the configuration file of the project, located at:

citizens-reserve/conf/application.conf

We need a citizens-reserve database running on PostgreSQL with username and password play/play as deduced from:

db.default.url = "postgres://play:play@localhost/citizens-reserve"

We can obtain a database backup online from the Heroku interface (see file Heroku.md) and install it locally.

Once all of this done, we can restart the project and reach the welcome page of the application.

